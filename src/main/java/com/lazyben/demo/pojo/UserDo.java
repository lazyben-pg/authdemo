package com.lazyben.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserDo {
    private Long id;
    private String username;
    private String password;
    private String createdAt;
    private String updatedAt;
    private int status;
}
