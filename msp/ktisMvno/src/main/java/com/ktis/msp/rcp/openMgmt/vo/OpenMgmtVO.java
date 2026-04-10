package com.ktis.msp.rcp.openMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : OpenMgmtVO
 * @since 2016. 11. 08
 * @version 1.0
 * @see
 *
 */
public class OpenMgmtVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 엑셀 다운로드
	private String prodTypeNm;         /*상품구분        */
	private String contractNum;        /*계약번호        */
	private String resNo;              /*예약번호        */
	private String cstmrName;          /*고객명          */
	private String ssn;                /*주민번호        */
	private String birth;   		   /*생년월일        */
	private String gender;			   /*성별            */
	private String age;				   /*나이(만)        */
	private String reqBuyTypeNm;       /*구매유형        */
	private String operTypeNm;         /*업무구분        */
	private String moveCompany;        
	private String moveCompanyNm;      /*이동전 통신사   */
	private String subscriberNo;       /*휴대폰번호      */
	private String fstRateNm;          /*최초요금제      */
	private String lstRateNm;          /*현재요금제      */
	private String salePlcyNm;         /*판매정책        */
	private String enggMnthCnt;        /*약정개월수      */
	private String instMnthCnt;        /*할부개월수      */
	private String lstModelNm;         /*단말모델명      */
	private String lstModelSrlNum;     /*단말일련번호    */
	private String fstModelNm;         /*최초개통단말    */
	private String fstModelSrlNum;     /*최초단말일련번호*/
	private String subStatusNm;        /*상태            */
	private String canRsn;             /*해지사유        */
	private String onOffTypeNm;        /*모집경로        */
	private String openMarketReferer;  /*유입경로        */
	private String recommendFlagNm;    /*추천인구분      */
	private String recommendInfo;      /*추천인정보      */
	private String recYn;              /*녹취여부        */
	private String agentNm;            /*대리점          */
	private String shopNm;             /*판매점          */
	private String reqInDay;           /*신청일자        */
	private String openDt;             /*개통일자        */
	private String tmntDt;             /*해지일자        */
	private String minorAgentName;     /*법정대리인      */
	private String minorAgentRelation; /*관계            */
	private String minorAgentTel;      /*대리인연락처    */
	private String appNm;              /*유해차단APP명   */
	private String joinPayMthdNm;      /*가입비납부방법  */
	private String joinPrice;          /*가입비          */
	private String usimPayMthdNm;      /*USIM납부방법    */
	private String usimPrice;          /*USIM비          */
	private String openAgntCd;         /*대리점ID        */
	private String instOrginAmnt;      /*할부원금        */
	private String sprtTpNm;           /*할인유형        */
	private String grntInsrMngmNo;     /*보증보험관리번호*/
	private String grntInsrStatNm;     /*보증보험가입상태*/
	private String simId;              /*심플할인ID      */
	private String simEnggMnthCnt;     /*심플약정기간    */
	private String simStartDt;         /*심플약정시작일자*/
	private String simEndDt;           /*심플약정종료일자*/
	private String simStatNm;          /*심플약정상태    */
	private String rentalBaseAmt;      /*렌탈기본료      */
	private String rentalBaseDcAmt;    /*렌탈료할인      */
	private String rentalModelCpAmt;   /*렌탈단말배상금  */

	// 가입자정보 추가 메뉴
	private String svcCntrNo;      /*서비스계약번호*/
	private String lstDataType;    /*고객구분      */
	private String iccId;          /*USIM일련번호  */
	private String unpdIndNm;      /*미납상태      */
	private String ban;            /*청구계정ID    */
	private String customerId;     /*고객ID        */
	private String realUseCustNm;  /*실사용고객    */
	private String customerAdr;    /*고객주소      */

	// 가입자관리 추가 메뉴
	private String shopUsrNm;         /*판매자명    */
	private String dvcChgCnt;         /*기변횟수    */
	
	// 검색조건
	private String lstComActvDateF; /*개통일      */
	private String lstComActvDateT; /*            */
	private String subStatus;       /*가입자상태  */
	private String cntpntShopId;    /*개통대리점  */
	private String cntpntShopNm;    /*            */
	private String pAgentCode;      /*판매점      */
	private String pAgentName;      /*            */
	private String reqBuyTypeS;     /*구매유형    */
	private String onOffType;       /*모집경로코드*/
	private String minorYn;         /*미성년자여부*/
	private String appBlckAgrmYn;   /*APP 설치동의*/
	private String appInstYn;       /*APP 설치확인*/
	private String searchName;      /*검색내용    */
	private String searchGbn;       /*검색조건    */
	private String sprtTp;          /*할인유형코드*/
	private String operType;        /*업무구분코드*/
	private String prodType;        /*상품구분코드*/
	
	private String cstmrType;       /*고객구분코드*/
	private String cstmrTypeNm;     /*고객구분    */
		
	private String requestKey;		/**/
	private String scanId;			/*SCAN ID     */
	
	// 복호화
	private String ssn1;				/**/
	private String cstmrForeignerRrn;	/*외국인주민번호    */
	private String minorAgentRrn;		/*법정대리인주민번호*/
	private String reqCardNo;			/*카드번호          */
	private String reqCardRrn;			/*카드주주민번호    */	
	private String reqAccountNumber;	/*계좌번호          */	
	private String reqAccountRrn;		/*예금주주민번호    */
	
	// 페이징
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	
	// 계약이력
	private String evntChangeDate; /*변경일시  */
//	private String evntNm;         /*변경작업  */
	private String subLinkName;    /*가입자    */
//	private String subStatusNm;    /*가입자상태*/
	private String slsPrsnId;      /*판매자ID  */
//	private String socNm;          /*요금제    */
	private String orgnNm;         /*대리점    */

	// 단말이력
	private String evntStr;   /*변경일시        */
//	private String evntNm;    /*변경작업        */
	private String modelName; /*모델명          */
//	private String intmSrlNo; /*단말일련번호    */
//	private String iccId;     /*USIM카드일련번호*/
	private String wifiMacId; /*WIFI_MAC-ID     */

	// 상품이력
//	private String evntTrtmDt;     /*변경일시*/
	private String socNm;          /*상품명  */
	private String serviceTypeNm;  /*상품유형*/
	private String effectiveDate;  /*시작일시*/
	private String expirationDate; /*종료일시*/
	private String status;         /*상태    */

	// 납부방법
