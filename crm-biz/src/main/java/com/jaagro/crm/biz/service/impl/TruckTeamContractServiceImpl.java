package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ContractStatus;
import com.jaagro.crm.api.dto.request.driver.CreateTruckTeamContractDto;
import com.jaagro.crm.api.service.TruckTeamContractService;
import com.jaagro.crm.biz.entity.TruckTeamContract;
import com.jaagro.crm.biz.mapper.TruckTeamContractMapper;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class TruckTeamContractServiceImpl implements TruckTeamContractService {

    @Autowired
    private TruckTeamContractMapper truckTeamContractMapper;

    /**
     * 创建合同
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto) {
        //创建合同对象
        TruckTeamContract truckTeamContract = new TruckTeamContract();
        BeanUtils.copyProperties(dto,truckTeamContract);
        truckTeamContract
                .setContractDate(new Date())
                .setContractStatus(ContractStatus.UNAUDITED);
        truckTeamContractMapper.insert(truckTeamContract);
        return ServiceResult.toResult("合同创建成功");
    }

    @Override
    public Map<String, Object> getById(Integer id) {
        return ServiceResult.toResult(truckTeamContractMapper.getById(id));
    }
}
