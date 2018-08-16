package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface CustomerService {
    /**
     * 创建客户
     * 该api有图片上传的功能，图片删除用的是oss
     *
     * @param dto 注意customer对象的子对象的插入
     * @return
     */
    Map<String, Object> createCustomer(CreateCustomerDto dto);

    /**
     * 修改客户
     * 该api有图片上传的功能，图片删除用的是oss
     *
     * @param dto 注意子对象的修改
     * @return
     */
    Map<String, Object> updateById(UpdateCustomerDto dto);

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Long id);

    /**
     * 分页获取list，注意criteria查询条件
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListCustomerCriteriaDto dto);

    /**
     * 审核客户，注意需要修改的字段有哪些，插入的表有哪些
     *
     * @param id
     * @param auditResult
     * @return
     */
    Map<String, Object> auditCustomer(Long id, String auditResult);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomer(Long id);
}
