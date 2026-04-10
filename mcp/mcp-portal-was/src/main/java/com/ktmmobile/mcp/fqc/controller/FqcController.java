package com.ktmmobile.mcp.fqc.controller;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.fqc.dto.FqcBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcDltDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBasDto;
import com.ktmmobile.mcp.fqc.dto.FqcPlcyBnfDto;
import com.ktmmobile.mcp.fqc.service.FqcSvc;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.GROUP_CODE_SERVICE_GUIDE_CTG;
import static com.ktmmobile.mcp.common.constants.Constants.REGULAR_MEMBER;

@Controller
public class FqcController {
    private static final Logger logger = LoggerFactory.getLogger(FqcController.class);

    @Autowired
    private FqcSvc fqcSvc;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    IpStatisticService ipstatisticService;


    /**
     * 설명 : 프리퀀시 이벤트 표현
     *
     * @Author : papier
     * @Date : 2025.03.25
     */
    @RequestMapping(value = {"/fqc/fqcEventView.do","/m/fqc/fqcEventView.do"})
    public String fqcEventView(HttpServletRequest request,Model model) {
        String loginUrl = "/loginForm.do?uri=/fqc/fqcEventView.do";
        String pageJsp = "/portal/fqc/fqcEventView";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            pageJsp = "/mobile/fqc/fqcEventView";
            loginUrl = "/m/loginForm.do?uri=/fqc/fqcEventView.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            return "redirect:" + loginUrl;
        }

        String userName = userSession.getName();

        // 7. 마스킹 세션 확인
        String maskingSession = SessionUtils.getMaskingSession() > 0  ? "Y" : "";
        if ("Y".equals(maskingSession)) {
             // 이력 insert
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        } else {
            // 마스킹 처리되어야 하는 데이터 세팅
            userName = StringMakerUtil.getName(userName);
        }

