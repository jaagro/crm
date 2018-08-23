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
public class ContractLog implements Serializable {

    /**
     * 合同日志
     */
    private Integer id;

    /**
     * 合同id
     */
    private Integer contractId;

    /**
     * 报价ID
     */
    private Integer pricingId;

    /**
     * 阶梯报价ID
     */
    private Integer selectionId;

    /**
     * 日志类型:1-新增  2-删除
     */
    private Integer logType;

    /**
     * 日志描述
     * Nullable
     * 修改前后各是什么内容
     */
    private String logContent;

    /**
     * 日志记录时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;
}