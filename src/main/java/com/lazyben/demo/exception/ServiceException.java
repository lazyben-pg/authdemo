package com.lazyben.demo.exception;

import lombok.*;

import javax.lang.model.type.ErrorType;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceException extends RuntimeException {
    private String msg;
    private ErrorType errorType;
    private int bizCode;
    private int statusCode;

    public enum ErrorType {
        CLIENT, SERVER, UNKNOWN
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
