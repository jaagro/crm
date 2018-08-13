package com.jaagro.crm.api.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 价格计算返回dto
 * @author tony
 */
@Data
@Accessors(chain = true)
public class PriceReturnDto implements Serializable {

    /**
     * 满足条件的单价
     */
    private BigDecimal price;

    /**
     * 满足条件的总费用
     */
    private BigDecimal money;
}
