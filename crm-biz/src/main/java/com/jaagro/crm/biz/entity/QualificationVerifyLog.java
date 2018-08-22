package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核表
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class QualificationVerifyLog implements Serializable {
    /**
     * 审核表主键id
     */
    private Integer id;

    /**
     * 资质类型(1-工商执照 2-身份证正面 3-身份证反面 4-......)
     */
    private Integer certificateType;

    /**
     * 审核结果(1-审核通过 2-审核不通过(客户信息和资质证照不匹配，客户信息和合同信息不匹配))
     */
    private Integer vertifyResult;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date createTime;

    /**
     * 审核不通过(2 审核不通过)
     */
    private Integer auditfailedReason;

    /**
     * 审核不通过描述信息(1、客户姓名与图片不符
     2、客户身份证号码与图片不符
     3、客户姓名与身份证号与合同信息不符)
     */
    private String description;

    /**
     * 和资质类型配合使用
     */
    private Integer referencesId;

}