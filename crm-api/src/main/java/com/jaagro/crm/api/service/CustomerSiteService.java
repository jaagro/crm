package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSiteDto;
import com.jaagro.crm.api.dto.request.customer.ListSiteCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowSiteDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerSiteDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.api.dto.response.selectValue.ReturnSelectSiteDto;

import java.util.List;
import java.util.Map;

/**
 * dto请参照customer
 *
 * @author tony
 */
public interface CustomerSiteService {

    /**
     * 新增单个地点，注意新增CreateDto
     *
     * @param customerSiteDto
     * @return
     */
    Map<String, Object> createSite(CreateCustomerSiteDto customerSiteDto);

    /**
     * 新增地点列表
     *
     * @param customerSiteDtos
     * @return
     */
    Map<String, Object> createSite(List<CreateCustomerSiteDto> customerSiteDtos, Integer CustomerId);

    /**
     * 修改单个地点，注意新增updateDto
     *
     * @return
     */
    Map<String, Object> updateSite(UpdateCustomerSiteDto customerSiteDto);

    /**
     * 修改地点列表
     *
     * @param customerSiteDtos
     * @return
     */
    Map<String, Object> updateSite(List<UpdateCustomerSiteDto> customerSiteDtos);

    /**
     * 获取单条记录
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Integer id);

    /**
     * 根据条件分页获取
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListSiteCriteriaDto dto);

    /**
     * 删除地点，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableSite(Integer id);

    /**
     * 删除地点，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableSite(List<CustomerSiteReturnDto> siteReturnDtos);

    /**
     * 验证名称是否重复
     *
     * @param id
     * @return
     */
    Map<String, Object> getBySiteDto(UpdateCustomerSiteDto siteDto);

    /**
     * 获取显示地址对象
     *
     * @param id
     * @return
     */
    ShowSiteDto getShowSiteById(Integer id);

    /**
     * 根据地址名称查询
     *
     * @param siteName
     * @param customerId
     * @return
     */
    ShowSiteDto getBySiteName(String siteName, Integer customerId);

    /**
     * 根据客户查询收发货地址
     *
     * @param criteriaDto
     * @return
     */
    List<ReturnSelectSiteDto> listSiteForSelectByCustomerId(ListSiteCriteriaDto criteriaDto);

    /**
     * 根据地址id数组获得地址名称
     *
     * @param siteIds
     * @return
     */
    List<String> listSiteNameByIds(List<Integer> siteIds);
}
