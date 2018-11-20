package com.jaagro.crm.web.vo.news;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻类别
 * @author yj
 * @since 2018/11/16
 */
@Data
@Accessors
public class NewsCategoryVo implements Serializable{
    /**
     * 新闻类别字典表id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

}
