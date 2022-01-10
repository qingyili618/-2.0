package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.Information;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.InformationMapper;
import com.example.bootthesisreview.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IInformationService")
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements IInformationService {
    @Autowired
    private InformationMapper infoMapper;

    public Page<Information> selectPage(Page thesisPage, LambdaQueryWrapper wrapper){
        return infoMapper.selectPage(thesisPage,wrapper);
    }
    @Override
    public boolean updateById(Information information){
        infoMapper.updateById(information);
        return true;
    }
    @Override
    public void insert(Information information){
        infoMapper.insert(information);
    }
}
