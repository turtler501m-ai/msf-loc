package com.ktis.msp.m2m.smsmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.m2m.smsmgmt.mapper.M2mSmsMgmtMapper;
import com.ktis.msp.m2m.smsmgmt.vo.SmsSendReqVO;
import com.ktis.msp.m2m.smsmgmt.vo.SmsSendResVO;


@Service
public class M2mSmsMgmtService extends BaseService {
	
	@Autowired
	private M2mSmsMgmtMapper m2mSmsMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	

	/**
	 * SMS발송조회
	 * @param vo
	 * @return
	 */
	public List<SmsSendResVO> getSmsSendList(SmsSendReqVO vo) {
		if(vo.getSearchStartDate() == null || "".equals(vo.getSearchStartDate())
				|| vo.getSearchEndDate() == null || "".equals(vo.getSearchEndDate())){
			throw new MvnoRunException(-1, "발송요청일자 누락");
		}
		
		List<SmsSendResVO> result = m2mSmsMgmtMapper.getSmsSendList(vo);
		
		// 수신자명, 수신자번호 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}

	@Transactional(rollbackFor=Exception.class)
	public void setSmsDelete(List<?> voList, LoginInfo loginInfo) {
		
		if(voList.size() <= 0) {
			throw new MvnoRunException(-1, "삭제할 내용이 없습니다.");
		}
		
		try {
			for(int i=0; i < voList.size(); i++) {
				SmsSendResVO vo = (SmsSendResVO) voList.get(i);
				loginInfo.putSessionToVo(vo);
				
				if(vo.getSendSeq() == null || vo.getSendSeq().equals("")) {
					throw new MvnoRunException(-1, "발송일련번호가 존재하지 않습니다");
				}
				
				if(vo.getSessionUserId() == null || vo.getSessionUserId().equals("")) {
					throw new MvnoRunException(-1, "사용자가 존재하지 않습니다");
				}
				
				// 삭제처리
				KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
				
				msgVO.setMsgId(vo.getMseq());
				msgVO.setSendSeq(vo.getSendSeq());
				
				int resultCount = smsMgmtMapper.checkKtMsgResult(msgVO);
				
				if(resultCount == 0) {
//					throw new MvnoRunException(-1, "전송 성공한 건입니다.[" + vo.getReceiveNm() + ", " + vo.getMaskMobileNo() + "]");
					smsMgmtMapper.deleteKtMsgQueue(msgVO);
					
					smsMgmtMapper.deleteKtSmsSendMst(msgVO);
				}
				
			}
			
		} catch(MvnoRunException e) {
			logger.info(e.getMessage());
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new MvnoRunException(-1, "삭제 중 오류가 발생하였습니다");
		}
		
	}

	@Transactional(rollbackFor=Exception.class)
	public void setSmsRetry(List<?> voList, LoginInfo loginInfo) {
		
		if(voList.size() <= 0) {
			throw new MvnoRunException(-1, "재발송할 내용이 없습니다.");
		}
		
		try {
			for(int i=0; i < voList.size(); i++) {
				SmsSendResVO vo = (SmsSendResVO) voList.get(i);
				loginInfo.putSessionToVo(vo);
				
				if(vo.getSendSeq() == null || vo.getSendSeq().equals("")) {
					throw new MvnoRunException(-1, "발송일련번호가 존재하지 않습니다");
				}
				
				if(vo.getSendDivision() == null || vo.getSendDivision().equals("")) {
					throw new MvnoRunException(-1, "발송구분이 존재하지 않습니다");
				}
				
				if(vo.getSessionUserId() == null || vo.getSessionUserId().equals("")) {
					throw new MvnoRunException(-1, "사용자가 존재하지 않습니다");
				}
				
				if(vo.getTemplateId() == null || vo.getTemplateId().equals("")) {
					throw new MvnoRunException(-1, "SMS 템플릿ID가 존재하지 않습니다");
				}
				
				// 재발송처리
				KtMsgQueueReqVO msgVO = new KtMsgQueueReqVO();
				
				msgVO.setReserved01("MSP");
				msgVO.setReserved02(vo.getTemplateId());
				msgVO.setReserved03(vo.getSessionUserId());
				msgVO.setTemplateId(vo.getTemplateId());
				msgVO.setRcptData(m2mSmsMgmtMapper.getMobileNo(vo));
				msgVO.setMessage(vo.getText());
				
				smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
				
				msgVO.setUserId(vo.getSessionUserId());
				msgVO.setSendSeq(vo.getSendSeq());
				
				smsMgmtMapper.updateKtSmsSendMst(msgVO);
				
			}
			
		} catch(MvnoRunException e) {
			logger.info(e.getMessage());
			throw e;
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new MvnoRunException(-1, "재발송 중 오류가 발생하였습니다");
		}
		
	}

	/**
	 * SMS템플릿 콤보조회
	 */
	public List<?> getTemplateCombo(CmnCdMgmtVo vo) {
		logger.debug("=======================================================");
		logger.debug("템플릿콤보" + vo.toString());
		logger.debug("=======================================================");
		
		return m2mSmsMgmtMapper.getTemplateCombo(vo);
	}

	/**
	 * 검색구분 콤보조회
	 */
	public List<?> getSearchCodeCombo(CmnCdMgmtVo vo) {
		logger.debug("=======================================================");
		logger.debug("검색구분콤보" + vo.toString());
		logger.debug("=======================================================");
		
		return m2mSmsMgmtMapper.getSearchCodeCombo(vo);
	}

	/**
	 * 마스킹필드 셋팅(수신자명,수신자번호)
	 * @return
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("receiveNm", "CUST_NAME");
		maskFields.put("maskMobileNo", "MOBILE_PHO");
		
		maskFields.put("mspUserId", "SYSTEM_ID");
		maskFields.put("procNm", "CUST_NAME");
		
		return maskFields;
	}
	
	
	/**
	 * SMS발송조회(과거)
	 * @param vo
	 * @return
	 */
	public List<SmsSendResVO> getSmsSendListOld(SmsSendReqVO vo) {
		if(vo.getSearchStartDate() == null || "".equals(vo.getSearchStartDate())
				|| vo.getSearchEndDate() == null || "".equals(vo.getSearchEndDate())){
			throw new MvnoRunException(-1, "발송요청일자 누락");
		}
		
		List<SmsSendResVO> result = m2mSmsMgmtMapper.getSmsSendListOld(vo);
		
		// 수신자명, 수신자번호 마스킹처리
		HashMap<String, String> maskFields = getMaskFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", vo.getSessionUserId());
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	
}
