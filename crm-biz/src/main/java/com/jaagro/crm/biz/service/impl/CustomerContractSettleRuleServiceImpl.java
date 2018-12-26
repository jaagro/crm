package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSectionRuleDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettleRuleDto;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettleTruckRuleDto;
import com.jaagro.crm.api.service.CtmContractSettleSectionRuleService;
import com.jaagro.crm.api.service.CtmContractSettleTruckService;
import com.jaagro.crm.api.service.CustomerContractSettleRuleService;
import com.jaagro.crm.biz.entity.CustomerContractSettleRule;
import com.jaagro.crm.biz.mapper.CustomerContractSettleRuleMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /**
     * 创建结算配制
     *
     * @param settleRuleDto
     * @return
     */
    @Override
    public Boolean createSettleRule(CreateCustomerSettleRuleDto settleRuleDto) {
        Boolean flag = false;
        if (StringUtils.isEmpty(settleRuleDto.getCustomerContractId())) {
            log.error("createSettleRule 创建结算配制合同id不能为空");
            return flag;
        }
        if (StringUtils.isEmpty(settleRuleDto.getStartMileage())) {
            log.error("createSettleRule 创建结算配制起始里程不能为空");
            return flag;
        }
        if (StringUtils.isEmpty(settleRuleDto.getEndMileage())) {
            log.error("createSettleRule 创建结算配制结束里程不能为空");
            return flag;
        }
        CustomerContractSettleRule settleRule = new CustomerContractSettleRule();
        BeanUtils.copyProperties(settleRuleDto, settleRule);
        settleRule
                .setCreateUserId(userService.getCurrentUser().getId())
                .setCreateTime(new Date());
        int result = settleRuleMapperExt.insertSelective(settleRule);
        if (result > 0) {
            flag = true;
            return flag;
        }
        //里程区间
        if (!CollectionUtils.isEmpty(settleRuleDto.getSectionRuleDtoList())) {
            for (CreateCustomerSectionRuleDto sectionRuleDto : settleRuleDto.getSectionRuleDtoList()) {
                Boolean sectionResult = settleSectionRuleService.createSectionRule(sectionRuleDto);
                if (!sectionResult) {
                    log.error("createSectionRule 创建里程区间失败");
                    return flag;
                }
            }
        }
        //车辆设置
        if (!CollectionUtils.isEmpty(settleRuleDto.getTruckRuleDtoList())) {
            for (CreateCustomerSettleTruckRuleDto truckRuleDto : settleRuleDto.getTruckRuleDtoList()) {
                Boolean truckResult = settleTruckService.createSettleTruck(truckRuleDto);
                if (!truckResult) {
                    log.error("createSectionRule 创建车辆配制失败");
                    return flag;
                }
            }
        }
        return flag;
    }
}
