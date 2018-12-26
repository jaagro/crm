package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.customer.CreateCustomerSectionRuleDto;
import com.jaagro.crm.api.service.CtmContractSettleSectionRuleService;
import com.jaagro.crm.biz.entity.CustomerContractSettleSectionRule;
import com.jaagro.crm.biz.mapper.CustomerContractSettleSectionRuleMapperExt;
import com.jaagro.crm.biz.service.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/26
 */
@Service
@Slf4j
public class CtmContractSettleSectionRuleServiceImpl implements CtmContractSettleSectionRuleService {

    @Autowired
    private CustomerContractSettleSectionRuleMapperExt sectionRuleMapperExt;
    @Autowired
    private CurrentUserService userService;

    /**
     * 创建里程区间配制
     *
     * @param sectionRuleDto
     * @return
     */
    @Override
    public Boolean createSectionRule(CreateCustomerSectionRuleDto sectionRuleDto) {
        Boolean flag = false;
        CustomerContractSettleSectionRule sectionRule = new CustomerContractSettleSectionRule();
        BeanUtils.copyProperties(sectionRuleDto, sectionRule);
        sectionRule
                .setCreateUserId(userService.getCurrentUser().getId())
                .setCreateTime(new Date());
        int result = sectionRuleMapperExt.insertSelective(sectionRule);
        if (result > 0) {
            flag = true;
            return flag;
        }
        return flag;
    }
}
