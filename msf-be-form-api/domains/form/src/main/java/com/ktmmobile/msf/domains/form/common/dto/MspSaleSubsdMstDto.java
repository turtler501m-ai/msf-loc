package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant;

@Getter
@Setter
@NoArgsConstructor
public class MspSaleSubsdMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(MspSaleSubsdMstDto.class);

    private MspSalePlcyMstDto mspSalePlcyMstDto;   // 정책정보마스터(할부이자율정보를 포함하고있다)
    private MspRateMstDto mspRateMstDto;            // 요금제 정보
    private String salePlcyCd;          // 판매정책코드
    private String rateCd;              // 요금제코드
    private String prdtId;              // 상품id
    private String oldYn;               // 중고여부
    private String agrmTrm;             // 약정기간
    private String agrmLabel;           // 약정기간 label
    private String operType;            // 업무유형(가입유형) NAC:신규가입 , MNP:번호이동, HCN:기기변경
    private String orgnId;              // 조직코드
    private int hndstAmt;               // 단말금액(VAT포함)
    private int subsdAmt;               // 공시지원금(VAT포함)
    private int instAmt;                // 할부원금 (단말기 가격 - 공시지원금) (VAT포함)
    private int instCmsn;               // 할부수수료(VAT포함)
    private int totalInstCmsn;          // 총 할부수수료(VAT포함)
    private int agncySubsdMax;          // 대리점보조금[추가지원금]MAX(VAT포함)
    private int agncySubsdAmt;          // 대리점보조금[추가지원금](VAT포함)
    private String regstId;             // 등록자
    private Date regstDttm;             // 등록일
    private String rvisnId;             // 수정자
    private Date rvisnDttm;             // 수정일
    private String sprtTp;              // 지원금유형 단말할인:KD ,요금할인:PM
    private String sprtTpLabel;         // 지원금유형 label
    private int baseAmt;                // 기본요금
    private int dcAmt;                  // 할인금액(약정할인선택시 할인금액)
    private int addDcAmt;               // 추가할인금액(요금할인선택시 할인금액)
    private int promotionDcAmt;         // 프로모션 할인 금액
    private String rateBenefitTxt;      // 요금제 혜택 텍스트
    private BigDecimal instRate;        // 할부 이자율
    private BigDecimal defaultInstRate; // 할부 이자율 기본
    private int vat;                    // 부가가치세
    private int instMnthAmt;            // 월단말요금
    private int payMnthAmt;             // 월납부 단말요금(할부수수료포함)
    private int payMnthChargeAmt;       // 월납부 통신료금
    private int hndstPayAmt;            // 단말실구매가
    private int modelMonthly;           // 단말기 할부 (24,36)
    private String noArgmYn;            // Y:무약정요금은 표기 하지 않는다
    private String payClCd;             // 선불 후불여부
    private String rateNm;              // 요금제명
    private String nwInCallCnt;         // 망내통화
    private String nwOutCallCnt;        // 망외통화
    private String freeSmsCnt;          // SMS
    private String freeDataCnt;         // 데이터
    private String cmnt;                // 요금제 혜택
    private String freeCallCnt;         // 무료통화
    private String dataType;            // data구분
    private String forFrontFastYn;      // 핸드폰 리스트 최저가 조회시 성능 개선용
    private int totalPrice;             // usim 합계가격 baseAmt + dcAmt + addDcAmt
    private int totalVatPrice;          // usim 합계가격 baseAmt + dcAmt + addDcAmt + vat
    private String expnsnStrVal1;       // 확장문자열값1
    private String expnsnStrVal2;       // 확장문자열값2
    private String expnsnStrVal3;       // 확장문자열값3
    private String plcySctnCd;          // 정책구분코드(01:단말,02:유심)
    private String onOffType;           // 온라인오프라인구분
    private String paySort;             // 관리자 정렬순서 PAY_SORT
    private int rateAdsvcGdncSeq;       // XML 시퀀스
    private int xmlPayMnthAmt;          // XML - 납부금액
    private String mmBasAmtVatDesc;     // XML - 기본료
    private String promotionAmtVatDesc; // XML - 프로모션 할인된 값
    private String rateAdsvcBasDesc;    // XML - 요금제 설명
    private String rateAdsvcNm;         // XML - 요금제 명
    private String rateAdsvcData;       // XML - 데이터 설명
    private String rateAdsvcCall;       // XML - 음성 설명
    private String rateAdsvcSms;        // XML - 문자 설명
    private String rateAdsvcPromData;   // XML - 프로모션 데이터 설명
    private String rateAdsvcPromCall;   // XML - 프로모션 음성 설명
    private String rateAdsvcPromSms;    // XML - 프로모션 문자 설명
    private String rateAdsvcBnfit;      // XML - 혜택 안내
    private String rateAdsvcAllianceBnfit; // XML - 제휴 혜택 안내
    private String designYn;            // 설계페이지 여부
    private String prdtIndCd;           // 유심 구분 (03:3G, 05:일반, 06:마이크로)
    private String prdtSctnCd;          // 제품구분코드(02:3G , 03:LTE)
    private String phoneBuyPhrase;
    private String maxDataDelivery;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getSprtTpLabel() {
        if ("0".equals(agrmTrm)) {
            sprtTpLabel = "무약정";
        } else if (PhoneConstant.PHONE_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            sprtTpLabel = "단말할인";
        } else if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            sprtTpLabel = "스폰서할인";
        }
        return sprtTpLabel;
    }

    //--------------------- 요금관련 정책 시작-------------------------------------------------------------//

    // 단말지원금: 공시지원금 + 대리점 보조금
    public int getHndstDcAmt() {
        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            return 0;
        }
        return subsdAmt + agncySubsdAmt;
    }

    // 단말기 할부원금: 단말기 출고가 - (공시지원금 + 대리점 보조금)
    public int getInstAmt() {
        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            return getHndstAmt();
        }
        instAmt = hndstAmt - (subsdAmt + agncySubsdAmt);
        if (instAmt < 0) {
            instAmt = 0;
        }
        return instAmt;
    }

    // 할부수수료: 단말기 할부원금 x (할부수수료/100) — BigDecimal 복리 공식
    public int getInstCmsn() {
        BigDecimal bgYearRate = new BigDecimal("0.059");
        BigDecimal bgInstAmt = new BigDecimal(getInstAmt() + "");
        BigDecimal bgModelMonthly = new BigDecimal(getModelMonthly() + "");
        BigDecimal bgMonth = new BigDecimal("12");
        BigDecimal bgTemp = new BigDecimal("1");
        BigDecimal bgPow;
        BigDecimal bgTemp2 = bgYearRate.divide(bgMonth, 38, BigDecimal.ROUND_HALF_UP).add(bgTemp);
        bgPow = bgTemp2.pow(getModelMonthly()).setScale(38, BigDecimal.ROUND_HALF_UP);
        BigDecimal bgPow2 = bgPow.subtract(bgTemp);

        BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth, 38, BigDecimal.ROUND_HALF_UP).multiply(bgPow)
            .divide(bgPow2, 38, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal bgRound2 = bgInstAmt.divide(bgModelMonthly, 0, BigDecimal.ROUND_DOWN);
        instCmsn = bgRound1.subtract(bgRound2).intValue();
        return instCmsn;
    }

    // 부가가치세: (기본요금 - 기본할인금액) x 0.1
    public int getVat() {
        BigDecimal baseAmt = new BigDecimal(getBaseAmt() + "");
        BigDecimal dcAmt = new BigDecimal(getDcAmt() + "");
        BigDecimal addDcAmt = new BigDecimal(getAddDcAmt() + "");
        BigDecimal addRate = new BigDecimal("0.1");

        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp) || PhoneConstant.SIMPLE_USIM_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            vat = baseAmt.subtract(dcAmt).subtract(addDcAmt).multiply(addRate).setScale(0, RoundingMode.DOWN).intValue();
        } else {
            vat = baseAmt.subtract(dcAmt).multiply(addRate).setScale(0, RoundingMode.DOWN).intValue();
        }

        if (vat < 0) {
            vat = 0;
        }
        return vat;
    }

    // 월납부 단말요금: (할부원금+총할부수수료)/할부개월수
    public int getPayMnthAmt() {
        BigDecimal bgTotal = new BigDecimal(getInstAmt() + getTotalInstCmsn() + "");
        BigDecimal bgModelMonthly = new BigDecimal(getModelMonthly() + "");
        payMnthAmt = bgTotal.divide(bgModelMonthly, 0, BigDecimal.ROUND_HALF_UP).intValue();
        return payMnthAmt;
    }

    // 월단말요금(할부수수료 제외): 단말기 실구매가 / 약정개월
    public int getInstMnthAmt() {
        instMnthAmt = getInstAmt() / getModelMonthly();
        return instMnthAmt;
    }

    // 단말기 실구매가: 월납부요금 x 할부개월
    public int getHndstPayAmt() {
        hndstPayAmt = getPayMnthAmt() * getModelMonthly();
        return hndstPayAmt;
    }

    // 단말기 할부 기간 0일 경우 1을 리턴
    public int getModelMonthly() {
        if (modelMonthly == 0) {
            modelMonthly = 1;
        }
        return modelMonthly;
    }

    // 월납부 통신요금: 기본요금 - 기본할인 - 요금할인 + 부가세
    public int getPayMnthChargeAmt() {
        payMnthChargeAmt = getBaseAmt() - getDcAmt() - getPromotionDcAmt() + getVat();
        if (PhoneConstant.CHARGE_DISCOUNT_FOR_MSP.equals(sprtTp) || PhoneConstant.SIMPLE_USIM_DISCOUNT_FOR_MSP.equals(sprtTp)) {
            payMnthChargeAmt = payMnthChargeAmt - getAddDcAmt();
        }
        return payMnthChargeAmt > 0 ? payMnthChargeAmt : 0;
    }
    //--------------------- 요금관련 정책 끝 -------------------------------------------------------------//

    // 단말금액 VAT제외 리턴
    public int getHndstVatExAmt() {
        BigDecimal bigHndstAmt = new BigDecimal(hndstAmt + "");
        BigDecimal divideVal = new BigDecimal("1.1");
        return bigHndstAmt.divide(divideVal, 0, RoundingMode.HALF_UP).intValue();
    }

    // 단말금액 VAT 리턴
    public int getHndstVatAmt() {
        return hndstAmt - getHndstVatExAmt();
    }

    public int getBaseVatAmt() {
        int rtnObj = 0;
        if (baseAmt > 0) {
            BigDecimal valueAmt = new BigDecimal(baseAmt + "");
            BigDecimal addRate = new BigDecimal("1.1");
            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.DOWN).intValue();
        }
        return rtnObj;
    }

    public int getDcVatAmt() {
        int rtnObj = 0;
        if (dcAmt > 0) {
            BigDecimal valueAmt = new BigDecimal(dcAmt + "");
            BigDecimal addRate = new BigDecimal("1.1");
            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return rtnObj;
    }

    public int getAddDcVatAmt() {
        int rtnObj = 0;
        if (addDcAmt > 0) {
            BigDecimal valueAmt = new BigDecimal(addDcAmt + "");
            BigDecimal addRate = new BigDecimal("1.1");
            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return rtnObj;
    }

    // 요금제 할인 금액
    public int getTotalVatPriceDC() {
        return getDcVatAmt() + getAddDcVatAmt();
    }

    public String getSprtTp() {
        if (sprtTp == null) {
            return "";
        }
        return sprtTp;
    }

    public BigDecimal getInstRate() {
        if (instRate == null) {
            instRate = BigDecimal.ZERO;
        }
        return instRate;
    }

    public String getAgrmLabel() {
        if ("0".equals(agrmTrm)) {
            agrmLabel = " [무약정]";
        } else {
            agrmLabel = "";
        }
        return agrmLabel;
    }

    public String getAgrmLabel2() {
        if ("0".equals(agrmTrm)) {
            return "/ [무약정]";
        } else {
            return "";
        }
    }

    // 총 할부수수료: (할부원금 × 0.059 ÷ 12 × (1+0.059÷12)^월수 ÷ ((1+0.059÷12)^월수-1)) × 월수 – 할부원금
    public int getTotalInstCmsn() {
        if (1 != getModelMonthly()) {
            BigDecimal bgYearRate = new BigDecimal("0.059");
            BigDecimal bgInstAmt = new BigDecimal(getInstAmt() + "");
            BigDecimal bgModelMonthly = new BigDecimal(getModelMonthly() + "");
            BigDecimal bgMonth = new BigDecimal("12");
            BigDecimal bgTemp = new BigDecimal("1");
            BigDecimal bgPow;
            BigDecimal bgTemp2 = bgYearRate.divide(bgMonth, 38, BigDecimal.ROUND_HALF_UP).add(bgTemp);
            bgPow = bgTemp2.pow(getModelMonthly()).setScale(38, BigDecimal.ROUND_HALF_UP);
            BigDecimal bgPow2 = bgPow.subtract(bgTemp);

            BigDecimal bgRound1 = bgInstAmt.multiply(bgYearRate).divide(bgMonth, 38, BigDecimal.ROUND_HALF_UP).multiply(bgPow)
                .divide(bgPow2, 0, BigDecimal.ROUND_DOWN);

            totalInstCmsn = bgRound1.multiply(bgModelMonthly).subtract(bgInstAmt).intValue();
        } else {
            totalInstCmsn = 0;
        }
        return totalInstCmsn;
    }

    public String getFreeCallCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeCallCnt) || freeCallCnt.equals("0")) {
            if (StringUtils.isEmpty(nwOutCallCnt) || StringUtils.isEmpty(nwInCallCnt)) {
                return "0분";
            } else {
                if (!StringUtils.isEmpty(nwOutCallCnt)) {
                    sbRtn.append("망외 ");
                    sbRtn.append(nwOutCallCnt);
                    if (nwOutCallCnt.indexOf("기본제공") == -1) {
                        sbRtn.append("분");
                    }
                }
                if (sbRtn.length() > 0) {
                    sbRtn.append(",");
                }
                if (!StringUtils.isEmpty(nwInCallCnt)) {
                    sbRtn.append("망내 ");
                    sbRtn.append(nwInCallCnt);
                    if (StringUtils.isNumeric(nwInCallCnt)) {
                        sbRtn.append("분");
                    }
                }
                return sbRtn.toString();
            }
        } else if (StringUtils.isNumeric(freeCallCnt)) {
            sbRtn.append(freeCallCnt);
            sbRtn.append("분");
        } else {
            sbRtn.append(freeCallCnt);
        }
        return sbRtn.toString();
    }

    public String getFreeSmsCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeSmsCnt) || freeSmsCnt.equals("0")) {
            return "0건";
        } else if (StringUtils.isNumeric(freeSmsCnt)) {
            sbRtn.append(freeSmsCnt);
            sbRtn.append("건");
        } else {
            sbRtn.append(freeSmsCnt);
        }
        return sbRtn.toString();
    }

    public String getFreeDataCntByMobile() {
        String convertFreeDataCnt = freeDataCnt;
        BigDecimal castData;
        try {
            if (StringUtils.isEmpty(convertFreeDataCnt)) {
                convertFreeDataCnt = "0";
            }
            castData = new BigDecimal(convertFreeDataCnt);
            if (castData.intValue() < 1) {
                return "0MB";
            }
            if (castData.intValue() >= 1024) {
                return castData.divide(new BigDecimal("1024"), 1, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "GB";
            }
            return castData.intValue() + "MB";
        } catch (NumberFormatException nfe) {
            return freeDataCnt;
        }
    }

    public int getTotalPrice() {
        totalPrice = baseAmt - dcAmt - addDcAmt;
        return totalPrice;
    }

    public int getTotalVatPrice() {
        totalVatPrice = baseAmt - dcAmt - addDcAmt - getPromotionDcAmt() + (int) ((baseAmt - dcAmt - addDcAmt) * 0.1);
        return totalVatPrice > 0 ? totalVatPrice : 0;
    }

    public int getPaySortInt() {
        try {
            return Integer.parseInt(paySort);
        } catch (NumberFormatException e) {
            return 9999;
        }
    }
}
