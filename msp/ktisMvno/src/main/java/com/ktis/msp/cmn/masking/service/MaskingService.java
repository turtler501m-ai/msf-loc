package com.ktis.msp.cmn.masking.service;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.base.mvc.BaseVo;
import com.ktis.msp.cmn.masking.mapper.MaskingMapper;
import com.ktis.msp.cmn.masking.vo.MaskingVO;

//import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: MaskingService.java
 * 3. Package	: com.ktis.msp.cmn.masking.service
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:59:19
 * </PRE>
 */
@Service
public class MaskingService extends BaseService {

	@Autowired
	private MaskingMapper maskingMapper;
	
//	public List<?> selectList(Map<String, Object> p_reqParamMap) throws Exception {
//						
//		//----------------------------------------------------------------
//    	// 목록 db select
//    	//----------------------------------------------------------------
//		List<?>  resultList = maskingMapper.selectList(p_reqParamMap);
//		logger.debug(resultList);
//		
//		return resultList;
//		
//	}

	/**
	 * 1. MethodName: getMaskFields
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 공통 마스킹 필드 (22.10.12 기준 사용중인 모든 마스킹 대상 컬럼 취합)
	 * 4. 작성자	: 박준성
	 * 5. 작성일	: 2022. 10. 12.
	 */
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		//M전산 계정 관련
		maskFields.put("usrId",				"SYSTEM_ID");
		maskFields.put("regId",				"SYSTEM_ID");
		maskFields.put("regstId",			"SYSTEM_ID");
		maskFields.put("regststId",			"SYSTEM_ID");
		maskFields.put("regstUsrId",		"SYSTEM_ID");
		maskFields.put("rvisnId",			"SYSTEM_ID");
		maskFields.put("chngId",			"SYSTEM_ID");
		maskFields.put("runUsrId",			"SYSTEM_ID");
		maskFields.put("rgstPrsnId",		"SYSTEM_ID");
		maskFields.put("procPrsnId",		"SYSTEM_ID");
		maskFields.put("procId",			"SYSTEM_ID");
		maskFields.put("shopUsrId",			"SYSTEM_ID");
		maskFields.put("admUserId",			"SYSTEM_ID");

		maskFields.put("cretNm",			"CUST_NAME");
		maskFields.put("amdNm",				"CUST_NAME");
		maskFields.put("usrNm",				"CUST_NAME");
		maskFields.put("regNm",				"CUST_NAME");
		maskFields.put("regName",			"CUST_NAME");
		maskFields.put("regstNm",			"CUST_NAME");
		maskFields.put("regststNm",			"CUST_NAME");
		maskFields.put("regstUsrNm",		"CUST_NAME");
		maskFields.put("rvisnNm",			"CUST_NAME");
		maskFields.put("procNm", 			"CUST_NAME");
		maskFields.put("procPrsnNm",		"CUST_NAME");
		maskFields.put("rgstPrsnNm", 		"CUST_NAME");
		maskFields.put("updtId",			"CUST_NAME");
		maskFields.put("sndUsrNm",			"CUST_NAME");
		maskFields.put("fstUsrNm",			"CUST_NAME");
		maskFields.put("shopUsrNm",			"CUST_NAME");
		maskFields.put("cnfmNm",			"CUST_NAME");
		maskFields.put("managerNm",			"CUST_NAME");
		
		//이름
		maskFields.put("name",				"CUST_NAME");
		maskFields.put("cstmrName",			"CUST_NAME");
		maskFields.put("cstmrName2",		"CUST_NAME");
		maskFields.put("cstmrNameMask",		"CUST_NAME");
		maskFields.put("cstmrNm",			"CUST_NAME");
		maskFields.put("reqAccountName",	"CUST_NAME");
		maskFields.put("reqCardName",		"CUST_NAME");
		maskFields.put("minorAgentNm",		"CUST_NAME");
		maskFields.put("minorAgentName",	"CUST_NAME");
		maskFields.put("jrdclAgentName",	"CUST_NAME");
		maskFields.put("realUseCustNm",		"CUST_NAME");
		maskFields.put("customerLinkName",	"CUST_NAME");
		maskFields.put("dlvryName",			"CUST_NAME");
	    maskFields.put("subLinkName",		"CUST_NAME");
		maskFields.put("receiveNm", 		"CUST_NAME");
		maskFields.put("mngNm", 			"CUST_NAME");
		maskFields.put("maskMngNm", 		"CUST_NAME");
		maskFields.put("cusNm", 			"CUST_NAME");
		maskFields.put("custNm", 			"CUST_NAME");
		maskFields.put("custNmMsk", 		"CUST_NAME");
		maskFields.put("custNmMask", 		"CUST_NAME");
		maskFields.put("custName",			"CUST_NAME");
		maskFields.put("deSubLinkName", 	"CUST_NAME");
		maskFields.put("apyNm",				"CUST_NAME");
		maskFields.put("minorName",			"CUST_NAME");
		maskFields.put("rprsenNm",			"CUST_NAME");
		maskFields.put("rprsPrsnNm",		"CUST_NAME");
		maskFields.put("approvalIdNm",		"CUST_NAME");
		maskFields.put("empNm", 			"CUST_NAME");
		maskFields.put("frgnrNameMsk",		"CUST_NAME");
		maskFields.put("cstmrNmMsk",		"CUST_NAME");
		maskFields.put("instCnfmNm",		"CUST_NAME");
        maskFields.put("cSubLinkName",		"CUST_NAME");	//고객명(해지)
		maskFields.put("banLinkName",		"CUST_NAME");
		maskFields.put("dpstPrsnNm",		"CUST_NAME");
		maskFields.put("custNmVoc",			"CUST_NAME");
		maskFields.put("dpstNm", 			"ACCOUNT_OW");
		

		//전화번호
		maskFields.put("subscriberNo",		"MOBILE_PHO");
		maskFields.put("deSubscriberNo",	"MOBILE_PHO");
		maskFields.put("subscriberNoMask", 	"MOBILE_PHO");
		maskFields.put("cstmrTel",			"MOBILE_PHO");
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("reqGuide",			"MOBILE_PHO");
		maskFields.put("moveMobile",		"MOBILE_PHO");
		maskFields.put("minorAgentTel",		"MOBILE_PHO");
		maskFields.put("jrdclAgentTel",		"MOBILE_PHO");
		maskFields.put("dlvTelNo", 			"MOBILE_PHO");
		maskFields.put("dlvryTel",			"MOBILE_PHO");
		maskFields.put("dlvryMobile",		"MOBILE_PHO");
		maskFields.put("dlvryMobileNo",		"MOBILE_PHO");
		maskFields.put("fax",				"MOBILE_PHO");
		maskFields.put("faxnum",			"MOBILE_PHO");
		maskFields.put("maskMobileNo", 		"MOBILE_PHO");
		maskFields.put("msgRecvCtn", 		"MOBILE_PHO");
		maskFields.put("cmpnCtn",			"MOBILE_PHO");
		maskFields.put("mngPhn", 			"TEL_NO");
		maskFields.put("mngMblphn", 		"MOBILE_PHO");
		maskFields.put("mobileNo",			"MOBILE_PHO");
		maskFields.put("mobileNoMsk", 		"MOBILE_PHO");
		maskFields.put("ctn",				"MOBILE_PHO");
		maskFields.put("tlphNo",			"MOBILE_PHO");
		maskFields.put("cntcTelNo",			"MOBILE_PHO");
		maskFields.put("tgtSvcNo",			"MOBILE_PHO");
		maskFields.put("tel1", 				"TEL_NO");
		maskFields.put("tel2", 				"TEL_NO");
		maskFields.put("telnum", 			"TEL_NO");
		maskFields.put("telNum", 			"TEL_NO");
		maskFields.put("telNo",				"TEL_NO");
		maskFields.put("telNo1", 			"TEL_NO");
        maskFields.put("svcTelNum",			"TEL_NO");
		maskFields.put("residenceSubscriberNo",	"TEL_NO");
		maskFields.put("billAddrSubscriberNo",	"TEL_NO");
		maskFields.put("ctnGet",			"MOBILE_PHO");
		maskFields.put("callNum",			"MOBILE_PHO");
		maskFields.put("phonCtn",			"MOBILE_PHO");
		maskFields.put("billAdInfo2",		"MOBILE_PHO");
		maskFields.put("agentTelNum",		"MOBILE_PHO");
        maskFields.put("cSubscriberNo",		"TEL_NO");		//전화번호(해지)
        maskFields.put("ctnVoc",			"MOBILE_PHO");
        maskFields.put("mblphnNum",			"MOBILE_PHO");
        maskFields.put("cancelMobileNo",			"MOBILE_PHO");
        maskFields.put("receiveMobileNo",			"MOBILE_PHO");
		
