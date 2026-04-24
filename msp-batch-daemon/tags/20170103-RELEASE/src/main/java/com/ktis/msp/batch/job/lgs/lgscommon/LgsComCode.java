package com.ktis.msp.batch.job.lgs.lgscommon;

/**
 * Common Constant Code
 * @Class Name : LgsComCode
 * @Description : 물류공통코드
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 * @
 * @author : IB
 * @Create Date : 2015. 8. 20.
 */
public class LgsComCode {
	
	
	/** 기초정보 **/
	public final static String YN_Y = "Y";	// Y
	public final static String YN_N = "N";	// N
	
	public final static String CNFM = "02";	// 확정
	
	
	/** 시퀀스 코드 **/
	public final static String SEQ_IO = "IO";	// 입출고
	public final static String SEQ_PO = "PO";	// 발주
	public final static String SEQ_OR = "OR";	// 주문
	public final static String SEQ_IV = "IV";	// 배송
	public final static String SEQ_MV = "MV";	// 이동,조정
	public final static String SEQ_RT = "RT";	// 반품
	public final static String SEQ_HS = "HS";	// 이력
	public final static String SEQ_SL = "SL";	// 판매
	
	
	/** 바코드구분코드 **/
	public final static String BCD_IND_01 = "BCD";	// 바코드입력
	public final static String BCD_IND_02 = "HND";	// 수기입력
	public final static String BCD_IND_03 = "IMI";	// IMEI
	
	
	/** 바코드자릿수코드 **/
	public final static int    BCD_POS_01 = 6;	// 6
	public final static int    BCD_POS_02 = 7;	// 7
	
	
	/** 바코드업무코드 **/
	public final static String BCD_JOB_01 = LgsSrEnum.SNR_PRCH_02.getSnrCode();	// 납품입고
	public final static String BCD_JOB_02 = LgsSrEnum.SNR_ORDR_03.getSnrCode();	// 주문출고
	public final static String BCD_JOB_03 = LgsSrEnum.SNR_ARVL_03.getSnrCode();	// 교환반품입고
	public final static String BCD_JOB_04 = LgsSrEnum.SNR_ARVL_09.getSnrCode();	// 교환단말입고
	public final static String BCD_JOB_05 = LgsSrEnum.SNR_ARVL_10.getSnrCode();	// 교환거부입고
	public final static String BCD_JOB_06 = LgsSrEnum.SNR_RTGD_03.getSnrCode();	// 일반반품입고
	
	
	/** 조직유형코드 (CMN0003) **/
	public final static String HQHR = "10";	// 본사
	public final static String AGNC = "20";	// 대리점
	public final static String STOR = "30";	// 판매점
	
	
	/** 조직상태코드 (CMN0005) **/
	public final static String ORG_STAT_01 = "1";	// 등록
	public final static String ORG_STAT_02 = "2";	// 폐기
	public final static String ORG_STAT_03 = "3";	// 청산
	public final static String ORG_STAT_04 = "4";	// 가동
	public final static String ORG_STAT_05 = "5";	// 정리
	
	
	/** 모델유형코드 (ORG0007) **/
	public final static String INTM_TYPE_HAND = "01";	// 단말
	public final static String INTM_TYPE_USIM = "02";	// USIM
	
	
	/** 모델신품중고코드 (ORG0011) **/
	public final static String INTM_OLD_N = "N";	// 신품
	public final static String INTM_OLD_Y = "Y";	// 중고품
	
	
	/** 단말상태코드 (LGS3002) **/
	public final static String INTM_STAT_GD = "1";	// 양품
	public final static String INTM_STAT_BD = "2";	// 불량
	
	
	/** 단말용도코드 (LGS3001) **/
	public final static String INTM_USE_SL = "1";	// 판매용
	public final static String INTM_USE_TS = "2";	// 시험용
	
	
	/** 주문유형코드 (LGS0001) **/
	public final static String ORD_ORGN_HQ = "01";	// 발주
	public final static String ORD_ORGN_AG = "02";	// 주문
	
	
	/** 주문상태코드 (LGS1001) **/
	public final static String ORD_STAT_01 = "L21";	// 주문등록
	public final static String ORD_STAT_02 = "L22";	// 주문확정
	public final static String ORD_STAT_03 = "L23";	// 주문취소
	
	
	/** 주문배송코드 (LGS0002) **/
	public final static String ORD_DLV_01 = "DLV01";	// 택배
	public final static String ORD_DLV_02 = "DLV02";	// 퀵
	public final static String ORD_DLV_03 = "DLV03";	// 자송
	
	
	/** 배송유형코드 (LGS1002) **/
	public final static String DLV_ORGN_HQ = "01";	// 본사
	public final static String DLV_ORGN_AG = "02";	// 대리점
	
	
	/** 배송상태코드 (LGS1003) **/
	public final static String DLV_STAT_01 = "L31";	// 출고지시
	public final static String DLV_STAT_02 = "L32";	// 출고확정
	public final static String DLV_STAT_03 = "L52";	// 인수확인
	public final static String DLV_STAT_04 = "L51";	// 인수거부
	
	
	/** 발주상태코드 (LGS1011) **/
	public final static String PCH_STAT_01 = "L11";	// 발주등록
	public final static String PCH_STAT_02 = "L12";	// 발주확정
	public final static String PCH_STAT_03 = "L13";	// 입고등록
	public final static String PCH_STAT_04 = "L14";	// 입고완료
	public final static String PCH_STAT_05 = "L15";	// 발주취소
	
	
	/** 이동유형코드 (LGS2001) **/
	public final static String MV_TYPE_01 = "S01";	// 재고이동
	public final static String MV_TYPE_02 = "S02";	// 조정출고
	public final static String MV_TYPE_03 = "S03";	// 조정입고
	
	
	/** 이동상태코드 (LGS2002) **/
	public final static String MV_STAT_01 = "01";	// 이동요청
	public final static String MV_STAT_02 = "02";	// 이동승인
	public final static String MV_STAT_03 = "03";	// 인수확인
	public final static String MV_STAT_04 = "04";	// 인수거부
	public final static String MV_STAT_05 = "05";	// 이동삭제
	public final static String MV_STAT_06 = "08";	// 승인거부
	
