package com.example.bootthesisreview.entity;

import lombok.Data;

@Data
public class StudentVO {
    private String name;
    private String thesisid;
    private String title;
    private String department;
    private String batch;
    private Integer grade1;
    private Integer grade2;
    private Integer grade3;
    private Integer grade4;
    private Integer grade5;
    private Integer total;
    private String comments;
}
