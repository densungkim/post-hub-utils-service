package com.post.hub.utilsservice.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorMessage {
    INVALID_TOKEN_PROVIDED("Invalid token provided"),
    INVALID_EMAIL("Invalid email"),
    USER_ACCESS_DENIED("User %s with tenantId %s doesn't have access to requested tenantId %s resource"),
    NOT_FOUND_LOG_USER("LogId: '%s' for userId: '%s' is not found in DB"),
    NOT_FOUND_ACTION_LOG("LogId: '%s' was not found"),
    NOT_FOUND_ACTION_LOG_FOR_USER("LogId: '%s' for user: '%s' was not found"),
    EMPTY_USER_ID("User ID must not be null"),
    ACCESS_DENIED("Access denied: You can only view your own logs."),
    ACCESS_THROUGH_NOT_API_SERVICE("Unauthorized Access, you should pass through the API service"),
    UNEXPECTED_ERROR("An unexpected error occurred. Please try again later."),
    ;

    private final String value;

    public String getMessage(Object... params) {
        return String.format(value, params);
    }
}
