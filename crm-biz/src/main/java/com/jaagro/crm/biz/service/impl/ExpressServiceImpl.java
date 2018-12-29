package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.ToDocment;
import com.jaagro.crm.api.constant.UserType;
import com.jaagro.crm.api.dto.request.express.CreateExpressDto;
import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;
import com.jaagro.crm.api.dto.response.news.NewsReturnDto;
import com.jaagro.crm.api.entity.Express;
import com.jaagro.crm.api.service.ExpressService;
import com.jaagro.crm.biz.mapper.ExpressMapperExt;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.UserClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 新闻管理
 *
 * @author gavin
 * @since 2018/12/29
 */
@Service
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressMapperExt expressMapperExt;

    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;

    /**
     * 创建智库直通车
     *
     * @param createExpressDto
     * @return
     */
    @Override
    public boolean createExpress(CreateExpressDto createExpressDto) {
        if (createExpressDto != null){
            Express express = new Express();
            BeanUtils.copyProperties(createExpressDto,express);
            UserInfo currentUser = currentUserService.getCurrentUser();
            express.setCreateTime(new Date())
                    .setCreateUserId(currentUser == null ? null : currentUser.getId())
                    .setEnable(true);
            Integer effectedNum = expressMapperExt.insertSelective(express);
            if (effectedNum < 1) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Express getExpressById(Integer id) {
        Express returnDto = expressMapperExt.selectByPrimaryKey(id);
        if (returnDto != null) {
//            convertImageUrl(newsReturnDto);
            List<NewsReturnDto> list = new ArrayList<>();
//            list.add(newsReturnDto);
//            putUserInfo(list);
        }
        return returnDto;
    }


    /**
     * 查询智库直通车列表
     *
     * @param criteriaDto
     * @return
     */
    @Override
    public PageInfo listExpressByCriteria(QueryExpressDto criteriaDto) {

        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        List<ExpressReturnDto> returnDtoList = expressMapperExt.listByCriteria(criteriaDto);
        if (!CollectionUtils.isEmpty(returnDtoList)) {
            Set<Integer> userIdSet = new HashSet<>();
            returnDtoList.forEach(newsReturnDto -> {
                if (newsReturnDto.getCreateUserId() != null) {
                    userIdSet.add(newsReturnDto.getCreateUserId());
                }
            });
            if (!CollectionUtils.isEmpty(userIdSet)) {
                List<UserInfo> userInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.EMPLOYEE);
                if (!CollectionUtils.isEmpty(userInfoList)) {
                    for (ExpressReturnDto returnDto : returnDtoList) {
                        for (UserInfo userInfo : userInfoList) {
                            if (returnDto.getCreateUserId().equals(userInfo.getId())) {
                                returnDto.setCreateUserName(userInfo.getName());
                            }
                        }
                    }
                }
            }
//            newsReturnDtoList.forEach(newsReturnDto -> convertImageUrl(newsReturnDto));
        }
        return new PageInfo<>(returnDtoList);
    }

    /**
     * 设为档案
     *
     * @param id
     * @return
     * @author yj
     */
    @Override
    public boolean toDocument(Integer id) {
        Express express = expressMapperExt.selectByPrimaryKey(id);
        if (express == null){
            throw new RuntimeException("id="+id+"不存在");
        }
        UserInfo currentUser = currentUserService.getCurrentUser();
        express.setToDocument(ToDocment.TRUE)
                .setModifyTime(new Date())
                .setModifyUserId(currentUser == null ? null : currentUser.getId());
        int effective = expressMapperExt.updateByPrimaryKeySelective(express);
        if(effective == 1){
            return true;
        }
        return false;
    }


    private String getAbstractImageUrl(String relativeImageUrl) {
        if (StringUtils.hasText(relativeImageUrl)) {
            String[] strArray = {relativeImageUrl};
            List<URL> urls = ossSignUrlClientService.listSignedUrl(strArray);
            if (!CollectionUtils.isEmpty(urls)) {
                return urls.get(0).toString();
            }
        }
        return null;
    }

    private void convertImageUrl(NewsReturnDto newsReturnDto) {
        if (newsReturnDto != null) {
            if (StringUtils.hasText(newsReturnDto.getImageUrl())) {
                newsReturnDto.setImageUrl(getAbstractImageUrl(newsReturnDto.getImageUrl()));
            }
            String content = newsReturnDto.getContent();
            if (StringUtils.hasText(content)) {
                // 获取新闻主体内容中所有的相对路径
                List<String> imgStr = getImgUrl(content);
                // 替换新闻主体内容中所有的相对路径改为绝对路径
                content = replaceImageUrl(imgStr, content);
                newsReturnDto.setContent(content);
            }
        }
    }

    private String replaceImageUrl(List<String> imgStr, String content) {
        if (!CollectionUtils.isEmpty(imgStr) && StringUtils.hasText(content)) {
            for (String imgUrl : imgStr) {
                String abstractImgUrl = getAbstractImageUrl(imgUrl);
                content = content.replace(imgUrl, abstractImgUrl);
            }
        }
        return content;
    }

    /**
     * @param htmlStr
     * @return
     */
    private static List<String> getImgUrl(String htmlStr) {
        List<String> pics = new ArrayList<>();
        String img;
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<.*img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

}
