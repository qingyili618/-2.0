package com.example.bootthesisreview;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.entity.User;
import com.example.bootthesisreview.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BootthesisreviewApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private InformationMapper mapper1;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private RoleAllocationMapper mapper;
    @Autowired
    private ThesisMapper thesisMapper;
    @Autowired
    CommitteeMapper committeeMapper;
    @Autowired
    CommitteeVoteMapper committeeVoteMapper;


    @Test
    void testSelect() {
        System.out.println((mapper1.infocount("138134")));
    }
//    @Test
//    void testSelect() {
//    }

    @Test
    void productVOTest(){
        mapper.roleList("138134").forEach(System.out::println);

    }
    @Test
    void testGetThesis() {
        //LambdaQueryWrapper<Thesis> wrapper=Wrappers.<Thesis>lambdaQuery();
        //wrapper.eq(Thesis::getStatus,'2');
        //thesisMapper.selectList(wrapper).forEach(System.out::println);
//        committeeMapper.gradedThesisList('2').forEach(System.out::println);
        committeeVoteMapper.committeevoteList().forEach(System.out::println);
    }
    @Test
    void testLogin(){
//        User user=new User();
//        user.setUsername("203056");
//        user.setPassword("12345");
//        User res=mapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
//        if(res==null) {
//            //return Result.error("-1","用户名或密码错误");
//            System.out.println("用户名或密码错误！");
//        }
//        else {
//            System.out.println("登录成功");
//        }
//        //return Result.success();
    }
    @Test
    void test(){
        System.out.println(committeeMapper.gradedThesisListForBatch("2","75","85","70","75","85","3","3"));
    }
    @Test
    void test1(){
        LambdaQueryWrapper<Grade> wrapper1= Wrappers.<Grade>lambdaQuery();
        wrapper1.eq(Grade::getReviewer,"138134");  // 需要传一个专家编号进来
        List<Grade> grades = gradeMapper.selectList(wrapper1);
        List<String> Thesisids = new ArrayList<>();
        for(int i=0;i < grades.size();i++){
            Grade grade = grades.get(i);
            Thesisids.add(grade.getThesisid());
        }
        // 根据thesisid找到所有的thesis
        List<Thesis> thesises = new ArrayList<>();
        for(int i=0;i<Thesisids.size();i++){
            String thesisid = Thesisids.get(i);
            LambdaQueryWrapper<Thesis> wrapper= Wrappers.<Thesis>lambdaQuery();
            if(StrUtil.isNotBlank("1") && (StrUtil.isNotBlank("3"))) {
                wrapper.eq(Thesis::getStatus,"1").eq(Thesis::getBatch,"3").eq(Thesis::getThesisid,thesisid);
            }else if(StrUtil.isNotBlank("1")) {
                wrapper.eq(Thesis::getStatus,"1").eq(Thesis::getThesisid,thesisid);
            }
            System.out.println();
            thesises.add(thesises.size(),thesisMapper.selectList(wrapper).get(0));
        }
    }
    @Test
    void test2(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("thesisid",1);
        List<Thesis> thesis = thesisMapper.selectByMap(map);
        System.out.println(thesis);
    }
    @Test
    void test3(){
        QueryWrapper<Thesis> wrapper = new QueryWrapper<Thesis>();
        wrapper.eq("thesisid",'1');
        thesisMapper.selectList(wrapper).forEach(System.out::println);;
    }
    @Test
    void test4(){
        LambdaQueryWrapper<Thesis> wrapper1= Wrappers.<Thesis>lambdaQuery();
        List<Thesis> thesisids = gradeMapper.getungraded("138056");
        System.out.println(thesisids);
    }
    @Test
    void test6(){
        LambdaQueryWrapper<Thesis> wrapper1= Wrappers.<Thesis>lambdaQuery();
        List<Thesis> thesis = thesisMapper.getthesis2("2");
        System.out.println(thesis);
    }
    @Test
    void test5(){
        // 找到属于这个专家的所有论文，得到这些论文的id
        List<Thesis> Thesisids = gradeMapper.getungraded("138056");
        // 根据thesisid找到所有的thesis
        List<Thesis> thesises = new ArrayList<>();
        for(int i=0;i<Thesisids.size();i++){
            String thesisid = Thesisids.get(i).getThesisid();
            List<Thesis> thesis = new ArrayList<>();
            thesis = thesisMapper.getthesis2(thesisid);
            thesises.add(thesis.get(0));
        }
        System.out.println(thesises);
    }
}
