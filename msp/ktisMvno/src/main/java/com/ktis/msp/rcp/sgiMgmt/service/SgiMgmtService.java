package com.ktis.msp.rcp.sgiMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.rcp.sgiMgmt.mapper.SgiMgmtMapper;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtCanListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtDmndDtlListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtDmndListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtOverPymListVO;
import com.ktis.msp.rcp.sgiMgmt.vo.SgiMgmtRejectListVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * @Class Name : RqstMgmtService
 * @Description : 청구 Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Service
public class SgiMgmtService extends BaseService {

	@Resource(name = "maskingService")
	private MaskingService maskingService;
	
	@Autowired
	private SgiMgmtMapper sgiMgmtMapper;
    
	public SgiMgmtService() {
		setLogPrefix("[SgitMgmtService] ");
	}

	/**
	 * @Description : 단말계약 리스트를 조회 한다.
	 * @Param  : SgiMgmtDmndListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndList(SgiMgmtDmndListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtDmndListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiDmndList(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		
		return sgiMgmtVoList;
	}
	
	/**
	 * @Description : 단말계약 리스트를 엑셀 조회 한다.
	 * @Param  : SgiMgmtDmndListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndListEx(SgiMgmtDmndListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약 리스트 엑셀 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtDmndListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiDmndListEx(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		
		return sgiMgmtVoList;
	}

	/**
	 * @Description : 단말계약상세 리스트를 조회 한다.
	 * @Param  : SgiMgmtDmndDtlListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndDtlList(SgiMgmtDmndDtlListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약상세 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtDmndDtlListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiDmndDtlList(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);

		return sgiMgmtVoList;
	}
	
	/**
	 * @Description : 단말계약상세 리스트를 엑셀 조회 한다.
	 * @Param  : SgiMgmtDmndDtlListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiDmndDtlListEx(SgiMgmtDmndDtlListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말계약상세 리스트 엑셀 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtDmndDtlListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiDmndDtlListEx(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		
		return sgiMgmtVoList;
	}

	/**
	 * @Description : 청구취소조회 리스트를 조회 한다.
	 * @Param  : SgiMgmtCanListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiCanList(SgiMgmtCanListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구취소조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [sgiMgmtVO] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtCanListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiCanList(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		

		return sgiMgmtVoList;
	}
	
	/**
	 * @Description : 청구취소조회 리스트를 엑셀 조회 한다.
	 * @Param  : SgiMgmtCanListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiCanListEx(SgiMgmtCanListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("청구취소조회 리스트 엑셀 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [sgiMgmtVO] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtCanListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiCanListEx(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		
		return sgiMgmtVoList;
	}
	
	/**
	 * @Description : 과납내역조회 리스트를 조회 한다.
	 * @Param  : SgiMgmtOverPymListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiOverPymList(SgiMgmtOverPymListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("과납내역조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtOverPymListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiOverPymList(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		

		return sgiMgmtVoList;
	}
	
	/**
	 * @Description : 과납내역조회 리스트를 엑셀 조회 한다.
	 * @Param  : SgiMgmtDmndDtlListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiOverPymListEx(SgiMgmtOverPymListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("과납내역조회 리스트 엑셀 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtOverPymListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiOverPymListEx(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		
		return sgiMgmtVoList;
	}

	/**
	 * @Description : 불능처리조회 리스트를 조회 한다.
	 * @Param  : SgiMgmtRejectListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiRejectList(SgiMgmtRejectListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("불능처리조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtRejectListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiRejectList(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		

		return sgiMgmtVoList;
	}
	
	/**
	 * @Description : 불능처리조회 리스트를 엑셀 조회 한다.
	 * @Param  : SgiMgmtRejectListVO
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
	public List<?> getSgiRejectListEx(SgiMgmtRejectListVO sgiMgmtVO, Map<String, Object> paramMap) throws EgovBizException {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("불능처리조회 리스트 엑셀 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [rqstMgmtVo] = " + sgiMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> sgiMgmtVoList = new ArrayList<SgiMgmtRejectListVO>();
		
		sgiMgmtVoList = sgiMgmtMapper.getSgiRejectListEx(sgiMgmtVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(sgiMgmtVoList, maskFields, paramMap);
		
		return sgiMgmtVoList;
	}
	

	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName","CUST_NAME");
		maskFields.put("subscriberNo","TEL_NO");
		maskFields.put("residenceSubscriberNo","TEL_NO");
		maskFields.put("billAddrSubscriberNo","TEL_NO");
		maskFields.put("residenceAddr","ADDR");
		maskFields.put("billAddr","ADDR");
		maskFields.put("minorSsn","SSN");
		maskFields.put("lstModelSerialNo","ESN");
		maskFields.put("minorName","CUST_NAME");
		maskFields.put("userSsn","SSN");
		
		return maskFields;
	}	
}