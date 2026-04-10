package com.ktmmobile.mcp.event.service;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static org.springframework.util.StringUtils.hasText;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.event.dto.EventBoardDto;
import com.ktmmobile.mcp.event.dto.FrndRecommendDto;
import com.ktmmobile.mcp.event.dto.PlanBoardDto;
import com.ktmmobile.mcp.event.dto.PlanProductDto;
import com.ktmmobile.mcp.event.dto.WinnerBoardDto;
import com.ktmmobile.mcp.common.dto.db.NmcpEventBoardBasDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.mspservice.dto.MspSaleAgrmMst;
import com.ktmmobile.mcp.event.dao.CoEventDao;
import com.ktmmobile.mcp.event.dto.NmcpEventLogDto;
import com.ktmmobile.mcp.event.dto.NmcpSurveyDto;

@Service
public class CoEventSvcImpl implements CoEventSvc {

    private static Logger logger = LoggerFactory.getLogger(CoEventSvcImpl.class);

    @Autowired
    CoEventDao coEventDao;

    @Autowired
    private IpStatisticService ipstatisticService;

/*	@Override
    public List<ComCdDto> eventComCd(){
        return coEventDao.eventComCd();
    }*/

    @Override
    public int countPlanListSelect(EventBoardDto eventBoardDto) {
        return coEventDao.countPlanListSelect(eventBoardDto);
    }

    @Override
    public List<EventBoardDto> planListSelect(EventBoardDto eventBoardDto, int skipResult) {
        return coEventDao.planListSelect(eventBoardDto, skipResult);
    }

    @Override
    public int countEventListSelect(EventBoardDto eventBoardDto){
        return coEventDao.countEventListSelect(eventBoardDto);
    }

