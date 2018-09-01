package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContactsDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerContactsCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerContactsDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;
import com.jaagro.crm.api.service.CustomerContactsService;
import com.jaagro.crm.biz.entity.CustomerContacts;
import com.jaagro.crm.biz.mapper.CustomerContactsMapper;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class CustomerContactsServiceImpl implements CustomerContactsService {

    @Autowired
    private CustomerContactsMapper customerContactsMapper;

    @Override
    public Map<String, Object> createCustomerContacts(CreateCustomerContactsDto dto) {
        CustomerContacts customerContacts = new CustomerContacts();
        BeanUtils.copyProperties(dto, customerContacts);
        customerContacts
                .setEnabled(true);
        customerContactsMapper.insertSelective(customerContacts);
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> createCustomerContacts(List<CreateCustomerContactsDto> dtos, Integer CustomerId) {
        if (dtos != null && dtos.size() > 0) {
            for (CreateCustomerContactsDto dto : dtos) {
                CustomerContacts customerContacts = new CustomerContacts();
                BeanUtils.copyProperties(dto, customerContacts);
                customerContacts
                        .setEnabled(true);
                customerContactsMapper.insertSelective(customerContacts);
            }
        }
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> updateCustomerContacts(UpdateCustomerContactsDto dto) {
        CustomerContacts customerContacts = new CustomerContacts();
        BeanUtils.copyProperties(dto, customerContacts);
        customerContactsMapper.updateByPrimaryKeySelective(customerContacts);
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> updateCustomerContacts(List<UpdateCustomerContactsDto> dtos) {
        if (dtos != null && dtos.size() > 0) {
            for (UpdateCustomerContactsDto dto : dtos) {
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
    public Map<String, Object> listByCriteria(ListCustomerContactsCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerContactsReturnDto> contractReturnDtos = this.customerContactsMapper.getByCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(contractReturnDtos));
    }

    @Override
    public Map<String, Object> disableCustomerContacts(Integer id) {
        CustomerContacts customerContacts = this.customerContactsMapper.selectByPrimaryKey(id);
        customerContacts.setEnabled(false);
        return ServiceResult.toResult("客户联系人停用成功");
    }

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

}