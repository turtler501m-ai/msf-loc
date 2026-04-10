package com.ktis.msp.gift.taxmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.gift.taxmgmt.vo.TaxMgmtVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("taxMgmtMapper")
public interface TaxMgmtMapper {

	/* 제세공과금 taxNo 생성 */
	String taxSmsListTaxNo(TaxMgmtVO taxMgmtVo);
   /* 제세공과금 리스트 조회	*/
	List<?> getTaxSmsSendList(TaxMgmtVO taxMgmtVo);
   /* 제세공과금 상세리스트 조회 */
	List<?> getTaxSmsSendListDt(TaxMgmtVO taxMgmtVo);	  
	/* 제세공과금 상세리스트 엑셀다운로드*/
	List<?> getTaxSmsSendListByExcel(TaxMgmtVO taxMgmtVo);	  
	/* 제세공과금 등록된 계약번호인지 확인*/
	int getContractNumChk(TaxMgmtVO taxMgmtVo);
	/* 제세공과금 계약번호 중복등록 확인*/
	int getContractNumDupChk(TaxMgmtVO taxMgmtVo);
    /* 제세공과금 insert */
	void insertTaxSmsTmp (TaxMgmtVO taxMgmtVo);
	/* 제세공과금 상세 insert */
	void insertTaxSmsSendTmp (TaxMgmtVO taxMgmtVo);
	/* 제세공과금 리스트 삭제 권한 확인 */
	String isTaxSmsDeleteAuth(TaxMgmtVO taxMgmtVo);
    /* 제세공과금 리스트 삭제	*/
	int deleteTaxSmsList(TaxMgmtVO taxMgmtVo);
	/* 제세공과금 리스트 상세내용 삭제*/
	int deleteTaxSmsListDt(TaxMgmtVO taxMgmtVo);
	/* 엑셀업로드 후 MSG_SMS_LIST 에 SEND_CNT 업데이트 */
	int getTaxSendListCount(TaxMgmtVO taxMgmtVo);
	/* SMS 템플릿 번호 조회해서 일치하는 메세지타입 가져오기 */
	Map<String,Object> getTaxSendTaxType(TaxMgmtVO taxMgmtVo);
	/* (법정대리인)SMS 템플릿 번호 조회해서 일치하는 메세지타입 가져오기 */
	Map<String,Object> getAgentSendTaxType(TaxMgmtVO taxMgmtVo);
	/* SMS 템플릿 K_TEMPLATE_CODE 조회해서 코드값이 등록되어 있는지 확인*/
	String getTaxSendKakaoYn(TaxMgmtVO taxMgmtVo);
	/* (법정대리인)SMS 템플릿 K_TEMPLATE_CODE 조회해서 코드값이 등록되어 있는지 확인*/
	String getAgentSendKakaoYn(TaxMgmtVO taxMgmtVo);
	/* SMS 템플릿 번호 조회해서 일치하는 메세지타입 가져오기 */
	int getTaxTmpCallBack(TaxMgmtVO taxMgmtVo);
	/* 제세공과금 해당일자 해당시간 전송가능여부 체크(동일시간대 등록 불가) */
	int getChkTaxCnt(TaxMgmtVO taxMgmtVo);
}
