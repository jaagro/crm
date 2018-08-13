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
public class ContractSectionPrice implements Serializable {

    private Integer selectionId;

    private Long contractPriceId;

    private Integer sectionType;

    private BigDecimal upperLimit;

    private BigDecimal lowerLimit;

    private BigDecimal price;

    private Integer selectionStatus;
}