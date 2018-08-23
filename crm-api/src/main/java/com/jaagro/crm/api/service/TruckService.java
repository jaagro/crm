package com.jaagro.crm.api.service;

import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckService {
    /**
     * 获取单条车辆
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Integer id);
}
