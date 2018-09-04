package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.truckTeamContractPrice;

public interface truckTeamContractPriceMapper {
    /**
     *
     * @mbggenerated 2018-09-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insert(truckTeamContractPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int insertSelective(truckTeamContractPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    truckTeamContractPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKeySelective(truckTeamContractPrice record);

    /**
     *
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKey(truckTeamContractPrice record);
}