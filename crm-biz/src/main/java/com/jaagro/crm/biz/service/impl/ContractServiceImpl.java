package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ContractType;
import com.jaagro.crm.api.constant.CustomerCategory;
import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettlePriceDto;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.entity.TruckType;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户合同service
 *
 * @author tony
 */
@CacheConfig(keyGenerator = "wiselyKeyGenerator")
@Service
public class ContractServiceImpl implements ContractService {

    private static final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    private CustomerContractMapperExt customerContractMapper;
    @Autowired
    private CustomerContractLogMapperExt customerContractLogMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerSiteMapperExt siteMapper;
    @Autowired
    private ContractQualificationMapperExt contractQualificationMapper;
    @Autowired
    private CustomerMapperExt customerMapper;
    @Autowired
    private ContractQualificationService contractQualificationService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private CustomerContractSettlePriceService settlePriceService;
    @Autowired
    private ContractOilPriceService oilPriceService;
    @Autowired
    private CustomerContractSettleRuleService settleRuleService;
    @Autowired
    private CustomerSiteMapperExt siteMapperExt;
    @Autowired
    private TruckTypeMapperExt truckTypeMapperExt;

    /**
     * 创建合同
     *
     * @param dto
     * @return
     */
    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createContract(CreateContractDto dto) {
        //创建contract对象
        CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(dto, customerContract);
        if (dto.getEndDate().before(new Date())) {
            throw new RuntimeException("合同结束日期不合法");
        }
        UpdateContractDto updateContractDto = new UpdateContractDto();
        updateContractDto.setContractNumber(customerContract.getContractNumber());
        if (this.customerContractMapper.getByUpdateDto(updateContractDto) != null) {
            throw new RuntimeException("合同编号已存在");
        }
        List<CustomerContract> contractList = this.customerContractMapper.getByCustomerAndGoodsType(customerContract);
        if (!CollectionUtils.isEmpty(contractList)) {
            CustomerContract contract = contractList.get(0);
            if (contract != null) {
                if (dto.getStartDate().getTime() < contract.getEndDate().getTime() || differentDays(contract.getEndDate(), dto.getStartDate()) > 1) {
                    throw new RuntimeException("合同开始日期不能与上一份合同结束日期有空隙");
                }
            }
            Boolean aBoolean = this.checkContract(contractList, dto);
            if (aBoolean) {
                throw new RuntimeException("此类型合同日期重叠");
            }
        }
        customerContract
                .setContractStatus(AuditStatus.UNCHECKED)
                .setCreateUser(userService.getCurrentUser().getId());
        customerContractMapper.insertSelective(customerContract);
        //创建 资质证
        if (!CollectionUtils.isEmpty(dto.getQualificationDtos())) {
            for (CreateContractQualificationDto qualificationDto : dto.getQualificationDtos()) {
                qualificationDto.setRelevanceId(customerContract.getId());
                contractQualificationService.createQuation(qualificationDto);
            }
        }
        return ServiceResult.toResult(customerContract.getId());
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createContract(List<CreateContractDto> dtos, Integer customerId) {
        if (dtos != null && dtos.size() > 0) {
            for (CreateContractDto contractDto : dtos) {
                //创建contract对象
                CustomerContract customerContract = new CustomerContract();
                BeanUtils.copyProperties(contractDto, customerContract);
                customerContract
                        .setCreateUser(userService.getCurrentUser().getId());
                customerContractMapper.insertSelective(customerContract);
            }
        }
        return ServiceResult.toResult("合同列表创建成功");
    }

    /**
     * 修改合同
     *
     * @param dto
     * @return
     */
    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateContract(UpdateContractDto dto) {
        // 创建contract对象
        CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(dto, customerContract);
        if (dto.getEndDate() != null && customerContract.getEndDate().before(new Date())) {
            throw new RuntimeException("截止日期不能小于今天");
        }
        if (this.customerContractMapper.getByUpdateDto(dto) != null) {
            throw new RuntimeException("合同编号已存在");
        }
        customerContract
                .setNewUpdateTime(new Date())
                .setNewUpdateUser(userService.getCurrentUser().getId());
        customerContractMapper.updateByPrimaryKeySelective(customerContract);

        return ServiceResult.toResult("合同修改成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateContract(List<UpdateContractDto> dtos) {
        if (dtos != null && dtos.size() > 0) {
            for (UpdateContractDto contractDto : dtos) {
                // 创建contract对象
                CustomerContract customerContract = new CustomerContract();
                BeanUtils.copyProperties(contractDto, customerContract);
                customerContract
                        .setNewUpdateTime(new Date())
                        .setNewUpdateUser(userService.getCurrentUser().getId());
                customerContractMapper.updateByPrimaryKeySelective(customerContract);
            }
        }
        return ServiceResult.toResult("合同修改成功");
    }

    /**
     * 查询单个合同
     *
     * @param contractId
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer contractId) {
        CustomerContract customerContract = customerContractMapper.selectByPrimaryKey(contractId);
        if (customerContract == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "id: " + contractId + "不存在");
        }
        ReturnContractDto contractDto = customerContractMapper.getById(contractId);
        if (contractDto != null) {
            List<ReturnContractQualificationDto> qualificationDtoList = new ArrayList<>();
            Customer customer = customerMapper.selectByPrimaryKey(customerContract.getCustomerId());
            if (customer != null && customer.getCustomerCategory().equals(CustomerCategory.FARMS)) {
                qualificationDtoList = contractQualificationMapper.listQualificationByContractIdAndType(contractDto.getId(), ContractType.BREED_CUSTOMER);
            } else {
                qualificationDtoList = contractQualificationMapper.listQualificationByContractIdAndType(contractDto.getId(), ContractType.CUSTOMER);
            }
            if (!CollectionUtils.isEmpty(qualificationDtoList)) {
                for (ReturnContractQualificationDto contractQualificationDto : qualificationDtoList
                ) {
                    //替换资质证照地址
                    String[] strArray = {contractQualificationDto.getCertificateImageUrl()};
                    List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                    contractQualificationDto.setCertificateImageUrl(urlList.get(0).toString());
                }
                contractDto.setQualifications(qualificationDtoList);
            }
        }
        //结算装卸货地
        List<ReturnCustomerSettlePriceDto> settlePriceDtoList = settlePriceService.listByContractId(contractId);
        if (!CollectionUtils.isEmpty(settlePriceDtoList)) {
            for (ReturnCustomerSettlePriceDto settlePriceDto : settlePriceDtoList) {
                settlePriceDto
                        .setLoadSiteName(siteMapperExt.selectByPrimaryKey(settlePriceDto.getLoadSiteId()).getSiteName())
                        .setUnloadSiteName(siteMapperExt.selectByPrimaryKey(settlePriceDto.getUnloadSiteId()).getSiteName());
                if (!StringUtils.isEmpty(settlePriceDto.getTruckTypeId())) {
                    TruckType truckType = truckTypeMapperExt.selectByPrimaryKey(settlePriceDto.getTruckTypeId());
                    if (truckType != null) {
                        settlePriceDto.setTruckTypeName(truckType.getTypeName());
                    }
                }
            }
            contractDto.setSettlePriceDtoList(settlePriceDtoList);
        }

        //油价
        contractDto.setContractOilPriceDto(oilPriceService.getByContractId(contractId, ContractType.CUSTOMER));
        //结算配制
        contractDto.setSettleRuleDto(settleRuleService.getByContractId(contractId));
        return ServiceResult.toResult(contractDto);
    }

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> listByCriteria(ListContractCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        dto.setTenantId(userService.getCurrentUser().getTenantId());
        List<ReturnContractDto> contracts = customerContractMapper.listByPage(dto);
        for (ReturnContractDto contractDto : contracts) {
            Customer customer = this.customerMapper.selectByPrimaryKey(contractDto.getCustomerId());
            if (customer != null) {
                contractDto.setCustomerName(customer.getCustomerName());
            }
            //剩余天数
            int remainingDays = differentDays(new Date(), contractDto.getEndDate());
            if (remainingDays > 0) {
                contractDto.setRemainingDays(remainingDays);
            } else {
                contractDto.setRemainingDays(0);
            }
        }
        return ServiceResult.toResult(new PageInfo<>(contracts));
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> disableById(Integer id) {
        CustomerContract contract = this.customerContractMapper.selectByPrimaryKey(id);
        if (contract == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同不存在");
        }
        if (!contract.getContractStatus().equals(AuditStatus.UNCHECKED)) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "除待审核状态,其他状态合同不允许删除");
        }
        ReturnContractDto contractDto = this.customerContractMapper.getById(id);
        CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(contractDto, customerContract);
        customerContract.setContractStatus(AuditStatus.STOP_COOPERATION);
        this.customerContractMapper.updateByPrimaryKeySelective(customerContract);
        return ServiceResult.toResult("合同删除成功");
    }

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Override
    public Map<String, Object> disableByID(List<ReturnContractDto> dtos) {
        for (ReturnContractDto returnContractDto : dtos
        ) {
            ReturnContractDto contractDto = this.customerContractMapper.getById(returnContractDto.getId());
            CustomerContract customerContract = new CustomerContract();
            BeanUtils.copyProperties(contractDto, customerContract);
            customerContract.setContractStatus(0);
            this.customerContractMapper.updateByPrimaryKeySelective(customerContract);
        }
        return ServiceResult.toResult("合同删除成功");
    }

    /**
     * 获取显示客户合同对象
     *
     * @param id
     * @return
     */
    @Override
    public ShowCustomerContractDto getShowCustomerContractById(Integer id) {
        return customerContractMapper.getShowCustomerContractById(id);
    }

    /**
     * 通过客户id获取当前客户所有合同（显示对象）
     *
     * @param customerId
     * @return
     */
    @Override
    public List<ShowCustomerContractDto> listShowCustomerContractByCustomerId(Integer customerId) {
        return customerContractMapper.listShowCustomerContractByCustomerId(customerId);
    }

    /**
     * 判断 是否有重叠合同
     *
     * @param contractList
     * @return
     */
    private Boolean checkContract(List<CustomerContract> contractList, CreateContractDto dto) {
        if (CollectionUtils.isEmpty(contractList)) {
            return false;
        } else {
            for (CustomerContract contract : contractList) {
                if (dto.getStartDate().getTime() <= contract.getStartDate().getTime() && dto.getEndDate().getTime() >= contract.getStartDate().getTime()) {
                    return true;
                } else if (dto.getStartDate().getTime() >= contract.getStartDate().getTime() && dto.getStartDate().getTime() <= contract.getEndDate().getTime()) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    @Override
    public int differentDays(Date date2, Date date1) {
        try {
            int a = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
            return a;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }

}
