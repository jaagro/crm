package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.response.dictionary.DictionaryDto;

import java.util.List;

/**
 * 字典管理
 * @author yj
 * @since 2018/12/29
 */
public interface DictionaryService {
    /**
     * 查询字典列表
     * @param category
     * @return
     */
    List<DictionaryDto> listByCategory(String category);
}
