package com.example.bootthesisreview.controller;

import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Student;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.StudentMapper;
import com.example.bootthesisreview.mapper.TeacherMapper;
import com.example.bootthesisreview.service.IStudentService;
import com.example.bootthesisreview.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    StudentMapper studentMapper;
    @Resource
    private IStudentService studentService;
    @PostMapping
    public Result<?> search(@RequestBody User user) {
        //userMapper.updateById(user);
        return Result.success(studentMapper.studentList(user.getUsername()));

    }
    @GetMapping
    public Result<?> getvoter(@RequestParam(defaultValue = "1") String thesisid,
                              @RequestParam(defaultValue = "1") String batch
    ) {
        return Result.success(studentMapper.studentVoteList(batch,thesisid));
    }

    @GetMapping("/getstate")
    public Result<?> getstate(@RequestParam(defaultValue = "1") String thesisid,
                              @RequestParam(defaultValue = "1") String batch
    ) {
        return Result.success(studentMapper.studentstate(batch,thesisid));
    }
}
