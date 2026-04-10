package com.ktmmobile.mcp.event.dao;

import java.util.HashMap;
import java.util.List;

import com.ktmmobile.mcp.common.dto.db.McpRequestOsstDto;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.event.dto.EventBoardDto;
import com.ktmmobile.mcp.event.dto.FrndRecommendDto;
import com.ktmmobile.mcp.event.dto.PlanBoardDto;
import com.ktmmobile.mcp.event.dto.PlanProductDto;
import com.ktmmobile.mcp.event.dto.WinnerBoardDto;
import com.ktmmobile.mcp.event.service.CoEventSvcImpl;
import com.ktmmobile.mcp.common.dto.db.NmcpEventBoardBasDto;
import com.ktmmobile.mcp.event.dto.NmcpEventLogDto;
import com.ktmmobile.mcp.event.dto.NmcpSurveyDto;

@Repository
public class CoEventDaoImpl implements CoEventDao {

    private static Logger logger = LoggerFactory.getLogger(CoEventSvcImpl.class);

    @Autowired
    SqlSessionTemplate sqlSession2; //postgresql

    @Autowired
    SqlSessionTemplate sqlSessionTemplate; // oracle

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int countPlanListSelect(EventBoardDto eventBoardDto) {
        return (Integer)sqlSession2.selectOne("EventBoardMapper.countPlanListSelect", eventBoardDto);
    }

    @Override
    public List<EventBoardDto> planListSelect(EventBoardDto eventBoardDto, int skipResult) {
        return sqlSession2.selectList("EventBoardMapper.planListSelect", eventBoardDto);
    }

    @Override
    public int countEventListSelect(EventBoardDto eventBoardDto) {
        return (Integer)sqlSession2.selectOne("EventBoardMapper.countEventListSelect", eventBoardDto);
    }

