package com.ktis.msp.rcp.rcpMgmt.service;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.rcp.rcpMgmt.mapper.DvcChgMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.DvcChgMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class DvcChgMgmtService {
	private static final Log LOGGER = LogFactory.getLog(DvcChgMgmtService.class);
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Autowired
	private DvcChgMgmtMapper dvcChgMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 기변대상조회
	 */
	public List<?> getDvcChgTrgtList(DvcChgMgmtVO searchVO, Map<String, Object> paramMap) {
		
		LOGGER.debug("기변대상조회");
		
		if(searchVO.getCrtYm() == null || "".equals(searchVO.getCrtYm())){
			throw new MvnoRunException(-1, "추출월이 존재하지 않습니다.");
		}
		if("".equals(searchVO.getSrchTp()) && !"".equals(searchVO.getSrchVal())){
			throw new MvnoRunException(-1, "검색구분이 존재하지 않습니다.");
		}
		if(!"".equals(searchVO.getSrchTp()) && "".equals(searchVO.getSrchVal())){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}
		
		List<?> list =  dvcChgMapper.getDvcChgTrgtList(searchVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
	 * 기변대상조회 엑셀
	 */
	public List<?> getDvcChgTrgtListExcel(DvcChgMgmtVO searchVO, Map<String, Object> paramMap) {
		
		LOGGER.debug("기변대상조회엑셀");
		
		if(searchVO.getCrtYm() == null || "".equals(searchVO.getCrtYm())){
			throw new MvnoRunException(-1, "추출월이 존재하지 않습니다.");
		}
		if("".equals(searchVO.getSrchTp()) && !"".equals(searchVO.getSrchVal())){
			throw new MvnoRunException(-1, "검색구분이 존재하지 않습니다.");
		}
		if(!"".equals(searchVO.getSrchTp()) && "".equals(searchVO.getSrchVal())){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}
		
		List<?> list = dvcChgMapper.getDvcChgTrgtListExcel(searchVO); 
		
		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	private HashMap<String, String> getMaskFields() {
		LOGGER.debug("마스킹처리 대상");
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		// 기변상담할당에서 사용
		maskFields.put("custNmMask", "CUST_NAME");
		maskFields.put("subscriberNoMask", "MOBILE_PHO");
		// 기변목록에서 사용
		maskFields.put("cstmrName"    , "CUST_NAME");//고객명
		maskFields.put("intmSrlNo"    , "DEV_NO");//단말일련번호
		maskFields.put("dvcIntmSrlNo" , "DEV_NO");//단말일련번호
//		maskFields.put("subscriberNo" , "MOBILE_PHO");
		
		return maskFields;
	}
	
	/**
	 * 상담원조회
	 */
	public List<?> getAsgnTrgtPrsnList(){
		return dvcChgMapper.getAsgnTrgtPrsnList();
	}
	
	/**
	 * 상담원할당
	 */
	@Async
	@Transactional(rollbackFor=Exception.class)
	public void setDvcChgTrgtAsgnList(DvcChgMgmtVO vo){
		try{
			LOGGER.debug("vo=" + vo);
			
			// 상담원조회
			List<?> plist = dvcChgMapper.getAsgnTrgtPrsnList();
			int prsnCnt = plist.size();
			
			// 할당대상조회
			DvcChgMgmtVO searchVO = new DvcChgMgmtVO();
			searchVO.setCrtYm(vo.getCrtYm());
			searchVO.setTmStatCd("00"); // 등록상태
			
			List<?> tlist = dvcChgMapper.getDvcChgAsgnTrgtList(searchVO);
			int trgtCnt = tlist.size();
			
			// 1인당 평균건수
			int avgCnt = trgtCnt / prsnCnt <= 0 ? 1 : trgtCnt / prsnCnt;
			
			LOGGER.debug("상담원수=" + prsnCnt);
			LOGGER.debug("할당건수=" + trgtCnt);
			LOGGER.debug("평균건수=" + avgCnt);
			
			// i=할당건수, j=상담원수, k=할당평균
			for(int i=0, j=0, k=0; i<trgtCnt; i++){
				Map<String, Object> tMap = (Map) tlist.get(i); // 할당대상
				Map<String, Object> pMap = (Map) plist.get(j); // 상담원
				
				vo.setCrtYm((String) tMap.get("crtYm"));
				vo.setContractNum((String) tMap.get("contractNum"));
				vo.setVocPrsnId((String) pMap.get("cdVal"));
				vo.setTmStatCd("10");
				vo.setUserId(vo.getSessionUserId());
								
				dvcChgMapper.updateDvcChgTrgtMst(vo);
								
				k++;
				
				// 1인 평균 할당건수 처리된 경우
				if(k == avgCnt && j < (prsnCnt - 1)){
					// 다음상담원
					j++;
					k = 0;
				}
			}
		}catch(Exception e){
			LOGGER.debug(e.getMessage());
		}
	}
	
	/**
	 * 기기변경 TM할당(수동)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void setDvcChgTrgtAsgnList2(DvcChgMgmtVO vo){
		// 상담원 확인
		if(vo.getVocPrsnId() == null || "".equals(vo.getVocPrsnId())){
			throw new MvnoRunException(-1, "상담원이 존재하지 않습니다");
		}
		
		// TM할당대상
		List<DvcChgMgmtVO> list = vo.getItems();
		
		for(int i=0; i<list.size(); i++){
			DvcChgMgmtVO paramVO = list.get(i);
			
			paramVO.setVocPrsnId(vo.getVocPrsnId());
			paramVO.setTmStatCd("10");	// 상담원할당
			paramVO.setUserId(vo.getSessionUserId());
			
			LOGGER.debug("paramVO=" + paramVO);
			dvcChgMapper.updateDvcChgTrgtMst(paramVO);
		}
	}
	
	/**
	 * 상담원할당조회
	 */
	public List<?> getDvcChgCnslList(DvcChgMgmtVO vo, Map<String, Object> paramMap){
		// 조직ID=본사가 아닌 경우 상담원ID 체크 
		if(!"1".equals(vo.getSessionUserOrgnId()) && (vo.getVocPrsnId() == null || "".equals(vo.getVocPrsnId()))){
			throw new MvnoRunException(-1, "상담원ID가 존재하지 않습니다.");
		}
		
		List<?> list =  dvcChgMapper.getDvcChgTrgtList(vo);
		
		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	// TM결과저장
	@Transactional(rollbackFor=Exception.class)
	public void updateDvcChgTmRslt(DvcChgMgmtVO vo){
		//등록상태로 변경 불가
		if("00".equals(vo.getTmStatCd())){
			throw new MvnoRunException(-1, "등록 상태로 변경할 수 없습니다.");
		}
		
		if(vo.getCrtYm() == null || "".equals(vo.getCrtYm())){
			throw new MvnoRunException(-1, "추출월이 존재하지 않습니다.");
		}
		
		dvcChgMapper.updateDvcChgTrgtMst(vo);
		
		// 기변사유 선택시 상담이력 생성
		if(vo.getDvcChgRsnCd() != null && !"".equals(vo.getDvcChgRsnCd()) 
				&& vo.getDvcChgRsnDtlCd() != null && !"".equals(vo.getDvcChgRsnDtlCd()) 
		){
			DvcChgMgmtVO paramVO = new DvcChgMgmtVO();
			
			// 상담코드 세팅
			paramVO.setCnslMstCd("HA");
			paramVO.setCnslMidCd(vo.getDvcChgRsnCd());
			paramVO.setCnslDtlCd(vo.getDvcChgRsnCd() + vo.getDvcChgRsnDtlCd());
			
			// 30:기변성공, 31:기변실패, 40:통화실패, 50:O/B금지인 경우 상담완료 
			if("30".equals(vo.getTmStatCd()) || "31".equals(vo.getTmStatCd()) || "40".equals(vo.getTmStatCd()) || "50".equals(vo.getTmStatCd())){
				paramVO.setCnslStatCd("COM");
			}else{
				paramVO.setCnslStatCd("RCP");
			}
			paramVO.setTmCntt(vo.getTmCntt());
			paramVO.setUserId(vo.getSessionUserId());
			paramVO.setUserOrgnId(vo.getSessionUserOrgnId());
			paramVO.setCrtYm(vo.getCrtYm());
			paramVO.setContractNum(vo.getContractNum());
			
			// 상담내역 신규 생성
			if(vo.getVocRcpId() == null){
				paramVO.setVocRcpId(dvcChgMapper.getVocRcpId());
				
				dvcChgMapper.insertVocMgmtMst(paramVO);
				
			}else{
				paramVO.setVocRcpId(vo.getVocRcpId());
				
				dvcChgMapper.updateVocMgmtMst(paramVO);
			}
			
			// 상세내역은 계속 생성
			dvcChgMapper.insertVocMgmtDtl(paramVO);
			
			LOGGER.debug("paramVO=" + paramVO);
			
			dvcChgMapper.updateDvcChgTrgtMst(paramVO);
		}
	}
	
	// 기기변경가능여부체크
	public List<?> getDvcChgPsblCheck(DvcChgMgmtVO vo){
		// 필수값 확인
		if(vo.getCustNm() == null || "".equals(vo.getCustNm())){
			throw new MvnoRunException(-1, "고객명이 존재하지 않습니다.");
		}
		
		if(vo.getIdntNum() == null || "".equals(vo.getIdntNum())){
			throw new MvnoRunException(-1, "식별번호가 존재하지 않습니다.");
		}
		
		if(vo.getSubscriberNo() == null || "".equals(vo.getSubscriberNo())){
			throw new MvnoRunException(-1, "전화번호가 존재하지 않습니다.");
		}
		
		return dvcChgMapper.getDvcChgPsblCheck(vo);
	}
	
	// 기기변경가능여부체크 => 심플할인 적용 고객인지 체크
	//20200122 고객사 요청으로 심플할인 적용 고객인지 체크 로직 제거
/*	public void getDvcChgSimCheck(DvcChgMgmtVO vo){
		// 필수값 확인
		if(vo.getCustNm() == null || "".equals(vo.getCustNm())){
			throw new MvnoRunException(-1, "고객명이 존재하지 않습니다.");
		}
		
		if(vo.getIdntNum() == null || "".equals(vo.getIdntNum())){
			throw new MvnoRunException(-1, "식별번호가 존재하지 않습니다.");
		}
		
		if(vo.getSubscriberNo() == null || "".equals(vo.getSubscriberNo())){
			throw new MvnoRunException(-1, "전화번호가 존재하지 않습니다.");
		}
		
		List<?> SimList  = dvcChgMapper.getDvcChgSimCheck(vo);
		
		if(SimList.size() > 0 ){
			throw new MvnoRunException(-1, "해당 고객은 심플할인 약정 만료 전 고객이므로 \n\r 기기변경이 불가능합니다.");
		}
	}
	*/ 
	
	// 권한자체크
	public String getAuthCheck(DvcChgMgmtVO vo){
		return dvcChgMapper.getAuthCheck(vo);
	}
	
	/**
	 * 기기변경목록조회
	 */
	public List<?> getDvcChgList(DvcChgMgmtVO searchVO, Map<String, Object> paramMap) {
		
		LOGGER.debug("기기변경목록조회");
		
		if( "".equals(searchVO.getSearchGbn()) && "".equals(searchVO.getApplDateFrom()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(searchVO.getSearchGbn()) && "".equals(searchVO.getApplDateTo()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(searchVO.getSearchGbn()) && "".equals(searchVO.getSearchName()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}
		
		List<?> list =  dvcChgMapper.getDvcChgList(searchVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
	 * 기기변경목록조회 엑셀
	 */
	public List<?> getDvcChgListExcel(DvcChgMgmtVO searchVO, Map<String, Object> paramMap) {
		
		LOGGER.debug("기기변경목록조회엑셀");
		
		if( "".equals(searchVO.getSearchGbn()) && "".equals(searchVO.getApplDateFrom()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(searchVO.getSearchGbn()) && "".equals(searchVO.getApplDateTo()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(searchVO.getSearchGbn()) && "".equals(searchVO.getSearchName()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}
		
		List<?> list = dvcChgMapper.getDvcChgListExcel(searchVO); 
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	// SMS인증번호 전송
	public String sendSmsAuth(DvcChgMgmtVO vo){
		if(vo.getSubscriberNo() == null ||"".equals(vo.getSubscriberNo())){
			throw new MvnoRunException(-1, "CTN이 존재하지 않습니다.");
		}
		
		// 인증번호 생성
		String otpNo = null;

//		20200512 소스코드점검 수정
//		Random random = new Random();
		try {
			Random random = SecureRandom.getInstance("SHA1PRNG");
			String a = String.valueOf(random.nextInt(10));
			String b = String.valueOf(random.nextInt(10));
			String c = String.valueOf(random.nextInt(10));
			String d = String.valueOf(random.nextInt(10));
			String e = String.valueOf(random.nextInt(10));
			String f = String.valueOf(random.nextInt(10));
			otpNo = a+b+c+d+e+f;
		} catch (NoSuchAlgorithmException e1) {
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			LOGGER.error("Connection Exception occurred");
		}

		KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
		msgVO.setMsgType("1");
		msgVO.setRcptData(vo.getSubscriberNo());
		msgVO.setCallbackNum(propertyService.getString("otp.sms.callcenter"));
		msgVO.setMessage("[kt 엠모바일] 인증 번호는 "+ otpNo +" 입니다.");
		msgVO.setReserved01("MSP");
		msgVO.setReserved02("MSP1000014");
		msgVO.setReserved03(vo.getSessionUserId());
		
		loginService.insertKtMsgQueue(msgVO);
		
		return otpNo;
	}
	
	/**
	 * 유심비조회
	 */
	public String getUsimPrice(RcpDetailVO vo){
		// 필수값 확인
		if(vo.getOperType() == null || "".equals(vo.getOperType())){
			throw new MvnoRunException(-1, "업무구분이 존재하지 않습니다.");
		}
		
		if(vo.getSocCode() == null || "".equals(vo.getSocCode())){
			throw new MvnoRunException(-1, "요금제가 존재하지 않습니다.");
		}
		
		return dvcChgMapper.getUsimPrice(vo);
	}
}
