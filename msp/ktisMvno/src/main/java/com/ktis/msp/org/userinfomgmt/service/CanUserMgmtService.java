package com.ktis.msp.org.userinfomgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.org.userinfomgmt.mapper.CanUserMgmtMapper;
import com.ktis.msp.org.userinfomgmt.vo.CanUserReqVO;
import com.ktis.msp.org.userinfomgmt.vo.CanUserResVO;


/**
 * @author TREXSHIN
 *
 */
@Service
public class CanUserMgmtService extends BaseService {

	@Autowired
	private CanUserMgmtMapper canUserMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	public CanUserMgmtService() {
		setLogPrefix("[CanUserMgmtService] ");
	}
	
	
	/**
	 * CAN 사용자 정보 조회
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<CanUserResVO> selectCanUserList(CanUserReqVO vo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CAN 사용자 정보 조회 서비스 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + vo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<CanUserResVO> result = new ArrayList<CanUserResVO>();
		
		result = canUserMgmtMapper.selectCanUserList(vo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",		"SYSTEM_ID");
		maskFields.put("usrNm",			"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
		
	}
	
	/**
	 * CAN 사용자 정보 저장
	 * @param vo
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveCanUser(CanUserResVO vo) {
		
		if((vo.getSaveType()).equals("U") && (vo.getUsrId() == null || "".equals(vo.getUsrId()))){
			throw new MvnoRunException(-1, "사용자ID 누락");
		}
		
		if(vo.getUsgStrtDt() == null || "".equals(vo.getUsgStrtDt()) || vo.getUsgEndDt() == null || "".equals(vo.getUsgEndDt())){
			throw new MvnoRunException(-1, "사용기간 누락");
		}
		
		if(vo.getReason() == null || "".equals(vo.getReason())){
			throw new MvnoRunException(-1, "사유 누락");
		}
		
		// 등록
		if((vo.getSaveType()).equals("I")) {
			
			canUserMgmtMapper.insertCanUser(vo);
			
			vo.setGrpId("G_HQ");
			canUserMgmtMapper.usrGrpAsgnInsert(vo);
			
			vo.setOperType("I");
			canUserMgmtMapper.usrGrpAsgnHstInsert(vo);
			
		// 수정
		} else if((vo.getSaveType()).equals("U")) {
			
			canUserMgmtMapper.updateCanUser(vo);
			
		}
		
		// 이력등록
		canUserMgmtMapper.insertCanUserHst(vo);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int checkCanUser(CanUserResVO vo) {
		
		if(vo.getUsrId() == null || "".equals(vo.getUsrId())){
			throw new MvnoRunException(-1, "사용자ID 누락");
		}
		
		return canUserMgmtMapper.checkCanUser(vo);
		
	}
	
}
