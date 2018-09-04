package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckTeamContractPrice;

public interface TruckTeamContractPriceMapper {
    /**
     *
     * @mbggenerated 2018-09-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insert(TruckTeamContractPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insertSelective(TruckTeamContractPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    TruckTeamContractPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKeySelective(TruckTeamContractPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKey(TruckTeamContractPrice record);
}