package com.post.hub.utilsservice.advice;

import com.post.hub.utilsservice.model.constant.ApiConstants;
import com.post.hub.utilsservice.model.constant.ApiErrorMessage;
import com.post.hub.utilsservice.model.exception.AuthException;
import com.post.hub.utilsservice.model.exception.BadRequestException;
import com.post.hub.utilsservice.model.exception.InternalException;
import com.post.hub.utilsservice.model.exception.NotFoundException;
import com.post.hub.utilsservice.model.response.UtilsResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.zone.ZoneRulesException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class CommonControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        logStackTrace(ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<UtilsResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(UtilsResponse.createFailed(ex.getMessage()));
    }

    @ExceptionHandler(ZoneRulesException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleNotFoundException(ZoneRulesException ex) {
        logStackTrace(ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new UtilsResponse<>(ex.getMessage(), null, false));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        logStackTrace(ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new UtilsResponse<>(ex.getMessage(), null, false));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleBadRequestException(BadRequestException ex) {
        logStackTrace(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new UtilsResponse<>(ex.getMessage(), null, false));
    }

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleAuthException(AuthException ex) {
        logStackTrace(ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new UtilsResponse<>(ex.getMessage(), null, false));
    }

    @ExceptionHandler(InternalException.class)
    @ResponseBody
    protected ResponseEntity<String> handleUndefinedException(InternalException ex) {
        logStackTrace(ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorMessage.UNEXPECTED_ERROR.getMessage());
    }

    private void logStackTrace(Exception ex) {
        StringBuilder stackTrace = new StringBuilder();

        stackTrace.append(ApiConstants.ANSI_RED);

        stackTrace.append(ex.getMessage()).append(ApiConstants.BREAK_LINE);

        if (Objects.nonNull(ex.getCause())) {
            stackTrace.append(ex.getCause().getMessage()).append(ApiConstants.BREAK_LINE);
        }

        Arrays.stream(ex.getStackTrace())
                .filter(st -> st.getClassName().startsWith(ApiConstants.TIME_ZONE_PACKAGE_NAME))
                .forEach(st -> stackTrace
                        .append(st.getClassName())
                        .append(".")
                        .append(st.getMethodName())
                        .append(" (")
                        .append(st.getLineNumber())
                        .append(") ")
                );

        log.error(stackTrace.append(ApiConstants.ANSI_WHITE).toString());
    }

}
