package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 审核证件照帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class CheckCustomerQualificationDto implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 证件状态(-1；审核未通过 0；未审核 1；已审核)
     */
    private Integer certificateStatus;

    /**
     * 审核不通过描述信息(1、客户姓名与图片不符
     * 2、客户身份证号码与图片不符
     * 3、客户姓名与身份证号与合同信息不符)
     */
    private String description;

}
