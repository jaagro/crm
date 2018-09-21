package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ContractStatus;
import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.truck.*;
import com.jaagro.crm.api.dto.response.contract.ReturnCheckContractQualificationDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamContractReturnDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.OssSignUrlClientService;
import com.jaagro.crm.api.service.TruckTeamContractPriceService;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.TruckTeam;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.ContractQualificationMapper;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class TruckTeamContractServiceImpl implements TruckTeamContractService {

    private static final Logger log = LoggerFactory.getLogger(TruckTeamContractServiceImpl.class);

    @Autowired
    private TruckTeamContractMapper truckTeamContractMapper;
    @Autowired
    private TruckTeamContractPriceService contractPriceService;
    @Autowired
    private TruckTeamMapper truckTeamMapper;
    @Autowired
    private ContractQualificationMapper contractQualificationMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private ContractQualificationService contractQualificationService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;

    /**
     * 创建车队合同
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto) {
        if (this.truckTeamMapper.selectByPrimaryKey(dto.getTruckTeamId()) == null) {
            throw new NullPointerException("车队不存在");
        }
        //创建合同
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto, truckTeamContract);
        truckTeamContract
                .setCreateUserId(this.userService.getCurrentUser().getId());
        truckTeamContractMapper.insertSelective(truckTeamContract);

        //创建合同报价及阶梯报价
        if (dto.getContractPriceDtoList() != null && dto.getContractPriceDtoList().size() > 0) {
            for (CreateTruckTeamContractPriceDto truckTeamContractPriceDto : dto.getContractPriceDtoList()
            ) {
                truckTeamContractPriceDto.setTruckTeamContractId(truckTeamContract.getId());
                this.contractPriceService.createPrice(truckTeamContractPriceDto);
            }
        }
        //创建资质证
        if (dto.getQualificationDtoList() != null && dto.getQualificationDtoList().size() > 0) {
            for (CreateContractQualificationDto qualificationDto : dto.getQualificationDtoList()) {
                qualificationDto.setRelevanceId(truckTeamContract.getId());
                this.contractQualificationService.createQuation(qualificationDto);
            }
        }
        return ServiceResult.toResult("合同创建成功");
    }

    /**
     * 根据合同ID查询合同
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer id) {
        TruckTeamContractReturnDto contractReturnDto = truckTeamContractMapper.getById(id);
        if (contractReturnDto.getQualificationDtoList().size() > 0) {
            for (ReturnCheckContractQualificationDto qualificationDto : contractReturnDto.getQualificationDtoList()
            ) {
                //替换资质证照地址
                String[] strArray = {qualificationDto.getCertificateImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                qualificationDto.setCertificateImageUrl(urlList.get(0).toString());
            }
        }
        return ServiceResult.toResult(contractReturnDto);
    }

    /**
     * 根据合同编号查询合同
     *
     * @param contractNumber
     * @return
     */
    @Override
    public Map<String, Object> getByContractNumber(String contractNumber) {
        return ServiceResult.toResult(truckTeamContractMapper.getByContractNumber(contractNumber));
    }

    /**
     * 修改车队合同
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateTruckTeamContract(UpdateTruckTeamContractDto dto) {
        //创建车队合同对象
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto, truckTeamContract);
        TruckTeamContractReturnDto byId = truckTeamContractMapper.getById(dto.getId());
        if (byId.getContractNumber().equals(dto.getContractNumber())) {
            throw new RuntimeException("合同编号不能相同");
        }
        if (truckTeamContract != null) {
            truckTeamContract
                    .setModifyTime(new Date())
                    .setModifyUserId(this.userService.getCurrentUser().getId());
            truckTeamContractMapper.updateByPrimaryKeySelective(truckTeamContract);
        }
        //修改合同报价
        if (dto.getContractPriceDtoList() != null && dto.getContractPriceDtoList().size() > 0) {
            this.contractPriceService.deleteByContractId(dto.getId());
            for (UpdateTruckTeamContractPriceDto priceDto : dto.getContractPriceDtoList()
            ) {
                priceDto.setTruckTeamContractId(truckTeamContract.getId());
                CreateTruckTeamContractPriceDto contractPriceDto = new CreateTruckTeamContractPriceDto();
                BeanUtils.copyProperties(priceDto, contractPriceDto);
                this.contractPriceService.createPrice(contractPriceDto);
            }
        }
        return ServiceResult.toResult("修改车队合同成功");
    }

    /**
     * 创建车队合同表
     *
     * @param dto
     * @param truckTeamId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamContracts(List<CreateTruckTeamContractDto> dto, Integer truckTeamId) {
        if (dto != null && dto.size() > 0) {
            for (CreateTruckTeamContractDto ctt : dto) {
                //创建车队合同对象
                TruckTeamContract truckTeamContract = new TruckTeamContract();
                BeanUtils.copyProperties(ctt, truckTeamContract);
                truckTeamContract
                        .setContractStatus(ContractStatus.UNAUDITED)
                        .setTruckTeamId(truckTeamId);
                truckTeamContractMapper.insert(truckTeamContract);
            }
        }
        return ServiceResult.toResult("车队合同创建成功");
    }

    @Override
    public Map<String, Object> listByCriteria(ListTruckTeamContractCriteriaDto criteriaDto) {
        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        List<ListTruckTeamContractDto> returnDtoList = this.truckTeamContractMapper.listByCriteria(criteriaDto);
        if (returnDtoList.size() > 0) {
            for (ListTruckTeamContractDto contractDto : returnDtoList
            ) {
                TruckTeam truckTeam = this.truckTeamMapper.selectByPrimaryKey(contractDto.getTruckTeamId());
                if (truckTeam != null) {
                    contractDto.setTruckTeamName(truckTeam.getTeamName());
                }
            }
        }
        return ServiceResult.toResult(new PageInfo<>(returnDtoList));
    }

    @Override
    public Map<String, Object> disableContract(Integer id) {
        if (truckTeamContractMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询不到合同ID");
        }
        TruckTeamContractReturnDto contractReturnDto = truckTeamContractMapper.getById(id);
        if (contractReturnDto != null) {
            TruckTeamContract contract = new TruckTeamContract();
            BeanUtils.copyProperties(contractReturnDto, contract);
            contract
                    .setModifyTime(new Date())
                    .setModifyUserId(this.userService.getCurrentUser().getId())
                    .setContractStatus(AuditStatus.STOP_COOPERATION);
            this.truckTeamContractMapper.updateByPrimaryKeySelective(contract);
            if (contractReturnDto.getContractPriceDtoList().size() > 0) {
                this.contractPriceService.disableByContractId(contract.getId());
            }

        }
        return ServiceResult.toResult("删除成功");
    }
}
