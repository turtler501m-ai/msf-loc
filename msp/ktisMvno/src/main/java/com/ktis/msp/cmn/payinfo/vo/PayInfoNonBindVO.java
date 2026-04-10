package com.ktis.msp.cmn.payinfo.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PayInfoNonBindVO
 * @Description : PayInfo VO
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.08.01    김용문   최초생성
 * @
 * @author : 김용문
 * @Create Date : 2016. 8. 01.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="payInfoNonBindVO")
public class PayInfoNonBindVO extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076215739607234108L;

	String contractNum; // 가입계약번호
	String ban; // 청구계정번호
	String fileName; // 파일명
	String filePath; // 파일경로
	String ext; // 파일확장자
	String regstId; // 등록ID
	String rvisnId; // 수정ID
	/**
     * 2016-08-01 증거파일에 계좌정보 입력 처리.
     * 김용문
     */
    private String dpstBankCd;   // 은행코드
    private String dpstAcntNum;  // 계좌번호
    private String dpstPrsnNm;   // 예금주명
    private String fileName1;
	
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
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
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
}
