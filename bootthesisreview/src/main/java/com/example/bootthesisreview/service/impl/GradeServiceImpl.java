package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.mapper.GradeMapper;
import com.example.bootthesisreview.mapper.ThesisMapper;
import com.example.bootthesisreview.mapper.UserMapper;
import com.example.bootthesisreview.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("IGradeService")
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements IGradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public void insert(Grade grade){
        gradeMapper.insert(grade);
    }

    @Override
    public int deletedById(Integer id) {
        gradeMapper.deleteById(id);
        return 0;
    }

}
