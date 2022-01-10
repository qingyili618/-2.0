package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.StudentVO;
import com.example.bootthesisreview.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("SELECT\n" +
            "\tteacher.department, \n" +
            "\tteacher.`name`\n" +
            "FROM\n" +
            "\troleallocation,\n" +
            "\tteacher\n" +
            "WHERE\n" +
            "\troleallocation.employee_id = teacher.employee_id AND\n" +
            "\troleallocation.role = 5")
    List<Teacher> departmentHead();
}
