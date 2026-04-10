package com.ktis.msp.insr.insrMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.insr.insrMgmt.mapper.InsrCmpnMapper;
import com.ktis.msp.insr.insrMgmt.vo.InsrCmpnVO;

@Service
public class InsrCmpnService extends BaseService {
	
	@Autowired
	private InsrCmpnMapper insrCmpnMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	@Autowired
	private MaskingService maskingService;
	
	private HashMap<String, String> getMaskFields() {

		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("subscriberNo",	"MOBILE_PHO");
		maskFields.put("cmpnCtn",		"MOBILE_PHO");
		maskFields.put("subLinkName",	"CUST_NAME");
		maskFields.put("intmSrlNo",		"DEV_NO");
		maskFields.put("acntNo",		"ACCOUNT");
		maskFields.put("dpstNm",		"CUST_NAME");
		maskFields.put("rvisnId",		"CUST_NAME");
		maskFields.put("reqPhoneSn",	"DEV_NO");		
		
		return maskFields;		
	}
	
	public List<InsrCmpnVO> getInsrCmpnListByInsrMngmNo(InsrCmpnVO insrCmpnVO){
		
		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCmpnListByInsrMngmNo(insrCmpnVO);
		
		return rsltAryListVo;
	}
	
	public List<InsrCmpnVO> getInsrCmpnList(InsrCmpnVO insrCmpnVO, Map<String, Object> paramMap){
		
		if ( (insrCmpnVO.getSearchCd() == null || "".equals(insrCmpnVO.getSearchCd()))
			&& ((insrCmpnVO.getSearchFromDt() == null || "".equals(insrCmpnVO.getSearchFromDt()))
			|| (insrCmpnVO.getSearchToDt() == null || "".equals(insrCmpnVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "사고일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCmpnList(insrCmpnVO);
		
		maskingService.setMask(rsltAryListVo, getMaskFields(), paramMap);
		
		return rsltAryListVo;
	}
	
	public List<InsrCmpnVO> getInsrCmpnListByExcel(InsrCmpnVO insrCmpnVO, Map<String, Object> paramMap){
		
		if ( (insrCmpnVO.getSearchCd() == null || "".equals(insrCmpnVO.getSearchCd()))
			&& ((insrCmpnVO.getSearchFromDt() == null || "".equals(insrCmpnVO.getSearchFromDt()))
			|| (insrCmpnVO.getSearchToDt() == null || "".equals(insrCmpnVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "사고일자 정보가 존재 하지 않습니다.");
		}
		
		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCmpnListByExcel(insrCmpnVO);
		
		maskingService.setMask(rsltAryListVo, getMaskFields(), paramMap);
		
		return rsltAryListVo;
	}
	
	public String updIntmInsrCmpnDtl(InsrCmpnVO insrCmpnVO) {
		
		String vacId = "";
		
		if ( insrCmpnVO.getCmpnTrtmTypeCd() == null || "".equals(insrCmpnVO.getCmpnTrtmTypeCd())){
			throw new MvnoRunException(-1, "보상 처리 유형 코드가 존재 하지 않습니다.");
		}
		
		if("01".equals(insrCmpnVO.getCmpnTrtmTypeCd())) {
			if ( insrCmpnVO.getCmpnCtn() == null || "".equals(insrCmpnVO.getCmpnCtn())){
				throw new MvnoRunException(-1, "연락처가 존재 하지 않습니다.");
			}
		} else if("02".equals(insrCmpnVO.getCmpnTrtmTypeCd())) {
			if ( insrCmpnVO.getPayType() == null || "".equals(insrCmpnVO.getPayType())){
				throw new MvnoRunException(-1, "결제유형이 존재 하지 않습니다.");
			}
			
			if("A".equals(insrCmpnVO.getPayType())) {			//신용카드 -> 가상계좌
				
				vacId = getVacId(insrCmpnVO, "REG");
				
				insrCmpnVO.setVacId(vacId);
				
				//문자 발송
				getSendSms(insrCmpnVO, "92");
				
			} else if ("C".equals(insrCmpnVO.getPayType())) {	//가상계좌 -> 신용카드
				
				getVacId(insrCmpnVO, "CAN");
				
				insrCmpnVO.setVacBankCd("");
				insrCmpnVO.setVacId("");
				
			}
		} else if ("03".equals(insrCmpnVO.getCmpnTrtmTypeCd())) {
			if ( insrCmpnVO.getVacBankCd() == null || "".equals(insrCmpnVO.getVacBankCd())){
				throw new MvnoRunException(-1, "은행 정보가 존재 하지 않습니다.");
			}
			
			getVacId(insrCmpnVO, "CAN");
			
			vacId = getVacId(insrCmpnVO, "REG");
			
			insrCmpnVO.setVacId(vacId);
			
			//문자 발송
			getSendSms(insrCmpnVO, "92");
			
		} else if ("04".equals(insrCmpnVO.getCmpnTrtmTypeCd())) {
			if ( insrCmpnVO.getPayYn() == null || "".equals(insrCmpnVO.getPayYn())){
				throw new MvnoRunException(-1, "결제 상태 정보가 존재 하지 않습니다.");
			}
		} else if ("05".equals(insrCmpnVO.getCmpnTrtmTypeCd())) {
			if ( insrCmpnVO.getResNo() == null || "".equals(insrCmpnVO.getResNo())){
				throw new MvnoRunException(-1, "예약번호가 존재 하지 않습니다.");
			}
		} else {
			throw new MvnoRunException(-1, "유효하지 않는 값입니다. 관리자에게 문의 하세요.");
		}
		
		insrCmpnMapper.updIntmInsrCmpnDtl(insrCmpnVO);
		
		return vacId;
	}
	
	private String getVacId(InsrCmpnVO insrCmpnVO, String opCode) {
		
		//REG:등록, CAN:회수
		insrCmpnVO.setOpCode(opCode);
		insrCmpnVO.setGubun("INSR");
		
		insrCmpnMapper.pVacOnce(insrCmpnVO);
		
		logger.debug("insrCmpnVO===>"+insrCmpnVO+"\n");
		
		if(!"0000".equals(insrCmpnVO.getRetCd())) {
			throw new MvnoRunException(-1, "[" + insrCmpnVO.getRetCd() + "] " +  insrCmpnVO.getRetMsg());
		}
		
		return insrCmpnVO.getoVacId();
	}
	
	private void getSendSms(InsrCmpnVO insrCmpnVO, String templateId) {
		
		// SMS 템플릿 제목,내용 가져오기
		KtMsgQueueReqVO msgVO = smsMgmtMapper.getKtTemplateText(templateId);
		msgVO.setMessage((msgVO.getTemplateText())
				.replaceAll(Pattern.quote("#{insrProdNm}"), insrCmpnVO.getInsrProdNm())
				.replaceAll(Pattern.quote("#{vacBankNm}"), insrCmpnVO.getVacBankNm())
				.replaceAll(Pattern.quote("#{vacId}"), insrCmpnVO.getVacId())
				.replaceAll(Pattern.quote("#{payAmt}"), insrCmpnVO.getPayAmt()));
		msgVO.setRcptData(insrCmpnVO.getCmpnCtn());
		msgVO.setTemplateId(templateId);
		msgVO.setReserved01("MSP");
		msgVO.setReserved02(templateId);
		msgVO.setReserved03(insrCmpnVO.getSessionUserId());
		
		// SMS 저장
		smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
		
		SmsSendVO sendVO = new SmsSendVO();
		sendVO.setTemplateId(templateId);
		sendVO.setMsgId(msgVO.getMsgId());
		sendVO.setSendReqDttm(msgVO.getScheduleTime());
		sendVO.setReqId(insrCmpnVO.getSessionUserId());
		
		// 발송내역 저장
		smsMgmtMapper.insertSmsSendMst(sendVO);
		
	}
	
	public List<InsrCmpnVO> getInsrCmpnMbsList(InsrCmpnVO insrCmpnVO, Map<String, Object> paramMap){

		if ( (insrCmpnVO.getSearchCd() == null || "".equals(insrCmpnVO.getSearchCd()))
			&& ((insrCmpnVO.getSearchFromDt() == null || "".equals(insrCmpnVO.getSearchFromDt()))
			|| (insrCmpnVO.getSearchToDt() == null || "".equals(insrCmpnVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "사고일자 정보가 존재 하지 않습니다.");
		}
		

		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCmpnMbsList(insrCmpnVO);

		maskingService.setMask(rsltAryListVo, getMaskFields(), paramMap);

		return rsltAryListVo;
	}

	public List<InsrCmpnVO> getInsrCmpnMbsListByExcel(InsrCmpnVO insrCmpnVO, Map<String, Object> paramMap){

		if ( (insrCmpnVO.getSearchCd() == null || "".equals(insrCmpnVO.getSearchCd()))
			&& ((insrCmpnVO.getSearchFromDt() == null || "".equals(insrCmpnVO.getSearchFromDt()))
			|| (insrCmpnVO.getSearchToDt() == null || "".equals(insrCmpnVO.getSearchToDt()))) ){
			throw new MvnoRunException(-1, "사고일자 정보가 존재 하지 않습니다.");
		}

		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCmpnMbsListByExcel(insrCmpnVO);

		maskingService.setMask(rsltAryListVo, getMaskFields(), paramMap);

		return rsltAryListVo;
	}

	public List<InsrCmpnVO> getInsrCanList(InsrCmpnVO insrCmpnVO, Map<String, Object> paramMap){

		if( "".equals(insrCmpnVO.getSearchCd()) && "".equals(insrCmpnVO.getSearchFromDt()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(insrCmpnVO.getSearchCd()) && "".equals(insrCmpnVO.getSearchToDt()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(insrCmpnVO.getSearchCd()) && "".equals(insrCmpnVO.getSearchVal()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}

		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCanList(insrCmpnVO);

		maskingService.setMask(rsltAryListVo, maskingService.getMaskFields(), paramMap);

		return rsltAryListVo;
	}
	
	public List<InsrCmpnVO> getInsrCanListByExcel(InsrCmpnVO insrCmpnVO, Map<String, Object> paramMap){

		if( "".equals(insrCmpnVO.getSearchCd()) && "".equals(insrCmpnVO.getSearchFromDt()) ){
			throw new MvnoRunException(-1, "시작일자가 존재하지 않습니다.");
		}
		
		if( "".equals(insrCmpnVO.getSearchCd()) && "".equals(insrCmpnVO.getSearchToDt()) ){
			throw new MvnoRunException(-1, "종료일자가 존재하지 않습니다.");
		}
		
		if( !"".equals(insrCmpnVO.getSearchCd()) && "".equals(insrCmpnVO.getSearchVal()) ){
			throw new MvnoRunException(-1, "검색어가 존재하지 않습니다.");
		}

		List<InsrCmpnVO> rsltAryListVo = insrCmpnMapper.getInsrCanListByExcel(insrCmpnVO);

		maskingService.setMask(rsltAryListVo, maskingService.getMaskFields(), paramMap);

		return rsltAryListVo;
	}
}
