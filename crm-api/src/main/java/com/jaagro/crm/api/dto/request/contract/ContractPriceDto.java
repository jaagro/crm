package com.jaagro.crm.api.dto.request.contract;

import com.jaagro.crm.api.dto.response.contract.ContractSectionPriceDto;
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
public class ContractPriceDto implements Serializable {

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

    private List<ContractSectionPriceDto> sectionPrice;
}
