package com.ktis.msp.sale.plcyMgmt.common;

public class PolicyConstants {
	
	/* 정책이벤트코드 DHX (ORG0058) */
	public final static String PLCY_EVENT_1001 = "PLCY1001";	// 신규정책생성
	public final static String PLCY_EVENT_1002 = "PLCY1002";	// 초기화
	public final static String PLCY_EVENT_1003 = "PLCY1003";	// 정책생성
	public final static String PLCY_EVENT_1004 = "PLCY1004";	// 확정
	public final static String PLCY_EVENT_1005 = "PLCY1005";	// 확정취소
	public final static String PLCY_EVENT_1006 = "PLCY1006";	// 정책삭제
	public final static String PLCY_EVENT_1007 = "PLCY1007";	// 정책종료
	public final static String PLCY_EVENT_1008 = "PLCY1008";	// 대리점추가
	public final static String PLCY_EVENT_1009 = "PLCY1009";	// 선불대리점추가
	public final static String PLCY_EVENT_1010 = "PLCY1010";	// 요금제추가
	public final static String PLCY_EVENT_1011 = "PLCY1011";	// 정책복사
	public final static String PLCY_EVENT_1012 = "PLCY1012";	// 할부기간
	
	/* 정책 Action Type */
	public final static String PLCY_ACTION_M = "M";	// 정책생성
	public final static String PLCY_ACTION_I = "I";	// 정책초기화
	public final static String PLCY_ACTION_D = "D";	// 정책삭제
	public final static String PLCY_ACTION_C = "C";	// 정책복사
	
}
