package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.CustomerStatus;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.api.dto.response.customer.ListCustomerDto;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerSiteService siteService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private QualificationCertificService certificService;
    @Autowired
    private CustomerContractService customerContractService;

    /**
     * 创建客户
     * 该api有图片上传的功能，图片删除用的是oss
     *
     * @param dto 注意customer对象的子对象的插入
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createCustomer(CreateCustomerDto dto) {
        //创建客户对象
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        customer
                .setCustomerStatus(CustomerStatus.UNCHECKED)
                .setCreateTime(new Date())
                .setCreateUserId(userService.getCurrentUser().getId());
        if (StringUtils.isEmpty(customer.getCustomerType())) {
            throw new RuntimeException("客户类型不能为空");
        }
        customerMapper.insert(customer);
        //新增联系人对象
        if (dto.getContracts() != null && dto.getContracts().size() > 0) {
            this.customerContractService.createCustomerContract(dto.getContracts(), customer.getId());
        }

        //新增客户合同
        if (dto.getCreateContractDtos() != null && dto.getCreateContractDtos().size() > 0) {
            this.contractService.createContract(dto.getCreateContractDtos(), customer.getId());
        }

        //新增客户资质证件照
        if (dto.getQualificationCertificDtos() != null && dto.getQualificationCertificDtos().size() > 0) {
            certificService.createQualificationCertific(dto.getQualificationCertificDtos(), customer.getId());
        }

        //新增收发货地址
        if (dto.getCustomerSites() != null && dto.getCustomerSites().size() > 0) {
            siteService.createSite(dto.getCustomerSites(), customer.getId());
        }
        return ServiceResult.toResult("客户创建成功");
    }

    /**
     * 修改客户
     * 该api有图片上传的功能，图片删除用的是oss
     *
     * @param dto 注意子对象的修改
     * @return
     */
    @Override
    public Map<String, Object> updateById(UpdateCustomerDto dto) {
        //修改客户表
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        if (customer != null) {
            customer
                    .setModifyTime(new Date())
                    .setModifyUserId(userService.getCurrentUser().getId());
            this.customerMapper.updateByPrimaryKeySelective(customer);

            //修改合同表
            if (dto.getCreateContractDtos() != null && dto.getCreateContractDtos().size() > 0) {
                this.contractService.updateContract(dto.getCreateContractDtos());
            }
            //修改收发货地址
            if (dto.getCustomerSites() != null && dto.getCustomerSites().size() > 0) {
                this.siteService.updateSite(dto.getCustomerSites());
            }

            //修改资质证件照
            if (dto.getQualificationCertificDtos() != null && dto.getQualificationCertificDtos().size() > 0) {
                this.certificService.updateQualificationCertific(dto.getQualificationCertificDtos());
            }

            //修改联系人
            if (dto.getCreateContractDtos() != null && dto.getCreateContractDtos().size() > 0) {
                this.customerContractService.updateCustomerContract(dto.getCustomerContractDtos());
            }
        }
        return ServiceResult.toResult("客户修改成功");
    }

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer id) {
        if (customerMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "id: " + id + "不存在");
        }
        return ServiceResult.toResult(customerMapper.getById(id));
    }

    /**
     * 分页获取list，注意criteria查询条件
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> listByCriteria(ListCustomerCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<ListCustomerDto> customerReturnDtos = this.customerMapper.getByCriteriaDto(dto);
        return ServiceResult.toResult(new PageInfo<>(customerReturnDtos));

    }

    /**
     * 审核客户，注意需要修改的字段有哪些，插入的表有哪些
     *
     * @param id
     * @param auditResult
     * @return
     */
    @Override
    public Map<String, Object> auditCustomer(Integer id, String auditResult) {
        return null;
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> disableCustomer(Integer id) {
        CustomerReturnDto customerDto = customerMapper.getById(id);
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        if (customer != null) {
            //逻辑删除 客户
            customer.setCustomerStatus(13);
            this.customerMapper.updateByPrimaryKeySelective(customer);
            //逻辑删除 地址
            if (customerDto.getSites() != null && customerDto.getSites().size() > 0) {
                this.siteService.disableSite(customerDto.getSites());
            }
            //逻辑删除 联系人
            if (customerDto.getCustomerContactsReturnDtos() != null && customerDto.getCustomerContactsReturnDtos().size() > 0) {
                this.customerContractService.disableCustomerContract(customerDto.getCustomerContactsReturnDtos());
            }
            //逻辑删除 证件照
            if (customerDto.getQualifications() != null && customerDto.getQualifications().size() > 0) {
                this.certificService.disableQualificationCertific(customerDto.getQualifications());
            }
            //逻辑删除 合同
            if (customerDto.getReturnContractDtos() != null && customerDto.getReturnContractDtos().size() > 0) {
                this.contractService.disableByID(customerDto.getReturnContractDtos());
            }
        }
        return ServiceResult.toResult("客户删除成功");
    }
}