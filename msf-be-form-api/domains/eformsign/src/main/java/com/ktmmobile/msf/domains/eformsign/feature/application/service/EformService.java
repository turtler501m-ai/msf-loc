package com.ktmmobile.msf.domains.eformsign.feature.application.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileResponse;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformValidateHttpResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformFileDownloadResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformParameter;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformsignFileDownloadRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformsignMultipartFile;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.in.EformReader;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.in.EformWriter;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.out.EformClient;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.out.EformRepository;
import com.ktmmobile.msf.domains.eformsign.feature.domain.code.ProcType;
import com.ktmmobile.msf.domains.eformsign.support.util.EformUtils;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeEformInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeEformService;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.in.CommonSmsWriter;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.CommonSmsType;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormCondition;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormResponse;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.port.in.FormCommonCompleteReader;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormCstmrType;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormType;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestForm;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestJoinForm;

@Slf4j
@Service
@RequiredArgsConstructor
public class EformService implements EformReader, EformWriter {

    private static final String LMS_RECV_TYPE_CD = "LMS";
    private static final String TALK_RECV_TYPE_CD = "TALK";

    private final EformClient eformClient;
    private final EformRepository eformRepository;
    private final NewChangeEformService newChangeEformService;
    private final CommonFileService commonFileService;
    private final ObjectMapper objectMapper;
    private final CommonSmsWriter commonSmsWriter;
    private final ObjectProvider<EformService> self;
    private final FormCommonCompleteReader formCommonCompleteReader;

    @Override
    public EformValidateResponse validateEformSignature(EformValidateRequest request) {
        EformValidateHttpResponse response = eformClient.validateEformSignature(request);
        return EformUtils.createValidateResponse(request, response);
    }

    @Override
    public EformFileDownloadResponse eformsignFileDownload(EformsignFileDownloadRequest request) {
        EformFileDownloadResponse response = eformClient.eformsignFileDownload(request);

        MultipartFile file = new EformsignMultipartFile(
            "file",
            response.fileName(),
            response.contentType(),
            response.srcFile()
        );

        FileRequest fileRequest = FileRequest.of(file, response.fileCategory() + "/" + response.documentId());

        CommonFile commonFile = commonFileService.writeFile(fileRequest);

        // 파일 저장까지 완료된 최종 성공 건의 재시도 현황을 추적하기 위한 로그
        log.info(
            "[전자서식 파일 생성 성공] documentId={}, retryCount={}",
            request.documentId(),
            request.retryCount()
        );

        return new EformFileDownloadResponse(
            response.documentId(),
            response.fileName(),
            response.fileCategory(),
            response.contentType(),
            null,
            FileResponse.of(commonFile)
        );
    }

    @Override
    public VerifyFormPwResponse verifyFormPw(VerifyFormPwRequest request) {

        RequestFormType formType = RequestFormType.valueOfCode(String.valueOf(request.formType()));

        boolean success = switch (formType) {
            case NEWCHANGE -> Boolean.TRUE.equals(eformRepository.verifyNewChangeFormPw(request));
            case SERVICECHANGE -> Boolean.TRUE.equals(eformRepository.verifyServiceChangeFormPw(request));
            case OWNERCHANGE -> Boolean.TRUE.equals(eformRepository.verifyOwnerChangeFormPw(request));
            case TERMINATION -> Boolean.TRUE.equals(eformRepository.verifyTerminationFormPw(request));
            case UNDEFINED -> false;
        };

        return new VerifyFormPwResponse(success);
    }

    @Override
    public EformResponse getFormInfo(NewChangeRequest request) {

        NewChangeEformInfoResponse response = newChangeEformService.getNewChangeRequestEformInfo(request);

        String jsonData = objectMapper.writeValueAsString(response);

        EformParameter parameter = EformParameter.builder()
            .name("jsondata")
            .value(jsonData)
            .build();

        EformResponse result = new EformResponse();
        result.setFormParameters(List.of(parameter));

        return result;
    }

