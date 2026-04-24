package com.ktis.msp.batch.job.ptnr;

public class PointConstants {
	
//	public final static String ROOT_DIR = "partner/";
	public final static String FILE_EXT = ".DAT";
	public final static String RESULT_PREFIX = "RESULT_";
	
	// 제주항공 제휴
	public final static String STR_JEJUAIR = "jejuair";
	public final static String JEJUAIR_IF_NO = "IF_WS_0009";        // 제주항공 제휴요금제 관련 인터페이스 ID
	
	// 엠하우스(기프티쇼) 제휴
	public final static String STR_GIFTI = "gifti";
	public final static String GIFTI_IF_NO = "IF_GT_0001";          // 엠하우스 제휴관련 인터페이스 ID
	public final static String SEED_KEY_GIFTI = "KTMHOWS2016!owm*"; // 엠하우스 시드키. 16자리여야 함.
	
	// 한국스마트카드(티머니) 제휴
	public final static String STR_TMONEY = "tmoney";
	public final static String TMONEY_IF_NO = "KTMMKSCC_001";       // 티머니 제휴관련 인터페이스 ID
	public final static String SEED_KEY_TMONEY = "KSCC2016!wer9sm*";// 티머니 시드키. 16자리여야 함.
	
	// 동부화재 생활안심요금제 제휴
	public final static String STR_DONGBU = "dongbu";
	public final static String DONGBU_IF_NO = "DONGBU_001";
	public final static String SEED_KEY_DONGBU = "DONGBU2017!wmpg*";// 티머니 시드키. 16자리여야 함.
	
	// 좋은라이프 라이프요금제 제휴
	public final static String STR_GOODLIFE = "gdlife";
	public final static String GOODLIFE_001 = "GOODLIFE_001";           // 가입자정보송신
	public final static String GOODLIFE_002 = "GOODLIFE_002";           // 상조가입 및 요금수납 수신
	public final static String GOODLIFE_003 = "GOODLIFE_003";           // 정산금액송신
	public final static String GOODLIFE_004 = "GOODLIFE_004";           // 정산결과수신
	public final static String SEED_KEY_GOODLIFE = "GDLIFE2017!wmpg*";  // 라이프요금제 시드키. 16자리여야 함.
	
	// 롯데멤버스 제휴
	public final static String STR_LPOINT = "lpoint";
	public final static String LPOINT_IF_NO = "IF_LPOINT_01";
	public final static String LPOINT_SND_FILE_NM = "FTP.M.KTMF.W3.O940.";
	public final static String LPOINT_RCV_FILE_NM = "FTP.M.KTMF.W3.O941.";
	public final static String LPOINT_FILE_EXT = ".01";
	
	
}
