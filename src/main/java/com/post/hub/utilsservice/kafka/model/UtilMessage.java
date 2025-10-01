package com.post.hub.utilsservice.kafka.model;

import com.post.hub.utilsservice.model.enums.ActionType;
import com.post.hub.utilsservice.model.enums.PostHubService;
import com.post.hub.utilsservice.model.enums.PriorityType;
import lombok.Data;

import java.io.Serializable;

@Data
public class UtilMessage implements Serializable {
    private Integer userId;
    private ActionType actionType;
    private PriorityType priorityType;
    private PostHubService service;
    private String message;
}
