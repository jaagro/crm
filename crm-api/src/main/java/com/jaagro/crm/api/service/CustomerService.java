package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;

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
     * 逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomer(Integer id);

    /**
     * 通过id获取客户显示对象，
     *
     * @param id
     * @return
     */
    ShowCustomerDto getShowCustomerById(Integer id);

    /**
     * 获得全部的客户
     *
     * @return
     */
    List<ShowCustomerDto> listAllCustomer();

    /**
     * 查询正常合作的全部客户
     *
     * @return
     */
    List<ShowCustomerDto> listNormalCustomer();

    /**
     * 根据客户名称查询客户id集合
     *
     * @param customerName
     * @return
     */
    List<Integer> listCustomerIdByName(String customerName);

    /**
     * 根据当前登录人 查询养殖客户信息
     *
     * @return
     */
    List<ShowCustomerDto> listCustomerInfoByCurrentUser();

    /**
     * 获取客户详细信息
     *
     * @param id
     * @return
     */
    CustomerReturnDto getCustomerDetail(Integer id);

    /**
     * 根据tenantId 获取客户列表
     *
     * @return
     */
    List<ShowCustomerDto> listCustomerByTenantId();
}
