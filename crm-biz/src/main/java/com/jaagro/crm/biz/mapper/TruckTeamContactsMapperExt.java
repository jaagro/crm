package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckTeamContacts;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckTeamContactsMapperExt extends TruckTeamContactsMapper{
    /**
     * 通过车队id获取联系人
     *
     * @param teamId
     * @return
     */
    List<TruckTeamContacts> listTruckTeamContacts(Integer teamId);

    /**
     * 根据车队id删除
     *
     * @param truckTeamId
     */
    void deleteByTruckTeamId(Integer truckTeamId);
}