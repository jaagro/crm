package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerContractCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerContractDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContractReturnDto;
import com.jaagro.crm.api.service.CustomerContractService;
import com.jaagro.crm.biz.entity.CustomerContacts;
import com.jaagro.crm.biz.mapper.CustomerContactsMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class CustomerContractServiceImpl implements CustomerContractService {

    @Autowired
    private CustomerContactsMapper customerContactsMapper;

    @Override
    public Map<String, Object> createCustomerContract(CreateCustomerContractDto dto) {
        CustomerContacts customerContacts = new CustomerContacts();
        BeanUtils.copyProperties(dto, customerContacts);
        customerContacts
                .setEnabled(true);
        customerContactsMapper.insert(customerContacts);
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> createCustomerContract(List<CreateCustomerContractDto> dtos, Integer CustomerId) {
        if (dtos != null && dtos.size() > 0) {
            for (CreateCustomerContractDto dto : dtos) {
                CustomerContacts customerContacts = new CustomerContacts();
                BeanUtils.copyProperties(dto, customerContacts);
                customerContacts
                        .setEnabled(true)
                        .setCustomerId(CustomerId);
                customerContactsMapper.insert(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> updateCustomerContract(UpdateCustomerContractDto dto) {
        CustomerContacts customerContacts = new CustomerContacts();
        BeanUtils.copyProperties(dto, customerContacts);
        customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> updateCustomerContract(List<UpdateCustomerContractDto> dtos) {
        if (dtos != null && dtos.size() > 0) {
            for (UpdateCustomerContractDto dto : dtos) {
                CustomerContacts customerContacts = new CustomerContacts();
                BeanUtils.copyProperties(dto, customerContacts);
                customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(this.customerContactsMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerContractCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerContractReturnDto> contractReturnDtos = this.customerContactsMapper.getByCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(contractReturnDtos));
    }

    @Override
    public Map<String, Object> disableCustomerContract(Integer id) {
        CustomerContacts customerContacts = this.customerContactsMapper.selectByPrimaryKey(id);
        customerContacts.setEnabled(false);
        return ServiceResult.toResult("客户联系人停用成功");
    }

    @Override
    public Map<String, Object> disableCustomerContract(List<CustomerContractReturnDto> customerContractReturnDtos) {
        if (customerContractReturnDtos != null && customerContractReturnDtos.size() > 0) {
            for (CustomerContractReturnDto contractReturnDto : customerContractReturnDtos) {
                CustomerContacts customerContacts = this.customerContactsMapper.selectByPrimaryKey(contractReturnDto.getId());
                customerContacts.setEnabled(false);
                this.customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人停用成功");
    }

}
