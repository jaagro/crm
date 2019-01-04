package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Dictionary;
import com.jaagro.crm.biz.entity.DictionaryExample;
import java.util.List;

public interface DictionaryMapper {
    /**
     *
     * @mbggenerated 2018-12-29
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int insert(Dictionary record);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int insertSelective(Dictionary record);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    List<Dictionary> selectByExample(DictionaryExample example);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    Dictionary selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int updateByPrimaryKeySelective(Dictionary record);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int updateByPrimaryKey(Dictionary record);
}