package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.AccountType;
import com.jaagro.crm.api.constant.AccountUserType;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.CustomerCategory;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.api.dto.response.customer.ListCustomerDto;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
//@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "customer")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerSiteService siteService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerContactsService customerContactsService;
    @Autowired
    private QualificationCertificService qualificationCertificService;
    @Autowired
    private CustomerMapperExt customerMapper;
    @Autowired
    private CustomerContactsMapperExt customerContactsMapper;

    /**
     * 创建客户
     * 该api有图片上传的功能，图片删除用的是oss
     *
     * @param dto 注意customer对象的子对象的插入
     * @return
     */
//    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createCustomer(CreateCustomerDto dto) {
        //创建客户对象
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        customer
                .setCreateUserId(userService.getCurrentUser().getId());
        if (CustomerCategory.FARMS.equals(dto.getCustomerCategory())) {
            customer.setTenantId(userService.getCurrentUser().getTenantId());
        }
        if (StringUtils.isEmpty(customer.getCustomerType())) {
            throw new NullPointerException("客户类型不能为空");
        }
        customerMapper.insertSelective(customer);
        return ServiceResult.toResult(customer.getId());
    }

    /**
     * 修改客户
     * 该api有图片上传的功能，图片删除用的是oss
     *
     * @param dto 注意子对象的修改
     * @return
     */
    @Override
//    @CacheEvict(cacheNames = "customer", allEntries = true)
    public Map<String, Object> updateById(UpdateCustomerDto dto) {
        if (this.customerMapper.selectByPrimaryKey(dto.getId()) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不存在");
        }
        //修改客户表
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        if (customer != null) {
            customer
                    .setModifyTime(new Date())
                    .setModifyUserId(userService.getCurrentUser().getId());
            this.customerMapper.updateByPrimaryKeySelective(customer);
        }
        return ServiceResult.toResult("客户修改成功");
    }

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
//    @Cacheable
    @Override
    public Map<String, Object> getById(Integer id) {
        if (customerMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "此客户不存在");
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
//    @Cacheable
    public Map<String, Object> listByCriteria(ListCustomerCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        dto.setTenantId(userService.getCurrentUser().getTenantId());
        List<ListCustomerDto> listCustomerDtoList = this.customerMapper.listByCriteriaDto(dto);
        if (listCustomerDtoList != null && listCustomerDtoList.size() > 0) {
            for (ListCustomerDto customerDto : listCustomerDtoList) {
                List<CustomerContactsReturnDto> contractDtoList = this.customerContactsMapper.listByCustomerId(customerDto.getId());
                if (contractDtoList.size() > 0) {
                    CustomerContactsReturnDto contactsReturnDto = contractDtoList.get(0);
                    customerDto
                            .setContactName(contactsReturnDto.getContact())
                            .setPhone(contactsReturnDto.getPhone());
                }
            }
        }
        return ServiceResult.toResult(new PageInfo<>(listCustomerDtoList));
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
//    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> disableCustomer(Integer id) {
        if (this.customerMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到相应数据");
        }

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
                this.customerContactsService.disableCustomerContacts(customerDto.getCustomerContactsReturnDtos());
            }
            //逻辑删除 证件照
            if (customerDto.getQualifications() != null && customerDto.getQualifications().size() > 0) {
                this.qualificationCertificService.disableQualificationCertific(customerDto.getQualifications());
            }
            //逻辑删除 合同
            if (customerDto.getReturnContractDtos() != null && customerDto.getReturnContractDtos().size() > 0) {
                this.contractService.disableByID(customerDto.getReturnContractDtos());
            }
            //逻辑删除 账户 add by yj 20181028
            accountService.deleteAccount(id, AccountUserType.CUSTOMER, AccountType.CASH);
        }
        return ServiceResult.toResult("客户删除成功");
    }

    /**
     * 通过id获取客户显示对象，
     *
     * @param id
     * @return
     */
    @Override
//    @Cacheable
    public ShowCustomerDto getShowCustomerById(Integer id) {
        return customerMapper.getShowCustomerById(id);
    }

    //    @Cacheable
    @Override
    public List<ShowCustomerDto> listAllCustomer() {
        return customerMapper.getAllCustomer();
    }

    /**
     * 查询正常合作的全部客户
     *
     * @return
     */
    @Override
    public List<ShowCustomerDto> listNormalCustomer() {
        return customerMapper.listNormalCustomer();
    }

    /**
     * 根据客户名称查询客户id集合
     *
     * @param customerName
     * @return
     */
    @Override
    public List<Integer> listCustomerIdByName(String customerName) {
        return customerMapper.listCustomerIdByName(customerName);
    }

    /**
     * 根据当前登录人 查询养殖客户信息
     *
     * @return
     */
    @Override
    public List<ShowCustomerDto> listCustomerInfoByCurrentUser() {
        List<ShowCustomerDto> showCustomerDtos = new ArrayList<>();
        ListCustomerCriteriaDto listCustomerCriteriaDto = new ListCustomerCriteriaDto();
        UserInfo currentUser = userService.getCurrentUser();
        List<ListCustomerDto> listCustomerDtos = null;
        if (currentUser != null && currentUser.getTenantId() != null) {
            listCustomerCriteriaDto
                    .setCustomerStatus(AuditStatus.NORMAL_COOPERATION)
                    .setCustomerCategory(CustomerCategory.FARMS)
                    .setTenantId(currentUser.getTenantId());
            listCustomerDtos = customerMapper.listByCriteriaDto(listCustomerCriteriaDto);
        }
        if (!CollectionUtils.isEmpty(listCustomerDtos)) {
            for (ListCustomerDto listCustomerDto : listCustomerDtos) {
                ShowCustomerDto showCustomerDto = new ShowCustomerDto();
                BeanUtils.copyProperties(listCustomerDto, showCustomerDto);
                showCustomerDtos.add(showCustomerDto);
            }
        }
        return showCustomerDtos;
    }
}