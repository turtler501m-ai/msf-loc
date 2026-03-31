package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formComm.dto.McpSubInfoDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formComm.service.FormMypageSvc;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgRegSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 부가서비스 신청/해지 REST 컨트롤러.
 * ASIS RegSvcController 7개 함수를 동일 이름으로 구현.
 *
 * -----------------------------------------------------------------------
 * ASIS 참조 소스: mcp-portal-was
 *   com.ktmmobile.mcp.mypage.controller.RegSvcController
 *   - regServiceView      : /m/mypage/regServiceView.do          (5007-01)
 *   - addSvcListAjax      : /m/mypage/addSvcListAjax.do          (5007-02)
 *   - myAddSvcListAjax    : /m/mypage/myAddSvcListAjax.do        (5007-03)
 *   - getContRateAdditionAjax : /m/mypage/getContRateAdditionAjax.do (5007-04)
 *   - moscRegSvcCanChgAjax: /m/mypage/moscRegSvcCanChgAjax.do   (5007-05 X38)
 *   - addSvcViewPop       : /m/mypage/addSvcViewPop.do           (5007-04/07/08/09)
 *   - regSvcChgAjax       : /m/mypage/regSvcChgAjax.do          (5007-06 Y25)
 *
 * TOBE 변경사항
 * -----------------------------------------------------------------------
 *   - UserSessionDto / SessionUtils 제거 (스마트서식지 비회원 방식)
 *   - mypageService.selectCntrList(userId)
 *     → formMypageSvc.selectCntrListNoLogin(ncn, ctn) — MSP_JUO_SUB_INFO@DL_MSP 직접 조회
 *   - mypageService.checkUserType(searchVO, cntrList, userSession)
 *     → checkUserType(req, cntrList) — UserDivision 체크 제거 (대리점 직원 방식)
 *   - 미성년자 체크 제거 (대리점 직원 사용)
 *   - 마스킹 해제 이력 제거
 *   - 로밍 관련 처리 제거
 */
