package com.jaagro.crm.api.dto.request.league;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 新增养殖体验意向
 * @author: @Gao.
 * @create: 2019-04-03 16:55
 **/
@Data
@Accessors(chain = true)
public class CreateTenantPurposeDto implements Serializable {

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
     * 验证码
     */
    private String verificationCode;

    /**
     * 规模
     */
    private Integer scale;

    /**
     * 邮箱地址
     */
    private String emailAddress;

}
