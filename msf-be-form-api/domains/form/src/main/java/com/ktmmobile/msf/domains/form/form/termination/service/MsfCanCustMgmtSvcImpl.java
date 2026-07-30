package com.ktmmobile.msf.domains.form.form.termination.service;


import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.form.common.code.ResTermMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstWebServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfOsstHistoryService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpOsstCanPrcVO;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvcChgPageServiceImpl;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.DetailDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ListResDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.MetaDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.PageMetaDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.PageReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessStatusDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CanCustMgmtDto.ProcessUpdateDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.CanCustMgmtRepositoryImpl;

@Slf4j
@Service
public class MsfCanCustMgmtSvcImpl {

    private static final String FORM_TYPE_CANCEL = "4";
    private static final int RES_MSG_MAX_LENGTH = 100;

    @Autowired
    private CanCustMgmtRepositoryImpl canCustMgmtRepository;

    @Autowired
    private MsfMplatFormOsstWebServerAdapter mplatFormOsstWebServerAdapter;

    @Autowired
    private MsfSvcChgPageServiceImpl msfSvcChgPageService;

    @Autowired
    private MsfOsstHistoryService msfOsstHistoryService;

    public ListResDto list(ListReqDto req) {
        log.info("[list] procCd={}, formTypeCd={}, searchGbn={}, searchName={}, startDt={}, endDt={}",
            req.getProcCd(), req.getFormTypeCd(), req.getSearchGbn(), req.getSearchName(), req.getStartDt(), req.getEndDt());
        return selectAppFormList(req);
    }

    public DetailDto get(ProcessReqDto req) {
        log.info("[get] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return null;
        }
        ProcessStatusDto status = canCustMgmtRepository.selectApplicationStatus(req.getRequestKey());
        if (status == null) {
            return null;
        }
        if (isCancelForm(status)) {
            return selectCanCustDetail(req.getRequestKey());
        }
        return canCustMgmtRepository.selectApplicationDetail(req.getRequestKey());
    }

    public FormResponse<ProcessResVO> statusCheck(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        log.info("[statusCheck] requestKey={}", req.getRequestKey());

        FormResponse<ProcessResVO> res;
        if (req.getRequestKey() == null) {
            res = FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        } else {
            ProcessStatusDto status = canCustMgmtRepository.selectApplicationStatus(req.getRequestKey());
            if (status == null) {
                res = FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
            } else if (!isCancelForm(status)) {
                res = FormResponse.of(ResTermMessage.ADMIN_PROCESS_NOT_SUPPORTED);
            } else if ("RC".equals(status.getProcCd())) {
                res = FormResponse.of(ResTermMessage.ADMIN_ALREADY_COMPLETED);
            } else if (!isCancelableCompleteStatus(status.getProcCd())) {
                res = FormResponse.of(ResTermMessage.ADMIN_CANCEL_COMPLETE_STATUS_INVALID);
            } else {
                res = FormResponse.of(ResTermMessage.SUCCESS);
            }
        }

        logAdminResponse("[statusCheck]", req.getRequestKey(), res, startedAt);
        return res;
    }

    public FormResponse<ProcessResVO> complete(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        log.info("[complete] requestKey={}, itgOderWhyCd={}, aftmnIncInCd={}, apyRelTypeCd={}, custTchMediCd={}",
            req.getRequestKey(), req.getItgOderWhyCd(), req.getAftmnIncInCd(),
            req.getApyRelTypeCd(), req.getCustTchMediCd());

        FormResponse<ProcessResVO> res = validateCancelProcessTarget(req);
        if (res == null) {
            FormResponse<ProcessResVO> validationError = validateCompleteRequest(req);
            res = validationError != null ? validationError : processCancelComplete(req);
        }
        long elapsed = System.currentTimeMillis() - startedAt;

        if (res != null && ResTermMessage.SUCCESS.getCode().equals(res.resCode())) {
            log.info("[complete] result: requestKey={}, success={}, osstOrdNo={}, elapsedMs={}",
                req.getRequestKey(), true, res.resData() != null ? res.resData().getOsstOrdNo() : "", elapsed);
        } else {
            String resCode = res != null ? res.resCode() : "";
            String resMessage = res != null ? res.resMessage() : "null response";
            log.warn("[complete] failed: requestKey={}, success={}, resCode={}, resMessage={}, elapsedMs={}",
                req.getRequestKey(), false, resCode, resMessage, elapsed);
        }

        return res;
    }

