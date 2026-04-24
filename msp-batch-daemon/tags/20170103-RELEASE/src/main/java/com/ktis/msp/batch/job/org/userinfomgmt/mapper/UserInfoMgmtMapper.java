package com.ktis.msp.batch.job.org.userinfomgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : UserInfoMgmtMapper
 * @Description : 사용자 관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */

@Mapper("userInfoMgmtMapper")
public interface UserInfoMgmtMapper {
	
	/**
	 * @Description : 웹 일반 회원 배치
	 * @Param : userInfoMgmtService
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 8. 18.
	 */
	int userInfoMgmtService(String usrId);
	
}
