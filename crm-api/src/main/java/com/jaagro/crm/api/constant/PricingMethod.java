package com.jaagro.crm.api.constant;

/**
 * 司机合同结算配制计价方式
 * @author yj
 * @since 2018/12/25
 */
public final class PricingMethod {
    /**
     * 按区间重量(饲料)
     */
    public static final Integer SECTION_WEIGHT = 1;
    /**
     * 按区间里程(毛鸡/仔猪/生猪)
     */
    public static final Integer SECTION_MILEAGE = 2;
    /**
     * 按起步里程+里程单价(仔猪/生猪)
     */
    public static final Integer BEGIN_MILEAGE = 3;
}
