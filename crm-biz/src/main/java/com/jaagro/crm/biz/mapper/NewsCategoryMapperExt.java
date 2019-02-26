package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.news.NewsCategoryReturnDto;
import com.jaagro.crm.biz.entity.NewsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @date 2018/11/16
 */
public interface NewsCategoryMapperExt extends NewsCategoryMapper {
    /**
     * 查询所有新闻类别
     * @return
     */
    List<NewsCategory> selectAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    NewsCategoryReturnDto selectById(@Param("id") Integer id);

    /**
     * 根据类别查询
     * @param category
     * @return
     */
    List<NewsCategory> listByCategory(@Param("category") Integer category);
}
