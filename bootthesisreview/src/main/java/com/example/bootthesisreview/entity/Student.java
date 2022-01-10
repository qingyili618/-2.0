package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@TableName("student")
@Data
public class Student {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String studentId;
    private String name;
    private Date entryDate;
}
