package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.league.ListLeagueCerteriaDto;
import com.jaagro.crm.api.dto.response.league.ListLeagueDto;

import java.util.List;

/**
 * LeagueMapper
 *
 * @author baiyiran
 */
public interface LeagueMapperExt extends LeagueMapper {
    /**
     * 分页查询
     *
     * @param certeriaDto
     * @return
     */
    List<ListLeagueDto> listByCriteria(ListLeagueCerteriaDto certeriaDto);
}