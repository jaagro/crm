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
public class CustomerContractSectionPrice implements Serializable {
    private static final long serialVersionUID = -7105355049166676638L;
    /**
     * 客户合同阶梯报价
     */
    private Integer selectionId;

    /**
     * 合同报价id
     */
    private Integer contractPriceId;

    /**
     * 1-重量阶梯报价 2-里程阶梯报价
     */
    private Integer sectionType;

    /**
     * 阶梯下限
     */
    private BigDecimal upperLimit;

    /**
     * 阶梯上限
     */
    private BigDecimal lowerLimit;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 是否有效 1-有效，0-无效
     */
    private Integer selectionStatus;
}