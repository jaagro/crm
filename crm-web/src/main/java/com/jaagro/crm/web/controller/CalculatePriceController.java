package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.contract.ListContractPriceCriteriaDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.BaseResponse;
import utils.ResponseStatusCode;
import utils.ServiceResult;

/**
 * @author tony
 */
@RestController
public class CalculatePriceController {

    @Autowired
    private CalculatePriceService calculatePriceService;

    @PostMapping("/calculatePrice")
    public BaseResponse calculatePrice(@RequestBody ListContractPriceCriteriaDto dto) {

        if (StringUtils.isEmpty(dto.getContractId())) {
            return BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "请选择计费合同编号"));
        }
        if (StringUtils.isEmpty(dto.getPricingType())) {
            return BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "请选择计价方式"));
        }
        if (StringUtils.isEmpty(dto.getProductType())) {
            return BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "请选择货物类型"));
        }

        return BaseResponse.service(calculatePriceService.calculatePrice(dto));
    }
}
