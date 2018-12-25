package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.ValidList;
import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author gavin
 */
@RestController
@Api(description = "结算管理", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
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
    public List<Map<Integer, BigDecimal>> calculatePaymentFromCustomer(@RequestBody @Validated ValidList<CalculatePaymentDto> dtoList) {
        log.info("O calculatePaymentFromCustomer param={}",dtoList);
        return calculatePriceService.calculatePaymentFromCustomer(dtoList);
    }

    /**
     * 与司机结算的计算
     * @param dtoList
     * @return 结算金额
     */
    @ApiOperation("司机结算")
    @PostMapping("/calculatePaymentFromDriver")
    public List<Map<Integer, BigDecimal>> calculatePaymentFromDriver(@RequestBody @Validated ValidList<CalculatePaymentDto> dtoList) {
        log.info("O calculatePaymentFromDriver param={}",dtoList);
        return calculatePriceService.calculatePaymentToDriver(dtoList);
    }
}
