package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ResponseStatusCode;
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
                .setCreateTime(new Date())
                .setCreatedUserId(userService.getCurrentUser().getId());
        customerMapper.insert(customer);
        //创建联系人对象
        if(dto.getContracts() != null && dto.getContracts().size() > 0){
            for(CreateCustomerContractDto cc: dto.getContracts()){
                CustomerContract customerContract=new CustomerContract();
                BeanUtils.copyProperties(cc,customerContract);
                System.out.println(customerContract);
                customerContract.setCustomerId(customer.getId());
                customerContractMapper.insert(customerContract);
            }
        }
        return ServiceResult.toResult("客户创建成功");
    }

    @Override
    public Map<String, Object> updateById(UpdateCustomerDto dto) {
        return null;
    }

    /**
     * 查询单个客户
     *
     * @param customerId
     * @return
     */
    @Override
    public Map<String, Object> getById(Long id) {
        if (customerMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "id: " + id + "不存在");
        }
        return ServiceResult.toResult(customerMapper.getById(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListCustomerCriteriaDto dto) {
        return null;
    }

    @Override
    public Map<String, Object> auditCustomer(Long id, String auditResult) {
        return null;
    }

    @Override
    public Map<String, Object> disableCustomer(Long id) {
        return null;
    }
}
