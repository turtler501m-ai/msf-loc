package com.ktis.msp.rcp.selfHpcMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.selfHpcMgmt.mapper.SelfHpcMgmtMapper;
import com.ktis.msp.rcp.selfHpcMgmt.vo.SelfHpcMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class SelfHpcMgmtService extends BaseService {
	
	@Autowired
	private SelfHpcMgmtMapper selfHpcMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	
	public List<EgovMap> getSelfHpcList(SelfHpcMgmtVO searchVO, Map<String, Object> paramMap){
	
		List<EgovMap> list = selfHpcMgmtMapper.getSelfHpcList(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		
		for(EgovMap map : list){			
			if ("JP".equals(map.get("cstmrType"))){
				map.put("age","법인");
			} else {
				map.put("age",Integer.toString(Util.getAge((String) map.get("userSsn"))));
			}			
		}
		return list;
	}
	
	public List<EgovMap> getSelfHpcListByExcel(SelfHpcMgmtVO searchVO, Map<String, Object> paramMap){
	
		List<EgovMap> list = selfHpcMgmtMapper.getSelfHpcListByExcel(searchVO);

		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);

		for(EgovMap map : list){
			if ("JP".equals(map.get("cstmrType"))){
				map.put("age","법인");
			} else {
				map.put("age",Integer.toString(Util.getAge((String) map.get("userSsn"))));
			}			
		}
		return list;	
	}
	
	public int updateHpcRst(SelfHpcMgmtVO selfHpcMgmtVO){
		//해피콜여부 필수값
		if (selfHpcMgmtVO.getHpcStat() == null || "".equals(selfHpcMgmtVO.getHpcStat()) ){
			throw new MvnoRunException(-1, "해피콜 여부를 선택해주세요.");
		}

		//해피콜여부가 "완료" 일때 해피콜결과 필수값
		if ("COMP".equals(selfHpcMgmtVO.getHpcStat())){
			if (selfHpcMgmtVO.getHpcRst() == null || "".equals(selfHpcMgmtVO.getHpcRst()) ){
				throw new MvnoRunException(-1, "해피콜 결과를 선택해주세요.");
			}	
		}

		if ("FST".equals(selfHpcMgmtVO.getHpcGbn())){
			if (selfHpcMgmtVO.getFstHpcRst() == null || "".equals(selfHpcMgmtVO.getFstHpcRst()) ){
				throw new MvnoRunException(-1, "1차 해피콜 결과를 선택해주세요.");
			}
			if (selfHpcMgmtVO.getFstHpcRmk() == null || "".equals(selfHpcMgmtVO.getFstHpcRmk()) ){
				throw new MvnoRunException(-1, "1차 해피콜 비고를 입력해주세요.");
			}
		}

		if ("SND".equals(selfHpcMgmtVO.getHpcGbn())){
			if (selfHpcMgmtVO.getSndHpcRst() == null || "".equals(selfHpcMgmtVO.getSndHpcRst()) ){
				throw new MvnoRunException(-1, "2차 해피콜 결과를 선택해주세요.");
			}
			if (selfHpcMgmtVO.getSndHpcRmk() == null || "".equals(selfHpcMgmtVO.getSndHpcRmk()) ){
				throw new MvnoRunException(-1, "2차 해피콜 비고를 입력해주세요.");
			}
		}

		if ("THD".equals(selfHpcMgmtVO.getHpcGbn())){
			if (selfHpcMgmtVO.getThdHpcRst() == null || "".equals(selfHpcMgmtVO.getThdHpcRst()) ){
				throw new MvnoRunException(-1, "3차 해피콜 결과를 선택해주세요.");
			}
			if (selfHpcMgmtVO.getThdHpcRmk() == null || "".equals(selfHpcMgmtVO.getThdHpcRmk()) ){
				throw new MvnoRunException(-1, "3차 해피콜 비고를 입력해주세요.");
			}
		}
		//중복체크
		if (selfHpcMgmtMapper.dupChkHpcStat(selfHpcMgmtVO) > 0) {
			return -1;
		}
		
		return selfHpcMgmtMapper.updateHpcRst(selfHpcMgmtVO);
	}
	
	
	/**
	 * 마스크 필드
	 * @return
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subLinkName","CUST_NAME");	// 고객명
		maskFields.put("subscriberNo","MOBILE_PHO"); // nice 인증 전화번호
		maskFields.put("mobileNo","MOBILE_PHO"); // nice 인증 전화번호
		maskFields.put("addr","ADDR"); // 주소
		
		return maskFields;
	}
}