//	private String evntTrtmDt;       /*변경일시        */
	private String blBillingMethod;  /*납부방법        */
	private String blSpclBanCode;    /*BAN구분         */
	private String colDelinqStatus;  /*미납상태        */
	private String bankAcctHolderId; /*실납부자주민번호*/
	private String addr;             /*납부자주소      */
	private String banLinkName;      /*납부자이름      */

	// 청구수납이력
	private String billYm;     /*청구월  */
	private String billItemNm; /*청구항목*/
	private String invAmnt;    /*청구금액*/
	private String adjAmnt;    /*조정금액*/
	private String pymnMthdNm; /*납부방법*/
	private String blpymDate;  /*수납일자*/
	private String pymAmnt;     /*수납금액*/

	// 법정대리인
//	private String minorAgentName; /*법정대리인명*/
//	private String appBlckAgrmYn;  /*APP설치동의 */
	private String agentTelNum;    /*연락처      */
//	private String appInstYn;      /*APP설치확인 */
	private String agentRelNm;     /*관계        */
	private String instCnfmNm;     /*확인자      */
	private String instCnfmDt;     /*확인일자    */
	private String instCnfmId;     /**/

	// 할인유형이력
	private String evntTrtmDt;     /*변경일시        */
	private String evntNm;         /*전문유형        */
//	private String sprtTpNm;       /*할인유형        */
//	private String enggMnthCnt;    /*약정기간        */
//	private String grntInsrMngmNo; /*보증보험가입번호*/
	private String statNm;         /*가입상태        */
//	private String simId;          /*SIM ID          */
//	private String simStartDt;     /*시작일자        */
//	private String simEndDt;       /*종료일자        */

	// 기기변경이력
	private String applStrtDttm;   /*기변일시    */
	private String dvcChgTpNm;     /*기변유형    */
	private String dvcChgRsnNm;    /*기변사유    */
	private String modelNm;        /*모델명      */
	private String modelId;        /*모델ID      */
	private String intmSrlNo;      /*단말일련번호*/
//	private String instMnthCnt;    /*할부개월    */
//	private String enggMnthCnt;    /*약정개월    */
	private String outAmnt;        /*출고가      */
	private String instAmnt;       /*할부원금    */
//	private String grntInsrMngmNo; /*보증보험번호*/
//	private String salePlcyNm;     /*판매정책    */

	// 신청정보
//	private String cstmrType;             /*고객구분                          */
	private String serviceType;           /*서비스구분                        */
	private String socCode;               /*요금제                            */
//	private String operType;              /*업무구분                          */
//	private String cstmrName;             /*고객명                            */
	private String cstmrNativeRrn1;       /*주민번호1                         */
	private String cstmrNativeRrn2;       /*주민번호2                         */
	private String cstmrForeignerPn;      /*여권번호                          */
	private String cstmrForeignerSdate;   /*체류기간1                         */
	private String cstmrForeignerEdate;   /*체류기간2                         */
	private String cstmrJuridicalCname;   /*법인명                            */
	private String cstmrJuridicalRrn1;    /*법인번호1                         */
	private String cstmrJuridicalRrn2;    /*법인번호2                         */
	private String cstmrJuridicalNumber1; /*사업자등록번호1 (법인)            */
	private String cstmrJuridicalNumber2; /*사업자등록번호2 (법인)            */
	private String cstmrJuridicalNumber3; /*사업자등록번호3 (법인)            */
	private String cstmrPrivateCname;     /*상호명                            */
	private String cstmrPrivateNumber1;   /*사업자등록번호1 (개인)            */
	private String cstmrPrivateNumber2;   /*사업자등록번호2 (개인)            */
	private String cstmrPrivateNumber3;   /*사업자등록번호3 (개인)            */
	private String cstmrMail1;            /*메일1                             */
	private String cstmrMail2;            /*메일2                             */
	private String cstmrMail3;            /*메일3                             */
	private String cstmrMailReceiveFlag;  /*메일수신여부                      */
	private String cstmrAddr;             /*주소                              */
	private String cstmrPost;             /*우편번호                          */
	private String cstmrAddrDtl;          /*상세주소                          */
	private String cstmrBillSendCode;     /*명세서수신유형                    */
	private String cstmrTelFn;            /*전화번호1                         */
	private String cstmrTelMn;            /*전화번호2                         */
	private String cstmrTelRn;            /*전화번호3                         */
	private String cstmrMobileFn;         /*휴대폰1                           */
	private String cstmrMobileMn;         /*휴대폰2                           */
	private String cstmrMobileRn;         /*휴대폰3                           */
//	private String reqInDay;              /*신청일자                          */
	private String onlineAuthType;        /*본인인증방식                      */
	private String onlineAuthInfo;        /*본인인증정보                      */
	private String pstate;                /*신청취소                          */
	private String requestStateCode;      /*개통진행상태                      */
	private String tbCd;                  /*택배사                            */
	private String dlvryNo;               /*송장번호                          */
//	private String onOffType;             /*모집경로                          */
//	private String resNo;                 /*예약번호                          */
	private String managerCode;           /*매니저 코드                       */
	private String dlvryName;             /*받으시는 분                       */
//	private String cloneBase;             /*고객상동                          */
	private String dlvryTelFn;            /*배송정보_전화번호1                */
	private String dlvryTelMn;            /*배송정보_전화번호2                */
	private String dlvryTelRn;            /*배송정보_전화번호3                */
	private String dlvryMobileFn;         /*배송정보_휴대폰1                  */
	private String dlvryMobileMn;         /*배송정보_휴대폰2                  */
	private String dlvryMobileRn;         /*배송정보_휴대폰3                  */
	private String dlvryAddr;             /*배송정보_주소                     */
	private String dlvryPost;             /*배송정보_우편번호                 */
	private String dlvryAddrDtl;          /*배송정보_상세주소                 */
	private String dlvryMemo;             /*배송정보_메모                     */
	private String reqPayType;            /*요금납부방법                      */
	private String reqBank;               /*은행명                            */
