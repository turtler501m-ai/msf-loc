package com.ktis.msp.org.usercanmgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.org.usercanmgmt.mapper.UserCanMgmtMapper;
import com.ktis.msp.org.usercanmgmt.vo.UserCanMgmtListVo;
import com.ktis.msp.org.usercanmgmt.vo.UserCanMgmtVo;

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
	 * @Description : 사용자 정보 리스트를 조회 한다.
	 * @Param  : userCanMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2015. 8. 18.
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<?> selectUserCanMgmtList(UserCanMgmtVo userCanMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지자 정보 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userCanMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> userCanMgmtVoList = new ArrayList<UserCanMgmtVo>();
		
		userCanMgmtVoList = userCanMgmtMapper.selectUserCanMgmtList(userCanMgmtVo);
		
		return userCanMgmtVoList;
		
	}

	/**
	 * @Description : 사용자 정보 리스트를 조회 한다. 엑셀용
	 * @Param  : UserCanMgmtVo
	 * @Return : List<?>
	 * @Author : 장익준
	 * @Create Date : 2015. 8. 19.
	 */
	public List<?> selectUserCanMgmtListEx(UserCanMgmtVo userCanMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지자 정보 엑셀 조회 서비스 START."));
        logger.info(generateLogMsg("Return Vo [powerTmntMgmtVo] = " + userCanMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> userCanMgmtVoList = new ArrayList<UserCanMgmtVo>();
		
		userCanMgmtVoList = userCanMgmtMapper.selectUserCanMgmtListEx(userCanMgmtVo);
		
		return userCanMgmtVoList;
	}
	
	/**
	 * @Description : 해지자 처리 YN
	 * @Param  : UserCanMgmtListVo
	 * @Return : int
	 * @Author : 장익준
	 * @Create Date : 2015. 8. 19.
	 */
    @Transactional(rollbackFor=Exception.class)
	public int updateResultYN(UserCanMgmtListVo userCanMgmtListVo){
		
		int returnCnt = 0;
		
		returnCnt = userCanMgmtMapper.updateResultYN(userCanMgmtListVo);
		
		return returnCnt;
	}

    /**
     * @Description : 웹 일반회원 12개월 동안 로그인 안한 유저 삭제
     * @Param  : String
     * @Return : int
     * @Author : 장익준
     * @Create Date : 2015. 9. 21.
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> userInfoCanService(Map<String, Object> param){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("웹 일반회원 12개월 동안 로그인 안한 유저 삭제 START"));
        logger.info(generateLogMsg("================================================================="));

        String usrId = null;
		
        //해지자 DB에 데이터 이관
		int returnCnt = userCanMgmtMapper.userInfoInsService(usrId);
		logger.info(generateLogMsg("=============생성건수=============" + returnCnt));
		
		if(returnCnt > 0)
		{
			//MSP 테이블 데이터 업데이트(CMN_WEBUSER_CAN_MST)
			returnCnt = userCanMgmtMapper.userInfoUpdService(usrId);
			logger.info(generateLogMsg("=============수정건수=============" + returnCnt));
			
			//MCP 테이블 데이터 삭제(MCP_USER)
			returnCnt = userCanMgmtMapper.userInfoDelService(usrId);
			logger.info(generateLogMsg("=============삭제건수=============" + returnCnt));
		}
		
		param.put("PROC_STAT_CD", "S");
		param.put("PROC_CNT", returnCnt);
		param.put("READ_CNT", returnCnt);
		
		return param;
    }
}
