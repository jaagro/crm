package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客户联系人新增dto
 *
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CreateCustomerContractDto implements Serializable {

    /**
     * 外键关联客户ID(References customer)
     */
    private Long customerId;

    /**
     * 联系人员
     */
    private String contractName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态(0 停用 1 启用)
     */
    private Integer status;
}
