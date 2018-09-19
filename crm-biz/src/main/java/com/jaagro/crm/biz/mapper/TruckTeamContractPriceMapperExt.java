package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.truck.TruckTeamContractPriceReturnDto;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckTeamContractPriceMapperExt extends TruckTeamContractPriceMapper{

    /**
     * 根据合同id查询列表
     *
     * @param contrctId
     * @return
     */
    List<TruckTeamContractPriceReturnDto> listByContractId(Integer contrctId);

    /**
     * 根据合同id逻辑删除
     *
     * @param id
     * @return
     */
    int disableByContractId(Integer id);

    /**
     * 根据合同id删除
     *
     * @param id
     * @return
     */
    int deleteByContractId(Integer id);
}