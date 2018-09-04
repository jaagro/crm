package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.truck.CreateDriverDto;
import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.DriverReturnDto;
import com.jaagro.crm.api.dto.response.truck.GetTruckDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckDto;
import com.jaagro.crm.api.service.DriverClientService;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.entity.Truck;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    private DriverClientService driverClientService;
    @Autowired
    private TruckQualificationService truckQualificationService;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTruckById(Integer id) {
        GetTruckDto result = truckMapper.getTruckById(id);
        if (StringUtils.isEmpty(result)) {
            return ServiceResult.error(ResponseStatusCode.FORBIDDEN_ERROR.getCode(), id + ": 无效");
        }
        List<DriverReturnDto> drivers = driverClientService.listByTruckId(id);
        result.setDrivers(drivers);
        return ServiceResult.toResult(result);
    }

    /**
     * 创建车辆对象
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruck(CreateTruckDto truckDto) {
        Truck truck = new Truck();
        BeanUtils.copyProperties(truckDto, truck);
        truck.setCreateUserId(this.currentUserService.getCurrentUser().getId());
        truckMapper.insertSelective(truck);

        //车辆资质列表
        if (truckDto.getTruckQualifications() != null && truckDto.getTruckQualifications().size() > 0) {
            CreateListTruckQualificationDto listTruckQualification = new CreateListTruckQualificationDto();
            listTruckQualification
                    .setTruckTeamId(truck.getTruckTeamId())
                    .setTruckId(truck.getId());
            listTruckQualification.setQualification(truckDto.getTruckQualifications());
            truckQualificationService.createTruckQualification(listTruckQualification);
        }

        //司机列表
        if (truckDto.getDriver() != null && truckDto.getDriver().size() > 0) {
            for (CreateDriverDto driverDto : truckDto.getDriver()) {
                Integer driverId = 0;
                driverDto
                        .setTruckId(truck.getId())
                        .setTruckTeamId(truck.getTruckTeamId());
                try {
                    driverId = driverClientService.createDriverReturnId(driverDto);
                } catch (RuntimeException e) {
                    log.error("司机新建失败：" + e.getMessage());
                    throw e;
                }

                //司机资质列表
                if (driverDto.getDriverQualifications() != null && driverDto.getDriverQualifications().size() > 0) {
                    CreateListTruckQualificationDto listTruckQualification = new CreateListTruckQualificationDto();
                    listTruckQualification
                            .setTruckTeamId(truck.getTruckTeamId())
                            .setTruckId(truck.getId())
                            .setDriverId(driverId);
                    listTruckQualification.setQualification(driverDto.getDriverQualifications());
                    truckQualificationService.createTruckQualification(listTruckQualification);
                }
            }
        }
        return ServiceResult.toResult(truck.getId());
    }

    /**
     * 修改车辆
     *
     * @param truckDto
     * @return
     */
    @Override
    public Map<String, Object> updateTruck(CreateTruckDto truckDto) {
        if (truckMapper.selectByPrimaryKey(truckDto.getTruckTeamId()) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), truckDto.getId() + " ：id不正确");
        }
        Truck truck = new Truck();
        BeanUtils.copyProperties(truckDto, truck);
        truck
                .setModifyUserId(currentUserService.getCurrentUser().getId())
                .setModifyTime(new Date());
        truckMapper.updateByPrimaryKeySelective(truck);
        return ServiceResult.toResult(truckMapper.getTruckById(truckDto.getId()));
    }

    /**
     * 删除车辆
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteTruck(Integer id) {
        if (truckMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), id + " ：id不正确");
        }
        //删除车辆所属的司机
        driverClientService.deleteDriverByTruckId(id);
        //删除车辆
        truckMapper.deleteTruckLogic(AuditStatus.STOP_COOPERATION, id);
        return ServiceResult.toResult("删除成功");
    }

    @Override
    public Map<String, Object> listTruck(ListTruckCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        List<ListTruckDto> result = truckMapper.listTruckByCriteria(criteria);
        if (result == null || result.size() == 0) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查无数据");
        }
        return ServiceResult.toResult(new PageInfo<>(result));
    }
}
