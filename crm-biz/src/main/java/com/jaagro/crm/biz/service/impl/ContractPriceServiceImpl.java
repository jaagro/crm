package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;
import com.jaagro.crm.api.service.ContractPriceService;
import com.jaagro.crm.api.service.ContractSectionPriceService;
import com.jaagro.crm.biz.entity.CustomerContractPrice;
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
    public Map<String, Object> disableByContractId(Integer id) {
        List<ReturnContractPriceDto> priceReturnDto = this.priceMapper.getByContractId(id);
        for (ReturnContractPriceDto dto : priceReturnDto) {
            CustomerContractPrice price = new CustomerContractPrice();
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
