package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.League;

/**
 * LeagueMapper
 *
 * @author baiyiran
 */
public interface LeagueMapper {
    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(League record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(League record);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    League selectByPrimaryKey(Integer id);

    /**
     * 动态修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(League record);

    /**
     * 修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(League record);
}