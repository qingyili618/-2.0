package com.example.bootthesisreview.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("committee")
@Data
public class Committee {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String address;
    private Integer afterDefence;
    private Integer attendanceNumber;
    private String chairmanId;
    private Integer supposedNumber;
    private Integer thesisNumber;
//    @TableField(fill = FieldFill.INSERT)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
