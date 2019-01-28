package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.response.dictionary.DictionaryDto;
import com.jaagro.crm.api.service.DictionaryService;
import com.jaagro.crm.web.vo.dictionary.DictionaryVo;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典管理
 *
 * @author yj
 * @since 2018/12/29
 */
@RestController
@Api(value = "dictionary", description = "字典管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/listDictionaryByCategory")
    @ApiOperation(value = "根据类别查询字典列表")
    public BaseResponse<List<DictionaryVo>> listDictionaryByCategory(@RequestParam("category") String category) {
        List<DictionaryDto> dictionaryDtoList = dictionaryService.listByCategory(category);
        if (CollectionUtils.isEmpty(dictionaryDtoList)) {
            return BaseResponse.queryDataEmpty();
        }
        List<DictionaryVo> dictionaryVoList = new ArrayList<>();
        dictionaryDtoList.forEach(dictionaryDto -> {
            DictionaryVo dictionaryVo = new DictionaryVo();
            dictionaryVo.setDictionaryId(dictionaryDto.getId()).setDictionaryName(dictionaryDto.getTypeName());
            dictionaryVoList.add(dictionaryVo);
        });
        return BaseResponse.successInstance(dictionaryVoList);
    }

    @GetMapping("/getDictionaryById")
    @ApiOperation(value = "根据id查询字典")
    public DictionaryDto listDictionaryByCategory(@RequestParam("id") Integer id) {
        return dictionaryService.getById(id);
    }
}
