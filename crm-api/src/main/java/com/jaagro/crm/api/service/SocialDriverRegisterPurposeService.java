package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;

/**
 * 社会司机注册意向管理
 * @author yj
 * @since 2018/12/4
 */
public interface SocialDriverRegisterPurposeService {
    /**
     * 根据手机号查询
     * @param phoneNumber
     * @return
     */
    SocialDriverRegisterPurposeDto getByPhoneNumber(String phoneNumber);
}
