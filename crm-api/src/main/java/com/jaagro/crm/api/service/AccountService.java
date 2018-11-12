package com.jaagro.crm.api.service;

import java.util.List;

/**
 * 账户查询,操作
 * @author yj
 * @date 2018/10/26
 */
public interface AccountService {
    /**
     * 逻辑删除账户
     * @param userId
     * @param userType
     * @param accountType
     */
    void deleteAccount(Integer userId, Integer userType, Integer accountType);
    /**
     * 创建账户
     * @param userId
     * @param userType
     * @param accountType
     * @param createUserId
     */
    void createAccount(Integer userId, Integer userType, Integer accountType,Integer createUserId);
}
