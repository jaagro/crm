package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettlePriceDto;
import com.jaagro.crm.api.dto.request.contract.UpdateCustomerContractSettlePriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettlePriceDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface CustomerContractSettlePriceService {

    /**
     * 创建客户合同结算信息
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

    /**
     * 逻辑删除
     *
     * @param priceId
     * @return
     */
    Map<String, Object> disableSettlePrice(Integer priceId);

    /**
     * 修改合同结算信息
     *
     * @param priceDto
     * @return
     */
    Map<String, Object> updateSettlePrice(UpdateCustomerContractSettlePriceDto priceDto);

    /**
     * 根据主键id查询历史记录
     *
     * @param priceId
     * @return
     */
    List<ReturnCustomerSettlePriceDto> listCustomerContractSettlePriceHistory(Integer priceId);

    /**
     * 根据客户合同id,装货地id,卸货地id获取实际里程
     * @param customerContractId
     * @param loadSiteId
     * @param unloadSiteId
     * @return
     */
    BigDecimal getMileageByParams(Integer customerContractId, Integer loadSiteId, Integer unloadSiteId);
}
