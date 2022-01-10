package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName("thesis")
@Data
public class Thesis {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String thesisid;
    private String studentid;
    private String title;
    private String author;
    private String supervisor;
    private String department;
    @TableField(fill = FieldFill.INSERT)
    private Date submitTime;
    private String memo;
    private String url;
    private Integer status;
    private String departmentallocation;
    private Integer reviewernum;
    private Integer reviewednum;
    private String batch;
}