	public final static String IV_STAT_01 = "06";	// 조정출고
	public final static String IV_STAT_02 = "07";	// 조정입고
	
	
	/** 반품유형코드 (LGS2003) **/
	public final static String RT_TYPE_01 = "S11";	// 일반반품
	public final static String RT_TYPE_02 = "S12";	// 교환반품
	public final static String RT_TYPE_03 = "S21";	// USIM일반반품
	public final static String RT_TYPE_04 = "S22";	// USIM교환반품
	
	
	/** 일반반품 상태코드 (LGS2007) **/
	public final static String RT_STAT_01 = "11";	// 반품등록
	public final static String RT_STAT_05 = "15";	// 반품취소
	public final static String RT_STAT_04 = "14";	// 인수확정
	public final static String RT_STAT_03 = "13";	// 인수거부
	
	
	/** 교환반품 상태코드 (LGS2004) **/
	public final static String AR_STAT_01 = "01";	// 요청등록
	public final static String AR_STAT_02 = "02";	// 인수검수
	public final static String AR_STAT_03 = "03";	// 인수확인
	public final static String AR_STAT_04 = "04";	// 인수거부
	public final static String AR_STAT_05 = "05";	// 요청삭제
	public final static String AR_STAT_06 = "06";	// 제조사출고
	
	
	/** 교환반품 사유코드 (LGS2008) **/
	public final static String AR_RSN_01 = "101";	// 송수신음불량
	public final static String AR_RSN_02 = "102";	// 액정불량
	public final static String AR_RSN_03 = "103";	// 키패드 및 버튼불량
	public final static String AR_RSN_04 = "104";	// 전원/베터리 불량
	public final static String AR_RSN_05 = "105";	// 카메라 불량
	public final static String AR_RSN_06 = "106";	// 통화끊김
	public final static String AR_RSN_07 = "107";	// 볼륨이상
	public final static String AR_RSN_08 = "108";	// 화면멈춤/오작동
	public final static String AR_RSN_09 = "109";	// 발열
	public final static String AR_RSN_10 = "110";	// 외관훼손
	public final static String AR_RSN_11 = "111";	// 소프트웨어 오류
	public final static String AR_RSN_12 = "112";	// 기타
	
	
	/** 교환반품 사유코드 (LGS2009) **/
	public final static String AR_STAT_10 = "10";	// 교환요청
	public final static String AR_STAT_11 = "11";	// 요청삭제 (교환삭제)
	public final static String AR_STAT_21 = "21";	// 입고완료 (인수확인)
	public final static String AR_STAT_22 = "22";	// 교환출고 (공급사출고)
	public final static String AR_STAT_31 = "31";	// 교품등록 (공급사 교환등록)
//	public final static String AR_STAT_32 = "32";	// 교품완료 (공급사 교환완료)
	public final static String AR_STAT_33 = "33";	// 환불등록 (공급사 환불등록)
	public final static String AR_STAT_34 = "34";	// 환불완료 (공급사 환불확정)
	public final static String AR_STAT_35 = "35";	// 반송등록 (공급사 교환거부등록)
	public final static String AR_STAT_41 = "41";	// 교환확정 (공급사 교환확정)
	public final static String AR_STAT_42 = "42";	// 반송확정 (공급사 교환거부확정)
	public final static String AR_STAT_43 = "43";	// 교환출고 (물류센터 교환출고)
	public final static String AR_STAT_44 = "44";	// 반송출고 (물류센터 반송출고)
	public final static String AR_STAT_12 = "12";	// 교환인수 (대리점 교환완료)
	public final static String AR_STAT_13 = "13";	// 반송인수 (대리점 반송완료)
	
	
	/** 교환반품 업로드 상태코드 (LGS2010) **/
	public final static String AR_UPLD_01 = "01";	// 등록대기 (단말업로드)
	public final static String AR_UPLD_02 = "02";	// 교환등록 (교품등록)
	public final static String AR_UPLD_03 = "03";	// 교환완료 (교환대기)
	
	
	/** 단말상태 변경코드 (LGS3004) **/
	public final static String HS_SATAT_01 = "H01";	// 용도
	public final static String HS_SATAT_02 = "H02";	// 상태
	
	
	/** 타사단말 처리 상태코드 (LGS4001) **/
	public final static String OTCP_STAT_01 = "REQ";	// 요청
	public final static String OTCP_STAT_02 = "REG";	// 등록
	public final static String OTCP_STAT_03 = "APPR";	// 승인
	public final static String OTCP_STAT_04 = "REJ";	// 반려
	public final static String OTCP_STAT_05 = "CNFM";	// 확정
	public final static String OTCP_STAT_06 = "DEL";	// 삭제
	
	
	/** 타사단말 처리구분코드 (LGS4003) **/
	public final static String OTCP_IND_01 = "R";	// 등록
	public final static String OTCP_IND_02 = "D";	// 삭제
	
	
	/** 타사단말 재고이동상태코드(LGS4006) **/
	public final static String OTCP_MV_STAT_01 = "MR";	// 이동요청
	public final static String OTCP_MV_STAT_02 = "MA";	// 이동승인
	public final static String OTCP_MV_STAT_03 = "MD";	// 이동삭제
	public final static String OTCP_MV_STAT_04 = "AR";	// 승인거부
	public final static String OTCP_MV_STAT_05 = "TC";	// 인수확인
	public final static String OTCP_MV_STAT_06 = "TR";	// 인수거부
	
	
	/** 타사단말 검수코드 (LGS4002) **/
	public final static String OTCP_VRIFY_001 = "VRIFY001";	// 기등록단말
	public final static String OTCP_VRIFY_002 = "VRIFY002";	// 미존재조직
	public final static String OTCP_VRIFY_003 = "VRIFY003";	// 미존재 대리점코드
	public final static String OTCP_VRIFY_004 = "VRIFY004";	// 조직ID 누락
	public final static String OTCP_VRIFY_005 = "VRIFY005";	// 사용자ID 누락
	public final static String OTCP_VRIFY_006 = "VRIFY006";	// 색상모델ID 누락
	public final static String OTCP_VRIFY_007 = "VRIFY007";	// 일련번호 누락
	public final static String OTCP_VRIFY_008 = "VRIFY008";	// IMEI 누락
	public final static String OTCP_VRIFY_009 = "VRIFY009";	// WIFI-MAC 누락
	public final static String OTCP_VRIFY_010 = "VRIFY010";	// 미존재 사용자ID
	public final static String OTCP_VRIFY_011 = "VRIFY011";	// 중고여부 누락
	public final static String OTCP_VRIFY_012 = "VRIFY012";	// 미존재 단가
	public final static String OTCP_VRIFY_013 = "VRIFY013";	// 미존재 색상모델ID
	public final static String OTCP_VRIFY_014 = "VRIFY014";	// IMEI 확인요망
	public final static String OTCP_VRIFY_015 = "VRIFY015";	// WIFI-MAC 확인요망
	public final static String OTCP_VRIFY_016 = "VRIFY016";	// 일련번호 확인요망
	public final static String OTCP_VRIFY_017 = "VRIFY017";	// 중고여부 확인요망
	public final static String OTCP_VRIFY_018 = "VRIFY018";	// 엑셀 중복입력 단말
	public final static String OTCP_VRIFY_019 = "VRIFY019";	// 등록불가 조직
	
	
	/** 여신반영코드 **/
	public final static String CRDT_APPL_YN = "Y";	// 여신적용여부 (Y, N)
	
	public final static String CRDT_TSK_01  = "U";	// 여신사용
	public final static String CRDT_TSK_02  = "R";	// 여신부활
	
	public final static String CRDT_TP_01   = "10";	// 단말주문
	public final static String CRDT_TP_02   = "60";	// 주문취소
	public final static String CRDT_TP_03   = "40";	// 단말반품
	public final static String CRDT_TP_04   = "50";	// 재고이동
	
	public final static String CRDT_CL_01   = "2";	// 여신구분 (당월)
	public final static String CRDT_CL_02   = "3";	// 여신구분 (익월) 2015-06-01 익월적용
	public final static String CRDT_CL_03   = "4";	// 여신구분 (익익월)
	
	
	/** 매출반영코드 **/
	public final static String SALE_ITEM_01 = "KTIS0010";	// 단말매출
	public final static String SALE_ITEM_02 = "KTIS0020";	// USIM매출
	
	public final static String SALE_OCCR_01 = "20";			// 출고
	public final static String SALE_OCCR_02 = "21";			// 반품
	public final static String SALE_OCCR_03 = "22";			// 이동입고
	public final static String SALE_OCCR_04 = "23";			// 이동출고
	
}