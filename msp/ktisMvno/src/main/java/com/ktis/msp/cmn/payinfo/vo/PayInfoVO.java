package com.ktis.msp.cmn.payinfo.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PayInfoVO
 * @Description : PayInfo VO
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.07.11  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 7. 11.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="payInfoVO")
public class PayInfoVO extends BaseVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
    private static final long serialVersionUID = -350972720516546525L;
	
    private String kftcEvidenceSeq;	//순번
	private String contractNum; //계약번호
	private String svcCntrNo; //서비스계약번호
	private String telNo; //전화번호
	private String custName; //고객명
	private String subStatus; //고객상태
	private String lstComActvDate; //가입일자
	private String openAgntCd; //대리점코드
	private String openAgntNm; //대리점명
	
    private String searchStartDt; //조회시작일
    private String searchEndDt; //조회종료일
    private String searchGbn; //조회구분
    private String searchName; //조회값
    
    private String resultYn; //등록구분
    private String orgnId; //조직ID
    private String orgnNm; //조직명
    private String userSsn; //생년월일
    private String ext; //파일확장자
    private String regYn; //등록구분
    private String regYnNm; //등록구분명
    private String addr; //주소
    private String rgstDt; //등록일자
    private String validDt; //유효일자
    private String ban; //청구계정번호
    private String regstId; //등록자
    private String regstDttm; //등록일자
    private String rvisnId; //수정자
    private String rvisnDttm; //수정일자
    private String dateGbn; //일자구분
    private String realFilePath; //파일경로
    private String realFileNm; //파일명
    private String rgstInflowCd; //등록유입경로
    private String fileNmSeq; //파일이름 시퀀스
    private String fileId; //파일ID
    private String blBillingMethodNm; //납부방법명
    private String blBillingMethod; //납부방법코드
    private String onOffTypeNm; //온/오프라인구분

    private String approvalId; /** 승인자ID */
    private String approvalIdNm; /** 승인자명 */
    private String approvalDt; /** 승인일자 */
    private String approvalMsg; /** 승인사유 */
    private String approvalCd;  /** 승인결과 */
    private String approvalCdNm;  /** 승인결과명 */
    private String evidenceTypeCd; /** 등록파일유형 */
    private String eviCdNm; /** 등록파일유형명 */
    
    private String fileType;
    private String fileTypeCd;
    private String subscriberNo;
    private String subLinkName;
    private String fileName;
    private String filePath;
    private String bankCd;
    private String bankCdNm;
    private String bankCdNo;
    
    /**
     * 2016-08-01 증거파일에 계좌정보 입력 처리.
     * 김용문
     */
    private String dpstBankCd;   // 은행코드
    private String dpstAcntNum;  // 계좌번호
    private String dpstPrsnNm;   // 예금주명
    private String kftcDtlSeq;
    
    //v2018.11 PMD 적용 소스 수정
    private int kftcMstSeq;
    
    // 2018-12-20, 카드사 공통코드 조회시 사용
    private String expnsnVal;
    
    public String getKftcDtlSeq() {
		return kftcDtlSeq;
	}

	public void setKftcDtlSeq(String kftcDtlSeq) {
		this.kftcDtlSeq = kftcDtlSeq;
	}

	//private int kftcMstSeq;
    
	public int getKftcMstSeq() {
		return kftcMstSeq;
	}

	public void setKftcMstSeq(int kftcMstSeq) {
		this.kftcMstSeq = kftcMstSeq;
	}

	public String getDpstBankCd() {
		return dpstBankCd;
	}

	public void setDpstBankCd(String dpstBankCd) {
		this.dpstBankCd = dpstBankCd;
	}

	public String getDpstAcntNum() {
		return dpstAcntNum;
	}

	public void setDpstAcntNum(String dpstAcntNum) {
		this.dpstAcntNum = dpstAcntNum;
	}

	public String getDpstPrsnNm() {
		return dpstPrsnNm;
	}

	public void setDpstPrsnNm(String dpstPrsnNm) {
		this.dpstPrsnNm = dpstPrsnNm;
	}

	@Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
    public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getKftcEvidenceSeq() {
		return kftcEvidenceSeq;
	}


	public void setKftcEvidenceSeq(String kftcEvidenceSeq) {
		this.kftcEvidenceSeq = kftcEvidenceSeq;
	}


	public String getFileNmSeq() {
		return fileNmSeq;
	}


	public void setFileNmSeq(String fileNmSeq) {
		this.fileNmSeq = fileNmSeq;
	}


	public String getRgstInflowCd() {
		return rgstInflowCd;
	}


	public void setRgstInflowCd(String rgstInflowCd) {
		this.rgstInflowCd = rgstInflowCd;
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


	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}

	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}

	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	/**
	 * @return the lstComActvDate
	 */
	public String getLstComActvDate() {
		return lstComActvDate;
	}

	/**
	 * @param lstComActvDate the lstComActvDate to set
	 */
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}

	/**
	 * @return the openAgntCd
	 */
	public String getOpenAgntCd() {
		return openAgntCd;
	}

	/**
	 * @param openAgntCd the openAgntCd to set
	 */
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}

	/**
	 * @return the openAgntNm
	 */
	public String getOpenAgntNm() {
		return openAgntNm;
	}

	/**
	 * @param openAgntNm the openAgntNm to set
	 */
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}

	/**
	 * @return the searchStartDt
	 */
	public String getSearchStartDt() {
		return searchStartDt;
	}

	/**
	 * @param searchStartDt the searchStartDt to set
	 */
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	/**
	 * @return the searchEndDt
	 */
	public String getSearchEndDt() {
		return searchEndDt;
	}

	/**
	 * @param searchEndDt the searchEndDt to set
	 */
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}

	/**
	 * @return the searchGbn
	 */
	public String getSearchGbn() {
		return searchGbn;
	}

	/**
	 * @param searchGbn the searchGbn to set
	 */
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	/**
	 * @return the resultYn
	 */
	public String getResultYn() {
		return resultYn;
	}

	/**
	 * @param resultYn the resultYn to set
	 */
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}

	/**
	 * @return the orgnId
	 */
	public String getOrgnId() {
		return orgnId;
	}

	/**
	 * @param orgnId the orgnId to set
	 */
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	/**
	 * @return the orgnNm
	 */
	public String getOrgnNm() {
		return orgnNm;
	}

	/**
	 * @param orgnNm the orgnNm to set
	 */
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}

	/**
	 * @return the userSsn
	 */
	public String getUserSsn() {
		return userSsn;
	}

	/**
	 * @param userSsn the userSsn to set
	 */
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @return the regYn
	 */
	public String getRegYn() {
		return regYn;
	}

	/**
	 * @param regYn the regYn to set
	 */
	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}

	/**
	 * @return the rgstDt
	 */
	public String getRgstDt() {
		return rgstDt;
	}

	/**
	 * @param rgstDt the rgstDt to set
	 */
	public void setRgstDt(String rgstDt) {
		this.rgstDt = rgstDt;
	}

	/**
	 * @return the validDt
	 */
	public String getValidDt() {
		return validDt;
	}

	/**
	 * @param validDt the validDt to set
	 */
	public void setValidDt(String validDt) {
		this.validDt = validDt;
	}

	/**
	 * @return the ban
	 */
	public String getBan() {
		return ban;
	}

	/**
	 * @param ban the ban to set
	 */
	public void setBan(String ban) {
		this.ban = ban;
	}

	/**
	 * @return the regstId
	 */
	public String getRegstId() {
		return regstId;
	}

	/**
	 * @param regstId the regstId to set
	 */
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	/**
	 * @return the regstDttm
	 */
	public String getRegstDttm() {
		return regstDttm;
	}

	/**
	 * @param regstDttm the regstDttm to set
	 */
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}

	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}

	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}

	/**
	 * @return the rvisnDttm
	 */
	public String getRvisnDttm() {
		return rvisnDttm;
	}

	/**
	 * @param rvisnDttm the rvisnDttm to set
	 */
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}

	/**
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}

	/**
	 * @param searchName the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getDateGbn() {
		return dateGbn;
	}

	public void setDateGbn(String dateGbn) {
		this.dateGbn = dateGbn;
	}

	/**
	 * @return the regYnNm
	 */
	public String getRegYnNm() {
		return regYnNm;
	}


	/**
	 * @param regYnNm the regYnNm to set
	 */
	public void setRegYnNm(String regYnNm) {
		this.regYnNm = regYnNm;
	}


	/**
	 * @return the blBillingMethodNm
	 */
	public String getBlBillingMethodNm() {
		return blBillingMethodNm;
	}


	/**
	 * @param blBillingMethodNm the blBillingMethodNm to set
	 */
	public void setBlBillingMethodNm(String blBillingMethodNm) {
		this.blBillingMethodNm = blBillingMethodNm;
	}


	/**
	 * @return the blBillingMethod
	 */
	public String getBlBillingMethod() {
		return blBillingMethod;
	}


	/**
	 * @param blBillingMethod the blBillingMethod to set
	 */
	public void setBlBillingMethod(String blBillingMethod) {
		this.blBillingMethod = blBillingMethod;
	}

	/**
	 * @return the onOffTypeNm
	 */
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}

	/**
	 * @param onOffTypeNm the onOffTypeNm to set
	 */
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}

	/**
	 * @return the approvalId
	 */
	public String getApprovalId() {
		return approvalId;
	}

	/**
	 * @param approvalId the approvalId to set
	 */
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	/**
	 * @return the approvalIdNm
	 */
	public String getApprovalIdNm() {
		return approvalIdNm;
	}

	/**
	 * @param approvalIdNm the approvalIdNm to set
	 */
	public void setApprovalIdNm(String approvalIdNm) {
		this.approvalIdNm = approvalIdNm;
	}

	/**
	 * @return the approvalDt
	 */
	public String getApprovalDt() {
		return approvalDt;
	}

	/**
	 * @param approvalDt the approvalDt to set
	 */
	public void setApprovalDt(String approvalDt) {
		this.approvalDt = approvalDt;
	}

	/**
	 * @return the approvalMsg
	 */
	public String getApprovalMsg() {
		return approvalMsg;
	}

	/**
	 * @param approvalMsg the approvalMsg to set
	 */
	public void setApprovalMsg(String approvalMsg) {
		this.approvalMsg = approvalMsg;
	}

	/**
	 * @return the evidenceTypeCd
	 */
	public String getEvidenceTypeCd() {
		return evidenceTypeCd;
	}

	/**
	 * @param evidenceTypeCd the evidenceTypeCd to set
	 */
	public void setEvidenceTypeCd(String evidenceTypeCd) {
		this.evidenceTypeCd = evidenceTypeCd;
	}


	/**
	 * @return the approvalCd
	 */
	public String getApprovalCd() {
		return approvalCd;
	}

	/**
	 * @param approvalCd the approvalCd to set
	 */
	public void setApprovalCd(String approvalCd) {
		this.approvalCd = approvalCd;
	}

	/**
	 * @return the approvalCdNm
	 */
	public String getApprovalCdNm() {
		return approvalCdNm;
	}

	/**
	 * @param approvalCdNm the approvalCdNm to set
	 */
	public void setApprovalCdNm(String approvalCdNm) {
		this.approvalCdNm = approvalCdNm;
	}

	/**
	 * @return the eviCdNm
	 */
	public String getEviCdNm() {
		return eviCdNm;
	}

	/**
	 * @param eviCdNm the eviCdNm to set
	 */
	public void setEviCdNm(String eviCdNm) {
		this.eviCdNm = eviCdNm;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}

	/**
	 * @param subscriberNo the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	/**
	 * @return the subLinkName
	 */
	public String getSubLinkName() {
		return subLinkName;
	}

	/**
	 * @param subLinkName the subLinkName to set
	 */
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileTypeCd
	 */
	public String getFileTypeCd() {
		return fileTypeCd;
	}

	/**
	 * @param fileTypeCd the fileTypeCd to set
	 */
	public void setFileTypeCd(String fileTypeCd) {
		this.fileTypeCd = fileTypeCd;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the bankCd
	 */
	public String getBankCd() {
		return bankCd;
	}

	/**
	 * @param bankCd the bankCd to set
	 */
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	/**
	 * @return the bankCdNm
	 */
	public String getBankCdNm() {
		return bankCdNm;
	}

	/**
	 * @param bankCdNm the bankCdNm to set
	 */
	public void setBankCdNm(String bankCdNm) {
		this.bankCdNm = bankCdNm;
	}

	/**
	 * @return the bankCdNo
	 */
	public String getBankCdNo() {
		return bankCdNo;
	}

	/**
	 * @param bankCdNo the bankCdNo to set
	 */
	public void setBankCdNo(String bankCdNo) {
		this.bankCdNo = bankCdNo;
	}

	public String getExpnsnVal() {
		return expnsnVal;
	}

	public void setExpnsnVal(String expnsnVal) {
		this.expnsnVal = expnsnVal;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
}