        //주민번호
		maskFields.put("userSsn",			"SSN");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("cstmrForeignerRrn",	"SSN");
		maskFields.put("reqAccountRrn",		"SSN");
		maskFields.put("reqCardRrn",		"SSN");
		maskFields.put("minorAgentRrn",		"SSN");
		maskFields.put("jrdclAgentRrn",		"SSN");
		maskFields.put("ssn",				"SSN");
		maskFields.put("ssn1",				"SSN");
		maskFields.put("ssn2",				"SSN");
		maskFields.put("ssn3",				"SSN");
		maskFields.put("minorSsn",			"SSN");
		maskFields.put("rprsenRrnum", 		"SSN");
		maskFields.put("bankAcctHolderId",	"SSN");

		//주소
		maskFields.put("cstmrAddrMsk",		"ADDR");
		maskFields.put("address", 			"ADDR");
		maskFields.put("cstmrAddr",			"ADDR");
		maskFields.put("dlvryAddr",			"ADDR");
		maskFields.put("dlvryPostAddr",		"ADDR");
		maskFields.put("customerAdr",		"ADDR");
	    maskFields.put("subAdrPrimaryLn",	"ADDR");
		maskFields.put("addr", 				"ADDR");
		maskFields.put("addr1", 			"ADDR");
		maskFields.put("residenceAddr",		"ADDR");
		maskFields.put("billAddr",			"ADDR");
		maskFields.put("recvText",			"ADDR");
		maskFields.put("arnoAdrBasSbst",	"ADDR");
    	maskFields.put("arnoAdr",			"ADDR");		//지번주소
    	maskFields.put("roadnAdr",			"ADDR");		//도로명주소
    	maskFields.put("roadnAdrBasSbst",	"ADDR");		//도로명주소기본내용
    	maskFields.put("roadnAdrRoadNm",	"ADDR");		//도로명주소도로명
		maskFields.put("banAdrPrimaryLn",	"ADDR");		
		
		//본인인증번호
		maskFields.put("cstmrForeignerPn",	"PASSPORT");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호
		
		//사업자번호
		maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
		maskFields.put("corpRegNum", 		"CORPORATE");
		
		//이메일
		maskFields.put("email", 			"EMAIL");
		maskFields.put("cstmrMail",			"EMAIL");
		maskFields.put("recpEmail",			"EMAIL");
		maskFields.put("billAdInfo1",		"EMAIL");
		maskFields.put("mailMsk",			"EMAIL");

		//계좌번호
		maskFields.put("acntNo", 			"ACCOUNT");
		maskFields.put("reqAccountNumber",	"ACCOUNT");
		maskFields.put("bankCdNo", 			"ACCOUNT");
		maskFields.put("bnkacnNo", 			"ACCOUNT");
		maskFields.put("dpstAcntNum", 		"ACCOUNT");
		maskFields.put("bankBnkacnNo", 		"ACCOUNT");
		
		//카드번호
		maskFields.put("reqCardNo",			"CREDIT_CAR");
		
		//카드유효기간
		maskFields.put("reqCardMm",			"CARD_EXP");
		maskFields.put("reqCardYy",			"CARD_EXP");
		
		//유심일련번호
		maskFields.put("reqUsimSn",			"USIM");
		maskFields.put("iccId",				"USIM");
		maskFields.put("reqUsimSnMask",		"USIM");
		maskFields.put("usimNo",			"USIM");
		maskFields.put("usimSn",			"USIM");

		//단말일련번호
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("intmSrlNo",			"DEV_NO");
		maskFields.put("reqPhoneSnMask",	"DEV_NO");
		maskFields.put("dvcIntmSrlNo" , 	"DEV_NO");
		maskFields.put("dvcIntmSrNo" , 		"DEV_NO");
		maskFields.put("phoneSn",			"DEV_NO");
		maskFields.put("lstIntmSrlNo",		"DEV_NO");
		maskFields.put("fstIntmSrlNo",		"DEV_NO");
		maskFields.put("fstModelSrlNum",	"DEV_NO");
		maskFields.put("lstModelSrlNum",	"DEV_NO");
		maskFields.put("ncn",				"DEV_NO");
		maskFields.put("intmMdlSrl",		"DEV_NO");
        maskFields.put("cIntmSrlNo",		"DEV_NO");		//일련번호(해지)
		maskFields.put("lstModelSerialNo",	"ESN");

		//회원ID
		maskFields.put("userId",			"SYSTEM_ID");
		maskFields.put("userid",			"SYSTEM_ID");
		maskFields.put("portalUserId",		"SYSTEM_ID");
		
		//전화번호 중간자리
		maskFields.put("telMn1", 			"PASSWORD");
		maskFields.put("telMn2", 			"PASSWORD");
		maskFields.put("cstmrTelMn",		"PASSWORD");
		maskFields.put("cstmrMobileMn",		"PASSWORD");
		maskFields.put("moveMobileMn",		"PASSWORD");
		maskFields.put("dlvryTelMn",		"PASSWORD");
		maskFields.put("dlvryMobileMn",		"PASSWORD");
		
		//상세주소
		maskFields.put("addrDtl", 			"PASSWORD");
		maskFields.put("maskAddrDtl", 		"PASSWORD");
		maskFields.put("dtlAddr", 			"PASSWORD");
		maskFields.put("cstmrAddrDtl",		"PASSWORD");
		maskFields.put("dlvryAddrDtl",		"PASSWORD");
		maskFields.put("addr2", 			"PASSWORD");
		maskFields.put("addressDtl", 		"PASSWORD");
    	maskFields.put("roadnAdrDtlSbst",	"PASSWORD");	//도로명주소상세내용
    	maskFields.put("arnoAdrDtlSbst",	"PASSWORD");	//지번주소상세내용
    	maskFields.put("arnoAdrBjNm",		"PASSWORD");	//지번주소번지명
    	maskFields.put("arnoAdrDongCd",		"PASSWORD");	//지번주소동코드   
    	maskFields.put("roadnAdrBldgNo",	"PASSWORD");	//도로명주소건물번호
		maskFields.put("subAdrSecondaryLn",	"PASSWORD");
		
    	/*************************** 선불 **************************/
		//이름
 	 	maskFields.put("cmsUserName", 			"CUST_NAME"); // CMS예금주명 
	 	maskFields.put("reqUserName", 			"CUST_NAME"); // 고객이름
		maskFields.put("cmsUserNameMsk",		"CUST_NAME");
		maskFields.put("adNameMsk",				"CUST_NAME");
		maskFields.put("subLinkNameMsk",		"CUST_NAME");
		maskFields.put("customerLinkNameMsk",	"CUST_NAME");
		maskFields.put("bankUserName", 			"CUST_NAME"); // 이름 
		maskFields.put("requester", 			"CUST_NAME"); // 이름 
		maskFields.put("banLinkNameMsk",		"CUST_NAME");

