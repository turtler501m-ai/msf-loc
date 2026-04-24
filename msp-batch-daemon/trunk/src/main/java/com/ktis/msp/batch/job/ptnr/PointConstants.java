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

	// 구글Play 제휴
	public final static String STR_GOOGLEPLAY = "googleplay";
	public final static String GOOGLEPLAY_IF_NO = "KTMMGPLAY_001";       // 구글Play 제휴관련 인터페이스 ID
	public final static String SEED_KEY_GOOGLEPLAY = "G55GLEPLAY2!2*11"; // 구글Play 시드키. 16자리여야 함.
	
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
	
	public final static String STR_LPOINT_PRM = "lpointP";
	public final static String LPOINT_PRM_IF_NO = "IF_LPOINT_02";
	public final static String LPOINT_PRM_SND_FILE_NM = "FTP.M.KTMF.T8.O940.";
	public final static String LPOINT_PRM_RCV_FILE_NM = "FTP.M.KTMF.T8.O941.";
	public final static String LPOINT_PRM_FILE_EXT = ".01";
	
	// 미래에셋 광고제휴
	public final static String STR_MIRAE = "miraeasset";
	public final static String MIRAE_IF_01 = "IF_MIRAE_01";
	public final static String MIRAE_IF_02 = "IF_MIRAE_02";
	public final static String MIRAE_IF_03 = "IF_MIRAE_03";
	public final static String MIRAE_IF_04 = "IF_MIRAE_04";
	public final static String TRADE_CODE01 = "BH3011";  //KTM 제휴 가입 계약건 전송
	public final static String TRADE_CODE02 = "BH3012";  //KTM 제휴 가입 계약건 회신
	public final static String TRADE_CODE03 = "BH3013";  //KTM 제휴 정산 전송
	public final static String TRADE_CODE04 = "BH3021";  //KTM 제휴 보험 해지 전송
	public final static String RCV_BIZ_CD = "L34";
	public final static String SEND_BIZ_CD = "KTM";
	public final static String HEADER_TYPE = "11";
	public final static String BODY_TYPE = "22";
	public final static String TAIL_TYPE = "33";
	
	// 휴대폰안심보험
	public final static String STR_MOBINS = "mobins";
	public final static String STR_DBINS = "dbins";
	public final static String MOBINS_001 = "MOBINS_001";
	public final static String MOBINS_002 = "MOBINS_002";
	public final static String MOBINS_003 = "MOBINS_003";
	public final static String MOBINS_004 = "MOBINS_004";
	public final static String MOBINS_005 = "MOBINS_005";
	public final static String DBINS_001 = "DBINS_001";
	public final static String SEED_KEY_MOBINS = "MOBINS2019!ah2n*";	// 모빈스 시드키
	public final static String SEED_KEY_DBINS = "DBINS2019!omvpz*";		// DB 시드키
	
	// 자급제 보상서비스
	public final static String STR_WINIA = "winiaaid";
	public final static String STR_WINIA_IMG = "winia_img";
	public final static String WINIA_001 = "WINIA_001";
	public final static String WINIA_002 = "WINIA_002";
	public final static String WINIA_003 = "WINIA_003";
	public final static String WINIA_004 = "VRF_RWD_WINIA";
	public final static String WINIA_005 = "STA_RWD_WINIA";
	public final static String WINIA_006 = "CMP_RWD_WINIA";
	public final static String WINIA_007 = "PAY_RWD_WINIA";
	public final static String SEED_KEY_WINIA = "WINIA2023!!ah2n*";// 위니아 시드키
	
	// 스카이라이프 제휴
	public final static String STR_SKYLIFE = "skylife";
	public final static String SKYLIFE_IF = "SKYLIFE";
	public final static String SEED_KEY_SKYLIFE = "SKYLIFE2019!ina*";
}
