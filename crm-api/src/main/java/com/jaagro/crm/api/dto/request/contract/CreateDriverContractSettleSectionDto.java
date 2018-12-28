package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class CreateDriverContractSettleSectionDto {

    /**
     * 区间起始(不包含)
     */
    private BigDecimal start;

    /**
     * 区间结束(包含)
     */
    private BigDecimal end;

    /**
     * 结算单价
     */
    private BigDecimal settlePrice;
}
