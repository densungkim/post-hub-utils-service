package com.post.hub.utilsservice.model.entity;

import com.post.hub.utilsservice.model.enums.ActionType;
import com.post.hub.utilsservice.model.enums.PostHubService;
import com.post.hub.utilsservice.model.enums.PriorityType;
import com.post.hub.utilsservice.utils.enums.converter.PostHubServiceConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "action_logs")
@Getter
@Setter
public class ActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "action_type", nullable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    private ActionType actionType;

    @Column(name = "priority_type")
    @Enumerated(value = EnumType.STRING)
    private PriorityType priorityType;

    @Column(name = "service", nullable = false, updatable = false)
    @Convert(converter = PostHubServiceConverter.class)
    private PostHubService service;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "message", nullable = false, updatable = false)
    private String message;

    @Column(name = "is_read", nullable = false, updatable = false)
    private Boolean isRead;

}
