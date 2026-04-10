/**
 * 
 */
package com.ktis.msp.rcp.rcpMgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktds.crypto.annotation.Crypto;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.cmn.smsmgmt.mapper.SmsMgmtMapper;
import com.ktis.msp.cmn.smsmgmt.vo.KtMsgQueueReqVO;
import com.ktis.msp.cmn.smsmgmt.vo.SmsSendVO;
import com.ktis.msp.rcp.idntMgmt.mapper.IdntMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.EncryptUtil;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.mapper.PointRecoveryMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.mapper.RcpNewMgmtMapper;
import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpCommonVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpRateVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @author SJLEE
 *
 */
@Service
public class RcpMgmtService extends BaseService {

	@Autowired
	private RcpNewMgmtMapper rcpNewMgmtMapper;
	
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private RcpMgmtMapper rcpMgmtMapper;
	
	@Autowired
	private SmsMgmtMapper smsMgmtMapper;
	
	@Resource(name = "idntMgmtMapper")
	private IdntMgmtMapper idntMgmtMapper;
	
	/** 포인트사용금액 서비스 */
	@Autowired
	private PointRecoveryMapper pointRecoveryMapper;
	
	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/** Constructor */
	public RcpMgmtService() {
		setLogPrefix("[RcpMgmtService] ");
	}
	
