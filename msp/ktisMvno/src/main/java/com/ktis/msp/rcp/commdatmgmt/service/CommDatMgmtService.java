package com.ktis.msp.rcp.commdatmgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rcp.commdatmgmt.mapper.CommDatMgmtMapper;
import com.ktis.msp.rcp.commdatmgmt.vo.CommDatMgmtVO;

@Service
public class CommDatMgmtService extends BaseService {
	
	@Autowired
	private CommDatMgmtMapper commDatMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/**
	 * 통신자료제공신청 내역 조회
	 * @param commDatMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getCommDatePrvList(CommDatMgmtVO commDatMgmtVO, Map<String, Object> paramMap) {
		if(commDatMgmtVO.getStrtDt() == null || "".equals(commDatMgmtVO.getStrtDt())){
			throw new MvnoRunException(-1, "신청일자를 확인해주세요.");
		}
		
		if(commDatMgmtVO.getEndDt() == null || "".equals(commDatMgmtVO.getEndDt())){
			throw new MvnoRunException(-1, "[신청일자] 검색조건을 확인해주세요.");
		}
		
		HashMap<String, String> maskFields = getMaskFields();
		
		List<?> list = commDatMgmtMapper.getCommDatePrvList(commDatMgmtVO);
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	/**
	 * 통신자료제공신청 내역 엑셀 다운로드 
	 * @param commDatMgmtVO
	 * @param paramMap
	 * @return
	 */
	public List<?> getCommDatePrvListEx(CommDatMgmtVO commDatMgmtVO, Map<String, Object> paramMap) {
		HashMap<String, String> maskFields = getMaskFields();
		
		List<?> list = commDatMgmtMapper.getCommDatePrvListEx(commDatMgmtVO);
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateResultYN(CommDatMgmtVO commDatMgmtVO) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("updateResultYN START."));
		logger.info(generateLogMsg("CommDatMgmtVO == " + commDatMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		int returnCnt = commDatMgmtMapper.updateResultYN(commDatMgmtVO);
		CommDatMgmtVO commData = commDatMgmtMapper.getCommDatePrv(commDatMgmtVO);

		String cdName = commData.getApyNm();
		String mobileNo = commData.getCntcTelNo().replaceAll("-","");
		String templateId = "233";
		
		if (mobileNo != null && mobileNo.length() == 11 && mobileNo.substring(0,2).equals("01")) {
			// SMS 템플릿 제목,내용 가져오기
			KtMsgQueueReqVO msgVO = smsMgmtMapper.getKtTemplateText(templateId);
			msgVO.setMessage(msgVO.getTemplateText());
			msgVO.setRcptData(mobileNo);
			msgVO.setTemplateId(templateId);
			msgVO.setReserved01("MSP");
			msgVO.setReserved02(templateId);
			msgVO.setReserved03(commDatMgmtVO.getSessionUserId());
			
			// SMS 저장
			smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
			
			SmsSendVO sendVO = new SmsSendVO();
			sendVO.setTemplateId(templateId);
			sendVO.setMsgId(msgVO.getMsgId());
			sendVO.setSendReqDttm(msgVO.getScheduleTime());
			sendVO.setReqId(commDatMgmtVO.getSessionUserId());
			// 발송내역 저장
			smsMgmtMapper.insertSmsSendMst(sendVO);
		}
		
		return returnCnt;
	}
	
	
	/**
	 * 마스크 필드
	 * @return
	 */
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("apyNm","CUST_NAME");
		maskFields.put("cntcTelNo","MOBILE_PHO");
		maskFields.put("tgtSvcNo","MOBILE_PHO");
		maskFields.put("recpEmail","EMAIL");
		maskFields.put("procrNm","CUST_NAME");
		
		return maskFields;
	}
}
