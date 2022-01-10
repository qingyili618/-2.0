package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("back")
@Data
public class Back {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String thesisid;
    private String reviewer;
}
