package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpUsimChangeVO;
import com.ktmmobile.msf.common.mplatform.vo.MpUsimCheckVO;
import com.ktmmobile.msf.formSvcChg.dto.UsimChangeReqDto;
import com.ktmmobile.msf.formSvcChg.dto.UsimCheckReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * USIM 변경 서비스 구현.
 * ASIS AppformController moscIntmMgmtAjax(X85) / usimChangeUC0() 와 동일 흐름.
 */
@Service
public class SvcChgUsimSvcImpl implements SvcChgUsimSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgUsimSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /**
     * X85 USIM 유효성 체크.
     * ASIS: AppformController /msp/moscIntmMgmtAjax.do → MplatFormService.moscIntmMgmtSO(X85)
     * usimNo(ICCID) 입력 → 유효한 USIM 번호인지 확인.
     */
    @Override
    public Map<String, Object> checkUsim(UsimCheckReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getUsimNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, usimNo)");
            return result;
        }

        try {
            MpUsimCheckVO vo = mplatFormSvc.moscIntmMgmtSO(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getUsimNo());

            if (vo.isSuccess()) {
                result.put("success", true);
                result.put("resultCode", vo.getResultCode());
                result.put("globalNo", vo.getGlobalNo());
                result.put("usimNo", vo.getUsimNo());
                result.put("usimSts", vo.getUsimSts());
                result.put("usimStsCd", vo.getUsimStsCd());
                result.put("usimType", vo.getUsimType());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("resultCode", vo.getResultCode());
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X85 USIM 유효성 체크 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "USIM 유효성 확인 중 오류가 발생했습니다.");
        }
        return result;
    }

    /**
     * UC0 USIM 변경 처리.
     * ASIS: AppformSvcImpl.usimChangeUC0()
     * 처리 순서: X85 유효성 확인 → UC0 USIM 변경.
     */
    @Override
    public Map<String, Object> changeUsim(UsimChangeReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getNcn()) || isBlank(req.getCtn()) || isBlank(req.getNewUsimNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터가 누락되었습니다. (ncn, ctn, newUsimNo)");
            return result;
        }

        try {
            // 1. X85 새 USIM 유효성 사전 확인
            MpUsimCheckVO chkVo = mplatFormSvc.moscIntmMgmtSO(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getNewUsimNo());

            if (!chkVo.isSuccess()) {
                result.put("success", false);
                result.put("resultCode", chkVo.getResultCode());
                result.put("message", "USIM 번호가 유효하지 않습니다: " + chkVo.getSvcMsg());
                return result;
            }

            // 2. UC0 USIM 변경
            MpUsimChangeVO changeVo = mplatFormSvc.usimChangeUC0(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getNewUsimNo());

            if (changeVo.isSuccess()) {
                result.put("success", true);
                result.put("resultCode", changeVo.getResultCode());
                result.put("globalNo", changeVo.getGlobalNo());
                result.put("message", "");
            } else {
                result.put("success", false);
                result.put("resultCode", changeVo.getResultCode());
                result.put("message", changeVo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("UC0 USIM 변경 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "USIM 변경 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
