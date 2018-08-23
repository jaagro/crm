package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.contract.ReturnContractSectionPriceDto;
import com.jaagro.crm.api.service.ContractSectionPriceService;
import com.jaagro.crm.biz.entity.CustomerContractSectionPrice;
import com.jaagro.crm.biz.mapper.ContractSectionPriceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class ContractSectionPriceServiceImpl implements ContractSectionPriceService {

    @Autowired
    private ContractSectionPriceMapper sectionPriceMapper;

    @Override
    public Map<String, Object> disableByPriceId(Integer priceId) {
        List<ReturnContractSectionPriceDto> contractSectionPriceReturnDto = this.sectionPriceMapper.listByPriceId(priceId);
        for (ReturnContractSectionPriceDto returnDto : contractSectionPriceReturnDto
        ) {
            CustomerContractSectionPrice sectionPrice = new CustomerContractSectionPrice();
            BeanUtils.copyProperties(returnDto, sectionPrice);
            sectionPrice.setSelectionStatus(0);
            this.sectionPriceMapper.updateByPrimaryKeySelective(sectionPrice);
        }
        return ServiceResult.toResult("删除成功");
    }
}
