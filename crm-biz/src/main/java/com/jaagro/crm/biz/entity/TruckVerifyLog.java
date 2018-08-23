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
public class TruckVerifyLog implements Serializable {
    /**
     * 司机车辆车队审核表主键id
     */
    private Integer id;

    /**
     * 验证状态(1-司机车辆车队信息 2-资质证照 3-合同)
     */
    private Integer verifyType;

    /**
     * 审核结果(1-审核通过 2-审核不通过(司机车辆车队信息和资质证照不匹配，司机车辆车队信息和合同信息不匹配))
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
     * 审核不通过
     */
    private Integer auditType;

    /**
     * 审核不通过描述信息(1、司机姓名与图片不符
 2、司机身份证号码与图片不符
 3、司机姓名与身份证号与合同信息不符)
     */
    private String description;

    /**
     * 关联司机车辆车队资质证照(类型,状态)
     */
    private Integer referencesId;

    /**
     * 司机车辆车队审核表主键id
     * @return id 司机车辆车队审核表主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 司机车辆车队审核表主键id
     * @param id 司机车辆车队审核表主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 验证状态(1-司机车辆车队信息 2-资质证照 3-合同)
     * @return verify_type 验证状态(1-司机车辆车队信息 2-资质证照 3-合同)
     */
    public Integer getVerifyType() {
        return verifyType;
    }

    /**
     * 验证状态(1-司机车辆车队信息 2-资质证照 3-合同)
     * @param verifyType 验证状态(1-司机车辆车队信息 2-资质证照 3-合同)
     */
    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }

    /**
     * 审核结果(1-审核通过 2-审核不通过(司机车辆车队信息和资质证照不匹配，司机车辆车队信息和合同信息不匹配))
     * @return audit_result 审核结果(1-审核通过 2-审核不通过(司机车辆车队信息和资质证照不匹配，司机车辆车队信息和合同信息不匹配))
     */
    public Integer getAuditResult() {
        return auditResult;
    }

    /**
     * 审核结果(1-审核通过 2-审核不通过(司机车辆车队信息和资质证照不匹配，司机车辆车队信息和合同信息不匹配))
     * @param auditResult 审核结果(1-审核通过 2-审核不通过(司机车辆车队信息和资质证照不匹配，司机车辆车队信息和合同信息不匹配))
     */
    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    /**
     * 审核人
     * @return auditor 审核人
     */
    public Integer getAuditor() {
        return auditor;
    }

    /**
     * 审核人
     * @param auditor 审核人
     */
    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    /**
     * 审核时间
     * @return audit_time 审核时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 审核时间
     * @param auditTime 审核时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 审核不通过
     * @return audit_type 审核不通过
     */
    public Integer getAuditType() {
        return auditType;
    }

    /**
     * 审核不通过
     * @param auditType 审核不通过
     */
    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    /**
     * 审核不通过描述信息(1、司机姓名与图片不符
 2、司机身份证号码与图片不符
 3、司机姓名与身份证号与合同信息不符)
     * @return description 审核不通过描述信息(1、司机姓名与图片不符
 2、司机身份证号码与图片不符
 3、司机姓名与身份证号与合同信息不符)
     */
    public String getDescription() {
        return description;
    }

    /**
     * 审核不通过描述信息(1、司机姓名与图片不符
 2、司机身份证号码与图片不符
 3、司机姓名与身份证号与合同信息不符)
     * @param description 审核不通过描述信息(1、司机姓名与图片不符
 2、司机身份证号码与图片不符
 3、司机姓名与身份证号与合同信息不符)
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 关联司机车辆车队资质证照(类型,状态)
     * @return references_id 关联司机车辆车队资质证照(类型,状态)
     */
    public Integer getReferencesId() {
        return referencesId;
    }

    /**
     * 关联司机车辆车队资质证照(类型,状态)
     * @param referencesId 关联司机车辆车队资质证照(类型,状态)
     */
    public void setReferencesId(Integer referencesId) {
        this.referencesId = referencesId;
    }
}