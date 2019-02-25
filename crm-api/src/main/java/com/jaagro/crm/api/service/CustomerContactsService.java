package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.*;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContactsDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;

import java.util.List;
import java.util.Map;

/**
 * dto请参照customer
 *
 * @author baiyiran
 */
public interface CustomerContactsService {

    /**
     * 新增单个客户联系人，注意新增CreateDto
     *
     * @param dto
     * @return
     */
    Map<String, Object> createCustomerContacts(CreateCustomerContactsDto dto);

    /**
     * 新增客户联系人列表
     *
     * @param dtos
     * @return
     */
    Map<String, Object> createCustomerContacts(List<CreateCustomerContactsDto> dtos, Integer CustomerId);

    /**
     * 修改单个客户联系人，注意新增updateDto
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateCustomerContacts(UpdateCustomerContactsDto dto);

    /**
     * 修改客户联系人列表
     *
     * @param dtos
     * @return
     */
    Map<String, Object> updateCustomerContacts(List<UpdateCustomerContactsDto> dtos);

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
    Map<String, Object> listByCriteria(ListCustomerContactsCriteriaDto dto);

    /**
     * 删除客户联系人，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomerContacts(Integer id);

    /**
     * 删除客户联系人，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomerContacts(List<CustomerContactsReturnDto> customerContactsReturnDtos);

    /**
     * 根据客户id查询
     *
     * @param customerId
     * @return
     */
    Map<String, Object> listByCustomerId(Integer customerId);

    /**
     * 获取显示客户合同
     *
     * @param id
     * @return
     */
    ShowCustomerContractDto getCustomerContactsById(Integer id);

    /**
     * 根据关键词查询客户Id集合
     * @param keyword
     * @return
     */
    List<Integer> listCustomerIdByKeyWord(String keyword);
}
