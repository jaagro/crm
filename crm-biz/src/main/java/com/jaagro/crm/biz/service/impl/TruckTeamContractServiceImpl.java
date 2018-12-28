package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ContractStatus;
import com.jaagro.crm.api.constant.GoodsType;
import com.jaagro.crm.api.constant.PricingMethod;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.request.truck.*;
import com.jaagro.crm.api.dto.response.contract.ReturnContractQualificationDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTypeDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamContractReturnDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.DriverContractSettleRule;
import com.jaagro.crm.biz.entity.DriverContractSettleSectionRule;
import com.jaagro.crm.biz.entity.TruckTeam;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
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
        //创建运力合同报价
        if (null != dto.getCreateDriverContractSettleDto()) {
            truckTeamContractService.createTruckTeamContractPrice(dto, currentUser.getId(), truckTeamContract.getId());
        }
        return ServiceResult.toResult("合同创建成功");
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
     * @param dto
     * @author @Gao.
     */
    @Override
    public void createTruckTeamContractPrice(CreateTruckTeamContractDto dto, Integer userId, Integer contractId) {
        if (null != dto.getCreateDriverContractSettleDto()) {
            CreateDriverContractSettleDto driverContractSettleDto = dto.getCreateDriverContractSettleDto();
            if (null != driverContractSettleDto.getTruckTypeId()) {
                ListTruckTypeDto truckType = truckTypeMapper.getById(driverContractSettleDto.getTruckTypeId());
                DriverContractSettleCondition driverContractSettleCondition = new DriverContractSettleCondition();
                driverContractSettleCondition
                        .setTruckTeamContractId(contractId)
                        .setTruckTypeId(driverContractSettleDto.getTruckTypeId());
                DriverContractSettleParam driverContractSettleParam = new DriverContractSettleParam();
                driverContractSettleParam
                        .setTruckTeamContractId(contractId)
                        .setCreateUserId(userId)
                        .setTruckTypeId(driverContractSettleDto.getTruckTypeId())
                        .setTruckTypeName(truckType.getTypeName());
                //鸡车类型
                if (GoodsType.CHICKEN.equals(Integer.parseInt(truckType.getProductName()))) {
                    //按照区间里程单价
                    contractCapacitySettle(dto, driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
                }
                //饲料类型
                if (GoodsType.FODDER.equals(Integer.parseInt(truckType.getProductName()))) {
                    //按照区间重量单价
                    contractCapacitySettle(dto, driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
                }
                //仔猪 生猪类型
                boolean flag = (GoodsType.SOW.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.BOAR.equals(Integer.parseInt(truckType.getProductName()))
                        || GoodsType.PIGLET.equals(Integer.parseInt(truckType.getProductName())) || GoodsType.LIVE_PIG.equals(Integer.parseInt(truckType.getProductName())));
                if (flag) {
                    driverContractSettleCondition.setPricingMethodFlag(2);
                    DriverContractSettleRule driverContractSettleRule = driverContractSettleRuleMapper.listDriverContractSettleRuleByCondition(driverContractSettleCondition);
                    if (null != driverContractSettleRule) {
                        if (driverContractSettleDto.getPricingMethod().equals(driverContractSettleRule.getPricingMethod())) {
                            //按照区间里程单价 或者按照起步价
                            contractCapacitySettle(dto, driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
                        } else {
                            throw new NullPointerException("当前货物类型只能选择一种报价方式");
                        }
                    } else {
                        //按照区间里程单价 或者按照起步价
                        contractCapacitySettle(dto, driverContractSettleCondition, driverContractSettleDto, driverContractSettleParam);
                    }
                }
            }
        }
    }

    /**
     * 1-按区间重量单价,2-按区间里程单价,3-按照起步价
     *
     * @param dto
     * @param driverContractSettleCondition
     * @param driverContractSettleDto
     * @param driverContractSettleParam
     * @author @Gao.
     */

    private void contractCapacitySettle(CreateTruckTeamContractDto dto, DriverContractSettleCondition driverContractSettleCondition,
                                        CreateDriverContractSettleDto driverContractSettleDto, DriverContractSettleParam driverContractSettleParam) {
        driverContractSettleCondition
                .setPricingMethodFlag(1)
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
        if (PricingMethod.BEGIN_WEIGHT.equals(driverContractSettleDto.getPricingMethod())) {
            driverContractSettleParam
                    .setPricingMethod(PricingMethod.BEGIN_WEIGHT)
                    .setBeginMileage(driverContractSettleDto.getBeginMileage())
                    .setBeginPrice(driverContractSettleDto.getBeginPrice())
                    .setMileagePrice(driverContractSettleDto.getMileagePrice());
        }
        if (null == driverContractSettleRule) {
            driverContractSettleParam
                    .setEffectiveTime(dto.getStartDate())
                    .setInvalidTime(dto.getEndDate());
            if (judgeExpired(dto.getEndDate())) {
                throw new NullPointerException("合同截止日期应当小于当前时间");
            }
            saveDriverContractSettle(driverContractSettleDto, driverContractSettleParam, null);
        } else {
            boolean flag = (driverContractSettleDto.getPricingMethod().equals(driverContractSettleRule.getPricingMethod())
                    && driverContractSettleDto.getTruckTypeId().equals(driverContractSettleRule.getModifyUserId()));
            if (flag) {
                driverContractSettleParam
                        .setEffectiveTime(new Date())
                        .setTruckTeamContractId(driverContractSettleRule.getTruckTeamContractId())
                        .setInvalidTime(driverContractSettleRule.getInvalidTime());
                TruckTeamContract truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(driverContractSettleRule.getTruckTeamContractId());
                if (judgeExpired(truckTeamContract.getEndDate())) {
                    throw new NullPointerException("当前合同已经过期了");
                }
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
}
