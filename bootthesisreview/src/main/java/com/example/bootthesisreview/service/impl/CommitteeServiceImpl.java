package com.example.bootthesisreview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Committee;
import com.example.bootthesisreview.mapper.CommitteeMapper;
import com.example.bootthesisreview.service.ICommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ICommitteeService")
public class CommitteeServiceImpl extends ServiceImpl<CommitteeMapper,Committee> implements ICommitteeService{
    @Autowired
    private CommitteeMapper committeeMapper;
    //@Autowired
    //private LambdaQueryWrapper wrapper;
    @Override
    public Committee selectOne(LambdaQueryWrapper wrapper){
        Committee res=committeeMapper.selectOne(wrapper);
        return res;
    }
    //Page<User> userPage=userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    @Override
    public Page<Committee> selectPage(Page committeePage,LambdaQueryWrapper wrapper){
        return committeeMapper.selectPage(committeePage,wrapper);
    }

    @Override
    public void insert(Committee committee){
        committeeMapper.insert(committee);
    }

    @Override
    public boolean updateById(Committee committee){
        committeeMapper.updateById(committee);
        return true;
    }

    @Override
    public int deletedById(Long id) {
        committeeMapper.deleteById(id);
        return 0;
    }
}
