package com.post.hub.utilsservice.utils;

import com.post.hub.utilsservice.model.constant.ApiConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiUtils {

    public static String getMethodName() {
        try {
            return new Throwable().getStackTrace()[1].getMethodName();
        } catch (Exception cause) {
            return ApiConstants.UNDEFINED;
        }
    }

}
