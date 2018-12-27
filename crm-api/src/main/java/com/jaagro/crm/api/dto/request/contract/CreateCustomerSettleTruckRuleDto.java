package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户合同 结算配制 车辆配制子表dto
 * * @author baiyiran
 *
 * @Date 2018/12/25
 */
@Accessors(chain = true)
@Data
public class CreateCustomerSettleTruckRuleDto implements Serializable {

    /**
     * 客户合同id
     */
    private Integer customerContractId;

    /**
     * 客户合同结算配制表id
     */
    private Integer customerContractSettleRuleId;

    /**
     * 车型id
     */
    private Integer truckTypeId;

    /**
     * 车辆类型名称
     */
    private String truckTypeName;

    /**
     * 最小装载量(吨)
     */
    private BigDecimal minLoad;

    /**
     * 常用装载量
     */
    private BigDecimal normalLoad;

    /**
     * 价格基数
     */
    private BigDecimal priceBase;

    /**
     * 最小里程数
     */
    private BigDecimal minMileage;

}
