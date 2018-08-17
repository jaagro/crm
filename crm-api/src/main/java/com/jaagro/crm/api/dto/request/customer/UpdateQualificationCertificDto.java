package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 修改证件照帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateQualificationCertificDto implements Serializable {
    /**
     * 客户资质证照主键id
     */
    private Long id;

    /**
     * 证件类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 证件图片地址
     */
    private String certificateImageUrl;

    /**
     * 证件状态(0-未审核。1-正常 2-审核未通过审核 4-不可用)
     */
    private Integer certificateStatus;

    /**
     * 外键关联客户ID(References customer)
     */
    private Long customerId;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 是否可用（0不可用 1可用）
     */
    private Boolean enabled;

}
