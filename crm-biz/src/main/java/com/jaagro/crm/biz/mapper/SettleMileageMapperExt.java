package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ReturnSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.SiteDto;
import com.jaagro.crm.api.dto.request.contract.listSettleMileageCriteriaDto;
import com.jaagro.crm.api.entity.SettleMileage;
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
     * 根据装卸货地id，车队合同id查询
     *
     * @param settleMileage
     * @return
     */
    SettleMileage selectByCriteria(SettleMileage settleMileage);

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer disableById(@Param("id") Integer id);
}
