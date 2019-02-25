package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerContactsCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface CustomerContactsMapperExt extends CustomerContactsMapper {

    /**
     * 查询客户全部联系人
     *
     * @param id
     * @return
     */
    List<CustomerContactsReturnDto> listByCustomerId(Integer id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<CustomerContactsReturnDto> getByCriteriDto(ListCustomerContactsCriteriaDto dto);

    /**
     * 根据客户id删除联系人
     *
     * @param customerId
     * @return
     */
    int deleteByCustomerId(Integer customerId);

    /**
     * 根据关键词查询客户id
     *
     * @param keyword
     * @return
     */
    List<Integer> listCustomerIdByKeyWord(@Param("keyword") String keyword);
}