package com.jaagro.crm.biz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @since 20181229
 */
@Data
@Accessors(chain = true)
public class Dictionary implements Serializable {
    private static final long serialVersionUID = -2089983202544204443L;
    /**
     * 通用字典表id
     */
    private Integer id;

    /**
     * 类型英文名
     */
    private String type;

    /**
     * 类型中文名称
     */
    private String typeName;

    /**
     * 展示顺序,值越小越靠前
     */
    private Integer displayOrder;

    /**
     * 所属类别英文名
     */
    private String category;

    /**
     * 所属类别中文名
     */
    private String categoryName;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

}