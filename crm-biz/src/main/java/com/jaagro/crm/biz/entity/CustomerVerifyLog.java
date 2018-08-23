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
public class CustomerVerifyLog implements Serializable {
    /**
     * 审核表主键id
     */
    private Long id;

    /**
     * 审核类型(1-客户信息 2-资质证照 3-合同)
     */
    private Integer verifyType;

    /**
     * 审核结果(1-审核通过 2-审核不通过(客户信息和资质证照不匹配，客户信息和合同信息不匹配))
     */
    private Integer auditResult;

    /**
     * 审核人
     */
    private Integer auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核不通过(2 审核不通过)
     */
    private Integer auditType;

    /**
     * 审核不通过描述信息(1、客户姓名与图片不符
 2、客户身份证号码与图片不符
 3、客户姓名与身份证号与合同信息不符)
     */
    private String description;

    /**
     * 根据审核类型判断关联表[customer|qualification_certific|contract]
     */
    private Long referencesId;
}