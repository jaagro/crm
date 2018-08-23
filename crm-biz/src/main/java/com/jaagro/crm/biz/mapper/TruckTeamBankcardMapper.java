package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckTeamBankcard;

public interface TruckTeamBankcardMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(TruckTeamBankcard record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckTeamBankcard record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    TruckTeamBankcard selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckTeamBankcard record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckTeamBankcard record);
}