package com.ktis.msp.rcp.rcpMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.util.List;

public class UsimDlvryChangeVO extends BaseVo  {

	private static final long serialVersionUID = 1071426434279463584L;

	/** 검색영역 */
	private String srchStrtDt;				//검색시작일자
	private String srchEndDt;				//검색종료일자
	private String searchGbn;				//검색구분
	private String searchName;				//검색키워드
	private String searchSubscriberNo;		//FORM2 조회 핸드폰번호
	private String nfcUsimYn;				//USIM종류


	private String seq;						//신청정보(유심교체) 주문번호
	private String customerLinkName;		//고객이름
	private String customerId;				//고객번호
	private String subscriberNo;			//고객연락처
	private String ctn;						//PRX연동용 고객연락처 (마스킹X)
	private String chnlCd;					//신청경로코드
	private String chnlNm;					//신청경로명
	private String reqQnty;					//신청수량
	private String dlvryName;				//받는사람 이름
	private String dlvryTel;				//받는사람 연락처
	private String dlvryTelFn;				//받는사람 연락처앞자리
	private String dlvryTelMn;				//받는사람 연락처중간자리
	private String dlvryTelRn;				//받는사람 연락처뒷자리
	private String dlvryPost;				//우편번호
	private String dlvryAddr;				//주소
	private String dlvryAddrDtl;			//상세주소
	private String dlvryMemo;				//배송요청사항
	private String agrmYn;					//수집 및 이용동의여부
	private String onlineAuthInfo;			//인증정보
	private String tbCd;					//택배사 코드
	private String tbNm;					//택배사 명
	private String dlvryNo;					//운송장번호
	private String dlvryYn;					//송장번호 등록여부
	private String status;					//MST, DTL 진행상태
	private String statusNm;				//MST, DTL 진행상태명
	private String procYn;					//MST 처리여부
	private String procYnNm;				//MST 처리여부명
	private String procMemo;				//MST 메모
	private String procDttm;				//MST 처리일시
	private String reqInDay;				//MST 신청일자
	private String reqInTime;				//MST 신청접수시간
	private String cretId;					//생성자
	private String cretDttm;				//생성일시
	private String amdId;					//수정자
	private String amdDttm;					//수정일시
	private String trnsYn;					//이관여부

	private String subSeq;					//DTL테이블 SUB시퀀스
	private String contractNum;				//계약번호
	private String svcCntrNo;				//서비스계약번호
	private String prdtId;					//유심제품ID
	private String prdtCode;				//유심제품명
	private String usimSn;					//유심번호
	private String chgYn;					//유심교체여부
	private String chgDttm;					//유심교체처리일시
	
	private String fileName;				//엑셀파일이름
	private String userId;					//세션 고객ID
	private String templateId;				//문자 발송 템플릿 ID

	List<UsimDlvryChangeVO> items;			//엑셀업로드 리스트
	
	/*연동결과*/
	private String osstNo;					//연동 번호
	private String preChkRsltCd;			//유심무상교체 사전체크 API연동코드
	private String preChkRsltMsg;			//유심무상교체 사전체크 API연동메세지
	private String applyRsltCd;				//유심무상교체 접수 API연동코드
	private String applyRsltMsg;			//유심무상교체 접수 API연동메세지
	private String usimType;				//유심타입 (U = USIM, E = ESIM)
	private String subStatus;				//계약상태

	/* 유심모델 데이터 등록 */
	private String nfcUsimType;				//NFC 여부
	private String usimModelCd;			//유심 모델코드
	private String usimModelId;				//유심 모델ID
	
	


	public String getSrchStrtDt() {
		return srchStrtDt;
	}

	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getSearchGbn() {
		return searchGbn;
	}

	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCustomerLinkName() {
		return customerLinkName;
	}

	public void setCustomerLinkName(String customerLinkName) {
		this.customerLinkName = customerLinkName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getChnlCd() {
		return chnlCd;
	}

	public void setChnlCd(String chnlCd) {
		this.chnlCd = chnlCd;
	}

	public String getChnlNm() {
		return chnlNm;
	}

	public void setChnlNm(String chnlNm) {
		this.chnlNm = chnlNm;
	}

	public String getReqQnty() {
		return reqQnty;
	}

	public void setReqQnty(String reqQnty) {
		this.reqQnty = reqQnty;
	}

	public String getDlvryName() {
		return dlvryName;
	}

	public void setDlvryName(String dlvryName) {
		this.dlvryName = dlvryName;
	}

	public String getDlvryTel() {
		return dlvryTel;
	}

	public void setDlvryTel(String dlvryTel) {
		this.dlvryTel = dlvryTel;
	}

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

	public String getDlvryMemo() {
		return dlvryMemo;
	}

	public void setDlvryMemo(String dlvryMemo) {
		this.dlvryMemo = dlvryMemo;
	}

	public String getAgrmYn() {
		return agrmYn;
	}

	public void setAgrmYn(String agrmYn) {
		this.agrmYn = agrmYn;
	}

	public String getOnlineAuthInfo() {
		return onlineAuthInfo;
	}

	public void setOnlineAuthInfo(String onlineAuthInfo) {
		this.onlineAuthInfo = onlineAuthInfo;
	}

	public String getTbCd() {
		return tbCd;
	}

	public void setTbCd(String tbCd) {
		this.tbCd = tbCd;
	}

	public String getTbNm() {
		return tbNm;
	}

	public void setTbNm(String tbNm) {
		this.tbNm = tbNm;
	}

	public String getDlvryNo() {
		return dlvryNo;
	}

	public void setDlvryNo(String dlvryNo) {
		this.dlvryNo = dlvryNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcYn() {
		return procYn;
	}

	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}

	public String getProcMemo() {
		return procMemo;
	}

	public void setProcMemo(String procMemo) {
		this.procMemo = procMemo;
	}

	public String getProcDttm() {
		return procDttm;
	}

	public void setProcDttm(String procDttm) {
		this.procDttm = procDttm;
	}

	public String getReqInDay() {
		return reqInDay;
	}

	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}

