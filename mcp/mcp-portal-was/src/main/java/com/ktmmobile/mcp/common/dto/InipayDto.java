/*=================================================
 * KT Copyright
 =================================================*/
package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;

import org.apache.http.message.BasicNameValuePair;

public class InipayDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String encfield;
    private String certid;
    private String enctype;

    /**==============INIPAY 결제에 필요한 파라메터==============*/
    private String uid; // INIpay User ID (절대 수정 불가)
    private String oid; // 주문번호
    private String goodname; // 상품명
    private String currency; // 화폐단위
    private String INI_ENCTYPE; // 웹페이지 위변조용 암호화 정보
    private String INI_RN; // 웹페이지 위변조용 RN값
    private String INI_PRICE; // 가격
    private String paymethod; // 지불방법 (절대 수정 불가)
    private String encrypted; // 암호문
    private String sessionkey;  // 암호문
    private String buyername; // 구매자 명
    private String buyertel; // 구매자 연락처(휴대폰 번호 또는 유선전화번호)
    private String buyeremail; // 구매자 이메일 주소
    private String cardcode;  // 카드코드 리턴
    private String parentemail; // 보호자 이메일 주소(핸드폰 , 전화결제시에 14세 미만의 고객이 결제하면  부모 이메일로 결제 내용통보 의무, 다른결제 수단 사용시에 삭제 가능)
    private String mid; // 상점아이디
    private String price;
    private String rn;
    private String gopaymethod ; //결 제 방 법  Card 신용카드 결제 , DirectBank 실시간 은행계좌이체


    /** OUTPUT */
    private String resultCode;
    private String resultMsg;
    private String totPrice; // 거래금액
    private String CARD_Code; // 카드사 코드
    private String CARD_Num; // 카드 번호
    private String applDate; // 승인일자
    private String applTime; // 승인 시간
    private String applNum; // 승인번호
    private String cardQuota; // 카드 할부
    private String tid; // 이니시스 거래번호
    private String acctBankcode; // 실시간 계좌이체은행코드
    private String vactNum; // 입금계좌번호
    private String vactBankcode; // 입금 은행코드
    private String vactName; // 예금주 명
    private String vactInputname; // 송금자 명
    private String VACT_MOID; // 상품 주문번호
    private String VACT_Date; // 송금 일자
    private String VACT_Time; // 송금 시간
    private String cshrType; // 발급구분코드
    private String cshrResultcode; // 발급결과코드
    private String settlWayCd; //결제수단코드
    private String confirmPrice ; //부분취소 후 승인될 금액
    private String rnResultmsg;
    private String cancelreason;
    private String msg;
    private String cancelDate;
    private String cancelTime;
    private String cshrCancelnum;
    private String resultErrorCode;
    private String cardInterest; // 카드 할부정보
    private String eventCode; //카드이벤트 정보
    private String cardBankcode; //카드 발급사정보


    /** 이니페이 실행 파라메터 */
    /**MPOINT적용여부*/
    private String mpointAplyYn;
    /** MPOINT상점아이디 */
    private String mpointMid;
    /** 결제방식 */
    private String paymentType;
    /** 상품명 */
    private String petNm;
    /** 전화번호 */
    private String custTelNo;
    /** 이메일 */
    private String custEmail;
    /** 구매자명 */
    private String custNm;
    /** 암호화된 금액 */
    private String cba;
    /** 암호화키값 */
    private String enk;
    /**모바일 이니페이 returnUrl 호출여부**/
    private String returnFlag;
    /**무이자 여부**/
    private String nointerest;

    /** 이니페이 모바일용 */
    private String pReqUrl;
    private String pStatus;
    private String pRmesg1;
    private String pTid;
    private String pNoti;
    /** 이니페이 모바일용 */

    public String getEncfield() {
        return encfield;
    }
    public void setEncfield(String encfield) {
        this.encfield = encfield;
    }
    public String getCertid() {
        return certid;
    }
    public void setCertid(String certid) {
        this.certid = certid;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    public String getGoodname() {
        return goodname;
    }
    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getINI_ENCTYPE() {
        return INI_ENCTYPE;
    }
    public void setINI_ENCTYPE(String iNI_ENCTYPE) {
        INI_ENCTYPE = iNI_ENCTYPE;
    }
    public String getINI_RN() {
        return INI_RN;
    }
    public void setINI_RN(String iNI_RN) {
        INI_RN = iNI_RN;
    }
    public String getINI_PRICE() {
        return INI_PRICE;
    }
    public void setINI_PRICE(String iNI_PRICE) {
        INI_PRICE = iNI_PRICE;
    }
    public String getPaymethod() {
        return paymethod;
    }
    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }
    public String getEncrypted() {
        return encrypted;
    }
    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
    public String getSessionkey() {
        return sessionkey;
    }
    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }
    public String getBuyername() {
        return buyername;
    }
    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }
    public String getBuyertel() {
        return buyertel;
    }
    public void setBuyertel(String buyertel) {
        this.buyertel = buyertel;
    }
    public String getBuyeremail() {
        return buyeremail;
    }
    public void setBuyeremail(String buyeremail) {
        this.buyeremail = buyeremail;
    }
    public String getCardcode() {
        return cardcode;
    }
    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }
    public String getParentemail() {
        return parentemail;
    }
    public void setParentemail(String parentemail) {
        this.parentemail = parentemail;
    }
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public String getTotPrice() {
        return totPrice;
    }
    public void setTotPrice(String totPrice) {
        this.totPrice = totPrice;
    }
    public String getCARD_Code() {
        return CARD_Code;
    }
    public void setCARD_Code(String cARD_Code) {
        CARD_Code = cARD_Code;
    }
    public String getCARD_Num() {
        return CARD_Num;
    }
    public void setCARD_Num(String cARD_Num) {
        CARD_Num = cARD_Num;
    }

    public String getVactBankcode() {
        return vactBankcode;
    }
    public void setVactBankcode(String vactBankcode) {
        this.vactBankcode = vactBankcode;
    }
    public String getVactName() {
        return vactName;
    }
    public void setVactName(String vactName) {
        this.vactName = vactName;
    }
    public String getVactInputname() {
        return vactInputname;
    }
    public void setVactInputname(String vactInputname) {
        this.vactInputname = vactInputname;
    }
    public String getVACT_MOID() {
        return VACT_MOID;
    }
    public void setVACT_MOID(String vACT_MOID) {
        VACT_MOID = vACT_MOID;
    }
    public String getVACT_Date() {
        return VACT_Date;
    }
    public void setVACT_Date(String vACT_Date) {
        VACT_Date = vACT_Date;
    }
    public String getVACT_Time() {
        return VACT_Time;
    }
    public void setVACT_Time(String vACT_Time) {
        VACT_Time = vACT_Time;
    }

    public String getCshrType() {
        return cshrType;
    }
    public void setCshrType(String cshrType) {
        this.cshrType = cshrType;
    }
    public String getCshrResultcode() {
        return cshrResultcode;
    }
    public void setCshrResultcode(String cshrResultcode) {
        this.cshrResultcode = cshrResultcode;
    }
    public String getSettlWayCd() {
        return settlWayCd;
    }
    public void setSettlWayCd(String settlWayCd) {
        this.settlWayCd = settlWayCd;
    }
    public String getConfirmPrice() {
        return confirmPrice;
    }
    public void setConfirmPrice(String confirmPrice) {
        this.confirmPrice = confirmPrice;
    }
    public String getMpointAplyYn() {
        return mpointAplyYn;
    }
    public void setMpointAplyYn(String mpointAplyYn) {
        this.mpointAplyYn = mpointAplyYn;
    }
    public String getMpointMid() {
        return mpointMid;
    }
    public void setMpointMid(String mpointMid) {
        this.mpointMid = mpointMid;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public String getPetNm() {
        return petNm;
    }
    public void setPetNm(String petNm) {
        this.petNm = petNm;
    }
    public String getCustTelNo() {
        return custTelNo;
    }
    public void setCustTelNo(String custTelNo) {
        this.custTelNo = custTelNo;
    }
    public String getCustEmail() {
        return custEmail;
    }
    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }
    public String getCustNm() {
        return custNm;
    }
    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }
    public String getCba() {
        return cba;
    }
    public void setCba(String cba) {
        this.cba = cba;
    }
    public String getEnk() {
        return enk;
    }
    public void setEnk(String enk) {
        this.enk = enk;
    }
    public String getReturnFlag() {
        return returnFlag;
    }
    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getNointerest() {
        return nointerest;
    }
    public void setNointerest(String nointerest) {
        this.nointerest = nointerest;
    }
    public String getRnResultmsg() {
        return rnResultmsg;
    }
    public void setRnResultmsg(String rnResultmsg) {
        this.rnResultmsg = rnResultmsg;
    }
    public String getCancelreason() {
        return cancelreason;
    }
    public void setCancelreason(String cancelreason) {
        this.cancelreason = cancelreason;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getCancelDate() {
        return cancelDate;
    }
    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }
    public String getCancelTime() {
        return cancelTime;
    }
    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getResultErrorCode() {
        return resultErrorCode;
    }
    public void setResultErrorCode(String resultErrorCode) {
        this.resultErrorCode = resultErrorCode;
    }
    public String getCshrCancelnum() {
        return cshrCancelnum;
    }
    public void setCshrCancelnum(String cshrCancelnum) {
        this.cshrCancelnum = cshrCancelnum;
    }
    public String getApplDate() {
        return applDate;
    }
    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }
    public String getApplTime() {
        return applTime;
    }
    public void setApplTime(String applTime) {
        this.applTime = applTime;
    }
    public String getApplNum() {
        return applNum;
    }
    public void setApplNum(String applNum) {
        this.applNum = applNum;
    }
    public String getCardQuota() {
        return cardQuota;
    }
    public void setCardQuota(String cardQuota) {
        this.cardQuota = cardQuota;
    }
    public String getTid() {
        return tid;
    }
    public void setTid(String tid) {
        this.tid = tid;
    }
    public String getAcctBankcode() {
        return acctBankcode;
    }
    public void setAcctBankcode(String acctBankcode) {
        this.acctBankcode = acctBankcode;
    }
    public String getVactNum() {
        return vactNum;
    }
    public void setVactNum(String vactNum) {
        this.vactNum = vactNum;
    }
    public String getCardInterest() {
        return cardInterest;
    }
    public void setCardInterest(String cardInterest) {
        this.cardInterest = cardInterest;
    }
    public String getEventCode() {
        return eventCode;
    }
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    public String getCardBankcode() {
        return cardBankcode;
    }
    public void setCardBankcode(String cardBankcode) {
        this.cardBankcode = cardBankcode;
    }
    public String getGopaymethod() {
        return gopaymethod;
    }
    public void setGopaymethod(String gopaymethod) {
        this.gopaymethod = gopaymethod;
    }
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public String getEnctype() {
		return enctype;
	}
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
	public String getpReqUrl() {
		return pReqUrl;
	}
	public void setpReqUrl(String pReqUrl) {
		this.pReqUrl = pReqUrl;
	}
	public String getpStatus() {
		return pStatus;
	}
	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}
	public String getpRmesg1() {
		return pRmesg1;
	}
	public void setpRmesg1(String pRmesg1) {
		this.pRmesg1 = pRmesg1;
	}
	public String getpTid() {
		return pTid;
	}
	public void setpTid(String pTid) {
		this.pTid = pTid;
	}
	public String getpNoti() {
		return pNoti;
	}
	public void setpNoti(String pNoti) {
		this.pNoti = pNoti;
	}




}
