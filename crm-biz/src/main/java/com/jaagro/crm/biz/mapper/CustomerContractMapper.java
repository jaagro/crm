package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.biz.entity.CustomerContract;

import java.util.List;

public interface CustomerContractMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContract record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContract record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerContract selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContract record);

    /**
     *
     * @mbggenerated 2018-08-23
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