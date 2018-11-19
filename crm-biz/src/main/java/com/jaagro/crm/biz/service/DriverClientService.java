package com.jaagro.crm.biz.service;

import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.api.dto.request.truck.CreateDriverDto;
import com.jaagro.crm.api.dto.request.truck.UpdateDriverDto;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.biz.service.fallback.DriverClientServiceFallback;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tony
 */
@FeignClient(value = "user", fallbackFactory = DriverClientServiceFallback.class)
public interface DriverClientService {

    /**
     * 创建司机对象
     *
     * @param driver
     * @return
     */
    @PostMapping("/driverFeign")
    String createDriverReturnId(@RequestBody CreateDriverDto driver);

    /**
     * 通过车辆id获取司机list
     *
     * @param truckId
     * @return
     */
    @GetMapping("/listDriverByTruckId/{truckId}")
    List<DriverReturnDto> listByTruckId(@PathVariable("truckId") Integer truckId);

    /**
     * 删除司机【逻辑】
     *
     * @param truckId
     * @return
     */
    @DeleteMapping("/driverByTruck/{truckId}")
    BaseResponse deleteDriverByTruckId(@PathVariable("truckId") Integer truckId);

    /**
     * 通过司机id获取司机
     *
     * @param id
     * @return
     */
    @GetMapping("/driverFeign/{id}")
    DriverReturnDto getDriverReturnObject(@PathVariable("id") Integer id);

    /**
     * 通过司机id获取司机
     *
     * @param driver
     * @return
     */
    @PostMapping("/updateDriverFeign")
    BaseResponse updateDriverFeign(@RequestBody UpdateDriverDto driver);

    /**
     * 通过司机id修改为审核通过
     *
     * @param driverId
     * @return
     */
    @PostMapping("/updateDriverStatusFeign/{driverId}")
    BaseResponse updateDriverStatusFeign(@PathVariable("driverId") Integer driverId);

    /**
     * 通过部门id获取部门名称
     *
     * @param id
     * @return
     */
    @GetMapping("/getDeptNameById/{id}")
    String getDeptNameById(@PathVariable("id") Integer id);

    /**
     * 根据id获取部门(项目部)信息
     * @param id
     * @return
     */
    @GetMapping("/getDepartmentById/{id}")
    DepartmentReturnDto getDepartmentById(@PathVariable("id") Integer id);

    /**
     * id获取customerUser
     *
     * @param id
     * @return
     */
    @GetMapping("/customerUser/{id}")
    GetCustomerUserDto getCustomerUserById(@PathVariable("id") Integer id);
}
