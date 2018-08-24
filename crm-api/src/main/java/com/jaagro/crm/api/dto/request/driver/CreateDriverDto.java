package com.jaagro.crm.api.dto.request.driver;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
    private String driverName;

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
}
