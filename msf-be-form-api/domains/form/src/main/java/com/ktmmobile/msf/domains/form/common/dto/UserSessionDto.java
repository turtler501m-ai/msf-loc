package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Getter
@Setter
@NoArgsConstructor
public class UserSessionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String passWord;
    private String name;
    private String authCode;
    private String pin;
    private String email;
    private String post;
    private String address;
    private String addressDtl;
    private String mobileNo;
    private String mobileNo1;
    private String mobileNo2;
    private String contactNo;
    private String birthday;
    private String gender;
    private String sysRdate;
    private String sysUdate;
    private String sysLdate;
    private String loginCount;
    private String status;
    private String custNum;
    private String authKey;
    private String accessIp;
    private int limitTime;
    private String userDivision;  // 01:정회원
    private String emailCode;
    private String addressBjd;
    private String revocationDate;
    private String answer;        // 캡챠가 생성한 코드
    private String authNum;       // sms인증번호
    private String timer0;        // sms인증 3분제한 경과시 "0"으로 셋팅
    private String authType;      // M:아이디찾기, P:비밀번호찾기
    private String tmpPasswrod;
    private String returnUrl;
    private String pwdChgDt;
    private int monCnt;           // 비번변경 3개월 경과체크용
    private int dayCnt;           // 비번 노출 2주간 체크
    private String autoLoginSeq;
    private String token;
    private String tokenValidPeriod;
    private String loginDivCd;
    private int loginFailCount;   // 로그인 실패 횟수
    private boolean isLogin = false;
    private String loginType;
    private String authSmsNo;     // sms 인증번호
    private String uuid;
    private String customerId;
    private boolean appIsFirst = true;
    private String recaptchaToken; // recaptcha token (매번 다름)
    private String limitType;      // 차단 유형
    private String sysCdate;       // 본인인증 일시
    private String personalInfoCollectAgree; // 개인정보수집및이용동의
    private String clausePriAdFlag;  // 약관_개인정보_광고전송_동의
    private String othersTrnsAgree;  // 제 3자 제공 동의
    private String smsRcvYn;         // 이메일 수신여부
    private String emailRcvYn;       // sms 수신여부

    // userId null이면 빈문자열 반환
    public String getUserId() {
        if (userId == null) {
            return "";
        }
        return userId;
    }

    // name null이면 빈문자열 반환
    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    // userDivision null이면 "00" 반환
    public String getUserDivision() {
        return StringUtil.NVL(userDivision, "00");
    }

    // isLogin 필드명과 동일한 메서드명 (Lombok과 충돌 방지를 위해 명시)
    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    // 로그인 5회 이상 실패 여부
    public boolean isLoginFailCntOver() {
        return this.loginFailCount >= 5;
    }

    // 무작위 대입공격 여부
    public boolean isLoginFailAttack() {
        return this.loginFailCount >= 999;
    }

}
