package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.form.common.code.ResTermMessage;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstWebServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpOsstCanPrcVO;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
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

@Service
public class MsfCanCustMgmtSvcImpl implements MsfCanCustMgmtSvc {

    private static final String FORM_TYPE_CANCEL = "4";

    private static final Logger logger = LoggerFactory.getLogger(MsfCanCustMgmtSvcImpl.class);

    @Autowired
    private CanCustMgmtRepositoryImpl canCustMgmtRepository;

    @Autowired
    private MsfMplatFormOsstWebServerAdapter mplatFormOsstWebServerAdapter;

    @Override
    public ListResDto list(ListReqDto req) {
        logger.info("[admin/cancel/list] procCd={}, formTypeCd={}, searchGbn={}, searchName={}, startDt={}, endDt={}",
            req.getProcCd(), req.getFormTypeCd(), req.getSearchGbn(), req.getSearchName(), req.getStartDt(), req.getEndDt());
        return selectAppFormList(req);
    }

    @Override
    public DetailDto get(ProcessReqDto req) {
        logger.info("[admin/application/get] requestKey={}", req.getRequestKey());
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

    @Override
    public FormResponse<ProcessResVO> statusCheck(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        logger.info("[admin/cancel/status/check] requestKey={}", req.getRequestKey());

        FormResponse<ProcessResVO> res;
        if (req.getRequestKey() == null) {
            res = FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        } else {
            ProcessStatusDto status = canCustMgmtRepository.selectApplicationStatus(req.getRequestKey());
            if (status == null) {
                res = FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
            } else if (!isCancelForm(status)) {
                res = FormResponse.of(ResTermMessage.ADMIN_PROCESS_NOT_SUPPORTED);
            } else if ("CP".equals(status.getProcCd())) {
                res = FormResponse.of(ResTermMessage.ADMIN_ALREADY_COMPLETED);
            } else if (!isCancelableCompleteStatus(status.getProcCd())) {
                res = FormResponse.of(ResTermMessage.ADMIN_CANCEL_COMPLETE_STATUS_INVALID);
            } else {
                res = FormResponse.of(ResTermMessage.SUCCESS);
            }
        }

        logAdminResponse("[admin/cancel/status/check]", req.getRequestKey(), res, startedAt);
        return res;
    }

    @Override
    public FormResponse<ProcessResVO> complete(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        logger.info("[admin/cancel/complete] requestKey={}, itgOderWhyCd={}, aftmnIncInCd={}, apyRelTypeCd={}, custTchMediCd={}",
            req.getRequestKey(), req.getItgOderWhyCd(), req.getAftmnIncInCd(),
            req.getApyRelTypeCd(), req.getCustTchMediCd());

        FormResponse<ProcessResVO> res = validateCancelProcessTarget(req);
        if (res == null) {
            FormResponse<ProcessResVO> validationError = validateCompleteRequest(req);
            res = validationError != null ? validationError : processCancelComplete(req);
        }
        long elapsed = System.currentTimeMillis() - startedAt;

        if (res != null && ResTermMessage.SUCCESS.getCode().equals(res.resCode())) {
            logger.info("[admin/cancel/complete] result: requestKey={}, success={}, osstOrdNo={}, elapsedMs={}",
                req.getRequestKey(), true, res.resData() != null ? res.resData().getOsstOrdNo() : "", elapsed);
        } else {
            String resCode = res != null ? res.resCode() : "";
            String resMessage = res != null ? res.resMessage() : "null response";
            logger.warn("[admin/cancel/complete] failed: requestKey={}, success={}, resCode={}, resMessage={}, elapsedMs={}",
                req.getRequestKey(), false, resCode, resMessage, elapsed);
        }

        return res;
    }

    @Override
    public FormResponse<ProcessResVO> revert(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        logger.info("[admin/cancel/revert] requestKey={}", req.getRequestKey());

        FormResponse<ProcessResVO> res = validateCancelProcessTarget(req);
        if (res == null) {
            res = processCancelRevert(req.getRequestKey());
        }

        logAdminResponse("[admin/cancel/revert]", req.getRequestKey(), res, startedAt);
        return res;
    }

    @Override
    public FormResponse<ProcessResVO> reject(ProcessReqDto req) {
        long startedAt = System.currentTimeMillis();
        logger.info("[admin/cancel/reject] requestKey={}", req.getRequestKey());

        FormResponse<ProcessResVO> res = validateCancelProcessTarget(req);
        if (res == null) {
            res = processCancelReject(req);
        }

        logAdminResponse("[admin/cancel/reject]", req.getRequestKey(), res, startedAt);
        return res;
    }

    @Override
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

    @Override
    public DetailDto selectCanCustDetail(Long requestKey) {
        return canCustMgmtRepository.selectCanCustDetail(requestKey);
    }

    @Override
    @Transactional
    public FormResponse<ProcessResVO> processCancelComplete(ProcessReqDto req) {
        Long requestKey = req.getRequestKey();
        logger.info("[processCancelComplete] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            logger.warn("[processCancelComplete] not found: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if ("CP".equals(currentProcCd)) {
            logger.warn("[processCancelComplete] already completed: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_ALREADY_COMPLETED);
        }
        if (!isCancelableCompleteStatus(currentProcCd)) {
            logger.warn("[processCancelComplete] invalid procCd: requestKey={}, procCd={}", requestKey, currentProcCd);
            return FormResponse.of(ResTermMessage.ADMIN_CANCEL_COMPLETE_STATUS_INVALID);
        }

        DetailDto detail = canCustMgmtRepository.selectCanCustDetail(requestKey);
        if (detail == null) {
            logger.error("[processCancelComplete] detail not found: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_DETAIL_NOT_FOUND);
        }

        String ncn = detail.getContractNum();
        String ctn = detail.getCancelMobileNo();
        String cntplcNo = detail.getReceiveMobileNo();
        String custId = "";
        String smsRcvYn = StringUtils.defaultIfBlank(req.getSmsRcvYn(), "Y");

        FormResponse<ProcessResVO> requiredValueError = validateCancelRequiredValues(detail);
        if (requiredValueError != null) {
            return requiredValueError;
        }

        logger.info("[processCancelComplete] EP0 call: requestKey={}, ncn={}, ctn={}", requestKey, ncn, ctn);

        MpOsstCanPrcVO ep0Vo;
        try {
            ep0Vo = new MpOsstCanPrcVO();
            mplatFormOsstWebServerAdapter.callService(
                buildEp0Param(
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
        } catch (Exception e) {
            logger.error("[processCancelComplete] EP0 exception: requestKey={}", requestKey, e);
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_ERROR,
                ResTermMessage.ADMIN_EP0_ERROR.getMessage() + ": " + e.getMessage(),
                null
            );
        }

        if (ep0Vo == null) {
            logger.error("[processCancelComplete] EP0 null response: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_EP0_EMPTY);
        }

        logger.info(
            "[processCancelComplete] EP0 response: requestKey={}, rslt={}, osstOrdNo={}",
            requestKey,
            ep0Vo.getRslt(),
            ep0Vo.getOsstOrdNo()
        );

        if (!"S".equals(ep0Vo.getRslt())) {
            logger.warn(
                "[processCancelComplete] EP0 failed: requestKey={}, rslt={}, rsltMsg={}",
                requestKey,
                ep0Vo.getRslt(),
                ep0Vo.getRsltMsg()
            );
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_FAILED,
                ResTermMessage.ADMIN_EP0_FAILED.getMessage() + ": " + StringUtils.defaultIfBlank(ep0Vo.getRsltMsg(), "EP0 오류"),
                null
            );
        }

        ProcessUpdateDto updateReq = new ProcessUpdateDto();
        updateReq.setRequestKey(requestKey);
        updateReq.setProcCd("CP");
        updateReq.setMemo(req.getMemo());
        updateReq.setResCd(ep0Vo.getRslt());
        updateReq.setResMsg(ep0Vo.getRsltMsg());
        updateReq.setResNo(ep0Vo.getOsstOrdNo());

        int updated = canCustMgmtRepository.updateCanCustProcCd(updateReq);
        if (updated <= 0) {
            logger.error("[processCancelComplete] DB update failed: requestKey={}, updated={}", requestKey, updated);
            return FormResponse.of(ResTermMessage.ADMIN_COMPLETE_SAVE_FAILED);
        }

        logger.info("[processCancelComplete] success: requestKey={}, osstOrdNo={}", requestKey, ep0Vo.getOsstOrdNo());
        return FormResponse.of(ResTermMessage.SUCCESS, ProcessResVO.complete(ep0Vo.getOsstOrdNo()));
    }

    @Override
    @Transactional
    public FormResponse<ProcessResVO> processCancelRevert(Long requestKey) {
        logger.info("[processCancelRevert] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if (!"CP".equals(currentProcCd)) {
            return FormResponse.of(ResTermMessage.ADMIN_COMPLETE_ONLY_REVERT);
        }

        ProcessUpdateDto revertReq = new ProcessUpdateDto();
        revertReq.setRequestKey(requestKey);
        revertReq.setProcCd("RC");

        int updated = canCustMgmtRepository.updateCanCustProcCd(revertReq);
        if (updated <= 0) {
            return FormResponse.of(ResTermMessage.ADMIN_REVERT_SAVE_FAILED);
        }

        logger.info("[processCancelRevert] success: requestKey={}", requestKey);
        return FormResponse.of(ResTermMessage.SUCCESS, ProcessResVO.revert());
    }

    @Override
    @Transactional
    public FormResponse<ProcessResVO> processCancelReject(ProcessReqDto req) {
        Long requestKey = req.getRequestKey();
        logger.info("[processCancelReject] start: requestKey={}", requestKey);

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

        logger.info("[processCancelReject] success: requestKey={}", requestKey);
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
            logger.warn("[admin/application/process] unsupported formTypeCd: requestKey={}, formTypeCd={}, procCd={}",
                req.getRequestKey(), status.getFormTypeCd(), status.getProcCd());
            return FormResponse.of(ResTermMessage.ADMIN_PROCESS_NOT_SUPPORTED);
        }
        return null;
    }

    private FormResponse<ProcessResVO> validateCancelRequiredValues(DetailDto detail) {
        if (StringUtils.isBlank(detail.getContractNum())
            || StringUtils.isBlank(detail.getCancelMobileNo())
            || StringUtils.isBlank(detail.getReceiveMobileNo())) {
            logger.warn("[processCancelComplete] required cancel fields missing: requestKey={}, ncnBlank={}, ctnBlank={}, cntplcNoBlank={}",
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
        return "RC".equals(procCd) || "RQ".equals(procCd);
    }

    private void logAdminResponse(String action, Long requestKey, FormResponse<ProcessResVO> res, long startedAt) {
        long elapsed = System.currentTimeMillis() - startedAt;
        if (res != null && ResTermMessage.SUCCESS.getCode().equals(res.resCode())) {
            logger.info("{} result: requestKey={}, success={}, procCd={}, osstOrdNo={}, elapsedMs={}",
                action,
                requestKey,
                true,
                res.resData() != null ? res.resData().getProcCd() : "",
                res.resData() != null ? res.resData().getOsstOrdNo() : "",
                elapsed);
        } else {
            String resCode = res != null ? res.resCode() : "";
            String resMessage = res != null ? res.resMessage() : "null response";
            logger.warn("{} failed: requestKey={}, success={}, resCode={}, resMessage={}, elapsedMs={}",
                action, requestKey, false, resCode, resMessage, elapsed);
        }
    }

    private HashMap<String, String> buildEp0Param(
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
        param.put("ncn", StringUtil.NVL(ncn, ""));
        param.put("ctn", StringUtil.NVL(ctn, ""));
        param.put("custId", StringUtil.NVL(custId, ""));
        param.put("userid", getSessionUserId());
        param.put("cntplcNo", StringUtil.NVL(cntplcNo, ""));
        param.put("itgOderWhyCd", StringUtil.NVL(itgOderWhyCd, ""));
        param.put("aftmnIncInCd", StringUtil.NVL(aftmnIncInCd, ""));
        param.put("apyRelTypeCd", StringUtil.NVL(apyRelTypeCd, ""));
        param.put("custTchMediCd", StringUtil.NVL(custTchMediCd, ""));
        param.put("smsRcvYn", StringUtil.NVL(smsRcvYn, ""));
        return param;
    }

    private String getSessionUserId() {
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto == null) {
            return "";
        }
        return StringUtil.NVL(userSessionDto.getUserId(), "");
    }
}
