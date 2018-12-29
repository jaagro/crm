package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateTruckQualificationDto implements Serializable {
    private static final long serialVersionUID = -27456897942571380L;
    /**
     * 资质证照主键id
     */
    private Integer id;

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
     * 不通过原因
     */
    private Integer vertifyResult;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 描述信息
     */
    private String notes;

}
