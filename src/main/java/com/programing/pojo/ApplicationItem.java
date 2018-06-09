package com.programing.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationItem {
    private Integer id;

    private Long applicationNo;

    private Integer competitionId;

    private String competitionName;

    private String competitionImage;

    private BigDecimal currentUnitPrice;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private Integer sponsorId;


}