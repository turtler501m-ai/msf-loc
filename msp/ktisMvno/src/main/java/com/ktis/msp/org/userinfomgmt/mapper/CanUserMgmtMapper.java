package com.ktis.msp.org.userinfomgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.org.userinfomgmt.vo.CanUserReqVO;
import com.ktis.msp.org.userinfomgmt.vo.CanUserResVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : CanUserMgmtMapper
 * @Description : CAN 사용자 관리 Mapper
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.06.09 TREXSHIN 최초생성
 * @
 * @author : TREXSHIN
 * @Create Date : 2017.06.09.
 */

@Mapper("canUserMgmtMapper")
public interface CanUserMgmtMapper {
	
	/**
	 * 해지고객관리자 조회
	 * @param userInfoMgmtVo
	 * @return
	 */
	List<CanUserResVO> selectCanUserList(CanUserReqVO vo);
	
	/**
	 * 해지고객관리자 등록
	 * @param vo
	 */
	void insertCanUser(CanUserResVO vo);
	
	/**
	 * 해지고객관리자 수정
	 * @param vo
	 */
	void updateCanUser(CanUserResVO vo);
	
	/**
	 * 해지고객관리자 이력등록
	 * @param vo
	 */
	void insertCanUserHst(CanUserResVO vo);
	
	/**
	 * 등록된 사용자 확인
	 * @param vo
	 * @return
	 */
	int checkCanUser(CanUserResVO vo);
	
	/**
	 * 사용자정보수정시 해지고객관리자 수정.
	 * @param vo
	 */
	void updateCanUserInfoMgmt(UserInfoMgmtVo vo);
	
	/**
	 * 사용자등록시 본사사용자그룹에 매핑시킴(G_HQ)
	 * @param vo
	 */
	void usrGrpAsgnInsert(CanUserResVO vo);
	
	/**
	 * 사용자 그룹 이력 등록
	 * @param vo
	 */
	void usrGrpAsgnHstInsert(CanUserResVO vo);
	
	/**
	 * 사용자패스워드정보수정시 해지고객관리자 수정.
	 * @param vo
	 */
	void updatePassInfo(Map<String, Object> pReqParamMap) ;
	
}
