package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.News;

import java.util.List;

public interface NewsMapper {

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int insert(News record);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int insertSelective(News record);


    /**
     *
     * @mbggenerated 2018-11-16
     */
    News selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int updateByPrimaryKeySelective(News record);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int updateByPrimaryKey(News record);
}