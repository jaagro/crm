package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.contract.ContractPriceReturnDto;
import com.jaagro.crm.api.service.ContractPriceService;
import com.jaagro.crm.api.service.ContractSectionPriceService;
import com.jaagro.crm.biz.entity.ContractPrice;
import com.jaagro.crm.biz.mapper.ContractPriceMapper;
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
public class ContractPriceServiceImpl implements ContractPriceService {

    @Autowired
    private ContractPriceMapper priceMapper;
    @Autowired
    private ContractSectionPriceService sectionPriceService;

    @Override
    public Map<String, Object> disableByContractId(Long id) {
        List<ContractPriceReturnDto> priceReturnDto = this.priceMapper.getByContractId(id);
        for (ContractPriceReturnDto dto : priceReturnDto) {
            ContractPrice price = new ContractPrice();
            BeanUtils.copyProperties(dto, price);
            price.setPriceStatus(0);
            this.priceMapper.updateByPrimaryKeySelective(price);
            if (dto.getSectionPrice() != null && dto.getSectionPrice().size() > 0) {
                sectionPriceService.disableByPriceId(price.getId());
            }
        }
        return ServiceResult.toResult("删除成功");
    }
}