	public List<EgovMap> getRcpList(RcpListVO searchVO, Map<String, Object> paramMap) {
		logger.debug("getRcpList>>>");
		logger.debug("searchVO=" + searchVO);

		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.getRcpList(searchVO);
		HashMap<String, String> exMap = new HashMap<String, String>();
		
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		//2022.02.27 포인트사용금액 조회
		PointTxnVO usePointAmt = pointRecoveryMapper.selectUsePointAmt(searchVO.getRequestKey());
		
		// 2015-08-22 접수대기시는 마스킹 하지 않음.
		exMap.put("requestStateCode", "00");
		
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(result, maskFields, paramMap, exMap);
		
		/**
		 * 데이터 파싱
		 * */
		for( EgovMap map : result  ){
			try {
				String cstmrForeignerRrn[] = Util.getJuminNumber((String)map.get("cstmrForeignerRrn"));
				map.put("cstmrForeignerRrn1", cstmrForeignerRrn[0]);
				map.put("cstmrForeignerRrn2", cstmrForeignerRrn[1]);

				String cstmrMobile[] = Util.getMobileNum((String)map.get("cstmrMobile"));
				map.put("cstmrMobileFn", cstmrMobile[0]);
				map.put("cstmrMobileMn", cstmrMobile[1]);
				map.put("cstmrMobileRn", cstmrMobile[2]);

				String cstmrJuridicalRrn[] = Util.getJuminNumber((String)map.get("cstmrJuridicalRrn"));
				map.put("cstmrJuridicalRrn1", cstmrJuridicalRrn[0]);
				map.put("cstmrJuridicalRrn2", cstmrJuridicalRrn[1]);
				
				if(map.get("cstmrMail")!=null && !map.get("cstmrMail").equals("")){
					String [] temp = ((String)map.get("cstmrMail")).split("@");
					if(temp.length>1){//이메일 뒷자리 
						String cstmrMail[] = Util.getEmailSplit((String)map.get("cstmrMail"));
						map.put("cstmrMail1", cstmrMail[0]);
						map.put("cstmrMail2", cstmrMail[1]);
					}
				}
				
				if(map.get("cstmrTel")!=null && !map.get("cstmrTel").equals("")){
					String[] cstmrTel = Util.getPhoneNum( (String)map.get("cstmrTel") );
					map.put("cstmrTelFn", cstmrTel[0]);
					map.put("cstmrTelMn", cstmrTel[1]);
					map.put("cstmrTelRn", cstmrTel[2]);
				}
				
				if(map.get("dlvryTel")!=null && !map.get("dlvryTel").equals("")){
					String[] dlvryTel = Util.getPhoneNum( (String)map.get("dlvryTel") );
					map.put("dlvryTelFn", dlvryTel[0]);
					map.put("dlvryTelMn", dlvryTel[1]);
					map.put("dlvryTelRn", dlvryTel[2]);
				}
				
				String dlvryMobile[] = Util.getMobileNum((String)map.get("dlvryMobile"));
				map.put("dlvryMobileFn", dlvryMobile[0]);
				map.put("dlvryMobileMn", dlvryMobile[1]);
				map.put("dlvryMobileRn", dlvryMobile[2]);

				String reqAccountRrn[] = Util.getJuminNumber((String)map.get("reqAccountRrn"));
				map.put("reqAccountRrn1", reqAccountRrn[0]);
				map.put("reqAccountRrn2", reqAccountRrn[1]);

				String[] reqCardNo = Util.getCardNumber( (String)map.get("reqCardNo") );
				map.put("reqCardNo1", reqCardNo[0]);
				map.put("reqCardNo2", reqCardNo[1]);
				map.put("reqCardNo3", reqCardNo[2]);
				map.put("reqCardNo4", reqCardNo[3]);

				String reqCardRrn[] = Util.getJuminNumber((String)map.get("reqCardRrn"));
				map.put("reqCardRrn1", reqCardRrn[0]);
				map.put("reqCardRrn2", reqCardRrn[1]);

				String reqGuide[] = Util.getMobileNum((String)map.get("reqGuide"));
				map.put("reqGuideFn", reqGuide[0]);
				map.put("reqGuideMn", reqGuide[1]);
				map.put("reqGuideRn", reqGuide[2]);

				String moveMobile[] = Util.getMobileNum((String)map.get("moveMobile"));
				map.put("moveMobileFn", moveMobile[0]);
				map.put("moveMobileMn", moveMobile[1]);
				map.put("moveMobileRn", moveMobile[2]);

				String minorAgentRrn[] = Util.getJuminNumber((String)map.get("minorAgentRrn"));
				map.put("minorAgentRrn1", minorAgentRrn[0]);
				map.put("minorAgentRrn2", minorAgentRrn[1]);

				String[] minorAgentTel = Util.getMobileNum( (String)map.get("minorAgentTel") );
				map.put("minorAgentTelFn", minorAgentTel[0]);
				map.put("minorAgentTelMn", minorAgentTel[1]);
				map.put("minorAgentTelRn", minorAgentTel[2]);

				String cstmrNativeRrn[] = Util.getJuminNumber((String)map.get("cstmrNativeRrn"));
				map.put("cstmrNativeRrn1", cstmrNativeRrn[0]);
				map.put("cstmrNativeRrn2", cstmrNativeRrn[1]);
				
				if(map.get("faxnum")!=null && !map.get("faxnum").equals("")){
					String[] faxnum = Util.getPhoneNum( (String)map.get("faxnum") );
					map.put("faxnum1", faxnum[0]);
					map.put("faxnum2", faxnum[1]);
					map.put("faxnum3", faxnum[2]);
				}
				if(StringUtils.equalsIgnoreCase((String)map.get("cstmrType"), "FN")){
					map.put("cstmrGenderStr", StringUtils.substring(cstmrForeignerRrn[1], 0, 1));
					map.put("birthDay", StringUtils.defaultIfBlank((String)map.get("cstmrForeignerBirth2"), cstmrForeignerRrn[0]));

				}else{
					map.put("cstmrGenderStr", StringUtils.substring(cstmrNativeRrn[1], 0, 1));
					map.put("birthDay", cstmrNativeRrn[0]);
				}
				//2022.02.27 포인트사용금액 조회
				if(usePointAmt != null ) {
					map.put("usePointAmt", usePointAmt.getPoint());
				}
				
				//2023.02.16 eSIM WATCH 모회선 전화번호
				String prntsCtn[] = Util.getMobileNum((String)map.get("prntsCtn"));
				map.put("prntsCtnFn", prntsCtn[0]);
				map.put("prntsCtnMn", prntsCtn[1]);
				map.put("prntsCtnRn", prntsCtn[2]);
				
				
				//2014년 12월 23일 추가 (모집경로)
				//
				/*
				String onOffType = (String)map.get("onOffType");
				if(onOffType!=null){
					if(onOffType.equals("0")){
						map.put("onOffTypeName", "온라인");
					}else{
						map.put("onOffTypeName", "오프라인");
					}
				}else{
					map.put("onOffTypeName", "");
				}
				*/

				String fathMobile[] = Util.getMobileNum((String)map.get("fathMobile"));
				map.put("fathMobileFn", fathMobile[0]);
				map.put("fathMobileMn", fathMobile[1]);
				map.put("fathMobileRn", fathMobile[2]);

			} catch (Exception e) {
				logger.error(e);
			}
		}

		return result;
	}

