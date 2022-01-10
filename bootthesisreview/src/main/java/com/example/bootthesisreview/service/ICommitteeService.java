package com.example.bootthesisreview.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.entity.Committee;

public interface ICommitteeService {
    public Committee selectOne(LambdaQueryWrapper wapper);
    public Page<Committee> selectPage(Page committeePage, LambdaQueryWrapper wrapper);
    public void insert(Committee committee);
    public boolean updateById(Committee committee);
    public int deletedById(Long id);
}
