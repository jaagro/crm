package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.ContractStatus;
import com.jaagro.crm.api.dto.request.contract.CreateContractQualificationDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractPriceDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckTeamContractCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamContractReturnDto;
import com.jaagro.crm.api.service.ContractQualificationService;
import com.jaagro.crm.api.service.TruckTeamContractPriceService;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.ContractQualificationMapper;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ServiceResult.toResult(truckTeamContractMapper.getById(id));
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
                    .setContractStatus(ContractStatus.UNAUDITED);
            truckTeamContractMapper.updateByPrimaryKeySelective(truckTeamContract);
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
        return ServiceResult.toResult(new PageInfo<>(returnDtoList));
    }
}
