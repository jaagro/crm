package com.jaagro.crm.api.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ContractSectionPriceDto implements Serializable {

    private Integer selectionId;

    private Long contractPriceId;

    private Integer sectionType;

    private BigDecimal lowerLimit;

    private BigDecimal upperLimit;

    private BigDecimal price;

    private Integer selectionStatus;
}
