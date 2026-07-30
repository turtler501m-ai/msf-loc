package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import com.ktds.crypto.exception.CryptoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.MaskingUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Getter
@Setter
@NoArgsConstructor
public class McpUserCntrMngDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String unUserSSn;
    private String userSsn;
    private String userid;          // 사용자 web ID
    private String cntrMobileNo;    // 휴대폰 번호
    private String sysRdate;        // 입력일
    private String svcCntrNo;       // 서비스 계약번호
    private String custId;          // 고객 ID
    private String soc;             // 요금제 코드
    private String rateNm;          // 요금제 이름
    private int baseAmt;            // 요금제 기본 요금
    private int vatAmt;             // 요금제 기본 요금 + 부가세
    private String rmk;             // 비고
    private int enggMnthCnt;        // 약정개월수
    private int dcAmt;              // 할인금액
    private String modelName;       // 단말기 모델명
    private String modelId;         // 단말기 모델ID
    private String rprsPrdtId;         // 대표 단말기 모델ID
    private String intmSrlNo;       // 단말기일련번호
    private String unIntmSrlNo;     // 단말기일련번호
    private String dobyyyymmdd;     // 생년월일
    private String pointId;         // 제주항공 회원번호
    private String contractNum;     // 계약번호
    private String userSSn;         // 주민등록 번호
    private String subLinkName;     // 실사용자이름
    private String subStatus;       // 상태값
    private String retvGubunCd;     // 주기회선인지 받기 회선인지 구분
    private String lstComActvDate;  // 최초개통일자
    private String pppo;
    private String remindYn;        // 리마인드 문자 수신/차단 대상
    private String remindProdType;  // 리마인드 문자 조회할 상품구분값(MI:밀리의서재, CU:씨유)
    private int promotionDcAmt;     // 요금제 할인 금액
    private String socCode;         // 요금제코드
    private String socNm;           // soc 명
    private String socPrice;        // soc 가격
    private String unSvcNo;         // 결합땜에 추가
    private String formatUnSvcNo;
    private String customerType;
    private String customerId;
    private String userName;        // 고객명
    private String cstmrType;       // 고객유형
    private String openAgntCd;      // 개통대리점
    private String freeDataCnt;     // 무료데이터사용량
    private String freeCallCnt;     // 무료통화사용량
    private String freeSmsCnt;      // 무료문자사용량
    private String onOffType;
    private String colDelinqStatus; // 미납여부
    private String prmtId;          // 평생할인프로모션ID
    private String apdSeq;
    private String banAdrZip;
    private String banAdrPrimaryLn;
    private String banAdrSecondaryLn;
    private String subscriberNo;    // 고객휴대폰번호
    private String age;             // 나이
    private String birth;           // 생년월일(앞6자리)
    private String esimYn;       // 최초 ESim 여부
    private String imei;
    private String blBillingMethod; //요금납부방법

    // cntrMobileNo setter에 formatUnSvcNo 연동 사이드이펙트
    public void setCntrMobileNo(String cntrMobileNo) {
        this.cntrMobileNo = cntrMobileNo;
        this.formatUnSvcNo = StringUtil.getMobileFullNum(StringUtil.NVL(cntrMobileNo, ""));
    }

    public String getCntrMobileNoMasking() {
        if (cntrMobileNo == null) {
            return "";
        }
        return MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(cntrMobileNo));
    }

    // 복호화 처리 포함
    public String getUserSSn() {
        try {
            return NmcpServiceUtils.getSsnDate(EncryptUtil.ace256Dec(userSSn));
        } catch (CryptoException e) {
            return "";
        }
    }

    public void setUserSSn(String userSSn) {
        this.userSSn = userSSn;
    }

    // vatAmt = baseAmt + 부가세(10%)
    public int getVatAmt() {
        vatAmt = baseAmt + (int) (baseAmt * 0.1);
        return vatAmt;
    }

    // 월요금 = vatAmt - promotionDcAmt (최소 0)
    public int getInstMnthAmt() {
        int rtnInt = getVatAmt() - getPromotionDcAmt();
        if (rtnInt > 0) {
            return rtnInt;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "McpUserCntrMngDto{" +
            "unUserSSn='" + unUserSSn + '\'' +
            ", userSsn='" + userSsn + '\'' +
            ", userid='" + userid + '\'' +
            ", cntrMobileNo='" + cntrMobileNo + '\'' +
            ", sysRdate='" + sysRdate + '\'' +
            ", svcCntrNo='" + svcCntrNo + '\'' +
            ", custId='" + custId + '\'' +
            ", soc='" + soc + '\'' +
            ", rateNm='" + rateNm + '\'' +
            ", baseAmt=" + baseAmt +
            ", vatAmt=" + vatAmt +
            ", rmk='" + rmk + '\'' +
            ", enggMnthCnt=" + enggMnthCnt +
            ", dcAmt=" + dcAmt +
            ", modelName='" + modelName + '\'' +
            ", modelId='" + modelId + '\'' +
            ", intmSrlNo='" + intmSrlNo + '\'' +
            ", unIntmSrlNo='" + unIntmSrlNo + '\'' +
            ", dobyyyymmdd='" + dobyyyymmdd + '\'' +
            ", pointId='" + pointId + '\'' +
            ", contractNum='" + contractNum + '\'' +
            ", userSSn='" + userSSn + '\'' +
            ", subLinkName='" + subLinkName + '\'' +
            ", subStatus='" + subStatus + '\'' +
            ", retvGubunCd='" + retvGubunCd + '\'' +
            ", lstComActvDate='" + lstComActvDate + '\'' +
            ", pppo='" + pppo + '\'' +
            ", promotionDcAmt=" + promotionDcAmt +
            ", socCode='" + socCode + '\'' +
            ", socNm='" + socNm + '\'' +
            ", socPrice='" + socPrice + '\'' +
            ", unSvcNo='" + unSvcNo + '\'' +
            ", customerType='" + customerType + '\'' +
            ", customerId='" + customerId + '\'' +
            ", userName='" + userName + '\'' +
            ", cstmrType='" + cstmrType + '\'' +
            ", openAgntCd='" + openAgntCd + '\'' +
            ", freeDataCnt='" + freeDataCnt + '\'' +
            ", freeCallCnt='" + freeCallCnt + '\'' +
            ", freeSmsCnt='" + freeSmsCnt + '\'' +
            ", onOffType='" + onOffType + '\'' +
            ", colDelinqStatus='" + colDelinqStatus + '\'' +
            '}';
    }

}
