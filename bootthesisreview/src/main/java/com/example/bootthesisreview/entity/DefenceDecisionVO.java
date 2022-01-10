package com.example.bootthesisreview.entity;
import lombok.Data;

@Data
public class DefenceDecisionVO {
    private Integer id;
    private Integer thesisid;
    private String title;
    private String department;
    private String url;
    private Integer status;
    private String batch;
}
