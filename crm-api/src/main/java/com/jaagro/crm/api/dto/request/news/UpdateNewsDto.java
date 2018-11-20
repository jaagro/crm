package com.jaagro.crm.api.dto.request.news;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 更新新闻参数
 * @author yj
 * @since 2018/11/16
 */
@Data
@Accessors(chain = true)
public class UpdateNewsDto implements Serializable{
    /**
     * 新闻表id
     */
    @NotNull(message = "{id.NotNull}")
    @Min(value = 1,message = "{id.Min}")
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
     * 新闻类别 1-新闻 2-活动
     */
    private Integer category;
}
