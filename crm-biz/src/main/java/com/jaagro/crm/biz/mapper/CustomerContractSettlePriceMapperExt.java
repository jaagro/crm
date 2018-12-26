package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.SiteDto;
import com.jaagro.crm.biz.entity.CustomerContractSettlePrice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 客户合同结算价格CRUD扩展
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettlePriceMapperExt extends CustomerContractSettlePriceMapper {

    List<CustomerContractSettlePrice> getSectionWeightPrice(@Param("customerContractId") Integer customerContractId, @Param("doneDate") Date doneDate, @Param("siteDtoList") List<SiteDto> siteDtoList);
}
