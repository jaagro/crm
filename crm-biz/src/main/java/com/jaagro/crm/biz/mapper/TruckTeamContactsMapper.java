package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckTeamContacts;

import java.util.List;

public interface TruckTeamContactsMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(TruckTeamContacts record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckTeamContacts record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    TruckTeamContacts selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckTeamContacts record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckTeamContacts record);

    /**
     * 通过车队id获取联系人
     * @param teamId
     * @return
     */
    List<TruckTeamContacts> listTruckTeamContacts(Integer teamId);
}