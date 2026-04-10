package com.ktis.msp.voc.reuseusim.service;

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
import com.ktis.msp.gift.custgmt.vo.CustMngVO;
import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNewMgmtVO;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;
import com.ktis.msp.voc.reuseusim.mapper.ReuseUsimMgmtMapper;
import com.ktis.msp.voc.reuseusim.vo.ReuseUsimMgmtVO;

@Service
public class ReuseUsimMgmtService extends BaseService {

	@Autowired
	private ReuseUsimMgmtMapper reuseUsimMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	@Transactional(rollbackFor=Exception.class)
	public List<?> getContractInfo(ReuseUsimMgmtVO vo) {
		
		// 계약정보조회
		List<?> list = reuseUsimMgmtMapper.getContractInfo(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertReuseUsimMst(ReuseUsimMgmtVO vo) {
		
		if(KtisUtil.isEmpty(vo.getContractNum())){
			throw new MvnoRunException(-1, "계약정보 검색을 먼저 해주십시오.");
		}
		
		if(KtisUtil.isEmpty(vo.getRegRsn())){
			throw new MvnoRunException(-1, "등록사유를 선택해주세요.");
		}
		
	   if(reuseUsimMgmtMapper.getReuseUsimCt(vo) > 0) {
				throw new MvnoRunException(-1, "이미 등록된 유심정보 입니다.");
		
	   }
		
		// 유심 재사용 설정 MST INSERT
	   reuseUsimMgmtMapper.insertReuseUsimMst(vo);
	   // 유심 재사용 설정 HIST INSERT
	   reuseUsimMgmtMapper.insertReuseUsimHist(vo);
	}
	
	public List<?> selectReuseUsimList(ReuseUsimMgmtVO vo) throws MvnoServiceException{
		
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
		
		
		List<?> list = reuseUsimMgmtMapper.selectReuseUsimList(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> pReqParamMap = new HashMap<String, Object>();
		pReqParamMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(list, maskFields, pReqParamMap);
		
		return list; 
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateReuseUsimMst(ReuseUsimMgmtVO vo) {
		
		if(KtisUtil.isEmpty(vo.getRegRsn())){
			throw new MvnoRunException(-1, "등록사유를 선택해주세요.");
		}
		
		// 유심 재사용 설정 MST INSERT
	   reuseUsimMgmtMapper.updateReuseUsimMst(vo);
	   // 유심 재사용 설정 HIST INSERT
	   reuseUsimMgmtMapper.insertReuseUsimHist(vo);
	}
	public String getRealUsim(String reuseSeq) {
		return reuseUsimMgmtMapper.getRealUsim(reuseSeq);
	}
	
	/**
	 * 요금제목록엑셀다운로드
	 * @param searchVO
	 * @return
	 */
	public List<?> selectReuseUsimListExcel(ReuseUsimMgmtVO searchVO ,  Map<String, Object> pRequestParamMap){
		
		List<?> reuseUsimListEx = new ArrayList<ReuseUsimMgmtVO>();
		reuseUsimListEx = reuseUsimMgmtMapper.selectReuseUsimListExcel(searchVO);

		// 배송지담당자,유선전화번호,핸드폰전화번호,상세주소 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", searchVO.getSessionUserId());

		maskingService.setMask(reuseUsimListEx, maskFields, paramMap);

		return reuseUsimListEx;
	}
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName","CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("fstUsimNo",			"USIM");
		maskFields.put("usimNo",			"USIM");
		maskFields.put("iccId",			"USIM");
				
		return maskFields;
	}
	
	public String getContUsim(ReuseUsimMgmtVO vo) {
		return reuseUsimMgmtMapper.getContUsim(vo);
	}
}
