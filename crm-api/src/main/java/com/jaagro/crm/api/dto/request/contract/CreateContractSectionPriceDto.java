package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 阶梯报价新增帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CreateContractSectionPriceDto implements Serializable {

    private static final long serialVersionUID = -7243316648996113676L;
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

}
