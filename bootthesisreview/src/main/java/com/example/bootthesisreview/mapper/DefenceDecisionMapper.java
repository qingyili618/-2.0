package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.DefenceDecision;
import com.example.bootthesisreview.entity.DefenceDecisionVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DefenceDecisionMapper extends BaseMapper<DefenceDecision> {
    @Select("SELECT thesis.id,thesis.thesisid,thesis.title,thesis.department,thesis.`status` ,thesis.url,thesis.batch from thesis where thesis.`status`=8;")
    List<DefenceDecisionVO> defencedecisionList();
    @Select("SELECT thesis.id,thesis.thesisid,thesis.title,thesis.department,thesis.`status` ,thesis.url,thesis.batch from thesis where thesis.`status`=8 and thesis.batch=#{batch};")
    List<DefenceDecisionVO> defencedecisionListForBatch(String batch);
}