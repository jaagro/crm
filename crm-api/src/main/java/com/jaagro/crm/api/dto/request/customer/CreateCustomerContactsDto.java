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
public class CreateCustomerContactsDto implements Serializable {

    /**
     * 外键关联客户ID(References customer)
     */
    private Integer customerId;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

}
