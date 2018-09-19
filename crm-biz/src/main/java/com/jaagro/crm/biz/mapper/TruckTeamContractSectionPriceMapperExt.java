package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.truck.TruckTeamContractSectionPriceReturnDto;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckTeamContractSectionPriceMapperExt extends TruckTeamContractSectionPriceMapper{

    /**
     * 根据报价id查询列表
     *
     * @param priceId
     * @return
     */
    List<TruckTeamContractSectionPriceReturnDto> listByPriceId(Integer priceId);

    /**
     * 根据报价id逻辑删除
     *
     * @param id
     * @return
     */
    int disableByPriceId(Integer id);

    /**
     * 根据报价id删除
     *
     * @param id
     * @return
     */
    int deleteByPriceId(Integer id);
}