package com.jaagro.crm.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @since 20181116
 */
@Data
@Accessors(chain = true)
public class NewsCategoryDto implements Serializable{
    private static final long serialVersionUID = 4189502807629278079L;
    /**
     * 新闻类别字典表id
     */
    private Integer id;

    /**
     * 类型 11-运力新闻 12-养殖新闻  21-运力活动 22-养殖活动
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 类别 1-新闻 2-活动
     */
    private Integer category;

    /**
     * 创建时间
     */
    private Date createTime;

}