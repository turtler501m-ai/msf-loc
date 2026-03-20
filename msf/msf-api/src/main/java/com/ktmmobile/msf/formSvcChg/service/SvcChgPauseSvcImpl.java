package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpPcsLostInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSuspenCnlPosInfoInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSuspenPosHisVO;
import com.ktmmobile.msf.formSvcChg.dto.PauseApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.PauseCheckReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 분실복구/일시정지해제 서비스 구현.
 * ASIS mPlatFormServiceImpl.suspenPosHis/suspenCnlPosInfo/suspenCnlChgIn/pcsLostInfo/pcsLostCnlChg 와 동일.
 */
@Service
public class SvcChgPauseSvcImpl implements SvcChgPauseSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgPauseSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * X26 일시정지이력 + X28 해제가능여부 + X33 분실신고가능여부 통합 조회.
     * ASIS: PauseController.getSuspenPosInfoAjax → suspenPosHis + suspenCnlPosInfo / pcsLostInfo
     */
    @Override
    public Map<String, Object> check(PauseCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ncn, ctn)가 없습니다.");
            return result;
        }
        try {
            // X26 일시정지 이력 조회
            MpSuspenPosHisVO suspenHis = mplatFormSvc.suspenPosHis(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getTermGubun());

            // X28 일시정지 해제 가능 여부
            MpSuspenCnlPosInfoInVO suspenCnlPos = mplatFormSvc.suspenCnlPosInfo(
                req.getNcn(), req.getCtn(), req.getCustId());

            // X33 분실신고 가능 여부
            MpPcsLostInfoVO lostInfo = mplatFormSvc.pcsLostInfo(
                req.getNcn(), req.getCtn(), req.getCustId());

            result.put("success", true);
            result.put("suspenHistSuccess", suspenHis.isSuccess());
            result.put("suspenCnlPossible", suspenCnlPos.isSuccess());
            result.put("lostInfoSuccess", lostInfo.isSuccess());
            result.put("message", "조회 완료");
        } catch (Exception e) {
            logger.error("분실복구/일시정지해제 조회 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * 분실복구(X35) 또는 일시정지해제(X30) 처리.
     * ASIS: PauseController.suspenCnlChgInAjax(X30) / pcsLostChgAjax(X35)
     * applyType: "PAUSE_CNL" → X30 일시정지해제, "LOST_CNL" → X35 분실복구.
     */
    @Override
    public Map<String, Object> apply(PauseApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ncn, ctn)가 없습니다.");
            return result;
        }
        if (isBlank(req.getApplyType())) {
            result.put("success", false);
            result.put("message", "처리 유형(applyType)을 입력해 주세요. (PAUSE_CNL 또는 LOST_CNL)");
            return result;
        }
        try {
            boolean ok;
            if ("LOST_CNL".equals(req.getApplyType())) {
                // X35 분실복구
                ok = mplatFormSvc.pcsLostCnlChg(
                    req.getNcn(), req.getCtn(), req.getCustId(),
                    req.getPassword(), req.getPwdType());
                if (ok) {
                    result.put("message", "분실복구 처리가 완료되었습니다.");
                } else {
                    result.put("message", "분실복구 처리에 실패했습니다.");
                }
            } else if ("PAUSE_CNL".equals(req.getApplyType())) {
                // X30 일시정지해제
                ok = mplatFormSvc.suspenCnlChgIn(
                    req.getNcn(), req.getCtn(), req.getCustId(),
                    req.getPwdType(), req.getPassword());
                if (ok) {
                    result.put("message", "일시정지해제 처리가 완료되었습니다.");
                } else {
                    result.put("message", "일시정지해제 처리에 실패했습니다.");
                }
            } else {
                result.put("success", false);
                result.put("message", "알 수 없는 처리 유형입니다: " + req.getApplyType());
                return result;
            }
            result.put("success", ok);
        } catch (Exception e) {
            logger.error("분실복구/일시정지해제 처리 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
