/**
 * 
 */
package com.ktis.msp.rcp.rcpMgmt.service;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.rcp.rcpMgmt.mapper.UsimDlvryChangeMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryChangeVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;


/**
 * @author 
 *
 */
@Service
public class UsimDlvryChangeService extends BaseService {

	@Autowired
	private UsimDlvryChangeMapper usimDlvryChangeMapper;

	@Autowired
	private SmsMgmtMapper smsMgmtMapper;


	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	@Autowired
	private MplatformService mplatformService;

	/**
	 * @Description : 신청정보(유심교체)리스트를 조회한다
	 * @Param  : UsimDlvryChangeVO
	 * @Return : List<EgovMap>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<EgovMap> selectUsimChangeMstList(UsimDlvryChangeVO searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = usimDlvryChangeMapper.selectUsimChangeMstList(searchVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}

	/**
	 * @Description : 신청정보(유심교체)리스트를 엑셀다운로드한다
	 * @Param  : UsimDlvryChangeVO
	 * @Return : List<EgovMap>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<EgovMap> selectUsimChangeMstListExcel(UsimDlvryChangeVO searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = usimDlvryChangeMapper.selectUsimChangeMstListExcel(searchVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}


	/**
	 * @Description : 신청정보(유심교체)상세리스트를 조회한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : List<EgovMap>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<EgovMap> selectUsimChangeDtlList(UsimDlvryChangeVO searchVO, Map<String, Object> paramMap) {

		String seq = searchVO.getSeq();
		if(KtisUtil.isEmpty(seq)){
			throw new MvnoRunException(-1, "주문번호가 존재하지 않습니다.");
		}

		List<EgovMap> list = usimDlvryChangeMapper.selectUsimChangeDtlList(seq);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}

	/**
	 * @Description : 신청정보(유심교체)상세리스트를 엑셀다운로드한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : List<EgovMap>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<EgovMap> selectUsimChangeDtlListExcel(UsimDlvryChangeVO searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = usimDlvryChangeMapper.selectUsimChangeDtlListExcel(searchVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}

	/**
	 * @Description : 일련번호/송장업로드 엑셀업로드양식을 다운로드 한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : List<EgovMap>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<EgovMap> selectUsimChgDlvryExcelTemp(UsimDlvryChangeVO searchVO, Map<String, Object> paramMap) {

		List<EgovMap> list = usimDlvryChangeMapper.selectUsimChgDlvryExcelTemp(searchVO);
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		return list;
	}
	
	/**
	 * @Description : 신청정보(유심교체)의 배송정보를 엑셀로 업로드하여 수정 한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : int
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public int updateUsimChgUsimDlvryExcel(UsimDlvryChangeVO vo) {
		int insertCnt = 0;
		List<UsimDlvryChangeVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}
		for(int i = 0; i < itemList.size(); i++) {
			UsimDlvryChangeVO item = itemList.get(i);
			item.setSessionUserId(vo.getSessionUserId());

			String seq = item.getSeq();
			String contractNum = item.getContractNum();
			
			//필수값 확인
			if(KtisUtil.isEmpty(seq)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 주문번호가<br> 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(contractNum)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호가<br> 누락되었습니다.");
			}
			//고객 ID조회
			String customerId = usimDlvryChangeMapper.selectMspJuoSubInfoByContractNum(contractNum);
			if(KtisUtil.isEmpty(customerId)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호<br> 고객번호가 존재하지않습니다.");
			}
			
			//택배사 코드 조회
			if(KtisUtil.isNotEmpty(item.getTbNm())){
				String tbCd = usimDlvryChangeMapper.getDlvryTbCd(item.getTbNm());
				if (tbCd == null || "".equals(tbCd)) {
					throw new MvnoRunException(-1, "["+ (i+1) +"행]일치하는<br> 택배사가 없습니다.");
				}
				item.setTbCd(tbCd);
			}
			//존재하는 주문건인지 확인
			UsimDlvryChangeVO selectMstvo = usimDlvryChangeMapper.selectUsimChangeMstBySeq(seq);
			if(KtisUtil.isEmpty(selectMstvo)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 주문번호["+ seq +"]가<br> 존재하지 않습니다");
			}
			
			//계약번호가 해당주문번호 건인지 확인
			String selectSeq = usimDlvryChangeMapper.selectSeqByContractNum(contractNum);
			if(KtisUtil.isEmpty(selectSeq)) { //계약번호로 주문번호가 존재하지 않는경우
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호["+ contractNum +"]로<br> 등록된 접수건이 없습니다.");
			}
			if(!selectSeq.equals(seq)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호["+ contractNum +"]는<br> 주문번호 ["+ selectSeq + "]입니다 주문번호를 확인해주세요.");
			}
			
			usimDlvryChangeMapper.updateDlvryInfoExcel(item); 	// 택배사, 운송장번호 업데이트
			usimDlvryChangeMapper.updateUsimInfo(item);			// 제품명, 유심번호 업데이트
			insertCnt++;
		}
		
		//택배사, 송장번호 들어가있는 대상 조회
		List<UsimDlvryChangeVO> smsSendList = usimDlvryChangeMapper.selectSmsSendList();
		for(UsimDlvryChangeVO smsSendVo : smsSendList) {
			if (smsSendList == null || smsSendList.isEmpty()) {
				continue;
			}
			//유심배송 발송 안내 문자
			smsSendVo.setTemplateId("423");
			smsSendVo.setSessionUserId(vo.getSessionUserId());
			this.sendSms(smsSendVo);

			smsSendVo.setStatus("ING"); //진행중 변경
			usimDlvryChangeMapper.updateStatusMst(smsSendVo);
			usimDlvryChangeMapper.updateStatusDtl(smsSendVo);
		}

		return insertCnt;
	}
	
	/**
	 * @Description : 유심 배송안내, 유심교체독려에 관한 문자를 전송한다
	 * @Param  : List<UsimDlvryChangeVO>
	 * @Return : int
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public int usimChangeSmsSend(List<UsimDlvryChangeVO> list, String sessionUserId) {
		int resultCnt = 0;

		for(UsimDlvryChangeVO vo : list){
			UsimDlvryChangeVO selectMstvo = usimDlvryChangeMapper.selectUsimChangeMstBySeq(vo.getSeq());
			vo.setDlvryTel(selectMstvo.getDlvryTel());
			vo.setSessionUserId(sessionUserId);
			vo.setDlvryNo(selectMstvo.getDlvryNo());
			vo.setTbNm(selectMstvo.getTbNm());
			this.sendSms(vo);
		}

		return resultCnt;
	}

	/**
	 * @Description : 유심 배송안내, 유심교체독려에 관한 문자를 전송한다
	 * @Param  : UsimDlvryChangeVO
	 * @Return : void
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	private void sendSms(UsimDlvryChangeVO vo) throws MvnoRunException {
		String templateId = vo.getTemplateId();
		String seq = vo.getSeq();

		KtMsgQueueReqVO ktMsgQueueReqVO = smsMgmtMapper.getKtTemplateText(templateId);
		if(ktMsgQueueReqVO == null){
			throw new MvnoRunException(-1, "주문번호["+ seq +"]존재하지 않는 템플릿입니다.");
		}
		if(vo.getDlvryTel() == null){
			throw new MvnoRunException(-1, "주문번호["+ seq +"]문자전송할 연락처가 존재하지 않습니다.");
		}

		//SMS(배송안내) 문자
		if("423".equals(templateId)) {
			String tbNm = vo.getTbNm();
			String dlvryNo = vo.getDlvryNo();
			if(KtisUtil.isEmpty(tbNm)) {
				throw new MvnoRunException(-1, "주문번호["+ seq +"]택배사 미등록 주문건입니다. 택배사 등록 후 다시 시도해주세요");
			}
			if(KtisUtil.isEmpty(dlvryNo)) {
				throw new MvnoRunException(-1, "주문번호["+ seq +"]운송장번호 미등록 주문건입니다. 운송장번호 등록 후 다시 시도해주세요.");
			}

			ktMsgQueueReqVO.setMessage(ktMsgQueueReqVO.getTemplateText()
					.replaceAll(Pattern.quote("#{tbNm}"), tbNm)
					.replaceAll(Pattern.quote("#{dlvryNo}"), dlvryNo));
		}

		ktMsgQueueReqVO.setSubject(ktMsgQueueReqVO.getTemplateSubject());
		ktMsgQueueReqVO.setRcptData(vo.getDlvryTel());
		ktMsgQueueReqVO.setReserved01("MSP");
		ktMsgQueueReqVO.setReserved02("MSP1010029");
		ktMsgQueueReqVO.setReserved03(vo.getSessionUserId());
		ktMsgQueueReqVO.setUserId(vo.getSessionUserId());

		smsMgmtMapper.insertKtMsgTmpQueue(ktMsgQueueReqVO);
		smsMgmtMapper.insertSmsSendMst(ktMsgQueueReqVO.toSmsSendVO());
	}

	/**
	 * @Description : 메모/처리여부를 엑셀로 등록한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : int
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public int updateUsimChgProcExcel(UsimDlvryChangeVO vo) {
		int resultCnt = 0;

		List<UsimDlvryChangeVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}
		for(int i = 0; i < itemList.size(); i++) {
			UsimDlvryChangeVO item = itemList.get(i);
			item.setSessionUserId(vo.getSessionUserId());
			
			String seq = item.getSeq();
			String itemProcYnNm = item.getProcYnNm();
			//필수값 확인
			if(KtisUtil.isEmpty(seq)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 주문번호가<br> 누락되었습니다.");
			}
			//존재하는 주문건인지 확인
			UsimDlvryChangeVO selectMstvo = usimDlvryChangeMapper.selectUsimChangeMstBySeq(seq);
			
			if(KtisUtil.isEmpty(selectMstvo)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 주문번호["+ seq +"]가<br> 존재하지 않습니다");
			}
			//공통코드 처리여부 확인
			if(KtisUtil.isNotEmpty(itemProcYnNm)){
				String procYn = usimDlvryChangeMapper.getProcYn(itemProcYnNm);
				if (procYn == null || "".equals(procYn)) {
					throw new MvnoRunException(-1, "엑셀데이터 " + (i+1) + "행의 처리여부는<br> '완료'나 '미완료'만 입력가능합니다.");
				}
				item.setProcYn(procYn);
			}
			
			usimDlvryChangeMapper.updateUsimChgProcExcel(item);
			resultCnt++;
		}
		return resultCnt;
	}

	/**
	 * @Description : 직접등록할 대상의 고객정보를 조회하고 T01(유심무상교체 가능여부 연동)결과까지 조회한다.
	 * @Param  : Map<String, Object>
	 * @Return : List<?>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<?> usimChgAccept(Map<String, Object> paramMap) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		//화면에서 입력한 휴대폰번호
		String searchSubscriberNo = (String) paramMap.get("searchSubscriberNo");
		if(KtisUtil.isEmpty(searchSubscriberNo)) {
			throw new MvnoRunException(-1, "핸드폰번호가 누락되었습니다.");
		}
		
		//1. 고객정보 조회
		List<UsimDlvryChangeVO> list = usimDlvryChangeMapper.selectCustomerInfo(searchSubscriberNo);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		
		//2. 미존재 회선일 경우
		if(list == null || list.isEmpty()) {
			throw new MvnoRunException(-1, "유심무상교체 대상이 아닙니다.<br> 확인후 다시 시도 해주세요.");
		}

		//3. 연동 시퀀스 조회
		String osstNo = usimDlvryChangeMapper.selectOsstNo();
		
		for(UsimDlvryChangeVO vo : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> resultPukMap = new HashMap<String, Object>();
			Map<String, Object> resultUsimMap = new HashMap<String, Object>();
			
			String contractNum = vo.getContractNum();
			String ncn = vo.getSvcCntrNo();
			String custId = vo.getCustomerId();
			String subscriberNo = vo.getSubscriberNo();
			String ctn = vo.getCtn(); //마스킹되지 않은 휴대폰번호 (MP연동시 해당값으로 전송)
			String customerLinkName = vo.getCustomerLinkName();

			if(KtisUtil.isEmpty(contractNum)) {
				throw new MvnoRunException(-1, "계약번호가 존재하지 않습니다.");
			}
			if(KtisUtil.isEmpty(ncn)) {
				throw new MvnoRunException(-1, "서비스계약번호가 존재하지 않습니다.");
			}
			if(KtisUtil.isEmpty(custId)) {
				throw new MvnoRunException(-1, "고객번호가 존재하지 않습니다.");
			}
			
			//모두 실패하는 경우(제외대상, 이미신청한경우, 연동실패)에도 화면으로 배송정보를 전송하기 위해 Map에 담음
			resultMap.put("osstNo", osstNo);
			resultMap.put("sessionUserId", paramMap.get("SESSION_USER_ID"));
			resultMap.put("prgrStatCd", "T01");
			resultMap.put("customerId", vo.getCustomerId());
			resultMap.put("dlvryPost", vo.getDlvryPost());
			resultMap.put("dlvryAddr", vo.getDlvryAddr());
			resultMap.put("dlvryAddrDtl", vo.getDlvryAddrDtl());
			
			//4. 제외 대상확인 
			// ESIM
			if("E".equals(vo.getUsimType())) {
				resultMap.put("customerLinkName", customerLinkName);
				resultMap.put("contractNum", contractNum);
				resultMap.put("svcCntrNo", ncn);
				resultMap.put("subscriberNo", subscriberNo);
				resultMap.put("preChkRsltCd", "91");
				resultMap.put("preChkRsltMsg", "[불가] ESIM 제한");
				resultList.add(resultMap);
				continue;
			}
			// 정지회선
			if("S".equals(vo.getSubStatus())) {
				resultMap.put("customerLinkName", customerLinkName);
				resultMap.put("contractNum", contractNum);
				resultMap.put("svcCntrNo", ncn);
				resultMap.put("subscriberNo", subscriberNo);
				resultMap.put("preChkRsltCd", "92");
				resultMap.put("preChkRsltMsg", "[불가] 정지회선 제한");
				resultList.add(resultMap);
				continue;
			}
			//5. 회션별로 이미 신청했는지 여부 확인
			String selectSeq  = usimDlvryChangeMapper.selectSeqByContractNum(contractNum);
			if(KtisUtil.isNotEmpty(selectSeq)){ //존재하는 경우
				resultMap.put("customerLinkName", customerLinkName);
				resultMap.put("contractNum", contractNum);
				resultMap.put("svcCntrNo", ncn);
				resultMap.put("subscriberNo", subscriberNo);
				resultMap.put("preChkRsltCd", "93");
				resultMap.put("preChkRsltMsg", "[불가] 유심 배송 신청 완료(재신청제한)");
				resultList.add(resultMap);
				continue;
			}

			//6. T01 연동
			try{
				resultMap = mplatformService.usimChgAccept(ncn, ctn, custId);
				
				if (!"N".equals(resultMap.get("responseType"))) {
					resultMap.put("customerLinkName", customerLinkName);
					resultMap.put("contractNum", contractNum);
					resultMap.put("svcCntrNo", ncn);
					resultMap.put("subscriberNo", subscriberNo);
					resultMap.put("preChkRsltCd", "94");
					resultMap.put("preChkRsltMsg", "[불가] 사전체크 연동 오류");
				} else {
					resultMap.put("responseCode", "N");
					resultMap.put("responseBasic", "성공");
					resultMap.put("customerLinkName", customerLinkName);
					resultMap.put("svcCntrNo", ncn);
					resultMap.put("contractNum", contractNum);
					resultMap.put("subscriberNo", subscriberNo);

					if("00".equals(resultMap.get("rsltCd"))){
						resultMap.put("preChkRsltCd", "00");
						resultMap.put("preChkRsltMsg", "[가능]");

						//T01 연동 후 성공인 경우 Y07 연동
						resultMap.put("usimModelCd", "");
						resultMap.put("nfcUsimType", "");
						resultPukMap = mplatformService.usimPukAccept(ncn, ctn, custId);
						if (!"N".equals(resultPukMap.get("responseType"))) {
							//연동 실패시
							resultMap.put("usimModelId", "0000");
						}else{
							//연동 성공 후 model데이터 DB 조회 > 없는경우 ID 값만 등록
							resultMap.put("usimModelId", resultPukMap.get("intmMdlId"));
							resultUsimMap = usimDlvryChangeMapper.selectUsimModelData(resultPukMap.get("intmMdlId").toString());
							if(resultUsimMap != null){
								resultMap.put("usimModelCd", resultUsimMap.get("prdtCode"));
								resultMap.put("nfcUsimType", resultUsimMap.get("nfcUsimType"));
							}
						}
					} else {
						resultMap.put("preChkRsltCd", "99");
						resultMap.put("preChkRsltMsg", "[불가] " +StringUtils.defaultIfEmpty((String) resultMap.get("rsltMsg"), "사전체크 연동 오류"));
					}
					
				}
			} catch (Exception e) {
				resultMap.put("customerLinkName", customerLinkName);
				resultMap.put("contractNum", contractNum);
				resultMap.put("svcCntrNo", ncn);
				resultMap.put("subscriberNo", subscriberNo);
				resultMap.put("preChkRsltCd", "95");
				resultMap.put("preChkRsltMsg", "[불가] 사전체크 연동 오류");
			}
			
			//성공한 경우 화면으로 배송정보를 전송하기 위해 Map에 담음
			resultMap.put("osstNo", osstNo);
			resultMap.put("sessionUserId", paramMap.get("SESSION_USER_ID"));
			resultMap.put("prgrStatCd", "T01");
			resultMap.put("customerId", vo.getCustomerId());
			resultMap.put("dlvryPost", vo.getDlvryPost());
			resultMap.put("dlvryAddr", vo.getDlvryAddr());
			resultMap.put("dlvryAddrDtl", vo.getDlvryAddrDtl());
			
			resultList.add(resultMap);
			
			//7. 연동이력 저장		
			usimDlvryChangeMapper.insertOsstHist(resultMap);
		}
		
		
		return resultList;
	}

	/**
	 * @Description : 직접등록 대상을 생성한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : void
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public void insertUsimChg(UsimDlvryChangeVO vo) {

		if(KtisUtil.isEmpty(vo.getChnlCd())) {
			throw new MvnoRunException(-1, "신청경로가 존재하지 않습니다.");
		}		
		if(KtisUtil.isEmpty(vo.getDlvryTelFn())) {
			throw new MvnoRunException(-1, "[연락처1] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryTelMn())) {
			throw new MvnoRunException(-1, "[연락처2] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryTelRn())) {
			throw new MvnoRunException(-1, "[연락처3] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryPost())) {
			throw new MvnoRunException(-1, "[우편번호] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryAddr())) {
			throw new MvnoRunException(-1, "[주소] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryAddrDtl())) {
			throw new MvnoRunException(-1, "[상세주소] 정보가 존재하지 않습니다.");
		}

		String seq = usimDlvryChangeMapper.selectUsimChangeSeq(); //주문번호 생성
		vo.setSeq(seq); //주문번호 SET
		
		String reqQnty = String.valueOf(vo.getItems().size());
		vo.setReqQnty(reqQnty); //주문수량 SET

		try{
			usimDlvryChangeMapper.insertUsimChangeMst(vo); // MST 테이블
			for(UsimDlvryChangeVO dtlVo : vo.getItems()){
				dtlVo.setSeq(seq); //주문번호 SET
				dtlVo.setSessionUserId(vo.getSessionUserId()); // 세션유저ID SET
				usimDlvryChangeMapper.insertUsimChangeDtl(dtlVo); // DTL 테이블
			}
		} catch (Exception e) {
			throw new MvnoRunException(-1, e.getMessage());
		}
	}

	/**
	 * @Description : 직접등록(수정) 상세내역을 조회한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : List<EgovMap>
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public List<EgovMap> selectUsimChgInfo(UsimDlvryChangeVO searchVO, Map<String, Object> paramMap) {

		String seq = searchVO.getSeq();
		if(KtisUtil.isEmpty(seq)){
			throw new MvnoRunException(-1, "주문번호가 존재하지 않습니다.");
		}
		List<EgovMap> list = usimDlvryChangeMapper.selectUsimChgInfo(seq);
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(list, maskFields, paramMap);
		
		return list;
	}

	/**
	 * @Description : 직접등록(수정)에서 배송정보(받는사람정보), 처리메모를 수정한다
	 * @Param  : UsimDlvryChangeVO
	 * @Return : void
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public void updateUsimChg(UsimDlvryChangeVO vo) {

		String seq = vo.getSeq();		
		if(KtisUtil.isEmpty(seq)){
			throw new MvnoRunException(-1, "주문번호가 존재하지 않습니다.");
		}
		//존재하는 주문건인지 확인
		UsimDlvryChangeVO selectMstvo = usimDlvryChangeMapper.selectUsimChangeMstBySeq(seq);
		if(KtisUtil.isEmpty(selectMstvo)) {
			throw new MvnoRunException(-1, "해당주문이 존재하지 않습니다");
		}
		
		if(KtisUtil.isEmpty(vo.getDlvryTelFn())) {
			throw new MvnoRunException(-1, "[연락처1] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryTelMn())) {
			throw new MvnoRunException(-1, "[연락처2] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryTelRn())) {
			throw new MvnoRunException(-1, "[연락처3] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryPost())) {
			throw new MvnoRunException(-1, "[우편번호] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryAddr())) {
			throw new MvnoRunException(-1, "[주소] 정보가 존재하지 않습니다.");
		}
		if(KtisUtil.isEmpty(vo.getDlvryAddrDtl())) {
			throw new MvnoRunException(-1, "[상세주소] 정보가 존재하지 않습니다.");
		}
		
		try {
			usimDlvryChangeMapper.updateUsimChg(vo); //MST 받는사람정보, 메모 수정
			for(UsimDlvryChangeVO dtlVo : vo.getItems()){
				dtlVo.setSeq(seq); //주문번호 SET
				dtlVo.setSessionUserId(vo.getSessionUserId()); // 세션유저ID SET
				usimDlvryChangeMapper.updateUsimInfo(dtlVo);   // DTL 제품명, 유심번호 업데이트

			}
		} catch (Exception e) {
			throw new MvnoRunException(-1, e.getMessage());
		}
	}

	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();

		maskFields.put("usimSn",			"USIM");
		maskFields.put("subscriberNo",		"MOBILE_PHO");
		maskFields.put("customerLinkName",	"CUST_NAME");
		maskFields.put("dlvryName",			"CUST_NAME");
		maskFields.put("dlvryTelMn",		"PASSWORD");

		return maskFields;
	}


	/**
	 * @Description : 신청정보(유심교체) ESIM를 엑셀로 업로드하여 등록한다.
	 * @Param  : UsimDlvryChangeVO
	 * @Return : int
	 * @Author : 박민건
	 * @Create Date : 2025. 11. 07.
	 */
	public int insertUsimChgInfoExcel(UsimDlvryChangeVO vo) {
		int insertCnt = 0;
		
		List<UsimDlvryChangeVO> itemList = vo.getItems();
		if(KtisUtil.isEmpty(itemList)){
			throw new MvnoRunException(-1,"등록할 데이터가 없습니다.");
		}
		for(int i = 0; i < itemList.size(); i++) {
			UsimDlvryChangeVO item = itemList.get(i);
			item.setSessionUserId(vo.getSessionUserId());
			
			//필수값 확인
			if(KtisUtil.isEmpty(item.getContractNum())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호가 누락되었습니다.");
			}
			//고객 ID조회
			String customerId = usimDlvryChangeMapper.selectMspJuoSubInfoByContractNum(item.getContractNum());
			if(KtisUtil.isEmpty(customerId)) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호의 고객번호가 존재하지않습니다.");
			}
			
			if(KtisUtil.isEmpty(item.getCustomerLinkName())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 고객명이 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getReqQnty())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 주문수량이 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getReqInDay())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 신청일시가 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getDlvryName())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 수령인이 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getDlvryPost())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 우편번호가 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getDlvryAddr())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 배송주소가 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getDlvryAddrDtl())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 주소상세가 누락되었습니다.");
			}
			if(KtisUtil.isEmpty(item.getDlvryTel())) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 배송연락처가 누락되었습니다.");
			}
			
			//신청경로 코드 조회
			if(KtisUtil.isNotEmpty(item.getChnlNm())){
				String chnlCd = usimDlvryChangeMapper.getChnlCd(item.getChnlNm());
				if (chnlCd == null || "".equals(chnlCd)) {
					throw new MvnoRunException(-1, "["+ (i+1) +"행]일치하는 신청경로가 없습니다.");
				}
				// OFF(고객센터), VISIT(대면)만 엑셀업로드 가능
				if(!"OFF".equals(chnlCd) && !"VISIT".equals(chnlCd)) {
					throw new MvnoRunException(-1, "["+ (i+1) +"행]신청경로는 고객센터나 대면만 등록가능합니다.");
				}
				item.setChnlCd(chnlCd);
			}

			// 배송휴대폰번호
			String dlvryTel = item.getDlvryTel().replaceAll("-","");
			item.setDlvryTel(dlvryTel);
			
			// 수량
			String reqQnty = item.getReqQnty(); // 엑셀에서 읽은 값 예: "2-1"
			if (reqQnty == null || !reqQnty.contains("-")) {
				throw new MvnoRunException(-1, "엑셀데이터 " + (i + 1) + "행의 수량형식[" + reqQnty + "]이 잘못되었습니다. 예: 2-1 형식으로 입력해주세요.");
			}
			item.setReqQnty(reqQnty.split("-")[0]);
			
			String contractNum = item.getContractNum();
			
			// 이미 주문번호가 존재하는지 확인
			String selectSeq  = usimDlvryChangeMapper.selectSeqByContractNum(contractNum);
			if(KtisUtil.isNotEmpty(selectSeq)){ //존재하는 경우
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행은 주문번호["+ selectSeq +"]가 이미 등록되어있습니다.");
			}
			//임시테이블에 이미 INSERT 되었는지 확인 (계약번호 중복건)
			if(usimDlvryChangeMapper.dupChkByContractNum(contractNum) > 0 ) {
				throw new MvnoRunException(-1, "엑셀데이터 "+ (i+1) +"행의 계약번호["+ contractNum +"]는 다른행의 계약번호와 중복됩니다.");
			}
			
			usimDlvryChangeMapper.insertUsimChangeChk(item);	//임시테이블에 INSERT
			insertCnt++;
		}
		// 임시 테이블에서 고객번호 목록 조회
		List<String> customerIdList = usimDlvryChangeMapper.selectCustomerIdList();

		// 고객번호 기준으로 INSERT
		for(String customerId : customerIdList ){
			//CHK테이블 고객번호 기준으로 신청리스트 조회
			List<UsimDlvryChangeVO> chkList = usimDlvryChangeMapper.selectUsimChangeChk(customerId);
			if (chkList == null || chkList.isEmpty()) {
				continue;
			}
			chkList.get(0).getReqInDay();

			String seq = usimDlvryChangeMapper.selectUsimChangeSeq(); //주문번호 생성
			chkList.get(0).setSeq(seq); //주문번호 SET
			chkList.get(0).setSessionUserId(vo.getSessionUserId());
			
			usimDlvryChangeMapper.insertUsimChangeMst(chkList.get(0)); // MST 테이블
			for(UsimDlvryChangeVO dtlVo : chkList){
 				dtlVo.setSeq(chkList.get(0).getSeq()); //주문번호 SET
 				dtlVo.setSessionUserId(vo.getSessionUserId());
				usimDlvryChangeMapper.insertUsimChangeDtl(dtlVo); // DTL 테이블
			}
		}
		//Chk테이블 데이터 삭제
		usimDlvryChangeMapper.deleteUsimChangeChk();


		return insertCnt;
	}
	
}