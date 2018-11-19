package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.league.CreateLeagueDto;
import com.jaagro.crm.api.dto.request.league.ListLeagueCerteriaDto;
import com.jaagro.crm.api.dto.response.league.ListLeagueDto;
import com.jaagro.crm.api.service.LeagueService;
import com.jaagro.crm.biz.entity.League;
import com.jaagro.crm.biz.mapper.LeagueMapperExt;
import com.jaagro.utils.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 * @Date 2018/11/14
 */
@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private LeagueMapperExt leagueMapper;

    /**
     * 新增销售机会
     *
     * @param createLeagueDto
     * @return
     */
    @Override
    public Map<String, Object> createLeague(CreateLeagueDto createLeagueDto) {
        League league = new League();
        BeanUtils.copyProperties(createLeagueDto, league);
        int result = leagueMapper.insertSelective(league);
        if (result > 0) {
            return ServiceResult.toResult("录入成功");
        }
        return ServiceResult.error("录入失败");
    }

    /**
     * 分页查询
     *
     * @param certeriaDto
     * @return
     */
    @Override
    public PageInfo listLeagueByCriteria(ListLeagueCerteriaDto certeriaDto) {
        PageHelper.startPage(certeriaDto.getPageNum(), certeriaDto.getPageSize());
        List<ListLeagueDto> leagueDtoList = leagueMapper.listByCriteria(certeriaDto);
        return new PageInfo(leagueDtoList);
    }
}
