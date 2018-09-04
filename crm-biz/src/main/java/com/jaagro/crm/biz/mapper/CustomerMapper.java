package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.api.dto.response.customer.ListCustomerDto;
import com.jaagro.crm.api.dto.response.customer.ReturnCustomerDto;
import com.jaagro.crm.biz.entity.Customer;

import java.util.List;

public interface CustomerMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(Customer record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(Customer record);

    /**
     * @mbggenerated 2018-08-23
     */
    Customer selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(Customer record);

    /**
     * 查询单个客户Dto
     *
     * @param id
     * @return
     */
    CustomerReturnDto getById(Integer id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<ListCustomerDto> getByCriteriaDto(ListCustomerCriteriaDto dto);

    /**
     * 新增时判断名称是否重复
     *
     * @param customer
     * @return
     */
    Customer getByCustomerDto(UpdateCustomerDto customer);

    /**
     * 根据id查询(审核客户资质需要的返回信息)
     *
     * @param id
     * @return
     */
    ReturnCustomerDto getCheckById(Integer id);

    /**
     * 客户显示对象
     * @param id
     * @return
     */
    ShowCustomerDto getShowCustomerById(Integer id);
}