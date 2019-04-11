package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerContract;

public interface CustomerContractMapper {
    /**
     *
     * @mbggenerated 2019-04-08
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-08
     */
    int insert(CustomerContract record);

    /**
     *
     * @mbggenerated 2019-04-08
     */
    int insertSelective(CustomerContract record);

    /**
     *
     * @mbggenerated 2019-04-08
     */
    CustomerContract selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-08
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     *
     * @mbggenerated 2019-04-08
     */
    int updateByPrimaryKey(CustomerContract record);
}