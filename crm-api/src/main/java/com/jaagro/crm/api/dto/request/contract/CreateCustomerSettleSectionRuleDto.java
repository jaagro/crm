package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户合同结算配制dto
 *
 * @author baiyiran
 * @Date 2018/12/25
 */
@Accessors(chain = true)
@Data
public class CreateCustomerSettleSectionRuleDto implements Serializable {

    private static final long serialVersionUID = 8071365076080303570L;
    /**
     * 客户合同id
     */
    private Integer customerContractId;

    /**
     * 客户合同结算配制表id
     */
    private Integer customerContractSettleRuleId;

    /**
     * 区间起始(不包含)
     */
    private BigDecimal start;

    /**
     * 区间结束(包含)
     */
    private BigDecimal end;

    /**
     * 区间类型,1-里程,2-重量
     */
    private Integer type;

    /**
     * 价格
     */
    private BigDecimal settlePrice;

}
