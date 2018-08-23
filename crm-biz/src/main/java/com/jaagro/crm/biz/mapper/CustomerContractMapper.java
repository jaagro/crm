package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerContractCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContractReturnDto;
import com.jaagro.crm.biz.entity.CustomerContracts;

import java.util.List;

/**
 * @author baiyiran
 */
public interface CustomerContractMapper {
    /**
     *
     * @mbggenerated 2018-08-22
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int insert(CustomerContracts record);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int insertSelective(CustomerContracts record);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    CustomerContracts selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKeySelective(CustomerContracts record);

    /**
     *
     * @mbggenerated 2018-08-22
     */
    int updateByPrimaryKey(CustomerContracts record);

    /**
     * 查询客户全部联系人
     *
     * @param id
     * @return
     */
    List<CustomerContractReturnDto> getByCustomerId(Integer id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<CustomerContractReturnDto> getByCriteriDto(ListCustomerContractCriteriaDto dto);
}