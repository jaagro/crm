package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerRegisterPurpose;

/**
 * 客户注册意向CRUD
 * @author yj
 * @since 20181213
 */
public interface CustomerRegisterPurposeMapper {
    /**
     *
     * @mbggenerated 2018-12-13
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-13
     */
    int insert(CustomerRegisterPurpose record);

    /**
     *
     * @mbggenerated 2018-12-13
     */
    int insertSelective(CustomerRegisterPurpose record);

    /**
     *
     * @mbggenerated 2018-12-13
     */
    CustomerRegisterPurpose selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-13
     */
    int updateByPrimaryKeySelective(CustomerRegisterPurpose record);

    /**
     *
     * @mbggenerated 2018-12-13
     */
    int updateByPrimaryKey(CustomerRegisterPurpose record);
}