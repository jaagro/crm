package com.jaagro.crm.api.dto.request.truck;

/**
 * @author baiyiran
 */
public class UpdateDriverDto {
    /**
     * 司机id
     */
    private Integer driverId;

    /**
     * 司机姓名
     */
    private String name;

    /**
     * 驾照类型
     */
    private Integer drivingLicense;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 驾驶证到期时间
     */
    private String expiryDrivingLicense;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 备注
     */
    private String notes;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属区县
     */
    private String county;
}
