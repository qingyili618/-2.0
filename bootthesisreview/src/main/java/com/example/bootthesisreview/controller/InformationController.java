package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.Information;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.InformationMapper;
import com.example.bootthesisreview.service.IInformationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/information")
public class InformationController {
    @Resource
    private InformationMapper informationMapper;

    @Resource
    private IInformationService informationService;
    @GetMapping("/count")
    public Result<?> count(@RequestParam String userid) {
        return Result.success(informationMapper.infocount(userid));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam String userid) {
        LambdaQueryWrapper<Information> wrapper= Wrappers.<Information>lambdaQuery();

        wrapper.eq(Information::getUserid,userid);
//        wrapper.eq(Information::getStatus,"未读");

        //Page<Thesis> thesisPage=thesisMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<Information> infoPage= informationService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(infoPage);

    }
    @PutMapping
    public Result<?> update(@RequestBody Information information) {
        //userMapper.updateById(user);
        informationService.updateById(information);
        return Result.success();

    }
    @PostMapping
    public Result<?> insert(@RequestBody Information information) {
        informationService.insert(information);
        return Result.success();
    }
}
