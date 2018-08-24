package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamContactsDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContactsService {

    /**
     * 创建车队合同关联关系
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContacts(List<CreateTruckTeamContactsDto> dto, Integer truckTeamId);
}
