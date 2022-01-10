package com.example.bootthesisreview.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Batch;
import com.example.bootthesisreview.entity.Teacher;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.BatchMapper;
import com.example.bootthesisreview.service.IBatchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/batch")
public class BatchController {
    @Resource
    BatchMapper batchMapper;
    @Resource
    private IBatchService batchService;
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Batch> wrapper= Wrappers.<Batch>lambdaQuery();
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(Batch::getAcademicYear, search).or()
                    .like(Batch::getType, search).or()
                    .like(Batch::getStartTime, search).or()
                    .like(Batch::getEndTime, search).or();
        }
        Page<Batch> batchPage=batchService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(batchPage);

    }
    @PostMapping
    public Result<?> save(@RequestBody Batch batch) {
        batchMapper.insert(batch);
        return Result.success(batch.getId());

    }
    @PutMapping
    public Result<?> update(@RequestBody Batch batch) {
        batchMapper.updateById(batch);
        return Result.success(batch.getId());

    }
    @PutMapping("/distinctYear")
    public Result<?> present() {
        return Result.success(batchMapper.distinctYearList());

    }
    @GetMapping("/getbatchID")
    public Result<?> present(@RequestParam(defaultValue = "2019") String year,
                             @RequestParam(defaultValue = "") String semester,
                             @RequestParam(defaultValue = "") String type){
        return Result.success(batchMapper.getBatch(year,semester,type));
    }
    @GetMapping("/getbatch")
    public Result<?>present(@RequestParam Integer batchid){
        return Result.success(batchMapper.selectById(batchid));
    }
}
