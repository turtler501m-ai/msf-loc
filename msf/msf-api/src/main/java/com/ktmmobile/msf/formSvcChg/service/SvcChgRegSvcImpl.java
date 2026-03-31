package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formSvcChg.dto.AdditionItemDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;
import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;
import com.ktmmobile.msf.formSvcChg.mapper.SvcChgRegMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 부가서비스 신청/해지 서비스 구현.
 *
 * -----------------------------------------------------------------------
 * ASIS 참조 소스
 * -----------------------------------------------------------------------
 * Controller : mcp-portal-was
 *   com.ktmmobile.mcp.mypage.controller.RegSvcController
 *   - regServiceView      : /m/mypage/regServiceView.do          (5007-01)
 *   - addSvcListAjax      : /m/mypage/addSvcListAjax.do          (5007-02)
 *   - myAddSvcListAjax    : /m/mypage/myAddSvcListAjax.do        (5007-03)
 *   - getContRateAdditionAjax : /m/mypage/getContRateAdditionAjax.do (5007-04)
 *   - addSvcViewPop       : /m/mypage/addSvcViewPop.do           (5007-04/07/08/09)
 *   - moscRegSvcCanChgAjax: /m/mypage/moscRegSvcCanChgAjax.do   (5007-05 X38)
 *   - regSvcChgAjax       : /m/mypage/regSvcChgAjax.do          (5007-06 Y25)
 *
 * Service : mcp-portal-was
 *   com.ktmmobile.mcp.mypage.service.RegSvcServiceImpl
 *   - selectAddSvcInfoDto()          : X20(구) → X97(현재 이용중 SOC 목록, useSocList용)
 *   - selectmyAddSvcList()           : X97 이용중 부가서비스 상세 + getMspRateMst enrichment
 *   - selectAddSvcList() [private]   : X97 실제 호출 + canCmnt/onlineCanYn 세팅 (fCommonSvc.getMspRateMst)
 *   - getMpSocListByDiv()            : X97 결과 일반(G)/로밍(R) 필터
 *   - getMcpRegServiceListByDiv()    : DB 카탈로그 일반(G)/로밍(R) 필터
 *   - chkRemove()                    : SOC 코드가 roamCdList 포함 여부로 제거 판단
 *   - moscRegSvcCanChg()             : X38 해지 (fCommonSvc.getMspRateMst → onlineCanYn 체크 후 호출)
 *   - regSvcChg()                    : X21 신청 → TOBE Y25 대체
 *
 * DAO/Mapper : mcp-portal-was
 *   com.ktmmobile.mcp.mypage.dao.RegSvcDao  →  RegSvcMapper.xml
 *   - getRoamCdList() : NMCP_RATE_ADSVC_GDNC_PROD_REL → MSF_RATE_ADSVC_GDNC_PROD_REL (접두사 교체)
 *
 *   com.ktmmobile.mcp.mypage.service.MypageService  →  MypageMapper.xml (mcp-api)
 *   - selectRegService(contractNum) : MSP_RATE_MST@DL_MSP + MSP_PROD_REL_MST@DL_MSP
 *                                     + MSP_JUO_FEATURE_INFO@DL_MSP (가입 가능 카탈로그)
 *
 * TOBE 변경사항
 * -----------------------------------------------------------------------
 *   - X20 → X97 대체 (MplatFormSvc.getAddSvcInfoParamDto)
 *   - X21 → Y25 대체 (MplatFormSvc.regSvcChgY25)
 *   - 회원 세션 체크(checkUserType) 제거 — 스마트서식지 앱은 비회원 방식
 *   - NMCP_RATE_ADSVC_GDNC_PROD_REL → MSF_RATE_ADSVC_GDNC_PROD_REL (접두사만 교체)
 */
