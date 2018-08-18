package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSiteDto;
import com.jaagro.crm.api.dto.request.customer.ListSiteCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerSiteDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;

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
    Map<String, Object> createSite(List<CreateCustomerSiteDto> customerSiteDtos, Long CustomerId);

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
    Map<String, Object> getById(Long id);

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
    Map<String, Object> disableSite(Long id);

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

}
