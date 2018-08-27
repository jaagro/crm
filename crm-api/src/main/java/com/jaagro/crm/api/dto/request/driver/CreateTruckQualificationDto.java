package com.jaagro.crm.api.dto.request.driver;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateTruckQualificationDto implements Serializable {

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
     * 创建人(关联用户ID)
     */
    private Integer createUserId;

    /**
     * 描述信息
     */
    private String notes;

}