@Service
public class SvcChgRegSvcImpl implements SvcChgRegSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgRegSvcImpl.class);

    /**
     * 무선데이터차단 SOC 코드.
     * ASIS: Constants.WIRELESS_BLOCK_SOC (또는 SOC 설명 문자열 포함 체크)
     */
    private static final String SOC_WIRELESS_BLOCK = "DATABLOCK";

    /**
     * 아무나솔로 내부 더미 부가서비스 SOC.
     * ASIS: RegSvcController.myAddSvcListAjax — "PL249Q800" 하드코딩 제거 주석.
     * "아무나솔로 상품 가입시, 내부 관리를 위해 자동가입되는 더미부가서비스(엠모바일) 목록에서 제거"
     */
    private static final String SOC_DUMMY_SOLO = "PL249Q800";

    /**
     * 요금할인 SOC — 해지 금지 강제 적용.
     * ASIS: Constants.REG_SVC_CD_4 (Constants.java:1093).
     * RegSvcServiceImpl.selectAddSvcList():224 — "요금할인 해지금지" → onlineCanYn = "N" 강제.
     */
    private static final String SOC_RATE_DISCOUNT = "PL2079770";

    /**
     * 불법TM차단 SOC 코드. ASIS: Constants.REG_SVC_CD_1.
     * addSvcViewPop(5007-04/07) → popType="TM"
     */
    private static final String SOC_NOSPAM = "NOSPAM4";

    /**
     * 번호도용차단 SOC 코드. ASIS: Constants.REG_SVC_CD_2.
     * addSvcViewPop(5007-08) → popType="STEAL"
     */
    private static final String SOC_STEAL = "STLPVTPHN";

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private SvcChgRegMapper svcChgRegMapper;

    // ============================================================
    // POST /api/v1/addition/current
    // ASIS: RegSvcController — selectAdditionCurrent 는 TOBE 신규 분리
    //       X97 이용중 부가서비스 + wirelessBlock/infoLimit 체크를 한 번에 반환
    // ============================================================

    /**
     * X97 현재 이용중 부가서비스 조회 (wirelessBlock/infoLimit 체크 포함).
     *
     * ASIS 참조:
     *   RegSvcController.addSvcListAjax (5007-02) 冒頭 부분
     *     → regSvcService.selectAddSvcInfoDto(X20) 에서 X97 으로 대체
     *   RegSvcServiceImpl.selectAddSvcInfoDto()
     *     → mPlatFormService.getAddSvcInfoDto(X20) 호출
     *     → TOBE: mplatFormSvc.getAddSvcInfoParamDto(X97) 로 대체
     *
     * wirelessBlock/infoLimit 체크 로직:
     *   ASIS RegSvcController.myAddSvcListAjax 내 outList 순회 시
     *   SOC_WIRELESS_BLOCK 포함 여부, 정보료상한 SOC 설명 포함 여부 확인
     */
    @Override
    public AdditionCurrentResVO selectAdditionCurrent(SvcChgInfoDto req) {
        AdditionCurrentResVO res = new AdditionCurrentResVO();
        List<AdditionItemDto> items = new ArrayList<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            res.setItems(items);
            res.setAvailableItems(new ArrayList<>());
            return res;
        }
        logger.debug("[X97] selectAdditionCurrent Start: ncn={}, ctn={}", req.getNcn(), req.getCtn());

        try {
            // X97 호출 — ASIS: mPlatFormService.getAddSvcInfoParamDto(ncn, ctn, custId)
            MpAddSvcInfoParamDto x97 = mplatFormSvc.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (x97 != null && x97.isSuccess() && x97.getList() != null) {
                for (MpSocVO soc : x97.getList()) {
                    AdditionItemDto item = toItemDto(soc);
                    items.add(item);
                    // wirelessBlock 체크 — ASIS: Constants.WIRELESS_BLOCK_SOC 또는 SOC 설명 포함 체크
                    if (SOC_WIRELESS_BLOCK.equals(soc.getSoc()) || isWirelessBlockDesc(soc.getSocDescription())) {
                        res.setWirelessBlockInUse(true);
                    }
                    // 정보료상한 체크 — ASIS: SOC 설명에 "정보료" + "상한" 포함 여부
                    if (isInfoLimitSoc(soc.getSocDescription())) {
                        int amount = parseAmount(soc.getSocRateValue());
                        if (amount > 0) res.setInfoLimitAmount(amount);
                    }
                }
            }
            logger.debug("[X97] selectAdditionCurrent 완료: {}건", items.size());
        } catch (Exception e) {
            logger.warn("[X97] selectAdditionCurrent 실패: {}", e.getMessage());
        }

        res.setItems(items);
        res.setAvailableItems(new ArrayList<>());
        return res;
    }

    // ============================================================
    // POST /api/v1/addition/reg-service-view
    // ASIS: RegSvcController.regServiceView (5007-01)
    //       URL: /m/mypage/regServiceView.do
    // ============================================================

    /**
     * 5007-01 regServiceView — 부가서비스 신청 화면 초기화.
     *
     * ASIS 참조:
     *   RegSvcController.regServiceView (5007-01)
     *     1) checkUserType → 회원/미성년자 체크 → TOBE: 제거 (스마트서식지 비회원)
     *     2) 마스킹 해제 → TOBE: 제거
     *   TOBE: 빈 성공 응답 반환.
     */
    @Override
    public Map<String, Object> regServiceView(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        // ASIS: 회원/미성년자 체크, 마스킹 해제 → TOBE 모두 제거 (스마트서식지 비회원)
        result.put("success", true);
        return result;
    }

    // ============================================================
    // POST /api/v1/addition/my-add-svc-list
    // ASIS: RegSvcController.myAddSvcListAjax (5007-03)
    //       URL: /mypage/myAddSvcListAjax.do, /m/mypage/myAddSvcListAjax.do
    // ============================================================

    /**
     * 5007-03 myAddSvcListAjax — 이용중 부가서비스 목록 조회 (ASIS 함수명 동일).
     *
     * ASIS 참조:
     *   RegSvcController.myAddSvcListAjax (RegSvcController.java:160-201)
     *     1) addDivCd = searchVO.getAddDivCd()
     *     2) mypageService.selectCntrList / checkUserType → TOBE: 제거 (스마트서식지 비회원 방식)
     *     3) lstComActvDate = cntrList.get(0).getLstComActvDate()
     *        → TOBE: req.getLstComActvDate() (SvcChgInfoDto 필드)
     *     4) regSvcService.selectmyAddSvcList(ncn, ctn, custId, lstComActvDate)
     *        → RegSvcServiceImpl.selectAddSvcList():195-237
     *          a) X97 mPlatFormService.getAddSvcInfoParamDto() 호출
     *          b) 각 SOC별 fCommonSvc.getMspRateMst(soc) → canCmnt, onlineCanYn 세팅
     *             - lstComActvDate 있으면 DateTimeUtil.isBlocked(lstComActvDate, onlineCanDay) 체크
     *             - REG_SVC_CD_4(PL2079770) → onlineCanYn="N" 강제 (요금할인 해지금지)
     *          → TOBE: svcChgRegMapper.getMspRateMstByRateCd(soc) 로 대체
     *     5) outList.removeIf(item -> item.getSoc().equals("PL249Q800"))
     *        → "아무나솔로 자동가입 더미부가서비스 목록에서 제거" (RegSvcController.java:192)
     *     6) regSvcService.getMpSocListByDiv(outList, addDivCd)
     *        → RegSvcServiceImpl.getMpSocListByDiv() (RegSvcServiceImpl.java:350)
     *     7) rtnMap: outList, contractNum
     *
     * @return {outList: List&lt;MpSocVO&gt;, contractNum: String}
     */
    @Override
    public Map<String, Object> myAddSvcListAjax(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            result.put("outList", new ArrayList<>());
            result.put("contractNum", "");
            return result;
        }

        String addDivCd = nvl(req.getAddDivCd(), "");
        String lstComActvDate = nvl(req.getLstComActvDate(), "");
        logger.debug("[5007-03] myAddSvcListAjax Start: ncn={}, ctn={}, addDivCd={}, lstComActvDate={}",
            req.getNcn(), req.getCtn(), addDivCd, lstComActvDate);

        List<MpSocVO> outList = new ArrayList<>();

        try {
            // 1) X97 이용중 부가서비스 목록 조회
            //    ASIS: mPlatFormService.getAddSvcInfoParamDto(ncn, ctn, custId) (RegSvcServiceImpl.java:199)
            MpAddSvcInfoParamDto vo = mplatFormSvc.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (vo == null || !vo.isSuccess()) {
                logger.warn("[5007-03] myAddSvcListAjax: X97 조회 실패 또는 빈 응답");
                result.put("outList", outList);
                result.put("contractNum", req.getNcn());
                return result;
            }

            outList = (vo.getList() != null) ? vo.getList() : new ArrayList<>();

            // 2) getMspRateMst enrichment — canCmnt / onlineCanYn / onlineCanDay 세팅
            //    ASIS: RegSvcServiceImpl.selectAddSvcList():204-228
            //          fCommonSvc.getMspRateMst(mSocVo.getSoc()) → MspRateMstDto
            //    TOBE: svcChgRegMapper.getMspRateMstByRateCd(soc) → Map
            for (MpSocVO soc : outList) {
                try {
                    Map<String, Object> rateMst = svcChgRegMapper.getMspRateMstByRateCd(soc.getSoc());
                    if (rateMst != null) {
                        // canCmnt: 해지 안내 문구 (ASIS: mSocVo.setCanCmnt(NVL(mspRateMstDto.getCanCmnt(),""))
                        String canCmnt = rateMst.get("canCmnt") != null ? rateMst.get("canCmnt").toString() : "";
                        soc.setCanCmnt(canCmnt);

                        // onlineCanYn: 해지 가능 여부 (ASIS: RegSvcServiceImpl.java:213-221)
                        String onlineCanYn = rateMst.get("onlineCanYn") != null ? rateMst.get("onlineCanYn").toString() : "";
                        if (!lstComActvDate.isEmpty()) {
                            // lstComActvDate 있으면 isBlocked 체크 (ASIS: DateTimeUtil.isBlocked)
                            int onlineCanDay = 0;
                            if (rateMst.get("onlineCanDay") != null) {
                                try { onlineCanDay = Integer.parseInt(rateMst.get("onlineCanDay").toString()); }
                                catch (NumberFormatException ignored) {}
                            }
                            if (isBlocked(lstComActvDate, onlineCanDay)) {
                                soc.setOnlineCanYn("N");
                            } else {
                                soc.setOnlineCanYn(onlineCanYn);
                            }
                        } else {
                            soc.setOnlineCanYn(onlineCanYn);
                        }
                    }
                } catch (Exception e) {
                    logger.warn("[5007-03] getMspRateMstByRateCd 조회 실패: soc={}, {}", soc.getSoc(), e.getMessage());
                }

                // 요금할인 SOC(PL2079770): onlineCanYn="N" 강제 (ASIS: Constants.REG_SVC_CD_4 해지금지)
                //   RegSvcServiceImpl.java:224 — "요금할인 해지금지"
                if (SOC_RATE_DISCOUNT.equals(soc.getSoc())) {
                    soc.setOnlineCanYn("N");
                }
            }

            // 3) 아무나솔로 더미 부가서비스 제거 (ASIS: RegSvcController.java:192)
            outList.removeIf(soc -> SOC_DUMMY_SOLO.equals(soc.getSoc()));

            // 4) 일반/로밍 필터 (addDivCd: G=일반, R=로밍, 빈값=전체)
            //    ASIS: regSvcService.getMpSocListByDiv(outList, addDivCd) (RegSvcController.java:196)
            getMpSocListByDiv(outList, addDivCd);

            logger.debug("[5007-03] myAddSvcListAjax 완료: {}건", outList.size());

        } catch (Exception e) {
            logger.error("[5007-03] myAddSvcListAjax 오류: {}", e.getMessage());
        }

        // 5) return: outList, contractNum (ASIS: RegSvcController.java:198-200)
        result.put("outList", outList);
        result.put("contractNum", req.getNcn());
        return result;
    }

    // ============================================================
    // POST /api/v1/addition/add-svc-list
    // ASIS: RegSvcController.addSvcListAjax (5007-02)
    //       URL: /mypage/addSvcListAjax.do, /m/mypage/addSvcListAjax.do
    // ============================================================

    /**
     * 5007-02 addSvcListAjax — 가입 가능 부가서비스 카탈로그 조회.
     *
     * ASIS 참조:
     *   RegSvcController.addSvcListAjax (RegSvcController.java:213)
     *     1) mypageService.selectCntrList / checkUserType → TOBE: 제거(회원 로직 불필요)
     *     2) regSvcService.selectAddSvcInfoDto(ncn, ctn, custId)
     *        → useSocList(List&lt;String&gt;) 획득
     *        → ASIS: X20 mPlatFormService.getAddSvcInfoDto() 사용
     *        → TOBE: X97 mplatFormSvc.getAddSvcInfoParamDto() 로 대체
     *     3) mypageService.selectRegService(contractNum)
     *        → MypageMapper.xml selectRegService
     *        → MSP_RATE_MST@DL_MSP + MSP_PROD_REL_MST@DL_MSP + MSP_JUO_FEATURE_INFO@DL_MSP
     *        → TOBE: SvcChgRegMapper.selectRegService(ncn) — 동일 SQL 이식
     *     4) regSvcService.getMcpRegServiceListByDiv(list, addDivCd)
     *        → RegSvcServiceImpl.getMcpRegServiceListByDiv() (RegSvcServiceImpl.java:377)
     *     5) useYn 세팅: useSocList.contains(rateCd) → "Y"/"N"
     *        → ASIS: RegSvcController.java:252-258
     *     6) listC(무료/필수): baseAmt="0" &amp;&amp; svcRelTp="C" || svcRelTp="B"
     *        listA(유료): 나머지
     *        → ASIS: RegSvcController.java:260-266
     *     7) return: listC, listA, list, ctn, contractNum(=ncn)
     *        → ASIS: RegSvcController.java:268-274
     *
     * @return {listC, listA, list, ctn, contractNum}
     */
    @Override
    public Map<String, Object> addSvcListAjax(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            result.put("listC", new ArrayList<>());
            result.put("listA", new ArrayList<>());
            result.put("list", new ArrayList<>());
            result.put("ctn", "");
            result.put("contractNum", "");
            return result;
        }

        String ncn = req.getNcn();
        String ctn = req.getCtn();
        String custId = req.getCustId();
        String addDivCd = nvl(req.getAddDivCd(), "");
        logger.debug("[5007-02] addSvcListAjax Start: ncn={}, ctn={}, addDivCd={}", ncn, ctn, addDivCd);

        // 1) 현재 이용중 SOC 코드 목록 조회 (X97 → useSocList)
        //    ASIS: regSvcService.selectAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId())
        List<String> useSocList = selectAddSvcInfoDto(ncn, ctn, custId);

        List<McpRegServiceDto> list = new ArrayList<>();
        List<McpRegServiceDto> listA = new ArrayList<>();
        List<McpRegServiceDto> listC = new ArrayList<>();

        try {
            // 2) DB 가입 가능 부가서비스 카탈로그 조회
            //    ASIS: mypageService.selectRegService(searchVO.getContractNum())
            //          → MypageMapper.xml selectRegService (mcp-api)
            List<McpRegServiceDto> tmpList = svcChgRegMapper.selectRegService(ncn);
            list = new ArrayList<>(tmpList); // iterator remove 위해 재선언 (ASIS 동일)

            // 3) 일반/로밍 필터
            //    ASIS: regSvcService.getMcpRegServiceListByDiv(list, addDivCd)
            getMcpRegServiceListByDiv(list, addDivCd);

            // 4) useYn 세팅 및 listA/listC 분류
            //    ASIS: RegSvcController.java:252-266
            for (McpRegServiceDto item : list) {
                // useYn: useSocList에 포함되면 "Y" (ASIS: useSocList.contains(list.get(i).getRateCd()))
                item.setUseYn(useSocList.contains(item.getRateCd()) ? "Y" : "N");

                String baseAmt = nvl(item.getBaseAmt(), "0");
                String svcRelTp = nvl(item.getSvcRelTp(), "");
                // ASIS: (baseAmt.equals("0") && svcRelTp.equals("C")) || svcRelTp.equals("B") → listC(무료/필수)
                if (("0".equals(baseAmt) && "C".equals(svcRelTp)) || "B".equals(svcRelTp)) {
                    listC.add(item);
                } else {
                    listA.add(item); // 유료
                }
            }

            logger.debug("[5007-02] addSvcListAjax 완료: 전체={}건 listA={}건 listC={}건",
                list.size(), listA.size(), listC.size());
        } catch (Exception e) {
            logger.warn("[5007-02] addSvcListAjax 실패: {}", e.getMessage());
        }

        // 5) return: listC, listA, list, ctn, contractNum (ASIS: RegSvcController.java:268-274)
        result.put("listC", listC);
        result.put("listA", listA);
        result.put("list", list);
        result.put("ctn", ctn);
        result.put("contractNum", ncn);
        return result;
    }

    // ============================================================
    // POST /api/v1/addition/get-cont-rate-addition
    // ASIS: RegSvcController.getContRateAdditionAjax (5007-04)
    //       URL: /m/mypage/getContRateAdditionAjax.do
    // ============================================================

    /**
     * 5007-04 getContRateAdditionAjax — 부가서비스 안내 상세 조회.
     *
     * ASIS 참조:
     *   RegSvcController.getContRateAdditionAjax (5007-04)
     *     → rateAdsvcCd 로 부가서비스 안내 상세 조회
     *     → regSvcService.selectAddSvcDtl(rateAdsvcCd)
     *   RegSvcServiceImpl.selectAddSvcDtl() — XML 파일(rateAdsvcGdncXmlMap) 캐시 조회
     *   TOBE: DB(MSF_RATE_ADSVC_GDNC_PROD_REL + MSF_RATE_ADSVC_GDNC_BAS) 조회
     *         req.getSoc() = rateAdsvcCd 로 사용
     *
     * @return {rateDtl: Map}
     */
    @Override
    public Map<String, Object> getContRateAdditionAjax(AdditionCancelReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getSoc())) {
            result.put("rateDtl", new HashMap<>());
            return result;
        }

        String rateAdsvcCd = req.getSoc();
        logger.debug("[5007-04] getContRateAdditionAjax Start: rateAdsvcCd={}", rateAdsvcCd);

        try {
            // ASIS: regSvcService.selectAddSvcDtl(rateAdsvcCd) → XML 캐시 조회
            // TOBE: DB(MSF_RATE_ADSVC_GDNC_PROD_REL + MSF_RATE_ADSVC_GDNC_BAS) 조회
            Map<String, Object> rateDtl = svcChgRegMapper.selectAddSvcDtl(rateAdsvcCd);
            result.put("rateDtl", rateDtl != null ? rateDtl : new HashMap<>());
            logger.debug("[5007-04] getContRateAdditionAjax 완료: rateAdsvcCd={}", rateAdsvcCd);
        } catch (Exception e) {
            logger.warn("[5007-04] getContRateAdditionAjax 실패: {}", e.getMessage());
            result.put("rateDtl", new HashMap<>());
        }

        return result;
    }

    // ============================================================
    // POST /api/v1/addition/mosc-reg-svc-can-chg
    // ASIS: RegSvcController.moscRegSvcCanChgAjax (5007-05)
    //       URL: /mypage/moscRegSvcCanChgAjax.do, /m/mypage/moscRegSvcCanChgAjax.do
    // ============================================================

    /**
     * 5007-05 moscRegSvcCanChgAjax — 부가서비스 해지 (X38).
     *
     * ASIS 참조:
     *   RegSvcController.moscRegSvcCanChgAjax (RegSvcController.java:319)
     *     → mypageService.checkUserType → TOBE: 제거
     *     → regSvcService.moscRegSvcCanChg(searchVO, rateAdsvcCd)
     *   RegSvcServiceImpl.moscRegSvcCanChg() (RegSvcServiceImpl.java:251)
     *     1) fCommonSvc.getMspRateMst(rateAdsvcCd) → onlineCanYn 체크
     *        → TOBE: onlineCanYn 체크 생략 (myAddSvcListAjax에서 이미 처리)
     *     2) mPlatFormService.moscRegSvcCanChg(ncn, ctn, custId, rateAdsvcCd) [X38]
     *        → TOBE: mplatFormSvc.moscRegSvcCanChg(ncn, ctn, custId, soc)
     *
     * @return {RESULT_CODE: "S"/"E", message, globalNo}
     */
    @Override
    public Map<String, Object> moscRegSvcCanChgAjax(AdditionCancelReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getSoc())) {
            result.put("RESULT_CODE", "E");
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, soc)");
            return result;
        }

        String ncn = req.getNcn();
        String ctn = req.getCtn();
        String custId = req.getCustId();
        String soc = req.getSoc();
        logger.debug("[5007-05] moscRegSvcCanChgAjax Start: ncn={}, ctn={}, soc={}", ncn, ctn, soc);

        try {
            // ASIS: mPlatFormService.moscRegSvcCanChg(ncn, ctn, custId, rateAdsvcCd) [X38]
            MpMoscRegSvcCanChgInVO vo = mplatFormSvc.moscRegSvcCanChg(ncn, ctn, custId, soc);
            if (vo.isSuccess()) {
                logger.debug("[5007-05] moscRegSvcCanChgAjax 완료: soc={}, globalNo={}", soc, vo.getGlobalNo());
                result.put("RESULT_CODE", "S");
                result.put("globalNo", vo.getGlobalNo());
                result.put("message", "");
            } else {
                logger.warn("[5007-05] moscRegSvcCanChgAjax 실패: soc={}, resultCode={}, msg={}",
                    soc, vo.getResultCode(), vo.getSvcMsg());
                result.put("RESULT_CODE", "E");
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("[5007-05] moscRegSvcCanChgAjax 오류: {}", e.getMessage());
            result.put("RESULT_CODE", "E");
            result.put("message", "부가서비스 해지 처리 중 오류가 발생했습니다.");
        }

        return result;
    }

    // ============================================================
    // POST /api/v1/addition/add-svc-view-pop
    // ASIS: RegSvcController.addSvcViewPop (5007-04/07/08/09)
    //       URL: /m/mypage/addSvcViewPop.do
    // ============================================================

    /**
     * 5007-04/07/08/09 addSvcViewPop — 부가서비스 신청 팝업 분기 정보 조회.
     *
     * ASIS 참조:
     *   RegSvcController.addSvcViewPop (5007-04/07/08/09)
     *     → SOC 코드(rateAdsvcCd)에 따른 팝업 타입 분기
     *     - Constants.REG_SVC_CD_1(NOSPAM4) → popType="TM" (불법TM차단)
     *     - Constants.REG_SVC_CD_2(STLPVTPHN) → popType="STEAL" (번호도용차단)
     *     - Constants.REG_SVC_CD_3(PL2078760) → TOBE: 로밍 제거
     *     - Constants.REG_SVC_CD_4(PL2079770) → popType="POINT" (포인트할인) // 포인트 조회: TODO
     *     - 기타 → popType="GENERAL"
     *     → 각 팝업별 baseVatAmt, rateAdsvcNm 등 부가 정보 세팅
     *
     * @return {popType, rateAdsvcCd, baseVatAmt, rateAdsvcNm, flag}
     */
    @Override
    public Map<String, Object> addSvcViewPop(AdditionCancelReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getSoc())) {
            result.put("popType", "GENERAL");
            result.put("rateAdsvcCd", "");
            return result;
        }

        String soc = req.getSoc();
        logger.debug("[5007-04/07/08/09] addSvcViewPop Start: soc={}", soc);

        // SOC 코드별 팝업 타입 분기 (ASIS: Constants.REG_SVC_CD_1~4)
        String popType;
        if (SOC_NOSPAM.equals(soc)) {
            // ASIS: Constants.REG_SVC_CD_1 → /m/mypage/addSvcViewPop.do (5007-07 TM차단)
            popType = "TM";
        } else if (SOC_STEAL.equals(soc)) {
            // ASIS: Constants.REG_SVC_CD_2 → /m/mypage/addSvcViewPop.do (5007-08 번호도용차단)
            popType = "STEAL";
        } else if (SOC_RATE_DISCOUNT.equals(soc)) {
            // ASIS: Constants.REG_SVC_CD_4 → /m/mypage/addSvcViewPop.do (5007-04 포인트할인)
            // TODO: 포인트 조회 서비스 없음 — 포인트 잔액 조회 추후 구현
            popType = "POINT";
        } else {
            // 기타 → 일반 팝업 (5007-04)
            popType = "GENERAL";
        }

        // 안내 상세 정보 조회 (ASIS: selectAddSvcDtl → rateAdsvcNm, baseVatAmt 등)
        String rateAdsvcNm = "";
        String baseVatAmt = "";
        try {
            Map<String, Object> rateDtl = svcChgRegMapper.selectAddSvcDtl(soc);
            if (rateDtl != null) {
                rateAdsvcNm = rateDtl.get("rateAdsvcNm") != null ? rateDtl.get("rateAdsvcNm").toString() : "";
                baseVatAmt = rateDtl.get("mmBasAmtVatDesc") != null ? rateDtl.get("mmBasAmtVatDesc").toString() : "";
            }
        } catch (Exception e) {
            logger.warn("[5007-04/07/08/09] addSvcViewPop selectAddSvcDtl 조회 실패: soc={}, {}", soc, e.getMessage());
        }

        result.put("popType", popType);
        result.put("rateAdsvcCd", soc);
        result.put("baseVatAmt", baseVatAmt);
        result.put("rateAdsvcNm", rateAdsvcNm);
        result.put("flag", "");
        logger.debug("[5007-04/07/08/09] addSvcViewPop 완료: soc={}, popType={}", soc, popType);

        return result;
    }

    // ============================================================
    // POST /api/v1/addition/reg-svc-chg
    // ASIS: RegSvcController.regSvcChgAjax (5007-06)
    //       URL: /mypage/regSvcChgAjax.do, /m/mypage/regSvcChgAjax.do
    // ============================================================

    /**
     * 5007-06 regSvcChgAjax — 부가서비스 신청 (Y25).
     *
     * ASIS 참조:
     *   RegSvcController.regSvcChgAjax (RegSvcController.java:445)
     *     → mypageService.checkUserType → TOBE: 제거
     *     → flag="Y": 해지(X38) 후 신청(Y25) — 로밍 변경
     *     → flag 없음: 신청(Y25)만
     *     → REG_SVC_CD_4(PL2079770) + couoponPrice: 포인트 사용 → TODO (별도 서비스 없음)
     *   RegSvcServiceImpl.regSvcChg() (RegSvcServiceImpl.java:161)
     *     → mPlatFormService.regSvcChg(ncn, ctn, custId, soc, ftrNewParam) [X21]
     *   TOBE: mplatFormSvc.regSvcChgY25() [Y25] — X21 대체 신규 API
     *
     * @return {resultCode: "00"/"E"/"99", message}
     */
    @Override
    public Map<String, Object> regSvcChgAjax(AdditionRegReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getSoc())) {
            result.put("resultCode", "E");
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, soc)");
            return result;
        }

        String ncn = req.getNcn();
        String ctn = req.getCtn();
        String custId = req.getCustId();
        String soc = req.getSoc();
        String ftrNewParam = nvl(req.getFtrNewParam(), "");
        String resultCode = "99";
        String message = "";

        logger.debug("[5007-06] regSvcChgAjax Start: ncn={}, ctn={}, soc={}", ncn, ctn, soc);

        try {
            // Y25 신청 (ASIS: RegSvcController.java:471-490)
            // 로밍 변경(flag="Y") 케이스 제거 — TOBE: 로밍 미지원
            MpRegSvcChgVO regVo = mplatFormSvc.regSvcChgY25(ncn, ctn, custId, soc, ftrNewParam);
            if (regVo.isSuccess()) {
                resultCode = "00";
                logger.debug("[5007-06] regSvcChgAjax Y25 완료: soc={}, globalNo={}", soc, regVo.getGlobalNo());
            } else {
                resultCode = nvl(regVo.getResultCode(), "E");
                message = nvl(regVo.getSvcMsg(), "");
                logger.warn("[5007-06] regSvcChgAjax Y25 실패: soc={}, resultCode={}, msg={}", soc, resultCode, message);
            }
            // REG_SVC_CD_4(PL2079770) 포인트할인 처리: TODO (별도 포인트 서비스 없음)
            // ASIS: RegSvcController.java:492-510 — couoponPrice != null → 포인트 사용 처리
        } catch (Exception e) {
            logger.error("[5007-06] regSvcChgAjax 오류: {}", e.getMessage());
            resultCode = "99";
            message = "부가서비스 신청 처리 중 오류가 발생했습니다.";
        }

        result.put("resultCode", resultCode);
        result.put("message", message);
        return result;
    }

    // ============================================================
    // 내부 ASIS 함수명 유지 메서드 (private)
    // ============================================================

    /**
     * X97 이용중 SOC 코드 목록 조회 (addSvcListAjax useSocList 용도).
     *
     * ASIS 참조:
     *   RegSvcServiceImpl.selectAddSvcInfoDto() (RegSvcServiceImpl.java:89)
     *   - 원래 X20 mPlatFormService.getAddSvcInfoDto() 사용
     *   - TOBE: X97 mplatFormSvc.getAddSvcInfoParamDto() 로 대체
     *   - 반환: 이용중 SOC 코드 문자열 목록 (List&lt;String&gt;)
     */
    private List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId) {
        List<String> useSocList = new ArrayList<>();
        try {
            // ASIS: mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId) [X20]
            // TOBE: X97 대체
            MpAddSvcInfoParamDto vo = mplatFormSvc.getAddSvcInfoParamDto(ncn, ctn, custId);
            if (vo != null && vo.isSuccess() && vo.getList() != null) {
                for (MpSocVO soc : vo.getList()) {
                    useSocList.add(soc.getSoc());
                }
            }
        } catch (Exception e) {
            logger.warn("[X97] selectAddSvcInfoDto 실패: {}", e.getMessage());
        }
        return useSocList;
    }

    /**
     * X97 결과 MpSocVO 리스트에서 일반/로밍 필터.
     *
     * ASIS 참조:
     *   RegSvcServiceImpl.getMpSocListByDiv(List&lt;MpSocVO&gt; mpSocList, String addDivCd)
     *     (RegSvcServiceImpl.java:350)
     *   - addDivCd 빈값이면 return (전체)
     *   - roamCdList = regSvcDao.getRoamCdList()
     *   - 각 SOC 에 대해 chkRemove() 판단 후 제거
     *
     * @param addDivCd G=일반만, R=로밍만, 빈값=전체
     */
    public void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd) {
        if (isBlank(addDivCd) || mpSocList == null || mpSocList.isEmpty()) {
            return;
        }
        // ASIS: regSvcDao.getRoamCdList() (RegSvcMapper.xml getRoamCdList)
        List<String> roamCdList = getRoamCdList();
        Iterator<MpSocVO> iter = mpSocList.iterator();
        while (iter.hasNext()) {
            MpSocVO mpSoc = iter.next();
            if (chkRemove(mpSoc.getSoc(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    /**
     * DB 카탈로그 McpRegServiceDto 리스트에서 일반/로밍 필터.
     *
     * ASIS 참조:
     *   RegSvcServiceImpl.getMcpRegServiceListByDiv(List&lt;McpRegServiceDto&gt; mcpRegServiceList, String addDivCd)
     *     (RegSvcServiceImpl.java:377)
     *   - addDivCd 빈값이면 return
     *   - roamCdList = regSvcDao.getRoamCdList()
     *   - 각 rateCd 에 대해 chkRemove() 판단 후 제거
     */
    public void getMcpRegServiceListByDiv(List<McpRegServiceDto> list, String addDivCd) {
        if (isBlank(addDivCd) || list == null || list.isEmpty()) {
            return;
        }
        // ASIS: regSvcDao.getRoamCdList()
        List<String> roamCdList = getRoamCdList();
        Iterator<McpRegServiceDto> iter = list.iterator();
        while (iter.hasNext()) {
            McpRegServiceDto item = iter.next();
            if (chkRemove(item.getRateCd(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    /**
     * 로밍 SOC 코드 목록 조회.
     *
     * ASIS 참조:
     *   RegSvcServiceImpl 내 regSvcDao.getRoamCdList() 호출
     *   RegSvcMapper.xml getRoamCdList
     *     → NMCP_RATE_ADSVC_GDNC_PROD_REL + NMCP_RATE_ADSVC_GDNC_BAS (WHERE ADD_DIV_CD='R')
     *   TOBE: MSF_RATE_ADSVC_GDNC_PROD_REL + MSF_RATE_ADSVC_GDNC_BAS (접두사만 교체)
     *   → SvcChgRegMapper.getRoamCdList() → SvcChgRegMapper.xml
     */
    private List<String> getRoamCdList() {
        try {
            return svcChgRegMapper.getRoamCdList();
        } catch (Exception e) {
            logger.warn("[getRoamCdList] 조회 실패: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 부가서비스 제거 여부 판단 (일반/로밍 구분).
     *
     * ASIS 참조:
     *   RegSvcServiceImpl.chkRemove(String soc, String addDivCd, List&lt;String&gt; roamCdList)
     *     (RegSvcServiceImpl.java:402)
     *   - addDivCd="G": roamCdList에 포함 → 제거 (로밍 SOC 제거)
     *   - addDivCd="R": roamCdList에 미포함 → 제거 (일반 SOC 제거)
     *   - 그 외: 제거 안함
     */
    private boolean chkRemove(String soc, String addDivCd, List<String> roamCdList) {
        if ("G".equals(addDivCd)) {
            // 일반만 — 로밍 코드이면 제거
            return roamCdList.contains(soc);
        }
        // "R"(로밍만) 케이스 제거 — TOBE: 로밍 미지원
        return false;
    }

    /**
     * MpSocVO → AdditionItemDto 변환.
     * ASIS: RegSvcController 에서 outList(List&lt;MpSocVO&gt;) 를 직접 rtnMap에 담아 반환.
     * TOBE: AdditionItemDto VO 로 변환 후 반환.
     */
    private AdditionItemDto toItemDto(MpSocVO soc) {
        AdditionItemDto item = new AdditionItemDto();
        item.setSoc(soc.getSoc());
        item.setSocDescription(soc.getSocDescription());
        item.setSocRateValue(soc.getSocRateValue());
        item.setEffectiveDate(soc.getEffectiveDate());
        item.setProdHstSeq(soc.getProdHstSeq());
        item.setParamSbst(soc.getParamSbst());
        return item;
    }

    /**
     * 무선데이터차단 SOC 설명 포함 여부 체크.
     * ASIS: 별도 상수 또는 SOC 코드 직접 비교 방식 사용.
     */
    private boolean isWirelessBlockDesc(String desc) {
        return desc != null && desc.contains("무선") && desc.contains("차단");
    }

    /**
     * 정보료상한 SOC 설명 포함 여부 체크.
     * ASIS: SOC 설명 문자열 포함 여부로 판단.
     */
    private boolean isInfoLimitSoc(String desc) {
        return desc != null && desc.contains("정보료") && desc.contains("상한");
    }

    private int parseAmount(String rateValue) {
        if (isBlank(rateValue)) return 0;
        try {
            return Integer.parseInt(rateValue.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 해지 가능 날짜 이전인지(해지 불가 차단 여부) 판단.
     *
     * ASIS 참조:
     *   DateTimeUtil.isBlocked(lstComActvDate, onlineCanDay) (RegSvcServiceImpl.java:216)
     *   - lstComActvDate(개통일자) + onlineCanDay(일수) &gt; 오늘 → 해지 불가 (blocked=true)
     *   - 즉, 개통일로부터 onlineCanDay 일이 아직 지나지 않으면 차단
     *
     * @param lstComActvDate 최종 개통일자 (yyyyMMdd 또는 yyyyMMddHHmmss)
     * @param onlineCanDay   해지 가능 기준 일수
     * @return true = 해지 차단 (onlineCanYn="N" 처리)
     */
    private boolean isBlocked(String lstComActvDate, int onlineCanDay) {
        if (isBlank(lstComActvDate) || onlineCanDay <= 0) {
            return false;
        }
        try {
            String dateStr = lstComActvDate.length() >= 8 ? lstComActvDate.substring(0, 8) : lstComActvDate;
            LocalDate actvDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
            // 개통일 + onlineCanDay일이 오늘 이후이면 아직 해지 불가 기간
            return LocalDate.now().isBefore(actvDate.plusDays(onlineCanDay));
        } catch (Exception e) {
            logger.warn("[isBlocked] 날짜 파싱 실패: lstComActvDate={}", lstComActvDate);
            return false;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String nvl(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s;
    }
}
