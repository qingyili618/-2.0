package com.example.bootthesisreview.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.UserMapper;
import com.example.bootthesisreview.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;
    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        // 上面的@RequestBody 是post传过来的，一般有两种方式，一是用Json字符串传的对象，二是简单对象，User是类型，usr是具体对象
        // 下面的eq查询中，前面的User::getUsername是从数据库取数据，user.getUsername是前方传过来的数据，下面代码过于难读，可以简化
        //User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        //上面代码的易读版本，用LambdaQueryWrapper可以避免硬编码查询条件值
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        //User res=userMapper.selectOne(wrapper);
        User res=userService.selectOne(wrapper);

        if(res==null) {
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();

        //Page<User> userPage=userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<User> userPage=userService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);

    }

    @PostMapping("/register")
    public Result<?> save(@RequestBody User user) {
        if(user.getPassword()==null){
            user.setPassword("123456");
        }
        //userMapper.insert(user);
        userService.insert(user);

        return Result.success();

    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        //userMapper.updateById(user);
        userService.updateById(user);
        return Result.success();

    }


}
