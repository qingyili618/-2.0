package com.example.bootthesisreview.entity;
import lombok.Data;

import java.util.Date;

@Data
public class gradedThesisVO {
    private Integer id;
    private Integer thesisid;
    private String title;
    private String author;
    private String supervisor;
    private String department;
    private String memo;
    private Date submitTime;
    private Integer avgScore;
    private Integer minScore;
    private Integer lowerNum1;
    private Integer lowerNum2;
    private String status;
    private String batch;
}
