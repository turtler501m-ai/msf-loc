/**
 * 
 */
package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.UsimDlvryMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @author 
 *
 */
@Service
public class UsimDlvryMgmtService extends BaseService {
	
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private UsimDlvryMgmtMapper usimDlvryMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/** Constructor */
	public UsimDlvryMgmtService() {
		setLogPrefix("[UsimDlvryMgmtService] ");
	}
	
	
	/**
	 * 신청정보(유심배송)
	 * @param paramMap
	 * @return
	 */
	public List<?> getUsimDlvryList(Map<String, Object> paramMap){
		
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
		
		List<EgovMap> list = (List<EgovMap>) usimDlvryMgmtMapper.getUsimDlvryList(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		/**
		 * 데이터 파싱
		 * */
		for( EgovMap map : result ) {
			try {
				// 보안 감사로 cstmrNativeRrn 처리 부분 삭제 20230811
				if(map.get("dlvryTel")!=null && !map.get("dlvryTel").equals("")){
					String[] dlvryTel = Util.getPhoneNum( (String)map.get("dlvryTel") );
					map.put("dlvryTelFn", dlvryTel[0]);
					map.put("dlvryTelMn", dlvryTel[1]);
					map.put("dlvryTelRn", dlvryTel[2]);
				}
				
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return result;
	}

	/**
	 * 신청정보(유심배송) 상세
	 * @param paramMap
	 * @return
	 */
	public List<?> getUsimDlvryDtlList(Map<String, Object> paramMap){
		
		List<EgovMap> list = (List<EgovMap>) usimDlvryMgmtMapper.getUsimDlvryDtlList(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);

		return result;
	}
	
	public List<?> getUsimDlvryListByExcel(Map<String, Object> paramMap){
		
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
		
		List<EgovMap> list = (List<EgovMap>) usimDlvryMgmtMapper.getUsimDlvryListByExcel(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);

		// 보안 감사로 cstmrNativeRrn 처리 부분 삭제 20230811
		
		return result;
	}

	public List<?> getUsimDlvryDtlListByExcel(Map<String, Object> paramMap){
		
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
		
		List<EgovMap> list = (List<EgovMap>) usimDlvryMgmtMapper.getUsimDlvryDtlListByExcel(paramMap);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);

		// 보안 감사로 cstmrNativeRrn 처리 부분 삭제 20230811
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setUsimDlvryInfo(UsimDlvryMgmtVO usimDlvryMgmtVO) {
		List<String> usimSnOrgList = new ArrayList<String>();
		usimSnOrgList.add(0,usimDlvryMgmtVO.getReqUsimSn1org());
		usimSnOrgList.add(1,usimDlvryMgmtVO.getReqUsimSn2org());
		usimSnOrgList.add(2,usimDlvryMgmtVO.getReqUsimSn3org());
		usimSnOrgList.add(3,usimDlvryMgmtVO.getReqUsimSn4org());
		usimSnOrgList.add(4,usimDlvryMgmtVO.getReqUsimSn5org());
		usimSnOrgList.add(5,usimDlvryMgmtVO.getReqUsimSn6org());
		usimSnOrgList.add(6,usimDlvryMgmtVO.getReqUsimSn7org());
		usimSnOrgList.add(7,usimDlvryMgmtVO.getReqUsimSn8org());
		usimSnOrgList.add(8,usimDlvryMgmtVO.getReqUsimSn9org());
		usimSnOrgList.add(9,usimDlvryMgmtVO.getReqUsimSn10org());
		
		List<String> usimSnList = new ArrayList<String>();
		usimSnList.add(0,usimDlvryMgmtVO.getReqUsimSn1());
		usimSnList.add(1,usimDlvryMgmtVO.getReqUsimSn2());
		usimSnList.add(2,usimDlvryMgmtVO.getReqUsimSn3());
		usimSnList.add(3,usimDlvryMgmtVO.getReqUsimSn4());
		usimSnList.add(4,usimDlvryMgmtVO.getReqUsimSn5());
		usimSnList.add(5,usimDlvryMgmtVO.getReqUsimSn6());
		usimSnList.add(6,usimDlvryMgmtVO.getReqUsimSn7());
		usimSnList.add(7,usimDlvryMgmtVO.getReqUsimSn8());
		usimSnList.add(8,usimDlvryMgmtVO.getReqUsimSn9());
		usimSnList.add(9,usimDlvryMgmtVO.getReqUsimSn10());
//		System.out.println("########## usimSnOrgList : " + usimSnOrgList );
//		System.out.println("########## usimSnList : " + usimSnList );
		
		int usimCnt = 0;
		for (String usimSn : usimSnList) {
			if (!"".equals(usimSn) && usimSn != null) {
				usimCnt++;
			}
		}
		
		if (usimCnt > Integer.parseInt(usimDlvryMgmtVO.getReqBuyQnty())) {
			throw new MvnoRunException(-1, "[주문갯수:"+usimDlvryMgmtVO.getReqBuyQnty()+"]유심 주문수량을 초과했습니다.");
		}
		
		usimDlvryMgmtMapper.updateUsimDlvryInfo(usimDlvryMgmtVO);
		
		for (int i=0; i<10; i++) {
			// 주문수량 초과하는지 체크
			String overChk = usimDlvryMgmtMapper.usimReqOverChk(usimDlvryMgmtVO);
			if ("N".equals(overChk)) {
				throw new MvnoRunException(-1, "[주문갯수:"+usimDlvryMgmtVO.getReqBuyQnty()+"]유심 주문수량을 초과했습니다.");
			}
			
			usimDlvryMgmtVO.setReqUsimSn(usimSnList.get(i));
			usimDlvryMgmtVO.setReqUsimSnOrg(usimSnOrgList.get(i));
//			System.out.println("########## usimSnOrgList " + i + " 번째 : " +  usimSnOrgList.get(i) );
//			System.out.println("########## usimSnList " + i + " 번째 : " + usimSnList.get(i) );
			if (!"".equals(usimSnList.get(i)) && usimSnList.get(i) != null) {
				if (!"".equals(usimSnOrgList.get(i)) && usimSnOrgList.get(i) != null) {
					usimDlvryMgmtVO.setUsimBuySeq(usimDlvryMgmtMapper.getUsimBuySeqNotNull(usimDlvryMgmtVO));
					usimDlvryMgmtMapper.updateUsimBuyTxnSn(usimDlvryMgmtVO);
				} else {
					List<EgovMap> seq = (List<EgovMap>) usimDlvryMgmtMapper.getUsimBuySeqNull(usimDlvryMgmtVO.getSelfDlvryIdx());
//					System.out.println("########## seq : " +  seq );
					usimDlvryMgmtVO.setUsimBuySeq(seq.get(0).get("usimBuySeq").toString());				
					usimDlvryMgmtMapper.updateUsimBuyTxnSn(usimDlvryMgmtVO);
				}
			} else {
				if (!"".equals(usimSnOrgList.get(i)) && usimSnOrgList.get(i) != null) {
					String seq = usimDlvryMgmtMapper.getUsimBuySeqNotNull(usimDlvryMgmtVO);
					usimDlvryMgmtVO.setUsimBuySeq(seq);	
					usimDlvryMgmtMapper.updateUsimBuyTxnSn(usimDlvryMgmtVO);
				}
			}
		}
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void regDlvryNoList(UsimDlvryMgmtVO usimDlvryMgmtVO, String userId) {
		
		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
		
		int cnt = 1;
		
		for( UsimDlvryMgmtVO vo : itemList ) {
			cnt++;
			
			if (vo.getSelfDlvryIdx() == null || "".equals(vo.getSelfDlvryIdx())){
				throw new MvnoRunException(-1, "["+cnt+"행]주문번호는 필수 항목 입니다.");
			}
			if (vo.getTbNm() == null || "".equals(vo.getTbNm())){
				throw new MvnoRunException(-1, "["+cnt+"행]택배사는 필수 항목 입니다.");
			}
			if (vo.getDlvryNo() == null || "".equals(vo.getDlvryNo())){
				throw new MvnoRunException(-1, "["+cnt+"행]송장번호는 필수 항목 입니다.");
			}
			
			String tbCd = usimDlvryMgmtMapper.getDlvryTbCd(vo.getTbNm());
			
			if (tbCd == null || "".equals(tbCd)) {
				throw new MvnoRunException(-1, "["+cnt+"행]일치하는 택배사가 없습니다.");
			}
			
			vo.setTbCd(tbCd);
			String result = usimDlvryMgmtMapper.isDlvryInfoChk(vo.getSelfDlvryIdx());
			
			if (result == null) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
			} else if (result.equals("N")) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]이미 등록된 송장번호가 있습니다.");
			}
			
			String cdName = vo.getTbNm();
			String dlvrNo = vo.getDlvryNo();
			String mobileNo = result;
			String templateId = "109";
			
			usimDlvryMgmtMapper.updateDlvryNo(vo);
			
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
	
	@Transactional(rollbackFor=Exception.class)
	public void regDlvryWaitList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
		
		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
				
		for( UsimDlvryMgmtVO vo : itemList ) {
												
			
			String result = usimDlvryMgmtMapper.isDlvryIdxChk(vo.getSelfDlvryIdx());
			
			if (result == null) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
			} 
			
			usimDlvryMgmtMapper.updateDlvryWait(vo);
						
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
	public void regDlvryOkList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
		
		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
				
		for( UsimDlvryMgmtVO vo : itemList ) {
												
			String result = usimDlvryMgmtMapper.isDlvryNoChk(vo.getDlvryNo());
			if (result == null) {
				throw new MvnoRunException(-1, "[송장번호:"+vo.getDlvryNo()+"]일치하는 송장번호가 없습니다.");
			}
			
			usimDlvryMgmtMapper.updateDlvryOk(vo);
						
		}
    }
	
	@Transactional(rollbackFor=Exception.class)
	public void regOpenOkList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
		
		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
		
		int cnt = 1;
		
		for( UsimDlvryMgmtVO vo : itemList ) {
			cnt++;
			
			if (vo.getSelfDlvryIdx() == null || "".equals(vo.getSelfDlvryIdx())){
				throw new MvnoRunException(-1, "["+cnt+"행]주문번호는 필수 항목 입니다.");
			}
			if (vo.getUsimBuySeq() == null || "".equals(vo.getUsimBuySeq())){
				throw new MvnoRunException(-1, "["+cnt+"행]유심주문번호는 필수 항목 입니다.");
			}
			if (vo.getContractNum() == null || "".equals(vo.getContractNum())){
				throw new MvnoRunException(-1, "["+cnt+"행]계약번호는 필수 항목 입니다.");
			}
									
//			String result = usimDlvryMgmtMapper.isOpenInfoChk(vo.getSelfDlvryIdx());
//			if (result == null) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
//			} else if (result.equals("N")) {
//				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]이미 등록된 계약번호가 있습니다.");
//			}
//			usimDlvryMgmtMapper.updateOpenOk(vo);
			
			String orderNoChk = usimDlvryMgmtMapper.isOpenInfoChk(vo.getSelfDlvryIdx());
			if (orderNoChk == null) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
			}
			
			String usimSnChk = usimDlvryMgmtMapper.isUsimOpenInfoChk(vo);
			if (usimSnChk == null) {
				throw new MvnoRunException(-1, "[유심주문번호:"+vo.getUsimBuySeq()+"]일치하는 유심주문번호가 없습니다.");
			} else if (usimSnChk.equals("N")) {
				throw new MvnoRunException(-1, "[유심주문번호:"+vo.getUsimBuySeq()+"]이미 등록된 계약번호가 있습니다.");
			}
			vo.setSessionUserId(usimDlvryMgmtVO.getSessionUserId());
			usimDlvryMgmtMapper.updateDlvryStateOpenOk(vo);
			usimDlvryMgmtMapper.updateUsimOpenOk(vo);
						
		}
    }
	
