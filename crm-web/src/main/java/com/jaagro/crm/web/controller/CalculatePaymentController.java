package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.ValidList;
import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据客户装卸货地实际里程获取结算单价
     * @param customerContractId
     * @param mileage
     * @return 结算金额
     */
    @ApiOperation("根据客户装卸货地实际里程获取结算单价")
    @GetMapping("/calculatePriceFromMileageSection")
    public BaseResponse calculatePriceFromMileageSection(@RequestParam("customerContractId") Integer customerContractId,@RequestParam("mileage") BigDecimal mileage) {
        log.info("O calculatePriceFromMileageSection customerContractId={},mileage={}",customerContractId,mileage);
        return BaseResponse.successInstance(calculatePriceService.calculatePriceFromMileageSection(customerContractId,mileage));
    }

    /**
     * 合同报价根据车型获取价格基数
     * @param customerContractId
     * @param truckTypeId
     * @return 结算金额
     */
    @ApiOperation("合同报价根据车型获取价格基数")
    @GetMapping("/calculatePriceFromTruckRule")
    public BaseResponse calculatePriceFromTruckRule(@RequestParam("customerContractId") Integer customerContractId,@RequestParam("truckTypeId") Integer truckTypeId) {
        log.info("O calculatePriceFromTruckRule customerContractId={},truckTypeId={}",customerContractId,truckTypeId);
        return BaseResponse.successInstance(calculatePriceService.calculatePriceFromTruckRule(customerContractId,truckTypeId));
    }

}
