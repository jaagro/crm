package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;
import com.jaagro.crm.biz.mapper.SocialDriverRegisterPurposeMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
     * 根据手机号查询
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public SocialDriverRegisterPurposeDto getByPhoneNumber(String phoneNumber) {
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null){
            SocialDriverRegisterPurposeDto socialDriverRegisterPurposeDto = new SocialDriverRegisterPurposeDto();
            BeanUtils.copyProperties(socialDriverRegisterPurpose,socialDriverRegisterPurposeDto);
            if (socialDriverRegisterPurpose.getUploadTime() == null){
                socialDriverRegisterPurposeDto.setUploadTime(socialDriverRegisterPurpose.getCreateTime());
            }
            return  socialDriverRegisterPurposeDto;
        }
        return null;
    }
}
