package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rcp.idntMgmt.mapper.IdntMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpNewMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpSelfSuffMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSuffVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RcpSelfSuffService extends BaseService {

	@Autowired
	private RcpNewMgmtMapper rcpNewMgmtMapper;
	
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;

	@Autowired
	private RcpSelfSuffMapper rcpSelfSuffMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	@Resource(name = "idntMgmtMapper")
	private IdntMgmtMapper idntMgmtMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;
	
	/** Constructor */
	public RcpSelfSuffService() {
		setLogPrefix("[RcpSelfSuffService] ");
	}

	/**
	 * 신청관리(자급제)
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtListSelfSuff(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		// 필수조건 체크
		if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
			throw new MvnoRunException(-1, "시작일자 누락");
		}
		if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
			throw new MvnoRunException(-1, "종료일자 누락");
		}

		// 서비스계약번호로 계약번호 추출
		if("2".equals(paramMap.get("searchCd"))){
			String pContractNum = rcpNewMgmtMapper.getpContractNum((String) paramMap.get("searchVal"));	

			if(pContractNum != null && !"".equals(pContractNum)){
				paramMap.put("searchVal", pContractNum);
			}
		}
		
		List<EgovMap> list = (List<EgovMap>) rcpSelfSuffMapper.getRcpMgmtListSelfSuff(paramMap);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		/**
		 * 데이터 파싱
		 * */
		for(EgovMap map : list){
			try {
				// 외국인인 경우 외국인등록번호로
				String cstmrNativeRrn[] = "FN".equals((String) map.get("cstmrType")) ? 
						Util.getJuminNumber((String)map.get("cstmrForeignerRrn")) : Util.getJuminNumber((String)map.get("cstmrNativeRrn"));
						
				map.put("birthDt", cstmrNativeRrn[0]);
				
				// 개인정보 노출 안하므로 제거
				map.remove("cstmrNativeRrn");
				map.remove("cstmrForeignerRrn");
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return list;
	}

	
	/**
	 * 신청관리(자급제) 상세조회
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtListSelfSuffDtl(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		if(!paramMap.containsKey("requestKey") || "".equals(paramMap.get("requestKey").toString())){
			throw new MvnoRunException(-1, "요청키가 존재하지 않습니다.");
		}
		
		List<EgovMap> list = (List<EgovMap>) rcpSelfSuffMapper.getRcpMgmtListSelfSuffDtl(paramMap);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		/**
		 * 데이터 파싱
		 * */
		for(EgovMap map : list){
			try {
				// 외국인인 경우 외국인등록번호로
				String cstmrNativeRrn[] = "FN".equals((String) map.get("cstmrType")) ? 
						Util.getJuminNumber((String)map.get("cstmrForeignerRrn")) : Util.getJuminNumber((String)map.get("cstmrNativeRrn"));
						
				map.put("birthDt", cstmrNativeRrn[0]);

				if(map.get("dlvryTel")!=null && !map.get("dlvryTel").equals("")){
					String[] dlvryTel = Util.getPhoneNum( (String)map.get("dlvryTel") );
					map.put("dlvryTelFn", dlvryTel[0]);
					map.put("dlvryTelMn", dlvryTel[1]);
					map.put("dlvryTelRn", dlvryTel[2]);
				}

				if(map.get("dlvryMobile")!=null && !map.get("dlvryMobile").equals("")){
					String[] dlvryMobile = Util.getPhoneNum( (String)map.get("dlvryMobile") );
					map.put("dlvryMobileFn", dlvryMobile[0]);
					map.put("dlvryMobileMn", dlvryMobile[1]);
					map.put("dlvryMobileRn", dlvryMobile[2]);
				}
				
				// 개인정보 노출 안하므로 제거
				map.remove("cstmrNativeRrn");
				map.remove("cstmrForeignerRrn");
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return list;
	}
	

	/**
	 * 신청관리(자급제) 상세정보 수정
	 * @param paramMap
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSelfSuffDetail(Map<String, Object> paramMap){
		if(!paramMap.containsKey("requestKey") || "".equals(paramMap.get("requestKey").toString())){
			throw new MvnoRunException(-1, "요청키가 존재하지 않습니다.");
		}
		
		if(!paramMap.containsKey("resNo") || "".equals(paramMap.get("resNo").toString())){
			throw new MvnoRunException(-1, "예약번호가 존재하지 않습니다.");
		}
		
		String usimModType = "";
		int cnt = rcpMgmtMapper.selectLinkusUsimMst(paramMap);
		
		if (paramMap.get("oldUsimSn") == null || "".equals(paramMap.get("oldUsimSn").toString())) {
			if (paramMap.get("reqUsimSn") != null && !"".equals(paramMap.get("reqUsimSn").toString())) {
				if (cnt > 0) {
					rcpMgmtMapper.updateLinkusUsimMst(paramMap);
					usimModType = "U";
				} else {
					rcpMgmtMapper.insertLinkusUsimMst(paramMap);
					usimModType = "I";
				}
			}
		} else {
			if(paramMap.get("reqUsimSn") != null && !"".equals(paramMap.get("reqUsimSn").toString())) {
				if (!paramMap.get("reqUsimSn").toString().equals(paramMap.get("oldUsimSn").toString())) {
					if(cnt > 0) {
						rcpMgmtMapper.updateLinkusUsimMst(paramMap);
						usimModType = "U";
					} else {
						rcpMgmtMapper.insertLinkusUsimMst(paramMap);
						usimModType = "I";
					}
				}
			}
		}
		
		if (!usimModType.equals("")) {
			paramMap.put("evntType", usimModType);
			paramMap.put("regstType", "V"); //화면에서 수정일 경우 V, 엑셀업로드 수정일 경우 E
			rcpMgmtMapper.insertLinkusUsimHst(paramMap);
		}

		// 신청정보 수정
		rcpSelfSuffMapper.updateMcpReq(paramMap);
		rcpSelfSuffMapper.updateMcpApdReq(paramMap);
		
		// 유심 상태정보 수정
		rcpSelfSuffMapper.updateMcpReqStateUsim(paramMap);
		
		// 단말 상태정보 수정
		rcpSelfSuffMapper.updateMcpReqStatePhone(paramMap);
		
		// 배송정보 수정
		rcpSelfSuffMapper.updateMcpReqDlvry(paramMap);
		rcpSelfSuffMapper.updateMcpReqApdDlvry(paramMap);
		
	}

	/**
	 * 신청관리(자급제) 상세정보 마스킹 수정
	 * @param paramMap
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSelfSuffDetailMask(Map<String, Object> paramMap){
		// 신청정보 수정
		rcpSelfSuffMapper.updateMcpReqMask(paramMap);
		rcpSelfSuffMapper.updateMcpApdReqMask(paramMap);
	}

	/**
	 * 신청관리(자급제) 엑셀다운로드
	 */	
	public List<?> getSelfSuffListByExcel(Map<String, Object> paramMap){
		
		// 필수조건 체크
		if(paramMap.get("searchCd") != null && !"".equals(paramMap.get("searchCd"))){
			if(paramMap.get("searchVal") == null || "".equals(paramMap.get("searchVal"))){
				throw new MvnoRunException(-1, "검색값 누락");
			}
		} else {
			if(paramMap.get("strtDt") == null || "".equals(paramMap.get("strtDt"))){
				throw new MvnoRunException(-1, "시작일자 누락");
			}
			if(paramMap.get("endDt") == null || "".equals(paramMap.get("endDt"))){
				throw new MvnoRunException(-1, "종료일자 누락");
			}
		}
		
		List<EgovMap> list = (List<EgovMap>) rcpSelfSuffMapper.getSelfSuffListByExcel(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(result, maskingService.getMaskFields(), paramMap);
		
		/**
		 * 데이터 파싱
		 * */
		for( EgovMap map : result ) {
			try {
				// 외국인인 경우 외국인등록번호로
				String cstmrNativeRrn[] = Util.getJuminNumber((String)map.get("cstmrNativeRrn"));
				
				map.put("birthDt", cstmrNativeRrn[0]);
				
				// 개인정보 노출 안하므로 제거
				map.remove("cstmrNativeRrn");
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return result;
	}
	
	/**
	 * 신청관리(자급제) 송장번호 엑셀등록
	 */
	@Transactional(rollbackFor=Exception.class)
	public void regSelfSuffDlvryNoList(RcpSelfSuffVO rcpSelfSuffVO, String userId) {
		
		List<RcpSelfSuffVO> itemList = rcpSelfSuffVO.getItems();
		
		int cnt = 1;
		
		for( RcpSelfSuffVO vo : itemList ) {
			cnt++;
			
			if (vo.getReqSrNo() == null || "".equals(vo.getReqSrNo())){
				throw new MvnoRunException(-1, "["+cnt+"행]일련번호는 필수 항목 입니다.");
			}
			if (vo.getResNo() == null || "".equals(vo.getResNo())){
				throw new MvnoRunException(-1, "["+cnt+"행]예약번호는 필수 항목 입니다.");
			}
			if (vo.getTbNm() == null || "".equals(vo.getTbNm())){
				throw new MvnoRunException(-1, "["+cnt+"행]택배사는 필수 항목 입니다.");
			}
			if (vo.getDlvryNo() == null || "".equals(vo.getDlvryNo())){
				throw new MvnoRunException(-1, "["+cnt+"행]송장번호는 필수 항목 입니다.");
			}

			//택배사 체크
			String tbCd = rcpSelfSuffMapper.getDlvryTbCd(vo.getTbNm());
			
			if (tbCd == null || "".equals(tbCd)) {
				throw new MvnoRunException(-1, "["+cnt+"행]일치하는 택배사가 없습니다.");
			}
			vo.setTbCd(tbCd);
			vo.setReqStateCd("10"); // 배송중으로 상태 변경
			vo.setSessionUserId(userId);
			vo.setReqSrLeng(vo.getReqSrNo().length());

			//예약번호 체크
			String result = "";
			if (vo.getReqSrLeng() == 19) {
				result = rcpSelfSuffMapper.isUsimDlvryInfoChk(vo.getResNo());
			} else {
				result = rcpSelfSuffMapper.isPhoneDlvryInfoChk(vo.getResNo());
			}
			
			if (result == null) {
				throw new MvnoRunException(-1, "[예약번호:"+vo.getResNo()+"]일치하는 예약번호가 없습니다.");
			} else if (result.equals("N")) {
				throw new MvnoRunException(-1, "[예약번호:"+vo.getResNo()+"]이미 등록된 송장번호가 있습니다.");
			}
			
			String cdName = vo.getTbNm();
			String dlvrNo = vo.getDlvryNo();
			String mobileNo = result;
			String templateId = "109";

			//택배사,송장번호,상태 변경
			if (vo.getReqSrLeng() == 19) {
				rcpSelfSuffMapper.updateUsimDlvryNo(vo);
				
				//링커스 유심 정보 등록
				Map<String, Object> paramMap = new HashMap<String,Object>();
				
				String usimModType = "";
				paramMap.put("resNo", vo.getResNo());
				paramMap.put("reqUsimSn", vo.getReqSrNo());
				paramMap.put("SESSION_USER_ID", userId);
							
				int size = rcpMgmtMapper.selectLinkusUsimMst(paramMap);
				
				if (size > 0) {
					rcpMgmtMapper.updateLinkusUsimMst(paramMap);
					usimModType = "U";
				} else {
					rcpMgmtMapper.insertLinkusUsimMst(paramMap);
					usimModType = "I";
				}
				
				if (!usimModType.equals("")) {
					paramMap.put("evntType", usimModType);
					paramMap.put("regstType", "E"); //화면에서 수정일 경우 V, 엑셀업로드 수정일 경우 E
					rcpMgmtMapper.insertLinkusUsimHst(paramMap);
				}				
			} else {
				rcpSelfSuffMapper.updatePhoneDlvryNo(vo);
			}
			//mcp_request 에 일련번호 업데이트
			rcpSelfSuffMapper.updateReqSrNo(vo);
			rcpSelfSuffMapper.updateReqApdSrNo(vo);
			
			if (mobileNo != null && mobileNo.length() > 2 && mobileNo.substring(0,2).equals("01")) {
				// SMS 템플릿 제목,내용 가져오기
				KtMsgQueueReqVO msgVO = smsMgmtMapper.getKtTemplateText(templateId);
				msgVO.setMessage((msgVO.getTemplateText())
						.replaceAll(Pattern.quote("#{cdName}"), cdName)
						.replaceAll(Pattern.quote("#{dlvrNo}"), dlvrNo));
				msgVO.setRcptData(result);
				msgVO.setTemplateId(templateId);
				msgVO.setReserved01("MSP");
				msgVO.setReserved02(templateId);
				msgVO.setReserved03(userId);
				
				// SMS 저장
				smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
				
				SmsSendVO sendVO = new SmsSendVO();
				sendVO.setTemplateId(templateId);
				sendVO.setMsgId(msgVO.getMsgId());
				sendVO.setSendReqDttm(msgVO.getScheduleTime());
				sendVO.setReqId(userId);
				
				// 발송내역 저장
				smsMgmtMapper.insertSmsSendMst(sendVO);
			}
			
									
		}
    }

	/**
	 * 신청관리(자급제) 배송완료 엑셀등록
	 */
	@Transactional(rollbackFor=Exception.class)
	public void regSelfSuffDlvryOkList(RcpSelfSuffVO rcpSelfSuffVO, String userId) {
		
		List<RcpSelfSuffVO> itemList = rcpSelfSuffVO.getItems();
				
		for( RcpSelfSuffVO vo : itemList ) {
			vo.setReqStateCd("11"); // 배송완료로 상태 변경
			vo.setSessionUserId(userId);

			String pResNo = rcpSelfSuffMapper.isPhoneDlvryOkChk(vo.getDlvryNo());
			String uResNo = rcpSelfSuffMapper.isUsimDlvryOkChk(vo.getDlvryNo());
			if (pResNo != null &&  !"".equals(pResNo)) {
				vo.setResNo(pResNo);
				rcpSelfSuffMapper.updatePhoneDlvryOk(vo);
			} else if (uResNo != null && !"".equals(uResNo)) {
				vo.setResNo(uResNo);
				rcpSelfSuffMapper.updateUsimDlvryOk(vo);
				rcpSelfSuffMapper.updateReqUsimDlvryOk(vo);
				rcpSelfSuffMapper.updateApdUsimDlvryOk(vo);
			} else {
				throw new MvnoRunException(-1, "[송장번호:"+vo.getDlvryNo()+"]일치하는 송장번호가 없습니다.");	
			}		
		}
    }
	
}