	public String getCretId() {
		return cretId;
	}

	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getCretDttm() {
		return cretDttm;
	}

	public void setCretDttm(String cretDttm) {
		this.cretDttm = cretDttm;
	}

	public String getAmdId() {
		return amdId;
	}

	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}

	public String getAmdDttm() {
		return amdDttm;
	}

	public void setAmdDttm(String amdDttm) {
		this.amdDttm = amdDttm;
	}

	public String getTrnsYn() {
		return trnsYn;
	}

	public void setTrnsYn(String trnsYn) {
		this.trnsYn = trnsYn;
	}

	public String getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(String subSeq) {
		this.subSeq = subSeq;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}

	public String getPrdtCode() {
		return prdtCode;
	}

	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}

	public String getUsimSn() {
		return usimSn;
	}

	public void setUsimSn(String usimSn) {
		this.usimSn = usimSn;
	}

	public String getChgYn() {
		return chgYn;
	}

	public void setChgYn(String chgYn) {
		this.chgYn = chgYn;
	}

	public String getChgDttm() {
		return chgDttm;
	}

	public void setChgDttm(String chgDttm) {
		this.chgDttm = chgDttm;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public List<UsimDlvryChangeVO> getItems() {
		return items;
	}

	public void setItems(List<UsimDlvryChangeVO> items) {
		this.items = items;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getSearchSubscriberNo() {
		return searchSubscriberNo;
	}

	public void setSearchSubscriberNo(String searchSubscriberNo) {
		this.searchSubscriberNo = searchSubscriberNo;
	}

	public String getPreChkRsltCd() {
		return preChkRsltCd;
	}

	public void setPreChkRsltCd(String preChkRsltCd) {
		this.preChkRsltCd = preChkRsltCd;
	}

	public String getPreChkRsltMsg() {
		return preChkRsltMsg;
	}

	public void setPreChkRsltMsg(String preChkRsltMsg) {
		this.preChkRsltMsg = preChkRsltMsg;
	}

	public String getApplyRsltCd() {
		return applyRsltCd;
	}

	public void setApplyRsltCd(String applyRsltCd) {
		this.applyRsltCd = applyRsltCd;
	}

	public String getApplyRsltMsg() {
		return applyRsltMsg;
	}

	public void setApplyRsltMsg(String applyRsltMsg) {
		this.applyRsltMsg = applyRsltMsg;
	}

	public String getOsstNo() {
		return osstNo;
	}

	public void setOsstNo(String osstNo) {
		this.osstNo = osstNo;
	}

	public String getUsimType() {
		return usimType;
	}

	public void setUsimType(String usimType) {
		this.usimType = usimType;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getCtn() {
		return ctn;
	}

	public void setCtn(String ctn) {
		this.ctn = ctn;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getProcYnNm() {
		return procYnNm;
	}

	public void setProcYnNm(String procYnNm) {
		this.procYnNm = procYnNm;
	}

	public String getDlvryYn() {
		return dlvryYn;
	}

	public void setDlvryYn(String dlvryYn) {
		this.dlvryYn = dlvryYn;
	}

	public String getReqInTime() {
		return reqInTime;
	}

	public void setReqInTime(String reqInTime) {
		this.reqInTime = reqInTime;
	}

	public String getNfcUsimYn() {
		return nfcUsimYn;
	}

	public void setNfcUsimYn(String nfcUsimYn) {
		this.nfcUsimYn = nfcUsimYn;
	}

	public String getNfcUsimType() {
		return nfcUsimType;
	}

	public void setNfcUsimType(String nfcUsimType) {
		this.nfcUsimType = nfcUsimType;
	}

	public String getUsimModelCd() {
		return usimModelCd;
	}

	public void setUsimModelCd(String usimModelCd) {
		this.usimModelCd = usimModelCd;
	}

	public String getUsimModelId() {
		return usimModelId;
	}

	public void setUsimModelId(String usimModelId) {
		this.usimModelId = usimModelId;
	}
	
}