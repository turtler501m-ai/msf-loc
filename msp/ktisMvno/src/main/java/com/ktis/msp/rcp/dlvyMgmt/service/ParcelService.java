package com.ktis.msp.rcp.dlvyMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rcp.dlvyMgmt.mapper.ParcelServiceMapper;
import com.ktis.msp.rcp.dlvyMgmt.vo.ParcelServiceVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class ParcelService extends BaseService {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Autowired
	private ParcelServiceMapper parcelServiceMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	final static String TB_CD_GRP_ID = "RCP0025";
	final static String REQ_STAT_CD_GRP_ID = "LGS1006";
	
	public List<ParcelServiceVO> getParcelServiceLstInfo(ParcelServiceVO vo, Map<String, Object> paramMap) {
		
		List<ParcelServiceVO> list = parcelServiceMapper.getParcelServiceLstInfo(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
	public List<ParcelServiceVO> getParcelServiceLstInfoByExcel(ParcelServiceVO vo, Map<String, Object> paramMap) {
		
		List<ParcelServiceVO> list = parcelServiceMapper.getParcelServiceLstInfoByExcel(vo);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}
	
//	@Async
	@Transactional(rollbackFor=Exception.class)
	public int regParcelServiceInfo(ParcelServiceVO voLst, String usrId) throws EgovBizException {
		
		List<ParcelServiceVO> itemList = voLst.getItems();
		
		int resultCnt = 0;
		
		logger.debug("송장번호등록 대상건수 : " + itemList.size());
		
		for(ParcelServiceVO paramVo : itemList ) {
			paramVo.setTbCd(parcelServiceMapper.getTbCd(paramVo));
			paramVo.setTbCdName(parcelServiceMapper.getTbCdName(paramVo));
			paramVo.setSessionUserId(usrId);
			
			int i = parcelServiceMapper.updMcpParcelServiceCodeInfo(paramVo);
			i += parcelServiceMapper.updMspParcelServiceCodeInfo(paramVo);
			i += parcelServiceMapper.updMspRequest(paramVo);
			
			if(i>0) resultCnt++;
			
			String tel = parcelServiceMapper.selectMsgCnt(paramVo.getResNo());
			
			if(tel != null || (StringUtils.isNotEmpty(tel) && tel.length() > 10)) {
				
				String templateId = "9";
				KtMsgQueueReqVO smsVO = smsMgmtMapper.getKtTemplateText(templateId);
				smsVO.setMessage((smsVO.getTemplateText())
										.replaceAll(Pattern.quote("#{cdName}"), paramVo.getTbCdName().trim() )
										.replaceAll(Pattern.quote("#{dlvrNo}"), paramVo.getDlvrNo() ));
				smsVO.setRcptData(tel);
				smsVO.setTemplateId(templateId);
				smsVO.setReserved01("MSP");
				smsVO.setReserved02(templateId);
				smsVO.setReserved03(usrId);

				// 카카오 알림톡
				smsVO.setkMessage((smsVO.getTemplateText())
						.replaceAll(Pattern.quote("#{cdName}"), paramVo.getTbCdName().trim() )
						.replaceAll(Pattern.quote("#{dlvrNo}"), paramVo.getDlvrNo() ));
				smsVO.setMsgTypeSecond(propertyService.getString("msp.kakao.kNextType"));
				smsVO.setkSenderkey(propertyService.getString("msp.kakao.ktSenderKey"));				
				
				// SMS 발송 등록
				smsMgmtMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				sendVO.setTemplateId(templateId);
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(usrId);
				
				// SMS 발송내역 등록
				smsMgmtMapper.insertSmsSendMst(sendVO);
			}
		}
		
		logger.debug("송장번호등록 처리건수 : " + resultCnt);
		return resultCnt;
		
	}
	
//	@Async
	@Transactional(rollbackFor=Exception.class)
	public int regParcelCompleServiceInfo(ParcelServiceVO voLst, String usrId) throws EgovBizException {
		
		int resultCnt = 0;
		
		String PARCEL_COMPLE_CD = "11";
		
		List<ParcelServiceVO> itemList = voLst.getItems();
		
		logger.debug("택배완료등록 대상건수 : " + itemList.size());
		
		for( ParcelServiceVO vo : itemList ) {
			Map<String, Object> rtnMap = new HashMap();
			try{
				rtnMap = parcelServiceMapper.getResNo(vo);
			}catch(Exception e){
				throw new MvnoRunException(-1, "송장번호("+ vo.getDlvrNo() + ")가 중복사용되었습니다.");
			}
			logger.debug("rtnMap : " + rtnMap);
				
			if(rtnMap != null){
				ParcelServiceVO updateVo = new ParcelServiceVO();
				
				updateVo.setSessionUserId(usrId);
				updateVo.setResNo((String) rtnMap.get("RES_NO"));
				updateVo.setRequestStateCode(PARCEL_COMPLE_CD);
				
				logger.debug("updateVo : " + updateVo);
				
				int i = parcelServiceMapper.updMspRequestStateByParcelInfo(updateVo);
				i += parcelServiceMapper.updMcpRequestStateByParcelInfo(updateVo);
				i += parcelServiceMapper.updMspRequestByParcelInfo(updateVo);
				i += parcelServiceMapper.updMcpRequestByParcelInfo(updateVo);
				
				if(i > 0) resultCnt++;
			}
		}
		
		logger.debug("택배완료등록 처리건수 : " + resultCnt);
		
		return resultCnt;
	}
	
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("dlvryName","CUST_NAME");
		maskFields.put("mobileNo","MOBILE_PHO");
		maskFields.put("dlvryAddr","ADDR");
		maskFields.put("dlvryAddrDtl","PASSWORD");
		
		return maskFields;
	}		
}
