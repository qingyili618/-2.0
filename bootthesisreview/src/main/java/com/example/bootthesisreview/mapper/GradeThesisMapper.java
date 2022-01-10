package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Grade;
import org.apache.ibatis.annotations.Select;

public interface GradeThesisMapper extends BaseMapper<Grade> {
    @Select("SELECT\n" +
            "\tgrade.id\n" +
            "FROM\n" +
            "\tgrade\n" +
            "WHERE\n" +
            "\tgrade.grade1=null AND grade.thesisid = #{thesisid} AND grade.reviewer = #{reviewer};")
    Integer fetchID(String thesisid,String reviewer);
}
