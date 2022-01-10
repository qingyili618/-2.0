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
        // 找到属于这个专家的未评审的所有论文，得到这些论文的id
        List<Thesis> Thesisids = gradeMapper.getungraded(reviewer);
      // 根据thesisid找到所有的thesis
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
        Page<Thesis> thesisPage = new Page<>(pageNum,pageSize); // 设置好page容器
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
                              @RequestParam(defaultValue = "管理科学与信息管理") String department
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
        Page<ThesisVO> thesisPage = new Page<>(pageNum,pageSize); // 设置好page容器
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
        // 找到属于这个专家的未评审的所有论文，得到这些论文的id
        List<Thesis> Thesisids = gradeMapper.gethavegraded(reviewer);
        // 根据thesisid找到所有的thesis
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
        Page<Thesis> thesisPage = new Page<>(pageNum,pageSize); // 设置好page容器
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

        // 判断文件是否为空
        if(file.isEmpty()){
            return Result.success("empty");
        }

        //创建输入输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //指定上传和读取的位置为 c:/upload/
        String path = "c:/upload/";
        //获取上传时的文件名
        String fileName = file.getOriginalFilename();

        try {
            //获取文件的输入流
            inputStream = file.getInputStream();
            //注意是路径+文件名
            File targetFile = new File(path + fileName);
            //如果之前的 String path = "d:/upload/" 没有在最后加 / ，那就要在 path 后面 + "/"

            //判断文件父目录是否存在
            if(!targetFile.getParentFile().exists()){
                //不存在就创建一个
                targetFile.getParentFile().mkdir();
            }

            //获取文件的输出流
            outputStream = new FileOutputStream(targetFile);
            //最后使用资源访问器FileCopyUtils的copy方法拷贝文件
            FileCopyUtils.copy(inputStream, outputStream);
            /*参数是通过源码
                public static int copy(InputStream in, OutputStream out) throws IOException {
                    ......
                }
           而得知的*/

            //告诉页面上传成功了
        } catch (IOException e) {
            e.printStackTrace();
            //出现异常，则告诉页面失败
        } finally {
            //无论成功与否，都有关闭输入输出流
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
        // 接着将数据插入到数据库里面
        // 取得实体类
        List<Object> result2 = excelRead.ReadExcelByPOJO(path + fileName, 2, -1, ThesisExcel.class);
        Integer num = 0;
        Integer number =(int)(result2.size()/2)+1;
        // 查前五篇，把相应的情况告知工作人员，这个具体的更多的功能可以之后改善
        for (int i = 0; i < number; i++) {
            Thesis thesis1 = (Thesis) result2.get(i);
            String thesisid = thesis1.getThesisid();
            if(thesisid == null || thesis1.getAuthor()==null){
                Result result = new Result();
                result.setMsg("导入的数据不符合格式或者数据存在缺失，请使用系统提供的模板并且检查数据情况，重新上传！");
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
        result.setMsg("请检查导入的excel最新的，检测到系统中已经存在其中包含的论文！");
        return result;}
}
