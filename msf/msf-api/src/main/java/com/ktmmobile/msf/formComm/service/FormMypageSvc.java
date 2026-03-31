package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.McpFarPriceDto;
import com.ktmmobile.msf.formComm.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.formComm.dto.McpSubInfoDto;
import com.ktmmobile.msf.formComm.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;

import java.util.List;
import java.util.Map;

/**
 * ASIS MypageService 중 TOBE 필요 함수 모음.
 * 요금제 변경(5006), 비회원 계약조회, 공통 DB 조회에 사용되는 mypage 공통 서비스.
 */
public interface FormMypageSvc {

    /* =====================================================================
     * 요금제 변경 — 조회
     * ===================================================================== */

    /**
     * 현재 계약의 요금제 정보 조회 (현재 적용 요금제 코드 + 기본료).
     * ASIS: mypageService.selectFarPricePlan → MypageMapper.selectFarPricePlan.
     * 5006-07 possibleStateCheck, 5006-12 proPriceChg 에서 사용.
     */
    McpFarPriceDto selectFarPricePlan(String ncn);

    /**
     * 현재 요금제 기준 변경 가능 요금제 목록 조회.
     * ASIS: mypageService.selectFarPricePlanList → MypageMapper.selectFarPricePlanList.
     * VW_GROUP_BY_RATE_INFO@DL_MSP (prvRateCd 기준 nxtRate 목록).
     */
    List<McpFarPriceDto> selectFarPricePlanList(String rateCd);

    /**
     * 변경 가능 요금제 수 조회.
     * ASIS: mypageService.countFarPricePlanList → MypageMapper.countFarPricePlanList.
     */
    int countFarPricePlanList(String rateCd);

    /**
     * 요금제 변경 적용 시작월(YYYYMM) 조회.
     * ASIS: mypageService.selectFarPriceAddInfo → MypageMapper.selectFarPriceAddInfo.
     * 5006-07 possibleStateCheck 에서 익월 변경 대상 여부 판단에 사용.
     *
     * @param cntrNo 계약번호
     * @param soc    현재 요금제 SOC 코드
     */
    String selectFarPriceAddInfo(String cntrNo, String soc);

    /* =====================================================================
     * 약정 / 부가서비스
     * ===================================================================== */

    /**
     * 약정 정보 조회 (enggYn 약정여부, appEndDate 종료일).
     * ASIS: mypageService.getEnggInfo → MypageMapper.getEnggInfo (VW_CNTR_ENGG_SELF@DL_MSP).
     * 5006-07 possibleStateCheck 에서 사용.
     */
    MspJuoAddInfoDto getEnggInfo(String contractNum);

    /**
     * 요금제 변경 시 함께 해지해야 할 부가서비스 목록.
     * ASIS: mypageService.getCloseSubList → MypageMapper.getCloseSubList.
     * 5006-12 proPriceChg 에서 X38 호출 전 사용.
     */
    List<McpRegServiceDto> getCloseSubList(String contractNum);

    /**
     * 변경할 요금제에 자동 가입해야 할 프로모션 부가서비스 목록.
     * ASIS: mypageService.getromotionDcList → MypageMapper.getromotionDcList.
     * 5006-12 proPriceChg 에서 X21 호출 대상 산출에 사용.
     */
    List<McpRegServiceDto> getromotionDcList(String toSocCode);

    /* =====================================================================
     * 서비스 변경 이력
     * ===================================================================== */

    /**
     * 서비스 변경 이력 저장 (MSF_SERVICE_ALTER_TRACE).
     * ASIS: mypageService.insertServiceAlterTrace → MypageMapper.insertServiceAlterTrace.
     * 5006-12 요금제 변경 처리 전/후 단계별로 호출.
     */
    void insertServiceAlterTrace(McpServiceAlterTraceDto dto);

    /**
     * 최근 60분 내 동일 요금제로 변경 성공 이력 수 조회.
     * ASIS: mypageService.checkAllreadPlanchgCount → MypageMapper.checkAllreadPlanchgCount.
     * 5006-12 proPriceChg 중복처리 방지에 사용.
     */
    int checkAllreadPlanchgCount(McpServiceAlterTraceDto dto);

    /* =====================================================================
     * 평생할인
     * ===================================================================== */

    /**
     * 요금제 변경 후 평생할인 프로모션 대상 등록 (MSP_DIS_APD@DL_MSP).
     * ASIS: mypageService.insertDisApd → MypageMapper.insertDisApd.
     * 5006-12 요금제 변경 성공 후 호출.
     * dto.tSocCode = 변경한 요금제 코드, dto.prcsMdlInd = PRMT_ID (있을 경우).
     */
    void insertDisApd(McpServiceAlterTraceDto dto);

    /**
     * 변경할 요금제의 온라인 평생할인 프로모션 ID 조회.
     * ASIS: mypageService.getChrgPrmtIdSocChg → MypageMapper.getChrgPrmtIdSocChg.
     * 5006-12 proPriceChg 에서 insertDisApd 전 프로모션 ID 확인에 사용.
     */
    String getChrgPrmtIdSocChg(String rateCd);

    /* =====================================================================
     * 요금제 변경 실패 처리
     * ===================================================================== */

    /**
     * 요금제 변경 실패 이력 MERGE (MSP_SOCFAIL_PROC_MST@DL_MSP).
     * ASIS: mypageService.insertSocfailProcMst → MypageMapper.insertSocfailProcMst.
     * 5006-12 M플랫폼 호출 실패 시 호출.
     */
    void insertSocfailProcMst(McpServiceAlterTraceDto dto);

    /* =====================================================================
     * 비회원 계약 조회 / QoS
     * ===================================================================== */

    /**
     * 비회원 계약 현행화 정보 조회 (svcCntrNo / cntrMobileNo / userName 조건).
     * ASIS: mypageService.selectCntrListNoLogin → MypageMapper.selectCntrListNoLogin.
     * MSP_JUO_SUB_INFO@DL_MSP + MSP_JUO_CUS_INFO@DL_MSP.
     */
    McpSubInfoDto selectCntrListNoLogin(McpSubInfoDto param);

    /**
     * 요금제 QoS 데이터/음성 정보 조회.
     * ASIS: mypageService.selectRateMst → MypageMapper.selectRateMst.
     * NMCP_RATE_MST → MSF_RATE_MST. qosDataCntDesc, callCnt 반환.
     */
    Map<String, Object> selectRateMst(String rateCd);
}
