package com.lbw.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/19 13:55
 * @description：TODO
 */

@Entity
@Table(name = "teacher")
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "subject")
    private String subject;

    @Column(name = "idcard")
    private String idcard;


    /**
     * 创建时间
     */

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    @CreationTimestamp
    private Date createTime;
    @Transient
    private Student student;
    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    @UpdateTimestamp
    private Date updateTime;




}
