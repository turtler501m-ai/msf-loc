package com.ktmmobile.msf.form.servicechange.service;

/**
 * 부가서비스 서비스 구현체
 *
 * =====================================================
 * [TOBE 변환 이력] 2026-04-03
 * =====================================================
 * ASIS 소스(MsfRegSvcServiceImpl.java)를 직접 변환.
 * 기존 ASIS 메서드는 삭제하지 않고 하단에 주석 처리.
 *
 * [주요 변경 내용]
 *   1. X20 → X97: getAddSvcInfoDto() → getAddSvcInfoParamDto()
 *      (X20은 기본 SOC 목록만 반환, X97은 SOC+이력+상세정보 반환)
 *   2. X21 → Y25: regSvcChg() → regSvcChgY25()
 *      (Y25는 단건이 아닌 multi 처리 지원, TOBE 표준 코드)
 *   3. MyPageSearchDto(세션) → AdditionReqDto/AdditionApplyReqDto(요청 바디)
 *      (Stateless REST 전환, 세션 의존 제거)
 *   4. Map<String,Object> 반환 → VO 반환 (타입 명확화)
 *   5. ASIS 공개 인터페이스 유틸 메서드 → private 내부 메서드로 이동
 *
 * [의존 서비스]
 *   MsfMplatFormService — M플랫폼 X97/X38/Y25 호출
 *   FCommonSvc       — MSP_RATE_MST@DL_MSP 조회 (온라인 해지 가능 여부)
 *   RegSvcDao        — 로밍 코드 목록 조회 (getRoamCdList)
 *   MsfMypageSvc     — DB 부가서비스 관리 목록 조회 (selectRegService)
 * =====================================================
 */

import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NO_EXSIST_RATE;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NO_ONLINE_CAN_CHANGE_ADD;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ktmmobile.msf.form.servicechange.dao.RegSvcDao;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.common.constants.Constants;
import com.ktmmobile.msf.common.exception.McpCommonException;
import com.ktmmobile.msf.common.exception.SelfServiceException;
import com.ktmmobile.msf.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.common.service.FCommonSvc;
import com.ktmmobile.msf.common.util.StringUtil;

@Service
public class MsfRegSvcServiceImpl implements MsfRegSvcService {

    private static Logger logger = LoggerFactory.getLogger(MsfRegSvcService.class);

    /** M플랫폼 연동 서비스 (X97/X38/Y25 호출) */
    @Autowired
    private MsfMplatFormService mPlatFormService;

    /** 공통 서비스 — MSP_RATE_MST@DL_MSP 조회 (온라인 해지 가능 여부, 해지 안내 문구) */
    @Autowired
    private FCommonSvc fCommonSvc;

    /** 부가서비스 DAO — 로밍 코드 목록(getRoamCdList) 조회 */
    @Autowired
    private RegSvcDao regSvcDao;

    /** 마이페이지 서비스 — DB 부가서비스 관리 목록(selectRegService) 조회 */
    @Autowired
    private MsfMypageSvc msfMypageSvc;

    // [ASIS] MsfMypageUserService — TOBE 메서드에서 미사용 (세션 기반 ASIS 전용)
    // @Autowired
    // private MsfMypageUserService mypageUserService;

    /** 공통 API 인터페이스 서버 주소 (ASIS getCtnByNcn에서만 사용, TOBE 미사용) */
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    // =====================================================
    // TOBE 메서드
    // =====================================================

