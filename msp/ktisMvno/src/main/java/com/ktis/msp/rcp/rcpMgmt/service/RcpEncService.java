/**
 * 
 */
package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.shopmgmt.vo.ShopMgmtVO;
import com.ktis.msp.rcp.formVrfyMgmt.vo.FormVrfyMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OpenInfoVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service
public class RcpEncService extends BaseService {
	@Crypto(encryptName="DBMSEnc", fields = {"userName", "passwd"})
	public List<EgovMap> encryptDBMSList2(List<EgovMap> list){
		return list;
	}
	
	/*
	@Crypto(decryptName="DBMSDec", fields = { "cstmrNativeRrn", "reqCardNo", "cstmrForeignerPn", "reqCardRrn", "minorAgentRrn", "reqAccountRrn", "reqAccountNumber" ,"ssn1","ssn2","ssn", "cstmrForeignerRrn"})//"bankAcctHolderId",2015년 1월 8일 리스트에 없는 항목 복호화하는부분 제외
	public List<EgovMap> decryptDBMSRcpMngtList(List<EgovMap> list){
		return list;
	}
	*/
	
	//2015년 1월20일 개통관리 청구이력에서 실납부자 주민번호 복호화
	@Crypto(decryptName="DBMSDec", fields = {"bankAcctHolderId"})
	public List<EgovMap> decryptDBMSCustMngtBankAcctHolderId(List<EgovMap> list){
		return list;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"bankAcctHolderId"})
	public List<?> decryptDBMSCustMngtBankAcctHolderIdNotEgovMap(List<?> list){
		return list;
	}
	
	//2015년 1월21일 개통관리 리스트 복호화시 사용 필드 
	@Crypto(decryptName="DBMSDec", fields = {"reqCardRrn" , "minorAgentRrn" , "reqCardNo", "reqAccountRrn" , "reqAccountNumber" , "ssn1" , "ssn" , "cstmrForeignerRrn"})//2015년 1월 26일 ssn2(암호화된 필드가 아님)제거 
	public List<EgovMap> decryptDBMSCustMngtList(List<EgovMap> list){
		return list;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"reqCardRrn" , "minorAgentRrn" , "reqCardNo", "reqAccountRrn" , "reqAccountNumber" , "ssn1" , "ssn" , "cstmrForeignerRrn"})//2015년 1월 26일 ssn2(암호화된 필드가 아님)제거 
	public List<?> decryptDBMSCustMngtListNotEgovMap(List<?> list){
		return list;
	}
		
	//2015년 1월21일 개통관리 리스트 excel 복호화시 사용 필드 
	@Crypto(decryptName="DBMSDec", fields = {"cstmrNativeRrn" , "cstmrForeignerRrn"})
	public List<EgovMap> decryptDBMSCustMngtListExcel(List<EgovMap> list){
		return list;
	}
	
	//2015년 1월21일 신청관리 리스트 복호화시 사용 필드
	@Crypto(decryptName="DBMSDec", fields = { "cstmrNativeRrn" , "reqCardNo" , "cstmrForeignerPn" , "reqCardRrn" , "minorAgentRrn" , "reqAccountRrn" , "reqAccountNumber" , "cstmrForeignerRrn", "selfIssuNum", "minorSelfIssuNum"})
	public List<EgovMap> decryptDBMSRcpMngtList(List<EgovMap> list){
		return list;
	}
	//2015년 1월21일 신청관리 리스트 엑셀 복호화시 사용 필드
	@Crypto(decryptName="DBMSDec", fields = { "cstmrNativeRrn" , "cstmrForeignerPn"})
	public List<EgovMap> decryptDBMSRcpMngtListExcel(List<EgovMap> list){
		return list;
	}
	
	@Crypto(decryptName="DBMSDec", fields = { "cstmrNativeRrn", "reqCardNo", "cstmrForeignerPn", "reqCardRrn", "minorAgentRrn", "reqAccountRrn", "reqAccountNumber" ,"ssn1","ssn2","ssn" ,"bankAcctHolderId", "cstmrForeignerRrn"})
	public EgovMap decryptDBMSRcpMngt(EgovMap vo){
		return vo;
	}
	
	@Crypto(encryptName="DBMSEnc", fields = {"cstmrNativeRrn", "cstmrForeignerRrn", "reqAccountNumber", "reqCardNo", "minorAgentRrn",
			"cstmrForeignerPn","othersPaymentRrn","jrdclAgentRrn","entrustResRrn","reqAccountRrn","reqCardRrn", "selfIssuNum", "minorSelfIssuNum"})
	public RcpDetailVO encryptDBMSRcpDetailVO(RcpDetailVO vo){
		return vo;
	}
	
	@Crypto(decryptName="DBMSDec", fields = {"cstmrNativeRrn", "cstmrForeignerRrn", "reqAccountNumber", "reqCardNo", "minorAgentRrn",
			"cstmrForeignerPn","othersPaymentRrn","jrdclAgentRrn","entrustResRrn","reqAccountRrn","reqCardRrn", "selfIssuNum", "minorSelfIssuNum"})
	public RcpDetailVO decryptDBMSRcpDetailVO(RcpDetailVO vo){
		return vo;
	}
	
	//2015년 1월28일 신청관리 리스트 복호화시 사용 필드
	@Crypto(decryptName="DBMSDec", fields = { "cstmrNativeRrn", "cstmrForeignerRrn", "ssn" })
	public List<EgovMap> decryptDBMSRcpMngtList2(List<EgovMap> list){
		return list;
	}
	
	// 판매점관리 대표자 주민번호 암호화
	@Crypto(encryptName="DBMSEnc", fields = {"rprsenRrnum"})
	public ShopMgmtVO encryptDBMSShopMgmtVO(ShopMgmtVO vo){
		return vo;
	}
	
	// 판매점관리 대표자 주민번호 복호화
	@Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> decryptDBMSShopMgmt(List<?> list){
		return list;
	}
	
	//v2017.03 신청관리 간소화 개통관리 신규 화면
	@Crypto(decryptName="DBMSDec", fields = { "cstmrNativeRrn" , "reqCardNo" , "cstmrForeignerPn" , "reqCardRrn" , "minorAgentRrn" , "reqAccountRrn" , "reqAccountNumber" , "cstmrForeignerRrn"})
	public List<OpenInfoVO> decryptDBMSOpenInfo(List<OpenInfoVO> list){
		return list;
	}
	
	//주민번호 복호화
	@Crypto(decryptName="DBMSDec", fields = { "ssn" })
	public List<EgovMap> decryptSSNList(List<EgovMap> list){
		return list;
	}
	
	// 서식지검증 복호화
	@Crypto(decryptName="DBMSDec", fields = {"reqCardRrn" , "minorAgentRrn" , "reqCardNo", "reqAccountRrn" , "reqAccountNumber" , "ssn1" , "ssn" , "cstmrForeignerRrn"})
	public FormVrfyMgmtVO decryptDBMSFormVrfyMgmtVO(FormVrfyMgmtVO vo){
		return vo;
	}
}
