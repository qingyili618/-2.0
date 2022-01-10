package com.example.bootthesisreview.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.mapper.GradeMapper;
import com.example.bootthesisreview.mapper.RoleAllocationMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/roleallocation")
public class RoleAllocationController {
    @Resource
    RoleAllocationMapper roleAllocationMapper;
    @GetMapping
    public Result<?> role(@RequestParam String employeeId){
        return Result.success(roleAllocationMapper.roleList(employeeId));
    }
    @GetMapping("/staff")
    public Result<?> getstaff(){
        return Result.success(roleAllocationMapper.staffList());
    }
}
