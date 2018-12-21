package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author gavin
 */
@RestController
@Api(description = "结算管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculatePaymentController {

    @Autowired
    private CalculatePriceService calculatePriceService;

    /**
     * 与客户结算的计算
     * @param dtoList
     * @return 结算金额
     */
    @ApiOperation("客户结算")
    @PostMapping("/calculatePaymentFromCustomer")

    public BaseResponse calculatePaymentFromCustomer(@RequestBody @Validated List<CalculatePaymentDto> dtoList) {
        return BaseResponse.successInstance(calculatePriceService.calculatePaymentFromCustomer(dtoList));
    }
}
