/**
 * 
 */
package com.ktis.msp.cmn.help.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.help.mapper.HelpMgmtMapper;
import com.ktis.msp.cmn.help.vo.HelpMgmtVO;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.common.vo.FileInfoVO;

/**
 * @Class Name : HelpMgmtService
 * @Description : 도움말관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017. 1. 11. 강무성 최초생성
 * @
 * @author : 강무성
 * @Create Date : 2017. 1. 11.
 */
@Service
public class HelpMgmtService extends BaseService {

	@Autowired
	private HelpMgmtMapper helpMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public List<HelpMgmtVO> getHelpMgmtList(HelpMgmtVO vo){
		
		List<HelpMgmtVO> result = helpMgmtMapper.getHelpMgmtList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("regstId",		"CUST_NAME");
		maskFields.put("rvisnId",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	public int insertFile(FileInfoVO vo){
		return helpMgmtMapper.insertFile(vo);
	}
	
	public String getHelpFileNm(String fileId) {
		return helpMgmtMapper.getHelpFileNm(fileId);
	}
}
