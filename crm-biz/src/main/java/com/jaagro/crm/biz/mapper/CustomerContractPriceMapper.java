package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractPriceCriteriaDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;
import com.jaagro.crm.biz.entity.CustomerContractPrice;

import java.util.List;

public interface CustomerContractPriceMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContractPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContractPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerContractPrice selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContractPrice record);

    /**
     *
     * @mbggenerated 2018-08-23
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