package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.truck.TruckTeamContractPriceReturnDto;
import com.jaagro.crm.biz.entity.TruckTeamContractPrice;

import java.util.List;

public interface TruckTeamContractPriceMapper {
    /**
     * @mbggenerated 2018-09-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-09-04
     */
    int insert(TruckTeamContractPrice record);

    /**
     * @mbggenerated 2018-09-04
     */
    int insertSelective(TruckTeamContractPrice record);

    /**
     * @mbggenerated 2018-09-04
     */
    TruckTeamContractPrice selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKeySelective(TruckTeamContractPrice record);

    /**
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKey(TruckTeamContractPrice record);

    /**
     * 根据合同id查询列表
     *
     * @param contrctId
     * @return
     */
    List<TruckTeamContractPriceReturnDto> listByContractId(Integer contrctId);
}