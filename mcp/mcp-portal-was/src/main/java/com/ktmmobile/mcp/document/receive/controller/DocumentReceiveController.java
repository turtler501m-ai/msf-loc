package com.ktmmobile.mcp.document.receive.controller;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.document.receive.dto.DocRcvDetailDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvItemDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvRequestDto;
import com.ktmmobile.mcp.document.receive.service.DocumentReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.document.receive.util.DocumentReceiveUtil.*;

@Controller
public class DocumentReceiveController {

    @Autowired
    private DocumentReceiveService documentReceiveService;

    @Value("${scan.V25.interface.url}")
    private String scanInterfaceUrl;

    @RequestMapping(value = {"/document/receive/auth.do", "/m/document/receive/auth.do"})
    public String documentReceiveAuth(@RequestParam(value = "i") String docRcvId,
                                      @RequestParam(value = "u") String rcvUrlId,
                                      ModelMap model) {
        String returnUrl = "/portal/document/receive/documentReceiveAuth";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/document/receive/documentReceiveAuth";
        }

        if (StringUtil.isEmpty(docRcvId) || StringUtil.isEmpty(rcvUrlId)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        DocRcvDetailDto docRcvDetail = documentReceiveService.getDocRcvDetail(docRcvId);
        if (!validateDetail(docRcvDetail)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        if (!rcvUrlId.equals(docRcvDetail.getDocRcvUrlDto().getRcvUrlId())) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        model.addAttribute("maskedMobileNo", MaskingUtil.getMaskedTelNo(docRcvDetail.getMobileNo()));
        model.addAttribute("docRcvId", docRcvDetail.getDocRcvId());
        model.addAttribute("rcvUrlId", docRcvDetail.getDocRcvUrlDto().getRcvUrlId());
        return returnUrl;
    }

    @RequestMapping(value = {"/document/receive/resendNewOtp.do"})
    @ResponseBody
    public Map<String, Object> resendNewOtp(DocRcvRequestDto docRcvRequest) {
        if (StringUtil.isEmpty(docRcvRequest.getDocRcvId()) || StringUtil.isEmpty(docRcvRequest.getRcvUrlId())) {
            throw new McpCommonJsonException("비정상적인 접근입니다.");
        }

        return documentReceiveService.resendNewOtp(docRcvRequest);
    }

    @RequestMapping(value = {"/document/receive/authenticateOtp.do"})
    @ResponseBody
    public Map<String, Object> authenticateOtp(DocRcvRequestDto docRcvRequest) {
        if (StringUtil.isEmpty(docRcvRequest.getOtp()) || StringUtil.isEmpty(docRcvRequest.getDocRcvId()) || StringUtil.isEmpty(docRcvRequest.getRcvUrlId())) {
            throw new McpCommonJsonException("비정상적인 접근입니다.");
        }

        return documentReceiveService.authenticateOtp(docRcvRequest);
    }

    @RequestMapping(value = {"/document/receive/uploadView.do", "/m/document/receive/uploadView.do"})
    public String uploadView(@RequestParam(value = "docRcvId") String docRcvId,
                                      ModelMap model) {
        String returnUrl = "/portal/document/receive/documentReceiveUploadView";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/document/receive/documentReceiveUploadView";
        }

        if (StringUtil.isEmpty(docRcvId)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        if (!validateSession(docRcvId)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        DocRcvDetailDto docRcvDetail = documentReceiveService.getDocRcvDetail(docRcvId);
        if (!validateDetail(docRcvDetail)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        model.addAttribute("docRcvId", docRcvDetail.getDocRcvId());
        model.addAttribute("scanInterfaceUrl", scanInterfaceUrl);
        return returnUrl;
    }

    @PostMapping(value = {"/document/receive/getDocRcvItemPendingList.do"})
    @ResponseBody
    public List<DocRcvItemDto> getDocRcvItemPendingList(@RequestParam(value = "docRcvId") String docRcvId) {
        if (StringUtil.isEmpty(docRcvId)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        if (!validateSession(docRcvId)) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        return documentReceiveService.getDocRcvItemPendingList(docRcvId);
    }

    @RequestMapping(value = {"/document/receive/completeView.do", "/m/document/receive/completeView.do"})
    public String completeView() {
        String returnUrl = "/portal/document/receive/documentReceiveCompleteView";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/document/receive/documentReceiveCompleteView";
        }
        return returnUrl;
    }
}
