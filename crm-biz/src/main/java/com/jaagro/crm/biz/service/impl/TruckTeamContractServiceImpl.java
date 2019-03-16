package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.*;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.request.truck.*;
import com.jaagro.crm.api.dto.response.contract.ReturnContractOilPriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.api.dto.response.truck.*;
import com.jaagro.crm.api.service.ContractOilPriceService;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.*;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.UserClientService;
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
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * @author liqiangping
 */
@CacheConfig(keyGenerator = "wiselyKeyGenerator")
@Service
public class TruckTeamContractServiceImpl implements TruckTeamContractService {

    private static final Logger log = LoggerFactory.getLogger(TruckTeamContractServiceImpl.class);

    @Autowired
    private TruckTeamContractMapperExt truckTeamContractMapper;
    @Autowired
    private TruckTeamMapperExt truckTeamMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private ContractQualificationService contractQualificationService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private TruckTypeMapperExt truckTypeMapper;
    @Autowired
    private DriverContractSettleRuleMapperExt driverContractSettleRuleMapper;
    @Autowired
    private DriverContractSettleSectionRuleMapperExt driverContractSettleSectionRuleMapper;
    @Autowired
    private ContractOilPriceService contractOilPriceService;
    @Autowired
    private ContractOilPriceMapperExt contractOilPriceMapper;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private ContractQualificationMapperExt contractQualificationMapperExt;
    @Autowired
    private ContractService contractService;