        model.addAttribute("userName", userName);
        return pageJsp;
    }


    /**
     * 설명 : 프리퀀시 사용자 진행 확인
     *
     * @Author : papier
     * @Date : 2025.03.25
     */
    @RequestMapping(value = "/m/fqc/fqcPopupView.do")
    public String fqcPopupView(HttpServletRequest request,Model model) {
        String loginUrl = "/loginForm.do?uri=/fqc/fqcEventView.do";
        String pageJsp = "/mobile/fqc/fqcPopupView";


        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonException(ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION);
        }

        String userName = userSession.getName();

        // 7. 마스킹 세션 확인
        String maskingSession = SessionUtils.getMaskingSession() > 0  ? "Y" : "";
        if ("Y".equals(maskingSession)) {
            // 이력 insert
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        } else {
            // 마스킹 처리되어야 하는 데이터 세팅
            userName = StringMakerUtil.getName(userName);
        }



        String socCode = ""; // 요금제 코드
        String contractNum = "" ; // 	계약번호
        String strActiveDate = ""; //개통일자
        String custId ="" ;     // 로그인 한 사용자 정보
        String ctn = ""  ;      // 로그인 한 사용자 정보


        if (REGULAR_MEMBER.equals(userSession.getUserDivision())) {
            //정회원 체크
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            McpUserCntrMngDto userCntrMng = Optional.ofNullable(cntrList)
                    .filter(list -> !list.isEmpty()) // 리스트가 비어 있지 않은 경우만
                    .map(list -> list.get(0)) // 첫 번째 요소 가져오기
                    .orElse(null); // 없으면 null 반환

            if(userCntrMng == null ) {
                throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
            }

            //정지 회선 .. 준회원으로 처리
            if ("A".equals(userCntrMng.getSubStatus())) {
                contractNum = userCntrMng.getContractNum();
                custId = userCntrMng.getCustId();      // 로그인 한 사용자 정보
                ctn = userCntrMng.getCntrMobileNo();     // 로그인 한 사용자 정보
                strActiveDate = StringUtils.rightPad(
                        Optional.ofNullable(userCntrMng.getLstComActvDate()).orElse(""), 14, '0'
                );
                //사용중인 요금제
                McpFarPriceDto  mcpFarPriceDto = mypageService.selectFarPricePlan(contractNum);
                socCode = mcpFarPriceDto.getPrvRateCd();
            } else {
                contractNum = ""; // 정지, 해지 회선는 그냥.. 준회원 처리
            }
        }

        FqcBasDto parmFqcBas = new FqcBasDto();
        parmFqcBas.setSocCode(socCode);
        parmFqcBas.setContractNum(contractNum);
        parmFqcBas.setOpenDate(strActiveDate);
        parmFqcBas.setUserId(userSession.getUserId());
        parmFqcBas.setCustId(custId);
        parmFqcBas.setCtn(ctn);

        FqcBasDto rtnFqcBas =  fqcSvc.syncParticipantMissions(parmFqcBas);


        int msnCnt = 0 ;//미션 성공수
        if (rtnFqcBas !=null ) {
            msnCnt = (int) rtnFqcBas.getMsnCnt();//미션 성공수
        }
        model.addAttribute("msnCnt", msnCnt);
        model.addAttribute("userName", userName);
        return pageJsp;
    }








    /**
     * 설명 : 프리퀀시 사용자 진행 확인
     *
     * @Author : papier
     * @Date : 2025.03.25
     */
    @RequestMapping(value = {"/fqc/getFqcProAjax.do","/m/fqc/getFqcProAjax.do"})
    @ResponseBody
    public Map<String, Object> getFqcPro() {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //1. 로그인 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        String socCode = ""; // 요금제 코드
        String contractNum = "" ; // 	계약번호
        String strActiveDate = ""; //개통일자
        String custId ="" ;     // 로그인 한 사용자 정보
        String ctn = ""  ;      // 로그인 한 사용자 정보

        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("DESC", "로그인 정보가 없음");
            return rtnMap;
        }

        if (REGULAR_MEMBER.equals(userSession.getUserDivision())) {
            //정회원 체크
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            McpUserCntrMngDto userCntrMng = Optional.ofNullable(cntrList)
                    .filter(list -> !list.isEmpty()) // 리스트가 비어 있지 않은 경우만
                    .map(list -> list.get(0)) // 첫 번째 요소 가져오기
                    .orElse(null); // 없으면 null 반환

            if(userCntrMng == null ) {
                rtnMap.put("RESULT_CODE", "-2");
                rtnMap.put("DESC", "정회원 정보가 없음 ");
                return rtnMap;
            }

            //정지 회선 .. 준회원으로 처리
            if ("A".equals(userCntrMng.getSubStatus())) {
                contractNum = userCntrMng.getContractNum();
                custId = userCntrMng.getCustId();      // 로그인 한 사용자 정보
                ctn = userCntrMng.getCntrMobileNo();     // 로그인 한 사용자 정보
                strActiveDate = StringUtils.rightPad(
                        Optional.ofNullable(userCntrMng.getLstComActvDate()).orElse(""), 14, '0'
                );
                //사용중인 요금제
                McpFarPriceDto  mcpFarPriceDto = mypageService.selectFarPricePlan(contractNum);
                socCode = mcpFarPriceDto.getPrvRateCd();
            } else {
                contractNum = ""; // 정지, 해지 회선는 그냥.. 준회원 처리
            }
        }

        FqcBasDto parmFqcBas = new FqcBasDto();
        parmFqcBas.setSocCode(socCode);
        parmFqcBas.setContractNum(contractNum);
        parmFqcBas.setOpenDate(strActiveDate);
        parmFqcBas.setUserId(userSession.getUserId());
        parmFqcBas.setCustId(custId);
        parmFqcBas.setCtn(ctn);

        //logger.info("getFqcProAjax parmFqcBas==>"+ ObjectUtils.convertObjectToString(parmFqcBas));

        /**
         * 2. 정책 조회 없으면 정책 조회
         * - 정회원 , 준회원
         * - 계약번호 , 개통일  , 요금제
         * - 프리퀀시 진행 리스트 조회
         * - 프리퀀시 진행 정책 조회
         * - 변경 사항 여부 확인
         */
        FqcBasDto rtnFqcBas =  fqcSvc.syncParticipantMissions(parmFqcBas);


        if (rtnFqcBas == null ) {
            rtnMap.put("RESULT_CODE", "-3");
            rtnMap.put("DESC", "프리퀀시 정책이 존재하지 않습니다.  ");
            return rtnMap;
        }

        /** X77 호출 하기 위한 로그인 정보 설정  */
        rtnFqcBas.setCustId(custId);
        rtnFqcBas.setCtn(ctn);

        //정책 정보 저장
        SessionUtils.saveFqcBas(rtnFqcBas);


        /** 각미션 진행 값  패치  */
        List<FqcDltDto> fqcDltList =  rtnFqcBas.getFqcDltDtoList();

        //Msg 치환 처리
        //공통 코드 조회
        List<NmcpCdDtlDto> fqcPlcyMsnList = Optional.ofNullable(NmcpServiceUtils.getCodeList(Constants.FQC_PLCY_MSN_CODE))
                .orElse(Collections.emptyList());

        //CACHE DATA 영향 때문에 깊은 복사 처리
        List<NmcpCdDtlDto> copiedList = fqcPlcyMsnList.stream().map(orig -> {
                    NmcpCdDtlDto copy = new NmcpCdDtlDto();
                    copy.setDtlCd(orig.getDtlCd());
                    copy.setDtlCdNm(orig.getDtlCdNm());
                    copy.setUseYn("-1");
                    return copy;
                }).collect(Collectors.toList());

        Map<String, NmcpCdDtlDto> fqcPlcyMsnListMap = copiedList.stream()
                .collect(Collectors.toMap(
                        NmcpCdDtlDto::getDtlCd,
                        Function.identity(),
                        (existing, replacement) -> replacement // 기존 값 덮어쓰기
                ));

        if (fqcDltList != null && fqcDltList.size() > 0) {
            /** 공통 코드에  각미션 진행 값  매핑  */
            for (FqcDltDto dlt : fqcDltList) {
                String msnTpCd = dlt.getMsnTpCd();
                String stateCode = dlt.getStateCode();

                NmcpCdDtlDto cdDtlDto = fqcPlcyMsnListMap.get(msnTpCd);
                if (cdDtlDto != null) {
                    cdDtlDto.setUseYn(stateCode); // 상태값 등록
                }
            }
        } else {
            fqcPlcyMsnListMap.values().forEach(cdDtlDto-> cdDtlDto.setUseYn("00"));
        }


        /** 프리퀀시 혜택 정보 조회 */
        List<FqcPlcyBnfDto> fqcPlcyBnfList = fqcSvc.getFqcPlcyBnfList(rtnFqcBas.getFqcPlcyCd());
        if (fqcPlcyBnfList != null && fqcPlcyBnfList.size() > 0) {
            fqcPlcyBnfList.forEach(fqcPlcyBnf -> fqcPlcyBnf.setBnfNm(NmcpServiceUtils.getCodeNm("FqcPlcyBnfList",fqcPlcyBnf.getBnfCd())));

            Map<Long, FqcPlcyBnfDto> fqcPlcyBnfListByCntMap = fqcPlcyBnfList.stream()
                    .collect(Collectors.toMap(
                            FqcPlcyBnfDto::getMsnCnt,
                            Function.identity(),
                            (existing, replacement) -> replacement // 기존 값 덮어쓰기
                    ));

            Map<Long, FqcPlcyBnfDto> fqcPlcyBnfListByLvlMap = fqcPlcyBnfList.stream()
                    .collect(Collectors.toMap(
                            FqcPlcyBnfDto::getBnfLvl,
                            Function.identity(),
                            (existing, replacement) -> replacement // 기존 값 덮어쓰기
                    ));

            rtnMap.put("FQC_PLCY_BNF_CNT_LIST", fqcPlcyBnfListByCntMap);
            rtnMap.put("FQC_PLCY_BNF_LVL_LIST", fqcPlcyBnfListByLvlMap);
        }

        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        //rtnMap.put("FQC_BAS", rtnFqcBas); //테스트용
        rtnMap.put("FQC_OBJ", fqcPlcyMsnListMap);
        rtnMap.put("MSN_CNT", rtnFqcBas.getMsnCnt());

        return rtnMap;
    }

    /**
     * 설명 : 프리퀀시 미션 등록
     * MCP_FQC_DLT
     * @Author : papier
     * @Date : 2025.03.25
     * checkFqcProAjax.do
     */
    @RequestMapping(value = "/fqc/setFqcDltAjax.do")
    @ResponseBody
    public Map<String, Object> setFqcDlt(FqcDltDto fqcDlt) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //1. 로그인 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())  ) {
            rtnMap.put("RESULT_CODE", "SESSIONCK");
            rtnMap.put("RESULT_DESC", "로그인 정보가 없음");
            return rtnMap;
        }

        //프리퀀시 정책 확인
        FqcBasDto fqcBas = SessionUtils.getFqcBasCookieBean();

        if(fqcBas == null || StringUtils.isEmpty(fqcBas.getFqcPlcyCd())  || fqcBas.getFqcSeq() < 1 ) {
            rtnMap.put("RESULT_CODE", "PLCY_CD_CK");
            rtnMap.put("RESULT_DESC", "프리퀀시 정책 없음");
            return rtnMap;
        }

        fqcDlt.setFqcSeq(fqcBas.getFqcSeq() );
        fqcDlt.setRegstId(userSession.getUserId());

        if (fqcSvc.setFqcDlt(fqcBas , fqcDlt)) {
            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
            rtnMap.put("RESULT_DESC", "정상 처리 ");
        }

        rtnMap.put("RESULT_OBJ", fqcDlt);
        return rtnMap ;
    }



}
