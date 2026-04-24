package com.ktis.msp.batch.job.lgs.lgscommon;

/*************************************
 *     STORED AND RELEASED  CODE     *
 *                                   *
 * @Class Name : LgsSrEnum           *
 * @Description : 수정금지           *
 * @author : IB                      *
 * @Create Date : 2015. 8. 20.       *
 *************************************/
public enum LgsSrEnum {
	

	/** 입출고코드 (LGS1000) **/
	
	SNR_PRCH_01("Z1401","PRCH","MN","N","Z14","R","Y","납품출고"),
	SNR_PRCH_02("Z1101","PRCH","PD","Y","Z11","I","Y","납품입고"),
	
	SNR_ORDR_01("Z1501","ORDR","PD","N","Z15","R","Y","주문출고등록"),
	SNR_ORDR_02("Z1104","ORDR","PD","N","Z11","R","Y","주문출고등록취소"),
	SNR_ORDR_03("Z1201","ORDR","PD","Y","Z12","O","Y","주문출고"),
	SNR_ORDR_04("Z1103","ORDR","AG","Y","Z11","I","Y","주문입고"),
	SNR_ORDR_05("Z1110","ORDR","PD","Y","Z11","I","Y","인수거부"),
	
	SNR_MOVE_01("Z1502","MOVE","AG","N","Z15","R","Y","이동등록"),
	SNR_MOVE_02("Z1113","MOVE","AG","N","Z11","R","Y","이동등록취소"),
	SNR_MOVE_03("Z1105","MOVE","AG","Y","Z11","I","Y","재고이동"),
	SNR_MOVE_04("Z1114","MOVE","AG","N","Z11","R","Y","이동거부"),
	
	SNR_ARVL_01("Z1504","ARVL","AG","N","Z15","R","Y","교환반품등록"),
	SNR_ARVL_02("Z1112","ARVL","AG","N","Z11","R","Y","교환반품등록취소"),
	SNR_ARVL_03("Z1102","ARVL","PD","Y","Z11","I","Y","교환반품입고"),
	SNR_ARVL_04("Z1205","ARVL","PD","Y","Z12","O","Y","교환반품출고"),
	SNR_ARVL_05("Z1402","ARVL","MN","N","Z14","R","Y","교환단말등록"),
	SNR_ARVL_06("Z1202","ARVL","MN","N","Z12","I","Y","교환반품완료"),
	SNR_ARVL_07("Z1404","ARVL","MN","N","Z14","F","Y","교환환불"),
//	SNR_ARVL_08("Z1403","ARVL","MN","N","Z14","F","Y","교환환불취소"),
//	SNR_ARVL_08("Z1403","ARVL","MN","N","Z14","O","Y","교환반품거부"),
	SNR_ARVL_09("Z1108","ARVL","PD","Y","Z11","I","Y","교환단말입고"),
	SNR_ARVL_10("Z1118","ARVL","PD","Y","Z11","I","Y","반송단말입고"),
	SNR_ARVL_11("Z1207","ARVL","PD","Y","Z12","O","Y","교환단말출고"),
	SNR_ARVL_12("Z1206","ARVL","PD","Y","Z12","O","Y","반송단말출고"),
	SNR_ARVL_13("Z1107","ARVL","AG","Y","Z11","I","Y","교환입고"),
	SNR_ARVL_14("Z1117","ARVL","AG","Y","Z11","I","Y","반송입고"),
	
	SNR_RTGD_01("Z1503","RTGD","AG","N","Z15","R","Y","일반반품등록"),
	SNR_RTGD_02("Z1115","RTGD","AG","N","Z11","R","Y","일반반품등록취소"),
	SNR_RTGD_03("Z1106","RTGD","PD","Y","Z11","I","Y","일반반품입고"),
	SNR_RTGD_04("Z1116","RTGD","AG","N","Z11","R","Y","일반반품입고거부"),
	
	SNR_ADIV_01("Z1204","ADIV","PD","Y","Z12","O","Y","조정출고"),
	SNR_ADIV_02("Z1109","ADIV","PD","Y","Z11","I","Y","조정입고"),
	
