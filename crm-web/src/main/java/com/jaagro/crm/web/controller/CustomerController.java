package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.customer.*;
import com.jaagro.crm.api.dto.response.customer.CustomerContactsReturnDto;
import com.jaagro.crm.api.service.CustomerContactsService;
import com.jaagro.crm.api.service.CustomerService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.mapper.CustomerContactsMapperExt;
import com.jaagro.crm.biz.mapper.CustomerContractMapperExt;
import com.jaagro.crm.biz.mapper.CustomerMapperExt;
import com.jaagro.crm.biz.mapper.CustomerQualificationMapperExt;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客户管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "customer", description = "客户管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapperExt customerMapper;
    @Autowired
    private CustomerContactsMapperExt customerContactsMapper;
    @Autowired
    private CustomerContactsService customerContactsService;
    @Autowired
    private CustomerQualificationMapperExt qualificationMapper;
    @Autowired
    private CustomerContractMapperExt contractMapper;

    /**
     * 新增客户
     *
     * @param customer
     * @return
     */
    @ApiOperation("新增客户")
    @PostMapping("/customer")
    public BaseResponse insertCustomer(@RequestBody CreateCustomerDto customer) {
        if (customer.getCustomerType() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户类型:[customerType]不能为空");
        }
        if (customer.getCustomerName() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户名称:[customerName]不能为空");
        }
        //先注释，最后商议
       /* if (customer.getLatitude() == null) {
            return BaseResponse.errorInstance("经度:[latitude]不能为空");
        }
        if (customer.getLongitude() == null) {
            return BaseResponse.errorInstance("纬度:[longitude]不能为空");
        }*/
        UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();
        updateCustomerDto.setCustomerName(customer.getCustomerName());
        return BaseResponse.service(customerService.createCustomer(customer));
    }

    /**
     * 删除客户[逻辑]
     *
     * @param id
     * @return
     */
    @ApiOperation("删除客户[逻辑]")
    @DeleteMapping("/deleteCustomerById/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        return BaseResponse.service(this.customerService.disableCustomer(id));
    }

    /**
     * 修改客户
     *
     * @param customer
     * @return
     */
    @ApiOperation("修改客户")
    @PutMapping("/customer")
    public BaseResponse updateCustomer(@RequestBody UpdateCustomerDto customer) {
        return BaseResponse.service(customerService.updateById(customer));
    }

    /**
     * 查询单个客户
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个客户")
    @GetMapping("/customer/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        Map<String, Object> result = customerService.getById(id);
        return BaseResponse.service(result);
    }

    /**
     * 分页查询客户
     *
     * @param criteriaDto
     * @return
     */
    @ApiOperation("分页查询客户")
    @PostMapping("/listCustomerByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListCustomerCriteriaDto criteriaDto) {
        return BaseResponse.service(this.customerService.listByCriteria(criteriaDto));
    }

    /**
     * 查询全部客户
     *
     * @param
     * @return
     */
    @ApiOperation("查询全部客户")
    @GetMapping("/listAllCustomer")
    public BaseResponse listAllCustomer() {
        return BaseResponse.successInstance(this.customerService.listAllCustomer());
    }

    /**
     * 获取客户显示对象，提供给feign
     *
     * @param id
     * @return
     */
    @Ignore
    @GetMapping("/getShowCustomer/{id}")
    public ShowCustomerDto getShowCustomerById(@PathVariable("id") Integer id) {
        return customerService.getShowCustomerById(id);
    }

    //-------------------------------------------------客户联系人---------------------------------

    /**
     * 客户联系人新增
     *
     * @param contractDtos
     * @return
     */
    @ApiOperation("客户联系人新增")
    @PostMapping("/createCustomerContacts")
    public BaseResponse createCustomerContacts(@RequestBody List<CreateCustomerContactsDto> contractDtos) {
        Integer customerId = 0;
        if (contractDtos != null && contractDtos.size() > 0) {
            for (CreateCustomerContactsDto contractDto : contractDtos) {
                if (StringUtils.isEmpty(contractDto.getCustomerId())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "联系人客户id不能为空");
                }
                Customer customer = this.customerMapper.selectByPrimaryKey(contractDto.getCustomerId());
                if (customer == null) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不存在");
                }
                if (StringUtils.isEmpty(contractDto.getPhone())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "联系人电话不能为空");
                }
                if (StringUtils.isEmpty(contractDto.getContact())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "联系人姓名不能为空");
                }
            }
        }
        customerId = contractDtos.get(0).getCustomerId();
        return BaseResponse.service(this.customerContactsService.createCustomerContacts(contractDtos, customerId));
    }

    /**
     * 客户联系人修改
     *
     * @param contractDtos
     * @return
     */
    @ApiOperation("客户联系人修改")
    @PostMapping("/updateCustomerContacts")
    public BaseResponse updateCustomerContacts(@RequestBody List<UpdateCustomerContactsDto> contractDtos) {
        if (contractDtos != null && contractDtos.size() > 0) {
            this.customerContactsService.updateCustomerContacts(contractDtos);
            return BaseResponse.successInstance("修改成功");
        } else {
            return BaseResponse.errorInstance("修改失败");
        }
    }

    /**
     * 客户联系人逻辑删除
     *
     * @param id
     * @return
     */
    @ApiOperation("客户联系人逻辑删除")
    @GetMapping("/deleteCustomerContactsById/{id}")
    public BaseResponse disableCustomerContacts(@PathVariable Integer id) {
        return BaseResponse.service(this.customerContactsService.disableCustomerContacts(id));
    }

    /**
     * 查询客户联系人根据客户id
     *
     * @param customerId
     * @return
     */
    @ApiOperation("查询客户联系人根据客户id")
    @GetMapping("/listContactsByCustomerId/{customerId}")
    public BaseResponse listCustomerContactsByCustomerId(@PathVariable Integer customerId) {
        if (this.customerMapper.selectByPrimaryKey(customerId) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不存在");
        }
        return BaseResponse.successInstance(this.customerContactsMapper.listByCustomerId(customerId));
    }

    /**
     * 根据客户id查询主客户联系人
     *
     * @param customerId
     * @return
     */
    @Ignore
    @GetMapping("/getCustomerContactByCustomerId/{customerId}")
    public CustomerContactsReturnDto getCustomerContactByCustomerId(@PathVariable Integer customerId) {
        if (this.customerMapper.selectByPrimaryKey(customerId) == null) {
            return null;
        }
        List<CustomerContactsReturnDto> contactsReturnDtos = this.customerContactsMapper.listByCustomerId(customerId);
        if (contactsReturnDtos.size() > 0) {
            return contactsReturnDtos.get(0);
        }
        return null;
    }

    /**
     * 查询单个户联系人
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个户联系人")
    @GetMapping("/getContactsById/{id}")
    public BaseResponse getContactsById(@PathVariable Integer id) {
        if (this.customerContactsMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户联系人不存在");
        }
        return BaseResponse.successInstance(this.customerContactsMapper.selectByPrimaryKey(id));
    }
}
