package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.GradeMapper;
import com.example.bootthesisreview.service.IGradeService;
import com.example.bootthesisreview.service.IThesisService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/grade")
public class GradeController {
    @Resource
    GradeMapper gradeMapper;
    @Resource
    private IGradeService gradeService;

    @PostMapping("/insert")
    public Result<?> save(@RequestBody Grade grade) {
        //if(user.getPassword()==null){
        //    user.setPassword("123456");
        //}
        //gradeMapper.insert(grade);
        gradeService.insert(grade);

        return Result.success();
    }
    @PostMapping
    public Result<?> update(@RequestBody Grade grade) {
        //if(user.getPassword()==null){
        //    user.setPassword("123456");
        //}
        //gradeMapper.insert(grade);
        gradeService.updateById(grade);

        return Result.success();

    }
    @GetMapping("/getreviewer")
    public Result<?> present1(@RequestParam String thesisid ){
        LambdaQueryWrapper<Grade> wrapper= Wrappers.<Grade>lambdaQuery();
        if(StrUtil.isNotBlank(thesisid)) {
            wrapper.eq(Grade::getThesisid,thesisid);
        }
        return Result.success(gradeMapper.selectList(wrapper));
    }
    @GetMapping
    public Result<?> present(@RequestParam String thesisid ){
        LambdaQueryWrapper<Grade> wrapper= Wrappers.<Grade>lambdaQuery();
        if(StrUtil.isNotBlank(thesisid)) {
            wrapper.eq(Grade::getThesisid,thesisid);
        }
        return Result.success(gradeMapper.selectList(wrapper));
    }

    @GetMapping("/returnID")
    public Result<?> returnID(@RequestParam String thesisid,
                              @RequestParam String reviewer){
        return Result.success(gradeMapper.fetchID(thesisid,reviewer));
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        //userMapper.deleteById(id);
        gradeService.deletedById(id);
        return Result.success();

    }
}
