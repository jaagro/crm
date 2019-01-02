package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleRuleDto;
import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleSectionRuleDto;
import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleTruckRuleDto;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleRuleDto;
import com.jaagro.crm.api.service.ContractOilPriceService;
import com.jaagro.crm.api.service.CtmContractSettleSectionRuleService;
import com.jaagro.crm.api.service.CtmContractSettleTruckService;
import com.jaagro.crm.api.service.CustomerContractSettleRuleService;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.entity.CustomerContractSettleRule;
import com.jaagro.crm.biz.mapper.CustomerContractMapperExt;
import com.jaagro.crm.biz.mapper.CustomerContractSettleRuleMapperExt;
import com.jaagro.crm.biz.mapper.TruckTypeMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/26
 */
@Service
@Slf4j
public class CustomerContractSettleRuleServiceImpl implements CustomerContractSettleRuleService {

    @Autowired
    private CustomerContractSettleRuleMapperExt settleRuleMapperExt;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CtmContractSettleTruckService settleTruckService;
    @Autowired
    private CtmContractSettleSectionRuleService settleSectionRuleService;
    @Autowired
    private CustomerContractMapperExt contractMapperExt;
    @Autowired
    private ContractOilPriceService contractOilPriceService;
    @Autowired
    private TruckTypeMapperExt truckTypeMapperExt;

    /**
     * 创建结算配制
     *
     * @param settleRuleDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createSettleRule(CreateCustomerSettleRuleDto settleRuleDto, CreateContractOilPriceDto oilPriceDto) {
        Boolean oilPrice = contractOilPriceService.createOilPrice(oilPriceDto);
        if (oilPrice != null && oilPrice) {
            return createCustomerSettleRule(settleRuleDto);
        }
        return Boolean.FALSE;
    }

    /**
     * 根据合同id获得结算
     *
     * @param contractId
     * @return
     */
    @Override
    public ReturnCustomerSettleRuleDto getByContractId(Integer contractId) {
        ReturnCustomerSettleRuleDto ruleDto = settleRuleMapperExt.getByContractId(contractId);
        if (ruleDto != null) {
            ruleDto
                    .setSectionRuleDtoList(settleSectionRuleService.listByRuleId(ruleDto.getId()))
                    .setTruckRuleDtoList(settleTruckService.listByRuleId(ruleDto.getId()));
        }
        return ruleDto;
    }

    private Boolean createCustomerSettleRule(CreateCustomerSettleRuleDto settleRuleDto) {
        Boolean flag = false;
        if (StringUtils.isEmpty(settleRuleDto.getCustomerContractId())) {
            throw new RuntimeException("createSettleRule 创建结算配制合同id不能为空");
        }
        if (StringUtils.isEmpty(settleRuleDto.getStartMileage())) {
            throw new RuntimeException("createSettleRule 创建结算配制起始里程不能为空");
        }
        if (StringUtils.isEmpty(settleRuleDto.getEndMileage())) {
            throw new RuntimeException("createSettleRule 创建结算配制结束里程不能为空");
        }
        CustomerContractSettleRule settleRule = new CustomerContractSettleRule();
        BeanUtils.copyProperties(settleRuleDto, settleRule);
        //货物类型为毛鸡
        CustomerContract customerContract = contractMapperExt.selectByPrimaryKey(settleRuleDto.getCustomerContractId());
        if (customerContract == null) {
            throw new RuntimeException("createSettleRule 创建结算配制合同不存在");
        }
        if (customerContract.getType().equals(ProductType.CHICKEN)) {
            if (CollectionUtils.isEmpty(settleRuleDto.getTruckRuleDtoList())) {
                throw new RuntimeException("毛鸡类型的合同里程区间配制不能为空");
            }
        }
        //查询是否存在纪录
        ReturnCustomerSettleRuleDto ruleDto = settleRuleMapperExt.getByContractId(settleRuleDto.getCustomerContractId());
        if (ruleDto != null) {
            CustomerContractSettleRule rule = new CustomerContractSettleRule();
            BeanUtils.copyProperties(ruleDto, rule);
            //将已存在的记录设置为历史 && 截止日期设置为当前日期
            rule
                    .setInvalidTime(new Date())
                    .setHistoryFlag(true)
                    .setModifyTime(new Date())
                    .setModifyUserId(userService.getCurrentUser().getId());
            settleRuleMapperExt.updateByPrimaryKeySelective(rule);
            //若有历史纪录 ==将新纪录的开始日期设置为当前日期
            settleRule
                    .setEffectiveTime(new Date())
                    .setInvalidTime(customerContract.getEndDate());
        } else {
            //若无历史纪录 ==将新纪录的开始日期设置为合同的生效日期
            settleRule
                    .setEffectiveTime(customerContract.getStartDate())
                    .setInvalidTime(customerContract.getEndDate());
        }
        //将新纪录新增
        settleRule
                .setCreateUserId(userService.getCurrentUser().getId())
                .setCreateTime(new Date());
        int result = settleRuleMapperExt.insertSelective(settleRule);
        if (result > 0 && settleRule.getId() != null && settleRule.getId() > 0) {
            //里程区间
            if (!CollectionUtils.isEmpty(settleRuleDto.getSectionRuleDtoList())) {
                for (CreateCustomerSettleSectionRuleDto sectionRuleDto : settleRuleDto.getSectionRuleDtoList()) {
                    sectionRuleDto
                            .setCustomerContractId(settleRuleDto.getCustomerContractId())
                            .setCustomerContractSettleRuleId(settleRule.getId());
                    Boolean sectionResult = settleSectionRuleService.createSectionRule(sectionRuleDto);
                    if (!sectionResult) {
                        throw new RuntimeException("createSectionRule 创建里程区间失败");
                    }
                }
            }
            //车辆设置
            if (!CollectionUtils.isEmpty(settleRuleDto.getTruckRuleDtoList())) {
                for (CreateCustomerSettleTruckRuleDto truckRuleDto : settleRuleDto.getTruckRuleDtoList()) {
                    truckRuleDto
                            .setTruckTypeName(truckTypeMapperExt.selectByPrimaryKey(truckRuleDto.getTruckTypeId()).getTypeName())
                            .setCustomerContractId(settleRule.getCustomerContractId())
                            .setCustomerContractSettleRuleId(settleRule.getId());
                    Boolean truckResult = settleTruckService.createSettleTruck(truckRuleDto);
                    if (!truckResult) {
                        throw new RuntimeException("createSectionRule 创建车辆配制失败");
                    }
                }
            }
            flag = true;
        }
        return flag;
    }
}
