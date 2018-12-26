package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;
import com.jaagro.crm.api.dto.request.contract.QuerySettleRuleDto;
import com.jaagro.crm.api.service.CalculatePriceService;
import com.jaagro.crm.biz.entity.CustomerContractSettlePrice;
import com.jaagro.crm.biz.entity.CustomerContractSettleSectionRule;
import com.jaagro.crm.biz.entity.CustomerContractSettleTruckRule;
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
            Integer contractid = calculatePaymentDto.getCustomerContractId();
            Map<Integer, BigDecimal> map = new HashMap<>();
            BigDecimal paymentMoney ;
            BigDecimal unitPrice ;
            BigDecimal actualMileage;
            BigDecimal minLoadWeight = new BigDecimal(0.00);
            List<CustomerContractSettlePrice> contractSettlePriceList;
            int compare = 0;
            int productType = calculatePaymentDto.getProductType();
            QuerySettleRuleDto querySettleRuleDto;
            CustomerContractSettleTruckRule customerContractSettleTruckRule;
            switch (productType) {

                //毛鸡结算
                case 1:
                    paymentMoney = new BigDecimal(0.00);
                    actualMileage = new BigDecimal(0.00);

                    //根据合同id、装货地Id,卸货地id获取实际公里数,一装多卸时最远里程作为实际公里数
                    List<SettleMileage> mileageList = settleMileageMapperExt.getSettleMileageList(contractid, calculatePaymentDto.getSiteDtoList());

                    if (mileageList.size() == calculatePaymentDto.getSiteDtoList().size()) {

                        //按里程从大到小排序
                        Collections.sort(mileageList, new Comparator<SettleMileage>() {
                            @Override
                            public int compare(SettleMileage o1, SettleMileage o2) {
                                return o1.getCustomerSettleMileage().compareTo(o2.getCustomerSettleMileage());
                            }
                        });
                        //取最大里程参与计算
                        actualMileage = mileageList.get(0).getCustomerSettleMileage();

                        for (SettleMileage settleMileage : mileageList) {
                            if (settleMileage.getCustomerSettleMileage() != null && settleMileage.getCustomerSettleMileage().compareTo(actualMileage) == 1) {
                                actualMileage = settleMileage.getCustomerSettleMileage();
                            }
                        }

                        //根据合同id、车型id和运单完成时间获取车型的最小装载量
                        minLoadWeight = new BigDecimal(0.00);
                        BigDecimal calculateWeight = new BigDecimal(0.00);
                        querySettleRuleDto = new QuerySettleRuleDto();
                        querySettleRuleDto.setCustomerContractId(contractid);
                        querySettleRuleDto.setTruckTypeId(calculatePaymentDto.getTruckTypeId());
                        querySettleRuleDto.setDoneDate(calculatePaymentDto.getDoneDate());
                        customerContractSettleTruckRule = customerContractSettleTruckRuleMapperExt.getSettleTruckRuleByCriteria(querySettleRuleDto);
                        if (null != customerContractSettleTruckRule) {
                            minLoadWeight = customerContractSettleTruckRule.getMinLoad();
                        }
                        compare = calculatePaymentDto.getUnloadWeight().compareTo(minLoadWeight);
                        if (compare == -1) {
                            calculateWeight = minLoadWeight;
                        } else {
                            calculateWeight = calculatePaymentDto.getUnloadWeight();
                        }

                        //根据合同id、实际公里数、和运单完成时间获取里程区间重量单价
                        unitPrice = new BigDecimal(0.00);

                        querySettleRuleDto = new QuerySettleRuleDto();
                        querySettleRuleDto.setCustomerContractId(contractid);
                        querySettleRuleDto.setActualMileage(actualMileage);
                        querySettleRuleDto.setDoneDate(calculatePaymentDto.getDoneDate());
                        CustomerContractSettleSectionRule customerContractSettleSectionRule = customerContractSettleSectionRuleMapperExt.getSettleSectionRuleByCriteria(querySettleRuleDto);
                        if (null != customerContractSettleSectionRule) {
                            unitPrice = customerContractSettleSectionRule.getSettlePrice();
                        }

                        //根据合同id、装货地Id,卸货地id和运单完成时间获取里程区间重量单价
                        contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractid, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                        for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                            if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                                if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                    actualMileage = customerContractSettlePrice.getMileage();
                                }
                            }
                        }
                        for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                            if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                                if (actualMileage.compareTo(customerContractSettlePrice.getMileage()) == 0) {
                                    unitPrice = customerContractSettlePrice.getSettlePrice();
                                    break;
                                }
                            }
                        }
                        //结算金额 = 结算重量（吨）✕ 区间重量单价（元/吨）
                        paymentMoney = calculateWeight.multiply(unitPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
                        map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    } else {
                        map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    }
                    returnList.add(map);
                    break;
                //饲料结算
                case 2:

                    //根据合同id、装货地Id,卸货地id、车型id和运单完成时间获取结算单价(元/吨)
                    unitPrice = new BigDecimal(0.00);
                    actualMileage = new BigDecimal(0.00);
                    contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractid, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                actualMileage = customerContractSettlePrice.getMileage();
                            }
                        }
                    }
                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (actualMileage.compareTo(customerContractSettlePrice.getMileage()) == 0) {
                                unitPrice = customerContractSettlePrice.getSettlePrice();
                                break;
                            }
                        }
                    }

                    //根据合同Id,车型id和运单完成时间获取车型的最小装载量、常用装载量
                    minLoadWeight = new BigDecimal(0.00);
                    BigDecimal normalWeight = new BigDecimal(0.00);
                    querySettleRuleDto = new QuerySettleRuleDto();
                    querySettleRuleDto.setCustomerContractId(calculatePaymentDto.getCustomerContractId());
                    querySettleRuleDto.setTruckTypeId(calculatePaymentDto.getTruckTypeId());
                    querySettleRuleDto.setDoneDate(calculatePaymentDto.getDoneDate());
                    customerContractSettleTruckRule = customerContractSettleTruckRuleMapperExt.getSettleTruckRuleByCriteria(querySettleRuleDto);

                    if (null != customerContractSettleTruckRule) {
                        minLoadWeight = customerContractSettleTruckRule.getMinLoad();
                        normalWeight = customerContractSettleTruckRule.getNormalLoad();
                    }
                    if (minLoadWeight != null && minLoadWeight.compareTo(new BigDecimal(0.00)) != 0) {
                        //当实际装载量 < 最小装载量时，结算单价=原结算单价*常用结算量 / 最小装载量；最小装载量设置为零时不限最小装载量
                        compare = calculatePaymentDto.getUnloadWeight().compareTo(minLoadWeight);
                        if (compare == -1) {
                            unitPrice = unitPrice.multiply(normalWeight.divide(minLoadWeight));
                        }
                    }
                    //结算金额 = 结算重量（吨）✕ 结算单价（元/吨）
                    paymentMoney = calculatePaymentDto.getUnloadWeight().multiply(unitPrice).setScale(2,BigDecimal.ROUND_HALF_UP);;
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    break;
                //仔猪结算
                case 5:
                    //根据合同id、装货地Id,卸货地id获取实际公里数
                    actualMileage = new BigDecimal(40.00);
                    //根据合同Id,车型id和运单完成时间获取车型的价格基数,因为不同的车型结算单价不同
                    unitPrice = new BigDecimal(0.00);
                    contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractid, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                actualMileage = customerContractSettlePrice.getMileage();
                            }
                        }
                    }
                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (actualMileage.compareTo(customerContractSettlePrice.getMileage()) == 0) {
                                unitPrice = customerContractSettlePrice.getSettlePrice();
                                break;
                            }
                        }
                    }

                    //结算金额=里程数（公里）✕ 数量（头）✕ 结算基数（元/头/公里）
                    paymentMoney = actualMileage.multiply(new BigDecimal(calculatePaymentDto.getUnloadQuantity())).multiply(unitPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    break;

                //生猪(商品猪)结算
                case 6:case 3: case 4:
                    //根据合同id、装货地Id,卸货地id获取实际里程数:一装多卸时取最大里程作为实际里程数
                    actualMileage = new BigDecimal(0.00);
                    contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractid, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                actualMileage = customerContractSettlePrice.getMileage();
                            }
                        }
                    }

                    //根据合同Id,车型id和运单完成时间获取车型的最小里程数
                    BigDecimal minMileage = new BigDecimal(0.00);
                    querySettleRuleDto = new QuerySettleRuleDto();
                    querySettleRuleDto.setCustomerContractId(calculatePaymentDto.getCustomerContractId());
                    querySettleRuleDto.setTruckTypeId(calculatePaymentDto.getTruckTypeId());
                    querySettleRuleDto.setDoneDate(calculatePaymentDto.getDoneDate());
                    customerContractSettleTruckRule = customerContractSettleTruckRuleMapperExt.getSettleTruckRuleByCriteria(querySettleRuleDto);

                    if (null != customerContractSettleTruckRule) {
                        minMileage = customerContractSettleTruckRule.getMinMileage();
                    }
                    //实际里程数低于最小里程数时按最小里程数结算
                    if (actualMileage.compareTo(minMileage) == -1) {
                        actualMileage = minMileage;
                    }
                    //根据合同Id,车型id和运单完成时间获取车型的价格基数,因为不同的车型结算单价不同
                    unitPrice = new BigDecimal(0.00);
                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if(calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (actualMileage.compareTo(customerContractSettlePrice.getMileage()) == 0) {
                                unitPrice = customerContractSettlePrice.getSettlePrice();
                                break;
                            }
                        }
                    }
                    //结算金额=里程数（公里）✕ 结算单价（元/公里
                    paymentMoney = actualMileage.multiply(unitPrice).setScale(2,BigDecimal.ROUND_HALF_UP);;
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    break;
                default:
                    System.out.println("没有匹配的货物类型");break;

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
            settleMileageMapperExt.getSettleMileageList(calculatePaymentDto.getCustomerContractId(), calculatePaymentDto.getSiteDtoList());
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
