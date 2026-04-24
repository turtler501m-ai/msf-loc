package com.ktis.msp.batch.job.rcp.rcpmgmt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.mapper.TossMgmtMapper;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.RcpDetailVO;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.ScanVO;
import com.ktis.msp.batch.util.EncryptUtil;
import com.ktis.msp.batch.util.MultipartUtility;
import com.ktis.msp.batch.util.ObjectUtils;
import com.ktis.msp.batch.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service
public class TossMgmtService extends BaseService {

	@Autowired
	private TossMgmtMapper tossMgmtMapper;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 신청정보 미생성 Toss 개통정보 조회
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<RcpDetailVO> selectNonTossRcpList() throws MvnoServiceException {

		LOGGER.info("신청정보 미생성 Toss 개통정보 조회 START");

		// 해지고객 TEMP 이관대상 조회
		List<RcpDetailVO> list = tossMgmtMapper.selectNonTossRcpList();

		LOGGER.info("판매유심 해지 후 180일 지난 고객 이관 End");

		return list;
	}

	/**
	 * Toss 신청정보 생성
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateRcpNewDetail(RcpDetailVO vo) throws MvnoServiceException {
		
		//v2017.05 렌탈 오프라인 신청
		// PROD_TYPE 02 - 렌탈 , ON_OFF_TYPE 1 - 오프라인
		if("02".equals(vo.getProdType()) && "UU".equals(vo.getReqBuyType()) && "1".equals(vo.getOnOffType())){
			vo.setReqBuyType("MM");
			vo.setPrdtId(vo.getRntlPrdtId());
			vo.setModelId(vo.getRntlPrdtId());
			vo.setReqModelName(vo.getRntlPrdtCode());
			vo.setRealMdlInstamt("0");
			vo.setModelInstallment("0");
		}
		
		/*신규등록만 처리
		boolean isNew = false;*/
		
		if(StringUtils.isEmpty(vo.getRequestKey())){
			/*isNew = true;*/
			RcpDetailVO keyMap = tossMgmtMapper.maxRequestkey();
			
			vo.setRequestKey(keyMap.getRequestKey());
			vo.setResNo(keyMap.getResNo());
			vo.setShopCd( vo.getSessionUserOrgnId() );
			
			// 대리점 코드
			/*vo.setCntpntShopId("1100023193"); // orgnId
			vo.setAgentCode("VKI0167"); // MSP_JUO_SUB_INFO.OPEN_AGENT_CD
*/			vo.setManagerCode("M0001");
		}
		
		// 납부방법(PAYM) - AA 자동충전(계좌이체), C 신용카드, D 은행계좌, R 지로
		if(StringUtils.equals(vo.getReqPayType(), "C")){
			vo.setReqBank(null);
			vo.setReqAccountNumber(null);
			vo.setReqAccountName(null);
			vo.setOthersPaymentAg(null);
			vo.setReqAccountRrn1(null);
			vo.setReqAccountRrn2(null);
			vo.setReqAcType(null);
			vo.setReqAc01Balance(null);
			vo.setReqAc02Day(null);
			vo.setReqAcAmount(null);
		} else if(StringUtils.equals(vo.getReqPayType(), "D")){
			vo.setReqCardCompany(null);
			vo.setReqCardNo1(null);
			vo.setReqCardNo2(null);
			vo.setReqCardNo3(null);
			vo.setReqCardNo4(null);
			vo.setReqCardMm(null);
			vo.setReqCardYy(null);
			vo.setReqCardName(null);
			vo.setOthersPaymentAgClone(null);
			vo.setReqCardRrn1(null);
			vo.setReqCardRrn2(null);
			vo.setReqAcType(null);
			vo.setReqAc01Balance(null);
			vo.setReqAc02Day(null);
			vo.setReqAcAmount(null);
		} else if(StringUtils.equals(vo.getReqPayType(), "AA")){
			vo.setReqCardCompany(null);
			vo.setReqCardNo1(null);
			vo.setReqCardNo2(null);
			vo.setReqCardNo3(null);
			vo.setReqCardNo4(null);
			vo.setReqCardMm(null);
			vo.setReqCardYy(null);
			vo.setReqCardName(null);
			vo.setOthersPaymentAgClone(null);
			vo.setReqCardRrn1(null);
			vo.setReqCardRrn2(null);
		} else {
			vo.setReqBank(null);
			vo.setReqAccountNumber(null);
			vo.setReqCardCompany(null);
			vo.setReqCardNo1(null);
			vo.setReqCardNo2(null);
			vo.setReqCardNo3(null);
			vo.setReqCardNo4(null);
			vo.setReqCardMm(null);
			vo.setReqCardYy(null);
			vo.setReqAccountName(null);
			vo.setOthersPaymentAg(null);
			vo.setReqAccountRrn1(null);
			vo.setReqAccountRrn2(null);
			vo.setReqCardName(null);
			vo.setOthersPaymentAgClone(null);
			vo.setReqCardRrn1(null);
			vo.setReqCardRrn2(null);
			vo.setReqAcType(null);
			vo.setReqAc01Balance(null);
			vo.setReqAc02Day(null);
			vo.setReqAcAmount(null);
		}
		
