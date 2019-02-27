package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.Express;

public interface ExpressMapper {
    /**
     *
     * @mbggenerated 2019-01-25
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-01-25
     */
    int insert(Express record);

    /**
     *
     * @mbggenerated 2019-01-25
     */
    int insertSelective(Express record);

    /**
     *
     * @mbggenerated 2019-01-25
     */
    Express selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-01-25
     */
    int updateByPrimaryKeySelective(Express record);

    /**
     *
     * @mbggenerated 2019-01-25
     */
    int updateByPrimaryKeyWithBLOBs(Express record);

    /**
     *
     * @mbggenerated 2019-01-25
     */
    int updateByPrimaryKey(Express record);
}