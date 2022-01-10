package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Teacher;
import com.example.bootthesisreview.mapper.TeacherMapper;
import com.example.bootthesisreview.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ITeacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public Page<Teacher> selectPage(Page teacherPage, LambdaQueryWrapper wrapper){
        return teacherMapper.selectPage(teacherPage,wrapper);
    }
}
