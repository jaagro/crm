package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettleTruckRuleDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettleTruckRuleDto;
import com.jaagro.crm.api.service.CtmContractSettleTruckService;
import com.jaagro.crm.biz.entity.CustomerContractSettleTruckRule;
import com.jaagro.crm.biz.mapper.CustomerContractSettleTruckRuleMapperExt;
import com.jaagro.crm.biz.mapper.TruckTypeMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2018/12/26
 */
@Service
@Slf4j
public class CtmContractSettleTruckServiceImpl implements CtmContractSettleTruckService {

    @Autowired
    private CustomerContractSettleTruckRuleMapperExt truckRuleMapperExt;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private TruckTypeMapperExt truckTypeMapperExt;

    /**
     * 创建车辆配制
     *
     * @param settleRuleDto
     * @return
     */
    @Override
    public Boolean createSettleTruck(CreateCustomerSettleTruckRuleDto truckRuleDto) {
        Boolean flag = false;
        CustomerContractSettleTruckRule truckRule = new CustomerContractSettleTruckRule();
        BeanUtils.copyProperties(truckRuleDto, truckRule);
        truckRule
                .setCreateUserId(userService.getCurrentUser().getId())
                .setCreateTime(new Date());
        int result = truckRuleMapperExt.insertSelective(truckRule);
        if (result > 0) {
            flag = true;
            return flag;
        }
        return flag;
    }

    /**
     * 根据配置结算主表id获得列表
     *
     * @param id
     * @return
     */
    @Override
    public List<ReturnCustomerSettleTruckRuleDto> listByRuleId(Integer id) {
        List<ReturnCustomerSettleTruckRuleDto> truckRuleDtoList = truckRuleMapperExt.listByRuleId(id);
        if (!CollectionUtils.isEmpty(truckRuleDtoList)) {
            for (ReturnCustomerSettleTruckRuleDto truckRuleDto : truckRuleDtoList) {
                truckRuleDto.setTruckTypeName(truckTypeMapperExt.selectByPrimaryKey(truckRuleDto.getTruckTypeId()).getTypeName());
            }
        }
        return truckRuleDtoList;
    }

}
