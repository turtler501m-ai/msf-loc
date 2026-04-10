package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

public class MspRateMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 정책코드 */
    private String salePlcyCd;
    /** 요금제코드 */
    private String rateCd;

    /** 적용종료일자*/
    private String applEndDt;

    /** 적용시작일자 */
    private String applStrtDt;

    /**요금제명 */
    private String rateNm;

    /**요금제그룹코드 */
    private String rateGrpCd;

    /**선후불구분 */
    private String payClCd;

    /** 요금제유형(ORG0008)*/
    private String rateType;

    /** 데이터유형 (ORG0008)*/
    private String dataType;

    /**기본료 */
    private int    baseAmt;

    /** 망내외무료통화구분 */
    private String freeCallClCd;

    /**무료통화건수 */
    private String freeCallCnt;

    /**망내무료통화건수 */
    private String nwInCallCnt;

    /** 망외무료통화건수 */
    private String nwOutCallCnt;

    /** 무료문자건수 */
    private String freeSmsCnt;

    /** 무료데이터건수 */
    private String freeDataCnt;

    /** 비고 */
    private String rmk;

    /** 등록자ID */
    private String regstId;

    /** 등록일시 */
    private Date regstDttm;

    /** 수정자ID*/
    private String rvisnId;

    /** 수정일시 */
    private Date rvisnDttm;

    /** 온라인유형코드 */
    private String onlineTypeCd;

    /** 알요금제구분자 */
    private String alFlag;

    /** 서비스유형  */
    private String serviceType;

    /** 약정 개월수 */
    private String agrmTrm;

    /** 해지 가능 여부 */
    private String onlineCanYn;

    /** 해지 안내 문구 */
    private String canCmnt;

    /** 해지 가능 날짜(개통일 + 해지 가능 날짜의 합 이까지) */
    private int onlineCanDay;

    public String getOnlineCanYn() {
        return onlineCanYn;
    }

    public void setOnlineCanYn(String onlineCanYn) {
        this.onlineCanYn = onlineCanYn;
    }

    public String getCanCmnt() {
        return canCmnt;
    }

    public void setCanCmnt(String canCmnt) {
        this.canCmnt = canCmnt;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getApplEndDt() {
        return applEndDt;
    }

    public void setApplEndDt(String applEndDt) {
        this.applEndDt = applEndDt;
    }

    public String getApplStrtDt() {
        return applStrtDt;
    }

    public void setApplStrtDt(String applStrtDt) {
        this.applStrtDt = applStrtDt;
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getRateGrpCd() {
        return rateGrpCd;
    }

    public void setRateGrpCd(String rateGrpCd) {
        this.rateGrpCd = rateGrpCd;
    }

    public String getPayClCd() {
        return payClCd;
    }

    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getBaseAmt() {
        return baseAmt;
    }

    public void setBaseAmt(int baseAmt) {
        this.baseAmt = baseAmt;
    }

    public int getBaseVatAmt() {
        int rtnObj = 0 ;
        if (baseAmt > 0) {
            BigDecimal valueAmt = new BigDecimal(baseAmt + ""); //기본요금
            BigDecimal addRate    = new BigDecimal("1.1");              //부가가치세율

            rtnObj = valueAmt.multiply(addRate).setScale(0, RoundingMode.UP).intValue();
        }
        return rtnObj;
    }

    public String getFreeCallClCd() {
        return freeCallClCd;
    }

    public void setFreeCallClCd(String freeCallClCd) {
        this.freeCallClCd = freeCallClCd;
    }

    public String getFreeCallCnt() {
        return freeCallCnt;
    }

    public void setFreeCallCnt(String freeCallCnt) {
        this.freeCallCnt = freeCallCnt;
    }

    public String getNwInCallCnt() {
        return nwInCallCnt;
    }

    public void setNwInCallCnt(String nwInCallCnt) {
        this.nwInCallCnt = nwInCallCnt;
    }

    public String getNwOutCallCnt() {
        return nwOutCallCnt;
    }

    public void setNwOutCallCnt(String nwOutCallCnt) {
        this.nwOutCallCnt = nwOutCallCnt;
    }

    public String getFreeSmsCnt() {
        return freeSmsCnt;
    }

    public void setFreeSmsCnt(String freeSmsCnt) {
        this.freeSmsCnt = freeSmsCnt;
    }

    public String getFreeDataCnt() {
        return freeDataCnt;
    }

    public void setFreeDataCnt(String freeDataCnt) {
        this.freeDataCnt = freeDataCnt;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public Date getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public Date getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getOnlineTypeCd() {
        return onlineTypeCd;
    }

    public void setOnlineTypeCd(String onlineTypeCd) {
        this.onlineTypeCd = onlineTypeCd;
    }

    public String getAlFlag() {
        return alFlag;
    }

    public void setAlFlag(String alFlag) {
        this.alFlag = alFlag;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAgrmTrm() {
        return agrmTrm;
    }

    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
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
                return castData.divide(new BigDecimal("1024"),1,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "GB";
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
                return castData.divide(new BigDecimal("1024"),1,BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "GB";
            }
            return castData.intValue() + "MB";

        } catch (NumberFormatException nfe) {
            return freeDataCnt;
        }
    }

    public String getFreeCallCntWithSize() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeCallCnt) || freeCallCnt.equals("0")) {
            if (StringUtils.isEmpty(nwOutCallCnt) ||  StringUtils.isEmpty(nwInCallCnt) ) {
                return "";
            } else {
                if (!StringUtils.isEmpty(nwOutCallCnt) ) {
                    sbRtn.append("망외");
                    sbRtn.append(nwOutCallCnt);

                    if (StringUtils.isNumeric(nwOutCallCnt) ) {
                        sbRtn.append("분");
                    }
                }

                if (sbRtn.length() > 0) {
                    sbRtn.append("<br/>");
                }

                if (!StringUtils.isEmpty(nwInCallCnt) ) {
                    sbRtn.append("망내");
                    sbRtn.append(nwInCallCnt);

                    if (StringUtils.isNumeric(nwInCallCnt) ) {
                        sbRtn.append("분");
                    }
                }
            }
        } else if (StringUtils.isNumeric(freeCallCnt)) {
            sbRtn.append(freeCallCnt);
            sbRtn.append("분");
        } else {
            sbRtn.append(freeCallCnt);
        }

        return sbRtn.toString();
    }

    public String getFreeCallCntByMobile() {
        StringBuffer sbRtn = new StringBuffer();
        if (StringUtils.isEmpty(freeCallCnt) || freeCallCnt.equals("0")) {
            if (StringUtils.isEmpty(nwOutCallCnt) ||  StringUtils.isEmpty(nwInCallCnt) ) {
                return "0분";
            } else {
                if (!StringUtils.isEmpty(nwOutCallCnt) ) {
                    sbRtn.append("망외 ");
                    sbRtn.append(nwOutCallCnt);
                    if (nwOutCallCnt.indexOf("기본제공") == -1){
                        sbRtn.append("분");
                    }
                }
                if (sbRtn.length() > 0) {
                    sbRtn.append(",");
                }
                if (!StringUtils.isEmpty(nwInCallCnt) ) {
                    sbRtn.append("망내 ");
                    sbRtn.append(nwInCallCnt);

                    if (StringUtils.isNumeric(nwInCallCnt) ) {
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

    public String getFreeSmsCntWithSize() {
        StringBuffer sbRtn = new StringBuffer();

        if (StringUtils.isEmpty(freeSmsCnt) || freeSmsCnt.equals("0")) {
            return "";
        } else if (StringUtils.isNumeric(freeSmsCnt)) {
            sbRtn.append(freeSmsCnt);
            sbRtn.append("건");
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
            sbRtn.append(freeSmsCnt);
            sbRtn.append("건");
        } else {
            sbRtn.append(freeSmsCnt);
        }


        return sbRtn.toString();
    }

    public int getOnlineCanDay() {
        return onlineCanDay;
    }

    public void setOnlineCanDay(int onlineCanDay) {
        this.onlineCanDay = onlineCanDay;
    }

}
