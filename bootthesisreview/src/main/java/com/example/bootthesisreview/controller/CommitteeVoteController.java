package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Committee;
import com.example.bootthesisreview.entity.Committeevote;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.mapper.CommitteeMapper;
import com.example.bootthesisreview.mapper.CommitteeVoteMapper;
import com.example.bootthesisreview.mapper.ThesisMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/committeevote")
public class CommitteeVoteController {
    @Resource
    CommitteeVoteMapper committeeVoteMapper;
    @Resource
    ThesisMapper thesisMapper;
    @Resource
    CommitteeMapper committeeMapper;

    @PostMapping
    public Result<?> save(@RequestBody Committeevote committeevote) {

        committeeVoteMapper.insert(committeevote);

        return Result.success();

    }
    @GetMapping
    public Result<?> abc(@RequestParam String batch) {
        //if(user.getPassword()==null){
        //    user.setPassword("123456");
        //}
        //gradeMapper.insert(grade);
//        gradeService.insert(grade);
        if (StrUtil.isNotBlank(batch)){
            return Result.success(committeeVoteMapper.committeevoteListForBatch(batch));
        }else {
            return Result.success(committeeVoteMapper.committeevoteList());
        }
    }

    @GetMapping("/getcommitteeid")
    public Result<?> getcommitteeid(@RequestParam String thesisid,
                                    @RequestParam String batch){
        QueryWrapper<Thesis> wrapper = new QueryWrapper<Thesis>();
        wrapper.eq("thesisid",thesisid);
        wrapper.eq("status",4);
        // 代表答辩后的论文，查找答辩后的committeeid
        if(thesisMapper.selectList(wrapper).size()==0){
//            QueryWrapper<Thesis> wrapper11 = new QueryWrapper<Thesis>();
//            wrapper11.eq("thesisid",thesisid);
//            wrapper11.eq("status",11);
            QueryWrapper<Committeevote> wrapper1 = new QueryWrapper<Committeevote>();
            wrapper1.eq("batch",batch).eq("thesisid",thesisid);
            List<Committeevote> comitteevote = committeeVoteMapper.selectList(wrapper1);
            Integer committeeid0 = 0;
            for(int i=0;i<comitteevote.size();i++){
                Integer committeeid = comitteevote.get(i).getCommitteeid();
                QueryWrapper wrapper2 = new QueryWrapper();
                wrapper2.eq("id",committeeid);
                List<Committee> committees = committeeMapper.selectList(wrapper2);
                if(committees.get(0).getAfterDefence()==2){
                    committeeid0 = committeeid;
                }
            }
            return Result.success(committeeid0);
        }
        // 返回上会的committeeid
        QueryWrapper<Committeevote> wrapper1 = new QueryWrapper<Committeevote>();
        wrapper1.eq("batch",batch).eq("thesisid",thesisid);
        List<Committeevote> comitteevote = committeeVoteMapper.selectList(wrapper1);
        Integer committeeid0 = 0;
        for(int i=0;i<comitteevote.size();i++){
            Integer committeeid = comitteevote.get(i).getCommitteeid();
            QueryWrapper wrapper2 = new QueryWrapper();
            wrapper2.eq("id",committeeid);
            List<Committee> committees = committeeMapper.selectList(wrapper2);
            if(committees.get(0).getAfterDefence()==1){
                committeeid0 = committeeid;
            }
        }
        return Result.success(committeeid0);
    }

}
