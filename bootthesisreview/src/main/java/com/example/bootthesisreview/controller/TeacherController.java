package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Teacher;
import com.example.bootthesisreview.mapper.TeacherMapper;
import com.example.bootthesisreview.service.ITeacherService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    private ITeacherService teacherService;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
//        LambdaQueryWrapper<Teacher> wrapper= Wrappers.<Thesis>lambdaQuery();
//        if(StrUtil.isNotBlank(search)) {
//            wrapper.like(User::getNickName,search);
//        }
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(Teacher::getName,search).or()
                    .like(Teacher::getDepartment,search).or()
                    .like(Teacher::getDirection,search).or()
                    .like(Teacher::getDiscipline,search).or()
                    .like(Teacher::getField,search).or();
        }

        //Page<Thesis> thesisPage=thesisMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<Teacher> teacherPage=teacherService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(teacherPage);
    }
    @GetMapping("/getDepartment")
    public Result<?> getDepartment(@RequestParam String teacherid){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("employee_id",teacherid);
        List<Teacher> teacher = teacherMapper.selectByMap(map);
        return Result.success(teacher);
    }
    @GetMapping("/getemployeeid")
    public Result<?> getemployeeid(@RequestParam String name){
        LambdaQueryWrapper<Teacher> wrapper= Wrappers.<Teacher>lambdaQuery();
        wrapper.eq(Teacher::getName,name);
        return Result.success(teacherMapper.selectList(wrapper));
    }
    @PostMapping("/departmentHead")
    public  Result<?> departmentHead(){
        return Result.success(teacherMapper.departmentHead());
    }
    @GetMapping("/staffinfo")
    public Result<?> getstaffinfo(@RequestParam String username){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("employee_id",username);
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        Teacher teacher = teachers.get(0);
        return Result.success(teacher);
    }

    @PostMapping("/updateinfo")
    public Result<?> updatestaffinfo(@RequestBody Teacher teacher){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("employee_id",teacher.getEmployeeId());
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        Teacher teacher0 = teachers.get(0);
        Integer id = teacher0.getId();
        teacher.setId(id);
        teacherMapper.updateById(teacher);
        return Result.success();}

}
