package com.ktis.msp.batch.job.org.userinfomgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.userinfomgmt.mapper.UserInfoMgmtMapper;

/**
 * @Class Name : UserInfoMgmtService
 * @Description : userInfoMgmtVo Service @ * @ 수정일 수정자 수정내용 @ ---------- ------
 *              ----------------------------- @ 2014.08.22 장익준 최초생성
 * @author      : 장익준
 * @Create Date : 2014. 8. 22.
 */
@Service
public class UserInfoMgmtService extends BaseService {

	@Autowired
	private UserInfoMgmtMapper userInfoMgmtMapper;

	public UserInfoMgmtService() {
		setLogPrefix("[UserInfoMgmtService] ");
	}

	/**
	 * @throws MvnoServiceException 
	 * @Description : 웹 일반회원 11개월 동안 로그인 안한 유저에게 mail발송.
	 * @Param : String
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 8. 18.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void userInfoMgmtService() throws MvnoServiceException {
		try {
			LOGGER.debug(generateLogMsg("================================================================="));
			LOGGER.debug(generateLogMsg("웹 일반회원 11개월 동안 로그인 안한 유저에게 mail발송 START"));
			LOGGER.debug(generateLogMsg("================================================================="));
			
			String usrId = null;
			
			userInfoMgmtMapper.userInfoMgmtService(usrId);
		} catch(Exception e) {
			throw new MvnoServiceException("EORG1003", e);
		}
	}

}
