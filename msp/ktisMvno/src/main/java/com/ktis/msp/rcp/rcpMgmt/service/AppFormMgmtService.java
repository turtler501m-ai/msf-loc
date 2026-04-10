package com.ktis.msp.rcp.rcpMgmt.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.rcpMgmt.mapper.AppFormMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.AppFormMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class AppFormMgmtService {
	private static final Log LOGGER = LogFactory.getLog(AppFormMgmtService.class);
	
	@Autowired
	private AppFormMgmtMapper appFormMapper;
	
	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;
	
	@Autowired
	private RcpEncService encSvc;
	
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * 서식지함조회
	 */
	public List<?> getAppFormMgmtList(AppFormMgmtVO searchVO, Map<String, Object> paramMap) {
		if(searchVO.getStrtDt() == null || "".equals(searchVO.getStrtDt())){
			throw new MvnoRunException(-1,"시작일자 누락");
		}
		if(searchVO.getEndDt() == null || "".equals(searchVO.getEndDt())){
			throw new MvnoRunException(-1,"종료일자 누락");
		}

		List<EgovMap> list = (List<EgovMap>) appFormMapper.getAppFormMgmtList(searchVO);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

		return list;
	}
	
	/**
	 * 서식지함엑셀
	 */
	public List<?> getAppFormMgmtListExcel(AppFormMgmtVO searchVO, Map<String, Object> paramMap) {
		if(searchVO.getStrtDt() == null || "".equals(searchVO.getStrtDt())){
			throw new MvnoRunException(-1,"시작일자 누락");
		}
		if(searchVO.getEndDt() == null || "".equals(searchVO.getEndDt())){
			throw new MvnoRunException(-1,"종료일자 누락");
		}
		
		List<EgovMap> list = (List<EgovMap>) appFormMapper.getAppFormMgmtListExcel(searchVO);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}
	
	/**
	 * 서식지상태변경
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateAppFormData(AppFormMgmtVO searchVO) {
		if(searchVO.getScanId() == null || "".equals(searchVO.getScanId())){
			throw new MvnoRunException(-1,"스캔ID 누락");
		}
		
//		//진행상태확인
//		List<?> list = getAppFormStatCd(searchVO);
//		String procStatCd = ((AppFormMgmtVO) list.get(0)).getProcStatCd();
//		if(!"00".equals(procStatCd)){
//			throw new MvnoRunException(-1,"접수 상태인 경우 수정가능합니다.");
//		}
		appFormMapper.updateAppFormData(searchVO);
	}
	
	/**
	 * 서식지상태조회
	 */
	public List<?> getAppFormStatCd(AppFormMgmtVO searchVO) {
		if(searchVO.getScanId() == null || "".equals(searchVO.getScanId())){
			throw new MvnoRunException(-1,"스캔ID 미존재");
		}
		
		return appFormMapper.getAppFormStatCd(searchVO);
	}
	
}
