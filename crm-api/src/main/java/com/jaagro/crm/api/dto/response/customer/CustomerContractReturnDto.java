package com.jaagro.crm.api.dto.response.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回的联系人
 *
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CustomerContractReturnDto implements Serializable {

    /**
     * 客户审核记录主键id
     */
    private Long id;

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
    private String mobile;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态(0 停用 1 启用)
     */
    private Boolean status;
}
