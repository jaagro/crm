package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListSiteCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowSiteDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerSiteDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.api.dto.response.selectValue.ReturnSelectSiteDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface CustomerSiteMapperExt extends CustomerSiteMapper {

    /**
     * 查询客户Id查询收发货地址
     *
     * @param customer_id
     * @return
     */
    List<CustomerSiteReturnDto> getByCustomerSiteId(Integer customer_id);

    /**
     * 查询客户Id查询收发货地址
     *
     * @param criteriaDto
     * @return
     */
    List<CustomerSiteReturnDto> getByCriteriDto(ListSiteCriteriaDto criteriaDto);

    /**
     * 根据地址名称查询
     *
     * @param siteDto
     */
    CustomerSiteReturnDto getSiteDto(UpdateCustomerSiteDto siteDto);

    /**
     * 查询客户Id查询收发货地址
     *
     * @param criteriaDto
     * @return
     */
    List<ReturnSelectSiteDto> listAllSite(ListSiteCriteriaDto criteriaDto);

    /**
     * 获取所有的装卸货地，用于更新他们的经纬度
     *
     * @return
     * @Athor Gavin
     * 20181213
     */
    List<CustomerSiteReturnDto> listAllCustomerSite();

    /**
     * 获取显示地址对象
     *
     * @param id
     * @return
     */
    ShowSiteDto getShowSiteById(@Param("id") Integer id);

    /**
     * 根据地址查询
     *
     * @param siteName
     * @param customerId
     * @return
     */
    ShowSiteDto getBySiteName(@Param("siteName") String siteName, @Param("customerId") Integer customerId);

    /**
     * 根据地址id数组获得地址名称
     *
     * @param siteIds
     * @return
     */
    List<String> listSiteNameByIds(@Param("siteIds") List<Integer> siteIds);
}