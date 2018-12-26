package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gavin
 * 20181225
 */
@Data
@Accessors(chain = true)
public class QuerySettleRuleDto implements Serializable {

    /**
     * 运单完成时间，必须完成的运单才能有结算金额
     */
    private Date doneDate;

    /**
     * 客户合同id
     */
    private Integer customerContractId;

    /**
     * 实际的里程
     */
    private BigDecimal actualMileage;

    /**
     * 车辆类型
     */
    private Integer truckTypeId;

}