@RestController
@RequestMapping("/api/v1/addition")
public class SvcChgRegController {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgRegController.class);

    @Autowired
    private SvcChgRegSvc svcChgRegSvc;

    /**
     * ASIS: mypageService.selectCntrList(userId) + mypageService.checkUserType() 대체.
     * 세션 없이 NCN/CTN 기반으로 계약 정보를 조회한다.
     * MSP_JUO_SUB_INFO@DL_MSP → lstComActvDate, subStatus, userName 등.
     */
    @Autowired
    private FormMypageSvc formMypageSvc;

    // ============================================================
    // X97 — 순수 이용중 부가서비스 조회 (내부 전용)
    // ============================================================

    /**
     * X97 현재 이용중 부가서비스 조회 (wirelessBlock/infoLimit 체크 포함).
     * POST /api/v1/addition/current
     */
    @PostMapping("/current")
    public AdditionCurrentResVO additionCurrent(@RequestBody SvcChgInfoDto req) {
        return svcChgRegSvc.selectAdditionCurrent(req);
    }

    // ============================================================
    // 5007-01 regServiceView
    // ASIS: RegSvcController.regServiceView (/m/mypage/regServiceView.do)
    // ============================================================

    /**
     * 5007-01 regServiceView — 부가서비스 신청 화면 초기화.
     *
     * ASIS 참조 (RegSvcController.java:95-148):
     *   1) cntrList = mypageService.selectCntrList(userSession.getUserId())
     *      → TOBE: selectCntrList(req)
     *   2) checkUserType(searchVO, cntrList, userSession) → chk
     *      → TOBE: checkUserType(req, cntrList) — 비회원이므로 false여도 진행
     *   3) 미성년자 체크 (userAge < 19) → TOBE: 제거
     *   4) 마스킹 해제 이력 → TOBE: 제거
     *   5) model: cntrList, searchVO → TOBE: cntrList, contractNum, ctn 반환
     *
     * POST /api/v1/addition/reg-service-view
     *
     * @return {cntrList, contractNum, ctn, success}
     */
    @PostMapping("/reg-service-view")
    public Map<String, Object> regServiceView(@RequestBody SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();

        // ASIS: cntrList = mypageService.selectCntrList(userSession.getUserId())
        // TOBE: selectCntrList — 세션 없이 NCN/CTN 기반 계약 조회
        List<McpSubInfoDto> cntrList = selectCntrList(req);
        // ASIS: checkUserType → 회원/미성년자 체크
        // TOBE: 비회원 방식이므로 false여도 화면 초기화는 진행
        checkUserType(req, cntrList);

        logger.debug("[5007-01] regServiceView: ncn={}, cntrListSize={}",
            req != null ? req.getNcn() : null, cntrList.size());

        result.put("cntrList", cntrList);
        result.put("contractNum", req != null ? req.getNcn() : "");
        result.put("ctn", req != null ? req.getCtn() : "");
        result.put("success", true);
        return result;
    }

    // ============================================================
    // 5007-03 myAddSvcListAjax
    // ASIS: RegSvcController.myAddSvcListAjax (/m/mypage/myAddSvcListAjax.do)
    // ============================================================

    /**
     * 5007-03 myAddSvcListAjax — 이용중 부가서비스 목록 조회 (ASIS 함수명 동일).
     *
     * ASIS 참조 (RegSvcController.java:160-201):
     *   1) cntrList = mypageService.selectCntrList(userSession.getUserId())
     *      → TOBE: selectCntrList(req)
     *   2) checkUserType → false이면 McpCommonException
     *      → TOBE: false이면 빈 응답 반환
     *   3) lstComActvDate = cntrList.get(0).getLstComActvDate()
     *      → TOBE: checkUserType에서 req.setLstComActvDate() 이미 처리
     *   4) regSvcService.selectmyAddSvcList(ncn, ctn, custId, lstComActvDate)
     *   5) outList.removeIf(PL249Q800)
     *   6) getMpSocListByDiv(outList, addDivCd)
     *   7) return {outList, contractNum}
     *
     * POST /api/v1/addition/my-add-svc-list
     *
     * @return {outList: List&lt;MpSocVO&gt;, contractNum: String}
     */
    @PostMapping("/my-add-svc-list")
    public Map<String, Object> myAddSvcListAjax(@RequestBody SvcChgInfoDto req) {

        // ASIS: cntrList = mypageService.selectCntrList(userSession.getUserId())
        // TOBE: selectCntrList — lstComActvDate 획득 포함
        List<McpSubInfoDto> cntrList = selectCntrList(req);
        // ASIS: checkUserType → false이면 NOT_FULL_MEMBER_EXCEPTION (McpCommonException)
        // TOBE: false이면 빈 응답 반환
        boolean chk = checkUserType(req, cntrList);
        if (!chk) {
            logger.warn("[5007-03] myAddSvcListAjax: checkUserType 실패 — ncn={}", req != null ? req.getNcn() : null);
            Map<String, Object> err = new HashMap<>();
            err.put("outList", new ArrayList<>());
            err.put("contractNum", "");
            err.put("error", "계약 정보를 확인할 수 없습니다.");
            return err;
        }
        // lstComActvDate 는 checkUserType 내에서 req.setLstComActvDate() 처리됨
        // ASIS: lstComActvDate = cntrList.get(0).getLstComActvDate()

        return svcChgRegSvc.myAddSvcListAjax(req);
    }

    // ============================================================
    // 5007-02 addSvcListAjax
    // ASIS: RegSvcController.addSvcListAjax (/m/mypage/addSvcListAjax.do)
    // ============================================================

    /**
     * 5007-02 addSvcListAjax — 가입 가능 부가서비스 카탈로그 조회 (ASIS 함수명 동일).
     *
     * ASIS 참조 (RegSvcController.java:213-280):
     *   1) cntrList = mypageService.selectCntrList(...)
     *      → TOBE: selectCntrList(req)
     *   2) checkUserType → false이면 McpCommonException
     *      → TOBE: false이면 빈 응답 반환
     *   3) useSocList = regSvcService.selectAddSvcInfoDto(ncn, ctn, custId) [X97 SOC 목록]
     *   4) tmpList = mypageService.selectRegService(contractNum) [DB 카탈로그]
     *   5) getMcpRegServiceListByDiv(list, addDivCd)
     *   6) useYn 세팅, listA/listC 분류
     *   7) return {listC, listA, list, ctn, contractNum}
     *
     * POST /api/v1/addition/add-svc-list
     *
     * @return {listC, listA, list, ctn, contractNum}
     */
    @PostMapping("/add-svc-list")
    public Map<String, Object> addSvcListAjax(@RequestBody SvcChgInfoDto req) {

        // ASIS: cntrList = mypageService.selectCntrList(userSession.getUserId())
        List<McpSubInfoDto> cntrList = selectCntrList(req);
        // ASIS: checkUserType → false이면 McpCommonException
        boolean chk = checkUserType(req, cntrList);
        if (!chk) {
            logger.warn("[5007-02] addSvcListAjax: checkUserType 실패 — ncn={}", req != null ? req.getNcn() : null);
            Map<String, Object> err = new HashMap<>();
            err.put("listC", new ArrayList<>());
            err.put("listA", new ArrayList<>());
            err.put("list", new ArrayList<>());
            err.put("ctn", "");
            err.put("contractNum", "");
            err.put("error", "계약 정보를 확인할 수 없습니다.");
            return err;
        }

        return svcChgRegSvc.addSvcListAjax(req);
    }

    // ============================================================
    // 5007-04 getContRateAdditionAjax
    // ASIS: RegSvcController.getContRateAdditionAjax
    // ============================================================

    /**
     * 5007-04 getContRateAdditionAjax — 부가서비스 안내 상세 조회 (ASIS 함수명 동일).
     *
     * ASIS 참조 (RegSvcController.java:293-305):
     *   - selectCntrList / checkUserType 없음 (ASIS 원본도 동일)
     *   1) rateAdsvcCtgBasDTO.setRateAdsvcCd(rateAdsvcCd)
     *   2) regSvcService.selectAddSvcDtl(rateAdsvcCtgBasDTO) — XML 캐시 조회
     *      → TOBE: svcChgRegMapper.selectAddSvcDtl(rateAdsvcCd) — DB 조회
     *   3) return {rateDtl}
     * req.soc = rateAdsvcCd 로 사용.
     *
     * POST /api/v1/addition/get-cont-rate-addition
     *
     * @return {rateDtl: Map}
     */
    @PostMapping("/get-cont-rate-addition")
    public Map<String, Object> getContRateAdditionAjax(@RequestBody AdditionCancelReqDto req) {
        return svcChgRegSvc.getContRateAdditionAjax(req);
    }

    // ============================================================
    // 5007-05 moscRegSvcCanChgAjax
    // ASIS: RegSvcController.moscRegSvcCanChgAjax (/m/mypage/moscRegSvcCanChgAjax.do)
    // ============================================================

    /**
     * 5007-05 moscRegSvcCanChgAjax — 부가서비스 해지 (X38, ASIS 함수명 동일).
     *
     * ASIS 참조 (RegSvcController.java:319-343):
     *   1) cntrList = mypageService.selectCntrList(userSession.getUserId())
     *      → TOBE: selectCntrList(req)
     *   2) checkUserType → false이면 RESULT_CODE="E"
     *      → TOBE: 동일 처리
     *   3) regSvcService.moscRegSvcCanChg(searchVO, rateAdsvcCd) [X38]
     *
     * POST /api/v1/addition/mosc-reg-svc-can-chg
     *
     * @return {RESULT_CODE: "S"/"E", message, globalNo}
     */
    @PostMapping("/mosc-reg-svc-can-chg")
    public Map<String, Object> moscRegSvcCanChgAjax(@RequestBody AdditionCancelReqDto req) {

        // ASIS: cntrList = mypageService.selectCntrList(userSession.getUserId())
        List<McpSubInfoDto> cntrList = selectCntrList(req);
        // ASIS: checkUserType → false이면 rtnMap.put("RESULT_CODE", "E")
        boolean chk = checkUserType(req, cntrList);
        if (!chk) {
            logger.warn("[5007-05] moscRegSvcCanChgAjax: checkUserType 실패 — ncn={}", req != null ? req.getNcn() : null);
            Map<String, Object> err = new HashMap<>();
            err.put("RESULT_CODE", "E");
            err.put("message", "계약 정보를 확인할 수 없습니다.");
            return err;
        }

        return svcChgRegSvc.moscRegSvcCanChgAjax(req);
    }

    // ============================================================
    // 5007-04/07/08/09 addSvcViewPop
    // ASIS: RegSvcController.addSvcViewPop (/m/mypage/addSvcViewPop.do)
    // ============================================================

    /**
     * 5007-04/07/08/09 addSvcViewPop — 부가서비스 신청 팝업 분기 정보 조회 (ASIS 함수명 동일).
     *
     * ASIS 참조 (RegSvcController.java:360-427):
     *   1) cntrList = mypageService.selectCntrList(userSession.getUserId())
     *      → TOBE: selectCntrList(req)
     *   2) checkUserType → false이면 successRedirect
     *      → TOBE: false이면 에러 응답
     *   3) SOC 코드별 팝업 URL 분기:
     *      REG_SVC_CD_1(NOSPAM4)    → TM차단 팝업
     *      REG_SVC_CD_2(STLPVTPHN) → 번호도용차단 팝업
     *      REG_SVC_CD_3(PL2078760) → 로밍 팝업 → TOBE: 제거
     *      REG_SVC_CD_4(PL2079770) → 포인트할인 팝업
     *      기타                      → 일반 팝업
     *   4) REG_SVC_CD_4 포인트 조회 (myBenefitService.selectCustPoint) → TODO
     *
     * POST /api/v1/addition/add-svc-view-pop
     *
     * @return {popType, rateAdsvcCd, baseVatAmt, rateAdsvcNm, flag}
     */
    @PostMapping("/add-svc-view-pop")
    public Map<String, Object> addSvcViewPop(@RequestBody AdditionCancelReqDto req) {

        // ASIS: cntrList = mypageService.selectCntrList(userSession.getUserId())
        List<McpSubInfoDto> cntrList = selectCntrList(req);
        // ASIS: checkUserType → false이면 successRedirect (ResponseSuccessDto)
        boolean chk = checkUserType(req, cntrList);
        if (!chk) {
            logger.warn("[5007-04/07/08/09] addSvcViewPop: checkUserType 실패 — ncn={}", req != null ? req.getNcn() : null);
            Map<String, Object> err = new HashMap<>();
            err.put("error", "계약 정보를 확인할 수 없습니다.");
            return err;
        }

        return svcChgRegSvc.addSvcViewPop(req);
    }

    // ============================================================
    // 5007-06 regSvcChgAjax
    // ASIS: RegSvcController.regSvcChgAjax (/m/mypage/regSvcChgAjax.do)
    // ============================================================

    /**
     * 5007-06 regSvcChgAjax — 부가서비스 신청 (Y25, ASIS 함수명 동일).
     *
     * ASIS 참조 (RegSvcController.java:445-559):
     *   1) cntrList = mypageService.selectCntrList(userSession.getUserId())
     *      → TOBE: selectCntrList(req)
     *   2) checkUserType → false이면 NOT_FULL_MEMBER_EXCEPTION
     *      → TOBE: false이면 resultCode="E" 반환
     *   3) 당겨쓰기(pullData) step 체크 → TOBE: 제거 (대리점 전용)
     *   4) flag="Y": X38 해지 후 Y25 신청 → TOBE: 로밍 제거로 해당 케이스 삭제
     *      flag 없음: Y25 신청만
     *   5) REG_SVC_CD_4(PL2079770) + couoponPrice: 포인트 사용 → TODO
     *
     * POST /api/v1/addition/reg-svc-chg
     *
     * @return {resultCode: "00"/"E"/"99", message}
     */
    @PostMapping("/reg-svc-chg")
    public Map<String, Object> regSvcChgAjax(@RequestBody AdditionRegReqDto req) {

        // ASIS: cntrList = mypageService.selectCntrList(userSession.getUserId())
        List<McpSubInfoDto> cntrList = selectCntrList(req);
        // ASIS: checkUserType → false이면 NOT_FULL_MEMBER_EXCEPTION (McpCommonException)
        boolean chk = checkUserType(req, cntrList);
        if (!chk) {
            logger.warn("[5007-06] regSvcChgAjax: checkUserType 실패 — ncn={}", req != null ? req.getNcn() : null);
            Map<String, Object> err = new HashMap<>();
            err.put("resultCode", "E");
            err.put("message", "계약 정보를 확인할 수 없습니다.");
            return err;
        }

        return svcChgRegSvc.regSvcChgAjax(req);
    }

    // ============================================================
    // 내부 유틸 — ASIS MypageService 대체
    // ============================================================

    /**
     * ASIS mypageService.selectCntrList(userId) 대체.
     * 세션 userId 대신 NCN/CTN 기반으로 계약 정보를 조회하여 List로 반환.
     *
     * ASIS: mypageService.selectCntrList(userSession.getUserId())
     *   → 내부 REST API /mypage/cntrList → List&lt;McpUserCntrMngDto&gt;
     * TOBE: formMypageSvc.selectCntrListNoLogin(ncn, ctn)
     *   → MSP_JUO_SUB_INFO@DL_MSP 직접 조회 → List&lt;McpSubInfoDto&gt; (단일 항목)
     *
     * @param req 요청 DTO (ncn, ctn 사용)
     * @return 계약 정보 목록 (없으면 빈 리스트)
     */
    private List<McpSubInfoDto> selectCntrList(SvcChgInfoDto req) {
        List<McpSubInfoDto> cntrList = new ArrayList<>();
        if (req == null || isBlank(req.getNcn())) {
            return cntrList;
        }
        try {
            McpSubInfoDto param = new McpSubInfoDto();
            param.setSvcCntrNo(req.getNcn());
            param.setCntrMobileNo(req.getCtn());
            McpSubInfoDto info = formMypageSvc.selectCntrListNoLogin(param);
            if (info != null) {
                cntrList.add(info);
            }
        } catch (Exception e) {
            logger.warn("[selectCntrList] 계약 정보 조회 실패: ncn={}, {}", req.getNcn(), e.getMessage());
        }
        return cntrList;
    }

    /**
     * ASIS mypageService.checkUserType(searchVO, cntrList, userSession) 대체.
     * cntrList에서 req.ncn과 일치하는 계약을 찾아 req에 값을 세팅하고 isOwn을 반환.
     *
     * ASIS: MypageServiceImpl.checkUserType (MypageServiceImpl.java:902)
     *   1) UserDivision == "01" 체크 → TOBE: 제거 (대리점 직원 방식, 정회원 체크 불필요)
     *   2) cntrList null/empty → return false
     *   3) selNcn 비어있으면 cntrList[0] 사용 → searchVO.setNcn/setCtn/setCustId/...
     *   4) for: selNcn == ncn 이면 searchVO에 값 세팅 → isOwn = true
     *   5) return isOwn
     *
     * TOBE 변경:
     *   - UserDivision 체크 제거 (대리점 비회원)
     *   - searchVO → req (SvcChgInfoDto)
     *   - McpUserCntrMngDto → McpSubInfoDto
     *   - setModelName/setContractNum/setSoc → SvcChgInfoDto에 없으므로 생략
     *   - setLstComActvDate 추가 (isBlocked 계산용)
     *
     * @param req     요청 DTO — 일치하는 계약 ncn/ctn/custId/lstComActvDate 가 세팅됨
     * @param cntrList selectCntrList 로 조회한 계약 목록
     * @return true = 계약 소유 확인됨, false = 확인 실패
     */
    private boolean checkUserType(SvcChgInfoDto req, List<McpSubInfoDto> cntrList) {
        // ASIS: if( !StringUtil.equals(UserDivision, "01") ) return false
        //        → TOBE: 제거 (대리점 비회원 방식)

        // ASIS: if(cntrList == null || cntrList.isEmpty()) return false
        if (cntrList == null || cntrList.isEmpty()) {
            return false;
        }

        boolean isOwn = false;
        String selNcn = (req != null) ? nvl(req.getNcn(), "") : "";

        // ASIS: if( StringUtil.isEmpty(selNcn) ) → cntrList[0] 사용
        if (isBlank(selNcn)) {
            McpSubInfoDto first = cntrList.get(0);
            if (req != null) {
                req.setNcn(first.getSvcCntrNo());
                req.setCtn(first.getCntrMobileNo());
                req.setCustId(first.getCustId());
                req.setLstComActvDate(first.getLstComActvDate());
            }
            isOwn = true;
        }

        // ASIS: for(McpUserCntrMngDto dto : cntrList) — selNcn == ncn 이면 searchVO 세팅
        for (McpSubInfoDto dto : cntrList) {
            String ncn = dto.getSvcCntrNo();
            String ctn = dto.getCntrMobileNo();
            String custId = dto.getCustId();
            String lstComActvDate = dto.getLstComActvDate();

            if (selNcn.equals(ncn)) {
                if (req != null) {
                    req.setNcn(ncn);
                    req.setCtn(ctn);
                    req.setCustId(custId);
                    req.setLstComActvDate(lstComActvDate);
                }
                isOwn = true;
            }
        }

        logger.debug("[checkUserType] selNcn={}, isOwn={}", selNcn, isOwn);
        return isOwn;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String nvl(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s;
    }
}
