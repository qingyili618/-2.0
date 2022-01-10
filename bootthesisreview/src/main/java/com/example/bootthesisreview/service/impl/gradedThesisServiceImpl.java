package com.example.bootthesisreview.service.impl;

import com.example.bootthesisreview.mapper.CommitteeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IgradeThesisService")
public class gradedThesisServiceImpl {
    @Autowired
    private CommitteeMapper committeeMapper;
//    @Override
//    public Page<gradedThesisVO> selectPage(Page gTPage,LambdaQueryWrapper wrapper){
//        return committeeMapper.selectPage(gTPage,wrapper);
//    }

}
