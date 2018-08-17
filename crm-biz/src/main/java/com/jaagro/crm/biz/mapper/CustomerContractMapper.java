package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerContractCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContractReturnDto;
import com.jaagro.crm.biz.entity.CustomerContract;

import java.util.List;

/**
 * @author baiyiran
 */
public interface CustomerContractMapper {
    /**
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2018-08-16
     */
    int insert(CustomerContract record);

    /**
     * @mbggenerated 2018-08-16
     */
    int insertSelective(CustomerContract record);

    /**
     * @mbggenerated 2018-08-16
     */
    CustomerContract selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKey(CustomerContract record);

    /**
     * 查询客户全部联系人
     *
     * @param id
     * @return
     */
    List<CustomerContractReturnDto> getByCustomerId(Long id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<CustomerContractReturnDto> getByCriteriDto(ListCustomerContractCriteriaDto dto);
}