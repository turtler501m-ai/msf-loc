package com.ktis.msp.batch.job.org.usercanmgmt.mapper;

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

@Mapper("userCanMgmtMapper")
public interface UserCanMgmtMapper {
	
	/**
	 * @Description : 해지자 저장
	 * @Param  : String
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 9. 21.
	 */
	int userInfoInsService(String usrId);
	
	/**
	 * @Description : 해지자 삭제
	 * @Param  : String
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 9. 21.
	 */
	int userInfoDelService(String usrId);
	
	/**
	 * @Description : 해지자 정보 Update
	 * @Param  : String
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 9. 21.
	 */
	int userInfoUpdService(String usrId);
}
