package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户合同结算基础信息主表dto
 *
 * @author baiyiran
 * @Date 2018/12/28
 */
@Data
@Accessors(chain = true)
public class UpdateCustomerContractSettlePriceDto implements Serializable {

    private static final long serialVersionUID = 1687640354272806707L;
    /**
     * 客户合同结算基础信息主表id
     */
    private Integer id;

    /**
     * 结算单价
     */
    private BigDecimal settlePrice;
}
