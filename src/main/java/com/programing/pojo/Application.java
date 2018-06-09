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
public class Application {
    private Integer id;

    private Long applicationNo;

    private Integer userId;

    private Integer sponsorId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer status;

    private Date paymentTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private Date updateTime;


}