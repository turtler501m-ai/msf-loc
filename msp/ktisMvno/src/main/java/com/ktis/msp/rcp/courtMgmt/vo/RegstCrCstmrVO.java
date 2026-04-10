package com.ktis.msp.rcp.courtMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class RegstCrCstmrVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = -6510970033252597452L;
    
    private String endYn;
    private int chkCstmrRrn;
    private String cstmrRrn;
    private String searchRrn;
    private String cstmrName;
    private String crTp;
    private String crTpCd;
    private String crComp;
	private String noJudg;
    private String judgPan;
    private String rbStrtDt;
    private String rbEsti;
    private String rbRate;
    private String rbPrid;
    private String acRegDt;
    private String crSeq;
    private String lcMstSeq;
    private String lcDtlSeq;
    private String crBondSeq;
    private String crRcivSeq;
    private String crInoutSeq;
    private String telFn;
    private String telMn;
    private String telRn;
    private String mobileNo;
    private String unpdPrc;
    private String instPrc;
    private String sumPrc;
    private String bondNum;
    private String openDt;
    private String rcivDt;
    private String stanDt;
    private String tpJinh;
    private String tpJinhCd;
    private String strDtl;
    private String fileReg;
    private String fileYn;
    private String opCode;
    private String gubun;
    private String orderKey;
    private String vacBankCd;
    private String vacBankNm;
    private String vacId;    
    private String dclrDate;    
    private String payAmt;
    private String sessionUserId;
    private String retCd;
    private String retMsg;
    private String oVacBankCd;
    private String oVacBankNm;
    private String oVacId;
    private String inoutDttm;
    private String inoutName;
    private String inoutPric;
    private String inoutCnt;
    private String inoutOrder;
    private String etcMemo;
    private String inPric;
    private String outPric;
    private String totalPric;
    private String acctId;

    private String ext; //파일확장자
    private String realFilePath; //파일경로
    private String realFileNm; //파일명
    private String rgstInflowCd; //등록유입경로
    private String fileNmSeq; //파일이름 시퀀스
    private String fileId; //파일ID
    private String fileType;
    private String fileTypeCd;
    private String subscriberNo;
    private String subLinkName;
    private String fileName;
    private String filePath;
    private String realFileDir;
    private String realFileName;
    
    public String getCstmrName() {
		return cstmrName;
	}

	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}

	public String getCrTp() {
		return crTp;
	}

	public void setCrTp(String crTp) {
		this.crTp = crTp;
	}

	public String getCrComp() {
		return crComp;
	}

	public void setCrComp(String crComp) {
		this.crComp = crComp;
	}

	public String getNoJudg() {
		return noJudg;
	}

	public void setNoJudg(String noJudg) {
		this.noJudg = noJudg;
	}

	public String getJudgPan() {
		return judgPan;
	}

	public void setJudgPan(String judgPan) {
		this.judgPan = judgPan;
	}

	public String getRbStrtDt() {
		return rbStrtDt;
	}

	public void setRbStrtDt(String rbStrtDt) {
		this.rbStrtDt = rbStrtDt;
	}

	public String getRbEsti() {
		return rbEsti;
	}

	public void setRbEsti(String rbEsti) {
		this.rbEsti = rbEsti;
	}

	public String getRbRate() {
		return rbRate;
	}

	public void setRbRate(String rbRate) {
		this.rbRate = rbRate;
	}

	public String getRbPrid() {
		return rbPrid;
	}

	public void setRbPrid(String rbPrid) {
		this.rbPrid = rbPrid;
	}

	public String getAcRegDt() {
		return acRegDt;
	}

	public void setAcRegDt(String acRegDt) {
		this.acRegDt = acRegDt;
	}
    
	public String getSearchRrn() {
		return searchRrn;
	}

	public void setSearchRrn(String searchRrn) {
		this.searchRrn = searchRrn;
	}

	public String getCstmrRrn() {
		return cstmrRrn;
	}

	public void setCstmrRrn(String cstmrRrn) {
		this.cstmrRrn = cstmrRrn;
	}

	public int getChkCstmrRrn() {
		return chkCstmrRrn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setChkCstmrRrn(int chkCstmrRrn) {
		this.chkCstmrRrn = chkCstmrRrn;
	}

	public String getEndYn() {
		return endYn;
	}

	public void setEndYn(String endYn) {
		this.endYn = endYn;
	}

	public String getCrSeq() {
		return crSeq;
	}

	public void setCrSeq(String crSeq) {
		this.crSeq = crSeq;
	}
	
	public String getCrBondSeq() {
		return crBondSeq;
	}

	public void setCrBondSeq(String crBondSeq) {
		this.crBondSeq = crBondSeq;
	}

	public String getCrRcivSeq() {
		return crRcivSeq;
	}

	public void setCrRcivSeq(String crRcivSeq) {
		this.crRcivSeq = crRcivSeq;
	}

	public String getCrTpCd() {
		return crTpCd;
	}

	public void setCrTpCd(String crTpCd) {
		this.crTpCd = crTpCd;
	}

	public String getTelFn() {
		return telFn;
	}

	public void setTelFn(String telFn) {
		this.telFn = telFn;
	}

	public String getTelMn() {
		return telMn;
	}

	public void setTelMn(String telMn) {
		this.telMn = telMn;
	}

	public String getTelRn() {
		return telRn;
	}

	public void setTelRn(String telRn) {
		this.telRn = telRn;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUnpdPrc() {
		return unpdPrc;
	}

	public void setUnpdPrc(String unpdPrc) {
		this.unpdPrc = unpdPrc;
	}

	public String getInstPrc() {
		return instPrc;
	}

	public void setInstPrc(String instPrc) {
		this.instPrc = instPrc;
	}

	public String getSumPrc() {
		return sumPrc;
	}

	public void setSumPrc(String sumPrc) {
		this.sumPrc = sumPrc;
	}

	public String getBondNum() {
		return bondNum;
	}

	public void setBondNum(String bondNum) {
		this.bondNum = bondNum;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getRcivDt() {
		return rcivDt;
	}

	public void setRcivDt(String rcivDt) {
		this.rcivDt = rcivDt;
	}

	public String getStanDt() {
		return stanDt;
	}

	public void setStanDt(String stanDt) {
		this.stanDt = stanDt;
	}

	public String getTpJinh() {
		return tpJinh;
	}

	public void setTpJinh(String tpJinh) {
		this.tpJinh = tpJinh;
	}

	public String getStrDtl() {
		return strDtl;
	}

	public void setStrDtl(String strDtl) {
		this.strDtl = strDtl;
	}

	public String getTpJinhCd() {
		return tpJinhCd;
	}

	public void setTpJinhCd(String tpJinhCd) {
		this.tpJinhCd = tpJinhCd;
	}

	public String getCrInoutSeq() {
		return crInoutSeq;
	}

	public void setCrInoutSeq(String crInoutSeq) {
		this.crInoutSeq = crInoutSeq;
	}

	public String getInoutDttm() {
		return inoutDttm;
	}

	public void setInoutDttm(String inoutDttm) {
		this.inoutDttm = inoutDttm;
	}

	public String getInoutName() {
		return inoutName;
	}

	public void setInoutName(String inoutName) {
		this.inoutName = inoutName;
	}

	public String getInoutPric() {
		return inoutPric;
	}

	public void setInoutPric(String inoutPric) {
		this.inoutPric = inoutPric;
	}

	public String getEtcMemo() {
		return etcMemo;
	}

	public void setEtcMemo(String etcMemo) {
		this.etcMemo = etcMemo;
	}

	public String getInPric() {
		return inPric;
	}

	public void setInPric(String inPric) {
		this.inPric = inPric;
	}

	public String getOutPric() {
		return outPric;
	}

	public void setOutPric(String outPric) {
		this.outPric = outPric;
	}

	public String getInoutCnt() {
		return inoutCnt;
	}

	public void setInoutCnt(String inoutCnt) {
		this.inoutCnt = inoutCnt;
	}

	public String getTotalPric() {
		return totalPric;
	}

	public void setTotalPric(String totalPric) {
		this.totalPric = totalPric;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getLcMstSeq() {
		return lcMstSeq;
	}

	public void setLcMstSeq(String lcMstSeq) {
		this.lcMstSeq = lcMstSeq;
	}

	public String getLcDtlSeq() {
		return lcDtlSeq;
	}

	public void setLcDtlSeq(String lcDtlSeq) {
		this.lcDtlSeq = lcDtlSeq;
	}

	public String getInoutOrder() {
		return inoutOrder;
	}

	public void setInoutOrder(String inoutOrder) {
		this.inoutOrder = inoutOrder;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public String getVacBankCd() {
		return vacBankCd;
	}

	public void setVacBankCd(String vacBankCd) {
		this.vacBankCd = vacBankCd;
	}

	public String getDclrDate() {
		return dclrDate;
	}

	public void setDclrDate(String dclrDate) {
		this.dclrDate = dclrDate;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getSessionUserId() {
		return sessionUserId;
	}

	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public String getRetCd() {
		return retCd;
	}

	public void setRetCd(String retCd) {
		this.retCd = retCd;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getoVacBankCd() {
		return oVacBankCd;
	}

	public void setoVacBankCd(String oVacBankCd) {
		this.oVacBankCd = oVacBankCd;
	}

	public String getoVacId() {
		return oVacId;
	}

	public void setoVacId(String oVacId) {
		this.oVacId = oVacId;
	}

	public String getVacId() {
		return vacId;
	}

	public void setVacId(String vacId) {
		this.vacId = vacId;
	}

	public String getoVacBankNm() {
		return oVacBankNm;
	}

	public void setoVacBankNm(String oVacBankNm) {
		this.oVacBankNm = oVacBankNm;
	}

	public String getVacBankNm() {
		return vacBankNm;
	}

	public void setVacBankNm(String vacBankNm) {
		this.vacBankNm = vacBankNm;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getRealFilePath() {
		return realFilePath;
	}

	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}

	public String getRealFileNm() {
		return realFileNm;
	}

	public void setRealFileNm(String realFileNm) {
		this.realFileNm = realFileNm;
	}

	public String getRgstInflowCd() {
		return rgstInflowCd;
	}

	public void setRgstInflowCd(String rgstInflowCd) {
		this.rgstInflowCd = rgstInflowCd;
	}

	public String getFileNmSeq() {
		return fileNmSeq;
	}

	public void setFileNmSeq(String fileNmSeq) {
		this.fileNmSeq = fileNmSeq;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeCd() {
		return fileTypeCd;
	}

	public void setFileTypeCd(String fileTypeCd) {
		this.fileTypeCd = fileTypeCd;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getSubLinkName() {
		return subLinkName;
	}

	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRealFileDir() {
		return realFileDir;
	}

	public void setRealFileDir(String realFileDir) {
		this.realFileDir = realFileDir;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public String getFileYn() {
		return fileYn;
	}

	public void setFileYn(String fileYn) {
		this.fileYn = fileYn;
	}

	public String getFileReg() {
		return fileReg;
	}

	public void setFileReg(String fileReg) {
		this.fileReg = fileReg;
	}
	
}
