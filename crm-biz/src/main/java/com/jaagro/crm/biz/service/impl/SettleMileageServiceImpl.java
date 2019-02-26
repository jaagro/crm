package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.constant.UserInfo;
import com.jaagro.crm.api.dto.SettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.CreateSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.UpdateSettleMileageDto;
import com.jaagro.crm.api.dto.request.contract.listSettleMileageCriteriaDto;
import com.jaagro.crm.api.dto.response.contract.ReturnSettleMileageDto;
import com.jaagro.crm.api.service.SettleMileageService;
import com.jaagro.crm.biz.entity.Customer;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.entity.CustomerSite;
import com.jaagro.crm.biz.entity.SettleMileage;
import com.jaagro.crm.biz.mapper.CustomerContractMapperExt;
import com.jaagro.crm.biz.mapper.CustomerMapperExt;
import com.jaagro.crm.biz.mapper.CustomerSiteMapperExt;
import com.jaagro.crm.biz.mapper.SettleMileageMapperExt;
import com.jaagro.crm.biz.service.DriverClientService;
import com.jaagro.crm.biz.service.UserClientService;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CustomerContractMapperExt contractMapperExt;
    @Autowired
    private CustomerMapperExt customerMapperExt;
    @Autowired
    private UserClientService userClientService;

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
        UserInfo currentUser = userService.getCurrentUser();
        settleMileage
                .setCreateTime(new Date())
                .setCreateUserId(currentUser == null ? null : currentUser.getId())
                .setCreateUserName(currentUser == null ? null : currentUser.getName());
        CustomerSite site = siteMapperExt.selectByPrimaryKey(settleMileage.getLoadSiteId());
        if (site != null) {
            settleMileage
                    .setDepartmentId(site.getDeptId())
                    .setDepartmentName(deptService.getDeptNameById(site.getDeptId()));
        }
        SettleMileage settleMileageDb = settleMileageMapperExt.selectByCriteria(settleMileage);
        int result;
        if (settleMileageDb != null) {
            settleMileage.setId(settleMileageDb.getId());
            // 不覆盖已调整过的司机结算里程
            if (settleMileageDb.getDriverSettleMileage() != null
                    && settleMileageDb.getCustomerSettleMileage() != null
                    && settleMileageDb.getDriverSettleMileage().compareTo(settleMileageDb.getCustomerSettleMileage()) != 0) {
                settleMileage.setDriverSettleMileage(settleMileageDb.getDriverSettleMileage());
            }
            if (settleMileageDb.getTrackMileage() != null) {
                settleMileage.setTrackMileage(settleMileageDb.getTrackMileage());
            }
            result = settleMileageMapperExt.updateByPrimaryKeySelective(settleMileage);
        } else {
            result = settleMileageMapperExt.insertSelective(settleMileage);
        }
        if (result > 0) {
            flag = true;
            return flag;
        }
        return flag;
    }

    /**
     * 分页查询结算里程
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo listByCriteria(listSettleMileageCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        //部门隔离
        List<Integer> departIds = userClientService.getDownDepartment();
        if (!CollectionUtils.isEmpty(departIds)) {
            dto.setDepartIds(departIds);
        }
        if (!StringUtils.isEmpty(dto.getDeptId())) {
            departIds = userClientService.getDownDepartmentByDeptId(dto.getDeptId());
            if (!CollectionUtils.isEmpty(departIds)) {
                dto.setDepartIds(departIds);
                dto.setDeptId(null);
            }
        }
        List<ReturnSettleMileageDto> settleMileageDtoList = settleMileageMapperExt.listByCriteria(dto);
        if (!CollectionUtils.isEmpty(settleMileageDtoList)) {
            for (ReturnSettleMileageDto settleMileageDto : settleMileageDtoList) {
                CustomerContract customerContract = contractMapperExt.selectByPrimaryKey(settleMileageDto.getCustomerContractId());
                if (customerContract != null) {
                    Customer customer = customerMapperExt.selectByPrimaryKey(customerContract.getCustomerId());
                    if (customer != null) {
                        settleMileageDto.setCompanyName(customer.getCustomerName());
                    }
                }
            }
        }
        return new PageInfo<>(settleMileageDtoList);
    }

    /**
     * 修改结算里程
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> updateSettleMileage(UpdateSettleMileageDto dto) {
        SettleMileage settleMileage = settleMileageMapperExt.selectByPrimaryKey(dto.getId());
        if (settleMileage == null) {
            throw new RuntimeException("结算里程不存在");
        }
        UserInfo currentUser = userService.getCurrentUser();
        settleMileage
                .setDriverSettleMileage(dto.getDriverSettleMileage())
                .setModifyTime(new Date())
                .setModifyUserName(currentUser == null ? null : currentUser.getName())
                .setModifyUserId(currentUser == null ? null : currentUser.getId());
        int result = settleMileageMapperExt.updateByPrimaryKeySelective(settleMileage);
        if (result > 0) {
            return ServiceResult.toResult("修改成功");
        }
        return ServiceResult.error("修改失败");
    }

    /**
     * 根据条件查询结算里程表id
     *
     * @param paramDto
     * @return
     */
    @Override
    public SettleMileageDto selectByCriteria(SettleMileageDto paramDto) {
        SettleMileageDto settleMileageDto = new SettleMileageDto();
        SettleMileage settleMileage = new SettleMileage();
        BeanUtils.copyProperties(paramDto,settleMileage);
        SettleMileage settleMileageDo = settleMileageMapperExt.selectByCriteria(settleMileage);
        BeanUtils.copyProperties(settleMileageDo,settleMileageDto);
        return settleMileageDto;
    }

    /**
     * 根据id逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer disableById(Integer id) {
        return settleMileageMapperExt.disableById(id);
    }

}
