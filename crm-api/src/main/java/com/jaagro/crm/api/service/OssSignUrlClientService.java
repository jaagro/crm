package com.jaagro.crm.api.service;

import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tony
 */
@FeignClient("component")
public interface OssSignUrlClientService {

    /**
     * 获取oss图片地址
     * @param filePath
     * @return
     */
    @PostMapping("/listSignedUrl")
    BaseResponse listSignedUrl(@RequestParam("filePath") String[] filePath);
}