    @Override
    public EformSendLinkResponse eformsignSendLink(EformSendLinkRequest request) {
        if (!StringUtils.hasText(request.requestKey())) {
            throw new SimpleDomainException("잘못된 접근입니다.");
        }

        CompletedFormCondition condition = new CompletedFormCondition(request.requestKey());
        CompletedRequestForm info = formCommonCompleteReader.getCompletedForm(condition);
        if (info == null) { // 신청서 정보 존재하지 않을 경우
            throw new SimpleDomainException("존재하지 않는 신청서입니다.");
        }
        CommonSmsType smsType = switch (info.getFormType()) {
            case RequestFormType.NEWCHANGE -> CommonSmsType.F_1_CMP;
            case RequestFormType.SERVICECHANGE -> CommonSmsType.F_2_CMP;
            case RequestFormType.OWNERCHANGE -> CommonSmsType.F_3_CMP;
            case RequestFormType.TERMINATION -> CommonSmsType.F_4_CMP;
            default -> null;
        };
        if (smsType == null) { // 신청서 유형이 유효하지 않을 경우
            throw new SimpleDomainException("유효한 유형이 아닌 신청서입니다.");
        }

        List<EformSendLinkRequest.SendInfo> nonNullMobiles = new ArrayList<>();
        CompletedFormResponse.CompletedFormMobileInfo first = formCommonCompleteReader.generateFirstMobileInfo(info);
        if (first != null) {
            nonNullMobiles.add(new EformSendLinkRequest.SendInfo(first.name(), first.mobile()));
        }
        CompletedFormResponse.CompletedFormMobileInfo second = formCommonCompleteReader.generateSecondMobileInfo(info);
        if (second != null) {
            nonNullMobiles.add(new EformSendLinkRequest.SendInfo(second.name(), second.mobile()));
        }
        EformSendLinkRequest finalRequest = new EformSendLinkRequest(
            request.requestKey(),
            info.getBaseScanId(),
            nonNullMobiles,
            getSendDocumentPwd(info),
            getSendDocumentHint(info)
        );

        Map<String, Long> lmsJoinFormMap = new HashMap<>();
        Map<String, Long> talkJoinFormMap = new HashMap<>();
        for (EformSendLinkRequest.SendInfo sendInfo: finalRequest.mobiles()) {
            String mobile = sendInfo.mobile();
            if (!StringUtils.hasText(mobile)) {
                continue;
            }

            CompletedRequestJoinForm lmsJoinForm = registryCompletedRequestJoinForm(info, LMS_RECV_TYPE_CD, mobile);
            if (lmsJoinForm != null) {
                lmsJoinFormMap.put(mobile, lmsJoinForm.getRequestRecvSeq());
            }
            CompletedRequestJoinForm talkJoinForm = registryCompletedRequestJoinForm(info, TALK_RECV_TYPE_CD, mobile);
            if (talkJoinForm != null) {
                talkJoinFormMap.put(mobile, talkJoinForm.getRequestRecvSeq());
            }
        }
        if (lmsJoinFormMap.isEmpty() && talkJoinFormMap.isEmpty()) {
            return new EformSendLinkResponse("0000", "이미 신청서를 발송하였습니다.", null);
        }

        if (!StringUtils.hasText(info.getBaseScanId())) { // 신청서 문서ID가 존재하지 않을 경우
            for (EformSendLinkRequest.SendInfo sendInfo: finalRequest.mobiles()) {
                String mobile = sendInfo.mobile();
                if (!StringUtils.hasText(mobile)) {
                    continue;
                }

                Long lmsRequestRecvSeq = lmsJoinFormMap.get(mobile);
                if (lmsRequestRecvSeq != null && lmsRequestRecvSeq > 0) {
                    CompletedRequestJoinForm lmsUpdateJoinForm = CompletedRequestJoinForm.builder()
                        .requestRecvSeq(lmsRequestRecvSeq)
                        .procCd(ProcType.ROLLBACK.getCode())
                        .etcMemo("{\"code\": \"9999\", message: \"scan_id가 존재하지 않음\"}")
                        .build();
                    self.getObject().modifyRequestJoinForm(lmsUpdateJoinForm);
                }
                Long talkRequestRecvSeq = talkJoinFormMap.get(mobile);
                if (talkRequestRecvSeq != null && talkRequestRecvSeq > 0) {
                    CompletedRequestJoinForm talkUpdateJoinForm = CompletedRequestJoinForm.builder()
                        .requestRecvSeq(talkRequestRecvSeq)
                        .procCd(ProcType.ROLLBACK.getCode())
                        .etcMemo("{\"code\": \"9999\", message: \"scan_id가 존재하지 않음\"}")
                        .build();
                    self.getObject().modifyRequestJoinForm(talkUpdateJoinForm);
                }
            }
            return new EformSendLinkResponse("9999", "신청서 발송이 실패하였습니다.\n다시 시도해 주세요.", null);
        }

        EformSendLinkResponse response = eformClient.eformsignSendLink(finalRequest);
        if (!"0000".equals(response.code()) || !StringUtils.hasText(response.url())) { // 신청서 문서 URL 결과가 정상적이지 않을 경우
            for (EformSendLinkRequest.SendInfo sendInfo: finalRequest.mobiles()) {
                String mobile = sendInfo.mobile();
                if (!StringUtils.hasText(mobile)) {
                    continue;
                }

                Long lmsRequestRecvSeq = lmsJoinFormMap.get(mobile);
                if (lmsRequestRecvSeq != null && lmsRequestRecvSeq > 0) {
                    CompletedRequestJoinForm lmsUpdateJoinForm = CompletedRequestJoinForm.builder()
                        .requestRecvSeq(lmsRequestRecvSeq)
                        .procCd(ProcType.ROLLBACK.getCode())
                        .etcMemo(objectMapper.writeValueAsString(response))
                        .build();
                    self.getObject().modifyRequestJoinForm(lmsUpdateJoinForm);
                }
                Long talkRequestRecvSeq = talkJoinFormMap.get(mobile);
                if (talkRequestRecvSeq != null && talkRequestRecvSeq > 0) {
                    CompletedRequestJoinForm talkUpdateJoinForm = CompletedRequestJoinForm.builder()
                        .requestRecvSeq(talkRequestRecvSeq)
                        .procCd(ProcType.ROLLBACK.getCode())
                        .etcMemo(objectMapper.writeValueAsString(response))
                        .build();
                    self.getObject().modifyRequestJoinForm(talkUpdateJoinForm);
                }
            }
            return new EformSendLinkResponse("9999", "신청서 발송이 실패하였습니다.\n다시 시도해 주세요.", null);
        }

        String r14Url = null;
        if (RequestFormType.SERVICECHANGE.equals(info.getFormType())) {
            String r14DocumentId = eformRepository.getR14DocumentIdOfServiceChange(info.getRequestKey());
            if (StringUtils.hasText(r14DocumentId)) {
                EformSendLinkRequest r14Request = new EformSendLinkRequest(finalRequest.requestKey(),
                    r14DocumentId,
                    finalRequest.mobiles(),
                    finalRequest.pwd(),
                    finalRequest.hint());
                EformSendLinkResponse r14Response = eformClient.eformsignSendLink(r14Request);
                if (StringUtils.hasText(response.url())) {
                    r14Url = r14Response.url();
                }
            }
        }

        List<Boolean> result = new ArrayList<>();
        for (EformSendLinkRequest.SendInfo sendInfo: finalRequest.mobiles()) {
            String mobile = sendInfo.mobile();
            if (!StringUtils.hasText(mobile)) {
                continue;
            }

            CommonSmsRequest smsRequest = new CommonSmsRequest(
                smsType,
                info.getFormType().getPath(),
                null,
                sendInfo.name(),
                mobile,
                null,
                null,
                null,
                StringUtils.hasText(r14Url) ? response.url() + "\n\n단말보험신청서:\n" + r14Url : response.url()
            );

            CompletedRequestJoinForm lmsJoinForm = info.getJoinForms().stream()
                .filter(v -> LMS_RECV_TYPE_CD.equals(v.getRecvTypeCd()) && mobile.equals(v.getJoinCstmrMobileNo()) && ProcType.COMPLETE.getCode()
                    .equals(v.getJoinProcCd())).findFirst().orElse(null);
            if (lmsJoinForm == null) {
                Boolean smsResult = commonSmsWriter.sendSms(smsRequest);
                result.add(smsResult);
                Long lmsRequestRecvSeq = lmsJoinFormMap.get(mobile);
                if (lmsRequestRecvSeq != null && lmsRequestRecvSeq > 0) {
                    String lmsProcType = smsResult ? ProcType.COMPLETE.getCode() : ProcType.ROLLBACK.getCode();
                    CompletedRequestJoinForm lmsUpdateJoinForm = CompletedRequestJoinForm.builder()
                        .requestRecvSeq(lmsRequestRecvSeq)
                        .procCd(lmsProcType)
                        .etcMemo(smsResult ? null : "{\"code\": \"9999\", message: \"LMS 전송 실패\"}")
                        .build();
                    self.getObject().modifyRequestJoinForm(lmsUpdateJoinForm);
                }
            } else {
                result.add(true);
            }

            CompletedRequestJoinForm talkJoinForm = info.getJoinForms().stream()
                .filter(v -> TALK_RECV_TYPE_CD.equals(v.getRecvTypeCd()) && mobile.equals(v.getJoinCstmrMobileNo()) && ProcType.COMPLETE.getCode()
                    .equals(v.getJoinProcCd())).findFirst().orElse(null);
            if (talkJoinForm == null) {
                Boolean talkResult = commonSmsWriter.sendKakao(smsRequest);
                result.add(talkResult);
                Long talkRequestRecvSeq = talkJoinFormMap.get(mobile);
                if (talkRequestRecvSeq != null && talkRequestRecvSeq > 0) {
                    String talkProcType = talkResult ? ProcType.COMPLETE.getCode() : ProcType.ROLLBACK.getCode();
                    CompletedRequestJoinForm talkUpdateJoinForm = CompletedRequestJoinForm.builder()
                        .requestRecvSeq(talkRequestRecvSeq)
                        .procCd(talkProcType)
                        .etcMemo(talkResult ? null : "{\"code\": \"9999\", message: \"TALK 전송 실패\"}")
                        .build();
                    self.getObject().modifyRequestJoinForm(talkUpdateJoinForm);
                }
            } else {
                result.add(true);
            }
        }

        if (result.stream().filter(v -> v).toList().isEmpty()) {
            return new EformSendLinkResponse("9999", "신청서 발송이 실패하였습니다.\n다시 시도해 주세요.", null);
        }

        return response;
    }

