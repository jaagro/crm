package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class DriverContractSettleParam implements Serializable {

    private static final long serialVersionUID = -7288538034155057030L;
    /**
     * 车队合同id
     */
    private Integer truckTeamContractId;

    /**
     * 车辆类型id
     */
    private Integer truckTypeId;

    /**
     * 车辆类型名称
     */
    private String truckTypeName;

    /**
     * 是否为历史配制 0-否,1-是
     */
    private Boolean historyFlag;

    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 区间类型,1-里程,2-重量
     */
    private Integer type;

    /**
     * 单位,1-元/吨,2-元/公里
     */
    private Integer unit;

    /**
     * 起始里程(起步计价,公里为单位)
     */
    private BigDecimal beginMileage;

    /**
     * 里程单价(元/公里)
     */
    private BigDecimal mileagePrice;

    /**
     * 起步价格(元)
     */
    private BigDecimal beginPrice;

    /**
     * 最小结算里程
     */
    private BigDecimal minSettleMileage;

    /**
     * 最小结算重量(吨)
     */
    private BigDecimal minSettleWeight;

    /**
     * 计价方式(1-按区间重量单价,2-按区间里程单价,3-按起步里程+里程单价)
     */
    private Integer pricingMethod;

    /**
     * 创建人
     */
    private Integer createUserId;
}
