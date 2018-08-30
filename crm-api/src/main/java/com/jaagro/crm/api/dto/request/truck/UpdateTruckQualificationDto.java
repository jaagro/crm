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
    /**
     * 资质证照主键id
     */
    private Integer id;

    /**
     * 资质类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 证件状态(0；审核未通过 1；未审核 2；已审核)
     */
    private Integer certificateStatus;

    /**
     * 描述信息
     */
    private String description;

}
