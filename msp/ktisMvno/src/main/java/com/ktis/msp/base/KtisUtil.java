package com.ktis.msp.base;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * 
 *
 */
public class KtisUtil { 

	public final static String DATE_SHORT		= "yyyyMMdd";
	public final static String DATETIME_SHORT	= "yyyyMMddHHmmss";
	
	/**
	 * HashMap String타입의 값을 리턴 ,null 또는 ""이면 nullChangeValue 리턴
	 *
	 * @param map
	 * @param key
	 * @param nullChangeValue
	 * @return
	 * @returnType String
	 * @author 장익준
	 * @version 
	 * @created 2013. 7. 15. 오후 8:43:06
	 * @updated
	 */
	public static String getMapStr(Map<String,Object> map, String key, String nullChangeValue){
		String rtnStr = "";
		try{
			rtnStr = map.get(key).toString();
		}catch(Throwable te){
			rtnStr = null;
		}
		
		if(rtnStr == null || rtnStr.equals("")){
			return nullChangeValue;
		}else{
			return rtnStr.trim();
		}
	}
	
	/**
	 * <p>Date를 지정된 포맷의 텍스트로 변환한다.</p>
	 * @author 장익준
	 * @param value
	 * @param dateFormat
	 * @return
	 */
	public static String toStr(Date value, String dateFormat)  {
		DateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault() );
		String strDate = format.format(value);
		return strDate;
	}
	
	/**
	 * HashMap String타입의 값을 리턴 ,null이면 ""로 리턴
	 *
	 * @param map
	 * @param key
	 * @return
	 * @returnType String
	 * @author 장익준
	 * @version 
	 * @created 2013. 7. 15. 오후 8:42:54
	 * @updated
	 */
	public static String getMapStr(Map<String,Object> map, String key){
		return getMapStr(map, key, "");
	}
	
	/**
	 * setParam - HashMap.put ,, value가 null 또는 ""이면 reVal을 put
	 *
	 * @param map
	 * @param key
	 * @param val
	 * @param reVal
	 * @returnType void
	 * @author 장익준
	 * @version 
	 * @created 2013. 7. 15. 오후 8:29:09
	 * @updated
	 */
//	public static void setParam(Map<String,Object> map, String key, String val, String reVal){
//		if(map == null)	return;
//		if(val == null || val.equals("")) val = reVal;
//		if(!val.equals("")){
//			map.put(key, val.trim());
//		}
//	}
	
	/**
	 * setParam - HashMap.put ,, value가 null 이면 ""로 put
	 *
	 * @param map
	 * @param key
	 * @param val
	 * @returnType void
	 * @author 장익준
	 * @version 
	 * @created 2013. 7. 15. 오후 8:30:13
	 * @updated
	 */