    public FormResponse<ProcessResVO> revert(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        log.info("[revert] requestKey={}", req.getRequestKey());

        FormResponse<ProcessResVO> res = validateCancelProcessTarget(req);
        if (res == null) {
            res = processCancelRevert(req.getRequestKey());
        }

        logAdminResponse("[revert]", req.getRequestKey(), res, startedAt);
        return res;
    }

    public FormResponse<ProcessResVO> reject(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        log.info("[reject] requestKey={}", req.getRequestKey());

        FormResponse<ProcessResVO> res = validateCancelProcessTarget(req);
        if (res == null) {
            res = processCancelReject(req);
        }

        logAdminResponse("[reject]", req.getRequestKey(), res, startedAt);
        return res;
    }

    public ListResDto selectAppFormList(ListReqDto req) {
        PageReqDto pageReq = req.getPage();
        if (pageReq == null) {
            pageReq = new PageReqDto();
            pageReq.setPageNum(1);
            pageReq.setRowSize(10);
            req.setPage(pageReq);
        } else {
            if (pageReq.getPageNum() == null || pageReq.getPageNum() <= 0) {
                pageReq.setPageNum(1);
            }
            if (pageReq.getRowSize() == null || pageReq.getRowSize() <= 0) {
                pageReq.setRowSize(10);
            }
        }

        int totalCount = canCustMgmtRepository.selectAppFormListCount(req);
        ListResDto response = new ListResDto();
        response.setData(canCustMgmtRepository.selectAppFormList(req));

        PageMetaDto pageMeta = new PageMetaDto();
        pageMeta.setPageNum(pageReq.getPageNum());
        pageMeta.setRowSize(pageReq.getRowSize());
        pageMeta.setTotalCount(totalCount);

        MetaDto meta = new MetaDto();
        meta.setPage(pageMeta);
        response.setMeta(meta);
        return response;
    }

    private FormResponse<ProcessResVO> validateCompleteRequest(ProcessReqDto req) {
        if (req.getRequestKey() == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        }
        if (StringUtils.isBlank(req.getItgOderWhyCd())) {
            return FormResponse.of(ResTermMessage.ADMIN_CANCEL_REASON_REQUIRED);
        }
        if ("01".equals(req.getItgOderWhyCd())) {
            return FormResponse.of(
                ResTermMessage.ADMIN_CANCEL_REASON_REQUIRED,
                "EP0 해지사유코드(itgOderWhyCd) [01]은 OSST 허용값이 아닙니다. 실제 EP0 해지사유코드를 전달해주세요.",
                null
            );
        }
        if (StringUtils.isBlank(req.getAftmnIncInCd())) {
            return FormResponse.of(ResTermMessage.ADMIN_AFTER_INCLINATION_REQUIRED);
        }
        if (StringUtils.isBlank(req.getApyRelTypeCd())) {
            return FormResponse.of(ResTermMessage.ADMIN_REL_TYPE_REQUIRED);
        }
        if (StringUtils.isBlank(req.getCustTchMediCd())) {
            return FormResponse.of(ResTermMessage.ADMIN_TOUCH_MEDIA_REQUIRED);
        }
        return null;
    }

    public DetailDto selectCanCustDetail(Long requestKey) {
        return canCustMgmtRepository.selectCanCustDetail(requestKey);
    }

