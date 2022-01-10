package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Defense;
import com.example.bootthesisreview.entity.defCommitteeVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DefenseMapper extends BaseMapper<Defense> {
    @Select("SELECT\n" +
            "\tthesis.thesisid, \n" +
            "\tthesis.id, \n" +
            "\tthesis.batch, \n" +
            "\tthesis.memo, \n" +
            "\tthesis.department, \n" +
            "\tthesis.supervisor, \n" +
            "\tthesis.author, \n" +
            "\tthesis.title, \n" +
            "\tdefencedecision.grade, \n" +
            "\tdefencedecision.noes, \n" +
            "\tdefencedecision.yesses, \n" +
            "\tdefencedecision.decision\n" +
            "FROM\n" +
            "\tthesis,\n" +
            "\tdefencedecision\n" +
            "WHERE\n" +
            "\tthesis.thesisid = defencedecision.thesisid\n" +
            "AND\n" +
            "\tthesis.status=10\n" +
            "and thesis.batch=#{batch}")
    List<defCommitteeVO> defCommitteeListForBatch(String batch);
    @Select("SELECT\n" +
            "\tthesis.thesisid, \n" +
            "\tthesis.id, \n" +
            "\tthesis.batch, \n" +
            "\tthesis.memo, \n" +
            "\tthesis.department, \n" +
            "\tthesis.supervisor, \n" +
            "\tthesis.author, \n" +
            "\tthesis.title, \n" +
            "\tdefencedecision.grade, \n" +
            "\tdefencedecision.noes, \n" +
            "\tdefencedecision.yesses, \n" +
            "\tdefencedecision.decision\n" +
            "FROM\n" +
            "\tthesis,\n" +
            "\tdefencedecision\n" +
            "WHERE\n" +
            "\tthesis.thesisid = defencedecision.thesisid\n" +
            "AND\n" +
            "\tthesis.status=10")
    List<defCommitteeVO> defCommitteeList();
}

