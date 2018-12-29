package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;
import com.jaagro.crm.api.entity.Express;

/**
 * 智库直通车管理
 * @author gavin
 * @since 2018/12/29
 */
public interface ExpressService {
    /**
     * 创建智库直通车
     * @param express
     * @return
     */
    boolean createExpress(Express express);



    /**
     * 根据id查询
     * @param id
     * @return
     */
    ExpressReturnDto getExpressById(Integer id);


    /**
     * 查询智库直通车列表
     * @param criteriaDto
     * @return
     */
    PageInfo listExpressByCriteria(QueryExpressDto criteriaDto);
}
