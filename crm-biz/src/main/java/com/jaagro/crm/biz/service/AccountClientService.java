package com.jaagro.crm.biz.service;

import com.jaagro.crm.api.dto.request.account.CreateAccountDto;
import com.jaagro.crm.api.dto.request.account.QueryAccountDto;
import com.jaagro.crm.api.dto.response.account.AccountReturnDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 账户远程操作
 * @author yj
 * @date 2018/10/25
 */
@FeignClient(value = "account")
public interface AccountClientService {
    /**
     * 创建账户
     * @param createAccountDto
     * @return
     */
    @PostMapping("/account")
    BaseResponse insertAccount(@RequestBody  CreateAccountDto createAccountDto);
    /**
     * 删除账户
     * @param id
     * @return
     */
    @DeleteMapping("/account/{id}")
    BaseResponse deleteAccount(@PathVariable("id") Integer id);

    /**
     * 查询账户
     *@param queryAccountDto
     * @return
     */
    @PostMapping("/getByQueryAccountDto")
    AccountReturnDto getByQueryAccountDto(@RequestBody QueryAccountDto queryAccountDto);
}