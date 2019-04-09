package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.base.CreateCustomerUserDto;
import com.jaagro.crm.api.dto.base.UpdateCustomerUserDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContactsDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerContactsCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerContactsDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;
import com.jaagro.crm.api.service.CustomerContactsService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContacts;
import com.jaagro.crm.biz.mapper.CustomerContactsMapperExt;
import com.jaagro.crm.biz.mapper.CustomerMapperExt;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@CacheConfig(keyGenerator = "wiselyKeyGenerator")
@Service
public class CustomerContactsServiceImpl implements CustomerContactsService {

    @Autowired
    private CustomerContactsMapperExt customerContactsMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerMapperExt customerMapper;
    @Autowired
    private UserClientService userClientService;


    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> createCustomerContacts(CreateCustomerContactsDto dto) {
        CustomerContacts customerContacts = new CustomerContacts();
        BeanUtils.copyProperties(dto, customerContacts);
        customerContacts
                .setEnabled(true)
                .setCreateUserId(this.userService.getCurrentUser().getId());
        customerContactsMapper.insertSelective(customerContacts);
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createCustomerContacts(List<CreateCustomerContactsDto> contactsDtoList, Integer customerId) {
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        if (contactsDtoList != null && contactsDtoList.size() > 0) {
            for (CreateCustomerContactsDto dto : contactsDtoList) {
                CustomerContacts customerContacts = new CustomerContacts();
                BeanUtils.copyProperties(dto, customerContacts);
                customerContacts
                        .setEnabled(true)
                        .setCreateUserId(this.userService.getCurrentUser().getId());
                customerContactsMapper.insertSelective(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> updateCustomerContacts(UpdateCustomerContactsDto dto) {
        CustomerContacts customerContacts = new CustomerContacts();
        BeanUtils.copyProperties(dto, customerContacts);
        customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> updateCustomerContacts(List<UpdateCustomerContactsDto> contractDtos) {
        if (contractDtos != null && contractDtos.size() > 0) {
            //删除联系人
            this.customerContactsMapper.deleteByCustomerId(contractDtos.get(0).getCustomerId());
            for (UpdateCustomerContactsDto contractDto : contractDtos) {
                if (StringUtils.isEmpty(contractDto.getCustomerId())) {
                    return ServiceResult.error("联系人客户id不能为空");
                }
                Customer customer = this.customerMapper.selectByPrimaryKey(contractDto.getCustomerId());
                if (customer == null) {
                    return ServiceResult.error("客户不存在");
                }
                if (StringUtils.isEmpty(contractDto.getPhone())) {
                    return ServiceResult.error("联系人电话不能为空");
                }
                if (StringUtils.isEmpty(contractDto.getContact())) {
                    return ServiceResult.error("联系人姓名不能为空");
                }
                CustomerContacts customerContacts = new CustomerContacts();
                BeanUtils.copyProperties(contractDto, customerContacts);
                customerContacts
                        .setCreateUserId(userService.getCurrentUser().getId());
                customerContactsMapper.insertSelective(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人修改成功");
    }


    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateCustomerUserContacts(List<UpdateCustomerContactsDto> contactsDtoList) {
        if (contactsDtoList != null && contactsDtoList.size() > 0) {
            for (UpdateCustomerContactsDto contractDto : contactsDtoList) {
                Customer customer = this.customerMapper.selectByPrimaryKey(contractDto.getCustomerId());
                if (customer == null) {
                    return ServiceResult.error("客户不存在");
                }
                if (StringUtils.isEmpty(contractDto.getId())) {
                    if (StringUtils.isEmpty(contractDto.getPhone())) {
                        return ServiceResult.error("联系人电话不能为空");
                    }
                    if (StringUtils.isEmpty(contractDto.getContact())) {
                        return ServiceResult.error("联系人姓名不能为空");
                    }
                    //新增
                    CustomerContacts customerContacts = new CustomerContacts();
                    BeanUtils.copyProperties(contractDto, customerContacts);
                    customerContacts
                            .setEnabled(true)
                            .setCreateUserId(this.userService.getCurrentUser().getId());
                    customerContactsMapper.insertSelective(customerContacts);
                    // 养殖客户
                    if (customer.getTenantId().equals(2)) {
                        CreateCustomerUserDto customerUserDto = new CreateCustomerUserDto();
                        customerUserDto
                                .setCustomerType(20)
                                .setStandbyId(customerContacts.getId())
                                .setRelevanceId(customer.getId())
                                .setName(customerContacts.getContact())
                                .setLoginName(customerContacts.getPhone())
                                .setPhoneNumber(customerContacts.getPhone())
                                .setCreateUserId(userService.getCurrentUser().getId())
                                .setTenantId(userService.getCurrentUser().getTenantId());
                        //新增登录账号
                        BaseResponse baseResponse = userClientService.createCustomerUser(customerUserDto);
                        if (baseResponse.getStatusCode() != 200) {
                            throw new RuntimeException(baseResponse.getStatusMsg());
                        }
                    }
                } else {
                    //修改
                    CustomerContacts customerContacts = new CustomerContacts();
                    BeanUtils.copyProperties(contractDto, customerContacts);
                    customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
                    // 养殖客户
                    if (customer.getTenantId().equals(2)) {
                        //修改登录账号
                        UpdateCustomerUserDto updateCustomerUserDto = new UpdateCustomerUserDto();
                        updateCustomerUserDto
                                .setName(contractDto.getContact())
                                .setPhoneNumber(contractDto.getPhone())
                                .setLoginName(contractDto.getPhone())
                                .setStandbyId(contractDto.getId());
                        //修改登录账号
                        BaseResponse baseResponse = userClientService.updateCustomerUser(updateCustomerUserDto);
                        if (baseResponse.getStatusCode() != 200) {
                            throw new RuntimeException(baseResponse.getStatusMsg());
                        }
                    }
                }
            }
        }
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(this.customerContactsMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerContactsCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerContactsReturnDto> contractReturnDtos = this.customerContactsMapper.getByCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(contractReturnDtos));
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> disableCustomerContacts(Integer id) {
        if (this.customerContactsMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到相应数据");
        }
        CustomerContacts customerContacts = this.customerContactsMapper.selectByPrimaryKey(id);
        customerContacts.setEnabled(false);
        customerContactsMapper.updateByPrimaryKeySelective(customerContacts);

        //删除 登录账号
        userClientService.deleteByStandbyId(customerContacts.getId());
        return ServiceResult.toResult("客户联系人停用成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> disableCustomerContacts(List<CustomerContactsReturnDto> customerContactsReturnDtos) {
        if (customerContactsReturnDtos != null && customerContactsReturnDtos.size() > 0) {
            for (CustomerContactsReturnDto contractReturnDto : customerContactsReturnDtos) {
                CustomerContacts customerContacts = this.customerContactsMapper.selectByPrimaryKey(contractReturnDto.getId());
                customerContacts.setEnabled(false);
                this.customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人停用成功");
    }

    @Override
    public Map<String, Object> listByCustomerId(Integer customerId) {
        return ServiceResult.toResult(this.customerContactsMapper.listByCustomerId(customerId));
    }

    /**
     * 获取显示客户合同
     *
     * @param id
     * @return
     */
    @Override
    public ShowCustomerContractDto getCustomerContactsById(Integer id) {
        return null;
    }

    /**
     * 根据关键词查询客户Id集合
     *
     * @param keyword
     * @return
     */
    @Override
    public List<Integer> listCustomerIdByKeyWord(String keyword) {
        return customerContactsMapper.listCustomerIdByKeyWord(keyword);
    }
}