		//전화번호
		maskFields.put("calledNumber",			"MOBILE_PHO");
		maskFields.put("smsPhoneMsk",			"MOBILE_PHO");
	 	maskFields.put("reqTelNo", 				"MOBILE_PHO");	
		maskFields.put("cstmrTelNo",			"TEL_NO");
		maskFields.put("subscriberNoMsk",		"MOBILE_PHO");

		//주민번호
		maskFields.put("bankPeopleId", 			"SSN"); //    주민번호
		maskFields.put("cmsUserSsnMsk",			"SSN");		
		maskFields.put("adSsnMsk",				"SSN");
		maskFields.put("userSsnMsk",			"SSN");
		maskFields.put("customerSsnMsk",		"SSN");
		maskFields.put("driverLicnsNoMsk",		"SSN");
		maskFields.put("bankAcctHolderIdMsk",	"SSN");
		
		//주소
		maskFields.put("adAddressMsk",			"ADDR");
		maskFields.put("subAdrSecondaryLnMsk",	"PASSWORD");
		maskFields.put("banAdrSecondaryLnMsk",	"PASSWORD");
		
		//계좌번호
		maskFields.put("bankAccount",			"ACCOUNT"); // 계좌번호 
	 	maskFields.put("cmsAccount", 			"ACCOUNT"); // 계좌번호 
		maskFields.put("cmsAccountMsk",			"ACCOUNT");
		
		//유심,단말일련번호
		maskFields.put("iccIdMsk",				"USIM");
		maskFields.put("intmSrlNoMsk",			"DEV_NO");
		
		//회원ID
		maskFields.put("remark",				"SYSTEM_ID");
		maskFields.put("adminNm",				"SYSTEM_ID");
		