//	public static void setParam(Map<String,Object> map, String key, String val){
//		setParam(map, key, val, "");
//	}
	
	/**
	 * FMWrapper ListList로 조회시 totalCount를 HashMap에 putter
	 * ex)	Adapter에서 리턴시
	 * 		...
	 * 		return PnaUtil.generateTotalCount(fmInMap, fmWrapperFacade.callFMSPListList(serviceName, fmInMap));
			...
	 *
	 * @param inMap
	 * @param rslt
	 * @return
	 * @returnType List<Object>
	 * @author 장익준
	 * @version 
	 * @created 2013. 7. 15. 오후 8:42:29
	 * @updated
	 */
	public static List<Object> generateTotalCount(Map<String,Object> inMap, List<Object> rslt){
		if(inMap == null || rslt == null){
			return rslt;
		}
		if(rslt.size() > 1){
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>)((List<Object>)rslt.get(1)).get(0);
			String totalCnt = map.get("").toString();
			map.remove("");
			map.put("totalCnt", totalCnt);
		}else{
			List<Object> list = new ArrayList<Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			list.add(map);
			rslt.add(list);
			map.put("totalCnt", getMapStr(inMap, "TOTAL_COUNT", "0"));
		}
		
		return rslt;
	}
	/**
	 * Object 비교 : source가 elements에 포함되면 true else fasle
	 * 		...
	 * 		return boolean
			...
			
	 * @param String
	 * @param Object
	 * @return boolean
	 * @author KKMIN
	 */
	public static boolean existInSet(String source, Object... elements)
	 {
	  boolean bExist = false; 
	  
	  if(source == null) return false;
	  
	  for (Object objElement : elements)
	  {
	   if(source.equals(objElement)) {
	    bExist = true;
	    break;
	   }
	  }
	  return bExist;
	 }

	/**
	 * Object 비교 : source가 elements에 포함되어 있지 않으면 true, 포함되어 있으면 fasle
	 * @param String
	 * @param Object
	 * @author KKMIN
	 */
	public static boolean notExistInSet(String source, Object... elements)
	 {
	  boolean bExist = true; 
	  
	  for (Object objElement : elements)
	  {
	   if(source.equals(objElement)) {
	    bExist = false;
	    break;
	   }
	  }
	  return bExist;
	 }

	/**
	 * @returnType boolean 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=source 와 values를 null인지 체크하고 equals 로 비교
	 */
	
	public static boolean equalsNck(Object source, Object values) {
		boolean blReturn = false;
		
		if (source != null && values !=null){
			if (values.equals(source) ) {
				blReturn = true;
			}
		} else if (source == null && values ==null){
			blReturn = true;
		}
		
		
		return blReturn;
	}
	
	/**
	 * @returnType boolean 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=source 와 values를 null인지 체크하고 equals 로 비교
	 */
	
	public static boolean notEquals(Object source, Object values) {
		boolean blReturn = false;

		if (source != null && values !=null){
			if (values.equals(source)) {
				blReturn = false;
			} else {
				blReturn = true;
			}
		} else {
			blReturn = true;
		}
		
		return blReturn;
	}
	
	/**
	 * @returnType Object 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=source가 null 이면 value를 리턴하고, null이 아니면 source를 리턴
	 */
	
	public static Object changeNull(Object source, Object value) {
		if (source == null && value !=null){
			return value;
		} else if(source != null) {
			return source;
		} else {
			return null;
		}
	}
	
	/**
	 * @returnType Object 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=source가 element의 0,2,4,..와 같으면 1,3,5,..를 리턴한다.
	 */
	
	public static Object changeSet(Object source, Object... elements) {
		if(source == null) return null;
		
		if(elements.length < 3) return null;

		for (int i = 0; i < elements.length; i=i+2) {
			if(source.equals(elements[i])) {
				return elements[i+1];
			}
		}
		
		if(elements.length % 2 == 0) {
			return null;
		} else {
			return elements[elements.length-1];
		}
	}
	
	/**
	 * 입력 받은 Object 가 null 또는 공백 인지 체크한다.
	 * @returnType boolean 
	 * @author 
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
    @SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object object) {
    	
    	if( object instanceof String ) {
    		return object==null || "".equals(object.toString().trim());        
    	} else if( object instanceof List ) {
    		return object==null || ((List)object).isEmpty();        
    	} else if( object instanceof Map ) {
    		return object==null || ((Map)object).isEmpty();       
    	} else if( object instanceof Object[] ) {
    		return object==null || Array.getLength(object)==0;        
    	} else return object==null;   
    }         
    
	/**
	 * 입력 받은 Object 가 not null 인지 체크한다.
	 * @returnType boolean 
	 * @author 
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
    public static boolean isNotEmpty(Object object) {        
    	return !isEmpty(object);   
    }
	
	/**
	 * 입력 받은String이 null or length == 0일 경우 기본 0설정, 그외는 int형으로 형변환.
	 * @returnType boolean 
	 * @author 
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static int parseIntZ(String source){
		int nResult = 0;
		if (source != null && source.length() > 0){
			nResult = Integer.parseInt(source);
		}
		return nResult;
	}
	
	/**
	 * 입력 받은String이 null or length == 0일 경우 기본 0설정, 그외는 Long형으로 형변환.
	 * @returnType boolean 
	 * @author 
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static long parseLongZ(String source){
		long nResult = 0;
		if (source != null && source.length() > 0){
			nResult = Long.parseLong(source);
		}
		return nResult;
	}
	
	/**
	 * @returnType Object 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=source가 null 이면 value를 리턴하고, null이 아니면 source를 리턴
	 */
	
