package com.jaagro.crm.api.dto.response.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class ListDriverContractSettlelInfoDto implements Serializable {
    /**
     * 合同运力结算配置表 id
     */
    private Integer contractSettleId;

    /**
     * 车辆类型名称
     */
    private String truckTypeName;

    /**
     * /**
     * 区间类型,1-按里程区间计价,2-按重量阶梯计价
     */
    private Integer type;

    /**
     * 单位,1-元/吨,2-元/公里
     */
    private Integer unit;

    /**
     * 最小结算里程
     * --毛鸡
     */
    private BigDecimal minSettleMileage;

    /**
     * 最小结算重量(吨)
     * --饲料
     */
    private BigDecimal minSettleWeight;

    /**
     *起步价
     */
    private BigDecimal beginSettlePrice;

    /**
     * 支付方式
     */
    private Integer pricingMethod;
}
