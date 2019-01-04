package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customerRegister.ListCustomerRegisterPurposeCriteriaDto;
import com.jaagro.crm.api.dto.response.customerRegister.CustomerRegisterPurposeDto;
import com.jaagro.crm.biz.entity.CustomerRegisterPurpose;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户注册意向CRUD扩展
 * @author yj
 * @since 2018/12/13
 */
public interface CustomerRegisterPurposeMapperExt extends CustomerRegisterPurposeMapper {
    /**
     * 根据手机号查询
     * @param phoneNumber
     * @return
     */
    CustomerRegisterPurpose selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 分页查询
     * @param criteria
     * @return
     */
    List<CustomerRegisterPurposeDto> listByCriteria(ListCustomerRegisterPurposeCriteriaDto criteria);
}
