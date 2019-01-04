package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yj
 * @since 20181224
 */
@Data
@Accessors(chain = true)
public class CustomerContractSettlePrice implements Serializable{
    /**
     * 客户合同结算价格表id
     */
    private Integer id;

    /**
     * 客户合同合同id
     */
    private Integer customerContractId;

    /**
     * 装货地id
     */
    private Integer loadSiteId;

    /**
     * 装货地名称
     */
    private String loadSiteName;

    /**
     * 卸货地id
     */
    private Integer unloadSiteId;

    /**
     * 卸货地名称
     */
    private String unloadSiteName;

    /**
     * 车辆类型id(0-所有)
     */
    private Integer truckTypeId;

    /**
     * 车辆类型名称
     */
    private String truckTypeName;

    /**
     * 货物类型 1-毛鸡,2-饲料,3-仔猪,4-商品猪(包含生猪,公猪,母猪)
     */
    private Integer goodsType;

    /**
     * 里程数
     */
    private BigDecimal mileage;

    /**
     * 结算单价
     */
    private BigDecimal settlePrice;

    /**
     * 单位 1-元/吨,2-元/头/公里,3-元/公里
     */
    private Integer unit;

    /**
     * 生效时间(最小值为合同生效时间)
     */
    private Date effectiveTime;

    /**
     * 失效时间(最大值为合同失效时间)
     */
    private Date invalidTime;

    /**
     * 是否为历史配制 0-否,1-是
     */
    private Boolean historyFlag;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

}