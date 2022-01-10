package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.entity.ThesisVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ThesisMapper extends BaseMapper<Thesis> {
    @Select("select * " +
            "from thesis " +
            "where thesisid = #{thesisid} and batch=#{batch}")
    List<Thesis> getthesis1(String thesisid, String batch);

    @Select("select * " +
            "from thesis " +
            "where thesisid = #{thesisid}")
    List<Thesis> getthesis2(String thesisid);
    @Select({"select thesis.thesisid,thesis.title,thesis.author,thesis.supervisor,thesis.department,thesis.submit_Time,thesis.memo,thesis.url,thesis.status, \n" +
            "(case when thesis.status=1 then \"未分配\"\n" +
            "when thesis.status=2 then \"已分配\" \n" +
            "when thesis.status=3 then \"已评审\"\n" +
            "when thesis.status=4 then \"评审已分配上会\"\n" +
            "when thesis.status=5 then \"会评通过\"\n" +
            "when thesis.status=6 then \"会评不通过\"\n" +
            "when thesis.status=7 then \"已分配给系主任\"\n" +
            "when thesis.status=8 then \"已安排答辩\"\n" +
            "when thesis.status=9 then \"答辩通过\"\n" +
            "when thesis.status=10 then \"答辩上会\"\n" +
            "when thesis.status=11 then \"答辩已分配上会\"\n" +
            "when thesis.status=12 then \"答辩上会未通过\"\n" +
            "end) as stringstatus from thesis\n" +
            "where (thesis.thesisid like concat(\"%\",#{search},\"%\")) or\n" +
            "(thesis.title like concat(\"%\",#{search},\"%\")) or\n" +
            "(thesis.author like concat(\"%\",#{search},\"%\")) or\n" +
            "(thesis.supervisor like concat(\"%\",#{search},\"%\")) or\n" +
            "(thesis.department like concat(\"%\",#{search},\"%\"))"})
    List<ThesisVO> getallthesis(Page page,String search);
}
