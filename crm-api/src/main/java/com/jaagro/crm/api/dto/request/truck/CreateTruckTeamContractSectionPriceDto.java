package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CreateTruckTeamContractSectionPriceDto implements Serializable {

    /**
     *
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

}
