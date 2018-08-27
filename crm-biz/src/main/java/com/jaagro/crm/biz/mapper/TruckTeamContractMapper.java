package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.response.driver.TruckTeamContractReturnDto;
import com.jaagro.crm.biz.entity.TruckTeamContract;

public interface TruckTeamContractMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(TruckTeamContract record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(TruckTeamContract record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    TruckTeamContract selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(TruckTeamContract record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(TruckTeamContract record);

    /**
     * 查询合同dto
     */
    TruckTeamContractReturnDto getById(Integer id);

    /**
     * 查询合同dto
     */
    TruckTeamContractReturnDto getByContractNumber(String contractNumber);
}