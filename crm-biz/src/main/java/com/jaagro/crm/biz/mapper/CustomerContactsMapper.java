package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContacts;

public interface CustomerContactsMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContacts record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContacts record);

    /**
     * @mbggenerated 2018-08-23
     */
    CustomerContacts selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContacts record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContacts record);

}