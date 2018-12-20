package com.jaagro.crm.api.dto.request.customerRegister;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @since 2018/12/17
 */
@Data
@Accessors(chain = true)
public class UpdateCustomerRegisterPurposeDto implements Serializable {
    /**
     * 客户注册意向表id
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
     * 客户类型(1-个人,2-企业)
     */
    @NotNull(message = "{customerType.NotNull}")
    @Min(value = 1,message = "{customerType.Min}")
    private Integer customerType;

    /**
     * 货物类型 1-毛鸡,2-饲料,3-母猪,4-公猪,5-仔猪,6-生猪
     */
    @NotNull(message = "{goodsType.NotNull}")
    @Min(value = 1,message = "{goodsType}")
    private Integer goodsType;

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
