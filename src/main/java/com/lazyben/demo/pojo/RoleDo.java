package com.lazyben.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role")
public class RoleDo {
    private Long id;
    private String name;
    private String remark;
    private String createdAt;
    private String updatedAt;
    private int status;
}
