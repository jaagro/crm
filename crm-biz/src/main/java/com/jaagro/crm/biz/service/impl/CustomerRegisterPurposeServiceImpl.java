package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.CustomerUserType;
import com.jaagro.crm.api.constant.UserType;
import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.api.dto.request.customerRegister.ListCustomerRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.customerRegister.UpdateCustomerRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.customerRegister.CustomerRegisterPurposeDto;
import com.jaagro.crm.api.service.CustomerRegisterPurposeService;
import com.jaagro.crm.biz.entity.CustomerRegisterPurpose;
import com.jaagro.crm.biz.mapper.CustomerRegisterPurposeMapperExt;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.crm.biz.service.VerificationCodeClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户注册意向管理
 *
 * @author yj
 * @since 2018/12/13
 */
@Service
@Slf4j
public class CustomerRegisterPurposeServiceImpl implements CustomerRegisterPurposeService {
    @Autowired
    private CustomerRegisterPurposeMapperExt customerRegisterPurposeMapperExt;
    @Autowired
    private VerificationCodeClientService verificationCodeClientService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 根据手机号查询
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public CustomerRegisterPurposeDto getByPhoneNumber(String phoneNumber) {
        CustomerRegisterPurpose registerPurpose = customerRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        return convertToDto(registerPurpose);
    }

    /**
     * 根据手机号验证码创建客户注册意向
     *
     * @param phoneNumber
     * @param verificationCode
     * @return
     */
    @Override
    public Map<String,Object> createCustomerRegisterByPhoneNumber(String phoneNumber, String verificationCode) {
        boolean existMessage = verificationCodeClientService.existMessage(phoneNumber, verificationCode);
        if (!existMessage) {
            throw new RuntimeException("验证码不正确");
        }
        Map<String,Object> result = new HashMap<>(3);
        result.put(ServiceKey.success.name(),Boolean.FALSE);
        CustomerRegisterPurpose customerRegisterPurpose = customerRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (customerRegisterPurpose != null) {
            if (customerRegisterPurpose.getUploadFlag() != null && customerRegisterPurpose.getUploadFlag()){
                result.put("userType", UserType.VISITOR_CUSTOMER_U);
                result.put(ServiceKey.msg.name(),"该手机号已填写基本信息");
            }else {
                result.put("userType", UserType.VISITOR_CUSTOMER_P);
                result.put(ServiceKey.msg.name(),"该手机号已注册");
            }
            return result;
        }
        BaseResponse<GetCustomerUserDto> response = userClientService.getCustomerUserByPhoneNumber(phoneNumber);
        if (ResponseStatusCode.OPERATION_SUCCESS.getCode() == response.getStatusCode()) {
            GetCustomerUserDto customerUserDto = response.getData();
            if (customerUserDto != null) {
                if (CustomerUserType.LOAD_SITE.equals(customerUserDto.getCustomerType())){
                    result.put("userType",UserType.LOAD_SITE);
                    result.put(ServiceKey.msg.name(),"该手机号已注册为装货地客户,请直接登录");
                }else if (CustomerUserType.UNLOAD_SITE.equals(customerUserDto.getCustomerType())){
                    result.put("userType",UserType.UNLOAD_SITE);
                    result.put(ServiceKey.msg.name(),"该手机号已注册为卸货地客户,请直接登录");
                }else {
                    result.put("userType", UserType.CUSTOMER);
                    result.put(ServiceKey.msg.name(),"该手机号已注册为正式客户,请直接登录");
                }
                return result;
            }
        }
        int nextUserId = userClientService.getNextUserId();
        customerRegisterPurpose = new CustomerRegisterPurpose();
        customerRegisterPurpose.setPhoneNumber(phoneNumber)
                .setCreateTime(new Date())
                .setId(nextUserId);
        customerRegisterPurposeMapperExt.insertSelective(customerRegisterPurpose);
        result.put("userType","");
        result.put(ServiceKey.success.name(),Boolean.TRUE);
        return result;
    }

    /**
     * 根据id查询客户注册意向
     *
     * @param id
     * @return
     */
    @Override
    public CustomerRegisterPurposeDto getCustomerRegisterPurposeDtoById(Integer id) {
        CustomerRegisterPurpose customerRegisterPurpose = customerRegisterPurposeMapperExt.selectByPrimaryKey(id);
        return convertToDto(customerRegisterPurpose);
    }

    /**
     * 更新客户注册意向
     *
     * @param registerPurposeDto
     */
    @Override
    public void updateCustomerRegisterPurpose(UpdateCustomerRegisterPurposeDto registerPurposeDto) {
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("未登录");
        }
        registerPurposeDto.setId(currentUser.getId());
        CustomerRegisterPurpose customerRegisterPurpose = customerRegisterPurposeMapperExt.selectByPrimaryKey(currentUser.getId());
        if (customerRegisterPurpose == null) {
            throw new RuntimeException("id不存在");
        }
        BeanUtils.copyProperties(registerPurposeDto, customerRegisterPurpose);
        customerRegisterPurpose.setUploadTime(new Date());
        customerRegisterPurpose.setUploadFlag(Boolean.TRUE);
        customerRegisterPurposeMapperExt.updateByPrimaryKeySelective(customerRegisterPurpose);
    }

    /**
     * 查询客户注册意向列表
     *
     * @param criteria
     * @return
     */
    @Override
    public PageInfo listCustomerRegisterPurposeByCriteria(ListCustomerRegisterPurposeCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        List<CustomerRegisterPurposeDto> dtoList = customerRegisterPurposeMapperExt.listByCriteria(criteria);
        return new PageInfo(dtoList);
    }

    private CustomerRegisterPurposeDto convertToDto(CustomerRegisterPurpose customerRegisterPurpose) {
        if (customerRegisterPurpose != null) {
            CustomerRegisterPurposeDto crpDto = new CustomerRegisterPurposeDto();
            BeanUtils.copyProperties(customerRegisterPurpose, crpDto);
            if (customerRegisterPurpose.getUploadTime() == null) {
                crpDto.setUploadTime(customerRegisterPurpose.getCreateTime());
            }
            return crpDto;
        }
        return null;
    }
}
