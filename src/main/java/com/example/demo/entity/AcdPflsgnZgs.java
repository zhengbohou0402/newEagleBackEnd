package com.example.demo.entity;

import lombok.Data;

/**
 * 事故年赔付率-支公司 实体
 * 对应表: acd_pflsgn_zgs
 */
@Data
public class AcdPflsgnZgs {

    private Long id;

    private String tjDate;          // 统计日期

    private String comcodeSgs;      // 市公司编码

    private String comnameSgs;      // 市公司名称

    private String comcode;             // 支公司编码

    private String comname;             // 支公司名称

    private Long sumpaidYh;          // 已核赔款
    private Long sumpaidWh;          // 未核赔款
    private Long sumpaidHj;          // 赔款合计
    private Long yzbf19;          // 已赚保费
    private String sgndPfl;          // 赔付率
    private String pflTb;          // 赔付率同比
    private Long yjAjl;          // 已决案件量
    private Long wjAjl;          // 未决案件
    private Long ajl;          // 已报案件量
    private Long yzbd;          // 已赚保单
    private String clv;          // 出险率
    private String clvTb;          // 出险率同比
    private Long yhaj;          // 已核案均
    private Long whaj;          // 未决案均
    private Long bgaj;          // 已报告案均
    private String bgajTb;          // 报告案均同比
    private Long djyz;          // 单均已赚
    private String djyzTb;          // 单均已赚同比
    private Long yjCs;          // 车损已决
    private Long yjRs;          // 人伤已决
    private Long yjWs;          // 物损已决
    private Long csAjl;          // 车损已决案件量
    private Long rsAjl;          // 人伤已决案件量
    private Long wsAjl;          // 物损已决案件量
    private Long csYjaj;          // 车损已决案均
    private Long rsYjaj;          // 人伤已决案均
    private Long wsYjaj;          // 物损已决案均
    private Long sumpaidYhQn;          // 已核赔款(去年)
    private Long sumpaidWhQn;          // 未核赔款(去年)
    private Long sumpaidHjQn;          // 赔款合计(去年)
    private Long yzbf19Qn;          // 已赚保费(去年)
    private String sgndPflQn;          // 赔付率(去年)
    private Long yjAjlQn;          // 已决案件量(去年)
    private Long wjAjlQn;          // 未决案件(去年)
    private Long ajlQn;          // 已报案件量(去年)
    private Long yzbdQn;          // 已赚保单(去年)
    private String clvQn;          // 出险率(去年)
    private Long yhajQn;          // 已核案均(去年)
    private Long whajQn;          // 未决案均(去年)
    private Long bgajQn;          // 已报告案均(去年)
    private Long djyzQn;          // 单均已赚(去年)
    private Long yjCsQn;          // 车损已决(去年)
    private Long yjRsQn;          // 人伤已决(去年)
    private Long yjWsQn;          // 物损已决(去年)
    private Long csAjlQn;          // 车损已决案件量(去年)
    private Long rsAjlQn;          // 人伤已决案件量(去年)
    private Long wsAjlQn;          // 物损已决案件量(去年)
    private Long csYjajQn;          // 车损已决案均(去年)
    private Long rsYjajQn;          // 人伤已决案均(去年)
    private Long wsYjajQn;          // 物损已决案均(去年)

    private String maxTjTime;       // 最大统计时间（用于前端标题展示）

}