    @Override
    public List<EventBoardDto> eventListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult) {
        return sqlSession2.selectList("EventBoardMapper.eventListSelect", eventBoardDto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public int countJehuListSelect(EventBoardDto eventBoardDto) {
        return (Integer)sqlSession2.selectOne("EventBoardMapper.countJehuListSelect", eventBoardDto);
    }

    @Override
    public EventBoardDto selectEventDetailView(int ntcartSeq) {
        return sqlSession2.selectOne("EventBoardMapper.selectEventDetailView", ntcartSeq);
    }

    @Override
    public WinnerBoardDto winnerDetailSelect(WinnerBoardDto winnerBoardDto) {
        return sqlSession2.selectOne("EventBoardMapper.winnerDetailSelect", winnerBoardDto);
    }

    @Override
    public int countWinnerListSelect(WinnerBoardDto winnerBoardDto) {

        return (Integer)sqlSession2.selectOne("EventBoardMapper.countWinnerListSelect", winnerBoardDto);
    }

    @Override
    public List<WinnerBoardDto> winnerListSelect(WinnerBoardDto winnerBoardDto, int skipResult2, int maxResult2) {
        return sqlSession2.selectList("EventBoardMapper.winnerListSelect", winnerBoardDto, new RowBounds(skipResult2, maxResult2));
    }

    @Override
    public List<EventBoardDto> hotListSelect(EventBoardDto eventBoardDto) {

        return sqlSession2.selectList("EventBoardMapper.hotListSelect", eventBoardDto);
    }

    @Override
    public List<EventBoardDto> jehuListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult) {

        return sqlSession2.selectList("EventBoardMapper.jehuListSelect", eventBoardDto, new RowBounds(skipResult, maxResult));
    }

    @Override
    public int updateHit(EventBoardDto eventBoardDto) {	//카운터 증가
        return sqlSession2.update("EventBoardMapper.updateHit", eventBoardDto);
    }

    @Override
    public int countWinnerMSelect(WinnerBoardDto winnerBoardDto) {
        return (Integer)sqlSession2.selectOne("EventBoardMapper.countWinnerListSelect", winnerBoardDto);
    }

    @Override
    public EventBoardDto eventDetailSelect(EventBoardDto eventBoardDto) {
        return sqlSession2.selectOne("EventBoardMapper.eventDetailSelect", eventBoardDto);
    }

    @Override
    public List<PlanBoardDto> eventPlanDetailSelect(EventBoardDto eventBoardDto) {
        return sqlSession2.selectList("EventBoardMapper.eventPlanDetailSelect", eventBoardDto);
    }

    @Override
    public List<PlanProductDto> eventPlanProdSelect(int plnSeq) {
        return sqlSession2.selectList("EventBoardMapper.eventPlanProdSelect", plnSeq);
    }

    @Override
    public List<PlanProductDto> eventPlanProdSelect2(int plnProdId) {
        return sqlSessionTemplate.selectList("EventMapper.eventPlanProdSelect2", plnProdId);
    }

    @Override
    public List<PlanProductDto> eventPlanProdSelect3(int plnProdId) {
        return sqlSessionTemplate.selectList("EventMapper.eventPlanProdSelect3", plnProdId);
    }

    @Override
    public List<PlanProductDto> eventPlanProdSelect4(int plnProdId) {
        return sqlSessionTemplate.selectList("EventMapper.eventPlanProdSelect4", plnProdId);
    }

    @Override
    public List<PlanProductDto> eventPlanProdSelect5(HashMap<String, Object> rtnMap) {
        return sqlSessionTemplate.selectList("EventMapper.eventPlanProdSelect5", rtnMap);
    }

    @Override
    public int updateWinnerHit(int ntcartSeq) {
        return sqlSession2.update("EventBoardMapper.updateWinnerHit", ntcartSeq);
    }

    @Override
    public int countByListNmcpEventBoard(NmcpEventBoardBasDto nmcpEventBoardBasDto){
        Object resultObj = sqlSession2.selectOne("EventBoardMapper.countByListNmcpEventBoard",nmcpEventBoardBasDto);
        if(resultObj instanceof Number){
            Number number = (Number) resultObj;
            return number.intValue();
        }else{
            throw new IllegalArgumentException(String.format("Wrong  resultClass type(%s) with queryId:%s, resultClass must be subclass of java.lang.Number", resultObj.getClass().getName(), ".countByListNmcpEventBoard"));
        }
    }

    @Override
    public List<NmcpEventBoardBasDto> listNmcpEventBoard(NmcpEventBoardBasDto nmcpEventBoardBasDto,int skipResult, int maxResult) {
        return sqlSession2.selectList("EventBoardMapper.listNmcpEventBoard", nmcpEventBoardBasDto,new RowBounds(skipResult, maxResult));
    }

    @Override
    public int insertNmcpEventLog(NmcpEventLogDto nmcpEventLogDto) {
        return sqlSession2.insert("EventBoardMapper.insertNmcpEventLog", nmcpEventLogDto);
    }

    @Override
    public int insertNmcpSurvey(NmcpSurveyDto nmcpSurveyDto) {
        return sqlSessionTemplate.insert("EventMapper.insertNmcpSurvey", nmcpSurveyDto);
    }

    @Override
    public int selectNmcpSurvey(String resNo) {
        return (Integer)sqlSessionTemplate.selectOne("EventMapper.selectNmcpSurvey", resNo);
    }

    @Override
    public FrndRecommendDto selectFrndId(FrndRecommendDto frndRecommendDto) {
        return sqlSessionTemplate.selectOne("EventMapper.selectFrndId", frndRecommendDto);
    }



    @Override
    public int insertFrndId(FrndRecommendDto frndRecommendDto) {
        return sqlSessionTemplate.insert("EventMapper.insertFrndId", frndRecommendDto);
    }

    @Override
    public int duplicateChk(String commendId) {
        return (Integer)sqlSessionTemplate.selectOne("EventMapper.duplicateChk", commendId);
    }

    @Override
    public List<EventBoardDto> selectEventBoardList() {
        return sqlSession2.selectList("EventBoardMapper.selectEventBoardList");
    }

    @Override
    public int countPlanListSelectByDate(EventBoardDto eventBoardDto) {
        return (Integer)sqlSession2.selectOne("EventBoardMapper.countPlanListSelectByDate", eventBoardDto);
    }

    @Override
    public List<EventBoardDto> planListSelectByDate(EventBoardDto eventBoardDto, int skipResult) {
        return sqlSession2.selectList("EventBoardMapper.planListSelectByDate", eventBoardDto);
    }

    @Override
    public EventBoardDto getEventDetailByDate(EventBoardDto eventBoardDto) {
        return sqlSession2.selectOne("EventBoardMapper.getEventDetailByDate", eventBoardDto);
    }


    @Override
    public boolean updateCommendInfo(FrndRecommendDto frndRecommend) {
        return     0 < sqlSessionTemplate.update("EventMapper.updateCommendInfo",frndRecommend);
    }

    @Override
    public boolean insertFrndHist(FrndRecommendDto frndRecommend) {
        return     0 < sqlSessionTemplate.insert("EventMapper.insertFrndHist",frndRecommend);
    }


    @Override
    public boolean updateFrndHist(FrndRecommendDto frndRecommend) {
        return     0 < sqlSessionTemplate.update("EventMapper.updateFrndHist",frndRecommend);
    }



}
