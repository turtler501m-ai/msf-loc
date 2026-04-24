package com.ktis.msp.batch.job.rcp.custmgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.custmgmt.mapper.CustMgmtMapper;

@Service
public class CustMgmtService extends BaseService {
	
	@Autowired
	private CustMgmtMapper custMapper;
	
	/**
	 * 청소년유해물차단SMS
	 * @param param
	 * @return
	 * @throws MvnoServiceException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setYthBlockSms() throws MvnoServiceException{
		
		int procCnt = 0;
		
		String sendReqTime = "1000";
		
		String smsMsg = "[kt 엠모바일]\n"
			+ "청소년 가입자의 이동전화는 유해정보 차단장치 설치가 의무화 되었습니다.(네트워크 차단&애플리케이션 차단) 애플리케이션 차단의 자세한 내용은 와이즈유저 홈페이지(m.wiseuser.go.kr)를 참조하세요.";
		
		try {
			// 청소년유해차단 발송대상 추출
			List<Map<String, Object>> list = custMapper.getMinorSmsTrgtList();
			
			for(Map<String, Object> rtMap : list) {
				// 가입자본인
				rtMap.put("REQ_SEND_TM",	sendReqTime);	// 발송시간(시분)
				rtMap.put("MSG_TYPE",		"3");	// 문자유형(1:SMS, 3:MMS)
				rtMap.put("TEXT_TYPE",		"0");	// 텍스트유형(0:PLAIN, 1:HTML)
				rtMap.put("CALLBACK",		"18995000");
				rtMap.put("MSG",			smsMsg);
				rtMap.put("CTN", 			rtMap.get("SVC_TEL_NO"));
				
				System.out.println("rtMap.get('CTN') : " + rtMap.get("CTN"));
				
				custMapper.insertMsgQueue(rtMap);
				
				// 법정대리인
//				if(!rtMap.get("AGENT_TEL_NO").equals("")){
				if(!"".equals(rtMap.get("AGENT_TEL_NO"))) {
					rtMap.put("CTN", rtMap.get("AGENT_TEL_NO"));
					
					System.out.println("rtMap.get('AGENT_TEL_NO') : " + rtMap.get("AGENT_TEL_NO"));
					custMapper.insertMsgQueue(rtMap);
				}
				
				procCnt++;
			}
		} catch(Exception e) {
			throw new MvnoServiceException("ECLS1044", e);
		}
		
		return procCnt;
	}
	
}
