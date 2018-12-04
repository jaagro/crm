package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;
import org.apache.ibatis.annotations.Param;

/**
 * @author yj
 * @since 2018/12/4
 */
public interface SocialDriverRegisterPurposeMapperExt extends SocialDriverRegisterPurposeMapper {
    /**
     *
     * @param phoneNumber
     * @return
     */
    SocialDriverRegisterPurpose selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
