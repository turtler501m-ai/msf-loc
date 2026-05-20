package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;

@Slf4j
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

    /**
     * 가입조건조회
     */
    public SubscriptionResponse getEligibilityCheck(SubscriptionRequest request) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        CstmrType cstmrTypeCd = request.getCstmrTypeCd();

        log.debug("-------------------------------------------------------------------");
        log.debug("CstmrTypeCd: {}", request.getCstmrTypeCd());
        log.debug("cstmr: {}", request.getCustomerSsn());
        log.debug("-------------------------------------------------------------------");

        //1년이내 사용회선 조회
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

        //가입제한 (5월 세째주 KTM모바일에서 조건 전달해주기로 함)
        String subscriptionRestrictionsYn = "Y";
        String subscriptionRestrictionsResultMessage = "가능";
        //가입한도
        String subscriptionLimitYn = "";
        String subscriptionLimitResultMessage = "";
        //미납
        String unPaidYn = "";
        String unPaidResultMessage = "";
        //상습해지이력
        String historyOfCancellationYn = "Y";
        String historyOfCancellationResultMessage = "가능";
        //할부할인 (5월 세째주 KTM모바일에서 조건 전달해주기로 함)
        String installmentDiscountYn = "Y";
        String installmentDiscountResultMessage = "가능";

        //테스트 데이터~~~ @삭제해야해요!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if ("9806145678901".equals(request.getCustomerSsn())) {
            cstmrTypeCd = CstmrType.FOREIGN_ADULT;
            actYearCnt = 2; //1년이내 사용회선 조회
            cancelYearCnt = 1; //1년이내 해지
            actThisMonthCnt = 1; //당월개통회선
            unpaidCnt = 1; //미납조회
            actTotalCnt = 2; //전체 개통 회선
        }


        //가입한도 >> 고객유형별 처리 : NA , NM , FN , FM , JP , GO
        switch (cstmrTypeCd) {
            case CstmrType.NATIVE_ADULT:
            case CstmrType.NATIVE_MINOR:
            case CstmrType.JURIDICAL_PERSON:
            case CstmrType.GOVERNMENT_ORGANIZATION:
                if (actThisMonthCnt >= 2 || actTotalCnt >= 3) {
                    subscriptionLimitYn = "N"; //가입한도
                    subscriptionLimitResultMessage = "불가능(2회선 사용중)"; //가입제한 메세지
                } else {
                    subscriptionLimitYn = "Y"; //가입한도
                    subscriptionLimitResultMessage = "가능(2회선 중 " + actTotalCnt + " 회선 사용중)"; //가입제한 메세지
                }
                break;
            case CstmrType.FOREIGN_ADULT:
            case CstmrType.FOREIGN_MINOR:
                if (actThisMonthCnt >= 1 || actTotalCnt >= 2) {
                    subscriptionLimitYn = "N"; //가입한도
                    subscriptionLimitResultMessage = "불가능(3회선 사용중)"; //가입제한 메세지
                } else {
                    subscriptionLimitYn = "Y"; //가입한도
                    subscriptionLimitResultMessage = "가능(3회선 중 " + actTotalCnt + " 회선 사용중)"; //가입제한 메세지
                }
                break;
            default:
                break;
        }

        //미납 >> 고객유형별 처리 : NA , NM , FN , FM , JP , GO
        if (unpaidCnt > 0) {
            unPaidYn = "N";
            unPaidResultMessage = "불가능(동일 고객 미납 보유)";
        } else {
            unPaidYn = "Y";
            unPaidResultMessage = "가능";
        }

        //상습해지이력 (상단에 기본값 설정함)
        if (actYearCnt == 3 && cancelYearCnt >= 1) {
            historyOfCancellationYn = "N";
            historyOfCancellationResultMessage = "3회선 사용 중 1회선 해지 후 1년 이내 가입 시 가입 불가능";
            //외국인은 2회선 한도라서 표시를 따로 해야하지 않을까? 또는 가입가능회선 중 1회선 해지 후 1년 이내 가입 시 가입 불가능
        } else if (actYearCnt == 2 && cancelYearCnt >= 1 && actTotalCnt == 1) {
            historyOfCancellationResultMessage = "가능";
        } else if (cancelYearCnt >= 1 && actTotalCnt >= 1) {
            historyOfCancellationYn = "N";
            historyOfCancellationResultMessage = "가입불가";
        }

        subscriptionResponse.setSubscriptionRestrictionsYn(subscriptionRestrictionsYn);
        subscriptionResponse.setSubscriptionRestrictionsResultMessage(subscriptionRestrictionsResultMessage);
        subscriptionResponse.setSubscriptionLimitYn(subscriptionLimitYn); //가입한도 조회 결과
        subscriptionResponse.setSubscriptionLimitResultMessage(subscriptionLimitResultMessage); //가입한도 조회 결과 메세지
        subscriptionResponse.setUnPaidYn(unPaidYn); //미납 조회 결과
        subscriptionResponse.setUnPaidResultMessage(unPaidResultMessage); //미납 조회 결과
        subscriptionResponse.setHistoryOfCancellationYn(historyOfCancellationYn); //상습해지이력 조회 결과
        subscriptionResponse.setHistoryOfCancellationResultMessage(historyOfCancellationResultMessage); //상습해지이력 조회 결과 메세지
        subscriptionResponse.setInstallmentDiscountYn(installmentDiscountYn);
        subscriptionResponse.setInstallmentDiscountResultMessage(installmentDiscountResultMessage);

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
