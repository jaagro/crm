package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.base.ListCustomerIdCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ListCustomerCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerDto;
import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.api.dto.response.customer.ListCustomerDto;
import com.jaagro.crm.api.dto.response.customer.ReturnCustomerDto;
import com.jaagro.crm.biz.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface CustomerMapperExt extends CustomerMapper {

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
    List<ListCustomerDto> listByCriteriaDto(ListCustomerCriteriaDto dto);

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
     *
     * @param id
     * @return
     */
    ShowCustomerDto getShowCustomerById(Integer id);

    /**
     * 获得全部的客户
     *
     * @return
     */
    List<ShowCustomerDto> getAllCustomer();

    /**
     * 查询正常合作的全部客户
     *
     * @return
     */
    List<ShowCustomerDto> listNormalCustomer();

    /**
     * 根据客户名称查询客户id集合
     *
     * @param listCustomerIdCriteriaDto
     * @return
     */
    List<Integer> listCustomerIdByName(ListCustomerIdCriteriaDto listCustomerIdCriteriaDto);

    /**
     * 根据tenantId 获取客户列表
     *
     * @param tenantId
     * @return
     */
    List<ShowCustomerDto> listCustomerByTenantId(@Param("tenantId") Integer tenantId);
}