		vo.setFaxnum(vo.getFaxnum1() + vo.getFaxnum2() + vo.getFaxnum3());
		if(StringUtils.equals(vo.getFaxyn(), "Y")){
			vo.setScanId(null);
		}
		// 고객메일
		if(StringUtils.isNotBlank(vo.getCstmrMail1()) && StringUtils.isNotBlank(vo.getCstmrMail2())){
			vo.setCstmrMail(vo.getCstmrMail1()+"@"+vo.getCstmrMail2());
		}
		// 주민번호
		if(StringUtils.isNotBlank(vo.getCstmrNativeRrn1()) && StringUtils.isNotBlank(vo.getCstmrNativeRrn2())){
			vo.setCstmrNativeRrn(vo.getCstmrNativeRrn1() + vo.getCstmrNativeRrn2());
		}
		// 외국인등록번호
		if(StringUtils.isNotBlank(vo.getCstmrForeignerRrn1()) && StringUtils.isNotBlank(vo.getCstmrForeignerRrn2())){
			vo.setCstmrForeignerRrn(vo.getCstmrForeignerRrn1() + vo.getCstmrForeignerRrn2());
		}
		// 카드번호
		if(StringUtils.isNotBlank(vo.getReqCardNo1()) && StringUtils.isNotBlank(vo.getReqCardNo2()) && StringUtils.isNotBlank(vo.getReqCardNo3()) && StringUtils.isNotBlank(vo.getReqCardNo4())){
			vo.setReqCardNo(vo.getReqCardNo1() + vo.getReqCardNo2() + vo.getReqCardNo3() + vo.getReqCardNo4());
		}
		// 미성년자주민번호
		if(StringUtils.isNotBlank(vo.getMinorAgentRrn1()) && StringUtils.isNotBlank(vo.getMinorAgentRrn2())){
			vo.setMinorAgentRrn(vo.getMinorAgentRrn1() + vo.getMinorAgentRrn2());
		}
		// 대리인주민번호
		if(StringUtils.isNotBlank(vo.getJrdclAgentRrn1()) && StringUtils.isNotBlank(vo.getJrdclAgentRrn2())){
			vo.setJrdclAgentRrn(vo.getJrdclAgentRrn1() + vo.getJrdclAgentRrn2());
		}
		// 계좌주 주민번호
		if(StringUtils.isNotBlank(vo.getReqAccountRrn1()) && StringUtils.isNotBlank(vo.getReqAccountRrn2())){
			vo.setReqAccountRrn(vo.getReqAccountRrn1() + vo.getReqAccountRrn2());
		}
		// 유효기간
		if(StringUtils.isNotBlank(vo.getReqCardRrn1()) && StringUtils.isNotBlank(vo.getReqCardRrn2())){
			vo.setReqCardRrn(vo.getReqCardRrn1() + vo.getReqCardRrn2());
		}
		// 법인번호
		if(StringUtils.isNotBlank(vo.getCstmrJuridicalRrn1()) && StringUtils.isNotBlank(vo.getCstmrJuridicalRrn2())){
			vo.setCstmrJuridicalRrn(vo.getCstmrJuridicalRrn1() + vo.getCstmrJuridicalRrn2());
		}
		// 법인사업자번호
		if(StringUtils.isNotBlank(vo.getCstmrJuridicalNumber1()) && StringUtils.isNotBlank(vo.getCstmrJuridicalNumber2()) && StringUtils.isNotBlank(vo.getCstmrJuridicalNumber3())){
			vo.setCstmrJuridicalNumber(vo.getCstmrJuridicalNumber1() + vo.getCstmrJuridicalNumber2() + vo.getCstmrJuridicalNumber3());
		}
		// 개인사업자번호
		if(StringUtils.isNotBlank(vo.getCstmrPrivateNumber1()) && StringUtils.isNotBlank(vo.getCstmrPrivateNumber2()) && StringUtils.isNotBlank(vo.getCstmrPrivateNumber3())){
			vo.setCstmrPrivateNumber(vo.getCstmrPrivateNumber1() + vo.getCstmrPrivateNumber2() + vo.getCstmrPrivateNumber3());
		}
		
		// 무선데이터이용구분 AN 차단, AY 이용, DN 데이터로밍차단
		String reqWireType = "";
		if(StringUtils.equals(vo.getReqWireType1(), "1")) {
			if(StringUtils.isNotEmpty(reqWireType)) reqWireType = reqWireType.concat(",");
			reqWireType = reqWireType.concat("AY");
		}
		if(StringUtils.equals(vo.getReqWireType2(), "1")) {
			if(StringUtils.isNotEmpty(reqWireType)) reqWireType = reqWireType.concat(",");
			reqWireType = reqWireType.concat("AN");
		}
		if(StringUtils.equals(vo.getReqWireType3(), "1")) {
			if(StringUtils.isNotEmpty(reqWireType)) reqWireType = reqWireType.concat(",");
			reqWireType = reqWireType.concat("DN");
		}
		
		vo.setReqWireType(reqWireType);
		
