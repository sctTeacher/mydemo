package com.shan.mydemo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 评审一览表-基层单位意见
 * @author: Liu Huan Ming
 * @date: 2021-01-21 15:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评审一览表-基层单位意见")
public class RvDclrBasicInfoFO {

    @ApiModelProperty("工作成绩-EXCELLENT、GOOD、PASS")
    private String workPerformance;
    @ApiModelProperty("业务能力-EXCELLENT、GOOD、PASS")
    private String businessAbility;
    @ApiModelProperty("职业道德-EXCELLENT、GOOD、PASS")
    private String professionalEthics;
    @ApiModelProperty("工作态度-EXCELLENT、GOOD、PASS")
    private String workAttitude;

    @ApiModelProperty("工作成绩-优秀")
    private String we;
    @ApiModelProperty("业务能力-优秀")
    private String be;
    @ApiModelProperty("职业道德-优秀")
    private String pe;
    @ApiModelProperty("工作态度-优秀")
    private String wte;

    @ApiModelProperty("工作成绩-良好")
    private String wg;
    @ApiModelProperty("业务能力-良好")
    private String bg;
    @ApiModelProperty("职业道德-良好")
    private String pg;
    @ApiModelProperty("工作态度-良好")
    private String wtg;

    @ApiModelProperty("工作成绩-及格")
    private String wp;
    @ApiModelProperty("业务能力-及格")
    private String bp;
    @ApiModelProperty("职业道德-及格")
    private String pp;
    @ApiModelProperty("工作态度-及格")
    private String wtp;


}
