package com.ktmmobile.mcp.event.dao;

import com.ktmmobile.mcp.event.dto.EventOrderDtlDto;
import com.ktmmobile.mcp.event.dto.EventOrderDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventOrderDao {
    @Autowired
    @Qualifier(value="booteventSqlSession")
    private SqlSession booteventSqlSession;

    public EventOrderDto getEventOrderBySeq(String mstSeq) {
        return booteventSqlSession.selectOne("EventOrderMapper.getEventOrderBySeq", mstSeq);
    }

    public List<EventOrderDtlDto> getEventOrderDtlListBySeq(int mstSeq) {
        return booteventSqlSession.selectList("EventOrderMapper.getEventOrderDtlListBySeq", mstSeq);
    }

    public int updateEventBoardOdrgList(List<EventOrderDtlDto> eventOrderDtlList) {
        return booteventSqlSession.update("EventOrderMapper.updateEventBoardOdrgList", eventOrderDtlList);
    }

    public int updateEventOrderComplete(int mstSeq) {
        return booteventSqlSession.update("EventOrderMapper.updateEventOrderComplete", mstSeq);
    }
}
