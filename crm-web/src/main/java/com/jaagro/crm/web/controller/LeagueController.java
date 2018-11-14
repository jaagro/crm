package com.jaagro.crm.web.controller;

import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售机会管理
 *
 * @author baiyiran
 * @Date 2018/11/14
 */
@RestController
@Api(value = "league", description = "销售机会管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeagueController {

    @ApiOperation(value = "新增销售机会")
    @PostMapping("/league")
    public BaseResponse insertLeague() {
        return BaseResponse.successInstance(null);
    }

}
