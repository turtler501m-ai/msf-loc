package com.ktis.msp.cmn.login.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.login.mapper.LoginMapper;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.MsgQueueReqVO;
import com.ktis.msp.org.userinfomgmt.mapper.CanUserMgmtMapper;
import com.ktis.msp.org.userinfomgmt.vo.CanUserResVO;

@Service
public class LoginService extends BaseService{

    @Autowired
    private LoginMapper loginMapper;
    
    @Autowired
    private SmsMgmtMapper smsMgmtMapper;
    
    @Autowired
	private CanUserMgmtMapper canUserMgmtMapper;

    public List<?> selectLogin(Map<String, Object> param){
        return loginMapper.selectLogin(param);
    }

    @Transactional(rollbackFor=Exception.class)
    public void insertLog(Map<String, Object> param){
        loginMapper.insertLog(param);
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateUserLastLoginDt(Map<String, Object> param){
        loginMapper.updateUserLastLoginDt(param);
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateUserPassErrNum(Map<String, Object> param){
        loginMapper.updateUserPassErrNum(param);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void updateUserOtpErrNum(Map<String, Object> param){
        loginMapper.updateUserOtpErrNum(param);
    }

    public Map<String, Object> selectLoginChk(Map<String, Object> param){
        return loginMapper.selectLoginChk(param);
    }
    
    public Map<String, Object> selectMacLoginChk(Map<String, Object> param){
        return loginMapper.selectMacLoginChk(param);
    }

    public Map<String, Object> selectAuthUseYN(Map<String, Object> param){
        return loginMapper.selectAuthUseYN(param);
    }
    
    public List<?> selectMacChkInfo(){
        return loginMapper.selectMacChkInfo();
    }

    @Transactional(rollbackFor=Exception.class)
    public void insertMsgQueue(MsgQueueReqVO vo){
        smsMgmtMapper.insertMsgQueue(vo);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void insertKtMsgQueue(KtMsgQueueReqVO vo){
        smsMgmtMapper.insertKtMsgQueue(vo);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public String isOtpUseKt(){
    	return smsMgmtMapper.isOtpUseKt();
    }
    
    @Transactional(rollbackFor=Exception.class)
    public void updateOtp(Map<String, Object> param){
        loginMapper.updateOtp(param);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public int updatePassInfo(Map<String, Object> param){
    	
    	int result = loginMapper.updatePassInfo(param);
    	
    	if (result == 1) {
            CanUserResVO canVO = new CanUserResVO();
            canVO.setUsrId((String)param.get("usrId"));
            int checkCnt = canUserMgmtMapper.checkCanUser(canVO);
            
            if(checkCnt > 0) {
            	canUserMgmtMapper.updatePassInfo(param);
            	canUserMgmtMapper.insertCanUserHst(canVO);
            }
    	}
    	    	
        return result;
    }

    @Transactional(rollbackFor=Exception.class)
    public String getUsrMskYn(String userId){
        return loginMapper.getUsrMskYn(userId);
    }
    
	@Transactional(rollbackFor=Exception.class)
	public List<?>  getUpMngTel(Map<String, Object> param){
		return loginMapper.getUpMngTel(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void insertMngMsg(MsgQueueReqVO vo){
		smsMgmtMapper.insertMsgQueue(vo);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateUsrStatusReq(Map<String, Object> param){
		return loginMapper.updateUsrStatusReq(param);
	}
	public Map<String, Object> selectUsrChk(Map<String, Object> param){
		return loginMapper.selectUsrChk(param);
	}

	public List<?> selectUsrOtpChk(Map<String, Object> param){
		return loginMapper.selectUsrOtpChk(param);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int selectUsrPwdReset(Map<String, Object> param){
		return loginMapper.selectUsrPwdReset(param);
	}
	
    public void insertUsrPwdReset(Map<String, Object> param){
        loginMapper.insertUsrPwdReset(param);
    }

    public void updateUserOtpChk(Map<String, Object> param){
    	loginMapper.updateUserOtpChk(param);
    }
}
