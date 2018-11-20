package com.jaagro.crm.api.dto.request.news;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建新闻参数
 * @author yj
 * @since 2018/11/16
 */
@Data
@Accessors(chain = true)
public class CreateNewsDto implements Serializable{

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

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
     * 新闻类别 1-新闻 2-活动
     */
    private Integer category;

}
