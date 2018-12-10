package com.jaagro.crm.api.dto.request.socialDriver;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建社会司机注册意向参数
 * @author yj
 * @since 2018/12/5
 */
@Data
@Accessors
public class UpdateSocialDriverRegisterPurposeDto implements Serializable{
    /**
     * 社会司机注册意向表id
     */
    private Integer id;
    /**
     * 真实姓名
     */
    @NotBlank(message = "{name.NotBlank}")
    private String name;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 司机类型(1-个人,2-企业)
     */
    @NotNull(message = "{driverType.NotNull}")
    @Min(value = 1,message = "{driverType.Min}")
    private Integer driverType;

    /**
     * 车辆数量
     */
    @NotNull(message = "{truckQuantity.NotNull}")
    @Min(value = 1,message = "{truckQuantity.Min}")
    private Integer truckQuantity;

    /**
     * 所属城市
     */
    @NotBlank(message = "{city.NotBlank}")
    private String city;

    /**
     * 详细地址
     */
    @NotBlank(message = "{detailAddress.NotBlank}")
    private String detailAddress;

    /**
     * 备注
     */
    private String notes;
}
