package com.ktmmobile.msf.domains.form.form.newchange.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import lombok.NonNull;

public class NewChangeFormUtil {

    // 만나이 구하기
    public static int getAge(String jumin, String toDay) {
        if (jumin == null || jumin.length() < 7 || toDay == null || toDay.length() < 8) {
            return 0;
        }

        try {
            // 1. 기준일(오늘)을 LocalDate 객체로 변환
            LocalDate todayDate = LocalDate.parse(toDay, DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 2. 주민번호 기반 8자리 생년월일 문자열 추출 (ex: "19830810")
            String birthStr = getStringBirthStr(jumin);
            LocalDate birthDate = LocalDate.parse(birthStr, DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 3. 자바 내장 Period 기능을 이용해 만 나이 계산
            return Period.between(birthDate, todayDate).getYears();

        } catch (Exception e) {
            // 파싱 에러 발생 시 시스템이 멈추지 않도록 0원/0세 반환
            return 0;
        }
    }

    // 8자리 생년월일 반환
    private static @NonNull String getStringBirthStr(String jumin) {
        // getAge에서 이미 7자리 이상임을 검증했으므로 안전하게 추출
        String genderCode = jumin.substring(6, 7);
        String yearPrefix = "19"; // 기본값: 1, 2(내국인) 및 5, 6(외국인)은 1900년대생

        // 💡 2000년대생 조건 처리 (3, 4: 내국인 / 7, 8: 외국인)
        if ("3".equals(genderCode) || "4".equals(genderCode) || "7".equals(genderCode) || "8".equals(genderCode)) {
            yearPrefix = "20";
        }
        // 💡 1800년대생 조건 처리 (9: 남성 / 0: 여성)
        else if ("9".equals(genderCode) || "0".equals(genderCode)) {
            yearPrefix = "18";
        }

        // 8자리 생년월일 포맷 조립 후 반환
        return yearPrefix + jumin.substring(0, 6);
    }
}