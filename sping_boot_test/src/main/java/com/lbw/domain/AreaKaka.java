package com.lbw.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/12/28 9:50
 * @description：TODO
 */
@Data
@Entity
@Table(name = "area_kaka")
public class AreaKaka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "code")
    private String code;

    @Column(name = "color")
    private String color;

    @Column(name = "cover_type")
    private Integer coverType;

    @Column(name = "intro")
    private String intro;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "area_level")
    private Integer areaLevel;

    @Column(name = "name")
    private String name;

    @Column(name = "sort_nbr")
    private Integer sortNbr;

    @Column(name = "square")
    private String square;

    @Column(name = "type")
    private Integer type;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "center_lat")
    private String centerLat;

    @Column(name = "center_lng")
    private String centerLng;

    @Column(name = "is_grid")
    private Integer isGrid;
}
