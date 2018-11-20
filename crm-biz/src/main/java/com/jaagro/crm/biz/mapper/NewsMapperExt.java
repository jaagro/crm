package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.news.ListNewsCriteriaDto;
import com.jaagro.crm.api.dto.response.news.NewsReturnDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @since 20181116
 */
public interface NewsMapperExt extends NewsMapper {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    NewsReturnDto selectById(@Param("id") Integer id);

    /**
     * 删除新闻(逻辑删除)
     * @param id
     * @return
     */
    Integer disableNews(@Param("id") Integer id);

    /**
     * 查询新闻列表
     * @param listNewsCriteriaDto
     * @return
     */
    List<NewsReturnDto> listByCriteria(@Param("criteria") ListNewsCriteriaDto listNewsCriteriaDto);
}
