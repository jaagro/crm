package com.jaagro.crm.biz.entity;

import java.math.BigDecimal;

public class TruckTeamContractSectionPrice {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer contractPriceId;

    /**
     * 1-重量阶梯报价 2-里程阶梯报价
     */
    private Integer sectionType;

    /**
     * 阶梯下限
     */
    private BigDecimal upperLimit;

    /**
     * 阶梯上限
     */
    private BigDecimal lowerLimit;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 是否有效 1-有效，0-无效
     */
    private Integer selectionStatus;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return contract_price_id 
     */
    public Integer getContractPriceId() {
        return contractPriceId;
    }

    /**
     * 
     * @param contractPriceId 
     */
    public void setContractPriceId(Integer contractPriceId) {
        this.contractPriceId = contractPriceId;
    }

    /**
     * 1-重量阶梯报价 2-里程阶梯报价
     * @return section_type 1-重量阶梯报价 2-里程阶梯报价
     */
    public Integer getSectionType() {
        return sectionType;
    }

    /**
     * 1-重量阶梯报价 2-里程阶梯报价
     * @param sectionType 1-重量阶梯报价 2-里程阶梯报价
     */
    public void setSectionType(Integer sectionType) {
        this.sectionType = sectionType;
    }

    /**
     * 阶梯下限
     * @return upper_limit 阶梯下限
     */
    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    /**
     * 阶梯下限
     * @param upperLimit 阶梯下限
     */
    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    /**
     * 阶梯上限
     * @return lower_limit 阶梯上限
     */
    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    /**
     * 阶梯上限
     * @param lowerLimit 阶梯上限
     */
    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    /**
     * 单价
     * @return price 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 单价
     * @param price 单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 是否有效 1-有效，0-无效
     * @return selection_status 是否有效 1-有效，0-无效
     */
    public Integer getSelectionStatus() {
        return selectionStatus;
    }

    /**
     * 是否有效 1-有效，0-无效
     * @param selectionStatus 是否有效 1-有效，0-无效
     */
    public void setSelectionStatus(Integer selectionStatus) {
        this.selectionStatus = selectionStatus;
    }
}