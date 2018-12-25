package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CreateContractQualificationDto implements Serializable {

    private static final long serialVersionUID = 6204518611244505211L;
    /**
     * 关联id
     */
    private Integer relevanceId;

    /**
     * 关联类型：1-客户合同 2-司机合同
     */
    private Integer relevanceType;

    /**
     * 资质类型：1-合同首页 2-合同签字页 3-合同报价页
     */
    private Integer certificateType;

    /**
     * 证件图片地址
     */
    private String certificateImageUrl;

    /**
     * 描述信息
     */
    private String description;
}
