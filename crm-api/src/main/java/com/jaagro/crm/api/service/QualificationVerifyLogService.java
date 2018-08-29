package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateQualificationVerifyLogDto;

import java.util.Map;

/**
 * @author baiyiran
 */
public interface QualificationVerifyLogService {

    /**
     * 新增审核记录
     *
     * @param verifyLogDto
     * @return
     */
    Map<String, Object> createVerifyLog(CreateQualificationVerifyLogDto verifyLogDto);
}
