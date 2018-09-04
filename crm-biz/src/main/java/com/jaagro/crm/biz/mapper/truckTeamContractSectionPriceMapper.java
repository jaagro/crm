package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.truckTeamContractSectionPrice;

public interface truckTeamContractSectionPriceMapper {
    /**
     *
     * @mbggenerated 2018-09-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insert(truckTeamContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insertSelective(truckTeamContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    truckTeamContractSectionPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKeySelective(truckTeamContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKey(truckTeamContractSectionPrice record);
}