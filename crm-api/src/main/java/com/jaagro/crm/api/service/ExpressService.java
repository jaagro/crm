package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.express.CreateExpressDto;
import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;

import java.util.List;
import java.util.Map;

/**
 * 智库直通车管理
 * @author gavin
 * @since 2018/12/29
 */
public interface ExpressService {
    /**
     * 创建智库直通车
     * @param createExpressDto
     * @author yj
     * @return
     */
    boolean createExpress(CreateExpressDto createExpressDto);



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

    /**
     * 设为档案
     * @author yj
     * @param id
     * @return
     */
    boolean toDocument(Integer id);

    /**
     * 新增查询所有智库直通车的人员
     * @param phoneNumberList
     * @return
     */
    Map<String,Object> addQueryAllPerson(List<String> phoneNumberList);
}
