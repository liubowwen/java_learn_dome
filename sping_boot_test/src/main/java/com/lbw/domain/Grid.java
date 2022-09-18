package com.lbw.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "grid")
public class Grid {
    @Id
    @Column(name = "code", length = 20)
    private String code;
    @Column(name = "region_cd", columnDefinition = "varchar(255) COMMENT '行政区划'")
    private String regionCd;
    @Column(name = "area_name", columnDefinition = "varchar(255) COMMENT '行政区划'")
    private String areaName;
    @Column(name = "name", columnDefinition = "varchar(255) COMMENT '网格名称'")
    private String name;
    @Column(name = "sname", columnDefinition = "varchar(255) COMMENT '简称'")
    private String sname;
    @Column(name = "color", columnDefinition = "varchar(255) COMMENT '网格边界颜色'")
    private String color;
    @Column(name = "square", columnDefinition = "varchar(255) COMMENT '面积 (平方米)'")
    private String square;
    @Column(name = "type", columnDefinition = "int(11) COMMENT '网格类型：1城市网格 2农村网格　3专属网格'")
    private Integer type;
    @Column(name = "population", columnDefinition = "int(11) COMMENT '人口规模：1、500人以下 2、500-1000人　3、1000-1500人 4、1500人以上'")
    private Integer population;
    @Column(name = "party_branch", columnDefinition = "int(11) COMMENT '是否成立党支部：1 是 0 否'")
    private Integer partyBranch;
    @Column(name = "branch_type", columnDefinition = "int(11) COMMENT '网格党组织类型：1 网格党支部 2 楼栋党小组 3 党员中心户 4 其他 '")
    private Integer branchType;
    @Column(name = "cover_type", columnDefinition = "int(1) DEFAULT NULL COMMENT '1 覆盖 0 不覆盖'")
    private Integer coverType;
    @Column(name = "area_type", columnDefinition = "int(11) DEFAULT NULL COMMENT '网络类型为专属网格时：1党政机关 2医院 3学校 4企业 5园区 6商圈 7市场 8景区 9其他'")
    private Integer areaType;
    private Integer isDelete;
    private Long rowId;
    @Column(name = "points", columnDefinition = "text COMMENT '边界坐标点'")
    private String points;
    @Column(name = "modification_num", columnDefinition = "int(11) DEFAULT '0' COMMENT '修改次数: 系统保留字段，记录的版本号。'")
    private Integer modificationNum;
    @Column(name = "ou_id", columnDefinition = "bigint COMMENT '机构id'")
    private Long ouId;
    @Column(name = "sort_nbr", columnDefinition = "int(11) COMMENT '序号'")
    private Integer sortBbr;
    @Column(name = "points_tencent", columnDefinition = "text COMMENT '百度坐标系'")
    private String pointsTencent;
    @Column(name = "intro", columnDefinition = "text COMMENT '网格介绍：专属网格需填写本网格内相关信息'")
    private String intro;


}
