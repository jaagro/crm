package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.driver.CreateTruckDto;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.entity.Truck;
import com.jaagro.crm.biz.mapper.TruckMapper;
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

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(truckMapper.getById(id));
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
