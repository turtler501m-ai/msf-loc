package com.ktmmobile.mcp.fqc.dto;

import java.io.Serializable;

public class FqcPlcyBnfDto implements Serializable {
    private static final long serialVersionUID = -5070786232592926837L;

    /** 프리퀀시 정책 코드 */
    private String fqcPlcyCd;
    /** 미션 수행 건수  */
    private long msnCnt;
    /** 혜택코드 */
    private String bnfCd;

    /** 혜택명 */
    private String bnfNm;

    /** 혜택레벨 */
    private long bnfLvl;


    public String getFqcPlcyCd() {
        return fqcPlcyCd;
    }

    public void setFqcPlcyCd(String fqcPlcyCd) {
        this.fqcPlcyCd = fqcPlcyCd;
    }

    public long getMsnCnt() {
        return msnCnt;
    }

    public void setMsnCnt(long msnCnt) {
        this.msnCnt = msnCnt;
    }

    public String getBnfCd() {
        return bnfCd;
    }

    public void setBnfCd(String bnfCd) {
        this.bnfCd = bnfCd;
    }

    public String getBnfNm() {
        return bnfNm;
    }

    public void setBnfNm(String bnfNm) {
        this.bnfNm = bnfNm;
    }

    public long getBnfLvl() {
        return bnfLvl;
    }

    public void setBnfLvl(long bnfLvl) {
        this.bnfLvl = bnfLvl;
    }
}
