package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.driver.CreateTruckDto;
import com.jaagro.crm.api.dto.request.driver.ListTruckCriteriaDto;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.crm.biz.mapper.TruckTypeMapper;
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
    @Autowired
    private TruckTeamMapper truckTeamMapper;
    @Autowired
    private TruckTypeMapper truckTypeMapper;

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
        if(truckMapper.getByTruckNumber(dto.getTruckNumber()) != null){
            return BaseResponse.errorInstance(dto.getTruckNumber() + " :当前车牌号已经存在");
        }
        if(truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()) == null){
            return BaseResponse.errorInstance("当前车队不存在");
        }
        if(StringUtils.isEmpty(dto.getTruckNumber())){
            return BaseResponse.errorInstance("车牌号码不能为空");
        }
        if(truckTypeMapper.selectByPrimaryKey(dto.getTruckTypeId()) == null){
            return BaseResponse.errorInstance("请选择正确的车辆类型");
        }
        BaseResponse response;
        try {
            response = BaseResponse.service(truckService.createTruck(dto));
        }catch (RuntimeException e){
            e.printStackTrace();
            response = BaseResponse.errorInstance(e.getMessage());
        }

        return response;
    }

//    @ApiOperation("新增车辆司机资质")
//    @PostMapping("/trucks")
//    public BaseResponse insertTruck(@RequestBody CreateTruckDto dto){
//        if(StringUtils.isEmpty(dto.getBusinessPermit())){
//            return BaseResponse.errorInstance("车辆营运证号不能为空");
//        }
//        return BaseResponse.service(truckService.createTrucks(dto));
//    }

    @PostMapping("/listTruck")
    public BaseResponse listTruck(@RequestBody ListTruckCriteriaDto truckCriteria){
        if(StringUtils.isEmpty(truckCriteria.getTruckTeamId())){
            return BaseResponse.idError("truckTeamId不能为空");
        }
        return BaseResponse.service(truckService.listTruck(truckCriteria));
    }
}
