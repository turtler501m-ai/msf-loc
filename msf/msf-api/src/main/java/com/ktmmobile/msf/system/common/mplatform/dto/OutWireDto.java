package com.ktmmobile.msf.system.common.mplatform.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.ktmmobile.msf.system.common.util.XmlParse;

/**
 *
 * 데이터 주고받기 결합 상태 조회
 * x86
 *
 */
public class OutWireDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int sbscPsblTotCnt; //결합가능한 총 회선수
	private int sbscBindNowCnt; //현재 결합된 회선수
	private int sbscBindRemdCnt; //가입가능한 회선수
	public int getSbscPsblTotCnt() {
		return sbscPsblTotCnt;
	}
	public void setSbscPsblTotCnt(int sbscPsblTotCnt) {
		this.sbscPsblTotCnt = sbscPsblTotCnt;
	}
	public int getSbscBindNowCnt() {
		return sbscBindNowCnt;
	}
	public void setSbscBindNowCnt(int sbscBindNowCnt) {
		this.sbscBindNowCnt = sbscBindNowCnt;
	}
	public int getSbscBindRemdCnt() {
		return sbscBindRemdCnt;
	}
	public void setSbscBindRemdCnt(int sbscBindRemdCnt) {
		this.sbscBindRemdCnt = sbscBindRemdCnt;
	}



}
