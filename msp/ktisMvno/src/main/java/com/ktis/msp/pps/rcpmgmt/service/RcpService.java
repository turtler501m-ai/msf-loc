package com.ktis.msp.pps.rcpmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.rcpmgmt.mapper.RcpMapper;
import com.ktis.msp.rcp.rcpMgmt.Util;
import com.ktis.msp.rcp.rcpMgmt.service.RcpEncService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service
public class RcpService extends BaseService {
	
	
	@Autowired
	private RcpEncService encSvc;

	@Autowired
	private RcpMapper rcpMgmtMapper;

	/** 마스킹 처리 서비스 */
	@Autowired
	private MaskingService maskingService;

	/** Constructor */
	public RcpService() {
		setLogPrefix("[RcpMgmtService] ");
	}
	
	/**
	 * @Description : 신청 내역 상세 정보 가져오기
	 * @Param  : RcpListVO
	 * @Return : List<EgovMap>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	public List<EgovMap> getRcpList(RcpListVO searchVO, Map<String, Object> paramMap) {
		logger.debug("getRcpList>>>");
		logger.debug("searchVO=" + searchVO);

		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.getRcpList(searchVO);
		
		HashMap<String, String> exMap = new HashMap<String, String>();
		
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList(list);
		
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

			} catch (Exception e) {
				logger.error(e);
			}
		}

		return result;
	}
	
	/**
	 * @Description : 마스킹 항목 매칭
	 * @Param  : 
	 * @Return : HashMap<String,String>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
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
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("faxnum",			"TEL_NO");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호
		
		return maskFields;
	}
	
	/**
	 * @Description : 조직 정보 찾아아기
	 * @Param  : orgnId
	 * @Return : EgovMap
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
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

	/**
	 * @Description : 신청 리스트 조회
	 * @Param  : RcpListVO
	 * @Return : List<EgovMap>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	public List<EgovMap> rcpMgmtList(RcpListVO searchVO, Map<String, Object> paramMap) {
		
		logger.debug("신청 리스트 조회 시작 ========================");

		List<EgovMap> list = (List<EgovMap>) rcpMgmtMapper.rcpMgmtList(searchVO);
		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(list);

		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(result, maskFields, paramMap);

		for( EgovMap map : result  ){
			try {
				String ssn[] = Util.getJuminNumber((String)map.get("ssn"));
				map.put("ssn1", ssn[0]);
				map.put("ssn2", ssn[1]);

				map.put("cstmrGenderStr", StringUtils.substring(ssn[1], 0, 1));
				map.put("birthDay", StringUtils.substring(ssn[0], 0, 6));

			} catch (Exception e) {
				logger.error(e);
			}
		}
		return result;
	}

	/**
	 * @Description : 엑셀 다운로드
	 * @Param  : RcpListVO
	 * @Return : List<RcpListVO>
	 * @Author : 장익준
	 * @Create Date : 2016. 1. 30.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<RcpListVO> getRcpListExNew(RcpListVO rcpListVO, Map<String, Object> paramMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리 리스트를 조회 엑셀용 START."));
		logger.info(generateLogMsg("Return Vo [rcpListVO] = " + rcpListVO.toString()));
		logger.info(generateLogMsg("================================================================="));


		
		List rcpListVOs = new ArrayList<RcpListVO>();
		
		rcpListVOs = rcpMgmtMapper.rcpMgmtListEx(rcpListVO);

		List<EgovMap> result = encSvc.decryptDBMSRcpMngtList2(rcpListVOs);
		
		HashMap<String, String> maskFields = getMaskFields();

		maskingService.setMask(result, maskFields, paramMap);
		
        for(int i = 0; i < result.size(); i++){
            EgovMap tempMap = (EgovMap)result.get(i);
            
			String ssn[] = Util.getJuminNumber((String)tempMap.get("ssn"));
			tempMap.put("ssn1", ssn[0]);
			tempMap.put("ssn2", ssn[1]);

			tempMap.put("cstmrGenderStr", StringUtils.substring(ssn[1], 0, 1));
			tempMap.put("cstmrNativeRrn", StringUtils.substring(ssn[0], 0, 6));
			
			result.set(i, tempMap);
        }
        
        rcpListVOs = result;
		return rcpListVOs;
	}
}