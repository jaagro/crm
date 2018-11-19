package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
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
    Map<String, Object> getById(Integer id);

    /**
     * 分页获取list，注意criteria查询条件
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListCustomerCriteriaDto dto);

    /**
     * 分页获取list，注意criteria查询条件,forOrder
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteriaForOrder(ListCustomerCriteriaDto dto);

    /**
     * 1
     * 审核客户，注意需要修改的字段有哪些，插入的表有哪些
     *
     * @param id
     * @param auditResult
     * @return
     */
    Map<String, Object> auditCustomer(Integer id, String auditResult);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomer(Integer id);

    /**
     * 通过id获取客户显示对象，
     * @param id
     * @return
     */
    ShowCustomerDto getShowCustomerById(Integer id);

    /**
     * 获得全部的客户
     * @return
     */
    List<ShowCustomerDto> listAllCustomer();
}
