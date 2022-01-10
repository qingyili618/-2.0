package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Grade;

public interface IGradeService extends IService<Grade> {
    public void insert(Grade grade);
    public int deletedById(Integer id);
}
