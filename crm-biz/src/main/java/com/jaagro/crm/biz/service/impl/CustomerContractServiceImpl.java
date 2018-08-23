package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerContractCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerContractDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContractReturnDto;
import com.jaagro.crm.api.service.CustomerContractService;
import com.jaagro.crm.biz.entity.CustomerContracts;
import com.jaagro.crm.biz.mapper.CustomerContractMapper;
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
    private CustomerContractMapper customerContractMapper;

    @Override
    public Map<String, Object> createCustomerContract(CreateCustomerContractDto dto) {
        CustomerContracts customerContracts = new CustomerContracts();
        BeanUtils.copyProperties(dto, customerContracts);
        customerContracts
                .setEnabled(true);
        customerContractMapper.insert(customerContracts);
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> createCustomerContract(List<CreateCustomerContractDto> dtos, Integer CustomerId) {
        if (dtos != null && dtos.size() > 0) {
            for (CreateCustomerContractDto dto : dtos) {
                CustomerContracts customerContracts = new CustomerContracts();
                BeanUtils.copyProperties(dto, customerContracts);
                customerContracts
                        .setEnabled(true)
                        .setCustomerId(CustomerId);
                customerContractMapper.insert(customerContracts);
            }
        }
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> updateCustomerContract(UpdateCustomerContractDto dto) {
        CustomerContracts customerContracts = new CustomerContracts();
        BeanUtils.copyProperties(dto, customerContracts);
        customerContractMapper.updateByPrimaryKeySelective(customerContracts);
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> updateCustomerContract(List<UpdateCustomerContractDto> dtos) {
        if (dtos != null && dtos.size() > 0) {
            for (UpdateCustomerContractDto dto : dtos) {
                CustomerContracts customerContracts = new CustomerContracts();
                BeanUtils.copyProperties(dto, customerContracts);
                customerContractMapper.updateByPrimaryKeySelective(customerContracts);
            }
        }
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(this.customerContractMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerContractCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerContractReturnDto> contractReturnDtos = this.customerContractMapper.getByCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(contractReturnDtos));
    }

    @Override
    public Map<String, Object> disableCustomerContract(Integer id) {
        CustomerContracts customerContracts = this.customerContractMapper.selectByPrimaryKey(id);
        customerContracts.setEnabled(false);
        return ServiceResult.toResult("客户联系人停用成功");
    }

    @Override
    public Map<String, Object> disableCustomerContract(List<CustomerContractReturnDto> customerContractReturnDtos) {
        if (customerContractReturnDtos != null && customerContractReturnDtos.size() > 0) {
            for (CustomerContractReturnDto contractReturnDto : customerContractReturnDtos) {
                CustomerContracts customerContracts = this.customerContractMapper.selectByPrimaryKey(contractReturnDto.getId());
                customerContracts.setEnabled(false);
                this.customerContractMapper.updateByPrimaryKeySelective(customerContracts);
            }
        }
        return ServiceResult.toResult("客户联系人停用成功");
    }

}
