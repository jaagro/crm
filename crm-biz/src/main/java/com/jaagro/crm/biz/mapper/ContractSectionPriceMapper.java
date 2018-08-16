package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ContractSectionPriceReturnDto;
import com.jaagro.crm.biz.entity.ContractSectionPrice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author baiyiran
 */
public interface ContractSectionPriceMapper {

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer selectionId);

    /**
     * 新增全表
     *
     * @param record
     * @return
     */
    int insert(ContractSectionPrice record);

    /**
     * 新增关键字段
     *
     * @param record
     * @return
     */
    int insertSelective(ContractSectionPrice record);

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    ContractSectionPrice selectByPrimaryKey(Integer selectionId);

    /**
     * 依据字段修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ContractSectionPrice record);

    /**
     * 修改全表
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(ContractSectionPrice record);

    /**
     * 根据外键id删除
     *
     * @param id
     * @return
     */
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