package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.base.QueryCustomerContractSettlePriceDto;
import com.jaagro.crm.api.dto.request.contract.CreateCustomerSettlePriceDto;
import com.jaagro.crm.api.dto.request.contract.CreateSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.UpdateCustomerContractSettlePriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettlePriceDto;
import com.jaagro.crm.api.service.CustomerContractSettlePriceService;
import com.jaagro.crm.api.service.SettleMileageService;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.entity.CustomerContractSettlePrice;
import com.jaagro.crm.biz.mapper.CustomerContractMapperExt;
import com.jaagro.crm.biz.mapper.CustomerContractSettlePriceMapperExt;
import com.jaagro.crm.biz.mapper.CustomerSiteMapperExt;
import com.jaagro.crm.biz.mapper.TruckTypeMapperExt;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CustomerContractMapperExt contractMapperExt;
    @Autowired
    private SettleMileageService settleMileageService;

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
            CustomerContract customerContract = contractMapperExt.selectByPrimaryKey(settlePriceDto.getCustomerContractId());
            if (!CollectionUtils.isEmpty(contractSettlePrice)) {
                for (CustomerContractSettlePrice price : contractSettlePrice) {
                    //将已存在的记录设置为历史 && 截止日期设置为当前日期
                    price
                            .setInvalidTime(new Date())
                            .setHistoryFlag(true)
                            .setModifyTime(new Date())
                            .setModifyUserId(userService.getCurrentUser().getId());
                    settlePriceMapper.updateByPrimaryKeySelective(price);
                }
                //若有历史纪录 ==将新纪录的开始日期设置为当前日期
                settlePrice
                        .setEffectiveTime(new Date());
            } else {
                //若无历史纪录 ==将新纪录的开始日期设置为合同的生效日期
                settlePrice
                        .setEffectiveTime(customerContract.getStartDate());
            }
            settlePrice
                    .setCreateTime(new Date())
                    .setInvalidTime(customerContract.getEndDate())
                    .setCreateUserId(userService.getCurrentUser().getId())
                    .setLoadSiteName(siteMapper.selectByPrimaryKey(settlePriceDto.getLoadSiteId()).getSiteName())
                    .setUnloadSiteName(siteMapper.selectByPrimaryKey(settlePriceDto.getUnloadSiteId()).getSiteName())
                    .setTruckTypeName(truckTypeMapper.getById(settlePriceDto.getTruckTypeId()).getTypeName());
            int result = settlePriceMapper.insertSelective(settlePrice);
            if (result > 0) {
                flag = true;
                //结算里程
                CreateSettleMileageDto settleMileageDto = new CreateSettleMileageDto();
                BeanUtils.copyProperties(settlePrice, settleMileageDto);
                settleMileageDto.setCustomerSettleMileage(settlePriceDto.getMileage());
                flag = settleMileageService.createSettleMileage(settleMileageDto);
                if (flag) {
                    return flag;
                }
                flag = false;
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

    @Override
    public Map<String, Object> disableSettlePrice(Integer priceId) {
        CustomerContractSettlePrice settlePrice = settlePriceMapper.selectByPrimaryKey(priceId);
        if (settlePrice == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "结算信息不存在");
        }
        settlePrice
                .setEnable(false)
                .setModifyTime(new Date())
                .setModifyUserId(userService.getCurrentUser().getId());
        int result = settlePriceMapper.updateByPrimaryKeySelective(settlePrice);
        if (result > 0) {
            return ServiceResult.toResult("删除成功");
        }
        return ServiceResult.error("删除失败");
    }

    /**
     * 修改合同结算信息
     *
     * @param priceDto
     * @return
     */
    @Override
    public Map<String, Object> updateSettlePrice(UpdateCustomerContractSettlePriceDto priceDto) {
        CustomerContractSettlePrice settlePrice = settlePriceMapper.selectByPrimaryKey(priceDto.getId());
        if (settlePrice == null) {
            return ServiceResult.error("合同报价结算信息不存在");
        }
        CustomerContract customerContract = contractMapperExt.selectByPrimaryKey(settlePrice.getCustomerContractId());
        if (customerContract == null) {
            return ServiceResult.error("合同信息有误");
        }
        //查询是否有历史记录
        QueryCustomerContractSettlePriceDto queryDto = new QueryCustomerContractSettlePriceDto();
        queryDto
                .setCustomerContractId(customerContract.getId())
                .setLoadSiteId(settlePrice.getLoadSiteId())
                .setUnloadSiteId(settlePrice.getUnloadSiteId());
        if (!customerContract.getType().equals(ProductType.CHICKEN)) {
            queryDto.setTruckTypeId(settlePrice.getTruckTypeId());
        }
        List<CustomerContractSettlePrice> prices = settlePriceMapper.getByCriteria(queryDto);
        if (!CollectionUtils.isEmpty(prices)) {
            for (CustomerContractSettlePrice price : prices) {
                price.setHistoryFlag(true)
                        .setModifyTime(new Date())
                        .setModifyUserId(userService.getCurrentUser().getId());
                settlePriceMapper.updateByPrimaryKeySelective(price);
            }
        }
        BeanUtils.copyProperties(priceDto, settlePrice);
        settlePrice
                .setMileage(priceDto.getSettlePrice())
                .setCreateUserId(userService.getCurrentUser().getId())
                .setCreateTime(new Date());
        int result = settlePriceMapper.insertSelective(settlePrice);
        if (result > 0) {
            return ServiceResult.toResult("修改成功");
        }
        return ServiceResult.error("修改失败");
    }

}
