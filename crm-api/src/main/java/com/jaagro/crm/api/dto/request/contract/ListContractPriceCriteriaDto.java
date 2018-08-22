package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListContractPriceCriteriaDto implements Serializable {

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 客户Id
     */
    private Long customerId;

    /**
     * 计价模式
     */
    private Long pricingType;

    /**
     * 货物类型
     */
    private Long productType;

    /**
     * 提货地址id
     */
    private Long pickupAddressId;

    /**
     * 送货地址id
     */
    private Long shippingAddressId;

    /**
     * 车辆类型
     */
    private Long vehicleType;

    /**
     * 商品规格
     */
    private String specification;

    /**
     * 需计算的重量
     */
    private BigDecimal weight;

    /**
     * 需计算的数量
     */
    private Integer quantity;

    /**
     * 需计算的里程
     */
    private BigDecimal mileage;

}
