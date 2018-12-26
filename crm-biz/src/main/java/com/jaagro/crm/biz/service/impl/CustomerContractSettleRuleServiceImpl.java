package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSettleRuleDto;
import com.jaagro.crm.api.service.CtmContractSettleSectionRuleService;
import com.jaagro.crm.api.service.CtmContractSettleTruckService;
import com.jaagro.crm.api.service.CustomerContractSettleRuleService;
import com.jaagro.crm.biz.mapper.CustomerContractSettleRuleMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
            log.error("createSettleRule 创建里程区间配制合同id不能为空");
            return flag;
        }
        //里程区间
        if (!CollectionUtils.isEmpty(settleRuleDto.getSectionRuleDtoList())) {

        }
        //车辆设置
        if (!CollectionUtils.isEmpty(settleRuleDto.getTruckRuleDtoList())) {

        }
        return flag;
    }
}
