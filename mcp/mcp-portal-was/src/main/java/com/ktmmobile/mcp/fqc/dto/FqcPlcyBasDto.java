package com.ktmmobile.mcp.fqc.dto;

import java.io.Serializable;

public class FqcPlcyBasDto implements Serializable {

    private static final long serialVersionUID = -6600422500820592142L;


    /** 프리퀀시 정책 코드 */
    private String fqcPlcyCd;
    /** 프리퀀시 제목 */
    private String fqcPlcyNm;
    /** 프리퀀시 설명 */
    private String fqcPlcyDesc;
    /** 참여 대상 */
    private String fqcTrgtCust;
    /** 요금제 포함 여부  */
    private String fqcPlanInclYn;
    /** 프리퀀시 시작일 */
    private String strtDttm;
    /** 프리퀀시 종료일 */
    private String endDttm;
    /** 상태값 A: 활성 , C:완료, D:삭제 */
    private String stateCode;


    public String getFqcPlcyCd() {
        return fqcPlcyCd;
    }

    public void setFqcPlcyCd(String fqcPlcyCd) {
        this.fqcPlcyCd = fqcPlcyCd;
    }

    public String getFqcPlcyNm() {
        return fqcPlcyNm;
    }

    public void setFqcPlcyNm(String fqcPlcyNm) {
        this.fqcPlcyNm = fqcPlcyNm;
    }

    public String getFqcPlcyDesc() {
        return fqcPlcyDesc;
    }

    public void setFqcPlcyDesc(String fqcPlcyDesc) {
        this.fqcPlcyDesc = fqcPlcyDesc;
    }

    public String getFqcTrgtCust() {
        return fqcTrgtCust;
    }

    public void setFqcTrgtCust(String fqcTrgtCust) {
        this.fqcTrgtCust = fqcTrgtCust;
    }

    public String getFqcPlanInclYn() {
        return fqcPlanInclYn;
    }

    public void setFqcPlanInclYn(String fqcPlanInclYn) {
        this.fqcPlanInclYn = fqcPlanInclYn;
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }


}