		// 유심비납부방법(1:면제,2:일시납,3:후납)
		// 유심비납부유형(B: 후청구, R:선납 , N:비구매)
		if("1".equals(vo.getUsimPayMthdCd())){
			vo.setUsimPriceType("N");
		}else if("2".equals(vo.getUsimPayMthdCd())){
			vo.setUsimPriceType("R");
		}else if("3".equals(vo.getUsimPayMthdCd())){
			vo.setUsimPriceType("B");
		}
		// 가입비납부방법(1:면제,2:일시납,3:3개월분납)
		// 가입비납부유형(R:완납, I:분납 , P:면제) 
		if("1".equals(vo.getJoinPayMthdCd())){
			vo.setJoinPriceType("P");
		}else if("2".equals(vo.getJoinPayMthdCd())){
			vo.setJoinPriceType("R");
		}else if("3".equals(vo.getJoinPayMthdCd())){
			vo.setJoinPriceType("I");
		}
		
		if(StringUtils.equals(vo.getReqPayType(), "D")){
			vo.setReqPayOtherFlag(vo.getOthersPaymentAg());
		}else if(StringUtils.equals(vo.getReqPayType(), "D")){
			vo.setReqPayOtherFlag(vo.getOthersPaymentAgClone());
		}
		//2015년 1월 19일  단말기 출고가 validation 추가  기존에는 isNotEmpty만 있었음 else 구문 추가   
		if(vo.getModelPrice()!=null && StringUtils.isNotEmpty(vo.getModelPrice()) && !vo.getModelPrice().equals("null")){
			int mp = 0;
			try {
				mp = Integer.parseInt(vo.getModelPrice());
			} catch (NumberFormatException e) {
			}
			if(mp > 0){
				vo.setModelPriceVat(String.valueOf( mp * 0.1 ));
			}
		}else{
			vo.setModelPriceVat("0");
		}
		
		// 스캔, 팩스 검색일 경우 받아온 scanId로 셋팅한다
		if(vo.getBtnType() != null && vo.getBtnType().equals("sSearch") && vo.getScanIdBySearch() != null && !vo.getScanIdBySearch().equals("")) {
			vo.setScanId(vo.getScanIdBySearch());
		}else if(vo.getBtnType() != null && vo.getBtnType().equals("fSearch") && vo.getScanIdBySearch() != null && !vo.getScanIdBySearch().equals("")) {
			vo.setScanId(vo.getScanIdBySearch());
		}
		
		// 스캔, 팩스일 검색일 경우 넘겨받은 판매점 코드가 있을 경우 판매점 코드를 셋팅한다.
		if( vo.getBtnType() != null && vo.getBtnType().equals("sSearch") && vo.getShopCdBySearch() != null && !vo.getShopCdBySearch().equals("")) {
			vo.setShopCd(vo.getShopCdBySearch());
		}else if( vo.getBtnType() != null && vo.getBtnType().equals("fSearch") && vo.getShopCdBySearch() != null && !vo.getShopCdBySearch().equals("")) {
			vo.setShopCd(vo.getShopCdBySearch());
		}
		
		int updateRcpRequestCnt = 0;
		
		
		/*암호화 대상 필드 암호화*/
		if(vo.getCstmrNativeRrn() != null && !vo.getCstmrNativeRrn().equals("")){
			vo.setCstmrNativeRrn(EncryptUtil.ace256Enc(vo.getCstmrNativeRrn()));
		}
		if(vo.getCstmrForeignerRrn() != null && !vo.getCstmrForeignerRrn().equals("")){
			vo.setCstmrForeignerRrn(EncryptUtil.ace256Enc(vo.getCstmrForeignerRrn()));
		}
		if(vo.getReqAccountNumber() != null && !vo.getReqAccountNumber().equals("")){
			vo.setReqAccountNumber(EncryptUtil.ace256Enc(vo.getReqAccountNumber()));
		}
		if(vo.getReqCardNo() != null && !vo.getReqCardNo().equals("")){
			vo.setReqCardNo(EncryptUtil.ace256Enc(vo.getReqCardNo()));
		}
		if(vo.getMinorAgentRrn() != null && !vo.getMinorAgentRrn().equals("")){
			vo.setMinorAgentRrn(EncryptUtil.ace256Enc(vo.getMinorAgentRrn()));
		}
		if(vo.getCstmrForeignerPn() != null && !vo.getCstmrForeignerPn().equals("")){
			vo.setCstmrForeignerPn(EncryptUtil.ace256Enc(vo.getCstmrForeignerPn()));
		}
		if(vo.getOthersPaymentRrn() != null && !vo.getOthersPaymentRrn().equals("")){
			vo.setOthersPaymentRrn(EncryptUtil.ace256Enc(vo.getOthersPaymentRrn()));
		}
		if(vo.getJrdclAgentRrn() != null && !vo.getJrdclAgentRrn().equals("")){
			vo.setJrdclAgentRrn(EncryptUtil.ace256Enc(vo.getJrdclAgentRrn()));
		}
		if(vo.getEntrustResRrn() != null && !vo.getEntrustResRrn().equals("")){
			vo.setEntrustResRrn(EncryptUtil.ace256Enc(vo.getEntrustResRrn()));
		}
		if(vo.getReqAccountRrn() != null && !vo.getReqAccountRrn().equals("")){
			vo.setReqAccountRrn(EncryptUtil.ace256Enc(vo.getReqAccountRrn()));
		}
		if(vo.getReqCardRrn() != null && !vo.getReqCardRrn().equals("")){
			vo.setReqCardRrn(EncryptUtil.ace256Enc(vo.getReqCardRrn()));
		}
		if(vo.getSelfIssuNum() != null && !vo.getSelfIssuNum().equals("")){
			vo.setSelfIssuNum(EncryptUtil.ace256Enc(vo.getSelfIssuNum()));
		}
		if(vo.getMinorSelfIssuNum() != null && !vo.getMinorSelfIssuNum().equals("")){
			vo.setMinorSelfIssuNum(EncryptUtil.ace256Enc(vo.getMinorSelfIssuNum()));
		}
			
