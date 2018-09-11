package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateDriverDto implements Serializable {

    /**
     * 关联车队表ID
     */
    private Integer truckTeamId;

    /**
     * 关联车辆ID
     */
    private Integer truckId;

    /**
     * 司机姓名
     */
    private String name;

    /**
     * 驾照类型
     */
    private Integer drivingLicense;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 驾驶证到期时间
     */
    private String expiryDrivingLicense;

    /**
     * 司机资质列表
     */
    private List<UpdateTruckQualificationDto> driverQualifications;
}
