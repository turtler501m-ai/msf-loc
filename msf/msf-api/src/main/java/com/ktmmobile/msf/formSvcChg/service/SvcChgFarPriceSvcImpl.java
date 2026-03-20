package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPriceChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPriceResvInfoVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgFarPriceReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 요금제 변경 서비스 구현.
 * ASIS FarPricePlanServiceImpl.selectFarPricePlanYn(X20기반), moscFarPriceChg(X19),
 * farPricePlanChgNeTrace(X84), doFarPricePlanRsrvChg(X88), doFarPricePlanRsrvSearch(X89),
 * doFarPricePlanRsrvCancel(X90) 와 동일.
 */
@Service
public class SvcChgFarPriceSvcImpl implements SvcChgFarPriceSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgFarPriceSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * 변경 가능 요금제 목록 조회.
     * ASIS: X20 현재 가입 SOC 조회 → 변경 가능 목록은 DB(MSF_CD_DTL/FarPricePlan) 기준.
     * 현재는 X20 응답의 현재 요금제 SOC만 반환하고, 변경 가능 요금제 목록은 프론트 설정 기준.
     */
    @Override
    public Map<String, Object> getFarPriceList(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        try {
            MpAddSvcInfoDto addSvcInfo = mplatFormSvc.getAddSvcInfoDto(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("success", addSvcInfo.isSuccess());
            result.put("currentSocList", addSvcInfo.getList());
            if (!addSvcInfo.isSuccess()) {
                result.put("message", addSvcInfo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("요금제 목록 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "요금제 목록 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * X89 현재 요금제 예약변경 조회.
     * ASIS: FarPricePlanServiceImpl.doFarPricePlanRsrvSearch(X89).
     */
    @Override
    public Map<String, Object> getFarPriceReservation(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        try {
            MpFarPriceResvInfoVO vo = mplatFormSvc.farPriceResvInfo(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("success", vo.isSuccess());
            result.put("hasReservation", vo.isHasReservation());
            result.put("resvSoc", vo.getResvSoc());
            result.put("resvSocNm", vo.getResvSocNm());
            result.put("resvApplyDt", vo.getResvApplyDt());
            if (!vo.isSuccess()) {
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X89 요금제 예약조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "예약 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 요금제 변경 처리.
     * schedule="immediate" → X19 즉시변경 / schedule="reserve" → X88 예약변경(익월1일).
     * ASIS: moscFarPriceChg(X19) / doFarPricePlanRsrvChg(X88).
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
        try {
            MpFarPriceChgVO vo;
            boolean isReserve = "reserve".equals(req.getSchedule());
            if (isReserve) {
                vo = mplatFormSvc.farPriceResvChg(
                    req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            } else {
                vo = mplatFormSvc.farPriceChg(
                    req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            }
            result.put("success", vo.isSuccess());
            result.put("resultCode", vo.getResultCode());
            result.put("globalNo", vo.getGlobalNo());
            result.put("rsltMsg", vo.getRsltMsg());
            result.put("message", vo.isSuccess()
                ? (isReserve ? "요금제 예약변경이 완료되었습니다." : "요금제 변경이 완료되었습니다.")
                : vo.getSvcMsg());
        } catch (Exception e) {
            logger.error("요금제 변경 처리 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "요금제 변경 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * X90 요금제 예약변경 취소.
     * ASIS: FarPricePlanServiceImpl.doFarPricePlanRsrvCancel(X90).
     */
    @Override
    public Map<String, Object> cancelFarPriceReservation(SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        try {
            MpFarPriceChgVO vo = mplatFormSvc.farPriceResvCancel(
                req.getNcn(), req.getCtn(), req.getCustId());
            result.put("success", vo.isSuccess());
            result.put("resultCode", vo.getResultCode());
            result.put("globalNo", vo.getGlobalNo());
            result.put("message", vo.isSuccess()
                ? "요금제 예약변경이 취소되었습니다."
                : vo.getSvcMsg());
        } catch (Exception e) {
            logger.error("X90 요금제 예약취소 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "예약 취소 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
