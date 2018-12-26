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
@Data
@Accessors(chain = true)
public class CreateCustomerSettlePriceDto implements Serializable {

    /**
     * 客户合同合同id
     */
    private Integer customerContractId;

    /**
     * 装货地id
     */
    private Integer loadSiteId;

    /**
     * 装货地名称
     */
    private String loadSiteName;

    /**
     * 卸货地id
     */
    private Integer unloadSiteId;

    /**
     * 卸货地名称
     */
    private String unloadSiteName;

    /**
     * 车辆类型id(0-所有)
     */
    private Integer truckTypeId;

    /**
     * 车辆类型名称
     */
    private String truckTypeName;

    /**
     * 货物类型 1-毛鸡,2-饲料,3-仔猪,4-生猪
     */
    private Integer goodsType;

    /**
     * 里程数
     */
    private BigDecimal mileage;

    /**
     * 结算单价
     */
    private BigDecimal settlePrice;

    /**
     * 单位 1-元/吨,2-元/头/公里,3-元/公里
     */
    private Integer unit;

    /**
     * 生效时间(最小值为合同生效时间)
     */
    private Date effectiveTime;

    /**
     * 失效时间(最大值为合同失效时间)
     */
    private Date invalidTime;

}
