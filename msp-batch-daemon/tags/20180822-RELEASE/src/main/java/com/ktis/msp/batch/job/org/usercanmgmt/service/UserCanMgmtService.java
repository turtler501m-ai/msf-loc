package com.ktis.msp.batch.job.org.usercanmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.usercanmgmt.mapper.UserCanMgmtMapper;

/**
 * @Class Name : UserInfoMgmtService
 * @Description : userInfoMgmtVo Service
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@Service
public class UserCanMgmtService extends BaseService {

	@Autowired
	private UserCanMgmtMapper userCanMgmtMapper;
	
	public UserCanMgmtService() {
		setLogPrefix("[UserInfoMgmtService] ");
	}
	
	/**
	 * @throws MvnoServiceException 
	 * @Description : 웹 일반회원 12개월 동안 로그인 안한 유저 삭제
	 * @Param : String
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 9. 21.
	 */
	@Transactional(rollbackFor = Exception.class)
	public int userInfoCanService() throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("웹 일반회원 12개월 동안 로그인 안한 유저 삭제 START"));
		LOGGER.info(generateLogMsg("================================================================="));
		
		String usrId = null;
		int returnCnt = 0;
		try {
			// 해지자 DB에 데이터 이관
			returnCnt = userCanMgmtMapper.userInfoInsService(usrId);
			LOGGER.info("=============생성건수============= {}", returnCnt);
			
			if (returnCnt > 0) {
				// MSP 테이블 데이터 업데이트(CMN_WEBUSER_CAN_MST)
				returnCnt = userCanMgmtMapper.userInfoUpdService(usrId);
				LOGGER.info("=============수정건수============= {}", returnCnt);
				
				// MCP 테이블 데이터 삭제(MCP_USER)
				returnCnt = userCanMgmtMapper.userInfoDelService(usrId);
				LOGGER.info("=============삭제건수============= {}", returnCnt);
			}
		} catch (Exception e) {
			throw new MvnoServiceException("EORG1002", e);
		}
		
		return returnCnt;
	}
}
