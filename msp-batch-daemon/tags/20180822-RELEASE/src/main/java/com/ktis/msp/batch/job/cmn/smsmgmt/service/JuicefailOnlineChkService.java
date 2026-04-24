package com.ktis.msp.batch.job.cmn.smsmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.mapper.JuicefailOnlineChkMapper;
import com.ktis.msp.batch.util.SFTPUtil;

@Service
public class JuicefailOnlineChkService extends BaseService {
	@Autowired
	private JuicefailOnlineChkMapper juicefailOnlineChkMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public void JuicefailOnlineChk() throws MvnoServiceException {
		SFTPUtil sFtp = new SFTPUtil();
		String strJUICE_Ip = "112.175.98.115";
		String strJUICE_User = "mvno";
		String strJUICE_PW = "ktmm1101!";
		int strJUICE_Port = 22;
		String strJUICE_DIR = "/home/mvno/JUICE/data/fail.online/";
		String strExt = "_[a-zA-Z0-9]{3}.pkt$";
		
		int iChk = 0;
		try {
			LOGGER.info("JUICE Fail Online 전문 확인 시작.");
//			1. sFtp를 통해서 JUICE 접속.
			sFtp.init(strJUICE_Ip, strJUICE_User, strJUICE_PW, strJUICE_Port);
			
//			2. 디렉토리 /home/mvno/JUICE/data/fail.online에서 fail 전문이 존재하는지 확인.
			Vector<LsEntry> vec = null;
			vec = sFtp.ls(strJUICE_DIR);
			if(!vec.isEmpty()) {
				for(LsEntry File : vec) {
					
//					3. fail 전문이 존재한다면 갯수 파악.
					LOGGER.info("비교 : " + strExt + " == " + File.getFilename());
					Matcher m = Pattern.compile(strExt).matcher(File.getFilename());
					if(m.find()) {
						LOGGER.info(File.getFilename().toUpperCase() + "은 fail 전문.");
						iChk++;
					}
				}
				
				if(iChk > 0) {
//					4. fail 전문이 확인되면, 담당자 SMS 발송.
					List<String> list = new ArrayList<String>();
					list = juicefailOnlineChkMapper.getPhoneNumber();
					
					for(String strPhone : list) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("phone", strPhone);
						map.put("msg", iChk + "건의 juice online fail 발생.");
						
						juicefailOnlineChkMapper.setMsgQueue(map);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new MvnoServiceException("ECMN2002", e);
		} finally {
			LOGGER.info("JUICE Fail Online 전문 확인 종료.");
			sFtp.disconnection();
		}
	}
}
