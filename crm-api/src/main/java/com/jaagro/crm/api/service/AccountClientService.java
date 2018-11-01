package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.account.CreateAccountDto;
import com.jaagro.crm.api.dto.request.account.QueryAccountDto;
import com.jaagro.crm.api.dto.request.account.UpdateAccountDto;
import com.jaagro.crm.api.dto.response.account.AccountReturnDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse insertAccount(@RequestBody  CreateAccountDto createAccountDto);

    /**
     * 修改账户
     * @param updateAccountDto
     * @return
     */
    @PutMapping("/account")
    public BaseResponse updateAccount(@RequestBody UpdateAccountDto updateAccountDto);

    /**
     * 删除账户
     * @param id
     * @return
     */
    @DeleteMapping("/account/{id}")
    public BaseResponse deleteAccount(@PathVariable("id") Integer id);

    /**
     * 查询账户
     *@param queryAccountDto
     * @return
     */
    @PostMapping("/getAccountDto")
    public AccountReturnDto getAccountDto(@RequestBody QueryAccountDto queryAccountDto);
}