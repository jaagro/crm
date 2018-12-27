package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettlePriceDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface CustomerContractSettlePriceService {

    /**
     * 创建客户合同报价
     *
     * @param settlePriceDto
     * @return
     */
    Boolean createCustomerSettlePrice(CreateCustomerSettlePriceDto settlePriceDto);
}
