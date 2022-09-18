package com.lbw.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zbl
 * @description
 * @date 2019/10/17 14:14
 */
@Data
@Entity
@Table(name = "vaccine_info")
public class VaccineInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "card_number")
    private String cardNumber;


    @Override
    public String toString() {
        return "'" + cardNumber + "'";
    }
}
