package io.homo_efficio.monolith.simple_mall._common.exception.handler;

import lombok.Getter;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-30
 */
@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400, "COMMON_001"),
    ENTITY_NOT_FOUND(404, "COMMON_002"),
    ENTITY_NOT_FOUND_WITH_NON_ID(404, "COMMON_003"),
    ;

    private final int statusCode;
    private final String errorCode;

    ErrorCode(int statusCode, String errorCode) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
