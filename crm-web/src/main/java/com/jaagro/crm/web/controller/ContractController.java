package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ContractType;
import com.jaagro.crm.api.constant.GoodsType;
import com.jaagro.crm.api.constant.WeChatCustomerType;
import com.jaagro.crm.api.dto.base.GetCustomerUserDto;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;
import com.jaagro.crm.api.dto.request.customer.ShowCustomerContractDto;
import com.jaagro.crm.api.dto.request.customer.ShowSiteDto;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamContractReturnDto;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.ContractQualification;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.entity.TruckTeam;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.impl.CurrentUserService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户合同管理
 *
 * @author baiyiran
 */
@RestController
@Api(value = "contract", description = "客户合同管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerSiteService siteService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private QualificationVerifyLogService logService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private CustomerContractSettleRuleService settleRuleService;
    @Autowired
    private CustomerContractSettlePriceService settlePriceService;
    @Autowired
    private ContractQualificationService contractQualificationService;
    @Autowired
    private TruckTeamMapperExt teamMapper;
    @Autowired
    private CustomerMapperExt customerMapper;
    @Autowired
    private TruckTeamMapperExt truckTeamMapper;
    @Autowired
    private CustomerContractMapperExt customerContractMapper;
    @Autowired
    private TruckTeamContractMapperExt truckTeamContractMapper;
    @Autowired
    private ContractQualificationMapperExt qualificationMapper;
    @Autowired
    private CurrentUserService userService;

    /**
     * 合同新增
     *
     * @param dto
     * @return
     */
    @ApiOperation("合同新增")
    @PostMapping("/contract")
    public BaseResponse createContract(@RequestBody CreateContractDto dto) {
        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idNull("客户不能为空");
        }
        if (this.customerMapper.selectByPrimaryKey(dto.getCustomerId()) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不存在");
        }
        UpdateContractDto updateContractDto = new UpdateContractDto();
        updateContractDto.setContractNumber(dto.getContractNumber());
        if (this.customerContractMapper.getByUpdateDto(updateContractDto) != null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号已存在");
        }
        Map<String, Object> result;
        try {
            result = contractService.createContract(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(e.getMessage());
        }
        return BaseResponse.service(result);
    }

    /**
     * 合同修改
     *
     * @param dto
     * @return
     */
    @ApiOperation("合同修改")
    @PutMapping("/contract")
    public BaseResponse updateContract(@RequestBody UpdateContractDto dto) {
        CustomerContract customerContract = customerContractMapper.selectByPrimaryKey(dto.getId());
        if (customerContract == null) {
            return BaseResponse.idError("合同不存在");
        }
        if (StringUtils.isEmpty(dto.getCustomerId())) {
            return BaseResponse.idError("合同客户不能为空");
        }
        if (this.customerContractMapper.getByUpdateDto(dto) != null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同编号已存在");
        }
        Map<String, Object> result;
        try {
            result = contractService.updateContract(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
        }
        return BaseResponse.service(result);
    }

    /**
     * 查询单个合同
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个合同")
    @GetMapping("/contract/{id}")
    public BaseResponse getById(@PathVariable Integer id) {
        if (this.customerContractMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同不存在");
        }
        Map<String, Object> result = contractService.getById(id);
        return BaseResponse.service(result);
    }

    /**
     * 分页查询合同
     *
     * @param dto
     * @return
     */
    @ApiOperation("分页查询合同")
    @PostMapping("/listContractByCriteria")
    public BaseResponse listByCriteria(@RequestBody ListContractCriteriaDto dto) {
        return BaseResponse.service(contractService.listByCriteria(dto));
    }

    @ApiOperation("查询客户有效合同")
    @GetMapping("/listContractByCustomerId/{customerId}")
    public BaseResponse listByCustomerId(@PathVariable("customerId") Integer customerId) {
        List<ShowCustomerContractDto> result = contractService.listShowCustomerContractByCustomerId(customerId);
        if (StringUtils.isEmpty(result)) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查无数据");
        }
        return BaseResponse.successInstance(result);
    }

    /**
     * 逻辑删除合同
     *
     * @param id
     * @return
     */
    @ApiOperation("删除合同【逻辑】")
    @GetMapping("/deleteContract/{id}")
    public BaseResponse deleteContract(@PathVariable Integer id) {
        return BaseResponse.service(contractService.disableById(id));
    }

    /**
     * 小程序下单页面，根据当前客户获取货物类型
     *
     * @return
     */
    @ApiOperation("提供给小程序开单：查询客户有效合同")
    @GetMapping("/listContractByToken")
    public BaseResponse listContractByToken() {
        GetCustomerUserDto customerUserDto = currentUserService.getCustomerUserById();
        if (customerUserDto != null) {
            Integer customerId;
            if (customerUserDto.getCustomerType().equals(WeChatCustomerType.CUSTOER)) {
                customerId = customerUserDto.getRelevanceId();
            } else {
                ShowSiteDto showSiteDto = siteService.getShowSiteById(customerUserDto.getRelevanceId());
                customerId = showSiteDto.getCustomerId();
            }
            if (customerId != null) {
                List<ShowCustomerContractDto> result = contractService.listShowCustomerContractByCustomerId(customerId);
                if (StringUtils.isEmpty(result)) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查无数据");
                }
                return BaseResponse.successInstance(result);
            }
        } else {
            return BaseResponse.errorInstance("获取当前用户失败");
        }
        return BaseResponse.errorInstance("获取当前用户失败");
    }

    //----------------------------------------------合同资质-------------------------------------------------

    /**
     * 合同资质新增【单张】
     *
     * @param dto
     * @return
     */
    @ApiOperation("合同资质新增【单张】")
    @PostMapping("/ContractQualificationByOne")
    public BaseResponse createContractQualificationByOne(@RequestBody CreateContractQualificationDto dto) {
        if (StringUtils.isEmpty(dto.getRelevanceId())) {
            return BaseResponse.idError("资质关联id不能为空");
        }
        if (StringUtils.isEmpty(dto.getRelevanceType())) {
            return BaseResponse.idError("资质关联类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getCertificateType())) {
            return BaseResponse.idError("资质类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getCertificateImageUrl())) {
            return BaseResponse.idError("资质路径不能为空");
        }
        Map<String, Object> result;
        try {
            result = contractQualificationService.createQuation(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
        }
        return BaseResponse.service(result);
    }

    /**
     * 合同资质修改【单张】
     *
     * @param dto
     * @return
     */
    @ApiOperation("合同资质修改【单张】")
    @PutMapping("/ContractQualificationByOne")
    public BaseResponse updateContractQualificationByOne(@RequestBody UpdateContractQualificationDto dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return BaseResponse.idError("合同资质id不能为空");
        }
        if (customerContractMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.idError("合同资质不存在");
        }
        Map<String, Object> result;
        try {
            result = contractQualificationService.updateContractQuaion(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
        }
        return BaseResponse.service(result);
    }

    /**
     * [逻辑]删除合同资质
     *
     * @param id
     * @return
     */
    @ApiOperation("[逻辑]删除合同资质")
    @GetMapping("/disableContractQualification/{id}")
    public BaseResponse disContractQualificationById(@PathVariable Integer id) {
        if (this.qualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到相应信息");
        }
        return BaseResponse.service(contractQualificationService.disableContractQuaion(id));
    }

    /**
     * 删除合同资质
     *
     * @param id
     * @return
     */
    @ApiOperation("删除合同资质")
    @GetMapping("/deleteContractQualification/{id}")
    public BaseResponse deleteContractQualificationById(@PathVariable Integer id) {
        if (this.qualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到相应信息");
        }
        int result = qualificationMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            return BaseResponse.successInstance("删除成功");
        } else {
            return BaseResponse.errorInstance("删除失败");
        }

    }

    /**
     * 查询单个合同资质【包括合同详细信息】
     *
     * @param id
     * @return
     */
    @ApiOperation("查询单个合同资质【包括合同详细信息】")
    @GetMapping("/ContractQualification/{id}")
    public BaseResponse getContractQualificationById(@PathVariable Integer id) {
        if (this.qualificationMapper.selectByPrimaryKey(id) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到相应信息");
        }
        //返回的合同资质
        ReturnCheckContractQualificationDto checkContractQualificationDto = this.qualificationMapper.getQualificationById(id);
        if (checkContractQualificationDto != null) {
            //替换资质证照地址
            String[] strArray = {checkContractQualificationDto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            checkContractQualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            //填充运力合同信息
            checkContractQualificationDto.setTruckTeamContractReturnDto(this.truckTeamContractMapper.getById(checkContractQualificationDto.getRelevanceId()));
            return BaseResponse.successInstance(checkContractQualificationDto);
        }
        return BaseResponse.successInstance(checkContractQualificationDto);
    }

    /**
     * 根据合同id查询资质
     *
     * @param contractId
     * @return
     */
    @ApiOperation("根据合同id查询资质")
    @PostMapping("/listConQualfctnByContractId")
    public BaseResponse listConQualfctnByContractId(@PathVariable Integer contractId) {
        return BaseResponse.successInstance(qualificationMapper.listByContractId(contractId));
    }

    //--------------------------------------------------------审核合同--------------------------------------------------------

    /**
     * 待审核合同资质分页
     *
     * @param dto
     * @return
     */
    @ApiOperation("待审核合同资质分页[客户&司机]")
    @PostMapping("/listContractQuaByCriteria")
    public BaseResponse listContractQuaByCriteria(@RequestBody ListContractQualificationCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<ReturnCheckContractQualificationDto> qualificationDtos = qualificationMapper.listByCriteria(dto);
        if (qualificationDtos.size() > 0) {
            for (ReturnCheckContractQualificationDto checkContractQualificationDto : qualificationDtos
            ) {
                TruckTeamContractReturnDto contractReturnDto = checkContractQualificationDto.getTruckTeamContractReturnDto();
                if (contractReturnDto != null) {
                    TruckTeam truckTeam = this.truckTeamMapper.selectByPrimaryKey(contractReturnDto.getTruckTeamId());
                    if (truckTeam != null) {
                        contractReturnDto.setTruckTeamName(truckTeam.getTeamName());
                    }
                }
            }
        }
        return BaseResponse.successInstance(new PageInfo<>(qualificationDtos));
    }

    /**
     * 待审核合同资质分页[养殖户]
     *
     * @param dto
     * @return
     */
    @ApiOperation("待审核合同资质分页[养殖户]")
    @PostMapping("/listPlantContractQuaByCriteria")
    public BaseResponse listPlantContractQuaByCriteria(@RequestBody ListContractQualificationCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        dto.setTenantId(userService.getCurrentUser().getTenantId());
        List<ReturnCheckContractQualificationDto> qualificationDtos = qualificationMapper.listPlantQuaByCriteria(dto);
        if (qualificationDtos.size() > 0) {
            for (ReturnCheckContractQualificationDto checkContractQualificationDto : qualificationDtos
            ) {
                TruckTeamContractReturnDto contractReturnDto = checkContractQualificationDto.getTruckTeamContractReturnDto();
                if (contractReturnDto != null) {
                    TruckTeam truckTeam = this.truckTeamMapper.selectByPrimaryKey(contractReturnDto.getTruckTeamId());
                    if (truckTeam != null) {
                        contractReturnDto.setTruckTeamName(truckTeam.getTeamName());
                    }
                }
            }
        }
        return BaseResponse.successInstance(new PageInfo<>(qualificationDtos));
    }

    /**
     * 合同资质待审核获取下一个
     *
     * @param
     * @return
     */
    @ApiOperation("合同资质待审核获取下一个[客户&司机]")
    @GetMapping("/getContractByCustmIdAuto/{relevanceId}/{relevanceType}")
    public BaseResponse getContractByCustmIdAuto(@PathVariable Integer relevanceId, @PathVariable Integer relevanceType) {
        /**
         * 客户合同
         */
        if (relevanceType == 1 || relevanceType == 3) {
            CustomerContract customerContract = this.customerContractMapper.selectByPrimaryKey(relevanceId);
            if (customerContract == null) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户合同不存在");
            }
            //查询此客户是否有合同
            List<ReturnContractDto> contractDtoList = this.customerContractMapper.getByCustomerId(customerContract.getCustomerId());
            if (contractDtoList.size() < 1) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户未上传合同");
            }
        } else {
            /**
             * 车队合同
             */
            TruckTeamContract teamContract = this.truckTeamContractMapper.selectByPrimaryKey(relevanceId);
            if (teamContract == null) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队合同不存在");
            }
        }
        ListContractQualificationCriteriaDto dto = new ListContractQualificationCriteriaDto();
        dto
                .setRelevanceId(relevanceId)
                .setRelevanceType(relevanceType);

        //返回要审核的合同
        List<ReturnCheckContractQualificationDto> contractDtos = this.qualificationMapper.listByCriteria(dto);
        if (contractDtos != null && contractDtos.size() > 0) {
            ReturnCheckContractQualificationDto checkContractQualificationDto = contractDtos.get(0);
            //替换资质证照地址
            String[] strArray = {checkContractQualificationDto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            checkContractQualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            //填充运力合同信息
            checkContractQualificationDto.setTruckTeamContractReturnDto(this.truckTeamContractMapper.getById(checkContractQualificationDto.getRelevanceId()));
            return BaseResponse.successInstance(checkContractQualificationDto);
        }
        return BaseResponse.successInstance(null);
    }

    /**
     * 获取待审核合同资质详情[客户&司机]
     *
     * @param
     * @return
     */
    @ApiOperation("获取待审核合同资质详情[客户&司机]")
    @GetMapping("/getContractById/{id}")
    public BaseResponse getContractById(@PathVariable Integer id) {
        ContractQualification qualification = qualificationMapper.selectByPrimaryKey(id);
        if (qualification == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同资质查询无此相关数据");
        }
        //返回要审核的合同
        ReturnCheckContractQualificationDto checkContractQualificationDto = this.qualificationMapper.getById(id);
        if (checkContractQualificationDto != null) {
            //替换资质证照地址
            String[] strArray = {checkContractQualificationDto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            checkContractQualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            //填充运力合同信息
            checkContractQualificationDto.setTruckTeamContractReturnDto(this.truckTeamContractMapper.getById(checkContractQualificationDto.getRelevanceId()));
            return BaseResponse.successInstance(checkContractQualificationDto);
        }
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "没有查询到需要审核的资质");
    }

    /**
     * 审核合同资质
     *
     * @param dto
     * @return
     */
    @ApiOperation("审核客户合同资质[客户&司机]")
    @PostMapping("/checkContractQualification")
    public BaseResponse listConQualfctnByContractId(@RequestBody UpdateContractQualificationDto dto) {
        if (dto.getId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质id[id]不能为空");
        }
        if (this.qualificationMapper.selectByPrimaryKey(dto.getId()) == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质不存在");
        }
        //1-客户合同 2-司机合同
        if (dto.getRelevanceType() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "资质关联类型[RelevanceType]不能为空");
        }
        //审核记录
        CreateQualificationVerifyLogDto logDto = new CreateQualificationVerifyLogDto();
        if (!dto.getCertificateStatus().equals(AuditStatus.NORMAL_COOPERATION)) {
            if (dto.getDescription() == null) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "审核不通过时描述信息不能为空");
            }
            logDto.setDescription(dto.getDescription());
        }
        this.contractQualificationService.updateContractQuaion(dto);
        logDto
                .setVertifyResult(dto.getCertificateStatus())
                .setReferencesId(dto.getId());
        // 1-客户资质 2-运力资质 3-客户合同 4-运力合同
        if (dto.getRelevanceType() == 1) {
            logDto.setCertificateType(3);
        } else if (dto.getRelevanceType() == 3) {
            logDto.setCertificateType(3);
        } else {
            logDto.setCertificateType(4);
        }
        return BaseResponse.service(this.logService.createVerifyLog(logDto));
    }

    //--------------------------------------------------提供给微服务调用------------------------------------------------------

    @Ignore
    @GetMapping("/getShowCustomerContract/{id}")
    public ShowCustomerContractDto getShowCustomerContractById(@PathVariable("id") Integer id) {
        return customerContractMapper.getShowCustomerContractById(id);
    }

    @ApiOperation("根据货物类型、客户id、运单完成时间获取客户合同和运力合同")
    @PostMapping("/getContractByContractDto")
    public ContractDto getContractByContractDto(@RequestBody @Validated ContractDto contractDto) {
        if (contractDto.getContractType() == 1 && contractDto.getCustomerId() != null) {
            List<ContractDto> contractDtos = customerContractMapper.getCustomerContract(contractDto);
            if (!CollectionUtils.isEmpty(contractDtos)) {

                ContractDto contract = contractDtos.get(0);
                contract.setContractType(contractDto.getContractType());
                contract.setGoodsType(contractDto.getGoodsType());
                contract.setWaybillDoneDate(contractDto.getWaybillDoneDate());
                return contract;
            }
        }
        if (contractDto.getContractType() == 2 && contractDto.getTruckTeamId() != null) {
            List<ContractDto> contractDtos = truckTeamContractMapper.getTruckTeamContract(contractDto);
            if (!CollectionUtils.isEmpty(contractDtos)) {
                ContractDto contract = contractDtos.get(0);
                contract.setContractType(contractDto.getContractType());
                contract.setGoodsType(contractDto.getGoodsType());
                contract.setTruckTeamId(contractDto.getTruckTeamId());
                contract.setWaybillDoneDate(contractDto.getWaybillDoneDate());
                return contract;
            }
        }
        return null;

    }
    //--------------------------------------------------------合同结算信息-----------------------------------------------------------

    /**
     * 合同报价新增
     *
     * @param priceDtoList
     * @return
     */
    @ApiOperation("合同结算信息新增")
    @PostMapping("/customerContractSettlePrice")
    public BaseResponse createCustomerContractSettlePrice(@RequestBody List<CreateCustomerSettlePriceDto> priceDtoList) {
        if (!CollectionUtils.isEmpty(priceDtoList)) {
            for (CreateCustomerSettlePriceDto priceDto : priceDtoList) {
                if (StringUtils.isEmpty(priceDto.getMileage())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "里程数不能为空");
                }
                if (StringUtils.isEmpty(priceDto.getSettlePrice())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "结算单价不能为空");
                }
                if (StringUtils.isEmpty(priceDto.getCustomerContractId())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同id不能为空");
                }
                CustomerContract customerContract = customerContractMapper.selectByPrimaryKey(priceDto.getCustomerContractId());
                if (customerContract.getEndDate().before(new Date())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同已过期，不可继续增加报价");
                }
                if (StringUtils.isEmpty(priceDto.getUnloadSiteId())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同报价卸货地不能为空");
                }
                if (StringUtils.isEmpty(priceDto.getGoodsType())) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同报价货物类型不能为空");
                }
                Boolean result = settlePriceService.createCustomerSettlePrice(priceDto);
                if (!result) {
                    return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "创建合同报价失败");
                }
            }
            return BaseResponse.successInstance("创建合同结算成功");
        }
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同报价不能为空");
    }

    /**
     * 删除结算信息
     *
     * @param priceId
     * @return
     */
    @ApiOperation("合同结算信息删除")
    @DeleteMapping("/customerContractSettlePrice/{priceId}")
    public BaseResponse<Map<String, Object>> createCustomerContractSettleRule(@PathVariable("priceId") Integer priceId) {
        return BaseResponse.service(settlePriceService.disableSettlePrice(priceId));
    }

    /**
     * 获取客户合同制定装卸货地实际里程
     *
     * @param customerContractId
     * @param loadSiteId
     * @param unloadSiteId
     * @return
     */
    @ApiOperation("获取客户合同指定装卸货地实际里程")
    @GetMapping("getMileageByParams")
    public BaseResponse<BigDecimal> getMileageByParams(@RequestParam("customerContractId") Integer customerContractId, @RequestParam("loadSiteId") Integer loadSiteId, @RequestParam("unloadSiteId") Integer unloadSiteId) {
        return BaseResponse.successInstance(settlePriceService.getMileageByParams(customerContractId, loadSiteId, unloadSiteId));
    }

    /**
     * 修改结算信息
     *
     * @param priceDto
     * @return
     */
    @ApiOperation("合同结算信息修改")
    @PutMapping("/customerContractSettlePrice")
    public BaseResponse<Map<String, Object>> customerContractSettlePrice(@RequestBody UpdateCustomerContractSettlePriceDto priceDto) {
        return BaseResponse.service(settlePriceService.updateSettlePrice(priceDto));
    }


    /**
     * 合同结算信息
     *
     * @param priceId
     * @return
     */
    @ApiOperation("修改结算单价时查询历史纪录列表")
    @PostMapping("/listCustomerContractSettlePriceHistory/{priceId}")
    public BaseResponse listCustomerContractSettlePriceHistory(@PathVariable("priceId") Integer priceId) {
        return BaseResponse.successInstance(settlePriceService.listCustomerContractSettlePriceHistory(priceId));
    }

    //--------------------------------------------------------合同结算配制-----------------------------------------------------------

    /**
     * 合同结算配制新增
     *
     * @param settleDto
     * @return
     */
    @ApiOperation("合同结算配制新增")
    @PostMapping("/customerContractSettleRule")
    public BaseResponse<String> createCustomerContractSettleRule(@RequestBody CreateContractSettleDto settleDto) {
        if (StringUtils.isEmpty(settleDto.getContractId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同id不能为空");
        }
        if (settleDto.getOilPriceDto() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同油价不能为空");
        }
        if (settleDto.getRuleDto() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "合同结算配制不能为空");
        }
        //油价
        CreateContractOilPriceDto oilPriceDto = settleDto.getOilPriceDto();
        oilPriceDto
                .setContractId(settleDto.getContractId())
                .setContractType(ContractType.CUSTOMER);
        //结算配置
        CreateCustomerSettleRuleDto ruleDto = settleDto.getRuleDto();
        ruleDto.setCustomerContractId(settleDto.getContractId());
        try {
            Boolean ruleResult = settleRuleService.createSettleRule(ruleDto, oilPriceDto);
            if (!ruleResult) {
                return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "创建合同结算配置失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), ex.getMessage());
        }
        return BaseResponse.successInstance("新增成功");
    }

}