		return maskFields;
	}
	
	public Object setMask(Object obj, HashMap pMaskFields, List<MaskingVO> lCmnMskGrp) {
		try{
			MaskingVO lCmnMskGrpRow = lCmnMskGrp.get(0);
			if( StringUtils.defaultString(lCmnMskGrpRow.getMskAuthYn(),"").equals("Y")) {
				return obj;
			}
		}catch( Exception e) {
			return obj;
		}
		
		Iterator itr =  pMaskFields.keySet().iterator();
		Map chgMasking = new HashMap<String, String>();
		while (itr.hasNext()) {
		    String lMaskFieldsKey = (String)itr.next();
		    String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
		    
		    try{
				Field[] fields = obj.getClass().getDeclaredFields();
//				Method[] methods = obj.getClass().getDeclaredMethods();
				for(int i=0; i<=fields.length-1;i++){
					fields[i].setAccessible(true);
					if(fields[i].getName().equals(lMaskFieldsKey)){
						//-----------------------------------------
						// loop maskinfo from DB
						//-----------------------------------------
						for (MaskingVO item : lCmnMskGrp) {
							String lCmnMskGrpRowMskId = StringUtils.defaultString(item.getMskId(),"");  

							//-----------------------------------------
							// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
							//-----------------------------------------
							if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) ) {
								//v2018.11 PMD 적용 소스 수정
							   //String getMethodString = "get" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
								String setMethodString = "set" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
								
								String lResultListRowValue = fields[i].get(obj) != null ? String.valueOf(fields[i].get(obj)) : "";
//								methods[i].invoke(obj, getMaskString(lResultListRowValue, item));
								chgMasking.put(setMethodString, getMaskString(lResultListRowValue, item));
							}
						}
					}
				}
			}catch ( Exception e) {
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
				//System.out.println("Connection Exception occurred");
				//20210722 pmd소스코드수정
				logger.error("Connection Exception occurred");
			}
		}
		Method[] methods = obj.getClass().getDeclaredMethods();
		for(int i=0; i<methods.length; i++) {
			if(chgMasking.get(methods[i].getName()) != null) {
				try {
					methods[i].invoke(obj, chgMasking.get(methods[i].getName()));
				} catch (Exception e) {
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
					//System.out.println("Connection Exception occurred");
					//20210722 pmd소스코드수정
					logger.error("Connection Exception occurred");
				}
			}
		}
		return obj;
	}

	

	/**
	 * <PRE>
	 * 1. MethodName: setMask
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:59:26
	 * </PRE>
	 * 		@return void
	 * 		@param p_resultList
	 * 		@param p_maskFields
	 * 		@param p_reqParamMap
	 * 		@throws Exception
	 */
	public void setMask(List<?> pResultList, HashMap pMaskFields, Map<String, Object> pReqParamMap) 
	{

		//-----------------------------------------
		// get user's mask information
		//-----------------------------------------
		List<?> lCmnMskGrp =  maskingMapper.selectList(pReqParamMap);
//		logger.debug(">>>>>>>>>>>>>>>>" + l_cmnMskGrp);
	
		//-----------------------------------------
		// if user.msk_auth_yn = "N' then return
		//-----------------------------------------
		try{
//			EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(0);
			Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(0);
			if( StringUtils.defaultString((String) lCmnMskGrpRow.get("mskAuthYn"),"").equals("Y"))
				return;
		}catch( Exception e)
		{
			return;
		}
		
		//-----------------------------------------
		// loop for result records to be masked
		//-----------------------------------------
		for ( int inx = 0 ; inx < pResultList.size(); inx++)
		{
				//-----------------------------------------
				// loop p_maskFields 
				//-----------------------------------------
				Iterator itr =  pMaskFields.keySet().iterator();
				while (itr.hasNext()) {
					String lMaskFieldsKey = (String)itr.next();
					String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
//					logger.debug("maskFields : " + lMaskFieldsKey + " - " + lMaskFieldsValue);
				
					// resultList의 row
//					EgovMap l_resultListRow = (EgovMap) p_resultList.get(inx);
					if( pResultList.get(inx) instanceof BaseVo){
						try{
							//-----------------------------------------
							// loop maskinfo from DB
							//-----------------------------------------
							for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
							{
								Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
								String lCmnMskGrpRowMskId = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  
								
								//-----------------------------------------
								// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
								//-----------------------------------------
								if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
								{
									String getMethodString = "get" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
									String setMethodString = "set" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
									
									//-----------------------------------------
									// getter를 구해서 -> 실행해서 -> value를 구함
									//-----------------------------------------
									Method[] methods = pResultList.get(inx).getClass().getDeclaredMethods();
									Object fieldValue = null;
									for(int knx=0; knx <=methods.length-1; knx++){
										if(getMethodString.equals(methods[knx].getName())){
											fieldValue = methods[knx].invoke(pResultList.get(inx), new Object[0]);
										}
									}
									
									String lMaskedString = getMaskString(StringUtils.defaultString((String) fieldValue,""), lCmnMskGrpRow);
									
									//-----------------------------------------
									// setter를 구해서 -> 실행해서 -> value를 set 함
									//-----------------------------------------
									for(int knx=0; knx <=methods.length-1; knx++){
										if(setMethodString.equals(methods[knx].getName())){
											//-----------------------------------------
											// masking 규칙 적용한 문자열 생성
											//-----------------------------------------
											Object[] inputParam =  { StringUtils.defaultString((String) lMaskedString,"") };
											methods[knx].invoke(pResultList.get(inx), inputParam );
										}
									}
								}
							}
						}catch ( Exception e)
						{
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
							//System.out.println("Connection Exception occurred");
							//20210722 pmd소스코드수정
							logger.error("Connection Exception occurred");
						}
					}else{
						
						Map lResultListRow = (Map) pResultList.get(inx);
						String lResultListRowValue = (String) lResultListRow.get(lMaskFieldsKey);
//						boolean l_isMask = true;
						
						try{
							//-----------------------------------------
							// loop maskinfo from DB
							//-----------------------------------------
							for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
							{
//								EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(jnx);
								Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
//								logger.debug("l_cmnMskGrpRow:" +l_cmnMskGrpRow);
								String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  
								//-----------------------------------------
								// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
								//-----------------------------------------
								if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
								{
									lResultListRow.put(lMaskFieldsKey, getMaskString(lResultListRowValue, lCmnMskGrpRow));
								}
							}
						}catch ( Exception e)
						{
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
							//System.out.println("Connection Exception occurred");
							//20210722 pmd소스코드수정
							logger.error("Connection Exception occurred");
						}
					}
				}
		}
	}

    public void setMask(List<?> pResultList, HashMap pMaskFields, BaseVo baseVo)
    {

        //-----------------------------------------
        // get user's mask information
        //-----------------------------------------
        List<?> lCmnMskGrp =  maskingMapper.selectListByBaseVo(baseVo);
//		logger.debug(">>>>>>>>>>>>>>>>" + l_cmnMskGrp);

        //-----------------------------------------
        // if user.msk_auth_yn = "N' then return
        //-----------------------------------------
        try{
//			EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(0);
            Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(0);
            if( StringUtils.defaultString((String) lCmnMskGrpRow.get("mskAuthYn"),"").equals("Y"))
                return;
        }catch( Exception e)
        {
            return;
        }

        //-----------------------------------------
        // loop for result records to be masked
        //-----------------------------------------
        for ( int inx = 0 ; inx < pResultList.size(); inx++)
        {
            //-----------------------------------------
            // loop p_maskFields
            //-----------------------------------------
            Iterator itr =  pMaskFields.keySet().iterator();
            while (itr.hasNext()) {
                String lMaskFieldsKey = (String)itr.next();
                String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
//					logger.debug("maskFields : " + lMaskFieldsKey + " - " + lMaskFieldsValue);

                // resultList의 row
//					EgovMap l_resultListRow = (EgovMap) p_resultList.get(inx);
                if( pResultList.get(inx) instanceof BaseVo){
                    try{
                        //-----------------------------------------
                        // loop maskinfo from DB
                        //-----------------------------------------
                        for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
                        {
                            Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
                            String lCmnMskGrpRowMskId = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");

                            //-----------------------------------------
                            // p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
                            //-----------------------------------------
                            if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
                            {
                                String getMethodString = "get" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
                                String setMethodString = "set" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);

                                //-----------------------------------------
                                // getter를 구해서 -> 실행해서 -> value를 구함
                                //-----------------------------------------
                                Method[] methods = pResultList.get(inx).getClass().getDeclaredMethods();
                                Object fieldValue = null;
                                for(int knx=0; knx <=methods.length-1; knx++){
                                    if(getMethodString.equals(methods[knx].getName())){
                                        fieldValue = methods[knx].invoke(pResultList.get(inx), new Object[0]);
                                    }
                                }

                                String lMaskedString = getMaskString(StringUtils.defaultString((String) fieldValue,""), lCmnMskGrpRow);

                                //-----------------------------------------
                                // setter를 구해서 -> 실행해서 -> value를 set 함
                                //-----------------------------------------
                                for(int knx=0; knx <=methods.length-1; knx++){
                                    if(setMethodString.equals(methods[knx].getName())){
                                        //-----------------------------------------
                                        // masking 규칙 적용한 문자열 생성
                                        //-----------------------------------------
                                        Object[] inputParam =  { StringUtils.defaultString((String) lMaskedString,"") };
                                        methods[knx].invoke(pResultList.get(inx), inputParam );
                                    }
                                }
                            }
                        }
                    }catch ( Exception e)
                    {
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
                        //System.out.println("Connection Exception occurred");
                        //20210722 pmd소스코드수정
                        logger.error("Connection Exception occurred");
                    }
                }else{

                    Map lResultListRow = (Map) pResultList.get(inx);
                    String lResultListRowValue = (String) lResultListRow.get(lMaskFieldsKey);
//						boolean l_isMask = true;

                    try{
                        //-----------------------------------------
                        // loop maskinfo from DB
                        //-----------------------------------------
                        for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
                        {
//								EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(jnx);
                            Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
//								logger.debug("l_cmnMskGrpRow:" +l_cmnMskGrpRow);
                            String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");
                            //-----------------------------------------
                            // p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
                            //-----------------------------------------
                            if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
                            {
                                lResultListRow.put(lMaskFieldsKey, getMaskString(lResultListRowValue, lCmnMskGrpRow));
                            }
                        }
                    }catch ( Exception e)
                    {
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
                        //System.out.println("Connection Exception occurred");
                        //20210722 pmd소스코드수정
                        logger.error("Connection Exception occurred");
                    }
                }
            }
        }
    }

	/**
	 * 1. MethodName: setOnlyMask
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 강제 마스킹 
	 * 4. 작성자	: 장익준
	 * 5. 작성일	: 2018. 9. 11.
	 */
	public void setOnlyMask(List<?> pResultList, HashMap pMaskFields, Map<String, Object> pReqParamMap) 
	{
		
		List<?> lCmnMskGrp =  maskingMapper.selectList(pReqParamMap);
		//-----------------------------------------
		// loop for result records to be masked
		//-----------------------------------------
		for ( int inx = 0 ; inx < pResultList.size(); inx++)
		{
				//-----------------------------------------
				// loop p_maskFields 
				//-----------------------------------------
				Iterator itr =  pMaskFields.keySet().iterator();
				while (itr.hasNext()) {
					String lMaskFieldsKey = (String)itr.next();
					String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
				
					// resultList의 row
					if( pResultList.get(inx) instanceof BaseVo){
						try{
							//-----------------------------------------
							// loop maskinfo from DB
							//-----------------------------------------
							for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
							{
								Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
								String lCmnMskGrpRowMskId = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  
								
								//-----------------------------------------
								// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
								//-----------------------------------------
								if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
								{
									String getMethodString = "get" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
									String setMethodString = "set" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
									
									//-----------------------------------------
									// getter를 구해서 -> 실행해서 -> value를 구함
									//-----------------------------------------
									Method[] methods = pResultList.get(inx).getClass().getDeclaredMethods();
									Object fieldValue = null;
									for(int knx=0; knx <=methods.length-1; knx++){
										if(getMethodString.equals(methods[knx].getName())){
											fieldValue = methods[knx].invoke(pResultList.get(inx), new Object[0]);
										}
									}
									
									String lMaskedString = getMaskString(StringUtils.defaultString((String) fieldValue,""), lCmnMskGrpRow);
									
									//-----------------------------------------
									// setter를 구해서 -> 실행해서 -> value를 set 함
									//-----------------------------------------
									for(int knx=0; knx <=methods.length-1; knx++){
										if(setMethodString.equals(methods[knx].getName())){
											//-----------------------------------------
											// masking 규칙 적용한 문자열 생성
											//-----------------------------------------
											Object[] inputParam =  { StringUtils.defaultString((String) lMaskedString,"") };
											methods[knx].invoke(pResultList.get(inx), inputParam );
										}
									}
								}
							}
						}catch ( Exception e)
						{
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
							//System.out.println("Connection Exception occurred");
							//20210722 pmd소스코드수정
							logger.error("Connection Exception occurred");
						}
					}else{
						
						Map lResultListRow = (Map) pResultList.get(inx);
						String lResultListRowValue = (String) lResultListRow.get(lMaskFieldsKey);
//						boolean l_isMask = true;
						
						try{
							//-----------------------------------------
							// loop maskinfo from DB
							//-----------------------------------------
							for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
							{
//								EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(jnx);
								Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
//								logger.debug("l_cmnMskGrpRow:" +l_cmnMskGrpRow);
								String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  
								//-----------------------------------------
								// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
								//-----------------------------------------
								if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
								{
									lResultListRow.put(lMaskFieldsKey, getMaskString(lResultListRowValue, lCmnMskGrpRow));
								}
							}
						}catch ( Exception e)
						{
//							20200512 소스코드점검 수정
//					    	e.printStackTrace();
						//	System.out.println("Connection Exception occurred");
							//20210722 pmd소스코드수정
							logger.error("Connection Exception occurred");
						}
					}
				}
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: setMask
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 조회된 정보 중, pParam에 등록된 정보가 존재한다면 Masking 처리 제외
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2015. 8. 24
	 * </PRE>
	 * 		@return void
	 * 		@param p_resultList
	 * 		@param p_maskFields
	 * 		@param p_reqParamMap
	 * 		@param p_Param
	 * 		@throws Exception
	 */
	public void setMask(List<?> pResultList, HashMap pMaskFields, Map<String, Object> pReqParamMap, HashMap pParam) 
	{

		//-----------------------------------------
		// get user's mask information
		//-----------------------------------------
		List<?> lCmnMskGrp =  maskingMapper.selectList(pReqParamMap);
		
		
		//-----------------------------------------
		// if user.msk_auth_yn = "N' then return
		//-----------------------------------------
		try{
//			EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(0);
			Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(0);
			if( StringUtils.defaultString((String) lCmnMskGrpRow.get("mskAuthYn"),"").equals("Y"))
				return;
		}catch( Exception e)
		{
			return;
		}
		
		//-----------------------------------------
		// loop for result records to be masked
		//-----------------------------------------
		for ( int inx = 0 ; inx < pResultList.size(); inx++)
		{
			boolean paramFlag = false;
			
			// Masking 제외 체크
			// Masking 제외에 하나라도 걸리면 Row 전체가 Masking 제외
			if(pParam != null) {
				
				Iterator itr =  pParam.keySet().iterator();
				while (itr.hasNext()) {
				    String unMaskFieldsKey = (String)itr.next();
				    String unMaskFieldsValue = (String) pParam.get(unMaskFieldsKey);

					Map lResultListRow = (Map) pResultList.get(inx);
					String lResultListRowValue = (String) lResultListRow.get(unMaskFieldsKey);
					
					if(lResultListRowValue.equals(unMaskFieldsValue)) {
						paramFlag = true;
						break;
					}
				}
			}
			
			if(paramFlag) continue;
			
			//-----------------------------------------
			// loop p_maskFields 
			//-----------------------------------------
			Iterator itr =  pMaskFields.keySet().iterator();
			while (itr.hasNext()) {
			    String lMaskFieldsKey = (String)itr.next();
			    String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);

				Map lResultListRow = (Map) pResultList.get(inx);
				String lResultListRowValue = (String) lResultListRow.get(lMaskFieldsKey);

				try{
					//-----------------------------------------
					// loop maskinfo from DB
					//-----------------------------------------
					for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
					{
						Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);

						String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  

						//-----------------------------------------
						// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
						//-----------------------------------------
						if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
						{	
							lResultListRow.put(lMaskFieldsKey, getMaskString(lResultListRowValue, lCmnMskGrpRow));
						}
					}

				}catch ( Exception e)
				{
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
					//System.out.println("Connection Exception occurred");
					//20210722 pmd소스코드수정
					logger.error("Connection Exception occurred");
				}
			}
		}
	}
	
	

	/**
	 * <PRE>
	 * 1. MethodName: setMask
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 8. 26. 오후 3:36:38
	 * </PRE>
	 * 		@return void
	 * 		@param p_resultVo
	 * 		@param p_maskFields
	 * 		@param p_reqParamMap
	 * 		@throws Exception
	 */
	public void setMask(BaseVo pResultVo, HashMap pMaskFields, Map<String, Object> pReqParamMap) 
	{
		

		//-----------------------------------------
		// get user's mask information
		//-----------------------------------------
		List<?> lCmnMskGrp =  maskingMapper.selectList(pReqParamMap);
//		logger.debug(">>>>>>>>>>>>>>>>" + l_cmnMskGrp);
	
		//-----------------------------------------
		// if user.msk_auth_yn = "N' then return
		//-----------------------------------------
		try{
//			EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(0);
			Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(0);
			if( StringUtils.defaultString((String) lCmnMskGrpRow.get("mskAuthYn"),"").equals("Y"))
				return;
		}catch( Exception e)
		{
			return;
		}
		
		//-----------------------------------------
		// loop p_maskFields
		//-----------------------------------------
		Iterator itr =  pMaskFields.keySet().iterator();
		while (itr.hasNext()) {
		    String lMaskFieldsKey = (String)itr.next();
		    String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
//			boolean l_isMask = true;
			
			
			try{
				//-----------------------------------------
				// loop maskinfo from DB
				//-----------------------------------------
				for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
				{
//					EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(jnx);
					Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
					String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  

					
					//-----------------------------------------
					// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
					//-----------------------------------------
					if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
					{
						String getMethodString = "get" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
						String setMethodString = "set" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
						
						//-----------------------------------------
						// getter를 구해서 -> 실행해서 -> value를 구함
						//-----------------------------------------
						Method[] methods = pResultVo.getClass().getDeclaredMethods();
						Object fieldValue = null;
						for(int knx=0; knx <=methods.length-1; knx++){
						      if(getMethodString.equals(methods[knx].getName())){
//									System.out.println(">>>>>>>>>>>invoke : "+getMethodString);
									fieldValue = methods[knx].invoke(pResultVo, new Object[0]);
//									System.out.println(">>>>>>>>>>>return : "+ StringUtils.defaultString((String) fieldValue,""));
						      }
						}
						
						String lMaskedString = getMaskString(StringUtils.defaultString((String) fieldValue,""), lCmnMskGrpRow);
						
						//-----------------------------------------
						// setter를 구해서 -> 실행해서 -> value를 set 함
						//-----------------------------------------
						for(int knx=0; knx <=methods.length-1; knx++){
						      if(setMethodString.equals(methods[knx].getName())){
//									System.out.println(">>>>>>>>>>>invoke : "+getMethodString);
									
									//-----------------------------------------
									// masking 규칙 적용한 문자열 생성
									//-----------------------------------------
									Object[] inputParam =  { StringUtils.defaultString((String) lMaskedString,"") };
									methods[knx].invoke(pResultVo, inputParam );
						      }
						}
					}
				}
				
				
			}catch ( Exception e)
			{
//				20200512 소스코드점검 수정
//		    	e.printStackTrace();
				//System.out.println("Connection Exception occurred");
				//20210722 pmd소스코드수정
				logger.error("Connection Exception occurred");
			}
		}

	}
	
	/**
	 * <PRE>
	 * 1. MethodName: getMaskString
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 8. 26. 오후 3:36:43
	 * </PRE>
	 * 		@return String
	 * 		@param p_resultListRowValue
	 * 		@param l_cmnMskGrpRow
	 * 		@return
	 */
