package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Teacher;

public interface ITeacherService extends IService<Teacher> {
    public Page<Teacher> selectPage(Page teacherPage, LambdaQueryWrapper wrapper);
}
