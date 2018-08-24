package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerContactsCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;
import com.jaagro.crm.biz.entity.CustomerContacts;

import java.util.List;

public interface CustomerContactsMapper {
    /**
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerContacts record);

    /**
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerContacts record);

    /**
     * @mbggenerated 2018-08-23
     */
    CustomerContacts selectByPrimaryKey(Integer id);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerContacts record);

    /**
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerContacts record);

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

}