//	public String getMaskString(String p_resultListRowValue, EgovMap l_cmnMskGrpRow)
	public String getMaskString(String pResultListRowValue, Map lCmnMskGrpRow)
	{
		/** 20230518 PMD 처리 */
		if ( "".equals(pResultListRowValue) || pResultListRowValue == null)
			return null;
		
		String lRtnString     = "";
		String lMskGrpId      = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskGrpId"),"");  
		String lMskLnth       = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskLnth"),"");   
		String lMskLoc        = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskLoc"),"");
		String lMskStrtLoc    = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskStrtLoc"),"");
		String lMaskChar      = "*";
		
		if (	   lMskGrpId.equals("SSN") 
//				|| lMskGrpId.equals("NAME") 
//				|| lMskGrpId.equals("EMAIL") 
				|| lMskGrpId.equals("ACCOUNT")
				|| lMskGrpId.equals("CORPORATE")
				|| lMskGrpId.equals("PASSPORT")
				|| lMskGrpId.equals("CREDIT_CAR") 
				|| lMskGrpId.equals("CARD_EXP") 
				|| lMskGrpId.equals("DEV_NO") 
				|| lMskGrpId.equals("IMEI") 
				|| lMskGrpId.equals("ESN") 
				|| lMskGrpId.equals("USIM") 
				|| lMskGrpId.equals("INTERNET_I") 
				|| lMskGrpId.equals("SYSTEM_ID") 
				|| lMskGrpId.equals("PASSWORD") 
			)
		{

//			String lExpressionString =   (lMskLoc.equals("1") ? "^":"" ) +  (lMskLoc.equals("1") ? "(.{" + ( Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ".{"  + lMskLnth  + "}" +  (lMskLoc.equals("-99") ? "(.{" + (Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ( lMskLoc.equals("-99") ? "$":"" );
//			String lTargetString =  String.format("$1%" + lMskLnth + "s$2", lMaskChar).replaceAll(" ", lMaskChar) ;
////			logger.debug("[" +  p_resultListRowValue.replaceAll( lExpressionString, lTargetString )+ "]<= exp:" + lExpressionString + "=>" + lTargetString);
//			l_rtnString = p_resultListRowValue.replaceAll( lExpressionString, lTargetString );
			
			StringBuilder lResultListRowValueWork = new StringBuilder(pResultListRowValue);
			if ( ! lMskLoc.equals("1"))
			{
				lResultListRowValueWork.reverse();
			}
			for ( int inx = Integer.parseInt(lMskStrtLoc) -1 ; 
				   inx < lResultListRowValueWork.length() && inx - Integer.parseInt(lMskStrtLoc) + 1 < Integer.parseInt(lMskLnth) ;
				   inx++)
			{
				lResultListRowValueWork.setCharAt(inx, '*');
			}
			if ( ! lMskLoc.equals("1"))
			{
				lResultListRowValueWork.reverse();
			}
			lRtnString = lResultListRowValueWork.toString();

		}else if (lMskGrpId.equals("NAME")  )
		{
			if (pResultListRowValue.length() >= 3) {
				StringBuffer msk = new StringBuffer("");
				for (int i=0; i<pResultListRowValue.length()-2; i++) {
					msk.append("*");
				}
				lRtnString = pResultListRowValue.substring(0,1) + msk + pResultListRowValue.substring(pResultListRowValue.length()-1);
			} else {
				lRtnString = pResultListRowValue.substring(0,pResultListRowValue.length()-1) + "*";
			}

		}else if (lMskGrpId.equals("EMAIL")  )
		{
			String [] splitEmail = pResultListRowValue.split("@");
			if (splitEmail.length > 1) {
				StringBuffer msk = new StringBuffer("");
				for (int i=0; i<splitEmail[0].length(); i++) {
					msk.append("*");
				}
				lRtnString = msk + "@" + splitEmail[1];
			}
			
		}else if (lMskGrpId.equals("PHONE")  )
		{
			if ( pResultListRowValue.contains("-"))
			{
				String phoneNum = pResultListRowValue.replaceAll("-","");
				if (phoneNum.length() >= 11) {
					//-------------------------------------------------
					// 010-1234-5678 => 010-####-5678
					// 0505-1234-5678 => 0505-####-5678
					//-------------------------------------------------
					String lExpressionString =  "-[0-9]{4}-";
					String lTargetString =  "-****-"  ;
					lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
				} else if (phoneNum.length() == 9 || phoneNum.length() == 10) {
					if (phoneNum.substring(0,2).equals("02")) {
						if (phoneNum.length() == 9) {
							//-------------------------------------------------
							// 02-123-4567 => 02-###-4567
							//-------------------------------------------------
							String lExpressionString =  "-[0-9]{3}-";
							String lTargetString =  "-***-"  ;
							lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );				
						} else {
							//-------------------------------------------------
							// 02-1234-5678 => 02-####-5678
							//-------------------------------------------------
							String lExpressionString =  "-[0-9]{4}-";
							String lTargetString =  "-****-"  ;
							lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
						}
					} else {
						//-------------------------------------------------
						// 031-123-4567 => 031-###-4567
						//-------------------------------------------------
						String lExpressionString =  "-[0-9]{3}-";
						String lTargetString =  "-***-"  ;
						lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
					}
				}
				//-------------------------------------------------
				// 010-1234-5678 => 010-12##-#678
				//-------------------------------------------------
//				String lExpressionString =  "[0-9]{2}-[0-9]([0-9]*)$";
//				String lTargetString =  "**-*$1"  ;
//				lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
			}else
			{
				if (pResultListRowValue.length() == 11) {
					//-------------------------------------------------
					// 01012345678 => 010####5678
					//-------------------------------------------------
					lRtnString = pResultListRowValue.substring(0,3) + "****" + pResultListRowValue.substring(7);
				} else if (pResultListRowValue.length() >= 12) {
					//-------------------------------------------------
					// 050512345678 => 0505####5678
					//-------------------------------------------------
					lRtnString = pResultListRowValue.substring(0,4) + "****" + pResultListRowValue.substring(8);
				} else if (pResultListRowValue.length() == 9 || pResultListRowValue.length() == 10) {
					if (pResultListRowValue.substring(0,2).equals("02")) {
						if (pResultListRowValue.length() == 9) {
							//-------------------------------------------------
							// 021234567 => 02###4567
							//-------------------------------------------------
							lRtnString = pResultListRowValue.substring(0,2) + "***" + pResultListRowValue.substring(5);						
						} else {
							//-------------------------------------------------
							// 0212345678 => 02####5678
							//-------------------------------------------------
							lRtnString = pResultListRowValue.substring(0,2) + "****" + pResultListRowValue.substring(6);
						}
					} else {
						//-------------------------------------------------
						// 0311234567 => 031###4567
						//-------------------------------------------------
						lRtnString = pResultListRowValue.substring(0,3) + "***" + pResultListRowValue.substring(6);
					}
				}
				//-------------------------------------------------
				// 01012345678 => 01012###678
				//-------------------------------------------------
//				lMskLoc ="-99";
//				lMskStrtLoc ="4";
//				lMskLnth ="3";
//				
//				String lExpressionString =   (lMskLoc.equals("1") ? "^":"" ) +  (lMskLoc.equals("1") ? "(.{" + ( Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ".{"  + lMskLnth  + "}" +  (lMskLoc.equals("-99") ? "(.{" + (Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ( lMskLoc.equals("-99") ? "$":"" );
//				String lTargetString =  String.format("$1%" + lMskLnth + "s$2", lMaskChar).replaceAll(" ", lMaskChar) ;
////				logger.debug("[" +  p_resultListRowValue.replaceAll( lExpressionString, lTargetString )+ "]<= exp:" + lExpressionString + "=>" + lTargetString);
//				lRtnString = pResultListRowValue.replaceAll( lExpressionString, lTargetString );

			}
			
		}else if (lMskGrpId.equals("ADDR")  )
		{
			//-------------------------------------------------
			// 서울 서대문동구동 창천면 3234-12문동구동" => "서울 서대문동구동 창천면 ##########
			// 시구동(로) 이하 전체 마스킹
			//-------------------------------------------------
			String regex = "(([가-힣]+(d|d(,|.)d|\\d|)+(동|면|리|로|길))(^구|)((d(~|-)d|d)(가|리|)|))([ ](산(d(~|-)d|d))|)|(([가-힣]|(d(~|-)d)|d|\\d)+(로|길))";			
			Matcher matcher = Pattern.compile(regex).matcher(pResultListRowValue);
			if(matcher.find()) {
		        String unMskAddr = pResultListRowValue.substring(0, matcher.end());
		        String mskAddr = pResultListRowValue.substring(matcher.end());
		        String result = unMskAddr + mskAddr.replaceAll("[\\S\\s]", "*");
				lRtnString =  result;
			} else {
				String lExpressionString =  "([동면리로])[^동면리로]*$";
				String lTargetString =  "$1******************" ;
				lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
			}

		}else if (lMskGrpId.equals("IP")  )
		{
			//-------------------------------------------------
			// 111.111.111.111 => ###.111.###.1111
			//-------------------------------------------------
//			String lExpressionString =  "^[0-9]+";
//			String lTargetString =  "###" ;
//			lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
//
//			lExpressionString =  "(^#+\\.)([0-9]+\\.)[0-9]+";
//			lTargetString =  "$1$2###" ;
//			lRtnString =   lRtnString.replaceAll( lExpressionString, lTargetString );
//			lRtnString = lRtnString.replaceAll( "#", "*" );

			//-------------------------------------------------
			// 111.111.111.111 => 111.111.###.1111
			//-------------------------------------------------
			String lExpressionString =  "(^[0-9]+\\.)([0-9]+\\.)[0-9]+";
			String lTargetString =  "$1$2***" ;
			lRtnString =   pResultListRowValue.replaceAll( lExpressionString, lTargetString );
		}else
		{
			lRtnString =  pResultListRowValue;
		}
		
		return lRtnString;
	}
	
	

	/**
	 * <PRE>
	 * 1. MethodName: getMaskString
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 오리지날 getMaskString 메소드의 파라미터 추가.
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2016. 11. 10
	 * </PRE>
	 * 		@return String
	 * 		@param p_resultListRowValue
	 * 		@param l_cmnMskGrpRow
	 * 		@return
	 */
	public String getMaskString(String pResultListRowValue, MaskingVO lCmnMskGrpRow) {
		/** 20230518 PMD 처리 */
		if ( "".equals(pResultListRowValue) ||  pResultListRowValue == null )
			return null;
		
		String lRtnString     = "";
		String lMskGrpId      = StringUtils.defaultString(lCmnMskGrpRow.getMskGrpId(),"");  
		String lMskLnth       = StringUtils.defaultString(lCmnMskGrpRow.getMskLnth(),"");   
		String lMskLoc        = StringUtils.defaultString(lCmnMskGrpRow.getMskLoc(),"");
		String lMskStrtLoc    = StringUtils.defaultString(lCmnMskGrpRow.getMskStrtLoc(),"");
		String lMaskChar      = "*";
		
		if (	   lMskGrpId.equals("SSN") 
//				|| lMskGrpId.equals("NAME") 
//				|| lMskGrpId.equals("EMAIL") 
				|| lMskGrpId.equals("ACCOUNT")
				|| lMskGrpId.equals("CORPORATE")
				|| lMskGrpId.equals("PASSPORT")
				|| lMskGrpId.equals("CREDIT_CAR") 
				|| lMskGrpId.equals("CARD_EXP") 
				|| lMskGrpId.equals("DEV_NO") 
				|| lMskGrpId.equals("IMEI") 
				|| lMskGrpId.equals("ESN") 
				|| lMskGrpId.equals("USIM") 
				|| lMskGrpId.equals("INTERNET_I") 
				|| lMskGrpId.equals("SYSTEM_ID") 
				|| lMskGrpId.equals("PASSWORD") 
			) {

//			String lExpressionString =   (lMskLoc.equals("1") ? "^":"" ) +  (lMskLoc.equals("1") ? "(.{" + ( Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ".{"  + lMskLnth  + "}" +  (lMskLoc.equals("-99") ? "(.{" + (Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ( lMskLoc.equals("-99") ? "$":"" );
//			String lTargetString =  String.format("$1%" + lMskLnth + "s$2", lMaskChar).replaceAll(" ", lMaskChar) ;
////			logger.debug("[" +  p_resultListRowValue.replaceAll( lExpressionString, lTargetString )+ "]<= exp:" + lExpressionString + "=>" + lTargetString);
//			l_rtnString = p_resultListRowValue.replaceAll( lExpressionString, lTargetString );
			
			StringBuilder lResultListRowValueWork = new StringBuilder(pResultListRowValue);
			if ( ! lMskLoc.equals("1")) {
				lResultListRowValueWork.reverse();
			}
			for ( int inx = Integer.parseInt(lMskStrtLoc) -1 ; 
				   inx < lResultListRowValueWork.length() && inx - Integer.parseInt(lMskStrtLoc) + 1 < Integer.parseInt(lMskLnth) ;
				   inx++) {
				lResultListRowValueWork.setCharAt(inx, '*');
			}
			if ( ! lMskLoc.equals("1")) {
				lResultListRowValueWork.reverse();
			}
			lRtnString = lResultListRowValueWork.toString();

		}else if (lMskGrpId.equals("NAME")  )
		{
			if (pResultListRowValue.length() >= 3) {
				StringBuffer msk = new StringBuffer("");
				for (int i=0; i<pResultListRowValue.length()-2; i++) {
					msk.append("*");
				}
				lRtnString = pResultListRowValue.substring(0,1) + msk + pResultListRowValue.substring(pResultListRowValue.length()-1);
			} else {
				lRtnString = pResultListRowValue.substring(0,pResultListRowValue.length()-1) + "*";
			}

		}else if (lMskGrpId.equals("EMAIL")  )
		{
			String [] splitEmail = pResultListRowValue.split("@");
			if (splitEmail.length > 1) {
				StringBuffer msk = new StringBuffer("");
				for (int i=0; i<splitEmail[0].length(); i++) {
					msk.append("*");
				}
				lRtnString = msk + "@" + splitEmail[1];
			}
			
			
		}else if (lMskGrpId.equals("PHONE") )
		{
			if ( pResultListRowValue.contains("-"))
			{
				String phoneNum = pResultListRowValue.replaceAll("-","");
				if (phoneNum.length() >= 11) {
					//-------------------------------------------------
					// 010-1234-5678 => 010-####-5678
					// 0505-1234-5678 => 0505-####-5678
					//-------------------------------------------------
					String lExpressionString =  "-[0-9]{4}-";
					String lTargetString =  "-****-"  ;
					lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
				} else if (phoneNum.length() == 9 || phoneNum.length() == 10) {
					if (phoneNum.substring(0,2).equals("02")) {
						if (phoneNum.length() == 9) {
							//-------------------------------------------------
							// 02-123-4567 => 02-###-4567
							//-------------------------------------------------
							String lExpressionString =  "-[0-9]{3}-";
							String lTargetString =  "-***-"  ;
							lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );				
						} else {
							//-------------------------------------------------
							// 02-1234-5678 => 02-####-5678
							//-------------------------------------------------
							String lExpressionString =  "-[0-9]{4}-";
							String lTargetString =  "-****-"  ;
							lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
						}
					} else {
						//-------------------------------------------------
						// 031-123-4567 => 031-###-4567
						//-------------------------------------------------
						String lExpressionString =  "-[0-9]{3}-";
						String lTargetString =  "-***-"  ;
						lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
					}
				}
				//-------------------------------------------------
				// 010-1234-5678 => 010-12##-#678
				//-------------------------------------------------
//				String lExpressionString =  "[0-9]{2}-[0-9]([0-9]*)$";
//				String lTargetString =  "**-*$1"  ;
//				lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
			}else
			{
				if (pResultListRowValue.length() == 11) {
					//-------------------------------------------------
					// 01012345678 => 010####5678
					//-------------------------------------------------
					lRtnString = pResultListRowValue.substring(0,3) + "****" + pResultListRowValue.substring(7);
				} else if (pResultListRowValue.length() >= 12) {
					//-------------------------------------------------
					// 050512345678 => 0505####5678
					//-------------------------------------------------
					lRtnString = pResultListRowValue.substring(0,4) + "****" + pResultListRowValue.substring(8);
				} else if (pResultListRowValue.length() == 9 || pResultListRowValue.length() == 10) {
					if (pResultListRowValue.substring(0,2).equals("02")) {
						if (pResultListRowValue.length() == 9) {
							//-------------------------------------------------
							// 021234567 => 02###4567
							//-------------------------------------------------
							lRtnString = pResultListRowValue.substring(0,2) + "***" + pResultListRowValue.substring(5);						
						} else {
							//-------------------------------------------------
							// 0212345678 => 02####5678
							//-------------------------------------------------
							lRtnString = pResultListRowValue.substring(0,2) + "****" + pResultListRowValue.substring(6);
						}
					} else {
						//-------------------------------------------------
						// 0311234567 => 031###4567
						//-------------------------------------------------
						lRtnString = pResultListRowValue.substring(0,3) + "***" + pResultListRowValue.substring(6);
					}
				}
				//-------------------------------------------------
				// 01012345678 => 01012###678
				//-------------------------------------------------
//				lMskLoc ="-99";
//				lMskStrtLoc ="4";
//				lMskLnth ="3";
//				
//				String lExpressionString =   (lMskLoc.equals("1") ? "^":"" ) +  (lMskLoc.equals("1") ? "(.{" + ( Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ".{"  + lMskLnth  + "}" +  (lMskLoc.equals("-99") ? "(.{" + (Integer.parseInt(lMskStrtLoc) -1 ) + "})":"(.{0})" ) + ( lMskLoc.equals("-99") ? "$":"" );
//				String lTargetString =  String.format("$1%" + lMskLnth + "s$2", lMaskChar).replaceAll(" ", lMaskChar) ;
////				logger.debug("[" +  p_resultListRowValue.replaceAll( lExpressionString, lTargetString )+ "]<= exp:" + lExpressionString + "=>" + lTargetString);
//				lRtnString = pResultListRowValue.replaceAll( lExpressionString, lTargetString );

			}
			
		} else if (lMskGrpId.equals("ADDR")) {
			//-------------------------------------------------
			// 서울 서대문동구동 창천면 3234-12문동구동" => "서울 서대문동구동 창천면 ##########
			// 시구동(로) 이하 전체 마스킹
			//-------------------------------------------------
			String regex = "(([가-힣]+(d|d(,|.)d|\\d|)+(동|면|리|로|길))(^구|)((d(~|-)d|d)(가|리|)|))([ ](산(d(~|-)d|d))|)|(([가-힣]|(d(~|-)d)|d|\\d)+(로|길))";			
			Matcher matcher = Pattern.compile(regex).matcher(pResultListRowValue);
			if(matcher.find()) {
		        String unMskAddr = pResultListRowValue.substring(0, matcher.end());
		        String mskAddr = pResultListRowValue.substring(matcher.end());
		        String result = unMskAddr + mskAddr.replaceAll("[\\S\\s]", "*");
				lRtnString =  result;
			} else {
				String lExpressionString =  "([동면리로])[^동면리로]*$";
				String lTargetString =  "$1******************" ;
				lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
			}

		} else if (lMskGrpId.equals("IP")) {
			//-------------------------------------------------
			// 111.111.111.111 => ###.111.###.1111
			//-------------------------------------------------
//			String lExpressionString =  "^[0-9]+";
//			String lTargetString =  "###" ;
//			lRtnString =  pResultListRowValue.replaceAll( lExpressionString, lTargetString );
//
//			lExpressionString =  "(^#+\\.)([0-9]+\\.)[0-9]+";
//			lTargetString =  "$1$2###" ;
//			lRtnString =   lRtnString.replaceAll( lExpressionString, lTargetString );
//			lRtnString = lRtnString.replaceAll( "#", "*" );

			//-------------------------------------------------
			// 111.111.111.111 => 111.111.###.1111
			//-------------------------------------------------
			String lExpressionString =  "(^[0-9]+\\.)([0-9]+\\.)[0-9]+";
			String lTargetString =  "$1$2***" ;
			lRtnString =   pResultListRowValue.replaceAll( lExpressionString, lTargetString );
		} else {
			lRtnString =  pResultListRowValue;
		}
		
		return lRtnString;
	}

    public String getMaskString(String value, String maskGrpId, String sessionUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("SESSION_USER_ID", sessionUserId);
        List<MaskingVO> maskingVOList = maskingMapper.selectListNotEgovMap(paramMap);

        if (!maskingVOList.isEmpty() && "Y".equals(maskingVOList.get(0).getMskAuthYn())) {
            return value;
        }

        MaskingVO target = new MaskingVO();
        if (maskingVOList.isEmpty()) {
            MaskingVO maskingVO = new MaskingVO();
            maskingVO.setMskGrpId(maskGrpId);
            target = maskingVO;
        } else {
            for (MaskingVO maskingVO : maskingVOList) {
                if (maskGrpId.equals(maskingVO.getMskGrpId())) {
                    target =  maskingVO;
                    break;
                }
            }
        }

        return this.getMaskString(value, target);
    }

	/**
	 * <PRE>
	 * 1. MethodName: convertMapToObject
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 8. 26. 오후 3:36:47
	 * </PRE>
	 * 		@return Object
	 * 		@param map
	 * 		@param objClass
	 * 		@return
	 */
