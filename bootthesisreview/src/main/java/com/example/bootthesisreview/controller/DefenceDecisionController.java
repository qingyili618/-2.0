package com.example.bootthesisreview.controller;
import cn.hutool.core.util.StrUtil;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.DefenceDecision;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/defencedecision")
public class DefenceDecisionController {

    @Resource
    com.example.bootthesisreview.mapper.DefenceDecisionMapper DefenceDecisionMapper;

    @GetMapping
    public Result<?> abcd(@RequestParam String batch) {

        if (StrUtil.isNotBlank(batch)){
            return Result.success(DefenceDecisionMapper.defencedecisionListForBatch(batch));
        }else {
            return Result.success(DefenceDecisionMapper.defencedecisionList());
        }
    }
    @PostMapping
    public Result<?> defg(@RequestBody DefenceDecision defencedecision) {
        //if(user.getPassword()==null){
        //    user.setPassword("123456");
        //}
        //gradeMapper.insert(grade);
        DefenceDecisionMapper.insert(defencedecision);
        return Result.success();
    }
}
