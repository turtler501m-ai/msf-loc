package com.ktis.msp.rcp.rcpMgmt;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util{
	private final static Logger LOGGER = LogManager.getLogger(Util.class);
	
	public static String[] getJuminNumber(String str){
		String [] result = {"", ""};
		try {
			if(StringUtils.isNotBlank(str)){
				String temp = StringUtils.replace(str, "-", "");
				result[0] = StringUtils.substring(temp, 0, 6);
				result[1] = StringUtils.substring(temp, 6);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
		}

		return result;
	}

	public static String[] getCardNumber(String str){
		String [] result = {"", "","", ""};
		try {
			if(StringUtils.isBlank(str)){
				return result;
			}
			String temp = StringUtils.replace(str, "-", "");
			result[0] = StringUtils.substring(temp, 0, 4);
			result[1] = StringUtils.substring(temp, 4, 8);
			result[2] = StringUtils.substring(temp, 8, 12);
			result[3] = StringUtils.substring(temp, 12);

		} catch (Exception e) {
			LOGGER.debug(e);
		}

		return result;
	}


	public static int getAge(String idNum){

		String today = ""; 
		String birthday = "";
		int myAge = 0; 

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

		today = formatter.format(new Date());

		if(idNum !=null && idNum.trim().length()==13) {

			if(idNum.charAt(6) == '1' || idNum.charAt(6) == '2' || idNum.charAt(6) == '5' || idNum.charAt(6) == '6' ){
				birthday = "19" + idNum.substring(0, 6);
			}else{ 
				birthday = "20" + idNum.substring(0, 6);
			}
		}
		else 
		{
			return 0;
		}
		
		myAge = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4)) ;
		// 생일 전이면 1 을 뺀다
		if(Integer.parseInt(today.substring(4)) < Integer.parseInt(birthday.substring(4))) myAge = myAge - 1;
		
		return myAge;

	}
	
	public static int getAgeYYYYMMDD(String idNum){
		String today = ""; 
		String birthday = idNum.substring(0, 4);
		int myAge = 0; 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.KOREA);
		today = formatter.format(new Date());
		myAge = Integer.parseInt(today) - Integer.parseInt(birthday) ;

		return myAge;
	}
	
	public static String[] getEmailSplit(String email){
		String[] result = new String[2];
		String email1 = "";
		String email2 = "";
		try{
			if(StringUtils.isNotBlank(email)){
				String[] emails = email.split("@");
				email1 = emails[0];
				email2 = emails[1];
			}
		}catch (Exception e) {
			LOGGER.debug(e);
		}
		
		result[0] = email1;
		result[1] = email2;		
		return result;
	}
	
	public static String[] getTelSplit(String value){
		String[] result = new String[3];
		String fn = "";
		String mn = "";
		String rn = "";
		try{
			if(StringUtils.isNotBlank(value) && StringUtils.contains(value, "-")) {
				String[] temp = value.split("-"); 
				fn = temp[0];
				mn = temp[1];
				rn = temp[2];
			}
		}catch (Exception e) {
			LOGGER.debug(e);
		}
		result[0] = fn;
		result[1] = mn;
		result[2] = rn;
		
		return result;
	}
	
	public static String[] getMobileNum(String value){
		String[] result = new String[3];
		String mobileNum1 = "";
		String mobileNum2 = "";
		String mobileNum3 = "";
		String moblieNum = StringUtils.replace(value, "-", "");
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
			LOGGER.debug(e);
		}

		result[0] = mobileNum1;
		result[1] = mobileNum2;
		result[2] = mobileNum3;

		return result;
	}	

	public static String[] getPhoneNum(String value){
		String[] result = new String[3];
		String phoneNum1 = "";
		String phoneNum2 = "";
		String phoneNum3 = "";
		String phoneNum = StringUtils.replace(value, "-", "");
		try {
			if(phoneNum.length() == 11 ){
				phoneNum1 = StringUtils.substring(phoneNum, 0, 3);
				phoneNum2 = StringUtils.substring(phoneNum, 3, 7);
				phoneNum3 = StringUtils.substring(phoneNum, 7);
			}else if(phoneNum.length() == 10){
				if(phoneNum.substring(0, 2).equals("02")){
					phoneNum1 = StringUtils.substring(phoneNum, 0, 2);
					phoneNum2 = StringUtils.substring(phoneNum, 2, 6);
					phoneNum3 = StringUtils.substring(phoneNum, 6);
				}else{
					phoneNum1 = StringUtils.substring(phoneNum, 0, 3);
					phoneNum2 = StringUtils.substring(phoneNum, 3, 6);
					phoneNum3 = StringUtils.substring(phoneNum, 6);
				}
			}else if(phoneNum.length() == 9){
				phoneNum1 = StringUtils.substring(phoneNum, 0, 2);
				phoneNum2 = StringUtils.substring(phoneNum, 2, 5);
				phoneNum3 = StringUtils.substring(phoneNum, 5);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
		}

		result[0] = phoneNum1;
		result[1] = phoneNum2;
		result[2] = phoneNum3;

		return result;
	}
	
	public static String getPrintStackTrace(Exception ex) {
		String strPST = "";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pinrtStream = new PrintStream(out);
//		20200512 소스코드점검 수정
//		ex.printStackTrace(pinrtStream);
		//System.out.println("Connection Exception occurred");
		//20210722 pmd소스코드수정
		LOGGER.error("Connection Exception occurred");
		strPST = out.toString();
		/*if(!"".equals(strPST)) {
			if(strPST.length() >= 4000) {
				strPST = strPST.substring(0, 4000);
			}
		}*/
		
		if(!"".equals(strPST) && strPST.length() >= 4000) {
			strPST = strPST.substring(0, 4000);
		}
		
		return strPST;
	}
	
	// 기변서식지 생성을 위한 추가
	public static String NVL(String parameter, String nullChange) {
		
		return parameter == null ?  nullChange : parameter;
	}
	
	// 기변서식지 생성을 위한 추가
	public static boolean isNotNull(String str){
		boolean result = StringUtils.isEmpty(str);
		if(result){
			return !result;
		}
		if(StringUtils.equals("null", str)){
			return false;
		}
		return true;
	}
}