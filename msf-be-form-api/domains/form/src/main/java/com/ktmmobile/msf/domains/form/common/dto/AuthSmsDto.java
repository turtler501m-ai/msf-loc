package com.ktmmobile.msf.domains.form.common.dto;

import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthSmsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phoneNum;
    private String authNum;
    private String startDate;
    private String endDate;
    private String message;
    private String smsNo;
    private boolean result = false;
    private boolean check = false;
    private boolean delete = false;
    private String menu;
    private String sendTime;
    private int duration;
    private String memberName;
    private String rateCd;
    private String rateNm;
    private String isReal;       // local,dev,stg:N, prd:Y
    private String reserved02;   // 발송목적
    private String reserved03;   // 발송자
    private String ctn;
    private String custId;
    private String subLinkName;
    private String svcCntrNo;
    private String birthday;
    // 결합 관련
    private String svcNoTypeCd;  // 회선구분코드 인터넷:IT, 모바일:MB (MVNO회선일 경우 MB만 가능)
    private String sexCd;        // 성별코드 (KT회선일경우 필수) 1:남성, 2:여성
    private String homeCombTerm; // 홈결합(인터넷+MVNO무선) 할인 기간 무약정:N, 1년:1, 2년:2, 3년:3
    private String unSSn;
    private String jobGubun;     // 작업구분코드 N:신규결합신청, A:회선추가, D:회선삭제
    private String sameCustYn;   // 동일명의여부
    private String contractNum;
    private String limitMin;
    private String reqPayType;   // 요금납부방법 자동이체:D, 신용카드:C, 가상계좌:VA, 자동충전:AA

    // authNum null이면 빈문자열 반환
    public String getAuthNum() {
        if (authNum == null) {
            return "";
        }
        return authNum;
    }

    // contractNum null이면 빈문자열 반환
    public String getContractNum() {
        if (contractNum == null) {
            return "";
        }
        return contractNum;
    }

    // isReal 필드명이 String 타입이므로 비표준 getter/setter 유지
    public String getIsReal() {
        return isReal;
    }

    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }

    public String getReserved02() {
        return reserved02;
    }

    public void setReserved02(String reserved02) {
        this.reserved02 = reserved02;
    }

    public String getReserved03() {
        return reserved03;
    }

    public void setReserved03(String reserved03) {
        this.reserved03 = reserved03;
    }

    // 주민등록번호로 생년월일 추출
    public String getBirthdayOfYYYY() {
        String rtnBirthDay = "";
        if (unSSn != null && unSSn.length() > 6) {
            rtnBirthDay = unSSn.substring(0, 6);
        } else {
            return "";
        }
        if ("1".equals(unSSn.substring(6, 7)) || "2".equals(unSSn.substring(6, 7)) || "5".equals(unSSn.substring(6, 7)) || "6".equals(unSSn.substring(6, 7)) || "*".equals(unSSn.substring(6, 7))) {
            return "19" + rtnBirthDay;
        } else if ("3".equals(unSSn.substring(6, 7)) || "4".equals(unSSn.substring(6, 7)) || "7".equals(unSSn.substring(6, 7)) || "8".equals(unSSn.substring(6, 7))) {
            return "20" + rtnBirthDay;
        } else {
            return rtnBirthDay;
        }
    }

    // 주민등록번호로 성별 구분
    public String getSexCdOfSSn() {
        String rtnSexCd = "";
        if (unSSn == null || unSSn.length() < 6) {
            return "";
        }
        if ("1".equals(unSSn.substring(6, 7)) || "3".equals(unSSn.substring(6, 7)) || "5".equals(unSSn.substring(6, 7)) || "7".equals(unSSn.substring(6, 7)) || "*".equals(unSSn.substring(6, 7))) {
            rtnSexCd = "1"; // 남자
        } else if ("2".equals(unSSn.substring(6, 7)) || "4".equals(unSSn.substring(6, 7)) || "6".equals(unSSn.substring(6, 7)) || "8".equals(unSSn.substring(6, 7))) {
            rtnSexCd = "2"; // 여자
        } else {
            return "";
        }
        return rtnSexCd;
    }

    // 주민등록번호로 고객구분
    public String getCstmrType() {
        String rtnCstmrType = "";
        if (unSSn == null || unSSn.length() < 6) {
            return "";
        }
        int age = NmcpServiceUtils.getAge(unSSn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
        String diviVal = unSSn.substring(6, 7);
        if ("|5|6|7|8".indexOf(diviVal) > -1) {
            if (19 > age) {
                rtnCstmrType = "";
            } else {
                rtnCstmrType = Constants.CSTMR_TYPE_FN;
            }
        } else {
            if (19 > age) {
                rtnCstmrType = Constants.CSTMR_TYPE_NM;
            } else {
                rtnCstmrType = Constants.CSTMR_TYPE_NA;
            }
        }
        return rtnCstmrType;
    }

    // 인증 유효시간 내 여부 확인
    public boolean checkAuthTime(int chekTime) {
        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int btw = 0;
        try {
            btw = DateTimeUtil.minsBetween(startDate, today, "yyyyMMddHHmmss");
        } catch (ParseException e) {
            return false;
        }
        return btw < chekTime;
    }

}
