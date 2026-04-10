package com.ktis.msp.rsk.rskMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;
import com.ktis.msp.rsk.rskMgmt.mapper.RskMgmtMapper;
import com.ktis.msp.rsk.rskMgmt.vo.RskMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RskMgmtService extends BaseService {
	
	@Autowired
	private RskMgmtMapper rskMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private RcpEncService encSvc;
	
	public List<?> getNgCustMgmtList(RskMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
		}
		
		if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
		}
		
		List<EgovMap> list = (List<EgovMap>) rskMgmtMapper.getNgCustMgmtList(vo);
//		List<EgovMap> result = list;	// 개발용
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);	// 복호화컬럼("cstmrNativeRrn", "cstmrForeignerRrn", "ssn")
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		for(EgovMap map : result){
			String[] ssn = Util.getJuminNumber((String)map.get("ssn"));
			map.put("birthDt", ssn[0]);
			map.put("gender", ssn[1].substring(0, 1));
		}
		
		return result;
	}
	
	public List<?> getNgCustMgmtListExcel(RskMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
		}
		
		if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
		}
		
		List<EgovMap> list = (List<EgovMap>) rskMgmtMapper.getNgCustMgmtListExcel(vo);
//		List<EgovMap> result = list;
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);	// 복호화컬럼("cstmrNativeRrn", "cstmrForeignerRrn", "ssn")
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		for(EgovMap map : result){
			String[] ssn = Util.getJuminNumber((String)map.get("ssn"));
			map.put("birthDt", ssn[0]);
			map.put("gender", ssn[1].substring(0, 1));
		}
		
		return result;
	}
	
	public List<?> getNgAccMgmtList(RskMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
		}
		
		if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
		}
		
		List<EgovMap> list = (List<EgovMap>) rskMgmtMapper.getNgAccMgmtList(vo);
		
//		List<EgovMap> result = list;	// 개발용
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);	// 복호화컬럼("cstmrNativeRrn", "cstmrForeignerRrn", "ssn")
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		for(EgovMap map : result){
			String[] ssn = Util.getJuminNumber((String)map.get("ssn"));
			map.put("birthDt", ssn[0]);
//			map.put("gender", ssn[1].substring(0, 1));
		}
		
		return result;
	}
	
	public List<?> getNgAccMgmtListExcel(RskMgmtVO vo, Map<String, Object> paramMap){
		if(vo.getStrtDt() == null || "".equals(vo.getStrtDt())){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다");
		}
		
		if(vo.getEndDt() == null || "".equals(vo.getEndDt())){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다");
		}
		
		List<EgovMap> list = (List<EgovMap>) rskMgmtMapper.getNgAccMgmtListExcel(vo);
//		List<EgovMap> result = list;
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);	// 복호화컬럼("cstmrNativeRrn", "cstmrForeignerRrn", "ssn")
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		for(EgovMap map : result){
			String[] ssn = Util.getJuminNumber((String)map.get("ssn"));
			map.put("birthDt", ssn[0]);
//			map.put("gender", ssn[1].substring(0, 1));
		}
		
		return result;
	}
}
	
