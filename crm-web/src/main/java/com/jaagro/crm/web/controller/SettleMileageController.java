package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.contract.UpdateSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.listSettleMileageCriteriaDto;
import com.jaagro.crm.api.service.SettleMileageService;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
        if (!StringUtils.isEmpty(dto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (!StringUtils.isEmpty(dto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        return BaseResponse.successInstance(settleMileageService.listByCriteria(dto));
    }

    /**
     * 调整结算里程
     *
     * @param dto
     * @return
     */
    @ApiOperation("调整结算里程")
    @PutMapping("/settleMileage")
    public BaseResponse settleMileage(@RequestBody UpdateSettleMileageDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "id不能为空");
        }
        if (StringUtils.isEmpty(dto.getDriverSettleMileage())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "司机结算里程不能为空");
        }
        Map<String, Object> result;
        try {
            result = settleMileageService.updateSettleMileage(dto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.errorInstance(ex.getMessage());
        }

        return BaseResponse.successInstance(result);
    }

}