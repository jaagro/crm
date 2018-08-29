package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContactsDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContactsService {

    /**
     * 创建车队联系人
     * @param contacts
     * @return
     */
    Map<String, Object> createTruckTeamContacts(CreateTruckTeamContactsDto contacts);

    /**
     * 根据车队获取车队联系人
     * @param teamId
     * @return
     */
    Map<String, Object> listTruckTeamContacts(Integer teamId);
}
