package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.List;
import java.util.Map;

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
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpCombMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpCombVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RcpCombMgmtService extends BaseService {
		
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private RcpCombMgmtMapper rcpCombMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/** Constructor */
	public RcpCombMgmtService() {
		setLogPrefix("[RcpCombMgmtService] ");
	}

	/**
	 * 신청관리(결합서비스) 조회
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpCombMgmtList(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		// 필수조건 체크
		if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
			throw new MvnoRunException(-1, "시작일자 누락");
		}
		if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
			throw new MvnoRunException(-1, "종료일자 누락");
		}
		
		List<EgovMap> list = (List<EgovMap>) rcpCombMgmtMapper.getRcpCombMgmtList(paramMap);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);

		return list;
	}

	/**
	 * 신청관리(결합서비스) - 상세조회(팝업) 
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpCombMgmtInfo(Map<String, Object> paramMap){
		List<EgovMap> list = (List<EgovMap>) rcpCombMgmtMapper.getRcpCombMgmtInfo(paramMap);
		list = encSvc.decryptDBMSRcpMngtList(list);		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}	

	/**
	 * 신청관리(결합서비스) - 계약번호별 계약 상세(팝업조회)
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpCombCntrInfo(Map<String, Object> paramMap){
		List<EgovMap> list = (List<EgovMap>) rcpCombMgmtMapper.getRcpCombCntrInfo(paramMap);
		list = encSvc.decryptDBMSRcpMngtList(list);		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		return list;
	}	
	/**
	 * 신청관리(결합서비스) - 결합상품 조회(팝업조회)
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpCombParentRateCdInfo(Map<String, Object> paramMap){
		List<EgovMap> list = (List<EgovMap>) rcpCombMgmtMapper.getRcpCombParentRateCdInfo(paramMap);
		
		return list;
	}	
	/**
	 * 신청관리(결합서비스) - 부가서비스명 조회(팝업조회)
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpCombRelationRateCdInfo(Map<String, Object> paramMap){
		List<EgovMap> list = (List<EgovMap>) rcpCombMgmtMapper.getRcpCombRelationRateCdInfo(paramMap);
		
		return list;
	}	
	
	/**
	 * 신청관리(결합서비스) - 결합서비스 저장
	 * @param paramMap
	 * @return
	 */
    @Transactional(rollbackFor=Exception.class)
	public void insertRcpCombMgmtInfo(RcpCombVO searchVO){
    	if(searchVO.getCombTypeCd() == null || searchVO.getCombTypeCd().equals("")){
    		throw new MvnoRunException(-1, "결합유형 선택은 필수입니다.");
    	}
    	if(searchVO.getCombTgtTypeCd() == null || searchVO.getCombTgtTypeCd().equals("")){
    		throw new MvnoRunException(-1, "결합대상 선택은 필수입니다.");
    	}
    	if(searchVO.getmSvcCntrNo() == null || searchVO.getmSvcCntrNo().equals("")){
    		throw new MvnoRunException(-1, "모계약의 계약번호 입력은 필수입니다.");
    	}
    	if(searchVO.getCstmrName() == null || searchVO.getCstmrName().equals("")){
    		throw new MvnoRunException(-1, "모계약 고객명 입력은 필수입니다.");
    	}
    	if(searchVO.getCstmrName2() == null || searchVO.getCstmrName2().equals("")){
    		throw new MvnoRunException(-1, "결합계약 고객명 입력은 필수입니다.");
    	}
    	if(searchVO.getCstmrName().indexOf('*')>= 0 || searchVO.getTel1().indexOf('*')>= 0) {
    		throw new MvnoRunException(-1, "모계약의 고객명과 전화번호는 마스킹 해제된 실제 정보를 입력해야 합니다.");
    	}
    	
    	if(searchVO.getCstmrName2().indexOf('*')>= 0 || searchVO.getTel2().indexOf('*')>= 0) {
    		throw new MvnoRunException(-1, "결합고객의 고객명과 전화번호는 마스킹 해제된 실제 정보를 입력해야 합니다.");
    	}
    	
    	rcpCombMgmtMapper.insertRcpCombMgmtInfo(searchVO);
	}
	

	/**
	 * 신청관리(결합서비스) - 결합서비스 저장
	 * @param paramMap
	 * @return
	 */
    @Transactional(rollbackFor=Exception.class)
	public void updateRcpCombMgmtInfo(RcpCombVO searchVO){
    	if(searchVO.getCombTypeCd() == null || searchVO.getCombTypeCd().equals("")){
    		throw new MvnoRunException(-1, "결합유형 선택은 필수입니다.");
    	}
    	if(searchVO.getCombTgtTypeCd() == null || searchVO.getCombTgtTypeCd().equals("")){
    		throw new MvnoRunException(-1, "결합대상 선택은 필수입니다.");
    	}
    	if(searchVO.getmSvcCntrNo() == null || searchVO.getmSvcCntrNo().equals("")){
    		throw new MvnoRunException(-1, "모계약의 계약번호 입력은 필수입니다.");
    	}
    	if(searchVO.getCstmrName() == null || searchVO.getCstmrName().equals("")){
    		throw new MvnoRunException(-1, "모계약 고객명 입력은 필수입니다.");
    	}
    	if(searchVO.getCstmrName2() == null || searchVO.getCstmrName2().equals("")){
    		throw new MvnoRunException(-1, "결합계약 고객명 입력은 필수입니다.");
    	}
    	if(searchVO.getCstmrName().indexOf('*')>= 0 || searchVO.getTel1().indexOf('*')>= 0) {
    		throw new MvnoRunException(-1, "모계약의 고객명과 전화번호는 마스킹 해제된 실제 정보를 입력해야 합니다.");
    	}
    	if(searchVO.getCstmrName2().indexOf('*')>= 0 || searchVO.getTel2().indexOf('*')>= 0) {
    		throw new MvnoRunException(-1, "결합고객의 고객명과 전화번호는 마스킹 해제된 실제 정보를 입력해야 합니다.");
    	}
    	
    	rcpCombMgmtMapper.updateRcpCombMgmtInfo(searchVO);
	}    
	/**
	 * 신청관리(결합서비스) 승인여부등록
	 * @param RcpCombVO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateRcpCombRslt(RcpCombVO rcpCombVO){
		
		rcpCombMgmtMapper.updateRcpCombRslt(rcpCombVO);
		
		//승인상태가 부적격인 경우 문자 발송
		/** 20230518 PMD 조치 */
		if("B".equals(rcpCombVO.getRsltCd()) ){
			
			String tel = rcpCombMgmtMapper.selectMsgTelNo(rcpCombVO.getReqSeq());
			
			if(StringUtils.isNotEmpty(tel) && tel.length() > 10) {
				
				String templateId = "247";
				KtMsgQueueReqVO smsVO = smsMgmtMapper.getKtTemplateText(templateId);
				smsVO.setMessage((smsVO.getTemplateText()));
				smsVO.setRcptData(tel);
				smsVO.setTemplateId(templateId);
				smsVO.setReserved01("MSP");
				smsVO.setReserved02(templateId);
				smsVO.setReserved03(rcpCombVO.getSessionUserId());
				
				smsMgmtMapper.insertKtMsgTmpQueue(smsVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				sendVO.setTemplateId(templateId);
				sendVO.setMsgId(smsVO.getMsgId());
				sendVO.setSendReqDttm(smsVO.getScheduleTime());
				sendVO.setReqId(rcpCombVO.getSessionUserId());
				
				// SMS 발송내역 등록
				smsMgmtMapper.insertSmsSendMst(sendVO);
				
			}
		
		}
	}

	public boolean canRequestCombineSolo(String pRateCd) {
		RcpCombVO rcpCombVO = rcpCombMgmtMapper.getCombRateMappByRateCd(pRateCd);
		if (rcpCombVO == null) {
			return false;
		}
        return !"EMPTY".equals(rcpCombVO.getrRateCd());
    }
}
