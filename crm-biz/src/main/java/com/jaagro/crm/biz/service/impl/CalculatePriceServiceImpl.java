package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import com.jaagro.crm.biz.entity.SettleMileage;
import com.jaagro.crm.biz.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author tony
 */

@Service
public class CalculatePriceServiceImpl implements CalculatePriceService {

    @Autowired
    private ContractOilPriceMapperExt contractOilPriceMapperExt;
    @Autowired
    private CustomerContractSettleRuleMapperExt customerContractSettleRuleMapperExt;
    @Autowired
    private CustomerContractSettleTruckRuleMapperExt customerContractSettleTruckRuleMapperExt;
    @Autowired
    private CustomerContractSettlePriceMapperExt customerContractSettlePriceMapperExt;
    @Autowired
    private CustomerContractSettleSectionRuleMapperExt customerContractSettleSectionRuleMapperExt;
    @Autowired
    private DriverContractSettleSectionRuleMapperExt driverContractSettleSectionRuleMapperExt;
    @Autowired
    private DriverContractSettleRuleMapperExt driverContractSettleRuleMapperExt;
    @Autowired
    private SettleMileageMapperExt settleMileageMapperExt;

    /**
     * 与客户结算的计算
     *
     * @param dtoList
     * @return 结算金额
     */
    @Override
    public List<Map<Integer, BigDecimal>> calculatePaymentFromCustomer(List<CalculatePaymentDto> dtoList) {
        List<Map<Integer, BigDecimal>> returnList = new ArrayList<>();
        for (CalculatePaymentDto calculatePaymentDto : dtoList) {
            Map<Integer, BigDecimal> map = new HashMap<>();
            BigDecimal paymentMoney = new BigDecimal(0.00);
            BigDecimal unitPrice = new BigDecimal(0.00);
            BigDecimal actualMileage = new BigDecimal(0.00);
            BigDecimal minLoadWeight = new BigDecimal(0.00);
            int compare = 0;
            int productType = calculatePaymentDto.getProductType();
            switch (productType) {

                //毛鸡结算
                case 1:
                    //根据合同id、装货地Id,卸货地id获取实际公里数
                    actualMileage = new BigDecimal(40.00);
                    List<SettleMileage> mileageList = settleMileageMapperExt.getSettleMileageList(calculatePaymentDto.getContractId(),calculatePaymentDto.getSiteDtoList());
                    if(mileageList.size()==calculatePaymentDto.getSiteDtoList().size()) {
                        //找出最远里程的
//                        Optional<SettleMileage> employeeOptional = mileageList.stream().max((e1, e2)->Integer.compare(e1.getCustomerSettleMileage(),e2.getCustomerSettleMileage()));

                        //根据合同id、车型id获取最小载重量
                        minLoadWeight = new BigDecimal(20.00);
                        compare = calculatePaymentDto.getUnloadWeight().compareTo(minLoadWeight);

                        //根据合同id、实际公里数、和运单完成时间获取区间重量单价
                        unitPrice = new BigDecimal(20.00);

                        //结算金额 = 结算重量（吨）✕ 区间重量单价（元/吨）
                        paymentMoney = map.put(calculatePaymentDto.getWaybillId(), paymentMoney);

                        returnList.add(map);
                    }
                    break;
                //饲料结算
                case 2:

                    //根据合同id、装货地Id,卸货地id和车型id获取结算单价(元/吨)
                    unitPrice = new BigDecimal(10.00);
                    //根据合同Id,车型id和运单完成时间获取车型的最小装载量、常用装载量
                    minLoadWeight = new BigDecimal(1.00);
                    BigDecimal normalWeight = new BigDecimal(0.00);

                    if (minLoadWeight != null && minLoadWeight.compareTo(new BigDecimal(0.00)) != 0) {
                        compare = calculatePaymentDto.getUnloadWeight().compareTo(minLoadWeight);
                        if (compare == -1) {
                            unitPrice = unitPrice.multiply(normalWeight.divide(minLoadWeight));
                        }
                    }
                    //结算金额 = 结算重量（吨）✕ 结算单价（元/吨）
                    paymentMoney = calculatePaymentDto.getUnloadWeight().multiply(unitPrice);
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    break;
                //仔猪结算
                case 5:
                    //根据合同id、装货地Id,卸货地id获取实际公里数
                     actualMileage = new BigDecimal(40.00);
                    //根据合同Id,车型id和运单完成时间获取车型的价格基数,因为不同的车型结算单价不同
                    unitPrice =  new BigDecimal(0.00);
                    //结算金额=里程数（公里）✕ 数量（头）✕ 结算基数（元/头/公里）
                    paymentMoney=actualMileage.multiply(new BigDecimal(calculatePaymentDto.getUnloadQuantity())).multiply(unitPrice);
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    break;

                //生猪(商品猪)结算
                case 6:
                    //根据合同id、装货地Id,卸货地id获取实际公里数
                    actualMileage = new BigDecimal(20.00);
                    //根据合同Id,车型id和运单完成时间获取车型的最小里程数
                    BigDecimal minMileage = new BigDecimal(10.00);

                    if(actualMileage.compareTo(minLoadWeight)==-1){
                        actualMileage = minLoadWeight;
                    }
                    //根据合同Id,车型id和运单完成时间获取车型的价格基数,因为不同的车型结算单价不同
                    unitPrice =  new BigDecimal(0.00);
                    //结算金额=里程数（公里）✕ 结算单价（元/公里
                    paymentMoney=actualMileage.multiply(unitPrice);
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    break;

            }

        }
        return returnList;
    }

    /**
     * 运力结算：与司机的结算
     *
     * @param dtoList
     * @return
     */
    @Override
    public List<Map<Integer, BigDecimal>> calculatePaymentToDriver(List<CalculatePaymentDto> dtoList) {
        for (CalculatePaymentDto calculatePaymentDto : dtoList) {
            // 1 获取合同装卸货地里程,一装多卸取最远里程
            settleMileageMapperExt.getSettleMileageList(calculatePaymentDto.getCustomerContractId(),calculatePaymentDto.getSiteDtoList());
            //饲料结算
            if (calculatePaymentDto.getProductType().equals(ProductType.CHICKEN)) {
                BigDecimal unitPrice = new BigDecimal(0.00);

            }
            //毛鸡结算
            if (calculatePaymentDto.getProductType().equals(ProductType.FODDER)) {
                BigDecimal unitPrice = new BigDecimal(0.00);
            }

            //仔猪/生猪结算：出租车模式
            if (calculatePaymentDto.getProductType().equals(ProductType.PIGLET) || calculatePaymentDto.getProductType().equals(ProductType.LIVE_PIG)) {

            }
        }
        return null;
    }
}
