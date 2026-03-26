package com.ktmmobile.msf.formSvcCncl.service;

import com.ktmmobile.msf.common.mplatform.MplatFormSvc;
import com.ktmmobile.msf.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscSdsInfoVo;
import com.ktmmobile.msf.formComm.dto.FormSendReqDto;
import com.ktmmobile.msf.formComm.dto.FormSendResVO;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formComm.service.FormCommRestSvc;
import com.ktmmobile.msf.formSvcCncl.dto.McpCancelRegisterReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.McpCancelRegisterResVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclConsultReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclDetailResVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclProcReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclRemainChargeResVO;
import com.ktmmobile.msf.formSvcCncl.mapper.SvcCnclInsertDto;
import com.ktmmobile.msf.formSvcCncl.mapper.SvcCnclMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 서비스해지 신청서 서비스 구현.
 * ASIS CancelConsultSvcImpl 참조.
 * 저장 테이블: MSF_REQUEST_CANCEL (SQ_REQUEST_KEY 시퀀스)
 */
@Service
public class SvcCnclSvcImpl implements SvcCnclSvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcCnclSvcImpl.class);

    private final SvcCnclMapper svcCnclMapper;

    @Autowired
    private MplatFormSvc mplatFormSvc;

    @Autowired
    private FormCommRestSvc formCommRestSvc;

    @Autowired
    private McpCancelRegisterSvc mcpCancelRegisterSvc;

    public SvcCnclSvcImpl(SvcCnclMapper svcCnclMapper) {
        this.svcCnclMapper = svcCnclMapper;
    }

    /**
     * 해지 사전체크.
     * ASIS: CancelConsultController.cancelConsultAjax() 체크 로직과 동일.
     * 체크 순서:
     *   1. 미성년자 확인 (birthDate → 만 19세 미만) → resultCode=00002
     *   2. 필수값 확인 (ncn/ctn 빈값) → resultCode=00004
     *   3. 중복접수 확인 (동일 휴대폰번호 접수 중) → resultCode=00006
     *   4. 본인인증 확인 (phoneAuthCompleted != true) → resultCode=00007
     */
    @Override
    public Map<String, Object> cancelConsult(SvcCnclConsultReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null) {
            result.put("success", false);
            result.put("resultCode", "00004");
            result.put("message", "요청 정보가 없습니다.");
            return result;
        }

        // ① 미성년자 확인 — 만 19세 미만
        if (!isBlank(req.getBirthDate()) && req.getBirthDate().length() == 8) {
            try {
                int birthYear  = Integer.parseInt(req.getBirthDate().substring(0, 4));
                int birthMonth = Integer.parseInt(req.getBirthDate().substring(4, 6));
                int birthDay   = Integer.parseInt(req.getBirthDate().substring(6, 8));
                java.time.LocalDate birth = java.time.LocalDate.of(birthYear, birthMonth, birthDay);
                java.time.LocalDate today = java.time.LocalDate.now();
                int age = today.getYear() - birth.getYear();
                if (today.getMonthValue() < birth.getMonthValue() ||
                        (today.getMonthValue() == birth.getMonthValue() && today.getDayOfMonth() < birth.getDayOfMonth())) {
                    age--;
                }
                if (age < 19) {
                    result.put("success", false);
                    result.put("resultCode", "00002");
                    result.put("message", "미성년자는 법정대리인 동행 후 대리점에서 직접 처리해 주세요.");
                    return result;
                }
            } catch (Exception e) {
                logger.warn("미성년자 체크 오류 (계속 진행): {}", e.getMessage());
            }
        }

        // ② 필수값 확인
        if (isBlank(req.getNcn()) && isBlank(req.getCtn())) {
            result.put("success", false);
            result.put("resultCode", "00004");
            result.put("message", "계약번호 또는 휴대폰번호를 입력해 주세요.");
            return result;
        }

        // ③ 중복접수 확인
        if (!isBlank(req.getCtn())) {
            String mobileNo = normalizePhone(req.getCtn());
            try {
                int count = svcCnclMapper.selectCancelCount(mobileNo);
                if (count > 0) {
                    result.put("success", false);
                    result.put("resultCode", "00006");
                    result.put("message", "이미 동일한 해지 신청이 접수 중입니다.");
                    return result;
                }
            } catch (Exception e) {
                logger.warn("중복접수 체크 조회 오류 (계속 진행): {}", e.getMessage());
            }
        }

        // ④ 본인인증 확인
        if (!Boolean.TRUE.equals(req.getPhoneAuthCompleted())) {
            result.put("success", false);
            result.put("resultCode", "00007");
            result.put("message", "본인 인증을 완료해 주세요.");
            return result;
        }

        result.put("success", true);
        result.put("resultCode", "00000");
        result.put("message", "");
        return result;
    }

    /**
     * 해지 정산 조회 (X18 + X54 + X62).
     * - 사용요금   = X18 실시간요금조회 → remainCharge
     * - 위약금     = X54 스폰서조회(trmnForecBprmsAmt) + X62 심플할인(ppPenlt) 합산 → penalty
     * - 잔여단말기대금(Y37): 삭제_미사용(확정) → installmentRemain null
     *
     * ASIS: MyinfoController.getRealTimePriceAjax() + ChargeServiceImpl 참조.
     */
    @Override
    public SvcCnclRemainChargeResVO getRemainCharge(SvcChgInfoDto req) {
        if (req == null || isBlank(req.getCtn())) {
            return SvcCnclRemainChargeResVO.empty();
        }

        SvcCnclRemainChargeResVO result = SvcCnclRemainChargeResVO.empty();

        // ── X18 실시간요금조회 (사용요금) ──────────────────────────────────
        try {
            MpFarRealtimePayInfoVO x18 = mplatFormSvc.farRealtimePayInfo(
                req.getNcn(), req.getCtn(), req.getCustId());

            if (!x18.isSuccess()) {
                logger.warn("X18 실시간요금조회 실패: {}", x18.getSvcMsg());
            } else {
                result.setSearchDay(x18.getSearchDay());
                result.setSearchTime(x18.getSearchTime());

                String sumAmt = x18.getSumAmt();
                result.setSumAmt(sumAmt != null ? sumAmt : "-");

                if (x18.getItems() != null) {
                    List<SvcCnclRemainChargeResVO.FareItemDto> items = new ArrayList<>();
                    for (MpFarRealtimePayInfoVO.FareItem item : x18.getItems()) {
                        items.add(new SvcCnclRemainChargeResVO.FareItemDto(item.getGubun(), item.getPayment()));
                    }
                    result.setItems(items);
                }

                if (sumAmt != null && !sumAmt.isEmpty()) {
                    String amtStr = sumAmt.replaceAll("[^0-9]", "");
                    if (!amtStr.isEmpty()) {
                        result.setRemainCharge(Long.parseLong(amtStr));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("X18 실시간요금조회 오류: {}", e.getMessage());
        }

        // ── X54 스폰서조회 + X62 심플할인 (위약금) ──────────────────────────
        long penaltyX54 = 0L;
        long penaltyX62 = 0L;

        try {
            MpMoscSpnsrItgInfoInVO x54 = mplatFormSvc.kosMoscSpnsrItgInfo(
                req.getNcn(), req.getCtn(), req.getCustId());
            if (x54.isSuccess()) {
                penaltyX54 = parseLong(x54.getTrmnForecBprmsAmt());
                logger.debug("X54 스폰서 위약금: {}, saleEngtOptnCd={}", penaltyX54, x54.getSaleEngtOptnCd());
            } else {
                logger.warn("X54 스폰서조회 실패 (위약금 0 처리): {}", x54.getSvcMsg());
            }
        } catch (Exception e) {
            logger.warn("X54 스폰서조회 오류 (위약금 0 처리): {}", e.getMessage());
        }

        try {
            MpMoscSdsInfoVo x62 = mplatFormSvc.moscSdsInfo(
                req.getNcn(), req.getCtn(), req.getCustId());
            if (x62.isSuccess() && "Y".equals(x62.getChageDcAplyYn())) {
                penaltyX62 = parseLong(x62.getPpPenlt());
                logger.debug("X62 심플할인 위약금: {}", penaltyX62);
            } else {
                logger.debug("X62 심플할인 미적용 또는 실패 (위약금 0 처리)");
            }
        } catch (Exception e) {
            logger.warn("X62 심플할인조회 오류 (위약금 0 처리): {}", e.getMessage());
        }

        long totalPenalty = penaltyX54 + penaltyX62;
        result.setPenalty(totalPenalty > 0 ? totalPenalty : null);

        // ── Y37 잔여단말기대금: 삭제_미사용(확정) → null ────────────────────
        // 인터페이스 설계서 IF_0208 참조: 최종 사용여부 = 삭제_미사용(확정)
        result.setInstallmentRemain(null);

        return result;
    }

    private static long parseLong(String s) {
        if (s == null || s.trim().isEmpty()) return 0L;
        try {
            return Long.parseLong(s.trim().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * 해지 신청서 등록 (MSF_REQUEST_CANCEL 단일 테이블).
     * ASIS: CancelConsultSvcImpl → NMCP_CUST_REQUEST_MST + MCP_CANCEL_REQUEST
     * TOBE: MSF_REQUEST_CANCEL (REQUEST_KEY는 SQ_REQUEST_KEY 시퀀스로 자동 생성)
     */
    @Override
    public SvcCnclApplyVO apply(SvcCnclApplyDto req) {
        if (req == null) {
            return SvcCnclApplyVO.fail("요청 정보가 없습니다.");
        }
        SvcCnclApplyDto.CustomerFormDto cf = req.getCustomerForm();
        if (cf == null) {
            return SvcCnclApplyVO.fail("고객 정보가 없습니다.");
        }
        if (isBlank(cf.getName())) {
            return SvcCnclApplyVO.fail("고객명을 입력해 주세요.");
        }
        if (isBlank(cf.getPhone())) {
            return SvcCnclApplyVO.fail("휴대폰 번호를 입력해 주세요.");
        }
        if (!Boolean.TRUE.equals(cf.getPhoneAuthCompleted())) {
            return SvcCnclApplyVO.fail("휴대폰 인증을 완료해 주세요.");
        }
        if (isBlank(req.getAgencyCode())) {
            return SvcCnclApplyVO.fail("대리점을 선택해 주세요.");
        }
        SvcCnclApplyDto.ProductFormDto pf = req.getProductForm();
        if (pf == null || isBlank(pf.getUseType())) {
            return SvcCnclApplyVO.fail("사용여부를 선택해 주세요.");
        }

        String mobileNo = normalizePhone(cf.getPhone());

        // 중복 신청 체크
        try {
            int count = svcCnclMapper.selectCancelCount(mobileNo);
            if (count > 0) {
                return SvcCnclApplyVO.fail("이미 동일한 해지 신청이 접수 중입니다.");
            }
        } catch (Exception e) {
            logger.warn("중복 체크 조회 오류 (계속 진행): {}", e.getMessage());
        }

        try {
            SvcCnclInsertDto dto = new SvcCnclInsertDto();
            dto.setCstmrNm(cf.getName());
            dto.setCstmrTypeCd(defaultIfBlank(cf.getCustType(), "NA"));
            dto.setCancelMobileNo(mobileNo);
            dto.setContractNum(cf.getNcn());
            dto.setManagerCd("0");            // TOBE 미사용, NOT NULL 요건 충족
            dto.setAgentCd(req.getAgencyCode());
            dto.setOperTypeCd("CC");          // Cancel Consult
            dto.setReceiveWayCd(hasEmail(cf) ? "E" : "P");
            dto.setReceiveMobileNo(mobileNo);
            dto.setCancelUseCompanyCd(useTypeToCompanyCd(pf.getUseType()));
            dto.setPayAmt(pf.getRemainCharge());
            dto.setPnltAmt(pf.getPenalty());
            // 최종정산 = 잔여요금 + 위약금 + 분납잔액
            dto.setLastSumAmt(calcLastSum(pf));
            dto.setInstamtMnthAmt(pf.getInstallmentRemain());
            dto.setBenefitAgreeYn("Y");
            dto.setMemo(pf.getMemo());
            dto.setRegstId("MFORM");
            dto.setProcCd("RC");

            int rows = svcCnclMapper.insertCancel(dto);
            if (rows <= 0 || dto.getRequestKey() == null) {
                return SvcCnclApplyVO.fail("해지 신청서 저장에 실패했습니다.");
            }

            Long requestKey = dto.getRequestKey();

            // ── M포탈 적재 (MCP_CANCEL_REQUEST INSERT) ─────────────────────
            McpCancelRegisterReqDto mcpReq = buildMcpRegisterReq(dto, cf, requestKey);
            try {
                McpCancelRegisterResVO mcpRes = mcpCancelRegisterSvc.register(mcpReq);
                if (mcpRes.isSuccess() && mcpRes.getCustReqSeq() != null) {
                    svcCnclMapper.updateCancelResNo(requestKey, mcpRes.getCustReqSeq());
                } else {
                    logger.warn("M포탈 적재 실패 (신청 계속): requestKey={}, {}", requestKey, mcpRes.getMessage());
                }
            } catch (Exception e) {
                logger.warn("M포탈 적재 중 오류 (신청 계속): requestKey={}, {}", requestKey, e.getMessage());
            }

            // ── SCAN 서버 전송 (신청서 XML) ─────────────────────────────────
            try {
                FormSendReqDto scanReq = new FormSendReqDto();
                scanReq.setCustReqSeq(requestKey);
                scanReq.setReqType("CC");
                scanReq.setUserId("MFORM");
                FormSendResVO scanRes = formCommRestSvc.sendScan(scanReq);
                if (!scanRes.isSuccess()) {
                    logger.warn("SCAN 서버 전송 실패 (신청 계속): requestKey={}, {}", requestKey, scanRes.getMessage());
                }
            } catch (Exception e) {
                logger.warn("SCAN 전송 중 오류 (신청 계속): requestKey={}, {}", requestKey, e.getMessage());
            }

            return SvcCnclApplyVO.ok(String.valueOf(requestKey));
        } catch (Exception e) {
            logger.error("서비스해지 신청서 저장 오류: {}", e.getMessage());
            return SvcCnclApplyVO.fail("신청서 저장 중 오류가 발생했습니다.");
        }
    }

    private McpCancelRegisterReqDto buildMcpRegisterReq(
            SvcCnclInsertDto dto, SvcCnclApplyDto.CustomerFormDto cf, Long requestKey) {
        McpCancelRegisterReqDto req = new McpCancelRegisterReqDto();
        req.setSourceKey(String.valueOf(requestKey));
        req.setContractNum(dto.getContractNum());
        req.setCancelMobileNo(dto.getCancelMobileNo());
        req.setCstmrName(dto.getCstmrNm());
        req.setCstmrType(dto.getCstmrTypeCd());
        req.setReceiveMobileNo(dto.getReceiveMobileNo());
        req.setReceiveWayCd(dto.getReceiveWayCd());
        req.setPayAmt(dto.getPayAmt());
        req.setPnltAmt(dto.getPnltAmt());
        req.setLastSumAmt(dto.getLastSumAmt());
        req.setRegstId(dto.getRegstId());
        return req;
    }

    /**
     * 사용여부 코드 → CANCEL_USE_COMPANY_CD 매핑.
     * KT_M_MOBILE_REUSE → 'KTM', KT_REUSE → 'KT', SKT_USE → 'SKT', LGT_USE → 'LGT', ETC → 'ETC'
     */
    private static String useTypeToCompanyCd(String useType) {
        if (useType == null) return "ETC";
        switch (useType) {
            case "KT_M_MOBILE_REUSE": return "KTM";
            case "KT_REUSE":          return "KT";
            case "SKT_USE":           return "SKT";
            case "LGT_USE":           return "LGT";
            default:                  return "ETC";
        }
    }

    private static Long calcLastSum(SvcCnclApplyDto.ProductFormDto pf) {
        long r = pf.getRemainCharge()     != null ? pf.getRemainCharge()     : 0L;
        long p = pf.getPenalty()          != null ? pf.getPenalty()          : 0L;
        long i = pf.getInstallmentRemain() != null ? pf.getInstallmentRemain() : 0L;
        return (r + p + i) > 0 ? r + p + i : null;
    }

    private static boolean hasEmail(SvcCnclApplyDto.CustomerFormDto cf) {
        return !isBlank(cf.getEmail()) && !isBlank(cf.getEmailDomain());
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return null;
        return phone.replaceAll("[^0-9]", "");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String defaultIfBlank(String s, String def) {
        return isBlank(s) ? def : s;
    }

    /**
     * MSP 처리완료 통보 — MSF_REQUEST_CANCEL.PROC_CD 업데이트.
     * custReqSeq(RES_NO) 기준. CP 이중 업데이트 방지.
     */
    @Override
    public Map<String, Object> updateProc(SvcCnclProcReqDto req) {
        Map<String, Object> result = new HashMap<>();

        if (req == null || isBlank(req.getCustReqSeq())) {
            result.put("success", false);
            result.put("resultCode", "00004");
            result.put("message", "custReqSeq가 없습니다.");
            return result;
        }
        if (isBlank(req.getProcCd())) {
            result.put("success", false);
            result.put("resultCode", "00004");
            result.put("message", "procCd가 없습니다.");
            return result;
        }

        try {
            int rows = svcCnclMapper.updateCancelProcCd(req.getCustReqSeq(), req.getProcCd());
            if (rows <= 0) {
                // 이미 CP이거나 RES_NO 없는 건 — 오류가 아닌 정상 스킵
                logger.info("[updateProc] 업데이트 대상 없음 (이미 처리됨 또는 미존재): custReqSeq={}", req.getCustReqSeq());
                result.put("success", true);
                result.put("resultCode", "00001");
                result.put("message", "처리할 대상이 없습니다 (이미 처리 완료 또는 미존재).");
                return result;
            }
            logger.info("[updateProc] PROC_CD 업데이트 완료: custReqSeq={}, procCd={}", req.getCustReqSeq(), req.getProcCd());
            result.put("success", true);
            result.put("resultCode", "00000");
            result.put("message", "");
            return result;
        } catch (Exception e) {
            logger.error("[updateProc] 오류: custReqSeq={}, {}", req.getCustReqSeq(), e.getMessage());
            result.put("success", false);
            result.put("resultCode", "99999");
            result.put("message", "처리 중 오류가 발생했습니다.");
            return result;
        }
    }

    /**
     * 서비스해지 신청서 단건 상세 조회.
     * M전산 분기처리용 API 응답 데이터.
     */
    @Override
    public SvcCnclDetailResVO getDetail(Long requestKey) {
        if (requestKey == null) {
            return SvcCnclDetailResVO.fail("requestKey가 없습니다.");
        }
        try {
            SvcCnclDetailResVO detail = svcCnclMapper.selectCancelDetail(requestKey);
            if (detail == null) {
                return SvcCnclDetailResVO.fail("해지 신청서를 찾을 수 없습니다. requestKey=" + requestKey);
            }
            detail.setSuccess(true);
            detail.setMessage("");
            return detail;
        } catch (Exception e) {
            logger.error("해지 신청서 상세 조회 오류: requestKey={}, {}", requestKey, e.getMessage());
            return SvcCnclDetailResVO.fail("상세 조회 중 오류가 발생했습니다.");
        }
    }
}
