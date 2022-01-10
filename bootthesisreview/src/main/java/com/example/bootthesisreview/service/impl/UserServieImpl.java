package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.UserMapper;
import com.example.bootthesisreview.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IUserService")
public class UserServieImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    //@Autowired
    //private LambdaQueryWrapper wrapper;
    @Override
    public User selectOne(LambdaQueryWrapper wrapper){
        User res=userMapper.selectOne(wrapper);
        return res;
    }
    //Page<User> userPage=userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    @Override
    public Page<User> selectPage(Page userPage,LambdaQueryWrapper wrapper){
        return userMapper.selectPage(userPage,wrapper);
    }

    @Override
    public void insert(User user){
        userMapper.insert(user);
    }

    @Override
    public int deletedById(Long id) {
        userMapper.deleteById(id);
        return 0;
    }
    @Override
    public boolean updateById(User user){
        userMapper.updateById(user);
        return true;
    }


}
