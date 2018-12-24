package com.jaagro.crm.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleMileage {
    /**
     * 结算里程表id
     */
    private Integer id;

    /**
     * 客户合同id
     */
    private Integer customerContractId;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 项目部名称
     */
    private String departmentName;

    /**
     * 装货地id
     */
    private Integer loadSiteId;

    /**
     * 装货地名称
     */
    private String loadSiteName;

    /**
     * 
     */
    private Integer unloadSiteId;

    /**
     * 卸货地名称
     */
    private String unloadSiteName;

    /**
     * 客户结算里程
     */
    private BigDecimal customerSettleMileage;

    /**
     * 司机结算里程
     */
    private BigDecimal driverSettleMileage;

    /**
     * 轨迹里程
     */
    private BigDecimal trackMileage;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Integer createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人id
     */
    private Integer modifyUserId;

    /**
     * 修改人姓名
     */
    private String modifyUserName;

    /**
     * 结算里程表id
     * @return id 结算里程表id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 结算里程表id
     * @param id 结算里程表id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 客户合同id
     * @return customer_contract_id 客户合同id
     */
    public Integer getCustomerContractId() {
        return customerContractId;
    }

    /**
     * 客户合同id
     * @param customerContractId 客户合同id
     */
    public void setCustomerContractId(Integer customerContractId) {
        this.customerContractId = customerContractId;
    }

    /**
     * 部门id
     * @return department_id 部门id
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * 部门id
     * @param departmentId 部门id
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 项目部名称
     * @return department_name 项目部名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 项目部名称
     * @param departmentName 项目部名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
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
     * 
     * @return unload_site_id 
     */
    public Integer getUnloadSiteId() {
        return unloadSiteId;
    }

    /**
     * 
     * @param unloadSiteId 
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
     * 客户结算里程
     * @return customer_settle_mileage 客户结算里程
     */
    public BigDecimal getCustomerSettleMileage() {
        return customerSettleMileage;
    }

    /**
     * 客户结算里程
     * @param customerSettleMileage 客户结算里程
     */
    public void setCustomerSettleMileage(BigDecimal customerSettleMileage) {
        this.customerSettleMileage = customerSettleMileage;
    }

    /**
     * 司机结算里程
     * @return driver_settle_mileage 司机结算里程
     */
    public BigDecimal getDriverSettleMileage() {
        return driverSettleMileage;
    }

    /**
     * 司机结算里程
     * @param driverSettleMileage 司机结算里程
     */
    public void setDriverSettleMileage(BigDecimal driverSettleMileage) {
        this.driverSettleMileage = driverSettleMileage;
    }

    /**
     * 轨迹里程
     * @return track_mileage 轨迹里程
     */
    public BigDecimal getTrackMileage() {
        return trackMileage;
    }

    /**
     * 轨迹里程
     * @param trackMileage 轨迹里程
     */
    public void setTrackMileage(BigDecimal trackMileage) {
        this.trackMileage = trackMileage;
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
     * 创建人id
     * @return create_user_id 创建人id
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人id
     * @param createUserId 创建人id
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建人姓名
     * @return create_user_name 创建人姓名
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 创建人姓名
     * @param createUserName 创建人姓名
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
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
     * 修改人id
     * @return modify_user_id 修改人id
     */
    public Integer getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 修改人id
     * @param modifyUserId 修改人id
     */
    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * 修改人姓名
     * @return modify_user_name 修改人姓名
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    /**
     * 修改人姓名
     * @param modifyUserName 修改人姓名
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }
}