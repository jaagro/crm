package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.biz.entity.Customer;

import java.util.List;

public interface CustomerMapper {
    /**
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2018-08-16
     */
    int insert(Customer record);

    /**
     * @mbggenerated 2018-08-16
     */
    int insertSelective(Customer record);

    /**
     * @mbggenerated 2018-08-16
     */
    Customer selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKey(Customer record);

    /**
     * 查询单个客户Dto
     *
     * @param id
     * @return
     */
    CustomerReturnDto getById(Long id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<CustomerReturnDto> getByCriteriDto(ListCustomerCriteriaDto dto);
}