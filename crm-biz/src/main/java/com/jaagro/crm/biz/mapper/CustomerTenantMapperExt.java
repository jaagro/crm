package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.biz.entity.CustomerTenant;
import org.apache.ibatis.annotations.Param;

/**
 * @author baiyiran
 */
public interface CustomerTenantMapperExt extends CustomerTenantMapper {

    /**
     * 根据客户id查询租户
     *
     * @param customerId
     * @return
     */
    CustomerTenant getByCustomerId(@Param("customer") Integer customerId);
}