package com.ktmmobile.mcp.event.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

//import com.ktmmobile.mcp.dto.ComCdDto;
import com.ktmmobile.mcp.event.dto.EventBoardDto;
import com.ktmmobile.mcp.event.dto.FrndRecommendDto;
import com.ktmmobile.mcp.event.dto.PlanBoardDto;
import com.ktmmobile.mcp.event.dto.PlanProductDto;
import com.ktmmobile.mcp.event.dto.WinnerBoardDto;
import com.ktmmobile.mcp.common.dto.db.NmcpEventBoardBasDto;
import com.ktmmobile.mcp.event.dto.NmcpEventLogDto;
import com.ktmmobile.mcp.event.dto.NmcpSurveyDto;

public interface CoEventDao {

//	public List<ComCdDto> eventComCd();

    public List<EventBoardDto> hotListSelect(EventBoardDto eventBoardDto);

    public List<EventBoardDto> jehuListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult);

    public int updateHit(EventBoardDto eventBoardDto);

    public EventBoardDto eventDetailSelect(EventBoardDto eventBoardDto);

    public List<PlanBoardDto> eventPlanDetailSelect(EventBoardDto eventBoardDto);

    public List<PlanProductDto> eventPlanProdSelect(int plnSeq);

    public List<PlanProductDto> eventPlanProdSelect2(int plnProdId);

    public List<PlanProductDto> eventPlanProdSelect3(int plnProdId);

    public List<PlanProductDto> eventPlanProdSelect4(int plnProdId);

    public List<PlanProductDto> eventPlanProdSelect5(HashMap<String, Object> rtnMap);

    public int updateWinnerHit(int ntcartSeq);

    //이벤트/제휴 진행중 카운터
    public int countPlanListSelect(EventBoardDto eventBoardDto);

    //이벤트/제휴 진행중 리스트
    public List<EventBoardDto> planListSelect(EventBoardDto eventBoardDto, int skipResult);

    //이벤트/제휴 종료 카운터
    public int countEventListSelect(EventBoardDto eventBoardDto);

    //이벤트/제휴 종료 리스트
    public List<EventBoardDto> eventListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult);

    //이벤트/제휴 진행중 - J
    public int countJehuListSelect(EventBoardDto eventBoardDto);

    //이벤트/제휴 상세보기
    public EventBoardDto selectEventDetailView(int ntcartSeq);

    //이벤트/제휴 당첨자 상세보기
    public WinnerBoardDto winnerDetailSelect(WinnerBoardDto winnerBoardDto);

    //이벤트/제휴 당첨자 카운터
    public int countWinnerListSelect(WinnerBoardDto winnerBoardDto);

    //이벤트/제휴 당첨차 리스트
    public List<WinnerBoardDto> winnerListSelect(WinnerBoardDto winnerBoardDto, int skipResult2, int maxResult2);

    /**
     * <pre>
     * 설명     : 이벤트  리스트 COUNT
     * @param nmcpEventBoardBasDto :
     * @return
     * </pre>
     */
    public int countByListNmcpEventBoard(NmcpEventBoardBasDto nmcpEventBoardBasDto);

    /**
     * <pre>
     * 설명     : 이벤트  리스트 조회
     * @param nmcpEventBoardBasDto :
     * @return
     * </pre>
     */
    public List<NmcpEventBoardBasDto> listNmcpEventBoard(NmcpEventBoardBasDto nmcpEventBoardBasDto,int skipResult, int maxResult) ;

    /**
     * <pre>
     * 설명     : 이벤트 참여 로그 저장
     * @param nmcpEventBoardBasDto :
     * @return
     * </pre>
     */
    public int insertNmcpEventLog(NmcpEventLogDto nmcpEventLogDto);

    /**
     * <pre>
     * 설명     : 설문 조사 저장
     * @param nmcpSurveyDto :
     * @return
     * </pre>
     */
    public int insertNmcpSurvey(NmcpSurveyDto nmcpSurveyDto);

    /**
     * <pre>
     * 설명     : 설문 조사 참여 여부
     * @param resNo :
     * @return
     * </pre>
     */
    public int selectNmcpSurvey(String resNo);

    public int countWinnerMSelect(WinnerBoardDto winnerBoardDto);

    public FrndRecommendDto selectFrndId(FrndRecommendDto frndRecommendDto);

    public int insertFrndId(FrndRecommendDto frndRecommendDto);

    public int duplicateChk(String commendId);

    public List<EventBoardDto> selectEventBoardList();

    //이벤트/제휴 진행중 카운터
    public int countPlanListSelectByDate(EventBoardDto eventBoardDto);

    //이벤트/제휴 진행중 리스트
    public List<EventBoardDto> planListSelectByDate(EventBoardDto eventBoardDto, int skipResult);

    EventBoardDto getEventDetailByDate(EventBoardDto eventBoardDto);


    public boolean updateCommendInfo(FrndRecommendDto frndRecommend) ;
    public boolean insertFrndHist(FrndRecommendDto frndRecommend) ;

    public boolean updateFrndHist(FrndRecommendDto frndRecommend) ;
}
