package com.ktmmobile.msf.commons.masking.domain.code;

public enum MaskingType {
    PASSWORD,                       // 비밀번호: password -> ********
    NAME,                           // 이름: 홍길동 -> 홍*동
    TELEPHONE,                      // 전화번호: 02-1234-5678 -> 02-****-5678
    MOBILE_PHONE,                   // 휴대폰번호: 010-1234-5678 -> 010-****-5678
    EMAIL,                          // 이메일: user@example.com -> u***@example.com
    RESIDENT_REGISTRATION_NUMBER,   // 주민등록번호: 900101-1234567 -> 900101-1******
    FOREIGN_REGISTRATION_NUMBER,    // 외국인등록번호: 900101-3234567 -> 900101-3******
    BUSINESS_REGISTRATION_NUMBER,   // 사업자번호: 123-45-67890 -> 123-**-*****
    CORPORATE_REGISTRATION_NUMBER,  // 법인번호: 110111-1234567 -> 110111-*******
    DRIVER_LICENSE_NUMBER,          // 운전면허번호: 12-34-567890-12 -> 12-**-******-12
    BANK_ACCOUNT_NUMBER,            // 은행계좌번호: 123-456789-01234 -> 123-******-**234
    CREDIT_CARD_NUMBER,             // 신용카드번호: 1234-5678-9012-5678 -> 1234-56**-****-5678
    IP_ADDRESS,                     // IP 주소: 192.168.0.1 -> 192.168.*.1, 2001:db8::1 -> 2001:db8:0:0:****:****:****:1
    ADDRESS,                        // 주소: 서울특별시 강남구 테헤란로 123 -> 서울특별시 강남구 테헤란로 ***
}
