package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.constant.ToDocment;
import com.jaagro.crm.api.constant.UserType;
import com.jaagro.crm.api.dto.request.express.CreateExpressDto;
import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.department.DepartmentReturnDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;
import com.jaagro.crm.api.entity.Express;
import com.jaagro.crm.api.service.ExpressService;
import com.jaagro.crm.biz.mapper.ExpressMapperExt;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.crm.biz.utils.UrlPathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            // 内容里空格标签替换成空格,已跟前端约定
            String content = createExpressDto.getContent().replace("&nbsp; "," ").replace("&nbsp;"," ");
            if (content.length() > 13000){
                throw new RuntimeException("亲,内容太长了");
            }
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
            List<UserInfo> userInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.EMPLOYEE);
            List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();
            if (!CollectionUtils.isEmpty(userInfoList)) {
                returnDto.setCreateUserName(userInfoList.get(0).getName());
                DepartmentReturnDto departmentReturnDto = departmentReturnDtos.stream().filter(c -> c.getId().equals(userInfoList.get(0).getDepartmentId())).collect(Collectors.toList()).get(0);
                if (null != departmentReturnDto) {
                    returnDto.setDepartmentName(departmentReturnDto.getDepartmentName());
                }
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
        List<ExpressReturnDto> returnDtoList = expressMapperExt.listByCriteria(criteriaDto);
        if (!CollectionUtils.isEmpty(returnDtoList)) {
            Set<Integer> userIdSet = new HashSet<>();
            returnDtoList.forEach(returnDto -> {
                if (returnDto.getCreateUserId() != null) {
                    userIdSet.add(returnDto.getCreateUserId());
                }
            });
            if (!CollectionUtils.isEmpty(userIdSet)) {
                List<UserInfo> userInfoList = userClientService.listUserInfo(new ArrayList<>(userIdSet), UserType.EMPLOYEE);

                if (!CollectionUtils.isEmpty(userInfoList)) {
                    List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();

                    for (ExpressReturnDto returnDto : returnDtoList) {

                        UserInfo userInfo = userInfoList.stream().filter(c -> c.getId().equals(returnDto.getCreateUserId())).collect(Collectors.toList()).get(0);

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

    private void convertImageUrl(ExpressReturnDto express) {
        if (express != null) {
            if (StringUtils.hasText(express.getAttachUrl())) {
                express.setAttachUrl(UrlPathUtil.getAbstractImageUrl(express.getAttachUrl()));
            }
        }
    }
}