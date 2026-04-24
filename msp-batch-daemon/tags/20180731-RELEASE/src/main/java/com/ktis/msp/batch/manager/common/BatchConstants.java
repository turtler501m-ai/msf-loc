package com.ktis.msp.batch.manager.common;

public class BatchConstants {
	
	/* 배치상태코드 (실행중:ING, 종료:END, 에러:ERR) */
	public final static String STAT_ING = "ING";
	public final static String STAT_END = "END";
	public final static String STAT_ERR = "ERR";
	
	/* 배치 타입(일회성: ONE_TIME, 주기: SCHEDULE) */
	public final static String BATCH_TYPE_ONE_TIME = "ONE_TIME";
	public final static String BATCH_TYPE_SCHEDULE = "SCHEDULE";
	
	public final static String BATCH_USER_ID = "BATCH";
	
	public final static String BATCH_RESULT_OK = "OK";
	public final static String BATCH_RESULT_NOK = "NOK";
	
	/* 배치 실행유형 (SCHE:스케줄러, REQ:요청, RETRY:재실행) */
	public final static String BATCH_EXEC_TYPE_SCHE = "SCHE";
	public final static String BATCH_EXEC_TYPE_REQ = "REQ";
	public final static String BATCH_EXEC_TYPE_RETRY = "RETRY";
	
}
