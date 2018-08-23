package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class QualificationCertific implements Serializable {
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
     * 创建人(References: user)
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人(References: user)
     */
    private Long modifyUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否可用（0不可用 1可用）
     */
    private Boolean enabled;
}