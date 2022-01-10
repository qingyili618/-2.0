package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName("grade")
@Data
public class Grade {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String thesisid;
    private String reviewer;
    private Integer grade1;
    private Integer grade2;
    private Integer grade3;
    private Integer grade4;
    private Integer grade5;
    private Float total;
    private Integer qualified;
    private String comments;
    @TableField(fill = FieldFill.INSERT)
    private Date submitTime;
}
