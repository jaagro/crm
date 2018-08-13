package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.ContractCriteriaDto;
import com.jaagro.crm.biz.entity.Contract;
import com.jaagro.crm.api.dto.response.ContractReturnDto;

import java.util.List;

public interface ContractMapper {

    /**
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);

    /**
     * 查询单个Dto
     *
     * @param id
     * @return
     */
    ContractReturnDto getByPrimaryKey(Long id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<ContractReturnDto> listByPage(ContractCriteriaDto dto);
}