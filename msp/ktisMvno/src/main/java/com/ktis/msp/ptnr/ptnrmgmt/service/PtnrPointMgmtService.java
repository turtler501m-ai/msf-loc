package com.ktis.msp.ptnr.ptnrmgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.ptnr.ptnrmgmt.mapper.PtnrPointMgmtMapper;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrPointMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class PtnrPointMgmtService extends BaseService {
	
	@Autowired
	private PtnrPointMgmtMapper mapper;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private RcpEncService encSvc;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/**
	 * @Description : 제휴포인트관리 내역 조회
	 * @Param  : PtnrInsrMemberVO
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 07.
	 */
	public List<?> getPointMgmtList(PtnrPointMgmtVO vo, Map<String, Object> paramMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제휴포인트관리 내역 조회"));
		logger.info(generateLogMsg("Return Vo [vo] = " + vo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		if(vo.getPartnerId() == null || "".equals(vo.getPartnerId())){
			throw new MvnoRunException(-1, "제휴사 정보가 존재하지 않습니다");
		}
		
		if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
			throw new MvnoRunException(-1, "지급년월이 존재하지 않습니다");
		}
		
		List<EgovMap> list = new ArrayList<EgovMap>();
		try {
			
			list = (List<EgovMap>) mapper.getPartnerPointList(vo);
			
			list = encSvc.decryptDBMSRcpMngtList(list);
			
			maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
			
		}catch(Exception e) {
			logger.debug(e.getMessage());
		}
		return list;
	}
	
	/**
	 * @Description : 개인 제휴포인트 내역
	 * @Param  : PtnrInsrMemberVO
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 07.
	 */
	public List<?> getPtnrRateUsgHstInfo(PtnrPointMgmtVO vo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개인제휴포인트 내역 조회"));
		logger.info(generateLogMsg("Return Vo [vo] = " + vo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> ptnrPointMgmtList = new ArrayList<PtnrPointMgmtVO>();
		
		ptnrPointMgmtList = mapper.getPtnrRateUsgHstInfo(vo);
		
		return ptnrPointMgmtList;
		
	}

	/**
	 *
	 * 제휴포인트관리 엑셀조회
	 * @param vo
	 * @return
	 */
	public List<?> getPtnrPointExcel(PtnrPointMgmtVO vo, Map<String, Object> paramMap){
		
		if(vo.getPartnerId() == null || "".equals(vo.getPartnerId())){
			throw new MvnoRunException(-1, "제휴사 정보가 존재하지 않습니다");
		}
		
		if(vo.getBillYm() == null || "".equals(vo.getBillYm())){
			throw new MvnoRunException(-1, "지급년월이 존재하지 않습니다");
		}
		
//		List<PtnrPointMgmtVO> list = mapper.getPtnrPointExcel(vo);
		List<EgovMap> list = (List<EgovMap>) mapper.getPartnerPointListExcel(vo);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}
}
