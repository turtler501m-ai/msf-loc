package com.ktis.msp.org.userinfomgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.login.vo.MsgQueueVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;

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
	 * @Description : 사용자 LIST 항목을 가져온다.
	 * @Param  : UserInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	List<?> selectUserInfoMgmtList(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	* @Description : 사용자 정보를 가져온다 
	* @Param  : UserInfoMgmtVo
	* @Return : UserInfoMgmtVo
	* @Author : 고은정
	* @Create Date : 2014. 10. 7.
	 */
	UserInfoMgmtVo selectUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	 * @Description : 사용자 정보를 생성한다.
	 * @Param  : UserInfoMgmtVo
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */    
    int insertUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 정보를 수정한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 26.
     */
    int updateUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 정보를 수정한다 마스킹 미권한자
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 박준성
     * @Create Date : 2022. 10. 24.
     */
    int updateUserInfoMgmtMsk(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 정보를 삭제한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 26.
     */
    int deleteUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo);

    /**
     * @Description : 사용자 권한정보를 삭제한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 이지연
     * @Create Date : 2024. 1. 8.
     */    
    int deleteUserInfoMgmtAsgn(UserInfoMgmtVo userInfoMgmtVo);

    /**
     * @Description : 사용자별 메뉴 권한 정보를 삭제한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 이지연
     * @Create Date : 2024. 1. 8.
     */    
    int deleteUserInfoMgmtUsrAsgn(UserInfoMgmtVo userInfoMgmtVo);

    /**
     * @Description : 사용자 권한 해지자 정보를 삭제한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 이지연
     * @Create Date : 2024. 1. 8.
     */    
    int deleteUserInfoMgmtCanAsgn(UserInfoMgmtVo userInfoMgmtVo);

    /**
     * @Description : 사용자 권한 해지자 이력 추가
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 이지연
     * @Create Date : 2024. 1. 8.
     */    
    int insertCanUserHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자별 메뉴 권한 정보를 삭제한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 이지연
     * @Create Date : 2024. 1. 8.
     */    
    int deleteUserInfoCanUsrAsgn(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자의 비밀번호를 초기화한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 26.
     */
    int initUserPassword(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 이력 건수 조회
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 9. 1.
     */
    int intCntUsrHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : MAX Seq 조회
     * @Param  : UserInfoMgmtVo
     * @Return : String
     * @Author : 고은정
     * @Create Date : 2014. 9. 1.
     */
    String maxSeqUsrHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자이력 추가
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 9. 1.
     */
    int insertUserHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 이력 리스트 조회
     * @Param  : UserInfoMgmtVo
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 9. 1.
     */
    List<?> selectUserHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
    * @Description : 사용자 ID 중복체크 
    * @Param  : UserInfoMgmtVo
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 27.
     */
    int isExistUsrId(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
    * @Description : 조직 유무 체크 
    * @Param  : UserInfoMgmtVo
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 27.
     */
    int selectValCnt(UserInfoMgmtVo userInfoMgmtVo);
    
	
    /**
     * @Description : 웹 일반 회원 배치
     * @Param  : userInfoMgmtService
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 8. 18.
     */
    int userInfoMgmtService(String usrId);
    
    /**
     * @Description : Admin 사용자 여부
     * @Param  : userInfoMgmtService
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 8. 18.
     */
    int isAdminGroupUser(String usrId);
    
    /**
     * @Description : DEV 사용자 여부
     * @Param  : userInfoMgmtService
     * @Return : int
     * @Author : 
     * @Create Date : 
     */
    int isDevGroupUser(String usrId);
    
    /**
     * @Description : 현재 패스워드 검증
     * @Param  : userInfoMgmtService
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 8. 18.
     */
    int isOldPass(UserInfoMgmtVo userInfoMgmtVo);
    
    
    /**
     * @Description : 사용자 MAC 정보 확인
     * @Param  : usrId
     * @Return : int
     * @Author : 박준성
     * @Create Date : 2017. 06. 10.
     */
    int userMacInfoCnt(String usrId);

    /**
//     * @Description : 사용자 MAC 정보 이동
     * @Param  : usrId
     * @Return : int
     * @Author : 박준성
     * @Create Date : 2017. 06. 10.
     */
    int userMacInfoMove(String usrId);
    
    /**
     * @Description : 사용자 MAC 정보 삭제
     * @Param  : usrId
     * @Return : int
     * @Author : 박준성
     * @Create Date : 2017. 06. 10.
     */
    int userMacInfoDel(String usrId);
    
    /**
	 * @Description : 판매점 사용자 LIST 항목을 가져온다.
	 * @Param  : UserInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	List<?> selectShopUserInfoMgmtList(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
     * @Description : 판매점 사용자 정보를 수정한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
     */
    int updateShopUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 상태를 초기화한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
     */
    int updateStopStatusInit(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 정지상태를 해제한다
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
     */
    int updateStopStat(UserInfoMgmtVo userInfoMgmtVo);
    
	/**
	* @Description : 사용자정지상태 리스트를 가져온다 
	* @Param  : UserInfoMgmtVo
	* @Return : UserInfoMgmtVo
	* @Author : 권오승
	* @Create Date : 2018.06.01
	 */
	List<?> getUserStopStatusList(UserInfoMgmtVo userInfoMgmtVo);
	
	List<UserInfoMgmtVo> getUserStopStatusListExcel(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	 * 사용자정지상태변경
	 * @param param
	 * @throws Exception
	 */
	public void updateUserStatusChg(UserInfoMgmtVo userInfoMgmtVo);
	
	
	/**
	 * 사용자정지상태이력 insert
	 *
	 * @param param
	 * @throws Exception
	 */
	public void updateUserInfoHst(UserInfoMgmtVo userInfoMgmtVo);
	
	
	/**
	 * 사용자정지update
	 *
	 * @param param
	 * @throws Exception
	 */
	public void updateUserStopStat(UserInfoMgmtVo userInfoMgmtVo);
	
    /**
     * @Description : 사용자맥주소 관리 LIST 항목을 가져온다.
     * @Param  : UserInfoMgmtVo
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2018. 9. 28.
     */
    List<?> selectUserMacAddressMgmtList(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자맥주소 정보 생성
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    int insertUserMacAddressMgmt(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자맥주소 관리 이력
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    int insertUserMacAddressHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자맥주소 수정
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    int updateUserMacAddressMgmt(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자맥주소 삭제
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    int deleteUserMacAddressMgmt(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자맥주소 건수조회
     * @Param  : UserInfoMgmtVo
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    int intCntUserMacAddress(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자조회 LIST 항목을 가져온다.
     * @Param  : UserInfoMgmtVo
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2018. 10. 17.
     */
    List<?> selectSalesUserInfoMgmtList(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자조회 리스트를 가져온다(엑셀다운로드)
     * @Param  : UserInfoMgmtVo
     * @Return : UserInfoMgmtVo
     * @Author : 권성광
     * @Create Date : 2018.10.19
      */
    List<UserInfoMgmtVo> getSalesUserInfoMgmtListExcel(UserInfoMgmtVo userInfoMgmtVo);
    

	/**
	 * @Description : 사용자 승인 리스트를 가져온다.
	 */
	List<?> selectUserApprovalList(UserInfoMgmtVo userInfoMgmtVo);

	/**
	 * @Description : 사용자 승인 히스토리를 가져온다.
	 */
	List<?> selectUserApprovalHist(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	 * @Description : HST 정보 수정
	 */
    int updateUserHst(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 승인
     */
    int updateUserApproval(UserInfoMgmtVo userInfoMgmtVo);

    /**
     * @Description : 사용자 승인처리 체크
     */
    int userApprovalChk(UserInfoMgmtVo userInfoMgmtVo);
    
    /**
     * @Description : 사용자 승인 반려 
     */
    int deleteUserApproval(UserInfoMgmtVo userInfoMgmtVo);

    /**
     * @Description : 사용자 승인이력 추가
     */
    int insertUserAppHst(UserInfoMgmtVo userInfoMgmtVo);

	/**
	 * @Description : 사용자그룹 매핑
	 */
    int insertUsgGrpAsgn(UserInfoMgmtVo userInfoMgmtVo);
    
	/**
	 * @Description : 사용자그룹 매핑 HIST
	 */
    int insertUsgGrpAsgnHist(UserInfoMgmtVo userInfoMgmtVo);
    
	/**
	 * @Description : 사용자 승인 파일명을 가져온다.
	 */
    String getFile(String appFileId);

	/**
	 * @Description : 대리점 타입을 가져온다.
	 */
    String getOrgnType(UserInfoMgmtVo userInfoMgmtVo);

    /**
	 * @Description : 상위그룹이 V000018607(신사업추진팀)인지 확인한다.
	 */
	int getOrgnHgCnt(UserInfoMgmtVo userInfoMgmtVo);

	/**
	 * @Description : 고객정보 상세조회 이력 생성
	 */
	int insertSearchLog(Map<String, Object> pRequestParamMap);
    
	/**
	* @Description : 사용자 패스워드 초기화 리스트를 가져온다 
	 */
	List<?> getUserPwdResetList(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	* @Description : 사용자 패스워드 초기화 상태변경 
	 */
	public void updateUserPwdReset(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	* @Description : 사용자 패스워드 오류 횟수 초기화
	 */
	public void updateUserPassErrNum(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	 * @Description : 사용자 잠금상태 초기화 리스트를 가져온다 
	 */
	List<?> getUserLockResetList(UserInfoMgmtVo userInfoMgmtVo);
	
	/**
	 * @Description : 사용자 잠금상태 해제
	 */
	public void updateLockStatus(UserInfoMgmtVo userInfoMgmtVo);
}
