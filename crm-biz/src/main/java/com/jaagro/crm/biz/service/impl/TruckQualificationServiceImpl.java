package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ReturnTruckQualificationDto;
import com.jaagro.crm.api.service.OssSignUrlClientService;
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

import java.net.URL;
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
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;

    /**
     * 创建车队资质
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckQualification(CreateListTruckQualificationDto dto) {
        for (CreateTruckQualificationDto qualification : dto.getQualification()) {
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

    /**
     * 分页查询待审核的运力资质
     *
     * @return
     */
    @Override
    public Map<String, Object> listQualification(ListTruckQualificationCriteriaDto criteriaDto) {
        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        return ServiceResult.toResult(new PageInfo<>(this.truckQualificationMapper.listByCriteria(criteriaDto)));
    }

    @Override
    public Map<String, Object> listQualificationByTruckIds(ListTruckQualificationCriteriaDto criteriaDto) {
        List<ReturnTruckQualificationDto> returnTruckQualificationDtoList = this.truckQualificationMapper.listByIds(criteriaDto);
        if (returnTruckQualificationDtoList.size() > 0) {
            for (ReturnTruckQualificationDto dto : returnTruckQualificationDtoList
            ) {
                //替换资质证照地址
                String[] strArray = {dto.getCertificateImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                dto.setCertificateImageUrl(urlList.get(0).toString());
            }
        }
        return ServiceResult.toResult(returnTruckQualificationDtoList);
    }

}