//	private String reqAccountNumber;      /*계좌번호                          */
	private String reqCardCompany;        /*카드사                            */
	private String reqCardNo1;            /*카드번호1                         */
	private String reqCardNo2;            /*카드번호2                         */
	private String reqCardNo3;            /*카드번호3                         */
	private String reqCardNo4;            /*카드번호4                         */
	private String reqCardMm;             /*카드유효일자(월)                  */
	private String reqCardYy;             /*카드유효일자(년)                  */
	private String reqAccountName;        /*예금주 명                         */
	private String othersPaymentAg;       /*타인납부여부                      */
	private String reqAccountRrn1;        /*예금주 주민번호1                  */
	private String reqAccountRrn2;        /*예금주 주민번호2                  */
	private String reqCardName;           /*카드명의자                        */
	private String othersPaymentAgClone;  /*타인납부여부(카드)                */
	private String reqCardRrn1;           /*카드주 주민번호1                  */
	private String reqCardRrn2;           /*카드주 주민번호2                  */
	private String reqAcType;             /*충전방법                          */
	private String reqAc01Balance;        /*충전잔액                          */
	private String reqAc02Day;            /*충전일                            */
	private String reqAcAmount;           /*충전금액                          */
	private String reqBuyType;            /*구매유형                          */
	private String reqModelName;          /*휴대폰모델                        */
	private String reqModelColor;         /*휴대폰색상                        */
	private String reqWantNumber;         /*가입희망번호                      */
	private String reqWantNumber2;        /*가입희망번호2                     */
	private String reqWantNumber3;        /*가입희망번호3                     */
	private String reqGuideFlag;          /*번호연결서비스여부                */
	private String reqGuideFn;            /*번호연결_전화번호1                */
	private String reqGuideMn;            /*번호연결_전화번호2                */
	private String reqGuideRn;            /*번호연결_전화번호3                */
	private String reqWireType1;          /*무선데이터이용타입(이용)          */
	private String reqWireType2;          /*무선데이터이용타입(차단)          */
	private String reqWireType3;          /*무선데이터이용타입(데이터로밍차단)*/
	private String additionKey;           /*부가서비스                        */
	private String additionName;          /*부가서비스명                      */
	private String trmnlInfo;             /*단말정보                          */
	private String moveMobileFn;          /*번호이동_전화번호1                */
	private String moveMobileMn;          /*번호이동_전화번호2                */
	private String moveMobileRn;          /*번호이동_전화번호3                */
//	private String moveCompany;           /*변경전 통신사                     */
	private String moveThismonthPayType;  /*번호이동_이달 사용요금납부방법    */
	private String moveAllotmentStat;     /*번호이동_휴대폰 할부금            */
	private String moveRefundAgreeFlag;   /*미환급액 요금상계 여부            */
	private String moveAuthType;          /*인증유형                          */
	private String moveAuthNumber;	      /*인증번호                          */
//	private String minorAgentName;        /*대리인성명                        */
//	private String minorAgentRelation;    /*관계                              */
	private String minorAgentRrn1;        /*대리인 주민번호1                  */
	private String minorAgentRrn2;        /*대리인 주민번호2                  */
	private String minorAgentTelFn;       /*대리인 연락처1                    */
	private String minorAgentTelMn;       /*대리인 연락처2                    */
	private String minorAgentTelRn;       /*대리인 연락처3                    */
	private String clausePriCollectFlag;  /*개인정보 수집동의                 */
	private String clausePriOfferFlag;    /*개인정보 제공동의                 */
	private String clauseEssCollectFlag;  /*고유식별정보 수집이용제공동의     */
	private String clausePriTrustFlag;    /*개인정보 위탁동의                 */
	private String clausePriAdFlag;       /*개인정보 광고전송동의             */
	private String clauseConfidenceFlag;  /*신용정보동의                      */
	private String nwBlckAgrmYn;          /*네트워크 차단동의                 */
//	private String appBlckAgrmYn;         /*유해정보차단APP 설치동의          */
//	private String appNm;                 /*유해정보차단APP명                 */
	private String clauseRentalService;   /*렌탈서비스 이용 동의              */
	private String clauseRentalModelCp;   /*단말배상금 안내사항 동의          */
	private String clauseRentalModelCpPr; /*단말배상금(부분파손) 안내사항 동의*/
//	private String rentalBaseAmt;         /*렌탈기본료                        */
//	private String rentalBaseDcAmt;       /*렌탈료할인                        */
//	private String rentalModelCpAmt;      /*단말금액                          */
	private String reqUsimSn;             /*유심번호                          */
	private String reqPhoneSn;            /*단말기일련번호                    */
	private String spcCode;               /*특별판매코드                      */
	private String requestMemo;           /*메모                              */
	private String plcyCd;                /*판매정책코드                      */
	private String plcyNm;                /*판매정책명                        */
	private String blDcType;              /*할인유형                          */
	private String agentCode;             /**/
