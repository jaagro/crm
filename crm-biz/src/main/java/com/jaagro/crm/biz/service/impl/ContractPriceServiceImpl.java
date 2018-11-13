package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;
import com.jaagro.crm.api.service.ContractPriceService;
import com.jaagro.crm.api.service.ContractSectionPriceService;
import com.jaagro.crm.biz.entity.CustomerContractPrice;
import com.jaagro.crm.biz.mapper.CustomerContractPriceMapperExt;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@CacheConfig(keyGenerator = "wiselyKeyGenerator")
@Service
public class ContractPriceServiceImpl implements ContractPriceService {

    @Autowired
    private CustomerContractPriceMapperExt priceMapper;
    @Autowired
    private ContractSectionPriceService sectionPriceService;

    @CacheEvict(cacheNames = "customer", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> disableByContractId(Integer id) {
        List<ReturnContractPriceDto> priceReturnDto = this.priceMapper.getByContractId(id);
        for (ReturnContractPriceDto dto : priceReturnDto) {
            CustomerContractPrice price = new CustomerContractPrice();
            BeanUtils.copyProperties(dto, price);
            price.setPriceStatus(AuditStatus.STOP_COOPERATION);
            this.priceMapper.updateByPrimaryKeySelective(price);
            if (dto.getSectionPrice() != null && dto.getSectionPrice().size() > 0) {
                sectionPriceService.disableByPriceId(price.getId());
            }
        }
        return ServiceResult.toResult("删除成功");
    }
}
