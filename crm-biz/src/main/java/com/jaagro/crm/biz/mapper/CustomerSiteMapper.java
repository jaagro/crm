package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ContractReturnDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.biz.entity.CustomerSite;

import java.util.List;

public interface CustomerSiteMapper {
    /**
     *
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insert(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insertSelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    CustomerSite selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKey(CustomerSite record);

    /**
     * 查询客户Id查询收发货地址
     *
     * @param dto
     * @return
     */
    List<CustomerSiteReturnDto> getByCustomerSiteId(Long customer_id);
}