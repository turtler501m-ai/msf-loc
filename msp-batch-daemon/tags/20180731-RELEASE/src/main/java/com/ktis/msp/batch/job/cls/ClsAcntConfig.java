package com.ktis.msp.batch.job.cls;

public class ClsAcntConfig {
	// 결산업무코드
	public static final String CLS_TSK_SALE_CNFM_CD				= "A010";	// 매출확정
	public static final String CLS_TSK_DPST_CLOSE_CD			= "B010";	// 입금마감(납기내)
	public static final String CLS_TSK_STOF_PRVD_CD				= "B011";	// 상계지급일자
	public static final String CLS_TSK_INST_BOND_PRVD_CD		= "B012";	// 할부채권지급
	public static final String CLS_TSK_CMSN_PRVD_CD				= "B013";	// 수수료지급
	public static final String CLS_TSK_INST_BOND_ADJ_CD			= "C010";	// 할부채권조정
	public static final String CLS_TSK_INST_BOND_PRCH_CD		= "C020";	// 할부채권매입
	public static final String CLS_TSK_CMSN_CNFM_CD				= "C030";	// 수수료확정
	public static final String CLS_TSK_CMSN_PRCH_CD				= "C040";	// 수수료매입
	public static final String CLS_TSK_CLS_ACNT_CNFM_CD			= "D010";	// 결산확정
	
	public static final String CLS_TSK_BASE_BOND_CD				= "S001";	// 기초채권생성
	public static final String CLS_TSK_EQT_DLY_ADD_AMT_CD		= "S012";	// 단말연체가산금생성
	public static final String CLS_TSK_SALE_CARRY_OVER_CD		= "S013";	// 매출이월
	public static final String CLS_TSK_DPST_STOF_CD				= "S020";	// 입금상계
	public static final String CLS_TSK_CRDT_LOAN_TSK_CD			= "S021";	// 여신반영
	public static final String CLS_TSK_CMSN_PRCH_CARRY_OVER_CD	= "S030";	// 수수료매입이월
	public static final String CLS_TSK_CMSN_PRCH_TRGT_CD		= "S031";	// 수수료매입대상생성
	public static final String CLS_TSK_INST_BOND_PRCH_TRGT_CD	= "S032";	// 할부채권매입대상
	public static final String CLS_TSK_CLS_ACNT_TRGT_CD			= "S040";	// 결산대상생성
	public static final String CLS_TSK_MINUS_EQT_SALE_STOF_CD	= "S041";	// (-)단말매출상계
	public static final String CLS_TSK_INST_BOND_PRCH_STOF_CD	= "S042";	// 할부채권매입상계
	public static final String CLS_TSK_CMSN_PRCH_STOF_CD		= "S043";	// 수수료매입상계
	
	
	// 매출유형
	public static final String SALE_TYPE_PRDT_CD				= "10";	// 단말매출
	public static final String SALE_TYPE_SALE_CD				= "20";	// 영업매출
	public static final String SALE_TYPE_ACNT_CD				= "30";	// 결산매출
	
	// 매출항목코드
	public static final String SALE_ITM_EQT						= "KTIS0010";	// 단말기
	public static final String SALE_ITM_USIM					= "KTIS0020";	// USIM
	public static final String SALE_ITM_STCK_CMPS				= "KTIS0030";	// 재고보상
	public static final String SALE_ITM_RLSE_GRNT				= "KTIS0031";	// 출고장려금
	public static final String SALE_ITM_PREPAY_CARD				= "KTIS0040";	// 선불카드
	public static final String SALE_ITM_PREPAY_CHRG				= "KTIS0041";	// 선불충전
	public static final String SALE_ITM_CHRG_RCPT				= "KTIS0050";	// 요금수납
	public static final String SALE_ITM_CHRG_DLY_AMT			= "KTIS0051";	// 요금연체이자
	public static final String SALE_ITM_EQT_DLY_AMT				= "KTIS0060";	// 단말연체가산금
	public static final String SALE_ITM_AGNT_PENALTY			= "KTIS0070";	// 대리점패널티
	public static final String SALE_ITM_CMSN_RDMPT				= "KTIS0080";	// 수수료환수
	public static final String SALE_ITM_INST_BOND_RDMPT			= "KTIS0090";	// 할부채권환수
	
