package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.service.CmnCdMgmtService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rcp.rcpMgmt.EncryptUtil;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.AppFormMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpNewMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.AppFormMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OpenInfoVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpBnftVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNatnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNewMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpPrdtListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpPrmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RntlPrdtInfoVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class RcpNewMgmtService extends BaseService {
	@Autowired
	private RcpNewMgmtMapper rcpNewMgmtMapper;
	
	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	@Autowired
	private AppFormMgmtMapper appFormMapper;
	
	@Autowired
	private RcpEncService encSvc;
	
	@Autowired
	private MaskingService maskingService;

	@Autowired
	private CmnCdMgmtService cmnCdMgmtService;
	
	public List<RcpPrdtListVO> checkNewPhoneSN(RcpDetailVO searchVO) {
		return rcpNewMgmtMapper.checkNewPhoneSN(searchVO);
	}
	
	public List<RcpPrdtListVO> getPrdtStatList(RcpPrdtListVO searchVO) {
		return rcpNewMgmtMapper.getPrdtStatList(searchVO);
	}
	
	public String getRateGrpByInfo(RcpNewMgmtVO searchVO) {
		return rcpNewMgmtMapper.getRateGrpByInfo(searchVO);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int copyRcpMgmt(RcpDetailVO rcpDetailVO) throws MvnoServiceException{
		int nInsertCount = 0;
		
		RcpDetailVO keyMap = rcpMgmtMapper.maxRequestkey();
		rcpDetailVO.setRequestKey(keyMap.getRequestKey());
		rcpDetailVO.setResNo(keyMap.getResNo());
		
		int stateKey = rcpMgmtMapper.getStateKey(rcpDetailVO);
		rcpDetailVO.setStatusKey(stateKey);
		
		rcpDetailVO.setRequestStateCode("00");
		rcpDetailVO.setPstate("00");
		
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpRequest(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpCustomer(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpSale(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpDelivery(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpReq(rcpDetailVO);
		this.insertPenaltyCopyHist(rcpDetailVO); // 위약금 이력 INSERT
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpMove(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpPay(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpAgent(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpState(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpChange(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpDvcChg(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpAddition(rcpDetailVO);
		nInsertCount += rcpNewMgmtMapper.insertCopyRcpBenefit(rcpDetailVO);
		

		// 20230718 자급제보상서비스 신청정보 복사
		nInsertCount += rcpNewMgmtMapper.insertCopyRwdOrder(rcpDetailVO);
		// 20231123 M포탈에서 선택한 사은품 선택 데이터 복사 
		nInsertCount += rcpNewMgmtMapper.insertGiftPrmtMPortalTrgt(rcpDetailVO);
		
		//추천인정보 INSERT
		//RECOMMEND_FLAG_CD 01 or 99 경우 체크
		String tRecommendFlag = rcpNewMgmtMapper.gettReCommendFalg(rcpDetailVO);
		rcpDetailVO.setRecommendFlagCd(tRecommendFlag);
		int idCount = 0;
		if("01".equals(tRecommendFlag)){
			//MCP_COMMEND_ID_MNG@DL_MCP 테이블에 ID존재 여부 체크
			idCount = rcpNewMgmtMapper.getCommendId(rcpDetailVO);
			if(idCount > 0)
			{
				nInsertCount += rcpNewMgmtMapper.insertCopyRecommendId(rcpDetailVO);
			}
		}else if("99".equals(tRecommendFlag)){
			idCount = rcpNewMgmtMapper.getCommendId(rcpDetailVO);
			if(idCount > 0)
			{
				Map<String,Object> map = rcpNewMgmtMapper.getCommendData(rcpDetailVO);
				rcpDetailVO.setLinkTypeCd(map.get("linkTypeCd").toString());
				rcpDetailVO.setOpenMthdCd(map.get("openMthdCd").toString());
				rcpDetailVO.setCommendSocCode01(map.get("commendSocCode01").toString());
				rcpDetailVO.setCommendSocCode02(map.get("commendSocCode02").toString());
				rcpDetailVO.setCommendSocCode03(map.get("commendSocCode03").toString());
				nInsertCount += rcpNewMgmtMapper.insertCopyRecommendId(rcpDetailVO);
			}
		}
		
		
		// 기존신청정보 상태변경
		rcpNewMgmtMapper.updateRequestState(rcpDetailVO);

		
		
		nInsertCount += rcpNewMgmtMapper.insertCopyKtInterId(rcpDetailVO);


		//20240312 K-Note 추가
		if(!StringUtils.isBlank(rcpDetailVO.getFrmpapId())){
			nInsertCount += rcpMgmtMapper.updateKisKnoteScanInfo(rcpDetailVO);
		}

		//신청서 상세 현행화
		RcpDetailVO dtlVo = rcpMgmtMapper.getMcpReqDtl(rcpDetailVO.getRequestKey());
		if (dtlVo != null) {
			if (rcpMgmtMapper.chkMcpReqDtl(rcpDetailVO.getRequestKey()) > 0) {
				rcpMgmtMapper.updateMcpReqDtl(dtlVo);
			} else {
				rcpMgmtMapper.insertMcpReqDtl(dtlVo);
			}
		}
		
		return nInsertCount;
	}
	
	private HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName",			"CUST_NAME");
		maskFields.put("cstmrForeignerRrn",	"SSN");
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("fathMobile",		"MOBILE_PHO");
		maskFields.put("cstmrForeignerPn",	"PASSPORT");
		maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
		maskFields.put("cstmrMail",			"EMAIL");
		maskFields.put("cstmrAddr",			"ADDR");
		maskFields.put("cstmrAddrDtl",		"PASSWORD");
		maskFields.put("cstmrTel",			"TEL_NO");
		maskFields.put("dlvryName",			"CUST_NAME");
		maskFields.put("dlvryTel",			"TEL_NO");
		maskFields.put("dlvryMobile",		"MOBILE_PHO");
		maskFields.put("dlvryAddr",			"ADDR");
		maskFields.put("dlvryAddrDtl",		"PASSWORD");
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
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("faxnum",			"TEL_NO");
		maskFields.put("selfIssuNum",		"SELF_ISSU_NUM");
		
		maskFields.put("usrId",				"SYSTEM_ID");
		maskFields.put("usrNm",				"CUST_NAME");
		maskFields.put("rvisnNm",			"CUST_NAME");
		
		return maskFields;
	}
	
	public List<OpenInfoVO> getOpenList(RcpListVO searchVO, Map<String, Object> paramMap) {
		
		List<OpenInfoVO> list = rcpNewMgmtMapper.getOpenList(searchVO);
		
		List<OpenInfoVO> result = (List<OpenInfoVO>)encSvc.decryptDBMSOpenInfo(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		for(OpenInfoVO item : result) {
			maskingService.setMask(item, maskFields, paramMap);
		}
		
		for( OpenInfoVO openInfoVO : list  ){
			try {
				String cstmrForeignerRrn[] = Util.getJuminNumber(openInfoVO.getCstmrForeignerRrn());
				openInfoVO.setCstmrForeignerRrn1(cstmrForeignerRrn[0]);
				openInfoVO.setCstmrForeignerRrn2(cstmrForeignerRrn[1]);

				String cstmrMobile[] = Util.getMobileNum(openInfoVO.getCstmrMobile());
				openInfoVO.setCstmrMobileFn(cstmrMobile[0]);
				openInfoVO.setCstmrMobileMn(cstmrMobile[1]);
				openInfoVO.setCstmrMobileRn(cstmrMobile[2]);

				String cstmrJuridicalRrn[] = Util.getJuminNumber(openInfoVO.getCstmrJuridicalRrn());
				openInfoVO.setCstmrJuridicalRrn1(cstmrJuridicalRrn[0]);
				openInfoVO.setCstmrJuridicalRrn2(cstmrJuridicalRrn[1]);
				
				if(openInfoVO.getCstmrMail()!=null && !openInfoVO.getCstmrMail().equals("")){
					String [] temp = (openInfoVO.getCstmrMail()).split("@");
					if(temp.length>1){//이메일 뒷자리 
						String cstmrMail[] = Util.getEmailSplit(openInfoVO.getCstmrMail());
						openInfoVO.setCstmrMail1(cstmrMail[0]);
						openInfoVO.setCstmrMail2(cstmrMail[1]);
					}
				}
				
				if(openInfoVO.getCstmrTel()!=null && !openInfoVO.getCstmrTel().equals("")){
					String[] cstmrTel = Util.getPhoneNum( openInfoVO.getCstmrTel() );
					openInfoVO.setCstmrTelFn(cstmrTel[0]);
					openInfoVO.setCstmrTelMn(cstmrTel[1]);
					openInfoVO.setCstmrTelRn(cstmrTel[2]);
				}
				
				if(openInfoVO.getDlvryTel()!=null && !openInfoVO.getDlvryTel().equals("")){
					String[] dlvryTel = Util.getPhoneNum( openInfoVO.getDlvryTel() );
					openInfoVO.setDlvryTelFn(dlvryTel[0]);
					openInfoVO.setDlvryTelMn(dlvryTel[1]);
					openInfoVO.setDlvryTelRn(dlvryTel[2]);
				}
				
				String dlvryMobile[] = Util.getMobileNum(openInfoVO.getDlvryMobile());
				openInfoVO.setDlvryMobileFn(dlvryMobile[0]);
				openInfoVO.setDlvryMobileMn(dlvryMobile[1]);
				openInfoVO.setDlvryMobileRn(dlvryMobile[2]);

				String reqAccountRrn[] = Util.getJuminNumber(openInfoVO.getReqAccountRrn());
				openInfoVO.setReqAccountRrn1(reqAccountRrn[0]);
				openInfoVO.setReqAccountRrn2(reqAccountRrn[1]);

				String[] reqCardNo = Util.getCardNumber( openInfoVO.getReqCardNo());
				openInfoVO.setReqCardNo1(reqCardNo[0]);
				openInfoVO.setReqCardNo2(reqCardNo[1]);
				openInfoVO.setReqCardNo3(reqCardNo[2]);
				openInfoVO.setReqCardNo4(reqCardNo[3]);

				String reqCardRrn[] = Util.getJuminNumber(openInfoVO.getReqCardRrn());
				openInfoVO.setReqCardRrn1(reqCardRrn[0]);
				openInfoVO.setReqCardRrn2(reqCardRrn[1]);

				String reqGuide[] = Util.getMobileNum(openInfoVO.getReqGuide());
				openInfoVO.setReqGuideFn(reqGuide[0]);
				openInfoVO.setReqGuideMn(reqGuide[1]);
				openInfoVO.setReqGuideRn(reqGuide[2]);

				String moveMobile[] = Util.getMobileNum(openInfoVO.getMoveMobile());
				openInfoVO.setMoveMobileFn(moveMobile[0]);
				openInfoVO.setMoveMobileMn(moveMobile[1]);
				openInfoVO.setMoveMobileRn(moveMobile[2]);

				String minorAgentRrn[] = Util.getJuminNumber(openInfoVO.getMinorAgentRrn());
				openInfoVO.setMinorAgentRrn1(minorAgentRrn[0]);
				openInfoVO.setMinorAgentRrn2(minorAgentRrn[1]);

				String[] minorAgentTel = Util.getMobileNum( openInfoVO.getMinorAgentTel() );
				openInfoVO.setMinorAgentTelFn(minorAgentTel[0]);
				openInfoVO.setMinorAgentTelMn(minorAgentTel[1]);
				openInfoVO.setMinorAgentTelRn(minorAgentTel[2]);

				String cstmrNativeRrn[] = Util.getJuminNumber(openInfoVO.getCstmrNativeRrn());
				openInfoVO.setCstmrNativeRrn1(cstmrNativeRrn[0]);
				openInfoVO.setCstmrNativeRrn2(cstmrNativeRrn[1]);
				
				if(openInfoVO.getFaxnum()!=null && !openInfoVO.getFaxnum().equals("")){
					String[] faxnum = Util.getPhoneNum( openInfoVO.getFaxnum() );
					openInfoVO.setFaxnum1(faxnum[0]);
					openInfoVO.setFaxnum2(faxnum[1]);
					openInfoVO.setFaxnum3(faxnum[2]);
				}
				if(StringUtils.equalsIgnoreCase(openInfoVO.getCstmrType(), "FN")){
					openInfoVO.setCstmrGenderStr(StringUtils.substring(cstmrForeignerRrn[1], 0, 1));
					openInfoVO.setBirthDay(StringUtils.defaultIfBlank(openInfoVO.getCstmrForeignerBirth2(), cstmrForeignerRrn[0]));
				}else{
					openInfoVO.setCstmrGenderStr(StringUtils.substring(cstmrNativeRrn[1], 0, 1));
					openInfoVO.setBirthDay(cstmrNativeRrn[0]);
				}
				
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		return result;
	}
	
	public RcpNewMgmtVO getUserInfo(RcpDetailVO searchVO){
		return rcpNewMgmtMapper.getUserInfo(searchVO);
	}
	
	public List<EgovMap> getRcpNewMgmtList(RcpListVO searchVO, Map<String, Object> paramMap) {
		
		logger.debug("신청 리스트 조회 시작 ========================");
		logger.debug("searchVO================" + searchVO);
		
		// 서비스계약번호로 계약번호 추출
		if("2".equals(searchVO.getpSearchGbn())){
			String pContractNum = rcpNewMgmtMapper.getpContractNum(searchVO.getpSearchName());	

			if(pContractNum != null && !"".equals(pContractNum)){
				searchVO.setpSearchName(pContractNum);
			}
		}
		
		List<EgovMap> list = rcpNewMgmtMapper.rcpNewMgmtList(searchVO);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		for( EgovMap map : result  ){
			try {
				String ssn[] = Util.getJuminNumber((String)map.get("ssn"));
				
				if("JP".equals((String) map.get("cstmrType"))) {
					map.put("cstmrGenderStr", "법인");
					map.put("birthDay", "");
				} else {
					map.put("cstmrGenderStr", StringUtils.substring(ssn[1], 0, 1));
					map.put("birthDay", StringUtils.substring(ssn[0], 0, 6));
				}
				
				map.remove("cstmrNativeRrn");
				map.remove("cstmrForeignerRrn");
				map.remove("ssn");
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateRcpNewDetail(RcpDetailVO vo) throws MvnoServiceException{
		
		//v2017.05 렌탈 오프라인 신청
		if("02".equals(vo.getProdType()) && "UU".equals(vo.getReqBuyType()) && "1".equals(vo.getOnOffType())){
			vo.setReqBuyType("MM");
			vo.setPrdtId(vo.getRntlPrdtId());
			vo.setModelId(vo.getRntlPrdtId());
			vo.setReqModelName(vo.getRntlPrdtCode());
			vo.setRealMdlInstamt("0");
			vo.setModelInstallment("0");
		}
		
		boolean isNew = false;
		
		if(StringUtils.isEmpty(vo.getRequestKey())){
			isNew = true;
			/* 개발팀 문의 후 주석처리 - 20220823
			if("04".equals(vo.getProdType())){ // 상품구분 자급제로는 신청서 작성 불가
				throw new MvnoServiceException("자급제는 선택할 수 없습니다.");
			} */
			RcpDetailVO keyMap = rcpMgmtMapper.maxRequestkey();
			
			vo.setRequestKey(keyMap.getRequestKey());
			vo.setResNo(keyMap.getResNo());

			// 2024-03-26 K-note 서식지는 넘겨받은 판매점코드 그대로 셋팅
			/*
			if (!"K".equals(vo.getFaxyn())) {
				vo.setShopCd(vo.getSessionUserOrgnId());
			}
			*/

			//2015-02-27 cntpntshopid가 없을시 리턴
			if(vo.getCntpntShopId()==null || "".equals(vo.getCntpntShopId())){
				throw new MvnoServiceException("대리점 정보가 존재하지 않습니다.");
			}

            if (!"Y".equals(vo.getPersonalInfoCollectAgree())) {
                if ("Y".equals(vo.getClausePriAdFlag())) {
                    throw new MvnoRunException(-1, "{개인정보 처리 위탁 및 혜택 제공 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의}에 동의하여야 합니다.");
                }
            }

			if(!"Y".equals(vo.getPersonalInfoCollectAgree()) || !"Y".equals(vo.getClausePriAdFlag())){
				if("Y".equals(vo.getOthersTrnsAgree())){
					throw new MvnoRunException(-1, "{혜택 제공을 위한 제 3자 제공 동의 : M모바일}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
				}
				if("Y".equals(vo.getOthersTrnsKtAgree())){
					throw new MvnoRunException(-1, "{혜택 제공을 위한 제 3자 제공 동의 : KT}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
				}
				if("Y".equals(vo.getOthersAdReceiveAgree())){
					throw new MvnoRunException(-1, "{제 3자 제공관련 광고 수신 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
				}
			}
			
			EgovMap shopMap = rcpMgmtMapper.getShopCd(vo.getCntpntShopId());
			
			vo.setCntpntShopId((String)shopMap.get("orgnId"));
			vo.setAgentCode((String)shopMap.get("agentCd"));
			vo.setManagerCode("M0001");
		}
		
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
		
		//유심종류 ESIM이 아닌 경우 세팅
		if(!StringUtils.equals(vo.getUsimKindsCd(), "09")){
			vo.setEid(null);
			vo.setImei1(null);
			vo.setImei2(null);
		}
		//유심단독구매이면서 유심종류가 Esim 의 경우 단말일련번호 세팅
		if("UU".equals(vo.getReqBuyType()) && "09".equals(vo.getUsimKindsCd())){
			vo.setReqPhoneSn(vo.getIntmSrlNo());
		}
		//진행상태가 접수인 경우 유심일련번호로 유심사용가능여부 체크
		if("00".equals(vo.getRequestStateCode())  &&  vo.getReqUsimSn() !=  null &&  !"".equals( vo.getReqUsimSn())){
			 if(rcpMgmtMapper.getImpoUsimNo(vo) > 0) {
				 throw new MvnoServiceException("개통불가 USIM으로 등록된 USIM일련번호 입니다.");
			 }
		}
		
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
				logger.error(e);
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
		
		RcpDetailVO vo2 = new RcpDetailVO();
		
		try {
			if(!isNew){
				if(vo.getRequestKey()==null || "".equals(vo.getRequestKey())){
					logger.debug("requestKey 미존재");
					throw new MvnoServiceException("가입신청 등록/수정 실패");
				}
				RcpDetailVO orgData = rcpMgmtMapper.selectRequest(vo.getRequestKey());
				encSvc.decryptDBMSRcpDetailVO(orgData);
				
				RcpDetailVO decVo = new RcpDetailVO();
				BeanUtils.copyProperties(decVo, orgData);
				
				HashMap<String, String> maskFields = getMaskFields();
				
				Map<String, Object> pReqParam = new HashMap<String, Object>();
				pReqParam.put("SESSION_USER_ID", vo.getSessionUserId());
				logger.debug("pReqParam = " + pReqParam);
				
				maskingService.setMask(decVo, maskFields, pReqParam);
				
				// 고객전화번호
				if(!"".equals(StringUtils.replace(decVo.getCstmrTel(), "-", ""))) {
					String cstmrTel[] = Util.getTelSplit(decVo.getCstmrTel());
					decVo.setCstmrTelFn(cstmrTel[0]);
					decVo.setCstmrTelMn(cstmrTel[1]);
					decVo.setCstmrTelRn(cstmrTel[2]);
				}
				
				// 고객휴대폰번호
				if(!"".equals(StringUtils.replace(decVo.getCstmrMobile(), "-", ""))) {
					String cstmrMobile[] = Util.getTelSplit(decVo.getCstmrMobile());
					decVo.setCstmrMobileFn(cstmrMobile[0]);
					decVo.setCstmrMobileMn(cstmrMobile[1]);
					decVo.setCstmrMobileRn(cstmrMobile[2]);
				}
				
				// 안면인증휴대폰번호
				if(!"".equals(StringUtils.replace(decVo.getFathMobile(), "-", ""))) {
					String fathMobile[] = Util.getTelSplit(decVo.getFathMobile());
					decVo.setFathMobileFn(fathMobile[0]);
					decVo.setFathMobileMn(fathMobile[1]);
					decVo.setFathMobileRn(fathMobile[2]);
				}
				
				// 배송전화번호
				if(!"".equals(StringUtils.replace(decVo.getDlvryTel(), "-", ""))) {
					String dlvryTel[] = Util.getTelSplit(decVo.getDlvryTel());
					decVo.setDlvryTelFn(dlvryTel[0]);
					decVo.setDlvryTelMn(dlvryTel[1]);
					decVo.setDlvryTelRn(dlvryTel[2]);
				}
				
				// 배송휴대폰번호
				if(!"".equals(StringUtils.replace(decVo.getDlvryMobile(), "-", ""))) {
					String dlvryMobile[] = Util.getTelSplit(decVo.getDlvryMobile());
					decVo.setDlvryMobileFn(dlvryMobile[0]);
					decVo.setDlvryMobileMn(dlvryMobile[1]);
					decVo.setDlvryMobileRn(dlvryMobile[2]);
				}
				
				// 번호이동전화번호
				if(!"".equals(StringUtils.replace(decVo.getMoveMobile(), "-", ""))) {
					String moveMobile[] = Util.getTelSplit(decVo.getMoveMobile());
					decVo.setMoveMobileFn(moveMobile[0]);
					decVo.setMoveMobileMn(moveMobile[1]);
					decVo.setMoveMobileRn(moveMobile[2]);
				}
				
				// 번호안내서비스번호
				if(!"".equals(StringUtils.replace(decVo.getReqGuide(), "-", ""))) {
					String reqGuide[] = Util.getTelSplit(decVo.getReqGuide());
					decVo.setReqGuideFn(reqGuide[0]);
					decVo.setReqGuideMn(reqGuide[1]);
					decVo.setReqGuideRn(reqGuide[2]);
				}
				
				// 법정대리인전화번호
				if(!"".equals(StringUtils.replace(decVo.getMinorAgentTel(), "-", ""))) {
					String minorAgentTel[] = Util.getTelSplit(decVo.getMinorAgentTel());
					decVo.setMinorAgentTelFn(minorAgentTel[0]);
					decVo.setMinorAgentTelMn(minorAgentTel[1]);
					decVo.setMinorAgentTelRn(minorAgentTel[2]);
				}
				
				// 법인전화번호
				if(!"".equals(StringUtils.replace(decVo.getJrdclAgentTel(), "-", ""))) {
					String jrdclAgentTel[] = Util.getTelSplit(decVo.getJrdclAgentTel());
					decVo.setJrdclAgentTelFn(jrdclAgentTel[0]);
					decVo.setJrdclAgentTelMn(jrdclAgentTel[1]);
					decVo.setJrdclAgentTelRn(jrdclAgentTel[2]);
				}

				if( StringUtils.equals(decVo.getFaxnum()           , vo.getFaxnum()           ) ){ vo.setFaxnum(orgData.getFaxnum()); }
				if( StringUtils.equals(decVo.getReqPhoneSn()       , vo.getReqPhoneSn()       ) ){ vo.setReqPhoneSn(orgData.getReqPhoneSn()); }
				if( StringUtils.equals(decVo.getReqUsimSn()        , vo.getReqUsimSn()        ) ){ vo.setReqUsimSn( orgData.getReqUsimSn()        );         }
				if( StringUtils.equals(decVo.getCstmrForeignerPn() , vo.getCstmrForeignerPn() ) ){ vo.setCstmrForeignerPn( orgData.getCstmrForeignerPn() );  }
				if( StringUtils.equals(decVo.getCstmrForeignerRrn(), vo.getCstmrForeignerRrn()) ){ vo.setCstmrForeignerRrn( orgData.getCstmrForeignerRrn()); }
				if( StringUtils.equals(decVo.getCstmrJuridicalRrn(), vo.getCstmrJuridicalRrn()) ){ vo.setCstmrJuridicalRrn( orgData.getCstmrJuridicalRrn()); }
				if( StringUtils.equals(decVo.getCstmrTelFn()       , vo.getCstmrTelFn()       ) ){ vo.setCstmrTelFn( orgData.getCstmrTelFn()       );        }
				if( StringUtils.equals(decVo.getCstmrTelMn()       , vo.getCstmrTelMn()       ) ){ vo.setCstmrTelMn( orgData.getCstmrTelMn()       );        }
				if( StringUtils.equals(decVo.getCstmrTelRn()       , vo.getCstmrTelRn()       ) ){ vo.setCstmrTelRn( orgData.getCstmrTelRn()       );        }
				if( StringUtils.equals(decVo.getCstmrMobileFn()    , vo.getCstmrMobileFn()    ) ){ vo.setCstmrMobileFn( orgData.getCstmrMobileFn()    );     }
				if( StringUtils.equals(decVo.getCstmrMobileMn()    , vo.getCstmrMobileMn()    ) ){ vo.setCstmrMobileMn( orgData.getCstmrMobileMn()    );     }
				if( StringUtils.equals(decVo.getCstmrMobileRn()    , vo.getCstmrMobileRn()    ) ){ vo.setCstmrMobileRn( orgData.getCstmrMobileRn()    );     }
				if( StringUtils.equals(decVo.getFathMobileFn()     , vo.getFathMobileFn()     ) ){ vo.setFathMobileFn( orgData.getFathMobileFn()    );     }
				if( StringUtils.equals(decVo.getFathMobileMn()     , vo.getFathMobileMn()     ) ){ vo.setFathMobileMn( orgData.getFathMobileMn()    );     }
				if( StringUtils.equals(decVo.getFathMobileRn()     , vo.getFathMobileRn()     ) ){ vo.setFathMobileRn( orgData.getFathMobileRn()    );     }
				if( StringUtils.equals(decVo.getCstmrAddr()        , vo.getCstmrAddr()        ) ){ vo.setCstmrAddr( orgData.getCstmrAddr()        );         }
				if( StringUtils.equals(decVo.getCstmrMail()        , vo.getCstmrMail()        ) ){ vo.setCstmrMail( orgData.getCstmrMail()        );         }
				if( StringUtils.equals(decVo.getCstmrName()        , vo.getCstmrName()        ) ){ vo.setCstmrName( orgData.getCstmrName()        );         }
				if( StringUtils.equals(decVo.getCstmrNativeRrn()   , vo.getCstmrNativeRrn()   ) ){ vo.setCstmrNativeRrn( orgData.getCstmrNativeRrn()   );    }
				if( StringUtils.equals(decVo.getDlvryName()        , vo.getDlvryName()        ) ){ vo.setDlvryName( orgData.getDlvryName()        );         }
				if( StringUtils.equals(decVo.getDlvryTelFn()       , vo.getDlvryTelFn()       ) ){ vo.setDlvryTelFn( orgData.getDlvryTelFn()       );        }
				if( StringUtils.equals(decVo.getDlvryTelMn()       , vo.getDlvryTelMn()       ) ){ vo.setDlvryTelMn( orgData.getDlvryTelMn()       );        }
				if( StringUtils.equals(decVo.getDlvryTelRn()       , vo.getDlvryTelRn()       ) ){ vo.setDlvryTelRn( orgData.getDlvryTelRn()       );        }
				if( StringUtils.equals(decVo.getDlvryMobileFn()    , vo.getDlvryMobileFn()    ) ){ vo.setDlvryMobileFn( orgData.getDlvryMobileFn()    );     }
				if( StringUtils.equals(decVo.getDlvryMobileMn()    , vo.getDlvryMobileMn()    ) ){ vo.setDlvryMobileMn( orgData.getDlvryMobileMn()    );     }
				if( StringUtils.equals(decVo.getDlvryMobileRn()    , vo.getDlvryMobileRn()    ) ){ vo.setDlvryMobileRn( orgData.getDlvryMobileRn()    );     }
				if( StringUtils.equals(decVo.getDlvryAddr()        , vo.getDlvryAddr()        ) ){ vo.setDlvryAddr( orgData.getDlvryAddr()        );         }
				if( StringUtils.equals(decVo.getDlvryAddrDtl()     , vo.getDlvryAddrDtl()     ) ){ vo.setDlvryAddrDtl( orgData.getDlvryAddrDtl()     );      }
				if( StringUtils.equals(decVo.getReqAccountName()   , vo.getReqAccountName()   ) ){ vo.setReqAccountName( orgData.getReqAccountName()   );    }
				if( StringUtils.equals(decVo.getReqAccountRrn()    , vo.getReqAccountRrn()    ) ){ vo.setReqAccountRrn( orgData.getReqAccountRrn()    );     }
				if( StringUtils.equals(decVo.getReqAccountNumber() , vo.getReqAccountNumber() ) ){ vo.setReqAccountNumber( orgData.getReqAccountNumber() );  }
				if( StringUtils.equals(decVo.getReqCardName()      , vo.getReqCardName()      ) ){ vo.setReqCardName( orgData.getReqCardName()      );       }
				if( StringUtils.equals(decVo.getReqCardRrn()       , vo.getReqCardRrn()       ) ){ vo.setReqCardRrn( orgData.getReqCardRrn()       );        }
				if( StringUtils.equals(decVo.getReqCardNo()        , vo.getReqCardNo()        ) ){ vo.setReqCardNo( orgData.getReqCardNo()        );         }
				if( StringUtils.equals(decVo.getReqCardYy()        , vo.getReqCardYy()        ) ){ vo.setReqCardYy( orgData.getReqCardYy()        );         }
				if( StringUtils.equals(decVo.getReqCardMm()        , vo.getReqCardMm()        ) ){ vo.setReqCardMm( orgData.getReqCardMm()        );         }
				if( StringUtils.equals(decVo.getMoveMobileFn()     , vo.getMoveMobileFn()     ) ){ vo.setMoveMobileFn( orgData.getMoveMobileFn()     );      }
				if( StringUtils.equals(decVo.getMoveMobileMn()     , vo.getMoveMobileMn()     ) ){ vo.setMoveMobileMn( orgData.getMoveMobileMn()     );      }
				if( StringUtils.equals(decVo.getMoveMobileRn()     , vo.getMoveMobileRn()     ) ){ vo.setMoveMobileRn( orgData.getMoveMobileRn()     );      }
				if( StringUtils.equals(decVo.getReqGuideFn()       , vo.getReqGuideFn()       ) ){ vo.setReqGuideFn( orgData.getReqGuideFn()       );        }
				if( StringUtils.equals(decVo.getReqGuideRn()       , vo.getReqGuideRn()       ) ){ vo.setReqGuideRn( orgData.getReqGuideRn()       );        }
				if( StringUtils.equals(decVo.getReqGuideMn()       , vo.getReqGuideMn()       ) ){ vo.setReqGuideMn( orgData.getReqGuideMn()       );        }
				if( StringUtils.equals(decVo.getMinorAgentName()   , vo.getMinorAgentName()   ) ){ vo.setMinorAgentName( orgData.getMinorAgentName()   );    }
				if( StringUtils.equals(decVo.getMinorAgentRrn()    , vo.getMinorAgentRrn()    ) ){ vo.setMinorAgentRrn( orgData.getMinorAgentRrn()    );     }
				if( StringUtils.equals(decVo.getMinorAgentTelFn()  , vo.getMinorAgentTelFn()  ) ){ vo.setMinorAgentTelFn( orgData.getMinorAgentTelFn()  );   }
				if( StringUtils.equals(decVo.getMinorAgentTelMn()  , vo.getMinorAgentTelMn()  ) ){ vo.setMinorAgentTelMn( orgData.getMinorAgentTelMn()  );   }
				if( StringUtils.equals(decVo.getMinorAgentTelRn()  , vo.getMinorAgentTelRn()  ) ){ vo.setMinorAgentTelRn( orgData.getMinorAgentTelRn()  );   }
				if( StringUtils.equals(decVo.getJrdclAgentRrn()    , vo.getJrdclAgentRrn()    ) ){ vo.setJrdclAgentRrn( orgData.getJrdclAgentRrn()    );     }
				if( StringUtils.equals(decVo.getJrdclAgentTelFn()  , vo.getJrdclAgentTelFn()  ) ){ vo.setJrdclAgentTelFn( orgData.getJrdclAgentTelFn()  );   }
				if( StringUtils.equals(decVo.getJrdclAgentTelMn()  , vo.getJrdclAgentTelMn()  ) ){ vo.setJrdclAgentTelMn( orgData.getJrdclAgentTelMn()  );   }
				if( StringUtils.equals(decVo.getJrdclAgentTelRn()  , vo.getJrdclAgentTelRn()  ) ){ vo.setJrdclAgentTelRn( orgData.getJrdclAgentTelRn()  );   }
				if( StringUtils.equals(decVo.getJrdclAgentTelRn()  , vo.getJrdclAgentTelRn()  ) ){ vo.setJrdclAgentTelRn( orgData.getJrdclAgentTelRn()  );   }
				if( StringUtils.equals(decVo.getSelfIssuNum()      , vo.getSelfIssuNum()      ) ){ vo.setSelfIssuNum( orgData.getSelfIssuNum()  );   }
				if( StringUtils.equals(decVo.getMinorSelfIssuNum() , vo.getMinorSelfIssuNum() ) ){ vo.setMinorSelfIssuNum( orgData.getMinorSelfIssuNum()  );   }
				if( StringUtils.equals(decVo.getCstmrJejuId() 	   , vo.getCstmrJejuId() 	  ) ){ vo.setCstmrJejuId( orgData.getCstmrJejuId()  );           }
				if( StringUtils.equals(decVo.getClauseJehuFlag()   , vo.getClauseJehuFlag()   ) ){ vo.setClauseJehuFlag( orgData.getClauseJehuFlag()  );     }
				if( StringUtils.equals(decVo.getClauseFinanceFlag(), vo.getClauseFinanceFlag()) ){ vo.setClauseFinanceFlag( orgData.getClauseFinanceFlag()); }
				if( StringUtils.equals(decVo.getVisaCd()           , vo.getVisaCd()           ) ){ vo.setVisaCd( orgData.getVisaCd()                      ); }

                //2026.03 빈문자열이 암호화되는것을 막기위해 null처리(vo는 공용이기 때문에 암호화 관련일 때에만 null처리함)
                if("".equals(vo.getCstmrNativeRrn())) {vo.setCstmrNativeRrn(null);}
                if("".equals(vo.getReqAccountNumber())) {vo.setReqAccountNumber(null);}
                if("".equals(vo.getReqCardNo())) {vo.setReqCardNo(null);}
                if("".equals(vo.getMinorAgentRrn())) {vo.setMinorAgentRrn(null);}
                if("".equals(vo.getOthersPaymentRrn())) {vo.setOthersPaymentRrn(null);}
                if("".equals(vo.getJrdclAgentRrn())) {vo.setJrdclAgentRrn(null);}
                if("".equals(vo.getEntrustResRrn())) {vo.setEntrustResRrn(null);}
                if("".equals(vo.getReqAccountRrn())) {vo.setReqAccountRrn(null);}
                if("".equals(vo.getReqCardRrn())) {vo.setReqCardRrn(null);}
                if("".equals(vo.getCstmrForeignerRrn())) {vo.setCstmrForeignerRrn(null);}
                if("".equals(vo.getCstmrForeignerPn())) {vo.setCstmrForeignerPn(null);}
                if("".equals(vo.getSelfIssuNum())) {vo.setSelfIssuNum(null);}
                if("".equals(vo.getMinorSelfIssuNum())) {vo.setMinorSelfIssuNum(null);}
			}

			vo2 = encSvc.encryptDBMSRcpDetailVO(vo);
            
			if(isNew) {
				updateRcpRequestCnt = rcpMgmtMapper.insertRcpRequest(vo2);
				rcpMgmtMapper.insertRcpCustomer(vo2);
				rcpMgmtMapper.insertRcpSale(vo2);
				rcpMgmtMapper.insertRcpDelivery(vo2);
				rcpMgmtMapper.insertRcpReq(vo2);
				this.insertPenaltyHist(vo2); // 위약금 이력 INSERT
				rcpMgmtMapper.insertRcpMove(vo2);
				rcpMgmtMapper.insertRcpPay(vo2);
				rcpMgmtMapper.insertRcpAgent(vo2);
				
				//시퀸스 선 조회 후 값..
				int stateKey = rcpMgmtMapper.getStateKey(vo2);
				vo2.setStatusKey(stateKey);
				
				rcpMgmtMapper.insertRcpState(vo2);
				
				rcpMgmtMapper.insertRcpChange(vo2);
				rcpMgmtMapper.insertRcpDvcChg(vo2);
				
				//20231016 KT 인터넷 ID 추가
				if(vo2.getKtInterSvcNo()!=null && StringUtils.isNotEmpty(vo2.getKtInterSvcNo()) && !vo2.getKtInterSvcNo().equals("null")){
					rcpMgmtMapper.insertRcpKtInter(vo2);
				}


				
				if( !"".equals(vo2.getCntpntShopId()))
				{
					int cnt = rcpMgmtMapper.selectMsgCnt(vo2.getCntpntShopId());
					
					//문자메시지 전송
					if( cnt > 0)
					{
						//번호 nullnullnull 체크
						String ctn = "";
						if(vo2.getCstmrMobileFn() !=null && StringUtils.isNotEmpty(vo2.getCstmrMobileFn()) && !vo2.getCstmrMobileFn().equals("null")){
							ctn = vo2.getCstmrMobileFn() + vo2.getCstmrMobileMn() + vo2.getCstmrMobileRn();
						}else if(vo2.getCstmrTelFn() !=null && StringUtils.isNotEmpty(vo2.getCstmrTelFn()) && !vo2.getCstmrTelFn().equals("null")){
							ctn = vo2.getCstmrTelFn() + vo2.getCstmrTelMn() + vo2.getCstmrTelRn();
						}
						if(ctn.matches("^\\d{11}$")){
							String templateId = "6";
							// SMS 템플릿 제목,내용 가져오기
							KtMsgQueueReqVO msgVO = smsMgmtMapper.getKtTemplateText(templateId);
							msgVO.setMessage((msgVO.getTemplateText()).replaceAll(Pattern.quote("#{resNo}"), vo2.getResNo()));
							msgVO.setRcptData(ctn);
							msgVO.setTemplateId(templateId);
							msgVO.setReserved01("MSP");
							msgVO.setReserved02(templateId);
							msgVO.setReserved03(vo2.getSessionUserId());
							
							// SMS 저장
							smsMgmtMapper.insertKtMsgTmpQueue(msgVO);
							
							SmsSendVO sendVO = new SmsSendVO();
							sendVO.setTemplateId(templateId);
							sendVO.setMsgId(msgVO.getMsgId());
							sendVO.setSendReqDttm(msgVO.getScheduleTime());
							sendVO.setReqId(vo2.getSessionUserId());
							
							// 발송내역 저장
							smsMgmtMapper.insertSmsSendMst(sendVO);
						}
					}
				}
				
			} else {
				updateRcpRequestCnt = rcpMgmtMapper.updateRcpRequest(vo2);
				rcpMgmtMapper.updateRcpCustomer(vo2);
				rcpMgmtMapper.updateRcpSale(vo2);
				rcpMgmtMapper.updateRcpDelivery(vo2);
				rcpMgmtMapper.updateRcpReq(vo2);
				this.insertPenaltyHist(vo2); // 위약금 이력 INSERT
				rcpMgmtMapper.updateRcpMove(vo2);
				rcpMgmtMapper.updateRcpPay(vo2);
				rcpMgmtMapper.updateRcpAgent(vo2);
				rcpMgmtMapper.updateRcpState(vo2);
				rcpMgmtMapper.updateRcpChange(vo2);
				rcpMgmtMapper.updateRcpDvcChg(vo2);
				
				//20231016 KT 인터넷 ID 추가
				int cnt = rcpMgmtMapper.selectKtInter(vo2.getResNo());
				if(cnt > 0){
					rcpMgmtMapper.updateRcpKtInter(vo2);
				}else{
					if(vo2.getKtInterSvcNo()!=null && StringUtils.isNotEmpty(vo2.getKtInterSvcNo()) && !vo2.getKtInterSvcNo().equals("null")){
						rcpMgmtMapper.insertRcpKtInter(vo2);
					}
					
				}
			}

			//20240307 K-Note 추가
			if("K".equals(vo2.getFaxyn())){
				rcpMgmtMapper.updateKisKnoteScanInfo(vo2);
			}


			if("NAC3".equals(vo2.getOperType()) || "MNP3".equals(vo2.getOperType())){
				rcpMgmtMapper.deleteRcpAddition(vo2);
				
				/* v2017.02 신청관리 간소화 부가서비스 필수 항목 제외로 null인경우 insert 제외 */
				if( vo2.getAdditionKey() != null && !"".equals(vo2.getAdditionKey()) ){ 
					rcpMgmtMapper.insertRcpAddition(vo2);
				}
				
			}
			
			//v2018.02 기기변경 시 고객 헤택정보 추가
			if("HCN3".equals(vo2.getOperType()) || "HDN3".equals(vo2.getOperType())){
				rcpNewMgmtMapper.deleteRcpBenefit(vo2);
				
				if( vo2.getBnftKey() != null && !"".equals(vo2.getBnftKey()) ){
					rcpNewMgmtMapper.insertRcpBenefit(vo2);
				}
			}

			//신청서 상세 현행화
			RcpDetailVO dtlVo = rcpMgmtMapper.getMcpReqDtl(vo2.getRequestKey());
			if (dtlVo != null) {
				if (rcpMgmtMapper.chkMcpReqDtl(vo2.getRequestKey()) > 0) {
					rcpMgmtMapper.updateMcpReqDtl(dtlVo);
				} else {
					rcpMgmtMapper.insertMcpReqDtl(dtlVo);
				}
			}
			
		} catch (Exception e) {
			updateRcpRequestCnt = 0;
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		}
		
		return updateRcpRequestCnt;
	}

	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> insertRcpNewRequest(RcpDetailVO vo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		boolean isNew = false;
		
		// 신규인 경우
		if(StringUtils.isEmpty(vo.getRequestKey())){
			isNew = true;
			
			RcpDetailVO keyMap = rcpMgmtMapper.maxRequestkey();
			
			vo.setRequestKey(keyMap.getRequestKey());
			vo.setResNo(keyMap.getResNo());
			
			if(vo.getCntpntShopId()==null || "".equals(vo.getCntpntShopId())){
				throw new MvnoRunException(-1, "가입신청 등록/수정 실패");
			}

            if (!"Y".equals(vo.getPersonalInfoCollectAgree())) {
                if("Y".equals(vo.getClausePriAdFlag())){
                    throw new MvnoRunException(-1, "{개인정보 처리 위탁 및 혜택 제공 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의}에 동의하여야 합니다.");
                }
            }

			if(!"Y".equals(vo.getPersonalInfoCollectAgree()) || !"Y".equals(vo.getClausePriAdFlag())){
				if("Y".equals(vo.getOthersTrnsAgree())){
					throw new MvnoRunException(-1, "{혜택 제공을 위한 제 3자 제공 동의 : M모바일}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
				}
				if("Y".equals(vo.getOthersTrnsKtAgree())){
					throw new MvnoRunException(-1, "{혜택 제공을 위한 제 3자 제공 동의 : KT}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
				}
				if("Y".equals(vo.getOthersAdReceiveAgree())){
					throw new MvnoRunException(-1, "{제 3자 제공관련 광고 수신 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
				}
			}

			EgovMap shopMap = rcpMgmtMapper.getShopCd(vo.getCntpntShopId());
			
			vo.setCntpntShopId((String)shopMap.get("orgnId"));
			vo.setAgentCode((String)shopMap.get("agentCd"));
			vo.setManagerCode("M0001");
		}
		
		// 납부방법 세팅
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
		
		//유심종류 ESIM이 아닌 경우 세팅
		if(!StringUtils.equals(vo.getUsimKindsCd(), "09")){
			vo.setEid(null);
			vo.setImei1(null);
			vo.setImei2(null);
		}
		
		// 메일세팅
		if(StringUtils.isNotBlank(vo.getCstmrMail1()) && StringUtils.isNotBlank(vo.getCstmrMail2())){
			vo.setCstmrMail(vo.getCstmrMail1()+"@"+vo.getCstmrMail2());
		}
		
		// 주민번호
		if(StringUtils.isNotBlank(vo.getCstmrNativeRrn1()) && StringUtils.isNotBlank(vo.getCstmrNativeRrn2())){
			vo.setCstmrNativeRrn(vo.getCstmrNativeRrn1() + vo.getCstmrNativeRrn2());
		}
		
		// 외국인번호
		if(StringUtils.isNotBlank(vo.getCstmrForeignerRrn1()) && StringUtils.isNotBlank(vo.getCstmrForeignerRrn2())){
			vo.setCstmrForeignerRrn(vo.getCstmrForeignerRrn1() + vo.getCstmrForeignerRrn2());
		}
		
		// 카드정보
		if(StringUtils.isNotBlank(vo.getReqCardNo1()) && StringUtils.isNotBlank(vo.getReqCardNo2()) && StringUtils.isNotBlank(vo.getReqCardNo3()) && StringUtils.isNotBlank(vo.getReqCardNo4())){
			vo.setReqCardNo(vo.getReqCardNo1() + vo.getReqCardNo2() + vo.getReqCardNo3() + vo.getReqCardNo4());
		}
		
		// 법정대리인
		if(StringUtils.isNotBlank(vo.getMinorAgentRrn1()) && StringUtils.isNotBlank(vo.getMinorAgentRrn2())){
			vo.setMinorAgentRrn(vo.getMinorAgentRrn1() + vo.getMinorAgentRrn2());
		}
		
		// 법인정보
		if(StringUtils.isNotBlank(vo.getJrdclAgentRrn1()) && StringUtils.isNotBlank(vo.getJrdclAgentRrn2())){
			vo.setJrdclAgentRrn(vo.getJrdclAgentRrn1() + vo.getJrdclAgentRrn2());
		}
		
		// 계좌 명의자 부빈번호
		if(StringUtils.isNotBlank(vo.getReqAccountRrn1()) && StringUtils.isNotBlank(vo.getReqAccountRrn2())){
			vo.setReqAccountRrn(vo.getReqAccountRrn1() + vo.getReqAccountRrn2());
		}
			
		// 카드 명의자 주민번호
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
		
		// 무선데이터
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
		
		// 타인납부
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
				logger.error(e);
			}
			if(mp > 0){
				vo.setModelPriceVat(String.valueOf( mp * 0.1 ));
			}
		}else{
			vo.setModelPriceVat("0");
		}
		
		try {

            //2026.03 빈문자열이 암호화되는것을 막기위해 null처리(vo는 공용이기 때문에 암호화 관련일 때에만 null처리함)
            if("".equals(vo.getCstmrNativeRrn())) {vo.setCstmrNativeRrn(null);}
            if("".equals(vo.getReqAccountNumber())) {vo.setReqAccountNumber(null);}
            if("".equals(vo.getReqCardNo())) {vo.setReqCardNo(null);}
            if("".equals(vo.getMinorAgentRrn())) {vo.setMinorAgentRrn(null);}
            if("".equals(vo.getOthersPaymentRrn())) {vo.setOthersPaymentRrn(null);}
            if("".equals(vo.getJrdclAgentRrn())) {vo.setJrdclAgentRrn(null);}
            if("".equals(vo.getEntrustResRrn())) {vo.setEntrustResRrn(null);}
            if("".equals(vo.getReqAccountRrn())) {vo.setReqAccountRrn(null);}
            if("".equals(vo.getReqCardRrn())) {vo.setReqCardRrn(null);}
            if("".equals(vo.getCstmrForeignerRrn())) {vo.setCstmrForeignerRrn(null);}
            if("".equals(vo.getCstmrForeignerPn())) {vo.setCstmrForeignerPn(null);}
            if("".equals(vo.getSelfIssuNum())) {vo.setSelfIssuNum(null);}
            if("".equals(vo.getMinorSelfIssuNum())) {vo.setMinorSelfIssuNum(null);}

			RcpDetailVO vo2 = encSvc.encryptDBMSRcpDetailVO(vo);
			
			logger.debug("vo2=" + vo2);
			
			if(isNew){
				// SMS인증정보 암호화
				// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
				/*if("S".equals(vo2.getOnlineAuthType()) && "4".equals(vo2.getOnOffType())){
					vo2.setOnlineAuthInfo(EncryptUtil.ace256Enc(vo2.getSmsAuthInfo()));
				}*/
				// 기기변경시 인증유형 체크
				if("HCN3".equals(vo2.getOperType()) || "HDN3".equals(vo2.getOperType())) {
					// OTP 인증 및 인증방법이 "SMS인증"인 경우
					if((vo2.getOtpCheckYN() != null && "Y".equals(vo2.getOtpCheckYN())) && "S".equals(vo2.getOnlineAuthType()) && "4".equals(vo2.getOnOffType())){
						if(vo2.getSmsAuthInfo() == null || "".equals(vo2.getSmsAuthInfo())) {
							throw new MvnoRunException(-1, "SMS인증 정보가 존재하지 않습니다.");
						}else {
							vo2.setOnlineAuthInfo(EncryptUtil.ace256Enc(vo2.getSmsAuthInfo()));
						}
					}
					// 정지 상태이고 단말분실인 경우 인증방법이 "납부방법인증"인 경우
					if((vo2.getSubStatus() != null && "S".equals(vo2.getSubStatus())) && (vo2.getLostYn() != null && "Y".equals(vo2.getLostYn()))
							&& "B".equals(vo2.getOnlineAuthType()) && "4".equals(vo2.getOnOffType())){
						if(vo2.getSmsAuthInfo() == null || "".equals(vo2.getSmsAuthInfo())) {
							throw new MvnoRunException(-1, "녹취ID 인증정보가 존재하지 않습니다.");
						}else {
							vo2.setOnlineAuthInfo(EncryptUtil.ace256Enc(vo2.getSmsAuthInfo()));
						}
					}
					
					logger.debug("onOffType=" + vo2.getOnOffType());
					logger.debug("onlineAuthType=" + vo2.getOnlineAuthType());
					logger.debug("subStatus=" + vo2.getSubStatus());
					logger.debug("otpCheckYN=" + vo2.getOtpCheckYN());
					logger.debug("lostYn=" + vo2.getLostYn());
					logger.debug("smsAuthInfo=" + vo2.getSmsAuthInfo());
					logger.debug("onlineAuthInfo=" + vo2.getOnlineAuthInfo());
				}
				
				
				// SMS인증정보 암호화
				
				rcpMgmtMapper.insertRcpRequest(vo2);
				rcpMgmtMapper.insertRcpCustomer(vo2);
				rcpMgmtMapper.insertRcpSale(vo2);
				rcpMgmtMapper.insertRcpDelivery(vo2);
				rcpMgmtMapper.insertRcpReq(vo2);
				this.insertPenaltyHist(vo2); // 위약금 이력 INSERT
				rcpMgmtMapper.insertRcpMove(vo2);
				rcpMgmtMapper.insertRcpPay(vo2);
				rcpMgmtMapper.insertRcpAgent(vo2);
				rcpMgmtMapper.insertRcpChange(vo2);
				rcpMgmtMapper.insertRcpDvcChg(vo2);
				
				if(vo2.getKtInterSvcNo()!=null && StringUtils.isNotEmpty(vo2.getKtInterSvcNo()) && !vo2.getKtInterSvcNo().equals("null")){
					rcpMgmtMapper.insertRcpKtInter(vo2);
				}
				
				//시퀸스 선 조회 후 값..
				int stateKey = rcpMgmtMapper.getStateKey(vo2);
				vo2.setStatusKey(stateKey);
				rcpMgmtMapper.insertRcpState(vo2);
				
				//서식지상태변경
				AppFormMgmtVO appVO = new AppFormMgmtVO();
				appVO.setScanId(vo2.getScanId());
				appVO.setProcStatCd("11"); //신청완료
				appVO.setSessionUserId(vo2.getSessionUserId());
				appFormMapper.updateAppFormData(appVO);
				
			}else{
				rcpMgmtMapper.updateRcpRequest(vo2);
				rcpMgmtMapper.updateRcpCustomer(vo2);
				rcpMgmtMapper.updateRcpSale(vo2);
				rcpMgmtMapper.updateRcpDelivery(vo2);
				rcpMgmtMapper.updateRcpReq(vo2);
				this.insertPenaltyHist(vo2); // 위약금 이력 INSERT
				rcpMgmtMapper.updateRcpMove(vo2);
				rcpMgmtMapper.updateRcpPay(vo2);
				rcpMgmtMapper.updateRcpAgent(vo2);
				rcpMgmtMapper.updateRcpState(vo2);
				rcpMgmtMapper.updateRcpChange(vo2);
				rcpMgmtMapper.updateRcpDvcChg(vo2);
				
				//20231016 KT 인터넷 ID 추가
				int cnt = rcpMgmtMapper.selectKtInter(vo2.getResNo());
				if(cnt > 0){
					rcpMgmtMapper.updateRcpKtInter(vo2);
				}else{
					if(vo2.getKtInterSvcNo()!=null && StringUtils.isNotEmpty(vo2.getKtInterSvcNo()) && !vo2.getKtInterSvcNo().equals("null")){
						rcpMgmtMapper.insertRcpKtInter(vo2);
					}
				}
			}
			
			// 부가서비스 변경을 위하여 삭제후 재등록
			// 기기변경인 경우 부가서비스정보 저장 안함.
			if("NAC3".equals(vo2.getOperType()) || "MNP3".equals(vo2.getOperType())){
				rcpMgmtMapper.deleteRcpAddition(vo2);
				
				/* v2017.02 신청관리 간소화 부가서비스 필수 항목 제외로 null인경우 insert 제외 */
				if( vo2.getAdditionKey() != null && !"".equals(vo2.getAdditionKey()) ){
					rcpMgmtMapper.insertRcpAddition(vo2);
				}
			}
			
			//v2018.02 기기변경 시 고객 헤택정보 추가
			if("HCN3".equals(vo2.getOperType()) || "HDN3".equals(vo2.getOperType())){
				rcpNewMgmtMapper.deleteRcpBenefit(vo2);
				
				if( vo2.getBnftKey() != null && !"".equals(vo2.getBnftKey()) ){
					rcpNewMgmtMapper.insertRcpBenefit(vo2);
				}
			}

			//신청서 상세 현행화
			RcpDetailVO dtlVo = rcpMgmtMapper.getMcpReqDtl(vo2.getRequestKey());
			if (dtlVo != null) {
				if (rcpMgmtMapper.chkMcpReqDtl(vo2.getRequestKey()) > 0) {
					rcpMgmtMapper.updateMcpReqDtl(dtlVo);
				} else {
					rcpMgmtMapper.insertMcpReqDtl(dtlVo);
				}
			}
			
			// 예약번호 리턴
			resultMap.put("resNo", vo2.getResNo());
			resultMap.put("requestKey", vo2.getRequestKey());
			
		} catch (Exception e) {
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210722 pmd소스코드수정
			logger.error("Connection Exception occurred");
		}
		
		
		logger.debug("resultMap=" + resultMap);
		
		return resultMap;
	}
	
	public List<RntlPrdtInfoVO> getRntlPrdtInfo(RntlPrdtInfoVO vo){
		
		if (vo.getBaseYm() == null || "".equals(vo.getBaseYm())) {
			throw new MvnoRunException(-1, "기준월이 존재 하지 않습니다.");
		}
		
		return rcpNewMgmtMapper.getRntlPrdtInfo(vo);
	}
	
	public List<RcpPrmtVO> getRcpPrmtPrdcList(RcpPrmtVO rcpPrmtVO){
		
		if (rcpPrmtVO.getReqInDay() == null || "".equals(rcpPrmtVO.getReqInDay())) {
			throw new MvnoRunException(-1, "신청일자가 존재 하지 않습니다.");
		}
		
		return rcpNewMgmtMapper.getRcpPrmtPrdcList(rcpPrmtVO);
	}
	
	public List<RcpPrmtVO> getRcpPrmtList(RcpPrmtVO rcpPrmtVO){
		
		if (rcpPrmtVO.getReqInDay() == null || "".equals(rcpPrmtVO.getReqInDay())) {
			throw new MvnoRunException(-1, "신청일자가 존재 하지 않습니다.");
		}
		
		if (rcpPrmtVO.getOnOffType() == null || "".equals(rcpPrmtVO.getOnOffType())) {
			throw new MvnoRunException(-1, "가입경로가 존재 하지 않습니다.");
		}
		
		if (rcpPrmtVO.getOrgnId() == null || "".equals(rcpPrmtVO.getOrgnId())) {
			throw new MvnoRunException(-1, "조직정보가 존재 하지 않습니다.");
		}
		
		return rcpNewMgmtMapper.getRcpPrmtList(rcpPrmtVO);
	}
	
	public List<RcpBnftVO> getRcpBnftList(RcpBnftVO rcpBnftVO){
		
		return rcpNewMgmtMapper.getRcpBnftList(rcpBnftVO);
	}
	
	/**
	 * 서식지 자동생성 대리점 확인
	 */
	public int getAppFormMakeCheck(RcpDetailVO vo){
		return rcpNewMgmtMapper.getAppFormMakeCheck(vo);
	}
	
	/**
	 * 국가코드 조회팝업
	 * @param rcpNatnVO
	 * @return
	 */
	public List<RcpNatnVO> getRcpNatnList(RcpNatnVO rcpNatnVO){
		
		return rcpNewMgmtMapper.getRcpNatnList(rcpNatnVO);
	}
	
	/**
	 */
	public List<?> getSaleinfo(RcpDetailVO rcpDetailVO){
		
		return rcpNewMgmtMapper.getSaleinfo(rcpDetailVO);
	}
	
	/**
	 * [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
	 * 기기변경 권한체크
	 */
	public String getDvcChgAuthYn(String sessionOrgnTypeCd) {
		return rcpNewMgmtMapper.getDvcChgAuthYn(sessionOrgnTypeCd);
	}

	/**
	 * 90일 이내 동일명의 개통회선 10회초과 체크
	 */
	public boolean chkCstmrCnt(RcpDetailVO vo) {
		
		// 주민번호
		if(StringUtils.isNotBlank(vo.getCstmrNativeRrn1()) && StringUtils.isNotBlank(vo.getCstmrNativeRrn2())){
			vo.setCstmrNativeRrn(vo.getCstmrNativeRrn1() + vo.getCstmrNativeRrn2());
		}
		// 외국인등록번호
		if(StringUtils.isNotBlank(vo.getCstmrForeignerRrn1()) && StringUtils.isNotBlank(vo.getCstmrForeignerRrn2())){
			vo.setCstmrForeignerRrn(vo.getCstmrForeignerRrn1() + vo.getCstmrForeignerRrn2());
		}
		
		if(rcpNewMgmtMapper.chkCstmrCnt(vo) >= 10 && "Y".equals(rcpNewMgmtMapper.rcpChkCodeYn())) {
			return true;
		}
		
		return false;
	}

	/** ACEN 연동대상 확인 */
	public String getAcenReqStatus(String requestKey) {
		return rcpNewMgmtMapper.getAcenReqStatus(requestKey);
	}

	/** ACEN 번호이동 위약금 입력대상 확인 */
	public String getAcenPenaltyStatus(String requestKey) {

		String status= "N";

		int searchCnt= rcpNewMgmtMapper.getAcenPenaltyStatus(requestKey);
		if(searchCnt > 0) status= "Y";

		return status;
	}

	/** 위약금 이력 INSERT */
	private void insertPenaltyHist(RcpDetailVO rcpDetailVO) {

		if(KtisUtil.isEmpty(rcpDetailVO.getRequestKey())) return;

		boolean isChange= false;

		// 기존 위약금 조회
		String prePenalty= rcpNewMgmtMapper.getMovePenalty(rcpDetailVO.getRequestKey());
		String postPenalty= rcpDetailVO.getMovePenalty();

		if(KtisUtil.isEmpty(prePenalty)){
			if(!KtisUtil.isEmpty(postPenalty)) isChange= true;
		}else{
			if(!prePenalty.equals(postPenalty)) isChange= true;
		}

		// 위약금 변경 이력 INSERT
		if(isChange) rcpNewMgmtMapper.insertPenaltyHist(rcpDetailVO);

	}

	/** 위약금 복사 이력 INSERT */
	private void insertPenaltyCopyHist(RcpDetailVO rcpDetailVO) {

		if(KtisUtil.isEmpty(rcpDetailVO.getTrgtRequestKey())) return;

		// 복사대상 신청서 위약금 조회
		String prePenalty= rcpNewMgmtMapper.getMovePenalty(rcpDetailVO.getTrgtRequestKey());

		// 위약금 변경 이력 INSERT
		if(!KtisUtil.isEmpty(prePenalty)){

			RcpDetailVO copyVO = new RcpDetailVO();
			copyVO.setRequestKey(rcpDetailVO.getRequestKey());
			copyVO.setMovePenalty(prePenalty);
			copyVO.setSessionUserId(rcpDetailVO.getSessionUserId());

			rcpNewMgmtMapper.insertPenaltyHist(copyVO);
		}
	}
	
	public List<EgovMap> getRcpMgmtPayFullList(RcpListVO searchVO, Map<String, Object> paramMap) {
		logger.debug("신청 리스트(일시납) 조회 시작 ========================");
		logger.debug("searchVO================" + searchVO);
		
		// 서비스계약번호로 계약번호 추출
		if("2".equals(searchVO.getpSearchGbn())){
			String pContractNum = rcpNewMgmtMapper.getpContractNum(searchVO.getSearchVal());	

			if(pContractNum != null && !"".equals(pContractNum)){
				searchVO.setSearchVal(pContractNum);
			}
		}
		
		List<EgovMap> list = rcpNewMgmtMapper.rcpMgmtPayFullList(searchVO);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		
		maskingService.setMask(result, maskFields, paramMap);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void rcpMgmtPayFullUpdate(RcpListVO searchVO) {
		logger.debug("신청관리(일시납) 결제상태 변경 수정 시작 ========================");
		logger.debug("searchVO================" + searchVO);
		rcpNewMgmtMapper.rcpMgmtPayFullUpdate(searchVO);
	}

	public String getJehuProdType(String rateCd) {
		return rcpNewMgmtMapper.getJehuProdType(rateCd);
	}

	public Map<String, String> checkJehuValidation(Map<String, String> paramMap) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCd", "00");

		String socCode = paramMap.get("socCode");
		String isJehuFlagChk = paramMap.get("isJehuFlagChk");

		if(KtisUtil.isEmpty(socCode)){
			return resultMap;  // 성공처리
		}

		String jehuProdType = rcpNewMgmtMapper.getJehuProdType(socCode);
		String jehuRequiredYn = KtisUtil.isEmpty(jehuProdType) ? "N" : "Y";

		if("Y".equals(jehuRequiredYn) && !"Y".equals(isJehuFlagChk)){
			resultMap.put("resultCd", "99");
			resultMap.put("resultMsg", "제휴서비스 약관에 동의해주세요.");
		}

		return resultMap;
	}


	public Map<String, String> checkPartnerValidation(Map<String, String> paramMap) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCd", "00");

		String agentCode = paramMap.get("pAgentCode");
		String isPartnerFlagChk = paramMap.get("isPartnerFlagChk");

		if(KtisUtil.isEmpty(agentCode)){
			return resultMap;  // 성공처리
		}

		String partnerOfferType = rcpNewMgmtMapper.getPartnerOfferType(agentCode);
		String partnerRequiredYn = KtisUtil.isEmpty(partnerOfferType) ? "N" : "Y";

		if("Y".equals(partnerRequiredYn) && !"Y".equals(isPartnerFlagChk)){
			resultMap.put("resultCd", "99");
			resultMap.put("resultMsg", "제휴사 제공동의 약관에 동의해주세요.");
		}

		return resultMap;
	}

	public int insertAcenReqStatus(String requestKey) {

		RcpDetailVO rcpDetailVO = rcpNewMgmtMapper.getMcpRequestInfoForAcen(requestKey);

		if(rcpDetailVO == null){
			return 0;  // Acen 대상 아님
		}

		Map<String, Object> insertParams = new HashMap<String, Object>();
		insertParams.put("requestKey", requestKey);
		insertParams.put("resNo", rcpDetailVO.getResNo());
		insertParams.put("regstId", rcpDetailVO.getRid());

		// esim인 경우 유심있음 처리
		boolean isNoSim = KtisUtil.isEmpty(rcpDetailVO.getReqUsimSn()) && !"09".equals(rcpDetailVO.getUsimKindsCd());

		if("NAC3".equals(rcpDetailVO.getOperType())){
			if(isNoSim){  // 신규 유심없음
				insertParams.put("evntGrpCd", "NEW_NOSIM");
				insertParams.put("evntType", "NEW_NOSIM_01");
			}else{  // 신규 유심있음
				insertParams.put("evntGrpCd", "NEW_SIM");
				insertParams.put("evntType", "NEW_SIM_01");
			}
		}else{
			if(isNoSim){  // 번이 유심없음
				insertParams.put("evntGrpCd", "MNP_NOSIM");
				insertParams.put("evntType", "MNP_NOSIM_01");
			}else{  // 번이 유심있음
				insertParams.put("evntGrpCd", "MNP_SIM");
				insertParams.put("evntType", "MNP_SIM_01");
			}
		}

		return rcpNewMgmtMapper.insertAcenReqStatus(insertParams);

	}

	/**
	 * 신청관리 목록 조회
	 */
	public List<EgovMap> getRcpRequestList(RcpListVO searchVO, Map<String, Object> paramMap) {

		logger.debug("신청 리스트 조회 시작 ========================");
		logger.debug("searchVO================" + searchVO);

		List<EgovMap> result = rcpNewMgmtMapper.getRcpRequestList(searchVO);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(result, maskFields, paramMap);

		return result;
	}

	/**
	 * 데이터 쉐어링 데이터 조회(공통코드)
	 */
	public List<EgovMap> getSharingList(String grpId) {
		return rcpNewMgmtMapper.getSharingList(grpId);
	}

    public String getHubOrderYn() {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("grpId", "MSP0038");
		param.put("cdVal", "HUB_ORDER_YN");
		Map<String, String> cdMap = cmnCdMgmtService.getCmnGrpCdMst(param);
		if (cdMap == null) {
			return "N";
		}

		return cdMap.get("USG_YN");
    }
}