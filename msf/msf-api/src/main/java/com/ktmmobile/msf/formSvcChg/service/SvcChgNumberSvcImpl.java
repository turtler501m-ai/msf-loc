package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpNumberChangeVO;
import com.ktmmobile.msf.common.mplatform.vo.MpPhoneNoListVO;
import com.ktmmobile.msf.common.mplatform.vo.SvcChgValdChkVO;
import com.ktmmobile.msf.formSvcChg.dto.NumberChangeReqDto;
import com.ktmmobile.msf.formSvcChg.dto.NumberReserveReqDto;
import com.ktmmobile.msf.formSvcChg.dto.NumberSearchReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 번호변경 서비스 구현.
 * ASIS AppformController searchNumberAjax(NU1) / setNumberAjax(NU2) / numChgeChg(X32) 와 동일 흐름.
 */
@Service
public class SvcChgNumberSvcImpl implements SvcChgNumberSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgNumberSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * NU1 희망번호 조회.
     * ASIS: AppformSvcImpl.getPhoneNoList(resNo, EVENT_CODE_SEARCH_NUMBER)
     * 입력 패턴(wantNo)으로 사용 가능한 번호 목록 반환.
     * 최대 20회 조회 제한은 프론트엔드에서 관리.
     */
    @Override
    public List<String> searchNewNumber(NumberSearchReqDto req) {
        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            logger.warn("NU1 번호조회: 필수 파라미터 누락");
            return new ArrayList<>();
        }

        try {
            MpPhoneNoListVO vo = mplatFormSvc.requestPhoneNoList(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getWantNo());

            if (vo.isSuccess()) {
                return vo.getPhoneNoList();
            }
            logger.warn("NU1 번호조회 실패: resultCode={}", vo.getResultCode());
        } catch (Exception e) {
            logger.error("NU1 번호조회 오류: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * NU2 희망번호 예약.
     * ASIS: AppformController.setNumber() → sendOsstService(NU2, WORK_CODE_RES)
     */
    @Override
    public Map<String, Object> reserveNumber(NumberReserveReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getTlphNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, tlphNo)");
            return result;
        }

        try {
            SvcChgValdChkVO vo = mplatFormSvc.reservePhoneNo(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getTlphNo());

            if (vo.isSuccess()) {
                result.put("success", true);
                result.put("resultCode", vo.getResultCode());
                result.put("globalNo", vo.getGlobalNo());
                result.put("reservedTlphNo", req.getTlphNo());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("NU2 번호예약 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "번호예약 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * NU2 희망번호 예약 취소.
     * ASIS: AppformController.cancelNumberAjax() → sendOsstService(NU2, WORK_CODE_RES_CANCEL)
     */
    @Override
    public Map<String, Object> cancelReservedNumber(NumberReserveReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn)");
            return result;
        }

        try {
            SvcChgValdChkVO vo = mplatFormSvc.cancelReservedPhoneNo(
                req.getNcn(), req.getCtn(), req.getCustId(),
                req.getTlphNo() != null ? req.getTlphNo() : "");

            result.put("success", vo.isSuccess());
            result.put("resultCode", vo.getResultCode());
            result.put("globalNo", vo.getGlobalNo());
            result.put("message", vo.isSuccess() ? "" : vo.getSvcMsg());
        } catch (Exception e) {
            logger.error("NU2 번호예약취소 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "번호예약 취소 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * X32 번호변경 처리.
     * ASIS: AppformController → MplatFormService.numChgeChg(X32)
     * 번호변경은 NU1(조회) → NU2(예약) → X32(변경) 순서로 처리.
     */
    @Override
    public Map<String, Object> changeNumber(NumberChangeReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getNewTlphNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, newTlphNo)");
            return result;
        }

        try {
            MpNumberChangeVO vo = mplatFormSvc.numChgeChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getNewTlphNo());

            if (vo.isSuccess()) {
                result.put("success", true);
                result.put("resultCode", vo.getResultCode());
                result.put("globalNo", vo.getGlobalNo());
                result.put("newTlphNo", vo.getNewTlphNo() != null ? vo.getNewTlphNo() : req.getNewTlphNo());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X32 번호변경 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "번호변경 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
