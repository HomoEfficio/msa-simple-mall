package io.homo_efficio.biz.common.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.homo_efficio.biz.common.exception.ResourceNotFoundException;
import io.homo_efficio.biz.common.message.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author homo.efficio@gmail.com
 * created on 2020-07-24
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final MessageResolver messageResolver;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();

        if (throwable instanceof ResourceNotFoundException) {
            return getErrorResponseMono(serverWebExchange, bufferFactory,
                    getErrorResponse(serverWebExchange, (ResourceNotFoundException)throwable));
        }
        if (throwable instanceof IllegalArgumentException) {
            return getErrorResponseMono(serverWebExchange, bufferFactory,
                    getErrorResponse(serverWebExchange, (IllegalArgumentException)throwable));
        }

        serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse()
                .writeWith(Mono.just(bufferFactory.wrap("Unknown error".getBytes())));
    }

    private ErrorResponse getErrorResponse(ServerWebExchange serverWebExchange, ResourceNotFoundException e) {
        log.error("handleResourceNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse;
        if (e.getResourceId() == null) {
            errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND_WITH_NON_ID,
                    messageResolver.getMessage(ErrorCode.ENTITY_NOT_FOUND_WITH_NON_ID.getErrorCode(), e.getMessage()),
                    serverWebExchange.getRequest().getURI().toString());
        } else {
            errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND,
                    messageResolver.getMessage(ErrorCode.ENTITY_NOT_FOUND.getErrorCode(),
                            e.getResourceClassSimpleName(), e.getResourceId()),
                    serverWebExchange.getRequest().getURI().toString());
        }
        return errorResponse;
    }

    private ErrorResponse getErrorResponse(ServerWebExchange serverWebExchange, IllegalArgumentException e) {
        log.error("handleIllegalArgumentException: {}", e.getMessage());
        return ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT,
                messageResolver.getMessage(ErrorCode.ILLEGAL_ARGUMENT.getErrorCode(), e.getMessage()),
                serverWebExchange.getRequest().getURI().toString());
    }

    private Mono<Void> getErrorResponseMono(ServerWebExchange serverWebExchange, DataBufferFactory bufferFactory, ErrorResponse errorResponse) {
        DataBuffer dataBuffer;
        try {
            dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(errorResponse));
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
            dataBuffer = bufferFactory.wrap("JSON processing error".getBytes());
        }
        serverWebExchange.getResponse()
                .setRawStatusCode(errorResponse.getStatusCode());
        serverWebExchange.getResponse()
                .getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse()
                .writeWith(Mono.just(dataBuffer));
    }
}
