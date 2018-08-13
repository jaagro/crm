package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.ContractSectionPriceReturnDto;
import com.jaagro.crm.biz.entity.ContractSectionPrice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ContractSectionPriceMapper {

    int deleteByPrimaryKey(Integer selectionId);

    int insert(ContractSectionPrice record);

    int insertSelective(ContractSectionPrice record);

    ContractSectionPrice selectByPrimaryKey(Integer selectionId);

    int updateByPrimaryKeySelective(ContractSectionPrice record);

    int updateByPrimaryKey(ContractSectionPrice record);

    int deleteByPriceId(Long id);

    /**
     * 根据priceId获取所有sectionPrice记录
     *
     * @param priceId
     * @return
     */
    List<ContractSectionPriceReturnDto> listByPriceId(Long priceId);

    /**
     * 根据传入值查询出符合limit条件的sectionPrice
     *
     * @param contractPriceId
     * @param value
     * @return
     */
    ContractSectionPrice getByLimit(@Param("contractPriceId") Long contractPriceId, @Param("value") BigDecimal value);
}