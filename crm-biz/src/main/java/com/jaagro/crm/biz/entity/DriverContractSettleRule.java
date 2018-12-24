package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yj
 * @since 20181224
 */
@Data
@Accessors(chain = true)
public class DriverContractSettleRule implements Serializable{
    private static final long serialVersionUID = -3710540210466594758L;
    /**
     * 司机结算规则主表id
     */
    private Integer id;

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
     * 起始里程(区间,公里为单位,不包含)
     */
    private BigDecimal startMileage;

    /**
     * 起始里程(起步计价,公里为单位)
     */
    private BigDecimal beginMileage;

    /**
     * 起步价格(元)
     */
    private BigDecimal beginPrice;

    /**
     * 里程单价(元/公里)
     */
    private BigDecimal mileagePrice;

    /**
     * 结束里程(包含)
     */
    private BigDecimal endMileage;

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
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date invalidTime;

    /**
     * 是否为历史配制 0-否,1-是
     */
    private Boolean historyFlag;

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
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;

}