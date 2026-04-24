package com.ktis.msp.batch.job.lgs.lgscommon;

/*************************************
 *           물류개통코드            *
 *                                   *
 * @Class Name : LgsOpenEnum         *
 * @Description : 수정금지           *
 * @author : IB                      *
 * @Create Date : 2015. 8. 20.       *
 *************************************/
public enum LgsOpenEnum {
	
	
	/** NSTEP 연동데이터 처리결과코드 (LGS3006) **/
	PRC000("PRC000","Q","EXEC","[EXEC] 처리예외전문"),
	
	
	PRC001("PRC001","S","SKIP","[SKIP] 반영제외전문"),
	PRC002("PRC002","S","SKIP","[SKIP] 미등록고객"),
	PRC003("PRC003","S","SKIP","[SKIP] 선불전문"),
	PRC004("PRC004","S","SKIP","[SKIP] 미등록모델"),
	PRC005("PRC005","S","SKIP","[SKIP] USIM전문"),
	PRC006("PRC006","S","SKIP","[SKIP] 미등록기기"),
	
	
	PRC801("PRC801","S","ORGN","[SKIP] 미등록조직"),
	PRC802("PRC802","S","ORGN","[SKIP] 비판매조직"),
	PRC803("PRC803","S","ORGN","[SKIP] 비가동조직"),
	
	
	PRC101("PRC101","C","NAC","[CHCK] 개통:재고조직상이"),
	PRC102("PRC102","C","NAC","[CHCK] 개통:이력존재"),
	PRC103("PRC103","C","NAC","[CHCK] 개통:사유코드상이"),
	
	
	PRC201("PRC201","C","C07","[CHCK] 기변:이력미존재"),
	PRC202("PRC202","C","C07","[CHCK] 기변:해지이력존재"),
	PRC203("PRC203","C","C07","[CHCK] 기변:사유코드상이"),
	PRC204("PRC204","C","C07","[CHCK] 기변:단말미존재"),
	PRC205("PRC205","C","C07","[CHCK] 기변:판매단말미존재"),
	PRC206("PRC206","C","C07","[CHCK] 기변:AS기간만료"),
	PRC207("PRC207","C","C07","[CHCK] 기변:대표모델상이"),
	PRC208("PRC208","C","C07","[CHCK] 기변:중고여부상이"),
	PRC209("PRC209","C","C07","[CHCK] 기변:판매조직상이"),
	PRC210("PRC210","C","C07","[CHCK] 기변:재고상태상이"),
	PRC211("PRC211","C","C07","[CHCK] 기변:우수기변이력미존재"),
	
	
	PRC301("PRC301","C","CAN","[CHCK] 해지:사유코드상이"),
	PRC302("PRC302","C","CAN","[CHCK] 해지:이력미존재"),
	PRC303("PRC303","C","CAN","[CHCK] 해지:해지이력존재"),
	PRC304("PRC304","C","CAN","[CHCK] 해지:재고상태상이"),
	
	
	PRC401("PRC401","C","RCL","[CHCK] 해지복구:사유코드상이"),
	PRC402("PRC402","C","RCL","[CHCK] 해지복구:이력미존재"),
	PRC403("PRC403","C","RCL","[CHCK] 해지복구:해지이력미존재"),
	PRC404("PRC404","C","RCL","[CHCK] 해지복구:복구일자만료"),
	PRC405("PRC405","C","RCL","[CHCK] 해지복구:재고조직상이"),
	PRC406("PRC406","C","RCL","[CHCK] 해지복구:재고상태상이"),
	
	
	PRC910("PRC910","D","DONE","[DONE] 개통처리"),
	PRC920("PRC920","D","DONE","[DONE] 기변처리"),
	PRC930("PRC930","D","DONE","[DONE] 해지처리"),
	PRC940("PRC940","D","DONE","[DONE] 복구처리"),
	PRC950("PRC950","D","DONE","[DONE] 우수기변처리"),
	PRC960("PRC960","D","DONE","[DONE] 우수기변취소처리"),
	
	PRC999("PRC999","D","DONE","[DONE] 처리완료");
	
	
	/** NSTEP 연동데이터 이벤트코드 (LGS0900) **/
	public final static String EVNT_NAC = "NAC"; // 개통
	public final static String EVNT_C07 = "C07"; // 기변
	public final static String EVNT_CAN = "CAN"; // 해지
	public final static String EVNT_RCL = "RCL"; // 당일해지복구
	public final static String EVNT_UPD = "UPD"; // 정합
	public final static String EVNT_UPS = "UPS"; // 부분정합
	public final static String EVNT_DEL = "DEL"; // 불가
	
	
	/** NSTEP 연동데이터 이벤트상세코드 (LGS0901) **/
	public final static String EVNT_RSN_VP = "01"; // 우수기변
	public final static String EVNT_RSN_VC = "07"; // 우수기변취소
	public final static String EVNT_RSN_NR = "11"; // 일반기변
	public final static String EVNT_RSN_LS = "13"; // 분실후기변
	public final static String EVNT_RSN_AS = "30"; // AS기변
	public final static String EVNT_RSN_ER = "32"; // EIR기변
	public final static String EVNT_RSN_UM = "38"; // USIM변경
	public final static String EVNT_RSN_NW = "39"; // 신규개통
	public final static String EVNT_RSN_CN = "40"; // 일반해지
	public final static String EVNT_RSN_CR = "44"; // 해지복구
	public final static String EVNT_RSN_MP = "45"; // 명변가입
	public final static String EVNT_RSN_CC = "00"; // 판매철회
	
	
	/** 연동데이터 조직처리 코드 **/
	public final static String ORG_STAT_NON = "NON"; // 조직미존재
	public final static String ORG_STAT_NSL = "NSL"; // 비판매조직
	public final static String ORG_STAT_NLV = "NLV"; // 비가동조직
	public final static String ORG_STAT_CLR = "CLR"; // 정상대리점
	
	
	/** 연동데이터 단말상태 코드 **/
	public final static String INTM_MYCP_Y = "Y"; // 자사단말
	public final static String INTM_MYCP_N = "N"; // 타사단말
	public final static String INTM_MYCP_X = "X"; // 미존재단말
	
	
	/** 연동데이터 재고상태 코드 **/
	public final static String INV_STAT_WAT = "WAT"; // 처리가능단말
	public final static String INV_STAT_REG = "REG"; // 입고단말
	public final static String INV_STAT_RSV = "RSV"; // 예정단말
	public final static String INV_STAT_SLS = "SLS"; // 판매단말
	public final static String INV_STAT_DEL = "DEL"; // 처리불가단말
	
	
	private final String code;		/** 개통상태코드 **/
	private final String stat;		/** 처리상태코드 **/
	private final String type;		/** 처리유형코드 **/
	private final String desc;		/** 개통상태코드명 **/
	
	LgsOpenEnum(String code, String stat, String type, String desc) {
		this.code = code;
		this.stat = stat;
		this.type = type;
		this.desc = desc;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getStat() {
		return this.stat;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
}