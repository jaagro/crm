package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customerRegister.ListCustomerRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.customerRegister.UpdateCustomerRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.customerRegister.CustomerRegisterPurposeDto;

/**
 * 客户注册意向管理
 *
 * @author yj
 * @since 2018/12/13
 */
public interface CustomerRegisterPurposeService {
    /**
     * 根据手机号查询
     *
     * @param phoneNumber
     * @return
     */
    CustomerRegisterPurposeDto getByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号验证码创建客户注册意向
     *
     * @param phoneNumber
     * @param verificationCode
     * @return
     */
    Integer createCustomerRegisterByPhoneNumber(String phoneNumber, String verificationCode);

    /**
     * 根据id查询客户注册意向
     *
     * @param id
     * @return
     */
    CustomerRegisterPurposeDto getCustomerRegisterPurposeDtoById(Integer id);

    /**
     * 更新客户注册意向
     *
     * @param registerPurposeDto
     */
    void updateCustomerRegisterPurpose(UpdateCustomerRegisterPurposeDto registerPurposeDto);

    /**
     * 查询客户注册意向列表
     *
     * @param criteria
     * @return
     */
    PageInfo listCustomerRegisterPurposeByCriteria(ListCustomerRegisterPurposeCriteriaDto criteria);
}
