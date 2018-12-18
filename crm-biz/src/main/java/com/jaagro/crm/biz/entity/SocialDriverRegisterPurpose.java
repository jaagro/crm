package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SocialDriverRegisterPurpose implements Serializable{
    /**
     * 社会司机注册意向表id
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
     * 司机类型(1-个人,2-企业)
     */
    private Integer driverType;

    /**
     * 车辆数量
     */
    private Integer truckQuantity;

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
     * 是否上传基本信息 0-未上传,1-已上传
     */
    private Boolean uploadFlag;

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