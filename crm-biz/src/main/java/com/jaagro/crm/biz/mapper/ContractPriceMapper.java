package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.PriceCriteriaDto;
import com.jaagro.crm.biz.entity.ContractPrice;
import com.jaagro.crm.api.dto.response.ContractPriceReturnDto;

import java.util.List;

/**
 * @author tony
 */
public interface ContractPriceMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增全表
     * @param record
     * @return
     */
    int insert(ContractPrice record);

    /**
     * 新增关键字段
     * @param record
     * @return
     */
    int insertSelective(ContractPrice record);

    /**
     * 获取单条
     * @param id
     * @return
     */
    ContractPrice selectByPrimaryKey(Long id);

    /**
     * 依据字段修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ContractPrice record);

    /**
     * 修改全表
     * @param record
     * @return
     */
    int updateByPrimaryKey(ContractPrice record);

    /**
     * 根据合同id 查出所有price记录
     *
     * @param contractId
     * @return
     */
    List<ContractPrice> listByContractId(Long contractId);

    /**
     * 根据合同id 查出所有priceDto记录
     *
     * @param contractId
     * @return
     */
    List<ContractPriceReturnDto> getByContractId(Long contractId);

    /**
     * 根据合同id删除price记录
     * @param contractId
     * @return
     */
    int deleteByContractId(Long contractId);

    /**
     * 依据条件dto查询price对象
     * @param dto
     * @return
     */
    ContractPrice getPriceByCriteria(PriceCriteriaDto dto);
}