    /**
     * 이용중 부가서비스 목록 조회 (X97 + MSP_RATE_MST)
     *
     * [처리 순서]
     * 1. M플랫폼 X97 호출 → 가입중인 SOC 목록 수신
     * 2. "PL249Q800" 제거
     *    : 아무나SOLO 상품 가입 시 내부 관리용 더미 SOC가 자동 등록됨
     *      (MVNO마스터결합전용 더미부가서비스(엠모바일)) → 사용자에게 노출 금지
     * 3. 각 SOC별 MSP_RATE_MST@DL_MSP 조회
     *    - canCmnt  : 온라인 해지 불가 시 표시할 안내 문구
     *    - onlineCanYn : 온라인 해지 가능 여부 ("Y"/"N")
     *    - Constants.REG_SVC_CD_4(포인트할인 SOC) → 온라인 해지 강제 "N"
     *      (포인트할인은 정책상 온라인 해지 불가)
     *
     * ASIS 참조: selectmyAddSvcList() / selectAddSvcList() — X97 호출, lstComActvDate 파라미터로
     *           개통일 기준 onlineCanDay 블로킹 처리 포함
     *           → TOBE에서는 lstComActvDate 없이 기본 onlineCanYn만 적용 (단순화)
     */
    @Override
    public AdditionMyListResVO selectMyAddSvcList(AdditionReqDto req) {
        MpAddSvcInfoParamDto vo = new MpAddSvcInfoParamDto();
        try {
            // [1] X97 — 가입중인 부가서비스 전체 조회
            vo = mPlatFormService.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (!vo.isSuccess()) {
                // M플랫폼 응답 null or 실패 시 공통 예외
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            List<MpSocVO> mSocVoList = vo.getList();

            if (mSocVoList != null) {
                for (MpSocVO mSocVo : mSocVoList) {
                    // [3] MSP_RATE_MST@DL_MSP 조회 — 해지 안내 문구 및 온라인 해지 가능 여부 세팅
                    MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(mSocVo.getSoc());
                    if (mspRateMstDto != null) {
                        mSocVo.setCanCmnt(StringUtil.NVL(mspRateMstDto.getCanCmnt(), ""));       // 해지 안내 문구
                        mSocVo.setOnlineCanYn(StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "")); // 온라인 해지 가능 여부
                    }
                    // 포인트할인(REG_SVC_CD_4) — 정책상 온라인 해지 불가, 강제 "N" 처리
                    if (Constants.REG_SVC_CD_4.equals(mSocVo.getSoc())) {
                        mSocVo.setOnlineCanYn("N");
                    }
                }

                // [2] 더미 SOC "PL249Q800" 제거 — 아무나SOLO 내부 관리 전용, 사용자 노출 금지
                mSocVoList.removeIf(item -> "PL249Q800".equals(item.getSoc()));
            }

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        }

        AdditionMyListResVO res = new AdditionMyListResVO();
        res.setList(vo.getList());
        return res;
    }

    /**
     * 가입가능 부가서비스 목록 조회 (X97 + DB)
     *
     * [처리 순서]
     * 1. M플랫폼 X97 호출 → 현재 가입중인 SOC 목록(useSocList) 추출
     * 2. DB selectRegService(ncn) → MSF에서 관리하는 전체 부가서비스 목록
     * 3. useSocList 기준으로 useYn 매핑 ("Y"=이미 가입 / "N"=미가입)
     * 4. "PL249Q800" 더미 SOC 필터링
     * 5. 유료/무료 분류:
     *    - listC (무료/번들): baseAmt="0" AND svcRelTp="C", 또는 svcRelTp="B"
     *    - listA (유료):      그 외 전부
     *
     * ASIS 참조: selectAddSvcInfoDto() — X20으로 이용중 SOC 조회 → TOBE에서 X97로 교체
     *           X20은 기본 SOC 목록만 반환, X97은 상세 이력 포함 반환
     */
    @Override
    public AdditionAvailableResVO selectAddSvcInfoDto(AdditionReqDto req) {
        // [1] X97 — 현재 가입중인 SOC 목록 추출 (useYn 매핑용)
        List<String> useSocList = new ArrayList<>();
        try {
            MpAddSvcInfoParamDto vo = mPlatFormService.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            if (!vo.isSuccess()) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }
            List<MpSocVO> mSocVoList = vo.getList();
            if (mSocVoList != null) {
                for (MpSocVO mSocVo : mSocVoList) {
                    useSocList.add(mSocVo.getSoc()); // 가입중인 SOC 코드만 추출
                }
            }
        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        }

        // [2] DB — MSF 관리 전체 부가서비스 목록 (MSF_REG_SVC_MST 등)
        // iterator remove를 위해 tmpList → 새 ArrayList로 복사
        List<McpRegServiceDto> list = new ArrayList<>(msfMypageSvc.selectRegService(req.getNcn()));

        List<McpRegServiceDto> listA = new ArrayList<>(); // 유료
        List<McpRegServiceDto> listC = new ArrayList<>(); // 무료/번들

