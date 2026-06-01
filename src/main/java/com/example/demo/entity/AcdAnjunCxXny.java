package com.example.demo.entity;

import lombok.Data;

@Data
public class AcdAnjunCxXny {
    private Integer id;
    private String tjDate;
    private String comcodeSgs;
    private String comnameSgs;
    private String xnyflag;

    // 整体车险
    private Double sumpaidZt;
    private Integer ajlZt;
    private Double ajZt;
    private String ajZtTb;

    // 车损
    private Double sumpaidCs;
    private Integer ajlCs;
    private Double ajCs;
    private String ajCsTb;

    // 人伤
    private Double sumpaidRs;
    private Integer ajlRs;
    private Double ajRs;
    private String ajRsTb;

    // 交强险
    private Double sumpaidDza;
    private Integer ajlDza;
    private Double ajDza;
    private String ajDzaTb;

    // 商业险
    private Double sumpaidDaa;
    private Integer ajlDaa;
    private Double ajDaa;
    private String ajDaaTb;

    // 去年指标
    private Double sumpaidZtQn;
    private Integer ajlZtQn;
    private Double ajZtQn;

    private Double sumpaidCsQn;
    private Integer ajlCsQn;
    private Double ajCsQn;

    private Double sumpaidRsQn;
    private Integer ajlRsQn;
    private Double ajRsQn;

    private Double sumpaidDzaQn;
    private Integer ajlDzaQn;
    private Double ajDzaQn;

    private Double sumpaidDaaQn;
    private Integer ajlDaaQn;
    private Double ajDaaQn;

    // 前端展示用
    private String maxTjTime;
}
