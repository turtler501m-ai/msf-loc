package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpDataSharingResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgShareDataReqDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 데이터쉐어링 서비스 구현.
 * ASIS MyShareDataSvcImpl.mosharingList(X71), moscDataSharingChk(X69), moscDataSharingSave(X70) 와 동일.
 */
@Service
public class SvcChgShareDataSvcImpl implements SvcChgShareDataSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgShareDataSvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    /** X71 데이터쉐어링 결합 중인 대상 조회. */
    @Override
    public Map<String, Object> list(SvcChgShareDataReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        try {
            MpDataSharingResVO vo = mplatFormSvc.mosharingList(
                req.getCustId(), req.getNcn(), req.getCtn());
            result.put("success", vo.isSuccess());
            result.put("items", vo.getItems());
            if (!vo.isSuccess()) {
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X71 데이터쉐어링 목록 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "목록 조회 중 오류가 발생했습니다.");
        }
        return result;
    }

    /** X69 데이터쉐어링 사전체크. */
    @Override
    public Map<String, Object> check(SvcChgShareDataReqDto req) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        try {
            MpDataSharingResVO vo = mplatFormSvc.moscDataSharingChk(
                req.getCustId(), req.getNcn(), req.getCtn());
            result.put("success", vo.isSuccess());
            result.put("items", vo.getItems());
            if (!vo.isSuccess()) {
                result.put("message", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("X69 데이터쉐어링 사전체크 오류: {}", e.getMessage());
            result.put("success", false);
            result.put("message", "사전체크 중 오류가 발생했습니다.");
        }
        return result;
    }

    /** X70 데이터쉐어링 가입 (A). */
    @Override
    public Map<String, Object> join(SvcChgShareDataReqDto req) {
        return dataSharingSave(req, "A");
    }

    /** X70 데이터쉐어링 해지 (C). */
    @Override
    public Map<String, Object> cancel(SvcChgShareDataReqDto req) {
        return dataSharingSave(req, "C");
    }

    private Map<String, Object> dataSharingSave(SvcChgShareDataReqDto req, String workDiv) {
        Map<String, Object> result = new HashMap<>();
        if (req == null || isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ctn)가 없습니다.");
            return result;
        }
        if (isBlank(req.getOpmdSvcNo())) {
            result.put("success", false);
            result.put("message", "데이터쉐어링 서비스번호(opmdSvcNo)가 없습니다.");
            return result;
        }
        try {
            boolean ok = mplatFormSvc.moscDataSharingSave(
                req.getCustId(), req.getNcn(), req.getCtn(),
                req.getOpmdSvcNo(), workDiv);
            result.put("success", ok);
            result.put("message", ok
                ? ("A".equals(workDiv) ? "데이터쉐어링 가입이 완료되었습니다." : "데이터쉐어링 해지가 완료되었습니다.")
                : ("A".equals(workDiv) ? "데이터쉐어링 가입에 실패했습니다." : "데이터쉐어링 해지에 실패했습니다."));
        } catch (Exception e) {
            logger.error("X70 데이터쉐어링 처리 오류({}): {}", workDiv, e.getMessage());
            result.put("success", false);
            result.put("message", "처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
