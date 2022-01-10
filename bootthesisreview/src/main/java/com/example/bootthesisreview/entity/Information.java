package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName("information")
@Data
public class Information {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String userid;
    private String role;
    private String info;
    private String send;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Date time;
}
