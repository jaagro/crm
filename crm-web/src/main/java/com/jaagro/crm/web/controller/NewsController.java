package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.RequestSource;
import com.jaagro.crm.api.dto.request.news.CreateNewsDto;
import com.jaagro.crm.api.dto.request.news.ListNewsCriteriaDto;
import com.jaagro.crm.api.dto.request.news.UpdateNewsDto;
import com.jaagro.crm.api.dto.response.news.NewsCategoryReturnDto;
import com.jaagro.crm.api.dto.response.news.NewsReturnDto;
import com.jaagro.crm.biz.entity.NewsCategory;
import com.jaagro.crm.biz.service.NewsService;
import com.jaagro.crm.web.vo.news.NewsCategoryVo;
import com.jaagro.crm.web.vo.news.NewsVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 新闻管理
 *
 * @author yj
 * @since 2018/11/16
 */
@RestController
@Slf4j
@Api(value = "news", description = "新闻管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {
    @Autowired
    private NewsService newsService;


    @ApiOperation(value = "查询新闻类别")
    @GetMapping("/getAllNewsCategory")
    public BaseResponse getAllNewsCategory() {
        List<NewsCategory> allNewsCategory = newsService.getAllNewsCategory();
        List<NewsCategoryVo> newsCategoryVoList = generateNewsCategoryVo(allNewsCategory);
        return BaseResponse.successInstance(newsCategoryVoList);
    }

    @ApiOperation(value = "新增新闻")
    @PostMapping("/news")
    public BaseResponse createNews(@RequestBody @Validated CreateNewsDto createNewsDto) {
        boolean isSuccess = newsService.createNews(createNewsDto);
        if (isSuccess) {
            return BaseResponse.successInstance("发布新闻成功");
        }
        return BaseResponse.errorInstance("发布新闻失败");
    }

    @ApiOperation(value = "编辑新闻")
    @PutMapping("/news")
    public BaseResponse updateNews(@RequestBody @Validated UpdateNewsDto updateNewsDto) {
        boolean isSuccess = newsService.updateNews(updateNewsDto);
        if (isSuccess) {
            return BaseResponse.successInstance("编辑新闻成功");
        }
        return BaseResponse.errorInstance("编辑新闻失败");
    }

    @ApiOperation(value = "查看新闻详情")
    @GetMapping("/news/{id}")
    public BaseResponse getNewsById(@PathVariable(value = "id") Integer id) {
        NewsReturnDto newsReturnDto = newsService.getNewsById(id);
        if (newsReturnDto == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "id=" + id + "不存在");
        }
        NewsVo newsVo = generateNewsVo(newsReturnDto);
        return BaseResponse.successInstance(newsVo);
    }

    @ApiOperation(value = "删除新闻")
    @DeleteMapping("/news/{id}")
    public BaseResponse deleteNews(@PathVariable(value = "id") Integer id) {
        boolean isSuccess = newsService.deleteNews(id);
        if (isSuccess) {
            return BaseResponse.successInstance("删除新闻成功");
        }
        return BaseResponse.errorInstance("删除新闻失败");
    }

    @ApiOperation(value = "新闻列表")
    @PostMapping("/listNewsByCriteria")
    public BaseResponse listByCriteria(@RequestBody @Validated ListNewsCriteriaDto listNewsCriteriaDto) {
        PageInfo pageInfo = newsService.listByCriteria(listNewsCriteriaDto);
        if (pageInfo != null) {
            List<NewsVo> newsVoList = generateNewsVoList(pageInfo.getList(), listNewsCriteriaDto.getRequestSource());
            pageInfo.setList(newsVoList);
            return BaseResponse.successInstance(pageInfo);
        }
        return BaseResponse.successInstance(pageInfo);
    }


    private List<NewsCategoryVo> generateNewsCategoryVo(List<NewsCategory> allNewsCategory) {
        List<NewsCategoryVo> newsCategoryVoList = new LinkedList<>();
        if (!CollectionUtils.isEmpty(allNewsCategory)) {
            for (NewsCategory newsCategory : allNewsCategory) {
                NewsCategoryVo vo = new NewsCategoryVo();
                vo.setId(newsCategory.getId());
                vo.setName(newsCategory.getName());
                newsCategoryVoList.add(vo);
            }
        }
        return newsCategoryVoList;
    }

    private NewsVo generateNewsVo(NewsReturnDto newsReturnDto) {
        if (newsReturnDto != null) {
            NewsVo newsVo = new NewsVo();
            BeanUtils.copyProperties(newsReturnDto, newsVo);
            NewsCategoryReturnDto newsCategoryReturnDto = newsReturnDto.getNewsCategoryReturnDto();
            if (newsCategoryReturnDto != null) {
                newsVo.setCategory(newsCategoryReturnDto.getName());
            }
            return newsVo;
        }
        return null;
    }

    private List<NewsVo> generateNewsVoList(List<NewsReturnDto> list, Integer requestSource) {
        List<NewsVo> newsVoList = new LinkedList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (NewsReturnDto dto : list) {
                NewsVo newsVo = new NewsVo();
                BeanUtils.copyProperties(dto, newsVo);
                if (dto.getNewsCategoryReturnDto() != null) {
                    NewsCategoryReturnDto newsCategoryReturnDto = dto.getNewsCategoryReturnDto();
                    newsVo.setCategory(newsCategoryReturnDto.getName());
                }
                if (RequestSource.PORTAL.equals(requestSource)) {
                    UserInfo userInfo = dto.getUserInfo();
                    newsVo.setCreateUserName(userInfo == null ? null : userInfo.getName());
                    // 运力后台新闻列表不展示新闻内容为减少网络消耗设置为空
                    newsVo.setContent(null);
                    newsVoList.add(newsVo);
                }
            }
        }
        return newsVoList;
    }
}
