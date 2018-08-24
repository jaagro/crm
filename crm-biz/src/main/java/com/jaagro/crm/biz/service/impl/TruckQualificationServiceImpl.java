package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.CertificateStatus;
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

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class TruckQualificationServiceImpl implements TruckQualificationService {

    private static final Logger log = LoggerFactory.getLogger(TruckQualificationServiceImpl.class);

    @Autowired
    private TruckQualificationMapper truckQualificationMapper;

    /**
     * 创建车队资质
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckQualification(List<CreateTruckQualificationDto> dto, Integer truckTeamId) {
        if (dto != null && dto.size() > 0) {
            for(CreateTruckQualificationDto ctq: dto) {
                //创建车队资质对象
                TruckQualification truckQualification = new TruckQualification();
                BeanUtils.copyProperties(ctq,truckQualification);
                truckQualification
                        .setCertificateStatus(CertificateStatus.UNCHECKED)
                        .setEnabled(false)
                        .setCreateUserId(1)
                        .setTruckTeamId(truckTeamId);
                truckQualificationMapper.insert(truckQualification);
            }
        }
        return ServiceResult.toResult("创建车队资质成功");
    }

    @Override
    public Map<String, Object> createTruckQualification(CreateTruckQualificationDto dto) {
        return null;
    }
}
