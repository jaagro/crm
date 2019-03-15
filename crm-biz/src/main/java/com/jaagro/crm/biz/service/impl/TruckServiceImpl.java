package com.jaagro.crm.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.constant.ProductType;
import com.jaagro.crm.api.dto.request.truck.*;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.crm.api.dto.response.truck.*;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.entity.Truck;
import com.jaagro.crm.biz.entity.TruckQualification;
import com.jaagro.crm.biz.mapper.TruckMapperExt;
import com.jaagro.crm.biz.mapper.TruckQualificationMapperExt;
import com.jaagro.crm.biz.mapper.TruckTypeMapperExt;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.crm.biz.service.OssSignUrlClientService;
import com.jaagro.crm.biz.service.AuthClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.*;

/**
 * @author liqiangping
 */
@Service
@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "truck")
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
    private AuthClientService authClientService;
    @Autowired
    private DriverClientService deptClientService;

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
    @CacheEvict(cacheNames = "truck", allEntries = true)
    @Override
    public Map<String, Object> getTruckById(Integer truckId) {
        Truck truck = truckMapper.selectByPrimaryKey(truckId);
        GetTruckDto result = truckMapper.getTruckById(truckId);
        if (StringUtils.isEmpty(result)) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_EMPTY.getCode(), "车辆不存在");
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
        if (null == result) {
            return null;
        }
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
    @CacheEvict(cacheNames = "truck", allEntries = true)
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
                driverDto
                        .setTruckId(truck.getId())
                        .setTruckTeamId(truck.getTruckTeamId());
                String driverId = driverClientService.createDriverReturnId(driverDto);
                Integer driverIdInt = null;
                if (NumberUtils.isNumber(driverId)) {
                    driverIdInt = Integer.valueOf(driverId);
                } else {
                    BaseResponse result = JSON.parseObject(driverId, BaseResponse.class);
                    throw new RuntimeException(result.getStatusMsg());
                }
                //司机资质列表
                if (driverDto.getDriverQualifications() != null && driverDto.getDriverQualifications().size() > 0) {
                    for (UpdateTruckQualificationDto qualificationDto : driverDto.getDriverQualifications()) {
                        TruckQualification driverQualification = new TruckQualification();
                        BeanUtils.copyProperties(qualificationDto, driverQualification);
                        driverQualification
                                .setTruckTeamId(truck.getTruckTeamId())
                                .setTruckId(truck.getId())
                                .setDriverId(driverIdInt)
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
    @CacheEvict(cacheNames = "truck", allEntries = true)
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
                Integer driverIdInt = null;
                //新增司机
                if (createDriverDto.getId() == null) {
                    CreateDriverDto driverDto = new CreateDriverDto();
                    BeanUtils.copyProperties(createDriverDto, driverDto);
                    driverDto
                            .setTruckId(truck.getId())
                            .setTruckTeamId(truck.getTruckTeamId());
                    String driverId = driverClientService.createDriverReturnId(driverDto);
                    if (NumberUtils.isNumber(driverId)) {
                        driverIdInt = Integer.valueOf(driverId);
                    } else {
                        BaseResponse result = JSON.parseObject(driverId, BaseResponse.class);
                        throw new RuntimeException(result.getStatusMsg());
                    }
                } else {
                    //修改
                    UpdateDriverDto updateDriverDto = new UpdateDriverDto();
                    BeanUtils.copyProperties(createDriverDto, updateDriverDto);
                    try {
                        this.driverClientService.updateDriverFeign(updateDriverDto);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex.getMessage());
                    }
                    driverIdInt = updateDriverDto.getId();
                }
                //司机资质
                if (createDriverDto.getDriverQualifications() != null && createDriverDto.getDriverQualifications().size() > 0) {
                    for (UpdateTruckQualificationDto truckQualificationDto : createDriverDto.getDriverQualifications()
                    ) {
                        // id为null - 新增
                        if (truckQualificationDto.getId() == null) {
                            CreateListTruckQualificationDto createListTruckQualificationDto = new CreateListTruckQualificationDto();
                            List<UpdateTruckQualificationDto> updateTruckQualificationDtos = new ArrayList<>();
                            updateTruckQualificationDtos.add(truckQualificationDto);
                            createListTruckQualificationDto
                                    .setTruckId(truck.getId())
                                    .setTruckTeamId(truck.getTruckTeamId())
                                    .setDriverId(driverIdInt)
                                    .setQualification(updateTruckQualificationDtos);
                            this.truckQualificationService.createTruckQualification(createListTruckQualificationDto);
                        } else {
                            //修改
                            this.truckQualificationService.updateQualificationCertific(truckQualificationDto);
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
                    this.truckQualificationService.updateQualificationCertific(truckQualificationDto);
                }
            }
        }
        return ServiceResult.toResult(this.getTruckById(truckDto.getId()));
    }

    /**
     * 删除车辆
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "truck", allEntries = true)
    public Map<String, Object> deleteTruck(Integer id) {
        if (truckMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), id + " ：id不正确");
        }
        //删除车辆
        truckMapper.deleteTruckLogic(AuditStatus.STOP_COOPERATION, id);
        //删除车辆所属的司机
        driverClientService.deleteDriverByTruckId(id);
        return ServiceResult.toResult("删除成功");
    }

    @Override
    @Cacheable
    public Map<String, Object> listTruck(ListTruckCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        return ServiceResult.toResult(new PageInfo<>(truckMapper.listTruckByTeamId(criteria.getTruckTeamId(), criteria.getTruckNumber())));
    }

    @Override
    @Cacheable
    public Map<String, Object> listTruckByCriteria(ListTruckCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        return ServiceResult.toResult(new PageInfo<>(truckMapper.listTruckByCriteria(criteria)));
    }

    @Override
    public List<ListTruckTypeDto> listTruckType(String productName) {
        List<ListTruckTypeDto> resultList = new ArrayList<>();
        if (productName.equals(ProductType.SOW.toString()) || productName.equals(ProductType.BOAR.toString()) || productName.equals(ProductType.LIVE_PIG.toString())) {
            productName = ProductType.BOAR.toString();
            resultList = truckTypeMapper.listAll(productName);
        } else {
            resultList = truckTypeMapper.listAll(productName);
        }

        if (!CollectionUtils.isEmpty(resultList)) {
            List<ListTruckDto> truckList = truckMapper.listTruckForAssignWaybill();
            for (ListTruckTypeDto truckTypeDto : resultList) {
                Long amount = truckList.stream().filter(c -> c.getTruckTypeId().equals(truckTypeDto.getId())).count();
                truckTypeDto.setAmount(amount);
            }
        }

        return resultList;
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
    @Cacheable
    public Map<String, Object> listTrucksWithDrivers(QueryTruckDto criteriaDto) {
        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        //Integer depID = currentUserService.getCurrentUser().getDepartmentId();
        Integer depID = criteriaDto.getNetWorkId();
        String province = null;
        String city = null;
        String county = null;
        if (null != depID) {
            DepartmentReturnDto dept = deptClientService.getDepartmentById(depID);
            if (null != dept) {
                if (!StringUtils.isEmpty(dept.getProvince())) {
                    province = dept.getProvince();
                }
                if (!StringUtils.isEmpty(dept.getCity())) {
                    city = dept.getCity();
                }
                if (!StringUtils.isEmpty(dept.getCounty())) {
                    county = dept.getCounty();
                }
            }
        }
        criteriaDto.setProvince(province);
        criteriaDto.setCity(city);
        criteriaDto.setCounty(county);
        List<ListTruckDto> truckList = truckMapper.listTruckForAssignWaybillByCriteria(criteriaDto);
        log.info("listTrucksWithDrivers criteriaDto={},truckList={}",JSON.toJSONString(criteriaDto),JSON.toJSONString(truckList));
        if (!CollectionUtils.isEmpty(truckList)) {
            Iterator<ListTruckDto> truckIterator = truckList.iterator();
            while (truckIterator.hasNext()) {
                ListTruckDto listTruckDto = truckIterator.next();
                List<DriverReturnDto> drivers = driverClientService.listByTruckId(listTruckDto.getTruckId());
                log.info("listTrucksWithDrivers criteriaDto={},truckId={},drivers={}",JSON.toJSONString(criteriaDto),listTruckDto.getTruckId(),JSON.toJSONString(drivers));
                if (CollectionUtils.isEmpty(drivers)) {
                    truckIterator.remove();
                    continue;
                } else {
                    Iterator<DriverReturnDto> driverIterator = drivers.iterator();
                    while (driverIterator.hasNext()) {
                        DriverReturnDto driverDto = driverIterator.next();
                        if (0 == driverDto.getStatus()) {
                            driverIterator.remove();
                        }
                    }
                    if (CollectionUtils.isEmpty(drivers)) {
                        truckIterator.remove();
                        continue;
                    }
                }
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
        UserInfo userInfo = authClientService.getUserByToken(token);
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
            List<DriverReturnDto> driverList = new ArrayList<>();
            driverList.add(driver);
            truck.setDrivers(driverList);
            log.info("当前车辆: " + truck.toString());
        }
        return truck;
    }

    /**
     * 根据车牌号模糊查询车辆id列表
     *
     * @param truckNumber
     * @return
     */
    @Override
    @Cacheable
    public List<Integer> getTruckIdsByTruckNum(String truckNumber) {
        if (truckNumber != null) {
            List<Integer> truckIds = this.truckMapper.getTruckIdsByTruckNum(truckNumber);
            return truckIds;
        }
        return null;
    }

    /**
     * 根据车辆id列表获取车辆信息 不区分状态
     *
     * @param truckIdList
     * @return
     * @author yj
     */
    @Override
    public List<TruckDto> listTruckByIds(List<Integer> truckIdList) {
        return truckMapper.listTruckByIds(truckIdList);
    }

    /**
     * 根据车队id获取换车列表
     *
     * @param truckTeamId
     * @return
     */
    @Override
    public List<ChangeTruckDto> listTruckByTruckTeamId(Integer truckTeamId) {
        List<ChangeTruckDto> truckDtoList = truckMapper.listTruckByTruckTeamId(truckTeamId);
        if (!CollectionUtils.isEmpty(truckDtoList)) {
            for (ChangeTruckDto truckDto : truckDtoList) {
                List<DriverReturnDto> driverReturnDtoList = driverClientService.listByTruckId(truckDto.getId() == null ? null : truckDto.getId());
                if (!CollectionUtils.isEmpty(driverReturnDtoList)) {
                    truckDto.setDriverAmount(driverReturnDtoList.size());
                }
            }
        }
        return truckDtoList;
    }

    /**
     * 根据车牌号查询车辆
     *
     * @param truckNumber
     * @return
     */
    @Override
    public GetTruckDto getByTruckNumber(String truckNumber) {
        GetTruckDto getTruckDto = truckMapper.getDetailByTruckNumber(truckNumber);
        if (getTruckDto != null){
            List<DriverReturnDto> driverReturnDtoList = driverClientService.listByTruckId(getTruckDto.getId());
            getTruckDto.setDrivers(driverReturnDtoList);
            return getTruckDto;
        }
        return null;
    }
}