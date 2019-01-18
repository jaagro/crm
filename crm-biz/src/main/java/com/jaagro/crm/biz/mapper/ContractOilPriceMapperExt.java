package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.contract.ReturnContractOilPriceDto;
import com.jaagro.crm.biz.entity.ContractOilPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同油价CRUD扩展
 *
 * @author yj
 * @since 2018/12/24
 */
public interface ContractOilPriceMapperExt extends ContractOilPriceMapper {

    /**
     * 查询不是历史记录的信息
     *
     * @param contractId   合同id
     * @param contractType 合同类型
     * @return
     */
    List<ContractOilPrice> listByContractIdAndType(@Param("contractId") Integer contractId, @Param("contractType") Integer contractType);

    /**
     * 根据合同id获得油价配置
     *
     * @param contractId
     * @param contractType
     * @return
     */
    ReturnContractOilPriceDto getByContractIdAndType(@Param("contractId") Integer contractId, @Param("contractType") Integer contractType);

    /**
     * 根据合同id和类型删除
     * @param contractId 合同id
     * @param contractType 合同类型
     * @return
     */
    Integer disableByContractIdAndType(@Param("contractId") Integer contractId, @Param("contractType") Integer contractType);
}
