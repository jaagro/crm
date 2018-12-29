package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class CreateDriverContractSettleDto implements Serializable {


    private static final long serialVersionUID = 2328859250127634282L;
    /**
     * 计价方式(1-按区间重量单价,2-按区间里程单价,3-按起步里程)
     */
    private Integer pricingMethod;

    /**
     * 车辆类型id
     */
    private Integer truckTypeId;
    //----------------区间里程-------------

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
     * 司机合同结算里程区间配制
     * -- 毛鸡
     * -- 饲料
     */
    List<CreateDriverContractSettleSectionDto> createDriverContractSettleSectionDto;

    //--------------起步价模式-------------

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
}
