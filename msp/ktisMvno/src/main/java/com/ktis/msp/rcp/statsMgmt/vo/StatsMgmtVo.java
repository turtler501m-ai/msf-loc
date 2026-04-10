package com.ktis.msp.rcp.statsMgmt.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

import java.util.Map;

public class StatsMgmtVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7174211654570525009L;
	
	private String srchStrtDt;
	private String srchEndDt;
	private String subStatus;
	private String phonCtn;
	private String searchName;
	private String searchGbn;
	private String onOffType;
	private String orderType;
	
	private String dlvryName;
	private String phonCtn1;
	private String phonCtn2;
	private String phonCtn3;
	
	private String dlvryPost;
	private String dlvryAddr;
	private String dlvryAddrDtl;
	private String agrmMemo;
	

	private String seq;
	private String contractNum;
	private String openEndDate;
	
	private String modelName;
	
	private String requestKey;        /**/
	private String prodTypeNm;        /*상품구분    */
	private String cstmrName;         /*고객명      */
	private String ssn;               /*주민번호    */
	private String birth;             /*생년월일    */
	private String age;               /*나이(만)    */
	private String subscriberNo;      /*휴대폰번호  */
	private String lstRateNm;         /*요금제      */
	private String sprtTpNm;          /*할인유형    */
	private String salePlcyNm;        /*판매정책명  */
	private String enggMnthCnt;       /*약정개월수  */
	private String instMnthCnt;       /*할부개월수  */
	private String lstModelNm;        /*단말모델    */
	private String lstModelSrlNum;    /*단말일련번호*/
	private String subStatusNm;       /*상태        */
	private String canRsn;            /*해지사유    */
	private String onOffTypeNm;       /*모집경로    */
	private String openMarketReferer; /*유입경로    */
	private String recommendFlagNm;   /*추천인구분  */
	private String recommendInfo;     /*추천인정보  */
	private String recYn;             /*녹취여부    */
	private String agentNm;           /*대리점      */
	private String shopNm;            /*판매점명    */
	private String shopUsrId;         /*판매자Id    */
	private String shopUsrNm;         /*판매자명    */
	private String dvcChgCnt;         /*기변횟수    */
	
	// 검색조건
	private String lstComActvDateF; /*개통일      */
	private String lstComActvDateT; /*            */
	private String cntpntShopId;    /*개통대리점  */
	private String cntpntShopNm;    /*            */
	private String pAgentCode;      /*판매점      */
	private String pAgentName;      /*            */
	private String reqBuyTypeS;     /*구매유형    */
	
	private String minorYn;         /*미성년자여부*/
	private String appBlckAgrmYn;   /*APP 설치동의*/
	private String appInstYn;       /*APP 설치확인*/
	private String sprtTp;          /*할인유형코드*/
	private String operType;        /*업무구분코드*/
	private String prodType;        /*상품구분코드*/
	
	private String cstmrType;       /*고객구분코드*/
	private String cstmrTypeNm;     /*고객구분    */
	private int total;
	

	// 페이징
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;	
	

	// 복호화
	private String ssn1;				/**/
	private String cstmrForeignerRrn;	/*외국인주민번호    */
	private String minorAgentRrn;		/*법정대리인주민번호*/
	private String reqCardNo;			/*카드번호          */
	private String reqCardRrn;			/*카드주주민번호    */	
	private String reqAccountNumber;	/*계좌번호          */	
	private String reqAccountRrn;		/*예금주주민번호    */
	
	
	
	// 스캔
	private String scanId;				/*스캔ID            */
	private String resNo;				/*예약번호          */
	
	private String ctnVoc;
	private String custNmVoc;
	
	private String reqPayType;			/*요금납부방법      */
	private String requestStateCode;	/*개통진행상태      */
	
	// 기변정보추가
	private String dvcChgDt;			/* 기변일자 */
	private String dvcModelId;			/* 기변모델ID */
	private String dvcModelNm;			/* 기변모델명 */
	private String dvcIntmSrlNo;		/* 기변단말일련번호 */
	private String dvcAgntNm;			/* 기변대리점명 */
	private String dvcOperTypeNm;		/* 기변유형명 */
	private Integer dvcHndstAmnt;		/* 기변단말출고가 */
	private Integer dvcInstOrginAmnt;	/* 기변단말할부원금 */
	private Integer dvcInstMnthCnt;		/* 기변단말할부개월수 */
	private Integer dvcEnggMnthCnt;		/* 기변약정개월수 */
	private String dvcSalePlcyNm;		/* 기변판매정책명 */
	
	
	private String cstmrNm;
	
	private String ctn;
	private String gender;
	private String successYn;
	private String svcContId;
	private String procCd;
	private String clMemo;
	private String custReqSeq;
	private String newScanId;
	private String oldScanId;
	private String fileNum;
	
	private int reqSeq;          	/* 시퀀스 */
	private String userId;				/* 로그인ID */
	private String mobileNo;			/* 핸드폰번호 */
	private String errorStartDate;		/* 발생시작일시 */
	private String errprEmdDate;		/* 발생종료일시 */
	private String errorTypeCd;			/* 불량증상여부 */
	private String errorVoice;			/* 통화품질불량 */
	private String errorCall;			/* 통화수발신불가 */
	private String errorSms;			/* 문자수발신불가 */
	private String errorWifi;			/* WIFI이용불가 */
	private String errorData;			/* 무선데이터이용불가 */
	private String errorLocTypeCd;		/* 증상발생지 */
	private String errorLocTypeNm;		/* 증상발생지 */
	private String cstmrPost;			/* 우편번호 */
	private String cstmrAddr;			/* 주소 */
	private String cstmrAddrDtl;		/* 상세주소 */
	private String regNm;				/* 신청자 */
	private String regMobileNo;			/* 신청자연락처 */
	private String sysDt;				/* 날짜 */
	private String regDt;				/* 신청날짜 */
	private String counselDetail;		/* 상담내용 */
	private String counselRsltCd;		/* 상담결과 */
	private String counselRsltCdNm;		/* 상담결과 */
	private String counselId;			/* 상담처리자 */
	private String counselDt;			/* 상담처리일시 */
	private String errorCd;

	private String csResDt;             /*예약상담일시*/
	private String csResTm;           	/*예약상담 시간대*/
	private Map<String,String> amTime;  /* 예약상담 오전시간대*/
	private Map<String,String> pmTime;  /* 예약상담 오후시간대*/

	// 평생할인 프로모션 적용 대상
	private String evntCd;				/*가입 유형 (NAC 등)*/
	private String procYn;				/*처리 여부 (Y/N)*/
	private String lateChk;				/*지연 여부*/
	private String regstDt;			    /*등록 일자*/
	private String prmtId;				/*평생할인프로모션아이디*/
	private String chkType;			    /*검증 유형*/
	private String regSeq;			    /*평생할인검증시퀀스*/

	private String succYn;				/*성공 여부 (Y/N)*/
	private String prcMstSeq;			/*정책 적용 이력 시퀀스*/
	private String prcDtlSeq;			/*가입 이력 시퀀스*/
	private String prcRtySeq;			/*재처리 시퀀스*/
	private String procMemo;			/*처리결과 메모*/
	private String soc;					/*요금제 코드*/
	private String accessIp;			/*접속 ip*/
	private String globalNo;			/*mplatforn 연동 globalNo*/
	private String rsltCd; 				/*처리 결과 코드*/
	private String prcsSbst;			/*처리 결과 내용*/
	private String customerId;			/*고객 계약 id*/
	private String ncn;					/*서비스 계약번호*/
	private String rtyMaxDt;			/*재처리 가능일*/
	
	private String combYn;			/* 결합여부 */
	private String svcCntrId;			/* 서비스계약ID */

	private String srchGubun;
	private String srchSuccYn;
	private String srchSuccRecentYn;
	private String itemId;
	private String srchVal;

	private String chkDt;			    /*검증 일자*/
	private String chkRslt;			    /*검증 결과*/
	private String compYn;				/*완료 여부*/
	private String compMemo;			/*완료 메모*/
	private String rtyYn;				/*재처리 여부*/
	private String rtyRslt;				/*재처리 결과*/
	private String ktSocs;				/*KT시스템 할인부가서비스*/
	private String prmtSocs;			/*M전산 정책 할인부가서비스*/

    private String mcnResNo;            //명의변경 예약번호
    private String mcnStateCode;        //명의변경 진행상태코드
    private String newCustReqSeq;      //신규 cust_req_seq
    private String newMcnResNo;         //신규 res_no
    private String cstmrMail;

    public int getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(int reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getErrorStartDate() {
		return errorStartDate;
	}

	public void setErrorStartDate(String errorStartDate) {
		this.errorStartDate = errorStartDate;
	}

	public String getErrprEmdDate() {
		return errprEmdDate;
	}

	public void setErrprEmdDate(String errprEmdDate) {
		this.errprEmdDate = errprEmdDate;
	}

	public String getErrorTypeCd() {
		return errorTypeCd;
	}

	public void setErrorTypeCd(String errorTypeCd) {
		this.errorTypeCd = errorTypeCd;
	}

	public String getErrorVoice() {
		return errorVoice;
	}

	public void setErrorVoice(String errorVoice) {
		this.errorVoice = errorVoice;
	}

	public String getErrorCall() {
		return errorCall;
	}

	public void setErrorCall(String errorCall) {
		this.errorCall = errorCall;
	}

	public String getErrorSms() {
		return errorSms;
	}

	public void setErrorSms(String errorSms) {
		this.errorSms = errorSms;
	}

	public String getErrorWifi() {
		return errorWifi;
	}

	public void setErrorWifi(String errorWifi) {
		this.errorWifi = errorWifi;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public String getErrorLocTypeCd() {
		return errorLocTypeCd;
	}

	public void setErrorLocTypeCd(String errorLocTypeCd) {
		this.errorLocTypeCd = errorLocTypeCd;
	}

	public String getErrorLocTypeNm() {
		return errorLocTypeNm;
	}

	public void setErrorLocTypeNm(String errorLocTypeNm) {
		this.errorLocTypeNm = errorLocTypeNm;
	}

	public String getCstmrPost() {
		return cstmrPost;
	}

	public void setCstmrPost(String cstmrPost) {
		this.cstmrPost = cstmrPost;
	}

	public String getCstmrAddr() {
		return cstmrAddr;
	}

	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}

	public String getCstmrAddrDtl() {
		return cstmrAddrDtl;
	}

	public void setCstmrAddrDtl(String cstmrAddrDtl) {
		this.cstmrAddrDtl = cstmrAddrDtl;
	}

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}

	public String getRegMobileNo() {
		return regMobileNo;
	}

	public void setRegMobileNo(String regMobileNo) {
		this.regMobileNo = regMobileNo;
	}

	public String getSysDt() {
		return sysDt;
	}

	public void setSysDt(String sysDt) {
		this.sysDt = sysDt;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getCounselDetail() {
		return counselDetail;
	}

	public void setCounselDetail(String counselDetail) {
		this.counselDetail = counselDetail;
	}

	public String getCounselRsltCd() {
		return counselRsltCd;
	}

	public void setCounselRsltCd(String counselRsltCd) {
		this.counselRsltCd = counselRsltCd;
	}

	public String getCounselRsltCdNm() {
		return counselRsltCdNm;
	}

	public void setCounselRsltCdNm(String counselRsltCdNm) {
		this.counselRsltCdNm = counselRsltCdNm;
	}

	public String getCounselId() {
		return counselId;
	}

	public void setCounselId(String counselId) {
		this.counselId = counselId;
	}

	public String getCounselDt() {
		return counselDt;
	}

	public void setCounselDt(String counselDt) {
		this.counselDt = counselDt;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	@Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getShopUsrId() {
		return shopUsrId;
	}

	public void setShopUsrId(String shopUsrId) {
		this.shopUsrId = shopUsrId;
	}

	public String getSuccessYn() {
		return successYn;
	}
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}
	
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCstmrNm() {
		return cstmrNm;
	}
	public void setCstmrNm(String cstmrNm) {
		this.cstmrNm = cstmrNm;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getProdTypeNm() {
		return prodTypeNm;
	}
	public void setProdTypeNm(String prodTypeNm) {
		this.prodTypeNm = prodTypeNm;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getLstRateNm() {
		return lstRateNm;
	}
	public void setLstRateNm(String lstRateNm) {
		this.lstRateNm = lstRateNm;
	}
	public String getSprtTpNm() {
		return sprtTpNm;
	}
	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
	}
	public String getSalePlcyNm() {
		return salePlcyNm;
	}
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}
	public String getEnggMnthCnt() {
		return enggMnthCnt;
	}
	public void setEnggMnthCnt(String enggMnthCnt) {
		this.enggMnthCnt = enggMnthCnt;
	}
	public String getInstMnthCnt() {
		return instMnthCnt;
	}
	public void setInstMnthCnt(String instMnthCnt) {
		this.instMnthCnt = instMnthCnt;
	}
	public String getLstModelNm() {
		return lstModelNm;
	}
	public void setLstModelNm(String lstModelNm) {
		this.lstModelNm = lstModelNm;
	}
	public String getLstModelSrlNum() {
		return lstModelSrlNum;
	}
	public void setLstModelSrlNum(String lstModelSrlNum) {
		this.lstModelSrlNum = lstModelSrlNum;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getCanRsn() {
		return canRsn;
	}
	public void setCanRsn(String canRsn) {
		this.canRsn = canRsn;
	}
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}
	public String getOpenMarketReferer() {
		return openMarketReferer;
	}
	public void setOpenMarketReferer(String openMarketReferer) {
		this.openMarketReferer = openMarketReferer;
	}
	public String getRecommendFlagNm() {
		return recommendFlagNm;
	}
	public void setRecommendFlagNm(String recommendFlagNm) {
		this.recommendFlagNm = recommendFlagNm;
	}
	public String getRecommendInfo() {
		return recommendInfo;
	}
	public void setRecommendInfo(String recommendInfo) {
		this.recommendInfo = recommendInfo;
	}
	public String getRecYn() {
		return recYn;
	}
	public void setRecYn(String recYn) {
		this.recYn = recYn;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getShopNm() {
		return shopNm;
	}
	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}
	public String getShopUsrNm() {
		return shopUsrNm;
	}
	public void setShopUsrNm(String shopUsrNm) {
		this.shopUsrNm = shopUsrNm;
	}
	public String getDvcChgCnt() {
		return dvcChgCnt;
	}
	public void setDvcChgCnt(String dvcChgCnt) {
		this.dvcChgCnt = dvcChgCnt;
	}
	public String getLstComActvDateF() {
		return lstComActvDateF;
	}
	public void setLstComActvDateF(String lstComActvDateF) {
		this.lstComActvDateF = lstComActvDateF;
	}
	public String getLstComActvDateT() {
		return lstComActvDateT;
	}
	public void setLstComActvDateT(String lstComActvDateT) {
		this.lstComActvDateT = lstComActvDateT;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getCntpntShopNm() {
		return cntpntShopNm;
	}
	public void setCntpntShopNm(String cntpntShopNm) {
		this.cntpntShopNm = cntpntShopNm;
	}
	public String getpAgentCode() {
		return pAgentCode;
	}
	public void setpAgentCode(String pAgentCode) {
		this.pAgentCode = pAgentCode;
	}
	public String getpAgentName() {
		return pAgentName;
	}
	public void setpAgentName(String pAgentName) {
		this.pAgentName = pAgentName;
	}
	public String getReqBuyTypeS() {
		return reqBuyTypeS;
	}
	public void setReqBuyTypeS(String reqBuyTypeS) {
		this.reqBuyTypeS = reqBuyTypeS;
	}
	public String getMinorYn() {
		return minorYn;
	}
	public void setMinorYn(String minorYn) {
		this.minorYn = minorYn;
	}
	public String getAppBlckAgrmYn() {
		return appBlckAgrmYn;
	}
	public void setAppBlckAgrmYn(String appBlckAgrmYn) {
		this.appBlckAgrmYn = appBlckAgrmYn;
	}
	public String getAppInstYn() {
		return appInstYn;
	}
	public void setAppInstYn(String appInstYn) {
		this.appInstYn = appInstYn;
	}
	public String getSprtTp() {
		return sprtTp;
	}
	public void setSprtTp(String sprtTp) {
		this.sprtTp = sprtTp;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}
	public String getCstmrTypeNm() {
		return cstmrTypeNm;
	}
	public void setCstmrTypeNm(String cstmrTypeNm) {
		this.cstmrTypeNm = cstmrTypeNm;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
	public String getSsn1() {
		return ssn1;
	}
	public void setSsn1(String ssn1) {
		this.ssn1 = ssn1;
	}
	public String getCstmrForeignerRrn() {
		return cstmrForeignerRrn;
	}
	public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
		this.cstmrForeignerRrn = cstmrForeignerRrn;
	}
	public String getMinorAgentRrn() {
		return minorAgentRrn;
	}
	public void setMinorAgentRrn(String minorAgentRrn) {
		this.minorAgentRrn = minorAgentRrn;
	}
	public String getReqCardNo() {
		return reqCardNo;
	}
	public void setReqCardNo(String reqCardNo) {
		this.reqCardNo = reqCardNo;
	}
	public String getReqCardRrn() {
		return reqCardRrn;
	}
	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
	}
	public String getReqAccountNumber() {
		return reqAccountNumber;
	}
	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getCtnVoc() {
		return ctnVoc;
	}
	public void setCtnVoc(String ctnVoc) {
		this.ctnVoc = ctnVoc;
	}
	public String getCustNmVoc() {
		return custNmVoc;
	}
	public void setCustNmVoc(String custNmVoc) {
		this.custNmVoc = custNmVoc;
	}
	public String getReqPayType() {
		return reqPayType;
	}
	public void setReqPayType(String reqPayType) {
		this.reqPayType = reqPayType;
	}
	public String getRequestStateCode() {
		return requestStateCode;
	}
	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}
	public String getDvcChgDt() {
		return dvcChgDt;
	}
	public void setDvcChgDt(String dvcChgDt) {
		this.dvcChgDt = dvcChgDt;
	}
	public String getDvcModelId() {
		return dvcModelId;
	}
	public void setDvcModelId(String dvcModelId) {
		this.dvcModelId = dvcModelId;
	}
	public String getDvcModelNm() {
		return dvcModelNm;
	}
	public void setDvcModelNm(String dvcModelNm) {
		this.dvcModelNm = dvcModelNm;
	}
	public String getDvcIntmSrlNo() {
		return dvcIntmSrlNo;
	}
	public void setDvcIntmSrlNo(String dvcIntmSrlNo) {
		this.dvcIntmSrlNo = dvcIntmSrlNo;
	}
	public String getDvcAgntNm() {
		return dvcAgntNm;
	}
	public void setDvcAgntNm(String dvcAgntNm) {
		this.dvcAgntNm = dvcAgntNm;
	}
	public String getDvcOperTypeNm() {
		return dvcOperTypeNm;
	}
	public void setDvcOperTypeNm(String dvcOperTypeNm) {
		this.dvcOperTypeNm = dvcOperTypeNm;
	}
	public Integer getDvcHndstAmnt() {
		return dvcHndstAmnt;
	}
	public void setDvcHndstAmnt(Integer dvcHndstAmnt) {
		this.dvcHndstAmnt = dvcHndstAmnt;
	}
	public Integer getDvcInstOrginAmnt() {
		return dvcInstOrginAmnt;
	}
	public void setDvcInstOrginAmnt(Integer dvcInstOrginAmnt) {
		this.dvcInstOrginAmnt = dvcInstOrginAmnt;
	}
	public Integer getDvcInstMnthCnt() {
		return dvcInstMnthCnt;
	}
	public void setDvcInstMnthCnt(Integer dvcInstMnthCnt) {
		this.dvcInstMnthCnt = dvcInstMnthCnt;
	}
	public Integer getDvcEnggMnthCnt() {
		return dvcEnggMnthCnt;
	}
	public void setDvcEnggMnthCnt(Integer dvcEnggMnthCnt) {
		this.dvcEnggMnthCnt = dvcEnggMnthCnt;
	}
	public String getDvcSalePlcyNm() {
		return dvcSalePlcyNm;
	}
	public void setDvcSalePlcyNm(String dvcSalePlcyNm) {
		this.dvcSalePlcyNm = dvcSalePlcyNm;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getOpenEndDate() {
		return openEndDate;
	}
	public void setOpenEndDate(String openEndDate) {
		this.openEndDate = openEndDate;
	}
	
	public String getSrchStrtDt() {
		return srchStrtDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getPhonCtn() {
		return phonCtn;
	}
	public void setPhonCtn(String phonCtn) {
		this.phonCtn = phonCtn;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getDlvryName() {
		return dlvryName;
	}
	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}
	public String getPhonCtn1() {
		return phonCtn1;
	}
	public void setPhonCtn1(String phonCtn1) {
		this.phonCtn1 = phonCtn1;
	}
	public String getPhonCtn2() {
		return phonCtn2;
	}
	public void setPhonCtn2(String phonCtn2) {
		this.phonCtn2 = phonCtn2;
	}
	public String getPhonCtn3() {
		return phonCtn3;
	}
	public void setPhonCtn3(String phonCtn3) {
		this.phonCtn3 = phonCtn3;
	}
	public String getDlvryPost() {
		return dlvryPost;
	}
	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}
	public String getDlvryAddr() {
		return dlvryAddr;
	}
	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}
	public String getDlvryAddrDtl() {
		return dlvryAddrDtl;
	}
	public void setDlvryAddrDtl(String dlvryAddrDtl) {
		this.dlvryAddrDtl = dlvryAddrDtl;
	}
	public String getAgrmMemo() {
		return agrmMemo;
	}
	public void setAgrmMemo(String agrmMemo) {
		this.agrmMemo = agrmMemo;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSvcContId() {
		return svcContId;
	}
	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	public String getProcCd() {
		return procCd;
	}

	public void setProcCd(String procCd) {
		this.procCd = procCd;
	}

	public String getClMemo() {
		return clMemo;
	}

	public void setClMemo(String clMemo) {
		this.clMemo = clMemo;
	}

	public String getCustReqSeq() {
		return custReqSeq;
	}

	public void setCustReqSeq(String custReqSeq) {
		this.custReqSeq = custReqSeq;
	}

	public String getNewScanId() {
		return newScanId;
	}

	public void setNewScanId(String newScanId) {
		this.newScanId = newScanId;
	}

	public String getOldScanId() {
		return oldScanId;
	}

	public void setOldScanId(String oldScanId) {
		this.oldScanId = oldScanId;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getCsResDt() {
		return csResDt;
	}

	public void setCsResDt(String csResDt) {
		this.csResDt = csResDt;
	}

	public String getCsResTm() {
		return csResTm;
	}

	public void setCsResTm(String csResTm) {
		this.csResTm = csResTm;
	}

	public Map<String, String> getAmTime() {
		return amTime;
	}

	public void setAmTime(Map<String, String> amTime) {
		this.amTime = amTime;
	}

	public Map<String, String> getPmTime() {
		return pmTime;
	}

	public void setPmTime(Map<String, String> pmTime) {
		this.pmTime = pmTime;
	}

	public String getEvntCd() {
		return evntCd;
	}

	public void setEvntCd(String evntCd) {
		this.evntCd = evntCd;
	}

	public String getProcYn() {
		return procYn;
	}

	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}

	public String getLateChk() {
		return lateChk;
	}

	public void setLateChk(String lateChk) {
		this.lateChk = lateChk;
	}

	public String getRegstDt() {
		return regstDt;
	}

	public void setRegstDt(String regstDt) {
		this.regstDt = regstDt;
	}

	public String getSuccYn() {
		return succYn;
	}

	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}

	public String getPrcMstSeq() {
		return prcMstSeq;
	}

	public void setPrcMstSeq(String prcMstSeq) {
		this.prcMstSeq = prcMstSeq;
	}

	public String getPrcDtlSeq() {
		return prcDtlSeq;
	}

	public void setPrcDtlSeq(String prcDtlSeq) {
		this.prcDtlSeq = prcDtlSeq;
	}

	public String getPrcRtySeq() {
		return prcRtySeq;
	}

	public void setPrcRtySeq(String prcRtySeq) {
		this.prcRtySeq = prcRtySeq;
	}

	public String getProcMemo() {
		return procMemo;
	}

	public void setProcMemo(String procMemo) {
		this.procMemo = procMemo;
	}

	public String getSoc() {
		return soc;
	}

	public void setSoc(String soc) {
		this.soc = soc;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public String getGlobalNo() {
		return globalNo;
	}

	public void setGlobalNo(String globalNo) {
		this.globalNo = globalNo;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getPrcsSbst() {
		return prcsSbst;
	}

	public void setPrcsSbst(String prcsSbst) {
		this.prcsSbst = prcsSbst;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}

	public String getRtyMaxDt() {
		return rtyMaxDt;
	}

	public void setRtyMaxDt(String rtyMaxDt) {
		this.rtyMaxDt = rtyMaxDt;
	}

	public String getCombYn() {
		return combYn;
	}

	public void setCombYn(String combYn) {
		this.combYn = combYn;
	}

	public String getSvcCntrId() {
		return svcCntrId;
	}

	public void setSvcCntrId(String svcCntrId) {
		this.svcCntrId = svcCntrId;
	}


	public String getSrchGubun() {
		return srchGubun;
	}

	public void setSrchGubun(String srchGubun) {
		this.srchGubun = srchGubun;
	}

	public String getSrchSuccYn() {
		return srchSuccYn;
	}

	public void setSrchSuccYn(String srchSuccYn) {
		this.srchSuccYn = srchSuccYn;
	}

	public String getSrchSuccRecentYn() {
		return srchSuccRecentYn;
	}

	public void setSrchSuccRecentYn(String srchSuccRecentYn) {
		this.srchSuccRecentYn = srchSuccRecentYn;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSrchVal() {
		return srchVal;
	}

	public void setSrchVal(String srchVal) {
		this.srchVal = srchVal;
	}

	public String getPrmtId() {
		return prmtId;
	}

	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}

	public String getChkType() {
		return chkType;
	}

	public void setChkType(String chkType) {
		this.chkType = chkType;
	}

	public String getRegSeq() {
		return regSeq;
	}

	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}

	public String getChkDt() {
		return chkDt;
	}

	public void setChkDt(String chkDt) {
		this.chkDt = chkDt;
	}

	public String getCompYn() {
		return compYn;
	}

	public void setCompYn(String compYn) {
		this.compYn = compYn;
	}

	public String getCompMemo() {
		return compMemo;
	}

	public void setCompMemo(String compMemo) {
		this.compMemo = compMemo;
	}

	public String getChkRslt() {
		return chkRslt;
	}

	public void setChkRslt(String chkRslt) {
		this.chkRslt = chkRslt;
	}

	public String getRtyYn() {
		return rtyYn;
	}

	public void setRtyYn(String rtyYn) {
		this.rtyYn = rtyYn;
	}

	public String getRtyRslt() {
		return rtyRslt;
	}

	public void setRtyRslt(String rtyRslt) {
		this.rtyRslt = rtyRslt;
	}

	public String getKtSocs() {
		return ktSocs;
	}

	public void setKtSocs(String ktSocs) {
		this.ktSocs = ktSocs;
	}

	public String getPrmtSocs() {
		return prmtSocs;
	}

	public void setPrmtSocs(String prmtSocs) {
		this.prmtSocs = prmtSocs;
	}

    public String getMcnResNo() {
        return mcnResNo;
    }

    public void setMcnResNo(String mcnResNo) {
        this.mcnResNo = mcnResNo;
    }

    public String getMcnStateCode() {
        return mcnStateCode;
    }

    public void setMcnStateCode(String mcnStateCode) {
        this.mcnStateCode = mcnStateCode;
    }

    public String getNewCustReqSeq() {
        return newCustReqSeq;
    }

    public void setNewCustReqSeq(String newCustReqSeq) {
        this.newCustReqSeq = newCustReqSeq;
    }

    public String getNewMcnResNo() {
        return newMcnResNo;
    }

    public void setNewMcnResNo(String newMcnResNo) {
        this.newMcnResNo = newMcnResNo;
    }

    public String getCstmrMail() {
        return cstmrMail;
    }

    public void setCstmrMail(String cstmrMail) {
        this.cstmrMail = cstmrMail;
    }
}
