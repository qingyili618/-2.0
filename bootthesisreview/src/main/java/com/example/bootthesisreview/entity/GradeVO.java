package com.example.bootthesisreview.entity;
import lombok.Data;

@Data
public class GradeVO {
    private String url;
    private Integer total;
    private Integer qualified;
    private String title;
    private String comments;
    private Integer grade1;
    private Integer grade2;
    private Integer grade3;
    private Integer grade4;
    private Integer grade5;
}
