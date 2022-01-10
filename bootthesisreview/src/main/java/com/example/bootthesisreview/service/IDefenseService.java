package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Defense;


public interface IDefenseService extends IService<Defense>{
    public Defense selectOne(LambdaQueryWrapper wapper);
    public Page<Defense> selectPage(Page defensePage, LambdaQueryWrapper wrapper);
    public void insert(Defense defense);

}


