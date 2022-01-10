package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Back;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.mapper.BackMapper;
import com.example.bootthesisreview.service.IBackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/back")
public class BackController {
    @Resource
    BackMapper backMapper;
    @Resource
    private IBackService backService;

    @GetMapping("/getbackreviewer")
    public Result<?> getbackreviewer(@RequestParam String thesisid ){
        LambdaQueryWrapper<Back> wrapper= Wrappers.<Back>lambdaQuery();
        if(StrUtil.isNotBlank(thesisid)) {
            wrapper.eq(Back::getThesisid,thesisid);
        }
        return Result.success(backMapper.selectList(wrapper));
    }

    @PutMapping("/insert")
    public Result<?> save(@RequestBody Back back) {
        backService.insert(back);
        return Result.success();
    }
}
