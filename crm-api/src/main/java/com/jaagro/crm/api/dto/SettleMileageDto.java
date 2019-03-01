package com.jaagro.crm.api.dto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SettleMileageDto implements Serializable{

    private static final long serialVersionUID = 3746056208440115032L;
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
     * 卸货地id
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
}
