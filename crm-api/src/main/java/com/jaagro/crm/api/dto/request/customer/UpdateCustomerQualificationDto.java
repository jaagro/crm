package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 修改证件照帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateCustomerQualificationDto implements Serializable {
    /**
     * 客户资质证照主键id
     */
    private Integer id;

    /**
     * 证件类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 证件图片地址
     */
    private String certificateImageUrl;

    /**
     * 证件状态(-1；审核未通过 0；未审核 1；已审核)
     */
    private Integer certificateStatus;

    /**
     * 外键关联客户ID(References customer)
     */
    private Integer customerId;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 是否可用（0不可用 1可用）
     */
    private Boolean enabled;

}
