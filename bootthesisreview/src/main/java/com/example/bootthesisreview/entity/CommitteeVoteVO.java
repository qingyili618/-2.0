package com.example.bootthesisreview.entity;

import lombok.Data;

@Data
public class CommitteeVoteVO {
    private Integer id;
    private Integer thesisid;
    private Integer committeeid;
    private String title;
    private String department;
    private String comment;
    private Integer voters;
    private Integer noes;
    private Integer equal;
    private Integer yesses;
    private String results;
    private String status;
    private String process;
}
