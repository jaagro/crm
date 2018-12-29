package com.jaagro.crm.api.dto.response.truck;

import com.jaagro.crm.api.dto.request.contract.CreateDriverContractSettleSectionDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class TruckTeamContractPriceHistoryDto implements Serializable {
    /**
     * 车队名称
     */
    private String truckTeamName;
    /**
     * 车辆类型名称
     */
    private String truckTypeName;
    /**
     * 车辆类型
     */
    private Integer teamType;
    /**
     *
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

    /**
     * 操作人
     */
    private String createUserName;


}
