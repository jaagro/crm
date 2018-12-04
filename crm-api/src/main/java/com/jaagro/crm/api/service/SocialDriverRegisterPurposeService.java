package com.jaagro.crm.api.service;

/**
 * 社会司机注册意向管理
 * @author yj
 * @since 2018/12/4
 */
public interface SocialDriverRegisterPurposeService {
    /**
     * 是否存在社会司机
     * @param phoneNumber
     * @return
     */
    Boolean existSocialDriver(String phoneNumber);
}
