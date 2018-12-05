package com.jaagro.crm.api.dto.request.socialDriver;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 创建社会司机注册意向参数
 * @author yj
 * @since 2018/12/5
 */
@Data
@Accessors
public class CreateSocialDriverRegisterPurposeDto implements Serializable{
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
     * 备注
     */
    private String notes;
}
