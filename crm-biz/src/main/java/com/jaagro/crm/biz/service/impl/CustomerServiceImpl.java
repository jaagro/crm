package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.CustomerContractDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ServiceResult;

import java.util.Date;
import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerContractMapper customerContractMapper;

    @Autowired
    private CurrentUserService userService;

    @Autowired
    private CustomerSiteMapper customerSiteMapper;

    @Autowired
    private CustomerVerifyLogMapper customerVerifyLogMapper;

    @Autowired
    private QualificationCertificMapper qualificationCertificMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createCustomer(CreateCustomerDto dto) {
        //创建客户对象
        Customer customer=new Customer();
        BeanUtils.copyProperties(dto,customer);
        customer
                .setCreatedTime(new Date())
                .setCreatedUserId(userService.getCurrentUser().getId());
        customerMapper.insert(customer);
        //创建联系人对象
        if(dto.getContracts() != null && dto.getContracts().size() > 0){
            for(CustomerContractDto cc: dto.getContracts()){
                CustomerContract customerContract=new CustomerContract();
                BeanUtils.copyProperties(cc,customerContract);
                System.out.println(customerContract);
                customerContract.setCustomerId(customer.getId());
                customerContractMapper.insert(customerContract);
            }
        }
        return ServiceResult.toResult("客户创建成功");
    }
}
