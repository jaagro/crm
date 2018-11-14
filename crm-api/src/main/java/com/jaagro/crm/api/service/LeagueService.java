package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.league.CreateLeagueDto;
import com.jaagro.crm.api.dto.request.league.ListLeagueCerteriaDto;

import java.util.Map;

/**
 * 销售机会
 *
 * @author baiyiran
 * @Date 2018/11/14
 */
public interface LeagueService {

    /**
     * 新增销售机会
     *
     * @param createLeagueDto
     * @return
     */
    Map<String, Object> createLeague(CreateLeagueDto createLeagueDto);

    /**
     * 分页查询
     *
     * @param certeriaDto
     * @return
     */
    PageInfo listLeagueByCriteria(ListLeagueCerteriaDto certeriaDto);
}
