package com.ktmmobile.msf.form.servicechange.service;

/**
 * 부가서비스 서비스 인터페이스
 *
 * =====================================================
 * [TOBE 변환 이력] 2026-04-03
 * =====================================================
 * ASIS 메서드를 전부 주석 처리하고 TOBE 메서드로 교체.
 *
 * [M플랫폼 코드 변경]
 *   X20 (가입중 부가서비스 조회) → X97 (가입중인 부가서비스 조회, 확장판)
 *   X21 (부가서비스 신청)        → Y25 (상품변경처리 multi)
 *   X38 (부가서비스 해지)        → X38 그대로 유지
 *
 * [파라미터 변경]
 *   ASIS: String ncn, String ctn, String custId (낱개) + MyPageSearchDto (세션)
 *   TOBE: AdditionReqDto / AdditionApplyReqDto (요청 바디)
 *
 * [반환값 변경]
 *   ASIS: Map<String,Object>, MpAddSvcInfoParamDto, MpRegSvcChgVO (혼재)
 *   TOBE: AdditionMyListResVO / AdditionAvailableResVO / AdditionApplyResVO (명시적 VO)
 * =====================================================
 */

import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.form.servicechange.dto.AdditionReqDto;

public interface MsfRegSvcService {

    // =====================================================
    // TOBE 메서드
    // =====================================================

    /**
     * 이용중 부가서비스 목록 조회
     *
     * 처리 흐름:
     *   [1] M플랫폼 X97 호출 → 가입중인 부가서비스 SOC 목록 수신
     *   [2] "PL249Q800" 더미 SOC 제거 (아무나SOLO 내부 관리용 더미)
     *   [3] 각 SOC → MSP_RATE_MST@DL_MSP 조회 → canCmnt(해지 안내 문구), onlineCanYn(온라인 해지 가능 여부) 세팅
     *       → Constants.REG_SVC_CD_4(포인트할인) 강제 onlineCanYn="N"
     *
     * ASIS 대응: selectmyAddSvcList() — X97 사용, MyPageSearchDto(세션) 의존
     *
     * @param req 공통 요청 (ncn, ctn, custId)
     * @return AdditionMyListResVO (이용중 SOC 목록)
     */
    AdditionMyListResVO getMyAddSvcList(AdditionReqDto req);

    /**
     * 가입가능 부가서비스 목록 조회
     *
     * 처리 흐름:
     *   [1] M플랫폼 X97 호출 → 이용중 SOC 목록 추출
     *   [2] MSF DB(selectRegService) → 전체 부가서비스 관리 목록 조회
     *   [3] useSocList 기준 useYn("Y"/"N") 매핑
     *   [4] "PL249Q800" 더미 SOC 필터링
     *   [5] listA(유료: baseAmt≠0 또는 svcRelTp≠C/B) / listC(무료: baseAmt=0&C, or B) 분리
     *
     * ASIS 대응: selectAddSvcInfoDto() — X20 사용 → TOBE에서 X97로 교체
     *
     * @param req 공통 요청 (ncn, ctn, custId)
     * @return AdditionAvailableResVO (list/listA/listC)
     */
    AdditionAvailableResVO getAvailableAddSvcList(AdditionReqDto req);

    /**
     * 부가서비스 해지
     *
     * 처리 흐름:
     *   [1] MSP_RATE_MST@DL_MSP 조회 → null이면 NO_EXSIST_RATE 오류
     *   [2] onlineCanYn ≠ "Y"이면 NO_ONLINE_CAN_CHANGE_ADD 오류 (고객센터 해지 안내)
     *   [3] prodHstSeq 있으면 X38 moscRegSvcCanChgSeq (이력 기반 해지)
     *       없으면 X38 moscRegSvcCanChg (단순 해지)
     *
     * ASIS 대응: moscRegSvcCanChg() / moscRegSvcCanChgSeq() — MyPageSearchDto 의존, Map 반환
     *
     * @param req 신청 요청 (ncn/ctn/custId/soc/prodHstSeq(선택))
     * @return AdditionApplyResVO (success=true/false, message)
     */
    AdditionApplyResVO cancelAddSvc(AdditionApplyReqDto req);

    /**
     * 부가서비스 신청
     *
     * 처리 흐름:
     *   [1] flag="Y"이면 선해지(cancelAddSvc) → 실패 시 즉시 반환
     *   [2] M플랫폼 Y25(상품변경처리 multi) 호출 → 신청 완료
     *
     * [미이관 항목]
     *   - 인증 STEP 검증 (certService.getStepCnt/vdlCertInfo) — 공통 미구현 (31번 §1-3)
     *   - 포인트 처리 (pointService.editPoint) — 포인트 기능 미이관
     *
     * ASIS 대응: regSvcChg() — X21 사용 → TOBE에서 Y25로 교체, 세션/인증/포인트 제거
     *
     * @param req 신청 요청 (ncn/ctn/custId/soc/ftrNewParam/flag)
     *            flag="Y": 선해지 후 신청(변경), flag≠"Y": 바로 신청
     * @return AdditionApplyResVO (success=true/false, message)
     */
    AdditionApplyResVO regAddSvc(AdditionApplyReqDto req);

    // =====================================================
    // [ASIS] 기존 메서드 — TOBE 전환 완료로 주석 처리 (삭제 금지)
    // =====================================================

    // [ASIS] X20 사용 → getAvailableAddSvcList()로 대체
    // List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId);

    // [ASIS] X21 사용 → regAddSvc()로 대체
    // MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam)
    //         throws SocketTimeoutException;

    // [ASIS] X97 사용, MyPageSearchDto 의존 → getMyAddSvcList()로 대체
    // MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId);
    // MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId, String lstComActvDate);

    // [ASIS] Map 반환, MyPageSearchDto 의존 → cancelAddSvc()로 대체
    // Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd)
    //         throws SocketTimeoutException;
    // Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq)
    //         throws SocketTimeoutException;

    // [ASIS] 내부 유틸 메서드 — ServiceImpl private 메서드로 이동 (인터페이스 노출 불필요)
    // void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd);
    // void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd);
    // String getUpdateYn(MpSocVO mpSoc);
    // String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd);
    // String getCtnByNcn(String ncn, boolean flagMasking);
}