    @Transactional
    @BusinessContextBoundary
    public FormResponse<ProcessResVO> processCancelComplete(ProcessReqDto req) {
        Long requestKey = req.getRequestKey();
        log.info("[processCancelComplete] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            log.warn("[processCancelComplete] not found: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if ("RC".equals(currentProcCd)) {
            log.warn("[processCancelComplete] already completed: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_ALREADY_COMPLETED);
        }
        if (!isCancelableCompleteStatus(currentProcCd)) {
            log.warn("[processCancelComplete] invalid procCd: requestKey={}, procCd={}", requestKey, currentProcCd);
            return FormResponse.of(ResTermMessage.ADMIN_CANCEL_COMPLETE_STATUS_INVALID);
        }

        DetailDto detail = canCustMgmtRepository.selectCanCustDetail(requestKey);
        if (detail == null) {
            log.error("[processCancelComplete] detail not found: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_DETAIL_NOT_FOUND);
        }
        BusinessContextHolder.setParentScanId(detail.getParentScanId());

        String ncn = detail.getContractNum();
        String ctn = detail.getCancelMobileNo();
        String cntplcNo = detail.getReceiveMobileNo();
        String smsRcvYn = StringUtils.defaultIfBlank(req.getSmsRcvYn(), "Y");

        FormResponse<ProcessResVO> requiredValueError = validateCancelRequiredValues(detail);
        if (requiredValueError != null) {
            return requiredValueError;
        }

        String custId = resolveCustIdForEp0(requestKey, ncn);
        if (StringUtils.isBlank(custId)) {
            log.warn("[processCancelComplete] EP0 required custId missing: requestKey={}, ncn={}, ctn={}",
                requestKey, ncn, ctn);
            updateCancelProcessResult(
                requestKey,
                req.getMemo(),
                "EP0_REQUIRED_MISSING",
                "EP0 필수 고객번호(custId)를 찾을 수 없습니다.",
                null
            );
            return FormResponse.of(
                ResTermMessage.ADMIN_CANCEL_REQUIRED_FIELD_MISSING,
                "EP0 필수 고객번호(custId)를 찾을 수 없습니다.",
                null
            );
        }

        log.info("[processCancelComplete] EP0 call: requestKey={}, ncn={}, ctn={}, custIdPresent={}",
            requestKey, ncn, ctn, !StringUtils.isBlank(custId));

        MpOsstCanPrcVO ep0Vo;
        try {
            ep0Vo = new MpOsstCanPrcVO();
            mplatFormOsstWebServerAdapter.callService(
                buildEp0Param(
                    requestKey,
                    detail.getResNo(),
                    ncn,
                    ctn,
                    custId,
                    cntplcNo,
                    req.getItgOderWhyCd(),
                    req.getAftmnIncInCd(),
                    req.getApyRelTypeCd(),
                    req.getCustTchMediCd(),
                    smsRcvYn
                ),
                ep0Vo
            );
        } catch (SelfServiceException e) {
            log.warn("[processCancelComplete] EP0 business error: requestKey={}, resultCode={}, globalNo={}, message={}",
                requestKey, e.getResultCode(), e.getGlobalNo(), e.getMessage());
            updateCancelProcessResult(requestKey, req.getMemo(), e.getResultCode(), e.getMessage(), e.getGlobalNo());
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_FAILED,
                buildCancelProcessFailureMessage(e.getMessage()),
                null
            );
        } catch (Exception e) {
            log.error("[processCancelComplete] EP0 exception: requestKey={}", requestKey, e);
            saveEp0FallbackHistory(requestKey, detail, null, "EP0_ERROR", e.getMessage(), null);
            updateCancelProcessResult(requestKey, req.getMemo(), "EP0_ERROR", e.getMessage(), null);
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_ERROR,
                buildCancelProcessFailureMessage(e.getMessage()),
                null
            );
        }

        if (ep0Vo == null) {
            log.error("[processCancelComplete] EP0 null response: requestKey={}", requestKey);
            saveEp0FallbackHistory(requestKey, detail, null, "EP0_EMPTY", null, null);
            updateCancelProcessResult(
                requestKey,
                req.getMemo(),
                "EP0_EMPTY",
                ResTermMessage.ADMIN_EP0_EMPTY.getMessage(),
                null
            );
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_EMPTY,
                buildCancelProcessFailureMessage(ResTermMessage.ADMIN_EP0_EMPTY.getMessage()),
                null
            );
        }

        log.info(
            "[processCancelComplete] EP0 response: requestKey={}, rslt={}, osstOrdNo={}",
            requestKey,
            ep0Vo.getRslt(),
            ep0Vo.getOsstOrdNo()
        );

        if (!"S".equals(ep0Vo.getRslt())) {
            if (StringUtils.isBlank(ep0Vo.getRslt()) && StringUtils.isBlank(ep0Vo.getOsstOrdNo())) {
                saveEp0FallbackHistory(requestKey, detail, null, "EP0_EMPTY", ep0Vo.getRsltMsg(), ep0Vo.getGlobalNo());
            }
            updateCancelProcessResult(
                requestKey,
                req.getMemo(),
                ep0Vo.getRslt(),
                ep0Vo.getRsltMsg(),
                ep0Vo.getOsstOrdNo()
            );
            log.warn(
                "[processCancelComplete] EP0 failed: requestKey={}, rslt={}, rsltMsg={}",
                requestKey,
                ep0Vo.getRslt(),
                ep0Vo.getRsltMsg()
            );
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_FAILED,
                buildCancelProcessFailureMessage(ep0Vo.getRsltMsg()),
                null
            );
        }

