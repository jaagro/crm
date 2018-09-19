package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractPriceCriteriaDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;

import java.util.List;

/**
 * @author gavin
 */
public interface CustomerContractPriceMapperExt extends CustomerContractPriceMapper{

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