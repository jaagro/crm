package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.dto.request.truck.CreateTruckVerifyLogDto;
import com.jaagro.crm.api.service.TruckVerifyLogService;
import com.jaagro.crm.biz.entity.TruckVerifyLog;
import com.jaagro.crm.biz.mapper.TruckVerifyLogMapperExt;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author baiyiran
 */
@Service
public class TruckVerifyLogServiceImpl implements TruckVerifyLogService {

    @Autowired
    private TruckVerifyLogMapperExt verifyLogMapper;
    @Autowired
    private CurrentUserService userService;

    @Override
    public Map<String, Object> createTruckVerifyLog(CreateTruckVerifyLogDto dto) {
        TruckVerifyLog verifyLog = new TruckVerifyLog();
        BeanUtils.copyProperties(dto, verifyLog);
        verifyLog
                .setAuditor(this.userService.getCurrentUser().getId());
        this.verifyLogMapper.insertSelective(verifyLog);
        return ServiceResult.toResult("新增成功");
    }
}
