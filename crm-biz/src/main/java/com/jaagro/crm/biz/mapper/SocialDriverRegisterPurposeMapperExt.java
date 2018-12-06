package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.socialDriver.ListDriverRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.response.socialDriver.SocialDriverRegisterPurposeDto;
import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @since 2018/12/4
 */
public interface SocialDriverRegisterPurposeMapperExt extends SocialDriverRegisterPurposeMapper {
    /**
     * 根据手机号查询
     *
     * @param phoneNumber
     * @return
     */
    SocialDriverRegisterPurpose selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 根据条件查询
     *
     * @param criteria
     * @return
     */
    List<SocialDriverRegisterPurposeDto> listByCriteria(ListDriverRegisterPurposeCriteriaDto criteria);
}
