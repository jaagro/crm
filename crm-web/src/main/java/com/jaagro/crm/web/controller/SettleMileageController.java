package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.contract.listSettleMileageCriteriaDto;
import com.jaagro.crm.api.service.SettleMileageService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 结算里程管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "settleMileage", description = "结算里程管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class SettleMileageController {
    @Autowired
    private SettleMileageService settleMileageService;

    /**
     * 分页查询结算里程
     *
     * @param dto
     * @return
     */
    @ApiOperation("分页查询结算里程")
    @PostMapping("/listSettleMileageByCriteria")
    public BaseResponse listSettleMileageByCriteria(@RequestBody listSettleMileageCriteriaDto dto) {
        return BaseResponse.successInstance(settleMileageService.listByCriteria(dto));
    }

}