package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.driver.CreateTruckDto;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "contract", description = "车辆管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckController {
    @Autowired
    private TruckService truckService;
    @Autowired
    private TruckMapper truckMapper;

    @ApiOperation("查询单个车辆")
    @GetMapping("/truck/{id}")
    public BaseResponse getTruckById(@PathVariable Integer id) {
        if (this.truckMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance("查询不到车辆信息");
        }
        Map<String, Object> result = truckService.getTruckById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("新增车辆")
    @PostMapping("/truck")
    public BaseResponse insert(@RequestBody CreateTruckDto dto){
        if(StringUtils.isEmpty(dto.getBusinessPermit())){
            return BaseResponse.errorInstance("车辆营运证号不能为空");
        }
        return BaseResponse.service(truckService.createTrucks(dto));
    }

    @ApiOperation("新增车辆司机资质")
    @PostMapping("/trucks")
    public BaseResponse insertTruck(@RequestBody CreateTruckDto dto){
        if(StringUtils.isEmpty(dto.getBusinessPermit())){
            return BaseResponse.errorInstance("车辆营运证号不能为空");
        }
        return BaseResponse.service(truckService.createTrucks(dto));
    }
}