        // [4] "PL249Q800" 더미 SOC 필터링 — 아무나SOLO 내부 SOC, 가입 화면에 노출 금지
        list.removeIf(item -> "PL249Q800".equals(item.getRateCd()));

        for (McpRegServiceDto item : list) {
            // [3] 이용중 여부 매핑
            item.setUseYn(useSocList.contains(item.getRateCd()) ? "Y" : "N");

            // [5] 유료/무료 분류
            // 무료: 기본료=0 이면서 서비스관계유형=C(무료구성), 또는 유형=B(번들)
            if (("0".equals(item.getBaseAmt()) && "C".equals(item.getSvcRelTp()))
                    || "B".equals(item.getSvcRelTp())) {
                listC.add(item);
            } else {
                listA.add(item); // 유료
            }
        }

        AdditionAvailableResVO res = new AdditionAvailableResVO();
        res.setList(list);   // 전체 (일반+로밍)
        res.setListA(listA); // 유료
        res.setListC(listC); // 무료/번들
        return res;
    }

    /**
     * 부가서비스 해지 (MSP_RATE_MST 검증 + X38)
     *
     * [처리 순서]
     * 1. MSP_RATE_MST@DL_MSP 조회
     *    - null → NO_EXSIST_RATE: 요금제 정보 없음 (해지 불가 처리)
     *    - onlineCanYn ≠ "Y" → NO_ONLINE_CAN_CHANGE_ADD: 온라인 해지 불가
     *      (해지 가능한 부가서비스만 온라인 처리, 나머지는 고객센터 안내)
     * 2. X38 해지 호출
     *    - prodHstSeq 있음 → moscRegSvcCanChgSeq: 특정 이력 번호 기준 해지
     *      (로밍처럼 동일 SOC를 여러 번 가입한 경우, 특정 건만 해지)
     *    - prodHstSeq 없음 → moscRegSvcCanChg: 단순 SOC 기준 해지
     *
     * ASIS 참조: moscRegSvcCanChg() / moscRegSvcCanChgSeq() — MyPageSearchDto 세션 의존,
     *           Map<String,Object> 반환 → TOBE에서 AdditionApplyResVO로 교체
     */
    @Override
    public AdditionApplyResVO moscRegSvcCanChg(AdditionApplyReqDto req) {
        try {
            // [1] MSP_RATE_MST@DL_MSP — 온라인 해지 가능 여부 사전 검증
            MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(req.getSoc());
            if (mspRateMstDto == null) {
                // 요금제 정보 자체가 없는 경우 — 해지 진행 불가
                return new AdditionApplyResVO(false, NO_EXSIST_RATE);
            }
            String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            if (!"Y".equals(onlineCanYn)) {
                // 온라인 해지 불가 SOC — 고객센터 통해 해지 안내
                return new AdditionApplyResVO(false, NO_ONLINE_CAN_CHANGE_ADD);
            }

            // [2] M플랫폼 X38 — 부가서비스 해지
            MpMoscRegSvcCanChgInVO vo;
            if (req.getProdHstSeq() != null && !req.getProdHstSeq().isEmpty()) {
                // prodHstSeq 있음: 특정 이력 건 해지 (로밍 등 동일 SOC 복수 가입 케이스)
                vo = mPlatFormService.moscRegSvcCanChgSeq(
                        req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getProdHstSeq());
            } else {
                // prodHstSeq 없음: SOC 기준 단순 해지
                vo = mPlatFormService.moscRegSvcCanChg(
                        req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            }

            if (!vo.isSuccess()) {
                // M플랫폼 응답 실패
                throw new McpCommonException(COMMON_EXCEPTION);
            }

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            return new AdditionApplyResVO(false, e.getMessage());
        }

        return new AdditionApplyResVO(true);
    }

    /**
     * 부가서비스 신청 (Y25, 선해지 포함)
     *
     * [처리 순서]
     * 1. flag="Y"이면 선해지 (cancelAddSvc 내부 호출)
     *    → 실패 시 즉시 반환 (신청 진행 중단)
     *    → 로밍 등 동일 SOC를 해지 후 재가입하는 "변경" 시나리오
     * 2. M플랫폼 Y25 호출 — 상품변경처리(multi)
     *    ASIS X21(단건)에서 Y25(multi)로 교체.
     *    Y25는 복수 SOC 처리 및 선/후처리 조합 지원.
     *
     * [미이관 항목 — 주석 처리]
     * - 인증 STEP 검증: certService.getStepCnt / vdlCertInfo
     *   당겨쓰기(/pullData01.do) 진입 시에만 STEP 검증이 필요했던 ASIS 로직.
     *   MSF에서 인증 공통 모듈 미구현 상태 → 추후 구현 (31번 §1-3 참조)
     * - 포인트 처리: pointService.editPoint
     *   포인트할인(REG_SVC_CD_4) 신청 시 포인트 차감 처리.
     *   MSF 포인트 기능 미이관 → 추후 구현
     *
     * ASIS 참조: regSvcChg() — X21 사용, 인증 STEP 검증, 포인트 처리 포함
     */
    @Override
    public AdditionApplyResVO regSvcChg(AdditionApplyReqDto req) {
        try {
            // [1] 선해지 (flag="Y": 동일 SOC 해지 후 재가입 — 로밍 변경 등)
            if ("Y".equals(req.getFlag())) {
                AdditionApplyResVO cancelRes = moscRegSvcCanChg(req);
                if (!cancelRes.isSuccess()) {
                    // 선해지 실패 시 신청 중단
                    return cancelRes;
                }
            }

            // [ASIS] 인증 STEP 검증 — 공통 미구현 (31번 §1-3)
            // 당겨쓰기 진입 시 최소 3스텝 인증 여부 및 계약번호·핸드폰번호 검증
            // if (certService.getStepCnt() < 3) { return STEP01 오류; }
            // Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            // if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) { return STEP02 오류; }

            // [2] M플랫폼 Y25 — 부가서비스 신청 (X21 대체)
            mPlatFormService.regSvcChgY25(
                    req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFtrNewParam());

            // [ASIS] 포인트 처리 — 포인트 기능 미이관
            // 포인트할인(REG_SVC_CD_4) 신청 시 포인트 사용 처리 (pointService.editPoint)
            // if (Constants.REG_SVC_CD_4.equals(req.getSoc())) {
            //     CustPointDto custPoint = myBenefitService.selectCustPoint(req.getNcn());
            //     if (custPoint != null) { pointService.editPoint(custPointTxnDto); }
            // }

        } catch (SocketTimeoutException e) {
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            return new AdditionApplyResVO(false, e.getMessage());
        }

        return new AdditionApplyResVO(true);
    }

    // =====================================================
    // private 유틸 메서드
    // ASIS에서 인터페이스 공개 메서드였으나 TOBE에서 내부 private으로 이동
    // (외부에서 직접 호출할 이유 없음 — Controller가 직접 호출하지 않음)
    // =====================================================

    /**
     * 부가서비스 목록에서 일반(G) 또는 로밍(R)만 남기도록 필터링
     *
     * addDivCd="G" → 로밍 SOC 제거 (일반만 남김)
     * addDivCd="R" → 일반 SOC 제거 (로밍만 남김)
     * addDivCd=""  → 전체 (필터 없음)
     *
     * 로밍 SOC 목록은 DB(getRoamCdList)에서 관리
     *
     * @param mpSocList 필터링할 이용중 SOC 목록 (X97 결과)
     * @param addDivCd  "G"=일반, "R"=로밍, ""=전체
     */
    private void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd) {
        if (addDivCd == null || "".equals(addDivCd)) return; // 전체 조회 시 필터 없음
        if (mpSocList == null || mpSocList.isEmpty()) return;
        Iterator<MpSocVO> iter = mpSocList.iterator();
        List<String> roamCdList = regSvcDao.getRoamCdList(); // DB에서 로밍 SOC 코드 목록 조회
        while (iter.hasNext()) {
            MpSocVO mpSoc = iter.next();
            if (chkRemove(mpSoc.getSoc(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    /**
     * 가입가능 부가서비스 목록에서 일반(G) 또는 로밍(R)만 남기도록 필터링
     *
     * @param mcpRegServiceList 필터링할 전체 부가서비스 목록 (DB)
     * @param addDivCd          "G"=일반, "R"=로밍, ""=전체
     */
    private void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd) {
        if (addDivCd == null || "".equals(addDivCd)) return;
        Iterator<McpRegServiceDto> iter = mcpRegServiceList.iterator();
        List<String> roamCdList = regSvcDao.getRoamCdList();
        while (iter.hasNext()) {
            McpRegServiceDto mcpRegService = iter.next();
            if (chkRemove(mcpRegService.getRateCd(), addDivCd, roamCdList)) {
                iter.remove();
            }
        }
    }

    /**
     * 특정 SOC를 목록에서 제거해야 하는지 판단
     *
     * "G"(일반만 조회): roamCdList에 포함되면 로밍 → 제거(true)
     * "R"(로밍만 조회): roamCdList에 포함되면 로밍 → 유지(false), 포함 안 되면 일반 → 제거(true)
     *
     * @param soc        SOC 코드
     * @param addDivCd   "G" 또는 "R"
     * @param roamCdList DB에서 조회한 로밍 SOC 코드 목록
     * @return true = 제거, false = 유지
     */
    private boolean chkRemove(String soc, String addDivCd, List<String> roamCdList) {
        if ("G".equals(addDivCd)) {
            // 일반 조회: roamCdList에 있으면 로밍 SOC → 제거
            for (String roamCd : roamCdList) {
                if (roamCd.equals(soc)) return true;
            }
            return false;
        } else if ("R".equals(addDivCd)) {
            // 로밍 조회: roamCdList에 없으면 일반 SOC → 제거
            for (String roamCd : roamCdList) {
                if (roamCd.equals(soc)) return false;
            }
            return true;
        }
        return false;
    }

    // =====================================================
    // [ASIS] 기존 메서드 — TOBE 전환 완료로 주석 처리 (삭제 금지)
    // =====================================================

    // [ASIS] X20 사용 → selectAddSvcInfoDto()로 대체
    // X20: 이용중인 부가서비스 SOC 목록만 반환 (단순)
    // X97: SOC + 이력 + 상세정보 포함 반환 (확장)
    // @Override
    // public List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId) {
    //     MpAddSvcInfoDto vo = mPlatFormService.getAddSvcInfoDto(ncn, ctn, custId);
    //     ... (X20 호출)
    // }

    // [ASIS] X21 사용 → regSvcChg()로 대체
    // X21: 단건 부가서비스 신청
    // Y25: 상품변경처리(multi) — 복수 SOC 처리 지원
    // @Override
    // public MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam)
    //         throws SocketTimeoutException {
    //     res = mPlatFormService.regSvcChg(ncn, ctn, custId, soc, ftrNewParam); // X21
    // }

    // [ASIS] X97 사용, MyPageSearchDto(세션) 의존 → selectMyAddSvcList()로 대체
    // lstComActvDate(최초 공통 활성 일자) 기반 onlineCanDay 블로킹 처리 포함
    // TOBE에서는 단순화 (lstComActvDate 미사용)
    // @Override
    // public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId) { ... }
    // @Override
    // public MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId, String lstComActvDate) { ... }

    // [ASIS] Map 반환, MyPageSearchDto(세션) 의존 → moscRegSvcCanChg()로 대체
    // Map<String,Object>에 "RESULT_CODE"("S"/"E"), "message" 키로 결과 반환
    // TOBE에서 AdditionApplyResVO(success, message)로 교체
    // @Override
    // public Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd)
    //         throws SocketTimeoutException { ... }
    // @Override
    // public Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq)
    //         throws SocketTimeoutException { ... }

    // [ASIS] 인터페이스 공개 메서드 → TOBE에서 private 내부 메서드로 이동
    // (외부에서 직접 호출하지 않으므로 인터페이스 노출 불필요)
    // @Override public void getMpSocListByDiv(...) { ... }
    // @Override public void getMcpRegServiceListByDiv(...) { ... }
    // @Override public String getUpdateYn(MpSocVO mpSoc) { ... }      // 로밍 상품 변경가능 여부
    // @Override public String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd) { ... } // 종료일 계산
    // @Override public String getCtnByNcn(String ncn, boolean flagMasking) { ... }   // 계약번호→회선번호
}
