package com.ktmmobile.mcp.event.service;

import com.ktmmobile.mcp.event.dto.EventBenefitBasDto;
import com.ktmmobile.mcp.event.dto.EventBenefitRelationDto;
import com.ktmmobile.mcp.event.dto.EventJoinDto;

import java.util.List;


public interface EventJoinService {

        public int selectEventJoinTotalCnt(EventJoinDto eventJoinDto);

        public List<EventJoinDto> selectEventJoinList(EventJoinDto eventJoinDto, int skipResult,
                int maxResult);

        int eventJoinInsert(EventJoinDto eventJoinDto);

        int eventJoinDelete(EventJoinDto eventJoinDto);

        public EventJoinDto selectRegUserChk(EventJoinDto eventJoinDto);

        public EventJoinDto selectUserChk(EventJoinDto eventJoinDto);

        public int selectEventShareTotalCnt(EventJoinDto eventJoinDto);

        public List<EventJoinDto> eventShareList(EventJoinDto eventJoinDto);

        int insertEventShare(EventJoinDto eventJoinDto);

        public int eventShareTotalCnt(EventJoinDto eventJoinDto);

        int updateEventShare(EventJoinDto eventJoinDto);

        int insertShareAdd(EventJoinDto eventJoinDto);


        public EventBenefitBasDto getEventBenefit(EventBenefitRelationDto eventBenefitRelation) ;
}