	SNR_SALE_01("Z1301","SALE","AG","Y","Z13","O","Y","판매"),
	SNR_SALE_02("Z1303","SALE","AG","Y","Z13","U","Y","인수전판매"),
	SNR_SALE_03("Z1302","SALE","AG","Y","Z13","O","Y","해지복구"),
	SNR_SALE_04("Z1111","SALE","AG","Y","Z11","I","Y","판매해지"),
	SNR_SALE_05("Z1304","SALE","AG","Y","Z13","O","Y","개별판매"),
	SNR_SALE_06("Z1122","SALE","AG","Y","Z11","I","Y","개별판매취소"),
	
	
	/** 타사단말 입출고코드 **/
	
	SNR_OTSR_01("Z1119","OTSR","AG","Y","Z11","I","N","유통폰입고"),
	SNR_OTSR_02("Z1208","OTSR","AG","N","Z12","R","N","유통폰출고대기"),
	
//	SNR_OTMV_01("Z1505","OTMV","AG","N","Z15","R","N","이동등록"),
//	SNR_OTMV_02("Z1120","OTMV","AG","N","Z11","R","N","이동등록취소"),
//	SNR_OTMV_03("Z1121","OTMV","AG","Y","Z11","I","N","이동입고"),
//	SNR_OTMV_04("Z1506","OTMV","AG","N","Z15","R","N","이동거부"),
	;
	
	
	/** 입출고 유형 코드 **/
	public final static String SNR_IN  = "Z11"; // 입고
	public final static String SNR_OUT = "Z12"; // 출고
	public final static String SNR_RSV = "Z15"; // 예정
	public final static String SNR_SLS = "Z13"; // 판매
	public final static String SNR_ETC = "Z14"; // 기타
	
	
	/** 입출고 구분 코드 **/
	public final static String SNR_IO_I = "I"; // 입고
	public final static String SNR_IO_O = "O"; // 출고
	public final static String SNR_IO_R = "R"; // 예정
	public final static String SNR_IO_F = "F"; // 환불
	public final static String SNR_IO_U = "U"; // 인수전판매
	
	
	/** 입출고 업무 구분 코드 **/
	public final static String SNR_PRCH = "PRCH"; // 발주
	public final static String SNR_ORDR = "ORDR"; // 주문
	public final static String SNR_MOVE = "MOVE"; // 이동
	public final static String SNR_ARVL = "ARVL"; // 교품
	public final static String SNR_RTGD = "RTGD"; // 반품
	public final static String SNR_ADIV = "ADIV"; // 조정
	public final static String SNR_SALE = "SALE"; // 판매
	public final static String SNR_OTSR = "OTSR"; // 타사단말요청
	
	
	/** 입출고 조직 구분 코드 **/
	public final static String SNR_AG = "AG"; // 대리점
	public final static String SNR_PD = "PD"; // 물류센터
	public final static String SNR_MN = "MN"; // 공급사
	public final static String SNR_NO = "NO"; // 없음
	
	
	private final String snrCode;	/** 입출고코드 **/
	private final String jobCode;	/** 업무구분코드 **/
	private final String orgCode;	/** 조직구분코드 **/
	private final String snrYn;		/** 입출고여부 **/
	private final String snrType;	/** 입출고유형 **/
	private final String snrInd;	/** 입출고구분 **/
	private final String innCode;	/** 자사구분 **/
	private final String snrDesc;	/** 입출고코드명 **/
	
	
	LgsSrEnum(String snrCode, String jobCode, String orgCode, String snrYn, String snrType, String snrInd, String innCode, String snrDesc) {
		this.snrCode = snrCode;
		this.jobCode = jobCode;
		this.orgCode = orgCode;
		this.snrYn   = snrYn;
		this.snrType = snrType;
		this.snrInd  = snrInd;
		this.innCode = innCode;
		this.snrDesc = snrDesc;
	}

	public String getSnrCode() {
		return snrCode;
	}

	public String getJobCode() {
		return jobCode;
	}

	public String getOrgCode() {
		return orgCode;
	}
	
	public String getSnrYn() {
		return snrYn;
	}

	public String getSnrType() {
		return snrType;
	}

	public String getSnrInd() {
		return snrInd;
	}
	
	public String getInnCode() {
		return innCode;
	}

	public String getSnrDesc() {
		return snrDesc;
	}

}