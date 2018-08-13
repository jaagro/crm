package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ContractPrice implements Serializable {

    private Long id;

    private Long contractId;

    private Integer pricingType;

    private Long pickupAddressId;

    private Long shippingAddressId;

    private Long vehicleType;

    private Long productType;

    private String specification;

    private BigDecimal price;

    private Integer hasWeightSectionValues;

    private Integer hasMileSectionValues;

    private BigDecimal minMile;

    private BigDecimal minWeight;

    private BigDecimal minMoney;

    private Integer priceStatus;
}