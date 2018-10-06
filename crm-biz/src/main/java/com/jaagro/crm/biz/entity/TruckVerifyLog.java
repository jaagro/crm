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

}