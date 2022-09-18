package com.lbw.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zbl
 * @description
 * @date 2019/10/17 14:14
 */
@Data
@Entity
@Table(name = "area")
public class Area implements Serializable {

    @Id
    @Column(name = "code",length = 30)
    private String code;

    @Column(name = "addr_type")
    private Integer addrType;//1:省 2：市 3：区 4：街道 5：社区或村 6:小区、街、路、巷 7：楼 8:单元 9:房间
    @Column(name = "sort_code")
    private String sortCode;

    @Column(name = "second_code")
    private String secondCode;
    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "floor",columnDefinition = "varchar(255) COMMENT '所属楼层'")
    private String floor;//所属楼层

    @Column(name = "floor_num",columnDefinition = "varchar(255) COMMENT '楼层数量'")
    private String floorNum;//楼层数量

    @ManyToOne
    @JoinColumn(name = "parent_code", referencedColumnName = "code", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT), columnDefinition = "varchar(24) COMMENT '父级code'")
    private Area parent;
    @Column(name = "x")
    private BigDecimal x;
    @Column(name = "y")
    private BigDecimal y;

    private Integer isHot;
    @Column(name = "image", length=2000)
    private String image;//图片

    @Column(name = "grid_code")
    private String gridCode;//网格code

    @Column(name = "grid_name")
    private String gridName;//网格名称

    @Column(name = "is_delete")
    private Integer isDelete=0;//0-正常，1-删除

    @Column(name = "acreage")
    private String acreage;//面积

    @Column(name = "house_number")
    private String houseNumber;//门牌

    @Column(name = "full_name")
    private String fullName;//全称

    @Column(name = "is_recalculate")
    private Integer isRecalculate = 0;//是否重新计算网格 0-否，1-是

    @Column(name = "complete_recalculate")
    private Integer completeRecalculate = 0;//是否完成网格重新计算 0-还未重新计算，1-重新计算完成，2-重新计算失败

    @Column(name = "grid_exchange")
    private Integer gridExchange = 0;//网格是否变化 0-否，1-是

    @Column(name = "xy_exchange")
    private Integer xyExchange = 0;//xy坐标是否变化 0-否，1-是

    private Integer type;//类型

    @Column(name = "create_time")
    private Date createTime;//创建时间



    @Column(name = "sort_num")
    private Integer sortNum;

    @Transient
    private List nextData;

    @Transient
    private Boolean isNode=true;//是否为节点

    //房户新增字段
    @Column(name = "house_type")
    private Integer houseType;//房屋类型：1楼房 2平房 3别墅 4违章房屋 5临时房屋

    @Column(name = "house_purpose")
    private Integer housePurpose;//房屋用途：1住宅2商业3办公4工业5仓储6商住混用7服务业、居住混用8出租9服务业10制造加工业11空置房12养殖业13教育14医疗15餐饮业99其他

    @Column(name = "owner_name")
    private String ownerName;//房主名字

    @Column(name = "owner_mobile")
    private String ownerMobile;//房主联系电话

    @Column(name = "cert_type")
    private Integer certType;//房主证件类型：1身份证2护照3港澳通行证

    @Column(name = "card_id")
    private String cardId;//房主证件号码

    @Column(name = "is_rent")
    private Integer isRent;//是否出租（0未出租，1出租）如果填出租了需要填写房屋租住信息表

    @Column(name = "master_id")
    private String masterId;//存放该房屋的房东在es中的kw_business_id

    //小区新增字段
    @Column(name = "lng")
    private BigDecimal lng;//小区定位的经度
    @Column(name = "lat")
    private BigDecimal lat;//小区定位的纬度
    @Column(name = "is_smart")
    private Integer isSmart;//是否是平安智慧小区，1是，0不是
    @Column(name = "is_close")
    private Integer isClose;//是否封闭，1是，0不是
    @Column(name = "property_type")
    private Integer propertyType;//物业公司类型，1公办，2私立
    @Column(name = "property_name")
    private String propertyName;//物业公司名字

    //楼栋新增字段
    @Column(name = "building_type")
    private Integer buildingType;//楼栋类型，1商品房2自建房3别墅4违建楼5临时房屋6军产房7公益房8保障性住房
    @Column(name = "floor_houses",columnDefinition = "varchar(255) COMMENT '每层户数'")
    private String floorHouses;//每层户数
    @Column(name = "unit")
    private Integer unit;//楼下单元数
    @Column(name = "households_num",columnDefinition = "varchar(20) COMMENT '楼栋内的住户数'")
    private String householdsNum;//楼栋内的住户数
    @Column(name = "building_leader",columnDefinition = "varchar(20) COMMENT '楼栋长'")
    private String buildingLeader;//楼栋长，如果没有楼栋长就填无，如果有就填名字
    @Column(name = "building_class",columnDefinition = "varchar(20) COMMENT '楼栋分类'")
    private String buildingClass;//楼栋分类，1楼房 2平房













}
