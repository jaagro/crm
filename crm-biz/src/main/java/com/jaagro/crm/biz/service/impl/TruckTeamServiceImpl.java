package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.TeamStatus;
import com.jaagro.crm.api.dto.request.driver.*;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.*;
import com.jaagro.crm.biz.mapper.*;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class TruckTeamServiceImpl implements TruckTeamService {

    private static final Logger log = LoggerFactory.getLogger(TruckTeamServiceImpl.class);

    @Autowired
    private TruckMapper truckMapper;

    @Autowired
    private TruckTeamMapper truckTeamMapper;

    @Autowired
    private TruckTeamBankcardMapper truckTeamBankcardMapper;

    @Autowired
    private TruckTeamContractMapper truckTeamContractMapper;

    @Autowired
    private TruckTeamContactsMapper truckTeamContactsMapper;

    @Autowired
    private TruckQualificationMapper truckQualificationMapper;

    @Autowired
    private TruckService truckService;
    @Autowired
    private TruckTeamBankcardService truckTeamBankcardService;
    @Autowired
    private TruckTeamContractService truckTeamContractService;
    @Autowired
    private TruckTeamContactsService truckTeamContactsService;
    @Autowired
    private TruckQualificationService truckQualificationService;

    /**
     * 创建所有车队对象
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeams(CreateTruckTeamDto dto) {
        //创建车队对象
        TruckTeam truckTeam = new TruckTeam();
        BeanUtils.copyProperties(dto, truckTeam);
        truckTeam
                .setTeamStatus(TeamStatus.UNAUDITED)
                .setCreateUserId(1);
        if(dto.getLegalName() == null){
            throw new RuntimeException("法人姓名不能为空");
        }
        if(dto.getTeamType() == null){
            throw new RuntimeException("车队类型不能为空");
        }
        truckTeamMapper.insert(truckTeam);
        //创建车辆对象
        if(dto.getTruck() != null && dto.getTruck().size() > 0){
            truckService.createTruck(dto.getTruck(),truckTeam.getId());
        }
        //创建车队银行卡号对象
        if(dto.getTruckTeamBankcard() != null && dto.getTruckTeamBankcard().size() > 0){
            truckTeamBankcardService.createTruckTeamBankcard(dto.getTruckTeamBankcard(),truckTeam.getId());
        }
        //创建车队资质对象
        if(dto.getTruckQualification() != null && dto.getTruckQualification().size() > 0){
            truckQualificationService.createTruckQualification(dto.getTruckQualification(),truckTeam.getId());
        }
        //创建车队联系人对象
        if(dto.getTruckTeamContract() != null && dto.getTruckTeamContract().size() > 0){
            truckTeamContractService.createTruckTeamContracts(dto.getTruckTeamContract(),truckTeam.getId());
        }
        //创建车队合同对象
        if(dto.getTruckTeamContacts() != null && dto.getTruckTeamContacts().size() > 0){
            truckTeamContactsService.createTruckTeamContacts(dto.getTruckTeamContacts(),truckTeam.getId());
        }
        return ServiceResult.toResult("创建车队成功");
    }
    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTruckTeamById(Integer id) {
        return ServiceResult.toResult(truckTeamMapper.getTruckTeamById(id));
    }

    /**
     * 创建车队对象
     * @param dto
     * @return
     */
    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeam(CreateTruckTeamDto dto) {
        //创建车队对象
        TruckTeam truckTeam = new TruckTeam();
        BeanUtils.copyProperties(dto,truckTeam);
        truckTeam
                .setTeamStatus(TeamStatus.UNAUDITED)
                .setCreateUserId(1);
        truckTeamMapper.insert(truckTeam);
        //创建车队对象
        if(dto.getTruck() != null && dto.getTruck().size() > 0){
            for(CreateTruckDto ct: dto.getTruck()) {
                Truck truck = new Truck();
                BeanUtils.copyProperties(ct,truck);
                truck.setTruckTeamId(dto.getId());
                truckMapper.insert(truck);
            }
        }
        //创建车队资质对象
        if(dto.getTruckQualification() != null && dto.getTruckQualification().size() > 0){
            for(CreateTruckQualificationDto ctq: dto.getTruckQualification()){
                TruckQualification truckQualification = new TruckQualification();
                BeanUtils.copyProperties(ctq,truckQualification);
                truckQualification.setTruckTeamId(dto.getId());
                truckQualificationMapper.insert(truckQualification);
            }
        }
        //创建车队联系人对象
        if(dto.getTruckTeamContacts() != null && dto.getTruckTeamContacts().size() > 0){
            for(CreateTruckTeamContactsDto ctt: dto.getTruckTeamContacts()){
                TruckTeamContacts truckTeamContacts = new TruckTeamContacts();
                BeanUtils.copyProperties(ctt,truckTeamContacts);
                truckTeamContacts.setTruckTeamId(dto.getId());
                truckTeamContactsMapper.insert(truckTeamContacts);
            }
        }
        //创建车队合同对象
        if(dto.getTruckTeamContract() != null && dto.getTruckTeamContract().size() > 0){
            for(CreateTruckTeamContractDto cttc: dto.getTruckTeamContract()){
                TruckTeamContract truckTeamContract = new TruckTeamContract();
                BeanUtils.copyProperties(cttc,truckTeamContract);
                truckTeamContract.setTruckTeamId(dto.getId());
                truckTeamContractMapper.insert(truckTeamContract);
            }
        }
        //创建车队资质对象
        if(dto.getTruckTeamBankcard() != null && dto.getTruckTeamBankcard().size() > 0){
            for(CreateTruckTeamBankcardDto ctq: dto.getTruckTeamBankcard()){
                TruckTeamBankcard truckTeamBankcard = new TruckTeamBankcard();
                BeanUtils.copyProperties(ctq,truckTeamBankcard);
                truckTeamBankcard.setTruckTeamId(dto.getId());
                truckTeamBankcardMapper.insert(truckTeamBankcard);
            }
        }
        return ServiceResult.toResult("创建车队成功");
    }
}
