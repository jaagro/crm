package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamDto;
import com.jaagro.crm.api.dto.response.truck.TruckTeamReturnDto;
import com.jaagro.crm.api.service.TruckTeamService;
import com.jaagro.crm.biz.entity.TruckTeam;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author tony
 */
@Service
public class TruckTeamServiceImpl implements TruckTeamService {

    private static final Logger log = LoggerFactory.getLogger(TruckTeamServiceImpl.class);

    @Autowired
    private TruckTeamMapper truckTeamMapper;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 创建关联车队
     *
     * @param truckTeamDto
     * @return
     */
    @Override
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
    public Map<String, Object> updateTruckTeam(CreateTruckTeamDto truckTeamDto) {
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
        TruckTeamReturnDto truckTeam = truckTeamMapper.getTruckTeamById(id);
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
    @Override
    public Map<String, Object> deleteTruckTeam(Integer id) {
        if (truckTeamMapper.selectByPrimaryKey(id) == null) {
            ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), id + ": 不存在");
        }
        truckTeamMapper.deleteByLogic(id);
        return ServiceResult.toResult("删除成功");
    }

}
