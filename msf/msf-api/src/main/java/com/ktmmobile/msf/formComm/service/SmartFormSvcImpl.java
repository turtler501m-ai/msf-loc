package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpOsstIpinCiVO;
import com.ktmmobile.msf.common.mplatform.vo.MpOsstPhoneNoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpOsstSimpleVO;
import com.ktmmobile.msf.formComm.dto.FormSendReqDto;
import com.ktmmobile.msf.formComm.dto.SmartFormOsstDto;
import com.ktmmobile.msf.formComm.dto.SmartFormReqDto;
import com.ktmmobile.msf.formComm.mapper.SmartFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 서비스 구현.
 * ASIS AppformController 의 saveDataSharingSimple/Step1~3/conPreCheck/sendScan/saveDataSharing 이식.
 * TOBE: stateless — requestKey/resNo/ncn/custId 클라이언트가 매 요청에 전달.
 */
@Service
public class SmartFormSvcImpl implements SmartFormSvc {

    private static final Logger logger = LoggerFactory.getLogger(SmartFormSvcImpl.class);

    /** PC2 완료 상태 코드. ASIS EVENT_CODE_PC_RESULT */
    private static final String EVENT_CODE_PC_RESULT = MplatFormSvc.EVENT_CODE_PC2;
    /** OSST 성공 결과 코드. ASIS OSST_SUCCESS */
    private static final String OSST_SUCCESS         = MplatFormSvc.OSST_SUCCESS;
    /** 번호예약 작업 구분. ASIS WORK_CODE_RES */
    private static final String WORK_CODE_RES        = "RES";

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private SmartFormMapper smartFormMapper;

    @Autowired
    private FormCommRestSvc formCommRestSvc;

