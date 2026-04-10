package com.ktis.msp.base.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.message.BaseMessage;
import com.ktis.msp.base.message.MessageCode;
import com.ktis.msp.rcp.rcpMgmt.Util;

public class BaseObject {

	/**
	 * Return Common JSON Object 
	 * @return
	 */
	
    @Autowired  
    protected MessageSource messageSource; 
    
	protected Logger logger = LogManager.getLogger(getClass());
	
	protected static Logger loggerStatic = LogManager.getLogger("BaseObject");
	
	private String logPrefix;
	
	protected BaseMessage getMessage() {
		return new BaseMessage();
	}
	
	/**
	 * Forward object
	 * @param model
	 * @param message
	 */
	protected void SendMessage(Model model, BaseMessage message) {
		
		if(!message.containsKey(BaseMessage.MSG_RETURN_CODE)) {
			message.setReturnCode(MessageCode.FAIL);
			message.setReturnMessage("반환코드가 없습니다.");
		}
		model.addAttribute("MESSAGE", message);
	}

	public String generateLogMsg(String message) {
		return String.format("%s%s", logPrefix, message);
	}
	
	public void setLogPrefix(String logPrefix) {
		this.logPrefix = logPrefix;
	}
	
	public String getLogPrefix() {
		return logPrefix;
	}
	
	public String getSqlCodeFromMsg(String pMsg) {
		Matcher matcher = Pattern.compile("(ORA-.....)").matcher(pMsg);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
		
	}
	
//	public SQLException findSQLExceptionWithVendorErrorCode (Throwable t) {
//		  Throwable rootCause = t;
//		  // this can become an endless loop if someone circularly nests an exception, which would be dumb, but throw a control in there anyway
//		  while (rootCause != null) {
//		    if (rootCause instanceof SQLException) {
//		        SQLException sqlException = (SQLException) rootCause;
//		        if (sqlException.getVendorCode() == SOME_EXPECTED_VALUE)  { // or not equal to some unexpected value, like 0
//		           return sqlException;
//		        }
//		    }
//		    rootCause = t.getCause();
//		  }
//		  return null;  // is there a better way to indicate that no SQLException was found?
//		}
	
	
	public boolean getErrReturn(Exception pException,  Map<String, Object> pResultMap)
	{
//		if ( pException instanceof SQLSyntaxErrorException ) 
//		{
//			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			pResultMap.put("msg", getSqlCodeFromMsg(pException.getLocalizedMessage()) + "\n 부적합한 SQL문장 에러"  );
//			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>e.getSQLException().getLocalizedMessage()" + ((BadSqlGrammarException) pException).getSQLException().getLocalizedMessage());
//		}else if ( pException instanceof BadSqlGrammarException ) 
//		{
//			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			pResultMap.put("msg", getSqlCodeFromMsg(pException.getLocalizedMessage()) + "\n 부적합한 SQL문장 에러"  );
//			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>e.getSQLException().getLocalizedMessage()" + ((BadSqlGrammarException) pException).getSQLException().getLocalizedMessage());
//		}else if ( pException instanceof DuplicateKeyException )
//		{
//			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
//		}else if ( pException instanceof DataAccessException )
//		{
//			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			pResultMap.put("msg", getSqlCodeFromMsg(pException.getLocalizedMessage()) + "\n 데이터 접근 에러"  );
//			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>DataAccessException" + getSqlCodeFromMsg(pException.getLocalizedMessage()) + "<br>" + pException.getLocalizedMessage());
//		}else if ( pException instanceof Exception )
//		{
//			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			pResultMap.put("msg", pException.getMessage());
//		}else 
//		{
//			return false;
//		}
		
		if ( pException instanceof DuplicateKeyException )
		{
			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", "");
			pResultMap.put("prgmId", "");
			pResultMap.put("prgmNm", "");
		}else if ( pException instanceof MvnoServiceException )
		{
			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			pResultMap.put("msg", pException.getMessage());
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", "");
			pResultMap.put("prgmId", "");
			pResultMap.put("prgmNm", "");
		}else if ( pException instanceof MvnoRunException )
		{
			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			pResultMap.put("msg", pException.getMessage());
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", "");
			pResultMap.put("prgmId", "");
			pResultMap.put("prgmNm", "");
		}else if ( pException instanceof Exception )
		{
			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", "");
			pResultMap.put("prgmId", "");
			pResultMap.put("prgmNm", "");
		}else if(pException instanceof MvnoErrorException){
			pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", "");
			pResultMap.put("prgmId", "");
			pResultMap.put("prgmNm", "");
		}else 
		{
			return false;
		}
		
		
		return true;
	}
	
