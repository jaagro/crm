package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ReturnContractSectionPriceDto;
import com.jaagro.crm.biz.entity.CustomerContractSectionPrice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerContractSectionPriceMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer selectionId);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerContractSectionPrice selectByPrimaryKey(Integer selectionId);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContractSectionPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContractSectionPrice record);

    /**
     * 根据外键id删除
     *
     * @param id
     * @return
     */
    int deleteByPriceId(Integer id);

    /**
     * 根据priceId获取所有sectionPrice记录
     *
     * @param priceId
     * @return
     */
    List<ReturnContractSectionPriceDto> listByPriceId(Integer priceId);

    /**
     * 根据传入值查询出符合limit条件的sectionPrice
     *
     * @param contractPriceId
     * @param value
     * @return
     */
    CustomerContractSectionPrice getByLimit(@Param("contractPriceId") Integer contractPriceId, @Param("value") BigDecimal value);
}