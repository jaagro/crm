package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContactsDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContactsDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContactsService {

    /**
     * 创建车队联系人
     *
     * @param truckTeamContacts
     * @return
     */
    Map<String, Object> createTruckTeamContacts(List<CreateTruckTeamContactsDto> truckTeamContacts);

    /**
     * 根据车队获取车队联系人
     *
     * @param teamId
     * @return
     */
    Map<String, Object> listTruckTeamContacts(Integer teamId);

    /**
     * 修改车队联系人
     *
     * @param contacts
     * @return
     */
    Map<String, Object> updateContacts(List<UpdateTruckTeamContactsDto> contacts);
}
