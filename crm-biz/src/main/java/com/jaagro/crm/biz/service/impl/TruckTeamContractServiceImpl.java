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

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private ContractQualificationMapperExt contractQualificationMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private ContractQualificationService contractQualificationService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private TruckTeamContractService truckTeamContractService;
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
        if (contractReturnDto.getQualificationDtoList().size() > 0) {
            for (ReturnContractQualificationDto qualificationDto : contractReturnDto.getQualificationDtoList()
                    ) {
                //替换资质证照地址
                String[] strArray = {qualificationDto.getCertificateImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                qualificationDto.setCertificateImageUrl(urlList.get(0).toString());
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
        if (truckTeamContractMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到合同");
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
            //鸡车类型
            if (GoodsType.CHICKEN.equals(Integer.parseInt(truckType.getProductName()))) {
                //按照区间里程单价
                contractCapacitySettle(driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
            }
            //饲料类型
            if (GoodsType.FODDER.equals(Integer.parseInt(truckType.getProductName()))) {
                //按照区间重量单价
                contractCapacitySettle(driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
            }
            //仔猪 生猪类型
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
        if (null == driverContractSettleRule) {
            TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(driverContractSettleDto.getContractId());
            driverContractSettleParam
                    .setEffectiveTime(truckTeamContract.getStartDate())
                    .setInvalidTime(truckTeamContract.getEndDate());
            if (judgeExpired(truckTeamContract.getEndDate())) {
                throw new NullPointerException("当前合同已经过期了");
            }
            //插入合同报价相关表
            saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, null);

        } else {
            TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(driverContractSettleRule.getTruckTeamContractId());
            if (truckTeamContract.getEndDate() != null) {
                if (judgeExpired(truckTeamContract.getEndDate())) {
                    throw new NullPointerException("当前合同已经过期了");
                }
            }
            boolean flag = (driverContractSettleDto.getPricingMethod().equals(driverContractSettleRule.getPricingMethod())
                    && driverContractSettleDto.getTruckTypeId().equals(driverContractSettleRule.getTruckTypeId()));
            if (flag) {
                driverContractSettleParam
                        .setEffectiveTime(new Date())
                        .setTruckTeamContractId(driverContractSettleRule.getTruckTeamContractId())
                        .setInvalidTime(driverContractSettleRule.getInvalidTime());

                saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, driverContractSettleRule.getId());
            } else {
                log.info("O contractCapacitySettle :The current vehicle type already exists in the record!");
            }
        }
    }

    /**
     * 用于判断当前合同截止时间是否超过当前时间
     *
     * @param endDate
     * @return
     * @author @Gao.
     */
    private boolean judgeExpired(Date endDate) {
        Date currentDate = new Date();
        if (currentDate.after(endDate)) {
            return true;
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
    private void saveDriverContractSettle(CreateDriverContractSettleDto driverContractSettleDto, DriverContractSettleParam driverContractSettleParam, Integer driverContractSettleId) {
        //有历史合同结算配置记录，更新失效时间
        if (null != driverContractSettleId) {
            DriverContractSettleRule driverContractSettle = new DriverContractSettleRule();
            driverContractSettle
                    .setHistoryFlag(true)
                    .setId(driverContractSettleId)
                    .setInvalidTime(new Date());
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
     * @author @Gao.
     */
    @Override
    public List<ListDriverContractSettleInfoFlagDto> listTruckTeamContractPrice(DriverContractSettleCondition condition) {
        Integer count1 = 0;
        Integer count2 = 0;
        Integer count3 = 0;
        Integer count4 = 0;
        condition.setFlag(1);
        List<ListDriverContractSettleDto> listDriverContractSettleDtos = driverContractSettleRuleMapper.listTruckTeamContractPriceCondition(condition);
        List<ListDriverContractSettleInfoDto> listDriverContractSettleInfo1 = new ArrayList<>();
        List<ListDriverContractSettleInfoDto> listDriverContractSettleInfo2 = new ArrayList<>();
        List<ListDriverContractSettleInfoDto> listDriverContractSettleInfo3 = new ArrayList<>();
        List<ListDriverContractSettleInfoDto> listDriverContractSettleInfo4 = new ArrayList<>();
        List<ListDriverContractSettleInfoFlagDto> listDriverContractSettleInfoFlagDtos = new ArrayList<>();
        ListDriverContractSettleInfoFlagDto listDriverContractSettleInfoFlagDto1 = new ListDriverContractSettleInfoFlagDto();
        ListDriverContractSettleInfoFlagDto listDriverContractSettleInfoFlagDto2 = new ListDriverContractSettleInfoFlagDto();
        ListDriverContractSettleInfoFlagDto listDriverContractSettleInfoFlagDto3 = new ListDriverContractSettleInfoFlagDto();
        ListDriverContractSettleInfoFlagDto listDriverContractSettleInfoFlagDto4 = new ListDriverContractSettleInfoFlagDto();
        if (CollectionUtils.isEmpty(listDriverContractSettleDtos)) {
            return new ArrayList<>();
        }
        for (ListDriverContractSettleDto listDriverContractSettleDto : listDriverContractSettleDtos) {
            TruckType truckType = truckTypeMapper.selectByPrimaryKey(listDriverContractSettleDto.getTruckTypeId());
            ListDriverContractSettleInfoDto listDriverContractSettleInfoDto = new ListDriverContractSettleInfoDto();
            listDriverContractSettleInfoDto
                    .setTruckTypeName(truckType.getTypeName())
                    .setContractSettleId(listDriverContractSettleDto.getId())
                    .setTruckTypeId(listDriverContractSettleDto.getTruckTypeId());
            //按区间重量 饲料
            List<CreateDriverContractSettleSectionDto> createDriverContractSettleSectionDto = listDriverContractSettleDto.getCreateDriverContractSettleSectionDto();
            if (GoodsType.FODDER.equals(Integer.parseInt(truckType.getProductName()))) {
                if (PricingMethod.SECTION_WEIGHT.equals(listDriverContractSettleDto.getPricingMethod())) {
                    if (!CollectionUtils.isEmpty(createDriverContractSettleSectionDto)) {
                        CreateDriverContractSettleSectionDto driverContractSettleSection = createDriverContractSettleSectionDto.get(0);
                        listDriverContractSettleInfoDto
                                .setPricingMethod(PricingMethod.SECTION_WEIGHT)
                                .setMinSettleWeight(listDriverContractSettleDto.getMinSettleWeight())
                                .setUnit(driverContractSettleSection.getUnit())
                                .setType(driverContractSettleSection.getType())
                                .setBeginSettlePrice(driverContractSettleSection.getSettlePrice());
                    }
                }
                count1++;
                List<ListTruckTypeDto> listTruckTypeDtos = truckTypeMapper.listAll(truckType.getProductName());
                if (count1.equals(listTruckTypeDtos.size())) {
                    listDriverContractSettleInfoFlagDto1.setFlag(true);
                }
                listDriverContractSettleInfo1.add(listDriverContractSettleInfoDto);
            }
            if (GoodsType.CHICKEN.equals(Integer.parseInt(truckType.getProductName()))) {
                // 按区间里程 羽鸡
                if (PricingMethod.SECTION_MILEAGE.equals(listDriverContractSettleDto.getPricingMethod())) {
                    if (!CollectionUtils.isEmpty(createDriverContractSettleSectionDto)) {
                        CreateDriverContractSettleSectionDto driverContractSettleSection = createDriverContractSettleSectionDto.get(0);
                        listDriverContractSettleInfoDto
                                .setPricingMethod(PricingMethod.SECTION_MILEAGE)
                                .setUnit(driverContractSettleSection.getUnit())
                                .setType(driverContractSettleSection.getType())
                                .setBeginSettlePrice(driverContractSettleSection.getSettlePrice())
                                .setMinSettleMileage(listDriverContractSettleDto.getMinSettleMileage());
                        count2++;
                        List<ListTruckTypeDto> listTruckTypeDtos = truckTypeMapper.listAll(truckType.getProductName());
                        if (count2.equals(listTruckTypeDtos.size())) {
                            listDriverContractSettleInfoFlagDto2.setFlag(true);
                        }
                    }
                }
                listDriverContractSettleInfo2.add(listDriverContractSettleInfoDto);
            }
            //仔猪 生猪类型
            boolean flag = (GoodsType.SOW.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.BOAR.equals(Integer.parseInt(truckType.getProductName()))
                    || GoodsType.PIGLET.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.LIVE_PIG.equals(Integer.parseInt(truckType.getProductName())));
            if (flag) {
                //按起步价
                if (PricingMethod.BEGIN_MILEAGE.equals(listDriverContractSettleDto.getPricingMethod())) {
                    listDriverContractSettleInfoDto
                            .setPricingMethod(PricingMethod.BEGIN_MILEAGE)
                            .setBeginSettlePrice(listDriverContractSettleDto.getBeginPrice())
                            .setMinSettleMileage(listDriverContractSettleDto.getBeginMileage());
                }
                if (PricingMethod.SECTION_MILEAGE.equals(listDriverContractSettleDto.getPricingMethod())) {
                    if (!CollectionUtils.isEmpty(createDriverContractSettleSectionDto)) {
                        CreateDriverContractSettleSectionDto driverContractSettleSection = createDriverContractSettleSectionDto.get(0);
                        listDriverContractSettleInfoDto
                                .setPricingMethod(PricingMethod.SECTION_MILEAGE)
                                .setUnit(driverContractSettleSection.getUnit())
                                .setType(driverContractSettleSection.getType())
                                .setBeginSettlePrice(driverContractSettleSection.getSettlePrice())
                                .setMinSettleMileage(listDriverContractSettleDto.getMinSettleMileage());
                    }
                }
                //仔猪
                if (GoodsType.PIGLET.equals(Integer.parseInt(truckType.getProductName()))) {
                    listDriverContractSettleInfo3.add(listDriverContractSettleInfoDto);
                    count3++;
                    List<ListTruckTypeDto> listTruckTypeDtos = truckTypeMapper.listAll(truckType.getProductName());
                    if (count3.equals(listTruckTypeDtos.size())) {
                        listDriverContractSettleInfoFlagDto3.setFlag(true);
                    }
                } else {
                    listDriverContractSettleInfo4.add(listDriverContractSettleInfoDto);
                    count4++;
                    List<ListTruckTypeDto> listTruckTypeDtos = truckTypeMapper.listAll(truckType.getProductName());
                    if (count4.equals(listTruckTypeDtos.size())) {
                        listDriverContractSettleInfoFlagDto4.setFlag(true);
                    }
                }
            }

        }
        listDriverContractSettleInfoFlagDto1
                .setDriverContractSettleInfoDtos(listDriverContractSettleInfo1);
        listDriverContractSettleInfoFlagDto2
                .setDriverContractSettleInfoDtos(listDriverContractSettleInfo2);
        listDriverContractSettleInfoFlagDto3
                .setDriverContractSettleInfoDtos(listDriverContractSettleInfo3);
        listDriverContractSettleInfoFlagDto4
                .setDriverContractSettleInfoDtos(listDriverContractSettleInfo4);
        listDriverContractSettleInfoFlagDtos
                .add(listDriverContractSettleInfoFlagDto1);
        listDriverContractSettleInfoFlagDtos
                .add(listDriverContractSettleInfoFlagDto2);
        listDriverContractSettleInfoFlagDtos
                .add(listDriverContractSettleInfoFlagDto3);
        listDriverContractSettleInfoFlagDtos
                .add(listDriverContractSettleInfoFlagDto4);
        return listDriverContractSettleInfoFlagDtos;
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
        if (truckTeamContract == null){
            throw new RuntimeException("车队合同id="+condition.getTruckTeamContractId()+"不存在");
        }
        Integer businessType = truckTeamContract.getBussinessType();
        if(businessType == null){
            throw new RuntimeException("车队合同业务类型为空");
        }
        log.info("businessType={}",businessType);
        List<ListTruckTypeDto> listTruckTypeDtoList;
        if (BusinessType.FODDER.equals(businessType)){
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.FODDER));
        }else if(BusinessType.CHICKEN.equals(businessType)){
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.CHICKEN));
        }else if(BusinessType.PIGLET.equals(businessType)){
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.PIGLET));
        }else{
            listTruckTypeDtoList = truckTypeMapper.listAll(String.valueOf(GoodsType.BOAR));
        }
        log.info("listTruckTypeDtoList={}",listTruckTypeDtoList);
        if (CollectionUtils.isEmpty(listTruckTypeDtoList)){
            throw new RuntimeException("找不到该业务类型下面的车型");
        }
        List<ListDriverContractSettleDto> driverContractSettleDtoList = driverContractSettleRuleMapper.listByTruckTeamContractId(condition.getTruckTeamContractId());
        if (CollectionUtils.isEmpty(driverContractSettleDtoList)){
            return new ListDriverContractSettleInfoFlagDto();
        }
        ListDriverContractSettleInfoFlagDto driverContractSettleInfoFlagDto = new ListDriverContractSettleInfoFlagDto();
        driverContractSettleInfoFlagDto.setFlag(false);
        if (listTruckTypeDtoList.size() == driverContractSettleDtoList.size()){
            driverContractSettleInfoFlagDto.setFlag(true);
        }
        List<ListDriverContractSettleInfoDto> driverContractSettleInfoDtoList = new ArrayList<>();
        for (ListDriverContractSettleDto dto : driverContractSettleDtoList ){
            log.info("dto={}",dto);
            ListDriverContractSettleInfoDto driverContractSettleInfoDto = new ListDriverContractSettleInfoDto();
            BeanUtils.copyProperties(dto,driverContractSettleInfoDto);
            driverContractSettleInfoDto.setContractSettleId(dto.getId());
            log.info("driverContractSettleInfoDto={}",driverContractSettleInfoDto);
            if (PricingMethod.BEGIN_MILEAGE.equals(dto.getPricingMethod())){
                driverContractSettleInfoDto
                        .setBeginSettlePrice(dto.getBeginPrice());
            }else{
                List<CreateDriverContractSettleSectionDto> DriverContractSettleSectionDtoList = dto.getCreateDriverContractSettleSectionDto();
                if (!CollectionUtils.isEmpty(DriverContractSettleSectionDtoList)){
                    CreateDriverContractSettleSectionDto driverContractSettleSectionDto = DriverContractSettleSectionDtoList.get(0);
                    driverContractSettleInfoDto
                            .setUnit(driverContractSettleSectionDto.getUnit())
                            .setType(driverContractSettleSectionDto.getType())
                            .setBeginSettlePrice(driverContractSettleSectionDto.getSettlePrice());
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
    public List<ListDriverContractSettleDto> listTruckTeamContractPriceDetails(DriverContractSettleCondition condition) {
        condition.setFlag(2);
        return driverContractSettleRuleMapper.listTruckTeamContractPriceCondition(condition);
    }

    /**
     * 当前车辆类型所有的报价历史记录
     *
     * @param condition
     * @return
     */
    @Override
    public PageInfo<ListDriverContractSettleDto> listTruckTeamContractPriceHistoryDetails(DriverContractSettleCondition condition) {
        condition.setFlag(3);
        return new PageInfo(driverContractSettleRuleMapper.listTruckTeamContractPriceCondition(condition));
    }

    /**
     * 逻辑删除某一个类的车型
     *
     * @param condition
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeamContractPrice(DriverContractSettleCondition condition) {
        condition.setFlag(3);
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

        List<TruckTeamContractReturnDto> teamContractReturnDtos =  truckTeamContractMapper.listByTruckTeamId(truckTeamId);
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
        return truckTypeMapper.listAll(String.valueOf(goodType));
    }
}
