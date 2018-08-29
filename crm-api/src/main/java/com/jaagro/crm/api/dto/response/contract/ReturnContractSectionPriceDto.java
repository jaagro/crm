package com.jaagro.crm.api.dto.response.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ReturnContractSectionPriceDto implements Serializable {

    private Integer selectionId;

    private Long contractPriceId;

    private Integer sectionType;

    private BigDecimal lowerLimit;

    private BigDecimal upperLimit;

    private BigDecimal price;
}
