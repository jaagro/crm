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
public class ListDriverContractSettleDto implements Serializable {

    /**
     * 合同运力结算配置表 id
     */
    private Integer id;
    /**
     * 合同id
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
     * 是否记为历史
     */
    private Integer historyFlag;
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

    /**
     * 创建人id
     */
    private Integer createUserId;


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
