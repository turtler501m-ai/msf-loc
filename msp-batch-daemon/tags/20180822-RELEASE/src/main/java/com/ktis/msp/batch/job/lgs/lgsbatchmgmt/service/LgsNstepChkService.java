package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.mapper.LgsNstepChkMapper;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.mapper.SmsCommonMapper;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;

@Service
public class LgsNstepChkService extends BaseService {
	@Autowired
	private LgsNstepChkMapper lgsnstepchkmapper;
	
	@Autowired
	private SmsCommonMapper smsCommonMapper;
	
	public LgsNstepChkService() {
		setLogPrefix("[LgsNstepChkService] ");
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void lgsNstepChkNSms(BatchCommonVO batchVo) throws MvnoServiceException {
		try {
			int iCnt = lgsnstepchkmapper.getLgsNstepProgressDateCnt();
			if(iCnt > 0) {
				String[] listSmsPhonenumber = lgsnstepchkmapper.getSendSmsPhonenumberList();
				for(String strPhonenumber : listSmsPhonenumber) {
					SmsCommonVO smsVO = new SmsCommonVO();
					smsVO.setSubject("kt m 모바일 관리자 경고 SMS");
					smsVO.setSendMessage("Nstep 물류처리 배치 처리갯수가 " + iCnt + "건이 넘었으니 확인 바랍니다.");
					smsVO.setTemplateId("8");
					smsVO.setMobileNo(strPhonenumber);
					
					// SMS 발송 등록
					int iLmsSendCnt = smsCommonMapper.insertTemplateMsgQueue(smsVO);

					smsVO.setSendDivision("LGS");
					smsVO.setReqId(BatchConstants.BATCH_USER_ID);
					// SMS 발송내역 등록
					smsCommonMapper.insertSmsSendMst(smsVO);
				}
			} else {
				LOGGER.info("Nstep 처리 체크 배치 기능 정상 수행중.");
			}
		} catch(Exception e) {
			batchVo.setRemrk(" 실패 : "+batchVo.getBatchJobId()+" : "+e.getMessage());
			LOGGER.error("Nstep 처리 체크 배치 | 함수: LgsSystemChkService.chkNSmsMain");
			String[] args = new String[2];
			args[0] = batchVo.getBatchJobId();
			args[1] = "Nstep 처리 체크 배치";
			throw new MvnoServiceException("ELGS1001", args, e);
		}
	}
}
