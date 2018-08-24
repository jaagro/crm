package com.jaagro.crm.biz.service.impl;


import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamContactsDto;
import com.jaagro.crm.api.service.TruckTeamContactsService;
import com.jaagro.crm.biz.entity.TruckTeamContacts;
import com.jaagro.crm.biz.mapper.TruckTeamContactsMapper;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 创建车队联系人对象
     * @param dto
     * @param truckTeamId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamContacts(List<CreateTruckTeamContactsDto> dto, Integer truckTeamId) {
        if(dto != null && dto.size() > 0){
            for(CreateTruckTeamContactsDto cttc: dto) {
                //创建联系人对象
                TruckTeamContacts TruckTeamContacts = new TruckTeamContacts();
                BeanUtils.copyProperties(cttc,TruckTeamContacts);
                TruckTeamContacts
                        .setEnabled(false)
                        .setTruckTeamId(truckTeamId)
                        .setCreateUserId(1);
                truckTeamContactsMapper.insert(TruckTeamContacts);
            }
        }
        return ServiceResult.toResult("创建车队联系人对象成功");
    }
}
