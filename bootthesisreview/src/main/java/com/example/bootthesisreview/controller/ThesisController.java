package com.example.bootthesisreview.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bootthesisreview.Excel.ThesisExcel;
import com.example.bootthesisreview.Excel.excelRead;
import com.example.bootthesisreview.common.Result;
import com.example.bootthesisreview.entity.Grade;
import com.example.bootthesisreview.entity.Thesis;
import com.example.bootthesisreview.entity.ThesisVO;
import com.example.bootthesisreview.mapper.GradeMapper;
import com.example.bootthesisreview.mapper.GradeThesisMapper;
import com.example.bootthesisreview.mapper.ThesisMapper;
import com.example.bootthesisreview.service.IThesisService;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/thesis")
public class ThesisController {

    @Resource
    ThesisMapper thesisMapper;
    @Resource
    GradeMapper gradeMapper;
    @Resource
    private IThesisService thesisService;

//    @GetMapping("/{thesisid}")
//    public Result<?> updateByThesisid(@RequestParam String thesisid){
////                                      @RequestParam Integer status
//        UpdateWrapper<Thesis> wrapper=new UpdateWrapper<>() ;
//        wrapper.eq("thesisid",thesisid);
//        thesisService.update(wrapper);
//        return Result.success();
//    }
    @GetMapping("/getreviewednum")
    public Result<?> getreviewednum(@RequestParam String thesisid) {
//                                      @RequestParam Integer status
        QueryWrapper<Thesis> wrapper = new QueryWrapper<Thesis>();
        wrapper.eq("thesisid",thesisid);
        List<Thesis> thesis =thesisMapper.selectList(wrapper);
        return Result.success(thesis);
    }
//    @PutMapping("/thesisForBatch")
//    public Result<?> thesisForBatch(@RequestParam String thesisid){
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("thesisid",thesisid);
//        List<Thesis> thesis = thesisMapper.selectByMap(map);
//        return Result.success(thesis);
//    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String status,
                              @RequestParam(defaultValue = "") String batch) {
        LambdaQueryWrapper<Thesis> wrapper= Wrappers.<Thesis>lambdaQuery();

        if(StrUtil.isNotBlank(status) && (StrUtil.isNotBlank(batch))) {
            wrapper.eq(Thesis::getStatus,status).eq(Thesis::getBatch,batch);
        }else if(StrUtil.isNotBlank(status)) {
            wrapper.eq(Thesis::getStatus,status);
        }
        //Page<Thesis> thesisPage=thesisMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<Thesis> thesisPage=thesisService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(thesisPage);
    }


    @GetMapping("/review")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String status,
                              @RequestParam(defaultValue = "") String batch,
                              @RequestParam(defaultValue = "203057") String reviewer
    ) {
        // ???????????????????????????????????????????????????????????????????????????id
        List<Thesis> Thesisids = gradeMapper.getungraded(reviewer);
      // ??????thesisid???????????????thesis
        List<Thesis> thesises = new ArrayList<>();
        for(int i=0;i<Thesisids.size();i++){
            String thesisid = Thesisids.get(i).getThesisid();
            List<Thesis> thesis = new ArrayList<>();
            if((StrUtil.isNotBlank(batch))) {
                thesis = thesisMapper.getthesis1(thesisid,batch);
            }else{
                thesis = thesisMapper.getthesis2(thesisid);
            }
            if(thesis.size() == 0){
                continue;
            }
            thesises.add(thesis.get(0));
        }
        Page<Thesis> thesisPage = new Page<>(pageNum,pageSize); // ?????????page??????
        thesisPage.setRecords(thesises);
        return Result.success(thesisPage);
        //List<Grade> grades = gradeMapper.getungraded(reviewer);
//        List<String> Thesisids = new ArrayList<>();
//        for(int i=0;i < grades.size();i++){
//            Grade grade = grades.get(i);
//            Thesisids.add(grade.getThesisid());
//        }
//
    }
    @GetMapping("/defence")
    public Result<?> findPageForDefence(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String status,
                              @RequestParam(defaultValue = "") String batch,
                              @RequestParam(defaultValue = "???????????????????????????") String department
    ) {
        LambdaQueryWrapper<Thesis> wrapper= Wrappers.<Thesis>lambdaQuery();

        if(StrUtil.isNotBlank(status) && (StrUtil.isNotBlank(batch))) {
            wrapper.eq(Thesis::getStatus,status).eq(Thesis::getBatch,batch).eq(Thesis::getDepartmentallocation,department);
        }else if(StrUtil.isNotBlank(status)) {
            wrapper.eq(Thesis::getStatus,status).eq(Thesis::getDepartmentallocation,department);
        }
        //Page<Thesis> thesisPage=thesisMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<Thesis> thesisPage=thesisService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(thesisPage);
    }
    @PutMapping
    public Result<?> update(@RequestBody Thesis thesis) {
        //userMapper.updateById(user);
        thesisService.updateById(thesis);
        return Result.success();
    }
    @GetMapping("/getallthesis")
    public Result<?> getallthesis(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search) {
        Page<ThesisVO> thesisPage = new Page<>(pageNum,pageSize); // ?????????page??????
        List<ThesisVO> allthesis = thesisMapper.getallthesis(thesisPage,search);

        thesisPage.setRecords(allthesis);
        return Result.success(thesisPage);
    }

    @GetMapping("/havereviewed")
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String status,
                               @RequestParam(defaultValue = "") String batch,
                               @RequestParam(defaultValue = "203057") String reviewer
    ) {
        // ???????????????????????????????????????????????????????????????????????????id
        List<Thesis> Thesisids = gradeMapper.gethavegraded(reviewer);
        // ??????thesisid???????????????thesis
        List<Thesis> thesises = new ArrayList<>();
        for(int i=0;i<Thesisids.size();i++){
            String thesisid = Thesisids.get(i).getThesisid();
            List<Thesis> thesis = new ArrayList<>();
            if((StrUtil.isNotBlank(batch))) {
                thesis = thesisMapper.getthesis1(thesisid,batch);
            }else{
                thesis = thesisMapper.getthesis2(thesisid);
            }
            if(thesis.size() == 0){
                continue;
            }
            thesises.add(thesis.get(0));
        }
        Page<Thesis> thesisPage = new Page<>(pageNum,pageSize); // ?????????page??????
        thesisPage.setRecords(thesises);
        return Result.success(thesisPage);
        //List<Grade> grades = gradeMapper.getungraded(reviewer);
//        List<String> Thesisids = new ArrayList<>();
//        for(int i=0;i < grades.size();i++){
//            Grade grade = grades.get(i);
//            Thesisids.add(grade.getThesisid());
//        }
//
    }

    @PostMapping("/upload")
    public Result<?> handleUploadFile( @RequestParam("file") MultipartFile file,@RequestParam("batch") String batch
    ) throws IntrospectionException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("successfully upload");
        System.out.println(file.isEmpty());

        // ????????????????????????
        if(file.isEmpty()){
            return Result.success("empty");
        }

        //?????????????????????
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //????????????????????????????????? c:/upload/
        String path = "c:/upload/";
        //???????????????????????????
        String fileName = file.getOriginalFilename();

        try {
            //????????????????????????
            inputStream = file.getInputStream();
            //???????????????+?????????
            File targetFile = new File(path + fileName);
            //??????????????? String path = "d:/upload/" ?????????????????? / ??????????????? path ?????? + "/"

            //?????????????????????????????????
            if(!targetFile.getParentFile().exists()){
                //????????????????????????
                targetFile.getParentFile().mkdir();
            }

            //????????????????????????
            outputStream = new FileOutputStream(targetFile);
            //???????????????????????????FileCopyUtils???copy??????????????????
            FileCopyUtils.copy(inputStream, outputStream);
            /*?????????????????????
                public static int copy(InputStream in, OutputStream out) throws IOException {
                    ......
                }
           ????????????*/

            //???????????????????????????
        } catch (IOException e) {
            e.printStackTrace();
            //????????????????????????????????????
        } finally {
            //????????????????????????????????????????????????
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
        // ???????????????????????????????????????
        // ???????????????
        List<Object> result2 = excelRead.ReadExcelByPOJO(path + fileName, 2, -1, ThesisExcel.class);
        Integer num = 0;
        Integer number =(int)(result2.size()/2)+1;
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
        for (int i = 0; i < number; i++) {
            Thesis thesis1 = (Thesis) result2.get(i);
            String thesisid = thesis1.getThesisid();
            if(thesisid == null || thesis1.getAuthor()==null){
                Result result = new Result();
                result.setMsg("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                return result;
            }
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("title",thesis1.getTitle());
            wrapper.eq("author",thesis1.getAuthor());
            wrapper.eq("thesisid", thesisid);
            List<Thesis> list1 = thesisMapper.selectList(wrapper);
            if (list1.size() != 0) {
                num = num + 1;
            }
        }
        if (num == 0) {
            for (Object object : result2) {
                Thesis thesis = (Thesis) object;
                thesis.setBatch(batch);
                thesis.setStatus(1);
                thesisMapper.insert(thesis);
            }
            return Result.success();
        }
        Result result = new Result();
        result.setMsg("??????????????????excel??????????????????????????????????????????????????????????????????");
        return result;}
}
