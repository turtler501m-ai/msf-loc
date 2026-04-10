package com.ktis.msp.rcp.idntMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.idntMgmt.mapper.IdntMgmtMapper;
import com.ktis.msp.rcp.idntMgmt.vo.IdntMgmtVO;

@Service("idntMgmtService")
public class IdntMgmtService extends BaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdntMgmtService.class);
	
	@Resource(name = "idntMgmtMapper")
	private IdntMgmtMapper idntMgmtMapper;
	
	@Resource(name = "idntMgmtCryptoService")
	private IdntMgmtCryptoService idntMgmtCryptoService;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * 가입자확인 목록조회
	 * @param idntMgmtVO
	 * @return
	 */
	public List<?> getInvstReqList(IdntMgmtVO idntMgmtVO, Map<String, Object> paramMap){
		
		// 검색유형이 전화번호를 제외하고 암호화
		if (!"00".equals(idntMgmtVO.getSearchGb()) && !"02".equals(idntMgmtVO.getSearchGb()) && !"03".equals(idntMgmtVO.getSearchGb()) && !"08".equals(idntMgmtVO.getSearchGb()) && (idntMgmtVO.getSearchVal() != null && !"".equals(idntMgmtVO.getSearchVal()))) {
			idntMgmtCryptoService.encryptDBMS(idntMgmtVO);
		}

		List<?> list = idntMgmtMapper.getInvstReqList(idntMgmtVO);
		
		idntMgmtCryptoService.decryptDBMSList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
	 * 가입자확인 엑셀저장
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getInvstReqListEx(IdntMgmtVO idntMgmtVO, Map<String, Object> paramMap){
		// 검색유형이 사업자번호, 법인번호, 전화번호, 계약번호를 제외하고 암호화를 제외하고 암호화
		if (!"00".equals(idntMgmtVO.getSearchGb()) && !"02".equals(idntMgmtVO.getSearchGb()) && !"03".equals(idntMgmtVO.getSearchGb()) && !"08".equals(idntMgmtVO.getSearchGb())  && (idntMgmtVO.getSearchVal() != null && !"".equals(idntMgmtVO.getSearchVal()))) {
			idntMgmtCryptoService.encryptDBMS(idntMgmtVO);
		}
		
		List<?> list = idntMgmtMapper.getInvstReqListEx(idntMgmtVO);
		
		idntMgmtCryptoService.decryptDBMSList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	/**
	 * 엑셀다운로드(포함된 수사기관명,문서번호) 조회
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getInvstNmValListEx(IdntMgmtVO idntMgmtVO, Map<String, Object> paramMap){
		// 검색유형이 사업자번호, 법인번호, 전화번호, 계약번호를 제외하고 암호화를 제외하고 암호화
		if (!"00".equals(idntMgmtVO.getSearchGb()) && !"02".equals(idntMgmtVO.getSearchGb()) && !"03".equals(idntMgmtVO.getSearchGb()) && !"08".equals(idntMgmtVO.getSearchGb()) && (idntMgmtVO.getSearchVal() != null && !"".equals(idntMgmtVO.getSearchVal()))) {
			idntMgmtCryptoService.encryptDBMS(idntMgmtVO);
		}
		
		List<?> list = idntMgmtMapper.getInvstNmValListEx(idntMgmtVO);
		
		idntMgmtCryptoService.decryptDBMSList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	/**
	 * 가입자확인 엑셀저장(선택된 정보만 조회)
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getInvstReqListSelectedEx(IdntMgmtVO idntMgmtVO, Map<String, Object> paramMap){
		// 검색유형이 사업자번호, 법인번호, 전화번호, 계약번호를 제외하고 암호화를 제외하고 암호화
		if (!"00".equals(idntMgmtVO.getSearchGb()) && !"02".equals(idntMgmtVO.getSearchGb()) && !"03".equals(idntMgmtVO.getSearchGb()) && !"08".equals(idntMgmtVO.getSearchGb()) && (idntMgmtVO.getSearchVal() != null && !"".equals(idntMgmtVO.getSearchVal()))) {
			idntMgmtCryptoService.encryptDBMS(idntMgmtVO);
		}
		
		List<?> list = idntMgmtMapper.getInvstReqListSelectedEx(idntMgmtVO);
		
		idntMgmtCryptoService.decryptDBMSList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	/**
	 * 가입자조회
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getInvstCusInfo(IdntMgmtVO idntMgmtVO, Map<String, Object> paramMap){
		
		if (KtisUtil.isEmpty(idntMgmtVO.getSearchVal())) {
			throw new MvnoRunException(-1, "검색어를 입력해주세요.");
		}

		List<?> list = new ArrayList();
		
		// 엑셀업로드한 자료 validation
		if("Y".equals(idntMgmtVO.getExcelYn())){
			getInvstCusInfoValidation(idntMgmtVO);
		}
		
		//식별유형이 사업자번호, 법인번호, 전화번호, 계약번호를 제외하고 암호화
		if (!"00".equals(idntMgmtVO.getSearchGb()) && !"02".equals(idntMgmtVO.getSearchGb()) && !"03".equals(idntMgmtVO.getSearchGb()) &&!"08".equals(idntMgmtVO.getSearchGb())) {
			idntMgmtCryptoService.encryptDBMS(idntMgmtVO);
		}
		
		LOGGER.info("**************************************************************");
		LOGGER.info("* 암복호화 조회 : " + idntMgmtVO.getSearchGb());
		LOGGER.info("* 암복호화 조회 : " + idntMgmtVO.getSearchVal());
		LOGGER.info("**************************************************************");
		
		
		if("00".equals(idntMgmtVO.getSearchGb())) {
			//전화번호 검색
			list = idntMgmtMapper.getCusInfoByTelnum(idntMgmtVO);
		} else if("02".equals(idntMgmtVO.getSearchGb())) { // 법인번호 검색
			list = idntMgmtMapper.getCusInfoByTaxNo(idntMgmtVO);
		} else if("03".equals(idntMgmtVO.getSearchGb())) { // 사업자번호 검색
			list = idntMgmtMapper.getCusInfoByDriNo(idntMgmtVO);
		} else if("08".equals(idntMgmtVO.getSearchGb())){	// 계약번호 검색
			list = idntMgmtMapper.getCusInfoByContractNum(idntMgmtVO);
		}
		else{ // 주민번호 검색
			list = idntMgmtMapper.getCusInfoByIdntNo(idntMgmtVO);
		}
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subscriberNoMsk","MOBILE_PHO");
		maskFields.put("userSsnMsk","SSN");
		maskFields.put("cstmrNmMsk","CUST_NAME");
		maskFields.put("cstmrAddrMsk","ADDR");
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list; 
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int savInvstReq(IdntMgmtVO idntMgmtVO){
		
		int returnCnt = 0;
		String appOdty = null;
		// 고객식별번호 암호화
		if("02".equals((String) idntMgmtVO.getSearchGb()) || "03".equals((String) idntMgmtVO.getSearchGb())){
				idntMgmtCryptoService.decryptDBM(idntMgmtVO);
		}
		
		String cusType = "";
		cusType = idntMgmtMapper.getCusType(idntMgmtVO);
		
		if( !"I".equals(cusType))
		{
			idntMgmtCryptoService.decryptDBM(idntMgmtVO);
		}
		
		logger.debug("#######################################");
		logger.debug("idntMgmtVO.getInvstType() >>" + idntMgmtVO.getInvstType() );
		logger.debug("#######################################");
		logger.debug("idntMgmtVO.getAppOdty() >>" + idntMgmtVO.getAppOdty() );
		
		if("10".equals(idntMgmtVO.getInvstType()) ){
			if("00".equals(idntMgmtVO.getAppOdty())){
				idntMgmtVO.setAppOdtyNm(idntMgmtVO.getAppOdtyNm());
			}else{
				idntMgmtVO.setSearchVal("CMN0112");
				appOdty = idntMgmtMapper.getAppOdtyNm(idntMgmtVO);
				idntMgmtVO.setAppOdtyNm(appOdty);
			}
		}else if("20".equals(idntMgmtVO.getInvstType())){
			if("00".equals(idntMgmtVO.getAppOdty())){
				idntMgmtVO.setAppOdtyNm(idntMgmtVO.getAppOdtyNm());
			}else {
				idntMgmtVO.setSearchVal("CMN0111");
				appOdty = idntMgmtMapper.getAppOdtyNm(idntMgmtVO);
				idntMgmtVO.setAppOdtyNm(appOdty);
			}
		}else{
			idntMgmtVO.setAppOdtyNm(idntMgmtVO.getAppOdtyNm());
		}
		
		idntMgmtMapper.savInvstReq(idntMgmtVO);
		return returnCnt;
	}

	//@Transactional(rollbackFor=Exception.class)
	public int getBeforeSaveCheck(IdntMgmtVO idntMgmtVO){
		
		int returnCnt = 0;
		String appOdty = null;
		// 고객식별번호 암호화
		if("02".equals((String) idntMgmtVO.getSearchGb()) || "03".equals((String) idntMgmtVO.getSearchGb())){
				idntMgmtCryptoService.decryptDBM(idntMgmtVO);
		}
		
		String cusType = "";
		cusType = idntMgmtMapper.getCusType(idntMgmtVO);
		
		if( !"I".equals(cusType))
		{
			idntMgmtCryptoService.decryptDBM(idntMgmtVO);
		}
		
		logger.debug("#######################################");
		logger.debug("idntMgmtVO.getInvstType() >>" + idntMgmtVO.getInvstType() );
		logger.debug("#######################################");
		logger.debug("idntMgmtVO.getAppOdty() >>" + idntMgmtVO.getAppOdty() );
		
		if("10".equals(idntMgmtVO.getInvstType()) ){
			if("00".equals(idntMgmtVO.getAppOdty())){
				idntMgmtVO.setAppOdtyNm(idntMgmtVO.getAppOdtyNm());
			}else{
				idntMgmtVO.setSearchVal("CMN0112");
				appOdty = idntMgmtMapper.getAppOdtyNm(idntMgmtVO);
				idntMgmtVO.setAppOdtyNm(appOdty);
			}
		}else if("20".equals(idntMgmtVO.getInvstType())){
			if("00".equals(idntMgmtVO.getAppOdty())){
				idntMgmtVO.setAppOdtyNm(idntMgmtVO.getAppOdtyNm());
			}else {
				idntMgmtVO.setSearchVal("CMN0111");
				appOdty = idntMgmtMapper.getAppOdtyNm(idntMgmtVO);
				idntMgmtVO.setAppOdtyNm(appOdty);
			}
		}else{
			idntMgmtVO.setAppOdtyNm(idntMgmtVO.getAppOdtyNm());
		}
		
		returnCnt = idntMgmtMapper.getBeforeSaveCheck(idntMgmtVO);
		return returnCnt;
	}

	public void getInvstCusInfoValidation(IdntMgmtVO idntMgmtVO) {
		/** 20230518 PMD 조치 */
		if("".equals(idntMgmtVO.getSearchGb()) || idntMgmtVO.getSearchGb()==null){
			throw new MvnoRunException(-1, "검색구분이 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getSearchVal()) || idntMgmtVO.getSearchVal()==null){
			throw new MvnoRunException(-1, "검색어가 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getReqType()) || idntMgmtVO.getReqType()==null){ 
			throw new MvnoRunException(-1, "요청종류가 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getInvstType()) || idntMgmtVO.getInvstType()==null){ 
			throw new MvnoRunException(-1, "접수처가 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getInvstNm()) || idntMgmtVO.getInvstNm()==null){ 
			throw new MvnoRunException(-1, "접수처명이 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getInvstLoc()) || idntMgmtVO.getInvstLoc()==null){ 
			throw new MvnoRunException(-1, "지역이 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getInvstVal()) || idntMgmtVO.getInvstVal()==null){ 
			throw new MvnoRunException(-1, "문서번호가 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getReqOdty()) || idntMgmtVO.getReqOdty()==null){ 
			throw new MvnoRunException(-1, "요청자 직책이 누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getReqUser()) || idntMgmtVO.getReqUser()==null){ 
			throw new MvnoRunException(-1, "요청자 이름이 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getAppOdty()) || idntMgmtVO.getAppOdty()==null){ 
			throw new MvnoRunException(-1, "결재권자 직책이 누락누락되었습니다.");
		}
		if(("00".equals(idntMgmtVO.getAppOdty()) && ("".equals(idntMgmtVO.getAppOdtyNm())) || idntMgmtVO.getAppOdtyNm()==null )){ 
			throw new MvnoRunException(-1, "결재권자 직책명이 누락누락되었습니다.");
		}
		if("".equals(idntMgmtVO.getAppUser())|| idntMgmtVO.getAppUser()==null){ 
			throw new MvnoRunException(-1, "결재권자 이름이 누락누락되었습니다.");
		}
		if(idntMgmtVO.getReqOdty().length() > 60){ 
			throw new MvnoRunException(-1, "요청자 직책 데이터길이가 초과되었습니다.");
		}
		if(idntMgmtVO.getReqUser().length() > 100){ 
			throw new MvnoRunException(-1, "요청자 이름 데이터길이가 초과되었습니다.");
		}
		if(idntMgmtVO.getAppOdty().length() > 60){ 
			throw new MvnoRunException(-1, "결재권자 직책 데이터길이가 초과되었습니다.");
		}
		if(idntMgmtVO.getAppUser().length() > 100){ 
			throw new MvnoRunException(-1, "결재권자 이름 데이터길이가 초과되었습니다.");
		}
		if(idntMgmtVO.getReqRsn().length() > 100){ 
			throw new MvnoRunException(-1, "사후요청 데이터길이가 초과되었습니다.");
		}
		if(idntMgmtVO.getReqDttm().length() >8){ 
			throw new MvnoRunException(-1, "요청일자 데이터길이가 초과되었습니다.");
		}
		
		String codeChk = null;
		CmnCdMgmtVo vo = new CmnCdMgmtVo();
		vo.setGrpId("RCP0022");
		vo.setCdVal(idntMgmtVO.getSearchGb());
		codeChk = idntMgmtMapper.getInvstCusInfoValidation(vo);
		if(!("1".equals(codeChk))){ 
			throw new MvnoRunException(-1, "검색구분 데이터를 확인해주세요.");
		}
		
		vo.setGrpId("CMN0108");
		vo.setCdVal(idntMgmtVO.getReqType());
		codeChk = idntMgmtMapper.getInvstCusInfoValidation(vo);
		if(!("1".equals(codeChk))){ 
			throw new MvnoRunException(-1, "요청종류 데이터를 확인해주세요.");
		}

		vo.setGrpId("CMN0107");
		vo.setCdVal(idntMgmtVO.getInvstType());
		codeChk = idntMgmtMapper.getInvstCusInfoValidation(vo);
		if(!("1".equals(codeChk))){ 
			throw new MvnoRunException(-1, "접수처 데이터를 확인해주세요.");
		}

		vo.setGrpId("RCP9020");
		vo.setCdVal(idntMgmtVO.getInvstLoc());
		codeChk = idntMgmtMapper.getInvstCusInfoValidation(vo);
		if(!("1".equals(codeChk))){ 
			throw new MvnoRunException(-1, "지역 데이터를 확인해주세요.");
		}
		/** 20230518 PMD 조치 */
		if("10".equals(idntMgmtVO.getInvstType())){ 
			vo.setGrpId("CMN0112");
			vo.setCdVal(idntMgmtVO.getInvstLoc());
			codeChk = idntMgmtMapper.getInvstCusInfoValidation(vo);
			if(!("1".equals(codeChk))){ 
				throw new MvnoRunException(-1, "결재권자직책코드 데이터를 확인해주세요.");
			}
		}else if("20".equals(idntMgmtVO.getInvstType())){
			vo.setGrpId("CMN0111");
			vo.setCdVal(idntMgmtVO.getInvstLoc());
			codeChk = idntMgmtMapper.getInvstCusInfoValidation(vo);
			if(!("1".equals(codeChk))){ 
				throw new MvnoRunException(-1, "결재권자직책코드 데이터를 확인해주세요.");
			}
		}
				
	}
	
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("userSsn","SSN");
		maskFields.put("cstmrNm","CUST_NAME");
		maskFields.put("cstmrAddr","ADDR");
		
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("cstmrNativeRrn","SSN");
		maskFields.put("cstmrForeignerRrn","SSN");
		maskFields.put("cstmrForeignerPn","PASSPORT");
		maskFields.put("cstmrJuridicalRrn","CORPORATE");
		maskFields.put("cstmrMail","EMAIL");
		maskFields.put("email","EMAIL");
		maskFields.put("cstmrTel","MOBILE_PHO");
		maskFields.put("cstmrMobile","MOBILE_PHO");
		maskFields.put("faxnum","MOBILE_PHO");
		maskFields.put("dlvryTel","MOBILE_PHO");
		maskFields.put("reqAccountNumber","ACCOUNT");
		maskFields.put("reqCardNo","CREDIT_CAR");
		maskFields.put("reqCardMm","CARD_EXP");
		maskFields.put("reqCardYy","CARD_EXP");
		maskFields.put("reqAccountName","CUST_NAME");
		maskFields.put("reqAccountRrn","SSN");
		maskFields.put("reqCardName","CUST_NAME");
		maskFields.put("reqCardRrn","SSN");
		maskFields.put("reqGuide","MOBILE_PHO");
		maskFields.put("moveMobile","MOBILE_PHO");
		maskFields.put("minorAgentName","CUST_NAME");
		maskFields.put("minorAgentRrn","SSN");
		maskFields.put("minorAgentTel","MOBILE_PHO");
		maskFields.put("jrdclAgentName","CUST_NAME");
		maskFields.put("jrdclAgentRrn","SSN");
		maskFields.put("jrdclAgentTel","MOBILE_PHO");
		maskFields.put("reqUsimSn","USIM");
		maskFields.put("reqPhoneSn","DEV_NO");
		maskFields.put("iccId","USIM");
		maskFields.put("intmSrlNo","DEV_NO");
		maskFields.put("cstmrAddr","ADDR");
		maskFields.put("dlvryAddr","ADDR");
		maskFields.put("realUseCustNm","CUST_NAME");
		maskFields.put("cstmrAddrDtl","PASSWORD");
		maskFields.put("customerAdr","ADDR");
		maskFields.put("dlvryName","CUST_NAME");
		maskFields.put("dlvryMobile","MOBILE_PHO");
		maskFields.put("dlvryAddrDtl","PASSWORD");
		maskFields.put("customerLinkName","CUST_NAME");
		maskFields.put("ssn","SSN");
		maskFields.put("ssn1","SSN");
		maskFields.put("ssn2","SSN");
		maskFields.put("ssn3","SSN");
		// 개통관리 엑셀다운로드시 최초단말일련번호 마스킹적용
		maskFields.put("fstModelSrlNum","DEV_NO");
		maskFields.put("lstModelSrlNum","DEV_NO");

		maskFields.put("reqUser","CUST_NAME"); //요청자
		maskFields.put("procrNm","CUST_NAME"); //결제권자
		maskFields.put("appUser","CUST_NAME"); //처리자
		
		return maskFields;
	}

	/**
	 * 번호이동 상세 이력
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getInvstCusInfoDtl(IdntMgmtVO idntMgmtVO, Map<String, Object> paramMap){
		
		List<?> list = new ArrayList();
		
		list = idntMgmtMapper.getInvstCusInfoDtl(idntMgmtVO);

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subscriberNoMsk","MOBILE_PHO");
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list; 
	}
	

	/**
	 * 가입자확인 삭제
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteMspInvstReqMst(IdntMgmtVO idntMgmtVO){
		
		int returnCnt = 0;
		
		returnCnt = idntMgmtMapper.deleteMspInvstReqMst(idntMgmtVO);
		return returnCnt;
	}

	/**
	 * 가입자확인 이력 생성
	 * @param idntMgmtVO
	 * @param paramMap
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int savMspInvstReqHist(IdntMgmtVO idntMgmtVO){
		
		int returnCnt = 0;
		
		returnCnt = idntMgmtMapper.savMspInvstReqHist(idntMgmtVO);
		return returnCnt;
	}

}
