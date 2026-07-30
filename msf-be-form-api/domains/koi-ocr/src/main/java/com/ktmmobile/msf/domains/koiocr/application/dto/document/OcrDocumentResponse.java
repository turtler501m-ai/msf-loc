package com.ktmmobile.msf.domains.koiocr.application.dto.document;

import java.util.List;

/**
 * OCR 신분증 응답 객체 정의
 */
public record OcrDocumentResponse(
    String type,                                // OCR 문서 타입
    List<FieldResult> fieldResults              // OCR 필드 결과 목록
) {

    /**
     * OCR 문서 타입
     */
    public static class Type {

        /**
         * 00100 주민등록증
         */
        public static final String RESIDENT_REGISTRATION_CARD = "00100";

        /**
         * 00110 운전면허증
         */
        public static final String DRIVER_LICENSE = "00110";

        /**
         * 00120 여권
         */
        public static final String PASSPORT = "00120";

        /**
         * 00140 외국인등록증
         */
        public static final String FOREIGNER_REGISTRATION_CARD = "00140";

        /**
         * 00141 영주증
         */
        public static final String PERMANENT_RESIDENCY_CARD = "00141";

        /**
         * 00142 국내거소신고증
         */
        public static final String DOMESTIC_RESIDENCE_REPORT = "00142";

        /**
         * 00150 외국인등록증/영주증/국내거소신고증 후면
         */
        public static final String FOREIGNER_BACK_SIDE = "00150";

        /**
         * 00160 국가보훈증
         */
        public static final String VETERANS_CARD = "00160";

        /**
         * 99999 장애인등록증(솔루션 미존재 항목)
         */
        public static final String DISABILITY_CARD = "99999";
    }

    /**
     * OCR 필드 ID
     */
    public static class FieldId {

        /**
         * 주민등록증
         */
        public static class ResidentRegistrationCard {

            public static final String RESIDENT_REGISTRATION_NUMBER = "101"; // 주민등록번호
            public static final String NAME = "102";                         // 이름
            public static final String ISSUED_DATE = "103";                  // 발급일
            public static final String ADDRESS = "109";                      // 주소
            public static final String COPY_DETECTION_RESULT = "191";        // 사본판별 결과
        }

        /**
         * 운전면허증
         */
        public static class DriverLicense {

            public static final String RESIDENT_REGISTRATION_NUMBER = "111"; // 주민등록번호
            public static final String NAME = "112";                         // 이름
            public static final String ISSUED_DATE = "113";                  // 발급일
            public static final String ISSUING_AUTHORITY = "114";            // 발급처
            public static final String DRIVER_LICENSE_NUMBER = "115";        // 면허번호
            public static final String SERIAL_NUMBER = "116";                // 시리얼번호
            public static final String APTITUDE_TEST_PERIOD = "117";         // 적성검사 기간
            public static final String ADDRESS = "119";                      // 주소
            public static final String COPY_DETECTION_RESULT = "191";        // 사본판별 결과
        }

        /**
         * 여권
         */
        public static class Passport {

            public static final String PASSPORT_NUMBER = "121";                  // 여권번호
            public static final String NAME = "122";                             // 이름(한글)
            public static final String ISSUED_DATE = "123";                      // 발급일
            public static final String BIRTH_DATE = "124";                       // 생년월일
            public static final String RESIDENT_REGISTRATION_NUMBER = "125";     // 주민등록번호(구형 여권만 존재)
            public static final String EXPIRATION_DATE = "126";                  // 만료일
            public static final String LAST_NAME_EN = "127";                     // 성(영문)
            public static final String FIRST_NAME_EN = "128";                    // 이름(영문)
            public static final String GENDER = "129";                           // 성별
            public static final String MRZ_LINE_1 = "130";                       // MRZ 1열
            public static final String MRZ_LINE_2 = "131";                       // MRZ 2열
            public static final String COPY_DETECTION_RESULT = "191";            // 사본판별 결과
        }

        /**
         * 외국인등록증 / 영주증 / 국내거소신고증
         */
        public static class ForeignerRegistrationCard {

            public static final String FOREIGNER_REGISTRATION_NUMBER = "141";    // 외국인 등록번호
            public static final String NAME = "142";                             // 이름
            public static final String ISSUED_DATE = "143";                      // 발급일
            public static final String NATIONALITY_OR_REGION = "144";            // 국가 / 지역
            public static final String VISA_STATUS = "145";                      // 체류자격
            public static final String COPY_DETECTION_RESULT = "191";            // 사본판별 결과
        }

        /**
         * 외국인등록증 / 영주증 / 국내거소신고증 후면
         */
        public static class ForeignerBackSide {

            public static final String SERIAL_NUMBER = "151";                    // 일련번호
        }

        /**
         * 국가보훈증
         */
        public static class VeteransCard {

            public static final String RESIDENT_REGISTRATION_NUMBER = "161";     // 주민등록번호
            public static final String NAME = "162";                             // 이름
            public static final String ISSUED_DATE = "163";                      // 발급일
            public static final String VETERANS_NUMBER = "164";                  // 보훈번호
            public static final String VETERANS_CARD_TYPE = "165";               // 국가보훈증 종류
            public static final String ADDRESS = "169";                          // 주소
            public static final String COPY_DETECTION_RESULT = "191";            // 사본판별 결과
        }
    }

    /**
     * OCR 필드 결과
     */
    public record FieldResult(
        String fieldId,                         // 필드 ID
        String displayName,                     // 필드명
        String value                            // OCR 추출 값
    ) {
    }
}