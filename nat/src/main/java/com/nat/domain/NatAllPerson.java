package com.nat.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：lbw
 * @date ：Created in 2022/3/16 20:13
 * @description：TODO
 */
@Data
@Entity
@Table(name = "nat_all_person")
public class NatAllPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "simple_time")
    private String simpleTime;

    @Column(name = "result")
    private Integer result;


    @Column(name = "discover_dept")
    private String discoverDept;

    @Column(name = "phone")
    private String phone;

    @Column(name = "type")
    private String type;

    @Column(name = "remark")
    private String remark;

    @Column(name = "code")
    private String code;
    @Column(name = "packge")
    private String packge;
    @Column(name = "place")
    private String place;

}
