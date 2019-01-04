package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.response.dictionary.DictionaryDto;
import com.jaagro.crm.api.service.DictionaryService;
import com.jaagro.crm.biz.mapper.DictionaryMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典管理
 * @author yj
 * @since 2018/12/29
 */
@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryMapperExt dictionaryMapperExt;
    /**
     * 查询字典列表
     *
     * @param category
     * @return
     */
    @Override
    public List<DictionaryDto> listByCategory(String category) {
        return dictionaryMapperExt.listByCategory(category);
    }
}
