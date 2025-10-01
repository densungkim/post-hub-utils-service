package com.post.hub.utilsservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PostHubService {
    IAM_SERVICE("iam-service"),
    UNDEFINED_SERVICE("undefined-service");

    private final String value;

    public static PostHubService fromValue(String value) {
        return Arrays.stream(PostHubService.values())
                .filter(v -> Objects.equals(v.value, value))
                .findFirst()
                .orElse(PostHubService.UNDEFINED_SERVICE);
    }
}
