package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Committee;
import com.example.bootthesisreview.entity.GradeVO;
import com.example.bootthesisreview.entity.gradedThesisVO;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommitteeMapper extends BaseMapper<Committee>  {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")

    @Select("SELECT *,(CASE WHEN TB.lower_num1>=#{lower_num} or TB.avg_score<#{lower_avg1} THEN '不通过' \n" +
            "\t\t\t\t\tWHEN (TB.lower_num1<#{lower_num} AND TB.lower_num2>=#{lower_num}) or TB.min_score<#{lower_min} or (TB.avg_score>=#{lower_avg1} AND TB.avg_score<#{lower_avg2}) THEN '上会'\n" +
            "\t\t\t\t\tELSE '通过' END) status\n" +
            "FROM(SELECT thesis.batch,thesis.id,thesis.thesisid, thesis.title, thesis.author,thesis.submit_time, thesis.supervisor, thesis.department, \n" +
            "\tthesis.memo, AVG(grade.total) avg_score, MIN(grade.total) min_score,\n" +
            "\t(SUM(CASE WHEN grade.total<#{lower1} THEN 1 ELSE 0 END)) lower_num1,(SUM(CASE WHEN grade.total<#{lower2} THEN 1 ELSE 0 END)) lower_num2\n" +
            "FROM thesis, grade WHERE thesis.thesisid = grade.thesisid and thesis.`status`=#{thesis_status} and thesis.`batch`=#{batch}\n" +
            "GROUP BY thesis.thesisid) TB")
    List<gradedThesisVO> gradedThesisListForBatch(String lower_num, String lower_avg1, String lower_avg2, String lower_min, String lower1, String lower2, String thesis_status,String batch);
    @Select("SELECT *,(CASE WHEN TB.lower_num1>=#{lower_num} or TB.avg_score<#{lower_avg1} THEN '不通过' \n" +
            "\t\t\t\t\tWHEN (TB.lower_num1<#{lower_num} AND TB.lower_num2>=#{lower_num}) or TB.min_score<#{lower_min} or (TB.avg_score>=#{lower_avg1} AND TB.avg_score<#{lower_avg2}) THEN '上会'\n" +
            "\t\t\t\t\tELSE '通过' END) status\n" +
            "FROM(SELECT thesis.batch,thesis.id,thesis.thesisid, thesis.title, thesis.author,thesis.submit_time, thesis.supervisor, thesis.department, \n" +
            "\tthesis.memo, AVG(grade.total) avg_score, MIN(grade.total) min_score,\n" +
            "\t(SUM(CASE WHEN grade.total<#{lower1} THEN 1 ELSE 0 END)) lower_num1,(SUM(CASE WHEN grade.total<#{lower2} THEN 1 ELSE 0 END)) lower_num2\n" +
            "FROM thesis, grade WHERE thesis.thesisid = grade.thesisid and thesis.`status`=#{thesis_status}\n" +
            "GROUP BY thesis.thesisid) TB")
    List<gradedThesisVO> gradedThesisList(String lower_num, String lower_avg1, String lower_avg2, String lower_min, String lower1, String lower2, String thesis_status);

    @Select({"select t.thesisid,t.title, g.*  from grade g, thesis t where g.thesisid =t.thesisid and g.thesisid=#{thesisid}"})
//    List<GradeVO> GradeList(Integer url) ;
    List<GradeVO> committeeList(String thesisid);  //这里应该还传入一个批次
}

