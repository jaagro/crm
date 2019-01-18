package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.contract.CreateSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.UpdateSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.listSettleMileageCriteriaDto;
import com.jaagro.crm.api.entity.SettleMileage;

import java.util.Map;

/**
 * @author baiyiran
 * @Date 2018/12/28
 */
public interface SettleMileageService {

    /**
     * 创建结算里程
     *
     * @param mileageDto
     * @return
     */
    Boolean createSettleMileage(CreateSettleMileageDto mileageDto);

    /**
     * 分页查询结算里程
     *
     * @param dto
     * @return
     */
    PageInfo listByCriteria(listSettleMileageCriteriaDto dto);

    /**
     * 修改结算里程
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateSettleMileage(UpdateSettleMileageDto dto);

    /**
     * 根据条件查询结算里程表id
     * @param settleMileage
     * @return
     */
    SettleMileage selectByCriteria(SettleMileage settleMileage);

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer disableById(Integer id);
}
