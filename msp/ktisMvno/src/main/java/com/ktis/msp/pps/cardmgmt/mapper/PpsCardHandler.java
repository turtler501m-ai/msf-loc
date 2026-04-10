package com.ktis.msp.pps.cardmgmt.mapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.ktds.crypto.decryptor.Decryptor;
import com.ktis.msp.base.dao.ExcelResultHandler;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;

public class PpsCardHandler extends ExcelResultHandler {
	private Decryptor decryptor;
	protected Logger logger = LogManager.getLogger(getClass());
	
	public PpsCardHandler(){
		
	}
	
	public PpsCardHandler(ExcelWriter writer, Map<String, XSSFCellStyle> styles, ExcelParam param) {
		super(writer, styles, param);
	}

	
	public void setDecryptor(Decryptor dec) {
		decryptor = dec;
	}
	@Override
	public HashMap<String, Object> refine(Object obj) {
		HashMap<String, Object> r = null;
		try {
		
		
		 r = (HashMap<String, Object>) obj;
		if(r.get("PIN_NUMBER")!=null && !r.get("PIN_NUMBER").equals(""))
		{
			String PIN_NUMBER = (String) r.get("PIN_NUMBER");	
			r.put("PIN_NUMBER", decryptor.decrypt(PIN_NUMBER));
		}
		
		if(r.get("OPEN_AGENT_TYPE")!=null && !r.get("OPEN_AGENT_TYPE").equals("")){
			String OPEN_AGENT_TYPE = (String) r.get("OPEN_AGENT_TYPE");
			if(OPEN_AGENT_TYPE.equals("D"))
			{
				r.put("OPEN_AGENT_ID", r.get("OUT_DELER_ID"));
				r.put("OPEN_AGENT_NM", r.get("OUT_DELAER_NM"));
			}
			
		}
		
			this.setMask(r, param);
		
		} catch (Exception e) {

//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
//			20210706 소스코드점검 수정
//			System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
		
		return r;
	}
	
	
	public void setMask(HashMap<String, Object> map, ExcelParam pReqParamMap) 
	{

		List<?> lCmnMskGrp = pReqParamMap.getCmnMaskGrpList();
		
		HashMap pMaskFields = new HashMap();
		pMaskFields.put("SUBSCRIBER_NO", "MOBILE_PHO");
		pMaskFields.put("CALLED_NUM", "MOBILE_PHO");
		
		pMaskFields.put("SUB_LINK_NAME", "CUST_NAME");
		pMaskFields.put("REQUESTER", "CUST_NAME");
		pMaskFields.put("BANK_USER_NAME", "CUST_NAME");
		pMaskFields.put("REQ_USER_NAME", "CUST_NAME");
		pMaskFields.put("CR_ADMIN_NM", "CUST_NAME");
		pMaskFields.put("OUT_ADMIN_NM", "CUST_NAME");
		pMaskFields.put("OPEN_ADMIN_NM", "CUST_NAME");
		pMaskFields.put("BACK_ADMIN_NM", "CUST_NAME");
		
		
		
		
		//-----------------------------------------
		// if user.msk_auth_yn = "N' then return
		//-----------------------------------------
//		EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(0);
		Map cmnMskGrpRow = (Map) lCmnMskGrp.get(0);
		if( !StringUtils.defaultString((String) cmnMskGrpRow.get("mskAuthYn"),"").equals("Y"))
		{
			
			
			Iterator itr =  pMaskFields.keySet().iterator();
			while (itr.hasNext()) {
			    String lMaskFieldsKey = (String)itr.next();
			    String lMaskFieldsValue = (String) pMaskFields.get(lMaskFieldsKey);
//			    logger.debug("maskFields : " + lMaskFieldsKey + " - " + lMaskFieldsValue);
			

				try{
					//-----------------------------------------
					// loop maskinfo from DB
					//-----------------------------------------
					for ( int jnx = 0 ; jnx < lCmnMskGrp.size(); jnx++)
					{
//						EgovMap l_cmnMskGrpRow = (EgovMap) l_cmnMskGrp.get(jnx);
						Map lCmnMskGrpRow = (Map) lCmnMskGrp.get(jnx);
//						logger.debug("l_cmnMskGrpRow:" +l_cmnMskGrpRow);
						String lCmnMskGrpRowMskId         = StringUtils.defaultString((String) lCmnMskGrpRow.get("mskId"),"");  

						//-----------------------------------------
						// p_maskFields에서의 field명과 mask info의 field명이 일치하면 mask
						
						if(map.get(lMaskFieldsKey) != null && !"".equals(map.get(lMaskFieldsKey)))
						{
							String lResultListRowValue = (String) map.get(lMaskFieldsKey);
							
							if (lMaskFieldsValue.equals(lCmnMskGrpRowMskId) )
							{
								map.put(lMaskFieldsKey, getMaskString(lResultListRowValue, lCmnMskGrpRow));
							}
						}
						
					}

				}catch ( Exception e)
				{
//					20200512 소스코드점검 수정
//			    	e.printStackTrace();
//					20210706 소스코드점검 수정
//					System.out.println("Connection Exception occurred");
					logger.error("Connection Exception occurred");
				}
			}
			
		}
		
	}
	
	public String getMaskString(String pResultListRowValue, Map lCmnMskGrpRow)
	{
		if ( pResultListRowValue == null)
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
				StringBuilder mskBuilder = new StringBuilder();
				String msk = "*";
				for (int i=0; i<pResultListRowValue.length()-2; i++) {
					mskBuilder.append(msk);
				}
				lRtnString = pResultListRowValue.substring(0,1) + mskBuilder.toString() + pResultListRowValue.substring(pResultListRowValue.length()-1);
			} else {
				lRtnString = pResultListRowValue.substring(0,pResultListRowValue.length()-1) + "*";
			}

		}else if (lMskGrpId.equals("EMAIL")  )
		{
			String [] splitEmail = pResultListRowValue.split("@");
			if (splitEmail.length > 1) {
				StringBuilder mskBuilder = new StringBuilder();
				String msk = "*";
				for (int i=0; i<splitEmail[0].length(); i++) {
					mskBuilder.append(msk);
				}
				lRtnString = mskBuilder.toString() + "@" + splitEmail[1];
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

}
