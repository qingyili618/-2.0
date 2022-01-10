package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.CommitteeVoteVO;
import com.example.bootthesisreview.entity.Student;
import com.example.bootthesisreview.entity.StudentVO;
import com.example.bootthesisreview.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {
    @Select("select s.name,t.thesisid,t.title, t.department, t.batch, " +
            "g.grade1, g.grade2,g.grade3, g.grade4, g.grade5, g.total, g.comments " +
            "from student s, thesis t,grade g " +
            "where s.student_id = t.studentid and t.thesisid = g.thesisid and s.student_id=#{id}")
    List<StudentVO> studentList(@Param("id") String id);//自己定义的方法

    @Select("select c.yesses, c.noes,c.equal, s.status " +
            "from committeevote c, thesis t, status s" +
            " where s.id=t.status and c.thesisid = #{thesisid} and c.batch =#{batch} and t.thesisid = #{thesisid} and t.batch =#{batch} "
    )
    List<CommitteeVoteVO> studentVoteList(@Param("batch") String batch, @Param("thesisid") String thesisid);

    @Select("select t.status " +
            "from thesis t" +
            " where t.thesisid = #{thesisid} and t.batch =#{batch} "
    )
    String studentstate(@Param("batch") String batch, @Param("thesisid") String thesisid);
}
