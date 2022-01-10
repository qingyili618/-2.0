package com.example.bootthesisreview.Excel;
import com.example.bootthesisreview.entity.Thesis;
import lombok.Data;

@Data
public class ThesisExcel extends Thesis{
    @excelRescoure(value = "序号")
    private String thesisid;
    @excelRescoure(value = "论文题目")
    private String title;
    @excelRescoure(value = "姓名")
    private String author;
    @excelRescoure(value = "导师姓名")
    private String supervisor;
    @excelRescoure(value = "导师所属系所")
    private String department;
    @excelRescoure(value = "研究方向")
    private String memo;
    @excelRescoure(value = "学生学号")
    private String studentid;
    public ThesisExcel() {}
}
