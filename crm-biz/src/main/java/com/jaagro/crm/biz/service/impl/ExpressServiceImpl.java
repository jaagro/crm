package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.LoginType;
import com.jaagro.crm.api.constant.ToDocment;
import com.jaagro.crm.api.constant.UserType;
import com.jaagro.crm.api.dto.request.express.CreateExpressDto;
import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;
import com.jaagro.crm.biz.entity.Express;
import com.jaagro.crm.api.service.ExpressService;
import com.jaagro.crm.biz.mapper.ExpressMapperExt;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.crm.biz.utils.UrlPathUtil;
import com.jaagro.utils.ServiceKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新闻管理
 *
 * @author gavin
 * @since 2018/12/29
 */
@Service
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressMapperExt expressMapperExt;

    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String QUERY_EXPRESS_PERSONS = "query_express_persons";


    /**
     * 创建智库直通车
     *
     * @param createExpressDto
     * @return
     */
    @Override
    public boolean createExpress(CreateExpressDto createExpressDto) {
        if (createExpressDto != null) {
            Express express = new Express();
            BeanUtils.copyProperties(createExpressDto, express);
            express.setPublishTime(new Date());
            String content = createExpressDto.getContent();
            express.setContent(content);
            UserInfo currentUser = currentUserService.getCurrentUser();
            express.setCreateTime(new Date())
                    .setCreateUserId(currentUser == null ? null : currentUser.getId())
                    .setEnable(true);
            Integer effectedNum = expressMapperExt.insertSelective(express);
            if (effectedNum < 1) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public ExpressReturnDto getExpressById(Integer id) {
        Express express = expressMapperExt.selectByPrimaryKey(id);
        ExpressReturnDto returnDto = new ExpressReturnDto();
        BeanUtils.copyProperties(express, returnDto);
        if (returnDto != null) {
            convertImageUrl(returnDto);
            Set<Integer> userIdSet = new HashSet<>();
            userIdSet.add(returnDto.getCreateUserId());
            if (express.getCreateUserType().equalsIgnoreCase(UserType.EMPLOYEE)) {
                List<UserInfo> userInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.EMPLOYEE);

                List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();
                if (!CollectionUtils.isEmpty(userInfoList) && null != userInfoList.get(0)) {
                    returnDto.setCreateUserName(userInfoList.get(0).getName());
                    DepartmentReturnDto departmentReturnDto = departmentReturnDtos.stream().filter(c -> c.getId().equals(userInfoList.get(0).getDepartmentId())).collect(Collectors.toList()).get(0);
                    if (null != departmentReturnDto) {
                        returnDto.setDepartmentName(departmentReturnDto.getDepartmentName());
                    }
                }
            }
            if (express.getCreateUserType().equalsIgnoreCase(UserType.DRIVER)) {
                List<UserInfo> driverInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.DRIVER);
                List<UserInfo> driverList = driverInfoList.stream().filter(c -> c.getId().equals(express.getCreateUserId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(driverList) && null != driverList.get(0)) {
                    returnDto.setCreateUserName(driverList.get(0).getName());
                }
                returnDto.setDepartmentName("司机");
            }
        }
        return returnDto;
    }


    /**
     * 查询智库直通车列表
     *
     * @param criteriaDto
     * @return
     */
    @Override
    public PageInfo listExpressByCriteria(QueryExpressDto criteriaDto) {

        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
        UserInfo currentUser = currentUserService.getCurrentUser();
        String currentUserId = currentUser == null ? null : currentUser.getId().toString();
        // 如果不在特权名单内只能查询自己创建的
        if (!opsForSet.isMember(QUERY_EXPRESS_PERSONS, currentUserId)) {
            criteriaDto.setCreateUserId(Integer.parseInt(currentUserId));
        }
        List<ExpressReturnDto> returnDtoList = expressMapperExt.listByCriteria(criteriaDto);
        if (!CollectionUtils.isEmpty(returnDtoList)) {
            Set<Integer> userIdSet = new HashSet<>();
            Set<Integer> driverIdSet = new HashSet<>();
            returnDtoList.forEach(returnDto -> {
                if (returnDto.getCreateUserId() != null && returnDto.getCreateUserType().equalsIgnoreCase(UserType.EMPLOYEE)) {
                    userIdSet.add(returnDto.getCreateUserId());
                }
                if (returnDto.getCreateUserId() != null && returnDto.getCreateUserType().equalsIgnoreCase(UserType.DRIVER)) {
                    driverIdSet.add(returnDto.getCreateUserId());
                }
            });
            if (!CollectionUtils.isEmpty(userIdSet)) {
                List<UserInfo> userInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.EMPLOYEE);

                if (!CollectionUtils.isEmpty(userInfoList)) {
                    List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();

                    for (ExpressReturnDto returnDto : returnDtoList) {
                        List<UserInfo> empList = userInfoList.stream().filter(c -> c.getId().equals(returnDto.getCreateUserId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(empList)) {
                            UserInfo userInfo = empList.get(0);
                            if (userInfo != null) {
                                returnDto.setCreateUserName(userInfo.getName());
                                DepartmentReturnDto departmentReturnDto = departmentReturnDtos.stream().filter(c -> c.getId().equals(userInfo.getDepartmentId())).collect(Collectors.toList()).get(0);
                                if (null != departmentReturnDto) {
                                    returnDto.setDepartmentName(departmentReturnDto.getDepartmentName());
                                }
                            }
                        }
                    }
                }
            }

            if (!CollectionUtils.isEmpty(driverIdSet)) {
                List<UserInfo> driverInfoList = userClientService.listUserInfo(new ArrayList<>(driverIdSet), UserType.DRIVER);
                if (!CollectionUtils.isEmpty(driverInfoList)) {
                    for (ExpressReturnDto returnDto : returnDtoList) {

                        List<UserInfo> driverList = driverInfoList.stream().filter(c -> c.getId().equals(returnDto.getCreateUserId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(driverList)) {
                            UserInfo driverInfo = driverList.get(0);
                            if (driverInfo != null) {
                                returnDto.setCreateUserName(driverInfo.getName());
                                returnDto.setDepartmentName("司机");
                            }
                        }
                    }
                }
            }
        }

        return new PageInfo<>(returnDtoList);
    }

    /**
     * 设为档案
     *
     * @param id
     * @return
     * @author yj
     */
    @Override
    public boolean toDocument(Integer id) {
        Express express = expressMapperExt.selectByPrimaryKey(id);
        if (express == null) {
            throw new RuntimeException("id=" + id + "不存在");
        }
        UserInfo currentUser = currentUserService.getCurrentUser();
        express.setToDocument(ToDocment.TRUE)
                .setModifyTime(new Date())
                .setModifyUserId(currentUser == null ? null : currentUser.getId());
        int effective = expressMapperExt.updateByPrimaryKeySelective(express);
        if (effective == 1) {
            return true;
        }
        return false;
    }

    /**
     * 新增查询所有智库直通车的人员
     *
     * @param phoneNumberList
     * @return
     */
    @Override
    public Map<String, Object> addQueryAllPerson(List<String> phoneNumberList) {
        Map<String, Object> result = new HashMap<>();
        result.put(ServiceKey.success.name(), Boolean.FALSE);
        Integer successNum = 0;
        Integer failNum = 0;
        if (!CollectionUtils.isEmpty(phoneNumberList)) {
            SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
            for (String phoneNumber : phoneNumberList) {
                UserInfo userInfo = userClientService.getUserInfo(phoneNumber, UserType.EMPLOYEE, LoginType.PHONE_NUMBER);
                if (userInfo != null) {
                    opsForSet.add(QUERY_EXPRESS_PERSONS, userInfo.getId().toString());
                    successNum += 1;
                } else {
                    failNum += 1;
                }
            }
        }
        if (successNum > 0) {
            result.put(ServiceKey.success.name(), Boolean.TRUE);
        }
        result.put("successNum", successNum);
        result.put("failNum", failNum);
        return result;
    }

    private void convertImageUrl(ExpressReturnDto express) {
        if (express != null) {
            if (StringUtils.hasText(express.getAttachUrl())) {
                express.setAttachUrl(UrlPathUtil.getAbstractImageUrl(express.getAttachUrl()));
            }
        }
    }
}