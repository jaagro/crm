package com.jaagro.crm.biz.service;

import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.dto.base.CreateCustomerUserDto;
import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.api.dto.base.UpdateCustomerUserDto;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
     *
     * @return
     */
    @GetMapping("/getNextUserId")
    int getNextUserId();

    /**
     * 根据手机号查询客户用户
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping("/getCustomerUserByPhoneNumber")
    BaseResponse<GetCustomerUserDto> getCustomerUserByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    /**
     * 根据客户关联id查询
     *
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

    /**
     * 获取指定部门id及下属部门
     *
     * @return
     */
    @PostMapping("/getDownDepartmentByDeptId/{deptId}")
    List<Integer> getDownDepartmentByDeptId(@PathVariable("deptId") Integer deptId);

    @PostMapping("/getAllDepartments")
    List<DepartmentReturnDto> getAllDepartments();

    /**
     * 获取用户信息
     *
     * @param key
     * @param userType
     * @param loginType
     * @return
     */
    @GetMapping("/getUserInfo")
    UserInfo getUserInfo(@RequestParam("key") Object key, @RequestParam("userType") String userType, @RequestParam("loginType") String loginType);

    /**
     * 新增养殖户app登录用户
     *
     * @param userDto
     * @return
     */
    @PostMapping("/customerUser")
    BaseResponse createCustomerUser(@RequestBody CreateCustomerUserDto userDto);

    /**
     * 修改养殖户app登录用户
     *
     * @param customerUserDto
     * @return
     */
    @PostMapping("/updateCustomerUser")
    BaseResponse updateCustomerUser(@RequestBody UpdateCustomerUserDto customerUserDto);
}
