package com.lazyben.demo.pojo;

import com.lazyben.demo.constant.BizCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStructure<T> {
    private int bizCode;
    private T data;

    public static <T> ResponseStructure<T> success(T data) {
        return new ResponseStructure<T>(BizCode.SUCCESS, data);
    }
}
