package com.jaagro.crm.api.dto.response.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListCustomerDto implements Serializable {

    /**
     * 客户主键id
     */
    private Integer id;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String phone;

    /**
     * 客户类型
     * (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 审核状态
     */
    private Integer customerStatus;

}
