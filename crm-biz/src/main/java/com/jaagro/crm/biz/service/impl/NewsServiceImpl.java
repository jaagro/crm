package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.UserType;
import com.jaagro.crm.api.dto.request.news.CreateNewsDto;
import com.jaagro.crm.api.dto.request.news.ListNewsCriteriaDto;
import com.jaagro.crm.api.dto.request.news.UpdateNewsDto;
import com.jaagro.crm.api.dto.response.news.NewsReturnDto;
import com.jaagro.crm.biz.entity.News;
import com.jaagro.crm.biz.entity.NewsCategory;
import com.jaagro.crm.biz.mapper.NewsCategoryMapperExt;
import com.jaagro.crm.biz.mapper.NewsMapperExt;
import com.jaagro.crm.biz.service.NewsService;
import com.jaagro.crm.biz.service.AuthClientService;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.UserClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;

/**
 * 新闻管理
 * @author yj
 * @since 2018/11/16
 */
@Service
@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "news")
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapperExt newsMapperExt;
    @Autowired
    private NewsCategoryMapperExt newsCategoryMapperExt;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    /**
     * 创建新闻
     *
     * @param createNewsDto
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "news", allEntries = true)
    public boolean createNews(CreateNewsDto createNewsDto) {
        News news = new News();
        BeanUtils.copyProperties(createNewsDto,news);
        UserInfo currentUser = currentUserService.getCurrentUser();
        news.setCreateTime(new Date())
                .setCreateUserId(currentUser == null ? null : currentUser.getId())
                .setEnable(true)
                .setSource(1);
        // 设置类别id
        Integer categoryId = getCategoryIdByCategory(createNewsDto.getCategory());
        news.setCategoryId(categoryId);
        Integer effectedNum = newsMapperExt.insertSelective(news);
        if (effectedNum < 1){
            return false;
        }
        return true;
    }

    /**
     * 查询所有新闻类别
     *
     * @return
     */
    @Override
    @Cacheable
    public List<NewsCategory> getAllNewsCategory() {
        return newsCategoryMapperExt.selectAll();
    }

    /**
     * 编辑新闻
     * @param updateNewsDto
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "news", allEntries = true)
    public boolean updateNews(UpdateNewsDto updateNewsDto) {
        News news = new News();
        BeanUtils.copyProperties(updateNewsDto,news);
        UserInfo currentUser = currentUserService.getCurrentUser();
        news.setModifyTime(new Date())
                .setModifyUserId(currentUser == null ? null : currentUser.getId());
        // 设置类别id
        Integer categoryId = getCategoryIdByCategory(updateNewsDto.getCategory());
        news.setCategoryId(categoryId);
        Integer effectedNum = newsMapperExt.updateByPrimaryKeySelective(news);
        if (effectedNum < 1){
            return false;
        }
        return true;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable
    public NewsReturnDto getNewsById(Integer id) {
        NewsReturnDto newsReturnDto = newsMapperExt.selectById(id);
        if (newsReturnDto != null){
            convertImageUrl(newsReturnDto);
            List<NewsReturnDto> list = new ArrayList<>();
            list.add(newsReturnDto);
            putUserInfo(list);
        }
        return newsReturnDto;
    }

    /**
     * 删除新闻(逻辑删除)
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "news", allEntries = true)
    public boolean deleteNews(Integer id) {
        Integer effectedNum = newsMapperExt.disableNews(id);
        if (effectedNum < 1){
            return false;
        }
        return true;
    }

    /**
     * 查询新闻列表
     *
     * @param listNewsCriteriaDto
     * @return
     */
    @Override
    @Cacheable
    public PageInfo listByCriteria(ListNewsCriteriaDto listNewsCriteriaDto) {
        Integer category = listNewsCriteriaDto.getCategory();
        if (category != null && category > 0){
            List<NewsCategory> newsCategoryList = newsCategoryMapperExt.listByCategory(category);
            if (CollectionUtils.isEmpty(newsCategoryList)){
                throw new RuntimeException("未查到该类别的新闻");
            }
            listNewsCriteriaDto.setCategoryId(newsCategoryList.get(0).getId());
        }
        PageHelper.startPage(listNewsCriteriaDto.getPageNum(),listNewsCriteriaDto.getPageSize());
        List<NewsReturnDto> newsReturnDtoList = newsMapperExt.listByCriteria(listNewsCriteriaDto);
        if (!CollectionUtils.isEmpty(newsReturnDtoList)){
            putUserInfo(newsReturnDtoList);
            newsReturnDtoList.forEach(newsReturnDto -> convertImageUrl(newsReturnDto));
        }
        return new PageInfo<>(newsReturnDtoList);
    }

    private void putUserInfo(List<NewsReturnDto> newsReturnDtoList) {
        if (!CollectionUtils.isEmpty(newsReturnDtoList)){
            Set<Integer> userIdSet = new HashSet<>();
            newsReturnDtoList.forEach(newsReturnDto ->{if (newsReturnDto.getCreateUserId() != null){userIdSet.add(newsReturnDto.getCreateUserId());}});
            if (!CollectionUtils.isEmpty(userIdSet)){
                List<UserInfo> userInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.EMPLOYEE);
                if (!CollectionUtils.isEmpty(userInfoList)){
                    for (NewsReturnDto newsReturnDto : newsReturnDtoList){
                        for (UserInfo userInfo : userInfoList){
                            if (newsReturnDto.getCreateUserId().equals(userInfo.getId())){
                                newsReturnDto.setUserInfo(userInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    private Integer getCategoryIdByCategory(Integer category) {
        if (category != null && category > 0){
            List<NewsCategory> newsCategoryList = newsCategoryMapperExt.listByCategory(category);
            if (CollectionUtils.isEmpty(newsCategoryList)){
                throw new RuntimeException("未查到该类别的新闻");
            }
            return newsCategoryList.get(0).getId();
        }
        return null;
    }

    private String getAbstractImageUrl(String relativeImageUrl){
        if (StringUtils.hasText(relativeImageUrl)){
            String[] strArray = {relativeImageUrl};
            List<URL> urls = ossSignUrlClientService.listSignedUrl(strArray);
            if (!CollectionUtils.isEmpty(urls)){
                return urls.get(0).toString();
            }
        }
        return null;
    }

    private void convertImageUrl(NewsReturnDto newsReturnDto){
        if (newsReturnDto != null){
            if (StringUtils.hasText(newsReturnDto.getImageUrl())){
                newsReturnDto.setImageUrl(getAbstractImageUrl(newsReturnDto.getImageUrl()));
            }
            if(StringUtils.hasText(newsReturnDto.getContent())){
                // TODO 截取新闻主体内容内图片路径转换成绝对路径
            }
        }
    }

}
