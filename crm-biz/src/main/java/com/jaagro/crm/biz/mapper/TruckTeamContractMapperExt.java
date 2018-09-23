package com.jaagro.crm.biz.mapper;


import com.jaagro.crm.api.dto.request.truck.ListTruckTeamContractCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamContractReturnDto;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckTeamContractMapperExt extends TruckTeamContractMapper{

    /**
     * 查询合同dto
     */
    TruckTeamContractReturnDto getById(Integer id);

    /**
     * 根据车队id查询
     *
     * @param TruckTeamId
     * @return
     */
    List<TruckTeamContractReturnDto> listByTruckTeamId(Integer TruckTeamId);

    /**
     * 分页查询车队合同
     *
     * @param criteriaDto
     * @return
     */
    List<ListTruckTeamContractDto> listByCriteria(ListTruckTeamContractCriteriaDto criteriaDto);

    /**
     * 根据合同编号查询合同
     *
     * @param contractNumber
     * @return
     */
    TruckTeamContract getByContractNumber(@Param("contractNumber") String contractNumber);
}