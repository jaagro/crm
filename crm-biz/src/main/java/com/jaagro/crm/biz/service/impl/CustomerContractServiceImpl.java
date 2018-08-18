package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerContractCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerContractDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContractReturnDto;
import com.jaagro.crm.api.service.CustomerContractService;
import com.jaagro.crm.biz.entity.CustomerContract;
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
        CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(dto, customerContract);
        customerContract
                .setStatus(true);
        customerContractMapper.insert(customerContract);
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> createCustomerContract(List<CreateCustomerContractDto> dtos, Long CustomerId) {
        if (dtos != null && dtos.size() > 0) {
            for (CreateCustomerContractDto dto : dtos) {
                CustomerContract customerContract = new CustomerContract();
                BeanUtils.copyProperties(dto, customerContract);
                customerContract
                        .setStatus(true)
                        .setCustomerId(CustomerId);
                customerContractMapper.insert(customerContract);
            }
        }
        return ServiceResult.toResult("客户联系人创建成功");
    }

    @Override
    public Map<String, Object> updateCustomerContract(UpdateCustomerContractDto dto) {
        CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(dto, customerContract);
        customerContractMapper.updateByPrimaryKeySelective(customerContract);
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> updateCustomerContract(List<UpdateCustomerContractDto> dtos) {
        if (dtos != null && dtos.size() > 0) {
            for (UpdateCustomerContractDto dto : dtos) {
                CustomerContract customerContract = new CustomerContract();
                BeanUtils.copyProperties(dto, customerContract);
                customerContractMapper.updateByPrimaryKeySelective(customerContract);
            }
        }
        return ServiceResult.toResult("客户联系人修改成功");
    }

    @Override
    public Map<String, Object> getById(Long id) {
        return ServiceResult.toResult(this.customerContractMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerContractCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerContractReturnDto> contractReturnDtos = this.customerContractMapper.getByCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(contractReturnDtos));
    }

    @Override
    public Map<String, Object> disableCustomerContract(Long id) {
        CustomerContract customerContract = this.customerContractMapper.selectByPrimaryKey(id);
        customerContract.setStatus(false);
        return ServiceResult.toResult("客户联系人停用成功");
    }

    @Override
    public Map<String, Object> disableCustomerContract(List<CustomerContractReturnDto> customerContractReturnDtos) {
        if (customerContractReturnDtos != null && customerContractReturnDtos.size() > 0) {
            for (CustomerContractReturnDto contractReturnDto : customerContractReturnDtos) {
                CustomerContract customerContract = this.customerContractMapper.selectByPrimaryKey(contractReturnDto.getId());
                customerContract.setStatus(false);
                this.customerContractMapper.updateByPrimaryKeySelective(customerContract);
            }
        }
        return ServiceResult.toResult("客户联系人停用成功");
    }

}
