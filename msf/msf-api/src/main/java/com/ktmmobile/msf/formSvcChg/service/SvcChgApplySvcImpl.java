package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgApplyResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgFarPriceReqDto;
import com.ktmmobile.msf.formSvcChg.dto.UsimChangeReqDto;
import com.ktmmobile.msf.formSvcChg.mapper.SvcChgApplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 서비스변경 통합 신청 서비스 구현.
 * M플랫폼 연동(X21, X38, X19, X88, X85, UC0) 후 신청서 DB INSERT.
 * ASIS: RegSvcController + FarPriceController + AppformSvcImpl 처리 통합.
 */
@Service
public class SvcChgApplySvcImpl implements SvcChgApplySvc {

    private static final Logger logger = LoggerFactory.getLogger(SvcChgApplySvcImpl.class);

    /** 무선데이터차단 SOC 코드 */
    private static final String SOC_WIRELESS_BLOCK = "DATABLOCK";

    @Autowired
    private SvcChgRegSvc regSvc;

    @Autowired
    private SvcChgFarPriceSvc farPriceSvc;

    @Autowired
    private SvcChgUsimSvc usimSvc;

    @Autowired
    private SvcChgApplyMapper applyMapper;

    @Override
    public SvcChgApplyResVO apply(SvcChgApplyReqDto req) {
        SvcChgApplyResVO res = new SvcChgApplyResVO();

        if (req == null || isBlank(req.getCtn())) {
            res.setSuccess(false);
            res.setMessage("필수 파라미터(ctn)가 없습니다.");
            return res;
        }

        List<String> opts = req.getSelectedOptions() != null ? req.getSelectedOptions() : new ArrayList<>();
        String ncn = req.getNcn() != null ? req.getNcn() : "";
        String ctn = req.getCtn();
        String custId = req.getCustId() != null ? req.getCustId() : "";

        logger.debug("[Apply] 서비스변경 신청 Start: ncn={}, ctn={}, opts={}", ncn, ctn, opts);

        // ── ① WIRELESS_BLOCK — X21(차단신청) / X38(해지) ──────────────────
        if (opts.contains("WIRELESS_BLOCK") && !isBlank(req.getWirelessBlock())) {
            String desired = req.getWirelessBlock();
            logger.debug("[Apply] WIRELESS_BLOCK 처리 Start: desired={}", desired);
            if ("block".equals(desired)) {
                Map<String, Object> r = regSvc.additionReg(buildRegDto(req, SOC_WIRELESS_BLOCK, null));
                if (Boolean.FALSE.equals(r.get("success"))) {
                    logger.warn("[Apply] WIRELESS_BLOCK X21 실패: {}", r.get("message"));
                    res.setSuccess(false);
                    res.setMessage("무선데이터차단 신청 실패: " + r.get("message"));
                    return res;
                }
                logger.debug("[Apply] WIRELESS_BLOCK X21 완료: globalNo={}", r.get("globalNo"));
            } else if ("use".equals(desired)) {
                Map<String, Object> r = regSvc.additionCancel(buildCancelDto(req, SOC_WIRELESS_BLOCK));
                if (Boolean.FALSE.equals(r.get("success"))) {
                    logger.warn("[Apply] WIRELESS_BLOCK X38 실패: {}", r.get("message"));
                    res.setSuccess(false);
                    res.setMessage("무선데이터차단 해지 실패: " + r.get("message"));
                    return res;
                }
                logger.debug("[Apply] WIRELESS_BLOCK X38 완료: globalNo={}", r.get("globalNo"));
            }
        }

        // ── ② INFO_LIMIT — M플랫폼 SOC 미확정, DB 기록만 처리 ──────────────
        if (opts.contains("INFO_LIMIT") && !isBlank(req.getInfoLimit())) {
            // TODO: 정보료 상한 SOC 코드 확정 후 X21(신청)/X38(해지) 연동
            logger.debug("[Apply] INFO_LIMIT DB 기록: infoLimit={}원", req.getInfoLimit());
        }

        // ── ③ RATE_CHANGE — X19(즉시) / X88(예약) ──────────────────────────
        if (opts.contains("RATE_CHANGE") && !isBlank(req.getRatePlanSoc())) {
            logger.debug("[Apply] RATE_CHANGE 처리 Start: soc={}, schedule={}",
                req.getRatePlanSoc(), req.getRateChangeSchedule());
            SvcChgFarPriceReqDto fareDto = new SvcChgFarPriceReqDto();
            fareDto.setNcn(ncn);
            fareDto.setCtn(ctn);
            fareDto.setCustId(custId);
            fareDto.setName(req.getName());
            fareDto.setSoc(req.getRatePlanSoc());
            fareDto.setSchedule(req.getRateChangeSchedule());
            Map<String, Object> r = farPriceSvc.applyFarPriceChange(fareDto);
            if (Boolean.FALSE.equals(r.get("success"))) {
                logger.warn("[Apply] RATE_CHANGE 실패: {}", r.get("message"));
                res.setSuccess(false);
                res.setMessage("요금제 변경 실패: " + r.get("message"));
                return res;
            }
            logger.debug("[Apply] RATE_CHANGE 완료: globalNo={}", r.get("globalNo"));
        }

        // ── ④ USIM_CHANGE — X85 사전확인 + UC0 변경 ────────────────────────
        if (opts.contains("USIM_CHANGE") && !isBlank(req.getUsimChange())) {
            logger.debug("[Apply] USIM_CHANGE 처리 Start: usimNo={}", req.getUsimChange());
            UsimChangeReqDto usimDto = new UsimChangeReqDto();
            usimDto.setNcn(ncn);
            usimDto.setCtn(ctn);
            usimDto.setCustId(custId);
            usimDto.setName(req.getName());
            usimDto.setNewUsimNo(req.getUsimChange());
            Map<String, Object> r = usimSvc.changeUsim(usimDto);
            if (Boolean.FALSE.equals(r.get("success"))) {
                logger.warn("[Apply] USIM_CHANGE 실패: {}", r.get("message"));
                res.setSuccess(false);
                res.setMessage("USIM 변경 실패: " + r.get("message"));
                return res;
            }
            logger.debug("[Apply] USIM_CHANGE 완료: globalNo={}", r.get("globalNo"));
        }

        // ── ⑤ ADDITION 개별 부가서비스 — X21(신청) / X38(해지) ─────────────
        if (opts.contains("ADDITION") && req.getAdditions() != null && !req.getAdditions().isEmpty()) {
            for (SvcChgApplyReqDto.AdditionApplyItem item : req.getAdditions()) {
                if (isBlank(item.getSoc())) continue;
                if ("cancel".equals(item.getAction())) {
                    Map<String, Object> r = regSvc.additionCancel(buildCancelDto(req, item.getSoc()));
                    logger.debug("[Apply] ADDITION X38: soc={}, success={}, globalNo={}",
                        item.getSoc(), r.get("success"), r.get("globalNo"));
                } else {
                    Map<String, Object> r = regSvc.additionReg(buildRegDto(req, item.getSoc(), item.getFtrNewParam()));
                    logger.debug("[Apply] ADDITION X21: soc={}, success={}, globalNo={}",
                        item.getSoc(), r.get("success"), r.get("globalNo"));
                }
            }
        }

        // ── ⑥⑦ DB INSERT — 마스터 + 선택 항목별 상세 ─────────────────────
        try {
            String custType  = !isBlank(req.getCustType())  ? req.getCustType()  : "NA";
            String managerCd = !isBlank(req.getManagerCd()) ? req.getManagerCd() : "SYSTEM";
            String agentCd   = !isBlank(req.getAgentCd())   ? req.getAgentCd()   : "SYSTEM";

            // 마스터 INSERT
            Map<String, Object> masterParams = new HashMap<>();
            masterParams.put("custType",  custType);
            masterParams.put("managerCd", managerCd);
            masterParams.put("agentCd",   agentCd);
            masterParams.put("ctn",       ctn);
            masterParams.put("memo",      req.getMemo());
            masterParams.put("name",      req.getName());

            applyMapper.insertSvcChgRequest(masterParams);
            long requestKey = ((Number) masterParams.get("requestKey")).longValue();
            logger.debug("[Apply] MSF_REQUEST_SVC_CHG INSERT 완료: requestKey={}", requestKey);

            // 상세 INSERT — 선택 항목별 1행
            for (String opt : opts) {
                Map<String, Object> dtlParams = buildDtlParams(requestKey, opt, req);
                applyMapper.insertSvcChgRequestDtl(dtlParams);
                logger.debug("[Apply] MSF_REQUEST_SVC_CHG_DTL INSERT: opt={}, dtlSeq={}", opt, dtlParams.get("dtlSeq"));
            }

            res.setSuccess(true);
            res.setRequestKey(requestKey);
            res.setApplicationNo(String.valueOf(requestKey));
            res.setMessage("");
            logger.debug("[Apply] 서비스변경 신청 완료: requestKey={}, opts={}", requestKey, opts);

        } catch (Exception e) {
            logger.error("[Apply] DB INSERT 오류: {}", e.getMessage(), e);
            res.setSuccess(false);
            res.setMessage("신청서 저장 중 오류가 발생했습니다.");
        }

        return res;
    }

