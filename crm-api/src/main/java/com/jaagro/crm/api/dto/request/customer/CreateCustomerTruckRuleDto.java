package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
@Accessors(chain = true)
@Data
public class CreateCustomerTruckRuleDto implements Serializable {

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

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;
}
