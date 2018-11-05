package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.contract.ReturnContractSectionPriceDto;
import com.jaagro.crm.api.service.ContractSectionPriceService;
import com.jaagro.crm.biz.entity.CustomerContractSectionPrice;
import com.jaagro.crm.biz.mapper.CustomerContractSectionPriceMapperExt;
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
@Service
@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "contractSectionPrice")
public class ContractSectionPriceServiceImpl implements ContractSectionPriceService {

    @Autowired
    private CustomerContractSectionPriceMapperExt sectionPriceMapper;

    @CacheEvict(cacheNames = "contractSectionPrice", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> disableByPriceId(Integer priceId) {
        List<ReturnContractSectionPriceDto> contractSectionPriceReturnDto = this.sectionPriceMapper.listByPriceId(priceId);
        for (ReturnContractSectionPriceDto returnDto : contractSectionPriceReturnDto
        ) {
            CustomerContractSectionPrice sectionPrice = new CustomerContractSectionPrice();
            BeanUtils.copyProperties(returnDto, sectionPrice);
            this.sectionPriceMapper.updateByPrimaryKeySelective(sectionPrice);
        }
        return ServiceResult.toResult("删除成功");
    }
}
