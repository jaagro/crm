package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.entity.Express;
import com.jaagro.crm.api.entity.ExpressExample;
import java.util.List;

public interface ExpressMapper {
    /**
     *
     * @mbggenerated 2018-12-29
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int insert(Express record);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int insertSelective(Express record);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    List<Express> selectByExample(ExpressExample example);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    Express selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int updateByPrimaryKeySelective(Express record);

    /**
     *
     * @mbggenerated 2018-12-29
     */
    int updateByPrimaryKey(Express record);
}