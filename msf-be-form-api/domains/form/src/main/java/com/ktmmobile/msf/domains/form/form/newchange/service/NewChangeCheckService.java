package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;

@Service
@RequiredArgsConstructor
public class NewChangeCheckService {

    private final FormCommReadMapper formCommReadMapper;

    //내국인, 내국인 미성년자, 외국인, 외국인 미성년자는 사람으로 구분
    private static final Set<String> PERSONAL_CUSTOMER_TYPES = Set.of("NA", "NM", "FN", "FM");


    //신청서 입력값 자릿수 체크
    //public FormResponse<NewChangeResponse> checkInputLength(NewChangeInfoRequest request) {
    public boolean checkInputLength(NewChangeInfoRequest request) {
        boolean isValid = true;
        if (PERSONAL_CUSTOMER_TYPES.contains(request.getCstmrTypeCd())) {
            if (hasTextAndExceededByteLength(request.getCstmrNm(), 60)
                || hasTextAndExceededByteLength(request.getMinorAgentNm(), 60)) {
                isValid = false;
                //return FormResponse.of(ResponseMessage.VALID_CSTMR_NAME_LENGTH_NOT_CORRECT);
            }
        } else {
            if (hasTextAndExceededByteLength(request.getCstmrJuridicalCname(), 60)
                || hasTextAndExceededByteLength(request.getCstmrJuridicalRepNm(), 60)
                || hasTextAndExceededByteLength(request.getCstmrJuridicalUserNm(), 60)) {
                isValid = false;
                //return FormResponse.of(ResponseMessage.VALID_CSTMR_NAME_LENGTH_NOT_CORRECT);
            }
        }

        // 이메일
        if (hasTextAndExceededByteLength(request.getCstmrEmailAdr(), 100)) {
            isValid = false;
            //return FormResponse.of(ResponseMessage.VALID_EMAIL_LENGTH_NOT_CORRECT);
        }

        // 번호이동 인증번호
        if ("MNP3".equals(request.getOperTypeCd())
            && hasTextAndNotMatchedByteLength(request.getMoveAuthNo(), 4)) {
            isValid = false;
            //return FormResponse.of(ResponseMessage.VALID_MNP3_MOVE_AUTH_NO_NOT_CORRECT);
        }

        //신분증 스캔 유형 값 자릿수 체크 (MSF_REQUEST.IDENTITY_TYPE_CD : 신분증유형코드)
        if (hasTextAndExceededByteLength(request.getIdentityTypeCd(), 2)) {
            isValid = false;
        }

        //return FormResponse.of(ResponseMessage.SUCCESS);
        return isValid;
    }

    //length 가 크면 안되는 경우
    private boolean hasTextAndExceededByteLength(String value, int maxLength) {
        return StringUtils.hasText(value)
            && value.getBytes(StandardCharsets.UTF_8).length > maxLength;
    }

    //length 가 동일해야 하는 경우
    private boolean hasTextAndNotMatchedByteLength(String value, int exactLength) {
        return StringUtils.hasText(value)
            && value.getBytes(StandardCharsets.UTF_8).length != exactLength;
    }


    //가입조건조회
    public SubscriptionResponse getEligibilityCheck(SubscriptionRequest request) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();

        //1년이내 이내 사용회선 조회
        int actYearCnt = this.getActYearCnt(request);
        //subscriptionResponse.setYearActCnt(actYearCnt);
        //1년이내 해지
        int cancelYearCnt = this.getCancelYearCnt(request);
        //subscriptionResponse.setYearCanCnt(cancelYearCnt);
        //당월개통회선
        int actThisMonthCnt = this.getActThisMonthCnt(request);
        //subscriptionResponse.setThisMonthActCnt(actThisMonthCnt);
        //미납조회
        int unpaidCnt = this.getUnpaidCnt(request);
        //subscriptionResponse.setDelinqStatusCnt(unpaidCnt);
        //전체 개통 회선
        int actTotalCnt = this.getActTotalCnt(request);
        //subscriptionResponse.setTotActCnt(actTotalCnt);

        //가입제한
        String subscriptionRestrictionsYn = "Y";
        //가입한도
        String subscriptionLimitYn = "Y";
        //미납
        String unPaidYn = "Y";
        //상습해지이력
        String historyOfCancellationYn = "Y";
        //할부할인
        String installmentDiscountYn = "Y";

        if ("9901013456789".equals(request.getCustomerSsn())) {
            subscriptionRestrictionsYn = "N";
            subscriptionLimitYn = "N";
            unPaidYn = "N";
            historyOfCancellationYn = "N";
            installmentDiscountYn = "N";
        }

        //고객유형별 처리 : NA , NM , FN , FM , JP , GO
        CstmrType cstmrTypeCd = request.getCstmrTypeCd();
        switch (cstmrTypeCd) {
            case CstmrType.NATIVE_ADULT:
                break;
            case CstmrType.NATIVE_MINOR:
                break;
            case CstmrType.FOREIGN_ADULT:
                break;
            case CstmrType.FOREIGN_MINOR:
                break;
            case CstmrType.JURIDICAL_PERSON:
                break;
            case CstmrType.GOVERNMENT_ORGANIZATION:
                break;
            default:
                break;
        }

        subscriptionResponse.setSubscriptionRestrictionsYn(subscriptionRestrictionsYn);
        subscriptionResponse.setSubscriptionLimitYn(subscriptionLimitYn);
        subscriptionResponse.setUnPaidYn(unPaidYn);
        subscriptionResponse.setHistoryOfCancellationYn(historyOfCancellationYn);
        subscriptionResponse.setInstallmentDiscountYn(installmentDiscountYn);

        return subscriptionResponse;
    }

    //1년이내 이내 사용회선 조회
    public int getActYearCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActYearCnt(request);
    }

    //1년이내 해지
    public int getCancelYearCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectCancelYearCnt(request);
    }

    //당월개통회선
    public int getActThisMonthCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActThisMonthCnt(request);
    }

    //미납조회
    public int getUnpaidCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectUnpaidCnt(request);
    }

    //전체 개통 회선
    public int getActTotalCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActTotalCnt(request);
    }


}
