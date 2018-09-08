package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.truck.CreateTruckDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTypeDto;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.crm.biz.mapper.TruckTypeMapper;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(description = "车辆管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckController {
    @Autowired
    private TruckService truckService;
    @Autowired
    private TruckMapper truckMapper;
    @Autowired
    private TruckTeamMapper truckTeamMapper;
    @Autowired
    private TruckTypeMapper truckTypeMapper;

    @ApiOperation("查询单个车辆")
    @GetMapping("/truck/{truckId}")
    public BaseResponse getTruckById(@PathVariable("truckId") Integer truckId) {
        if (this.truckMapper.selectByPrimaryKey(truckId) == null) {
            return BaseResponse.errorInstance("查询不到车辆信息");
        }
        Map<String, Object> result = truckService.getTruckById(truckId);
        return BaseResponse.service(result);
    }

    @ApiOperation("新增车辆")
    @PostMapping("/truck")
    public BaseResponse insert(@RequestBody CreateTruckDto truck) {
        if (truckMapper.getByTruckNumber(truck.getTruckNumber()) != null) {
            return BaseResponse.errorInstance(truck.getTruckNumber() + " :当前车牌号已经存在");
        }
        if (truckTeamMapper.selectByPrimaryKey(truck.getTruckTeamId()) == null) {
            return BaseResponse.errorInstance("当前车队不存在");
        }
        if (StringUtils.isEmpty(truck.getTruckNumber())) {
            return BaseResponse.errorInstance("车牌号码不能为空");
        }
        if (truckTypeMapper.selectByPrimaryKey(truck.getTruckTypeId()) == null) {
            return BaseResponse.errorInstance("请选择正确的车辆类型");
        }
        BaseResponse response;
        try {
            response = BaseResponse.service(truckService.createTruck(truck));
        } catch (FeignException e) {
            e.printStackTrace();
            response = BaseResponse.errorInstance("司机创建失败");
        }
        return response;
    }

    @ApiOperation("车辆修改")
    @PutMapping("/truck")
    public BaseResponse updateTruckTeam(@RequestBody CreateTruckDto truck) {
        if (StringUtils.isEmpty(truck.getTruckNumber())) {
            return BaseResponse.service(ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车牌号码不能为空"));
        }
        return BaseResponse.service(truckService.updateTruck(truck));
    }

    @ApiOperation("车辆删除【逻辑】")
    @DeleteMapping("/truck/{id}")
    public BaseResponse deleteTruckTeam(@PathVariable("id") Integer id) {
        return BaseResponse.service(truckService.deleteTruck(id));
    }

    /**
     * 分页查询车辆
     *
     * @param truckCriteria
     * @return
     */
    @ApiOperation("分页查询车辆")
    @PostMapping("/listTruck")
    public BaseResponse listTruck(@RequestBody ListTruckCriteriaDto truckCriteria) {
        return BaseResponse.service(truckService.listTruck(truckCriteria));
    }

    @ApiOperation("获取全部车型")
    @GetMapping("/listTruckType")
    public BaseResponse listTruckType() {
        return BaseResponse.successInstance(truckService.listTruckType());
    }

    @Ignore
    @GetMapping("/listTruckTypeToFeign")
    public List<ListTruckTypeDto> listTruckTypeReturnDto(){
        return truckService.listTruckType();
    }

    @Ignore
    @GetMapping("/getTruckTypeById/{id}")
    public ListTruckTypeDto getTruckTypeById(@PathVariable("id") Integer id) {
        return truckService.getTruckTypeById(id);
    }
}
