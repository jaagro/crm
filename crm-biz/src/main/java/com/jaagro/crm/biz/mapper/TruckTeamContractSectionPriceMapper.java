package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.truck.TruckTeamContractSectionPriceReturnDto;
import com.jaagro.crm.biz.entity.TruckTeamContractSectionPrice;

import java.util.List;

public interface TruckTeamContractSectionPriceMapper {
    /**
     * @mbggenerated 2018-09-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-09-04
     */
    int insert(TruckTeamContractSectionPrice record);

    /**
     * @mbggenerated 2018-09-04
     */
    int insertSelective(TruckTeamContractSectionPrice record);

    /**
     * @mbggenerated 2018-09-04
     */
    TruckTeamContractSectionPrice selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKeySelective(TruckTeamContractSectionPrice record);

    /**
     * @mbggenerated 2018-09-04
     */
    int updateByPrimaryKey(TruckTeamContractSectionPrice record);

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