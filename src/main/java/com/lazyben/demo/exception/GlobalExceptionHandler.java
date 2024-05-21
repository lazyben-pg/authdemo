package com.lazyben.demo.exception;

import com.lazyben.demo.pojo.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        return ResponseEntity.status(e.getStatusCode())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ErrorResponse.builder()
                                                .bizCode(e.getBizCode())
                                                .msg(e.getMsg())
                                                .errorType(e.getErrorType())
                                                .build());
    }
}
