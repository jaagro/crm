package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;
import com.jaagro.crm.biz.mapper.SocialDriverRegisterPurposeMapperExt;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 社会司机注册意向管理
 *
 * @author yj
 * @since 2018/12/4
 */
@Service
@Slf4j
public class SocialDriverRegisterPurposeServiceImpl implements SocialDriverRegisterPurposeService {
    @Autowired
    private SocialDriverRegisterPurposeMapperExt socialDriverRegisterPurposeMapperExt;
    @Autowired
    private DriverClientService driverClientService;
    /**
     * 根据手机号查询
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public SocialDriverRegisterPurposeDto getByPhoneNumber(String phoneNumber) {
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null) {
            SocialDriverRegisterPurposeDto socialDriverRegisterPurposeDto = new SocialDriverRegisterPurposeDto();
            BeanUtils.copyProperties(socialDriverRegisterPurpose, socialDriverRegisterPurposeDto);
            if (socialDriverRegisterPurpose.getUploadTime() == null) {
                socialDriverRegisterPurposeDto.setUploadTime(socialDriverRegisterPurpose.getCreateTime());
            }
            return socialDriverRegisterPurposeDto;
        }
        return null;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public SocialDriverRegisterPurposeDto getSocialDriverRegisterPurposeDtoById(Integer id) {
        SocialDriverRegisterPurpose sdr = socialDriverRegisterPurposeMapperExt.selectByPrimaryKey(id);
        if(null == sdr){
            throw new NullPointerException("id error");
        }
        SocialDriverRegisterPurposeDto sdrDto = new SocialDriverRegisterPurposeDto();
        BeanUtils.copyProperties(sdr, sdrDto);
        return sdrDto;
    }


    /**
     * 根据手机号创建
     * @param phoneNumber
     */
    @Override
    public boolean createSocialDriverByPhoneNumber(String phoneNumber) {
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null) {
            return false;
        }
        socialDriverRegisterPurpose = new SocialDriverRegisterPurpose();
        socialDriverRegisterPurpose.setPhoneNumber(phoneNumber);
        socialDriverRegisterPurposeMapperExt.insertSelective(socialDriverRegisterPurpose);
        return true;
    }

    /**
     * 注册发送验证码
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public Map<String, String> registerSendSMS(String phoneNumber) {
        // 手机号不是原有司机且未注册才可以发送验证码
        Map<String,String> result = new HashMap<>();
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null) {
            result.put(ServiceKey.status.name(), ResponseStatusCode.QUERY_DATA_ERROR.getCode()+"");
        }
        return null;
    }
}