	//2020.12.10 유심일련번호 등록 로직 (유심배송)
	@Transactional(rollbackFor=Exception.class)
	public void regUsimSnList(UsimDlvryMgmtVO usimDlvryMgmtVO) {
		
		List<UsimDlvryMgmtVO> itemList = usimDlvryMgmtVO.getItems();
		
		int cnt = 1;
		
		for( UsimDlvryMgmtVO vo : itemList ) {
			cnt++;
			
			if (vo.getSelfDlvryIdx() == null || "".equals(vo.getSelfDlvryIdx())){
				throw new MvnoRunException(-1, "["+cnt+"행]주문번호는 필수 항목 입니다.");
			}
			if (vo.getReqUsimSn() == null || "".equals(vo.getReqUsimSn())){
				throw new MvnoRunException(-1, "["+cnt+"행]유심일련번호는 필수 항목 입니다.");
			}
			
			// 주문번호 체크
			String result = usimDlvryMgmtMapper.isDlvryIdxChk(vo.getSelfDlvryIdx());
			if (result == null) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]일치하는 주문번호가 없습니다.");
			}
			
			// 유심일련번호 중복체크
			int usimDupChk = usimDlvryMgmtMapper.usimSnDupChk(vo);
			if (usimDupChk > 0) {
				throw new MvnoRunException(-1, "[유심일련번호:"+vo.getReqUsimSn()+"]이미 등록된 유심일련번호 입니다.");
			}
			
