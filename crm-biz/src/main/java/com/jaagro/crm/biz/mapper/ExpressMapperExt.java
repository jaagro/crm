package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;
import com.jaagro.crm.api.entity.Express;

import java.util.List;

/**
 * 直通车CRUD扩展
 * @author yj
 * @since 2018/12/29
 */
public interface ExpressMapperExt extends ExpressMapper {


    /**
     * 查询智库直通车列表
     * @param criteriaDto
     * @return
     */
    List<ExpressReturnDto> listByCriteria(QueryExpressDto criteriaDto);


//    ExpressReturnDto getExpressById(Integer id);
}
