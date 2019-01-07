package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.ContractType;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckTeamContractCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.ListDriverContractSettleDto;
import com.jaagro.crm.api.service.ContractOilPriceService;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.crm.biz.service.impl.CurrentUserService;
import com.jaagro.crm.web.vo.contract.TruckTeamContractPriceHistoryVo;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.TruckTeam;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapperExt;
import com.jaagro.crm.biz.mapper.TruckTeamMapperExt;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@RestController
@Api(value = "truckTeamContract", description = "车队合同管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TruckTeamContractController {

    @Autowired
    private TruckTeamContractService truckTeamContractService;

    @Autowired
    private TruckTeamContractMapperExt truckTeamContractMapper;
    @Autowired
    private TruckTeamMapperExt truckTeamMapper;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private ContractOilPriceService contractOilPriceService;


    /**
     * 新增合同
     *
     * @param dto
     * @return
     */
    @ApiOperation("新增合同")
    @PostMapping("/truckTeamContract")
    public BaseResponse insert(@RequestBody CreateTruckTeamContractDto dto) {
        if (StringUtils.isEmpty(dto.getTruckTeamId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队ID不能为空");
        }
        if (StringUtils.isEmpty(dto.getBussinessType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "业务类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getContractNumber())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号不能为空");
        }
        if (this.truckTeamContractMapper.getByContractNumber(dto.getContractNumber()) != null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号已存在");
        }
        if (StringUtils.isEmpty(dto.getStartDate())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同开始时间不能为空");
        }
        if (StringUtils.isEmpty(dto.getEndDate())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同结束时间不能为空");
        }
        Map<String, Object> resultMap;
        try {
            resultMap = truckTeamContractService.createTruckTeamContract(dto);
        } catch (Exception ex) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
        }
        return BaseResponse.service(resultMap);
    }

    @ApiOperation("修改合同")
    @PostMapping("/updateTruckTeamContract")
    public BaseResponse updateTruckTeamContract(@RequestBody UpdateTruckTeamContractDto dto) {
        if (StringUtils.isEmpty(dto.getTruckTeamId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队ID不能为空");
        }
        if (StringUtils.isEmpty(dto.getBussinessType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "业务类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getContractNumber())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号不能为空");
        }
        return BaseResponse.service(truckTeamContractService.updateTruckTeamContract(dto));
    }

    @ApiOperation("删除合同[逻辑]")
    @GetMapping("/deleteTeamContract/{id}")
    public BaseResponse deleteTeamContract(@PathVariable Integer id) {
        Map<String, Object> result = truckTeamContractService.disableContract(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("查询单个合同")
    @GetMapping("/truckTeamContract/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (truckTeamContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "此合同不存在");
        }
        Map<String, Object> result = truckTeamContractService.getById(id);
        return BaseResponse.service(result);
    }

    @ApiOperation("查询单个合同编号")
    @GetMapping("/truckTeamContractNumber/{contractNumber}")
    public BaseResponse getByContractNumber(@PathVariable String contractNumber) {
        if (truckTeamContractMapper.getByContractNumber(contractNumber) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到合同编号");
        }
        Map<String, Object> result = truckTeamContractService.getByContractNumber(contractNumber);
        return BaseResponse.service(result);
    }

    @ApiOperation("分页查询合同")
    @PostMapping("/listTruckTeam")
    public BaseResponse listTruckTeam(@RequestBody ListTruckTeamContractCriteriaDto criteriaDto) {
        Map<String, Object> result = truckTeamContractService.listByCriteria(criteriaDto);
        return BaseResponse.service(result);
    }

    @ApiOperation("合同报价列表")
    @PostMapping("/listTruckTeamContractPrice")
    public BaseResponse listTruckTeamContractPrice(@RequestBody DriverContractSettleCondition condition) {
        return BaseResponse.successInstance(truckTeamContractService.listDriverContractPrice(condition));
    }


    @ApiOperation("合同报价明细")
    @PostMapping("/listTruckTeamContractPriceDetails")
    public BaseResponse listTruckTeamContractPriceDetails(@RequestBody DriverContractSettleCondition condition) {
        return BaseResponse.successInstance(truckTeamContractService.listTruckTeamContractPriceDetails(condition));
    }

    @ApiOperation("查询车型")
    @GetMapping("/listTruckTeamTypeByGoodType/{goodType}")
    public BaseResponse listTruckTeamTypeByGoodType(@PathVariable Integer goodType) {
        return BaseResponse.successInstance(truckTeamContractService.listTruckTeamTypeByGoodType(goodType));
    }

    @ApiOperation("删除合同报价")
    @PostMapping("/deleteTeamContractPrice")
    public BaseResponse deleteTeamContractPrice(@RequestBody DriverContractSettleCondition condition) {
        truckTeamContractService.deleteTeamContractPrice(condition);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @ApiOperation("合同报价新增更新")
    @PostMapping("/updateTeamContractPrice")
    public BaseResponse updateTeamContractPrice(@RequestBody CreateDriverContractSettleDto createDriverContractSettleDto) {
        truckTeamContractService.createTruckTeamContractPrice(createDriverContractSettleDto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @ApiOperation("最新油价")
    @PostMapping("/getNewOilPrice")
    public BaseResponse getNewOilPrice(@RequestBody ContractOilPriceCondition condition) {
        GetContractOilPriceDto newOilPrice = truckTeamContractService.getNewOilPrice(condition);
        return BaseResponse.successInstance(newOilPrice);
    }

    @ApiOperation("更新油价")
    @PostMapping("/updateOilPrice")
    public BaseResponse updateOilPrice(@RequestBody CreateContractOilPriceDto createContractOilPriceDto) {
        if (createContractOilPriceDto.getContractId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同id不能为空");
        }
        truckTeamContractService.updateOilPrice(createContractOilPriceDto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @ApiOperation("合同报价历史列表")
    @PostMapping("/listTruckTeamContractPriceHistoryDetails")
    public BaseResponse listTruckTeamContractPriceHistoryDetails(@RequestBody DriverContractSettleCondition condition) {
        TruckTeamContract truckTeamContract = null;
        TruckTeam truckTeam = null;
        PageInfo listDriverContractSettleDtoPageInfo = truckTeamContractService.listTruckTeamContractPriceHistoryDetails(condition);
        List<ListDriverContractSettleDto> listDriverContractSettleDtos = null;
        List<TruckTeamContractPriceHistoryVo> truckTeamContractPriceHistoryVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(listDriverContractSettleDtoPageInfo.getList())) {
            listDriverContractSettleDtos = listDriverContractSettleDtoPageInfo.getList();
        }
        if (listDriverContractSettleDtos == null) {
            throw new RuntimeException("当前没有合同报价历史记录");
        }
        for (ListDriverContractSettleDto listDriverContractSettleDto : listDriverContractSettleDtos) {
            if (listDriverContractSettleDto != null) {
                TruckTeamContractPriceHistoryVo truckTeamContractPriceHistoryVo = new TruckTeamContractPriceHistoryVo();
                if (truckTeamContract == null) {
                    truckTeamContract = truckTeamContractMapper.selectByPrimaryKey(listDriverContractSettleDto.getTruckTeamContractId());

                }
                if (truckTeamContract != null && truckTeam == null) {
                    truckTeam = truckTeamMapper.selectByPrimaryKey(truckTeamContract.getTruckTeamId());
                }
                BaseResponse<UserInfo> globalUser = userClientService.getGlobalUser(listDriverContractSettleDto.getCreateUserId());
                UserInfo userInfo = null;
                if (globalUser.getData() != null) {
                    userInfo = globalUser.getData();
                }
                BeanUtils.copyProperties(listDriverContractSettleDto, truckTeamContractPriceHistoryVo);
                truckTeamContractPriceHistoryVo
                        .setTeamType(truckTeam.getTeamType() == null ? null : truckTeam.getTeamType())
                        .setTruckTeamName(truckTeam.getTeamName() == null ? null : truckTeam.getTeamName())
                        .setCreateUserName(userInfo.getName() == null ? null : userInfo.getName());
                List<CreateDriverContractSettleSectionDto> createDriverContractSettleSectionDto = listDriverContractSettleDto.getCreateDriverContractSettleSectionDto();
                if (!CollectionUtils.isEmpty(createDriverContractSettleSectionDto)) {
                    truckTeamContractPriceHistoryVo.setCreateDriverContractSettleSectionDto(createDriverContractSettleSectionDto);
                }
                truckTeamContractPriceHistoryVos.add(truckTeamContractPriceHistoryVo);
            }
        }
        listDriverContractSettleDtoPageInfo.setList(truckTeamContractPriceHistoryVos);
        return BaseResponse.successInstance(listDriverContractSettleDtoPageInfo);
    }
}
