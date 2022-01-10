package com.example.bootthesisreview.entity;
import lombok.Data;

@Data
public class defCommitteeVO {
    private Integer id;
    private Integer thesisid;
    private String title;
    private String author;
    private String supervisor;
    private String department;
    private String memo;
    private Integer grade;
    private Integer noes;
    private Integer yesses;
    private String decision;
    private String batch;
}
