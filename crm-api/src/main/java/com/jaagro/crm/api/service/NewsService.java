package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.news.CreateNewsDto;
import com.jaagro.crm.api.dto.request.news.ListNewsCriteriaDto;
import com.jaagro.crm.api.dto.request.news.UpdateNewsDto;
import com.jaagro.crm.api.dto.response.news.NewsReturnDto;
import com.jaagro.crm.api.dto.NewsCategoryDto;

import java.util.List;

/**
 * 新闻管理
 * @author yj
 * @since 2018/11/16
 */
public interface NewsService {
    /**
     * 创建新闻
     * @param createNewsDto
     * @return
     */
    boolean createNews(CreateNewsDto createNewsDto);

    /**
     * 查询所有新闻类别
     * @return
     */
    List<NewsCategoryDto> getAllNewsCategory();

    /**
     * 编辑新闻
     * @param updateNewsDto
     * @return
     */
    boolean updateNews(UpdateNewsDto updateNewsDto);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    NewsReturnDto getNewsById(Integer id);

    /**
     * 删除新闻(逻辑删除)
     * @param id
     * @return
     */
    boolean deleteNews(Integer id);

    /**
     * 查询新闻列表
     * @param listNewsCriteriaDto
     * @return
     */
    PageInfo listByCriteria(ListNewsCriteriaDto listNewsCriteriaDto);
}
