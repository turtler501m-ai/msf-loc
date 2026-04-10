package com.ktis.msp.org.userinfomgmt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.org.userinfomgmt.mapper.CanUserMgmtMapper;
import com.ktis.msp.org.userinfomgmt.mapper.UserInfoMgmtMapper;
import com.ktis.msp.org.userinfomgmt.vo.CanUserResVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;

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
public class UserInfoMgmtService extends BaseService {

	@Autowired
	private UserInfoMgmtMapper userInfoMgmtMapper;
	
	@Autowired
	private CanUserMgmtMapper canUserMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	public UserInfoMgmtService() {
		setLogPrefix("[UserInfoMgmtService] ");
	}
	
	/**
	 * @Description : 사용자 정보 리스트를 조회 한다.
	 * @Param  : userInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> selectUserInfoMgmtList(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 정보 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> userInfoMgmtVoList = new ArrayList<UserInfoMgmtVo>();
		
		userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
		userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		
		userInfoMgmtVoList = userInfoMgmtMapper.selectUserInfoMgmtList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();		
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",				"SYSTEM_ID");
		maskFields.put("kosIdMsk",				"SYSTEM_ID");
		maskFields.put("usrNmMsk",				"CUST_NAME");
		maskFields.put("mblphnNumMsk",			"MOBILE_PHO");
		maskFields.put("telNumMsk",				"MOBILE_PHO");
		maskFields.put("faxMsk",				"MOBILE_PHO");
		maskFields.put("emailMsk",				"EMAIL");
		
		maskingService.setMask(userInfoMgmtVoList, maskFields, paramMap);
		
		return userInfoMgmtVoList;
		
	}
	
	@Transactional(rollbackFor=Exception.class)
    public UserInfoMgmtVo selectUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 정보 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        UserInfoMgmtVo userInfoMgmt = userInfoMgmtMapper.selectUserInfoMgmt(userInfoMgmtVo);
        
        return userInfoMgmt;
        
    }
	
    /**
     * @Description : 사용자 정보를 생성한다.
     * @Param  : hndstModelVo
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2014. 8. 14.
     */
	@Transactional(rollbackFor=Exception.class)
	@Crypto(encryptName="SHA512", fields = {"pass"}, throwable=true)
    public int insertUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 정보 등록 서비스 START."));
		logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = 0;
		int valdCnt = 0;
		
		valdCnt = userInfoMgmtMapper.selectValCnt(userInfoMgmtVo);
		
		if(valdCnt == 0)
		{
			throw new MvnoServiceException("조직정보가 올바르지 않습니다. <br/> 조직을 다시 선택하세요.");
		}
		
	    resultCnt = userInfoMgmtMapper.insertUserInfoMgmt(userInfoMgmtVo);
		
		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
		
