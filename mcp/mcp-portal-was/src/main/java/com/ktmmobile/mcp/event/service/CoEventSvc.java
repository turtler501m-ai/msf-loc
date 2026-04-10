package com.ktmmobile.mcp.event.service;

import java.util.List;

import com.ktmmobile.mcp.event.dto.EventBoardDto;
import com.ktmmobile.mcp.event.dto.FrndRecommendDto;
import com.ktmmobile.mcp.event.dto.PlanBoardDto;
import com.ktmmobile.mcp.event.dto.WinnerBoardDto;
import com.ktmmobile.mcp.common.dto.db.NmcpEventBoardBasDto;
import com.ktmmobile.mcp.event.dto.NmcpEventLogDto;
import com.ktmmobile.mcp.event.dto.NmcpSurveyDto;

public interface CoEventSvc {

//	public List<ComCdDto> eventComCd();

    public List<EventBoardDto> hotListSelect(EventBoardDto eventBoardDto);

    public List<EventBoardDto> jehuListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult);

    public int countWinnerMSelect(WinnerBoardDto winnerBoardDto);

    public EventBoardDto eventDetailSelect(EventBoardDto eventBoardDto);

    public List<PlanBoardDto> eventPlanDetailSelect(EventBoardDto eventBoardDto);

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

    //이벤트/제휴 당첨차 카운터
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
     * @param nmcpSurveyDtoList :
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
    public boolean selectNmcpSurvey(String resNo);

    /**
     * <pre>
     * 설명     : 조회수 증가
     * @param resNo :
     * @return
     * </pre>
     */
    public int updateHit(EventBoardDto eventBoardDto);

    public FrndRecommendDto selectFrndId(FrndRecommendDto frndRecommendDto);


    /**
     * <pre>
     * 설명     : 친구초대 2.0
     * @param  frndRecommendDto db 조회 인자값
     * @param  frndRecommendPra 입력값
     *         db값 조회후 변경 여부 확인
     *         정보 업데이트 및 이력 저장 철
     * </pre>
     */
    public FrndRecommendDto selectFrndIdUpdate(FrndRecommendDto frndRecommendDto , FrndRecommendDto frndRecommendPra);

    public int insertFrndId(FrndRecommendDto frndRecommendDto);

    public int duplicateChk(String commendId);

    public List<EventBoardDto> selectEventBoardList();

    //이벤트/제휴 진행중 카운터
    public int countPlanListSelectByDate(EventBoardDto eventBoardDto);

    //이벤트/제휴 진행중 리스트
    public List<EventBoardDto> planListSelectByDate(EventBoardDto eventBoardDto, int skipResult);

    EventBoardDto getEventDetailByDate(EventBoardDto eventBoardDto);


    /**
     * <pre>
     * 설명     : Random 하게 친구초대 아이디 생성
     * </pre>
     */
    public String getRandCommendId() ;
}
