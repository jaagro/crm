package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.account.CreateAccountDto;
import com.jaagro.crm.api.dto.request.account.QueryAccountDto;
import com.jaagro.crm.api.dto.response.account.AccountReturnDto;
import com.jaagro.crm.api.service.AccountClientService;
import com.jaagro.crm.api.service.AccountService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户查询, 操作
 * @author yj
 * @date 2018/10/26
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountClientService accountClientService;
    /**
     * 逻辑删除账户
     *
     * @param userId
     * @param userType
     * @param accountType
     */
    @Override
    public void deleteAccount(Integer userId, Integer userType, Integer accountType) {
        QueryAccountDto queryAccountDto = new QueryAccountDto();
        queryAccountDto
                .setAccountType(accountType)
                .setUserId(userId)
                .setUserType(userType);
        AccountReturnDto accountDto = accountClientService.getByQueryAccountDto(queryAccountDto);
        if (accountDto != null && accountDto.getId() != null){
            BaseResponse baseResponse = accountClientService.deleteAccount(accountDto.getId());
            if (ResponseStatusCode.OPERATION_SUCCESS.getCode() != baseResponse.getStatusCode()){
                log.warn("deleteAccount fail,id={}",accountDto.getId());
                throw new RuntimeException("删除账户失败");
            }
        }else{
            log.warn("account not exist,{}",queryAccountDto);
        }
    }

    /**
     * 创建账户
     *
     * @param userId
     * @param userType
     * @param accountType
     */
    @Override
    public void createAccount(Integer userId, Integer userType, Integer accountType,Integer createUserId) {
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto
                .setUserType(userType)
                .setUserId(userId)
                .setAccountType(accountType)
                .setCreateUserId(createUserId);
        BaseResponse baseResponse = accountClientService.insertAccount(createAccountDto);
        if(ResponseStatusCode.OPERATION_SUCCESS.getCode() != baseResponse.getStatusCode()){
            log.warn("createAccount fail createAccountDto={},baseResponse={}",createAccountDto,baseResponse);
            throw new RuntimeException("创建账户失败");
        }
    }

}
