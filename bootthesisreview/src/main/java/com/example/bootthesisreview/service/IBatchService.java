package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Batch;
import com.example.bootthesisreview.entity.Committee;

public interface IBatchService {
    public Batch selectOne(LambdaQueryWrapper wapper);
    public Page<Batch> selectPage(Page batchPage, LambdaQueryWrapper wrapper);
    public void insert(Batch batch);
    public boolean updateById(Batch batch);
    public int deletedById(Long id);
}