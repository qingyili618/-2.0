package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Batch;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface BatchMapper extends BaseMapper<Batch> {
    @Select("SELECT\n" +
            "\tDISTINCT(batch.academic_year)\n" +
            "FROM\n" +
            "\tbatch")
    List<Batch> distinctYearList();

    @Select("SELECT\n" +
            "\tbatch.id batch\n" +
            "FROM\n" +
            "\tbatch\n" +
            "WHERE\n" +
            "\tbatch.academic_year = #{year} AND\n" +
            "\tbatch.semester = #{semester} AND\n" +
            "\tbatch.type = #{type}")
    String getBatch(String year,String semester,String type);
}
