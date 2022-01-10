package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.Thesis;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GradeMapper extends BaseMapper<Grade> {
    @Select("SELECT\n" +
            "\tgrade.id\n" +
            "FROM\n" +
            "\tgrade\n" +
            "WHERE\n" +
            "\tgrade.thesisid = #{thesisid} AND grade.reviewer = #{reviewer};")
    Integer fetchID(String thesisid,String reviewer);

    @Select("SELECT grade.thesisid FROM grade WHERE grade.grade1 is null and grade.reviewer = #{reviewer};")
    List<Thesis> getungraded(String reviewer);
    @Select("SELECT grade.thesisid FROM grade WHERE grade.grade1 is not null and grade.reviewer = #{reviewer};")
    List<Thesis> gethavegraded(String reviewer);
}

