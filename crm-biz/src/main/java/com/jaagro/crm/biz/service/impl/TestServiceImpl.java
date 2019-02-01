package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.service.TestService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author yj
 * @date 2019/1/31 15:58
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    @Transactional
    public void testDelTransactional(String token, String wxId, String userId) {
        //redisTemplate.multi();
        redisTemplate.delete(token);
        redisTemplate.delete(wxId);
        if (userId.equals("ee")){
            throw new RuntimeException("抛出异常");
        }
        redisTemplate.delete(userId);
        //redisTemplate.exec();
    }

    @Override
    public void testDelTransactionUseOneConnection(String token, String wxId, String userId) {
        final String tokenF = token;
        final String wxIdF = wxId;
        final String userIdF = userId;
        Boolean execute = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Long delFromRedis = 0L;
            byte[] bytesToken = new byte[1];
            if (StringUtils.hasText(tokenF)){
                bytesToken = tokenF.getBytes();
                delFromRedis = delFromRedis+1;
            }
            byte[] bytesWxId = new byte[1];
            if (StringUtils.hasText(wxIdF)){
                bytesWxId = wxIdF.getBytes();
                delFromRedis = delFromRedis+1;
            }
            byte[] bytesUserId = new byte[1];
            if (StringUtils.hasText(userIdF)){
                bytesUserId = userIdF.getBytes();
                delFromRedis = delFromRedis+1;
            }
            Long del = connection.del(bytesToken, bytesWxId, bytesUserId);
            if (del != null && del.equals(delFromRedis)) {
                return true;
            }
            return false;
        });
        if (execute == null || !execute){
            log.warn("invalidateToken fail token={},userId={},wxId={}",token,userId,wxId);
        }
    }


}