//	public static String toFMAddrTypeCode(String source) {
//		
//		if(source.equals("01") || source.equals("일반")){
//			source = "일반";
//		}
//		else if(source.equals("02") || source.equals("산")){
//			source = "산";
//		} 
//		else if(source.equals("03") || source.equals("블럭")){
//			source = "블럭";
//		}
//		else if(source.equals("04") || source.equals("단지")){
//			source = "단지";
//		}
//		else if(source.equals("05") || source.equals("사서함")){
//			source = "사서함";
//		}
//		else{
//			source = "일반";
//		}
//		
//		return source;
//	}
	
	/**
	 * 입력 받은 null or String일 경우 .
	 * @returnType Date 
	 * @author k.s.c
	 * @version 1.0
	 * @throws ParseException 
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static Date convertStringToDate(String source, DATEPATTERN pattern) throws ParseException{
		Date dateResult = null;
		SimpleDateFormat simpleDate = null; 
		
		if (source != null && pattern != null){
			if(pattern.equals(DATEPATTERN.YearToSecond)){
				simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				dateResult = simpleDate.parse(source);
			}
			else if(pattern.equals(DATEPATTERN.YearToDay)){
				simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				dateResult = simpleDate.parse(source);
			}
			else if(pattern.equals(DATEPATTERN.YearToDayWithNoBar)){
				simpleDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				dateResult = simpleDate.parse(source);
			}
		}
		
		return dateResult;
	}

	
	/**
	 * 입력 받은 null or Date일 경우 .
	 * @returnType boolean 
	 * @author 
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static String convertDateToString(Date source, DATEPATTERN pattern){
		String strResult = "";
		SimpleDateFormat simpleDate = null; 
		
		if (source != null && pattern != null){
			if(pattern.equals(DATEPATTERN.YearToSecond)){
				simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				strResult = simpleDate.format(source);
			}
			else if(pattern.equals(DATEPATTERN.YearToDay)){
				simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				strResult = simpleDate.format(source);
			}
			else if(pattern.equals(DATEPATTERN.YearToDayWithNoBar)){
				simpleDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				strResult = simpleDate.format(source);
			}
			else if(pattern.equals(DATEPATTERN.ZoneKSTEnghish)){
				//Output: Tue Jan 07 00:19:58 KST 2014
				simpleDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				strResult = simpleDate.format(source);
			}
		}
		return strResult;
	}
	
	public static boolean isCompareDate(String stDate, String edDate, DATEPATTERN pattern) throws ParseException {
		
		return convertStringToDate(edDate, pattern).compareTo(convertStringToDate(stDate, pattern)) >= 0;
	}
	
	public static long getCompareDateGap(String stDate, String edDate, DATEPATTERN pattern) throws ParseException {
		
		return (convertStringToDate(edDate, pattern).getTime() - convertStringToDate(stDate, pattern).getTime()) / (24*60*60*1000);
	}
	
	public enum DATEPATTERN{
		YearToSecond,
		YearToDay,
		YearToDayWithNoBar,
		YearToKST,
		ZoneKSTEnghish
	}
	
	/**
	 * @returnType int 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=코드문자열에서 특정 코드값을 찾아서 해당 자리(위치)를 리턴한다.
	 */
	
	public static int searchCode(String strCodes, int codeSize, String strSearch) {
		if(strCodes == null || strSearch == null || strCodes.length() == 0 || strSearch.length() == 0) {
			return -1;
		}
		
		for(int i=0; i < strCodes.length(); i=i+codeSize) {
			if(strCodes.substring(i, i+codeSize).equals(strSearch)) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * 입력 받은 문자열이 "Y" 또는 "YES"일 경우(대소문자 구분 없이) true,
	 * 그렇지 않으면(Null 포함해서) false를 리턴한다.
	 * @returnType boolean 
	 * @author 김종훈
	 * @version 1.0
	 * @created 2013.07.29
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static boolean isYesOrNot(String yesOrNot) {
		
		if (yesOrNot == null) {
			return false;
		}
		
		if (yesOrNot.equalsIgnoreCase("Y") ||
			yesOrNot.equalsIgnoreCase("YES")) {
			return true;
		}
		return false;
	}

	/**
	 * 입력 받은 boolean이 true면 "Y"를, false면 "N"을 리턴한다.
	 * @returnType boolean 
	 * @author 김종훈
	 * @version 1.0
	 * @created 2013.07.29
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static String toStringYN(boolean yn) {
		
		if (yn) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 입력 받은 boolean이 true면 "TRUE"를, false면 "FALSE"를 리턴한다.
	 * @returnType boolean 
	 * @author 김종훈
	 * @version 1.0
	 * @created 2013.07.29
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=
	 */
	public static String toStringTF(boolean tf) {
		
		if (tf) {
			return "TRUE";
		} else {
			return "FALSE";
		}
	}

	
	
	/**
	 * Byte 기준으로 string 을 자른다. (한글기준 2byte 적용)
	 */
	public static String substring(String str, int start) {
		if (str == null || str.length() == 0) {
			return "";
		}
		byte[] byteBody = str.getBytes();
		if (byteBody == null) {
			return null;
		}
		return substring(byteBody, start, byteBody.length);
	}

	/**
	 * Byte 기준으로 string 을 자른다. (한글기준 2byte 적용)
	 */
	public static String substring(String str, int start, int end) {
		if (str == null || str.length() == 0) {
			return "";
		}
		byte[] byteBody = str.getBytes();
		if (byteBody == null) {
			return null;
		}
		return substring(byteBody, start, end);
	}

	/**
	 * Byte Array 데이터를 string 으로 자른다.
	 */
	public static String substring(byte[] byteBody, int start, int end) {
		if (byteBody == null || byteBody.length == 0) {
			return "";
		}
		int size = end - start;
		int length = byteBody.length - start;
		if (length < size) {
			size = length;
		}

		// byte[] tmp_body = new byte[end - start];
		byte[] tmpBody = new byte[size];
		System.arraycopy(byteBody, start, tmpBody, 0, tmpBody.length);
		return new String(tmpBody);
	}
	

	/**
	 * @returnType String 
	 * @author KKMIN
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=ICIS에서 전달한 여부(YN)속성 변환 : 0=>N, 1=>Y.  null일 경우 처리할 값은 파라메터로 전달한다.
	 */
	
	public static String convertIcisYn(String source, String ifNull) {
		if(source == null) {
			return ifNull;
		}
		else if(source.equals("0")){
			return "N";
		} 
		else if(source.equals("1")){
			return "Y";
		} 
		else{
			return source;
		}
	}
	
	
	/**
	 * @returnType String 
	 * @author 정지은
	 * @version 1.0
	 * @created 
	 * @updated  
	 * @EA-METHOD-GUID={}
	 * @EA-METHOD-NAME=왼쪽 문자열 padding
	 */
	public static String leftPad(String str, int length, char padChar) {

		int lenStr = str.length();
		if(lenStr >= length) return str;

		char strResult[] = new char[length];
		char strArr[] = str.toCharArray();

		for(int i = 0; i < length; i++) {

			if(i < (length - lenStr)) {
				strResult[i] = padChar;
			} else {
				strResult[i] = strArr[i - (length - lenStr)];
			}
		}

		return new String(strResult);

	}
	
}
