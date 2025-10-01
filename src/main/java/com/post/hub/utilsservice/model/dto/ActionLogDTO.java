package com.post.hub.utilsservice.model.dto;

import com.post.hub.utilsservice.model.enums.ActionType;
import com.post.hub.utilsservice.model.enums.PostHubService;
import com.post.hub.utilsservice.model.enums.PriorityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActionLogDTO {
    private Integer id;
    private Integer userId;

    private ActionType actionType;
    private PriorityType priorityType;
    private PostHubService postHubService;

    private LocalDateTime created;
    private String message;
    private Boolean isRead;
}
