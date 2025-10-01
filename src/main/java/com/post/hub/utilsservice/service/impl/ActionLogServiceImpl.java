package com.post.hub.utilsservice.service.impl;

import com.post.hub.utilsservice.kafka.model.UtilMessage;
import com.post.hub.utilsservice.mapper.ActionLogMapper;
import com.post.hub.utilsservice.model.constant.ApiErrorMessage;
import com.post.hub.utilsservice.model.dto.ActionLogDTO;
import com.post.hub.utilsservice.model.entity.ActionLog;
import com.post.hub.utilsservice.model.exception.NotFoundException;
import com.post.hub.utilsservice.model.request.ActionLogIsReadRequest;
import com.post.hub.utilsservice.model.response.ActionLogUpdateResultDTO;
import com.post.hub.utilsservice.model.response.PaginationResponse;
import com.post.hub.utilsservice.model.response.UtilsResponse;
import com.post.hub.utilsservice.repository.ActionLogRepository;
import com.post.hub.utilsservice.service.ActionLogService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionLogServiceImpl implements ActionLogService {
    private final ActionLogRepository repository;
    private final ActionLogMapper mapper;

    @Override
    public UtilsResponse<ActionLogDTO> getActionLogById(Integer logId, Integer userId) {
        ActionLog actionLog;
        if (userId == null) {
            actionLog = repository.findById(logId)
                    .orElseThrow(() -> new NotFoundException(ApiErrorMessage.NOT_FOUND_ACTION_LOG.getMessage(logId)));
        } else {
            actionLog = repository.findByIdAndUserId(logId, userId)
                    .orElseThrow(() -> new NotFoundException(ApiErrorMessage.NOT_FOUND_ACTION_LOG_FOR_USER.getMessage(logId, userId)));
        }

        return UtilsResponse.createSuccessful(mapper.toDTO(actionLog));
    }

    @Override
    public UtilsResponse<PaginationResponse<ActionLogDTO>> findAllActionLogs(Pageable pageable) {
        Page<ActionLogDTO> logs = repository.findAll(pageable)
                .map(mapper::toDTO);

        PaginationResponse<ActionLogDTO> paginationResponse = new PaginationResponse<>(
                logs.getContent(),
                new PaginationResponse.Pagination(
                        logs.getTotalElements(),
                        pageable.getPageSize(),
                        logs.getNumber() + 1,
                        logs.getTotalPages()
                )
        );

        return UtilsResponse.createSuccessful(paginationResponse);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UtilsResponse<ActionLogUpdateResultDTO> setIsReadEqualsTrue(@NotNull ActionLogIsReadRequest request) {
        Integer currentUserId = request.getUserId();

        List<ActionLog> logs = repository.findAllById(request.getIds());

        Map<Boolean, List<Integer>> partitioned = logs.stream()
                .collect(Collectors.partitioningBy(
                        log -> log.getUserId().equals(currentUserId),
                        Collectors.mapping(ActionLog::getId, Collectors.toList())
                ));

        List<Integer> allowedIds = partitioned.get(true);
        List<Integer> skippedIds = partitioned.get(false);

        int updatedCount = allowedIds.isEmpty() ? 0 : repository.setIsReadEqualsTrue(allowedIds);

        return UtilsResponse.createSuccessful(
                ActionLogUpdateResultDTO.builder()
                        .updatedCount(updatedCount)
                        .updatedIds(allowedIds)
                        .skippedIds(skippedIds)
                        .build()
        );
    }

    @Override
    @Transactional
    public ActionLog saveLogFromKafkaMessage(UtilMessage message) {
        ActionLog actionLog = mapper.mapKafkaMessageToEntity(message);
        return repository.save(actionLog);
    }
}
