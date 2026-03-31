package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpOsstIpinCiVO;
import com.ktmmobile.msf.common.mplatform.vo.MpOsstPhoneNoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpOsstSimpleVO;
import com.ktmmobile.msf.formSvcChg.dto.DataSharingApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.DataSharingOsstDto;
import com.ktmmobile.msf.formSvcChg.mapper.DataSharingApplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 서비스 구현.
 * ASIS AppformController(saveDataSharingSimple/Step1~3) + AppformSvcImpl 로직 이식.
 * TOBE: 세션 없음(stateless) — requestKey/resNo 클라이언트가 매 요청에 전달.
 */
@Service
public class DataSharingApplySvcImpl implements DataSharingApplySvc {

    private static final Logger logger = LoggerFactory.getLogger(DataSharingApplySvcImpl.class);

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private DataSharingApplyMapper applyMapper;

    // ─────────────────────────────────────────────────────────────────────
    // [Step0] 신청서 저장
    // ASIS: saveDataSharingSimple + fnSetDataOfdataSharing + saveAppform
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> apply(DataSharingApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        // 1. 입력값 검증
        if (isBlank(req.getNcn()) || isBlank(req.getReqUsimSn())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(ncn, reqUsimSn)가 없습니다.");
            return result;
        }

        try {
            // 2. MSF_REQUEST INSERT
            Map<String, Object> params = new HashMap<>();
            params.put("ncn",          req.getNcn());
            params.put("ctn",          req.getCtn());
            params.put("custId",       req.getCustId());
            params.put("reqUsimSn",    req.getReqUsimSn());
            params.put("agentCode",    nvl(req.getAgentCode(), "0"));
            params.put("cntpntShopId", nvl(req.getCntpntShopId(), ""));

            applyMapper.insertMsfRequest(params);

            Long requestKey = (Long) params.get("requestKey");
            // RES_NO = LPAD(requestKey, 14, '0')
            String resNo = String.format("%014d", requestKey);

            // 3. MSF_REQUEST_OSST INSERT (초기 레코드)
            DataSharingOsstDto osstDto = new DataSharingOsstDto();
            osstDto.setRequestKey(requestKey);
            osstDto.setMvnoOrdNo(resNo);
            osstDto.setSvcCntrNo(req.getNcn());   // SVC_CNTR_NO NOT NULL — 모회선 NCN
            osstDto.setPrgrStatCd("00");            // 초기상태

            applyMapper.insertMsfRequestOsst(osstDto);

            result.put("success",    true);
            result.put("requestKey", requestKey);
            result.put("resNo",      resNo);
            logger.info("[DataSharingApply][Step0] 신청서 저장 완료: requestKey={}, resNo={}", requestKey, resNo);

        } catch (Exception e) {
            logger.error("[DataSharingApply][Step0] 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "신청서 저장 중 오류가 발생했습니다.");
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────────────
    // [Step1] PC0 사전체크 및 고객생성 (OSST)
    // ASIS: saveDataSharingStep1
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> step1(DataSharingApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(requestKey, resNo)가 없습니다.");
            return result;
        }

        try {
            // 신청서 조회 (모회선 NCN 확인)
            Map<String, Object> reqRow = applyMapper.getMsfRequest(req.getRequestKey());
            if (reqRow == null) {
                result.put("success", false);
                result.put("message", "신청서를 찾을 수 없습니다.");
                return result;
            }
            String prntsContractNum = nvlStr(reqRow.get("prntsContractNum"));
            String custId           = nvl(req.getCustId(), "");

            // OSST에서 이미 PC2 성공 기록이 있으면 스킵 (방어로직)
            DataSharingOsstDto osstRow = applyMapper.getMsfRequestOsst(req.getRequestKey());
            if (osstRow != null && MplatFormSvc.EVENT_CODE_PC2.equals(osstRow.getPrgrStatCd())) {
                result.put("success",    true);
                result.put("requestKey", req.getRequestKey());
                result.put("resNo",      req.getResNo());
                result.put("message",    "이미 PC2 처리 완료.");
                return result;
            }

            // 3초 대기 후 PC0 호출
            sleepSafe(3000);

            Map<String, String> osstParam = new HashMap<>();
            osstParam.put("resNo",           req.getResNo());
            osstParam.put("prntsContractNo", prntsContractNum);
            osstParam.put("custNo",          custId);

            MpOsstSimpleVO vo = mplatFormSvc.sendOsstService(osstParam, MplatFormSvc.EVENT_CODE_PC0);

            if (vo.isSuccess()) {
                // MSF_REQUEST_OSST UPDATE
                DataSharingOsstDto upd = new DataSharingOsstDto();
                upd.setRequestKey(req.getRequestKey());
                upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_PC0);
                upd.setOsstOrdNo(vo.getOsstOrdNo());
                upd.setRsltCd(vo.getRsltCd());
                applyMapper.updateMsfRequestOsst(upd);

                result.put("success",    true);
                result.put("requestKey", req.getRequestKey());
                result.put("resNo",      req.getResNo());
                logger.info("[DataSharingApply][Step1] PC0 성공: requestKey={}", req.getRequestKey());
            } else {
                result.put("success",   false);
                result.put("RESULT_CODE", "1001");
                result.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_PC0);
                result.put("ERROR_MSG",   vo.getResultCode());
                result.put("message",     vo.getSvcMsg());
                logger.warn("[DataSharingApply][Step1] PC0 실패: {}", vo.getSvcMsg());
            }
        } catch (Exception e) {
            logger.error("[DataSharingApply][Step1] 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "PC0 사전체크 중 오류가 발생했습니다.");
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────────────
    // [Step2] PC2 폴링 + Y39 CI 조회
    // ASIS: conPreCheck + chkRealPc2Result + MoscSvcContService(Y39)
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> step2(DataSharingApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(requestKey, resNo)가 없습니다.");
            return result;
        }

        try {
            // 신청서·OSST 조회
            Map<String, Object> reqRow = applyMapper.getMsfRequest(req.getRequestKey());
            if (reqRow == null) {
                result.put("success", false);
                result.put("message", "신청서를 찾을 수 없습니다.");
                return result;
            }
            String prntsContractNum = nvlStr(reqRow.get("prntsContractNum"));
            String custId           = nvl(req.getCustId(), "");

            DataSharingOsstDto osstRow = applyMapper.getMsfRequestOsst(req.getRequestKey());

            // --- chkRealPc2Result 로직 (ST1 폴링, 최대 2회, 5초 대기) ---
            Map<String, String> st1Param = new HashMap<>();
            st1Param.put("resNo",           req.getResNo());
            st1Param.put("prntsContractNo", prntsContractNum);
            st1Param.put("custNo",          custId);

            MpOsstSimpleVO st1Vo = null;
            boolean pc2Done = false;

            for (int i = 0; i < 2; i++) {
                sleepSafe(5000);
                st1Vo = mplatFormSvc.sendOsstSt1Service(st1Param);

                if (!st1Vo.isSuccess()) {
                    result.put("success",     false);
                    result.put("RESULT_CODE", "ERROR_001");
                    result.put("ERROR_MSG",   st1Vo.getSvcMsg());
                    return result;
                }

                if (!MplatFormSvc.OSST_SUCCESS.equals(st1Vo.getRsltCd())) {
                    result.put("success",         false);
                    result.put("RESULT_CODE",     "ERROR_002");
                    result.put("OSST_RESULT_CODE", "RESULT_" + st1Vo.getRsltCd());
                    result.put("ERROR_MSG",        st1Vo.getRsltMsg());
                    return result;
                }

                if (MplatFormSvc.EVENT_CODE_PC2.equals(st1Vo.getPrgrStatCd())) {
                    pc2Done = true;
                    break;
                }
                // prgrStatCd 가 PC2 가 아니면 재시도
                logger.info("[DataSharingApply][Step2] ST1 재시도 {}/2, prgrStatCd={}", i + 1, st1Vo.getPrgrStatCd());
            }

            if (!pc2Done) {
                result.put("success",     false);
                result.put("RESULT_CODE", "ERROR_003");
                result.put("ERROR_MSG",   "사전체크 처리 완료 결과 확인 불가");
                return result;
            }

            // --- Y39 CI 조회 ---
            String osstOrdNo = (osstRow != null) ? osstRow.getOsstOrdNo() : "";
            MpOsstIpinCiVO y39Vo = mplatFormSvc.moscSvcContService(osstOrdNo);

            if (y39Vo == null || !y39Vo.isSuccess()) {
                result.put("success",     false);
                result.put("RESULT_CODE", "ERROR_004");
                result.put("ERROR_MSG",   "Y39 CI 조회 실패");
                return result;
            }

            // MSF_REQUEST_OSST UPDATE — PC2 완료
            DataSharingOsstDto upd = new DataSharingOsstDto();
            upd.setRequestKey(req.getRequestKey());
            upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_PC2);
            upd.setRsltCd(MplatFormSvc.OSST_SUCCESS);
            applyMapper.updateMsfRequestOsst(upd);

            result.put("success",     true);
            result.put("prgrStatCd",  MplatFormSvc.EVENT_CODE_PC2);
            result.put("rsltCd",      MplatFormSvc.OSST_SUCCESS);
            result.put("ipinCi",      y39Vo.getIpinCi());
            logger.info("[DataSharingApply][Step2] PC2+Y39 완료: requestKey={}", req.getRequestKey());

        } catch (Exception e) {
            logger.error("[DataSharingApply][Step2] 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "PC2 폴링 중 오류가 발생했습니다.");
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────────────
    // [Step3] NU1 번호조회 + NU2 번호예약
    // ASIS: saveDataSharingStep2 + fnSearchNumber
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> step3(DataSharingApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(requestKey, resNo)가 없습니다.");
            return result;
        }

        try {
            // 신청서 조회
            Map<String, Object> reqRow = applyMapper.getMsfRequest(req.getRequestKey());
            if (reqRow == null) {
                result.put("success", false);
                result.put("message", "신청서를 찾을 수 없습니다.");
                return result;
            }
            DataSharingOsstDto osstRow = applyMapper.getMsfRequestOsst(req.getRequestKey());
            if (osstRow == null) {
                result.put("success", false);
                result.put("message", "OSST 레코드를 찾을 수 없습니다.");
                return result;
            }

            String prntsContractNum = nvlStr(reqRow.get("prntsContractNum"));
            String custId           = nvl(req.getCustId(), "");
            String ctn              = nvl(req.getCtn(), "");
            String agentCode        = nvlStr(reqRow.get("agentCode"));

            // NU1: 번호조회 (끝번호 → 중간번호 → Random 순서로 fallback)
            // ASIS: fnSearchNumber 로직 그대로
            MpOsstPhoneNoVO.PhoneNoItem selected = null;

            if (ctn.length() >= 4) {
                // 1) 끝번호 4자리
                selected = searchPhoneNo(req.getResNo(), prntsContractNum, custId,
                    ctn.substring(ctn.length() - 4));
            }

            if (selected == null && ctn.length() >= 8) {
                // 2) 중간번호 4자리
                selected = searchPhoneNo(req.getResNo(), prntsContractNum, custId,
                    ctn.substring(ctn.length() - 8, ctn.length() - 4));
            }

            if (selected == null) {
                // 3) Random 4자리
                String rand = randomFourDigits();
                selected = searchPhoneNo(req.getResNo(), prntsContractNum, custId, rand);
            }

            if (selected == null) {
                result.put("success",     false);
                result.put("RESULT_CODE", "3001");
                result.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_NU1);
                result.put("ERROR_MSG",   "조회 전화번호 없음");
                return result;
            }

            // MSF_REQUEST_OSST에 번호 정보 임시 저장
            DataSharingOsstDto nuReg = new DataSharingOsstDto();
            nuReg.setRequestKey(req.getRequestKey());
            nuReg.setTlphNo(selected.getTlphNo());
            nuReg.setTlphNoStatCd(selected.getTlphNoStatCd());
            nuReg.setEncdTlphNo(selected.getEncdTlphNo());
            nuReg.setTlphNoOwnCmpnCd(selected.getTlphNoOwnCmpnCd());
            nuReg.setAsgnAgncId(agentCode);
            nuReg.setOpenSvcIndCd("03");  // 03 고정 (3G)
            applyMapper.updateMsfRequestOsst(nuReg);

            // NU2: 번호 예약 (3초 대기)
            sleepSafe(3000);

            Map<String, String> nu2Param = new HashMap<>();
            nu2Param.put("resNo",           req.getResNo());
            nu2Param.put("gubun",           "RES");
            nu2Param.put("tlphNo",          selected.getTlphNo());
            nu2Param.put("prntsContractNo", prntsContractNum);
            nu2Param.put("custNo",          custId);

            MpOsstSimpleVO nu2Vo = mplatFormSvc.sendOsstService(nu2Param, MplatFormSvc.EVENT_CODE_NU2);

            if (nu2Vo.isSuccess()) {
                // prgrStatCd=NU2 업데이트
                DataSharingOsstDto nu2Upd = new DataSharingOsstDto();
                nu2Upd.setRequestKey(req.getRequestKey());
                nu2Upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_NU2);
                nu2Upd.setRsltCd(nu2Vo.getRsltCd());
                applyMapper.updateMsfRequestOsst(nu2Upd);

                result.put("success", true);
                result.put("tlphNo",  selected.getTlphNo());
                logger.info("[DataSharingApply][Step3] NU2 번호예약 완료: tlphNo={}", selected.getTlphNo());
            } else {
                result.put("success",     false);
                result.put("RESULT_CODE", "4001");
                result.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_NU2);
                result.put("ERROR_MSG",   nu2Vo.getResultCode());
                result.put("message",     nu2Vo.getSvcMsg());
                logger.warn("[DataSharingApply][Step3] NU2 실패: {}", nu2Vo.getSvcMsg());
            }

        } catch (Exception e) {
            logger.error("[DataSharingApply][Step3] 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "번호조회/예약 중 오류가 발생했습니다.");
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────────────
    // [Step4] OP0 개통 및 수납
    // ASIS: saveDataSharingStep3
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> step4(DataSharingApplyReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            result.put("success", false);
            result.put("message", "필수 파라미터(requestKey, resNo)가 없습니다.");
            return result;
        }

        try {
            Map<String, Object> reqRow = applyMapper.getMsfRequest(req.getRequestKey());
            if (reqRow == null) {
                result.put("success", false);
                result.put("message", "신청서를 찾을 수 없습니다.");
                return result;
            }
            DataSharingOsstDto osstRow = applyMapper.getMsfRequestOsst(req.getRequestKey());
            if (osstRow == null) {
                result.put("success", false);
                result.put("message", "OSST 레코드를 찾을 수 없습니다.");
                return result;
            }

            String prntsContractNum = nvlStr(reqRow.get("prntsContractNum"));
            String custId           = nvl(req.getCustId(), "");
            String billAcntNo       = nvl(req.getBillAcntNo(), "");

            // 3초 대기 후 OP0 호출
            sleepSafe(3000);

            Map<String, String> op0Param = new HashMap<>();
            op0Param.put("resNo",           req.getResNo());
            op0Param.put("billAcntNo",      billAcntNo);
            op0Param.put("prntsContractNo", prntsContractNum);
            op0Param.put("custNo",          custId);

            MpOsstSimpleVO op0Vo = mplatFormSvc.sendOsstService(op0Param, MplatFormSvc.EVENT_CODE_OP0);

            if (op0Vo.isSuccess()) {
                // MSF_REQUEST UPDATE — 개통번호 저장, 처리완료 상태
                Map<String, Object> reqUpd = new HashMap<>();
                reqUpd.put("requestKey", req.getRequestKey());
                reqUpd.put("openNo",     osstRow.getTlphNo());
                reqUpd.put("proCd",      "CP");  // 처리완료
                applyMapper.updateMsfRequest(reqUpd);

                // MSF_REQUEST_OSST UPDATE — OP0 완료
                DataSharingOsstDto op0Upd = new DataSharingOsstDto();
                op0Upd.setRequestKey(req.getRequestKey());
                op0Upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_OP0);
                op0Upd.setRsltCd(MplatFormSvc.OSST_SUCCESS);
                applyMapper.updateMsfRequestOsst(op0Upd);

                result.put("success", true);
                result.put("openNo",  osstRow.getTlphNo());
                logger.info("[DataSharingApply][Step4] OP0 개통 완료: openNo={}", osstRow.getTlphNo());
            } else {
                result.put("success",     false);
                result.put("RESULT_CODE", "5001");
                result.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_OP0);
                result.put("ERROR_MSG",   op0Vo.getResultCode());
                result.put("message",     op0Vo.getSvcMsg());
                logger.warn("[DataSharingApply][Step4] OP0 실패: {}", op0Vo.getSvcMsg());
            }

        } catch (Exception e) {
            logger.error("[DataSharingApply][Step4] 오류: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "개통 처리 중 오류가 발생했습니다.");
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────────────
    // 내부 유틸리티
    // ─────────────────────────────────────────────────────────────────────

    /**
     * NU1 번호조회 — ASIS fnSearchNumber 내부 로직.
     * reqWantNumber(끝번호 or 중간번호 or Random 4자리) 로 조회.
     */
    private MpOsstPhoneNoVO.PhoneNoItem searchPhoneNo(
            String resNo, String prntsContractNo, String custId, String reqWantNumber) {
        Map<String, String> nu1Param = new HashMap<>();
        nu1Param.put("resNo",           resNo);
        nu1Param.put("reqWantNumber",   reqWantNumber);
        nu1Param.put("prntsContractNo", prntsContractNo);
        nu1Param.put("custNo",          custId);

        MpOsstPhoneNoVO vo = mplatFormSvc.getPhoneNoList(nu1Param);
        List<MpOsstPhoneNoVO.PhoneNoItem> items = vo.getItems();
        if (items != null && !items.isEmpty()) {
            return items.get(0);
        }
        return null;
    }

    /** Random 4자리 숫자 문자열 생성 */
    private String randomFourDigits() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            return String.format("%04d", random.nextInt(10000));
        } catch (Exception e) {
            return String.valueOf((int)(Math.random() * 10000));
        }
    }

    private void sleepSafe(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String nvl(String s, String def) {
        return (s == null || s.trim().isEmpty()) ? def : s;
    }

    private static String nvlStr(Object o) {
        return o == null ? "" : String.valueOf(o);
    }
}
