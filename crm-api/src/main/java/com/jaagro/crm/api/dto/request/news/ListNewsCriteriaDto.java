package com.jaagro.crm.api.dto.request.news;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询新闻列表条件
 * @author yj
 * @since 2018/11/17
 */
@Data
@Accessors(chain = true)
public class ListNewsCriteriaDto implements Serializable {
    /**
     * 起始页
     */
    @NotNull(message = "{pageNum.NotNull}")
    @Min(value = 1,message = "{pageNum.Min}")
    private Integer pageNum;

    /**
     * 每页条数
     */
    @NotNull(message = "{pageSize.NotNull}")
    @Min(value = 1,message = "{pageSize.Min}")
    private Integer pageSize;

    /**
     * 新闻类别 1-新闻 2-活动
     */
    private Integer category;

    /**
     * 请求来源 1-运力后台 2-官网
     */
    @NotNull(message = "{requestSource.NotNull}")
    @Min(value = 1,message = "{requestSource.Min}")
    private Integer requestSource;

    /**
     * 类别id
     */
    private Integer categoryId;
}
