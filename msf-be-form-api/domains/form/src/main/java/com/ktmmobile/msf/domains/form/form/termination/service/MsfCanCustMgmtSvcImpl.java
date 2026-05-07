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
import com.ktmmobile.msf.domains.form.form.termination.repository.CanCustMgmtRepositoryImpl;

@Service
public class MsfCanCustMgmtSvcImpl implements MsfCanCustMgmtSvc {

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
        logger.info("[admin/cancel/get] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return null;
        }
        return selectCanCustDetail(req.getRequestKey());
    }

    @Override
    public FormResponse<ProcessResVO> statusCheck(ProcessReqDto req) {
        logger.info("[admin/cancel/status/check] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        }

        DetailDto detail = selectCanCustDetail(req.getRequestKey());
        if (detail == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if ("CP".equals(detail.getProcCd())) {
            return FormResponse.of(ResTermMessage.ADMIN_ALREADY_COMPLETED);
        }
        return FormResponse.ok(null);
    }

    @Override
    public FormResponse<ProcessResVO> complete(ProcessReqDto req) {
        logger.info("[admin/cancel/complete] requestKey={}, itgOderWhyCd={}, aftmnIncInCd={}, apyRelTypeCd={}, custTchMediCd={}",
            req.getRequestKey(), req.getItgOderWhyCd(), req.getAftmnIncInCd(),
            req.getApyRelTypeCd(), req.getCustTchMediCd());

        FormResponse<ProcessResVO> validationError = validateCompleteRequest(req);
        if (validationError != null) {
            return validationError;
        }
        return processComplete(req);
    }

    @Override
    public FormResponse<ProcessResVO> revert(ProcessReqDto req) {
        logger.info("[admin/cancel/revert] requestKey={}", req.getRequestKey());
        if (req.getRequestKey() == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        }
        return processRevert(req.getRequestKey());
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
    public FormResponse<ProcessResVO> processComplete(ProcessReqDto req) {
        Long requestKey = req.getRequestKey();
        logger.info("[processComplete] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            logger.warn("[processComplete] not found: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if ("CP".equals(currentProcCd)) {
            logger.warn("[processComplete] already completed: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_ALREADY_COMPLETED);
        }

        DetailDto detail = canCustMgmtRepository.selectCanCustDetail(requestKey);
        if (detail == null) {
            logger.error("[processComplete] detail not found: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_DETAIL_NOT_FOUND);
        }

        String ncn = detail.getContractNum();
        String ctn = detail.getCancelMobileNo();
        String cntplcNo = detail.getReceiveMobileNo();
        String custId = "";
        String smsRcvYn = StringUtils.defaultIfBlank(req.getSmsRcvYn(), "Y");

        logger.info("[processComplete] EP0 call: requestKey={}, ncn={}, ctn={}", requestKey, ncn, ctn);

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
            logger.error("[processComplete] EP0 exception: requestKey={}", requestKey, e);
            return FormResponse.of(
                ResTermMessage.ADMIN_EP0_ERROR,
                ResTermMessage.ADMIN_EP0_ERROR.getMessage() + ": " + e.getMessage(),
                null
            );
        }

        if (ep0Vo == null) {
            logger.error("[processComplete] EP0 null response: requestKey={}", requestKey);
            return FormResponse.of(ResTermMessage.ADMIN_EP0_EMPTY);
        }

        logger.info(
            "[processComplete] EP0 response: requestKey={}, rslt={}, osstOrdNo={}",
            requestKey,
            ep0Vo.getRslt(),
            ep0Vo.getOsstOrdNo()
        );

        if (!"S".equals(ep0Vo.getRslt())) {
            logger.warn(
                "[processComplete] EP0 failed: requestKey={}, rslt={}, rsltMsg={}",
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

        req.setProcCd("CP");
        req.setResCd(ep0Vo.getRslt());
        req.setResMsg(ep0Vo.getRsltMsg());
        req.setResNo(ep0Vo.getOsstOrdNo());

        int updated = canCustMgmtRepository.updateCanCustProcCd(req);
        if (updated <= 0) {
            logger.error("[processComplete] DB update failed: requestKey={}, updated={}", requestKey, updated);
            return FormResponse.of(ResTermMessage.ADMIN_COMPLETE_SAVE_FAILED);
        }

        logger.info("[processComplete] success: requestKey={}, osstOrdNo={}", requestKey, ep0Vo.getOsstOrdNo());
        return FormResponse.ok(ProcessResVO.complete(ep0Vo.getOsstOrdNo()));
    }

    @Override
    @Transactional
    public FormResponse<ProcessResVO> processRevert(Long requestKey) {
        logger.info("[processRevert] start: requestKey={}", requestKey);

        String currentProcCd = canCustMgmtRepository.selectProcCd(requestKey);
        if (currentProcCd == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_NOT_FOUND);
        }
        if (!"CP".equals(currentProcCd)) {
            return FormResponse.of(ResTermMessage.ADMIN_COMPLETE_ONLY_REVERT);
        }

        ProcessReqDto revertReq = new ProcessReqDto();
        revertReq.setRequestKey(requestKey);
        revertReq.setProcCd("RC");

        int updated = canCustMgmtRepository.updateCanCustProcCd(revertReq);
        if (updated <= 0) {
            return FormResponse.of(ResTermMessage.ADMIN_REVERT_SAVE_FAILED);
        }

        logger.info("[processRevert] success: requestKey={}", requestKey);
        return FormResponse.ok(ProcessResVO.revert());
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
