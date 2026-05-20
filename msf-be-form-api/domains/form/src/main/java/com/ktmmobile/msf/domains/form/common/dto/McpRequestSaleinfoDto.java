package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestSaleinfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;            // 가입신청_키
    private String sprtTp;              // 지원금유형 (단말할인:KD, 요금할인:PM)
    private long maxDiscount3;          // 추가지원금(MAX)
    private long dcAmt;                 // 할인금액
    private long addDcAmt;              // 추가할인금액
    private String modelId;             // 모델ID
    private String modelMonthly;        // 단말할부개월수
    private long modelInstallment;      // 단말할부원금
    private String modelSalePolicyCode; // 단말출고가_부가세
    private long modelPriceVat;         // 단말출고가_부가세
    private String recycleYn;           // 중고여부
    private long modelDiscount1;        // 제조사장려금
    private long modelDiscount2;        // 공시지원금
    private long enggMnthCnt;           // 약정개월수
    private String socCode;             // 요금제코드
    private String socNm;               // soc 명
    private String socPrice;            // soc 가격
    private String usimPriceType;       // 유심납부유형(B:정기, I:즉납)
    private String joinPriceType;       // 가입비납부유형(B:정기, I:즉납)
    private long joinPrice;
    private long usimPrice;             // 유심판매가
    private long modelPrice;            // 단말출고가
    private String addtionService;      // 부가서비스
    private String addtionServiceSum;   // 부가서비스합계
    private long modelDiscount3;        // 대리점보조금
    private String usimPayMthdCd;       // USIM비 납부방법(0:면제, 1:일시납, 2:분납)
    private String joinPayMthdCd;       // 가입비 납부방법(0:면제, 1:일시납, 2:분납)
    private long realMdlInstamt;        // 실제단말할부원금(VAT포함)
    private String settlWayCd;          // 결제수단코드 (01:신용카드, 02:실시간계좌이체)
    private int settlAmt = 0;           // 결제금액
    private String settlApvNo;          // 결제 승인번호
    private String settlTraNo;          // 결제 거래번호
    private String ownPersonalCode;     // 개인 통관 고유 부호-외산 휴대폰
    private String rprsPrdtId;          // 대표모델 아이디
    private int rentalBaseAmt = 0;      // 렌탈 기본료 금액
    private int rentalBaseDcAmt = 0;    // 렌탈 기본료 할인 금액
    private int rentalModelCpAmt = 0;   // 단말기 배상 금액

    public String getModelMonthlyTxt() {
        if (modelMonthly == null || modelMonthly.equals("")) {
            return "";
        } else {
            return modelMonthly + "개월";
        }
    }

    public String getEnggMnthCntTxt() {
        if (enggMnthCnt == 0) {
            return "무약정";
        } else {
            return enggMnthCnt + "개월";
        }
    }

    public String getSettlWayNm() {
        if (settlWayCd == null) {
            return "";
        } else if ("01".equals(settlWayCd)) {
            return "신용카드";
        } else if ("02".equals(settlWayCd)) {
            return "실시간계좌이체";
        } else {
            return "";
        }
    }

}
