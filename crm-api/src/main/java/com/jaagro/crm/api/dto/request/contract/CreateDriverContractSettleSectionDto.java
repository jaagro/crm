package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class CreateDriverContractSettleSectionDto implements Serializable {

    private static final long serialVersionUID = -1542749376562527303L;
    /**
     *
     */
    private Integer id;
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

    /**
     * 区间类型,1-按里程区间计价,2-按重量阶梯计价
     */
    private Integer type;

    /**
     * 单位,1-元/吨,2-元/公里
     */
    private Integer unit;
}
