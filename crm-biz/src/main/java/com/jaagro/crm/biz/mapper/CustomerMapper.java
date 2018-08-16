package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.customer.CustomerReturnDto;
import com.jaagro.crm.biz.entity.Customer;

public interface CustomerMapper {
    /**
     *
     * @mbggenerated 2018-08-16
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insert(Customer record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int insertSelective(Customer record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    Customer selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     *
     * @mbggenerated 2018-08-16
     */
    int updateByPrimaryKey(Customer record);

    /**
     * 查询单个客户Dto
     *
     * @param id
     * @return
     */
    CustomerReturnDto getById(Long id);

//    /**
//     * 查询单个合同Dto
//     *
//     * @param id
//     * @return
//     */
//    CustomerContractReturnDto getByContractId(Long id);
//
//    /**
//     * 查询单个收发货地Dto
//     *
//     * @param id
//     * @return
//     */
//    CustomerSiteReturnDto getBySiteId(Long id);
//
//    /**
//     * 查询单个收发货地Dto
//     *
//     * @param id
//     * @return
//     */
//    QualificationCertificReturnDto getByQualificationId(Long id);
}