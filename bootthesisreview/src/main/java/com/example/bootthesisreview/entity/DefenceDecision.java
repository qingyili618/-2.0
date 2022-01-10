package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("defencedecision")
@Data
public class DefenceDecision {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer thesisid;
    private Integer grade;
    private Integer noes;
    private Integer yesses;
    private String decision;
    private String comment;
    private String record;
}

