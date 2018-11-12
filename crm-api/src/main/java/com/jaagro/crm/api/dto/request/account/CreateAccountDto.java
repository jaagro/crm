package com.jaagro.crm.api.dto.request.account;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *创建账户参数
 * @author yj
 * @date 2018/10/23
 */
@Data
@Accessors(chain = true)
public class CreateAccountDto implements Serializable{

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户类型1-客户 2-司机
     */
    private Integer userType;
    /**
     * 账户类型1-现金账户 2-保证金账户
     */
    private Integer accountType;
    /**
     * 创建人用户id
     */
    private Integer createUserId;
}
