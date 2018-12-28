package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.contract.CreateSettleMileageDto;
import com.jaagro.crm.api.service.SettleMileageService;
import com.jaagro.crm.biz.entity.CustomerSite;
import com.jaagro.crm.biz.entity.SettleMileage;
import com.jaagro.crm.biz.mapper.CustomerSiteMapperExt;
import com.jaagro.crm.biz.mapper.SettleMileageMapperExt;
import com.jaagro.crm.biz.service.DriverClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author baiyiran
 * @Date 2018/12/28
 */
@Service
public class SettleMileageServiceImpl implements SettleMileageService {

    @Autowired
    private CurrentUserService userService;
    @Autowired
    private SettleMileageMapperExt settleMileageMapperExt;
    @Autowired
    private CustomerSiteMapperExt siteMapperExt;
    @Autowired
    private DriverClientService deptService;

    /**
     * 创建结算里程
     *
     * @param mileageDto
     * @return
     */
    @Override
    public Boolean createSettleMileage(CreateSettleMileageDto mileageDto) {
        Boolean flag = false;
        SettleMileage settleMileage = new SettleMileage();
        BeanUtils.copyProperties(mileageDto, settleMileage);
        settleMileage
                .setCreateTime(new Date())
                .setCreateUserId(userService.getCurrentUser().getId())
                .setCreateUserName(userService.getCurrentUser().getName());
        CustomerSite site = siteMapperExt.selectByPrimaryKey(settleMileage.getLoadSiteId());
        if (site != null) {
            settleMileage
                    .setDepartmentId(site.getDeptId())
                    .setDepartmentName(deptService.getDeptNameById(site.getDeptId()));
        }
        int result = settleMileageMapperExt.insertSelective(settleMileage);
        if (result > 0) {
            flag = true;
            return flag;
        }
        return flag;
    }
}
