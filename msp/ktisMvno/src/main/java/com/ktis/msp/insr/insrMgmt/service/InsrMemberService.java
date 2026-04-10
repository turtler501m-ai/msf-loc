package com.ktis.msp.insr.insrMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.insr.insrMgmt.mapper.InsrMemberMapper;
import com.ktis.msp.insr.insrMgmt.vo.InsrMemberVO;
import com.ktis.msp.insr.insrMgmt.vo.InsrRegVO;

@Service
public class InsrMemberService extends BaseService {
	
	@Autowired
	private InsrMemberMapper insrMemberMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public List<InsrMemberVO> getInsrMemberList(InsrMemberVO insrMemberVO, Map<String, Object> paramMap){
		
		if ( (insrMemberVO.getSearchCd() == null || "".equals(insrMemberVO.getSearchCd()))
			&& ((insrMemberVO.getSearchFromDt() == null || "".equals(insrMemberVO.getSearchFromDt()))
			|| (insrMemberVO.getSearchToDt() == null || "".equals(insrMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrMemberVO> rsltAryListVo = insrMemberMapper.getInsrMemberList(insrMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	public List<InsrMemberVO> getInsrMemberListByExcel(InsrMemberVO insrMemberVO, Map<String, Object> paramMap){
		
		if ( (insrMemberVO.getSearchCd() == null || "".equals(insrMemberVO.getSearchCd()))
			&& ((insrMemberVO.getSearchFromDt() == null || "".equals(insrMemberVO.getSearchFromDt()))
			|| (insrMemberVO.getSearchToDt() == null || "".equals(insrMemberVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "가입일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrMemberVO> rsltAryListVo = insrMemberMapper.getInsrMemberListByExcel(insrMemberVO);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(rsltAryListVo, maskFields, paramMap);
		
		return rsltAryListVo;
	}
	
	private HashMap<String, String> getMaskFields() {
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		
		maskFields.put("subLinkName", "CUST_NAME");
		maskFields.put("subscriberNo","MOBILE_PHO");
		maskFields.put("custNm", "CUST_NAME");
		maskFields.put("intmSrlNo", "DEV_NO");
		
		return maskFields;
	}
	
	public InsrRegVO getInsrCntrInfo(InsrRegVO insrRegVO, Map<String, Object> paramMap) {
		
		if (insrRegVO.getSearchCntrNo() == null || "".equals(insrRegVO.getSearchCntrNo()) ){
			throw new MvnoRunException(-1, "계약번호가 존재 하지 않습니다.");
		}
		
		InsrRegVO rsltVO = insrMemberMapper.getInsrCntrInfo(insrRegVO);
		
		if(rsltVO != null && rsltVO.getContractNum() != null && !"".equals(rsltVO.getContractNum())) {
			HashMap<String, String> maskFields = getMaskFields();
		
			maskingService.setMask(rsltVO, maskFields, paramMap);
		}
		
		return rsltVO;
	}
	
	public void regMspIntmInsrOrder(InsrRegVO insrRegVO) {
		
		String updYn = "";
		
		updYn = insrMemberMapper.getIntmInsrOrderYn(insrRegVO);
		
		if("Y".equals(updYn)) {
			insrMemberMapper.updMspIntmInsrOrderByCancel(insrRegVO);
		}
		
		try {
			
			insrMemberMapper.regMspIntmInsrOrder(insrRegVO);
			
		} catch (Exception e) {
			
			if (e instanceof DataAccessException) {
				throw new MvnoRunException(-1, "동일한 신청 정보가 존재합니다.<br>신청 정보를 확인 하세요.");
			}
			
			throw new MvnoRunException(-1, e.getMessage());
		}
		
	}
}
