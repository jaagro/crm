package com.jaagro.crm.api.dto.request.express;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author gavin
 */
@Data
@Accessors(chain = true)
public class QueryExpressDto implements Serializable {
    private static final long serialVersionUID = 7275670058562815876L;

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
     * 标签查询字段
     */
    private String  tags;
    /**
     * 类型:1智库 2，直通车
     */
    private Integer categoryId;

    /**
     * 从现在到多久以前的查询
     */
    private String strDate;

}
