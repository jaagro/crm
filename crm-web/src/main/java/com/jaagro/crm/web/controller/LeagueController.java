package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.league.*;
import com.jaagro.crm.api.dto.response.league.ListLeagueDto;
import com.jaagro.crm.api.service.LeagueService;
import com.jaagro.crm.api.service.TenantPurposeService;
import com.jaagro.crm.web.vo.league.ListLeagueVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 销售机会管理
 *
 * @author baiyiran
 * @Date 2018/11/14
 */
@RestController
@Slf4j
@Api(value = "league", description = "销售机会管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private TenantPurposeService tenantPurposeService;

    @ApiOperation(value = "新增销售机会")
    @PostMapping(value = "/league", consumes = "application/json")
    public BaseResponse insertLeague(@RequestBody CreateLeagueDto createLeagueDto) {
        if (StringUtils.isEmpty(createLeagueDto.getLeagueType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "销售机会类型不能为空");
        }
        if (StringUtils.isEmpty(createLeagueDto.getLeagueName())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "姓名不能为空");
        }
        if (StringUtils.isEmpty(createLeagueDto.getPhone())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "手机号不能为空");
        }
        Map<String, Object> result;
        try {
            result = leagueService.createLeague(createLeagueDto);
        } catch (Exception ex) {
            log.error("录入销售机会:{}", ex.getMessage());
            return BaseResponse.errorInstance(ex.getMessage());
        }
        return BaseResponse.service(result);
    }

    @ApiOperation(value = "分页查询销售机会")
    @PostMapping("/listLeagueByCriteria")
    public BaseResponse listLeagueByCriteria(@RequestBody ListLeagueCerteriaDto certeriaDto) {
        if (StringUtils.isEmpty(certeriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(certeriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        PageInfo pageInfo = leagueService.listLeagueByCriteria(certeriaDto);
        List<ListLeagueDto> leagueDtoList = pageInfo.getList();
        if (!CollectionUtils.isEmpty(leagueDtoList)) {
            List<ListLeagueVo> leagueVoList = new ArrayList<>();
            for (ListLeagueDto leagueDto : leagueDtoList) {
                ListLeagueVo leagueVo = new ListLeagueVo();
                BeanUtils.copyProperties(leagueDto, leagueVo);
                leagueVoList.add(leagueVo);
            }
            pageInfo.setList(leagueVoList);
        }
        return BaseResponse.successInstance(pageInfo);
    }

    @ApiOperation(value = "新增养殖意向")
    @PostMapping(value = "/insertTenantPurpose")
    public BaseResponse insertTenantPurpose(@RequestBody CreateTenantPurposeDto dto) {
        if (StringUtils.isEmpty(dto.getContractName())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "联系人不能为空");
        }
        if (StringUtils.isEmpty(dto.getPhoneNumber())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "手机号码不能为空");
        }
        if (StringUtils.isEmpty(dto.getVerificationCode())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "验证码不能为空");
        }
        tenantPurposeService.insertTenantPurpose(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "养殖意向列表管理")
    @PostMapping(value = "/listTenantPurpose")
    public BaseResponse listTenantPurpose(@RequestBody TenantPuroseCerteria certeriaDto) {
        if (StringUtils.isEmpty(certeriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(certeriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        return BaseResponse.successInstance(tenantPurposeService.listTenantPurpose(certeriaDto));
    }

    @ApiOperation(value = "养殖意向详情")
    @PostMapping(value = "/tenantPurposeDetails/{tenantPurposeId}")
    public BaseResponse tenantPurposeDetails(@PathVariable("tenantPurposeId") Integer tenantPurposeId) {
        if (tenantPurposeId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖意向id不能为空");
        }
        return BaseResponse.successInstance(tenantPurposeService.tenantPurposeDetails(tenantPurposeId));
    }

    @ApiOperation(value = "养殖意向处理")
    @PostMapping(value = "/tenantPurposeDispose")
    public BaseResponse tenantPurposeDispose(@RequestBody PurposeDisposeDto purposeDisposeDto) {
        if (purposeDisposeDto.getTenantPurposeId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖意向id不能为空");
        }
        tenantPurposeService.tenantPurposeDispose(purposeDisposeDto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }
}