    @Override
    public List<EventBoardDto> eventListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult) {
        return coEventDao.eventListSelect(eventBoardDto, skipResult, maxResult);
    }

    @Override
    public int countJehuListSelect(EventBoardDto eventBoardDto) {
        return coEventDao.countJehuListSelect(eventBoardDto);
    }

    @Override
    public EventBoardDto selectEventDetailView(int ntcartSeq) {
        EventBoardDto eventBoardDto = new EventBoardDto();
        eventBoardDto.setNtcartSeq(ntcartSeq);
        coEventDao.updateHit(eventBoardDto);
        return coEventDao.selectEventDetailView(ntcartSeq);
    }

    @Override
    public WinnerBoardDto winnerDetailSelect(WinnerBoardDto winnerBoardDto){
        coEventDao.updateWinnerHit(winnerBoardDto.getNtcartSeq());	//카운터 증가
        return coEventDao.winnerDetailSelect(winnerBoardDto);
    }

    @Override
    public int countWinnerListSelect(WinnerBoardDto winnerBoardDto) {

        return coEventDao.countWinnerListSelect(winnerBoardDto);
    }

    @Override
    public List<WinnerBoardDto> winnerListSelect(WinnerBoardDto winnerBoardDto, int skipResult2, int maxResult2) {
        return coEventDao.winnerListSelect(winnerBoardDto, skipResult2, maxResult2);
    }

    @Override
    public int countWinnerMSelect(WinnerBoardDto winnerBoardDto) {
        return coEventDao.countWinnerListSelect(winnerBoardDto);
    }

    @Override
    public List<EventBoardDto> hotListSelect(EventBoardDto eventBoardDto) {
        return coEventDao.hotListSelect(eventBoardDto);
    }

    @Override
    public List<EventBoardDto> jehuListSelect(EventBoardDto eventBoardDto, int skipResult, int maxResult) {
        return coEventDao.jehuListSelect(eventBoardDto, skipResult, maxResult);
    }

    @Override
    public EventBoardDto eventDetailSelect(EventBoardDto eventBoardDto) {
        coEventDao.updateHit(eventBoardDto);	//카운터 증가
        return coEventDao.eventDetailSelect(eventBoardDto);
    }

    @Override
    public int updateHit(EventBoardDto eventBoardDto) {
        return coEventDao.updateHit(eventBoardDto);
    }

    @Override
    public List<PlanBoardDto> eventPlanDetailSelect(EventBoardDto eventBoardDto) {

        List<PlanBoardDto> planList = coEventDao.eventPlanDetailSelect(eventBoardDto);	//이벤트 기획전

        if(planList != null) {
            for(int i=0; planList.size()>i; i++){
                int plnSeq = planList.get(i).getPlnSeq();

                List<PlanProductDto> prodList = coEventDao.eventPlanProdSelect(plnSeq);	//이벤트 기획전의 상품들

                for(int j=0; prodList.size()>j; j++){
                    int plnProdId = prodList.get(j).getProdId();
                    String plnValCd1 = prodList.get(j).getPlnAtribValCd1();
                    HashMap<String, Object> rtnMap = new HashMap<String, Object>();
                    rtnMap.put("plnProdId", plnProdId);
                    rtnMap.put("plnValCd1", plnValCd1);

                    List<PlanProductDto> prodList2 = coEventDao.eventPlanProdSelect2(plnProdId);//이벤트 기획전의 상품들
                    List<PlanProductDto> prodList3 = coEventDao.eventPlanProdSelect3(plnProdId); //이벤트 기획전의 상품들
                    List<PlanProductDto> prodList4 = coEventDao.eventPlanProdSelect4(plnProdId);//이벤트 기획전의 상품들
                    List<PlanProductDto> prodList5 = coEventDao.eventPlanProdSelect5(rtnMap);	//이벤트 기획전의 상품들
                    @SuppressWarnings("unchecked")
                    List<PlanProductDto> builderStream = (List<PlanProductDto>) Stream.builder()
                                                                                      .add(prodList).add(prodList2)
                                                                                      .add(prodList3).add(prodList4)
                                                                                      .add(prodList5).build();
                    planList.get(i).setProdList(builderStream);
                }
            }
        }


        return planList;
    }

    @Override
    public int countByListNmcpEventBoard(NmcpEventBoardBasDto nmcpEventBoardBasDto){
        return coEventDao.countByListNmcpEventBoard(nmcpEventBoardBasDto);
    }

    @Override
    public List<NmcpEventBoardBasDto> listNmcpEventBoard(NmcpEventBoardBasDto nmcpEventBoardBasDto,int skipResult, int maxResult) {
        return coEventDao.listNmcpEventBoard(nmcpEventBoardBasDto, skipResult, maxResult);
    }

    @Override
    public int insertNmcpEventLog(NmcpEventLogDto nmcpEventLogDto) {
        return coEventDao.insertNmcpEventLog(nmcpEventLogDto);
    }

    @Override
    @Transactional
    public int insertNmcpSurvey(NmcpSurveyDto nmcpSurveyDto) {

        List<NmcpSurveyDto> nmcpSurveyDtoList = nmcpSurveyDto.getNmcpSurveyDtoList();

        int resultCnt = 0;

        try {

            for (NmcpSurveyDto item : nmcpSurveyDtoList) {
                item.setResNo(nmcpSurveyDto.getResNo());
                resultCnt += coEventDao.insertNmcpSurvey(item);
            }

            if (nmcpSurveyDtoList.size() != resultCnt) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        return resultCnt;
    }

    @Override
    public boolean selectNmcpSurvey(String resNo) {

        boolean resultFlag = false;

        int cnt = coEventDao.selectNmcpSurvey(resNo);
        if (cnt > 0) {
            resultFlag = true;
        }

        return resultFlag;
    }

    @Override
    public FrndRecommendDto selectFrndId(FrndRecommendDto frndRecommendDto) {
        if(!hasText(frndRecommendDto.getContractNum()) && !hasText(frndRecommendDto.getCommendId())){
            throw new IllegalArgumentException("조회 조건 필수");
        }
        return coEventDao.selectFrndId(frndRecommendDto);
    }



    @Override
    public FrndRecommendDto selectFrndIdUpdate(FrndRecommendDto frndRecommendDto , FrndRecommendDto frndRecommendPra) {
        FrndRecommendDto getFrndRecommend = coEventDao.selectFrndId(frndRecommendDto);
        if (getFrndRecommend == null) {
            return null ;
        }

        boolean isChanged =
                !Objects.equals(getFrndRecommend.getOpenMthdCd(), frndRecommendPra.getOpenMthdCd())
                        || !Objects.equals(getFrndRecommend.getLinkTypeCd(),  frndRecommendPra.getLinkTypeCd())
                        || !Objects.equals(getFrndRecommend.getCommendSocCode01(),  frndRecommendPra.getCommendSocCode01())
                        || !Objects.equals(getFrndRecommend.getCommendSocCode02(),  frndRecommendPra.getCommendSocCode02())
                        || !Objects.equals(getFrndRecommend.getCommendSocCode03(),  frndRecommendPra.getCommendSocCode03());

        if (isChanged) {
            frndRecommendPra.setContractNum(getFrndRecommend.getContractNum());
            frndRecommendPra.setCommendId(getFrndRecommend.getCommendId());
            frndRecommendPra.setCretIp(ipstatisticService.getClientIp());
            UserSessionDto usd = SessionUtils.getUserCookieBean();
            if (usd != null && !usd.getUserId().equals("")) {
                frndRecommendPra.setCretId(usd.getUserId());
            }
            coEventDao.updateCommendInfo(frndRecommendPra);
            coEventDao.updateFrndHist(frndRecommendPra);
            coEventDao.insertFrndHist(frndRecommendPra);
            return frndRecommendPra ;
        } else {
            return getFrndRecommend ;
        }
    }

    @Override
    public int insertFrndId(FrndRecommendDto frndRecommendDto) {
        frndRecommendDto.setCretIp(ipstatisticService.getClientIp());

        UserSessionDto usd = SessionUtils.getUserCookieBean();
        if (usd != null && !usd.getUserId().equals("")) {
            frndRecommendDto.setCretId(usd.getUserId());
        }
        coEventDao.insertFrndHist(frndRecommendDto);
        return coEventDao.insertFrndId(frndRecommendDto);
    }

    @Override
    public int duplicateChk(String commendId) {
        return coEventDao.duplicateChk(commendId);
    }

    @Override
    public List<EventBoardDto> selectEventBoardList() {
        return coEventDao.selectEventBoardList();
    }

    @Override
    public int countPlanListSelectByDate(EventBoardDto eventBoardDto) {
        return coEventDao.countPlanListSelectByDate(eventBoardDto);
    }

    @Override
    public List<EventBoardDto> planListSelectByDate(EventBoardDto eventBoardDto, int skipResult) {
        return coEventDao.planListSelectByDate(eventBoardDto, skipResult);
    }

    @Override
    public EventBoardDto getEventDetailByDate(EventBoardDto eventBoardDto) {
        return coEventDao.getEventDetailByDate(eventBoardDto);
    }

    @Override
    public String getRandCommendId() {
        StringBuffer buf = null;
        String result = "";

        SecureRandom rnd = new SecureRandom();

        try {

            while(true){
                buf = new StringBuffer();
                try {
                    // 영문 2자리
                    buf.append((char)(rnd.nextInt(26) + 65));
                    buf.append((char)(rnd.nextInt(26) + 65));

                    // 숫자 4자리
                    for(int i=0;i<4;i++){
                        buf.append((rnd.nextInt(10)));
                    }

                    result = buf.toString();

                    int cnt = this.duplicateChk(result);
                    if(cnt == 0){
                        break;
                    }
                }catch (ArithmeticException e) {
                    logger.error("ArithmeticException e : {}", e.getMessage());
                }catch (Exception e) {
                    logger.error("Exception e : {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            return "";
        }

        return result;
    }

}
