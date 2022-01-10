package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Committee;
import com.example.bootthesisreview.entity.Committeevote;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.mapper.CommitteeMapper;
import com.example.bootthesisreview.mapper.CommitteeVoteMapper;
import com.example.bootthesisreview.mapper.ThesisMapper;
import com.example.bootthesisreview.service.ICommitteeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/committee")
public class CommitteeController {
    @Resource
    CommitteeMapper committeeMapper;
    @Resource
    CommitteeVoteMapper committeeVoteMapper;
    @Resource
    ThesisMapper thesisMapper;
    @Resource
    private ICommitteeService committeeService;
    @GetMapping
    public Result<?> present(@RequestParam String lower_num,
                             @RequestParam String lower_avg1,
                             @RequestParam String lower_avg2,
                             @RequestParam String lower_min,
                             @RequestParam String lower1,
                             @RequestParam String lower2,
                             @RequestParam String thesis_status,
                             @RequestParam String batch) {
//        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
//        //Page<User> userPage=userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
//        Page<User> userPage=userService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        if (StrUtil.isNotBlank(batch)){
            return Result.success(committeeMapper.gradedThesisListForBatch(lower_num,lower_avg1,lower_avg2,lower_min, lower1,lower2,thesis_status,batch));
        }else {
            return Result.success(committeeMapper.gradedThesisList(lower_num,lower_avg1,lower_avg2,lower_min,lower1,lower2,thesis_status));
        }


    }
    @PostMapping
    public Result<?> save(@RequestBody Committee committee) {

        committeeMapper.insert(committee);

        return Result.success(committee.getId());

    }
    @PostMapping("/review")
    public Result<?> Getcomment(@RequestBody Thesis thesis){
//        QueryWrapper<GradeVO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("url",thesisid);
//        List<GradeVO> thesisgrade =  CommitteeService.selectList(thesisid,queryWrapper);
        return Result.success(committeeMapper.committeeList(thesis.getThesisid()));
    }
    @PutMapping("/vote")
    public Result<?> save(@RequestBody Thesis thesis){
        // 修改投票的票数
        QueryWrapper<Committeevote> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("thesisid",thesis.getThesisid()).eq("committeeid",thesis.getMemo()).eq("batch",thesis.getBatch());
//        queryWrapper.eq("committeeid",committeeid);  // 在这里可以决定是第几次开会
        List<Committeevote> committeeVotes = committeeVoteMapper.selectList(queryWrapper);
        Committeevote committeeVote1 = new Committeevote();
        committeeVote1 = committeeVotes.get(0);
        if(committeeVote1.getYesses()==null){
            committeeVote1.setYesses(0);
        }
        if(committeeVote1.getNoes()==null){
            committeeVote1.setNoes(0);
        }
        if(committeeVote1.getEqual()==null){
            committeeVote1.setEqual(0);
        }
        if(thesis.getId()==1){
                committeeVote1.setYesses(committeeVote1.getYesses()+1);
        }
        else if(thesis.getId() == 2){
                committeeVote1.setNoes(committeeVote1.getNoes()+1);
        }
        else{
                committeeVote1.setEqual(committeeVote1.getEqual()+1);
        }
        committeeVoteMapper.updateById(committeeVote1);
//        // 所有人都投票过后，修改论文状态
//        if (committeeVote1.getNoes()+committeeVote1.getYesses()+committeeVote1.getEqual()==committeeVote1.getVoters()){
//        QueryWrapper<Thesis> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("thesisid",thesis.getThesisid()).eq("batch", thesis.getBatch());
//        List<Thesis> theses = thesisMapper.selectList(queryWrapper1);
//        Thesis thesis1 = theses.get(0);
//        if(committeeVote1.getYesses() >= 2* committeeVote1.getNoes()){
//            thesis1.setStatus(5);
//        }
//            if(committeeVote1.getYesses() < 2* committeeVote1.getNoes()){
//                thesis1.setStatus(6);
//            }
//        thesisMapper.updateById(thesis1);}
        return Result.success();
    }

}