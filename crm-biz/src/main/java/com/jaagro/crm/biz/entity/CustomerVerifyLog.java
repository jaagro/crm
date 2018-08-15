package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CustomerVerifyLog implements Serializable {
    /**
     * 客户审核表主键id
     */
    private Long id;

    /**
     * 验证状态(1-客户信息 2-资质证照 3-合同)
     */
    private Integer verifyType;

    /**
     * 审核结果(1-审核通过 2-审核不通过(不通过类型再商议))
     */
    private Integer auditResult;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核不通过(2 审核不通过)
     */
    private Integer auditType;

    /**
     * 审核不通过描述信息
     */
    private String desc;

    /**
     * 关联客户资质证照(类型,状态)
     */
    private Long referencesId;

}