package com.jaagro.crm.api.dto.response.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 客户合同分页查询 返回类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)

public class ReturnContractQualificationDto implements Serializable {
    /**
     * 合同资质证照主键id
     */
    private Integer id;

    /**
     * 关联类型：1-客户合同 2-司机合同
     */
    private Integer relevanceType;

    /**
     * 关联id
     */
    private Integer relevanceId;

    /**
     * 资质类型：1-合同首页 2-合同签字页 3-合同报价页
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
     * 描述信息
     */
    private String description;

    /**
     * 创建人(References: user)
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人(References: user)
     */
    private Integer modifyUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否可用（0不可用 1可用）
     */
    private Boolean enabled;
}