        // EP0 호출 완료 시각을 OPEN_REQ_DT(개통MP 호출일시)에 기록
        canCustMgmtRepository.updateCancelOpenReqDt(requestKey);

        ProcessUpdateDto updateReq = new ProcessUpdateDto();
        updateReq.setRequestKey(requestKey);
        updateReq.setProcCd("RC");
        updateReq.setMemo(req.getMemo());
        updateReq.setResCd(ep0Vo.getRslt());
        updateReq.setResMsg(ep0Vo.getRsltMsg());
        updateReq.setResNo(ep0Vo.getOsstOrdNo());

        // PROC_DT(처리일시) 포함 상태 업데이트
        int updated = canCustMgmtRepository.updateCanCustProcCd(updateReq);
        if (updated <= 0) {
            log.error("[processCancelComplete] DB update failed: requestKey={}, updated={}", requestKey, updated);
            return FormResponse.of(ResTermMessage.ADMIN_COMPLETE_SAVE_FAILED);
        }

        log.info("[processCancelComplete] success: requestKey={}, osstOrdNo={}", requestKey, ep0Vo.getOsstOrdNo());
        return FormResponse.of(ResTermMessage.SUCCESS, ProcessResVO.complete(ep0Vo.getOsstOrdNo()));
    }

    private void updateCancelProcessResult(Long requestKey, String memo, String resCd, String resMsg, String resNo) {
        ProcessUpdateDto updateReq = new ProcessUpdateDto();
        updateReq.setRequestKey(requestKey);
        updateReq.setMemo(memo);
        updateReq.setResCd(StringUtils.defaultIfBlank(resCd, "EP0_FAILED"));
        updateReq.setResMsg(StringUtils.left(resMsg, RES_MSG_MAX_LENGTH));
        updateReq.setResNo(resNo);

        int updated = canCustMgmtRepository.updateCanCustProcessResult(updateReq);
        if (updated <= 0) {
            log.warn("[processCancelComplete] process result update skipped: requestKey={}, updated={}", requestKey, updated);
        }
    }

    @Transactional
    public FormResponse<ProcessResVO> processCancelRevert(Long requestKey) {
        log.info("[processCancelRevert] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if (!"RC".equals(currentProcCd)) {
            return FormResponse.of(ResTermMessage.ADMIN_COMPLETE_ONLY_REVERT);
        }

        ProcessUpdateDto revertReq = new ProcessUpdateDto();
        revertReq.setRequestKey(requestKey);
        revertReq.setProcCd("RQ");

        int updated = canCustMgmtRepository.updateCanCustProcCd(revertReq);
        if (updated <= 0) {
            return FormResponse.of(ResTermMessage.ADMIN_REVERT_SAVE_FAILED);
        }

        log.info("[processCancelRevert] success: requestKey={}", requestKey);
        return FormResponse.of(ResTermMessage.SUCCESS, ProcessResVO.revert());
    }

    @Transactional
    public FormResponse<ProcessResVO> processCancelReject(ProcessReqDto req) {
        Long requestKey = req.getRequestKey();
        log.info("[processCancelReject] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if (!isCancelableCompleteStatus(currentProcCd)) {
            return FormResponse.of(ResTermMessage.ADMIN_REJECT_STATUS_INVALID);
        }

        ProcessUpdateDto rejectReq = new ProcessUpdateDto();
        rejectReq.setRequestKey(requestKey);
        rejectReq.setProcCd("BK");
        rejectReq.setMemo(req.getMemo());

        int updated = canCustMgmtRepository.updateCanCustProcCd(rejectReq);
        if (updated <= 0) {
            return FormResponse.of(ResTermMessage.ADMIN_REJECT_SAVE_FAILED);
        }

        int mcpUpdated = canCustMgmtRepository.updateMcpCancelRequestProcCd(rejectReq);
        if (mcpUpdated <= 0) {
            log.error("[processCancelReject] MCP update failed: requestKey={}, mcpUpdated={}", requestKey, mcpUpdated);
            return FormResponse.of(ResTermMessage.ADMIN_REJECT_SAVE_FAILED);
        }

        log.info("[processCancelReject] success: requestKey={}", requestKey);
        return FormResponse.of(ResTermMessage.SUCCESS, ProcessResVO.reject());
    }

    private FormResponse<ProcessResVO> validateCancelProcessTarget(ProcessReqDto req) {
        if (req.getRequestKey() == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        }

        ProcessStatusDto status = canCustMgmtRepository.selectApplicationStatus(req.getRequestKey());
        if (status == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if (!isCancelForm(status)) {
            log.warn("[validateCancelProcessTarget] unsupported formTypeCd: requestKey={}, formTypeCd={}, procCd={}",
                req.getRequestKey(), status.getFormTypeCd(), status.getProcCd());
            return FormResponse.of(ResTermMessage.ADMIN_PROCESS_NOT_SUPPORTED);
        }
        return null;
    }

    private FormResponse<ProcessResVO> validateCancelRequiredValues(DetailDto detail) {
        if (StringUtils.isBlank(detail.getContractNum())
            || StringUtils.isBlank(detail.getCancelMobileNo())
            || StringUtils.isBlank(detail.getReceiveMobileNo())) {
            log.warn("[processCancelComplete] required cancel fields missing: requestKey={}, ncnBlank={}, ctnBlank={}, cntplcNoBlank={}",
                detail.getRequestKey(),
                StringUtils.isBlank(detail.getContractNum()),
                StringUtils.isBlank(detail.getCancelMobileNo()),
                StringUtils.isBlank(detail.getReceiveMobileNo()));
            return FormResponse.of(ResTermMessage.ADMIN_CANCEL_REQUIRED_FIELD_MISSING);
        }
        return null;
    }

    private boolean isCancelForm(ProcessStatusDto status) {
        return status != null && FORM_TYPE_CANCEL.equals(status.getFormTypeCd());
    }

    private boolean isCancelableCompleteStatus(String procCd) {
        return "RQ".equals(procCd);
    }

    private String resolveCustIdForEp0(Long requestKey, String ncn) {
        if (StringUtils.isBlank(ncn)) {
            return "";
        }
        try {
            // EP0 inDto.custId는 필수값이다. 신청 상세에는 custId가 없으므로 계약번호로 최신 회선 정보를 보강한다.
            McpUserCntrMngDto cntrInfo = msfSvcChgPageService.selectCntrListNoLogin(ncn, false);
            String custId = cntrInfo == null ? "" : StringUtil.NVL(cntrInfo.getCustId(), "");
            log.info("[processCancelComplete] EP0 custId resolved: requestKey={}, ncn={}, custIdPresent={}",
                requestKey, ncn, !StringUtils.isBlank(custId));
            return custId;
        } catch (Exception e) {
            log.warn("[processCancelComplete] EP0 custId resolve failed: requestKey={}, ncn={}",
                requestKey, ncn, e);
            return "";
        }
    }

    private void saveEp0FallbackHistory(
        Long requestKey,
        DetailDto detail,
        String osstOrdNo,
        String rsltCd,
        String rsltMsg,
        String nstepGlobalId
    ) {
        if (requestKey == null) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("appEventCd", "EP0");
        params.put("resNo", resolveEp0MvnoOrdNo(requestKey, detail));
        params.put("requestKey", String.valueOf(requestKey));
        params.put("ctn", detail == null ? "" : StringUtil.NVL(detail.getCancelMobileNo(), ""));
        params.put("ip", RequestUtils.getClientIp());
        params.put("userid", getSessionUserId());
        msfOsstHistoryService.saveFallback(params, osstOrdNo, rsltCd, rsltMsg, nstepGlobalId);
    }

    private String resolveEp0MvnoOrdNo(Long requestKey, DetailDto detail) {
        if (detail != null && StringUtils.isNotBlank(detail.getResNo())) {
            return detail.getResNo();
        }
        return String.valueOf(requestKey);
    }

    private void logAdminResponse(String action, Long requestKey, FormResponse<ProcessResVO> res, long startedAt) {
        long elapsed = System.currentTimeMillis() - startedAt;
        if (res != null && ResTermMessage.SUCCESS.getCode().equals(res.resCode())) {
            log.info("[logAdminResponse] action={}, result: requestKey={}, success={}, procCd={}, osstOrdNo={}, elapsedMs={}",
                action,
                requestKey,
                true,
                res.resData() != null ? res.resData().getProcCd() : "",
                res.resData() != null ? res.resData().getOsstOrdNo() : "",
                elapsed);
        } else {
            String resCode = res != null ? res.resCode() : "";
            String resMessage = res != null ? res.resMessage() : "null response";
            log.warn("[logAdminResponse] action={}, failed: requestKey={}, success={}, resCode={}, resMessage={}, elapsedMs={}",
                action, requestKey, false, resCode, resMessage, elapsed);
        }
    }

    private HashMap<String, String> buildEp0Param(
        Long requestKey,
        String resNo,
        String ncn,
        String ctn,
        String custId,
        String cntplcNo,
        String itgOderWhyCd,
        String aftmnIncInCd,
        String apyRelTypeCd,
        String custTchMediCd,
        String smsRcvYn
    ) {
        HashMap<String, String> param = new HashMap<>();
        param.put("appEventCd", "EP0");
        param.put("resNo", StringUtil.NVL(resNo, ""));
        if (requestKey != null) {
            param.put("requestKey", String.valueOf(requestKey));
        }
        param.put("ncn", StringUtil.NVL(ncn, ""));
        param.put("ctn", StringUtil.NVL(ctn, ""));
        param.put("custId", StringUtil.NVL(custId, ""));
        param.put("ip", RequestUtils.getClientIp());
        param.put("userid", getSessionUserId());
        param.put("cntplcNo", StringUtil.NVL(cntplcNo, ""));
        param.put("itgOderWhyCd", StringUtil.NVL(itgOderWhyCd, ""));
        param.put("aftmnIncInCd", StringUtil.NVL(aftmnIncInCd, ""));
        param.put("apyRelTypeCd", StringUtil.NVL(apyRelTypeCd, ""));
        param.put("custTchMediCd", StringUtil.NVL(custTchMediCd, ""));
        param.put("smsRcvYn", StringUtil.NVL(smsRcvYn, ""));
        return param;
    }

    private String buildCancelProcessFailureMessage(String message) {
        String detailMessage = StringUtils.defaultIfBlank(message, "해지처리 오류");
        return "해지처리에 실패했습니다.\n"
            + "- " + detailMessage;
    }

    private String getSessionUserId() {
        try {
            return StringUtil.NVL(AuthenticationUtils.getUser().getUserId(), "");
        } catch (RuntimeException e) {
            return "";
        }
    }
}
