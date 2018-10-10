package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.truck.*;
import com.jaagro.crm.api.dto.response.truck.*;
import com.jaagro.crm.api.service.*;
import com.jaagro.crm.biz.entity.Truck;
import com.jaagro.crm.biz.entity.TruckQualification;
import com.jaagro.crm.biz.mapper.TruckMapperExt;
import com.jaagro.crm.biz.mapper.TruckQualificationMapperExt;
import com.jaagro.crm.biz.mapper.TruckTypeMapperExt;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.ArrayList;
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
    private TruckMapperExt truckMapper;
    @Autowired
    private DriverClientService driverClientService;
    @Autowired
    private TruckQualificationService truckQualificationService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TruckTypeMapperExt truckTypeMapper;
    @Autowired
    private TruckQualificationMapperExt truckQualificationMapper;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserClientService userClientService;

    private void changeUrl(List<ListTruckQualificationDto> driverQualificationList) {
        for (ListTruckQualificationDto dto : driverQualificationList
        ) {
            //替换资质证照地址
            String[] strArray = {dto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            dto.setCertificateImageUrl(urlList.get(0).toString());
        }
    }

    /**
     * 获取单条
     *
     * @param truckId
     * @return
     */
    @Override
    public Map<String, Object> getTruckById(Integer truckId) {
        Truck truck = this.truckMapper.selectByPrimaryKey(truckId);
        if (truck == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_EMPTY.getCode(), "车辆不存在");
        }
        GetTruckDto result = truckMapper.getTruckById(truckId);
        if (StringUtils.isEmpty(result)) {
            return ServiceResult.error(ResponseStatusCode.FORBIDDEN_ERROR.getCode(), truckId + ": 无效");
        }
        //替换车辆资质证地址
        if (result.getQualificationDtoList().size() > 0) {
            changeUrl(result.getQualificationDtoList());
        }
        //司机列表
        List<DriverReturnDto> drivers = driverClientService.listByTruckId(truckId);
        if (drivers.size() > 0) {
            //填充司机资质证照列表
            for (DriverReturnDto driverReturnDto : drivers) {
                List<ListTruckQualificationDto> driverQualificationList = this.truckQualificationMapper.listByDriverId(driverReturnDto.getId());
                if (driverQualificationList.size() > 0) {
                    changeUrl(driverQualificationList);
                }
                driverReturnDto.setDriverQualificationList(driverQualificationList);
            }
        }
        result
                .setDrivers(drivers)
                .setTruckTypeId(this.truckTypeMapper.getById(truck.getTruckTypeId()));
        return ServiceResult.toResult(result);
    }

    /**
     * 获取单条车辆 返回对象
     *
     * @param truckId
     * @return
     */
    @Override
    public GetTruckDto getTruckByIdReturnObject(Integer truckId) {
        Truck truck = this.truckMapper.selectByPrimaryKey(truckId);
        GetTruckDto result = truckMapper.getTruckById(truckId);
        List<DriverReturnDto> drivers = driverClientService.listByTruckId(truckId);
        result
                .setDrivers(drivers)
                .setTruckTypeId(this.truckTypeMapper.getById(truck.getTruckTypeId()));
        return result;
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
                if (driverId == 0) {
                    throw new NullPointerException("新增司机失败，司机id为空");
                }
                //司机资质列表
                if (driverDto.getDriverQualifications() != null && driverDto.getDriverQualifications().size() > 0) {
                    for (UpdateTruckQualificationDto qualificationDto : driverDto.getDriverQualifications()) {
                        TruckQualification driverQualification = new TruckQualification();
                        BeanUtils.copyProperties(qualificationDto, driverQualification);
                        driverQualification
                                .setTruckTeamId(truck.getTruckTeamId())
                                .setTruckId(truck.getId())
                                .setDriverId(driverId)
                                .setCreateUserId(currentUserService.getCurrentUser().getId());
                        truckQualificationMapper.insertSelective(driverQualification);
                    }
                }
            }
        }
        return ServiceResult.toResult(truck.getTruckTeamId());
    }

    /**
     * 修改车辆
     *
     * @param truckDto
     * @return
     */
    @Override
    public Map<String, Object> updateTruck(CreateTruckDto truckDto) {
        if (truckMapper.selectByPrimaryKey(truckDto.getId()) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), truckDto.getId() + " ：id不正确");
        }
        //车辆
        Truck truck = new Truck();
        BeanUtils.copyProperties(truckDto, truck);
        truck
                .setModifyUserId(currentUserService.getCurrentUser().getId())
                .setModifyTime(new Date());
        truckMapper.updateByPrimaryKeySelective(truck);
        //司机
        if (truckDto.getDriver() != null && truckDto.getDriver().size() > 0) {
            for (CreateDriverDto createDriverDto : truckDto.getDriver()
            ) {
                Integer driverId = 0;
                //新增司机
                if (createDriverDto.getId() == null) {
                    CreateDriverDto driverDto = new CreateDriverDto();
                    BeanUtils.copyProperties(createDriverDto, driverDto);
                    driverDto
                            .setTruckId(truck.getId())
                            .setTruckTeamId(truck.getTruckTeamId());
                    driverId = this.driverClientService.createDriverReturnId(driverDto);
                    if (driverId < 1) {
                        throw new RuntimeException("司机创建失败");
                    }
                } else {
                    //修改
                    UpdateDriverDto updateDriverDto = new UpdateDriverDto();
                    BeanUtils.copyProperties(createDriverDto, updateDriverDto);
                    this.driverClientService.updateDriverFeign(updateDriverDto);
                    driverId = updateDriverDto.getId();
                }
                //司机资质
                if (createDriverDto.getDriverQualifications() != null && createDriverDto.getDriverQualifications().size() > 0) {
                    for (UpdateTruckQualificationDto truckQualificationDto : createDriverDto.getDriverQualifications()
                    ) {
                        // id为null - 新增
                        if (truckQualificationDto.getId() == null) {
                            CreateListTruckQualificationDto createListTruckQualificationDto = new CreateListTruckQualificationDto();
                            createListTruckQualificationDto
                                    .setTruckId(truck.getId())
                                    .setTruckTeamId(truck.getTruckTeamId())
                                    .setDriverId(driverId)
                                    .setQualification(createDriverDto.getDriverQualifications());
                            this.truckQualificationService.createTruckQualification(createListTruckQualificationDto);
                        } else {
                            //修改
                            this.truckQualificationService.updateQualification(createDriverDto.getDriverQualifications());
                        }
                    }
                }
            }
        }
        //车辆资质
        if (truckDto.getTruckQualifications() != null && truckDto.getTruckQualifications().size() > 0) {
            for (UpdateTruckQualificationDto truckQualificationDto : truckDto.getTruckQualifications()
            ) {
                // id为null - 新增
                if (truckQualificationDto.getId() == null) {
                    CreateListTruckQualificationDto createListTruckQualificationDto = new CreateListTruckQualificationDto();
                    BeanUtils.copyProperties(truckQualificationDto, createListTruckQualificationDto);
                    this.truckQualificationService.createTruckQualification(createListTruckQualificationDto);
                } else {
                    //修改
                    this.truckQualificationService.updateQualification(truckDto.getTruckQualifications());
                }
            }
        }
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
        return ServiceResult.toResult(new PageInfo<>(truckMapper.listTruckByTeamId(criteria.getTruckTeamId(), criteria.getTruckNumber())));
    }

    @Override
    public Map<String, Object> listTruckByCriteria(ListTruckCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        return ServiceResult.toResult(new PageInfo<>(truckMapper.listTruckByCriteria(criteria)));
    }

    @Override
    public List<ListTruckTypeDto> listTruckType(String productName) {
        if (productName.equals(ProductType.SOW) || productName.equals(ProductType.BOAR) || productName.equals(ProductType.LIVE_PIG)) {
            productName = ProductType.BOAR.toString();
            return truckTypeMapper.listAll(productName);
        }
        return truckTypeMapper.listAll(productName);
    }

    @Override
    public List<ListTruckTypeDto> listTruckType() {
        return truckTypeMapper.listAll(null);
    }

    /**
     * 获取单条车辆类型
     *
     * @param id
     * @return
     */
    @Override
    public ListTruckTypeDto getTruckTypeById(Integer id) {
        return truckTypeMapper.getById(id);
    }

    /**
     * @param criteriaDto
     * @return
     * @Author gavin
     */
    @Override
    public Map<String, Object> listTrucksWithDrivers(ListTruckCriteriaDto criteriaDto) {
        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        List<ListTruckDto> truckList = truckMapper.listTruckByCriteria(criteriaDto);
        if (truckList == null || truckList.size() == 0) {
            return ServiceResult.error(ResponseStatusCode.OPERATION_SUCCESS.getCode(), "查无数据");
        }
        if (!CollectionUtils.isEmpty(truckList)) {
            for (ListTruckDto listTruckDto : truckList) {
                List<DriverReturnDto> drivers = driverClientService.listByTruckId(listTruckDto.getTruckId());
                listTruckDto.setDrivers(drivers);
            }
        }
        return ServiceResult.toResult(new PageInfo<>(truckList));
    }

    /**
     * 通过token获取truck
     *
     * @return
     * @author tony
     */
    @Override
    public GetTruckDto getTruckByToken() {
        String token = request.getHeader("token");
        UserInfo userInfo = userClientService.getUserByToken(token);
        DriverReturnDto driver;
        GetTruckDto truck = null;
        log.info("当前user: " + userInfo.toString());
        if (null != userInfo) {
            driver = driverClientService.getDriverReturnObject(userInfo.getId());
            if (null == driver) {
                throw new NullPointerException("当前司机不存在");
            }
            log.info("当前司机: " + driver.toString());
            truck = truckMapper.getTruckById(driver.getTruckId());
            log.info("当前车辆: " + truck.toString());
        }
        return truck;
    }
}