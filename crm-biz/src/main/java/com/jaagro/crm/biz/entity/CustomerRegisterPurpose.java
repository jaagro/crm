package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户注册意向
 * @author yj
 * @since 20181213
 */
@Data
@Accessors(chain = true)
public class CustomerRegisterPurpose implements Serializable{
    /**
     * 客户注册意向表id
     */
    private Integer id;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 客户类型(1-个人,2-企业)
     */
    private Integer customerType;

    /**
     * 货物类型 1-毛鸡,2-饲料,3-母猪,4-公猪,5-仔猪,6-生猪
     */
    private Integer goodsType;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

}