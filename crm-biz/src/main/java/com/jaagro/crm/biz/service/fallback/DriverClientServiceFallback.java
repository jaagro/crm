package com.jaagro.crm.biz.service.fallback;

import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.api.dto.request.message.CreateMessageDto;
import com.jaagro.crm.api.dto.request.truck.CreateDriverDto;
import com.jaagro.crm.api.dto.request.truck.UpdateDriverDto;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.utils.BaseResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

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
                return null;
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
            /**
             * 根据id获取部门(项目部)信息
             *
             * @param id
             * @return
             */
            @Override
            public DepartmentReturnDto getDepartmentById(Integer id) {
                return null;
            }

            /**
             * id获取customerUser
             *
             * @param id
             * @return
             */
            @Override
            public GetCustomerUserDto getCustomerUserById(Integer id) {
                return null;
            }

            /**
             * 查询近一个月过期证件
             * Author: @Gao.
             *
             * @return
             */
            @Override
            public BaseResponse<List<DriverReturnDto>> listDriverCertificateOverdueNotice(Integer driverExpiryDateType) {
                return BaseResponse.errorInstance("查询出错");
            }

            /**
             * 根据手机号查询司机
             *
             * @param phoneNumber
             * @return
             */
            @Override
            public BaseResponse<DriverReturnDto> getByPhoneNumber(String phoneNumber) {
                return null;
            }
        };
    }
}
