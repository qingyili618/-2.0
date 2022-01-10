package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName("batch")
@Data
public class Batch {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String semester;
    private String type;
    private Date startTime;
    private Date endTime;
    private String academicYear;
    private Integer thesisNumber;
}
