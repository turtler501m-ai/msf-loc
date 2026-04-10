package com.ktis.msp.cmn.payinfo.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PayInfoDtlVO
 * @Description : PAYINFO 관리 상세
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.07.14  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 7. 14.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="payInfoDtlVO")
public class PayInfoDtlVO extends BaseVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7103132702239592394L;
	
    private int kftcMstSeq; /** PAYINFO MST SEQ */
    private int kftcDtlSeq; /** PAYINFO DTL SEQ */
    private String billAcntNo; /** [전문]청구계정번호 */
    private String ban;
    private String realFilePath;
    private String realFileNm;
    private String contractNum; /** 가입계약번호 */
    private String subscriberNo; /** 전화번호 이전CTN */
    private String subLinkName; /** 사용자이름 */
    private String agreMngmNo; /** [전문]동의 관리번호 */
    private String rgstDt; /** [전문]등록일자 */
    private String agreTypeCd; /** [전문][CMN0205]동의구분코드 */
    private String slsCmpnCd; /** [전문]사업자코드 */
    private String bankCd; /** [전문]은행코드 */
    private String bankCdNm;/** [전문]은행코드명 */
    private String cmsAplySrlNo; /** [전문]CMS등록일련번호 */
    private String outpymSrlNo; /** [전문]출금일련번호 */
    private String bankBnkacnNo; /** [전문]계좌번호 뒷자리8자리는 *로 처리 */
    private String realFileName; /** [전문]동의관련 실파일명 */
    private String realFileDir; /** 동의파일 full path */
    private String errorCd; /** [전문][CMN0206]오류코드 */
    private String errorCdNm; /** [전문][CMN0206]오류코드 */
    private String readyCd; /** [CMN0207]실파일 송신준비 코드 */
    private String sendCd; /** [CMN0208]실파일 송신 코드 */
    private String regstId; /** 등록자ID */
    private String regstDttm; /** 등록일시 */
    private String rvisnId; /** 수정자ID */
    private String rvisnDttm; /** 수정일시 */    
    private String searchStartDt; /** 조회시작일 */
    private String searchEndDt; /** 조회종료일 */
    private String fileRegYn; /** 증거파일 등록 여부 */
    private String fileRegYnNm; /** 증거파일 등록 여부명 */
    private String searchGbn; //조회구분
    private String searchName; //조회값

    private String blBillingMethodNm; //납부방법명
    private String blBillingMethod; //납부방법코드
	private String subStatus; //고객상태
	private String lstComActvDate; //가입일자
	private String openAgntCd; //대리점코드
	private String openAgntNm; //대리점명
    private String addr; //주소
    private String onOffTypeNm; //온/오프라인구분
    
    private String approvalId; /** 승인자ID */
    private String approvalIdNm; /** 승인자명 */
    private String approvalDt; /** 승인일자 */
    private String approvalMsg; /** 승인사유 */
    private String approvalCd;  /** 승인결과 */
    private String approvalCdNm;  /** 승인결과명 */
    private String evidenceTypeCd; /** 등록파일유형 */
    private String evidenceTypeCdNm; /** 등록파일유형명 */
    private String orgnId; //조직ID
    
    /**
     * 2016-08-02 증거파일에 계좌정보 입력 처리.
     * 김용문
     */
    private String dpstBankCd;   // 은행코드
    private String dpstAcntNum;  // 계좌번호
    private String dpstPrsnNm;   // 예금주명
    private String deleteFileName;
    
    public String getDeleteFileName() {
		return deleteFileName;
	}

	public void setDeleteFileName(String deleteFileName) {
		this.deleteFileName = deleteFileName;
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

	public String getFileRegYn() {
		return fileRegYn;
	}


	public void setFileRegYn(String fileRegYn) {
		this.fileRegYn = fileRegYn;
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



	/**
	 * @return the kftcMstSeq
	 */
	public int getKftcMstSeq() {
		return kftcMstSeq;
	}


	/**
	 * @param kftcMstSeq the kftcMstSeq to set
	 */
	public void setKftcMstSeq(int kftcMstSeq) {
		this.kftcMstSeq = kftcMstSeq;
	}


	/**
	 * @return the kftcDtlSeq
	 */
	public int getKftcDtlSeq() {
		return kftcDtlSeq;
	}


	/**
	 * @param kftcDtlSeq the kftcDtlSeq to set
	 */
	public void setKftcDtlSeq(int kftcDtlSeq) {
		this.kftcDtlSeq = kftcDtlSeq;
	}


	/**
	 * @return the billAcntNo
	 */
	public String getBillAcntNo() {
		return billAcntNo;
	}


	/**
	 * @param billAcntNo the billAcntNo to set
	 */
	public void setBillAcntNo(String billAcntNo) {
		this.billAcntNo = billAcntNo;
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
	 * @return the agreMngmNo
	 */
	public String getAgreMngmNo() {
		return agreMngmNo;
	}


	/**
	 * @param agreMngmNo the agreMngmNo to set
	 */
	public void setAgreMngmNo(String agreMngmNo) {
		this.agreMngmNo = agreMngmNo;
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
	 * @return the agreTypeCd
	 */
	public String getAgreTypeCd() {
		return agreTypeCd;
	}


	/**
	 * @param agreTypeCd the agreTypeCd to set
	 */
	public void setAgreTypeCd(String agreTypeCd) {
		this.agreTypeCd = agreTypeCd;
	}


	/**
	 * @return the slsCmpnCd
	 */
	public String getSlsCmpnCd() {
		return slsCmpnCd;
	}


	/**
	 * @param slsCmpnCd the slsCmpnCd to set
	 */
	public void setSlsCmpnCd(String slsCmpnCd) {
		this.slsCmpnCd = slsCmpnCd;
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
	 * @param bankCd the bankCdNm to set
	 */
	public void setBankCdNm(String bankCdNm) {
		this.bankCdNm = bankCdNm;
	}
	

	/**
	 * @return the cmsAplySrlNo
	 */
	public String getCmsAplySrlNo() {
		return cmsAplySrlNo;
	}


	/**
	 * @param cmsAplySrlNo the cmsAplySrlNo to set
	 */
	public void setCmsAplySrlNo(String cmsAplySrlNo) {
		this.cmsAplySrlNo = cmsAplySrlNo;
	}


	/**
	 * @return the outpymSrlNo
	 */
	public String getOutpymSrlNo() {
		return outpymSrlNo;
	}


	/**
	 * @param outpymSrlNo the outpymSrlNo to set
	 */
	public void setOutpymSrlNo(String outpymSrlNo) {
		this.outpymSrlNo = outpymSrlNo;
	}


	/**
	 * @return the bankBnkacnNo
	 */
	public String getBankBnkacnNo() {
		return bankBnkacnNo;
	}


	/**
	 * @param bankBnkacnNo the bankBnkacnNo to set
	 */
	public void setBankBnkacnNo(String bankBnkacnNo) {
		this.bankBnkacnNo = bankBnkacnNo;
	}


	/**
	 * @return the realFileName
	 */
	public String getRealFileName() {
		return realFileName;
	}


	/**
	 * @param realFileName the realFileName to set
	 */
	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}


	/**
	 * @return the realFileDir
	 */
	public String getRealFileDir() {
		return realFileDir;
	}


	/**
	 * @param realFileDir the realFileDir to set
	 */
	public void setRealFileDir(String realFileDir) {
		this.realFileDir = realFileDir;
	}


	/**
	 * @return the errorCd
	 */
	public String getErrorCd() {
		return errorCd;
	}


	/**
	 * @param errorCd the errorCd to set
	 */
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}


	/**
	 * @return the readyCd
	 */
	public String getReadyCd() {
		return readyCd;
	}


	/**
	 * @param readyCd the readyCd to set
	 */
	public void setReadyCd(String readyCd) {
		this.readyCd = readyCd;
	}


	/**
	 * @return the sendCd
	 */
	public String getSendCd() {
		return sendCd;
	}


	/**
	 * @param sendCd the sendCd to set
	 */
	public void setSendCd(String sendCd) {
		this.sendCd = sendCd;
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
	 * @return the fileRegYnNm
	 */
	public String getFileRegYnNm() {
		return fileRegYnNm;
	}

	/**
	 * @param fileRegYnNm the fileRegYnNm to set
	 */
	public void setFileRegYnNm(String fileRegYnNm) {
		this.fileRegYnNm = fileRegYnNm;
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
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
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
	 * @return the evidenceTypeCdNm
	 */
	public String getEvidenceTypeCdNm() {
		return evidenceTypeCdNm;
	}

	/**
	 * @param evidenceTypeCdNm the evidenceTypeCdNm to set
	 */
	public void setEvidenceTypeCdNm(String evidenceTypeCdNm) {
		this.evidenceTypeCdNm = evidenceTypeCdNm;
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
	 * @return the errorCdNm
	 */
	public String getErrorCdNm() {
		return errorCdNm;
	}

	/**
	 * @param errorCdNm the errorCdNm to set
	 */
	public void setErrorCdNm(String errorCdNm) {
		this.errorCdNm = errorCdNm;
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

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
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
}
