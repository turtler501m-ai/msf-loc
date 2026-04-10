package com.ktmmobile.mcp.app.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.app.dao.AppPushDao;
import com.ktmmobile.mcp.app.dto.PushBaseVO;
import com.ktmmobile.mcp.app.dto.PushSendDto;
import com.ktmmobile.mcp.common.util.ObjectUtils;


@Repository
public class AppPushSvcImpl implements AppPushSvc {

    private static final Logger logger = LoggerFactory.getLogger(AppPushSvcImpl.class);

    @Autowired
    private AppPushDao appPushDao;

    @Override
    public void immediatelyPushSend(PushSendDto pushSendDto){

    	if(!"".equals(pushSendDto.getPushSndSetSno()) && !"".equals(pushSendDto.getUserId())){

    		PushBaseVO pushBaseVO = appPushDao.selectPushSetupBase(pushSendDto.getPushSndSetSno());

    		if(!Objects.isNull(pushBaseVO) && !"".equals(pushBaseVO.getSndMsg())) {
    			String pushSndMsg = pushBaseVO.getSndMsg();

    			String replaceStr[] = pushSendDto.getMsgArr();

    			for(int i=0; i<replaceStr.length; i++) {
    				String tmpStr = "[$]"+i+"[$]";

    				pushSndMsg = pushSndMsg.replaceAll(tmpStr, replaceStr[i]);
    			}

    			pushBaseVO.setSndMsg(pushSndMsg);

    			pushBaseVO.setPushSndSetSno(pushSendDto.getPushSndSetSno());
    			pushBaseVO.setUserId(pushSendDto.getUserId());
    			pushBaseVO.setServerColctYn("N"); // 서버수집여부
    			pushBaseVO.setAppColctYn("N"); // 앱수집여부
    			pushBaseVO.setSndCnt(0); // 발송횟수
    			pushBaseVO.setSndTestYn("N"); //발송테스트여부
    			pushBaseVO.setTgtServer("portal");

    			int retval = appPushDao.insertPushTargetUserSnd(pushBaseVO);
    		}
    	}
    }

}
