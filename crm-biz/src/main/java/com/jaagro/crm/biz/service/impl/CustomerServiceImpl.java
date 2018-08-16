package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.CertificateStatus;
import com.jaagro.crm.api.constant.ContractStatus;
import com.jaagro.crm.api.constant.CustomerStatus;
import com.jaagro.crm.api.constant.SiteStatus;
import com.jaagro.crm.api.dto.request.contract.ContractPriceDto;
import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import com.jaagro.crm.api.dto.request.customer.*;
import com.jaagro.crm.api.dto.response.contract.ContractSectionPriceDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.entity.*;
import com.jaagro.crm.biz.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractPriceMapper contractPriceMapper;
    @Autowired
    private ContractSectionPriceMapper contractSectionPriceMapper;

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
        System.err.println(userService.getCurrentUser().getId());
        customer
                .setCustomerStatus(CustomerStatus.UNCHECKED)
                .setEnable(false)
                .setCreateTime(new Date())
                .setCreatedUserId(userService.getCurrentUser().getId());
        customerMapper.insert(customer);
        //新增联系人对象
        if (dto.getContracts() != null && dto.getContracts().size() > 0) {
            for (CreateCustomerContractDto cc : dto.getContracts()) {
                CustomerContract customerContract = new CustomerContract();
                BeanUtils.copyProperties(cc, customerContract);
                customerContract
                        .setCustomerId(customer.getId())
                        .setStatus(ContractStatus.ACTIVE);
                customerContractMapper.insert(customerContract);
            }
        }

        //新增客户合同
        if (dto.getCreateContractDtos() != null && dto.getCreateContractDtos().size() > 0) {
            for (CreateContractDto contractDto : dto.getCreateContractDtos()) {
                //创建contract对象
                Contract contract = new Contract();
                BeanUtils.copyProperties(contractDto, contract);
                contract
                        .setCreateTime(new Date())
                        .setContractStatus(ContractStatus.ACTIVE)
                        .setCreateUser(userService.getCurrentUser().getId())
                        .setCustomerId(customer.getId());
                contractMapper.insert(contract);
                //创建contractPrice对象
                createPrice(contractDto, contract);
            }
        }

        //新增客户资质证件照
        if (dto.getQualificationCertificDtos() != null && dto.getQualificationCertificDtos().size() > 0) {
            for (CreateQualificationCertificDto certificDto : dto.getQualificationCertificDtos()) {
                QualificationCertific qc = new QualificationCertific();
                BeanUtils.copyProperties(certificDto, qc);
                qc
                        .setCustomerId(customer.getId())
                        .setEnabled(false)
                        .setCertificateStatus(CertificateStatus.UNCHECKED)
                        .setCreateUserId(userService.getCurrentUser().getId())
                        .setCreateTime(new Date());
                qualificationCertificMapper.insert(qc);
            }
        }

        //新增收发货地址
        if (dto.getCustomerSites() != null && dto.getCustomerSites().size() > 0) {
            for (CreateCustomerSiteDto siteDto : dto.getCustomerSites()) {
                CustomerSite site = new CustomerSite();
                BeanUtils.copyProperties(siteDto, site);
                site
                        .setCustomerId(customer.getId())
                        .setCreateTime(new Date())
                        .setSiteStatus(SiteStatus.ACTIVE)
                        .setCreateUserId(userService.getCurrentUser().getId());
                customerSiteMapper.insert(site);
            }
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
                for (CreateContractDto contractDto : dto.getCreateContractDtos()) {
                    //创建contract对象
                    Contract contract = new Contract();
                    BeanUtils.copyProperties(contractDto, contract);
                    contract
                            .setNewUpdateTime(new Date())
                            .setNewUpdateUser(userService.getCurrentUser().getId());
                }
            }
            //修改收发货地址
            if (dto.getCustomerSites() != null && dto.getCustomerSites().size() > 0) {
                for (CreateCustomerSiteDto siteDto : dto.getCustomerSites()) {
                    CustomerSite site = new CustomerSite();
                    BeanUtils.copyProperties(siteDto, site);
                    site
                            .setModifyTime(new Date())
                            .setModifyUserId(userService.getCurrentUser().getId());
                }
            }

            //修改资质证件照
            if (dto.getQualificationCertificDtos() != null && dto.getQualificationCertificDtos().size() > 0) {
                for (CreateQualificationCertificDto certificDto : dto.getQualificationCertificDtos()) {
                    QualificationCertific qc = new QualificationCertific();
                    BeanUtils.copyProperties(certificDto, qc);
                    qc
                            .setModifyTime(new Date())
                            .setModifyUserId(userService.getCurrentUser().getId());
                }
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
    public Map<String, Object> getById(Long id) {
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
        return null;
    }

    /**
     * 审核客户，注意需要修改的字段有哪些，插入的表有哪些
     *
     * @param id
     * @param auditResult
     * @return
     */
    @Override
    public Map<String, Object> auditCustomer(Long id, String auditResult) {
        return null;
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> disableCustomer(Long id) {
        CustomerReturnDto customerDto = customerMapper.getById(id);
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        if (customer != null) {
            customer.setCustomerStatus(CustomerStatus.STOP_COOPERATION);
            this.customerMapper.updateByPrimaryKeySelective(customer);
            //逻辑删除关联表
            if (customerDto.getSites() != null && customerDto.getSites().size() > 0) {

            }
        }
        return ServiceResult.toResult("客户删除成功");
    }

    public void createPrice(CreateContractDto dto, Contract contract) {
        //创建contractPrice对象
        if (dto.getPrice() != null && dto.getPrice().size() > 0) {
            for (ContractPriceDto cp : dto.getPrice()) {
                ContractPrice contractPrice = new ContractPrice();
                BeanUtils.copyProperties(cp, contractPrice);
                contractPrice.setContractId(contract.getId());
                if (StringUtils.isEmpty(contractPrice.getPricingType())) {
                    throw new RuntimeException("计价模式不能为空");
                }
                contractPriceMapper.insert(contractPrice);
                //创建contractSectionPrice对象
                if (cp.getSectionPrice() != null && cp.getSectionPrice().size() > 0) {
                    for (ContractSectionPriceDto cspDto : cp.getSectionPrice()) {
                        ContractSectionPrice csp = new ContractSectionPrice();
                        BeanUtils.copyProperties(cspDto, csp);
                        csp.setContractPriceId(contractPrice.getId());
                        contractSectionPriceMapper.insert(csp);
                    }
                }
            }
        }
    }
}
