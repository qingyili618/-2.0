package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.User;

public interface IUserService extends IService<User> {
    public User selectOne(LambdaQueryWrapper wapper);
    public Page<User> selectPage(Page userPage, LambdaQueryWrapper wrapper);
    public void insert(User user);
    public boolean updateById(User user);
    public int deletedById(Long id);
}

