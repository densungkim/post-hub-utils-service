package com.post.hub.utilsservice.service;

import com.post.hub.utilsservice.kafka.model.UtilMessage;
import com.post.hub.utilsservice.model.dto.ActionLogDTO;
import com.post.hub.utilsservice.model.entity.ActionLog;
import com.post.hub.utilsservice.model.request.ActionLogIsReadRequest;
import com.post.hub.utilsservice.model.response.ActionLogUpdateResultDTO;
import com.post.hub.utilsservice.model.response.PaginationResponse;
import com.post.hub.utilsservice.model.response.UtilsResponse;
import org.springframework.data.domain.Pageable;

public interface ActionLogService {

    UtilsResponse<ActionLogDTO> getActionLogById(Integer logId, Integer userId);

    UtilsResponse<PaginationResponse<ActionLogDTO>> findAllActionLogs(Pageable pageable);

    UtilsResponse<ActionLogUpdateResultDTO> setIsReadEqualsTrue(ActionLogIsReadRequest request);

    ActionLog saveLogFromKafkaMessage(UtilMessage message);

}
