package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ContractType;
import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.contract.CreateContractDto;
import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettlePriceDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettleRuleDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        UpdateContractDto updateContractDto = new UpdateContractDto();
        updateContractDto.setContractNumber(customerContract.getContractNumber());
        if (this.customerContractMapper.getByUpdateDto(updateContractDto) != null) {
            throw new RuntimeException("合同编号已存在");
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
        //创建 结算基础信息(存在历史)
        if (!CollectionUtils.isEmpty(dto.getSettlePriceDtoList())) {
            for (CreateCustomerSettlePriceDto settlePriceDtos : dto.getSettlePriceDtoList()) {
                if (StringUtils.isEmpty(settlePriceDtos.getLoadSiteId())) {
                    throw new RuntimeException("合同报价装货地不能为空");
                }
                if (StringUtils.isEmpty(settlePriceDtos.getUnloadSiteId())) {
                    throw new RuntimeException("合同报价卸货地不能为空");
                }
                if (StringUtils.isEmpty(settlePriceDtos.getTruckTypeId())) {
                    throw new RuntimeException("合同报价车型不能为空");
                }
                settlePriceDtos
                        .setCustomerContractId(customerContract.getId())
                        .setEffectiveTime(customerContract.getStartDate())
                        .setInvalidTime(customerContract.getEndDate());
                Boolean result = settlePriceService.createCustomerSettlePrice(settlePriceDtos);
                if (!result) {
                    throw new RuntimeException("创建合同报价失败");
                }
            }
        }
        //创建 油价设置(存在历史)
        if (dto.getOilPriceDto() != null) {
            CreateContractOilPriceDto oilPriceDto = dto.getOilPriceDto();
            if (StringUtils.isEmpty(oilPriceDto.getPrice())) {
                throw new RuntimeException("油价配制价格不能为空");
            }
            oilPriceDto
                    .setContractId(customerContract.getId())
                    .setContractType(ContractType.CUSTOMER)
                    .setEffectiveTime(customerContract.getStartDate())
                    .setInvalidTime(customerContract.getEndDate());
            Boolean result = oilPriceService.createOilPrice(oilPriceDto);
            if (!result) {
                throw new RuntimeException("创建合同油价配制失败");
            }
        }
        //创建 结算配制(存在历史)
        if (dto.getSettleRuleDto() != null) {
            CreateCustomerSettleRuleDto settleRuleDto = dto.getSettleRuleDto();
            //货物类型为毛鸡
            if (customerContract.getType().equals(ProductType.CHICKEN)) {
                if (CollectionUtils.isEmpty(settleRuleDto.getTruckRuleDtoList())) {
                    throw new RuntimeException("毛鸡类型的合同里程区间配制不能为空");
                }
            }
            settleRuleDto
                    .setEffectiveTime(customerContract.getStartDate())
                    .setInvalidTime(customerContract.getEndDate())
                    .setCustomerContractId(customerContract.getId());
            Boolean result = settleRuleService.createSettleRule(settleRuleDto);
            if (!result) {
                throw new RuntimeException("创建合同里程区间配制失败");
            }
        }
        return ServiceResult.toResult("合同创建成功");
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
        if (customerContractMapper.selectByPrimaryKey(contractId) == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "id: " + contractId + "不存在");
        }
        ReturnContractDto contractDto = customerContractMapper.getById(contractId);
        if (contractDto.getQualifications().size() > 0) {
            for (ReturnContractQualificationDto contractQualificationDto : contractDto.getQualifications()
            ) {
                //替换资质证照地址
                String[] strArray = {contractQualificationDto.getCertificateImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                contractQualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            }
        }
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
        List<ReturnContractDto> contracts = customerContractMapper.listByPage(dto);
        for (ReturnContractDto contractDto : contracts) {
            Customer customer = this.customerMapper.selectByPrimaryKey(contractDto.getCustomerId());
            if (customer != null) {
                contractDto.setCustomerName(customer.getCustomerName());
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

}