	public List<?> getRcpCodeList() {
		return rcpMgmtMapper.getRcpCodeList();
	}

	public List<?> getRcpCommon(RcpCommonVO searchVO) {
		return rcpMgmtMapper.getRcpCommon(searchVO);
	}
	
	public List<?> getRcpSimPrdtInfo(RcpCommonVO searchVO) {
		return rcpMgmtMapper.getRcpSimPrdtInfo(searchVO);
	}

	public int updateRcpRequestRecYn(RcpDetailVO rcpDetailVO) {
		return rcpMgmtMapper.updateRcpRequestRecYn(rcpDetailVO);
	}
	
	public List<?> getRcpRateList(RcpRateVO rcpRateVO){


		List<?> getRcpRateList = new ArrayList<RcpRateVO>();

		/*rcpRateVO.setUsrId(rcpRateVO.getUsrId().toLowerCase());
		rcpRateVO.setUsrNm(rcpRateVO.getUsrNm().toLowerCase());*/

		getRcpRateList = rcpMgmtMapper.getRcpRateList(rcpRateVO);

		return getRcpRateList;

	}

	
	@Transactional(rollbackFor=Exception.class)
	public int updateRcpDetail(RcpDetailVO vo) throws MvnoServiceException{
		
		boolean isNew = false;
		if(StringUtils.isEmpty(vo.getRequestKey())){
			isNew = true;
			RcpDetailVO keyMap = rcpMgmtMapper.maxRequestkey();
			
			vo.setRequestKey(keyMap.getRequestKey());
			vo.setResNo(keyMap.getResNo());
			vo.setShopCd( vo.getSessionUserOrgnId() );
			
			//2015-02-27 cntpntshopid가 없을시 리턴
			if(vo.getCntpntShopId()==null || "".equals(vo.getCntpntShopId())){
				throw new MvnoServiceException("가입신청 등록/수정 실패");
			}
			EgovMap shopMap = rcpMgmtMapper.getShopCd(vo.getCntpntShopId());
			
			vo.setCntpntShopId((String)shopMap.get("orgnId"));
			vo.setAgentCode((String)shopMap.get("agentCd"));
			vo.setManagerCode("M0001");
			//keyMap = null;
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
		// 모회선전화번호
		if(StringUtils.isNotBlank(vo.getPrntsCtnFn()) && StringUtils.isNotBlank(vo.getPrntsCtnMn()) && StringUtils.isNotBlank(vo.getPrntsCtnRn())){
			vo.setPrntsCtn(vo.getPrntsCtnFn() + vo.getPrntsCtnMn() + vo.getPrntsCtnRn());
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
		// 2016-10-25
//		vo.setUsimPriceType("B");
//		vo.setJoinPriceType("I");
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
		
		// 2015-02-23, 오류시 vo 내용을 보기위해서 try 구문 밖에서 선언
		RcpDetailVO vo2 = new RcpDetailVO();
		
		try {
			if(!isNew){
				//2015-02-27 requestkey가 없을시 리턴
				if(vo.getRequestKey()==null || "".equals(vo.getRequestKey())){
					throw new MvnoServiceException("가입신청 등록/수정 실패");
				}
				RcpDetailVO orgData = rcpMgmtMapper.selectRequest(vo.getRequestKey());
				encSvc.decryptDBMSRcpDetailVO(orgData);
				
				RcpDetailVO decVo = new RcpDetailVO();
				BeanUtils.copyProperties(decVo, orgData);
				
				HashMap<String, String> maskFields = getMaskFields();
//				maskingService.setMask(decVo, maskFields, null);
				
				// 2015-02-06, 테스트
				Map<String, Object> pReqParam = new HashMap<String, Object>();
				pReqParam.put("SESSION_USER_ID", vo.getSessionUserId());
				logger.debug("pReqParam = " + pReqParam);
				
				maskingService.setMask(decVo, maskFields, pReqParam);
				
				String cstmrTel[] = Util.getTelSplit(decVo.getCstmrTel());
				decVo.setCstmrTelFn(cstmrTel[0]);
				decVo.setCstmrTelMn(cstmrTel[1]);
				decVo.setCstmrTelRn(cstmrTel[2]);
				
				String cstmrMobile[] = Util.getTelSplit(decVo.getCstmrMobile());
				decVo.setCstmrMobileFn(cstmrMobile[0]);
				decVo.setCstmrMobileMn(cstmrMobile[1]);
				decVo.setCstmrMobileRn(cstmrMobile[2]);
				
				String fathMobile[] = Util.getTelSplit(decVo.getFathMobile());
				decVo.setFathMobileFn(fathMobile[0]);
				decVo.setFathMobileMn(fathMobile[1]);
				decVo.setFathMobileRn(fathMobile[2]);
				
				String dlvryTel[] = Util.getTelSplit(decVo.getDlvryTel());
				decVo.setDlvryTelFn(dlvryTel[0]);
				decVo.setDlvryTelMn(dlvryTel[1]);
				decVo.setDlvryTelRn(dlvryTel[2]);
				
				String dlvryMobile[] = Util.getTelSplit(decVo.getDlvryMobile());
				decVo.setDlvryMobileFn(dlvryMobile[0]);
				decVo.setDlvryMobileMn(dlvryMobile[1]);
				decVo.setDlvryMobileRn(dlvryMobile[2]);
				
				String moveMobile[] = Util.getTelSplit(decVo.getMoveMobile());
				decVo.setMoveMobileFn(moveMobile[0]);
				decVo.setMoveMobileMn(moveMobile[1]);
				decVo.setMoveMobileRn(moveMobile[2]);
				
				String reqGuide[] = Util.getTelSplit(decVo.getReqGuide());
				decVo.setReqGuideFn(reqGuide[0]);
				decVo.setReqGuideMn(reqGuide[1]);
				decVo.setReqGuideRn(reqGuide[2]);
				
				String minorAgentTel[] = Util.getTelSplit(decVo.getMinorAgentTel());
				decVo.setMinorAgentTelFn(minorAgentTel[0]);
				decVo.setMinorAgentTelMn(minorAgentTel[1]);
				decVo.setMinorAgentTelRn(minorAgentTel[2]);
				
				String jrdclAgentTel[] = Util.getTelSplit(decVo.getJrdclAgentTel());
				decVo.setJrdclAgentTelFn(jrdclAgentTel[0]);
				decVo.setJrdclAgentTelMn(jrdclAgentTel[1]);
				decVo.setJrdclAgentTelRn(jrdclAgentTel[2]);
				
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
			
			vo2 = encSvc.encryptDBMSRcpDetailVO(vo); //개발 배포시에는 주석 풀어야해요
			
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
				
				if( !"".equals(vo2.getCntpntShopId()))
				{
					int cnt = rcpMgmtMapper.selectMsgCnt(vo2.getCntpntShopId());

					//문자메시지 전송
					if( cnt > 0)
					{
						String templateId = "6";
						// SMS 템플릿 제목,내용 가져오기
						KtMsgQueueReqVO msgVO = smsMgmtMapper.getKtTemplateText(templateId);
						msgVO.setMessage((msgVO.getTemplateText()).replaceAll(Pattern.quote("#{resNo}"), vo2.getResNo()));
						msgVO.setRcptData(vo2.getCstmrMobileFn() + vo2.getCstmrMobileMn() + vo2.getCstmrMobileRn());
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
			}
			
			if("NAC3".equals(vo2.getOperType()) || "MNP3".equals(vo2.getOperType())){
				rcpMgmtMapper.deleteRcpAddition(vo2);
				
				rcpMgmtMapper.insertRcpAddition(vo2);
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

	/**
	 * <PRE>
	 *
	 * </PRE>
	 * @param 
	 * @param 
	 * @return 
	 * @exception 
	 */
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
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("faxnum",			"TEL_NO");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호
		maskFields.put("intmSrlNo",	"DEV_NO");	// 단말기 일련번호

		maskFields.put("cstmrName2",		"CUST_NAME");
		maskFields.put("cstmrNameMask",		"CUST_NAME");
		maskFields.put("cstmrAddrDtl",		"PASSWORD");
		maskFields.put("dlvryAddrDtl",		"PASSWORD");
		maskFields.put("cstmrTelMn",		"PASSWORD");
		maskFields.put("cstmrMobileMn",		"PASSWORD");
		maskFields.put("fathMobileMn",		"PASSWORD");
		maskFields.put("dlvryTelMn",		"PASSWORD");
		maskFields.put("dlvryMobileMn",		"PASSWORD");
		maskFields.put("moveMobileMn",		"PASSWORD");
		maskFields.put("reqUsimSnMask",		"USIM");
		maskFields.put("reqPhoneSnMask",	"DEV_NO");		
		maskFields.put("userId",			"SYSTEM_ID");
		
		//관리자 정보
		maskFields.put("usrId",				"SYSTEM_ID");
		maskFields.put("usrNm",				"CUST_NAME");
		maskFields.put("rvisnNm",			"CUST_NAME");
		
		maskFields.put("prntsCtn",			"MOBILE_PHO");
		
		return maskFields;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateRcpSend(RcpDetailVO rcpDetailVO){
		int updateRcpAlterCnt = rcpMgmtMapper.updateRcpAlter(rcpDetailVO);
		logger.info(generateLogMsg("updateRcpAgent = ") + updateRcpAlterCnt);

		return updateRcpAlterCnt;
	}

	@Crypto(decryptName="DBMSDec", fields =  {"cstmrNativeRrn"})
	public List<RcpListVO> decryptDBMSList(List<RcpListVO> rcpList){

		return rcpList;
	}

	@Crypto(decryptName="DBMSDec", fields = {"cstmrNativeRrn","reqCardNo","cstmrForeignerPn","reqCardRrn","minorAgentRrn","reqAccountRrn","reqAccountNumber"})	
	public RcpListVO decryptDBMS(RcpListVO vo){
		return vo;
	}

	@Crypto(encryptName="DBMSEnc", fields = {"cstmrNativeRrn","reqCardNo","cstmrForeignerPn","reqCardRrn","minorAgentRrn","reqAccountRrn","reqAccountNumber"})
	public RcpDetailVO encryptDBMS(RcpDetailVO vo){
		return vo;
	}
	
	public List<?> getRcpPlc(RcpListVO rcpListVO){


		List<?> rcpPlcListVO = new ArrayList<RcpListVO>();

		/*		rcpListVO.setPlcId(rcpListVO.getPlcId().toLowerCase());*/
		rcpPlcListVO = rcpMgmtMapper.getRcpPlc(rcpListVO);

		return rcpPlcListVO;

	}


	public List<?> comboList(RcpListVO rcpListVO){


		List<?> comboList = new ArrayList<RcpListVO>();

		comboList = rcpMgmtMapper.comboList(rcpListVO);

		return comboList;

	}

	public EgovMap orgnInf(String orgnId) {
		EgovMap result = rcpMgmtMapper.orgnInfo(orgnId);
		if(result.get("fax")!=null && !result.get("fax").equals("")){
			String[] faxnum = Util.getPhoneNum( (String)result.get("fax") );
			result.put("faxnum1", faxnum[0]);
			result.put("faxnum2", faxnum[1]);
			result.put("faxnum3", faxnum[2]);
		}
		return result; 
	}

	public EgovMap checkPhoneSN(RcpDetailVO searchVO) {
		return rcpMgmtMapper.checkPhoneSN(searchVO);
	}
	
	
	@Crypto(decryptName="DBMSDec", fields =  {"mspSsn","mcpSsn"})
	public List<RcpListVO> decryptDBMSGridList(List list){
		return list;
	}
	
	
	/**
	 * 신청관리(링커스)
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtListLinkus(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		// 필수조건 체크
		if(paramMap.get("agntCd") == null || "".equals(paramMap.get("agntCd"))){
			throw new MvnoRunException(-1, "대리점코드 누락");
		}
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
		
		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.getRcpMgmtListLinkus(paramMap);
		
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
	 * 신청관리(링커스) 상세조회
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtListLinkusDtl(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		if(!paramMap.containsKey("requestKey") || "".equals(paramMap.get("requestKey").toString())){
			throw new MvnoRunException(-1, "요청키가 존재하지 않습니다.");
		}
		
		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.getRcpMgmtListLinkusDtl(paramMap);
		
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
	
//	public String chkMcpRequest(RcpMgmtVO param) {
//		return rcpMgmtMapper.chkMcpRequest(param);
//	}
	
	public void updateRcpDetailLinkus(Map<String, Object> paramMap){
		if(!paramMap.containsKey("requestKey") || "".equals(paramMap.get("requestKey").toString())){
			throw new MvnoRunException(-1, "요청키가 존재하지 않습니다.");
		}
		
		if(!paramMap.containsKey("resNo") || "".equals(paramMap.get("resNo").toString())){
			throw new MvnoRunException(-1, "예약번호가 존재하지 않습니다.");
		}
		
		String usimModType = "";
		int cnt = rcpMgmtMapper.selectLinkusUsimMst(paramMap);
		
		if(paramMap.get("oldUsimSn") == null || "".equals(paramMap.get("oldUsimSn").toString())) {
			if(paramMap.get("reqUsimSn") != null && !"".equals(paramMap.get("reqUsimSn").toString())) {
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
			} //else {
//				/* 삭제로직은 현재 없음
//				if (cnt > 0) {
//					rcpMgmtMapper.deleteLinkusUsimMst(paramMap);
//					usimModType = "D";
//				}
//				*/
//			}  
			//20210722 pmd 소스 점검 EmptyIfStmt 조치
		}
		
		if (!usimModType.equals("")) {
			paramMap.put("evntType", usimModType);
			paramMap.put("regstType", "V"); //화면에서 수정일 경우 V, 엑셀업로드 수정일 경우 E
			rcpMgmtMapper.insertLinkusUsimHst(paramMap);
		}
		
		// 신청정보 수정
		rcpMgmtMapper.setMcpRequestLinkus(paramMap);
		
		// 상태정보 수정
		rcpMgmtMapper.setMcpRequestStateLinkus(paramMap);
		
		// 배송정보 수정
		if ("Y".equals(String.valueOf(paramMap.get("maskingYn")))) {
			rcpMgmtMapper.setMcpRequestDlvryLinkus(paramMap);

			//신청서 상세 현행화
			RcpDetailVO dtlVo = rcpMgmtMapper.getMcpReqDtl(String.valueOf(paramMap.get("requestKey")));
			if (dtlVo != null) {
				if (rcpMgmtMapper.chkMcpReqDtl(String.valueOf(paramMap.get("requestKey"))) > 0) {
					rcpMgmtMapper.updateMcpReqDtl(dtlVo);
				} else {
					rcpMgmtMapper.insertMcpReqDtl(dtlVo);
				}
			}
		}
		
	}
	
	public String getMcpRequestScanId(HashMap<String, String> params) {
		
		HashMap<String, String> resultMap = rcpMgmtMapper.getMcpRequestScanId(params);
		
		String scanId = resultMap.get("SCAN_ID");
		
		return scanId;
	}
	
	/**
	 * 신청관리(렌탈)
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtRentalList(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		// 필수조건 체크
		if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (paramMap.get("orgnId") == null || "".equals(paramMap.get("orgnId")))){
			throw new MvnoRunException(-1, "대리점코드 누락");
		}
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
		
		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.getRcpMgmtRentalList(paramMap);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		/**
		 * 데이터 파싱
		 * */
		for( EgovMap map : list ){
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
	 * 신청관리(렌탈) 엑셀다운
	 * @param paramMap
	 * @return
	 */
	public List<?> getRcpMgmtRentalListExcel(Map<String, Object> paramMap){
		logger.debug("paramMap=" + paramMap);
		
		// 필수조건 체크
		if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD")) && (paramMap.get("orgnId") == null || "".equals(paramMap.get("orgnId")))){
			throw new MvnoRunException(-1, "대리점코드 누락");
		}
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
		
		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.getRcpMgmtRentalListExcel(paramMap);
		
		list = encSvc.decryptDBMSRcpMngtList(list);
		
		maskingService.setMask(list, maskingService.getMaskFields(), paramMap);
		
		/**
		 * 데이터 파싱
		 * */
		for( EgovMap map : list ) {
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
	 * 신청관리(링커스) 마스킹 처리 
	 */
	private HashMap<String, String> getMaskFieldsLinkus() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("cstmrName2",		"CUST_NAME");
		maskFields.put("cstmrNameMask",		"CUST_NAME");
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("reqUsimSnMask",		"USIM");
		maskFields.put("reqPhoneSnMask",	"DEV_NO");
		return maskFields;
	}
	
	// 개통이력확인
	public List<?> getCheckCstmr(RcpDetailVO vo){
		// 주민번호확인
		if(vo.getCstmrNativeRrn() == null || "".equals(vo.getCstmrNativeRrn())){
			throw new MvnoRunException(-1, "주민번호를 입력하세요");
		}
		try{
			// 주민번호 암호화
			vo.setCstmrNativeRrn(EncryptUtil.ace256Enc(vo.getCstmrNativeRrn()));
			
			return rcpMgmtMapper.getCheckCstmr(vo);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}
	
	// 개통이력확인
	public List<?> getCheckCstmrList(RcpDetailVO vo){
		// 주민번호확인
		if(vo.getCstmrNativeRrn() == null || "".equals(vo.getCstmrNativeRrn())){
			throw new MvnoRunException(-1, "주민번호를 입력하세요");
		}
		try{
			// 주민번호 암호화
			vo.setCstmrNativeRrn(EncryptUtil.ace256Enc(vo.getCstmrNativeRrn()));
			
			return rcpMgmtMapper.getCheckCstmrList(vo);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 사전인증조회 
	 */
	public List<?> selectPreAuthList(RcpDetailVO searchVO, Map<String, Object> paramMap){
    	List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.selectPreAuthList(paramMap);
    	
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
		HashMap<String, String> maskFields = getMaskFields();
		maskingService.setMask(result, maskFields, paramMap);
		
        return result;
    }
	
	
	/**
	 * 사전인증조회 신청서 메모 수정
	 */
	public void updatePreAuthMemo(RcpDetailVO searchVO, Map<String, Object> paramMap){
		
    	rcpMgmtMapper.updatePreAuthMcpMemo(paramMap);
    	rcpMgmtMapper.updatePreAuthMspMemo(paramMap);
    }	
	
	//2020.12.10 유심일련번호 등록 로직
	//regUsimSnListLinkus
	@Transactional(rollbackFor=Exception.class)
	public void regUsimSnListLinkus(RcpListVO rcpListVO,String userId) {
		
		List<RcpListVO> itemList = rcpListVO.getItems();
		
		int cnt = 1;
		String codeChk ="";
		
		for( RcpListVO vo : itemList ) {
			cnt++;
			
			if (vo.getResNo() == null || "".equals(vo.getResNo())){
				throw new MvnoRunException(-1, "["+cnt+"행]예약번호는 필수 항목 입니다.");
			}
			if (vo.getReqUsimSn() == null || "".equals(vo.getReqUsimSn())){
				throw new MvnoRunException(-1, "["+cnt+"행]유심일련번호는 필수 항목 입니다.");
			}
			//2021.01.07 진행상태관련 vaildation체크 추가
			if (vo.getRequestStateCode() == null || "".equals(vo.getRequestStateCode())){
				throw new MvnoRunException(-1, "["+cnt+"행]진행상태값은 필수 항목 입니다.");
			}
			String result = rcpMgmtMapper.isUsimInfoChkLinkus(vo.getResNo());
			if (result == null) {
				throw new MvnoRunException(-1, "[예약번호:"+vo.getResNo()+"]일치하는 예약번호가 없습니다.");
			}
			
			//2021.01.07 진행상태관련 vaildation체크 추가
			CmnCdMgmtVo cmnCdMgntvo = new CmnCdMgmtVo();
			cmnCdMgntvo.setGrpId("RCP0006");
			cmnCdMgntvo.setCdVal(vo.getRequestStateCode());
			
			codeChk = idntMgmtMapper.getInvstCusInfoValidation(cmnCdMgntvo);
			
			if(!("1".equals(codeChk))){ 
				throw new MvnoRunException(-1, "[예약번호:"+ vo.getResNo() + "] 진행상태 값을 확인해주세요.");
			}
			
			vo.setSessionUserId(userId);
			
			Map<String, Object> paramMap = new HashMap<String,Object>();
			
			String usimModType = "";
			paramMap.put("resNo", vo.getResNo());
			paramMap.put("reqUsimSn", vo.getReqUsimSn());
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
			
			
			rcpMgmtMapper.updateUsimSnLinkus(vo);
			rcpMgmtMapper.updateRequestStateLinkus(vo);
						
		}
    }

	// K-Note 사전승낙제 적용 대상조직여부
	public String getKnoteYn(String orgnId){
		int cnt  = rcpMgmtMapper.getKnoteYn(orgnId);
		String knoteYn = cnt > 0 ? "Y": "N";

		return knoteYn;
	}

	public int getKnoteShopYn(RcpDetailVO rcpDetailVO){
		return rcpMgmtMapper.getKnoteShopYn(rcpDetailVO);
	}

	public HashMap<String, String> checkAuth(RcpDetailVO rcpDetailVO){
		return rcpMgmtMapper.checkAuth(rcpDetailVO);
	}

	public boolean checkSendFS5(){
		return rcpMgmtMapper.checkSendFS5() > 0;
	}

	public String getKosId(String userId){
		return rcpMgmtMapper.getKosId(userId);
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

	public String getCustomerIdBySvcCntrNo(String svcCntrNo) {
		return rcpNewMgmtMapper.getCustomerIdBySvcCntrNo(svcCntrNo);
	}

}