//	public static Object convertMapToObject(Map map, Object objClass){
//		String keyAttribute = null;
//		String setMethodString = "set";
//		
//		String methodString = null;
//		Iterator itr = map.keySet().iterator();
//		
//		while(itr.hasNext()){
//			  keyAttribute = (String) itr.next();
//			  methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
//			  try {
//			      Method[] methods = objClass.getClass().getDeclaredMethods();
//			      for(int i=0;i<=methods.length-1;i++){
//			          if(methodString.equals(methods[i].getName())){
//			              //logger.debug("invoke : "+methodString);
//			              methods[i].invoke(objClass, map.get(keyAttribute));
//			          }
//			      }
//			  } catch (SecurityException e) {
//			      e.printStackTrace();
//			  } catch (IllegalAccessException e) {
//			      e.printStackTrace();
//			  } catch (IllegalArgumentException e) {
//			      e.printStackTrace();
//	//		  } catch (InvocationTargetException e) {
//	//		      e.printStackTrace();
//  			  } catch (Exception e) {
//  			      e.printStackTrace();
//			  }	
//		}
//		return objClass;
//	}


}


//public static Map ConverObjectToMap(Object obj){
//    try {
//        //Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
//        Field[] fields = obj.getClass().getDeclaredFields();
//        Map resultMap = new HashMap();
//        for(int i=0; i<=fields.length-1;i++){
//            fields[i].setAccessible(true);
//            resultMap.put(fields[i].getName(), fields[i].get(obj));
//        }
//        return resultMap;
//    } catch (IllegalArgumentException e) {
//        e.printStackTrace();
//    } catch (IllegalAccessException e) {
//        e.printStackTrace();
//    }
//    return null;
//}
//
//public static Object convertMapToObject(Map map, Object objClass){
//    String keyAttribute = null;
//    String setMethodString = "set";
//    String methodString = null;
//    Iterator itr = map.keySet().iterator();
//    while(itr.hasNext()){
//        keyAttribute = (String) itr.next();
//        methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
//        try {
//            Method[] methods = objClass.getClass().getDeclaredMethods();
//            for(int i=0;i<=methods.length-1;i++){
//                if(methodString.equals(methods[i].getName())){
//                    System.out.println("invoke : "+methodString);
//                    methods[i].invoke(objClass, map.get(keyAttribute));
//                }
//            }
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//    return objClass;
//}

