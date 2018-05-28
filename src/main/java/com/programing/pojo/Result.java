package com.programing.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer id;

    private Integer sponsorId;

    private Integer competitionId;

    private String title;

    private String detail;

    private Date createTime;

    private Date updateTime;
}
