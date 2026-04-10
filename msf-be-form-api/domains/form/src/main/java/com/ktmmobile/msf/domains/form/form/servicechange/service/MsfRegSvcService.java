package com.ktmmobile.msf.domains.form.form.servicechange.service;

/**
 * 부가서비스 서비스 인터페이스
 *
 * =====================================================
 * [TOBE 변환 이력] 2026-04-03
 * =====================================================
 * ASIS 메서드를 전부 주석 처리하고 TOBE 메서드로 교체.
 *
 * [함수명 규칙 — ASIS 함수명 최대한 유지]
 *   Service : ASIS 함수명과 동일하게 유지 (파라미터·반환값 시그니처만 TOBE로 교체)
 *   Controller : ASIS 함수명에서 "Ajax" 접미사만 제거
 *
 * [ASIS → TOBE 함수명 대응]
 *   selectmyAddSvcList  → selectMyAddSvcList  (대소문자 정규화)
 *   selectAddSvcInfoDto → selectAddSvcInfoDto (동일)
 *   moscRegSvcCanChg    → moscRegSvcCanChg    (동일)
 *   regSvcChg           → regSvcChg           (동일)
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

import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionReqDto;

public interface MsfRegSvcService {

    // =====================================================
    // TOBE 메서드
    // =====================================================

    /**
     * 이용중 부가서비스 목록 조회
     * ASIS: selectmyAddSvcList(String ncn, String ctn, String custId)
     *
     * 처리 흐름:
     *   [1] M플랫폼 X97 호출 → 가입중인 부가서비스 SOC 목록 수신
     *   [2] "PL249Q800" 더미 SOC 제거 (아무나SOLO 내부 관리용 더미)
     *   [3] 각 SOC → MSP_RATE_MST@DL_MSP 조회 → canCmnt(해지 안내 문구), onlineCanYn(온라인 해지 가능 여부) 세팅
     *       → Constants.REG_SVC_CD_4(포인트할인) 강제 onlineCanYn="N"
     *
     * 시그니처 변경:
     *   ASIS: MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId)
     *   TOBE: AdditionMyListResVO   selectMyAddSvcList(AdditionReqDto req)
     *
     * @param req 공통 요청 (ncn, ctn, custId)
     * @return AdditionMyListResVO (이용중 SOC 목록)
     */
    AdditionMyListResVO selectMyAddSvcList(AdditionReqDto req);

    /**
     * 가입가능 부가서비스 목록 조회
     * ASIS: selectAddSvcInfoDto(String ncn, String ctn, String custId)
     *
     * 처리 흐름:
     *   [1] M플랫폼 X97 호출 → 이용중 SOC 목록 추출 (ASIS X20 → TOBE X97 교체)
     *   [2] MSF DB(selectRegService) → 전체 부가서비스 관리 목록 조회
     *   [3] useSocList 기준 useYn("Y"/"N") 매핑
     *   [4] "PL249Q800" 더미 SOC 필터링
     *   [5] listA(유료) / listC(무료: baseAmt=0&C, or B) 분리
     *
     * 시그니처 변경:
     *   ASIS: List<String>         selectAddSvcInfoDto(String ncn, String ctn, String custId)
     *   TOBE: AdditionAvailableResVO selectAddSvcInfoDto(AdditionReqDto req)
     *
     * @param req 공통 요청 (ncn, ctn, custId)
     * @return AdditionAvailableResVO (list/listA/listC)
     */
    AdditionAvailableResVO selectAddSvcInfoDto(AdditionReqDto req);

    /**
     * 부가서비스 해지
     * ASIS: moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd)
     *       moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq)
     *
     * 처리 흐름:
     *   [1] MSP_RATE_MST@DL_MSP 조회 → null이면 NO_EXSIST_RATE 오류
     *   [2] onlineCanYn ≠ "Y"이면 NO_ONLINE_CAN_CHANGE_ADD 오류 (고객센터 해지 안내)
     *   [3] prodHstSeq 있으면 X38 moscRegSvcCanChgSeq (이력 기반 해지)
     *       없으면 X38 moscRegSvcCanChg (단순 해지)
     *
     * 시그니처 변경:
     *   ASIS: Map<String,Object>  moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd)
     *   TOBE: AdditionApplyResVO  moscRegSvcCanChg(AdditionApplyReqDto req)
     *   (moscRegSvcCanChgSeq는 prodHstSeq를 req에 포함시켜 단일 메서드로 통합)
     *
     * @param req 신청 요청 (ncn/ctn/custId/soc/prodHstSeq(선택))
     * @return AdditionApplyResVO (success=true/false, message)
     */
    AdditionApplyResVO moscRegSvcCanChg(AdditionApplyReqDto req);

    /**
     * 부가서비스 신청
     * ASIS: regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam)
     *
     * 처리 흐름:
     *   [1] flag="Y"이면 선해지(moscRegSvcCanChg) → 실패 시 즉시 반환
     *   [2] M플랫폼 Y25(상품변경처리 multi) 호출 → 신청 완료
     *
     * [미이관 항목]
     *   - 인증 STEP 검증 (certService.getStepCnt/vdlCertInfo) — 공통 미구현 (31번 §1-3)
     *   - 포인트 처리 (pointService.editPoint) — 포인트 기능 미이관
     *
     * 시그니처 변경:
     *   ASIS: MpRegSvcChgVO      regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam)
     *   TOBE: AdditionApplyResVO regSvcChg(AdditionApplyReqDto req)
     *
     * @param req 신청 요청 (ncn/ctn/custId/soc/ftrNewParam/flag)
     *            flag="Y": 선해지 후 신청(변경), flag≠"Y": 바로 신청
     * @return AdditionApplyResVO (success=true/false, message)
     */
    AdditionApplyResVO regSvcChg(AdditionApplyReqDto req);

    // =====================================================
    // [ASIS] 기존 메서드 — TOBE 전환 완료로 주석 처리 (삭제 금지)
    // =====================================================

    // [ASIS] X20 사용, List<String> 반환 → selectAddSvcInfoDto(AdditionReqDto)로 대체
    // List<String> selectAddSvcInfoDto(String ncn, String ctn, String custId);

    // [ASIS] X21 사용, MpRegSvcChgVO 반환 → regSvcChg(AdditionApplyReqDto)로 대체
    // MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam)
    //         throws SocketTimeoutException;

    // [ASIS] X97 사용, MyPageSearchDto(세션) 의존 → selectMyAddSvcList(AdditionReqDto)로 대체
    // MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId);
    // MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId, String lstComActvDate);

    // [ASIS] Map 반환, MyPageSearchDto(세션) 의존 → moscRegSvcCanChg(AdditionApplyReqDto)로 대체
    // (prodHstSeq는 AdditionApplyReqDto 필드로 통합)
    // Map<String, Object> moscRegSvcCanChg(MyPageSearchDto searchVO, String rateAdsvcCd)
    //         throws SocketTimeoutException;
    // Map<String, Object> moscRegSvcCanChgSeq(MyPageSearchDto searchVO, String rateAdsvcCd, String prodHstSeq)
    //         throws SocketTimeoutException;

    // [ASIS] 내부 유틸 메서드 → ServiceImpl private으로 이동 (인터페이스 노출 불필요)
    // void getMpSocListByDiv(List<MpSocVO> mpSocList, String addDivCd);
    // void getMcpRegServiceListByDiv(List<McpRegServiceDto> mcpRegServiceList, String addDivCd);
    // String getUpdateYn(MpSocVO mpSoc);
    // String getEndDttmUsePrd(MpSocVO mpSoc, String usePrd);
    // String getCtnByNcn(String ncn, boolean flagMasking);
}
