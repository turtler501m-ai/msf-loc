package com.ktis.msp.batch.manager.common.masking;


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

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.BaseVo;

import egovframework.rte.psl.dataaccess.util.EgovMap;

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
	

	/**
	 * 1. MethodName: getMaskFields
	 * 2. ClassName	: MaskingService
	 * 3. Commnet	: 공통 마스킹 필드 (22.10.12 기준 사용중인 모든 마스킹 대상 컬럼 취합)
	 * 4. 작성자	: 박준성
	 * 5. 작성일	: 2022. 10. 12.
	 */
	public HashMap<String, String> getMaskFields() {
		HashMap<String, String> maskFields = new HashMap<String, String>();
		maskFields.put("sublinkname",		"CUST_NAME");
		maskFields.put("subLinkName",		"CUST_NAME");
		maskFields.put("custnm", 			"CUST_NAME");
		maskFields.put("custNm", 			"CUST_NAME");
		maskFields.put("reqcustnm", 		"CUST_NAME");
		maskFields.put("reqCustNm", 		"CUST_NAME");
		maskFields.put("custname",			"CUST_NAME");
		maskFields.put("cstmrname",			"CUST_NAME");
		maskFields.put("cstmrName",			"CUST_NAME");
		maskFields.put("dlvryName",			"CUST_NAME");
		maskFields.put("dlvryname",			"CUST_NAME");
		maskFields.put("reqAccountName",	"CUST_NAME");
		maskFields.put("reqaccountname",	"CUST_NAME");
		maskFields.put("reqCardName",		"CUST_NAME");
		maskFields.put("reqcardname",		"CUST_NAME");
		maskFields.put("minorAgentName",	"CUST_NAME");
		maskFields.put("minoragentname",	"CUST_NAME");
		maskFields.put("jrdclagentname",	"CUST_NAME");
		maskFields.put("realusecustnm",		"CUST_NAME");
		maskFields.put("customerlinkname",	"CUST_NAME");
		maskFields.put("cstmrName2",		"CUST_NAME");
		maskFields.put("cstmrNameMask",		"CUST_NAME");
		maskFields.put("cstmrnm",			"CUST_NAME");
		maskFields.put("cstmrNm",			"CUST_NAME");
		maskFields.put("maskMngNm", 		"CUST_NAME");
		maskFields.put("fstHpcUsrNm", 		"CUST_NAME");
		maskFields.put("sndHpcUsrNm", 		"CUST_NAME");
		maskFields.put("thdHpcUsrNm", 		"CUST_NAME");
		maskFields.put("talkNm", 		"CUST_NAME");
		
		maskFields.put("telNum", 			"MOBILE_PHO");
		maskFields.put("reqTelNum", 		"MOBILE_PHO");	
		maskFields.put("subscriberno",		"MOBILE_PHO");
		maskFields.put("subscriberNo",		"MOBILE_PHO");
		maskFields.put("mobileNo",			"MOBILE_PHO");
		
		
		
		maskFields.put("telno",				"TEL_NO");
		maskFields.put("minoragenttel",		"MOBILE_PHO");
		maskFields.put("minorAgentTel",		"TEL_NO");
		maskFields.put("jrdclagenttel",		"MOBILE_PHO");
		maskFields.put("faxnum",			"MOBILE_PHO");
		maskFields.put("dlvrytel",			"MOBILE_PHO");
		maskFields.put("dlvryTel",			"TEL_NO");
		maskFields.put("cstmrmobile",		"MOBILE_PHO");	
		maskFields.put("cstmrMobile",		"MOBILE_PHO");
		maskFields.put("dlvrymobile",		"MOBILE_PHO");
		maskFields.put("dlvryMobile",		"MOBILE_PHO");
		maskFields.put("reqguide",			"MOBILE_PHO");
		maskFields.put("reqGuide",			"MOBILE_PHO");
		maskFields.put("moveMobile",		"MOBILE_PHO");
		maskFields.put("movemobile",		"MOBILE_PHO");
		maskFields.put("cstmrtel",			"MOBILE_PHO");
		maskFields.put("cstmrTel",			"TEL_NO");
		maskFields.put("cstmrTelNo",		"MOBILE_PHO");
		maskFields.put("phonctn",			"MOBILE_PHO");
		maskFields.put("tel1",				"MOBILE_PHO");
		maskFields.put("tel2",				"MOBILE_PHO");
		maskFields.put("talkTelNo",		"MOBILE_PHO");
		maskFields.put("ctn",				  "MOBILE_PHO");
		maskFields.put("vocctn",				  "MOBILE_PHO");
		
		maskFields.put("addr",				"ADDR");
		maskFields.put("subadrprimaryln",	"ADDR");
		maskFields.put("subAdrPrimaryLn",	"ADDR");
		maskFields.put("cstmraddr",			"ADDR");
		maskFields.put("cstmrAddr",			"ADDR");
		maskFields.put("dlvryaddr",			"ADDR");
		maskFields.put("dlvryAddr",			"ADDR");
		maskFields.put("customeradr",		"ADDR");
		maskFields.put("fullDlvryAddr",		"ADDR");
		maskFields.put("mAddr",		"ADDR");
		maskFields.put("combAddr",		"ADDR");
		
		
		maskFields.put("dlvryaddrdtl",		"PASSWORD");
		maskFields.put("cstmraddrdtl",		"PASSWORD");
		maskFields.put("subAdrSecondaryLn",	"PASSWORD");
				
		maskFields.put("ssn",				"SSN");
		maskFields.put("ssn1",				"SSN");
		maskFields.put("ssn2",				"SSN");
		maskFields.put("ssn3",				"SSN");
		maskFields.put("cstmrforeignerrrn",	"SSN");
		maskFields.put("cstmrForeignerRrn",	"SSN");
		maskFields.put("reqaccountrrn",		"SSN");
		maskFields.put("reqAccountRrn",		"SSN");
		maskFields.put("reqcardrrn",		"SSN");
		maskFields.put("reqCardRrn",		"SSN");
		maskFields.put("minoragentrrn",		"SSN");
		maskFields.put("minorAgentRrn",		"SSN");
		maskFields.put("cstmrnativerrn",	"SSN");
		maskFields.put("cstmrNativeRrn",	"SSN");
		maskFields.put("jrdclagentrrn",		"SSN");
		maskFields.put("userssn",			"SSN");
		maskFields.put("userSsn",			"SSN");

		maskFields.put("selfissunum",		"PASSPORT");
		maskFields.put("selfIssuNum",		"PASSPORT");	// 본인인증번호
		maskFields.put("minorselfissunum",	"PASSPORT");	// 본인인증번호
		maskFields.put("minorSelfIssuNum",	"PASSPORT");	// 본인인증번호
		maskFields.put("cstmrforeignerpn",	"PASSPORT");
		maskFields.put("cstmrForeignerPn",	"PASSPORT");
		maskFields.put("cstmrjuridicalrrn",	"CORPORATE");
		maskFields.put("cstmrJuridicalRrn",	"CORPORATE");
		
		maskFields.put("fstusimnum",		"USIM");
		maskFields.put("lstusimnum",		"USIM");		
		maskFields.put("requsimsn",			"USIM");
		maskFields.put("reqUsimSn",			"USIM");
		maskFields.put("reqUsimSnMask",		"USIM");
		maskFields.put("iccid",				"USIM");
		maskFields.put("usimNo",			"USIM");
		maskFields.put("reqphonesn",		"DEV_NO");
		maskFields.put("reqPhoneSn",		"DEV_NO");
		maskFields.put("intmsrlno",			"DEV_NO");
		maskFields.put("fstIntmSrlNo",		"DEV_NO");
		maskFields.put("lstIntmSrlNo",		"DEV_NO");
		maskFields.put("fstmodelsrlnum",	"DEV_NO");
		maskFields.put("lstmodelsrlnum",	"DEV_NO");
		maskFields.put("reqPhoneSnMask",	"DEV_NO");
		maskFields.put("phonesn",			"DEV_NO");
		maskFields.put("phoneSn",			"DEV_NO");
		maskFields.put("dvcintmsrno",		"DEV_NO");
		maskFields.put("dvcintmsrlno",		"DEV_NO");
		
		maskFields.put("cstmrmail",			"EMAIL");
		maskFields.put("cstmrMail",			"EMAIL");
		maskFields.put("reqaccountnumber",	"ACCOUNT");
		maskFields.put("reqAccountNumber",	"ACCOUNT");
		maskFields.put("reqcardno",			"CREDIT_CAR");
		maskFields.put("reqCardNo",			"CREDIT_CAR");
		maskFields.put("reqcardmm",			"CARD_EXP");
		maskFields.put("reqCardMm",			"CARD_EXP");
		maskFields.put("reqcardyy",			"CARD_EXP");
		maskFields.put("reqCardYy",			"CARD_EXP");
		
		//관리자 계정 마스킹
		maskFields.put("regstNm",			"CUST_NAME");
		maskFields.put("regstnm",			"CUST_NAME");
		maskFields.put("rvisnNm",			"CUST_NAME");
		maskFields.put("rvisnnm",			"CUST_NAME");
		maskFields.put("reqrNm",			"CUST_NAME");
		maskFields.put("procrNm",			"CUST_NAME");
		maskFields.put("cmplrNm",			"CUST_NAME");
		maskFields.put("cnsltNm",			"CUST_NAME");

		maskFields.put("regstId",			"SYSTEM_ID");
		maskFields.put("regstid",			"SYSTEM_ID");
		maskFields.put("rvisnId",			"SYSTEM_ID");
		maskFields.put("rvisnid",			"SYSTEM_ID");
		maskFields.put("reqrId",			"SYSTEM_ID");
		maskFields.put("procrId",			"SYSTEM_ID");
		maskFields.put("cmplrId",			"SYSTEM_ID");
		maskFields.put("recommendInfo",		"SYSTEM_ID");
		maskFields.put("recommendinfo",		"SYSTEM_ID");
		maskFields.put("userid",			"SYSTEM_ID");
		maskFields.put("recommid",			"SYSTEM_ID");

		maskFields.put("imei",		"IMEI");

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
								String getMethodString = "get" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
								String setMethodString = "set" + lMaskFieldsKey.substring(0,1).toUpperCase() + lMaskFieldsKey.substring(1);
								
								String lResultListRowValue = fields[i].get(obj) != null ? String.valueOf(fields[i].get(obj)) : "";
//								methods[i].invoke(obj, getMaskString(lResultListRowValue, item));
								chgMasking.put(setMethodString, getMaskString(lResultListRowValue, item));
							}
						}
					}
				}
			}catch ( Exception e) {
				e.printStackTrace();
			}
		}
		Method[] methods = obj.getClass().getDeclaredMethods();
		for(int i=0; i<methods.length; i++) {
			if(chgMasking.get(methods[i].getName()) != null) {
				try {
					methods[i].invoke(obj, chgMasking.get(methods[i].getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
	
		
//	/**
//	 * <PRE>
//	 * 1. MethodName: setMask
//	 * 2. ClassName	: MaskingService
//	 * 3. Commnet	: 
//	 * 4. 작성자	: Administrator
//	 * 5. 작성일	: 2014. 9. 25. 오후 3:59:26
//	 * </PRE>
//	 * 		@return void
//	 * 		@param p_resultList
//	 * 		@param p_maskFields
//	 * 		@param p_reqParamMap
//	 * 		@throws Exception
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void setMask(List<?> pResultList, HashMap pMaskFields, Map<String, Object> pReqParamMap) 
//	{
//
//		//-----------------------------------------
//		// get user's mask information
//		//-----------------------------------------
//		List<?> lCmnMskGrp =  maskingMapper.selectList(pReqParamMap);
////		logger.debug(">>>>>>>>>>>>>>>>" + l_cmnMskGrp);
//	
//		//-----------------------------------------
//		// if user.msk_auth_yn = "N' then return
//		//-----------------------------------------
//		try{
////			EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(0);
//			Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(0);
//			if( StringUtils.defaultString((String) lCmnMskGrpRow.get("mskAuthYn"),"").equals("Y"))
//				return;
//		}catch( Exception e)
//		{
//			return;
//		}
//		
//		//-----------------------------------------
//		// loop for result records to be masked
//		//-----------------------------------------
//		for ( int inx = 0 ; inx < pResultList.size(); inx++)
//		{
//				//-----------------------------------------
//				// loop p_maskFields 
//				//-----------------------------------------
//				Iterator itr =  pMaskFields.keySet().iterator();
//				while (itr.hasNext()) {
//				    String lMaskFieldsKey = (String)itr.next();
//				    String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
////				    logger.debug("maskFields : " + lMaskFieldsKey + " - " + lMaskFieldsValue);
//				
//				    // resultList의 row
////				    EgovMap l_resultListRow = (EgovMap) p_resultList.get(inx);
//					Map lResultListRow = (Map) pResultList.get(inx);
//					String lResultListRowValue = (String) lResultListRow.get(lMaskFieldsKey);
////					boolean l_isMask = true;
//					
//
//					try{
//						//-----------------------------------------
//						// loop maskinfo from DB
//						//-----------------------------------------
//						for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
//						{
////							EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(jnx);
//							Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
////							logger.debug("l_cmnMskGrpRow:" +l_cmnMskGrpRow);
//							String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  
//
//							//-----------------------------------------
//							// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
//							//-----------------------------------------
//							if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
//							{
//								lResultListRow.put(lMaskFieldsKey, getMaskString(lResultListRowValue, lCmnMskGrpRow));
//							}
//						}
//
//					}catch ( Exception e)
//					{
//						e.printStackTrace();
//					}
//				}
//		}
//	}
	
	
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
					e.printStackTrace();
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
	@SuppressWarnings("rawtypes")
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
				e.printStackTrace();
			}
		}

	}
	
	@SuppressWarnings("rawtypes")
	public List<?> beforeSetMask (Map<String, Object> pReqParamMap) {
		
		//-----------------------------------------
		// get user's mask information
		//-----------------------------------------
		List<?> lCmnMskGrp =  maskingMapper.selectList(pReqParamMap);
		
		//-----------------------------------------
		// if user.msk_auth_yn = "N' then return
		//-----------------------------------------
		try {
			Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(0);
			if( StringUtils.defaultString((String) lCmnMskGrpRow.get("mskAuthYn"),"").equals("Y")) {
				return null;
			}
			
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
		return lCmnMskGrp;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void setMask(EgovMap pResultMap, HashMap pMaskFields, List<?> lCmnMskGrp) 
	{
		
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
						pResultMap.put(lMaskFieldsKey, getMaskString(StringUtils.defaultString((String) pResultMap.get(lMaskFieldsKey), ""), lCmnMskGrpRow));
					}
				}
				
				
			}catch ( Exception e)
			{
				e.printStackTrace();
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
	@SuppressWarnings("rawtypes")
	public String getMaskString(String pResultListRowValue, Map lCmnMskGrpRow)
	{
		if ( pResultListRowValue == null || pResultListRowValue == "")
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
				String msk = "";
				for (int i=0; i<pResultListRowValue.length()-2; i++) {
					msk += "*";
				}
				lRtnString = pResultListRowValue.substring(0,1) + msk + pResultListRowValue.substring(pResultListRowValue.length()-1);
			} else {
				lRtnString = pResultListRowValue.substring(0,pResultListRowValue.length()-1) + "*";
			}

		}else if (lMskGrpId.equals("EMAIL")  )
		{
			String [] splitEmail = pResultListRowValue.split("@");
			if (splitEmail.length > 1) {
				String msk = "";
				for (int i=0; i<splitEmail[0].length(); i++) {
					msk += "*";
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
			
		}else if (lMskGrpId.equals("NAME2") || lMskGrpId.equals("BIRTH")) 
		{
			//-------------------------------------------------
			// 홍길동 => 홍**
			//-------------------------------------------------
			//-------------------------------------------------
			// 891204 => 89****
			//-------------------------------------------------			
			lMskLoc ="-99";
			lMskStrtLoc ="1";					
			if(lMskGrpId.equals("NAME2")){		
				lMskLnth = pResultListRowValue.length()-1 + "";
			}else if(lMskGrpId.equals("BIRTH")){
				lMskLnth = pResultListRowValue.length()-2 + "";		
			}
			
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
			
			
		}else if (lMskGrpId.equals("ADDR2"))
		{
			//-------------------------------------------------
			// 전라북도 전주시 완주군 구이면 3234-12문동구동" => "전라북도 ##########"
			//-------------------------------------------------
			String[] pResultListRowValueSplit = pResultListRowValue.split(" ");
			String lTargetString =  "";
			
			for(int i=0; i<pResultListRowValue.length(); i++){
				lTargetString = lTargetString + "*";
			}
			
			lRtnString = pResultListRowValueSplit[0] + lTargetString;
									
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
		if ( pResultListRowValue == null || pResultListRowValue == "")
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
				String msk = "";
				for (int i=0; i<pResultListRowValue.length()-2; i++) {
					msk += "*";
				}
				lRtnString = pResultListRowValue.substring(0,1) + msk + pResultListRowValue.substring(pResultListRowValue.length()-1);
			} else {
				lRtnString = pResultListRowValue.substring(0,pResultListRowValue.length()-1) + "*";
			}

		}else if (lMskGrpId.equals("EMAIL")  )
		{
			String [] splitEmail = pResultListRowValue.split("@");
			if (splitEmail.length > 1) {
				String msk = "";
				for (int i=0; i<splitEmail[0].length(); i++) {
					msk += "*";
				}
				lRtnString = msk + "@" + splitEmail[1];
			}
			
		} else if (lMskGrpId.equals("PHONE")) {
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
							e.printStackTrace();
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
							e.printStackTrace();
						}
					}
				}
		}
	}
	
}
