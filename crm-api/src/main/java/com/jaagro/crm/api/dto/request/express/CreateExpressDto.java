package com.jaagro.crm.api.dto.request.express;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @since 2018/12/29
 */
@Data
@Accessors(chain = true)
public class CreateExpressDto implements Serializable {
    private static final long serialVersionUID = -8659244716492450203L;
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
     * 类别:1智库类；2直通车
     */
    @NotNull(message = "{category.NotNull}")
    @Min(value = 1,message = "{category.Min}")
    private Integer categoryId;

    /**
     * 子类别id
     */
    @NotNull(message = "{childCategoryId.NotNull}")
    @Min(value = 1,message = "{childCategoryId.Min}")
    private Integer childCategoryId;

    /**
     * 附件url
     */
    private String attachUrl;

    /**
     * 标签
     */
    @NotBlank(message = "{tags.NotBlank}")
    private String tags;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * "employee","driver"
     */
    private String createUserType;

}
