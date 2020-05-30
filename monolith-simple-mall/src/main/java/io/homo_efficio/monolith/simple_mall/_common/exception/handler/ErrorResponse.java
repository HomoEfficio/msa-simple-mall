package io.homo_efficio.monolith.simple_mall._common.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-30
 */
@Getter
@NoArgsConstructor
public class ErrorResponse {

    private int statusCode;
    private String errorCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldErr> errors;
    private final LocalDateTime timestamp = LocalDateTime.now();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestUrl;


    private ErrorResponse(ErrorCode errorCode, String message) {
        this.statusCode = errorCode.getStatusCode();
        this.errorCode = errorCode.getErrorCode();
        this.message = message;
    }

    private ErrorResponse(ErrorCode errorCode, String message, String requestUrl) {
        this.statusCode = errorCode.getStatusCode();
        this.errorCode = errorCode.getErrorCode();
        this.message = message;
        this.requestUrl = requestUrl;
    }

    private ErrorResponse(ErrorCode errorCode, String message, List<FieldErr> errors) {
        this(errorCode, message);
        this.errors = errors;
    }

    private ErrorResponse(ErrorCode errorCode, String message, List<FieldErr> errors, String requestUrl) {
        this(errorCode, message);
        this.errors = errors;
        this.requestUrl = requestUrl;
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, String requestUrl) {
        return new ErrorResponse(errorCode, message, requestUrl);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, BindingResult bindingResult) {
        if (bindingResult == null) return ErrorResponse.of(errorCode, message);
        return new ErrorResponse(errorCode, message, FieldErr.from(bindingResult));
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, BindingResult bindingResult, String requestUrl) {
        if (bindingResult == null) return ErrorResponse.of(errorCode, message);
        return new ErrorResponse(errorCode, message, FieldErr.from(bindingResult), requestUrl);
    }
}
