package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.driver.CreateDriverDto;
import com.jaagro.crm.api.dto.response.driver.DriverReturnDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/driver")
    Integer createDriverToFeign(@RequestBody CreateDriverDto driver);

    /**
     * 通过车辆id获取司机list
     * @param truckId
     * @return
     */
    @GetMapping("/listDriverByTruckId/{truckId}")
    List<DriverReturnDto> listByTruckId(@PathVariable("truckId") Integer truckId);
}
