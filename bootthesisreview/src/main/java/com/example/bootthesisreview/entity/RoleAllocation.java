package com.example.bootthesisreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("roleallocation")
@Data
public class RoleAllocation {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String employeeId;
    private String role;
}
