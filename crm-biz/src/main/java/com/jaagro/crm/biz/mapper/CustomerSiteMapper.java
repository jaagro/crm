package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListSiteCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowSiteDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerSiteDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.api.dto.response.selectValue.ReturnSelectSiteDto;
import com.jaagro.crm.biz.entity.CustomerSite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerSiteMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerSite record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerSite record);

    /**
     * @mbggenerated 2018-08-23
     */
    CustomerSite selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerSite record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerSite record);

}