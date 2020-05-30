package io.homo_efficio.monolith.simple_mall._common.exception.handler;

import io.homo_efficio.monolith.simple_mall._common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-30
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("handleEntityNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse;
        if (e.getEntityId() == null) {
            errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND_WITH_NON_ID,
                    e.getMessage(),
                    ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
        } else {
            errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND,
                    e.getMessage(),
                    ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
