package com.jaagro.crm.api.dto.request.news;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "{title.NotBlank}")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "{content.NotBlank}")
    private String content;

    /**
     * 发布时间
     */
    @NotNull(message = "{publishTime.NotNull}")
    private Date publishTime;

    /**
     * 来源 1-系统
     */
    @Min(value = 1,message = "{source.Min}")
    private Integer source;

    /**
     * 图片路径
     */
    @NotBlank(message = "{imageUrl.NotBlank}")
    private String imageUrl;

    /**
     * 新闻类别 1-新闻 2-活动
     */
    @NotNull(message = "{category.NotNull}")
    @Min(value = 1,message = "{category.Min}")
    private Integer category;

}
