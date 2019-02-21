package com.jaagro.crm.api.service;

/**
 * @author yj
 * @date 2019/1/31 15:57
 */
public interface TestService {
    void testDelTransactional(String token, String wxId, String userId);
    void testDelTransactionUseOneConnection(String token,String wxId,String userId);
}
