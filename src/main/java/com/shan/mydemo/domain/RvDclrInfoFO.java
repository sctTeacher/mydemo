package com.shan.mydemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: 员工一览表数据传输类
 * @author: Liu Huan Ming
 * @date: 2021-01-19 11:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RvDclrInfoFO {

    private Long dclrId;
    // 年度
    private Integer year;
    //系列
    private String series;
    // 职级
    private String projLevel;
    // 序号
    private Integer number;
    // 用户id
    private Long userId;
    // 姓名
    private String userName;
    // 性别
    private String sex;
    // 出生年月
    private String birthDay;
    // 参加工作时间
    private String joinWorkDate;


    // 现职称
    private String nowJosTitle;
    // 获得时间
    private String getDate;
    // 现从事专业
    private String nowMajor;
    // 现专业工作年限
    private Integer nowWorkLength;
    // 年度考核结果
    private String yearReviewResult;
    // 外语分数，example:英语 83
    private String foreignLanguageSocre;
    // 计算机分数，exmaple:集团公司 80
    private String computerScore;
    // 论文成绩
    private Double paperScore;
    // 答辩成绩
    private Double replyScore;
    // 工作简历
    private String workExperience;
    // 主要工作成绩
    private String mainWorkResult;
    // 参评论文
    private String reviewPaper;

    // 综合考核-评审工作-基层单位意见
    private RvDclrBasicInfoFO basic;


    // 备注
    private String remarks;


    // 获得日期
    public String getGetDate() {
        return StringUtils.isNotBlank(getDate)
            ? TasDateTimeUtils.formatDateStr(getDate)
            : getDate;
    }


}