	public boolean getErrReturn(Exception pException,  Map<String, Object> pResultMap, String strRequestMapping, String strCode, String strMsg, String strPrgmId, String strPrgmNm)
	{
		if ( pException instanceof DuplicateKeyException )
		{
			if(!"".equals(strCode)) {
				pResultMap.put("code", strCode );
			} else {
				pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			}
			if(!"".equals(strMsg)) {
				pResultMap.put("msg", strMsg );
			} else {
				pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.DUP", null, Locale.getDefault()) );
			}
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", strRequestMapping);
			pResultMap.put("prgmId", strPrgmId);
			pResultMap.put("prgmNm", strPrgmNm);
		}else if ( pException instanceof MvnoServiceException )
		{
			if(!"".equals(strCode)) {
				pResultMap.put("code", strCode );
			} else {
				pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			}
			if(!"".equals(strMsg)) {
				pResultMap.put("msg", strMsg);
			} else {
				pResultMap.put("msg", pException.getMessage());
			}
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", strRequestMapping);
			pResultMap.put("prgmId", strPrgmId);
			pResultMap.put("prgmNm", strPrgmNm);
		}else if ( pException instanceof MvnoRunException )
		{
			if(!"".equals(strCode)) {
				pResultMap.put("code", strCode );
			} else {
				pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			}
			if(!"".equals(strMsg)) {
				pResultMap.put("msg", strMsg);
			} else {
				pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			}
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", strRequestMapping);
			pResultMap.put("prgmId", strPrgmId);
			pResultMap.put("prgmNm", strPrgmNm);
		}else if ( pException instanceof Exception )
		{
			if(!"".equals(strCode)) {
				pResultMap.put("code", strCode );
			} else {
				pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			}
			if(!"".equals(strMsg)) {
				pResultMap.put("msg", strMsg);
			} else {
				pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
			}
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", strRequestMapping);
			pResultMap.put("prgmId", strPrgmId);
			pResultMap.put("prgmNm", strPrgmNm);
		}else if ( pException instanceof MvnoErrorException )
		{
			if(!"".equals(strCode)) {
				pResultMap.put("code", strCode );
			} else {
				pResultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			}
			if(!"".equals(strMsg)) {
				pResultMap.put("msg", strMsg);
			} else {
				pResultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
			}
			//pResultMap.put("stackTrace", Util.getPrintStackTrace(pException));
			pResultMap.put("category", "MSP");
			pResultMap.put("requestMapping", strRequestMapping);
			pResultMap.put("prgmId", strPrgmId);
			pResultMap.put("prgmNm", strPrgmNm);
		}else 
		{
			return false;
		}
		
		
		return true;
	}

	
	public List getVoFromMultiJson(String pJsonString, String pNodeName, Class pClass) throws ParseException, JsonParseException, JsonMappingException, IOException 
	{
	    //----------------------------------------------------------------
    	// JSON STRING -> JSON OBJECT로 변환  ObjectMapper.configure( org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
    	//----------------------------------------------------------------
	    JSONObject jSONObject = new JSONObject();
//	    jSONObject = (JSONObject) new JSONParser().parse(pJsonString.toString());
	    jSONObject = (JSONObject) new JSONParser().parse(pJsonString);
	    
	    //----------------------------------------------------------------
    	// data node 구함
    	//----------------------------------------------------------------
	    JSONArray lJsonArray = ((JSONArray)jSONObject.get(pNodeName));
	    ArrayList lRtn = new ArrayList();
	    
	    
	    ObjectMapper lObjectMapper = new ObjectMapper();
	    lObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    for ( int inx = 0 ; inx < lJsonArray.size(); inx++)
	    {
	    	Object lClass = lObjectMapper.readValue(lJsonArray.get(inx).toString(), pClass);
	    	lRtn.add(lClass);
	    }
	    
	    return lRtn;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: dummyFinally
	 * 2. ClassName	: BaseController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 26. 오전 11:39:24
	 * </PRE>
	 * 		@return void
	 */
	public String  dummyCatch()
	{
		return "";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: dummyFinally
	 * 2. ClassName	: BaseController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 26. 오전 11:39:24
	 * </PRE>
	 * 		@return void
	 */
	public String dummyFinally()
	{
		return "";

	}
	
	public void writeCsv(List<?> resultList, PrintWriter printWriter)
	{
    	for ( int inx = 0 ; inx < resultList.size(); inx ++)
    	{
    		Map row  = (Map) resultList.get(inx);

//    		if ( inx == 0 )
//    		{
//        		Iterator<Map.Entry<String, String>> entries = row.entrySet().iterator();
//				
//				while (entries.hasNext()) {
//				    Map.Entry<String, String> entry = entries.next();
//				    printWriter.write( "\"" + entry.getKey() + "\",");
//				}
//    			printWriter.write("\n
//    		}
    		
    		Iterator<Map.Entry<String, String>> entries = row.entrySet().iterator();
			
			while (entries.hasNext()) {
			    Map.Entry<String, String> entry = entries.next();
			    printWriter.write( "\"" + row.get(entry.getKey()) + "\",");
			}

        	printWriter.write("\n");
    	}

	}
	
	
}