		if(resultCnt > 0){
		    resultCnt = userInfoMgmtMapper.intCntUsrHst(userInfoMgmtVo);
		    if(resultCnt == 0){
		        userInfoMgmtVo.setSeqNum("1");
		        resultCnt = userInfoMgmtMapper.insertUserHst(userInfoMgmtVo);
		    } else {
		        resultCnt= insertUserHst(userInfoMgmtVo);
		    }
		}
        return resultCnt;
    }

    /**
     * @Description : 사용자의 정보를 수정한다 (암호화용)
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 26.
     */
	@Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="SHA512", fields = {"pass"})
    public int updateUserInfoMgmtEncrypt(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 정보 수정 서비스 암호화 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        
        int resultCnt = 0;
        Date date = new Date();
        String strToDay = KtisUtil.toStr(date, KtisUtil.DATETIME_SHORT);
        
        try{
            userInfoMgmtVo.setPassLastRvisnDt(strToDay);
            
            resultCnt = userInfoMgmtMapper.updateUserInfoMgmt(userInfoMgmtVo);
            
            logger.info(generateLogMsg("수정 건수 = ") + resultCnt);
            if (resultCnt > 0){
                resultCnt= insertUserHst(userInfoMgmtVo);
            }
            
            // 해지고객 관리자에 등록된 내용인지 확인하고 수정함. 2017.06.13 TREXSHIN
            CanUserResVO canVO = new CanUserResVO();
            canVO.setUsrId(userInfoMgmtVo.getUsrId());
            int checkCnt = canUserMgmtMapper.checkCanUser(canVO);
            
            if(checkCnt > 0) {
            	canUserMgmtMapper.updateCanUserInfoMgmt(userInfoMgmtVo);
            	canUserMgmtMapper.insertCanUserHst(canVO);
            }
            
        } catch(Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
    * @Description : 사용자의 정보를 수정한다
    * @Param  : 
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 16.
     */
	@Transactional(rollbackFor=Exception.class)
    public int updateUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 정보 수정 서비스 비암호화 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        
        int resultCnt = 0;
        
		int valdCnt = 0;
		
		valdCnt = userInfoMgmtMapper.selectValCnt(userInfoMgmtVo);
		
		if(valdCnt == 0)
		{
			throw new MvnoServiceException("조직정보가 올바르지 않습니다. <br/> 조직을 다시 선택하세요.");
		}
		
        userInfoMgmtVo.setPassLastRvisnDt("");
        if ("Y".equals(loginService.getUsrMskYn(userInfoMgmtVo.getSessionUserId()))) {
            resultCnt = userInfoMgmtMapper.updateUserInfoMgmt(userInfoMgmtVo);
        } else {
            resultCnt = userInfoMgmtMapper.updateUserInfoMgmtMsk(userInfoMgmtVo);
        }
        
        logger.info(generateLogMsg("수정 건수 = ") + resultCnt);
        if (resultCnt > 0){
            resultCnt= insertUserHst(userInfoMgmtVo);
        }
        
        // 해지고객 관리자에 등록된 내용인지 확인하고 수정함. 2017.06.13 TREXSHIN
        CanUserResVO canVO = new CanUserResVO();
        canVO.setUsrId(userInfoMgmtVo.getUsrId());
        int checkCnt = canUserMgmtMapper.checkCanUser(canVO);
        
        if(checkCnt > 0) {
        	canUserMgmtMapper.updateCanUserInfoMgmt(userInfoMgmtVo);
        	canUserMgmtMapper.insertCanUserHst(canVO);
        }
        
        return resultCnt;
    }
    
    /**
     * @Description : 사용자의 정보를 삭제한다
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 26.
     */
	@Transactional(rollbackFor=Exception.class)
    public int deleteUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 정보 삭제 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0, resultCanCnt = 0;
        try{
            resultCnt = userInfoMgmtMapper.deleteUserInfoMgmt(userInfoMgmtVo);
            logger.info(generateLogMsg("삭제 계정 건수 = ") + resultCnt);   
            
            if (resultCnt > 0){
                userInfoMgmtVo.setDelYn("Y");
                // 20240105 계정 삭제시 권한도 삭제 처리
                userInfoMgmtMapper.deleteUserInfoMgmtAsgn(userInfoMgmtVo);
                // 사용자별 메뉴 권한 삭제
                userInfoMgmtMapper.deleteUserInfoMgmtUsrAsgn(userInfoMgmtVo);
                // 사용자 변경 이력
                insertUserHst(userInfoMgmtVo);
            }

            //사용자 해지자 권한 삭제
            resultCanCnt = userInfoMgmtMapper.deleteUserInfoMgmtCanAsgn(userInfoMgmtVo);
            logger.info(generateLogMsg("삭제 계정 해지자 건수 = ") + resultCanCnt);
            if (resultCanCnt > 0){
            	//사용자 해지자 메뉴 권한 삭제
                userInfoMgmtMapper.deleteUserInfoCanUsrAsgn(userInfoMgmtVo);
            	//해지자 권한변경 이력
            	userInfoMgmtMapper.insertCanUserHst(userInfoMgmtVo);
            }
        } catch(Exception e){
            logger.error(e);
        }
        return resultCnt;
    }

    /**
     * @Description : 사용자의 비밀번호를 초기화한다
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 8. 26.
     */
	@Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="SHA512", fields = {"pass"})
    public int initUserPassword(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 비밀번호 초기화 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));

        int resultCnt = 0;
        Date date = new Date();
        String strToDay = KtisUtil.toStr(date, KtisUtil.DATETIME_SHORT);
        
        try{
            userInfoMgmtVo.setPassLastRvisnDt(strToDay);
            
            resultCnt = userInfoMgmtMapper.initUserPassword(userInfoMgmtVo);
            
            logger.info(generateLogMsg("수정 건수 = ") + resultCnt);
            
            if (resultCnt > 0){
                resultCnt= insertUserHst(userInfoMgmtVo);
            }
        } catch(Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * @Description : 사용자이력 추가
     * @Param  : 
     * @Return : int
     * @Author : 고은정
     * @Create Date : 2014. 9. 1.
     */
	@Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="SHA512", fields = {"pass"})
    public int insertUserHst(UserInfoMgmtVo userInfoMgmtVo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 이력 추가 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        String maxSeq = userInfoMgmtMapper.maxSeqUsrHst(userInfoMgmtVo);
        int addSeq = Integer.parseInt(maxSeq)+1;
        userInfoMgmtVo.setSeqNum(Integer.toString(addSeq));
        
        int resultCnt = userInfoMgmtMapper.insertUserHst(userInfoMgmtVo);
        
        return resultCnt;
    }
    
    /**
     * @Description : 사용자이력 리스트 조회
     * @Param  : 
     * @Return : List<?>
     * @Author : 고은정
     * @Create Date : 2014. 9. 1.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<?> selectUserHst(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 이력 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> result = new ArrayList<UserInfoMgmtVo>();
        
        userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
        userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		
		result = userInfoMgmtMapper.selectUserHst(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();        
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
        
    }
    
    /**
    * @Description : 사용자ID 중복체크 
    * @Param  : 
    * @Return : int
    * @Author : 고은정
    * @Create Date : 2014. 9. 27.
     */
    @Transactional(rollbackFor=Exception.class)
    public int isExistUsrId(UserInfoMgmtVo userInfoMgmtVo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 ID 중복체크 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = userInfoMgmtMapper.isExistUsrId(userInfoMgmtVo);
        
        return resultCnt;
    }
    
    /**
     * @Description : 웹 일반회원 11개월 동안 로그인 안한 유저에게 mail발송.
     * @Param  : String
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 8. 18.
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> userInfoMgmtService(Map<String, Object> param){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("웹 일반회원 11개월 동안 로그인 안한 유저에게 mail발송 START"));
        logger.info(generateLogMsg("================================================================="));

        String usrId = null;
		
		int returnCnt = userInfoMgmtMapper.userInfoMgmtService(usrId);
		
		param.put("PROC_STAT_CD", "S");
		param.put("PROC_CNT", returnCnt);
		param.put("READ_CNT", returnCnt);
		
		return param;
    }
    
    /**
     * @Description : 사용자 admin 권한 체크.
     * @Param  : String
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 8. 18.
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean isAdminGroupUser(String usrId){
		return userInfoMgmtMapper.isAdminGroupUser(usrId) > 0;
    }

    /**
     * @Description : 사용자 DEV 권한 체크.
     * @Param  : String
     * @Return : int
     * @Author : 
     * @Create Date : 
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean isDevGroupUser(String usrId){
		return userInfoMgmtMapper.isDevGroupUser(usrId) > 0;
    }
    
    /**
     * @Description : 현재 패스워드 검증.
     * @Param  : String
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 8. 18.
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean isOldPass(UserInfoMgmtVo userInfoMgmtVo){
		return userInfoMgmtMapper.isOldPass(userInfoMgmtVo) > 0;
    }
    
    /**
     * @Description : 사용자 MAC 정보 확인 -> MAC 정보 이동(HIST) -> MAC 정보 삭제
     * @Param  : userId
     * @Return : String
     * @Author : 박준성
     * @Create Date : 2017. 06. 10.
     */
    @Transactional(rollbackFor=Exception.class)
    public int macAddressDelete(String userId){
    	
    	int userMacInfoCnt = 0;
		int userMacInfoMoveCnt = 0;
		int userMacInfoDelCnt = 0;
		
    	// 사용자 MAC 정보 확인
		userMacInfoCnt = userInfoMgmtMapper.userMacInfoCnt(userId);
		logger.info(">> 등록된 MAC 건수 : " + userMacInfoCnt);
		
		if(userMacInfoCnt > 0){
			// 사용자 MAC 정보 이동
			userMacInfoMoveCnt = userInfoMgmtMapper.userMacInfoMove(userId);
			logger.info(">> HST 에 이동한 MAC 건수 : " + userMacInfoMoveCnt);
				
			// 사용자 MAC 정보 삭제
			userMacInfoDelCnt = userInfoMgmtMapper.userMacInfoDel(userId);
			logger.info(">> 삭제한 MAC 건수 : " + userMacInfoDelCnt);
		}
				
		return userMacInfoDelCnt;
    }
    
    /**
	 * @Description : 판매점 사용자 정보 리스트를 조회 한다.
	 * @Param  : userInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
	public List<?> selectShopUserInfoMgmtList(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 정보 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> result = new ArrayList<UserInfoMgmtVo>();
		
		result = userInfoMgmtMapper.selectShopUserInfoMgmtList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",	"SYSTEM_ID");
		maskFields.put("usrNm",		"CUST_NAME");
		maskFields.put("mblphnNum",	"MOBILE_PHO");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	/**
     * @Description : 판매점 사용자의 정보를 수정한다
     * @Param  : userInfoMgmtVo
     * @Return : int
     * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
     */
	@Transactional(rollbackFor=Exception.class)
    public int updateShopUserInfoMgmt(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
        
        int resultCnt = userInfoMgmtMapper.updateShopUserInfoMgmt(userInfoMgmtVo);
        
        if (resultCnt > 0){
            resultCnt= insertUserHst(userInfoMgmtVo);
        }
                
        return resultCnt;
    }
	
	/**
     * @Description : 사용자 상태를 초기화한다
     * @Param  : userInfoMgmtVo
     * @Return : int
     * @Author : 장익준
	 * @Create Date : 2018. 6. 14.
     */
	@Transactional(rollbackFor=Exception.class)
    public int updateStopStatusInit(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
        
        int resultCnt = userInfoMgmtMapper.updateStopStatusInit(userInfoMgmtVo);
        
        if (resultCnt > 0){
        	userInfoMgmtMapper.updateStopStat(userInfoMgmtVo);
            resultCnt= insertUserHst(userInfoMgmtVo);
        }
                
        return resultCnt;
    }
	/**
	 * @Description : 사용자정지상태 리스트를 조회 한다.
	 * @Param  : userInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 01.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> getUserStopStatusList(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자정지상태 리스트 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> result = new ArrayList<UserInfoMgmtVo>();
		
		userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
		userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		
		result = userInfoMgmtMapper.getUserStopStatusList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",	"SYSTEM_ID");
		maskFields.put("usrNm",		"CUST_NAME");
		maskFields.put("procNm",	"CUST_NAME");
				
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
		
	}
	
	
	/**
	 * 사용자상태변경
	 * @param vo
	 * <li>taxbillSrlNum=계산서일련번호</li>
	 * <li>userId=사용자id</li>
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateUserStatusChg(UserInfoMgmtVo vo) {
				
		if(vo.getUsrId() == null || "".equals(vo.getUsrId())){
			throw new MvnoRunException(-1, "사용자ID 누락");
		}
		userInfoMgmtMapper.updateUserStopStat(vo);	
		userInfoMgmtMapper.updateUserStatusChg(vo);
		userInfoMgmtMapper.updateUserInfoHst(vo);
			
		}
	
	/**
	 * @Description : 사용자정지상태 리스트를 조회 한다.
	 * @Param  : userInfoMgmtVo
	 * @Return : List<?>
	 * @Author : 권오승
	 * @Create Date : 2018. 07. 26.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<UserInfoMgmtVo> getUserStopStatusListExcel(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자정지상태 엑셀다운로드 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<UserInfoMgmtVo> result = new ArrayList<UserInfoMgmtVo>();
		userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
		userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		result = userInfoMgmtMapper.getUserStopStatusListExcel(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
		
	}
	
	/**
     * @Description : 사용자맥주소 관리 리스트를 조회 한다.
     * @Param  : userInfoMgmtVo
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<?> selectUserMacAddressMgmtList(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 관리 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> result = new ArrayList<UserInfoMgmtVo>();
        
        userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toUpperCase());
        
        result = userInfoMgmtMapper.selectUserMacAddressMgmtList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());
		
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",	"SYSTEM_ID");
		maskFields.put("usrNm",		"CUST_NAME");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
        
    }
    
    /**
     * @Description : 사용자맥주소 정보를 생성한다.
     * @Param  : userInfoMgmtVo
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 9. 28.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertUserMacAddressMgmt(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 등록 서비스 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        resultCnt = userInfoMgmtMapper.intCntUserMacAddress(userInfoMgmtVo);
        
        // 똑같은 맥주소 중복등록 방지
        if(resultCnt < 1){
            // 사용자맥주소 등록
            resultCnt = userInfoMgmtMapper.insertUserMacAddressMgmt(userInfoMgmtVo);
            // 사용자맥주소 관리 이력 추가
            resultCnt = userInfoMgmtMapper.insertUserMacAddressHst(userInfoMgmtVo);
        } 
        
        return resultCnt;
    }
    
    /**
     * @Description : 사용자맥주소 관리 정보를 수정한다
     * @Param  : 
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    @Transactional(rollbackFor=Exception.class)
    public int updateUserMacAddressMgmt(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException{
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 관리 정보 수정 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        try{
            resultCnt = userInfoMgmtMapper.intCntUserMacAddress(userInfoMgmtVo);
            
            if (resultCnt > 0){
                resultCnt = userInfoMgmtMapper.updateUserMacAddressMgmt(userInfoMgmtVo);
                resultCnt = userInfoMgmtMapper.insertUserMacAddressHst(userInfoMgmtVo);
            }
            
        } catch(Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
     * @Description : 사용자맥주소 관리 정보를 삭제한다
     * @Param  : 
     * @Return : int
     * @Author : 권성광
     * @Create Date : 2018. 10. 1.
     */
    @Transactional(rollbackFor=Exception.class)
    public int deleteUserMacAddressMgmt(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException{
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 관리 정보 삭제 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;
        try{
            
            resultCnt = userInfoMgmtMapper.intCntUserMacAddress(userInfoMgmtVo);
        
            if (resultCnt > 0){
                resultCnt = userInfoMgmtMapper.insertUserMacAddressHst(userInfoMgmtVo);
                resultCnt = userInfoMgmtMapper.deleteUserMacAddressMgmt(userInfoMgmtVo);
            }
        } catch(Exception e){
            logger.error(e);
        }
        return resultCnt;
    }
    
    /**
    * @Description : 사용자맥주소 중복체크
    * @Param  : 
    * @Return : int
    * @Author : 권성광
    * @Create Date : 2018. 10. 1.
     */
    @Transactional(rollbackFor=Exception.class)
    public int isExistMacAddress(UserInfoMgmtVo userInfoMgmtVo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자맥주소 중복체크 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = userInfoMgmtMapper.intCntUserMacAddress(userInfoMgmtVo);
        
        return resultCnt;
    }
    
    /**
     * @Description : 사용자 조회
     * @Param  : userInfoMgmtVo
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2018. 10. 17.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<?> selectSalesUserInfoMgmtList(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List<?> result = new ArrayList<UserInfoMgmtVo>();
        
        userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
        userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
        
        result = userInfoMgmtMapper.selectSalesUserInfoMgmtList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
        
    }
    
    /**
     * @Description : 사용자조회 리스트
     * @Param  : userInfoMgmtVo
     * @Return : List<?>
     * @Author : 권성광
     * @Create Date : 2018. 10. 19.
     */
    @Transactional(rollbackFor=Exception.class)
    public List<UserInfoMgmtVo> getSalesUserInfoMgmtListExcel(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자조회 엑셀다운로드 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        List<UserInfoMgmtVo> result = new ArrayList<UserInfoMgmtVo>();
        result = userInfoMgmtMapper.getSalesUserInfoMgmtListExcel(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		return result;
        
    }
    

	/**
	 * @Description : 사용자 승인 리스트를 조회 한다.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> selectUserApprovalList(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 리스트 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> result = new ArrayList<UserInfoMgmtVo>();
		
		userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
		userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		
		result = userInfoMgmtMapper.selectUserApprovalList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",			"SYSTEM_ID");
		maskFields.put("usrNm",				"CUST_NAME");
		maskFields.put("appUsrNm",			"CUST_NAME");
		maskFields.put("mblphnNum",			"MOBILE_PHO");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
		
	}

	/**
	 * @Description : 사용자 승인 히스토리를 조회 한다.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> selectUserApprovalHist(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 히스토리 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> result = new ArrayList<UserInfoMgmtVo>();
		
		result = userInfoMgmtMapper.selectUserApprovalHist(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrId",				"SYSTEM_ID");
		maskFields.put("usrNm",				"CUST_NAME");
		maskFields.put("appUsrNm",			"CUST_NAME");
		maskFields.put("mblphnNum",			"MOBILE_PHO");
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
		
	}
	
    /**
    * @Description : 사용자 승인
     */
	@Transactional(rollbackFor=Exception.class)
	@Crypto(encryptName="SHA512", fields = {"pass"}, throwable=true)
    public int updateUserApproval(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 승인 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;

    	if(userInfoMgmtMapper.userApprovalChk(userInfoMgmtVo) == 0){
    		// 이미 처리됬다면 별다른 처리 없이 리턴
    		return resultCnt;
    	}
        
        userInfoMgmtVo.setAppYn("A");
        userInfoMgmtVo.setUsgYn("Y");
        resultCnt = userInfoMgmtMapper.updateUserApproval(userInfoMgmtVo);
        
        logger.info(generateLogMsg("수정 건수 = ") + resultCnt);

        if(resultCnt > 0){
            insertUserAppHst(userInfoMgmtVo);
            if("SU".equals(userInfoMgmtMapper.getOrgnType(userInfoMgmtVo))){
            	userInfoMgmtVo.setGrpId("G_PPS_AG"); //선불대리점
            } else {
            	userInfoMgmtVo.setGrpId("G_AGENT");	//후불대리점
            }
            userInfoMgmtMapper.insertUsgGrpAsgn(userInfoMgmtVo);
            userInfoMgmtMapper.insertUsgGrpAsgnHist(userInfoMgmtVo);
        }
        
        // 해지고객 관리자에 등록된 내용인지 확인하고 수정함. 2017.06.13 TREXSHIN
        CanUserResVO canVO = new CanUserResVO();
        canVO.setUsrId(userInfoMgmtVo.getUsrId());
        int checkCnt = canUserMgmtMapper.checkCanUser(canVO);
        
        if(checkCnt > 0) {
        	canUserMgmtMapper.updateCanUserInfoMgmt(userInfoMgmtVo);
        	canUserMgmtMapper.insertCanUserHst(canVO);
        }
        
        return resultCnt;
    }
	
    
    /**
     * @Description : 사용자 승인 반려
     */
	@Transactional(rollbackFor=Exception.class)
    public int deleteUserApproval(UserInfoMgmtVo userInfoMgmtVo){
        
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 승인 반려 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        int resultCnt = 0;

        String maxSeq = userInfoMgmtMapper.maxSeqUsrHst(userInfoMgmtVo);
        userInfoMgmtVo.setSeqNum(maxSeq);

    	if(userInfoMgmtMapper.userApprovalChk(userInfoMgmtVo) < 1){
    		// 이미 처리됬다면 별다른 처리 없이 리턴
    		return resultCnt;
    	}
    	
        try{
        	
            userInfoMgmtVo.setDelYn("Y");
            userInfoMgmtVo.setAppYn("C");
            userInfoMgmtVo.setUsgYn("N");
            resultCnt = userInfoMgmtMapper.updateUserApproval(userInfoMgmtVo);
            
            insertUserAppHst(userInfoMgmtVo);
            
            resultCnt = userInfoMgmtMapper.deleteUserApproval(userInfoMgmtVo);
        
            logger.info(generateLogMsg("삭제 건수 = ") + resultCnt);
            
        } catch(Exception e){
            logger.error(e);
        }
        return resultCnt;
    }

    /**
     * @Description : 사용자 승인이력 추가
     */
	@Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="SHA512", fields = {"pass"})
    public int insertUserAppHst(UserInfoMgmtVo userInfoMgmtVo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 승인이력 추가 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userInfoMgmtVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        String maxSeq = userInfoMgmtMapper.maxSeqUsrHst(userInfoMgmtVo);
        int addSeq = Integer.parseInt(maxSeq)+1;
        userInfoMgmtVo.setSeqNum(Integer.toString(addSeq));
        
        int resultCnt = userInfoMgmtMapper.insertUserAppHst(userInfoMgmtVo);
        
        return resultCnt;
    }	

    @Transactional(rollbackFor=Exception.class)
    public void insertMsgQueue(MsgQueueReqVO vo){
    	smsMgmtMapper.insertMsgQueue(vo);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void insertKtMsgQueue(KtMsgQueueReqVO vo){
        smsMgmtMapper.insertKtMsgQueue(vo);
    }
    
	/**
	 * @Description : 사용자 승인 파일명을 가져온다.
	 */
	@Transactional(rollbackFor=Exception.class)
	public String getFile(String appFileId){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 승인 파일명 조회 START."));
		logger.info(generateLogMsg("================================================================="));		

		return userInfoMgmtMapper.getFile(appFileId);		
	}

	
	/**
	 * 
	 * @Description : 상위그룹이 V000018607(신사업추진팀)인지 확인한다.
	 * 
	 */
	public boolean chkOrgnHg(UserInfoMgmtVo userInfoMgmtVo) {
		
		int resultCnt = userInfoMgmtMapper.getOrgnHgCnt(userInfoMgmtVo);
		
		if(resultCnt > 0)
			return true;
		
		return false;
	}

	/**
	 * @Description : 고객정보 상세조회 이력 생성
	 */
	@Transactional(rollbackFor=Exception.class)
	public void insertSearchLog(Map<String, Object> pRequestParamMap) {
		
		userInfoMgmtMapper.insertSearchLog(pRequestParamMap);
		
	}
	
	
	/**
	 * @Description : 사용자 패스워드 초기화 신청 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 이승국
	 * @Create Date : 2024. 08. 19.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> getUserPwdResetList(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 패스워드 초기화 신청 리스트 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> result = new ArrayList<UserInfoMgmtVo>();
		
		userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
		userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		
		result = userInfoMgmtMapper.getUserPwdResetList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",	"SYSTEM_ID");
		maskFields.put("usrNm",		"CUST_NAME");
		maskFields.put("procNm",	"CUST_NAME");
				
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
		
	}
	
	
	/**
	 * 사용자 패스워드 초기화
	 * @param vo
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateUserPwdReset(UserInfoMgmtVo vo) {
				
		if(vo.getUsrId() == null || "".equals(vo.getUsrId())){
			throw new MvnoRunException(-1, "사용자ID 누락");
		}
		userInfoMgmtMapper.updateUserPwdReset(vo);	
	}

	/**
	 * 사용자 패스워드 오류 횟수 초기화
	 * @param vo
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
    public void updateUserPassErrNum(UserInfoMgmtVo userInfoMgmtVo) throws MvnoServiceException {
        userInfoMgmtMapper.updateUserPassErrNum(userInfoMgmtVo);
    }
	
	/**
	 * @Description : 사용자 잠금상태 해제 START
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2026.01.28
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateLockStatus(UserInfoMgmtVo userInfoMgmtVo) {
		userInfoMgmtMapper.updateLockStatus(userInfoMgmtVo);
	}
	
	/**
	 * @Description : 사용자 잠금상태 초기화 신청 리스트 조회
	 * @Param  : userInfoMgmtVo
	 * @Return : ModelAndView
	 * @Author : 이승국
	 * @Create Date : 2026. 01. 28.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> getUserLockResetList(UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("사용자 잠금상태 초기화 신청 리스트 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userInfoMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> result = new ArrayList<UserInfoMgmtVo>();
		
		userInfoMgmtVo.setUsrId(userInfoMgmtVo.getUsrId().toLowerCase());
		userInfoMgmtVo.setUsrNm(userInfoMgmtVo.getUsrNm().toLowerCase());
		
		result = userInfoMgmtMapper.getUserLockResetList(userInfoMgmtVo);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userInfoMgmtVo.getSessionUserId());

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("usrIdMsk",	"SYSTEM_ID");
		maskFields.put("usrNm",		"CUST_NAME");
				
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
		
	}
	
}
