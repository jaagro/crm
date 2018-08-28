package com.jaagro.crm.api.dto.response.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客户审核需要的返回信息
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ReturnCustomerDto implements Serializable {
    /**
     * 客户主键id
     */
    private Integer id;

    /**
     * 客户名称(个体客户时，就是自然人姓名)
     */
    private String customerName;

    /**
     * 客户类型
     * (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 统一社会验证码(个体客户时，就是自然人身份证号码)
     */
    private String creditCode;

    /**
     * 审核状态
     * (0未审核，1-正常合作  10-停止合作 11-审核未通过 13-作废)
     */
    private Integer customerStatus;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属区县
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 备注信息(用车要求)
     */
    private String notes;

}
