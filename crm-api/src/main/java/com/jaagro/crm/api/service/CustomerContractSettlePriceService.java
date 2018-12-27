package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettlePriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettlePriceDto;

import java.util.List;

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

    /**
     * 根据合同id获得装卸货地报价列表【不包括历史】
     *
     * @param contractId
     * @return
     */
    List<ReturnCustomerSettlePriceDto> listByContractId(Integer contractId);
}
