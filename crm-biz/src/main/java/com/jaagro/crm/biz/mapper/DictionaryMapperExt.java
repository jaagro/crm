package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.response.dictionary.DictionaryDto;

import java.util.List;

/**
 * 字典表CRUD扩展
 * @author yj
 * @since 2018/12/29
 */
public interface DictionaryMapperExt extends DictionaryMapper {
    /**
     * 根据
     * @param category
     * @return
     */
    List<DictionaryDto> listByCategory(String category);
}
