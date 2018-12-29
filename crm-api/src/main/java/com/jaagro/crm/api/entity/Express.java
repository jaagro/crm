package com.jaagro.crm.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @since 20181229
 */
@Data
@Accessors(chain = true)
public class Express implements Serializable {
    private static final long serialVersionUID = -1100310077988226748L;
    /**
     * 智能直通车id
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
     * 类别:1智库类；2直通车
     */
    private Integer categoryId;

    /**
     * 子类别id
     */
    private Integer childCategoryId;

    /**
     * 附件url
     */
    private String attachUrl;

    /**
     * 归档标记
     */
    private String toDocument;

    /**
     * 标签
     */
    private String tags;

    /**
     * 发布时间
     */
    private Date publishTime;

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
}