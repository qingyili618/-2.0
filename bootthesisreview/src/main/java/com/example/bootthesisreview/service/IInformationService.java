package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Information;
import com.example.bootthesisreview.entity.User;


public interface IInformationService extends IService<Information> {
    public Page<Information> selectPage(Page infoPage, LambdaQueryWrapper wrapper);
    public boolean updateById(Information information);
    public void insert(Information information);
}
