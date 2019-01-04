package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.entity.NewsCategory;

public interface NewsCategoryMapper {

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int insert(NewsCategory record);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int insertSelective(NewsCategory record);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    NewsCategory selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int updateByPrimaryKeySelective(NewsCategory record);

    /**
     *
     * @mbggenerated 2018-11-16
     */
    int updateByPrimaryKey(NewsCategory record);
}