package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractPriceCriteriaDto;
import com.jaagro.crm.biz.entity.CustomerContractPrice;
import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;

import java.util.List;

/**
 * @author tony
 */
public interface ContractPriceMapper {
    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增全表
     *
     * @param record
     * @return
     */
    int insert(CustomerContractPrice record);

    /**
     * 新增关键字段
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerContractPrice record);

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    CustomerContractPrice selectByPrimaryKey(Integer id);

    /**
     * 依据字段修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerContractPrice record);

    /**
     * 修改全表
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerContractPrice record);

    /**
     * 根据合同id 查出所有price记录
     *
     * @param contractId
     * @return
     */
    List<ReturnContractPriceDto> listByContractId(Integer contractId);

    /**
     * 根据合同id 查出所有priceDto记录
     *
     * @param contractId
     * @return
     */
    List<ReturnContractPriceDto> getByContractId(Integer contractId);

    /**
     * 根据合同id删除price记录
     *
     * @param contractId
     * @return
     */
    int deleteByContractId(Integer contractId);

    /**
     * 依据条件dto查询price对象
     *
     * @param dto
     * @return
     */
    ReturnContractPriceDto getPriceByCriteria(ListContractPriceCriteriaDto dto);
}