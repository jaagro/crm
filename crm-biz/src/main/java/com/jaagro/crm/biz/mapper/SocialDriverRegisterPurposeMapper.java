package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.SocialDriverRegisterPurpose;

public interface SocialDriverRegisterPurposeMapper {
    /**
     *
     * @mbggenerated 2018-12-04
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-04
     */
    int insert(SocialDriverRegisterPurpose record);

    /**
     *
     * @mbggenerated 2018-12-04
     */
    int insertSelective(SocialDriverRegisterPurpose record);

    /**
     *
     * @mbggenerated 2018-12-04
     */
    SocialDriverRegisterPurpose selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-04
     */
    int updateByPrimaryKeySelective(SocialDriverRegisterPurpose record);

    /**
     *
     * @mbggenerated 2018-12-04
     */
    int updateByPrimaryKey(SocialDriverRegisterPurpose record);
}