	// 매출상태코드
	public static final String SALE_STAT_REG					= "10";	// 등록
	public static final String SALE_STAT_APRV					= "20";	// 승인
	public static final String SALE_STAT_CNFM					= "30";	// 확정
	
	// 매출조정상태코드
	public static final String SALE_ADJ_STAT_REG				= "10";	// 등록
	public static final String SALE_ADJ_STAT_RJT				= "20";	// 반려
	public static final String SALE_ADJ_STAT_CNFM				= "30";	// 승인

	// 매출발생구분코드
	public static final String SALE_OCCR_BASIC_BAL				= "10";	// 기초채권
	public static final String SALE_OCCR_EQT_DLV				= "20";	// 출고
	public static final String SALE_OCCR_EQT_BCK				= "21";	// 반품
	public static final String SALE_OCCR_EQT_MV_IN				= "22";	// 이동입고
	public static final String SALE_OCCR_EQT_MV_OUT				= "23";	// 이동출고
	public static final String SALE_OCCR_UNPAY_ADJ				= "24";	// 단가조정(미납)
	public static final String SALE_OCCR_SALE_ADJ				= "30";	// 매출조정
	public static final String SALE_OCCR_STCK_CMPS				= "40";	// 재고보상
	public static final String SALE_OCCR_PREPAY_CARD			= "50";	// 선불카드판매
	public static final String SALE_OCCR_PREPAY_CMSN			= "51";	// 카드판매수수료
	public static final String SALE_OCCR_PREPAY_CHRG			= "60";	// 선불충전
	public static final String SALE_OCCR_CHRG_RCPT				= "70";	// 요금수납
	public static final String SALE_OCCR_CHRG_DLY				= "71";	// 요금연체이자 
	public static final String SALE_OCCR_EQT_DLY				= "80";	// 단말연체이자 
	public static final String SALE_OCCR_PENALTY				= "81";	// 패널티 
	
	// 결산기준코드
	public static final String CLS_ACNT_STDR_PRV_GEN			= "10";	// 전월일반
	public static final String CLS_ACNT_STDR_PRV_CDT			= "11";	// 전월여신
	public static final String CLS_ACNT_STDR_THS_GEN			= "20";	// 당월일반
	public static final String CLS_ACNT_STDR_THS_CDT			= "21";	// 당월여신
	public static final String CLS_ACNT_STDR_NXT_GEN			= "30";	// 익월일반
	public static final String CLS_ACNT_STDR_NXT_CDT			= "31";	// 익월여신
	public static final String CLS_ACNT_STDR_NNXT_GEN			= "40";	// 익익월일반
	public static final String CLS_ACNT_STDR_NNXT_CDT			= "41";	// 익익월여신
	
	// 결산마감상태코드
	public static final String CLOSE_STAT_REG					= "10";	// 등록
	public static final String CLOSE_STAT_ING					= "20";	// 진행
	public static final String CLOSE_STAT_STP					= "30";	// 정지
	public static final String CLOSE_STAT_CMP					= "40";	// 완료
	
	// 결산상태코드
	public static final String CLS_ACNT_STAT_REG				= "10";	// 등록
	public static final String CLS_ACNT_STAT_CFM				= "20";	// 확정
	
	// 할부채권매입발생구분
	public static final String INST_BOND_PRCH_OPEN				= "O";	// 개통
	public static final String INST_BOND_PRCH_TERM				= "T";	// 해지
	
	// 입금상계작업년월
	public static final String TMP_WORK_YM						= "000000";
	
	// 사용자
	public static final String USER_ID							= "SYSTEM";
	
	// 부가세율
	public static final double VAT_RATE							= 0.1;
	
	// 스케줄러 실행상태
	public static final String SCHD_PROC_STAT_ING				= "P";
	public static final String SCHD_PROC_STAT_SUC				= "S";
	public static final String SCHD_PROC_STAT_FAIL				= "F";
	
	
}
