package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.entity.TruckTeamContractExample;
import java.util.List;

public interface TruckTeamContractMapper {
    /**
     *
     * @mbggenerated 2019-04-11
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-11
     */
    int insert(TruckTeamContract record);

    /**
     *
     * @mbggenerated 2019-04-11
     */
    int insertSelective(TruckTeamContract record);

    /**
     *
     * @mbggenerated 2019-04-11
     */
    List<TruckTeamContract> selectByExample(TruckTeamContractExample example);

    /**
     *
     * @mbggenerated 2019-04-11
     */
    TruckTeamContract selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-11
     */
    int updateByPrimaryKeySelective(TruckTeamContract record);

    /**
     *
     * @mbggenerated 2019-04-11
     */
    int updateByPrimaryKey(TruckTeamContract record);
}