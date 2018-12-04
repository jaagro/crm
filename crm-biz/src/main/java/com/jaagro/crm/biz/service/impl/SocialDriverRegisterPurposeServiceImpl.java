package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;
import com.jaagro.crm.biz.mapper.SocialDriverRegisterPurposeMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 社会司机注册意向管理
 * @author yj
 * @since 2018/12/4
 */
@Service
@Slf4j
public class SocialDriverRegisterPurposeServiceImpl implements SocialDriverRegisterPurposeService {
    @Autowired
    private SocialDriverRegisterPurposeMapperExt socialDriverRegisterPurposeMapperExt;
    /**
     * 是否存在社会司机
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public Boolean existSocialDriver(String phoneNumber) {
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