		updateRcpRequestCnt = tossMgmtMapper.insertRcpRequest(vo);
		
		tossMgmtMapper.insertRcpCustomer(vo);
		tossMgmtMapper.insertRcpSale(vo);
		tossMgmtMapper.insertRcpDelivery(vo);
		tossMgmtMapper.insertRcpReq(vo);
		tossMgmtMapper.insertRcpMove(vo);
		tossMgmtMapper.insertRcpPay(vo);
		tossMgmtMapper.insertRcpAgent(vo);
		
		//시퀸스 선 조회 후 값..
		int stateKey = tossMgmtMapper.getStateKey(vo);
		vo.setStatusKey(stateKey);
		
		tossMgmtMapper.insertRcpState(vo);
		tossMgmtMapper.insertRcpChange(vo);
		tossMgmtMapper.insertRcpDvcChg(vo);
		
		if("NAC3".equals(vo.getOperType()) || "MNP3".equals(vo.getOperType())){
			tossMgmtMapper.deleteRcpAddition(vo);
			
			if( vo.getAdditionKey() != null && !"".equals(vo.getAdditionKey()) ){ 
				tossMgmtMapper.insertRcpAddition(vo);
			}
			
		}
		
		// 서식지가 생성된 TOSS 연동정보 수정
		tossMgmtMapper.updateTossRcpCrYn(vo);
		
