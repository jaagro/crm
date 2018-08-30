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
     * @param truckTeamContacts
     * @return
     */
    @Override
    public Map<String, Object> createTruckTeamContacts(List<CreateTruckTeamContactsDto> truckTeamContacts) {

        if(truckTeamContacts != null && truckTeamContacts.size() > 0){
            for(CreateTruckTeamContactsDto contactsDto : truckTeamContacts){
                if(StringUtils.isEmpty(contactsDto.getTruckTeamId())){
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "车队id不能为空");
                }
                if(StringUtils.isEmpty(contactsDto.getContacts())){
                    return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "联系人姓名不能为空");
                }
                TruckTeamContacts contacts = new TruckTeamContacts();
                BeanUtils.copyProperties(truckTeamContacts, contacts);
                contacts
                        .setCreateUserId(currentUserService.getCurrentUser().getId());
                truckTeamContactsMapper.insertSelective(contacts);
            }
            return ServiceResult.toResult("联系人新增完成");
        }
        return ServiceResult.toResult("联系人新增失败");
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
