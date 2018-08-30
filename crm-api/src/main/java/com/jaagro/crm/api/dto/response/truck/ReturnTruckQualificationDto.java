package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ReturnTruckQualificationDto implements Serializable {
    /**
     * 资质证照主键id
     */
    private Integer id;

    /**
     * 关联车队表
     */
    private Integer truckTeamId;

    /**
     * 关联车辆表
     */
    private Integer truckId;

    /**
     * 所属驾驶员ID
     */
    private Integer driverId;

    /**
     * 资质类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 证件图片地址
     */
    private String certificateImageUrl;

    /**
     * 证件状态(0；审核未通过 1；未审核 2；已审核)
     */
    private Integer certificateStatus;

    /**
     * 描述信息
     */
    private String notes;

    /**
     * 车队信息
     */
    private ReturnCheckTruckTeamDto teamDto;

    /**
     * 车辆信息
     */
    private ReturnCheckTruckDto truckDto;

    /**
     * 司机信息
     */
    private DriverReturnDto driverReturnDto;

}
