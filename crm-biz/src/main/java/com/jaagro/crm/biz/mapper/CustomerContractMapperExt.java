package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.ListContractCriteriaDto;
import com.jaagro.crm.api.dto.request.contract.UpdateContractDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.biz.entity.CustomerContract;

import java.util.List;

/**
 * @author gavin
 */
public interface CustomerContractMapperExt extends CustomerContractMapper {

    /**
     * 查询单个Dto
     *
     * @param idgetById
     */
    ReturnContractDto getById(Integer id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    List<ReturnContractDto> listByPage(ListContractCriteriaDto dto);

    /**
     * 查询客户Id查询合同
     *
     * @param dto
     * @return
     */
    List<ReturnContractDto> getByCustomerId(Integer id);

    /**
     * 新增时判断是否存在
     *
     * @param updateContractDto
     * @return
     */
    CustomerContract getByUpdateDto(UpdateContractDto updateContractDto);

    /**
     * 根据客户id和货物类型查询
     *
     * @param updateContractDto
     * @return
     */
    List<CustomerContract> getByCustomerAndGoodsType(UpdateContractDto updateContractDto);

    /**
     * 根据客户查询未审核个数
     *
     * @param customerId
     * @return
     */
    int countByCustomerId(Integer customerId);

    /**
     * 根据客户id查询需要审核的合同
     *
     * @param customerId
     * @return
     */
    List<ReturnCheckContractDto> listByCustomerIdAndStatus(Integer customerId);

    /**
     * 获取显示客户合同
     *
     * @param id
     * @return
     */
    ShowCustomerContractDto getShowCustomerContractById(Integer id);

    /**
     * 根据客户id获取合同显示对象
     *
     * @param customerId
     * @return
     */
    List<ShowCustomerContractDto> listShowCustomerContractByCustomerId(Integer customerId);
}