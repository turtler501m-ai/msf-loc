package com.ktis.msp.rwd.rwdMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RwdMemberVO extends BaseVo {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6498388435576155801L;
	
	private String rwdSeq;
	private String subscriberNo;
	private String subLinkName;
	private String contractNum;
	private String rwdProdCd;
	private String rwdProdNm;
	private String strtDttm;
	private String endDttm;
	private String regstDt;
	private String ifTrgtCd;
	private String ifTrgtNm;
	private String modelNm;
	private String imei;
	private String imeiTwo;
	private String modelId;
	private String chnCd;
	private String chnNm;
	private String reqBuyType;
	private String reqBuyTypeNm;
	private String scanId;
	private String resNo;
	private String openAgntCd;
	private String openAgntNm;
	private String insrMngmNo;
	private String vrfyRsltCd;
	private String vrfyRsltNm;
	private String vrfyId;
	private String vrfyDttm;
	private String rmk;
	private String rvisnId;
	private String rvisnDttm;
	private String canRsltCd;
	private String canRsltNm;
	private String rwdStatCd;
	private String intmSrlNo;
	private String amt;			//보장금액
	private String rwdPrd;		//보상기간
	private String leftPrd;		//도래남은기간
	private String pymnCd;		//청구수납코드
	private String blpymDate;	//청구수납일자
	private String pymnAmnt;	//청구수납금액
	private String pymnMthdNm;	//납부방법
	private String updType;		//수정구분
	
	private String searchFromDt;
	private String searchToDt;
	private String searchCd;
	private String searchVal;
	private String searchGbVal;
	private String searchGb;
	private String agntId;
	private String searchChnCd;
	private String searchIfTrgtCd;
	private String searchRwdProdCd;
	private String searchRsltCd;
	private String searchCntrNo;
    private String searchBillYm; //청구월
    private String searchTermCd; //도래기간

	private String custNm;
	private String subStatusNm;
	private String openDt;
	private String requestKey;
	private String buyPric;
	private String sbscRwdProdNm;

	private String fileId;
	private String fileDir;
    private String realFilePath;	//파일경로
    private String realFileNm;		//파일명
    private String fileNmSeq;		//파일이름 시퀀스
    private String fileType;
    private String fileTypeCd;
    private String fileName;
    private String filePath;
    private String ext;				//파일확장자
    
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	public String getRwdPrd() {
		return rwdPrd;
	}
	public void setRwdPrd(String rwdPrd) {
		this.rwdPrd = rwdPrd;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getIntmSrlNo() {
		return intmSrlNo;
	}
	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}
	public String getCanRsltCd() {
		return canRsltCd;
	}
	public void setCanRsltCd(String canRsltCd) {
		this.canRsltCd = canRsltCd;
	}
	public String getCanRsltNm() {
		return canRsltNm;
	}
	public void setCanRsltNm(String canRsltNm) {
		this.canRsltNm = canRsltNm;
	}
	public String getRwdStatCd() {
		return rwdStatCd;
	}
	public void setRwdStatCd(String rwdStatCd) {
		this.rwdStatCd = rwdStatCd;
	}
	public String getSearchGbVal() {
		return searchGbVal;
	}
	public void setSearchGbVal(String searchGbVal) {
		this.searchGbVal = searchGbVal;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSbscRwdProdNm() {
		return sbscRwdProdNm;
	}
	public void setSbscRwdProdNm(String sbscRwdProdNm) {
		this.sbscRwdProdNm = sbscRwdProdNm;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getBuyPric() {
		return buyPric;
	}
	public void setBuyPric(String buyPric) {
		this.buyPric = buyPric;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getSearchCntrNo() {
		return searchCntrNo;
	}
	public void setSearchCntrNo(String searchCntrNo) {
		this.searchCntrNo = searchCntrNo;
	}
	public String getSearchChnCd() {
		return searchChnCd;
	}
	public void setSearchChnCd(String searchChnCd) {
		this.searchChnCd = searchChnCd;
	}
	public String getSearchIfTrgtCd() {
		return searchIfTrgtCd;
	}
	public void setSearchIfTrgtCd(String searchIfTrgtCd) {
		this.searchIfTrgtCd = searchIfTrgtCd;
	}
	public String getSearchRwdProdCd() {
		return searchRwdProdCd;
	}
	public void setSearchRwdProdCd(String searchRwdProdCd) {
		this.searchRwdProdCd = searchRwdProdCd;
	}
	public String getSearchRsltCd() {
		return searchRsltCd;
	}
	public void setSearchRsltCd(String searchRsltCd) {
		this.searchRsltCd = searchRsltCd;
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getRwdProdCd() {
		return rwdProdCd;
	}
	public void setRwdProdCd(String rwdProdCd) {
		this.rwdProdCd = rwdProdCd;
	}
	public String getRwdProdNm() {
		return rwdProdNm;
	}
	public void setRwdProdNm(String rwdProdNm) {
		this.rwdProdNm = rwdProdNm;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getEndDttm() {
		return endDttm;
	}
	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}
	public String getIfTrgtCd() {
		return ifTrgtCd;
	}
	public void setIfTrgtCd(String ifTrgtCd) {
		this.ifTrgtCd = ifTrgtCd;
	}
	public String getIfTrgtNm() {
		return ifTrgtNm;
	}
	public void setIfTrgtNm(String ifTrgtNm) {
		this.ifTrgtNm = ifTrgtNm;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getChnCd() {
		return chnCd;
	}
	public void setChnCd(String chnCd) {
		this.chnCd = chnCd;
	}
	public String getChnNm() {
		return chnNm;
	}
	public void setChnNm(String chnNm) {
		this.chnNm = chnNm;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}
	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
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
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public String getInsrMngmNo() {
		return insrMngmNo;
	}
	public void setInsrMngmNo(String insrMngmNo) {
		this.insrMngmNo = insrMngmNo;
	}
	public String getVrfyRsltCd() {
		return vrfyRsltCd;
	}
	public void setVrfyRsltCd(String vrfyRsltCd) {
		this.vrfyRsltCd = vrfyRsltCd;
	}
	public String getVrfyRsltNm() {
		return vrfyRsltNm;
	}
	public void setVrfyRsltNm(String vrfyRsltNm) {
		this.vrfyRsltNm = vrfyRsltNm;
	}
	public String getVrfyId() {
		return vrfyId;
	}
	public void setVrfyId(String vrfyId) {
		this.vrfyId = vrfyId;
	}
	public String getVrfyDttm() {
		return vrfyDttm;
	}
	public void setVrfyDttm(String vrfyDttm) {
		this.vrfyDttm = vrfyDttm;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getSearchFromDt() {
		return searchFromDt;
	}
	public void setSearchFromDt(String searchFromDt) {
		this.searchFromDt = searchFromDt;
	}
	public String getSearchToDt() {
		return searchToDt;
	}
	public void setSearchToDt(String searchToDt) {
		this.searchToDt = searchToDt;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getAgntId() {
		return agntId;
	}
	public void setAgntId(String agntId) {
		this.agntId = agntId;
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
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getRwdSeq() {
		return rwdSeq;
	}
	public void setRwdSeq(String rwdSeq) {
		this.rwdSeq = rwdSeq;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getImeiTwo() {
		return imeiTwo;
	}
	public void setImeiTwo(String imeiTwo) {
		this.imeiTwo = imeiTwo;
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
	public String getFileNmSeq() {
		return fileNmSeq;
	}
	public void setFileNmSeq(String fileNmSeq) {
		this.fileNmSeq = fileNmSeq;
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
	public String getRegstDt() {
		return regstDt;
	}
	public void setRegstDt(String regstDt) {
		this.regstDt = regstDt;
	}

	public String getSearchBillYm() {
		return searchBillYm;
	}
	public void setSearchBillYm(String searchBillYm) {
		this.searchBillYm = searchBillYm;
	}
	public String getSearchTermCd() {
		return searchTermCd;
	}
	public void setSearchTermCd(String searchTermCd) {
		this.searchTermCd = searchTermCd;
	}
	public String getLeftPrd() {
		return leftPrd;
	}
	public void setLeftPrd(String leftPrd) {
		this.leftPrd = leftPrd;
	}
	public String getPymnCd() {
		return pymnCd;
	}
	public void setPymnCd(String pymnCd) {
		this.pymnCd = pymnCd;
	}
	public String getBlpymDate() {
		return blpymDate;
	}
	public void setBlpymDate(String blpymDate) {
		this.blpymDate = blpymDate;
	}
	public String getPymnAmnt() {
		return pymnAmnt;
	}
	public void setPymnAmnt(String pymnAmnt) {
		this.pymnAmnt = pymnAmnt;
	}
	public String getPymnMthdNm() {
		return pymnMthdNm;
	}
	public void setPymnMthdNm(String pymnMthdNm) {
		this.pymnMthdNm = pymnMthdNm;
	}
	public String getUpdType() {
		return updType;
	}
	public void setUpdType(String updType) {
		this.updType = updType;
	}	
	
}