//	private String requestKey;            /**/
//	private String resNo;                 /**/
//	private String scanId;                /**/
	
	// 2017-06-19 이관여부 추가
	private String trnsYn;
	
	// 2017-06-19 이관여부 추가
	private String canChk;
	
	private String insrNm;
	private String insrNm1;
	private String insrNm2;
	
	//2020.02.21 포인트사용금액 추가
	private String usePointAmt;
	
	public String getRequestKey() {
		return requestKey;
	}
	public String getGrntInsrMngmNo() {
		return grntInsrMngmNo;
	}
	public void setGrntInsrMngmNo(String grntInsrMngmNo) {
		this.grntInsrMngmNo = grntInsrMngmNo;
	}
	public String getGrntInsrStatNm() {
		return grntInsrStatNm;
	}
	public void setGrntInsrStatNm(String grntInsrStatNm) {
		this.grntInsrStatNm = grntInsrStatNm;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSocCode() {
		return socCode;
	}
	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}
	public String getCstmrNativeRrn1() {
		return cstmrNativeRrn1;
	}
	public void setCstmrNativeRrn1(String cstmrNativeRrn1) {
		this.cstmrNativeRrn1 = cstmrNativeRrn1;
	}
	public String getCstmrNativeRrn2() {
		return cstmrNativeRrn2;
	}
	public void setCstmrNativeRrn2(String cstmrNativeRrn2) {
		this.cstmrNativeRrn2 = cstmrNativeRrn2;
	}
	public String getCstmrForeignerPn() {
		return cstmrForeignerPn;
	}
	public void setCstmrForeignerPn(String cstmrForeignerPn) {
		this.cstmrForeignerPn = cstmrForeignerPn;
	}
	public String getCstmrForeignerSdate() {
		return cstmrForeignerSdate;
	}
	public void setCstmrForeignerSdate(String cstmrForeignerSdate) {
		this.cstmrForeignerSdate = cstmrForeignerSdate;
	}
	public String getCstmrForeignerEdate() {
		return cstmrForeignerEdate;
	}
	public void setCstmrForeignerEdate(String cstmrForeignerEdate) {
		this.cstmrForeignerEdate = cstmrForeignerEdate;
	}
	public String getCstmrJuridicalCname() {
		return cstmrJuridicalCname;
	}
	public void setCstmrJuridicalCname(String cstmrJuridicalCname) {
		this.cstmrJuridicalCname = cstmrJuridicalCname;
	}
	public String getCstmrJuridicalRrn1() {
		return cstmrJuridicalRrn1;
	}
	public void setCstmrJuridicalRrn1(String cstmrJuridicalRrn1) {
		this.cstmrJuridicalRrn1 = cstmrJuridicalRrn1;
	}
	public String getCstmrJuridicalRrn2() {
		return cstmrJuridicalRrn2;
	}
	public void setCstmrJuridicalRrn2(String cstmrJuridicalRrn2) {
		this.cstmrJuridicalRrn2 = cstmrJuridicalRrn2;
	}
	public String getCstmrJuridicalNumber1() {
		return cstmrJuridicalNumber1;
	}
	public void setCstmrJuridicalNumber1(String cstmrJuridicalNumber1) {
		this.cstmrJuridicalNumber1 = cstmrJuridicalNumber1;
	}
	public String getCstmrJuridicalNumber2() {
		return cstmrJuridicalNumber2;
	}
	public void setCstmrJuridicalNumber2(String cstmrJuridicalNumber2) {
		this.cstmrJuridicalNumber2 = cstmrJuridicalNumber2;
	}
	public String getCstmrJuridicalNumber3() {
		return cstmrJuridicalNumber3;
	}
	public void setCstmrJuridicalNumber3(String cstmrJuridicalNumber3) {
		this.cstmrJuridicalNumber3 = cstmrJuridicalNumber3;
	}
	public String getCstmrPrivateCname() {
		return cstmrPrivateCname;
	}
	public void setCstmrPrivateCname(String cstmrPrivateCname) {
		this.cstmrPrivateCname = cstmrPrivateCname;
	}
	public String getCstmrPrivateNumber1() {
		return cstmrPrivateNumber1;
	}
	public void setCstmrPrivateNumber1(String cstmrPrivateNumber1) {
		this.cstmrPrivateNumber1 = cstmrPrivateNumber1;
	}
	public String getCstmrPrivateNumber2() {
		return cstmrPrivateNumber2;
	}
	public void setCstmrPrivateNumber2(String cstmrPrivateNumber2) {
		this.cstmrPrivateNumber2 = cstmrPrivateNumber2;
	}
	public String getCstmrPrivateNumber3() {
		return cstmrPrivateNumber3;
	}
	public void setCstmrPrivateNumber3(String cstmrPrivateNumber3) {
		this.cstmrPrivateNumber3 = cstmrPrivateNumber3;
	}
	public String getCstmrMail1() {
		return cstmrMail1;
	}
	public void setCstmrMail1(String cstmrMail1) {
		this.cstmrMail1 = cstmrMail1;
	}
	public String getCstmrMail2() {
		return cstmrMail2;
	}
	public void setCstmrMail2(String cstmrMail2) {
		this.cstmrMail2 = cstmrMail2;
	}
	public String getCstmrMail3() {
		return cstmrMail3;
	}
	public void setCstmrMail3(String cstmrMail3) {
		this.cstmrMail3 = cstmrMail3;
	}
	public String getCstmrMailReceiveFlag() {
		return cstmrMailReceiveFlag;
	}
	public void setCstmrMailReceiveFlag(String cstmrMailReceiveFlag) {
		this.cstmrMailReceiveFlag = cstmrMailReceiveFlag;
	}
	public String getCstmrAddr() {
		return cstmrAddr;
	}
	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}
	public String getCstmrPost() {
		return cstmrPost;
	}
	public void setCstmrPost(String cstmrPost) {
		this.cstmrPost = cstmrPost;
	}
	public String getCstmrAddrDtl() {
		return cstmrAddrDtl;
	}
	public void setCstmrAddrDtl(String cstmrAddrDtl) {
		this.cstmrAddrDtl = cstmrAddrDtl;
	}
	public String getCstmrBillSendCode() {
		return cstmrBillSendCode;
	}
	public void setCstmrBillSendCode(String cstmrBillSendCode) {
		this.cstmrBillSendCode = cstmrBillSendCode;
	}
	public String getCstmrTelFn() {
		return cstmrTelFn;
	}
	public void setCstmrTelFn(String cstmrTelFn) {
		this.cstmrTelFn = cstmrTelFn;
	}
	public String getCstmrTelMn() {
		return cstmrTelMn;
	}
	public void setCstmrTelMn(String cstmrTelMn) {
		this.cstmrTelMn = cstmrTelMn;
	}
	public String getCstmrTelRn() {
		return cstmrTelRn;
	}
	public void setCstmrTelRn(String cstmrTelRn) {
		this.cstmrTelRn = cstmrTelRn;
	}
	public String getCstmrMobileFn() {
		return cstmrMobileFn;
	}
	public void setCstmrMobileFn(String cstmrMobileFn) {
		this.cstmrMobileFn = cstmrMobileFn;
	}
	public String getCstmrMobileMn() {
		return cstmrMobileMn;
	}
	public void setCstmrMobileMn(String cstmrMobileMn) {
		this.cstmrMobileMn = cstmrMobileMn;
	}
	public String getCstmrMobileRn() {
		return cstmrMobileRn;
	}
	public void setCstmrMobileRn(String cstmrMobileRn) {
		this.cstmrMobileRn = cstmrMobileRn;
	}
	public String getOnlineAuthType() {
		return onlineAuthType;
	}
	public void setOnlineAuthType(String onlineAuthType) {
		this.onlineAuthType = onlineAuthType;
	}
	public String getOnlineAuthInfo() {
		return onlineAuthInfo;
	}
	public void setOnlineAuthInfo(String onlineAuthInfo) {
		this.onlineAuthInfo = onlineAuthInfo;
	}
	public String getPstate() {
		return pstate;
	}
	public void setPstate(String pstate) {
		this.pstate = pstate;
	}
	public String getRequestStateCode() {
		return requestStateCode;
	}
	public void setRequestStateCode(String requestStateCode) {
		this.requestStateCode = requestStateCode;
	}
	public String getTbCd() {
		return tbCd;
	}
	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}
	public String getDlvryNo() {
		return dlvryNo;
	}
	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getDlvryName() {
		return dlvryName;
	}
	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}
