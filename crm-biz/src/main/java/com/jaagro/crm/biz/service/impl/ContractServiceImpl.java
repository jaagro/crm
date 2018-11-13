package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.api.service.ContractPriceService;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.api.service.OssSignUrlClientService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.entity.CustomerContractPrice;
import com.jaagro.crm.biz.entity.CustomerContractSectionPrice;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.utils.BaseResponse;
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
    private CustomerContractPriceMapperExt customerContractPriceMapper;
    @Autowired
    private CustomerContractSectionPriceMapperExt customerContractSectionPriceMapper;
    @Autowired
    private CustomerContractLogMapperExt customerContractLogMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private ContractPriceService priceService;
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

        //创建资质证
        if (dto.getQualificationDtos() != null && dto.getQualificationDtos().size() > 0) {
            for (CreateContractQualificationDto qualificationDto : dto.getQualificationDtos()) {
                qualificationDto.setRelevanceId(customerContract.getId());
                this.contractQualificationService.createQuation(qualificationDto);
            }
        }
        //创建合同报价及阶梯报价
        createPrice(dto, customerContract);
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

                //创建contractPrice对象
                createPrice(contractDto, customerContract);
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

        //删除原数据
        List<ReturnContractPriceDto> priceList = customerContractPriceMapper.listByContractId(dto.getId());
        if (priceList.size() > 0) {
            for (ReturnContractPriceDto cp : priceList) {
                customerContractSectionPriceMapper.deleteByPriceId(cp.getId());
            }
            customerContractPriceMapper.deleteByContractId(dto.getId());
        }
        //创建contractPrice对象
        createPrice(dto, customerContract);
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

                //删除原数据
                List<ReturnContractPriceDto> priceList = customerContractPriceMapper.listByContractId(contractDto.getId());
                if (priceList.size() > 0) {
                    for (ReturnContractPriceDto cp : priceList) {
                        customerContractSectionPriceMapper.deleteByPriceId(cp.getId());
                    }
                    customerContractPriceMapper.deleteByContractId(contractDto.getId());
                }
                //创建contractPrice对象
                createPrice(contractDto, customerContract);
            }
        }
        return ServiceResult.toResult("合同修改成功");
    }

    private void createPrice(UpdateContractDto dto, CustomerContract customerContract) {
        //创建contractPrice对象
        if (dto.getPrice() != null && dto.getPrice().size() > 0) {
            for (UpdateContractPriceDto cp : dto.getPrice()) {
                CustomerContractPrice customerContractPrice = new CustomerContractPrice();
                BeanUtils.copyProperties(cp, customerContractPrice);
                customerContractPrice
                        .setContractId(customerContract.getId())
                        .setPriceStatus(1);
                if (StringUtils.isEmpty(customerContractPrice.getPricingType())) {
                    throw new RuntimeException("计价模式不能为空");
                }
                customerContractPriceMapper.insertSelective(customerContractPrice);
                //创建contractSectionPrice对象
                if (cp.getSectionPrice() != null && cp.getSectionPrice().size() > 0) {
                    for (CreateContractSectionPriceDto cspDto : cp.getSectionPrice()) {
                        CustomerContractSectionPrice csp = new CustomerContractSectionPrice();
                        BeanUtils.copyProperties(cspDto, csp);
                        csp
                                .setContractPriceId(customerContractPrice.getId())
                                .setSelectionStatus(1);
                        customerContractSectionPriceMapper.insertSelective(csp);
                    }
                }
            }
        }
    }

    private void createPrice(CreateContractDto dto, CustomerContract customerContract) {
        //创建contractPrice对象
        if (dto.getPrice() != null && dto.getPrice().size() > 0) {
            for (CreateContractPriceDto cp : dto.getPrice()) {
                CustomerContractPrice customerContractPrice = new CustomerContractPrice();
                BeanUtils.copyProperties(cp, customerContractPrice);
                customerContractPrice
                        .setContractId(customerContract.getId())
                        .setPriceStatus(1);
                if (StringUtils.isEmpty(customerContractPrice.getPricingType())) {
                    throw new RuntimeException("计价模式不能为空");
                }
                if (this.siteMapper.selectByPrimaryKey(customerContractPrice.getLoadSiteId()) == null) {
                    throw new RuntimeException("发货地址:[" + customerContractPrice.getLoadSiteId() + "]不存在");
                }
                if (this.siteMapper.selectByPrimaryKey(customerContractPrice.getUnloadSiteId()) == null) {
                    throw new RuntimeException("收货地址:[" + customerContractPrice.getUnloadSiteId() + "]不存在");
                }
                customerContractPriceMapper.insertSelective(customerContractPrice);
                //创建contractSectionPrice对象
                if (cp.getSectionPrice() != null && cp.getSectionPrice().size() > 0) {
                    for (CreateContractSectionPriceDto cspDto : cp.getSectionPrice()) {
                        CustomerContractSectionPrice csp = new CustomerContractSectionPrice();
                        BeanUtils.copyProperties(cspDto, csp);
                        csp
                                .setContractPriceId(customerContractPrice.getId())
                                .setSelectionStatus(1);
                        customerContractSectionPriceMapper.insertSelective(csp);
                    }
                }
            }
        }
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
        if (this.customerContractMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同不存在");
        }
        ReturnContractDto contractDto = this.customerContractMapper.getById(id);
        CustomerContract customerContract = new CustomerContract();
        BeanUtils.copyProperties(contractDto, customerContract);
        customerContract.setContractStatus(AuditStatus.STOP_COOPERATION);
        this.customerContractMapper.updateByPrimaryKeySelective(customerContract);
        if (contractDto.getPrices() != null && contractDto.getPrices().size() > 0) {
            this.priceService.disableByContractId(customerContract.getId());
        }
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
            if (contractDto.getPrices() != null && contractDto.getPrices().size() > 0) {
                this.priceService.disableByContractId(customerContract.getId());
            }
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