			// 주문수량 초과하는지 체크
			String overChk = usimDlvryMgmtMapper.usimReqOverChk(vo);
			if (!"Y".equals(overChk)) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]유심 주문수량을 초과했습니다.");
			}
			
			// usim 일련번호 null 인 seq 찾아와서 업데이트
			List<EgovMap> seq = (List<EgovMap>) usimDlvryMgmtMapper.getUsimBuySeqNull(vo.getSelfDlvryIdx());
//			System.out.println("########## seq : " +  seq );
			vo.setIpAddr(usimDlvryMgmtVO.getIpAddr());
			vo.setSessionUserId(usimDlvryMgmtVO.getSessionUserId());
			vo.setUsimBuySeq(seq.get(0).get("usimBuySeq").toString());		
			usimDlvryMgmtMapper.updateUsimBuyTxnSn(vo);
			
			// 진행상태가 '05(배송대기)'가 아니면 일련번호 등록시에도 배송중으로 변동되지 않게끔 함
			String idxCode = usimDlvryMgmtMapper.isIdxCodeChk(vo.getSelfDlvryIdx());
			/** 20230518 PMD 조치 */
			if("05".equals(idxCode)) {
					if("E".equals(usimDlvryMgmtMapper.usimReqOverChk(vo))) {
						usimDlvryMgmtMapper.updateUsimSn(vo);
				}
			}
						
		}
    }
	

	/**
	 * 상세 - 유심정보 저장
	 * @param VO
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveUsimBuyTxnSn(UsimDlvryMgmtVO vo) {
		// 등록
		if ("I".equals(vo.getSaveType())) {
			
			// 유심일련번호 중복체크
			int usimDupChk = usimDlvryMgmtMapper.usimSnDupChk(vo);
			if (usimDupChk > 0) {
				throw new MvnoRunException(-1, "[유심일련번호:"+vo.getReqUsimSn()+"]이미 등록된 유심일련번호 입니다.");
			}
			// 주문수량 초과하는지 체크
			String overChk = usimDlvryMgmtMapper.usimReqOverChk(vo);
			if ("N".equals(overChk)) {
				throw new MvnoRunException(-1, "[주문번호:"+vo.getSelfDlvryIdx()+"]유심 주문수량을 초과했습니다.");
			}
			
			usimDlvryMgmtMapper.insertUsimSn(vo);
			
		// 수정
		} else if((vo.getSaveType()).equals("U")) {
			usimDlvryMgmtMapper.updateUsimBuyTxnSn(vo);
		}
		
	}	
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName",			"CUST_NAME");
		maskFields.put("cstmrForeignerRrn",	"SSN");
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("cstmrForeignerPn",	"PASSPORT");
		maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
		maskFields.put("cstmrMail",			"EMAIL");
		maskFields.put("cstmrAddr",			"ADDR");
		maskFields.put("cstmrTel",			"TEL_NO");
		maskFields.put("dlvryName",			"CUST_NAME");
		maskFields.put("dlvryTel",			"TEL_NO");
		maskFields.put("dlvryMobile",		"MOBILE_PHO");
		maskFields.put("dlvryAddr",			"ADDR");
		maskFields.put("reqAccountNumber",	"ACCOUNT");
		maskFields.put("reqAccountName",	"CUST_NAME");
		maskFields.put("reqAccountRrn",		"SSN");
		maskFields.put("reqCardNo",			"CREDIT_CAR");
		maskFields.put("reqCardMm",			"CARD_EXP");
		maskFields.put("reqCardYy",			"CARD_EXP");
		maskFields.put("reqCardName",		"CUST_NAME");
		maskFields.put("reqCardRrn",		"SSN");
		maskFields.put("reqGuide",			"MOBILE_PHO");
		maskFields.put("moveMobile",		"MOBILE_PHO");
		maskFields.put("minorAgentName",	"CUST_NAME");
		maskFields.put("minorAgentRrn",		"SSN");
		maskFields.put("minorAgentTel",		"TEL_NO");
		maskFields.put("reqUsimSn",			"USIM");
		maskFields.put("reqUsimSn1",		"USIM");
		maskFields.put("reqUsimSn2",		"USIM");
		maskFields.put("reqUsimSn3",		"USIM");
		maskFields.put("reqUsimSn4",		"USIM");
		maskFields.put("reqUsimSn5",		"USIM");
		maskFields.put("reqUsimSn6",		"USIM");
		maskFields.put("reqUsimSn7",		"USIM");
		maskFields.put("reqUsimSn8",		"USIM");
		maskFields.put("reqUsimSn9",		"USIM");
		maskFields.put("reqUsimSn10",		"USIM");
		maskFields.put("reqUsimSnTxn",		"USIM");
		maskFields.put("reqPhoneSn",		"DEV_NO");
		//maskFields.put("cstmrNativeRrn",	"SSN"); 보안 감사로 cstmrNativeRrn 처리 부분 삭제 20230811
		maskFields.put("faxnum",			"TEL_NO");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호

		maskFields.put("cstmrName2",		"CUST_NAME");
		maskFields.put("cstmrNameMask",		"CUST_NAME");
		maskFields.put("cstmrAddrDtl",		"PASSWORD");
		maskFields.put("dlvryPostAddrDtl",	"PASSWORD");
		maskFields.put("dlvryAddrDtl",		"PASSWORD");
		maskFields.put("cstmrTelMn",		"PASSWORD");
		maskFields.put("cstmrMobileMn",		"PASSWORD");
		maskFields.put("dlvryTelMn",		"PASSWORD");
		maskFields.put("dlvryMobileMn",		"PASSWORD");
		maskFields.put("moveMobileMn",		"PASSWORD");
		maskFields.put("reqUsimSnMask",		"USIM");
		maskFields.put("reqPhoneSnMask",	"DEV_NO");		
		maskFields.put("userId",			"SYSTEM_ID");
		
		maskFields.put("rvisnId",			"CUST_NAME"); //수정자
		maskFields.put("amdId",				"SYSTEM_ID"); //수정자
		maskFields.put("cnclNm",			"CUST_NAME"); //결제취소자
		
		return maskFields;
	}

	@Transactional(rollbackFor=Exception.class)
	public void setUsimDlvryInfoMask(UsimDlvryMgmtVO usimDlvryMgmtVO) {

		usimDlvryMgmtMapper.updateUsimDlvryInfoMask(usimDlvryMgmtVO);
	}

}