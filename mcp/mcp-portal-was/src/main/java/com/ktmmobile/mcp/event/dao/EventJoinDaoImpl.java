package com.ktmmobile.mcp.event.dao;

import com.ktmmobile.mcp.event.dto.EventBenefitBasDto;
import com.ktmmobile.mcp.event.dto.EventBenefitRelationDto;
import com.ktmmobile.mcp.event.dto.EventJoinDto;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventJoinDaoImpl implements EventJoinDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int selectEventJoinTotalCnt(EventJoinDto eventJoinDto) {
        return (Integer)sqlSessionTemplate.selectOne("EventJoinMapper.selectEventJoinTotalCnt", eventJoinDto);
    }

    @Override
    public List<EventJoinDto> selectEventJoinList(EventJoinDto eventJoinDto, int skipResult,
            int maxResult) {
        return sqlSessionTemplate.selectList("EventJoinMapper.selectEventJoinList", eventJoinDto,new RowBounds(skipResult, maxResult));
    }

    @Override
    public int eventJoinInsert(EventJoinDto eventJoinDto) {
        sqlSessionTemplate.insert("EventJoinMapper.insertMcpEventJoin",eventJoinDto);
        return 1;
    }

    @Override
    public int eventJoinDelete(EventJoinDto eventJoinDto) {
        return sqlSessionTemplate.delete("EventJoinMapper.deleteMcpEventJoin", eventJoinDto);
    }

    @Override
    public EventJoinDto selectUserChk(EventJoinDto eventJoinDto) {
        return sqlSessionTemplate.selectOne("EventJoinMapper.selectUserChk", eventJoinDto);
    }

    @Override
    public EventJoinDto selectRegUserChk(EventJoinDto eventJoinDto) {
        return sqlSessionTemplate.selectOne("EventJoinMapper.selectRegUserChk", eventJoinDto);
    }

    @Override
    public int selectEventShareTotalCnt(EventJoinDto eventJoinDto) {
        return (Integer)sqlSessionTemplate.selectOne("EventJoinMapper.selectEventShareTotalCnt", eventJoinDto);
    }

    @Override
    public List<EventJoinDto> eventShareList(EventJoinDto eventJoinDto) {
        return sqlSessionTemplate.selectList("EventJoinMapper.eventShareList", eventJoinDto);
    }

    @Override
    public int insertEventShare(EventJoinDto eventJoinDto) {
        sqlSessionTemplate.insert("EventJoinMapper.insertEventShare",eventJoinDto);
        return 1;
    }

    @Override
    public int eventShareTotalCnt(EventJoinDto eventJoinDto) {
        return (Integer)sqlSessionTemplate.selectOne("EventJoinMapper.eventShareTotalCnt", eventJoinDto);
    }

    @Override
    public int updateEventShare(EventJoinDto eventJoinDto) {
        return sqlSessionTemplate.update("EventJoinMapper.updateEventShare", eventJoinDto);
    }

    @Override
    public int insertShareAdd(EventJoinDto eventJoinDto) {
        sqlSessionTemplate.insert("EventJoinMapper.insertShareAdd",eventJoinDto);
        return 1;
    }

    @Override
    public EventBenefitBasDto getEventBenefit(EventBenefitRelationDto eventBenefitRelation) {
        return sqlSessionTemplate.selectOne("EventJoinMapper.getEventBenefit",eventBenefitRelation);
    }
}
