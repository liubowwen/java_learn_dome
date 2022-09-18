package com.nat.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/8/19 13:55
 * @description：TODO
 */
@Entity
@Table(name = "student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "idcard")
    private String idcard;


    /**
     * 创建时间
     */

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    @CreationTimestamp
    private Date createTime;


    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    @UpdateTimestamp
    private Date updateTime;
}
