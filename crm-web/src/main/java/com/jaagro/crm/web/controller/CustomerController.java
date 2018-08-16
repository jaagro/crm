package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import utils.BaseResponse;

import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "contract", description = "客户管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;


    @ApiOperation("查询单个客户")
    @GetMapping("/customer/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        if (this.customerMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.queryDataEmpty();
        }
        Map<String, Object> result = customerService.getById(id);
        return BaseResponse.service(result);
    }

}
