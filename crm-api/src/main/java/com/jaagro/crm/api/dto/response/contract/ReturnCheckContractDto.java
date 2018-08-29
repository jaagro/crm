package com.jaagro.crm.api.dto.response.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 审核客户合同返回类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)

public class ReturnCheckContractDto implements Serializable {
    /**
     * 合同ID
     */
    private Integer id;

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 合同状态: 1-正常 2-终止
     */
    private Integer contractStatus;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 签约日期
     */
    private Date contractDate;

    /**
     * 合同类型
     */
    private Integer type;

    /**
     * 合同编号
     */
    private String contractNumber;

}