		return updateRcpRequestCnt;
	}
	
	public void prodSendScan(ScanVO vo, RcpDetailVO rcpDetailVO) throws MvnoServiceException {
				
		//1. 서식지 정보를 조회
		Map<String, String> requestData = tossMgmtMapper.getAppFormData(vo);
		
		LOGGER.info("requestData ===> "+ requestData);
		
		//2. 복호화 처리
		try{
			requestData.put("CSTMR_FOREIGNER_PN",  EncryptUtil.ace256Dec(requestData.get("CSTMR_FOREIGNER_PN")) ) ;
			requestData.put("CSTMR_FOREIGNER_RRN", EncryptUtil.ace256Dec(requestData.get("CSTMR_FOREIGNER_RRN")) ) ;
			requestData.put("CSTMR_NATIVE_RRN",    EncryptUtil.ace256Dec(requestData.get("CSTMR_NATIVE_RRN")) ) ;
			requestData.put("MINOR_AGENT_RRN",     EncryptUtil.ace256Dec(requestData.get("MINOR_AGENT_RRN")) ) ;
			requestData.put("ENTRUST_RES_RRN",     EncryptUtil.ace256Dec(requestData.get("ENTRUST_RES_RRN")) ) ;
			requestData.put("OTHERS_PAYMENT_RRN",  EncryptUtil.ace256Dec(requestData.get("OTHERS_PAYMENT_RRN")) ) ;
			requestData.put("NAME_CHANGE_RRN",     EncryptUtil.ace256Dec(requestData.get("NAME_CHANGE_RRN")) ) ;
			requestData.put("REQ_ACCOUNT_NUMBER",  EncryptUtil.ace256Dec(requestData.get("REQ_ACCOUNT_NUMBER")) ) ;
			requestData.put("REQ_ACCOUNT_RRN",     EncryptUtil.ace256Dec(requestData.get("REQ_ACCOUNT_RRN")) ) ;
			requestData.put("REQ_CARD_NO",         EncryptUtil.ace256Dec(requestData.get("REQ_CARD_NO")) ) ;
			requestData.put("RES_NO", vo.getResNo());
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP1008", e);
		}
		
		//3. 주민번호 분석 값 설정
		settingSsnData(requestData);
		
		//4. 각종 설정 값 설정
		settingData(requestData);
		
		//5. XML 파일 생성
		createXml(vo, requestData);
		
		//6. XML 파일 전송 및 삭제 처리
		xmlFileSend(vo) ;
		
		//7. 전송상태 상태값 UPDATE 처리
		tossMgmtMapper.updateAppFormXmlYn(vo);
		
		String scanId = tossMgmtMapper.selectScanId(vo);
		
		rcpDetailVO.setScanId(scanId);
		
		//8. ScanId 값 UPDATE 처리
		tossMgmtMapper.updateTossJueSubInfo(rcpDetailVO);
		tossMgmtMapper.updateTossRequestDtl(rcpDetailVO);
		
	}

	/** 주민번호 분석후 값을 설정 처리
	 * @param
	 */
	public void settingSsnData(Map<String, String> reqMapData) {
		if ( reqMapData.containsKey("CSTMR_NATIVE_RRN")
				&& !StringUtils.isBlank(reqMapData.get("CSTMR_NATIVE_RRN"))
				&& reqMapData.get("CSTMR_NATIVE_RRN").length() > 7 ) {
				
			String tempVal = reqMapData.get("CSTMR_NATIVE_RRN").substring(6, 7);
			reqMapData.put("CSTMR_NATIVE_RRN" ,reqMapData.get("CSTMR_NATIVE_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("MINOR_AGENT_RRN")
				&& !StringUtils.isBlank(reqMapData.get("MINOR_AGENT_RRN"))
				&& reqMapData.get("MINOR_AGENT_RRN").length() > 7 ) {
			
			String tempVal = reqMapData.get("MINOR_AGENT_RRN").substring(6, 7);
			reqMapData.put("MINOR_AGENT_RRN" ,reqMapData.get("MINOR_AGENT_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("MINOR_AGENT_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("MINOR_AGENT_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("MINOR_AGENT_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("MINOR_AGENT_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("ENTRUST_RES_RRN")
				&& !StringUtils.isBlank(reqMapData.get("ENTRUST_RES_RRN"))
				&& reqMapData.get("ENTRUST_RES_RRN").length() > 7 ) {
			
			String tempVal = reqMapData.get("ENTRUST_RES_RRN").substring(6, 7);
			reqMapData.put("ENTRUST_RES_RRN" ,reqMapData.get("ENTRUST_RES_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("ENTRUST_RES_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("ENTRUST_RES_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("ENTRUST_RES_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("ENTRUST_RES_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("NAME_CHANGE_RRN")
				&& !StringUtils.isBlank(reqMapData.get("NAME_CHANGE_RRN"))
				&& reqMapData.get("NAME_CHANGE_RRN").length() > 7 ) {
			
			String tempVal = reqMapData.get("NAME_CHANGE_RRN").substring(6, 7);
			reqMapData.put("NAME_CHANGE_RRN" ,reqMapData.get("NAME_CHANGE_RRN").substring(0, 6) );
			
			if("1".equals(tempVal)) {
				reqMapData.put("NAME_CHANGE_RRN_M", "V");
			} else if("2".equals(tempVal)) {
				reqMapData.put("NAME_CHANGE_RRN_W", "V");
			} else if("3".equals(tempVal)) {
				reqMapData.put("NAME_CHANGE_RRN_M", "V");
			} else if("4".equals(tempVal) ) {
				reqMapData.put("NAME_CHANGE_RRN_W", "V");
			}
		}
		
		if ( reqMapData.containsKey("OTHERS_PAYMENT_RRN")
				&& !StringUtils.isBlank(reqMapData.get("OTHERS_PAYMENT_RRN"))
				&& reqMapData.get("OTHERS_PAYMENT_RRN").length() > 6 ) {
			reqMapData.put("OTHERS_PAYMENT_RRN" ,reqMapData.get("OTHERS_PAYMENT_RRN").substring(0, 6) );
		}
		
		if ( reqMapData.containsKey("CSTMR_FOREIGNER_RRN")
				&& !StringUtils.isBlank(reqMapData.get("CSTMR_FOREIGNER_RRN"))
				&& reqMapData.get("CSTMR_FOREIGNER_RRN").length() > 7 ) {
			String tempVal = reqMapData.get("CSTMR_FOREIGNER_RRN").substring(6, 7);
			reqMapData.put("CSTMR_FOREIGNER_RRN" ,reqMapData.get("CSTMR_FOREIGNER_RRN").substring(0, 6) );
			
			if("5".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_M", "V");
			} else if("6".equals(tempVal)) {
				reqMapData.put("CSTMR_NATIVE_RRN_W", "V");
			}
		}
	}
	
	
	/** 주민번호 분석후 값을 설정 처리
	 * @param
	 */
	public void settingData(Map<String,String> reqMapData) {
		
		//납부방법
		if ( reqMapData.containsKey("REQ_PAY_TYPE")
				&& !StringUtils.isBlank(reqMapData.get("REQ_PAY_TYPE")) ) {
			String tempVal = reqMapData.get("REQ_PAY_TYPE");
			
			if ("D".equals(tempVal) || "AA".equals(tempVal) ) {//자동이체 = 계좌번호
				reqMapData.remove("REQ_CARD_COMPANY");
				reqMapData.remove("REQ_CARD_NO");
				reqMapData.remove("REQ_CARD_YY");
				reqMapData.remove("REQ_CARD_MM");
			}else if("C".equals(tempVal)) {// 신용카드 = 카드번호
				reqMapData.remove("REQ_BANK");
				reqMapData.remove("REQ_ACCOUNT_NUMBER");
			}
		}
		
		//구매유형
		if ( reqMapData.containsKey("REQ_BUY_TYPE")
				&& !StringUtils.isBlank(reqMapData.get("REQ_BUY_TYPE"))  ) {
			String tempVal = reqMapData.get("REQ_BUY_TYPE");
	
			if ("UU".equals(tempVal)) {//유심 구매면 핸드폰 대금 체크 안함
				reqMapData.remove("MODEL_MONTHLY_TYPE");
				reqMapData.remove("REQ_MODEL_NAME");
				reqMapData.remove("REQ_MODEL_COLOR");
			}else {
				String modelName = StringUtil.NVL(reqMapData.get("REQ_MODEL_NAME"),"");
				String modelColor = StringUtil.NVL(reqMapData.get("REQ_MODEL_COLOR"),"");
				if(!modelName.equals("")) {
					reqMapData.remove("REQ_MODEL_NAME");
					reqMapData.remove("REQ_MODEL_COLOR");
					String tempName = modelName + "( " + modelColor + " )";
					reqMapData.put("REQ_MODEL_NAME", tempName);
				}
			}
		}
		
		//신청정보_무선데이터_이용_타입
		if ( reqMapData.containsKey("REQ_WIRE_TYPE")
				&& !StringUtils.isBlank(reqMapData.get("REQ_WIRE_TYPE"))
				&& reqMapData.get("REQ_WIRE_TYPE").length() > 1 ) {
			String tempVal = reqMapData.get("REQ_WIRE_TYPE");
	
			String [] tempArray = tempVal.split(",");
			for (int i = 0; i < tempArray.length; i++) {
				reqMapData.put("REQ_WIRE_TYPE" + i, tempArray[i]);
			}
			reqMapData.remove("REQ_WIRE_TYPE");
		}
		
		//고객구분
		if ( reqMapData.containsKey("CSTMR_TYPE_ORG")
				&& !StringUtils.isBlank(reqMapData.get("CSTMR_TYPE_ORG")) ) {
			String tempVal = reqMapData.get("CSTMR_TYPE_ORG");
	
			if(!"NM".equals(tempVal)){
				reqMapData.remove("NW_BLCK_AGRM_YN");
				reqMapData.remove("APP_BLCK_AGRM_YN");
				reqMapData.remove("APP_CD");
				reqMapData.remove("APP_CD_NAME");
			}
			if(!"FN".equals(tempVal)){
				//외국인이 아니면 등록번호 여권번호 국적을 제외한다
				reqMapData.remove("CSTMR_FOREIGNER_RRN");
				reqMapData.remove("CSTMR_FOREIGNER_NATION");
				reqMapData.remove("CSTMR_FOREIGNER_PN");
			}
		}
		
		//APP구분코드
		//청소년 유해차단관련 올레 자녀 안심폰서비스 , 스마트보안관 선택시 기타는 표시 안한다
		if ( reqMapData.containsKey("APP_CD")
				&& !StringUtils.isBlank(reqMapData.get("APP_CD")) ) {
			String tempVal = reqMapData.get("APP_CD");
			
			if("1".equals(tempVal) || "3".equals(tempVal)){
				reqMapData.remove("APP_CD_NAME");
			}
		}
	}
	
	public void createXml(ScanVO vo, Map<String,String> requestData) throws MvnoServiceException {
		File folderPath = new File(vo.getScanPath());
		
		if(!folderPath.exists()){
			folderPath.mkdirs();
		}
		
		LOGGER.info("[createXml]scanVO = " + vo);
		LOGGER.info("[createXml]requestData = " + requestData);
		
//		String tempIndex = "1";
//		boolean xmlCreate = true;
//		boolean inputTagCreate = true;
		
//		StringBuffer xml = new StringBuffer(InitializaionXml(vo));
		
		// 서식지 그룹화
		String tempPageCode = "";
		int totalPage = 0; //서식지 갯수
		boolean xmlCreate = true;
		
		String xmlFileName = "online_information" + "_" + vo.getRequestKey() + ".xml";
		
		LOGGER.info("[createXml]xmlFileName = " + xmlFileName);
		
		//서식지 그룹 코드 가져오기
		String groupCode = this.getGroupCode(requestData);
		
		LOGGER.info("[createXml]groupCode = " + groupCode);
		
		//5-1. 서식지 위치정보 가져오기
		List<Map<String, String>> appPointInfoList = tossMgmtMapper.getAppFormPointGroupList(groupCode);
		
		StringBuffer inputDataXml = new StringBuffer();
		
		for (int i = 0; i < appPointInfoList.size(); i++) {
			Map<String,String> appPointInfo = appPointInfoList.get(i);
			
			LOGGER.info("[createXml]appPointInfo = " + appPointInfo);
			
			String appFormColunmName = appPointInfo.get("COLUMN_NAME"); //데이터 저장 칼럼명
			int metaRow              = Integer.parseInt(appPointInfo.get("METAROW"));
			int metaLine             = Integer.parseInt(appPointInfo.get("METALINE"));
			String codeDataYn        = String.valueOf(StringUtil.NVL(appPointInfo.get("CODEDATA_YN"), ""));
			String codeData          = String.valueOf(StringUtil.NVL(appPointInfo.get("CODEDATA"), ""));
//			String pageIndex         = String.valueOf(StringUtil.NVL(appPointInfo.get("PAGEINDEX"), ""));
			String pageCode          = String.valueOf(StringUtil.NVL(appPointInfo.get("PAGE_CODE"), ""));
			String deleteColumnYn    = String.valueOf(StringUtil.NVL(appPointInfo.get("DELETECOLUMNYN"), ""));
			String appFormInsertData = String.valueOf(requestData.get(appFormColunmName));
			
			if (i == 0) {
				inputDataXml.append("\n").append("<INPUT_DATA pagecode='"+pageCode+"'>");
				tempPageCode = pageCode;
				totalPage++;
			}
			
			if(!pageCode.equals(tempPageCode)) {
				inputDataXml.append("\n").append("</INPUT_DATA>");
				inputDataXml.append("\n").append("<INPUT_DATA pagecode='"+pageCode+"'>");
				tempPageCode = pageCode;
				totalPage++;
			}
			
			//마켓팅 제공 동의
			if(deleteColumnYn.equals("CLAUSE_PRI_AD_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_PRI_AD_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//2015-12-23제휴관련 추가
			if(deleteColumnYn.equals("CLAUSE_JEHU_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_JEHU_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			//20160601 렌탈 단말배상금 안내사항 동의여부
			if(deleteColumnYn.equals("CLAUSE_RENTAL_MODEL_CP")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_MODEL_CP"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//20160601 렌탈 단말배상금(부분파손) 안내사항 동의여부
			if(deleteColumnYn.equals("CLAUSE_RENTAL_MODEL_CP_PR")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_MODEL_CP_PR"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//20160601 중고렌탈 프로그램 서비스 이용에 대한 동의서 동의여부
			if(deleteColumnYn.equals("CLAUSE_RENTAL_SERVICE")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_RENTAL_SERVICE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//20170110 MPPS35선불요금제 제약사항 동의 여부
			if(deleteColumnYn.equals("CLAUSE_MPPS35_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_MPPS35_FLAG"));
				if( !deleteTempValue.equals("Y")) { //Y일때만 서명 입력
					continue;
				}
			}
			
			//201703 금융제휴 요금제 동의 여부
			if (deleteColumnYn.equals("CLAUSE_FINANCE_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_FINANCE_FLAG"));
				if (deleteTempValue.equals("Y")) { //Y일때만 서명 입력
					//법정대리인 서명 여부
					if ("MINOR".equals(codeData) && (StringUtils.isEmpty(requestData.get("MINOR_AGENT_NAME")) || StringUtils.isEmpty(requestData.get("MINOR_AGENT_RRN")))) {
						continue;
					}
				} else {
					continue;
				}
			}

			if(deleteColumnYn.equals("CLAUSE_5G_COVERAGE_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_5G_COVERAGE_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("PERSONAL_INFO_COLLECT_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("PERSONAL_INFO_COLLECT_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("OTHERS_TRNS_AGREE")) {
				String deleteTempValue = String.valueOf(requestData.get("OTHERS_TRNS_AGREE"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}

			if(deleteColumnYn.equals("CLAUSE_PARTNER_OFFER_FLAG")) {
				String deleteTempValue = String.valueOf(requestData.get("CLAUSE_PARTNER_OFFER_FLAG"));
				if(deleteTempValue.equals("N")) {
					continue;
				}
			}
			
			//메타 데이터 여부
			if(codeDataYn.equals("Y")) {
				//데이터와 메타 정보가 동일 한지 확인한다.
				if(appFormInsertData.equals(codeData)) { // 메타데이터를 V로 변환한다.
					xmlCreate = true;
					if(appFormColunmName.equals("PROD_TYPE")){//20160608 렌탈일때만 출고가 밑에 텍스트 넣어준다
						appFormInsertData = "(분실파손 단말배상금)";
					}else{
						appFormInsertData = "V";
					}
				}else {
					xmlCreate = false;
				}
			}else {
				xmlCreate = true;
			}
			
			//데이터 생성 여부
			if(xmlCreate && StringUtil.isNotNull(appFormInsertData)) {
				inputDataXml.append("\n").append("<DATA XPosition='"+metaLine+"' YPosition='"+metaRow+"'>"+appFormInsertData+"</DATA>");
			}
			
		}// end for
		
		inputDataXml.append("\n").append("</INPUT_DATA>");
		inputDataXml.append("\n").append("</ONLINE_INFORMATION>");
		
		//REGISTER_DATA XML 생성
		StringBuffer registerDataXml = new StringBuffer(InitializaionXml(vo, totalPage));
		
		String xml = registerDataXml.append(inputDataXml).toString();
		
		saveXML(xml, xmlFileName);
			
		LOGGER.info("requestData xml ===> "+ xml );
			
	}
	
	/**
	 * 저장된 XML 전달
	 * @param xmlFileName
	 * @throws IOException
	 */
	public void xmlFileSend(ScanVO vo) throws MvnoServiceException{
		
		
		String xmlFileName = "online_information" + "_" + vo.getRequestKey() + ".xml";
		LOGGER.error("fileName #########################:" + xmlFileName);

		// --------------------------------------------------------------------
		// Initializaion.
		// --------------------------------------------------------------------
		//요청 Url
		String requestUrl = vo.getScanUrl();

		//인코딩
		String charSet = "UTF-8";
		// 파일 불러오는 경로
		String filePath = vo.getScanPath() + xmlFileName;

		LOGGER.error("scan url ################################: " + requestUrl);
		
		//List<String> responseList = new ArrayList<String>();

		// --------------------------------------------------------------------
		// Service Process.
		// --------------------------------------------------------------------
		File xmlFile = new File(filePath);
		try {
			MultipartUtility multipart = new MultipartUtility(requestUrl, charSet);
			multipart.addFilePart("TransferFile", xmlFile);
			//responseList = multipart.finish();
			List<String> responseList = multipart.finish();
			if (responseList != null && responseList.size() > 0) {
				LOGGER.info(ObjectUtils.convertObjectToString("JTK responseList ::" + responseList));
			}
			
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP1008", e);
		} finally {
			// DELETE FILE
			if (xmlFile != null) {
				xmlFile.delete();
			}
		}
		
		LOGGER.error("xmlFileSend END ################################: " );
	}
	
	//서식지 그룹 구분하기
	private String getGroupCode(Map<String,String> requestData) {
		
		LOGGER.info("[getGroupCode] requestData :: " + requestData);
		
		String groupCode = "";
		
		boolean isFinaceRate = false; //금융제휴 요금제 여부
		boolean isRental = false;//렌탈 상품 여부
		
		String socCode = StringUtil.NVL(requestData.get("SOC_CODE"), ""); //가입 요금제 코드
		String clauseRentalService = StringUtil.NVL(requestData.get("CLAUSE_RENTAL_SERVICE"), ""); //렌탈상품
		
//			String socCode = requestData.containsKey("SOC_CODE") && requestData.get("SOC_CODE") != null ? requestData.get("SOC_CODE") : "";
//			String clauseRentalService = requestData.containsKey("CLAUSE_RENTAL_SERVICE") && requestData.get("CLAUSE_RENTAL_SERVICE") != null ? requestData.get("CLAUSE_RENTAL_SERVICE") : "";
		
		LOGGER.info("[getGroupCode] socCode :: " + socCode);
		LOGGER.info("[getGroupCode] clauseRentalService :: " + clauseRentalService);
		
		//금융제휴 요금제 체크
		List<Map<String, Object>> list = tossMgmtMapper.getMcpCodeList("ClauseFinanceRatesCD");
		
		if(list != null){
			for(Map<String, Object> map : list){
				if(socCode.equals((String) map.get("DTL_CD"))){
					isFinaceRate = true;
					break;
				}
			}
		}
		
		//렌탈 상품 체크
		if ("Y".equals(clauseRentalService)) {
			isRental = true;
		}
		
		if (isRental) {
			if (isFinaceRate) { //렌탈동부그룹
				groupCode = "B002";
			} else { //렌탈그룹
				groupCode = "B001";
			}
		} else {
			if (isFinaceRate) { //기본동부그룹
				groupCode = "A002";
			} else { //기본그룹
				groupCode = "A001"; //default 그룹
			}
		}
		
		LOGGER.info("[getGroupCode] getGroupCode :: " + groupCode);
		
		return groupCode;
	}
	
	/**
	 * 초기 XML 생성
	 * @param InitializaionXml
	 * @return
	 */
	public String InitializaionXml(ScanVO vo, int totalPage){
		// --------------------------------------------------------------------
		// Initializaion.
		// --------------------------------------------------------------------
		StringBuffer xml = new StringBuffer();
		
		xml.append("<?xml version='1.0' encoding='utf-8' ?>");
		xml.append("\n").append("<ONLINE_INFORMATION>");
		xml.append("\n").append("<REGISTER_DATA>");
		
		Map<String,String> requestData = tossMgmtMapper.getAppFormUserData(vo);
		
		String cstmrName =  requestData.get("CSTMR_NAME");
		String agencyId = requestData.get("CNTPNT_SHOP_ID");
		String companyId = requestData.get("CNTPNT_SHOP_ID");
		String rgstPrsnId = vo.getUserId();
		String resNo = requestData.get("RES_NO");
		
		xml.append("\n").append("<AGENCY_ID>"+agencyId+"</AGENCY_ID>");
		xml.append("\n").append("<COMPANY_ID>"+companyId+"</COMPANY_ID>");
		xml.append("\n").append("<CUST_NM>"+cstmrName+"</CUST_NM>");
		xml.append("\n").append("<RGST_PRSN_ID>"+rgstPrsnId+"</RGST_PRSN_ID>");
		xml.append("\n").append("<RES_NO>"+resNo+"</RES_NO>");
		xml.append("\n").append("<TOTAL_PAGE>" + totalPage + "</TOTAL_PAGE>");
		xml.append("\n").append("</REGISTER_DATA>");
		
		return  xml.toString();
	}
	
	/**
	 * 서식지 XML 생성 함수
	 * @param xml
	 */
	public void saveXML(String xml, String xmlFileName) throws MvnoServiceException {
		
		String scanPath = propertiesService.getString("scan.form.path");
		
		LOGGER.debug("xml ===> "+ xml );
		LOGGER.debug("xmlFileName ===> "+ xmlFileName );
		LOGGER.debug("scanPath ===> "+ scanPath );
		
		FileOutputStream fos = null;
		try {
			String xmlSavePath = scanPath;
			
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xml)));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			
			fos = new FileOutputStream(new File(xmlSavePath + xmlFileName));
			StreamResult result = new StreamResult(fos);
			transformer.transform(source, result);
			
		} catch (Exception e) {
			throw new MvnoServiceException("ERCP1008", e);
		} finally {
			
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
}
