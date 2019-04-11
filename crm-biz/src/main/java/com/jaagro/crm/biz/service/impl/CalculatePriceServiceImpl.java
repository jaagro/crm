package com.jaagro.crm.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.PricingMethod;
import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.contract.CalculatePaymentDto;
import com.jaagro.crm.api.dto.request.contract.QuerySettleRuleDto;
import com.jaagro.crm.biz.entity.SettleMileage;
import com.jaagro.crm.api.service.CalculatePriceService;
import com.jaagro.crm.biz.entity.*;
import com.jaagro.crm.biz.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author tony
 */

@Service
@Slf4j
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

    @Autowired
    private CustomerContractMapperExt customerContractMapperExt;
    @Autowired
    private TruckTeamContractMapperExt truckTeamContractMapperExt;

    /**
     * 与客户结算的计算
     *
     * @param dtoList
     * @return 结算金额
     */
    @Override
    public List<Map<Integer, BigDecimal>> calculatePaymentFromCustomer(List<CalculatePaymentDto> dtoList) {
        List<Map<Integer, BigDecimal>> returnList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dtoList)) {
            log.info("O calculatePaymentFromCustomer dtoList isEmpty");
            return returnList;
        }
        log.info("O calculatePaymentFromCustomer begin *****************");
        for (CalculatePaymentDto calculatePaymentDto : dtoList) {
            log.info("O calculatePaymentFromCustomer calculatePaymentDto={}",calculatePaymentDto);
            Integer contractId = calculatePaymentDto.getCustomerContractId();
            // 校验合同状态,合同未审核通过不计算报价
            if (!checkContract(calculatePaymentDto)) {
                continue;
            }

            Map<Integer, BigDecimal> map = new HashMap<>();
            BigDecimal paymentMoney;
            BigDecimal unitPrice;
            BigDecimal actualMileage;
            BigDecimal minLoadWeight = new BigDecimal(0.00);
            List<CustomerContractSettlePrice> contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractId, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());
            if(contractSettlePriceList.size()< calculatePaymentDto.getSiteDtoList().size()){
                continue;
            }
            int compare = 0;
            int productType = calculatePaymentDto.getProductType();
            QuerySettleRuleDto querySettleRuleDto;
            CustomerContractSettleTruckRule customerContractSettleTruckRule;
            switch (productType) {

                //毛鸡结算
                case 1:
                    log.info("O calculatePaymentFromCustomer 毛鸡结算 begin");
                    paymentMoney = new BigDecimal(0.00);

                    //根据合同id、装货地Id,卸货地id获取实际公里数,一装多卸时最远里程作为实际公里数
                    List<SettleMileage> mileageList = settleMileageMapperExt.getSettleMileageList(contractId, calculatePaymentDto.getSiteDtoList());

                    if (mileageList.size() >= calculatePaymentDto.getSiteDtoList().size()) {

                        unitPrice = new BigDecimal(0.00);
                        //根据合同id、装货地Id,卸货地id和运单完成时间获取里程区间重量单价
                        actualMileage = new BigDecimal(0.00);
                        contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractId, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                        for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                            if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                actualMileage = customerContractSettlePrice.getMileage();
                            }
                        }
                        for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                            if (actualMileage.compareTo(customerContractSettlePrice.getMileage()) == 0) {
                                unitPrice = customerContractSettlePrice.getSettlePrice();
                                break;
                            }
                        }

                        //根据合同id、车型id和运单完成时间获取车型的最小装载量
                        minLoadWeight = new BigDecimal(0.00);
                        BigDecimal calculateWeight = new BigDecimal(0.00);
                        querySettleRuleDto = new QuerySettleRuleDto();
                        querySettleRuleDto.setCustomerContractId(contractId);
                        querySettleRuleDto.setTruckTypeId(calculatePaymentDto.getTruckTypeId());
                        querySettleRuleDto.setDoneDate(calculatePaymentDto.getDoneDate());
                        customerContractSettleTruckRule = customerContractSettleTruckRuleMapperExt.getSettleTruckRuleByCriteria(querySettleRuleDto);
                        if (null != customerContractSettleTruckRule) {
                            minLoadWeight = customerContractSettleTruckRule.getMinLoad();
                        }
                        compare = calculatePaymentDto.getCustomerCalWeight().compareTo(minLoadWeight);
                        if (compare == -1) {
                            calculateWeight = minLoadWeight;
                        } else {
                            calculateWeight = calculatePaymentDto.getCustomerCalWeight();
                        }
                        //结算金额 = 结算重量（吨）✕ 区间重量单价（元/吨）
                        paymentMoney = calculateWeight.multiply(unitPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                        map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    } else {
                        map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    }
                    returnList.add(map);
                    log.info("O calculatePaymentFromCustomer 毛鸡结算 end");
                    break;
                //饲料结算(只有饲料有一装多卸，其它都是一装一卸即均按一装一卸计算)
                case 2:
                    log.info("O calculatePaymentFromCustomer 饲料结算 begin");
                    paymentMoney = new BigDecimal(0.00);
                    //根据合同id、装货地Id,卸货地id、车型id和运单完成时间获取结算单价(元/吨)
                    unitPrice = new BigDecimal(0.00);
                    actualMileage = new BigDecimal(0.00);
                    contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractId, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if (calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                actualMileage = customerContractSettlePrice.getMileage();
                            }
                        }
                    }
                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if (calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
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
                        compare = calculatePaymentDto.getCustomerCalWeight().compareTo(minLoadWeight);
                        if (compare == -1) {
                            unitPrice = unitPrice.multiply(normalWeight).divide(minLoadWeight, 6, BigDecimal.ROUND_HALF_UP);

                        }
                    }
                    //结算金额 = 结算重量（吨）✕ 结算单价（元/吨）
                    paymentMoney = calculatePaymentDto.getCustomerCalWeight().multiply(unitPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    log.info("O calculatePaymentFromCustomer 饲料结算 end");
                    break;
                //仔猪结算
                case 5:
                    log.info("O calculatePaymentFromCustomer 仔猪结算 begin");
                    paymentMoney = new BigDecimal(0.00);
                    //根据合同id、装货地Id,卸货地id获取实际公里数
                    actualMileage = new BigDecimal(00.00);
                    //根据合同Id,车型id和运单完成时间获取车型的价格基数,因为不同的车型结算单价不同
                    unitPrice = new BigDecimal(0.00);
                    contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractId, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if (calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (customerContractSettlePrice.getMileage() != null && customerContractSettlePrice.getMileage().compareTo(actualMileage) == 1) {
                                actualMileage = customerContractSettlePrice.getMileage();
                            }
                        }
                    }
                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if (calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
                            if (actualMileage.compareTo(customerContractSettlePrice.getMileage()) == 0) {
                                unitPrice = customerContractSettlePrice.getSettlePrice();
                                break;
                            }
                        }
                    }

                    //结算金额=里程数（公里）✕ 数量（头）✕ 结算基数（元/头/公里）
                    paymentMoney = actualMileage.multiply(new BigDecimal(calculatePaymentDto.getCustomerCalQuantity())).multiply(unitPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    log.info("O calculatePaymentFromCustomer 仔猪结算 end");
                    break;

                //生猪(商品猪)结算
                case 6:
                case 3:
                case 4:
                    log.info("O calculatePaymentFromCustomer 生猪(商品猪)结算 begin");
                    paymentMoney = new BigDecimal(0.00);
                    //根据合同id、装货地Id,卸货地id获取实际里程数:一装多卸时取最大里程作为实际里程数
                    actualMileage = new BigDecimal(0.00);
                    contractSettlePriceList = customerContractSettlePriceMapperExt.getSectionWeightPrice(contractId, calculatePaymentDto.getDoneDate(), calculatePaymentDto.getSiteDtoList());

                    for (CustomerContractSettlePrice customerContractSettlePrice : contractSettlePriceList) {
                        if (calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {
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
                        if (calculatePaymentDto.getTruckTypeId().equals(customerContractSettlePrice.getTruckTypeId())) {

                            unitPrice = customerContractSettlePrice.getSettlePrice();
                            break;

                        }
                    }
                    //结算金额=里程数（公里）✕ 结算单价（元/公里
                    paymentMoney = actualMileage.multiply(unitPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    ;
                    map.put(calculatePaymentDto.getWaybillId(), paymentMoney);
                    returnList.add(map);
                    log.info("O calculatePaymentFromCustomer 生猪(商品猪)结算 end");
                    break;
                default:
                    System.out.println("没有匹配的货物类型");
                    break;

            }

        }
        log.info("O calculatePaymentFromCustomer end *****************");
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
        List<Map<Integer, BigDecimal>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(dtoList)) {
            log.info("O calculatePaymentToDriver dtoList isEmpty");
            return result;
        }
        for (CalculatePaymentDto calculatePaymentDto : dtoList) {
            log.info("O calculatePaymentToDriver calculatePaymentDto={}", JSON.toJSONString(calculatePaymentDto));
            try {
                // 校验合同状态,合同未审核通过不计算报价
                if (!checkContract(calculatePaymentDto)) {
                    continue;
                }
                // 获取合同装卸货地里程,一装多卸取最远里程
                List<SettleMileage> settleMileageList = settleMileageMapperExt.getSettleMileageList(calculatePaymentDto.getCustomerContractId(), calculatePaymentDto.getSiteDtoList());
                if (CollectionUtils.isEmpty(settleMileageList)) {
                    log.warn("O calculatePaymentToDriver settleMileageList isEmpty calculatePaymentDto={}", calculatePaymentDto);
                    continue;
                }
                if (settleMileageList.size() < calculatePaymentDto.getSiteDtoList().size()) {
                    log.warn("O calculatePaymentToDriver settleMileage is not enough calculatePaymentDto={}", calculatePaymentDto);
                    continue;
                }
                // 结算里程
                BigDecimal mileage = new BigDecimal("0");
                for (SettleMileage settleMileage : settleMileageList) {
                    BigDecimal driverSettleMileage = settleMileage.getDriverSettleMileage();
                    if (driverSettleMileage != null && driverSettleMileage.compareTo(mileage) == 1) {
                        mileage = driverSettleMileage;
                    }
                }
                // 获取司机合同结算配制信息
                DriverContractSettleRule driverContractSettleRule = driverContractSettleRuleMapperExt.selectEffectiveOne(calculatePaymentDto.getTruckTeamContractId(), calculatePaymentDto.getTruckTypeId(), calculatePaymentDto.getDoneDate());
                if (driverContractSettleRule == null) {
                    log.warn("O calculatePaymentToDriver driverContractSettleRule isNull calculatePaymentDto={}", calculatePaymentDto);
                    continue;
                }
                // 当司机里程小于最小结算里程,以最小结算里程计算
                if (driverContractSettleRule.getMinSettleMileage() != null && mileage.compareTo(driverContractSettleRule.getMinSettleMileage()) < 0) {
                    mileage = driverContractSettleRule.getMinSettleMileage();
                }
                // 实际里程为零不计算报价
                if (mileage.compareTo(BigDecimal.ZERO) == 0) {
                    log.warn("O calculatePaymentToDriver mileage isZero calculatePaymentDto={}", calculatePaymentDto);
                    continue;
                }
                // 按区间重量单价,按区间里程单价
                if (!driverContractSettleRule.getPricingMethod().equals(PricingMethod.BEGIN_MILEAGE)) {
                    // 获取里程区间
                    DriverContractSettleSectionRule effectiveSection = null;
                    List<DriverContractSettleSectionRule> settleSectionRuleList = driverContractSettleSectionRuleMapperExt.listByDriverContractSettleRuleId(driverContractSettleRule.getId());
                    if (CollectionUtils.isEmpty(settleSectionRuleList)) {
                        log.warn("O calculatePaymentToDriver settleSectionRuleList isEmpty calculatePaymentDto={}", calculatePaymentDto);
                        continue;
                    }
                    for (DriverContractSettleSectionRule driverContractSettleSectionRule : settleSectionRuleList) {
                        if (mileage.compareTo(driverContractSettleSectionRule.getStart()) == 1 && mileage.compareTo(driverContractSettleSectionRule.getEnd()) <= 0) {
                            effectiveSection = driverContractSettleSectionRule;
                        }
                    }
                    // 饲料结算(结算金额 = 结算重量（吨）✕ 区间重量单价（元/吨）最小结算重量：实际重量数小于最小重量时，按最小重量结算。)
                    if (effectiveSection == null) {
                        log.warn("O calculatePaymentToDriver effectiveSection isEmpty calculatePaymentDto={}", calculatePaymentDto);
                        continue;
                    }
                    if (ProductType.FODDER.equals(calculatePaymentDto.getProductType())) {
                        BigDecimal settleWeight = calculatePaymentDto.getDriverCalWeight();
                        if (calculatePaymentDto.getDriverCalWeight() != null && effectiveSection.getSettlePrice() != null) {
                            if (settleWeight.compareTo(driverContractSettleRule.getMinSettleWeight()) < 0) {
                                settleWeight = driverContractSettleRule.getMinSettleWeight();
                            }
                            BigDecimal settlePrice = settleWeight.multiply(effectiveSection.getSettlePrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            Map<Integer, BigDecimal> settlePriceMap = new HashMap<>(2);
                            settlePriceMap.put(calculatePaymentDto.getWaybillId(), settlePrice);
                            result.add(settlePriceMap);
                        }
                    } else {
                        // 毛鸡/仔猪/生猪结算(结算金额 = 里程（公里）✕ 区间里程单价（元/公里）,最小结算里程：实际里程数小于最小里程时，按最小里程结算。)
                        BigDecimal settlePrice = mileage.multiply(effectiveSection.getSettlePrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        Map<Integer, BigDecimal> settlePriceMap = new HashMap<>(2);
                        settlePriceMap.put(calculatePaymentDto.getWaybillId(), settlePrice);
                        result.add(settlePriceMap);
                    }
                    // 按起步里程+里程单价,结算金额 = 起步价（元）+ 结算单价 ✕（实际里程 - 起小里程）
                } else {
                    if (driverContractSettleRule.getBeginMileage() != null && mileage.compareTo(driverContractSettleRule.getBeginMileage()) < 0) {
                        BigDecimal settlePrice = driverContractSettleRule.getBeginPrice();
                        Map<Integer, BigDecimal> settlePriceMap = new HashMap<>(2);
                        settlePriceMap.put(calculatePaymentDto.getWaybillId(), settlePrice);
                        result.add(settlePriceMap);
                    } else {
                        if (driverContractSettleRule.getBeginPrice() != null && driverContractSettleRule.getBeginMileage() != null && driverContractSettleRule.getMileagePrice() != null) {
                            BigDecimal settlePrice = driverContractSettleRule.getBeginPrice().add(mileage.subtract(driverContractSettleRule.getBeginMileage()).multiply(driverContractSettleRule.getMileagePrice())).setScale(2, BigDecimal.ROUND_HALF_UP);
                            Map<Integer, BigDecimal> settlePriceMap = new HashMap<>(2);
                            settlePriceMap.put(calculatePaymentDto.getWaybillId(), settlePrice);
                            result.add(settlePriceMap);
                        } else {
                            log.warn("O calculatePaymentToDriver driverContractSettleRule value is not enough,calculatePaymentDto={}", calculatePaymentDto);
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                log.error("O calculatePaymentToDriver error calculatePaymentDto={}", calculatePaymentDto);
            }

        }
        return result;
    }

    /**
     * 根据客户装卸货地实际里程获取结算单价
     *
     * @param mileage
     * @param customerContractId
     * @return
     */
    @Override
    public BigDecimal calculatePriceFromMileageSection(Integer customerContractId, BigDecimal mileage) {
        BigDecimal price = customerContractSettleSectionRuleMapperExt.getPriceByMileageAndContractId(customerContractId, mileage);
        if (price == null) {
            throw new RuntimeException("未能查询到该里程的报价");
        }
        return price;
    }

    /**
     * 合同报价根据车型获取价格基数
     *
     * @param customerContractId
     * @param truckTypeId
     * @return
     */
    @Override
    public BigDecimal calculatePriceFromTruckRule(Integer customerContractId, Integer truckTypeId) {
        CustomerContractSettleTruckRule truckRule = customerContractSettleTruckRuleMapperExt.getLatestTruckRule(customerContractId, truckTypeId);
        if (truckRule != null) {
            return truckRule.getPriceBase();
        }
        return null;
    }

    private boolean checkContract(CalculatePaymentDto calculatePaymentDto) {
        if (calculatePaymentDto.getCustomerContractId() == null && calculatePaymentDto.getTruckTeamContractId() == null) {
            return false;
        }
        if (calculatePaymentDto.getCustomerContractId() != null) {
            CustomerContract customerContract = customerContractMapperExt.selectByPrimaryKey(calculatePaymentDto.getCustomerContractId());
            if (customerContract == null) {
                return false;
            }
            if (!AuditStatus.NORMAL_COOPERATION.equals(customerContract.getContractStatus())) {
                return false;
            }
        }
        if ((calculatePaymentDto.getTruckTeamContractId() != null)) {
            TruckTeamContract truckTeamContract = truckTeamContractMapperExt.selectByPrimaryKey(calculatePaymentDto.getTruckTeamContractId());
            if (truckTeamContract == null) {
                return false;
            }
            if (!AuditStatus.NORMAL_COOPERATION.equals(truckTeamContract.getContractStatus())) {
                return false;
            }
        }
        return true;
    }
}
