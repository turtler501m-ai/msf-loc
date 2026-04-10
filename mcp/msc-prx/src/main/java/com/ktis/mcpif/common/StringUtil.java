package com.ktis.mcpif.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StringUtil extends StringUtils{
	protected static Logger logger = LogManager.getLogger(StringUtil.class);	
	public static String NVL(String parameter, String nullChange) {

		return parameter == null ?  nullChange : parameter;
	}

	/**
	 * int형 값 반환 default 0
	 */
	public static int toInteger(String str){
		int result = 0;
		try{
			result = Integer.parseInt(str);
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static long toLong(String str){
		long result = 0;

		try {
			result = Long.parseLong(str);
		} catch (NumberFormatException e) {
			logger.debug(e);
		}
		return result;
	}

	public static String booleanToYN(boolean set){
		String result = "N";
		try{
			if(set) result = "Y";
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static String booleanToString(boolean set){
		String result = "false";
		try{
			if(set) result = "true";
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static boolean YNToBoolean(String set){
		boolean result = false;
		try{
			if("Y".equals(set)) result = true;
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static String abbreviateByte(String str, int maxWidth){
		String result = "";
		try {
			byte[] bytes = str.getBytes("UTF-8");		
			if(bytes.length <= maxWidth){
				result = new String(bytes, 0, bytes.length, "UTF-8");
			}else{
				result = new String(bytes, 0, maxWidth - 3, "UTF-8")+"...";
			}
		} catch (UnsupportedEncodingException e) {
			logger.debug(e);
		}
		return result;
	}

	public static String equalsReplace(String str1, String str2, String replace){
		String result = str1;
		try{
			if(StringUtil.equals(str1, str2))
				result = replace;
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static String decode(String str1, String str2, String ifStr, String elseStr){
		String result = str1;
		try{
			if(StringUtil.equals(str1, str2)) result = ifStr;
			else result = elseStr;
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static String decode(boolean flag, String ifStr, String elseStr){
		String result = ifStr;
		try{
			if(flag) result = ifStr;
			else result = elseStr;
		}catch (Exception e) {
			logger.debug(e);
		}
		return result;
	}

	public static String getScriptFormat(String value) {
		try {			
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<value.length();i++) {
				char tmp = value.charAt(i);
				switch(tmp) {
				case '\r' : break;
				case '\n' : sb.append("%0").append(Integer.toHexString((int)'\n')); break;
				case ' ' : sb.append("%").append(Integer.toHexString((int)' ')); break;
				case '"' : sb.append("%").append(Integer.toHexString((int)'"')); break;
				case '&' : sb.append("%").append(Integer.toHexString((int)'&')); break;
				case '\'' : sb.append("%").append(Integer.toHexString((int)'\'')); break;
				case '<' : sb.append("%").append(Integer.toHexString((int)'<')); break;
				case '>' : sb.append("%").append(Integer.toHexString((int)'>')); break;
				default : sb.append(tmp);
				}
			}
			return sb.toString();
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String getHtmlFormat(String value) {
		try {
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<value.length();i++) {
				char tmp = value.charAt(i);
				switch(tmp) {
				case '\r' : break;
				case '\n' : sb.append("<br/>"); break;
				case '\"' : sb.append("&quot;"); break;
				case '&' : sb.append("&amp;"); break;
				case '\'' : sb.append("&#39;"); break;
				case '<' : sb.append("&lt;"); break;
				case '>' : sb.append("&gt;"); break;
				default : sb.append(tmp);
				}
			}
			return sb.toString();
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String leftPad(String startStr, String str, int size, String padStr) {
		int length = size - startStr.length();
		if(length < 0)
			throw new ArrayIndexOutOfBoundsException("문자열이 초과 되었습니다.");

		String leftpad = StringUtil.leftPad(str, length, padStr);
		String result = startStr + leftpad;
		if(result.length() > size)
			throw new ArrayIndexOutOfBoundsException("문자열이 초과 되었습니다.");

		return result;
	}

	public static String getNumberFormat(String value) {
		Number number = new Double(value);
		return StringUtil.getNumberFormat(number);
	}

	public static String getNumberFormat(Number value) {
		DecimalFormat format = new DecimalFormat("#,##0");
		return format.format(value);
	}

	/**
	 * 전화번호(FAX) 자릿수 자르기
	 */
	public static String[] getPhoneNum(String phoneNum){
		String[] result = new String[3];
		String phoneNum1 = "";
		String phoneNum2 = "";
		String phoneNum3 = "";

		try {
			if(phoneNum.length() == 13 ){
				phoneNum1 = phoneNum.substring(0, 3);
				phoneNum2 = phoneNum.substring(4, 8);
				phoneNum3 = phoneNum.substring(9, 13);
			}else if(phoneNum.length() == 12){
				if(phoneNum.substring(0, 2).equals("02")){
					phoneNum1 = phoneNum.substring(0, 2);
					phoneNum2 = phoneNum.substring(3, 7);
					phoneNum3 = phoneNum.substring(8, 12);
				}else{
					phoneNum1 = phoneNum.substring(0, 3);
					phoneNum2 = phoneNum.substring(4, 7);
					phoneNum3 = phoneNum.substring(8, 12);
				}
			}else if(phoneNum.length() == 11){
				phoneNum1 = phoneNum.substring(0, 2);
				phoneNum2 = phoneNum.substring(3, 6);
				phoneNum3 = phoneNum.substring(7, 11);
			}
		} catch (Exception e) {
			logger.debug(e);
		}

		result[0] = phoneNum1;
		result[1] = phoneNum2;
		result[2] = phoneNum3;

		return result;
	}

	/**
	 * 휴대전화번호 자릿수 자르기
	 */
	public static String[] getMobileNum(String moblieNum){
		String[] result = new String[3];
		String mobileNum1 = "";
		String mobileNum2 = "";
		String mobileNum3 = "";
		try {
			if(moblieNum != null){
				if(moblieNum.length() == 13 ){
					mobileNum1 = moblieNum.substring(0, 3);
					mobileNum2 = moblieNum.substring(4, 8);
					mobileNum3 = moblieNum.substring(9, 13);
				}else if(moblieNum.length() == 12){
					mobileNum1 = moblieNum.substring(0, 3);
					mobileNum2 = moblieNum.substring(4, 7);
					mobileNum3 = moblieNum.substring(8, 12);
				}else if(moblieNum.length() == 11){
					mobileNum1 = moblieNum.substring(0, 3);
					mobileNum2 = moblieNum.substring(3, 7);
					mobileNum3 = moblieNum.substring(7);
				}else if(moblieNum.length() == 10){
					mobileNum1 = moblieNum.substring(0, 3);
					mobileNum2 = moblieNum.substring(3, 6);
					mobileNum3 = moblieNum.substring(6);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
		}

		result[0] = mobileNum1;
		result[1] = mobileNum2;
		result[2] = mobileNum3;

		return result;
	}	

	/**
	 * 우편번호 자릿수 자르기
	 */
	public static String[] getPostCode(String postcode){
		String[] result = new String[2];
		String code1 = "";
		String code2 = "";
		try {
			if(postcode != null){
				if(postcode.length() == 6){
					code1 = postcode.substring(0, 3);
					code2 = postcode.substring(3, 6);
				}else if(postcode.length() == 7){
					code1 = postcode.substring(0, 3);
					code2 = postcode.substring(4, 7);
				}	
			}
		} catch (Exception e) {
			logger.debug(e);
		}

		result[0] = code1;
		result[1] = code2;

		return result;
	}	


	/**
	 * email 자르기
	 */
	public static String[] getEmailSplit(String email){
		String[] result = new String[2];
		String email_id = "";
		String address = "";
		try{
			if(email != null){
				email_id = email.split("@")[0];
				address = email.split("@")[1];
			}
		}catch (Exception e) {
			logger.debug(e);
		}

		result[0] = email_id;
		result[1] = address;
		return result;
	}	

	/**
	 * YYYYMMDD 날짜 자르기
	 */
	public static String[] getDateSplit(String yyyymmdd){
		String[] result = new String[3];
		String yyyy = "";
		String mm = "";
		String dd= "";

		try {
			if(yyyymmdd.length() == 8 ){
				yyyy = yyyymmdd.substring(0, 4);
				mm = yyyymmdd.substring(4, 6);
				dd = yyyymmdd.substring(6);
			}else if(yyyymmdd.length() == 10){
				yyyy = yyyymmdd.substring(0, 4);
				mm = yyyymmdd.substring(5, 7);
				dd = yyyymmdd.substring(8);
			}
		} catch (Exception e) {
			logger.debug(e);
		}

		result[0] = yyyy;
		result[1] = mm;
		result[2] = dd;

		return result;
	}

	public static String toTraceStr(Throwable e){
		String result = null;
		ByteArrayOutputStream stream = null;
		PrintWriter writer = null;

		try
		{
			stream = new ByteArrayOutputStream();
			writer = new PrintWriter(stream);

			e.printStackTrace(writer);
			writer.flush();

			result = new String(stream.toByteArray());


		}
		catch(Exception e1)
		{
			result = "Cannot catch exception";
		}
		finally
		{
			try
			{
				if(stream != null)
					stream.close();
				if(writer != null)
					writer.close();
			}
			catch(Exception e2)
			{
				result = "Cannot catch exception";
			}
		}

		if(result.length() > 1900)
			result = result.substring(0, 1900) + "...";

		return result;
	}

	/*
	 * html 태그 제거	 
	 * */
	public static String delHTML(String html){
		String textWithoutTag = "";
		if(html!=""&&html!=null){
			textWithoutTag = html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			textWithoutTag = textWithoutTag.replaceAll("<!--(([^-]*)|([-]{1})|([-]{2})([^>]{1}))*?-->", "");
			textWithoutTag = textWithoutTag.replaceAll("&nbsp;", " ");
		}
		return textWithoutTag;
	}
	public static boolean isHangul(String str){
		boolean result = false;
		String pattren = "^[ㄱ-ㅎㅏ-ㅣ가-힣]*$";
		result = Pattern.matches(pattren, str);

		return result;
	}

	public static boolean isEmail(String str){
		boolean result = false;
		String pattren = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z-]+(.[_0-9a-zA-Z-]+)*$";
		result = Pattern.matches(pattren, str);

		return result;
	}

	public static boolean isAlphanumericUnderscore(String str){
		boolean result = false;
		String pattren = "^[a-zA-Z0-9_]*$";
		result = Pattern.matches(pattren, str);

		return result;
	}

	public static boolean isSpecialStr(String str){
		boolean result = false;
		String pattren = ".*[^가-힣a-zA-Z0-9].*";
		result = Pattern.matches(pattren, str);

		return result;
	}

	public static String join(String sep, String ...strings){
		String result = "";

		for(String str : strings){
			if(StringUtil.isNotEmpty(result)){
				result = result.concat(sep);
			}
			result = result.concat(str);
		}

		return result;
	}

	public static int getByteSizeToComplex(String str) {
		int en = 0;
		int ko = 0;
		int etc = 0;

		char[] string = str.toCharArray();

		for (int j=0; j<string.length; j++) {
			if (string[j]>='A' && string[j]<='z') {
				en++;
			}
			else if (string[j]>='\uAC00' && string[j]<='\uD7A3') {
				ko++;
				ko++;
			}
			else {
				etc++;
			}
		}
		return (en + ko + etc);
	}

	public static boolean validatorPW(String passwd){
		if(passwd.length() < 8){
			return false;
		}
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=[\\S]+$)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(passwd);
		if (m.find()){
			return true;
		}else{
			return false;
		}
	}
	
	public static String getPrintStackTrace(Exception ex) {
		String strPST = "";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pinrtStream = new PrintStream(out);
		ex.printStackTrace(pinrtStream);
		strPST = out.toString(); 
		return strPST;
	}
}
