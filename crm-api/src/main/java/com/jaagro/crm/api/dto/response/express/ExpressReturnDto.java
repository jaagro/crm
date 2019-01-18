package com.jaagro.crm.api.dto.response.express;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gavin
 * @since 2018/12/29
 */
@Data
@Accessors(chain = true)
public class ExpressReturnDto implements Serializable{


    private static final long serialVersionUID = -7874407299264466014L;
    /**
     * 智库直通车id
     */
    private Integer id;
    /**
     * 类别:1智库类；2直通车
     */
    private Integer categoryId;
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件url
     */
    private String attachUrl;
    /**
     * 归档
     */
    private String toDocument;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 发布人Id
     */
    private Integer createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建人部门名称
     */
    private String departmentName;

    /**
     * 标签查询字段
     */
    private String  tags;
}
