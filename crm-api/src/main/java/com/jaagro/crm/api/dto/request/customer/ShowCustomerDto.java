package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ShowCustomerDto implements Serializable {

    private Integer id;

    /**
     *
     */
    private Integer tenantId;

    /**
     * 客户名称(个体客户时，就是自然人姓名)
     */
    private String customerName;

    /**
     * 客户类型
     */
    private Integer customerType;


    /**
     * 客户是否直接下单
     */
    private String enableDirectOrder;

    /**
     * 城市
     */
    private String city;

    /**
     * 省
     */
    private String province;

    /**
     * 区
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;
}
