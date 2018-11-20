package com.jaagro.crm.biz.service;

import com.jaagro.crm.api.dto.request.message.CreateMessageDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: @Gao.
 */
@FeignClient(value = "tms")
public interface MessageClientService {
    /**
     * 创建消息
     * @param createMessageDto
     * @return
     */
    @PostMapping("/createMessage")
    BaseResponse createMessage(@RequestBody @Validated CreateMessageDto createMessageDto);
}