    /**
     * 선택 항목별 DTL 파라미터 맵 생성.
     * SVC_CHG_TYPE_CD: '1'=신청/가입, '2'=해지/취소
     * PROC_TYPE_CD: '1'=즉시, '2'=예약
     */
    private Map<String, Object> buildDtlParams(long requestKey, String opt, SvcChgApplyReqDto req) {
        Map<String, Object> p = new HashMap<>();
        p.put("requestKey", requestKey);
        p.put("svcTgtCd",   opt);
        p.put("svcChgTypeCd", "1"); // 기본: 신청
        p.put("procTypeCd",   "1"); // 기본: 즉시

        switch (opt) {
            case "WIRELESS_BLOCK":
                p.put("svcChgTypeCd", "block".equals(req.getWirelessBlock()) ? "1" : "2");
                p.put("prodId", SOC_WIRELESS_BLOCK);
                break;
            case "INFO_LIMIT":
                p.put("svcChgTypeCd", "0".equals(req.getInfoLimit()) ? "2" : "1");
                p.put("addtionInfo", req.getInfoLimit());
                break;
            case "RATE_CHANGE":
                p.put("svcChgTypeCd", "1");
                p.put("procTypeCd",   "reserve".equals(req.getRateChangeSchedule()) ? "2" : "1");
                p.put("prodId", req.getRatePlanSoc());
                break;
            case "USIM_CHANGE":
                p.put("reqUsimSn", req.getUsimChange());
                break;
            case "NUM_CHANGE":
                p.put("chgMobileNo", req.getNumChange());
                break;
            case "LOST_RESTORE":
                p.put("releasePwd", req.getPausePassword());
                break;
            default:
                break;
        }
        return p;
    }

    private AdditionRegReqDto buildRegDto(SvcChgApplyReqDto req, String soc, String ftrNewParam) {
        AdditionRegReqDto dto = new AdditionRegReqDto();
        dto.setNcn(req.getNcn());
        dto.setCtn(req.getCtn());
        dto.setCustId(req.getCustId());
        dto.setName(req.getName());
        dto.setSoc(soc);
        dto.setFtrNewParam(ftrNewParam);
        return dto;
    }

    private AdditionCancelReqDto buildCancelDto(SvcChgApplyReqDto req, String soc) {
        AdditionCancelReqDto dto = new AdditionCancelReqDto();
        dto.setNcn(req.getNcn());
        dto.setCtn(req.getCtn());
        dto.setCustId(req.getCustId());
        dto.setName(req.getName());
        dto.setSoc(soc);
        return dto;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
