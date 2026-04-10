package com.ktis.msp.gift.custgmt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.gift.custgmt.mapper.CustMngMapper;
import com.ktis.msp.gift.custgmt.vo.CustMngVO;
import com.ktis.msp.gift.custgmt.vo.PrmtResultVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;



@Service
public class CustMngService extends BaseService {

	@Autowired
	private CustMngMapper custMngMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	public List<?> custMngList(CustMngVO searchVO, Map<String, Object> pRequestParamMap){
		
		// 필수조건 체크
		if(searchVO.getFrDate() == null || "".equals(searchVO.getFrDate())){
				throw new MvnoRunException(-1, "시작일자 누락");
		}
		if(searchVO.getToDate() == null || "".equals(searchVO.getToDate())){
				throw new MvnoRunException(-1, "종료일자 누락");
		}
		

		List<?> custMngList = new ArrayList<CustMngVO>();
		custMngList = custMngMapper.custMngList(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(custMngList, maskFields, paramMap);

		return custMngList;
	}

	@SuppressWarnings("unchecked")
	public void giftPrmtCustomerUpdate(CustMngVO searchVO, Map<String, Object> pRequestParamMap){

		custMngMapper.giftPrmtCustomerUpdate(searchVO);


		//제품정보
		String itemData = searchVO.getItemData();
		logger.debug("itemData:" + itemData);			

		List<PrmtResultVO> list = null;
		Map<String, Object> item = null;
		try {
			list = getVoFromMultiJson(itemData, "ALL", PrmtResultVO.class);
			
			// 이력 저장으로 사용
			item = new ObjectMapper().readValue(itemData, Map.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException occurred"); 
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException occurred"); 
		} catch (ParseException e) {
			logger.error("ParseException occurred"); 
		} catch (IOException e) {
			logger.error("IOException occurred"); 
		}
		
		if (item != null) {
			item.put("sessionUserId", searchVO.getSessionUserId());
			item.put("prmtId", searchVO.getPrmtId());
			item.put("contractNum", searchVO.getContractNum());
			try {
				custMngMapper.giftPrmtResultHistInsert(item);
			} catch (DuplicateKeyException e) {
				// seq를 쓰지 않고 select max+1을 사용해서(ITO 요청사항) 동시 요청이 들어올 경우 pk 중복 오류가 발생할 수 있어 1번 재시도
				custMngMapper.giftPrmtResultHistInsert(item);
			}
		}

		//추가전 전체삭제
		custMngMapper.giftPrmtResultDelete(searchVO);
		
		for(int i=0 ; i<list.size(); i++) {
			PrmtResultVO prmtResultVO = (PrmtResultVO) list.get(i);
			
			prmtResultVO.setSessionUserId(searchVO.getSessionUserId());
			prmtResultVO.setPrmtId(searchVO.getPrmtId());
			prmtResultVO.setContractNum(searchVO.getContractNum());
			int quantity = 0;
			quantity = Integer.parseInt(prmtResultVO.getQuantity());
			prmtResultVO.setQuantity(String.valueOf(quantity));
			
			custMngMapper.giftPrmtResultInsert(prmtResultVO);
		}
		
		
		
	}

	public List<?> getCustGiftList(CustMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getCustGiftList = new ArrayList<CustMngVO>();
		getCustGiftList = custMngMapper.getCustGiftList(searchVO);

		return getCustGiftList;
	}

	public List<?> getCustGiftPrmtPrdtList(CustMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> getCustGiftPrmtPrdtList = new ArrayList<CustMngVO>();
		getCustGiftPrmtPrdtList = custMngMapper.getCustGiftPrmtPrdtList(searchVO);

		return getCustGiftPrmtPrdtList;
	}

	public void giftPrmtResultDelete(CustMngVO searchVO, Map<String, Object> pRequestParamMap){

		custMngMapper.giftPrmtResultDelete(searchVO);

	}

	public void giftPrmtResultInsert(PrmtResultVO searchVO, Map<String, Object> pRequestParamMap){

		custMngMapper.giftPrmtResultInsert(searchVO);

	}

	public List<?> custMngListEx(CustMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> custMngListEx = new ArrayList<CustMngVO>();
		custMngListEx = custMngMapper.custMngListEx(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(custMngListEx, maskFields, paramMap);

		return custMngListEx;
	}

	public List<?> custMngListEx2(CustMngVO searchVO, Map<String, Object> pRequestParamMap){

		List<?> custMngListEx2 = new ArrayList<CustMngVO>();
		custMngListEx2 = custMngMapper.custMngListEx2(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(custMngListEx2, maskFields, paramMap);

		return custMngListEx2;
	}
	
	/**
	 * 마스킹필드 셋팅(고객명,전화번호,유선전화번호,핸드폰전화번호,상세주소)
	 * @return
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName", "CUST_NAME");
		maskFields.put("maskMngNm", "CUST_NAME");
		maskFields.put("tel1", "TEL_NO");
		maskFields.put("tel2", "TEL_NO");
		maskFields.put("subscriberNo", "MOBILE_PHO");
		maskFields.put("addr", "ADDR");
		maskFields.put("maskAddrDtl", "PASSWORD");
		
		return maskFields;
	}

	/**
	 * M전산에서 고객에게 매핑된 프로모션 조회
	 * SMS 배치에서 MSP_REQUEST_DTL테이블의 request_key로 inner join을 거는 조건이 있어  
	 * request_key가 null인 case는  MSP_GIFT_PRMT_CUSTOMER 테이블에 insert 되지 않음
	 * 
	 * @param requestKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> custGiftPrmtPrdtResultList(String requestKey, String menuId) {
		if (requestKey == null || "".equals(requestKey) || menuId == null || "".equals(menuId)) {
			return null;
		}

		List<?> prmtPrdtResultList = null;
		
		// 신청관리에서 신청 조회 시
		if ("MSP1000015".equals(menuId.trim()) || "MSP1010089".equals(menuId.trim()) || "MSP1000018".equals(menuId.trim())) {
			// M포털 DB 검색
			prmtPrdtResultList = custMngMapper.getMPortalPrmtPrdtResultList(requestKey);
		} else {
			// M전산 DB 검색
			prmtPrdtResultList = custMngMapper.getPrmtPrdtResultList(requestKey);
		}

		// DB 접속 최소화하기 위해 prdtResult 기준으로 list로 뽑았음. backend에서 가공해서 전달
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		// 2중 for문 안하기 위한 prmt index list
		List<String> indexList = new ArrayList<String>();
		
		for (int i = 0; i < prmtPrdtResultList.size(); i++) {
			EgovMap map = (EgovMap) prmtPrdtResultList.get(i);
			String prmtId = String.valueOf(map.get("prmtId"));
			
			// 프로모션 단위 최초 추가 시
			if (indexList.indexOf(prmtId) == -1) {
				// 프로모션 정보 추가
				Map<String, Object> prmtInfo = new HashMap<String, Object>();
				prmtInfo.put("prmtId", prmtId);
				prmtInfo.put("prmtNm", map.get("prmtNm"));
				prmtInfo.put("prmtType", map.get("prmtType"));
				prmtInfo.put("prmtTypeNm", map.get("prmtTypeNm"));
				prmtInfo.put("choiceLimit", map.get("choiceLimit"));
				
				// 사은품 정보 추가
				List<Map<String, Object>> prdtList = new ArrayList<Map<String, Object>>();
				prdtList.add(this.getPrdtInfo(map));

				// 프로모션 정보에 사은품 정보가 담긴 list 추가
				prmtInfo.put("prdtList", prdtList);
				resultList.add(prmtInfo);
				
				// 프로모션 index 값을 위한 add
				indexList.add(prmtId);
			} else {
				// 프로모션 정보 list에 사은품 정보 추가
				Map<String, Object> prmtInfo = resultList.get(indexList.indexOf(prmtId));
				List<Map<String, Object>> prdtList = (List<Map<String, Object>>) prmtInfo.get("prdtList");
				prdtList.add(this.getPrdtInfo(map));
			}
		}
		
		return resultList;
	}

	private Map<String, Object> getPrdtInfo(Map<String, Object> map) {
		Map<String, Object> prdtInfo = new HashMap<String, Object>();
		prdtInfo.put("prdtNm", map.get("prdtNm"));
		prdtInfo.put("quantity", map.get("quantity"));
		prdtInfo.put("rowCheck", "1".equals(String.valueOf(map.get("rowCheck"))) ? true : false);
		return prdtInfo;
	}
}

