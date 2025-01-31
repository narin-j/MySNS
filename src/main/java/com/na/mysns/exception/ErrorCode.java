package com.na.mysns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, "User name is already exist"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    ;

    // 어떤 에러이냐 따라 http status 변경
    private HttpStatus status;
    private String message;
}
