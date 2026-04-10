package com.ktmmobile.mcp.mypage.dto;


import java.io.Serializable;


public class RwdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rwdProdCd;  /* 보상서비스 코드 */
    private String rwdProdNm;  /* 보상서비스 명 */
    private int rwdPrd;        /* 보상 약정기간 */
    private String etc5;       /* 보상서비스 정렬기준 (asc) */

    public String getRwdProdCd() {
        return rwdProdCd;
    }

    public void setRwdProdCd(String rwdProdCd) {
        this.rwdProdCd = rwdProdCd;
    }

    public String getRwdProdNm() {
        return rwdProdNm;
    }

    public void setRwdProdNm(String rwdProdNm) {
        this.rwdProdNm = rwdProdNm;
    }

    public int getRwdPrd() {
        return rwdPrd;
    }

    public void setRwdPrd(int rwdPrd) {
        this.rwdPrd = rwdPrd;
    }

    public String getEtc5() {
        return etc5;
    }

    public void setEtc5(String etc5) {
        this.etc5 = etc5;
    }
}
