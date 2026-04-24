package com.ktis.msp.batch.job.rcp.taxmgmt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.taxmgmt.mapper.TaxMgmtMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class TaxMgmtService extends BaseService {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private TaxMgmtMapper taxMgmtMapper;

	public TaxMgmtService() {
		setLogPrefix("[TaxMgmtService]");
	}	

	// LMS 전송
	// 매일 3시 30분 시작 			
	@Transactional(rollbackFor=Exception.class)
	public void taxSendTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("제세공과금 LMS 발송 START"));
    	LOGGER.info(generateLogMsg("================================================================="));
		
		try {
	    	LOGGER.info(generateLogMsg("제세공과금 select"));
	    	List<Map<String,Object>> list = taxMgmtMapper.selectTaxSendList();
	    	LOGGER.info(generateLogMsg("제세공과금 : " + list.size() + " 건"));
	    	
	    	if(list.size() > 0){
	    		for(Map<String,Object> map : list){
	    			int sendCnt = 0;
	    			int agentCnt = 0;
	    			int reqCnt = Integer.parseInt(map.get("SEND_CNT").toString());
	    			KtSmsCommonVO smsVO = new KtSmsCommonVO();
	    			smsVO.setTaxNo(map.get("TAX_NO").toString());
	    			// SMS 발송 등록
	    			if("Y".equals(map.get("KAKAO_YN").toString())){
	    				// 카카오 알림톡
	    				smsVO.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
	    				smsVO.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));
	    				sendCnt = taxMgmtMapper.insertKakaoTaxMsgQueue(smsVO);
	    			}else{
	    				sendCnt = taxMgmtMapper.insertTaxMsgQueue(smsVO);
	    			}
	    			if(sendCnt == reqCnt){
	    				smsVO.setTaxUsgYn("S");
	    				LOGGER.info("[제세공과금문자 예약발송] TAX_NO:"+smsVO.getTaxNo()+", "+sendCnt+"건 처리완료");
	    			}else{
	    				smsVO.setTaxUsgYn("F");
	    				LOGGER.error("[제세공과금문자 예약발송] 건수불일치(TAX_NO:"+smsVO.getTaxNo()+", 건수:"+sendCnt+"/"+reqCnt+")");
	    			}
	    			
	    			// 법정대리인 문자 발송(법정대리인 문자발송은 건수 및 상태값 변화 X )
	    			if("Y".equals(map.get("AGENT_YN").toString())){
	    				// 대상자 조회
	    				agentCnt = taxMgmtMapper.selectAgentCnt(smsVO);
	    				if(agentCnt > 0){
	    					if("Y".equals(map.get("KAKAO_YN").toString())){
	    						// 카카오 알림톡
	    						smsVO.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
	    						smsVO.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));
	    						taxMgmtMapper.insertKakaoAgentMsgQueue(smsVO);
	    					}else{
	    						taxMgmtMapper.insertAgentMsgQueue(smsVO);
	    					}
	    				}
	    			}
	    			// MST 테이블에 상태값 변경
	    			taxMgmtMapper.updateTaxSendList(smsVO);
	    			LOGGER.info(generateLogMsg("제세공과금 LMS 발송 완료"));
	    		}
			} else {
				LOGGER.info("LMS 대상 없음");				
			}
	    	
	    	// 매월 25일일 경우 전달 제세공과금 삭제 로직 구현
	    	Date today = new Date();
	    	Calendar calendar = Calendar.getInstance();
	        calendar.setTime(today);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);
	        if (day == 25) {
	        	List<Map<String,Object>> delList = taxMgmtMapper.selectDelTaxData();
	        	if(delList.size() > 0){
	        		taxMgmtMapper.deleteTaxSendData();
	        	}
	        }
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2017", e);
		}
	}    


	//  LMS 재전송
	//  1차 재발송 : 최초 발송일시 기준 +2일까지 회신정보가 없을 경우 대상자/법정대리인에게 문자 자동 재발송
    //  2차 재발송 : 최초 발송일시 기준 +4일까지 회신정보가 없을 경우 대상지/법정대리인에게 문자 자동 재발송
	@Transactional(rollbackFor=Exception.class)
	public void taxReSendTrgt(BatchCommonVO batchVo) throws MvnoServiceException {
		LOGGER.info(generateLogMsg("================================================================="));
		LOGGER.info(generateLogMsg("제세공과금 LMS 재발송 START"));
		LOGGER.info(generateLogMsg("================================================================="));
		
		try {
			LOGGER.info(generateLogMsg("제세공과금 재발송 select"));
			List<Map<String,Object>> list = taxMgmtMapper.selectTaxReSendList();

			if(list.size() > 0){
				for(Map<String,Object> map : list){
					int agentCnt = 0;
					KtSmsCommonVO smsVO = new KtSmsCommonVO();
					// 재발송
					smsVO.setReSendYn("Y");
					smsVO.setTaxNo(map.get("TAX_NO").toString());
					// SMS 발송 등록
					if("Y".equals(map.get("KAKAO_YN").toString())){
						// 카카오 알림톡
						smsVO.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
						smsVO.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));
						taxMgmtMapper.insertKakaoTaxReMsgQueue(smsVO);
					}else{
						taxMgmtMapper.insertTaxReMsgQueue(smsVO);
					}
					
					// 법정대리인 문자 발송
					agentCnt = taxMgmtMapper.selectAgentCnt(smsVO);
					if(agentCnt > 0){
						if("Y".equals(map.get("KAKAO_YN").toString())){
							// 카카오 알림톡
							smsVO.setMsgTypeSecond(propertiesService.getString("msg.kakao.ktKNextType"));
							smsVO.setkSenderkey(propertiesService.getString("msg.kakao.ktSenderKey"));
							taxMgmtMapper.insertKakaoAgentReMsgQueue(smsVO);
						}else{
							taxMgmtMapper.insertAgentReMsgQueue(smsVO);
						}
					}
				}
			} else {
				LOGGER.info("LMS 대상 없음");				
			}
			
			LOGGER.info(generateLogMsg("제세공과금 재발송 종료"));
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP2017", e);
		}
	}    
}
