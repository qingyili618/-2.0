package com.example.bootthesisreview.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Defense;
import com.example.bootthesisreview.mapper.DefenseMapper;
import com.example.bootthesisreview.service.IDefenseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/defense")
public class DefenseController {
    @Resource
    DefenseController defenseController;
    @Resource
    private IDefenseService defenseService;
    @Resource
    DefenseMapper defenseMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Defense> wrapper= Wrappers.<Defense>lambdaQuery();
        //if(StrUtil.isNotBlank(search)) {
        //    wrapper.like(User::getNickName,search);
        //}

        //Page<Thesis> thesisPage=thesisMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<Defense> defensePage=defenseService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(defensePage);

    }

    @PostMapping
    public Result<?> save(@RequestBody Defense defense) {
        //if(user.getPassword()==null){
        //    user.setPassword("123456");
        //}
        //gradeMapper.insert(grade);
        defenseMapper.insert(defense);

        return Result.success(defense.getId());


    }
    @GetMapping("/defCommitteeVO")
    public Result<?> present(@RequestParam String batch) {
        if (StrUtil.isNotBlank(batch)){
            return Result.success(defenseMapper.defCommitteeListForBatch(batch));
        }else {
            return Result.success(defenseMapper.defCommitteeList());
        }

    }







}
