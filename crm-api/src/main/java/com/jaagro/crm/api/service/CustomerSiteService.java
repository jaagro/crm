package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.ListSiteCriteriaDto;

import java.util.Map;

/**
 * dto请参照customer
 * @author tony
 */
public interface CustomerSiteService {

    /**
     * 新增地点，注意新增CreateDto
     * @return
     */
    Map<String, Object> createSite();

    /**
     * 修改地点，注意新增updateDto
     * @return
     */
    Map<String, Object> updateSite();

    /**
     * 获取单条记录
     * @param id
     * @return
     */
    Map<String, Object> getById(Long id);

    /**
     * 根据条件分页获取
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListSiteCriteriaDto dto);

    /**
     * 删除地点，注意逻辑删除
     * @param id
     * @return
     */
    Map<String, Object> disableSite(Long id);
}
