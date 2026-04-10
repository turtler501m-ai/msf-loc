package com.ktis.msp.voc.reuserip.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.voc.reuserip.mapper.ReuseRipMgmtMapper;
import com.ktis.msp.voc.reuserip.vo.ReuseRipMgmtVO;
import com.ktis.msp.voc.reuseusim.vo.ReuseUsimMgmtVO;

@Service
public class ReuseRipMgmtService extends BaseService {

	@Autowired
	private ReuseRipMgmtMapper reuseRipMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Transactional(rollbackFor=Exception.class)
	public List<?> getContractInfo(ReuseRipMgmtVO vo) {
		
		// 계약정보조회
		List<?> list = reuseRipMgmtMapper.getContractInfo(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertReuseRipMst(ReuseRipMgmtVO vo) {
		
		if(KtisUtil.isEmpty(vo.getContractNum())){
			throw new MvnoRunException(-1, "계약정보 검색을 먼저 해주십시오.");
		}
		
		if(KtisUtil.isEmpty(vo.getRegRsn())){
			throw new MvnoRunException(-1, "등록사유를 선택해주세요.");
		}
		
		if(KtisUtil.isEmpty(vo.getRequestKey())){
			throw new MvnoRunException(-1, "신청정보가 없습니다.");
		}
		
	   if(reuseRipMgmtMapper.getReuseRipCt(vo) > 0) {
				throw new MvnoRunException(-1, "이미 등록된 IP정보 입니다.");
		
	   }
		
		// ip 재사용설정
		reuseRipMgmtMapper.insertReuseRipMst(vo);
	   // ip 재사용설정 hist insert
		reuseRipMgmtMapper.insertReuseRipHist(vo);
	}
	
public List<?> selectReuseRipList(ReuseRipMgmtVO vo) throws MvnoServiceException{
		
		if(vo.getSearchGb() == null || "".equals(vo.getSearchGb())){
			if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
				throw new MvnoServiceException( "시작일자가 존재하지 않습니다");
			}
			
			if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
				throw new MvnoServiceException("종료일자가 존재하지 않습니다");
			}
		}else{
			if(vo.getSearchName() == null ||"".equals(vo.getSearchName())){
				throw new MvnoServiceException("검색어가 존재하지 않습니다.");
			}
		}
		
		List<?> list = reuseRipMgmtMapper.selectReuseRipList(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list; 
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateReuseRipMst(ReuseRipMgmtVO vo) {
		
		if(KtisUtil.isEmpty(vo.getRegRsn())){
			throw new MvnoRunException(-1, "등록사유를 선택해주세요.");
		}
		
		if(KtisUtil.isEmpty(vo.getRipStatus())){
			throw new MvnoRunException(-1, "IP상태를 선택해주세요.");
		}
		
		// 유심 재사용 설정 MST INSERT
		reuseRipMgmtMapper.updateReuseRipMst(vo);
	   // 유심 재사용 설정 HIST INSERT
		reuseRipMgmtMapper.insertReuseRipUpdHist(vo);
	}
	
	public List<?> selectReuseRipListExcel(ReuseRipMgmtVO searchVO ,  Map<String, Object> pRequestParamMap){
		
		List<?> reuseRipListEx = new ArrayList<ReuseRipMgmtVO>();

		reuseRipListEx = reuseRipMgmtMapper.selectReuseRipListExcel(searchVO);
		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(reuseRipListEx, maskFields, paramMap);

		return reuseRipListEx;
	}
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("subLinkName","CUST_NAME");
		maskFields.put("rip","IP");
		maskFields.put("regstNm","CUST_NAME");
		maskFields.put("rvisnNm","CUST_NAME");
		
		return maskFields;
	}
	
}
