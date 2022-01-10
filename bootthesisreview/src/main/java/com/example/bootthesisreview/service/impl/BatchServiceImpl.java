package com.example.bootthesisreview.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootthesisreview.entity.Batch;
import com.example.bootthesisreview.entity.Committee;
import com.example.bootthesisreview.mapper.BatchMapper;
import com.example.bootthesisreview.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IBatchService")
public class BatchServiceImpl extends ServiceImpl<BatchMapper,Batch> implements IBatchService{
    @Autowired
    private BatchMapper batchMapper;
    @Override
    public Batch selectOne(LambdaQueryWrapper wrapper){
        Batch res=batchMapper.selectOne(wrapper);
        return res;
    }
    //Page<User> userPage=userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    @Override
    public Page<Batch> selectPage(Page batchPage,LambdaQueryWrapper wrapper){
        return batchMapper.selectPage(batchPage,wrapper);
    }

    @Override
    public void insert(Batch batch){
        batchMapper.insert(batch);
    }

    @Override
    public boolean updateById(Batch batch){
        batchMapper.updateById(batch);
        return true;
    }

    @Override
    public int deletedById(Long id) {
        batchMapper.deleteById(id);
        return 0;
    }
}
