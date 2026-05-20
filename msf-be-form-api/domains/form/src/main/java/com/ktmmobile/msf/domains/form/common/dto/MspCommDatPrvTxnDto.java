package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspCommDatPrvTxnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String apySeq;          // 신청일련번호
    private String apyId;           // 신청자ID
    private String apyDt;           // 신청일시
    private String apyNm;           // 신청자이름
    private String bthday;          // 생년월일 (YYYYMMDD)
    private String gender;          // 성별 (1:남, 2:여)
    private String sbscPrdtCd;      // 가입상품 (01:모바일)
    private String cntcTelNo;       // 연락처번호
    private String tgtSvcNo;        // 대상서비스번호
    private String recpEmail;       // 수령이메일
    private String confSbst01Yn;    // 요청기간
    private String confSbst02Yn;    // 제공일자
    private String confSbst03Yn;    // 요청사유
    private String confSbst04Yn;    // 제공내역
    private String myslfAthnCi;     // 본인인증CI
    private String rnum;
    private String contractNum;     // 계약번호
    private String resultYn;        // 처리결과
    private String isInvstProc;     // 제공여부
    private String isInvstProcNm;
    private String procDt;          // 제공일자
    private String invstNm;         // 요청기관
    private String reqRsn;          // 요청사유
    private String cstmrNm;         // 고객명
    private String subscriberNo;    // 고객휴대폰번호
    private String userSsn;         // 주민번호
    private String openDt;          // 개통일자
    private String subStatus;       // 상태
    private String tmntDt;          // 해지일자
    private String cstmrAddr;       // 고객주소
    private String reqDttm;         // 요청일자

    @Override
    public String toString() {
        return "MspCommDatPrvTxnDto [apySeq=" + apySeq + ", apyId=" + apyId + ", apyDt=" + apyDt + ", apyNm=" + apyNm
                + ", bthday=" + bthday + ", gender=" + gender + ", sbscPrdtCd=" + sbscPrdtCd + ", cntcTelNo="
                + cntcTelNo + ", tgtSvcNo=" + tgtSvcNo + ", recpEmail=" + recpEmail + ", confSbst01Yn=" + confSbst01Yn
                + ", confSbst02Yn=" + confSbst02Yn + ", confSbst03Yn=" + confSbst03Yn + ", confSbst04Yn=" + confSbst04Yn
                + ", myslfAthnCi=" + myslfAthnCi + ", rnum=" + rnum + ", contractNum=" + contractNum + ", resultYn="
                + resultYn + ", isInvstProc=" + isInvstProc + ", isInvstProcNm=" + isInvstProcNm + ", procDt=" + procDt
                + ", invstNm=" + invstNm + ", reqRsn=" + reqRsn + ", cstmrNm=" + cstmrNm + ", subscriberNo="
                + subscriberNo + ", userSsn=" + userSsn + ", openDt=" + openDt + ", subStatus=" + subStatus
                + ", tmntDt=" + tmntDt + ", cstmrAddr=" + ", reqDttm=" + reqDttm + "]";
    }

}
