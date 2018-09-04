package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class TruckTeamContractPrice implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 合同id
 Nullable
 References: contract
     */
    private Integer truckTeamContractId;

    /**
     * 计价方式
 说明：
 1、按重量; 
 2、按数量;
 3、按重量＊里程； 
 4，按数量＊里程
 Nullable
     */
    private Integer pricingType;

    /**
     * 车辆类型
     */
    private Integer vehicleType;

    /**
     * 货物类型
     */
    private Integer productType;

    /**
     * 单价
 Nullable
     */
    private BigDecimal price;

    /**
     * 是否有效
     */
    private Boolean priceStatus;

    /**
     * 报价生效时间
     */
    private Date startDate;

    /**
     * 报价截止时间
     */
    private Date endDate;
}