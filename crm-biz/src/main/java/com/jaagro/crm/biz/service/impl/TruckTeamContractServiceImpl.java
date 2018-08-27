package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ContractStatus;
import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.driver.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.driver.TruckTeamContractReturnDto;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 创建车队合同
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto) {
        //创建合同对象
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto,truckTeamContract);
        TruckTeamContractReturnDto byId = truckTeamContractMapper.getById(dto.getId());
        if(byId.getContractNumber().equals(dto.getContractNumber())){
            throw new RuntimeException("合同编号不能相同");
        }
        truckTeamContract
                .setContractDate(new Date())
                .setContractStatus(ContractStatus.UNAUDITED);
        truckTeamContractMapper.insert(truckTeamContract);
        return ServiceResult.toResult("合同创建成功");
    }

    /**
     * 根据合同ID查询合同
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(truckTeamContractMapper.getById(id));
    }

    /**
     * 根据合同编号查询合同
     * @param contractNumber
     * @return
     */
    @Override
    public Map<String, Object> getByContractNumber(String contractNumber) {
        return ServiceResult.toResult(truckTeamContractMapper.getByContractNumber(contractNumber));
    }

    /**
     * 修改车队合同
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateTruckTeamContract(UpdateTruckTeamContractDto dto) {
        //创建车队合同对象
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto,truckTeamContract);
        TruckTeamContractReturnDto byId = truckTeamContractMapper.getById(dto.getId());
        if(byId.getContractNumber().equals(dto.getContractNumber())){
            throw new RuntimeException("合同编号不能相同");
        }
        if(truckTeamContract != null) {
            truckTeamContract
                    .setContractStatus(ContractStatus.UNAUDITED);
            truckTeamContractMapper.updateByPrimaryKeySelective(truckTeamContract);
        }
        return ServiceResult.toResult("修改车队合同成功");
    }

    /**
     * 创建车队合同表
     * @param dto
     * @param truckTeamId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamContracts(List<CreateTruckTeamContractDto> dto, Integer truckTeamId) {
        if(dto != null && dto.size() > 0){
            for(CreateTruckTeamContractDto ctt: dto) {
                //创建车队合同对象
                TruckTeamContract truckTeamContract = new TruckTeamContract();
                BeanUtils.copyProperties(ctt,truckTeamContract);
                truckTeamContract
                        .setContractStatus(ContractStatus.UNAUDITED)
                        .setTruckTeamId(truckTeamId);
                truckTeamContractMapper.insert(truckTeamContract);
            }
        }
        return ServiceResult.toResult("车队合同创建成功");
    }
}
