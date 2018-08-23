package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;

import java.util.List;

/**
 * @author baiyiran
 */
public interface ContractMapper {

    /**
     * 主键删除
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
    int insert(CustomerContract record);

    /**
     * 动态新增
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerContract record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    CustomerContract selectByPrimaryKey(Integer id);

    /**
     * 动态更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerContract record);

    /**
     * 查询单个Dto
     *
     * @param id
     * @return
     */
    ReturnContractDto getById(Integer id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<ReturnContractDto> listByPage(ListContractCriteriaDto dto);

    /**
     * 查询客户Id查询合同
     *
     * @param dto
     * @return
     */
    List<ReturnContractDto> getByCustomerId(Integer id);

    /**
     * 新增时判断是否存在
     *
     * @param updateContractDto
     * @return
     */
    CustomerContract getByUpdateDto(UpdateContractDto updateContractDto);
}