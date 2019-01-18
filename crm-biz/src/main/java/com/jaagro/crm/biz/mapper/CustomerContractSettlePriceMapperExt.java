package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.base.QueryCustomerContractSettlePriceDto;
import com.jaagro.crm.api.dto.request.contract.SiteDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCustomerSettlePriceDto;
import com.jaagro.crm.biz.entity.CustomerContractSettlePrice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户合同结算价格CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface CustomerContractSettlePriceMapperExt extends CustomerContractSettlePriceMapper {

    List<CustomerContractSettlePrice> getSectionWeightPrice(@Param("customerContractId") Integer customerContractId, @Param("doneDate") Date doneDate, @Param("siteDtoList") List<SiteDto> siteDtoList);

    /**
     * 根据 合同、装货地、卸货地、车型ID查询是否存在历史记录
     *
     * @param queryCustomerContractSettlePriceDto
     * @return
     */
    List<CustomerContractSettlePrice> getByCriteria(QueryCustomerContractSettlePriceDto queryCustomerContractSettlePriceDto);

    /**
     * 根据合同id获得列表(不包括历史记录)
     *
     * @param contractId
     * @return
     */
    List<ReturnCustomerSettlePriceDto> listByContractId(Integer contractId);

    /**
     * 根据主键id查询历史记录
     *
     * @param dto
     * @return
     */
    List<ReturnCustomerSettlePriceDto> listCustomerContractSettlePriceHistory(QueryCustomerContractSettlePriceDto dto);

    /**
     * 根据客户合同id,装货地id,卸货地id获取实际里程
     * @param customerContractId
     * @param loadSiteId
     * @param unloadSiteId
     * @return
     */
    BigDecimal getMileageByParams(@Param("customerContractId") Integer customerContractId, @Param("loadSiteId") Integer loadSiteId, @Param("unloadSiteId") Integer unloadSiteId);
}
