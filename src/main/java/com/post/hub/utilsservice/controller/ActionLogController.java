package com.post.hub.utilsservice.controller;

import com.post.hub.utilsservice.model.constant.ApiLogMessage;
import com.post.hub.utilsservice.model.dto.ActionLogDTO;
import com.post.hub.utilsservice.model.request.ActionLogIsReadRequest;
import com.post.hub.utilsservice.model.response.ActionLogUpdateResultDTO;
import com.post.hub.utilsservice.model.response.PaginationResponse;
import com.post.hub.utilsservice.model.response.UtilsResponse;
import com.post.hub.utilsservice.service.ActionLogService;
import com.post.hub.utilsservice.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${endpoints.action.logs}")
@RequiredArgsConstructor
public class ActionLogController {
    private final ActionLogService service;

    @PutMapping("${endpoints.id}")
    public ResponseEntity<UtilsResponse<ActionLogUpdateResultDTO>> setIsReadEqualsTrue(
            @RequestBody @Valid ActionLogIsReadRequest request
    ) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        return ResponseEntity.ok(service.setIsReadEqualsTrue(request));
    }

    @GetMapping("${endpoints.id}")
    public ResponseEntity<UtilsResponse<ActionLogDTO>> getActionLogById(
            @PathVariable(name = "id") Integer logId,
            @RequestParam(name = "userId", required = false) Integer userId
    ) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        return ResponseEntity.ok(service.getActionLogById(logId, userId));
    }

    @GetMapping
    public ResponseEntity<UtilsResponse<PaginationResponse<ActionLogDTO>>> findAllActionLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        UtilsResponse<PaginationResponse<ActionLogDTO>> response = service.findAllActionLogs(pageable);

        return ResponseEntity.ok(response);
    }
}
