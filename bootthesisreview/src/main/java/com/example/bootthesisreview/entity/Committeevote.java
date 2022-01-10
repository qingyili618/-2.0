package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("committeevote")
@Data
public class Committeevote {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String comment;
    private Integer noes;
    private Integer yesses;
    private Integer equal;
    private Integer voters;
    private String thesisid;
    private Integer committeeid;
    private Integer batch;
}
