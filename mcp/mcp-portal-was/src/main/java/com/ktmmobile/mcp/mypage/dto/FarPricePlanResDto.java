package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class FarPricePlanResDto implements Serializable {

	private static final long serialVersionUID = 1L;

    private String rateAdsvcLteDesc = "";	//데이터 요금제설명
    private String rateAdsvcCallDesc = "";	//음성 요금제설명
    private String rateAdsvcSmsDesc = "";	//sms문자 요금제 설명

	private int rateAdsvcGdncSeq = 0;     // 요금제부가서비스안내일련번호

	public String getRateAdsvcLteDesc() {
		return rateAdsvcLteDesc;
	}
	public void setRateAdsvcLteDesc(String rateAdsvcLteDesc) {
		this.rateAdsvcLteDesc = rateAdsvcLteDesc;
	}
	public String getRateAdsvcCallDesc() {
		return rateAdsvcCallDesc;
	}
	public void setRateAdsvcCallDesc(String rateAdsvcCallDesc) {
		this.rateAdsvcCallDesc = rateAdsvcCallDesc;
	}
	public String getRateAdsvcSmsDesc() {
		return rateAdsvcSmsDesc;
	}
	public void setRateAdsvcSmsDesc(String rateAdsvcSmsDesc) {
		this.rateAdsvcSmsDesc = rateAdsvcSmsDesc;
	}

	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}

	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
}
