package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class MspRateMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String salePlcyCd;          // 정책코드
    private String rateCd;              // 요금제코드
    private String applEndDt;           // 적용종료일자
    private String applStrtDt;          // 적용시작일자
    private String rateNm;              // 요금제명
    private String rateGrpCd;           // 요금제그룹코드
    private String payClCd;             // 선후불구분
    private String rateType;            // 요금제유형(ORG0008)
    private String dataType;            // 데이터유형(ORG0008)
    private int baseAmt;                // 기본료
    private String freeCallClCd;        // 망내외무료통화구분
    private String freeCallCnt;         // 무료통화건수
    private String nwInCallCnt;         // 망내무료통화건수
    private String nwOutCallCnt;        // 망외무료통화건수
    private String freeSmsCnt;          // 무료문자건수
    private String freeDataCnt;         // 무료데이터건수
    private String rmk;                 // 비고
    private String regstId;             // 등록자ID
    private Date regstDttm;             // 등록일시
    private String rvisnId;             // 수정자ID
    private Date rvisnDttm;             // 수정일시
    private String onlineTypeCd;        // 온라인유형코드
    private String alFlag;              // 알요금제구분자
    private String serviceType;         // 서비스유형
    private String agrmTrm;             // 약정 개월수
    private String onlineCanYn;         // 해지 가능 여부
    private String canCmnt;             // 해지 안내 문구
    private List<String> salePlcyCdList; // 정책리스트
    private String noArgmYn;
    private String sprtTp;              // 할인유형
    private String repRate;
    private int onlineCanDay;
    private String xmlDataCnt;
    private String xmlQosCnt;
    private String xmlCallCnt;
    private String jehuProdType;        // 요금제 제휴처
    private String jehuProdNm;        // 요금제 제휴처명

    // 기본료 + 부가세 10% 계산
    public int getBaseVatAmt() {
        int rtnObj = 0;
        if (baseAmt > 0) {
            BigDecimal valueAmt = new BigDecimal(baseAmt + "");
            BigDecimal addRate = new BigDecimal("1.1");
            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.UP).intValue();
        }
        return rtnObj;
    }

    public String getFreeDataCntWithSize() {
        String convertFreeDataCnt = freeDataCnt;
        BigDecimal castData;
        try {
            if (StringUtils.isEmpty(convertFreeDataCnt)) {
                convertFreeDataCnt = "0";
            }
            castData = new BigDecimal(convertFreeDataCnt);
            if (castData.intValue() < 1) {
                return "";
            }
            if (castData.intValue() >= 1024) {
                return castData.divide(new BigDecimal("1024"), 1, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "GB";
            }
            return castData.intValue() + "MB";
        } catch (NumberFormatException nfe) {
            return freeDataCnt;
        }
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

    public String getFreeCallCntWithSize() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeCallCnt) || freeCallCnt.equals("0")) {
            if (StringUtils.isEmpty(nwOutCallCnt) || StringUtils.isEmpty(nwInCallCnt)) {
                return "";
            } else {
                if (!StringUtils.isEmpty(nwOutCallCnt)) {
                    sbRtn.append("망외").append(nwOutCallCnt);
                    if (StringUtils.isNumeric(nwOutCallCnt)) {
                        sbRtn.append("분");
                    }
                }
                if (sbRtn.length() > 0) {
                    sbRtn.append("<br/>");
                }
                if (!StringUtils.isEmpty(nwInCallCnt)) {
                    sbRtn.append("망내").append(nwInCallCnt);
                    if (StringUtils.isNumeric(nwInCallCnt)) {
                        sbRtn.append("분");
                    }
                }
            }
        } else if (StringUtils.isNumeric(freeCallCnt)) {
            sbRtn.append(freeCallCnt).append("분");
        } else {
            sbRtn.append(freeCallCnt);
        }
        return sbRtn.toString();
    }

    public String getFreeCallCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeCallCnt) || freeCallCnt.equals("0")) {
            if (StringUtils.isEmpty(nwOutCallCnt) || StringUtils.isEmpty(nwInCallCnt)) {
                return "0분";
            } else {
                if (!StringUtils.isEmpty(nwOutCallCnt)) {
                    sbRtn.append("망외 ").append(nwOutCallCnt);
                    if (nwOutCallCnt.indexOf("기본제공") == -1) {
                        sbRtn.append("분");
                    }
                }
                if (sbRtn.length() > 0) {
                    sbRtn.append(",");
                }
                if (!StringUtils.isEmpty(nwInCallCnt)) {
                    sbRtn.append("망내 ").append(nwInCallCnt);
                    if (StringUtils.isNumeric(nwInCallCnt)) {
                        sbRtn.append("분");
                    }
                }
                return sbRtn.toString();
            }
        } else if (StringUtils.isNumeric(freeCallCnt)) {
            sbRtn.append(freeCallCnt).append("분");
        } else {
            sbRtn.append(freeCallCnt);
        }
        return sbRtn.toString();
    }

    public String getFreeSmsCntWithSize() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeSmsCnt) || freeSmsCnt.equals("0")) {
            return "";
        } else if (StringUtils.isNumeric(freeSmsCnt)) {
            sbRtn.append(freeSmsCnt).append("건");
        } else {
            sbRtn.append(freeSmsCnt);
        }
        return sbRtn.toString();
    }

    public String getFreeSmsCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeSmsCnt) || freeSmsCnt.equals("0")) {
            return "0건";
        } else if (StringUtils.isNumeric(freeSmsCnt)) {
            sbRtn.append(freeSmsCnt).append("건");
        } else {
            sbRtn.append(freeSmsCnt);
        }
        return sbRtn.toString();
    }

}
