package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Defense;
import com.example.bootthesisreview.mapper.DefenseMapper;
import com.example.bootthesisreview.service.IDefenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IDefenseService")
public class DefenseServiceImpl extends ServiceImpl<DefenseMapper, Defense> implements IDefenseService {
    @Autowired
    private DefenseMapper defenseMapper;

    public Page<Defense> selectPage(Page defensePage, LambdaQueryWrapper wrapper){
        return defenseMapper.selectPage(defensePage,wrapper);
    }

    @Override
    public void insert(Defense defense) { defenseMapper.insert(defense);}

    @Override
    public Defense selectOne(LambdaQueryWrapper wrapper){
        Defense res=defenseMapper.selectOne(wrapper);
        return res;
    }
}
