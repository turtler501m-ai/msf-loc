package com.ktmmobile.msf.system.common.mplatform.vo;


public class MpTelVO {
    private String strSvcName;//서비스명
    private String strCtnSecs;//총 통화시간
    private String strSecsToRate;//과금대상 통화시간
    private String strSecsToAmt;//과금대상 금액
    private String strFreeMinCur;//당월 무료통화
    private String strFreeMinRoll;//이월 무료통화
    private String strFreeMinTotal;//총 무료통화
    private String strFreeMinUse;//사용한 무료통화
    private String strFreeMinRemain;//남은 무료통화
    private int strCtnSecsInt=0;
    private int strSecsToRateInt=0;
    private int strFreeMinUseInt = 0;
    private int strFreeMinTotalInt = 0;
    private int strFreeMinRemainInt=0;
	private String bunGunNm ;
	private String percent;



	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public int getStrFreeMinRemainInt() {
		return strFreeMinRemainInt;
	}
	public void setStrFreeMinRemainInt(int strFreeMinRemainInt) {
		this.strFreeMinRemainInt = strFreeMinRemainInt;
	}

    public String getBunGunNm() {
		return bunGunNm;
	}
	public void setBunGunNm(String bunGunNm) {
		this.bunGunNm = bunGunNm;
	}
	public int getStrCtnSecsInt() {
		return strCtnSecsInt;
	}
	public void setStrCtnSecsInt(int strCtnSecsInt) {
		this.strCtnSecsInt = strCtnSecsInt;
	}
	public int getStrSecsToRateInt() {
		return strSecsToRateInt;
	}
	public void setStrSecsToRateInt(int strSecsToRateInt) {
		this.strSecsToRateInt = strSecsToRateInt;
	}
	public String getStrSvcName() {
        return strSvcName;
    }
    public void setStrSvcName(String strSvcName) {
        this.strSvcName = strSvcName;
    }
    public String getStrCtnSecs() {
        return strCtnSecs;
    }
    public void setStrCtnSecs(String strCtnSecs) {
        this.strCtnSecs = strCtnSecs;
    }
    public String getStrSecsToRate() {
        return strSecsToRate;
    }
    public void setStrSecsToRate(String strSecsToRate) {
        this.strSecsToRate = strSecsToRate;
    }
    public String getStrSecsToAmt() {
        return strSecsToAmt;
    }
    public void setStrSecsToAmt(String strSecsToAmt) {
        this.strSecsToAmt = strSecsToAmt;
    }
    public String getStrFreeMinCur() {
        return strFreeMinCur;
    }
    public void setStrFreeMinCur(String strFreeMinCur) {
        this.strFreeMinCur = strFreeMinCur;
    }
    public String getStrFreeMinRoll() {
        return strFreeMinRoll;
    }
    public void setStrFreeMinRoll(String strFreeMinRoll) {
        this.strFreeMinRoll = strFreeMinRoll;
    }
    public String getStrFreeMinTotal() {
        return strFreeMinTotal;
    }
    public void setStrFreeMinTotal(String strFreeMinTotal) {
        this.strFreeMinTotal = strFreeMinTotal;
    }
    public String getStrFreeMinUse() {
        return strFreeMinUse;
    }
    public void setStrFreeMinUse(String strFreeMinUse) {
        this.strFreeMinUse = strFreeMinUse;
    }
    public String getStrFreeMinRemain() {
        return strFreeMinRemain;
    }
    public void setStrFreeMinRemain(String strFreeMinRemain) {
        this.strFreeMinRemain = strFreeMinRemain;
    }
    public int getStrFreeMinUseInt() {
        return strFreeMinUseInt;
    }
    public void setStrFreeMinUseInt(int strFreeMinUseInt) {
        this.strFreeMinUseInt = strFreeMinUseInt;
    }
    public int getStrFreeMinTotalInt() {
        return strFreeMinTotalInt;
    }
    public void setStrFreeMinTotalInt(int strFreeMinTotalInt) {
        this.strFreeMinTotalInt = strFreeMinTotalInt;
    }


}
