package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ReturnSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.SiteDto;
import com.jaagro.crm.api.dto.request.contract.listSettleMileageCriteriaDto;
import com.jaagro.crm.biz.entity.SettleMileage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 结算里程配制CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface SettleMileageMapperExt extends SettleMileageMapper {
    /**
     * 根据合同id装卸货地id列表查询
     *
     * @param customerContractId
     * @param siteDtoList
     * @return
     */
    List<SettleMileage> getSettleMileageList(@Param("customerContractId") Integer customerContractId, @Param("siteDtoList") List<SiteDto> siteDtoList);

    /**
     * 根据条件查询列表
     *
     * @param dto
     * @return
     */
    List<ReturnSettleMileageDto> listByCriteria(listSettleMileageCriteriaDto dto);

    /**
     * 根据装卸货地id，车型id判断是否存在
     *
     * @param settleMileage
     * @return
     */
    Integer selectByCriteria(SettleMileage settleMileage);

    /**
     * 根据结算信息id逻辑删除
     *
     * @param priceId
     */
    void disableBySettlePriceId(@Param("priceId") Integer priceId);
}
