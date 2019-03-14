package com.jaagro.crm.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Aaa {
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
     * 饲料类型：1-散装 2-袋装 (仅饲料情况下)
     */
    private Integer feedType;

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

    /**
     * 客户合同结算价格表id
     * @return id 客户合同结算价格表id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 客户合同结算价格表id
     * @param id 客户合同结算价格表id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 客户合同合同id
     * @return customer_contract_id 客户合同合同id
     */
    public Integer getCustomerContractId() {
        return customerContractId;
    }

    /**
     * 客户合同合同id
     * @param customerContractId 客户合同合同id
     */
    public void setCustomerContractId(Integer customerContractId) {
        this.customerContractId = customerContractId;
    }

    /**
     * 装货地id
     * @return load_site_id 装货地id
     */
    public Integer getLoadSiteId() {
        return loadSiteId;
    }

    /**
     * 装货地id
     * @param loadSiteId 装货地id
     */
    public void setLoadSiteId(Integer loadSiteId) {
        this.loadSiteId = loadSiteId;
    }

    /**
     * 装货地名称
     * @return load_site_name 装货地名称
     */
    public String getLoadSiteName() {
        return loadSiteName;
    }

    /**
     * 装货地名称
     * @param loadSiteName 装货地名称
     */
    public void setLoadSiteName(String loadSiteName) {
        this.loadSiteName = loadSiteName == null ? null : loadSiteName.trim();
    }

    /**
     * 卸货地id
     * @return unload_site_id 卸货地id
     */
    public Integer getUnloadSiteId() {
        return unloadSiteId;
    }

    /**
     * 卸货地id
     * @param unloadSiteId 卸货地id
     */
    public void setUnloadSiteId(Integer unloadSiteId) {
        this.unloadSiteId = unloadSiteId;
    }

    /**
     * 卸货地名称
     * @return unload_site_name 卸货地名称
     */
    public String getUnloadSiteName() {
        return unloadSiteName;
    }

    /**
     * 卸货地名称
     * @param unloadSiteName 卸货地名称
     */
    public void setUnloadSiteName(String unloadSiteName) {
        this.unloadSiteName = unloadSiteName == null ? null : unloadSiteName.trim();
    }

    /**
     * 车辆类型id(0-所有)
     * @return truck_type_id 车辆类型id(0-所有)
     */
    public Integer getTruckTypeId() {
        return truckTypeId;
    }

    /**
     * 车辆类型id(0-所有)
     * @param truckTypeId 车辆类型id(0-所有)
     */
    public void setTruckTypeId(Integer truckTypeId) {
        this.truckTypeId = truckTypeId;
    }

    /**
     * 车辆类型名称
     * @return truck_type_name 车辆类型名称
     */
    public String getTruckTypeName() {
        return truckTypeName;
    }

    /**
     * 车辆类型名称
     * @param truckTypeName 车辆类型名称
     */
    public void setTruckTypeName(String truckTypeName) {
        this.truckTypeName = truckTypeName == null ? null : truckTypeName.trim();
    }

    /**
     * 货物类型 1-毛鸡,2-饲料,3-仔猪,4-商品猪(包含生猪,公猪,母猪)
     * @return goods_type 货物类型 1-毛鸡,2-饲料,3-仔猪,4-商品猪(包含生猪,公猪,母猪)
     */
    public Integer getGoodsType() {
        return goodsType;
    }

    /**
     * 货物类型 1-毛鸡,2-饲料,3-仔猪,4-商品猪(包含生猪,公猪,母猪)
     * @param goodsType 货物类型 1-毛鸡,2-饲料,3-仔猪,4-商品猪(包含生猪,公猪,母猪)
     */
    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 饲料类型：1-散装 2-袋装 (仅饲料情况下)
     * @return feed_type 饲料类型：1-散装 2-袋装 (仅饲料情况下)
     */
    public Integer getFeedType() {
        return feedType;
    }

    /**
     * 饲料类型：1-散装 2-袋装 (仅饲料情况下)
     * @param feedType 饲料类型：1-散装 2-袋装 (仅饲料情况下)
     */
    public void setFeedType(Integer feedType) {
        this.feedType = feedType;
    }

    /**
     * 里程数
     * @return mileage 里程数
     */
    public BigDecimal getMileage() {
        return mileage;
    }

    /**
     * 里程数
     * @param mileage 里程数
     */
    public void setMileage(BigDecimal mileage) {
        this.mileage = mileage;
    }

    /**
     * 结算单价
     * @return settle_price 结算单价
     */
    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    /**
     * 结算单价
     * @param settlePrice 结算单价
     */
    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    /**
     * 单位 1-元/吨,2-元/头/公里,3-元/公里
     * @return unit 单位 1-元/吨,2-元/头/公里,3-元/公里
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * 单位 1-元/吨,2-元/头/公里,3-元/公里
     * @param unit 单位 1-元/吨,2-元/头/公里,3-元/公里
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * 生效时间(最小值为合同生效时间)
     * @return effective_time 生效时间(最小值为合同生效时间)
     */
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * 生效时间(最小值为合同生效时间)
     * @param effectiveTime 生效时间(最小值为合同生效时间)
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * 失效时间(最大值为合同失效时间)
     * @return invalid_time 失效时间(最大值为合同失效时间)
     */
    public Date getInvalidTime() {
        return invalidTime;
    }

    /**
     * 失效时间(最大值为合同失效时间)
     * @param invalidTime 失效时间(最大值为合同失效时间)
     */
    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    /**
     * 是否为历史配制 0-否,1-是
     * @return history_flag 是否为历史配制 0-否,1-是
     */
    public Boolean getHistoryFlag() {
        return historyFlag;
    }

    /**
     * 是否为历史配制 0-否,1-是
     * @param historyFlag 是否为历史配制 0-否,1-是
     */
    public void setHistoryFlag(Boolean historyFlag) {
        this.historyFlag = historyFlag;
    }

    /**
     * 是否有效：1-有效 0-无效
     * @return enable 是否有效：1-有效 0-无效
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 是否有效：1-有效 0-无效
     * @param enable 是否有效：1-有效 0-无效
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 修改时间
     * @return modify_time 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 修改人
     * @return modify_user_id 修改人
     */
    public Integer getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 修改人
     * @param modifyUserId 修改人
     */
    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
}