    // ─────────────────────────────────────────────────────────────────────
    // saveDataSharingSimple: DB저장 + 전체 OSST 자동 플로우
    // ASIS AppformController.saveDataSharingSimple()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> saveDataSharingSimple(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (isBlank(req.getNcn()) || isBlank(req.getReqUsimSn())) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "필수 파라미터(ncn, reqUsimSn)가 없습니다.");
            return rtnMap;
        }

        // [Step0] 신청서 저장
        // 두번 호출 하면 첫번째 요청에만 DB 저장한다.
        Map<String, Object> saveResult = saveDataSharing(req);
        if (!isAjaxSuccess(saveResult)) {
            return saveResult;
        }

        Long requestKey = (Long) saveResult.get("requestKey");
        String resNo    = (String) saveResult.get("RES_NO");
        req.setRequestKey(requestKey);
        req.setResNo(resNo);

        // [Step1] 사전체크 및 고객생성(PC0)
        Map<String, Object> step1 = saveDataSharingStep1(req);
        if (!isAjaxSuccess(step1)) {
            step1.put("step", "step1");
            return step1;
        }

        // [Step1-2] conPreCheck: ST1폴링(PC2) + Y39 CI 조회
        req.setPrgrStatCd(EVENT_CODE_PC_RESULT);
        Map<String, Object> preCheck = conPreCheck(req);
        if (!isAjaxSuccess(preCheck)) {
            preCheck.put("step", "conPreCheck");
            return preCheck;
        }

        // [Step2] 번호조회(NU1) + 번호예약(NU2)
        Map<String, Object> step2 = saveDataSharingStep2(req);
        if (!isAjaxSuccess(step2)) {
            step2.put("step", "step2");
            return step2;
        }

        // [Step3] 개통및수납(OP0)
        Map<String, Object> step3 = saveDataSharingStep3(req);
        if (!isAjaxSuccess(step3)) {
            step3.put("step", "step3");
            return step3;
        }

        rtnMap.put("RESULT_CODE", "S");
        rtnMap.put("success",     true);
        rtnMap.put("REQUEST_KET", requestKey);
        rtnMap.put("RES_NO",      resNo);
        rtnMap.put("TLPH_NO",     step2.get("TLPH_NO"));
        logger.info("[saveDataSharingSimple] 완료: requestKey={}, TLPH_NO={}", requestKey, step2.get("TLPH_NO"));
        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // sendScan: SCAN 서버 전송
    // ASIS AppformController.sendScan()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> sendScan(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (req.getRequestKey() == null) {
            rtnMap.put("RESULT_MSG",  "신청서 정보가 없습니다.");
            rtnMap.put("RESULT_CODE", "FAIL");
            return rtnMap;
        }

        // 신청서 정보 조회
        Map<String, Object> mcpRequest = smartFormMapper.getShareDataRequest(req.getRequestKey());
        if (mcpRequest == null) {
            rtnMap.put("RESULT_MSG",  "신청서 정보가 없습니다. ");
            rtnMap.put("RESULT_CODE", "FAIL");
            return rtnMap;
        }

        String appFormXmlYn = nvlStr(mcpRequest.get("appFormXmlYn"));

        if ("N".equals(appFormXmlYn)) {
            try {
                // SCAN 서버에 서식지 데이터 생성 및 전송
                FormSendReqDto sendReq = new FormSendReqDto();
                sendReq.setCustReqSeq(req.getRequestKey());
                sendReq.setReqType("DS");   // DS = DataSharing
                sendReq.setUserId(req.getName());
                formCommRestSvc.sendScan(sendReq);

                rtnMap.put("RESULT_CODE", "S");
            } catch (Exception e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                logger.error("[sendScan] 오류: {}", e.getMessage());
            }
        } else {
            if ("Y".equals(appFormXmlYn)) {
                rtnMap.put("RESULT_MSG", "이미 생성된 서식지입니다.");
            }
            rtnMap.put("RESULT_CODE", "FAIL22");
        }
        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // saveDataSharing: 신청서 저장 (OSST 미포함)
    // ASIS AppformController.saveDataSharing()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> saveDataSharing(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (isBlank(req.getNcn()) || isBlank(req.getReqUsimSn())) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG",  "필수 파라미터(ncn, reqUsimSn)가 없습니다.");
            return rtnMap;
        }

        logger.info("[saveDataSharing] onOffType={}", req.getContractNum());

        try {
            // MSF_REQUEST_SVC_CHG INSERT
            Map<String, Object> params = new HashMap<>();
            params.put("ncn",          req.getNcn());
            params.put("ctn",          nvl(req.getCtn(), ""));
            params.put("custId",       nvl(req.getCustId(), ""));
            params.put("name",         nvl(req.getName(), ""));
            params.put("reqUsimSn",    req.getReqUsimSn());
            params.put("agentCd",      nvl(req.getAgentCode(), "M0001"));
            params.put("managerCd",    "M0001");
            params.put("cntpntShopId", nvl(req.getCntpntShopId(), ""));
            params.put("cstmrType",    nvl(req.getCstmrType(), "NA"));

            smartFormMapper.insertShareDataRequest(params);

            Long requestKey = (Long) params.get("requestKey");
            String resNo    = String.format("%014d", requestKey);

            // MSF_REQUEST_OSST INSERT (초기 레코드)
            SmartFormOsstDto osstDto = new SmartFormOsstDto();
            osstDto.setRequestKey(requestKey);
            osstDto.setMvnoOrdNo(resNo);
            osstDto.setSvcCntrNo(req.getNcn());
            osstDto.setCustId(nvl(req.getCustId(), ""));
            osstDto.setPrgrStatCd("00");

            smartFormMapper.insertMsfRequestOsst(osstDto);

            // session.setAttribute("opmdSvcNo", "11111111");
            // TOBE: stateless - session 미사용

            rtnMap.put("RESULT_CODE", "S");
            rtnMap.put("REQUEST_KET", requestKey);
            rtnMap.put("RES_NO",      resNo);
            rtnMap.put("requestKey",  requestKey);  // saveDataSharingSimple 에서 사용
            logger.info("[saveDataSharing] 신청서 저장 완료: requestKey={}", requestKey);

        } catch (Exception e) {
            logger.error("[saveDataSharing] 오류: {}", e.getMessage(), e);
            rtnMap.put("RESULT_CODE", "999");
            rtnMap.put("RESULT_MSG",  "신청서 저장 중 오류가 발생했습니다.");
        }
        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // saveDataSharingStep1: 신청서 저장_데이터쉐어링 DATA 생성 + 사전체크 및 고객생성(PC0)
    // ASIS AppformController.saveDataSharingStep1()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> saveDataSharingStep1(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG",  "필수 파라미터(requestKey, resNo)가 없습니다.");
            return rtnMap;
        }

        // 신청서 조회 (모회선 NCN, 고객ID 확인)
        Map<String, Object> reqRow = smartFormMapper.getShareDataRequest(req.getRequestKey());
        if (reqRow == null) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG",  "신청서를 찾을 수 없습니다.");
            return rtnMap;
        }

        String certContractNum = nvlStr(reqRow.get("ncn"));
        String customerId      = nvlStr(reqRow.get("custId"));

        logger.info("[saveDataSharingStep1] certContractNum={}", certContractNum);
        logger.info("[saveDataSharingStep1] customerId={}", customerId);

        // 방어 로직 - PC2(EVENT_CODE_PC_RESULT) 성공 이력 확인
        SmartFormOsstDto chkDto = new SmartFormOsstDto();
        chkDto.setRequestKey(req.getRequestKey());
        chkDto.setPrgrStatCd(EVENT_CODE_PC_RESULT);
        int tryCount = smartFormMapper.requestOsstCount(chkDto);

        if (tryCount > 0) {
            // 성공 처리
            rtnMap.put("RESULT_CODE", "S");
            rtnMap.put("REQUEST_KET", req.getRequestKey());
            rtnMap.put("RES_NO",      req.getResNo());
            return rtnMap;
        }

        // PC0 ITL_SST_E0002
        // 접수받은 MVNO 접수번호가 있습니다 → 성공 처리
        tryCount = 0;
        chkDto.setPrgrStatCd(MplatFormSvc.EVENT_CODE_PC0);
        chkDto.setRsltCd("ITL_SST_E0002");
        tryCount = smartFormMapper.requestOsstCount(chkDto);

        if (tryCount > 0) {
            // 성공 처리
            rtnMap.put("RESULT_CODE", "S");
            rtnMap.put("REQUEST_KET", req.getRequestKey());
            rtnMap.put("RES_NO",      req.getResNo());
            return rtnMap;
        }

        // 2. 사전체크 및 고객생성(PC0)
        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디/모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam = new HashMap<>();
            osstParam.put("resNo",           req.getResNo());
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo",          customerId);

            MpOsstSimpleVO simpleOsstXmlVO = mplatFormSvc.sendOsstService(osstParam, MplatFormSvc.EVENT_CODE_PC0);

            if (simpleOsstXmlVO.isSuccess()) {
                SmartFormOsstDto upd = new SmartFormOsstDto();
                upd.setRequestKey(req.getRequestKey());
                upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_PC0);
                upd.setOsstOrdNo(simpleOsstXmlVO.getOsstOrdNo());
                upd.setRsltCd(simpleOsstXmlVO.getRsltCd());
                smartFormMapper.updateMsfRequestOsst(upd);

                rtnMap.put("RESULT_CODE", "S");
                rtnMap.put("REQUEST_KET", req.getRequestKey());
                rtnMap.put("RES_NO",      req.getResNo());
                logger.info("[saveDataSharingStep1] PC0 성공: requestKey={}", req.getRequestKey());
            } else {
                rtnMap.put("RESULT_CODE", "1001");
                rtnMap.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_PC0);
                rtnMap.put("RESULT_XML",  simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG",   simpleOsstXmlVO.getResultCode());
                logger.warn("[saveDataSharingStep1] PC0 실패: {}", simpleOsstXmlVO.getResultCode());
                return rtnMap;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            rtnMap.put("RESULT_CODE", "1005");
            rtnMap.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_PC0);
            rtnMap.put("ERROR_MSG",   "InterruptedException");
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "1005");
            rtnMap.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_PC0);
            rtnMap.put("ERROR_MSG",   "Exception");
            logger.error("[saveDataSharingStep1] 오류: {}", e.getMessage(), e);
            return rtnMap;
        }

        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // conPreCheck: 개통사전 체크 확인 (ST1/PC2 폴링) + Y39 CI 조회
    // ASIS AppformController.conPreCheck()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> conPreCheck(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (isBlank(req.getResNo())) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG",  "필수 파라미터(resNo)가 없습니다.");
            return rtnMap;
        }

        String prgrStatCd = nvl(req.getPrgrStatCd(), EVENT_CODE_PC_RESULT);

        // OSST 콜백 조회
        SmartFormOsstDto mcpRequestOsstDto = new SmartFormOsstDto();
        SmartFormOsstDto mcpRequestOsstDtoRtn = null;
        mcpRequestOsstDto.setMvnoOrdNo(req.getResNo());
        mcpRequestOsstDto.setPrgrStatCd(prgrStatCd);

        // 신청서에서 모회선 계약번호 조회 (ST1 연동 파라미터용)
        String certContractNum = "";
        if (req.getRequestKey() != null) {
            Map<String, Object> reqRow = smartFormMapper.getShareDataRequest(req.getRequestKey());
            if (reqRow != null) {
                certContractNum = nvlStr(reqRow.get("ncn"));
            }
            mcpRequestOsstDtoRtn = smartFormMapper.getMsfRequestOsst(req.getRequestKey());
        }

        if (mcpRequestOsstDtoRtn != null) {
            String rsltMsg = mcpRequestOsstDtoRtn.getRsltMsg();
            String rsltCd  = mcpRequestOsstDtoRtn.getRsltCd();
            rtnMap.put("RESULT_OBJ", mcpRequestOsstDtoRtn);
            rtnMap.put("RESULT_MSG", rsltMsg);

            if (EVENT_CODE_PC_RESULT.equals(prgrStatCd)) {
                if (OSST_SUCCESS.equals(rsltCd)) {
                    // ======= START : 사전체크 완료상태 조회 (ST1 연동으로 PC2 완료상태 조회) =======
                    // ** issue : 사전체크 완료 소켓전송 시간과 사전체크 작업 완료 후 MP측 DB반영 시간 텀 존재
                    // ** to-be : ST1 연동으로 PC2 완료상태 조회 > PC2 완료 확인 후 Y39 연동
                    Map<String, String> st1Param = new HashMap<>();
                    st1Param.put("resNo",           req.getResNo());
                    st1Param.put("prntsContractNo", certContractNum);
                    st1Param.put("custNo",          nvl(req.getCustId(), ""));

                    Map<String, String> prcSchMap = chkRealPc2Result(st1Param);
                    if (!"S".equals(prcSchMap.get("RESULT_CODE"))) {
                        // 연동오류 또는 사전체크 완료 상태 확인 불가
                        rtnMap.put("RESULT_CODE", prcSchMap.get("RESULT_CODE"));
                        rtnMap.put("EVENT_CODE",  "PRE_SCH");
                        rtnMap.put("RESULT_MSG",  nvl(prcSchMap.get("ERROR_MSG"), "공통 오류가 발생하였습니다."));
                        return rtnMap;
                    }
                    // ======= END : 사전체크 완료상태 조회 =======

                    // ======= START : Y39 아이핀 CI 조회(마이알뜰폰) =======
                    MpOsstIpinCiVO mpSvcContIpinVO = null;
                    try {
                        mpSvcContIpinVO = mplatFormSvc.moscSvcContService(mcpRequestOsstDtoRtn.getOsstOrdNo());
                    } catch (Exception e) {
                        logger.info("Y39 Exception [{}]", e.getMessage());
                    }

                    if (mpSvcContIpinVO == null || !mpSvcContIpinVO.isSuccess()) {
                        // PC2는 정상적으로 성공했으므로 OSST 상태 그대로 유지
                        rtnMap.put("RESULT_CODE", "0002");
                        rtnMap.put("RESULT_MSG",  "공통 오류가 발생하였습니다.");
                        return rtnMap;
                    }
                    // ======= END : Y39 아이핀 CI 조회 =======

                    // OSST UPDATE (PC2 완료)
                    if (req.getRequestKey() != null) {
                        SmartFormOsstDto upd = new SmartFormOsstDto();
                        upd.setRequestKey(req.getRequestKey());
                        upd.setPrgrStatCd(EVENT_CODE_PC_RESULT);
                        upd.setRsltCd(OSST_SUCCESS);
                        smartFormMapper.updateMsfRequestOsst(upd);
                    }

                    rtnMap.put("RESULT_CODE", "S");
                    rtnMap.put("RESULT_MSG",  "확인완료");
                    rtnMap.put("ipinCi",      mpSvcContIpinVO.getIpinCi());
                    logger.info("[conPreCheck] PC2+Y39 완료: requestKey={}", req.getRequestKey());

                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    if ("2202".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("연동오류")) {
                        rsltCd = "2202_01";
                    } else if ("2412".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("BF1005")) {
                        rsltCd = "2412_01";
                    } else if ("2412".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("BF1004")) {
                        rsltCd = "2412_02";
                    } else if ("2412".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("BF2001")) {
                        rsltCd = "2412_03";
                    } else if ("2412".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("BF1010")) {
                        rsltCd = "2412_04";
                    } else if ("2412".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("BF1029")) {
                        rsltCd = "2412_04";
                    } else if ("7003".equals(rsltCd)) {
                        rsltCd = "7003";
                    }
                    rtnMap.put("OSST_RESULT_CODE", "RESULT_" + rsltCd);
                }
            } else if (MplatFormSvc.EVENT_CODE_OP0.equals(prgrStatCd)) {
                if (OSST_SUCCESS.equals(rsltCd)) {
                    rtnMap.put("RESULT_CODE", "S");
                    rtnMap.put("RESULT_MSG",  "확인완료");
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    if ("2028".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("유효기간")) {
                        rsltCd = "2028_01";
                    } else if ("2028".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("카드번호")) {
                        rsltCd = "2028_01";
                    } else if ("2028".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("입금계좌오류")) {
                        rsltCd = "2028_01";
                    } else if ("9000".equals(rsltCd) && rsltMsg != null && rsltMsg.contains("ACTE0003")) {
                        rsltCd = "9000_01";
                    }
                    rtnMap.put("OSST_RESULT_CODE", "RESULT_" + rsltCd);
                }
            }

        } else {
            // OSST 레코드 없음 — 아직 처리 중
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("OSST_RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", "아직 정보가 없습니다. 잠시후 다시 시도 하여 주시기 바랍니다.");
        }
        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // saveDataSharingStep2: 번호조회(NU1) + 번호예약/취소(NU2)
    // ASIS AppformController.saveDataSharingStep2()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> saveDataSharingStep2(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG",  "필수 파라미터(requestKey, resNo)가 없습니다.");
            return rtnMap;
        }

        // 신청서 조회
        Map<String, Object> reqRow = smartFormMapper.getShareDataRequest(req.getRequestKey());
        if (reqRow == null) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG",  "신청서를 찾을 수 없습니다.");
            return rtnMap;
        }

        // OSST 이력 조회 (osstOrdNo 필요)
        SmartFormOsstDto sessRequestOsstDto = smartFormMapper.getMsfRequestOsst(req.getRequestKey());
        if (sessRequestOsstDto == null) {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_MSG",  "OSST 레코드를 찾을 수 없습니다.");
            return rtnMap;
        }

        String certContractNum = nvlStr(reqRow.get("ncn"));
        String customerId      = nvlStr(reqRow.get("custId"));
        String ctn             = nvl(req.getCtn(), nvlStr(reqRow.get("ctn")));
        String agentCode       = nvlStr(reqRow.get("agentCode"));

        // 4. 번호조회(NU1) — 끝번호 → 중간번호 → Random
        String tlphNo          = "";
        String tlphNoStatCd    = "";
        String tlphNoOwnCmpnCd = "";
        String encdTlphNo      = "";

        Map<String, String> nu1Param = new HashMap<>();
        nu1Param.put("resNo",           req.getResNo());
        nu1Param.put("prntsContractNo", certContractNum);
        nu1Param.put("custNo",          customerId);

        List<MpOsstPhoneNoVO.PhoneNoItem> phoneNmeverList = null;

        // 끝번호
        if (ctn.length() >= 4) {
            nu1Param.put("reqWantNumber", ctn.substring(ctn.length() - 4));
            phoneNmeverList = getPhoneNoList(nu1Param);
        }

        if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
            tlphNo          = phoneNmeverList.get(0).getTlphNo();
            tlphNoStatCd    = phoneNmeverList.get(0).getTlphNoStatCd();
            tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmpnCd();
            encdTlphNo      = phoneNmeverList.get(0).getEncdTlphNo();
        } else {
            // 끝번호 조회 안되면 중간 번호로 다시 시도
            if (ctn.length() >= 8) {
                nu1Param.put("reqWantNumber", ctn.substring(ctn.length() - 8, ctn.length() - 4));
                phoneNmeverList = getPhoneNoList(nu1Param);
            }
            if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                tlphNo          = phoneNmeverList.get(0).getTlphNo();
                tlphNoStatCd    = phoneNmeverList.get(0).getTlphNoStatCd();
                tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmpnCd();
                encdTlphNo      = phoneNmeverList.get(0).getEncdTlphNo();
            } else {
                // 중간 번호 조회 안되면 Random
                String a, b, c, d;
                try {
                    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                    a = String.valueOf(random.nextInt(10));
                    b = String.valueOf(random.nextInt(10));
                    c = String.valueOf(random.nextInt(10));
                    d = String.valueOf(random.nextInt(10));
                } catch (NoSuchAlgorithmException e1) {
                    a = String.valueOf((int)(Math.random() * 10));
                    b = String.valueOf((int)(Math.random() * 10));
                    c = String.valueOf((int)(Math.random() * 10));
                    d = String.valueOf((int)(Math.random() * 10));
                }
                nu1Param.put("reqWantNumber", a + b + c + d);  // Random
                phoneNmeverList = getPhoneNoList(nu1Param);
                if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                    tlphNo          = phoneNmeverList.get(0).getTlphNo();
                    tlphNoStatCd    = phoneNmeverList.get(0).getTlphNoStatCd();
                    tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmpnCd();
                    encdTlphNo      = phoneNmeverList.get(0).getEncdTlphNo();
                } else {
                    rtnMap.put("RESULT_CODE", "3001");
                    rtnMap.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_NU1);
                    rtnMap.put("ERROR_MSG",   "조회 전화번호 없음");
                    return rtnMap;
                }
            }
        }

        // 5. 번호예약/취소(NU2)
        // 번호 정보 DB 저장 (OSST UPDATE) — insertMcpRequestOsst 역할
        SmartFormOsstDto nuReg = new SmartFormOsstDto();
        nuReg.setRequestKey(req.getRequestKey());
        nuReg.setTlphNo(tlphNo);
        nuReg.setTlphNoStatCd(tlphNoStatCd);
        nuReg.setTlphNoOwnCmpnCd(tlphNoOwnCmpnCd);
        nuReg.setEncdTlphNo(encdTlphNo);
        nuReg.setAsgnAgncId(agentCode);
        nuReg.setOpenSvcIndCd("03");  // 03 고정 (3G)
        nuReg.setRsltCd(OSST_SUCCESS);
        smartFormMapper.updateMsfRequestOsst(nuReg);

        // 번호예약(NU2)
        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디/모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam = new HashMap<>();
            osstParam.put("resNo",           req.getResNo());
            osstParam.put("gubun",           WORK_CODE_RES);
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo",          customerId);

            MpOsstSimpleVO simpleOsstXmlReg = mplatFormSvc.sendOsstService(osstParam, MplatFormSvc.EVENT_CODE_NU2);

            if (simpleOsstXmlReg.isSuccess()) {
                SmartFormOsstDto nu2Upd = new SmartFormOsstDto();
                nu2Upd.setRequestKey(req.getRequestKey());
                nu2Upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_NU2);
                nu2Upd.setRsltCd(simpleOsstXmlReg.getRsltCd());
                smartFormMapper.updateMsfRequestOsst(nu2Upd);

                rtnMap.put("RESULT_CODE", "S");
                rtnMap.put("TLPH_NO",     tlphNo);
                logger.info("[saveDataSharingStep2] NU2 번호예약 완료: tlphNo={}", tlphNo);
            } else {
                rtnMap.put("RESULT_CODE", "4001");
                rtnMap.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_NU2);
                rtnMap.put("RESULT_XML",  simpleOsstXmlReg.getResponseXml());
                rtnMap.put("ERROR_MSG",   simpleOsstXmlReg.getResultCode());
                logger.warn("[saveDataSharingStep2] NU2 실패: {}", simpleOsstXmlReg.getResultCode());
                return rtnMap;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[saveDataSharingStep2] InterruptedException: {}", e.getMessage());
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "4004");
            rtnMap.put("EVENT_CODE",  MplatFormSvc.EVENT_CODE_NU2);
            rtnMap.put("ERROR_MSG",   e.getMessage());
            logger.error("[saveDataSharingStep2] 오류: {}", e.getMessage(), e);
            return rtnMap;
        }

        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // saveDataSharingStep3: 개통및수납(OP0)
    // ASIS AppformController.saveDataSharingStep3()
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public Map<String, Object> saveDataSharingStep3(SmartFormReqDto req) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        if (req.getRequestKey() == null || isBlank(req.getResNo())) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG",  "필수 파라미터(requestKey, resNo)가 없습니다.");
            return rtnMap;
        }

        // 신청서 조회
        Map<String, Object> reqRow = smartFormMapper.getShareDataRequest(req.getRequestKey());
        if (reqRow == null) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG",  "신청서를 찾을 수 없습니다.");
            return rtnMap;
        }

        // OSST 이력 조회
        SmartFormOsstDto sessRequestOsstDto = smartFormMapper.getMsfRequestOsst(req.getRequestKey());
        if (sessRequestOsstDto == null) {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_MSG",  "OSST 레코드를 찾을 수 없습니다.");
            return rtnMap;
        }

        String certContractNum = nvlStr(reqRow.get("ncn"));
        String customerId      = nvlStr(reqRow.get("custId"));
        // 청구계정번호 (TOBE: req 에서 받거나 DB 조회)
        String billAcntNo = nvl(req.getBillAcntNo(), "");

        // 6. 개통및수납(OP0)
        MpOsstSimpleVO simpleOsstXmlVO = null;
        String svcMsg = "";
        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디/모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam = new HashMap<>();
            osstParam.put("resNo",           req.getResNo());
            osstParam.put("billAcntNo",      billAcntNo);
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo",          customerId);

            simpleOsstXmlVO = mplatFormSvc.sendOsstService(osstParam, MplatFormSvc.EVENT_CODE_OP0);

            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", "S");
                rtnMap.put("success",     true);
                rtnMap.put("TLPH_NO",     sessRequestOsstDto.getTlphNo());

                // OP0 성공 후 완료 처리
                Map<String, Object> updParams = new HashMap<>();
                updParams.put("requestKey", req.getRequestKey());
                updParams.put("openNo",     sessRequestOsstDto.getTlphNo());
                updParams.put("procCd",     "CP");
                smartFormMapper.updateShareDataRequest(updParams);

                SmartFormOsstDto op0Upd = new SmartFormOsstDto();
                op0Upd.setRequestKey(req.getRequestKey());
                op0Upd.setPrgrStatCd(MplatFormSvc.EVENT_CODE_OP0);
                op0Upd.setRsltCd(OSST_SUCCESS);
                smartFormMapper.updateMsfRequestOsst(op0Upd);

                logger.info("[saveDataSharingStep3] OP0 개통 완료: TLPH_NO={}", sessRequestOsstDto.getTlphNo());
            } else {
                svcMsg = simpleOsstXmlVO.getSvcMsg();
                rtnMap.put("RESULT_CODE",  "5001");
                rtnMap.put("EVENT_CODE",   MplatFormSvc.EVENT_CODE_OP0);
                rtnMap.put("RESULT_XML",   simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG",    simpleOsstXmlVO.getResultCode());
                rtnMap.put("REQUEST_MSG",  svcMsg);
                rtnMap.put("ERROR_NE_MSG", svcMsg);
                logger.warn("[saveDataSharingStep3] OP0 실패: {}", simpleOsstXmlVO.getResultCode());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[saveDataSharingStep3] InterruptedException: {}", e.getMessage());
        } catch (Exception e) {
            if (simpleOsstXmlVO != null) svcMsg = simpleOsstXmlVO.getSvcMsg();
            rtnMap.put("RESULT_CODE",  "5004");
            rtnMap.put("EVENT_CODE",   MplatFormSvc.EVENT_CODE_OP0);
            rtnMap.put("REQUEST_MSG",  svcMsg);
            rtnMap.put("ERROR_MSG",    e.getMessage());
            rtnMap.put("ERROR_NE_MSG", e.getMessage());
            logger.error("[saveDataSharingStep3] 오류: {}", e.getMessage(), e);
            return rtnMap;
        }

        return rtnMap;
    }

    // ─────────────────────────────────────────────────────────────────────
    // 내부 유틸리티
    // ─────────────────────────────────────────────────────────────────────

    /**
     * ST1 연동으로 PC2 완료상태 조회.
     * ASIS AppformSvc.chkRealPc2Result() 역할.
     * PC2 완료 확인 후 Y39 연동에 사용.
     */
    private Map<String, String> chkRealPc2Result(Map<String, String> st1Param) {
        Map<String, String> result = new HashMap<>();

        try {
            for (int i = 0; i < 2; i++) {
                Thread.sleep(5000);
                MpOsstSimpleVO st1Vo = mplatFormSvc.sendOsstSt1Service(st1Param);

                if (!st1Vo.isSuccess()) {
                    result.put("RESULT_CODE", "ERROR_001");
                    result.put("ERROR_MSG",   st1Vo.getSvcMsg());
                    return result;
                }

                if (!OSST_SUCCESS.equals(st1Vo.getRsltCd())) {
                    result.put("RESULT_CODE", "ERROR_002");
                    result.put("ERROR_MSG",   st1Vo.getRsltMsg());
                    return result;
                }

                if (EVENT_CODE_PC_RESULT.equals(st1Vo.getPrgrStatCd())) {
                    result.put("RESULT_CODE", "S");
                    return result;
                }
                logger.info("[chkRealPc2Result] ST1 재시도 {}/2, prgrStatCd={}", i + 1, st1Vo.getPrgrStatCd());
            }

            result.put("RESULT_CODE", "ERROR_003");
            result.put("ERROR_MSG",   "사전체크 처리 완료 결과 확인 불가");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            result.put("RESULT_CODE", "ERROR_007");
            result.put("ERROR_MSG",   "InterruptedException");
        } catch (Exception e) {
            logger.error("[chkRealPc2Result] 오류: {}", e.getMessage(), e);
            result.put("RESULT_CODE", "ERROR_007");
            result.put("ERROR_MSG",   e.getMessage());
        }
        return result;
    }

    private List<MpOsstPhoneNoVO.PhoneNoItem> getPhoneNoList(Map<String, String> param) {
        MpOsstPhoneNoVO vo = mplatFormSvc.getPhoneNoList(param);
        return vo != null ? vo.getItems() : null;
    }

    private boolean isAjaxSuccess(Map<String, Object> map) {
        Object code = map.get("RESULT_CODE");
        return "S".equals(code) || Boolean.TRUE.equals(map.get("success"));
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
