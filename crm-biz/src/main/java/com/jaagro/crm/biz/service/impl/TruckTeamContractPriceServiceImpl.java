package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractPriceDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractSectionPriceDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamContractPriceReturnDto;
import com.jaagro.crm.api.service.TruckTeamContractPriceService;
import com.jaagro.crm.api.service.TruckTeamContractSectionPriceService;
import com.jaagro.crm.biz.entity.TruckTeamContractPrice;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapperExt;
import com.jaagro.crm.biz.mapper.TruckTeamContractPriceMapperExt;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class TruckTeamContractPriceServiceImpl implements TruckTeamContractPriceService {

    @Autowired
    private TruckTeamContractPriceMapperExt contractPriceMapper;
    @Autowired
    private TruckTeamContractMapperExt contractMapper;
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

    @Override
    public Map<String, Object> disableByContractId(Integer id) {
        List<TruckTeamContractPriceReturnDto> priceReturnDtoList = this.contractPriceMapper.listByContractId(id);
        if (priceReturnDtoList.size() > 0) {
            for (TruckTeamContractPriceReturnDto priceReturnDto : priceReturnDtoList
            ) {
                this.sectionPriceService.disableByPriceId(priceReturnDto.getId());
            }
        }
        this.contractPriceMapper.disableByContractId(id);
        return ServiceResult.toResult("删除成功");
    }

    @Override
    public Map<String, Object> deleteByContractId(Integer id) {
        this.contractPriceMapper.deleteByContractId(id);
        return ServiceResult.toResult("删除成功");
    }

}
