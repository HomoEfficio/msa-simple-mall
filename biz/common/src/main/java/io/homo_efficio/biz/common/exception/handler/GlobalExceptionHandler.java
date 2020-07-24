package io.homo_efficio.biz.common.exception.handler;

import io.homo_efficio.biz.common.exception.ResourceNotFoundException;
import io.homo_efficio.biz.common.message.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-24
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageResolver messageResolver;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("handleResourceNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse;
        if (e.getResourceId() == null) {
            errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND_WITH_NON_ID,
                    messageResolver.getMessage(ErrorCode.ENTITY_NOT_FOUND_WITH_NON_ID.getErrorCode(), e.getMessage()),
                    "RequestURL NOT IMPLEMENTED");
        } else {
            errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND,
                    messageResolver.getMessage(ErrorCode.ENTITY_NOT_FOUND.getErrorCode(),
                            e.getResourceClassSimpleName(), e.getResourceId()),
                    "RequestURL NOT IMPLEMENTED");
//                    ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("handleIllegalArgumentException: {}", e.getMessage());
        ErrorResponse errorResponse;
        errorResponse = ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT,
                messageResolver.getMessage(ErrorCode.ILLEGAL_ARGUMENT.getErrorCode(), e.getMessage()),
                "RequestURL NOT IMPLEMENTED");
//                    ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
