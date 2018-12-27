package com.jaagro.crm.biz.mapper;

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
    List<ContractOilPrice> getByContractIdAndType(@Param("contractId") Integer contractId, @Param("contractType") Integer contractType);

}
