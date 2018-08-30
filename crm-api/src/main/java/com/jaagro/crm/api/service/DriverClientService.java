package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateDriverDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tony
 */
@FeignClient("user")
public interface DriverClientService {

    /**
     * 创建司机对象
     * @param driver
     * @return
     */
    @PostMapping("/driverFeign")
    Integer createDriverReturnId(@RequestBody CreateDriverDto driver);

    /**
     * 通过车辆id获取司机list
     * @param truckId
     * @return
     */
    @GetMapping("/listDriverByTruckId/{truckId}")
    List<DriverReturnDto> listByTruckId(@PathVariable("truckId") Integer truckId);

    /**
     * 删除司机【逻辑】
     * @param truckId
     * @return
     */
    @DeleteMapping("/driverByTruck/{truckId}")
    BaseResponse deleteDriverByTruckId(@PathVariable("truckId") Integer truckId);

    /**
     * 通过司机id获取司机
     * @param id
     * @return
     */
    @GetMapping("/driverFeign/{id}")
    DriverReturnDto getDriverReturnObject(@PathVariable("id") Integer id);
}
