package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("defense")
@Data
public class Defense {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String secretary;
    private String place;
    private Date startTime;
    private Date endTime;
    private Integer expert1;
    private Integer expert2;
    private Integer expert3;
    private Integer expert4;
    private Integer expert5;


}

