package com.ktmmobile.mcp.document.receive.service;

import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.document.receive.dao.DocumentReceiveDao;
import com.ktmmobile.mcp.document.receive.dto.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.document.receive.constants.DocumentReceiveConstants.DOC_RCV_ITEM_STATUS_PENDING;
import static com.ktmmobile.mcp.document.receive.constants.DocumentReceiveConstants.DOC_RCV_URL_OTP_STATUS_ACTIVE;

@Service
public class DocumentReceiveServiceImpl implements DocumentReceiveService {

    @Autowired
    private DocumentReceiveDao documentReceiveDao;

    @Autowired
    private SmsSvc smsSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Override
    public DocRcvDetailDto getDocRcvDetail(String docRcvId) {
        return documentReceiveDao.getDocRcvDetail(docRcvId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> resendNewOtp(DocRcvRequestDto docRcvRequest) {
        DocRcvDetailDto docRcvDetail = documentReceiveDao.getDocRcvDetail(docRcvRequest.getDocRcvId());
        if (docRcvDetail == null) {
            throw new McpCommonJsonException("90001", "비정상적인 접근입니다.");
        }

        if (!docRcvRequest.getRcvUrlId().equals(docRcvDetail.getDocRcvUrlDto().getRcvUrlId())) {
            throw new McpCommonJsonException("90002", "비정상적인 접근입니다.");
        }

        this.generateNewOtp(docRcvDetail.getDocRcvUrlOtpDto());
        this.sendOtp(docRcvDetail);
        SessionUtils.invalidateDocumentReceive(docRcvRequest.getDocRcvId());

        return Map.of("RESULT_CODE", AJAX_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> authenticateOtp(DocRcvRequestDto docRcvRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        DocRcvDetailDto docRcvDetail = documentReceiveDao.getDocRcvDetail(docRcvRequest.getDocRcvId());
        DocRcvUrlOtpDto docRcvUrlOtpDto;
        try {
            if (docRcvDetail == null) {
                throw new McpCommonJsonException("90001", "비정상적인 접근입니다.");
            }

            if (!docRcvRequest.getRcvUrlId().equals(docRcvDetail.getDocRcvUrlDto().getRcvUrlId())) {
                throw new McpCommonJsonException("90002", "비정상적인 접근입니다.");
            }

            docRcvUrlOtpDto = docRcvDetail.getDocRcvUrlOtpDto();
            if (docRcvUrlOtpDto == null) {
                throw new McpCommonJsonException("90003", "비정상적인 접근입니다.");
            }

            if (!DOC_RCV_URL_OTP_STATUS_ACTIVE.equals(docRcvUrlOtpDto.getStatus()) || docRcvUrlOtpDto.getFailCnt() >= 5) {
                if (docRcvUrlOtpDto.getSeq() > 2) {
                    throw new McpCommonJsonException("90004", "인증번호가 만료되었습니다.<br>발급 횟수가 초과되어 인증번호 재발급이 불가능합니다.<br>고객센터로 문의바랍니다.");
                } else {
                    throw new McpCommonJsonException("90005", "인증번호가 만료되었습니다.<br>인증번호를 다시 발급 받아주세요.");
                }
            }

            if (!docRcvRequest.getOtp().equals(docRcvUrlOtpDto.getOtp())) {
                documentReceiveDao.increaseUrlOtpFailCount(docRcvUrlOtpDto);
                SessionUtils.invalidateDocumentReceive(docRcvRequest.getDocRcvId());
                throw new McpCommonJsonException("90006", "인증번호가 일치하지 않습니다.");
            }

            resultMap.put("RESULT_CODE", AJAX_SUCCESS);
        } catch (McpCommonJsonException e) {
            resultMap.put("RESULT_CODE", e.getRtnCode());
            resultMap.put("RESULT_MSG", e.getErrorMsg());
            return resultMap;
        }

        documentReceiveDao.updateUrlOtpAuth(docRcvUrlOtpDto);
        DocRcvUrlOtpDto authDocRcvUrlOtpDto = documentReceiveDao.getDocRcvUrlOtp(docRcvUrlOtpDto);

        SessionUtils.setDocumentReceive(new DocRcvSessionDto(docRcvRequest.getDocRcvId(), authDocRcvUrlOtpDto.getLastAuthDt()));

        return resultMap;
    }

    @Override
    public List<DocRcvItemDto> getDocRcvItemPendingList(String docRcvId) {
        List<DocRcvItemDto> docRcvItemList = documentReceiveDao.getDocRcvItemList(docRcvId);

        return docRcvItemList.stream()
                .filter(item -> DOC_RCV_ITEM_STATUS_PENDING.equals(item.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void insertLogRequest(Map<String, Object> paramMap) {
        documentReceiveDao.insertLogRequest(paramMap);
    }

    private void generateNewOtp(DocRcvUrlOtpDto docRcvUrlOtpDto) {
        if (docRcvUrlOtpDto == null) {
            throw new McpCommonJsonException("91001", "비정상적인 접근입니다.");
        }

        if (docRcvUrlOtpDto.getSeq() > 2) {
            throw new McpCommonJsonException("91002", "발급 횟수가 초과되어 인증번호 재발급이 불가능합니다.<br>고객센터로 문의바랍니다.");
        }

        String otp;
        try {
            otp = StringUtil.generateNumbers(6);
        } catch (NoSuchAlgorithmException e) {
            throw new McpCommonJsonException("91003", "처리 중 오류가 발생했습니다.");
        }

        DocRcvUrlOtpDto newDocRcvUrlOtpDto = new DocRcvUrlOtpDto();
        newDocRcvUrlOtpDto.setRcvUrlId(docRcvUrlOtpDto.getRcvUrlId());
        newDocRcvUrlOtpDto.setOtp(otp);
        documentReceiveDao.insertDocRcvUrlOtp(newDocRcvUrlOtpDto);
    }

    private void sendOtp(DocRcvDetailDto docRcvDetail) {
        DocRcvUrlOtpDto docRcvUrlOtpDto = documentReceiveDao.getLastDocRcvUrlOtp(docRcvDetail.getDocRcvUrlDto().getRcvUrlId());
        if (docRcvUrlOtpDto == null || !DOC_RCV_URL_OTP_STATUS_ACTIVE.equals(docRcvUrlOtpDto.getStatus()) || StringUtils.isBlank(docRcvUrlOtpDto.getOtp())) {
            throw new McpCommonJsonException("92001", "인증번호가 유효하지 않습니다.");
        }

        MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(SMS_TEMPLATE_SEND_URL_OTP);
        if (mspSmsTemplateMstDto == null) {
            throw new McpCommonJsonException("92002", "처리 중 오류가 발생했습니다.");
        }

        smsSvc.sendMsgQueue(mspSmsTemplateMstDto.getSubject(), docRcvDetail.getMobileNo(), mspSmsTemplateMstDto.getText().replace("#{otp}", docRcvUrlOtpDto.getOtp()),
                mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY, String.valueOf(SMS_TEMPLATE_SEND_URL_OTP), "SYSTEM");
    }
}

