package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CreateContractPriceDto implements Serializable {

    private static final long serialVersionUID = -9131615925507984571L;
    /**
     * 计价方式
     * 说明：
     * 1、按重量;
     * 2、按数量;
     * 3、按重量＊里程；
     * 4，按数量＊里程
     * Nullable
     */
    private Integer pricingType;

    /**
     * 发货地址id
     */
    private Integer loadSiteId;

    /**
     * 收货地址id
     */
    private Integer unloadSiteId;

    /**
     * 车辆类型
     */
    private Integer vehicleType;

    /**
     * 货物类型
     */
    private Integer productType;

    /**
     * 商品规格
     */
    private String specification;

    /**
     * 单价
     * Nullable
     */
    private BigDecimal price;

    /**
     * 是否有重量阶梯定价，0-非阶梯定价 1-有阶梯定价
     */
    private Integer hasWeightSectionValues;

    /**
     * 是否有里程阶梯定价，0-非阶梯定价  1-有阶梯定价
     */
    private Integer hasMileSectionValues;

    /**
     * 起步里程
     * 说明：用于设置里程的起步值，例：小于5km默认5km计算，0
     * 表示没有起步设置
     * Not Null
     */
    private BigDecimal minMile;

    /**
     * 起步重量
     * 说明：用于设置重量的起步值，例：小于5T按照5T计算，0
     * 表示没有起步设置
     * Not Null
     */
    private BigDecimal minWeight;

    /**
     * 起步运费
     */
    private BigDecimal minMoney;

    /**
     * 阶梯报价列表
     */
    private List<CreateContractSectionPriceDto> sectionPrice;
}
