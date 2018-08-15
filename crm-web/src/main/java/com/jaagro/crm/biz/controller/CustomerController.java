package com.jaagro.crm.biz.controller;

import com.jaagro.crm.api.dto.request.CreateCustomerDto;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.mapper.CustomerMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResponseStatusCode;
import utils.ServiceResult;

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

    @ApiOperation("客户新增")
    @PostMapping("/createCustomer")
    public Map<String,Object> createCustomer(@RequestBody CreateCustomerDto dto){
        if(StringUtils.isEmpty(dto.getBranchId())){
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户所属网点不能为空");
        }
        Map<String, Object> result = null;
        try{
             result= customerService.createCustomer(dto);
        }catch (Exception e){
            e.printStackTrace();
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
        }
        return result;
    }
}
