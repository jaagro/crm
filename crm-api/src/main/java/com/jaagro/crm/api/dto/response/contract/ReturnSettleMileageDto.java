package com.jaagro.crm.api.dto.response.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/29
 */
@Data
@Accessors(chain = true)
public class ReturnSettleMileageDto implements Serializable {

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
     * 关联公司
     */
    private String companyName;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 修改时间
     */
    private Date modifyTime;


}