/*	public String getCloneBase() {
		return cloneBase;
	}
	public void setCloneBase(String cloneBase) {
		this.cloneBase = cloneBase;
	}*/
	public String getDlvryTelFn() {
		return dlvryTelFn;
	}
	public void setDlvryTelFn(String dlvryTelFn) {
		this.dlvryTelFn = dlvryTelFn;
	}
	public String getDlvryTelMn() {
		return dlvryTelMn;
	}
	public void setDlvryTelMn(String dlvryTelMn) {
		this.dlvryTelMn = dlvryTelMn;
	}
	public String getDlvryTelRn() {
		return dlvryTelRn;
	}
	public void setDlvryTelRn(String dlvryTelRn) {
		this.dlvryTelRn = dlvryTelRn;
	}
	public String getDlvryMobileFn() {
		return dlvryMobileFn;
	}
	public void setDlvryMobileFn(String dlvryMobileFn) {
		this.dlvryMobileFn = dlvryMobileFn;
	}
	public String getDlvryMobileMn() {
		return dlvryMobileMn;
	}
	public void setDlvryMobileMn(String dlvryMobileMn) {
		this.dlvryMobileMn = dlvryMobileMn;
	}
	public String getDlvryMobileRn() {
		return dlvryMobileRn;
	}
	public void setDlvryMobileRn(String dlvryMobileRn) {
		this.dlvryMobileRn = dlvryMobileRn;
	}
	public String getDlvryAddr() {
		return dlvryAddr;
	}
	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}
	public String getDlvryPost() {
		return dlvryPost;
	}
	public void setDlvryPost(String dlvryPost) {
		this.dlvryPost = dlvryPost;
	}
	public String getDlvryAddrDtl() {
		return dlvryAddrDtl;
	}
	public void setDlvryAddrDtl(String dlvryAddrDtl) {
		this.dlvryAddrDtl = dlvryAddrDtl;
	}
	public String getDlvryMemo() {
		return dlvryMemo;
	}
	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}
	public String getReqPayType() {
		return reqPayType;
	}
	public void setReqPayType(String reqPayType) {
		this.reqPayType = reqPayType;
	}
	public String getReqBank() {
		return reqBank;
	}
	public void setReqBank(String reqBank) {
		this.reqBank = reqBank;
	}
	public String getReqCardCompany() {
		return reqCardCompany;
	}
	public void setReqCardCompany(String reqCardCompany) {
		this.reqCardCompany = reqCardCompany;
	}
	public String getReqCardNo1() {
		return reqCardNo1;
	}
	public void setReqCardNo1(String reqCardNo1) {
		this.reqCardNo1 = reqCardNo1;
	}
	public String getReqCardNo2() {
		return reqCardNo2;
	}
	public void setReqCardNo2(String reqCardNo2) {
		this.reqCardNo2 = reqCardNo2;
	}
	public String getReqCardNo3() {
		return reqCardNo3;
	}
	public void setReqCardNo3(String reqCardNo3) {
		this.reqCardNo3 = reqCardNo3;
	}
	public String getReqCardNo4() {
		return reqCardNo4;
	}
	public void setReqCardNo4(String reqCardNo4) {
		this.reqCardNo4 = reqCardNo4;
	}
	public String getReqCardMm() {
		return reqCardMm;
	}
	public void setReqCardMm(String reqCardMm) {
		this.reqCardMm = reqCardMm;
	}
	public String getReqCardYy() {
		return reqCardYy;
	}
	public void setReqCardYy(String reqCardYy) {
		this.reqCardYy = reqCardYy;
	}
	public String getReqAccountName() {
		return reqAccountName;
	}
	public void setReqAccountName(String reqAccountName) {
		this.reqAccountName = reqAccountName;
	}
	public String getOthersPaymentAg() {
		return othersPaymentAg;
	}
	public void setOthersPaymentAg(String othersPaymentAg) {
		this.othersPaymentAg = othersPaymentAg;
	}
	public String getReqAccountRrn1() {
		return reqAccountRrn1;
	}
	public void setReqAccountRrn1(String reqAccountRrn1) {
		this.reqAccountRrn1 = reqAccountRrn1;
	}
	public String getReqAccountRrn2() {
		return reqAccountRrn2;
	}
	public void setReqAccountRrn2(String reqAccountRrn2) {
		this.reqAccountRrn2 = reqAccountRrn2;
	}
	public String getReqCardName() {
		return reqCardName;
	}
	public void setReqCardName(String reqCardName) {
		this.reqCardName = reqCardName;
	}
	public String getOthersPaymentAgClone() {
		return othersPaymentAgClone;
	}
	public void setOthersPaymentAgClone(String othersPaymentAgClone) {
		this.othersPaymentAgClone = othersPaymentAgClone;
	}
	public String getReqCardRrn1() {
		return reqCardRrn1;
	}
	public void setReqCardRrn1(String reqCardRrn1) {
		this.reqCardRrn1 = reqCardRrn1;
	}
	public String getReqCardRrn2() {
		return reqCardRrn2;
	}
	public void setReqCardRrn2(String reqCardRrn2) {
		this.reqCardRrn2 = reqCardRrn2;
	}
	public String getReqAcType() {
		return reqAcType;
	}
	public void setReqAcType(String reqAcType) {
		this.reqAcType = reqAcType;
	}
	public String getReqAc01Balance() {
		return reqAc01Balance;
	}
	public void setReqAc01Balance(String reqAc01Balance) {
		this.reqAc01Balance = reqAc01Balance;
	}
	public String getReqAc02Day() {
		return reqAc02Day;
	}
	public void setReqAc02Day(String reqAc02Day) {
		this.reqAc02Day = reqAc02Day;
	}
	public String getReqAcAmount() {
		return reqAcAmount;
	}
	public void setReqAcAmount(String reqAcAmount) {
		this.reqAcAmount = reqAcAmount;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getReqModelName() {
		return reqModelName;
	}
	public void setReqModelName(String reqModelName) {
		this.reqModelName = reqModelName;
	}
	public String getReqModelColor() {
		return reqModelColor;
	}
	public void setReqModelColor(String reqModelColor) {
		this.reqModelColor = reqModelColor;
	}
	public String getReqWantNumber() {
		return reqWantNumber;
	}
	public void setReqWantNumber(String reqWantNumber) {
		this.reqWantNumber = reqWantNumber;
	}
	public String getReqWantNumber2() {
		return reqWantNumber2;
	}
	public void setReqWantNumber2(String reqWantNumber2) {
		this.reqWantNumber2 = reqWantNumber2;
	}
	public String getReqWantNumber3() {
		return reqWantNumber3;
	}
	public void setReqWantNumber3(String reqWantNumber3) {
		this.reqWantNumber3 = reqWantNumber3;
	}
	public String getReqGuideFlag() {
		return reqGuideFlag;
	}
	public void setReqGuideFlag(String reqGuideFlag) {
		this.reqGuideFlag = reqGuideFlag;
	}
	public String getReqGuideFn() {
		return reqGuideFn;
	}
	public void setReqGuideFn(String reqGuideFn) {
		this.reqGuideFn = reqGuideFn;
	}
	public String getReqGuideMn() {
		return reqGuideMn;
	}
	public void setReqGuideMn(String reqGuideMn) {
		this.reqGuideMn = reqGuideMn;
	}
	public String getReqGuideRn() {
		return reqGuideRn;
	}
	public void setReqGuideRn(String reqGuideRn) {
		this.reqGuideRn = reqGuideRn;
	}
	public String getReqWireType1() {
		return reqWireType1;
	}
	public void setReqWireType1(String reqWireType1) {
		this.reqWireType1 = reqWireType1;
	}
	public String getReqWireType2() {
		return reqWireType2;
	}
	public void setReqWireType2(String reqWireType2) {
		this.reqWireType2 = reqWireType2;
	}
	public String getReqWireType3() {
		return reqWireType3;
	}
	public void setReqWireType3(String reqWireType3) {
		this.reqWireType3 = reqWireType3;
	}
	public String getAdditionKey() {
		return additionKey;
	}
	public void setAdditionKey(String additionKey) {
		this.additionKey = additionKey;
	}
	public String getAdditionName() {
		return additionName;
	}
	public void setAdditionName(String additionName) {
		this.additionName = additionName;
	}
	public String getTrmnlInfo() {
		return trmnlInfo;
	}
	public void setTrmnlInfo(String trmnlInfo) {
		this.trmnlInfo = trmnlInfo;
	}
	public String getMoveMobileFn() {
		return moveMobileFn;
	}
	public void setMoveMobileFn(String moveMobileFn) {
		this.moveMobileFn = moveMobileFn;
	}
	public String getMoveMobileMn() {
		return moveMobileMn;
	}
	public void setMoveMobileMn(String moveMobileMn) {
		this.moveMobileMn = moveMobileMn;
	}
	public String getMoveMobileRn() {
		return moveMobileRn;
	}
	public void setMoveMobileRn(String moveMobileRn) {
		this.moveMobileRn = moveMobileRn;
	}
	public String getMoveThismonthPayType() {
		return moveThismonthPayType;
	}
	public void setMoveThismonthPayType(String moveThismonthPayType) {
		this.moveThismonthPayType = moveThismonthPayType;
	}
	public String getMoveAllotmentStat() {
		return moveAllotmentStat;
	}
	public void setMoveAllotmentStat(String moveAllotmentStat) {
		this.moveAllotmentStat = moveAllotmentStat;
	}
	public String getMoveRefundAgreeFlag() {
		return moveRefundAgreeFlag;
	}
	public void setMoveRefundAgreeFlag(String moveRefundAgreeFlag) {
		this.moveRefundAgreeFlag = moveRefundAgreeFlag;
	}
	public String getMoveAuthType() {
		return moveAuthType;
	}
	public void setMoveAuthType(String moveAuthType) {
		this.moveAuthType = moveAuthType;
	}	
	public String getMoveAuthNumber(){
		return moveAuthNumber;
	}
	public void setMoveAuthNumber(String moveAuthNumber){
		this.moveAuthNumber = moveAuthNumber;
	}	
	public String getMinorAgentRrn1() {
		return minorAgentRrn1;
	}
	public void setMinorAgentRrn1(String minorAgentRrn1) {
		this.minorAgentRrn1 = minorAgentRrn1;
	}
	public String getMinorAgentRrn2() {
		return minorAgentRrn2;
	}
	public void setMinorAgentRrn2(String minorAgentRrn2) {
		this.minorAgentRrn2 = minorAgentRrn2;
	}
	public String getMinorAgentTelFn() {
		return minorAgentTelFn;
	}
	public void setMinorAgentTelFn(String minorAgentTelFn) {
		this.minorAgentTelFn = minorAgentTelFn;
	}
	public String getMinorAgentTelMn() {
		return minorAgentTelMn;
	}
	public void setMinorAgentTelMn(String minorAgentTelMn) {
		this.minorAgentTelMn = minorAgentTelMn;
	}
	public String getMinorAgentTelRn() {
		return minorAgentTelRn;
	}
	public void setMinorAgentTelRn(String minorAgentTelRn) {
		this.minorAgentTelRn = minorAgentTelRn;
	}
	public String getClausePriCollectFlag() {
		return clausePriCollectFlag;
	}
	public void setClausePriCollectFlag(String clausePriCollectFlag) {
		this.clausePriCollectFlag = clausePriCollectFlag;
	}
	public String getClausePriOfferFlag() {
		return clausePriOfferFlag;
	}
	public void setClausePriOfferFlag(String clausePriOfferFlag) {
		this.clausePriOfferFlag = clausePriOfferFlag;
	}
	public String getClauseEssCollectFlag() {
		return clauseEssCollectFlag;
	}
	public void setClauseEssCollectFlag(String clauseEssCollectFlag) {
		this.clauseEssCollectFlag = clauseEssCollectFlag;
	}
	public String getClausePriTrustFlag() {
		return clausePriTrustFlag;
	}
	public void setClausePriTrustFlag(String clausePriTrustFlag) {
		this.clausePriTrustFlag = clausePriTrustFlag;
	}
	public String getClausePriAdFlag() {
		return clausePriAdFlag;
	}
	public void setClausePriAdFlag(String clausePriAdFlag) {
		this.clausePriAdFlag = clausePriAdFlag;
	}
	public String getClauseConfidenceFlag() {
		return clauseConfidenceFlag;
	}
	public void setClauseConfidenceFlag(String clauseConfidenceFlag) {
		this.clauseConfidenceFlag = clauseConfidenceFlag;
	}
	public String getNwBlckAgrmYn() {
		return nwBlckAgrmYn;
	}
	public void setNwBlckAgrmYn(String nwBlckAgrmYn) {
		this.nwBlckAgrmYn = nwBlckAgrmYn;
	}
	public String getClauseRentalService() {
		return clauseRentalService;
	}
	public void setClauseRentalService(String clauseRentalService) {
		this.clauseRentalService = clauseRentalService;
	}
	public String getClauseRentalModelCp() {
		return clauseRentalModelCp;
	}
	public void setClauseRentalModelCp(String clauseRentalModelCp) {
		this.clauseRentalModelCp = clauseRentalModelCp;
	}
	public String getClauseRentalModelCpPr() {
		return clauseRentalModelCpPr;
	}
	public void setClauseRentalModelCpPr(String clauseRentalModelCpPr) {
		this.clauseRentalModelCpPr = clauseRentalModelCpPr;
	}
	public String getReqUsimSn() {
		return reqUsimSn;
	}
	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}
	public String getReqPhoneSn() {
		return reqPhoneSn;
	}
	public void setReqPhoneSn(String reqPhoneSn) {
		this.reqPhoneSn = reqPhoneSn;
	}
	public String getSpcCode() {
		return spcCode;
	}
	public void setSpcCode(String spcCode) {
		this.spcCode = spcCode;
	}
	public String getRequestMemo() {
		return requestMemo;
	}
	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
	}
	public String getPlcyCd() {
		return plcyCd;
	}
	public void setPlcyCd(String plcyCd) {
		this.plcyCd = plcyCd;
	}
	public String getPlcyNm() {
		return plcyNm;
	}
	public void setPlcyNm(String plcyNm) {
		this.plcyNm = plcyNm;
	}
	public String getBlDcType() {
		return blDcType;
	}
	public void setBlDcType(String blDcType) {
		this.blDcType = blDcType;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getEvntChangeDate() {
		return evntChangeDate;
	}
	public void setEvntChangeDate(String evntChangeDate) {
		this.evntChangeDate = evntChangeDate;
	}
	public String getSubLinkName() {
		return subLinkName;
	}
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	public String getSlsPrsnId() {
		return slsPrsnId;
	}
	public void setSlsPrsnId(String slsPrsnId) {
		this.slsPrsnId = slsPrsnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getEvntStr() {
		return evntStr;
	}
	public void setEvntStr(String evntStr) {
		this.evntStr = evntStr;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getWifiMacId() {
		return wifiMacId;
	}
	public void setWifiMacId(String wifiMacId) {
		this.wifiMacId = wifiMacId;
	}
	public String getSocNm() {
		return socNm;
	}
	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}
	public String getServiceTypeNm() {
		return serviceTypeNm;
	}
	public void setServiceTypeNm(String serviceTypeNm) {
		this.serviceTypeNm = serviceTypeNm;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBlBillingMethod() {
		return blBillingMethod;
	}
	public void setBlBillingMethod(String blBillingMethod) {
		this.blBillingMethod = blBillingMethod;
	}
	public String getBlSpclBanCode() {
		return blSpclBanCode;
	}
	public void setBlSpclBanCode(String blSpclBanCode) {
		this.blSpclBanCode = blSpclBanCode;
	}
	public String getColDelinqStatus() {
		return colDelinqStatus;
	}
	public void setColDelinqStatus(String colDelinqStatus) {
		this.colDelinqStatus = colDelinqStatus;
	}
	public String getBankAcctHolderId() {
		return bankAcctHolderId;
	}
	public void setBankAcctHolderId(String bankAcctHolderId) {
		this.bankAcctHolderId = bankAcctHolderId;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getBanLinkName() {
		return banLinkName;
	}
	public void setBanLinkName(String banLinkName) {
		this.banLinkName = banLinkName;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getBillItemNm() {
		return billItemNm;
	}
	public void setBillItemNm(String billItemNm) {
		this.billItemNm = billItemNm;
	}
	public String getInvAmnt() {
		return invAmnt;
	}
	public void setInvAmnt(String invAmnt) {
		this.invAmnt = invAmnt;
	}
	public String getAdjAmnt() {
		return adjAmnt;
	}
	public void setAdjAmnt(String adjAmnt) {
		this.adjAmnt = adjAmnt;
	}
	public String getPymnMthdNm() {
		return pymnMthdNm;
	}
	public void setPymnMthdNm(String pymnMthdNm) {
		this.pymnMthdNm = pymnMthdNm;
	}
	public String getBlpymDate() {
		return blpymDate;
	}
	public void setBlpymDate(String blpymDate) {
		this.blpymDate = blpymDate;
	}
	public String getPymAmnt() {
		return pymAmnt;
	}
	public void setPymAmnt(String pymAmnt) {
		this.pymAmnt = pymAmnt;
	}
	public String getAgentTelNum() {
		return agentTelNum;
	}
	public void setAgentTelNum(String agentTelNum) {
		this.agentTelNum = agentTelNum;
	}
	public String getAgentRelNm() {
		return agentRelNm;
	}
	public void setAgentRelNm(String agentRelNm) {
		this.agentRelNm = agentRelNm;
	}
	public String getInstCnfmNm() {
		return instCnfmNm;
	}
	public void setInstCnfmNm(String instCnfmNm) {
		this.instCnfmNm = instCnfmNm;
	}
	public String getInstCnfmDt() {
		return instCnfmDt;
	}
	public void setInstCnfmDt(String instCnfmDt) {
		this.instCnfmDt = instCnfmDt;
	}
	public String getInstCnfmId() {
		return instCnfmId;
	}
	public void setInstCnfmId(String instCnfmId) {
		this.instCnfmId = instCnfmId;	
	}
	public String getEvntTrtmDt() {
		return evntTrtmDt;
	}
	public void setEvntTrtmDt(String evntTrtmDt) {
		this.evntTrtmDt = evntTrtmDt;
	}
	public String getEvntNm() {
		return evntNm;
	}
	public void setEvntNm(String evntNm) {
		this.evntNm = evntNm;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	public String getApplStrtDttm() {
		return applStrtDttm;
	}
	public void setApplStrtDttm(String applStrtDttm) {
		this.applStrtDttm = applStrtDttm;
	}
	public String getDvcChgTpNm() {
		return dvcChgTpNm;
	}
	public void setDvcChgTpNm(String dvcChgTpNm) {
		this.dvcChgTpNm = dvcChgTpNm;
	}
	public String getDvcChgRsnNm() {
		return dvcChgRsnNm;
	}
	public void setDvcChgRsnNm(String dvcChgRsnNm) {
		this.dvcChgRsnNm = dvcChgRsnNm;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getOutAmnt() {
		return outAmnt;
	}
	public void setOutAmnt(String outAmnt) {
		this.outAmnt = outAmnt;
	}
	public String getInstAmnt() {
		return instAmnt;
	}
	public void setInstAmnt(String instAmnt) {
		this.instAmnt = instAmnt;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getReqCardRrn() {
		return reqCardRrn;
	}
	public void setReqCardRrn(String reqCardRrn) {
		this.reqCardRrn = reqCardRrn;
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
	public String getReqAccountRrn() {
		return reqAccountRrn;
	}
	public void setReqAccountRrn(String reqAccountRrn) {
		this.reqAccountRrn = reqAccountRrn;
	}
	public String getReqAccountNumber() {
		return reqAccountNumber;
	}
	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
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
	
	
	
	
	public String getAppBlckAgrmYn() {
		return appBlckAgrmYn;
	}
	public void setAppBlckAgrmYn(String appBlckAgrmYn) {
		this.appBlckAgrmYn = appBlckAgrmYn;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
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
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
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
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getMinorYn() {
		return minorYn;
	}
	public void setMinorYn(String minorYn) {
		this.minorYn = minorYn;
	}
	public String getAppInstYn() {
		return appInstYn;
	}
	public void setAppInstYn(String appInstYn) {
		this.appInstYn = appInstYn;
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
	public String getProdTypeNm() {
		return prodTypeNm;
	}
	public void setProdTypeNm(String prodTypeNm) {
		this.prodTypeNm = prodTypeNm;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}
	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}
	public String getOperTypeNm() {
		return operTypeNm;
	}
	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}
	public String getMoveCompany() {
		return moveCompany;
	}
	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}
	public String getMoveCompanyNm() {
		return moveCompanyNm;
	}
	public void setMoveCompanyNm(String moveCompanyNm) {
		this.moveCompanyNm = moveCompanyNm;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getFstRateNm() {
		return fstRateNm;
	}
	public void setFstRateNm(String fstRateNm) {
		this.fstRateNm = fstRateNm;
	}
	public String getLstRateNm() {
		return lstRateNm;
	}
	public void setLstRateNm(String lstRateNm) {
		this.lstRateNm = lstRateNm;
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
	public String getFstModelNm() {
		return fstModelNm;
	}
	public void setFstModelNm(String fstModelNm) {
		this.fstModelNm = fstModelNm;
	}
	public String getFstModelSrlNum() {
		return fstModelSrlNum;
	}
	public void setFstModelSrlNum(String fstModelSrlNum) {
		this.fstModelSrlNum = fstModelSrlNum;
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
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getTmntDt() {
		return tmntDt;
	}
	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
	}
	public String getMinorAgentName() {
		return minorAgentName;
	}
	public void setMinorAgentName(String minorAgentName) {
		this.minorAgentName = minorAgentName;
	}
	public String getMinorAgentRelation() {
		return minorAgentRelation;
	}
	public void setMinorAgentRelation(String minorAgentRelation) {
		this.minorAgentRelation = minorAgentRelation;
	}
	public String getMinorAgentTel() {
		return minorAgentTel;
	}
	public void setMinorAgentTel(String minorAgentTel) {
		this.minorAgentTel = minorAgentTel;
	}
	public String getAppNm() {
		return appNm;
	}
	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}
	public String getJoinPayMthdNm() {
		return joinPayMthdNm;
	}
	public void setJoinPayMthdNm(String joinPayMthdNm) {
		this.joinPayMthdNm = joinPayMthdNm;
	}
	public String getJoinPrice() {
		return joinPrice;
	}
	public void setJoinPrice(String joinPrice) {
		this.joinPrice = joinPrice;
	}
	public String getUsimPayMthdNm() {
		return usimPayMthdNm;
	}
	public void setUsimPayMthdNm(String usimPayMthdNm) {
		this.usimPayMthdNm = usimPayMthdNm;
	}
	public String getUsimPrice() {
		return usimPrice;
	}
	public void setUsimPrice(String usimPrice) {
		this.usimPrice = usimPrice;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getInstOrginAmnt() {
		return instOrginAmnt;
	}
	public void setInstOrginAmnt(String instOrginAmnt) {
		this.instOrginAmnt = instOrginAmnt;
	}
	public String getSprtTpNm() {
		return sprtTpNm;
	}
	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
	}
	public String getSimId() {
		return simId;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public String getSimEnggMnthCnt() {
		return simEnggMnthCnt;
	}
	public void setSimEnggMnthCnt(String simEnggMnthCnt) {
		this.simEnggMnthCnt = simEnggMnthCnt;
	}
	public String getSimStartDt() {
		return simStartDt;
	}
	public void setSimStartDt(String simStartDt) {
		this.simStartDt = simStartDt;
	}
	public String getSimEndDt() {
		return simEndDt;
	}
	public void setSimEndDt(String simEndDt) {
		this.simEndDt = simEndDt;
	}
	public String getSimStatNm() {
		return simStatNm;
	}
	public void setSimStatNm(String simStatNm) {
		this.simStatNm = simStatNm;
	}
	public String getRentalBaseAmt() {
		return rentalBaseAmt;
	}
	public void setRentalBaseAmt(String rentalBaseAmt) {
		this.rentalBaseAmt = rentalBaseAmt;
	}
	public String getRentalBaseDcAmt() {
		return rentalBaseDcAmt;
	}
	public void setRentalBaseDcAmt(String rentalBaseDcAmt) {
		this.rentalBaseDcAmt = rentalBaseDcAmt;
	}
	public String getRentalModelCpAmt() {
		return rentalModelCpAmt;
	}
	public void setRentalModelCpAmt(String rentalModelCpAmt) {
		this.rentalModelCpAmt = rentalModelCpAmt;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getLstDataType() {
		return lstDataType;
	}
	public void setLstDataType(String lstDataType) {
		this.lstDataType = lstDataType;
	}
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	public String getUnpdIndNm() {
		return unpdIndNm;
	}
	public void setUnpdIndNm(String unpdIndNm) {
		this.unpdIndNm = unpdIndNm;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRealUseCustNm() {
		return realUseCustNm;
	}
	public void setRealUseCustNm(String realUseCustNm) {
		this.realUseCustNm = realUseCustNm;
	}
	public String getCustomerAdr() {
		return customerAdr;
	}
	public void setCustomerAdr(String customerAdr) {
		this.customerAdr = customerAdr;
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
	public String getTrnsYn() {
		return trnsYn;
	}
	public void setTrnsYn(String trnsYn) {
		this.trnsYn = trnsYn;
	}
	public String getCanChk() {
		return canChk;
	}
	public void setCanChk(String canChk) {
		this.canChk = canChk;
	}
   public String getInsrNm() {
      return insrNm;
   }
   public void setInsrNm(String insrNm) {
      this.insrNm = insrNm;
   }
   public String getInsrNm1() {
      return insrNm1;
   }
   public String getInsrNm2() {
      return insrNm2;
   }
   public void setInsrNm1(String insrNm1) {
      this.insrNm1 = insrNm1;
   }
   public void setInsrNm2(String insrNm2) {
      this.insrNm2 = insrNm2;
   }
	public String getUsePointAmt() {
		return usePointAmt;
	}
	public void setUsePointAmt(String usePointAmt) {
		this.usePointAmt = usePointAmt;
	}

}
