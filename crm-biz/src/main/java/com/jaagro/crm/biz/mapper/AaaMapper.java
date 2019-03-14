package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Aaa;

public interface AaaMapper {
    /**
     *
     * @mbggenerated 2019-03-14
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    int insert(Aaa record);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    int insertSelective(Aaa record);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    Aaa selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    int updateByPrimaryKeySelective(Aaa record);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    int updateByPrimaryKey(Aaa record);
}