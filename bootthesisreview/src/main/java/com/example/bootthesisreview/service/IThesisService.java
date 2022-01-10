package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.entity.User;

public interface IThesisService extends IService<Thesis> {
    public Page<Thesis> selectPage(Page thesisPage, LambdaQueryWrapper wrapper);
}

