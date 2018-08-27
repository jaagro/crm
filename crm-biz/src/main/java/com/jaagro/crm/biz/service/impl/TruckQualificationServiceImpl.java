package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.driver.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.driver.CreateTruckQualificationDto;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.biz.entity.TruckQualification;
import com.jaagro.crm.biz.mapper.TruckQualificationMapper;
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
public class TruckQualificationServiceImpl implements TruckQualificationService {

    private static final Logger log = LoggerFactory.getLogger(TruckQualificationServiceImpl.class);

    @Autowired
    private TruckQualificationMapper truckQualificationMapper;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 创建车队资质
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckQualification(CreateListTruckQualificationDto dto) {
        for(CreateTruckQualificationDto qualification : dto.getQualification()){
            TruckQualification truckQualification = new TruckQualification();
            BeanUtils.copyProperties(qualification, truckQualification);
            truckQualification
                    .setTruckTeamId(dto.getTruckTeamId())
                    .setTruckId(dto.getTruckId())
                    .setDriverId(dto.getDriverId())
                    .setCreateUserId(currentUserService.getCurrentUser().getId());
            truckQualificationMapper.insertSelective(truckQualification);
        }
        return ServiceResult.toResult("资质保存成功");
    }
}
