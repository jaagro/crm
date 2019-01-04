package com.jaagro.crm.biz.service;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj
 * @since 2018/11/19
 */
@FeignClient(value = "user")
public interface UserClientService {
    /**
     * 根据id列表查用户列表
     *
     * @param userIdList
     * @param userType
     * @return
     */
    @GetMapping("/listUserInfo")
    List<UserInfo> listUserInfo(@RequestParam("userIdList") List<Integer> userIdList, @RequestParam("userType") String userType);

    /**
     * 获取用户id
     * @return
     */
    @GetMapping("/getNextUserId")
    int getNextUserId();

    /**
     * 根据手机号查询客户用户
     * @param phoneNumber
     * @return
     */
    @GetMapping("/getCustomerUserByPhoneNumber")
    BaseResponse<GetCustomerUserDto> getCustomerUserByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据客户关联id查询
     * @param relevanceId
     * @return
     */
    @GetMapping("/getCustomerUserByRelevanceId/{relevanceId}")
    BaseResponse<GetCustomerUserDto> getCustomerUserByRelevanceId(@PathVariable("relevanceId") Integer relevanceId);

    /**
     * 根据userId获取对应的用户数据
     *
     * @param userId
     * @return
     */
    @GetMapping("/getGlobalUser/{userId}")
    BaseResponse<UserInfo> getGlobalUser(@PathVariable("userId") int userId);

    /**
     * 获取当前token可查询的数据范围 -- 依据部门id
     *
     * @return
     */
    @PostMapping("/getDownDepartment")
    List<Integer> getDownDepartment();

    @PostMapping("/getAllDepartments")
    List<DepartmentReturnDto> getAllDepartments();

}
