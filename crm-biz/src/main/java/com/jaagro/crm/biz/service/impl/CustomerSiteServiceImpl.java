package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerSiteDto;
import com.jaagro.crm.api.dto.request.customer.ListSiteCriteriaDto;
import com.jaagro.crm.api.dto.request.customer.UpdateCustomerSiteDto;
import com.jaagro.crm.api.dto.response.customer.CustomerSiteReturnDto;
import com.jaagro.crm.api.service.CustomerSiteService;
import com.jaagro.crm.biz.entity.CustomerSite;
import com.jaagro.crm.biz.mapper.CustomerSiteMapper;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class CustomerSiteServiceImpl implements CustomerSiteService {

    @Autowired
    private CustomerSiteMapper siteMapper;
    @Autowired
    private CurrentUserService userService;

    @Override
    public Map<String, Object> createSite(CreateCustomerSiteDto customerSiteDto) {
        CustomerSite site = new CustomerSite();
        BeanUtils.copyProperties(customerSiteDto, site);
        site
                .setCreateTime(new Date())
                .setSiteStatus(1)
                .setCreateUserId(userService.getCurrentUser().getId());
        siteMapper.insert(site);
        return ServiceResult.toResult("地址创建成功");
    }

    @Override
    public Map<String, Object> createSite(List<CreateCustomerSiteDto> customerSiteDtos, Integer CustomerId) {
        if (customerSiteDtos != null && customerSiteDtos.size() > 0) {
            for (CreateCustomerSiteDto customerSiteDto : customerSiteDtos) {
                CustomerSite site = new CustomerSite();
                BeanUtils.copyProperties(customerSiteDto, site);
                site
                        .setCustomerId(CustomerId)
                        .setCreateTime(new Date())
                        .setSiteStatus(1)
                        .setCreateUserId(userService.getCurrentUser().getId());
                siteMapper.insert(site);
            }
        }
        return ServiceResult.toResult("地址创建成功");
    }

    @Override
    public Map<String, Object> updateSite(UpdateCustomerSiteDto customerSiteDto) {
        CustomerSite site = new CustomerSite();
        BeanUtils.copyProperties(customerSiteDto, site);
        site
                .setModifyTime(new Date())
                .setModifyUserId(userService.getCurrentUser().getId());
        siteMapper.updateByPrimaryKeySelective(site);
        return ServiceResult.toResult("地址修改成功");
    }

    @Transactional
    @Override
    public Map<String, Object> updateSite(List<UpdateCustomerSiteDto> customerSiteDtos) {
        if (customerSiteDtos.size() > 0) {
            for (UpdateCustomerSiteDto customerSiteDto : customerSiteDtos) {
                if (customerSiteDto.getId() == null) {
//                    throw RuntimeException();
                }
                CustomerSite site = new CustomerSite();
                BeanUtils.copyProperties(customerSiteDto, site);
                site
                        .setModifyTime(new Date())
                        .setModifyUserId(userService.getCurrentUser().getId());
                siteMapper.updateByPrimaryKeySelective(site);
            }
        }
        return ServiceResult.toResult("地址修改成功");
    }

    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(this.siteMapper.selectByPrimaryKey(id));
    }

    @Override
    public Map<String, Object> listByCriteria(ListSiteCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<CustomerSiteReturnDto> siteReturnDtos = siteMapper.getByCriteriDto(dto);
        return ServiceResult.toResult(new PageInfo<>(siteReturnDtos));
    }

    @Override
    public Map<String, Object> disableSite(Integer id) {
        CustomerSite site = this.siteMapper.selectByPrimaryKey(id);
        site.setSiteStatus(0);
        this.siteMapper.updateByPrimaryKeySelective(site);
        return ServiceResult.toResult("地址删除成功");
    }

    @Override
    public Map<String, Object> disableSite(List<CustomerSiteReturnDto> siteReturnDtos) {
        if (siteReturnDtos != null && siteReturnDtos.size() > 0) {
            for (CustomerSiteReturnDto siteDto : siteReturnDtos) {
                CustomerSite site = this.siteMapper.selectByPrimaryKey(siteDto.getId());
                site.setSiteStatus(0);
                this.siteMapper.updateByPrimaryKeySelective(site);
            }
        }
        return ServiceResult.toResult("地址删除成功");
    }

    @Override
    public Map<String, Object> getBySiteDto(UpdateCustomerSiteDto siteDto) {
        return ServiceResult.toResult(this.siteMapper.getSiteDto(siteDto));
    }

}