    @Transactional(noRollbackFor = Exception.class)
    protected void registryRequestJoinForm(CompletedRequestJoinForm request) {
        eformRepository.registryRequestJoinForm(request);
    }

    @Transactional(noRollbackFor = Exception.class)
    protected void modifyRequestJoinForm(CompletedRequestJoinForm request) {
        eformRepository.modifyRequestJoinForm(request);
    }

    private String getSendDocumentPwd(CompletedRequestForm info) {
        if (RequestFormCstmrType.JURIDICALPERSON.equals(info.getCstmrTypeCd()) || RequestFormCstmrType.GOVERNMENTORGANIZATION.equals(info.getCstmrTypeCd())) {
            return StringUtils.hasText(info.getCstmrJuridicalBizNo()) ? info.getCstmrJuridicalBizNo() : info.getCstmrJuridicalRrn();
        } else {
            return StringUtils.hasText(info.getCstmrForeignerBirth()) ? info.getCstmrForeignerBirth() : info.getCstmrNativeBirth();
        }
    }

    private String getSendDocumentHint(CompletedRequestForm info) {
        if (RequestFormCstmrType.JURIDICALPERSON.equals(info.getCstmrTypeCd()) || RequestFormCstmrType.GOVERNMENTORGANIZATION.equals(info.getCstmrTypeCd())) {
            return StringUtils.hasText(info.getCstmrJuridicalBizNo()) ? "사업자번호 10자리" : "법인번호 앞 6자리";
        } else {
            return "생년월일(YYYYMMDD) 8자리";
        }
    }

    private CompletedRequestJoinForm registryCompletedRequestJoinForm(CompletedRequestForm info, String recvTypeCd, String mobile) {
        CompletedRequestJoinForm joinForm = info.getJoinForms().stream()
            .filter(v -> recvTypeCd.equals(v.getRecvTypeCd()) && mobile.equals(v.getJoinCstmrMobileNo())).findFirst().orElse(null);

        if (joinForm != null && ProcType.COMPLETE.getCode().equals(joinForm.getJoinProcCd())) {
            return null;
        }

        if (joinForm == null || joinForm.getRequestRecvSeq() < 1) {
            joinForm = CompletedRequestJoinForm.builder()
                .requestKey(info.getRequestKey())
                .recvTypeCd(recvTypeCd)
                .cstmrMobileNo(mobile)
                .procCd(ProcType.REQUEST.getCode())
                .build();
            self.getObject().registryRequestJoinForm(joinForm);
        } else {
            joinForm = CompletedRequestJoinForm.builder()
                .requestRecvSeq(joinForm.getRequestRecvSeq())
                .procCd(ProcType.REQUEST.getCode())
                .build();
            self.getObject().modifyRequestJoinForm(joinForm);
        }
        return joinForm;
    }
}
