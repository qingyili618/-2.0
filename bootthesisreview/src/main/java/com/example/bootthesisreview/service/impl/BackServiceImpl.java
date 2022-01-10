package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Back;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.mapper.BackMapper;
import com.example.bootthesisreview.mapper.GradeMapper;
import com.example.bootthesisreview.service.IBackService;
import com.example.bootthesisreview.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IBackService")
public class BackServiceImpl extends ServiceImpl<BackMapper, Back> implements IBackService {

    @Autowired
    private BackMapper backMapper;

    @Override
    public void insert(Back back){
        backMapper.insert(back);
    }


}
