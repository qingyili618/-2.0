package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Student;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.GradeMapper;
import com.example.bootthesisreview.mapper.StudentMapper;
import com.example.bootthesisreview.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IStudentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements IStudentService{
//    @Autowired
//    private StudentMapper studentMapper;


}

