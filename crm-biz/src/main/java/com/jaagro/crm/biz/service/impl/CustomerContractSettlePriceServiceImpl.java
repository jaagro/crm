package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.base.QueryCustomerContractSettlePriceDto;
import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettlePriceDto;
import com.jaagro.crm.api.dto.request.contract.CreateSettleMileageDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettlePriceDto;
import com.jaagro.crm.api.service.CustomerContractSettlePriceService;
import com.jaagro.crm.api.service.SettleMileageService;
import com.jaagro.crm.biz.entity.CustomerContractSettlePrice;
import com.jaagro.crm.biz.mapper.CustomerContractSettlePriceMapperExt;
import com.jaagro.crm.biz.mapper.CustomerSiteMapperExt;
import com.jaagro.crm.biz.mapper.TruckTypeMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
@Service
@Slf4j
public class CustomerContractSettlePriceServiceImpl implements CustomerContractSettlePriceService {

    @Autowired
    private CustomerContractSettlePriceMapperExt settlePriceMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerSiteMapperExt siteMapper;
    @Autowired
    private TruckTypeMapperExt truckTypeMapper;
    @Autowired
    private SettleMileageService mileageService;

    /**
     * 创建客户合同报价
     *
     * @param settlePriceDto
     * @return
     */
    @Override
    public Boolean createCustomerSettlePrice(CreateCustomerSettlePriceDto settlePriceDto) {
        Boolean flag = false;
        if (StringUtils.isEmpty(settlePriceDto.getCustomerContractId())) {
            log.error("createCustomerSettlePrice 合同报价基础信息合同id为空");
            return flag;
        }
        if (settlePriceDto != null) {
            //新增合同报价
            CustomerContractSettlePrice settlePrice = new CustomerContractSettlePrice();
            BeanUtils.copyProperties(settlePriceDto, settlePrice);
            //修改之前的数据为历史数据
            QueryCustomerContractSettlePriceDto queryCustomerContractSettlePriceDto = new QueryCustomerContractSettlePriceDto();
            BeanUtils.copyProperties(settlePriceDto, queryCustomerContractSettlePriceDto);
            List<CustomerContractSettlePrice> contractSettlePrice = settlePriceMapper.getByCriteria(queryCustomerContractSettlePriceDto);
            if (!CollectionUtils.isEmpty(contractSettlePrice)) {
                for (CustomerContractSettlePrice price : contractSettlePrice) {
                    //将原来的记录改为历史记录
                    price
                            .setHistoryFlag(true)
                            .setModifyTime(new Date())
                            .setModifyUserId(userService.getCurrentUser().getId());
                    settlePriceMapper.updateByPrimaryKeySelective(price);
                }
            }
            settlePrice
                    .setCreateTime(new Date())
                    .setCreateUserId(userService.getCurrentUser().getId())
                    .setLoadSiteName(siteMapper.selectByPrimaryKey(settlePriceDto.getLoadSiteId()).getSiteName())
                    .setUnloadSiteName(siteMapper.selectByPrimaryKey(settlePriceDto.getUnloadSiteId()).getSiteName())
                    .setTruckTypeName(truckTypeMapper.getById(settlePriceDto.getTruckTypeId()).getTypeName());
            int result = settlePriceMapper.insertSelective(settlePrice);
            if (result > 0) {
                flag = true;
                //结算里程
                CreateSettleMileageDto createSettleMileageDto = new CreateSettleMileageDto();
                BeanUtils.copyProperties(settlePrice, createSettleMileageDto);


                return flag;
            }
        }
        return flag;
    }

    /**
     * 根据合同id获得装卸货地报价列表【不包括历史】
     *
     * @param contractId
     * @return
     */
    @Override
    public List<ReturnCustomerSettlePriceDto> listByContractId(Integer contractId) {
        return settlePriceMapper.listByContractId(contractId);
    }
}
