package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamDto;
import com.jaagro.crm.api.dto.response.driver.TruckTeamReturnDto;
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
        truckTeam.setCreateUserId(currentUserService.getCurrentUser().getId());
        truckTeamMapper.insertSelective(truckTeam);
        return ServiceResult.toResult(truckTeam.getId());
    }

    /**
     * 获取单条车辆
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTruckTeamById(Integer id) {
        TruckTeamReturnDto truckTeam = truckTeamMapper.getTruckTeamById(id);
        if(truckTeam == null){
            ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), id + ": 不存在");
        }
        return ServiceResult.toResult(truckTeam);
    }

    @Override
    public Map<String, Object> listTruckTeam() {
        return null;
    }

}
