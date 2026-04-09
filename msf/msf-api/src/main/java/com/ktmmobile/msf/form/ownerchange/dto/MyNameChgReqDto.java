package com.ktmmobile.msf.form.ownerchange.dto;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import com.ktmmobile.msf.common.util.EncryptUtil;

public class MyNameChgReqDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    // 양도인 정보
    private String custReqSeq;					//일련번호
    private String reqType;						//요청타입(CL 통화열람, NC 명의변경)
    private String userid;						//고객ID
    private String grCstmrName;					//고객명
    private String mobileNo;					//전화번호
    private String grCstmrNativeRrn;			//주민번호
    private String contractNum;					//계약번호
    private String grCstmrType;					//고객유형
    private String grOnlineAuthType;			//본인인증_방법
    private String grOnlineAuthInfo;			//본인인증_인증정보
    private String clauseCntrDelFlag;			//양도인_고객정보삭제동의
    private String grReqSeq;
    private String grResSeq;
    private String etcMobile;                   //양도인_미납을 위한 연락처

    // 양수인 정보
    private String scanId;
    private String cstmrName;					//고객명
    private String cstmrType;					//고객유형
    private String cstmrNativeRrn;				//주민번호
    private String onlineAuthType;				//본인인증_방법
    private String onlineAuthInfo;				//본인인증_인증정보
    private String clausePriCollectFlag; 		//양수인_개인정보수집동의
    private String clausePriOfferFlag;			//양수인_개인정보제공동의
    private String clauseEssCollectFlag;		//양수인_고유식별정보수집이용동의
    private String clauseConfidenceFlag;		//양수인_신용정보조회이용제공동의
    private String clausePriAdFlag;				//양수인_정보,광고전송을위한 개인정보제공동의
    private String clauseJehuFlag;				//양수인_제휴서비스를위한동의
    private String clauseFinanceFlag;			//양수인_개인정보처리 및 보험가입동의
    private String reqInfoChgYn;				//가입정보변경여부
    private String soc;							//요금제
    private String socNm;
    private String cstmrPost;					//우편번호
    private String cstmrAddr;					//주소
    private String cstmrAddrDtl;				//상세주소
    private String cstmrBillSendCode;			//명세서종류
    private String cstmrMail;					//이메일
    private String reqPayType;					//요금납부방법
    private String reqBank;						//계좌이체_은행코드
    private String reqAccountNumber;			//계좌이체_계좌번호
    private String reqCardCompany;				//신용카드_카드사
    private String reqCardNo;					//신용카드_번호
    private String reqCardYy;					//신용카드_유효년
    private String reqCardMm;					//신용카드_유효월
    private String selfCertType;				//신분증인증_유형
    private String selfIssuExprDt;				//신분증인증_발급/만료일자
    private String selfIssuNum;					//신분증인증_발급번호
    private String cstmrReceiveTelFn;			//연락가능연락처
    private String cstmrReceiveTelMn;
    private String cstmrReceiveTelRn;
    private String reqSeq;
    private String resSeq;
    private String jehuProdType;
    private String personalInfoCollectAgree;	//고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
    private String othersTrnsAgree;				//혜택 제공을 위한 제3자 제공 동의 : M모바일
    private String othersTrnsKtAgree;				//혜택 제공을 위한 제3자 제공 동의 : KT
    private String othersAdReceiveAgree;				//제3자 제공관련 광고 수신 동의
    private String cstmrForeignerNation;	//국가
    private String selfInqryAgrmYn; //본인인증조회동의
    private String mcnStatRsnCd;    //명의변경 사유 코드
    private String usimSuccYn;      //USIM 승계 여부
    private String iccId;           //USIM 일련번호
    private String agntTelNo;       //법정대리인 연락처
    private String customerId;      //
    private String chkTelType;
    private String clauseFathFlag;
    private String selfCstmrCi;
    private String fathTrgYn;                 //안면인증대상여부
    private String fathTransacId;             //안면인증트랜잭션ID
    private String fathCmpltNtfyDt;           //FS9 안민인증 완료일
    private String fathTelNo;                 //안면인증 URL 전송 전화번호
    private String indvLocaPrvAgree;                 //안면인증 URL 전송 전화번호

    // 법정 대리인 정보
    private String grMinorAgentName;          //양도인_법정대리인 성명
    private String grMinorAgentRelation;      //양도인_법정대리인 관계
    private String grMinorAgentRrn;           //양도인_법정대리인 식별번호
    private String grMinorAgentTel;           //양도인_법정대리인 전화번호
    private String grMinorAgentAgrmYn;        //양도인_법정대리인 본인인증조회동의

    private String minorAgentName;            //양수인_법정대리인 성명
    private String minorAgentRelation;        //양수인_법정대리인 관계
    private String minorAgentRrn;             //양수인_법정대리인 식별번호
    private String minorAgentTel;             //양수인_법정대리인 전화번호
    private String minorAgentAgrmYn;          //양수인_법정대리인 본인인증조회동의
    private String mcnResNo;                  //명의변경 예약번호

    public String getCustReqSeq() {
        return custReqSeq;
    }
    public void setCustReqSeq(String custReqSeq) {
        this.custReqSeq = custReqSeq;
    }
    public String getReqType() {
        return reqType;
    }
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getGrCstmrName() {
        return grCstmrName;
    }
    public void setGrCstmrName(String grCstmrName) {
        this.grCstmrName = grCstmrName;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getGrCstmrNativeRrn() {
        return grCstmrNativeRrn;
    }
    public void setGrCstmrNativeRrn(String grCstmrNativeRrn) {
        if (StringUtils.isBlank(grCstmrNativeRrn)) {
            this.grCstmrNativeRrn = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if (StringUtils.isNumeric(grCstmrNativeRrn)) {
                this.grCstmrNativeRrn = EncryptUtil.ace256Enc(grCstmrNativeRrn);
            } else {
                this.grCstmrNativeRrn = grCstmrNativeRrn;
            }

        }
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getCstmrType() {
        return cstmrType;
    }
    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }
    public String getGrOnlineAuthType() {
        return grOnlineAuthType;
    }
    public void setGrOnlineAuthType(String grOnlineAuthType) {
        this.grOnlineAuthType = grOnlineAuthType;
    }
    public String getGrOnlineAuthInfo() {
        return grOnlineAuthInfo;
    }
    public void setGrOnlineAuthInfo(String grOnlineAuthInfo) {
        this.grOnlineAuthInfo = grOnlineAuthInfo;
    }
    public String getClauseCntrDelFlag() {
        return clauseCntrDelFlag;
    }
    public void setClauseCntrDelFlag(String clauseCntrDelFlag) {
        this.clauseCntrDelFlag = clauseCntrDelFlag;
    }
    public String getScanId() {
        return scanId;
    }
    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getCstmrNativeRrn() {
        return cstmrNativeRrn;
    }
    //암호화
    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        if (StringUtils.isBlank(cstmrNativeRrn)) {
            this.cstmrNativeRrn = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if (StringUtils.isNumeric(cstmrNativeRrn)) {
                this.cstmrNativeRrn = EncryptUtil.ace256Enc(cstmrNativeRrn);
            } else {
                this.cstmrNativeRrn = cstmrNativeRrn;
            }

        }
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
    public String getClausePriAdFlag() {
        return clausePriAdFlag;
    }
    public void setClausePriAdFlag(String clausePriAdFlag) {
        this.clausePriAdFlag = clausePriAdFlag;
    }
    public String getClauseJehuFlag() {
        return clauseJehuFlag;
    }
    public void setClauseJehuFlag(String clauseJehuFlag) {
        this.clauseJehuFlag = clauseJehuFlag;
    }
    public String getClauseFinanceFlag() {
        return clauseFinanceFlag;
    }
    public void setClauseFinanceFlag(String clauseFinanceFlag) {
        this.clauseFinanceFlag = clauseFinanceFlag;
    }
    public String getReqInfoChgYn() {
        return reqInfoChgYn;
    }
    public void setReqInfoChgYn(String reqInfoChgYn) {
        this.reqInfoChgYn = reqInfoChgYn;
    }
    public String getSoc() {
        return soc;
    }
    public void setSoc(String soc) {
        this.soc = soc;
    }
    public String getSocNm() {
        return socNm;
    }
    public void setSocNm(String socNm) {
        this.socNm = socNm;
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
    public String getCstmrBillSendCode() {
        return cstmrBillSendCode;
    }
    public void setCstmrBillSendCode(String cstmrBillSendCode) {
        this.cstmrBillSendCode = cstmrBillSendCode;
    }
    public String getCstmrMail() {
        return cstmrMail;
    }
    public void setCstmrMail(String cstmrMail) {
        this.cstmrMail = cstmrMail;
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
    public String getReqAccountNumber() {
        return reqAccountNumber;
    }
    //암호화
    public void setReqAccountNumber(String reqAccountNumber) {
      if (StringUtils.isBlank(reqAccountNumber)) {
            this.reqAccountNumber = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if (StringUtils.isNumeric(reqAccountNumber)) {
                this.reqAccountNumber = EncryptUtil.ace256Enc(reqAccountNumber);
            } else {
                this.reqAccountNumber = reqAccountNumber;
            }
        }
    }
    public String getReqCardCompany() {
        return reqCardCompany;
    }
    public void setReqCardCompany(String reqCardCompany) {
        this.reqCardCompany = reqCardCompany;
    }
    public String getReqCardNo() {
        return reqCardNo;
    }
    //암호화
    public void setReqCardNo(String reqCardNo) {
      if (StringUtils.isBlank(reqCardNo)) {
            this.reqCardNo = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if (StringUtils.isNumeric(reqCardNo)) {
                this.reqCardNo = EncryptUtil.ace256Enc(reqCardNo);
            } else {
                this.reqCardNo = reqCardNo;
            }
        }
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
    public String getSelfCertType() {
        return selfCertType;
    }
    public void setSelfCertType(String selfCertType) {
        this.selfCertType = selfCertType;
    }
    public String getSelfIssuExprDt() {
        return selfIssuExprDt;
    }
    public void setSelfIssuExprDt(String selfIssuExprDt) {
        this.selfIssuExprDt = selfIssuExprDt;
    }
    public String getSelfIssuNum() {
        return selfIssuNum;
    }
    public void setSelfIssuNum(String selfIssuNum) {
        if (StringUtils.isBlank(selfIssuNum)) {
            this.selfIssuNum = "";
        } else {
            if (StringUtils.isNumeric(selfIssuNum)) {
                this.selfIssuNum = EncryptUtil.ace256Enc(selfIssuNum);
            } else {
                this.selfIssuNum = selfIssuNum;
            }
        }
    }
    public String getCstmrReceiveTelFn() {
        return cstmrReceiveTelFn;
    }
    public void setCstmrReceiveTelFn(String cstmrReceiveTelFn) {
        this.cstmrReceiveTelFn = cstmrReceiveTelFn;
    }
    public String getCstmrReceiveTelMn() {
        return cstmrReceiveTelMn;
    }
    public void setCstmrReceiveTelMn(String cstmrReceiveTelMn) {
        this.cstmrReceiveTelMn = cstmrReceiveTelMn;
    }
    public String getCstmrReceiveTelRn() {
        return cstmrReceiveTelRn;
    }
    public void setCstmrReceiveTelRn(String cstmrReceiveTelRn) {
        this.cstmrReceiveTelRn = cstmrReceiveTelRn;
    }
    public String getClauseConfidenceFlag() {
        return clauseConfidenceFlag;
    }
    public void setClauseConfidenceFlag(String clauseConfidenceFlag) {
        this.clauseConfidenceFlag = clauseConfidenceFlag;
    }
    public String getGrCstmrType() {
        return grCstmrType;
    }
    public void setGrCstmrType(String grCstmrType) {
        this.grCstmrType = grCstmrType;
    }

    public String getGrReqSeq() {
        return grReqSeq;
    }

    public void setGrReqSeq(String grReqSeq) {
        this.grReqSeq = grReqSeq;
    }

    public String getGrResSeq() {
        return grResSeq;
    }

    public void setGrResSeq(String grResSeq) {
        this.grResSeq = grResSeq;
    }

    public String getReqSeq() {
        return reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getResSeq() {
        return resSeq;
    }

    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }

    public String getJehuProdType() {
        return jehuProdType;
    }

    public void setJehuProdType(String jehuProdType) {
        this.jehuProdType = jehuProdType;
    }

    public String getPersonalInfoCollectAgree() {
        return personalInfoCollectAgree;
    }
    public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
        this.personalInfoCollectAgree = personalInfoCollectAgree;
    }
    public String getOthersTrnsAgree() {
        return othersTrnsAgree;
    }
    public void setOthersTrnsAgree(String othersTrnsAgree) {
        this.othersTrnsAgree = othersTrnsAgree;
    }

    public String getOthersTrnsKtAgree() {
        return othersTrnsKtAgree;
    }

    public void setOthersTrnsKtAgree(String othersTrnsKtAgree) {
        this.othersTrnsKtAgree = othersTrnsKtAgree;
    }

    public String getOthersAdReceiveAgree() {
        return othersAdReceiveAgree;
    }

    public void setOthersAdReceiveAgree(String othersAdReceiveAgree) {
        this.othersAdReceiveAgree = othersAdReceiveAgree;
    }

    public String getCstmrForeignerNation() {
        return cstmrForeignerNation;
    }
    public void setCstmrForeignerNation(String cstmrForeignerNation) {
        this.cstmrForeignerNation = cstmrForeignerNation;
    }
    public String getSelfInqryAgrmYn() {
        return selfInqryAgrmYn;
    }
    public void setSelfInqryAgrmYn(String selfInqryAgrmYn) {
        this.selfInqryAgrmYn = selfInqryAgrmYn;
    }

    public String getEtcMobile() {
        return etcMobile;
    }

    public void setEtcMobile(String etcMobile) {
        this.etcMobile = etcMobile;
    }

    public String getMcnStatRsnCd() {
        return mcnStatRsnCd;
    }

    public void setMcnStatRsnCd(String mcnStatRsnCd) {
        this.mcnStatRsnCd = mcnStatRsnCd;
    }

    public String getUsimSuccYn() {
        return usimSuccYn;
    }

    public void setUsimSuccYn(String usimSuccYn) {
        this.usimSuccYn = usimSuccYn;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public String getAgntTelNo() {
        return agntTelNo;
    }

    public void setAgntTelNo(String agntTelNo) {
        this.agntTelNo = agntTelNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChkTelType() {
        return chkTelType;
    }

    public void setChkTelType(String chkTelType) {
        this.chkTelType = chkTelType;
    }

    public String getClauseFathFlag() {
        return clauseFathFlag;
    }

    public void setClauseFathFlag(String clauseFathFlag) {
        this.clauseFathFlag = clauseFathFlag;
    }

    public String getSelfCstmrCi() {
        return selfCstmrCi;
    }

    public void setSelfCstmrCi(String selfCstmrCi) {
        this.selfCstmrCi = selfCstmrCi;
    }

    public String getGrMinorAgentName() {
        return grMinorAgentName;
    }

    public void setGrMinorAgentName(String grMinorAgentName) {
        this.grMinorAgentName = grMinorAgentName;
    }

    public String getGrMinorAgentRelation() {
        return grMinorAgentRelation;
    }

    public void setGrMinorAgentRelation(String grMinorAgentRelation) {
        this.grMinorAgentRelation = grMinorAgentRelation;
    }

    public String getGrMinorAgentRrn() {        
        return grMinorAgentRrn;
    }

    public void setGrMinorAgentRrn(String grMinorAgentRrn) {
        if (StringUtils.isBlank(grMinorAgentRrn)) {
            this.grMinorAgentRrn = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if (StringUtils.isNumeric(grMinorAgentRrn)) {
                this.grMinorAgentRrn = EncryptUtil.ace256Enc(grMinorAgentRrn);
            } else {
                this.grMinorAgentRrn = grMinorAgentRrn;
            }
        }
    }

    public String getGrMinorAgentTel() {
        return grMinorAgentTel;
    }

    public void setGrMinorAgentTel(String grMinorAgentTel) {
        this.grMinorAgentTel = grMinorAgentTel;
    }

    public String getGrMinorAgentAgrmYn() {
        return grMinorAgentAgrmYn;
    }

    public void setGrMinorAgentAgrmYn(String grMinorAgentAgrmYn) {
        this.grMinorAgentAgrmYn = grMinorAgentAgrmYn;
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

    public String getMinorAgentRrn() {
        return minorAgentRrn;
    }

    public void setMinorAgentRrn(String minorAgentRrn) {
        if (StringUtils.isBlank(minorAgentRrn)) {
            this.minorAgentRrn = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if (StringUtils.isNumeric(minorAgentRrn)) {
                this.minorAgentRrn = EncryptUtil.ace256Enc(minorAgentRrn);
            } else {
                this.minorAgentRrn = minorAgentRrn;
            }
        }
    }

    public String getMinorAgentTel() {
        return minorAgentTel;
    }

    public void setMinorAgentTel(String minorAgentTel) {
        this.minorAgentTel = minorAgentTel;
    }

    public String getMinorAgentAgrmYn() {
        return minorAgentAgrmYn;
    }

    public void setMinorAgentAgrmYn(String minorAgentAgrmYn) {
        this.minorAgentAgrmYn = minorAgentAgrmYn;
    }

    public String getFathTransacId() {
        return fathTransacId;
    }

    public void setFathTransacId(String fathTransacId) {
        this.fathTransacId = fathTransacId;
    }

    public String getFathCmpltNtfyDt() {
        return fathCmpltNtfyDt;
    }

    public void setFathCmpltNtfyDt(String fathCmpltNtfyDt) {
        this.fathCmpltNtfyDt = fathCmpltNtfyDt;
    }

    public String getFathTrgYn() {
        return fathTrgYn;
    }

    public void setFathTrgYn(String fathTrgYn) {
        this.fathTrgYn = fathTrgYn;
    }

    public String getFathTelNo() {
        return fathTelNo;
    }

    public void setFathTelNo(String fathTelNo) {
        this.fathTelNo = fathTelNo;
    }

    public String getIndvLocaPrvAgree() {
        return indvLocaPrvAgree;
    }

    public void setIndvLocaPrvAgree(String indvLocaPrvAgree) {
        this.indvLocaPrvAgree = indvLocaPrvAgree;
    }

    @Override
    public String toString() {
        return "MyNameChgReqDto{" +
                "custReqSeq='" + custReqSeq + '\'' +
                ", reqType='" + reqType + '\'' +
                ", userid='" + userid + '\'' +
                ", grCstmrName='" + grCstmrName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", grCstmrNativeRrn='" + grCstmrNativeRrn + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", grCstmrType='" + grCstmrType + '\'' +
                ", grOnlineAuthType='" + grOnlineAuthType + '\'' +
                ", grOnlineAuthInfo='" + grOnlineAuthInfo + '\'' +
                ", clauseCntrDelFlag='" + clauseCntrDelFlag + '\'' +
                ", grReqSeq='" + grReqSeq + '\'' +
                ", grResSeq='" + grResSeq + '\'' +
                ", etcMobile='" + etcMobile + '\'' +
                ", scanId='" + scanId + '\'' +
                ", cstmrName='" + cstmrName + '\'' +
                ", cstmrType='" + cstmrType + '\'' +
                ", cstmrNativeRrn='" + cstmrNativeRrn + '\'' +
                ", onlineAuthType='" + onlineAuthType + '\'' +
                ", onlineAuthInfo='" + onlineAuthInfo + '\'' +
                ", clausePriCollectFlag='" + clausePriCollectFlag + '\'' +
                ", clausePriOfferFlag='" + clausePriOfferFlag + '\'' +
                ", clauseEssCollectFlag='" + clauseEssCollectFlag + '\'' +
                ", clauseConfidenceFlag='" + clauseConfidenceFlag + '\'' +
                ", clausePriAdFlag='" + clausePriAdFlag + '\'' +
                ", clauseJehuFlag='" + clauseJehuFlag + '\'' +
                ", clauseFinanceFlag='" + clauseFinanceFlag + '\'' +
                ", reqInfoChgYn='" + reqInfoChgYn + '\'' +
                ", soc='" + soc + '\'' +
                ", socNm='" + socNm + '\'' +
                ", cstmrPost='" + cstmrPost + '\'' +
                ", cstmrAddr='" + cstmrAddr + '\'' +
                ", cstmrAddrDtl='" + cstmrAddrDtl + '\'' +
                ", cstmrBillSendCode='" + cstmrBillSendCode + '\'' +
                ", cstmrMail='" + cstmrMail + '\'' +
                ", reqPayType='" + reqPayType + '\'' +
                ", reqBank='" + reqBank + '\'' +
                ", reqAccountNumber='" + reqAccountNumber + '\'' +
                ", reqCardCompany='" + reqCardCompany + '\'' +
                ", reqCardNo='" + reqCardNo + '\'' +
                ", reqCardYy='" + reqCardYy + '\'' +
                ", reqCardMm='" + reqCardMm + '\'' +
                ", selfCertType='" + selfCertType + '\'' +
                ", selfIssuExprDt='" + selfIssuExprDt + '\'' +
                ", selfIssuNum='" + selfIssuNum + '\'' +
                ", cstmrReceiveTelFn='" + cstmrReceiveTelFn + '\'' +
                ", cstmrReceiveTelMn='" + cstmrReceiveTelMn + '\'' +
                ", cstmrReceiveTelRn='" + cstmrReceiveTelRn + '\'' +
                ", reqSeq='" + reqSeq + '\'' +
                ", resSeq='" + resSeq + '\'' +
                ", jehuProdType='" + jehuProdType + '\'' +
                ", personalInfoCollectAgree='" + personalInfoCollectAgree + '\'' +
                ", othersTrnsAgree='" + othersTrnsAgree + '\'' +
                ", othersTrnsKtAgree='" + othersTrnsKtAgree + '\'' +
                ", othersAdReceiveAgree='" + othersAdReceiveAgree + '\'' +
                ", cstmrForeignerNation='" + cstmrForeignerNation + '\'' +
                ", selfInqryAgrmYn='" + selfInqryAgrmYn + '\'' +
                ", mcnStatRsnCd='" + mcnStatRsnCd + '\'' +
                ", usimSuccYn='" + usimSuccYn + '\'' +
                ", iccId='" + iccId + '\'' +
                ", agntTelNo='" + agntTelNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", chkTelType='" + chkTelType + '\'' +
                ", clauseFathFlag='" + clauseFathFlag + '\'' +
                ", selfCstmrCi='" + selfCstmrCi + '\'' +
                ", fathTrgYn='" + fathTrgYn + '\'' +
                ", fathTransacId='" + fathTransacId + '\'' +
                ", fathCmpltNtfyDt='" + fathCmpltNtfyDt + '\'' +
                ", fathTelNo='" + fathTelNo + '\'' +
                ", indvLocaPrvAgree='" + indvLocaPrvAgree + '\'' +
                ", grMinorAgentName='" + grMinorAgentName + '\'' +
                ", grMinorAgentRelation='" + grMinorAgentRelation + '\'' +
                ", grMinorAgentRrn='" + grMinorAgentRrn + '\'' +
                ", grMinorAgentTel='" + grMinorAgentTel + '\'' +
                ", grMinorAgentAgrmYn='" + grMinorAgentAgrmYn + '\'' +
                ", minorAgentName='" + minorAgentName + '\'' +
                ", minorAgentRelation='" + minorAgentRelation + '\'' +
                ", minorAgentRrn='" + minorAgentRrn + '\'' +
                ", minorAgentTel='" + minorAgentTel + '\'' +
                ", minorAgentAgrmYn='" + minorAgentAgrmYn + '\'' +
                ", mcnResNo='" + mcnResNo + '\'' +
                '}';
    }

    public String getMcnResNo() {
        return mcnResNo;
    }

    public void setMcnResNo(String mcnResNo) {
        this.mcnResNo = mcnResNo;
    }
}
