package com.jaagro.crm.web.vo.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典
 * @author yj
 * @since 2018/12/29
 */
@Data
@Accessors(chain = true)
public class DictionaryVo implements Serializable {
    private static final long serialVersionUID = 75863948931404363L;
    /**
     * 字典id
     */
    private Integer dictionaryId;
    /**
     * 字典名称
     */
    private String dictionaryName;
}
