package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.biz.entity.CustomerSite;

import java.util.List;

/**
 * @author baiyiran
 */
public interface CustomerSiteMapper {
    /**
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(CustomerSite record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerSite record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    CustomerSite selectByPrimaryKey(Long id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     * 主键更新
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