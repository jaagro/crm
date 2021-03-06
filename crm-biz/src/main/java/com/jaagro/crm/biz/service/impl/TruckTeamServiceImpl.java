package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckTeamCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckTeamDto;
import com.jaagro.crm.api.service.TruckTeamService;
import com.jaagro.crm.biz.entity.TruckTeam;
import com.jaagro.crm.biz.mapper.TruckTeamMapperExt;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 */
@Service
@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "truck")
public class TruckTeamServiceImpl implements TruckTeamService {

    private static final Logger log = LoggerFactory.getLogger(TruckTeamServiceImpl.class);

    @Autowired
    private TruckTeamMapperExt truckTeamMapper;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 创建关联车队
     *
     * @param truckTeamDto
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "truck", allEntries = true)
    public Map<String, Object> createTruckTeam(CreateTruckTeamDto truckTeamDto) {
        TruckTeam truckTeam = new TruckTeam();
        BeanUtils.copyProperties(truckTeamDto, truckTeam);
        truckTeam
                .setCreateUserId(currentUserService.getCurrentUser().getId())
                .setTeamStatus(AuditStatus.UNCHECKED);
        truckTeamMapper.insertSelective(truckTeam);
        return ServiceResult.toResult(truckTeam.getId());
    }

    /**
     * 修改车队
     *
     * @param truckTeamDto
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "truck", allEntries = true)
    public Map<String, Object> updateTruckTeam(UpdateTruckTeamDto truckTeamDto) {
        if (truckTeamMapper.selectByPrimaryKey(truckTeamDto.getId()) == null) {
            ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), truckTeamDto.getId() + " ：该车队不存在");
        }
        TruckTeam truckTeam = new TruckTeam();
        BeanUtils.copyProperties(truckTeamDto, truckTeam);
        truckTeam
                .setModifyTime(new Date())
                .setModifyUserId(currentUserService.getCurrentUser().getId());
        truckTeamMapper.updateByPrimaryKeySelective(truckTeam);
        return ServiceResult.toResult(truckTeamMapper.getTruckTeamById(truckTeam.getId()));
    }

    /**
     * 获取单条车队
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTruckTeamById(Integer id) {
        ListTruckTeamDto truckTeam = truckTeamMapper.getTruckTeamById(id);
        if (truckTeam == null) {
            ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), id + ": 不存在");
        }
        return ServiceResult.toResult(truckTeam);
    }

    @Override
    public Map<String, Object> listTruckTeam() {
        return null;
    }

    /**
     * 删除单条车队
     *
     * @param id
     * @return
     */
    @CacheEvict(cacheNames = "truck", allEntries = true)
    @Override
    public Map<String, Object> deleteTruckTeam(Integer id) {
        if (truckTeamMapper.selectByPrimaryKey(id) == null) {
            ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), id + ": 不存在");
        }
        truckTeamMapper.deleteByLogic(id);
        return ServiceResult.toResult("删除成功");
    }

    @Cacheable
    @Override
    public Map<String, Object> listTruckTeamByCerteria(ListTruckTeamCriteriaDto truckCriteria) {
        PageHelper.startPage(truckCriteria.getPageNum(), truckCriteria.getPageSize());
        List<ListTruckTeamDto> listTruckTeamDtoList = this.truckTeamMapper.listByCeriteria(truckCriteria);
        return ServiceResult.toResult(new PageInfo<>(listTruckTeamDtoList));
    }

}
