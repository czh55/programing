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
public class Code {

    private Integer id;

    private Integer productId;

    private Integer userId;

    private String title;

    private String src;

    private Date createTime;

    private Date updateTime;
}
