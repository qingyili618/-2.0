package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bootthesisreview.entity.Back;
import com.example.bootthesisreview.entity.Grade;

public interface IBackService extends IService<Back> {
    public void insert(Back back);
}
