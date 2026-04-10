package com.ktmmobile.mcp.fqc.service;


import com.ktmmobile.mcp.common.dao.FCommonDao;
import com.ktmmobile.mcp.common.dto.MoscCombReqDto;
import com.ktmmobile.mcp.common.dto.UserEventTraceDto;
import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.mcp.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.content.service.MyCombinationSvc;
import com.ktmmobile.mcp.event.dto.FrndRecommendDto;
import com.ktmmobile.mcp.event.service.CoEventSvc;
import com.ktmmobile.mcp.fqc.dao.FqcDao;
import com.ktmmobile.mcp.fqc.dto.FqcBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcDltDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBnfDto;
import com.ktmmobile.mcp.mypage.dao.MypageDao;
import com.ktmmobile.mcp.mypage.dto.CommendStateDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.requestReview.dao.RequestReviewDao;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


@Service("fqcSvc")
public class FqcSvcImpl implements FqcSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(FqcSvcImpl.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private FqcDao fqcDao;

    @Autowired
    private MypageDao mypageDao;

    @Autowired
    private FCommonDao fCommonDao;

    @Autowired
    private RequestReviewDao reviewDao ;

    @Autowired
    private CoEventSvc coEventSvc;

    @Autowired
    private IpStatisticService ipstatisticService;


    @Autowired
    MyCombinationSvc myCombinationSvc;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    Map<String, BiConsumer<FqcBasDto, FqcDltDto>> fqcFunctionMap = Map.of(
            "00", (FqcBasDto fqcBas, FqcDltDto fqcDlt) -> applyMissionRestriction(fqcBas, fqcDlt),
            "01", (FqcBasDto fqcBas, FqcDltDto fqcDlt) -> isActivationCompleted(fqcBas, fqcDlt),
            "02", (FqcBasDto fqcBas, FqcDltDto fqcDlt) -> isEventShareCompleted(fqcBas, fqcDlt),
            "03", (FqcBasDto fqcBas, FqcDltDto fqcDlt)-> isMarketingConsentCompleted(fqcBas, fqcDlt),
            "04", (FqcBasDto fqcBas, FqcDltDto fqcDlt)-> isPlanReviewCompleted(fqcBas, fqcDlt),
            "05", (FqcBasDto fqcBas, FqcDltDto fqcDlt)-> isFriendInvitationCompleted(fqcBas, fqcDlt),
            "06", (FqcBasDto fqcBas, FqcDltDto fqcDlt)-> isFriendInvitationCompleted(fqcBas, fqcDlt),
            "07", (FqcBasDto fqcBas, FqcDltDto fqcDlt)-> isTripleDiscountCompleted(fqcBas, fqcDlt)
    );

    /**
     * 미션 참여 불가 정책 등록
     * Duplicated code fragment
     */
    private void applyMissionRestriction(FqcBasDto fqcBas, FqcDltDto fqcDlt) {
        String asStateCode = fqcDlt.getStateCode();  //현재 미션 상태 값..
        String asFqcPlcyMsnCd = fqcDlt.getFqcPlcyMsnCd();
        if (asFqcPlcyMsnCd ==null) {
            asFqcPlcyMsnCd = "";
        }
        fqcDlt.setFqcPlcyMsnCd(fqcBas.getFqcPlcyCd()+fqcDlt.getMsnTpCd() );
        fqcDlt.setStateCode("00");
        fqcDlt.setRegstId(fqcBas.getUserId());
        fqcDlt.setRip(ipstatisticService.getClientIp());

        fqcDlt.setContractNum("");
        fqcDlt.setUetSeq(0);
        fqcDlt.setReviewId(0);
        fqcDlt.setCommendId("");
        fqcDlt.setReContractNum("");

        fqcDlt.setDtlDesc("미션 참여 불가 정책");
        if ("-2".equals(asStateCode)) {
            /** 요금제 변경
             *  기존 DATA 만료 처리
             *  신규 등록 처리
             * **/
            //기존 종료 후 , 다시 insert
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);
            FqcDltDto upFqcDlt = new FqcDltDto ();
            upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
            upFqcDlt.setFqcPlcyMsnCd(asFqcPlcyMsnCd);
            upFqcDlt.setDtlDesc("미션 변경에 따른 만료");
            upFqcDlt.setEndDttm("99991231235959");
            upFqcDlt.setRegstId(fqcBas.getUserId());
            upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
            fqcDao.updateFqcDlt(upFqcDlt);
            fqcDao.insertFqcDlt(fqcDlt)  ;
        } else if ("-3".equals(asStateCode)) {
            /** 대표회선 변경으로 정책이 없는 경우
             *  기존 DATA 만료 처리
             * **/
            //기존 종료 처리
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);
            FqcDltDto upFqcDlt = new FqcDltDto ();
            upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
            upFqcDlt.setFqcPlcyMsnCd(asFqcPlcyMsnCd);
            upFqcDlt.setDtlDesc("정책이 존재 하지 않습니다.");
            upFqcDlt.setEndDttm("99991231235959");
            upFqcDlt.setRegstId(fqcBas.getUserId());
            upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
            fqcDao.updateFqcDlt(upFqcDlt);
        } else if (!asStateCode.equals("00")) {
            fqcDao.insertFqcDlt(fqcDlt)  ;
        }
    }

    /**
     * 개통완료
     */
    private void isActivationCompleted(FqcBasDto fqcBas, FqcDltDto fqcDlt) {

        String asStateCode = fqcDlt.getStateCode();  //현재 미션 상태 값..
        String bypassCheckYn = fqcDlt.getBypassCheckYn();  //Y인 경우 검증 로직을 수행하지 않음
        String asContractNum = fqcDlt.getContractNum() ; //이전 계약번호
        String toContractNum = "";

        if ("00".equals(asStateCode) || "Y".equals(bypassCheckYn) ) {
            /**
             * 00: 불가참여
             */
            return;
        }

        String baseAgntCd = "1100011741" ;

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(fqcBas.getUserId());
        McpUserCntrMngDto userCntrMng = cntrList.stream()
                .filter(cntr -> cntr.getContractNum().equals(fqcBas.getContractNum()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 contractNum을 찾을 수 없습니다."));

        String openAgntCd = userCntrMng.getOpenAgntCd() ; //대리점 코드

        /** 직영 대리점 여부 */
        if (baseAgntCd.equals(openAgntCd)) {
            toContractNum = userCntrMng.getContractNum();
            fqcDlt.setDtlDesc(openAgntCd);
            fqcDlt.setStateCode("02");
            fqcDlt.setContractNum(toContractNum);
        } else {
            fqcDlt.setDtlDesc(openAgntCd);
            fqcDlt.setStateCode("01");
        }
        fqcDlt.setEndDttm("99991231235959");
        fqcDlt.setRip(ipstatisticService.getClientIp());
        fqcDlt.setRegstId(fqcBas.getUserId());

        if (!asStateCode.equals(fqcDlt.getStateCode())) {
            if ("-2".equals(asStateCode) || "01".equals(asStateCode) ) {
                SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
                String sysRdateDd = yyyyMMdd.format(new Date());
                //미션 참여 불가  --> 가능
                fqcDlt.setSysRdateDd(sysRdateDd);
                fqcDlt.setDtlDesc("["+asStateCode+"][-2 가능][01 직영]");
                fqcDao.updateFqcDlt(fqcDlt);
            } else {
                //신규
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        } else if ("02".equals(fqcDlt.getStateCode())) {
            /** 대표회선 변경으로 으로  계약번호가 변경 될경우
             *  계약번호가 동일한 정책인 경우
             * */
            if (!"".equals(asContractNum) && !asContractNum.equals(toContractNum)) {
                SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);
                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setDtlDesc("계약 번호 변경 처리");
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
                fqcDao.updateFqcDlt(upFqcDlt);
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        }
    }

    /**
     * 이벤트 공유 ..
     */
    private void isEventShareCompleted(FqcBasDto fqcBas, FqcDltDto fqcDlt) {
        String asStateCode = fqcDlt.getStateCode();  //현재 미션 상태 값..
        String bypassCheckYn = fqcDlt.getBypassCheckYn();  //Y인 경우 검증 로직을 수행하지 않음

        if ("00".equals(asStateCode)  || "Y".equals(bypassCheckYn)) {
            /**
             * 00: 불가참여
             */
            return;
        }

        //공통코드 이벤트
        NmcpCdDtlDto eventVo = NmcpServiceUtils.getCodeNmDto("Constant", "fqcEventSeq");


        //카카오 공유 이력 확인
        String strPlcyStrtDttm = fqcBas.getFqcPlcyBas().getStrtDttm(); // 시작일 기준
        UserEventTraceDto userEventTrace = new UserEventTraceDto();
        userEventTrace.setRegstId(fqcBas.getUserId());
        userEventTrace.setPrcsMdlMain("SNS_SHARE");
        userEventTrace.setPrcsMdlMid("KAKAO");
        userEventTrace.setStrRegstDttm(strPlcyStrtDttm); // 시작일 기준
        if (eventVo != null && !StringUtils.isEmpty(eventVo.getDtlCdNm()) ) {
            userEventTrace.setTrtmRsltSmst(eventVo.getDtlCdNm()); // 특정 이벤트 ....
        }
        List<UserEventTraceDto> userEventTraceList = fCommonDao.getUserEventTraceList(userEventTrace);

        fqcDlt.setEndDttm("99991231235959");
        fqcDlt.setRip(ipstatisticService.getClientIp());
        fqcDlt.setRegstId(fqcBas.getUserId());
        fqcDlt.setDtlDesc("");  //기존 DATA 초기화

        if (userEventTraceList == null || userEventTraceList.size() < 1) {
            fqcDlt.setStateCode("01");
            fqcDlt.setDtlDesc("카카오 공유 이력 없음");
        } else {
            fqcDlt.setStateCode("02");
            fqcDlt.setUetSeq(userEventTraceList.get(0).getUetSeq());
        }

        if (!asStateCode.equals(fqcDlt.getStateCode())) {
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
            String sysRdateDd = yyyyMMdd.format(new Date());
            if ("01".equals(asStateCode)) {
                //참여 ==> 02 완료...
                fqcDlt.setDtlDesc("참여에서 완료");
                fqcDlt.setSysRdateDd(sysRdateDd);
                fqcDao.updateFqcDlt(fqcDlt);
            } else if ("02".equals(asStateCode)) {
                //기존 종료 후 , 다시 insert
                SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setDtlDesc("누수가 있음.. ");
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
                fqcDao.updateFqcDlt(upFqcDlt);
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else if ("-2".equals(asStateCode)) {
                //미션 참여 불가  --> 가능
                if ("01".equals(fqcDlt.getStateCode())) {
                    fqcDlt.setDtlDesc("[불가][선택]");
                } else {
                    fqcDlt.setSysRdateDd(sysRdateDd);
                    fqcDlt.setDtlDesc("[불가][완료]");
                }
                fqcDao.updateFqcDlt(fqcDlt);
            }  else {
                //신규
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        }
    }

    /**
     * 마켓팅 수신 동의  확인 ..
     */
    private void isMarketingConsentCompleted(FqcBasDto fqcBas, FqcDltDto fqcDlt) {

        String asStateCode = fqcDlt.getStateCode(); //현재 미션 상태 값..
        String bypassCheckYn = fqcDlt.getBypassCheckYn();  //Y인 경우 검증 로직을 수행하지 않음
        if ("00".equals(asStateCode) || "Y".equals(bypassCheckYn) ) {
            //00: 불가참여
            return;
        }


        //마켓팅 수신 동의 정보 확인
        McpMrktHistDto mrktHistPara = new McpMrktHistDto();
        mrktHistPara.setUserid(fqcBas.getUserId());
        mrktHistPara.setEndDttm("99991231235959");
        mrktHistPara.setAgrYn("Y");
        mrktHistPara.setGubun("04");  //04  정보/광고 수신 동의

        fqcDlt.setEndDttm("99991231235959");
        fqcDlt.setRip(ipstatisticService.getClientIp());
        fqcDlt.setRegstId(fqcBas.getUserId());
        fqcDlt.setDtlDesc("");  //기존 DATA 초기화
        mrktHistPara = mypageDao.getMrktHistInfo(mrktHistPara);

        if ( mrktHistPara != null &&   !"".equals(mrktHistPara.getStrtDttm()) ) {
            String strtDttm = mrktHistPara.getStrtDttm();
            LOGGER.info("strtDttm===" + strtDttm);
            //MRK_STRT_DTTM
            fqcDlt.setStateCode("02");
            fqcDlt.setMrkStrtDttm(strtDttm);
        } else {
            fqcDlt.setStateCode("01");
            fqcDlt.setDtlDesc("동의한 정보 없음");
        }

        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
        String sysRdateDd = yyyyMMdd.format(new Date());

        if (!asStateCode.equals(fqcDlt.getStateCode())) {
            if ("01".equals(asStateCode)) {
                //참여 ==> 02 완료...
                fqcDlt.setDtlDesc("참여에서 완료");
                fqcDlt.setSysRdateDd(sysRdateDd);
                fqcDao.updateFqcDlt(fqcDlt);

            } else if ("02".equals(asStateCode)) {
                //기존 종료 후 , 다시 insert
                SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setDtlDesc("미동의 변경으로 만료");
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
                fqcDao.updateFqcDlt(upFqcDlt);
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else if ("-2".equals(asStateCode)) {
                //미션 참여 불가  --> 가능
                if ("01".equals(fqcDlt.getStateCode())) {
                    fqcDlt.setDtlDesc("[불가][선택]");
                } else {
                    fqcDlt.setSysRdateDd(sysRdateDd);
                    fqcDlt.setDtlDesc("[불가][완료]");
                }
                fqcDao.updateFqcDlt(fqcDlt);
            } else {
                //신규
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        }

    }

    /**
     * 요금제 리뷰작성
     */
    private void isPlanReviewCompleted(FqcBasDto fqcBas, FqcDltDto fqcDlt) {
        String asStateCode = fqcDlt.getStateCode();  //현재 미션 상태 값..
        String bypassCheckYn = fqcDlt.getBypassCheckYn();  //Y인 경우 검증 로직을 수행하지 않음
        if ("00".equals(asStateCode) || "Y".equals(bypassCheckYn) ) {
            /**
             * 00: 불가참여
             */
            return;
        }

        if (fqcBas == null || fqcBas.getFqcPlcyBas() == null ||
                fqcBas.getFqcPlcyBas().getStrtDttm() == null || fqcBas.getContractNum() == null) {
            throw new IllegalArgumentException("Required fields are missing.");
        }

        String strPlcyStrtDttm = fqcBas.getFqcPlcyBas().getStrtDttm(); // 시작일 기준
        String contractNum = fqcBas.getContractNum() ;
        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setContractNum(contractNum);
        requestReviewDto.setStrtDttm(strPlcyStrtDttm);  //기준일 이후

        List<RequestReviewDto> reviewList = reviewDao.selectReviewList(requestReviewDto);

        fqcDlt.setEndDttm("99991231235959");
        fqcDlt.setRip(ipstatisticService.getClientIp());
        fqcDlt.setRegstId(fqcBas.getUserId());
        fqcDlt.setDtlDesc("");


        if (reviewList != null && 0 < reviewList.size()) {
            fqcDlt.setStateCode("02");
            fqcDlt.setReviewId(reviewList.get(0).getReviewId());
        } else {
            fqcDlt.setStateCode("01");
            fqcDlt.setReviewId(0);
            fqcDlt.setDtlDesc("기간에["+strPlcyStrtDttm+"] 리뷰 작성 없음");
        }

        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
        String sysRdateDd = yyyyMMdd.format(new Date());

        if (!asStateCode.equals(fqcDlt.getStateCode())) {
            if ("01".equals(asStateCode)) {
                //참여 ==> 02 완료...
                fqcDlt.setDtlDesc("참여에서 완료");
                fqcDlt.setSysRdateDd(sysRdateDd);
                fqcDao.updateFqcDlt(fqcDlt);

            } else if ("02".equals(asStateCode)) {
                //기존 종료 후 , 다시 insert
                SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setDtlDesc("다시 종료 처리 ");
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
                fqcDao.updateFqcDlt(upFqcDlt);
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else if ("-2".equals(asStateCode)) {
                //미션 참여 불가  --> 가능
                if ("01".equals(fqcDlt.getStateCode())) {
                    fqcDlt.setDtlDesc("[불가][선택]");
                } else {
                    fqcDlt.setSysRdateDd(sysRdateDd);
                    fqcDlt.setDtlDesc("[불가][완료]");
                }
                fqcDao.updateFqcDlt(fqcDlt);
            } else {
                //신규
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        }
    }


    /**
     * 1명친구초대
     */
    private void isFriendInvitationCompleted(FqcBasDto fqcBas, FqcDltDto fqcDlt) {

        String asStateCode = fqcDlt.getStateCode();  //현재 미션 상태 값..
        String bypassCheckYn = fqcDlt.getBypassCheckYn();  //Y인 경우 검증 로직을 수행하지 않음
        if ("00".equals(asStateCode) || "Y".equals(bypassCheckYn) ) {
            /**
             * 00: 불가참여
             */
            return;
        }

        String contractNum = fqcBas.getContractNum() ;
        FrndRecommendDto frndRecommendDto = new FrndRecommendDto();
        frndRecommendDto.setContractNum(contractNum);

        //추천 아이디 확인
        FrndRecommendDto resDto = coEventSvc.selectFrndId(frndRecommendDto);
        String commendId = "";
        if( resDto != null ){ // 기존
            commendId  = resDto.getCommendId();
        }


        String strPlcyStrtDttm = fqcBas.getFqcPlcyBas().getStrtDttm(); // 시작일 기준
        String fqcPlcyCd = fqcBas.getFqcPlcyCd();  //정책 코드
        LOGGER.info("fqcPlcyCd   ===> " + fqcPlcyCd );
        //개통 정보
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("commendId", commendId);
        params.put("lstComActvDate", strPlcyStrtDttm);

        CommendStateDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/getFriendInvitationList", params, CommendStateDto[].class);
        List<CommendStateDto> commendStateList = Arrays.asList(resultList);

        fqcDlt.setRip(ipstatisticService.getClientIp());
        fqcDlt.setRegstId(fqcBas.getUserId());
        fqcDlt.setDtlDesc("");

        boolean hasMatch = true;

        if (commendStateList != null && 0 < commendStateList.size()) {
           /** 또다른 친무 초대 값 확인
             * 05 1명친구초대
             * 06 1명친구초대
             * */
            String msnTpCd = "05".equals(fqcDlt.getMsnTpCd()) ? "06":"05" ;
            String reContractNum  ;
            String orgReContractNum = fqcDlt.getReContractNum(); //기존 정보

            // 다른 친구 초대 확인
            FqcDltDto fqcDltNe = new FqcDltDto();
            fqcDltNe.setEndDttm("99991231235959");
            fqcDltNe.setFqcSeq(fqcDlt.getFqcSeq());
            fqcDltNe.setMsnTpCd(msnTpCd);

            List<FqcDltDto> fqcDltDBList = fqcDao.getFqcDltList(fqcDltNe);

            if (fqcDltDBList != null && fqcDltDBList.size() > 0) {
                reContractNum =fqcDltDBList.get(0).getReContractNum();
            } else {
                reContractNum = "-1";
            }

            //기존에 있는 것 제외 처리
            List<CommendStateDto> commendStateFilterList = commendStateList.stream().filter(commendState-> !commendState.getContractNum().equals(reContractNum)).collect(Collectors.toList());

            String insertReContractNum = "";
            if (commendStateFilterList !=null && commendStateFilterList.size() > 0 ) {
                if ("".equals(orgReContractNum)) {
                    insertReContractNum = commendStateFilterList.get(0).getContractNum();
                } else {
                    hasMatch = false;
                    //기존 코드 존재 여부 ???
                    hasMatch = commendStateFilterList.stream().anyMatch(commendState -> orgReContractNum.equals(commendState.getContractNum()));
                    if (hasMatch) {
                        insertReContractNum = orgReContractNum;
                    } else {
                        insertReContractNum = commendStateFilterList.get(0).getContractNum();
                    }
                }
                fqcDlt.setStateCode("02");
                fqcDlt.setCommendId(commendId);
                fqcDlt.setReContractNum(insertReContractNum);  // 가장 최근에 개통 건에 대해서..
            } else {
                fqcDlt.setStateCode("01");
                fqcDlt.setCommendId("");
                fqcDlt.setReContractNum("");
                fqcDlt.setDtlDesc("기간에["+strPlcyStrtDttm+"] 추가 1명친구초대없음");
            }
        } else {
            fqcDlt.setStateCode("01");
            fqcDlt.setCommendId("");
            fqcDlt.setReContractNum("");
            fqcDlt.setDtlDesc("기간["+strPlcyStrtDttm+"] 1명친구초대없음");
        }

        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
        String sysRdateDd = yyyyMMdd.format(new Date());

        if (!asStateCode.equals(fqcDlt.getStateCode())) {
            if ("01".equals(asStateCode)) {
                //참여 ==> 02 완료...
                fqcDlt.setDtlDesc("참여에서 완료");
                fqcDlt.setSysRdateDd(sysRdateDd);
                fqcDao.updateFqcDlt(fqcDlt);
            } else if ("02".equals(asStateCode)) {
                //기존 종료 후 , 다시 insert
                SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setDtlDesc("RE_CONTRACT_NUM 정보 없음 ");
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
                fqcDao.updateFqcDlt(upFqcDlt);
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else if ("-2".equals(asStateCode)) {
                //미션 참여 불가  --> 가능
                if ("01".equals(fqcDlt.getStateCode())) {
                    fqcDlt.setDtlDesc("[불가][선택]");
                } else {
                    fqcDlt.setSysRdateDd(sysRdateDd);
                    fqcDlt.setDtlDesc("[불가][완료]");
                }
                fqcDao.updateFqcDlt(fqcDlt);
            } else {
                //신규
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        } else if (!hasMatch) {
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);
            //기존 코드 존재 값이 없을 경우
            FqcDltDto upFqcDlt = new FqcDltDto ();
            upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
            upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
            upFqcDlt.setDtlDesc("RE_CONTRACT_NUM 정보 없음");
            upFqcDlt.setEndDttm("99991231235959");
            upFqcDlt.setRegstId(fqcBas.getUserId());
            upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
            fqcDao.updateFqcDlt(upFqcDlt);

            fqcDao.insertFqcDlt(fqcDlt)  ;
        }



    }

    /**
     * 트리플할인
     */
    private void isTripleDiscountCompleted(FqcBasDto fqcBas, FqcDltDto fqcDlt) {
        String asStateCode = fqcDlt.getStateCode();  //현재 미션 상태 값..
        String contractNum = fqcBas.getContractNum() ;
        String custId = fqcBas.getCustId() ;
        String ctn = fqcBas.getCtn() ;
        String bypassCheckYn = fqcDlt.getBypassCheckYn();  //Y인 경우 검증 로직을 수행하지 않음

        if ("00".equals(asStateCode) || StringUtils.isBlank(contractNum) || "Y".equals(bypassCheckYn)    ) {
            /**
             * 00: 불가참여
             */
            return;
        }

        //LOGGER.info("[WOO][WOO][WOO]custId==> " + custId);
        //LOGGER.info("[WOO][WOO][WOO]ctn==> " + ctn);

        String prcsMdlInd = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        fqcDlt.setRip(ipstatisticService.getClientIp());
        fqcDlt.setRegstId(fqcBas.getUserId());
        fqcDlt.setDtlDesc("");

        // MP연동 X77
        // 각각 결합여부 확인
        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(custId);
        moscCombReqDto.setNcn(contractNum);
        moscCombReqDto.setCtn(ctn);
        moscCombReqDto.setCombSvcNoCd("M");  //결합 모회선 사업자 구분 코드	1	O	M: MVNO회선, K:KT 회선
        moscCombReqDto.setCombSrchId(ctn); // 결합 모회선 조회값	10	O	"MVNO회선은 전화번호        KT 회선은 이름"
        moscCombReqDto.setSameCustKtRetvYn("Y"); // 동일명의 KT회선 조회여부 = 'Y'로 조회 시 미동의 회선의 정보 응답

        MoscCombInfoResDTO moscCombInfoResDTO = myCombinationSvc.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd,contractNum);
        String internetId = "";


        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess() && moscCombInfoResDTO.getMoscSrchCombInfoList() != null && moscCombInfoResDTO.getMoscSrchCombInfoList().size() > 0 ) {
            List<MoscMvnoComInfo> moscSrchCombInfoList = moscCombInfoResDTO.getMoscSrchCombInfoList();
            for(MoscMvnoComInfo item : moscSrchCombInfoList){
                String svcDivCd = item.getSvcDivCd();
                String corrNm = item.getCorrNm();
                /** 결합대상 회선 조회(X77)
                 * svcDivCd 서비스 종류 : 인터넷
                 * corrNm 모집경로  :  MVNOKIS
                 * svcDivCd:인터넷,svcNo:z!63213713416,svcContOpnDt:20241129,corrNm:MVNOKIS
                 * */
                if ("인터넷".equals(svcDivCd) && "MVNOKIS".equals(corrNm)){
                    internetId = item.getSvcNo();
                    break;
                }
            }

            if (!StringUtils.isBlank(internetId) )  {

                /** 인터넷 회선 상태 확인 (MDS_INET_INFO)
                 * 최근 정보에 대한 기준 : REG_DTTM
                 * 상태값  [사용중] :  STATUS
                 * OPEN_DT : KT유선 설치완료일자
                 * */

                String strPlcyStrtDttm = fqcBas.getFqcPlcyBas().getStrtDttm(); // 시작일 기준
                //개통 정보
                RestTemplate restTemplate = new RestTemplate();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("internetId", internetId);
                params.put("openDt", strPlcyStrtDttm);  // 기준일 이후 설치 완료
                String status = restTemplate.postForObject(apiInterfaceServer + "/msp/getInetInfo", params, String.class);

                if ("Y".equals(status)) {
                    fqcDlt.setStateCode("02");
                    fqcDlt.setInternetId(internetId);
                    fqcDlt.setDtlDesc("완료");
                } else {
                    fqcDlt.setStateCode("01");
                    fqcDlt.setInternetId(internetId);
                    fqcDlt.setDtlDesc("기간["+strPlcyStrtDttm+"]["+internetId+"]회선 없음" );
                }
            } else {
                fqcDlt.setStateCode("01");
                fqcDlt.setInternetId("");
                fqcDlt.setDtlDesc("X77 inernetid  없음");
            }
        } else if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess() ) {
            // 결합대상회선정보 가 없음 .... moscCombInfoResDTO.getMoscSrchCombInfoList() 존재 하지 않을때...
            fqcDlt.setStateCode("01");
            fqcDlt.setInternetId("");
            fqcDlt.setDtlDesc("X77 결합대상회선정보 없음");
        } else if (moscCombInfoResDTO != null && !moscCombInfoResDTO.isSuccess() ) {

            if ("-1".equals(asStateCode)) {
                //값이 없음 첫음
                fqcDlt.setStateCode("01");
                fqcDlt.setInternetId("");
                fqcDlt.setDtlDesc("[X77]실패[" +moscCombInfoResDTO.getGlobalNo()+ "]");
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else {
                // X77 연동 실패 아무것도 하지 않는다..
                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setEndDttm("99991231235959");
                fqcDlt.setDtlDesc("[X77]실패[" +moscCombInfoResDTO.getGlobalNo()+ "]");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                fqcDao.updateFqcDlt(upFqcDlt);
            }
            return;
        } else {
            if ("-1".equals(asStateCode)) {
                //값이 없음 첫음
                fqcDlt.setStateCode("01");
                fqcDlt.setInternetId("");
                fqcDlt.setDtlDesc("[X77]연동 실패");
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else {
                // X77 연동 실패 아무것도 하지 않는다..
                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setDtlDesc("[X77]연동 실패");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                fqcDao.updateFqcDlt(upFqcDlt);
            }
            return;
        }

        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
        String sysRdateDd = yyyyMMdd.format(new Date());

        if (!asStateCode.equals(fqcDlt.getStateCode())) {
            if ("01".equals(asStateCode)) {
                //참여 ==> 02 완료...
                fqcDao.updateFqcDlt(fqcDlt);
                fqcDlt.setSysRdateDd(sysRdateDd);

            } else if ("02".equals(asStateCode)) {
                //기존 종료 후 , 다시 insert
                SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

                FqcDltDto upFqcDlt = new FqcDltDto ();
                upFqcDlt.setFqcSeq(fqcDlt.getFqcSeq());
                upFqcDlt.setFqcPlcyMsnCd(fqcDlt.getFqcPlcyMsnCd());
                upFqcDlt.setEndDttm("99991231235959");
                upFqcDlt.setRegstId(fqcBas.getUserId());
                upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
                fqcDao.updateFqcDlt(upFqcDlt);
                fqcDao.insertFqcDlt(fqcDlt)  ;
            } else if ("-2".equals(asStateCode)) {
                //미션 참여 불가  --> 가능
                if ("01".equals(fqcDlt.getStateCode())) {
                    fqcDlt.setDtlDesc("[불가][선택]");
                } else {
                    fqcDlt.setSysRdateDd(sysRdateDd);
                    fqcDlt.setDtlDesc("[불가][완료]");
                }
                fqcDao.updateFqcDlt(fqcDlt);
            } else {
                //신규
                fqcDao.insertFqcDlt(fqcDlt)  ;
            }
        }
    }

    @Override
    public List<FqcPlcyBasDto> getFqcPlcyBasList() {
        return fqcDao.getFqcPlcyBasList();
    }


    @Override
    @Transactional
    public FqcBasDto syncParticipantMissions(FqcBasDto fqcBasParm) {

        //1. 사용자 프리퀀시 참여 조회
        FqcBasDto fqcBasDb = fqcDao.getFqcBas(fqcBasParm);
        FqcPlcyBasDto fqcPlcyBasOne = null;


        if (fqcBasDb != null) {
            Date rvisnDttm = fqcBasDb.getRvisnDttm();
            Date now = new Date();
            long diffMillis = now.getTime() - rvisnDttm.getTime();
            if (diffMillis < 30 * 1000) { // 1분 = 30초 = 30 * 1000 밀리초
                //1분 이내  정보 있음
                LOGGER.info("30초 이내 정보 있음~!");
                //기존 정책 정보를 조회 한다.
                FqcPlcyBasDto fqcPlcyBas= fqcDao.getFqcPlcyBas(fqcBasDb.getFqcPlcyCd());
                if (fqcPlcyBas != null) {
                    /** X77 호출 하기 위한 로그인 정보 설정  */
                    fqcBasDb.setCustId(fqcBasParm.getCustId());
                    fqcBasDb.setCtn(fqcBasParm.getCtn());
                    fqcBasDb.setContractNum(fqcBasParm.getContractNum());
                    fqcBasDb.setFqcPlcyBas(fqcPlcyBas);
                    return fqcBasDb;
                }
            }
        }


        //2. 현재 포리퀀시 정책 조회
        List<FqcPlcyBasDto> fqcPlcyBasList =  this.getFqcPlcyBasList();
        if (fqcPlcyBasList == null || fqcPlcyBasList.size() < 1) {
            return null;
        }

        if ("".equals(fqcBasParm.getContractNum())) {
            //준회원 정책
            fqcPlcyBasOne = fqcPlcyBasList.stream()
                    .filter(fqcPlcyBas -> "0".equals(fqcPlcyBas.getFqcTrgtCust()))
                    .findFirst() // 첫 번째 항목 선택
                    .orElse(null); // 없으면 null 반환
        } else {
            //정회원 정책
            //기준점 조회
            FqcPlcyBasDto fqcPlcyBasBase = fqcPlcyBasList.get(0); // 신규 고객 , 기존 고객  비교 대상
            String strDttm = fqcPlcyBasBase.getStrtDttm(); // 시작일 기준


            //신규 고객 , 기존 고객  처리
            /*
              strDttm 정책에 시작일이 크다 //기존 회원 1
              strDttm 정책에 시작일이 작다 //신규 회원 2
             */
            final String finalFqcTrgtCust = (strDttm.compareTo(fqcBasParm.getOpenDate()) > 0) ? "1" : "2";
            // fqcTrgtCust 값이 일치하는 정책 필터링
            List<FqcPlcyBasDto> filteredList = fqcPlcyBasList.stream()
                    .filter(fqcPlcyBas -> finalFqcTrgtCust.equals(fqcPlcyBas.getFqcTrgtCust()))
                    .collect(Collectors.toList());

            // 조건에 맞는 첫 번째 정책 찾기
            // 요금제 있는 정책
            // "Y" 조건이 맞는 첫 번째 항목을 찾고, 없으면 "N" 조건이 맞는 첫 번째 항목을 찾음
            fqcPlcyBasOne = filteredList.stream()
                    .filter(tempPlcyBay -> "Y".equals(tempPlcyBay.getFqcPlanInclYn()) &&
                            fqcDao.isFqcPlcyPlaCount(tempPlcyBay.getFqcPlcyCd(), fqcBasParm.getSocCode()) > 0)
                    .findFirst() // 첫 번째 조건 만족하는 항목 선택
                    .or(() -> filteredList.stream()
                            .filter(tempPlcyBay -> "N".equals(tempPlcyBay.getFqcPlanInclYn()))
                            .findFirst()) // 없으면 "N" 조건 만족하는 첫 번째 항목 선택
                    .orElse(null);
        }

        if (fqcPlcyBasOne == null) {
            if (fqcBasDb != null) {
                //4. 각각 미션  검증및 UPDATE 처리
                List<FqcDltDto> fqcDltList = fqcBasDb.getFqcDltDtoList();
                if (fqcDltList != null) {
                    for (FqcDltDto fqcDlt : fqcDltList) {
                        String msnTpcd = fqcDlt.getMsnTpCd();
                        //미션 참여 가능  --> 불가
                        fqcDlt.setStateCode("-3");
                        msnTpcd = "00";  // 미션 참여 불가 정책

                        if (fqcFunctionMap.containsKey(msnTpcd) ) {
                            fqcFunctionMap.get(msnTpcd).accept(fqcBasDb, fqcDlt);
                        } else {
                            throw new IllegalArgumentException("Invalid key: " + msnTpcd);
                        }
                    }
                }
            }
            return null;
        }

        //사용자 정책이 없다...  프리퀀시 참여 기본 등록 처리
        if (fqcBasDb == null) {
            fqcBasDb = new FqcBasDto();
            fqcBasDb.setFqcPlcyCd(fqcPlcyBasOne.getFqcPlcyCd());
            fqcBasDb.setUserId(fqcBasParm.getUserId());
            fqcBasDb.setRip(ipstatisticService.getClientIp());
            fqcBasDb.setFqcPlcyBas(fqcPlcyBasOne);
            fqcDao.insertFqcBas(fqcBasDb);
        }

        //계약번호를 저장해야  개통 여부 확인 가능
        fqcBasDb.setContractNum(fqcBasParm.getContractNum());
        fqcBasDb.setCustId(fqcBasParm.getCustId());
        /** X77 호출 하기 위한 로그인 정보 설정  */
        fqcBasDb.setCtn(fqcBasParm.getCtn());
        fqcBasDb.setFqcPlcyBas(fqcPlcyBasOne);

        //3. 기존 프리퀀시 정책 현재 정책 비교 변경 처리
        if (fqcBasDb.getFqcPlcyCd().equals(fqcPlcyBasOne.getFqcPlcyCd())) {
            //4. 각각 미션  검증및 UPDATE 처리
            List<FqcDltDto> fqcDltList = fqcBasDb.getFqcDltDtoList();
            if (fqcDltList != null) {
                for (FqcDltDto fqcDlt : fqcDltList) {
                    String msnTpcd = fqcDlt.getMsnTpCd();
                    /**
                     * 미션이 변경이 될 경우 미션이 정보 변경에 따른... 처리
                     */
                    String fqcPlcyMsnCd =  fqcDao.getPlcyMsnCd(fqcPlcyBasOne.getFqcPlcyCd(), msnTpcd);
                    if (fqcPlcyMsnCd == null || "".equals(fqcPlcyMsnCd) ) {
                        fqcPlcyMsnCd = "00";  // 미션 참여 불가 정책
                    }

                    if ("00".equals(fqcDlt.getStateCode()) && !"00".equals(fqcPlcyMsnCd) ) {
                        // 미션 참여 불가 --> 가능
                        fqcDlt.setStateCode("-2");
                        fqcDlt.setFqcPlcyMsnCdUp(fqcPlcyMsnCd);
                    } else if (!"00".equals(fqcDlt.getStateCode()) && "00".equals(fqcPlcyMsnCd) ) {
                        //미션 참여 가능  --> 불가
                        fqcDlt.setStateCode("-2");
                        msnTpcd = "00";  // 미션 참여 불가 정책
                    }

                    if (fqcFunctionMap.containsKey(msnTpcd) ) {
                        fqcFunctionMap.get(msnTpcd).accept(fqcBasDb, fqcDlt);
                    } else {
                        throw new IllegalArgumentException("Invalid key: " + msnTpcd);
                    }
                }
            }

            /** 최종 변경일 UPDATE 처리  **/
            fqcBasDb.setRegstId(fqcBasDb.getUserId());
            fqcDao.updateFqcBas(fqcBasDb);

        } else {
            /**
             * 1. 준회원에서 정회원으로
             * 2. 정회원이 회선 정지????
             * 3. 요금제 변경에 따른 정책 변경
             * 정책이 상이
             * **/
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

            /** 기존  프리퀀시 정보 종료 처리 */
            fqcBasDb.setFqcDesc("정책 변경에 따른 만료");
            fqcBasDb.setEndDttm(yyyyMMddHHmmss.format(new Date()));
            fqcBasDb.setRegstId(fqcBasDb.getUserId());
            fqcDao.updateFqcBas(fqcBasDb);

            FqcDltDto upFqcDlt = new FqcDltDto ();
            upFqcDlt.setFqcSeq(fqcBasDb.getFqcSeq());
            upFqcDlt.setDtlDesc("정책 변경에 따른 만료");
            upFqcDlt.setEndDttm("99991231235959");
            upFqcDlt.setRegstId(fqcBasDb.getUserId());
            upFqcDlt.setUpEndDttm(yyyyMMddHHmmss.format(new Date()));
            fqcDao.updateFqcDlt(upFqcDlt);

            /**신규 정책 등록 */
            FqcBasDto fqcBasDbNe = new FqcBasDto();
            fqcBasDbNe.setFqcPlcyCd(fqcPlcyBasOne.getFqcPlcyCd());
            fqcBasDbNe.setUserId(fqcBasParm.getUserId());
            fqcBasDbNe.setRip(ipstatisticService.getClientIp());
            fqcBasDbNe.setContractNum(fqcBasParm.getContractNum());
            fqcBasDbNe.setFqcPlcyBas(fqcPlcyBasOne);
            fqcDao.insertFqcBas(fqcBasDbNe);

            /**각각 미션 신규 정책 등록 */
            List<FqcDltDto> fqcDltList = fqcBasDb.getFqcDltDtoList();
            if (fqcDltList != null) {
                for (FqcDltDto fqcDlt : fqcDltList) {
                    fqcDlt.setFqcSeq(fqcBasDbNe.getFqcSeq()); //신규 참여 일련번호
                    String msnTpcd = fqcDlt.getMsnTpCd();

                    //1. 현재 정책에 권한이 있냐?
                    String fqcPlcyMsnCd =  fqcDao.getPlcyMsnCd(fqcBasDbNe.getFqcPlcyCd(), msnTpcd);
                    fqcDlt.setStateCode("-1");
                    if (fqcPlcyMsnCd == null || "".equals(fqcPlcyMsnCd) ) {
                        msnTpcd = "00";  // 미션 참여 불가 정책
                    } else {
                        fqcDlt.setFqcPlcyMsnCd(fqcPlcyMsnCd);
                    }

                    if (fqcFunctionMap.containsKey(msnTpcd) ) {
                        fqcFunctionMap.get(msnTpcd).accept(fqcBasDbNe, fqcDlt);
                    } else {
                        throw new IllegalArgumentException("Invalid key: " + msnTpcd);
                    }
                }
            }
            /** 다시 등록한 미션 정보를  */
            fqcBasDb = fqcDao.getFqcBas(fqcBasParm);
            /** X77 호출 하기 위한 로그인 정보 설정  */
            fqcBasDb.setCtn(fqcBasParm.getCtn());
            fqcBasDb.setFqcPlcyBas(fqcPlcyBasOne);
            fqcBasDb.setContractNum(fqcBasParm.getContractNum());

        }

        //6. 공통 코드에 MAP 형식으로 정보 치환 처리
        return fqcBasDb;
    }

    @Override
    public boolean setFqcDlt(FqcBasDto fqcBas, FqcDltDto fqcDlt){
        //1. 현재 정책에 권한이 있냐?
        String fqcPlcyMsnCd =  fqcDao.getPlcyMsnCd(fqcBas.getFqcPlcyCd(), fqcDlt.getMsnTpCd());

        //현재 프리퀀시 참여 상세 조외
        fqcDlt.setEndDttm("99991231235959");

        List<FqcDltDto> fqcDltDBList = fqcDao.getFqcDltList(fqcDlt);
        String asStateCode = "";  //현재 미션 상태 값..

        if (fqcDltDBList == null || fqcDltDBList.size() < 1) {
            asStateCode = "-1";
        } else {
            asStateCode = fqcDltDBList.get(0).getStateCode();
        }


        String msnTpcd = "";
        fqcDlt.setStateCode(asStateCode);  //이전 상태값
        if (fqcPlcyMsnCd == null || "".equals(fqcPlcyMsnCd) ) {
            msnTpcd = "00";  // 미션 참여 불가 정책
        } else {
            msnTpcd = fqcDlt.getMsnTpCd();
            fqcDlt.setFqcPlcyMsnCd(fqcPlcyMsnCd);
        }

        if (fqcFunctionMap.containsKey(msnTpcd)) {
            fqcFunctionMap.get(msnTpcd).accept(fqcBas,fqcDlt);
        } else {
            throw new IllegalArgumentException("Invalid key: " + msnTpcd);
        }
        return true;
    }


    @Override
    public List<FqcPlcyBnfDto>  getFqcPlcyBnfList(String fqcPlcyCd) {
        return fqcDao.getFqcPlcyBnfList(fqcPlcyCd);
    }

}
