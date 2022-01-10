package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.ThesisMapper;
import com.example.bootthesisreview.mapper.UserMapper;
import com.example.bootthesisreview.service.IThesisService;
import com.example.bootthesisreview.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IThesisService")
public class ThesisServiceImpl extends ServiceImpl<ThesisMapper, Thesis> implements IThesisService {
    @Autowired
    private ThesisMapper thesisMapper;

    public Page<Thesis> selectPage(Page thesisPage, LambdaQueryWrapper wrapper){
        return thesisMapper.selectPage(thesisPage,wrapper);
    }
}
