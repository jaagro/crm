package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListSiteCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerSiteDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.biz.entity.CustomerSite;

import java.util.List;

public interface CustomerSiteMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerSite selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerSite record);

    /**
     * 查询客户Id查询收发货地址
     *
     * @param dto
     * @return
     */
    List<CustomerSiteReturnDto> getByCustomerSiteId(Integer customer_id);

    /**
     * 查询客户Id查询收发货地址
     *
     * @param dto
     * @return
     */
    List<CustomerSiteReturnDto> getByCriteriDto(ListSiteCriteriaDto criteriaDto);

    /**
     * 根据地址名称查询
     *
     * @param siteName
     */
    CustomerSiteReturnDto getSiteDto(UpdateCustomerSiteDto siteDto);
}