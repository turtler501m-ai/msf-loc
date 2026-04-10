package com.ktmmobile.mcp.event.service;

import com.ktmmobile.mcp.event.dao.EventJoinDao;
import com.ktmmobile.mcp.event.dto.EventBenefitBasDto;
import com.ktmmobile.mcp.event.dto.EventBenefitRelationDto;
import com.ktmmobile.mcp.event.dto.EventJoinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventJoinServiceImpl implements EventJoinService {


    @Autowired
    private EventJoinDao eventJoinDao;


    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int selectEventJoinTotalCnt(EventJoinDto eventJoinDto) {
        return eventJoinDao.selectEventJoinTotalCnt(eventJoinDto);
    }

    @Override
    public List<EventJoinDto> selectEventJoinList(EventJoinDto eventJoinDto, int skipResult,
            int maxResult) {
        return eventJoinDao.selectEventJoinList(eventJoinDto,skipResult,maxResult);
    }

    @Override
    public int eventJoinInsert(EventJoinDto eventJoinDto) {
        return eventJoinDao.eventJoinInsert(eventJoinDto);
    }

    @Override
    public int eventJoinDelete(EventJoinDto eventJoinDto) {
        return eventJoinDao.eventJoinDelete(eventJoinDto);
    }

    @Override
    public EventJoinDto selectRegUserChk(EventJoinDto eventJoinDto) {
        return eventJoinDao.selectRegUserChk(eventJoinDto);
    }

    @Override
    public EventJoinDto selectUserChk(EventJoinDto eventJoinDto) {
        return eventJoinDao.selectUserChk(eventJoinDto);
    }

    @Override
    public int selectEventShareTotalCnt(EventJoinDto eventJoinDto) {
         return eventJoinDao.selectEventShareTotalCnt(eventJoinDto);
    }

    @Override
    public List<EventJoinDto> eventShareList(EventJoinDto eventJoinDto) {
         return eventJoinDao.eventShareList(eventJoinDto);
    }

    @Override
    public int insertEventShare(EventJoinDto eventJoinDto) {
        return eventJoinDao.insertEventShare(eventJoinDto);
    }

    @Override
    public int eventShareTotalCnt(EventJoinDto eventJoinDto) {
        return eventJoinDao.eventShareTotalCnt(eventJoinDto);
    }

    @Override
    public int updateEventShare(EventJoinDto eventJoinDto) {
        return eventJoinDao.updateEventShare(eventJoinDto);
    }

    @Override
    public int insertShareAdd(EventJoinDto eventJoinDto) {
        return eventJoinDao.insertShareAdd(eventJoinDto);
    }

    public EventBenefitBasDto getEventBenefit(EventBenefitRelationDto eventBenefitRelation) {
        return eventJoinDao.getEventBenefit(eventBenefitRelation);
    }
}
