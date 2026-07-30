package com.ktmmobile.msf.domains.cache.commoncode.domain.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UseYn;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * 외부 도메인에서 사용하는 공통코드 캐시 데이터
 */
public record CommonCodeData(
    String groupId,
    String code,
    String title,
    UseYn useYn,
    Detail detail
) {

    /** 공통코드 엔터티의 캐시 조회 DTO 변환 */
    public static CommonCodeData from(CommonCode commonCode) {
        return new CommonCodeData(
            commonCode.getGroupId(),
            commonCode.getCode(),
            commonCode.getTitle(),
            commonCode.getUseYn(),
            Detail.from(commonCode.getDetail())
        );
    }

    /** 코드 목록의 지정 코드 공통코드 조회 */
    public static Optional<CommonCodeData> get(List<CommonCodeData> commonCodes, String code) {
        return commonCodes.stream()
            .filter(commonCode -> commonCode.code().equals(code))
            .findFirst();
    }

    /** 사용 중인 코드 여부 반환 */
    public boolean isUsed() {
        return this.useYn.isUsed();
    }

    /** 미사용 코드 여부 반환 */
    public boolean isUnused() {
        return this.useYn.isUnused();
    }


    /**
     * 공통코드 상세 캐시 데이터
     */
    public record Detail(
        String abbrName,
        String description,
        String upperGroupCode,
        String filePathName,
        String imageName,
        int sortOrder,
        String etcValue1,
        String etcValue2,
        String etcValue3,
        String etcValue4,
        String etcValue5,
        String etcValue6,
        String startDate,
        String endDate
    ) {

        /** 약어명 또는 기본값 반환 */
        public String abbrName(String defaultValue) {
            return valueOrDefault(abbrName, defaultValue);
        }

        /** 설명 또는 기본값 반환 */
        public String description(String defaultValue) {
            return valueOrDefault(description, defaultValue);
        }

        /** 상위 그룹 코드 또는 기본값 반환 */
        public String upperGroupCode(String defaultValue) {
            return valueOrDefault(upperGroupCode, defaultValue);
        }

        /** 파일 경로명 또는 기본값 반환 */
        public String filePathName(String defaultValue) {
            return valueOrDefault(filePathName, defaultValue);
        }

        /** 이미지명 또는 기본값 반환 */
        public String imageName(String defaultValue) {
            return valueOrDefault(imageName, defaultValue);
        }

        /** 확장 문자열 값1 또는 기본값 반환 */
        public String etcValue1(String defaultValue) {
            return valueOrDefault(etcValue1, defaultValue);
        }

        /** 확장 문자열 값1 숫자 또는 기본값 반환 */
        public int etcValue1AsInt(int defaultValue) {
            return parseIntOrDefault(etcValue1, defaultValue);
        }

        /** 확장 문자열 값2 또는 기본값 반환 */
        public String etcValue2(String defaultValue) {
            return valueOrDefault(etcValue2, defaultValue);
        }

        /** 확장 문자열 값2 숫자 또는 기본값 반환 */
        public int etcValue2AsInt(int defaultValue) {
            return parseIntOrDefault(etcValue2, defaultValue);
        }

        /** 확장 문자열 값3 또는 기본값 반환 */
        public String etcValue3(String defaultValue) {
            return valueOrDefault(etcValue3, defaultValue);
        }

        /** 확장 문자열 값3 숫자 또는 기본값 반환 */
        public int etcValue3AsInt(int defaultValue) {
            return parseIntOrDefault(etcValue3, defaultValue);
        }

        /** 확장 문자열 값4 또는 기본값 반환 */
        public String etcValue4(String defaultValue) {
            return valueOrDefault(etcValue4, defaultValue);
        }

        /** 확장 문자열 값4 숫자 또는 기본값 반환 */
        public int etcValue4AsInt(int defaultValue) {
            return parseIntOrDefault(etcValue4, defaultValue);
        }

        /** 확장 문자열 값5 또는 기본값 반환 */
        public String etcValue5(String defaultValue) {
            return valueOrDefault(etcValue5, defaultValue);
        }

        /** 확장 문자열 값5 숫자 또는 기본값 반환 */
        public int etcValue5AsInt(int defaultValue) {
            return parseIntOrDefault(etcValue5, defaultValue);
        }

        /** 확장 문자열 값6 또는 기본값 반환 */
        public String etcValue6(String defaultValue) {
            return valueOrDefault(etcValue6, defaultValue);
        }

        /** 확장 문자열 값6 숫자 또는 기본값 반환 */
        public int etcValue6AsInt(int defaultValue) {
            return parseIntOrDefault(etcValue6, defaultValue);
        }

        /** 게시 시작일자 또는 기본값 반환 */
        public String startDate(String defaultValue) {
            return valueOrDefault(startDate, defaultValue);
        }

        /** 게시 종료일자 또는 기본값 반환 */
        public String endDate(String defaultValue) {
            return valueOrDefault(endDate, defaultValue);
        }

        /** 공통코드 상세 엔터티의 캐시 조회 DTO 변환 */
        static Detail from(CommonCode.Detail detail) {
            if (detail == null) {
                return null;
            }

            return new Detail(
                detail.getAbbrName(),
                detail.getDescription(),
                detail.getUpperGroupCode(),
                detail.getFilePathName(),
                detail.getImageName(),
                detail.getSortOrder(),
                detail.getEtcValue1(),
                detail.getEtcValue2(),
                detail.getEtcValue3(),
                detail.getEtcValue4(),
                detail.getEtcValue5(),
                detail.getEtcValue6(),
                detail.getStartDate(),
                detail.getEndDate()
            );
        }

        private static String valueOrDefault(String value, String defaultValue) {
            return StringUtils.hasText(value) ? value : defaultValue;
        }

        private static int parseIntOrDefault(String value, int defaultValue) {
            if (!StringUtils.hasText(value)) {
                return defaultValue;
            }
            return Integer.parseInt(value);
        }
    }
}
