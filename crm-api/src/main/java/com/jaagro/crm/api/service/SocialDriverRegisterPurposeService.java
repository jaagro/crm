package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.socialDriver.ListDriverRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.request.socialDriver.UpdateSocialDriverRegisterPurposeDto;
import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SocialDriverRegisterPurposeDto getSocialDriverRegisterPurposeDtoById(Integer id);

    /**
     * 根据手机号创建
     * @param phoneNumber
     */
    void createSocialDriverByPhoneNumber(String phoneNumber);

    /**
     * 注册发送验证码
     * @param phoneNumber
     * @return
     */
    Map<String,Object> registerSendSMS(String phoneNumber);

    /**
     * 加入平台更新社会司机注册意向
     * @param registerPurposeDto
     */
    void updateSocialDriverRegisterPurpose(UpdateSocialDriverRegisterPurposeDto registerPurposeDto);

    /**
     * 查询司机注册意向列表
     * @param criteria
     * @return
     */
    PageInfo listDriverRegisterPurposeByCriteria(ListDriverRegisterPurposeCriteriaDto criteria);
}
