package com.jaagro.crm.api.dto.request.league;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 养殖意向
 * @author: @Gao.
 * @create: 2019-04-04 10:21
 **/
@Data
@Accessors(chain = true)
public class TenantPurposeDto implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 联系人名称
     */
    private String contractName;

    /**
     * 联系人手机号
     */
    private String phoneNumber;

    /**
     * 状态1-待处理2-已处理
     */
    private Integer status;

    /**
     * 状态1-待处理2-已处理
     */
    private String strStatus;

    /**
     * 邮箱地址
     */
    private String emailAddress;

    /**
     * 创建时间
     */
    private Date createTime;
}
