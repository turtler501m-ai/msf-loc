package com.ktis.msp.cmn.login.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.cmn.login.vo.MsgQueueVO;
import com.ktis.msp.cmn.login.vo.UserApprVO;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * <PRE>
 * 1. ClassName	:
 * 2. FileName	: UserApprMapper.java
 * 3. Package	: com.ktis.msp.cmn.login.mapper
 * 4. Commnet	:
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2019. 6. 19. 오후 3:58:10
 * </PRE>
 */
@Mapper("userApprMapper")
public interface UserApprMapper {

    String getIdCheck(String usrId);
    
    List<?> getHeadAgencyOrgnList(OrgMgmtVO orgMgmtVO);
    
    /**
    * @Description : 사용자 ID 중복체크 
    * @Param  : UserApprVO
    * @Return : int
    * @Author : 고은정
    * @Create Date :2019. 6. 20.
     */
    int isExistUsrId(UserApprVO userApprVo);
    
    /**
    * @Description : 조직 유무 체크 
    * @Param  : UserApprVO
    * @Return : int
    * @Author : 권오승
    * @Create Date : 2019. 6. 20.
     */
    int selectValCnt(UserApprVO userApprVo);
    
	/**
	 * @Description : 사용자 정보를 생성한다.
	 * @Param  : UserApprVO
	 * @Return : int
	 * @Author : 권오승
	 * @Create Date : 2019. 6. 20.
	 */    
    int insertUserInfoMgmt(UserApprVO userApprVo);
    
    /**
     * @Description : 이력 건수 조회
     * @Param  : UserApprVO
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
    int intCntUsrHst(UserApprVO userApprVo);
    
    /**
     * @Description : 사용자이력 추가
     * @Param  : UserApprVO
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
    int insertUserHst(UserApprVO userApprVo);
    
    /**
     * @Description : MAX Seq 조회
     * @Param  : UserApprVO
     * @Return : String
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
    String maxSeqUsrHst(UserApprVO userApprVo);
    /**
     * @Description : 승인자 ID 가져오기
     * @Param  : String
     * @Return : String
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
    String getAppUserId(String orgnId);
    
    /**
     * @Description : 승인자 ID COUNT
     * @Param  : UserApprVO
     * @Return : String
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
    int getAppUserCount(UserApprVO userApprVo);
    
	/**
	 * @Description : 파일의 기본정보를 등록한다.
	 * @Param  : UserApprVO
	 * @Return : int
	 * @Author : 권오승
	 * @Create Date : 2019. 6. 20.
	 */    
    int insertFile(UserApprVO userApprVo);
    /**
	 * @Description : 승인자 전화번호 가져오기
	 * @Param  : UserApprVO
	 * @Return : int
	 * @Author : 권오승
	 * @Create Date : 2019. 6. 21.
	 */    
    String getAppUsrTel(String appUsrId);
    
}


