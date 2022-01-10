package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

@TableName("teacher")
@Data
public class Teacher {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String employeeId;
    private String name;
    private Integer isstaff;
    private String discipline;
    private String direction;
    private String department;
    private Date birthday;
    private String field;
}
