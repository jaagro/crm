package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ListContractSecPriceCriteriaDto implements Serializable {

    private static final long serialVersionUID = -1195852192413693296L;
    private Integer selectionId;

    private Long contractPriceId;

    private Integer sectionType;

    private BigDecimal lowerLimit;

    private BigDecimal upperLimit;

    private BigDecimal price;

    private Integer selectionStatus;
}
