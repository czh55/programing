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
public class Favourite {

    private Integer id;

    private Integer userId;

    private Integer competitionId;

    private Integer checked;

    private Date createTime;

    private Date updateTime;

}