package com.post.hub.utilsservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilsResponse<P> {

    private String message;
    private P payload;
    private boolean success;

    public static <P> UtilsResponse<P> create(String message, P payload, boolean success) {
        return new UtilsResponse<>(message, payload, success);
    }

    public static <P> UtilsResponse<P> createFailed(String message) {
        return new UtilsResponse<>(message, null, false);
    }

    public static <P> UtilsResponse<P> createFailed(String message, P payload) {
        return new UtilsResponse<>(message, payload, false);
    }

    public static <P> UtilsResponse<P> createSuccessful(P payload) {
        return new UtilsResponse<>(StringUtils.EMPTY, payload, true);
    }

}
