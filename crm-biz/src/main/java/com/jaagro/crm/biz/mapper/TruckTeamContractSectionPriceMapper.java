package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckTeamContractSectionPrice;

public interface TruckTeamContractSectionPriceMapper {
    /**
     *
     * @mbggenerated 2018-09-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insert(TruckTeamContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insertSelective(TruckTeamContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    TruckTeamContractSectionPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKeySelective(TruckTeamContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKey(TruckTeamContractSectionPrice record);
}