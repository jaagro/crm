package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.contract.PriceCriteriaDto;
import com.jaagro.crm.api.dto.response.PriceReturnDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import com.jaagro.crm.biz.entity.ContractPrice;
import com.jaagro.crm.biz.mapper.ContractSectionPriceMapper;
import com.jaagro.crm.api.constant.PricingType;
import com.jaagro.crm.biz.entity.ContractSectionPrice;
import com.jaagro.crm.biz.mapper.ContractPriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utils.ResponseStatusCode;
import utils.ServiceResult;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author tony
 */
@Service
public class CalculatePriceServiceImpl implements CalculatePriceService {

    @Autowired
    private ContractPriceMapper contractPriceMapper;
    @Autowired
    private ContractSectionPriceMapper contractSectionPriceMapper;

    /**
     * 根据条件计算费用
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> calculatePrice(PriceCriteriaDto dto) {

        //按数量计价
        if (PricingType.QUANTITY.longValue() == dto.getPricingType()) {
            if (StringUtils.isEmpty(dto.getQuantity())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "数量不能为空");
            }
            ContractPrice cp = contractPriceMapper.getPriceByCriteria(dto);
            if (cp == null) {
                return ServiceResult.error("未找到相应的报价");
            }
            PriceReturnDto priceReturnDto = new PriceReturnDto();
            priceReturnDto
                    .setPrice(cp.getPrice())
                    .setMoney(cp.getPrice().multiply(new BigDecimal(dto.getQuantity())));
            return ServiceResult.toResult(priceReturnDto);
        }

        //按每车计价
        if (PricingType.VEHICLE.longValue() == dto.getPricingType()) {
            if (StringUtils.isEmpty(dto.getShippingAddressId())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "请选择收货地址");
            }
            if (StringUtils.isEmpty(dto.getPickupAddressId())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "请选择提货地址");
            }
            ContractPrice cp = contractPriceMapper.getPriceByCriteria(dto);
            if (cp == null) {
                return ServiceResult.error("未找到相应的报价");
            }
            PriceReturnDto priceReturnDto = new PriceReturnDto();
            priceReturnDto
                    .setMoney(cp.getMinMoney());
            return ServiceResult.toResult(priceReturnDto);
        }

        //按里程计价
        if (PricingType.MILEAGE.longValue() == dto.getPricingType()) {
            if (StringUtils.isEmpty(dto.getMileage())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "里程数不能为空");
            }
            ContractPrice cp = contractPriceMapper.getPriceByCriteria(dto);
            if (cp == null) {
                return ServiceResult.error("未找到相应的报价");
            }
            PriceReturnDto priceReturnDto = new PriceReturnDto();
            priceReturnDto
                    .setPrice(cp.getPrice())
                    .setMoney(cp.getPrice().multiply(dto.getMileage()));
            return ServiceResult.toResult(priceReturnDto);
        }

        //按起步里程数加价
        if (PricingType.MILEAGE_RAISE_PRICE.longValue() == dto.getPricingType()) {
            if (StringUtils.isEmpty(dto.getMileage())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "里程数不能为空");
            }
            ContractPrice cp = contractPriceMapper.getPriceByCriteria(dto);
            if (cp == null) {
                return ServiceResult.error("未找到相应的报价");
            }
            PriceReturnDto priceReturnDto = new PriceReturnDto();
            int compare = cp.getMinMile().compareTo(dto.getMileage());
            if (compare == 1 || compare == 0) {
                priceReturnDto.setMoney(cp.getMinMoney());
                return ServiceResult.toResult(priceReturnDto);
            }
            if (compare == -1) {
                BigDecimal collectMoneyMile = dto.getMileage().subtract(cp.getMinMile());
                BigDecimal collectMoney = cp.getMinMoney().add(cp.getPrice().multiply(collectMoneyMile));
                priceReturnDto
                        .setPrice(cp.getPrice())
                        .setMoney(collectMoney);
                return ServiceResult.toResult(priceReturnDto);
            }
        }

        //按里程区间计价
        if (PricingType.MILEAGE_SUBSECTION_PRICE.longValue() == dto.getPricingType()) {
            if (StringUtils.isEmpty(dto.getMileage())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "里程数不能为空");
            }
            ContractPrice cp = contractPriceMapper.getPriceByCriteria(dto);
            if (cp == null) {
                return ServiceResult.error("未找到相应的报价");
            }
            ContractSectionPrice csp = contractSectionPriceMapper.getByLimit(cp.getId(), dto.getMileage());
            if (csp == null) {
                return ServiceResult.error("未找到相应的区间报价");
            }
            PriceReturnDto priceReturnDto = new PriceReturnDto();
            BigDecimal countMoney = dto.getMileage().multiply(csp.getPrice());
            int compare = countMoney.compareTo(cp.getMinMoney());
            if (compare == 1) {
                priceReturnDto
                        .setPrice(csp.getPrice())
                        .setMoney(countMoney);
                return ServiceResult.toResult(priceReturnDto);
            }
            if (compare == 0 || compare == -1) {
                priceReturnDto.setMoney(cp.getMinMoney());
                return ServiceResult.toResult(priceReturnDto);
            }
        }

        //按重量计价
        if (PricingType.WEIGHT.longValue() == dto.getPricingType()) {
            if (StringUtils.isEmpty(dto.getWeight())) {
                return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "重量不能为空");
            }
            ContractPrice cp = contractPriceMapper.getPriceByCriteria(dto);
            if (cp == null) {
                return ServiceResult.error("未找到相应的报价");
            }
            PriceReturnDto priceReturnDto = new PriceReturnDto();
            priceReturnDto
                    .setPrice(cp.getPrice())
                    .setMoney(cp.getPrice().multiply(dto.getWeight()));
            return ServiceResult.toResult(priceReturnDto);
        }
        return ServiceResult.error("条件不正确，未找到相应的报价");
    }

}
