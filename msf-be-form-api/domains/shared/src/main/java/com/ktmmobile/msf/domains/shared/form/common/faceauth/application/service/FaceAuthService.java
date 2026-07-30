package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;
import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.util.CryptoUtils;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.service.CommonSmsService;
import com.ktmmobile.msf.domains.shared.common.sms.domain.code.CommonSmsType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthConfirmRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthConfirmResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthFs8Request;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthFs9Request;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthFt0Request;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthFt1Request;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthIgnoreResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthQrResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthResultRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthResultResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthSendRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthSmsResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthUrlResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.in.FaceAuthWriter;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.out.FaceAuthRepository;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthCustomerType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthDecideCode;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityForm;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthJoinType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthOnlineOfflineDivision;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthPrxCommHeaderResponseKey;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthPrxRequestKey;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthPrxRequestType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthPrxResponseKey;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthVisitType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.FaceAuthCacheData;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpRequestOsst;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathSelfUrl;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class FaceAuthService implements FaceAuthWriter {

    private static final String TEMP_RES_NO = "0000000000000";

    private final MspPrxClient mspPrxClient;
    private final CacheService<String> ft0CacheService;
    private final CacheService<FaceAuthCacheData> cacheService;
    private final AgencyCacheReader agencyCacheReader;
    private final GenerateKeyRepository generateKeyRepository;
    private final CommonSmsService commonSmsService;
    private final FaceAuthRepository faceAuthRepository;
    private final ObjectProvider<FaceAuthService> self;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public FaceAuthConfirmResponse requestFaceAuthConfirm(FaceAuthConfirmRequest request) {
        // MsfUser user = AuthenticationUtils.getUser();

        // String tempResNo = generateKeyRepository.getGeneratedResNo();

        // FaceAuthConfirmRequest finalRequest = request;
        //
        // if (StringUtils.hasText(request.agentCode())) {
        //     String ktAgentCode = agencyCacheReader.getAgency(request.agentCode()).orElse(AgencyCache.from(new Agency())).ktOrganizationId();
        //     finalRequest = request.withKtAgentCode(ktAgentCode);
        // }

        // FaceAuthFt0Request ft0Request = new FaceAuthFt0Request();
        // ft0Request.setOnlineOfflnDivCd(FaceAuthOnlineOfflineDivision.OFFLINE.getCode());
        // ft0Request.setOrgId(finalRequest.ktAgentCode());
        // ft0Request.setCpntId(user.getOrganization().shopCode());
        // ft0Request.setRetvCdVal(finalRequest.identityType().getApiCode());
        // ft0Request.setFathSbscDivCd(finalRequest.joinType().getFathDivCode());
        // if (finalRequest.identityType().equals(FaceAuthIdentityType.DRIVE)) {
        //     if (finalRequest.customerType().equals(FaceAuthCustomerType.FOREIGNER)) {
        //         ft0Request.setCustIdfyNoTypeCd("05");
        //     }
        //     ft0Request.setCustIdfyNoTypeCd("01");
        // }
        // String xml = XmlConvertUtils.convertObjectToXml(ft0Request);
        //
        // Map<String, String> params = new HashMap<>();
        // params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FT0.getCode());
        // params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), TEMP_RES_NO);
        // params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), finalRequest.ktAgentCode());
        // params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        // params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathTgtYnRetv");
        // params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathTgtYnRetvInVO");
        // params.put(FaceAuthPrxRequestKey.XML.getCode(), xml);
        // MspPrxSoapResponse mspPrxSoapResponse = callXmlOsstServiceFt0(TEMP_RES_NO, params, ft0Request);
        //
        // String resultCode = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.TRT_RESULT_CODE);
        // String resultMessage = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.TRT_RESULT_MESSAGE);
        //
        // String cacheKey = generateFt0CacheKey(finalRequest.joinType(), finalRequest.customerType(), finalRequest.ktAgentCode(), user.getUserId());
        // ft0CacheService.setValue(cacheKey, payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.STABILIZATION_PERIOD_YN), Duration.ofMinutes(10));

        String resultCode = "0000";
        String resultMessage = "성공";
        return FaceAuthConfirmResponse.of(resultCode, resultMessage, TEMP_RES_NO);
    }

    @Override
    public FaceAuthUrlResponse requestFaceAuthUrl(FaceAuthSendRequest request) {
        FaceAuthUrlResponse response = self.getObject().callFaceAuthUrlService(request);

        return response;
    }

    @Override
    public FaceAuthSmsResponse requestFaceAuthSms(FaceAuthSendRequest request) {
        FaceAuthUrlResponse response = self.getObject().callFaceAuthUrlService(request);

        if (StringUtils.hasText(response.url())) {
            CommonSmsRequest commonSmsRequest = new CommonSmsRequest(
                CommonSmsType.from(request.formType().getSmsType()),
                request.path(),
                null,
                null,
                request.phone(),
                null,
                null,
                null,
                response.url()
            );
            Boolean result = commonSmsService.sendSms(commonSmsRequest);

            if (!result) {
                throw new SimpleDomainException("SMS 전송을 실패하였습니다.\n다시 시도해 주세요.");
            }
            return FaceAuthSmsResponse.of(response, true);
        }

        return FaceAuthSmsResponse.of(response, false);
    }

    @Override
    public FaceAuthQrResponse requestFaceAuthQr(FaceAuthSendRequest request) {
        FaceAuthUrlResponse response = self.getObject().callFaceAuthUrlService(request);

        String qr = "";
        if (StringUtils.hasText(response.url())) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (OutputStream b64os = Base64.getEncoder().wrap(out)) {
                BitMatrix matrix = new MultiFormatWriter().encode(
                    response.url(),
                    BarcodeFormat.QR_CODE,
                    300,
                    300,
                    Map.of(
                        EncodeHintType.CHARACTER_SET, "UTF-8",
                        EncodeHintType.MARGIN, 1
                    )
                );

                MatrixToImageWriter.writeToStream(matrix, "PNG", b64os);
                qr = out.toString();
            } catch (IOException | WriterException e) {
                throw new SimpleDomainException("QR 코드 생성을 실패했습니다. 다시 시도해 주세요.");
            }
        }

        return FaceAuthQrResponse.of(response, qr);
    }

    @Override public FaceAuthIgnoreResponse requestFaceAuthIgnore(FaceAuthSendRequest request) {
        // MsfUser user = AuthenticationUtils.getUser();

        String resNo = request.resNo();
        if (!StringUtils.hasText(resNo)) {
            resNo = generateKeyRepository.getGeneratedResNo();
        }

        FaceAuthSendRequest finalRequest = request;

        if (StringUtils.hasText(request.agentCode())) {
            String ktAgentCode = agencyCacheReader.getAgency(request.agentCode()).orElse(AgencyCache.from(new Agency())).ktOrganizationId();
            finalRequest = request.withKtAgentCode(ktAgentCode);
        }

        // String cacheKey = generateFt0CacheKey(finalRequest.joinType(), finalRequest.customerType(), finalRequest.ktAgentCode(), user.getUserId());
        // ft0CacheService.setValue(cacheKey, resNo, Duration.ofMinutes(3));

        FaceAuthSendRequest ignoreRequest = new FaceAuthSendRequest(
            finalRequest.formType(),
            finalRequest.joinType(),
            FaceAuthIdentityForm.MOBILE_IDCARD,
            finalRequest.identityType(),
            finalRequest.customerType(),
            finalRequest.visitType(),
            resNo,
            finalRequest.agentCode(),
            finalRequest.path(),
            finalRequest.formId(),
            finalRequest.phone(),
            finalRequest.bizNumber(),
            finalRequest.minorAgentName(),
            finalRequest.minorAgentBirthday(),
            finalRequest.ktAgentCode()
        );
        FaceAuthUrlResponse urlResponse = self.getObject().callFaceAuthUrlService(ignoreRequest);
        if (!StringUtils.hasText(urlResponse.url())) {
            return FaceAuthIgnoreResponse.of(resNo, urlResponse.resultCode(), urlResponse.resultMessage(), urlResponse.resNo());
        }

        delayTime();

        return self.getObject().callFaceAuthIgnoreService(resNo, urlResponse.transactionId(), finalRequest.ktAgentCode());
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public FaceAuthResultResponse requestFaceAuthResultPrev(FaceAuthResultRequest request) {
        MsfUser user = AuthenticationUtils.getUser();

        FaceAuthResultRequest finalRequest = request;

        if (StringUtils.hasText(request.agentCode())) {
            String ktAgentCode = agencyCacheReader.getAgency(request.agentCode()).orElse(AgencyCache.from(new Agency())).ktOrganizationId();
            finalRequest = request.withKtAgentCode(ktAgentCode);
        }

        String resNo = finalRequest.resNo();
        if (StringUtils.hasText(resNo)) {
            MsfFathResultPush msfPush = faceAuthRepository.getMsfFathResultPush(resNo);
            if (msfPush != null) {
                FaceAuthCacheData cacheData = FaceAuthCacheData.builder()
                    .resultCode(msfPush.getFathResltCd())
                    .resultMessage(msfPush.getFathResltSbst())
                    .transactionId(msfPush.getFathTransacId())
                    .url(null)
                    .resNo(resNo)
                    .formId(finalRequest.formId())
                    .requestDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                    .build();
                String cacheKey = generateFs8CacheKey(finalRequest.joinType(),
                    finalRequest.customerType(),
                    finalRequest.ktAgentCode(),
                    user.getUserId(),
                    resNo);
                cacheService.setValue(cacheKey, cacheData, Duration.ofMinutes(10));

                return FaceAuthResultResponse.of(
                    msfPush.getFathTransacId(),
                    finalRequest.formId(),
                    resNo,
                    finalRequest.joinType(),
                    finalRequest.customerType(),
                    msfPush,
                    null
                );
            }
        } else {
            resNo = generateKeyRepository.getGeneratedResNo();
        }

        FaceAuthFs9Request fs9Request = new FaceAuthFs9Request();
        fs9Request.setFrmpapId(finalRequest.formId());
        fs9Request.setRetvDivCd("2");

        String xml = XmlConvertUtils.convertObjectToXml(fs9Request);

        Map<String, String> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FS9.getCode());
        params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), resNo);
        params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), finalRequest.ktAgentCode());
        params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathTxnRetv");
        params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathTxnRetvInVO");
        params.put(FaceAuthPrxRequestKey.XML.getCode(), xml);

        MspPrxSoapResponse mspPrxSoapResponse = callXmlOsstServiceFs9(resNo, params, fs9Request);

        String decideCode = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_DECIDE_CODE);
        if (!"SUCC".equals(decideCode) && !"SKIP".equals(decideCode) && !"WAIT".equals(decideCode)) {
            return FaceAuthResultResponse.of(
                null,
                finalRequest.formId(),
                resNo,
                finalRequest.joinType(),
                finalRequest.customerType(),
                decideCode
            );
        }

        log.debug("===========> 1. prxResposne.custNm: {}", payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_NAME));

        McpFathResultPush push = McpFathResultPush.builder()
            .fathTransacId(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.TRANSACTION_ID))
            .slsCmpcCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.SALE_COMPANY_ID))
            .retvCdVal(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_IDCARD_TYPE_CODE))
            // .custNm(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_NAME))
            .custNm(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_NAME),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            // .custIdfyNo(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_IDENDIFY_NO))
            .custIdfyNo(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_IDENDIFY_NO),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .issDateVal(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_IDCARD_ISSUE_DATE))
            // .driveLicnsNo(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_DRIVE_LICENSE_NO))
            .driveLicnsNo(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_DRIVE_LICENSE_NO),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .idcardPhotoImg(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.IDCARD_PHOTO_IMAGE))
            .idcardCopiesImg(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.IDCARD_COPIES_IMAGE))
            .mblIdcardQrImg(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.MOBILE_IDCARD_QR_IMAGE))
            .idcardConfWay(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_IDENTITY_CONFIG_WAY_CODE))
            .distRsrtnYn(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.DISTANCE_RESTRICTION_YN))
            .fathProgrStepCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_PROGRESS_STEP_CODE))
            .fathCmpltNtfyDt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_COMPLETE_DATE))
            .fathUrlRqtDt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_URL_REQUEST_DATE))
            .fathResltCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_RESULT_CODE))
            .fathResltSbst(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_RESULT_MESSAGE))
            .skipPsblYn(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.SKIP_POSABLE_YN))
            // .smsRcvTelNo(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RECEIVED_SMS_TEL_NO))
            .smsRcvTelNo(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RECEIVED_SMS_TEL_NO),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .build();

        log.debug("===========> 1. MCP_PUSH.custNm: {}", push.getCustNm());

        faceAuthRepository.registMsfFathResultPushByMsp(push);

        String resultCode = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RESULT_CODE);
        String resultMessage = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RESULT_MESSAGE);
        String transactionId = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.TRANSACTION_ID);

        FaceAuthCacheData cacheData = FaceAuthCacheData.builder()
            .resultCode(resultCode)
            .resultMessage(resultMessage)
            .transactionId(transactionId)
            .url(null)
            .resNo(resNo)
            .formId(finalRequest.formId())
            .requestDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
            .build();
        String cacheKey = generateFs8CacheKey(finalRequest.joinType(),
            finalRequest.customerType(),
            finalRequest.ktAgentCode(),
            user.getUserId(),
            resNo);
        cacheService.setValue(cacheKey, cacheData, Duration.ofMinutes(10));

        return FaceAuthResultResponse.of(
            transactionId,
            finalRequest.formId(),
            resNo,
            finalRequest.joinType(),
            finalRequest.customerType(),
            push,
            decideCode
        );
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public FaceAuthResultResponse requestFaceAuthResult(FaceAuthResultRequest request) {
        MsfUser user = AuthenticationUtils.getUser();

        FaceAuthResultRequest finalRequest = request;

        if (StringUtils.hasText(request.agentCode())) {
            String ktAgentCode = agencyCacheReader.getAgency(request.agentCode()).orElse(AgencyCache.from(new Agency())).ktOrganizationId();
            finalRequest = request.withKtAgentCode(ktAgentCode);
        }

        String fs8CacheKey = generateFs8CacheKey(finalRequest.joinType(),
            finalRequest.customerType(),
            finalRequest.ktAgentCode(),
            user.getUserId(),
            finalRequest.resNo());
        FaceAuthCacheData data = cacheService.getValue(fs8CacheKey);
        if (data == null) {
            throw new SimpleDomainException("비정상적인 접근입니다." + (EnvironmentUtils.isProduction() ? "" : ": " + fs8CacheKey));
        }

        // McpFathResultPush push = null;
        // if (StringUtils.hasText(data.getTransactionId())) {
        //     push = faceAuthRepository.getMspFathResultPush(data.getTransactionId());
        // }
        // if (push != null) {
        // if (!"0000".equals(push.getFathResltCd())) {
        //     throw new SimpleDomainException("안면인증 실패 하였습니다.\n향후 추가 인증이 필요할 수 있습니다.\n(개통은 계속 진행 됩니다.)");
        // }
        // } else {
        FaceAuthFs9Request fs9Request = new FaceAuthFs9Request();
        fs9Request.setFathTransacId(data.getTransactionId());
        fs9Request.setRetvDivCd("1");

        String xml = XmlConvertUtils.convertObjectToXml(fs9Request);

        Map<String, String> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FS9.getCode());
        params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), data.getResNo());
        params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), finalRequest.ktAgentCode());
        params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathTxnRetv");
        params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathTxnRetvInVO");
        params.put(FaceAuthPrxRequestKey.XML.getCode(), xml);

        MspPrxSoapResponse mspPrxSoapResponse = callXmlOsstServiceFs9(data.getResNo(), params, fs9Request);

        // push = McpFathResultPush.builder()
        MsfFathResultPush push = MsfFathResultPush.builder()
            .fathTransacId(data.getTransactionId())
            .slsCmpcCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.SALE_COMPANY_ID))
            .identityCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_IDCARD_TYPE_CODE))
            .custNm(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_NAME),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .custIdfyNo(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_CUSTOMER_IDENDIFY_NO),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .issueDate(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_IDCARD_ISSUE_DATE))
            .driveLicnsNo(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_DRIVE_LICENSE_NO),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .idcardPhotoImgNm(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.IDCARD_PHOTO_IMAGE))
            .idcardCopiesImgNm(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.IDCARD_COPIES_IMAGE))
            .mblIdcardQrImgNm(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.MOBILE_IDCARD_QR_IMAGE))
            .idcardConfWayCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_IDENTITY_CONFIG_WAY_CODE))
            .distRsrtnYn(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.DISTANCE_RESTRICTION_YN))
            .fathProgrStepCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_PROGRESS_STEP_CODE))
            .fathCmpltNtfyDate(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_COMPLETE_DATE))
            .fathUrlRqtDate(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_URL_REQUEST_DATE))
            .fathResltCd(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_RESULT_CODE))
            .fathResltSbst(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_RESULT_MESSAGE))
            .birth(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_BIRTH_DATE),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .skipPsblYn(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.SKIP_POSABLE_YN))
            .photoAthnTxnSeq(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.PHOTO_ATHN_TXN_SEQ))
            .photoAthnDt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.PHOTO_ATHN_DT))
            .smsRcvTelNo(CryptoUtils.decrypt(payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RECEIVED_SMS_TEL_NO),
                FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .build();

        faceAuthRepository.registMsfFathResultPush(push);

        String decideCode = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.FATH_DECIDE_CODE);
        if (FaceAuthDecideCode.SUCCESS.getCode().equals(decideCode) || FaceAuthDecideCode.SKIP.getCode().equals(decideCode)) {
            return FaceAuthResultResponse.of(
                data.getTransactionId(),
                data.getFormId(),
                data.getResNo(),
                finalRequest.joinType(),
                finalRequest.customerType(),
                push,
                decideCode
            );
        }
        if (FaceAuthDecideCode.WAIT.getCode().equals(decideCode)) {
            throw new SimpleDomainException("안면인증 결과가 아직 확인되지 않습니다.\n잠시 후 결과 확인을 다시 시도해 주세요.");
        }
        // }

        if (FaceAuthDecideCode.FAIL.getCode().equals(decideCode)) {
            if (!"Y".equals(push.getSkipPsblYn())) {
                String resultCode = push.getFathResltCd();
                String resultMessage = push.getFathResltSbst();
                throw new SimpleDomainException("안면인증을 실패하였습니다. 안면인증을 다시 진행해 주세요." + (EnvironmentUtils.isProduction()
                    ? ""
                    : ": (" + resultCode + ") " + resultMessage));
            }

            FaceAuthIgnoreResponse ignoreResponse = self.getObject().callFaceAuthIgnoreService(finalRequest.resNo(),
                data.getTransactionId(),
                finalRequest.ktAgentCode());

            if (!"0000".equals(ignoreResponse.resultCode()) && !"0002".equals(ignoreResponse.resultCode())) {
                throw new SimpleDomainException("안면인증 SKIP 요청을 실패하였습니다." + (EnvironmentUtils.isProduction()
                    ? ""
                    : ": " + decideCode + "(skipPsblYn: " + push.getSkipPsblYn() + ") - (" + ignoreResponse.resultCode() + ") " + ignoreResponse.resultMessage()));
            }
        }

        if (StringUtils.hasText(finalRequest.customerName()) &&
            (
                StringUtils.hasText(finalRequest.customerNo()) ||
                    (FaceAuthIdentityType.DRIVE.equals(finalRequest.identityType()) && StringUtils.hasText(finalRequest.customerDriveNo()))
            ) &&
            StringUtils.hasText(finalRequest.customerIssueDate())
        ) {
            String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            MsfFathResultPush tempPush = MsfFathResultPush.builder()
                .fathTransacId(StringUtils.hasText(push.getFathTransacId()) ? push.getFathTransacId() : data.getTransactionId())
                .slsCmpcCd(StringUtils.hasText(push.getSlsCmpcCd()) ? push.getSlsCmpcCd() : "KIS")
                .identityCd(StringUtils.hasText(push.getIdentityCd()) ? push.getIdentityCd() : finalRequest.identityType().getApiResponseCode())
                .custNm(finalRequest.customerName())
                .custIdfyNo(finalRequest.customerNo())
                .issueDate(finalRequest.customerIssueDate())
                .driveLicnsNo(finalRequest.customerDriveNo())
                .idcardConfWayCd(StringUtils.hasText(push.getIdcardConfWayCd()) ? push.getIdcardConfWayCd() : "WEB")
                .fathProgrStepCd(StringUtils.hasText(push.getFathProgrStepCd()) ? push.getFathProgrStepCd() : "04")
                .fathCmpltNtfyDate(StringUtils.hasText(push.getFathCmpltNtfyDate()) ? push.getFathCmpltNtfyDate() : nowDate)
                .fathUrlRqtDate(StringUtils.hasText(push.getFathUrlRqtDate())
                    ? push.getFathUrlRqtDate()
                    : StringUtils.hasText(data.getRequestDate()) ? data.getRequestDate() : nowDate)
                .fathResltCd("0000")
                .fathResltSbst("성공")
                .skipPsblYn(push.getSkipPsblYn())
                .build();
            faceAuthRepository.registMsfFathResultPush(tempPush);

            push = tempPush;

            if (!StringUtils.hasText(push.getFathCmpltNtfyDate())) {
                throw new SimpleDomainException("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            }
            if (!StringUtils.hasText(push.getCustNm()) || !StringUtils.hasText(push.getCustIdfyNo())) {
                throw new SimpleDomainException("안면인증 정보가 누락되었습니다.");
            }
        }

        return FaceAuthResultResponse.of(
            data.getTransactionId(),
            data.getFormId(),
            data.getResNo(),
            finalRequest.joinType(),
            finalRequest.customerType(),
            push,
            decideCode
        );
    }

    @Transactional(noRollbackFor = Exception.class)
    protected FaceAuthUrlResponse callFaceAuthUrlService(FaceAuthSendRequest request) {
        MsfUser user = AuthenticationUtils.getUser();

        if (!FaceAuthIdentityForm.MOBILE_IDCARD.equals(request.identityForm()) && !StringUtils.hasText(request.formId())) {
            throw new SimpleDomainException("비정상적인 접근입니다." + (EnvironmentUtils.isProduction() ? "" : ": " + request));
        }

        FaceAuthSendRequest finalRequest = request;
        String resNo = finalRequest.resNo();
        if (!StringUtils.hasText(resNo)) {
            resNo = generateKeyRepository.getGeneratedResNo();
        }

        AgencyCache agency = null;
        if (StringUtils.hasText(request.agentCode())) {
            agency = agencyCacheReader.getAgency(request.agentCode()).orElse(AgencyCache.from(new Agency()));
            String ktAgentCode = agency.ktOrganizationId();
            finalRequest = request.withKtAgentCode(ktAgentCode);
        } else {
            agency = AgencyCache.from(new Agency());
        }

        // if (StringUtils.hasText(finalRequest.resNo())) {
        //     String cacheKey = generateFs8CacheKey(finalRequest.joinType(),
        //         finalRequest.customerType(),
        //         finalRequest.ktAgentCode(),
        //         user.getUserId(),
        //         resNo);
        //     FaceAuthCacheData cacheData = cacheService.getValue(cacheKey);
        //     if (cacheData != null) {
        //         return FaceAuthUrlResponse.of("0000",
        //             "성공",
        //             finalRequest.resNo(),
        //             cacheData.getTransactionId(),
        //             cacheData.getUrl(),
        //             cacheService.getTimeToLive(cacheKey).toSeconds());
        //     }
        // }

        // String ft0CacheKey = generateFt0CacheKey(finalRequest.joinType(), finalRequest.customerType(), finalRequest.ktAgentCode(), user.getUserId());
        // String resNo = ft0CacheService.getValue(ft0CacheKey);

        FaceAuthFs8Request fs8Request = new FaceAuthFs8Request();
        FaceAuthFs8Request.CustFathInfoDTO custFathInfoDTO = new FaceAuthFs8Request.CustFathInfoDTO();
        custFathInfoDTO.setOrgId(finalRequest.ktAgentCode());
        custFathInfoDTO.setCpntId(user.getOrganization().shopCode());
        custFathInfoDTO.setFathSbscDivCd(finalRequest.joinType().getFathDivCode());
        custFathInfoDTO.setScanTypeCd(request.identityForm().getCode());
        if (StringUtils.hasText(finalRequest.formId())) {
            custFathInfoDTO.setFrmpapId(finalRequest.formId());
        }
        custFathInfoDTO.setRetvCdVal(finalRequest.identityType().getApiCode());
        if (
            (finalRequest.customerType().equals(FaceAuthCustomerType.CORPORATION) || finalRequest.customerType()
                .equals(FaceAuthCustomerType.GOVERNMENT))
        ) {
            custFathInfoDTO.setOnlineOfflnDivCd(FaceAuthOnlineOfflineDivision.ONLINE.getCode());
            custFathInfoDTO.setCrprAgntYn("Y");
            custFathInfoDTO.setFathBizrNo(finalRequest.bizNumber());
            custFathInfoDTO.setFathAgntCustNm(finalRequest.minorAgentName());
            if (StringUtils.hasText(finalRequest.minorAgentBirthday())) {
                String minorAgentBirthday = LocalDate.parse(finalRequest.minorAgentBirthday(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                    .format(DateTimeFormatter.ofPattern("yyMMdd"));
                custFathInfoDTO.setFathAgntBthday(minorAgentBirthday);
            }
        } else {
            custFathInfoDTO.setOnlineOfflnDivCd(FaceAuthOnlineOfflineDivision.OFFLINE.getCode());
        }
        custFathInfoDTO.setPhotoAthnNcstYn("N");
        custFathInfoDTO.setFathRglsEnvTestYn("N");

        FaceAuthFs8Request.PhotoAthnRqtInDTO photoAthnRqtInDTO = null;
        if (FaceAuthIdentityForm.MOBILE_IDCARD.equals(request.identityForm())) {
            photoAthnRqtInDTO = new FaceAuthFs8Request.PhotoAthnRqtInDTO();
            photoAthnRqtInDTO.setPhotoAthnRqtDivCd("01");
            if (FaceAuthCustomerType.MINOR.equals(request.customerType()) || FaceAuthCustomerType.FOREIGN_MINOR.equals(request.customerType())) {
                photoAthnRqtInDTO.setPhotoAthnIndvDivCd("03");
            } else if ((FaceAuthCustomerType.CORPORATION.equals(request.customerType()) || FaceAuthCustomerType.GOVERNMENT.equals(request.customerType())) && FaceAuthVisitType.VDP.equals(
                request.visitType())) {
                photoAthnRqtInDTO.setPhotoAthnIndvDivCd("02");
            } else {
                photoAthnRqtInDTO.setPhotoAthnIndvDivCd("01");
            }
            photoAthnRqtInDTO.setPhotoAthnSvcDivCd("MOB");
            photoAthnRqtInDTO.setPhotoAthnSbscChCd("D");
            photoAthnRqtInDTO.setPhotoAthnSbscDivCd(finalRequest.joinType().getFathDivCode());
            photoAthnRqtInDTO.setPhotoAthnRetvPotimCd("BE");
            photoAthnRqtInDTO.setPhotoAthnAgreeDivYn("Y");
            photoAthnRqtInDTO.setPhotoAthnConnIpadr(RequestUtils.getClientIp());
            photoAthnRqtInDTO.setPhotoAthnAgncyId(request.agentCode());
            photoAthnRqtInDTO.setPhotoAthnRetvPrsnId(user.getUserId());
            photoAthnRqtInDTO.setPhotoAthnAgncyNm(agency.organizationName());
            photoAthnRqtInDTO.setPhotoAthnSalerCd(user.getOrganization().shopCode());
        }

        fs8Request.setCustFathInfoDTO(custFathInfoDTO);
        if (photoAthnRqtInDTO != null) {
            fs8Request.setPhotoAthnRqtInDTO(photoAthnRqtInDTO);
        }

        String xml = XmlConvertUtils.convertObjectToXml(fs8Request);

        Map<String, String> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FS8.getCode());
        params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), resNo);
        params.put(FaceAuthPrxRequestKey.SMS_RECV_TEL_NO.getCode(), finalRequest.phone());
        params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), finalRequest.ktAgentCode());
        params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathUrlRqt");
        params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathUrlRqtInVO");
        params.put(FaceAuthPrxRequestKey.XML.getCode(), xml);

        MspPrxSoapResponse mspPrxSoapResponse = callXmlOsstServiceFs8(resNo, finalRequest.phone(), params, fs8Request);

        String resultCode = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RESULT_CODE);
        String resultMessage = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.RESULT_MESSAGE);
        String transactionId = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.TRANSACTION_ID);
        String url = payloadText(mspPrxSoapResponse, FaceAuthPrxResponseKey.URL);

        registResponseFs8(finalRequest, transactionId, resNo, url, resultCode, resultMessage);

        return FaceAuthUrlResponse.of(resultCode,
            resultMessage,
            resNo,
            transactionId,
            finalRequest.formId(),
            url,
            Duration.ofMinutes(10).toSeconds());
    }

    @Transactional
    protected void registResponseFs8(
        FaceAuthSendRequest request,
        String transactionId,
        String resNo,
        String url,
        String resultCode,
        String resultMessage
    ) {
        MsfUser user = AuthenticationUtils.getUser();

        if (StringUtils.hasText(transactionId)) {
            MsfFathSelfUrl msfFathSelfUrl = MsfFathSelfUrl.builder()
                .fathKey(transactionId)
                .resNo(resNo)
                .urlAdr(url)
                .build();
            faceAuthRepository.registFathSelfUrl(msfFathSelfUrl);
        }

        FaceAuthCacheData cacheData = FaceAuthCacheData.builder()
            .resultCode(resultCode)
            .resultMessage(resultMessage)
            .transactionId(transactionId)
            .url(url)
            .resNo(resNo)
            .formId(request.formId())
            .requestDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
            .build();
        String cacheKey = generateFs8CacheKey(request.joinType(),
            request.customerType(),
            request.ktAgentCode(),
            user.getUserId(),
            resNo);
        cacheService.setValue(cacheKey, cacheData, Duration.ofMinutes(10));
    }

    @Transactional
    protected FaceAuthIgnoreResponse callFaceAuthIgnoreService(String resNo, String transactionId, String ktAgentCode) {
        // MsfUser user = AuthenticationUtils.getUser();

        FaceAuthFt1Request ft1Request = new FaceAuthFt1Request();
        ft1Request.setFathTransacId(transactionId);
        String xml = XmlConvertUtils.convertObjectToXml(ft1Request);

        Map<String, String> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.APP_EVENT_CODE.getCode(), FaceAuthPrxRequestType.FT1.getCode());
        params.put(FaceAuthPrxRequestKey.RES_NO.getCode(), resNo);
        params.put(FaceAuthPrxRequestKey.ASGN_AGNC_ID.getCode(), ktAgentCode);
        params.put(FaceAuthPrxRequestKey.SERVICE_NAME.getCode(), "OsstCustFathMgmtService");
        params.put(FaceAuthPrxRequestKey.SERVICE_INFO.getCode(), "osst:custFathTxnSkipReq");
        params.put(FaceAuthPrxRequestKey.SERVICE_VO.getCode(), "CustFathTxnSkipReqInVO");
        params.put(FaceAuthPrxRequestKey.XML.getCode(), xml);
        MspPrxSoapResponse ft1MspPrxSoapResponse = callXmlOsstServiceFt1(resNo, params, ft1Request);

        faceAuthRepository.copyMcpRequestOsstToMsf(FaceAuthPrxRequestType.FT1.getCode(), resNo);

        String resultCode = payloadText(ft1MspPrxSoapResponse, FaceAuthPrxResponseKey.S_RESULT_CODE);
        String resultMessage = payloadText(ft1MspPrxSoapResponse, FaceAuthPrxResponseKey.RESULT_MSG);
        return FaceAuthIgnoreResponse.of(resNo, resultCode, resultMessage, transactionId);
    }

    private String generatePrdcChkNotiMsgOfFt0(FaceAuthFt0Request request, MspPrxSoapResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.ORG_ID.getCode(), request.getOrgId());
        params.put(FaceAuthPrxRequestKey.ONLINE_OFFLINE_DIV_CD.getCode(), request.getOnlineOfflnDivCd());
        params.put(FaceAuthPrxRequestKey.RETV_CD_VAL.getCode(), request.getRetvCdVal());
        params.put(FaceAuthPrxRequestKey.CPNT_ID.getCode(), request.getCpntId());
        params.put(FaceAuthPrxRequestKey.FATH_SBSC_DIV_CD.getCode(), request.getFathSbscDivCd());
        params.put(FaceAuthPrxResponseKey.TRT_RESULT_CODE.getCode(), payloadText(response, FaceAuthPrxResponseKey.TRT_RESULT_CODE));
        params.put(FaceAuthPrxResponseKey.TRT_RESULT_MESSAGE.getCode(), payloadText(response, FaceAuthPrxResponseKey.TRT_RESULT_MESSAGE));
        params.put(FaceAuthPrxResponseKey.STABILIZATION_PERIOD_YN.getCode(), payloadText(response, FaceAuthPrxResponseKey.STABILIZATION_PERIOD_YN));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);
        return json.replaceAll("[\"]", "");
    }

    private String generatePrdcChkNotiMsgOfFs8(String phone, FaceAuthFs8Request request, MspPrxSoapResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.ORG_ID.getCode(), request.getCustFathInfoDTO().getOrgId());
        params.put(FaceAuthPrxRequestKey.CPNT_ID.getCode(), request.getCustFathInfoDTO().getCpntId());
        params.put(FaceAuthPrxRequestKey.ONLINE_OFFLINE_DIV_CD.getCode(), request.getCustFathInfoDTO().getOnlineOfflnDivCd());
        params.put(FaceAuthPrxRequestKey.SMS_RECV_TEL_NO.getCode(), phone);
        params.put(FaceAuthPrxRequestKey.FATH_SBSC_DIV_CD.getCode(), request.getCustFathInfoDTO().getFathSbscDivCd());
        params.put(FaceAuthPrxRequestKey.RETV_CD_VAL.getCode(), request.getCustFathInfoDTO().getRetvCdVal());
        params.put(FaceAuthPrxResponseKey.RESULT_CODE.getCode(), payloadText(response, FaceAuthPrxResponseKey.RESULT_CODE));
        params.put(FaceAuthPrxResponseKey.RESULT_MESSAGE.getCode(), payloadText(response, FaceAuthPrxResponseKey.RESULT_MESSAGE));
        params.put(FaceAuthPrxResponseKey.URL.getCode(), payloadText(response, FaceAuthPrxResponseKey.URL));
        params.put(FaceAuthPrxResponseKey.TRANSACTION_ID.getCode(), payloadText(response, FaceAuthPrxResponseKey.TRANSACTION_ID));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);
        return json.replaceAll("[\"]", "");
    }

    private String generatePrdcChkNotiMsgOfFs9(FaceAuthFs9Request request, MspPrxSoapResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.FATH_TRANSAC_ID.getCode(), request.getFathTransacId());
        params.put(FaceAuthPrxRequestKey.RETV_DIV_CD.getCode(), request.getRetvDivCd());
        params.put(FaceAuthPrxResponseKey.FATH_DECIDE_CODE.getCode(), payloadText(response, FaceAuthPrxResponseKey.FATH_DECIDE_CODE));
        params.put(FaceAuthPrxResponseKey.FATH_RESULT_MESSAGE.getCode(), payloadText(response, FaceAuthPrxResponseKey.FATH_RESULT_MESSAGE));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);
        return json.replaceAll("[\"]", "");
    }

    private String generatePrdcChkNotiMsgOfFt1(FaceAuthFt1Request request, MspPrxSoapResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put(FaceAuthPrxRequestKey.FATH_TRANSAC_ID.getCode(), request.getFathTransacId());
        params.put(FaceAuthPrxResponseKey.S_RESULT_CODE.getCode(), payloadText(response, FaceAuthPrxResponseKey.S_RESULT_CODE));
        params.put(FaceAuthPrxResponseKey.RESULT_MSG.getCode(), payloadText(response, FaceAuthPrxResponseKey.RESULT_MSG));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(params);
        return json.replaceAll("[\"]", "");
    }

    private void registRequestOsst(FaceAuthPrxRequestType type, String resNo, String prdcChkNotiMsg, MspPrxSoapResponse response) {
        McpRequestOsst.McpRequestOsstBuilder builder = McpRequestOsst.builder()
            .mvnoOrdNo(resNo)
            .prgrStatCd(type.getCode())
            .osstOrdNo(payloadText(response, FaceAuthPrxResponseKey.OSST_ORD_NO))
            .nstepGlobalId(response.globalNo())
            .prdcChkNotiMsg(prdcChkNotiMsg);
        String responseType = commHeaderText(response, FaceAuthPrxCommHeaderResponseKey.RESPONSE_TYPE);
        String responseCode = commHeaderText(response, FaceAuthPrxCommHeaderResponseKey.RESPONSE_CODE);
        if (!"N".equals(responseType)) {
            builder
                .rsltCd(responseCode)
                .rsltMsg(commHeaderText(response, FaceAuthPrxCommHeaderResponseKey.RESPONSE_MESSAGE));
        } else {
            builder.rsltCd(StringUtils.hasText(responseCode) ? responseCode : responseType);
        }

        McpRequestOsst mcpRequestOsst = builder.build();
        faceAuthRepository.registMcpRequestOsst(mcpRequestOsst);
        faceAuthRepository.copyMcpRequestOsstToMsf(type.getCode(), resNo);
    }

    // private MspPrxSoapResponse callXmlOsstServiceFt0(String resNo, Map<String, String> params, FaceAuthFt0Request request) {
    //     MspPrxSoapResponse response = callXmlOsstService(params);
    //     registRequestOsst(FaceAuthPrxRequestType.FT0, resNo, generatePrdcChkNotiMsgOfFt0(request, response), response);
    //     validateMspPrxResponse(response);
    //     validateFaceAuthResponse(
    //         payloadText(response, FaceAuthPrxResponseKey.TRT_RESULT_CODE),
    //         payloadText(response, FaceAuthPrxResponseKey.TRT_RESULT_MESSAGE)
    //     );
    //     return response;
    // }

    private MspPrxSoapResponse callXmlOsstServiceFs8(String resNo, String phone, Map<String, String> params, FaceAuthFs8Request request) {
        MspPrxSoapResponse response = callXmlOsstService(params);
        registRequestOsst(FaceAuthPrxRequestType.FS8, resNo, generatePrdcChkNotiMsgOfFs8(phone, request, response), response);
        // if (EnvironmentUtils.isProduction()) {
        validateMspPrxResponse(response);
        validateFaceAuthResponse(
            payloadText(response, FaceAuthPrxResponseKey.RESULT_CODE),
            payloadText(response, FaceAuthPrxResponseKey.RESULT_MESSAGE)
        );
        // }
        return response;
    }

    private MspPrxSoapResponse callXmlOsstServiceFs9(String resNo, Map<String, String> params, FaceAuthFs9Request request) {
        MspPrxSoapResponse response = callXmlOsstService(params);
        registRequestOsst(FaceAuthPrxRequestType.FS9, resNo, generatePrdcChkNotiMsgOfFs9(request, response), response);
        if (EnvironmentUtils.isProduction()) {
            validateMspPrxResponse(response);
        }
        return response;
    }

    private MspPrxSoapResponse callXmlOsstServiceFt1(String resNo, Map<String, String> params, FaceAuthFt1Request request) {
        MspPrxSoapResponse response = callXmlOsstService(params);
        registRequestOsst(FaceAuthPrxRequestType.FT1, resNo, generatePrdcChkNotiMsgOfFt1(request, response), response);
        if (EnvironmentUtils.isProduction()) {
            validateMspPrxResponse(response);
        }
        return response;
    }

    // private String generateFt0CacheKey(FaceAuthJoinType joinType, FaceAuthCustomerType customerType, String ktAgentCode, String userId) {
    //     return FaceAuthPrxRequestType.FT0.getCode() + ":" +
    //         joinType.getCode() + ":" +
    //         customerType.getCode() + ":" +
    //         ktAgentCode + ":" +
    //         userId;
    // }

    private String generateFs8CacheKey(
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType,
        String ktAgentCode,
        String userId,
        String resNo
    ) {
        return FaceAuthPrxRequestType.FS8.getCode() + ":" +
            joinType.getCode() + ":" +
            customerType.getCode() + ":" +
            ktAgentCode + ":" +
            userId + ":" +
            resNo;
    }

    private MspPrxSoapResponse callXmlOsstService(Map<String, String> params) {
        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        return mspPrxClient.callXmlOsstService(mspPrxFormRequest);
    }

    private void validateMspPrxResponse(MspPrxSoapResponse response) {
        String responseType = commHeaderText(response, FaceAuthPrxCommHeaderResponseKey.RESPONSE_TYPE);
        if (!"N".equals(responseType)) {
            throw new SimpleDomainException(commHeaderText(response, FaceAuthPrxCommHeaderResponseKey.RESPONSE_MESSAGE));
        }
    }

    private void validateFaceAuthResponse(String resultCode, String resultMessage) {
        if (List.of("0000", "CD02", "CD05").contains(resultCode)) {
            return;
        }
        if (List.of("CD01", "CD04", "CD06", "CD07", "CD08", "CD09", "CD11", "CD12", "CD13", "CD16").contains(resultCode)) {
            throw new SimpleDomainException(resultMessage);
        }
        throw new SimpleDomainException(
            StringUtils.hasText(resultMessage) ? resultMessage : "현재 안면인증 시스템 오류로 개통 신청 불가합니다."
        );
    }

    // private String bizHeaderText(MspPrxSoapResponse response, FaceAuthPrxBizHeaderResponseKey key) {
    //     return response.bizHeader().getOrDefault(key.getCode(), "");
    // }

    private String commHeaderText(MspPrxSoapResponse response, FaceAuthPrxCommHeaderResponseKey key) {
        return response.commHeader().getOrDefault(key.getCode(), "");
    }

    private String payloadText(MspPrxSoapResponse response, FaceAuthPrxResponseKey key) {
        return response.payloadText(FaceAuthPrxResponseKey.OUT_DTO.getCode(), key.getCode()).orElse("");
    }

    private void delayTime() {
        delayTime(0);
    }

    private void delayTime(long seconds) {
        delayTime(seconds, null);
    }

    private void delayTime(long seconds, String date) {
        long sec = seconds > 0 ? seconds : 70;
        if (StringUtils.hasText(date)) {
            LocalDateTime requestDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            sec = (sec - Duration.between(requestDate, LocalDateTime.now()).getSeconds());
        }
        if (sec > 0) {
            try {
                TimeUnit.SECONDS.sleep(sec);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(sec * 1000);
                } catch (InterruptedException ex) {
                    log.warn("=======> 최대 {}초 시간 지연 실패.", sec);
                }
            }
        }
    }
}
