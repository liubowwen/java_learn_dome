package com.lbw.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：lbw
 * @date ：Created in 2022/3/6 16:14
 * @description：TODO
 */
@Data
public class Student implements Serializable {
    private Long id;
    private String name;
    private String age;
    private String idCard;
    private String address;
}