    /**
     * 创建车队合同
     *
     * @param dto
     * @return
     */
    @CacheEvict(cacheNames = "truck", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto) {
        if (this.truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()) == null) {
            throw new NullPointerException("车队不存在");
        }
        if (this.truckTeamContractMapper.getByContractNumber(dto.getContractNumber()) != null) {
            throw new RuntimeException("合同编号已存在");
        }
        if (judgeExpired(dto.getEndDate(), true)) {
            throw new RuntimeException("当前填写的合同时间已经过期");
        }
        List<TruckTeamContract> contractList = truckTeamContractMapper.getByTeamIdAndType(dto);
        if (!CollectionUtils.isEmpty(contractList)) {
            TruckTeamContract contract = contractList.get(0);
            if (contract != null) {
                if (dto.getStartDate().getTime() < contract.getEndDate().getTime() || contractService.differentDays(contract.getEndDate(), dto.getStartDate()) > 1) {
                    throw new RuntimeException("合同开始日期不能与上一份合同结束日期有空隙");
                }
            }
            Boolean aBoolean = checkContract(contractList, dto);
            if (aBoolean) {
                throw new RuntimeException("此类型合同日期重叠");
            }
        }
        UserInfo currentUser = this.userService.getCurrentUser();
        //创建合同
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto, truckTeamContract);
        truckTeamContract
                .setCreateUserId(currentUser.getId());
        truckTeamContractMapper.insertSelective(truckTeamContract);
        //创建合同资质证
        if (dto.getQualificationDtoList() != null && dto.getQualificationDtoList().size() > 0) {
            for (CreateContractQualificationDto qualificationDto : dto.getQualificationDtoList()) {
                qualificationDto.setRelevanceId(truckTeamContract.getId());
                this.contractQualificationService.createQuation(qualificationDto);
            }
        }
        return ServiceResult.toResult(truckTeamContract.getId());
    }

    /**
     * 根据合同ID查询合同
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer id) {
        TruckTeamContractReturnDto contractReturnDto = truckTeamContractMapper.getById(id);
        if (contractReturnDto != null) {
            List<ReturnContractQualificationDto> qualificationDtoList = contractQualificationMapperExt.listQualificationByContractIdAndType(contractReturnDto.getId(), ContractType.DRIVER);
            if (!CollectionUtils.isEmpty(qualificationDtoList)) {
                for (ReturnContractQualificationDto qualificationDto : qualificationDtoList) {
                    //替换资质证照地址
                    String[] strArray = {qualificationDto.getCertificateImageUrl()};
                    List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                    qualificationDto.setCertificateImageUrl(urlList.get(0).toString());
                }
                contractReturnDto.setQualificationDtoList(qualificationDtoList);
            }
        }
        return ServiceResult.toResult(contractReturnDto);
    }

    /**
     * 根据合同编号查询合同
     *
     * @param contractNumber
     * @return
     */
    @Override
    public Map<String, Object> getByContractNumber(String contractNumber) {
        return ServiceResult.toResult(truckTeamContractMapper.getByContractNumber(contractNumber));
    }

    /**
     * 修改车队合同
     *
     * @param dto
     * @return
     */
    @CacheEvict(cacheNames = "truck", allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateTruckTeamContract(UpdateTruckTeamContractDto dto) {
        //创建车队合同对象
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto, truckTeamContract);
        TruckTeamContractReturnDto byId = truckTeamContractMapper.getById(dto.getId());
        if (byId.getContractNumber().equals(dto.getContractNumber())) {
            throw new RuntimeException("合同编号不能相同");
        }
        if (truckTeamContract != null) {
            truckTeamContract
                    .setModifyTime(new Date())
                    .setModifyUserId(this.userService.getCurrentUser().getId());
            truckTeamContractMapper.updateByPrimaryKeySelective(truckTeamContract);
        }
        return ServiceResult.toResult("修改车队合同成功");
    }

    /**
     * 创建车队合同表
     *
     * @param dto
     * @param truckTeamId
     * @return
     */
    @CacheEvict(cacheNames = "truck", allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamContracts(List<CreateTruckTeamContractDto> dto, Integer truckTeamId) {
        if (dto != null && dto.size() > 0) {
            for (CreateTruckTeamContractDto ctt : dto) {
                //创建车队合同对象
                TruckTeamContract truckTeamContract = new TruckTeamContract();
                BeanUtils.copyProperties(ctt, truckTeamContract);
                truckTeamContract
                        .setContractStatus(ContractStatus.UNAUDITED)
                        .setTruckTeamId(truckTeamId);
                truckTeamContractMapper.insert(truckTeamContract);
            }
        }
        return ServiceResult.toResult("车队合同创建成功");
    }

    @Override
    public Map<String, Object> listByCriteria(ListTruckTeamContractCriteriaDto criteriaDto) {
        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        List<ListTruckTeamContractDto> returnDtoList = this.truckTeamContractMapper.listByCriteria(criteriaDto);
        if (returnDtoList.size() > 0) {
            for (ListTruckTeamContractDto contractDto : returnDtoList
            ) {
                TruckTeam truckTeam = this.truckTeamMapper.selectByPrimaryKey(contractDto.getTruckTeamId());
                if (truckTeam != null) {
                    contractDto.setTruckTeamName(truckTeam.getTeamName());
                }
            }
        }
        return ServiceResult.toResult(new PageInfo<>(returnDtoList));
    }

    @CacheEvict(cacheNames = "truck", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> disableContract(Integer id) {
        TruckTeamContract teamContract = truckTeamContractMapper.selectByPrimaryKey(id);
        if (teamContract == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到合同");
        }
        if (!teamContract.getContractStatus().equals(AuditStatus.UNCHECKED)) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "除待审核状态,其他状态合同不允许删除");
        }
        TruckTeamContractReturnDto contractReturnDto = truckTeamContractMapper.getById(id);
        if (contractReturnDto != null) {
            TruckTeamContract contract = new TruckTeamContract();
            BeanUtils.copyProperties(contractReturnDto, contract);
            contract
                    .setModifyTime(new Date())
                    .setModifyUserId(this.userService.getCurrentUser().getId())
                    .setContractStatus(AuditStatus.STOP_COOPERATION);
            this.truckTeamContractMapper.updateByPrimaryKeySelective(contract);
        }
        // 删除合同报价配置
        DriverContractSettleCondition contractSettleCondition = new DriverContractSettleCondition();
        contractSettleCondition.setFlag(3)
                .setTruckTeamContractId(id);
        deleteTeamContractPrice(contractSettleCondition);
        // 删除油价
        contractOilPriceService.disableByContractIdAndType(id, ContractType.DRIVER);
        return ServiceResult.toResult("删除成功");
    }

    /**
     * 创建车队合同报价
     *
     * @author @Gao.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTruckTeamContractPrice(CreateDriverContractSettleDto driverContractSettleDto) {
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (null != driverContractSettleDto.getTruckTypeId()) {
            ListTruckTypeDto truckType = truckTypeMapper.getById(driverContractSettleDto.getTruckTypeId());
            DriverContractSettleCondition driverContractSettleCondition = new DriverContractSettleCondition();
            driverContractSettleCondition
                    .setTruckTeamContractId(driverContractSettleDto.getContractId() == null ? null : driverContractSettleDto.getContractId())
                    .setTruckTypeId(driverContractSettleDto.getTruckTypeId());
            DriverContractSettleParam driverContractSettleParam = new DriverContractSettleParam();
            driverContractSettleParam
                    .setTruckTeamContractId(driverContractSettleDto.getContractId() == null ? null : driverContractSettleDto.getContractId())
                    .setCreateUserId(currentUser.getId())
                    .setTruckTypeId(driverContractSettleDto.getTruckTypeId())
                    .setTruckTypeName(truckType.getTypeName());
            //鸡车类型 饲料类型 只有一种结算方式
            if (GoodsType.CHICKEN.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.FODDER.equals(Integer.parseInt(truckType.getProductName()))) {
                contractCapacitySettle(driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
            }
            //仔猪 生猪类型 存在两种结算方式 只能选择一种
            boolean flag = (GoodsType.SOW.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.BOAR.equals(Integer.parseInt(truckType.getProductName()))
                    || GoodsType.PIGLET.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.LIVE_PIG.equals(Integer.parseInt(truckType.getProductName())));
            if (flag) {
                driverContractSettleCondition.setFlag(2);
                DriverContractSettleRule driverContractSettleRule = driverContractSettleRuleMapper.listDriverContractSettleRuleByCondition(driverContractSettleCondition);
                if (null != driverContractSettleRule) {
                    if (driverContractSettleDto.getPricingMethod().equals(driverContractSettleRule.getPricingMethod())) {
                        //按照区间里程单价 或者按照起步价
                        contractCapacitySettle(driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
                    } else {
                        throw new NullPointerException("当前货物类型只能选择一种报价方式");
                    }
                } else {
                    //按照区间里程单价 或者按照起步价
                    contractCapacitySettle(driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
                }
            }
        }
    }

    /**
     * 1-按区间重量单价,2-按区间里程单价,3-按照起步价
     *
     * @param driverContractSettleCondition
     * @param driverContractSettleDto
     * @param driverContractSettleParam
     * @author @Gao.
     */

    private void contractCapacitySettle(DriverContractSettleCondition driverContractSettleCondition,
                                        CreateDriverContractSettleDto driverContractSettleDto, DriverContractSettleParam driverContractSettleParam) {
        driverContractSettleCondition
                .setFlag(1)
                .setPricingMethod(driverContractSettleDto.getPricingMethod());
        DriverContractSettleRule driverContractSettleRule = driverContractSettleRuleMapper.listDriverContractSettleRuleByCondition(driverContractSettleCondition);
        // 1-按区间重量单价
        if (PricingMethod.SECTION_WEIGHT.equals(driverContractSettleDto.getPricingMethod())) {
            driverContractSettleParam
                    .setUnit(1)
                    .setType(1)
                    .setPricingMethod(PricingMethod.SECTION_WEIGHT)
                    .setMinSettleWeight(driverContractSettleDto.getMinSettleWeight());
        }
        //2-按区间里程单价
        if (PricingMethod.SECTION_MILEAGE.equals(driverContractSettleDto.getPricingMethod())) {
            driverContractSettleParam
                    .setType(1)
                    .setUnit(2)
                    .setPricingMethod(PricingMethod.SECTION_MILEAGE)
                    .setMinSettleMileage(driverContractSettleDto.getMinSettleMileage());
        }
        //3-按照起步价
        if (PricingMethod.BEGIN_MILEAGE.equals(driverContractSettleDto.getPricingMethod())) {
            driverContractSettleParam
                    .setPricingMethod(PricingMethod.BEGIN_MILEAGE)
                    .setBeginMileage(driverContractSettleDto.getBeginMileage())
                    .setBeginPrice(driverContractSettleDto.getBeginPrice())
                    .setMileagePrice(driverContractSettleDto.getMileagePrice());
        }
        //当前报价合同第一次插入
        if (null == driverContractSettleRule) {
            TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(driverContractSettleDto.getContractId());
            driverContractSettleParam
                    .setEffectiveTime(truckTeamContract.getStartDate())
                    .setInvalidTime(truckTeamContract.getEndDate());
            if (judgeExpired(truckTeamContract.getEndDate(), true)) {
                throw new NullPointerException("当前合同已经过期了");
            }
            saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, null, false);

        } else {
            TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(driverContractSettleRule.getTruckTeamContractId());
            if (truckTeamContract.getEndDate() != null) {
                if (judgeExpired(truckTeamContract.getEndDate(), true)) {
                    throw new NullPointerException("当前合同已经过期了");
                }
            }
            boolean flag = (driverContractSettleDto.getPricingMethod().equals(driverContractSettleRule.getPricingMethod())
                    && driverContractSettleDto.getTruckTypeId().equals(driverContractSettleRule.getTruckTypeId()));
            if (flag) {
                //合同起始时间小于当前时间
                if (judgeExpired(truckTeamContract.getStartDate(), false)) {
                    driverContractSettleParam
                            .setEffectiveTime(truckTeamContract.getStartDate())
                            .setInvalidTime(truckTeamContract.getEndDate());
                    saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, driverContractSettleRule.getId(), false);
                } else {
                    //当前最后一条记录为历史记录
                    driverContractSettleParam
                            .setTruckTeamContractId(driverContractSettleRule.getTruckTeamContractId());
                    if (driverContractSettleRule.getHistoryFlag() == true) {
                        driverContractSettleParam
                                .setEffectiveTime(driverContractSettleRule.getInvalidTime())
                                .setInvalidTime(truckTeamContract.getEndDate());
                        saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, null, false);
                        //当前最后一条记录为非历史记录
                    } else {
                        driverContractSettleParam
                                .setEffectiveTime(new Date())
                                .setInvalidTime(driverContractSettleRule.getInvalidTime());
                        saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, driverContractSettleRule.getId(), true);
                    }
                }
            } else {
                log.info("O contractCapacitySettle :The current vehicle type already exists in the record!");
            }
        }
    }

    /**
     * 当前合同起始截止时间与当前时间比较
     *
     * @param
     * @return
     * @author @Gao.
     */
    private boolean judgeExpired(Date date, boolean type) {
        Date currentDate = new Date();
        if (type == true) {
            if (currentDate.after(date)) {
                return true;
            }
        }
        if (type == false) {
            if (currentDate.before(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 插入合同结算相关配置表
     *
     * @param driverContractSettleDto
     * @param driverContractSettleParam
     * @author @Gao.
     */
    private void saveDriverContractSettle(CreateDriverContractSettleDto driverContractSettleDto, DriverContractSettleParam driverContractSettleParam, Integer driverContractSettleId, boolean type) {
        //有历史合同结算配置记录，更新上一条记录失效时间
        if (null != driverContractSettleId && type == true) {
            DriverContractSettleRule driverContractSettle = new DriverContractSettleRule();
            driverContractSettle
                    .setHistoryFlag(true)
                    .setId(driverContractSettleId)
                    .setInvalidTime(new Date());
            driverContractSettleRuleMapper.updateByPrimaryKeySelective(driverContractSettle);
        }
        //合同起始时间大于当前时间时，不更改起始和截止时间
        if (null != driverContractSettleId && type == false) {
            DriverContractSettleRule driverContractSettle = new DriverContractSettleRule();
            driverContractSettle
                    .setHistoryFlag(true)
                    .setId(driverContractSettleId);
            driverContractSettleRuleMapper.updateByPrimaryKeySelective(driverContractSettle);
        }
        //插入合同结算配置规则
        DriverContractSettleRule driverContractSettleRule = new DriverContractSettleRule();
        BeanUtils.copyProperties(driverContractSettleParam, driverContractSettleRule);
        driverContractSettleRuleMapper.insertSelective(driverContractSettleRule);
        if (null != driverContractSettleDto.getCreateDriverContractSettleSectionDto()) {
            List<CreateDriverContractSettleSectionDto> driverContractSettleSectionDto = driverContractSettleDto.getCreateDriverContractSettleSectionDto();
            for (CreateDriverContractSettleSectionDto driverContractSettleSection : driverContractSettleSectionDto) {
                DriverContractSettleSectionRule driverContractSettleSectionRule = new DriverContractSettleSectionRule();
                BeanUtils.copyProperties(driverContractSettleSection, driverContractSettleSectionRule);
                driverContractSettleSectionRule
                        .setType(driverContractSettleParam.getType() == null ? null : driverContractSettleParam.getType())
                        .setUnit(driverContractSettleParam.getUnit() == null ? null : driverContractSettleParam.getUnit())
                        .setCreateUserId(driverContractSettleParam.getCreateUserId() == null ? null : driverContractSettleParam.getCreateUserId())
                        .setTruckTeamContractId(driverContractSettleParam.getTruckTeamContractId() == null ? null : driverContractSettleParam.getTruckTeamContractId())
                        .setDriverContractSettleRuleId(driverContractSettleRule.getId() == null ? null : driverContractSettleRule.getId());
                //插入合同结算区间配置
                driverContractSettleSectionRuleMapper.insertSelective(driverContractSettleSectionRule);
            }
        }
    }

    /**
     * 运力合同报价列表
     *
     * @param condition
     * @return
     */
    @Override
    public ListDriverContractSettleInfoFlagDto listDriverContractPrice(DriverContractSettleCondition condition) {
        // 根据合同id获取货物类型
        TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(condition.getTruckTeamContractId());
        if (truckTeamContract == null) {
            throw new RuntimeException("车队合同id=" + condition.getTruckTeamContractId() + "不存在");
        }
        Integer businessType = truckTeamContract.getBussinessType();
        if (businessType == null) {
            throw new RuntimeException("车队合同业务类型为空");
        }
        List<ListTruckTypeDto> listTruckTypeDtoList;
        if (BusinessType.FODDER.equals(businessType)) {
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.FODDER));
        } else if (BusinessType.CHICKEN.equals(businessType)) {
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.CHICKEN));
        } else if (BusinessType.PIGLET.equals(businessType)) {
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.PIGLET));
        } else {
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.BOAR));
        }
        if (CollectionUtils.isEmpty(listTruckTypeDtoList)) {
            throw new RuntimeException("找不到该业务类型下面的车型");
        }
        List<ListDriverContractSettleDto> driverContractSettleDtoList = driverContractSettleRuleMapper.listByTruckTeamContractId(condition.getTruckTeamContractId());
        if (CollectionUtils.isEmpty(driverContractSettleDtoList)) {
            return new ListDriverContractSettleInfoFlagDto();
        }
        ListDriverContractSettleInfoFlagDto driverContractSettleInfoFlagDto = new ListDriverContractSettleInfoFlagDto();
        driverContractSettleInfoFlagDto.setFlag(false);
        if (listTruckTypeDtoList.size() == driverContractSettleDtoList.size()) {
            driverContractSettleInfoFlagDto.setFlag(true);
        }
        List<ListDriverContractSettleInfoDto> driverContractSettleInfoDtoList = new ArrayList<>();
        for (ListDriverContractSettleDto dto : driverContractSettleDtoList) {
            ListDriverContractSettleInfoDto driverContractSettleInfoDto = new ListDriverContractSettleInfoDto();
            BeanUtils.copyProperties(dto, driverContractSettleInfoDto);
            driverContractSettleInfoDto.setContractSettleId(dto.getId());
            if (PricingMethod.BEGIN_MILEAGE.equals(dto.getPricingMethod())) {
                driverContractSettleInfoDto
                        .setBeginSettlePrice(dto.getBeginPrice());
            } else {
                List<BigDecimal> settlePriceList = new ArrayList<>();
                List<CreateDriverContractSettleSectionDto> DriverContractSettleSectionDtoList = dto.getCreateDriverContractSettleSectionDto();
                if (!CollectionUtils.isEmpty(DriverContractSettleSectionDtoList)) {
                    for (CreateDriverContractSettleSectionDto createDriverContractSettleSectionDto : DriverContractSettleSectionDtoList) {
                        settlePriceList.add(createDriverContractSettleSectionDto.getSettlePrice());
                    }
                    CreateDriverContractSettleSectionDto driverContractSettleSectionDto = DriverContractSettleSectionDtoList.get(0);
                    driverContractSettleInfoDto
                            .setUnit(driverContractSettleSectionDto.getUnit())
                            .setType(driverContractSettleSectionDto.getType())
                            .setBeginSettlePrice(Collections.min(settlePriceList));
                }
            }
            driverContractSettleInfoDtoList.add(driverContractSettleInfoDto);
        }
        driverContractSettleInfoFlagDto.setDriverContractSettleInfoDtos(driverContractSettleInfoDtoList);
        return driverContractSettleInfoFlagDto;
    }

    /**
     * 运力合同报价详情
     *
     * @param condition
     * @return
     * @author @Gao.
     */
    @Override
    public ListDriverContractSettleDto listTruckTeamContractPriceDetails(DriverContractSettleCondition condition) {
        List<ListDriverContractSettleDto> listDriverContractSettleDtos = driverContractSettleRuleMapper.listTruckTeamContractPriceCondition(condition.setFlag(2));
        ListDriverContractSettleDto listDriverContractSettle = null;
        if (!CollectionUtils.isEmpty(listDriverContractSettleDtos)) {
            listDriverContractSettle = listDriverContractSettleDtos.get(0);
        }
        return listDriverContractSettle;
    }

    /**
     * 当前车辆类型所有的报价历史记录
     *
     * @param condition
     * @return
     */
    @Override
    public PageInfo<ListDriverContractSettleDto> listTruckTeamContractPriceHistoryDetails(DriverContractSettleCondition condition) {
        return new PageInfo(driverContractSettleRuleMapper.listTruckTeamContractPriceCondition(condition.setFlag(5)));
    }

    /**
     * 逻辑删除某一个类的车型
     *
     * @param condition
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeamContractPrice(DriverContractSettleCondition condition) {
        condition.setFlag(condition.getFlag());
        List<ListDriverContractSettleDto> listDriverContractSettleDtos = driverContractSettleRuleMapper.listTruckTeamContractPriceCondition(condition);
        List<Integer> driverContractSettleIds = new ArrayList<>();
        List<Integer> driverContractSettleSectionIds = new ArrayList<>();
        for (ListDriverContractSettleDto listDriverContractSettleDto : listDriverContractSettleDtos) {
            driverContractSettleIds.add(listDriverContractSettleDto.getId());
            if (!CollectionUtils.isEmpty(listDriverContractSettleDto.getCreateDriverContractSettleSectionDto())) {
                List<CreateDriverContractSettleSectionDto> createDriverContractSettleSectionDto = listDriverContractSettleDto.getCreateDriverContractSettleSectionDto();
                for (CreateDriverContractSettleSectionDto driverContractSettleSectionDto : createDriverContractSettleSectionDto) {
                    driverContractSettleSectionIds.add(driverContractSettleSectionDto.getId());
                }
            }
        }
        //批量逻辑删除
        if (!CollectionUtils.isEmpty(driverContractSettleIds)) {
            driverContractSettleRuleMapper.deleteDriverContractSettleById(driverContractSettleIds);
        }
        if (!CollectionUtils.isEmpty(driverContractSettleSectionIds)) {
            driverContractSettleSectionRuleMapper.deleteDriverContractSettleSectionById(driverContractSettleSectionIds);
        }
    }

    /**
     * 获取最新的油价
     *
     * @return
     */
    @Override
    public GetContractOilPriceDto getNewOilPrice(ContractOilPriceCondition condition) {
        ReturnContractOilPriceDto contractIdAndType = null;
        if (condition.getContractId() != null) {
            contractIdAndType = contractOilPriceMapper.getByContractIdAndType(condition.getContractId(), ContractType.DRIVER);
        }
        BaseResponse<UserInfo> globalUser = null;
        if (contractIdAndType != null) {
            globalUser = userClientService.getGlobalUser(contractIdAndType.getCreateUserId());
        }
        GetContractOilPriceDto contractOilPriceDto = new GetContractOilPriceDto();
        if (contractIdAndType != null) {
            if (globalUser.getData() != null) {
                contractOilPriceDto
                        .setCreateUserName(globalUser.getData().getName());
            }
            contractOilPriceDto
                    .setOilPrice(contractIdAndType.getPrice())
                    .setCreateTime(contractIdAndType.getCreateTime());
        }
        return contractOilPriceDto;
    }

    /**
     * 更新油价
     *
     * @param createContractOilPriceDto
     */
    @Override
    public void updateOilPrice(CreateContractOilPriceDto createContractOilPriceDto) {
        TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(createContractOilPriceDto.getContractId());
        if (truckTeamContract != null) {
            //插入油价
            createContractOilPriceDto
                    .setContractType(ContractType.DRIVER)
                    .setEffectiveTime(truckTeamContract.getStartDate())
                    .setInvalidTime(truckTeamContract.getEndDate())
                    .setContractId(createContractOilPriceDto.getContractId());
            contractOilPriceService.createOilPrice(createContractOilPriceDto);
        }
    }

    /**
     * 根据车队ID查询车队合同列表
     * Gavin 20190108
     *
     * @param truckTeamId
     * @return
     */
    @Override
    public List<TruckTeamContractReturnDto> getTruckTeamContractByTruckTeamId(Integer truckTeamId) {

        List<TruckTeamContractReturnDto> teamContractReturnDtos = truckTeamContractMapper.listByTruckTeamId(truckTeamId);
        return teamContractReturnDtos;
    }

    /**
     * 根据条件查询某一类车型
     *
     * @param goodType
     * @return
     */
    @Override
    public List<ListTruckTypeDto> listTruckTeamTypeByGoodType(Integer goodType) {
        Integer productName = goodType;
        if (GoodsType.BOAR.equals(goodType) || GoodsType.SOW.equals(goodType) || GoodsType.LIVE_PIG.equals(goodType)) {
            productName = ProductName.COMMERCIAL_PIG;
        }
        return truckTypeMapper.listAll(String.valueOf(productName));
    }

    /**
     * 判断 是否有重叠合同
     *
     * @param contractList
     * @return
     */
    private Boolean checkContract(List<TruckTeamContract> contractList, CreateTruckTeamContractDto dto) {
        if (CollectionUtils.isEmpty(contractList)) {
            return false;
        } else {
            for (TruckTeamContract teamContract : contractList) {
                if (dto.getStartDate().getTime() <= teamContract.getStartDate().getTime() && dto.getEndDate().getTime() >= teamContract.getStartDate().getTime()) {
                    return true;
                } else if (dto.getStartDate().getTime() >= teamContract.getStartDate().getTime() && dto.getStartDate().getTime() <= teamContract.getEndDate().getTime()) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }
}
