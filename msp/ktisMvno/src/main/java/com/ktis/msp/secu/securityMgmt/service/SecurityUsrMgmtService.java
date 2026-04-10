package com.ktis.msp.secu.securityMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.secu.securityMgmt.mapper.SecurityUsrMgmtMapper;
import com.ktis.msp.secu.securityMgmt.vo.SecurityMgmtVo;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class SecurityUsrMgmtService extends BaseService{

	@Autowired
	private SecurityUsrMgmtMapper securityUsrMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * 월간보안점검
	 * 
	 * @author  
	 * @since 2019.01.17
	 * @version 1.0
	 * @see <pre>
	 *  == M전산_등록계정(Modification Information) ==
	 *
	 *          수정일          수정자           수정내용
	 *  ----------------    ------------    ---------------------------
	 *   2019.01.17          권오승        최초 생성
	 *
	 * </pre>
	 */
	public List<?> mspUsrMgmtList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityUsrMgmtMapper.mspUsrMgmtList(param); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}	
	
	public List<SecurityMgmtVo> mspUsrMgmtListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityUsrMgmtMapper.mspUsrMgmtListExcel(param);	
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}	
	
	/**
	 * 월간보안점검
	 * 
	 * @author  
	 * @since 2019.01.17
	 * @version 1.0
	 * @see <pre>
	 *  == M전산_마스킹권한자(Modification Information) ==
	 *
	 *          수정일          수정자           수정내용
	 *  ----------------    ------------    ---------------------------
	 *   2019.01.17          권오승        최초 생성
	 *
	 * </pre>
	 */
	public List<?> mspUsrMaskList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityUsrMgmtMapper.mspUsrMaskList(param); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}	
	
	public List<SecurityMgmtVo> mspUsrMaskListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityUsrMgmtMapper.mspUsrMaskListExcel(param);	
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}	
	
	/**
	 * 월간보안점검
	 * 
	 * @author  
	 * @since 2019.01.21
	 * @version 1.0
	 * @see <pre>
	 *  == M전산_삭제계정(Modification Information) ==
	 *
	 *          수정일          수정자           수정내용
	 *  ----------------    ------------    ---------------------------
	 *   2019.01.21          권오승        최초 생성
	 *
	 * </pre>
	 */
	public List<?> mspUsrDelList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityUsrMgmtMapper.mspUsrDelList(param); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}	
	
	public List<SecurityMgmtVo> mspUsrDelListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityUsrMgmtMapper.mspUsrDelListExcel(param);	
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}	
	
	/**
	 * 월간보안점검
	 * 
	 * @author  
	 * @since 2019.01.21
	 * @version 1.0
	 * @see <pre>
	 *  == M전산_전체계정(Modification Information) ==
	 *
	 *          수정일          수정자           수정내용
	 *  ----------------    ------------    ---------------------------
	 *   2019.01.21          권오승        최초 생성
	 *
	 * </pre>
	 */
	public List<?> mspAllUsrMgmtList(Map<String, Object> param){
		List<EgovMap> result = (List<EgovMap>) securityUsrMgmtMapper.mspAllUsrMgmtList(param); 
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("USR_ID",		"SYSTEM_ID");
		maskFields.put("USR_NM",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, param);
		
		return result;
	}	
	
	public List<SecurityMgmtVo> mspAllUsrMgmtListExcel(Map<String, Object> param){
	    List<SecurityMgmtVo> result = securityUsrMgmtMapper.mspAllUsrMgmtListExcel(param);	
		
		maskingService.setMask(result, maskingService.getMaskFields(), param);
		
		return result;
	}	
	
}
