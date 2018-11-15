package com.jaagro.crm.biz.service.fallback;

import com.jaagro.crm.api.dto.request.truck.CreateDriverDto;
import com.jaagro.crm.api.dto.request.truck.UpdateDriverDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.utils.BaseResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tony
 */
@Component
@Slf4j
public class DriverClientServiceFallback implements FallbackFactory<DriverClientService> {
    @Override
    public DriverClientService create(Throwable throwable) {
        return new DriverClientService() {
            @Override
            public String createDriverReturnId(CreateDriverDto driver) {
                return  null;
            }

            @Override
            public List<DriverReturnDto> listByTruckId(Integer truckId) {
                return null;
            }

            @Override
            public BaseResponse deleteDriverByTruckId(Integer truckId) {
                return BaseResponse.errorInstance("删除id为：" + truckId + "的司机失败");
            }

            @Override
            public DriverReturnDto getDriverReturnObject(Integer id) {
                return null;
            }

            @Override
            public BaseResponse updateDriverFeign(UpdateDriverDto driver) {
                return BaseResponse.errorInstance("修改司机出错");
            }

            @Override
            public BaseResponse updateDriverStatusFeign(Integer driverId) {
                return BaseResponse.errorInstance("修改司机状态出错");
            }

            @Override
            public String getDeptNameById(Integer id) {
                return null;
            }
        };
    }
}
