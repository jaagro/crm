package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamBankcardDto;
import com.jaagro.crm.api.service.TruckTeamBankcardService;
import com.jaagro.crm.biz.entity.TruckTeamBankcard;
import com.jaagro.crm.biz.mapper.TruckTeamBankcardMapper;
import com.jaagro.utils.ServiceResult;
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
public class TruckTeamBankcardImpl implements TruckTeamBankcardService {

    @Autowired
    private TruckTeamBankcardMapper truckTeamBankcardMapper;

    /**
     * 创建车队银行卡号成功
     * @param dto
     * @param truckTeamId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamBankcard(List<CreateTruckTeamBankcardDto> dto, Integer truckTeamId) {
        if(dto != null && dto.size() > 0){
            for(CreateTruckTeamBankcardDto cttb: dto) {
                //创建车队银行卡号
                TruckTeamBankcard truckTeamBankcard = new TruckTeamBankcard();
                BeanUtils.copyProperties(cttb,truckTeamBankcard);
                truckTeamBankcard
                        .setTruckTeamId(truckTeamId);
                truckTeamBankcardMapper.insert(truckTeamBankcard);
            }
        }
        return ServiceResult.toResult("创建车队用户卡号成功");
    }
}
