package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractPriceDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractSectionPriceDto;
import com.jaagro.crm.api.service.TruckTeamContractPriceService;
import com.jaagro.crm.api.service.TruckTeamContractSectionPriceService;
import com.jaagro.crm.biz.entity.TruckTeamContractPrice;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.crm.biz.mapper.TruckTeamContractPriceMapper;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class TruckTeamContractPriceServiceImpl implements TruckTeamContractPriceService {

    @Autowired
    private TruckTeamContractPriceMapper contractPriceMapper;
    @Autowired
    private TruckTeamContractMapper contractMapper;
    @Autowired
    private TruckTeamContractSectionPriceService sectionPriceService;

    @Override
    public Map<String, Object> createPrice(CreateTruckTeamContractPriceDto createContractPriceDto) {
        TruckTeamContractPrice contractPrice = new TruckTeamContractPrice();
        BeanUtils.copyProperties(createContractPriceDto, contractPrice);
        this.contractPriceMapper.insertSelective(contractPrice);
        if (createContractPriceDto.getContractSectionPriceDtoList() != null && createContractPriceDto.getContractSectionPriceDtoList().size() > 0) {
            for (CreateTruckTeamContractSectionPriceDto contractSectionPriceDto : createContractPriceDto.getContractSectionPriceDtoList()
            ) {
                contractSectionPriceDto.setContractPriceId(contractPrice.getId());
                this.sectionPriceService.createSectionPrice(contractSectionPriceDto);
            }
        }
        return ServiceResult.toResult("新增成功");
    }

}
