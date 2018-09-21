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
public class UpdateCustomerQualificationDto implements Serializable {

    /**
     * 客户资质证照主键id
     */
    private Integer id;

    /**
     * 证件状态(-1；审核未通过 0；未审核 1；已审核)
     */
    private Integer certificateStatus;

    /**
     * 证件图片地址
     */
    private String certificateImageUrl;

    /**
     * 证件类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 描述信息
     */
    private String description;
}
