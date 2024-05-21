package com.lazyben.demo.pojo;

import com.lazyben.demo.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse {
    private int bizCode;
    private String msg;
    private ServiceException.ErrorType errorType;
}
