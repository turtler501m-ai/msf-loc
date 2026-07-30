package com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;

@Getter
@RequiredArgsConstructor
public enum ImageDocTypeCd {

    SERVICE_JOIN("E0001", "서비스신규계약서"),
    CHANGE_REQUEST("E0002", "변경신청서"),
    OWNER_CHANGE("E0003", "명의변경계약서"),
    DEVICE_USIM_CHANGE("E0004", "단말기/USIM변경신청서"),
    TERMINATION("E0005", "해지신청서"),
    PREPAID_JOIN("E0006", "선불가입신청서"),
    PAYMENT("E0007", "요금수납신청서(일반요금 수납용)"),
    INSURANCE("E0008", "알뜰폰케어가입신청서"),
    POINT_CARD("E0009", "포인트카드가입신청서(14세미만)"),
    CALL_HISTORY("E0010", "통화내역 열람 신청서"),
    INSTALLMENT("E0011", "단말기할부매매계약서"),
    GROUP_INSURANCE("E0012", "고객사랑단체보험가입신청서"),
    PRODUCT_GUARANTEE("E0013", "생산물보증보상 책임보험 가입신청서"),
    PERSONAL_INFO("E0014", "개인(신용)정보 동의서"),
    LEGAL_REPRESENTATIVE("E0015", "법정대리인 동의서"),
    ETC("E0016", "기타 첨부서류"),
    PAYMENT_CHANGE("E0017", "요금수납신청서(해지, 명변, 기변용)"),
    FAX_ATTACHMENT("E0018", "FAX 첨부서류"),
    NUMBER_PORTING("E0023", "무선번호이동계약서"),
    PREMIUM_DEVICE_CHANGE("E0024", "우수기변신청서"),
    PHONE_DEVICE_CHANGE("E0025", "휴대폰기변신청서"),
    CREDIT_INQUIRY("E0026", "신용정보조회신청서"),
    CUSTOMER_INFO_CHANGE("E0027", "고객정보변경신청서"),
    DISCOUNT_SUPPORT("E0028", "요금할인지원금신청서"),
    BUNDLE_DISCOUNT_CHANGE("E0029", "결합할인변경신청서"),
    BUNDLE_LINE_ADD("E0030", "결합회선추가신청서"),
    BUNDLE_JOIN("E0031", "결합신규신청서"),
    BUNDLE_PRODUCT_CHANGE("E0032", "결합상품전환신청서"),
    INSTALLMENT_INFO_CHANGE("E0033", "할부정보변경신청서"),
    SUBSCRIPTION_GUIDE("E0034", "가입내역안내서신청서");

    private final String code;
    private final String title;

    public static ImageDocTypeCd from(String code) {
        return Arrays.stream(values())
            .filter(value -> value.code.equals(code))
            .findFirst()
            .orElseThrow(() ->
                new SimpleDomainException("지원하지 않는 docCd=" + code));
    }
}