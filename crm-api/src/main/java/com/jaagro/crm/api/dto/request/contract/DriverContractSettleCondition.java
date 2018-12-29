package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author @Gao.
 */
@Data
@Accessors(chain = true)
public class DriverContractSettleCondition implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 车队合同id
     */
    private Integer truckTeamContractId;
    /**
     *运力合同配置主表id
     */
    private Integer contractSettleId;

    /**
     * 车辆类型id
     */
    private Integer truckTypeId;

    /**
     * 计价方式(1-按区间重量单价,2-按区间里程单价,3-按起步里程+里程单价)
     */
    private Integer pricingMethod;
    /**
     *
     */
    private Integer flag;
}
