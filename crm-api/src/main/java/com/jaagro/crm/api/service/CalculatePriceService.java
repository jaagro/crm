package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 价格计算service
 *
 * @author tony
 */
public interface CalculatePriceService {

    /**
     * 与客户结算的计算
     * @param dtoList
     * @return 结算金额
     */

    List<Map<Integer,BigDecimal>> calculatePaymentFromCustomer(List<CalculatePaymentDto> dtoList);

    /**
     * 运力结算：与司机的结算
     * @param dtoList
     * @return
     */
    List<Map<Integer,BigDecimal>> calculatePaymentToDriver(List<CalculatePaymentDto> dtoList);

    /**
     * 根据客户装卸货地实际里程获取结算单价
     * @param mileage
     * @param customerContractId
     * @return
     */
    BigDecimal calculatePriceFromMileageSection(Integer customerContractId,BigDecimal mileage);
}
