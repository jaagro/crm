package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.contract.CreateSettleMileageDto;

/**
 * @author baiyiran
 * @Date 2018/12/28
 */
public interface SettleMileageService {

    /**
     * 创建结算里程
     *
     * @param mileageDto
     * @return
     */
    Boolean createSettleMileage(CreateSettleMileageDto mileageDto);

}
