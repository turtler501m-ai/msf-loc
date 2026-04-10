package com.ktis.msp.cmn.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.login.mapper.LoginMapper;
import com.ktis.msp.cmn.login.mapper.UserApprMapper;
import com.ktis.msp.cmn.login.vo.MsgQueueVO;
import com.ktis.msp.cmn.login.vo.UserApprVO;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;
import com.ktis.msp.org.userinfomgmt.mapper.CanUserMgmtMapper;
import com.ktis.msp.org.userinfomgmt.vo.CanUserResVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;

@Service
public class UserApprService extends BaseService{

    @Autowired
    private UserApprMapper userApprMapper;
    
    @Transactional(rollbackFor=Exception.class)
    public String getIdCheck(String usrId){
    	logger.info(generateLogMsg("사용자 ID 중복체크 서비스 START."));
        return userApprMapper.getIdCheck(usrId);
    }
    
    @Crypto(decryptName="DBMSDec", fields = {"rprsenRrnum"})
	public List<?> getHeadAgencyOrgnList(OrgMgmtVO orgMgmtVO) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("getHeadAgencyOrgnList START."));
		logger.info(generateLogMsg("OrgMgmtVO == " + orgMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		List<?> listOrgMgmts = new ArrayList<OrgMgmtVO>();
		
		listOrgMgmts = userApprMapper.getHeadAgencyOrgnList(orgMgmtVO);
		
		return listOrgMgmts;
	}
    
    /**
     * @Description : 사용자ID 중복체크 
     * @Param  : 
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
      */
     @Transactional(rollbackFor=Exception.class)
     public int isExistUsrId(UserApprVO userApprVo){
         logger.info(generateLogMsg("================================================================="));
         logger.info(generateLogMsg("사용자 ID 중복체크 서비스 START."));
         logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + userApprVo.toString()));
         logger.info(generateLogMsg("================================================================="));
         
         int resultCnt = userApprMapper.isExistUsrId(userApprVo);
         
         return resultCnt;
     }
     
     /**
      * @Description : 사용자 정보를 생성한다.
      * @Param  : UserApprVO
      * @Return : int
      * @Author : 권오승
      * @Create Date : 2019. 6. 20.
      */
 	@Transactional(rollbackFor=Exception.class)
 	@Crypto(encryptName="SHA512", fields = {"pass"}, throwable=true)
     public int insertUserInfoMgmt(UserApprVO userApprVo) throws MvnoServiceException {
     	
 		logger.info(generateLogMsg("================================================================="));
 		logger.info(generateLogMsg("사용자 정보 등록 서비스 START."));
 		logger.info(generateLogMsg("userInfoMgmtVo == " + userApprVo.toString()));
 		logger.info(generateLogMsg("================================================================="));
 		
 		int resultCnt = 0;
 		int valdCnt = 0;
 		
 		valdCnt = userApprMapper.selectValCnt(userApprVo);
 		
 		if(valdCnt == 0)
 		{
 			throw new MvnoServiceException("조직정보가 올바르지 않습니다. <br/> 조직을 다시 선택하세요.");
 		}
 		
 	    resultCnt = userApprMapper.insertUserInfoMgmt(userApprVo);
 		
 		logger.info(generateLogMsg("등록 건수 = ") + resultCnt);
 		
 		if(resultCnt > 0){
 		    resultCnt = userApprMapper.intCntUsrHst(userApprVo);
 		    if(resultCnt == 0){
 		    	userApprVo.setSeqNum("1");
 		        resultCnt = userApprMapper.insertUserHst(userApprVo);
 		    } else {
 		        resultCnt= insertUserHst(userApprVo);
 		    }
 		}
         return resultCnt;
     }
 	
    /**
     * @Description : 사용자이력 추가
     * @Param  : 
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
	@Transactional(rollbackFor=Exception.class)
    @Crypto(encryptName="SHA512", fields = {"pass"})
    public int insertUserHst(UserApprVO userApprVo){
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("사용자 이력 추가 서비스 START."));
        logger.info(generateLogMsg("userInfoMgmtVo == " + userApprVo.toString()));
        logger.info(generateLogMsg("================================================================="));
        
        String maxSeq = userApprMapper.maxSeqUsrHst(userApprVo);
        int addSeq = Integer.parseInt(maxSeq)+1;
        userApprVo.setSeqNum(Integer.toString(addSeq));
        
        int resultCnt = userApprMapper.insertUserHst(userApprVo);
        
        return resultCnt;
    }
	/**
     * @Description :승인자 ID가져오기
     * @Param  : 
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
	@Transactional(rollbackFor=Exception.class)
    public String getAppUserId(String orgnId){
    	logger.info(generateLogMsg("승인자 ID 가져오기 START."));
 		return userApprMapper.getAppUserId(orgnId);
    }
	
    /**
     * @Description : 파일 정보를 등록한다.
     * @Param  : UserApprVO
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2014. 8. 14.
     */
    @Transactional(rollbackFor=Exception.class)
    public int insertFile(UserApprVO userApprVo){
    	
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("insertFile START."));
		logger.info(generateLogMsg("================================================================="));
		
		int resultCnt = userApprMapper.insertFile(userApprVo);
		
        return resultCnt;
    }
    
	/**
     * @Description :승인자 전화번호 가져오기
     * @Param  : 
     * @Return : int
     * @Author : 권오승
     * @Create Date : 2019. 6. 20.
     */
	@Transactional(rollbackFor=Exception.class)
    public String getAppUsrTel(String appUsrId){
    	logger.info(generateLogMsg("승인자 ID 가져오기 START."));
 		return userApprMapper.getAppUsrTel(appUsrId);
    }
    
}