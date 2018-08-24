package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.driver.CreateDriverDto;
import com.jaagro.crm.api.dto.request.driver.CreateTruckDto;
import com.jaagro.crm.api.dto.request.driver.CreateTruckQualificationDto;
import com.jaagro.crm.api.service.DriverClientService;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.entity.Truck;
import com.jaagro.crm.biz.entity.TruckQualification;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.crm.biz.mapper.TruckQualificationMapper;
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
public class TruckServiceImpl implements TruckService {

    private static final Logger log = LoggerFactory.getLogger(TruckServiceImpl.class);

    @Autowired
    private TruckMapper truckMapper;
    @Autowired
    private TruckQualificationMapper truckQualificationMapper;
    @Autowired
    private DriverClientService driverClientService;
    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTruckById(Integer id) {
        return ServiceResult.toResult(truckMapper.getTruckById(id));
    }

    /**
     * 关联创建车辆
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTrucks(CreateTruckDto dto) {
        //创建车辆对象
        Truck truck = new Truck();
        BeanUtils.copyProperties(dto,truck);
        truck
                .setTruckStatus(AuditStatus.UNCHECKED)
                .setCreateUserId(1);
        truckMapper.insert(truck);
        //创建车辆资质对象
        if(dto.getTruckQualification() != null && dto.getTruckQualification().size() > 0){
            for(CreateTruckQualificationDto ctq: dto.getTruckQualification()) {
                //创建车辆资质对象
                TruckQualification truckQualification = new TruckQualification();
                BeanUtils.copyProperties(ctq,truckQualification);
                truckQualification.setTruckId(dto.getId());
                truckQualificationMapper.insert(truckQualification);
            }
        }
        //创建车辆司机对象
        if(dto.getDriver() != null && dto.getDriver().size() > 0){
            for(CreateDriverDto cd: dto.getDriver()){
                //创建司机对象
                driverClientService.createDriver(cd);
            }
        }
        return ServiceResult.toResult("创建车辆资质成功");
    }

    /**
     * 创建车辆对象
     * @param dto
     * @param truckTeamId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruck(List<CreateTruckDto> dto, Integer truckTeamId) {
        if(dto != null && dto.size() > 0){
            for(CreateTruckDto ctd: dto) {
                //创建车辆对象
                Truck truck = new Truck();
                BeanUtils.copyProperties(ctd,truck);
                truck
                        .setTruckStatus(AuditStatus.UNCHECKED)
                        .setTruckTeamId(truckTeamId)
                        .setCreateUserId(1);
                truckMapper.insert(truck);
            }
        }
        return ServiceResult.toResult("车辆对象创建成功");
    }

}
