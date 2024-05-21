package com.lazyben.demo.exception;

import org.springframework.http.HttpStatus;

import static com.lazyben.demo.constant.BizCode.AUTHENTICATION_FAILED;

public class AuthenticationException extends ServiceException {
    public AuthenticationException(String message) {
        super(message);
        this.setMsg(message);
        this.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        this.setErrorType(ErrorType.CLIENT);
        this.setBizCode(AUTHENTICATION_FAILED);
    }
}
