package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamBankcardDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamBankcardService {

    /**
     * 创建车队银行账号
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamBankcard(List<CreateTruckTeamBankcardDto> dto, Integer truckTeamId);
}
