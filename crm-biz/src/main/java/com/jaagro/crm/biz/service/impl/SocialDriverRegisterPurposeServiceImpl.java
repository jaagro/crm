package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.jaagro.crm.api.constant.Constants;
import com.jaagro.crm.api.dto.request.socialDriver.ListDriverRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.socialDriver.UpdateSocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.api.service.SocialDriverRegisterPurposeService;
import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;
import com.jaagro.crm.biz.mapper.SocialDriverRegisterPurposeMapperExt;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.crm.biz.service.SmsClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private SmsClientService smsClientService;
    @Autowired
    private StringRedisTemplate redisTemplate;


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
        if (null == sdr) {
            throw new NullPointerException("id error");
        }
        SocialDriverRegisterPurposeDto sdrDto = new SocialDriverRegisterPurposeDto();
        BeanUtils.copyProperties(sdr, sdrDto);
        return sdrDto;
    }


    /**
     * 根据手机号创建
     *
     * @param phoneNumber
     */
    @Override
    public Integer createSocialDriverByPhoneNumber(String phoneNumber, String verificationCode) {
        log.info("O createSocialDriverByPhoneNumber phoneNumber={},verificationCode={}", phoneNumber, verificationCode);
        if (!verificationCode.equals(redisTemplate.opsForValue().get(Constants.SOCIAL_DRIVER_REGISTER + phoneNumber))) {
            throw new RuntimeException("验证码不正确");
        }
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null) {
            throw new RuntimeException("该手机号已注册");
        }
        socialDriverRegisterPurpose = new SocialDriverRegisterPurpose();
        socialDriverRegisterPurpose.setPhoneNumber(phoneNumber)
                .setCreateTime(new Date());
        Integer id = socialDriverRegisterPurposeMapperExt.insertSelective(socialDriverRegisterPurpose);
        return id;
    }

    /**
     * 注册发送验证码
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public Map<String, Object> registerSendSMS(String phoneNumber) {
        log.info("R registerSendSMS phoneNumber={}", phoneNumber);
        // 手机号不是原有司机且未注册才可以发送验证码
        Map<String, Object> result = new HashMap<>();
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPhoneNumber(phoneNumber);
        if (socialDriverRegisterPurpose != null) {
            result.put(ServiceKey.status.name(), ResponseStatusCode.QUERY_DATA_ERROR.getCode());
            result.put(ServiceKey.msg.name(), "该手机号已注册");
            return result;
        }
        BaseResponse<DriverReturnDto> response = driverClientService.getByPhoneNumber(phoneNumber);
        if (ResponseStatusCode.OPERATION_SUCCESS.getCode() == response.getStatusCode()) {
            DriverReturnDto driverReturnDto = response.getData();
            if (driverReturnDto != null) {
                result.put(ServiceKey.status.name(), ResponseStatusCode.QUERY_DATA_ERROR.getCode());
                result.put(ServiceKey.msg.name(), "该手机号已注册为正式司机");
                return result;
            }
        }
        sendSMS(phoneNumber);
        result.put(ServiceKey.success.name(), true);
        return result;
    }

    /**
     * 加入平台更新社会司机注册意向
     *
     * @param registerPurposeDto
     */
    @Override
    public void updateSocialDriverRegisterPurpose(UpdateSocialDriverRegisterPurposeDto registerPurposeDto) {
        SocialDriverRegisterPurpose socialDriverRegisterPurpose = socialDriverRegisterPurposeMapperExt.selectByPrimaryKey(registerPurposeDto.getId());
        if (socialDriverRegisterPurpose == null) {
            throw new NullPointerException("id不存在");
        }
        BeanUtils.copyProperties(registerPurposeDto, socialDriverRegisterPurpose);
        socialDriverRegisterPurposeMapperExt.updateByPrimaryKeySelective(socialDriverRegisterPurpose);
    }

    /**
     * 查询司机注册意向列表
     *
     * @param criteria
     * @return
     */
    @Override
    public PageInfo listDriverRegisterPurposeByCriteria(ListDriverRegisterPurposeCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        List<SocialDriverRegisterPurposeDto> registerPurposeDtoList = socialDriverRegisterPurposeMapperExt.listByCriteria(criteria);
        return new PageInfo<>(registerPurposeDtoList);
    }

    private void sendSMS(String phoneNumber) {
        Random random = new Random();
        //获取5位随机验证码
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder verificationCodeBuilder = null;
        for (int i = 0; i < 5; i++) {
            verificationCodeBuilder = stringBuilder.append(random.nextInt(9));
        }
        String verificationCode = verificationCodeBuilder.toString();
        //先存库
        try {
            redisTemplate.opsForValue().set(Constants.SOCIAL_DRIVER_REGISTER + phoneNumber, verificationCode, 10, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("set verificationCode to redis error phoneNumber=" + phoneNumber + ",verificationCode=" + verificationCode, e);
            throw new RuntimeException("验证码发送失败");
        }
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("code", verificationCode);
        try {
            smsClientService.sendSMS(phoneNumber, Constants.SOCIAL_DRIVER_REGISTER_SMS_TEMPLATE_CODE, map);
        } catch (Exception e) {
            log.error("sendSMS error phoneNumber=" + phoneNumber + ",verificationCode=" + verificationCode, e);
            throw new RuntimeException("验证码发送失败");
        }
    }
}
