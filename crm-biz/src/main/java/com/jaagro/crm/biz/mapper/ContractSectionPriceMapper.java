package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ReturnContractSectionPriceDto;
import com.jaagro.crm.biz.entity.CustomerContractSectionPrice;
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
    int insert(CustomerContractSectionPrice record);

    /**
     * 新增关键字段
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerContractSectionPrice record);

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    CustomerContractSectionPrice selectByPrimaryKey(Integer selectionId);

    /**
     * 依据字段修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerContractSectionPrice record);

    /**
     * 修改全表
     *
     * @param record
     * @return
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