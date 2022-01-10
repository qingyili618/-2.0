package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Information;
import com.example.bootthesisreview.entity.Thesis;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InformationMapper extends BaseMapper<Information> {
    @Select("select count(*) " +
            "from information " +
            "where userid = #{userid} and status='未读'")
    Integer infocount(String userid);
}
