package com.jaagro.crm.biz.service.impl;


import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContactsDto;
import com.jaagro.crm.api.service.TruckTeamContactsService;
import com.jaagro.crm.biz.entity.TruckTeamContacts;
import com.jaagro.crm.biz.mapper.TruckTeamContactsMapper;
import com.jaagro.crm.biz.mapper.TruckTeamMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class TruckTeamContactsServiceImpl implements TruckTeamContactsService {

    private static final Logger log = LoggerFactory.getLogger(TruckTeamContractServiceImpl.class);

    @Autowired
    private TruckTeamContactsMapper truckTeamContactsMapper;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TruckTeamMapper truckTeamMapper;

    /**
     * 创建车队联系人对象
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> createTruckTeamContacts(CreateTruckTeamContactsDto dto) {

        TruckTeamContacts contacts = new TruckTeamContacts();
        BeanUtils.copyProperties(dto, contacts);
        contacts
                .setCreateUserId(currentUserService.getCurrentUser().getId());
        truckTeamContactsMapper.insertSelective(contacts);
        return ServiceResult.toResult(contacts.getId());
    }

    @Override
    public Map<String, Object> listTruckTeamContacts(Integer teamId){
        if(truckTeamMapper.selectByPrimaryKey(teamId) == null){
            ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), teamId + ": 车队id无效");
        }
        List<TruckTeamContacts> contacts = truckTeamContactsMapper.listTruckTeamContacts(teamId);
        if(StringUtils.isEmpty(contacts)){
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_EMPTY.getCode(), "查无数据");
        }
        return ServiceResult.toResult(contacts);
    }
}
