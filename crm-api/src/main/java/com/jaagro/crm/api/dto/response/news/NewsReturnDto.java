package com.jaagro.crm.api.dto.response.news;

import com.jaagro.constant.UserInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @since 2018/11/16
 */
@Data
@Accessors(chain = true)
public class NewsReturnDto implements Serializable{

    /**
     * 新闻表id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类别id
     */
    private NewsCategoryReturnDto newsCategoryReturnDto;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 来源 1-系统
     */
    private Integer source;

    /**
     * 图片路径
     */
    private String imageUrl;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

    /**
     * 创建人用户信息
     */
    private UserInfo userInfo;
}
