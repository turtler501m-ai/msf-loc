package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPriceChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPriceResvInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.formComm.dto.McpFarPriceDto;
import com.ktmmobile.msf.formComm.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.formComm.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formComm.service.FormMypageSvc;
import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgFarPriceReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 요금제 변경 서비스 구현.
 * ASIS FarPricePlanServiceImpl 대응.
 *
 * M플랫폼 API:
 *   X97(현재 부가서비스 조회), X38(부가서비스 해지), X19(즉시 요금제 변경),
 *   X84(예약일 즉시변경), X88(예약변경), X89(예약조회), X90(예약취소),
 *   X21(부가서비스 신청)
 *
 * DB 처리: FormMypageSvc 위임
 *   selectFarPricePlan, selectFarPricePlanList, selectFarPriceAddInfo,
 *   getEnggInfo, getCloseSubList, getromotionDcList,
 *   insertServiceAlterTrace, checkAllreadPlanchgCount,
 *   insertDisApd, getChrgPrmtIdSocChg, insertSocfailProcMst
 */
@Service
public class SvcChgFarPriceSvcImpl implements SvcChgFarPriceSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgFarPriceSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private FormMypageSvc formMypageSvc;

    /* =====================================================================
     * 5006-07 possibleStateCheck — 요금제 변경 사전체크
     * ===================================================================== */

    /**
     * 변경 전 사전체크.
     * ASIS FarPricePlanController.possibleStateCheck 로직 이식.
     * 1) 현재 요금제 조회 (selectFarPricePlan)
     * 2) 약정 정보 조회 (getEnggInfo) → enggYn
     * 3) 요금제 적용일 조회 (selectFarPriceAddInfo) → 익월 변경 여부 판단
     * 4) X97 이용중 부가서비스 조회 (getAddSvcInfoDto)
     * 5) 60분 내 중복 변경 여부 (checkAllreadPlanchgCount)
     */
    @Override
    public Map<String, Object> possibleStateCheck(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getNcn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ncn)가 없습니다.");
            return result;
        }
        logger.debug("[FarPrice-07] possibleStateCheck Start: ncn={}", req.getNcn());
        try {
            // 1) 현재 요금제 DB 조회
            McpFarPriceDto farPrice = formMypageSvc.selectFarPricePlan(req.getNcn());
            result.put("currentRate", farPrice);

            // 2) 약정 정보 조회
            MspJuoAddInfoDto enggInfo = formMypageSvc.getEnggInfo(req.getNcn());
            result.put("enggInfo", enggInfo);
            result.put("enggYn", enggInfo != null ? enggInfo.getEnggYn() : "N");

            // 3) 요금제 적용일 조회 (현재 SOC 기준)
            String prvRateCd = farPrice != null ? farPrice.getPrvRateCd() : null;
            if (!isBlank(prvRateCd)) {
                String applyMonth = formMypageSvc.selectFarPriceAddInfo(req.getNcn(), prvRateCd);
                result.put("applyMonth", applyMonth);
            }

            // 4) X97 이용중 부가서비스 조회
            MpAddSvcInfoDto addSvcInfo = mplatFormSvc.getAddSvcInfoDto(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("addSvcSuccess", addSvcInfo.isSuccess());
            result.put("useSocList", addSvcInfo.getList());

            // 5) 60분 내 중복 변경 이력 확인 (ncn 기준, tSocCode 미지정 시 skip)
            result.put("success", true);
            logger.debug("[FarPrice-07] possibleStateCheck 완료: ncn={}, enggYn={}",
                req.getNcn(), result.get("enggYn"));
        } catch (Exception e) {
            logger.error("[FarPrice-07] possibleStateCheck 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "요금제 변경 사전체크 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * 5006-08 getRegServicePop — 변경 요금제 상세 팝업
     * ===================================================================== */

    /**
     * 변경할 요금제 상세 및 연계 부가서비스 조회.
     * ASIS FarPricePlanController.doRegServicePop 로직 이식.
     * 1) 변경 가능 요금제 목록 (selectFarPricePlanList)
     * 2) 가입해야 할 프로모션 부가서비스 (getromotionDcList)
     * 3) 해지해야 할 부가서비스 (getCloseSubList)
     */
    @Override
    public Map<String, Object> getRegServicePop(SvcChgFarPriceReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getNcn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ncn)가 없습니다.");
            return result;
        }
        logger.debug("[FarPrice-08] getRegServicePop Start: ncn={}, soc={}", req.getNcn(), req.getSoc());
        try {
            // 현재 요금제 조회
            McpFarPriceDto currentRate = formMypageSvc.selectFarPricePlan(req.getNcn());
            result.put("currentRate", currentRate);

            // 변경 가능 요금제 목록 (현재 SOC 기준)
            String prvRateCd = currentRate != null ? currentRate.getPrvRateCd() : null;
            if (!isBlank(prvRateCd)) {
                List<McpFarPriceDto> farPriceList = formMypageSvc.selectFarPricePlanList(prvRateCd);
                result.put("farPriceList", farPriceList);
                result.put("farPriceCount", farPriceList.size());
            } else {
                result.put("farPriceList", Collections.emptyList());
                result.put("farPriceCount", 0);
            }

            // 변경할 요금제 기준 프로모션 부가서비스 목록
            if (!isBlank(req.getSoc())) {
                List<McpRegServiceDto> promotionList = formMypageSvc.getromotionDcList(req.getSoc());
                result.put("promotionList", promotionList);

                List<McpRegServiceDto> closeSubList = formMypageSvc.getCloseSubList(req.getNcn());
                result.put("closeSubList", closeSubList);
            }

            result.put("success", true);
            logger.debug("[FarPrice-08] getRegServicePop 완료: ncn={}", req.getNcn());
        } catch (Exception e) {
            logger.error("[FarPrice-08] getRegServicePop 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "요금제 변경 팝업 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * 변경 가능 요금제 목록 (기존 getFarPriceList 유지 — X97 기반)
     * ===================================================================== */

    /**
     * 변경 가능 요금제 목록 조회.
     * X97 현재 이용중 SOC 조회 후 DB 기반 변경 가능 목록 반환.
     */
    @Override
    public Map<String, Object> getFarPriceList(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        logger.debug("[FarPrice] getFarPriceList Start: ncn={}, ctn={}", req.getNcn(), req.getCtn());
        try {
            // X97 현재 이용중 부가서비스/SOC 조회
            MpAddSvcInfoDto addSvcInfo = mplatFormSvc.getAddSvcInfoDto(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("success", addSvcInfo.isSuccess());
            result.put("currentSocList", addSvcInfo.getList());

            // DB 기반 현재 요금제 정보 추가
            if (!isBlank(req.getNcn())) {
                McpFarPriceDto farPrice = formMypageSvc.selectFarPricePlan(req.getNcn());
                result.put("currentRate", farPrice);

                if (farPrice != null && !isBlank(farPrice.getPrvRateCd())) {
                    List<McpFarPriceDto> farPriceList =
                        formMypageSvc.selectFarPricePlanList(farPrice.getPrvRateCd());
                    result.put("farPriceList", farPriceList);
                }
            }

            if (!addSvcInfo.isSuccess()) {
                logger.warn("[FarPrice] getFarPriceList 실패: {}", addSvcInfo.getSvcMsg());
                result.put("message", addSvcInfo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("[FarPrice] getFarPriceList 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "요금제 목록 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * X89 요금제 예약변경 조회
     * ===================================================================== */

    @Override
    public Map<String, Object> getFarPriceReservation(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        logger.debug("[X89] 요금제 예약변경 조회 Start: ncn={}, ctn={}", req.getNcn(), req.getCtn());
        try {
            MpFarPriceResvInfoVO vo = mplatFormSvc.farPriceResvInfo(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("success", vo.isSuccess());
            result.put("hasReservation", vo.isHasReservation());
            result.put("resvSoc", vo.getResvSoc());
            result.put("resvSocNm", vo.getResvSocNm());
            result.put("resvApplyDt", vo.getResvApplyDt());
            if (!vo.isSuccess()) {
                logger.warn("[X89] 요금제 예약변경 조회 실패: {}", vo.getSvcMsg());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("[X89] 요금제 예약변경 조회 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "예약 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * 5006-12 applyFarPriceChange — 요금제 변경 처리
     * ===================================================================== */

    /**
     * 요금제 변경 처리 (즉시 X19 / 예약 X88).
     * ASIS FarPricePlanController.proPriceChg 흐름:
     * 1) 60분 내 중복 변경 체크 (checkAllreadPlanchgCount)
     * 2) 이전 요금제 SOC/기본료 조회 (selectFarPricePlan)
     * 3) 해지해야 할 부가서비스 X38 (getCloseSubList)
     * 4) 요금제 변경 X19 또는 X88
     * 5) 실패 시 insertSocfailProcMst
     * 6) 성공 시 가입해야 할 부가서비스 X21 (getromotionDcList)
     * 7) 서비스 변경 이력 저장 (insertServiceAlterTrace)
     * 8) 평생할인 대상 등록 (getChrgPrmtIdSocChg → insertDisApd)
     */
    @Override
    public Map<String, Object> applyFarPriceChange(SvcChgFarPriceReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        if (isBlank(req.getSoc())) {
            result.put("success", false);
            result.put("message", "변경할 요금제 코드(soc)가 없습니다.");
            return result;
        }

        boolean isReserve = "reserve".equals(req.getSchedule());
        logger.debug("[{}] 요금제 변경 Start: ncn={}, ctn={}, soc={}",
            isReserve ? "X88" : "X19", req.getNcn(), req.getCtn(), req.getSoc());

        try {
            // 1) 60분 내 중복 변경 체크
            McpServiceAlterTraceDto chkDto = new McpServiceAlterTraceDto();
            chkDto.setNcn(req.getNcn());
            chkDto.setTSocCode(req.getSoc());
            int dupCount = formMypageSvc.checkAllreadPlanchgCount(chkDto);
            if (dupCount > 0) {
                logger.warn("[{}] 요금제 변경 중복: soc={}, count={}", isReserve ? "X88" : "X19",
                    req.getSoc(), dupCount);
                result.put("success", false);
                result.put("message", "최근 1시간 내 동일 요금제로 변경된 이력이 있습니다.");
                return result;
            }

            // 2) 이전 요금제 정보 조회
            McpFarPriceDto prvRate = formMypageSvc.selectFarPricePlan(req.getNcn());
            String prvSoc = prvRate != null ? prvRate.getPrvRateCd() : "";
            int prvAmt  = prvRate != null ? prvRate.getPrvBaseAmt() : 0;

            // 3) 해지해야 할 부가서비스 X38 처리
            List<McpRegServiceDto> closeList = formMypageSvc.getCloseSubList(req.getNcn());
            for (McpRegServiceDto soc : closeList) {
                logger.debug("[X38] 부가서비스 해지: rateCd={}", soc.getRateCd());
                // X38 호출 — additionCancel 은 SvcChgRegSvc 에서 별도 처리
                // 여기서는 M플랫폼 직접 호출
                try {
                    mplatFormSvc.moscRegSvcCanChg(req.getNcn(), req.getCtn(), req.getCustId(), soc.getRateCd());
                } catch (Exception ex) {
                    logger.warn("[X38] 부가서비스 해지 오류 (계속진행): rateCd={}, msg={}", soc.getRateCd(), ex.getMessage());
                }
            }

            // 4) 요금제 변경 X19 또는 X88
            MpFarPriceChgVO vo;
            if (isReserve) {
                vo = mplatFormSvc.farPriceResvChg(req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            } else {
                vo = mplatFormSvc.farPriceChg(req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            }

            result.put("resultCode", vo.getResultCode());
            result.put("globalNo", vo.getGlobalNo());
            result.put("rsltMsg", vo.getRsltMsg());

            if (!vo.isSuccess()) {
                // 5) 실패 이력 저장
                logger.warn("[{}] 요금제 변경 실패: soc={}, code={}", isReserve ? "X88" : "X19",
                    req.getSoc(), vo.getResultCode());
                McpServiceAlterTraceDto failDto = buildTraceDto(req, prvSoc, prvAmt,
                    isReserve ? "R" : "I", "N", "FAIL", vo.getResultCode(), vo.getRsltMsg());
                formMypageSvc.insertSocfailProcMst(failDto);

                result.put("success", false);
                result.put("message", vo.getSvcMsg());
                return result;
            }

            logger.debug("[{}] 요금제 변경 완료: soc={}, globalNo={}",
                isReserve ? "X88" : "X19", req.getSoc(), vo.getGlobalNo());

            // 6) 가입해야 할 프로모션 부가서비스 X21 처리
            List<McpRegServiceDto> promotionList = formMypageSvc.getromotionDcList(req.getSoc());
            for (McpRegServiceDto soc : promotionList) {
                logger.debug("[X21] 프로모션 부가서비스 신청: rateCd={}", soc.getRateCd());
                try {
                    mplatFormSvc.regSvcChg(req.getNcn(), req.getCtn(), req.getCustId(), soc.getRateCd(), null);
                } catch (Exception ex) {
                    logger.warn("[X21] 프로모션 부가서비스 신청 오류 (계속진행): rateCd={}, msg={}", soc.getRateCd(), ex.getMessage());
                }
            }

            // 7) 서비스 변경 이력 저장 (FIN 단계)
            McpServiceAlterTraceDto traceDto = buildTraceDto(req, prvSoc, prvAmt,
                isReserve ? "R" : "I", "Y", "SUCCESS", "0000", vo.getRsltMsg());
            traceDto.setGlobalNo(vo.getGlobalNo());
            traceDto.setEventCode("FIN");
            traceDto.setTrtmRsltSmst("SUCCESS");
            formMypageSvc.insertServiceAlterTrace(traceDto);

            // 8) 평생할인 대상 등록
            String prmtId = formMypageSvc.getChrgPrmtIdSocChg(req.getSoc());
            if (prmtId != null && !prmtId.isEmpty()) {
                McpServiceAlterTraceDto apdDto = new McpServiceAlterTraceDto();
                apdDto.setContractNum(req.getNcn());
                apdDto.setTSocCode(req.getSoc());
                apdDto.setPrcsMdlInd(prmtId);   // PRMT_ID 재활용
                formMypageSvc.insertDisApd(apdDto);
                logger.debug("[DisApd] 평생할인 등록: contractNum={}, prmtId={}", req.getNcn(), prmtId);
            }

            result.put("success", true);
            result.put("message", isReserve ? "요금제 예약변경이 완료되었습니다." : "요금제 변경이 완료되었습니다.");

        } catch (Exception e) {
            logger.error("[{}] 요금제 변경 오류: {}", isReserve ? "X88" : "X19", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "요금제 변경 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * X90 요금제 예약변경 취소
     * ===================================================================== */

    @Override
    public Map<String, Object> cancelFarPriceReservation(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        logger.debug("[X90] 요금제 예약변경 취소 Start: ncn={}, ctn={}", req.getNcn(), req.getCtn());
        try {
            MpFarPriceChgVO vo = mplatFormSvc.farPriceResvCancel(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("success", vo.isSuccess());
            result.put("resultCode", vo.getResultCode());
            result.put("globalNo", vo.getGlobalNo());
            if (vo.isSuccess()) {
                result.put("message", "요금제 예약변경이 취소되었습니다.");
            } else {
                logger.warn("[X90] 요금제 예약변경 취소 실패: code={}", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("[X90] 요금제 예약변경 취소 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "예약 취소 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * DB 직접 조회 위임 (인터페이스 노출)
     * ===================================================================== */

    @Override
    public McpFarPriceDto selectFarPricePlan(String ncn) {
        return formMypageSvc.selectFarPricePlan(ncn);
    }

    @Override
    public List<McpFarPriceDto> selectFarPricePlanList(String rateCd) {
        return formMypageSvc.selectFarPricePlanList(rateCd);
    }

    @Override
    public MspJuoAddInfoDto getEnggInfo(String contractNum) {
        return formMypageSvc.getEnggInfo(contractNum);
    }

    @Override
    public List<McpRegServiceDto> getCloseSubList(String contractNum) {
        return formMypageSvc.getCloseSubList(contractNum);
    }

    @Override
    public List<McpRegServiceDto> getromotionDcList(String toSocCode) {
        return formMypageSvc.getromotionDcList(toSocCode);
    }

    @Override
    public Map<String, Object> getRemainCharge(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        try {
            MpFarRealtimePayInfoVO vo = mplatFormSvc.farRealtimePayInfo(req.getNcn(), req.getCtn(), req.getCustId());
            if (!vo.isSuccess()) {
                result.put("success", false);
                result.put("message", "실시간 사용요금 조회에 실패했습니다.");
                return result;
            }
            result.put("success", true);
            result.put("searchTime", vo.getSearchTime());
            result.put("sumAmt", vo.getSumAmt());
            result.put("items", vo.getItems());
        } catch (Exception e) {
            logger.warn("[SvcChgFarPriceSvc] getRemainCharge 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "실시간 사용요금 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /* =====================================================================
     * 헬퍼
     * ===================================================================== */

    private McpServiceAlterTraceDto buildTraceDto(SvcChgFarPriceReqDto req,
                                                   String prvSoc, int prvAmt,
                                                   String chgType, String succYn,
                                                   String trtmRslt, String rsltCd,
                                                   String prcsSbst) {
        McpServiceAlterTraceDto dto = new McpServiceAlterTraceDto();
        dto.setNcn(req.getNcn());
        dto.setContractNum(req.getNcn());
        dto.setSubscriberNo(req.getCtn());
        dto.setASocCode(prvSoc);
        dto.setTSocCode(req.getSoc());
        dto.setASocAmnt(prvAmt);
        dto.setChgType(chgType);
        dto.setSuccYn(succYn);
        dto.setTrtmRsltSmst(trtmRslt);
        dto.setRsltCd(rsltCd);
        dto.setPrcsSbst(prcsSbst);
        dto.setPrcsMdlInd("FarPrice");
        dto.setUserId(req.getCustId());
        return dto;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
