package com.ktmmobile.msf.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class MaskingUtil {
 
	// ------------- start: 사용되지않고 있는 정규식 -------------
    public final static String REGEX_SSN_DASH  		= "(\\d{6})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{1})\\d{6}"; // 주민번호 (-)
    public final static String MASK_SSN_DASH      	= "$1$2$3******"; // 주민번호

    public final static String REGEX_SSN_DASH2  	= "(\\d{6})([- 　\\t\\n\\x0B\\f\\r]*)\\d{7}"; // 주민번호 (-)
    public final static String MASK_SSN_DASH2      	= "$1$2*******"; // 주민번호

    public final static String REGEX_SSN     		= "(\\d{6})(\\d{1})\\d{6}"; // 주민번호 (-없는것)
    public final static String MASK_SSN      		= "$1$2******"; // 주민번호

    public final static String REGEX_BUSI_NO 		= "(\\d{3})([- 　\\t\\n\\x0B\\f\\r])(\\d{2})([- 　\\t\\n\\x0B\\f\\r])\\d{5}"; // 사업자/법인번호
    public final static String MASK_BUSI_NO  		= "$1$2$3$4*****"; // 사업자/법인번호

    public final static String REGEX_TEL_NO  		= "(\\d{2,3})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{1,2})(\\d{2})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{1})(\\d{3})"; // 전화번호(-)
    public final static String MASK_TEL_NO   		= "$1$2$3**$5*$7"; // 전화번호 (-)

    public final static String REGEX_TEL_444 		= "(\\d{4})(\\d{2})(\\d{2})(\\d{1})(\\d{3})"; // 전화번호(444)
    public final static String MASK_TEL_444   		= "$1$2***$5"; // 전화번호 (444)

    public final static String REGEX_TEL2_444 		= "(\\d{4})(\\d{4})(\\d{4})"; // 전화번호(444)
    public final static String MASK_TEL2_444   		= "$1****$3"; // 전화번호 (444)
    
    public final static String REGEX_CREDIT_CARD    = "(\\d{4})([- ]*)(\\d{2})(\\d{2})([- ]*)(\\d{4})([- ]*)(\\d{3})(\\d{1})"; // 16자리 신용카드
    public final static String MASK_CREDIT_CARD     = "$1$2$3**$5****$7***$9"; // 16자리 신용카드

    public final static String REGEX_CREDIT_CARD2   = "(\\d{4})([- ]*)(\\d{2})(\\d{2})([- ]*)(\\d{4})([- ]*)(\\d{2})(\\d{1})"; // 15자리 신용카드(AMEX)
    public final static String MASK_CREDIT_CARD2    = "$1$2$3**$5****$7**$9"; // 15자리 신용카드(AMEX)

    public final static String REGEX_CREDIT_CARD3   = "(\\d{4})([- ]*)(\\d{2})(\\d{2})([- ]*)(\\d{4})([- ]*)(\\d{1})(\\d{1})"; // 14자리 신용카드(Diners Club)
    public final static String MASK_CREDIT_CARD3    = "$1$2$3**$5****$7*$9"; // 14자리 신용카드(Diners Club)

    public final static String REGEX_EMAIL  		= "([a-zA-Z][\\w\\.-]*)([a-zA-Z0-9]{3})@([a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$)"; // email
    public final static String MASK_EMAIL   		= "$1***@$3"; // email
    
    public final static String REGEX_ID  			= "([a-zA-Z][\\w\\.-]*)([a-zA-Z0-9]{3})"; // ID류
    public final static String MASK_ID   			= "$1***"; // ID류 

    public final static String REGEX_IPADDR  		= "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"; // IP ADDR
    public final static String MASK_IPADDR   		= "$1.***.$3.***"; // IP ADDR
    public final static String MASK_IPADDR2   		= "***.$2.***.$4"; // IP ADDR
    public final static String MASK_IPADDR3   		= "$1.$2.***.$4"; // IP ADDR

    public final static String REGEX_DRIVER  		= "([0-9]{2})([-~.[:space:]])([0-9]{6})([-~.[:space:]])([0-9]{2})";
    public final static String MASK_DRIVER   		= "$1-******-$5"; // 운전면허 (12-123456-12 -> 12-******-12 )
    public final static String REGEX_DRIVER2     		= "(\\d{2})(\\d{6})(\\d{2})"; //
    public final static String MASK_DRIVER2   		= "$1-******-$3"; // 운전면허 (1212345612 -> 12-******-12 )

    public final static String REGEX_DRIVER3     	= "([0-9]{2})([-~.[:space:]])([0-9]{2})([-~.[:space:]])([0-9]{3})([0-9]{3})([-~.[:space:]])([0-9]{2})"; // 지역번호 포함 운전면허 (11-12-123456-12 -> 11-12-123***-**)
    public final static String MASK_DRIVER3   		= "$1-$3-$5***-**"; // 지역번호 포함 운전면허 (앞 7자리 이하 전체 마스킹)
    
    public final static String REGEX_DRIVER4     	= "(\\d{2})(\\d{2})(\\d{3})(\\d{3})(\\d{2})"; // 지역번호 포함 운전면허 (111212345612 -> 11-12-123***-**)
    public final static String MASK_DRIVER4   		= "$1-$2-$3***-**"; // 지역번호 포함 운전면허 (앞 7자리 이하 전체 마스킹)
    
    public final static String REGEX_INTERTEL       = "(\\d{1,2})(\\d{1})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{2,3})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{1,2})(\\d{2})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{1})(\\d{3})";
    public final static String MASK_INTERTEL        = "$1$2$3$4$5$6**$8*$10"; //국제전화
    
    public final static String REGEX_VETERAN_ID     =  "(\\d{2})([- ]*)(\\d{5})(\\d{1})"; // 국가유공자증
    public final static String MASK_VETERAN_ID      =  "$1-$3*"; // 국가유공자증 (앞 7자리 이하 전체마스킹)
    
    // ------------- end: 사용되지않고 있는 정규식 -------------
    
    // ------------- start: 사용되고있는 정규식 -------------
    public final static String REGEX_EMAIL_ALL 		= "([a-zA-Z][\\w\\.-]*)@([a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$)"; // email
    // public final static String MASK_EMAIL_ALL  		= "***@$2"; // email 
    public final static String REGEX_NAME 		    = "([a-zA-Zㄱ-힣]{1})([a-zA-Zㄱ-힣- 　\\t\\n\\x0B\\f\\r]*)([a-zA-Zㄱ-힣]{1})";
    
    // 이미 마스킹한 전화번호를 한번 더 마스킹 메서드 태우는 경우가 존재
    public final static String REGEX_TEL_NO2  		= "(\\u0030\\u0032|\\d{3})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{3,4}|\\*{3,4})([- 　\\t\\n\\x0B\\f\\r]*)(\\d{4})$"; // 전화번호
    
    public final static String REGEX_CREDIT_CARD4   = "(\\d{4})([- ]*)(\\d{2})(\\d{2})([- ]*)(\\d{4})([- ]*)(\\d{4})"; // 16자리 신용카드
    public final static String MASK_CREDIT_CARD4    = "$1$2$3**$5****$7****"; // 16자리 신용카드 (앞 6자리 이하 전체 마스킹) 2022.10.05 적용
    
    public final static String REGEX_CREDIT_CARD5   = "(\\d{4})([- ]*)(\\d{2})(\\d{2})([- ]*)(\\d{4})([- ]*)(\\d{3})"; // 15자리 신용카드(AMEX)
    public final static String MASK_CREDIT_CARD5    = "$1$2$3**$5****$7***"; // 15자리 신용카드(AMEX) (앞 6자리 이하 전체 마스킹) 2022.10.05 적용

    public final static String REGEX_CREDIT_CARD6   = "(\\d{4})([- ]*)(\\d{2})(\\d{2})([- ]*)(\\d{4})([- ]*)(\\d{2})"; // 14자리 신용카드(Diners Club)
    public final static String MASK_CREDIT_CARD6    = "$1$2$3**$5****$7**"; // 14자리 신용카드(Diners Club) (앞 6자리 이하 전체 마스킹) 2022.10.05 적용

    public final static String MASK_CREDIT_CARD_ERR = "****-****-****-****"; // 신용카드 예외처리용
    
    public final static String REGEX_CARD_YYYYMM 	= "(\\d{4})([/\\- ]*)(\\d{2})"; // 카드유효일
    public final static String MASK_CARD_YYYYMM  	= "******"; // 카드유효일 (전체 마스킹)  2022.10.05 적용
    public final static String REGEX_CARD_YYYYMMDD 	= "(\\d{4})([/\\- ]*)(\\d{2})([/\\- ]*)(\\d{2})"; // 카드유효일
    public final static String MASK_CARD_YYYYMMDD  	= "********"; // 카드유효일 (전체 마스킹)  2022.10.05 적용
    // ------------- end: 사용되고있는 정규식 -------------
    
    public final static  char MASK_CHAR = '*';

    private static List<Character> excludeCharList;

    static {
        excludeCharList = new ArrayList<Character>();
        excludeCharList.add(new Character('-'));
    }
 
    
    /**
     * <PRE>
     * 1. MethodName : getMaskedTelNo
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 전화번호 마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:01
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedTelNo(String value){
        String ret = "";
        if (!StringUtils.isBlank(value)) {
            // ret = value.replaceAll(REGEX_TEL_NO,MASK_TEL_NO);
        	// 2022.10.05 적용 > 가운데 번호 마스킹 (010-****-1111 , 02-***-1111, 010****1111, 02***1111 등의 형식)
        	ret= getMaskedByGroupNo(value, REGEX_TEL_NO2, 3, null);
        }
        return ret;
    }

    /**
     * <PRE>
     * 1. MethodName : getMaskedEmail
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 이메일  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:23
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedEmail2(String value){
        if ("@".equals(value)) {
            return "";
        }
        return getMaskedByEmail(value);
    }

    public static String getMaskedByEmail(String value) {
        String ret = "";
        if (!StringUtils.isBlank(value)) {
        	ret= getMaskedByGroupNo(value,REGEX_EMAIL_ALL,1, null); // 2022.10.05 적용 > @ 앞자리 전체 마스킹
        }
        return ret;
    }
    
    /**
     * <PRE>
     * 1. MethodName : getMaskedId
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 아이디(ID) 마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:29
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedId(String value){
        String ret = "";
        /*if (!StringUtils.isBlank(value)) {
            ret = value.replaceAll(REGEX_ID,MASK_ID);
        }*/
        
        // 2022.10.05 적용 > 아이디 마스킹 변경 뒷 3자리 마스킹
        if (!StringUtils.isBlank(value)) {
        	if(value.length()<3) { // 아이디가 3자리 미만인경우 전체 마스킹
        		ret= getMaskedAll(value);
        	}else { // 뒤에서 3자리만 마스킹
        		StringBuffer sb = new StringBuffer(value);
        	    sb.replace(value.length() - 3, value.length(),  "***");
        	    ret = sb.toString();
        	}
        }
        return ret;
    }

    /**
     * <PRE>
     * 1. MethodName : getMaskedName
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 이름  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:45
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedName(String value){
        String ret = "";
        if (!StringUtils.isBlank(value)) {
            // ret = getMaskedValue(value, 1,false);
        	
        	// 2022.10.05 적용 
        	// > 2자 : 끝글자 마스킹 (김*)
        	// > 3자 : 중간글자 마스킹 (홍*동)
        	// > 4자이상 : 첫글자, 끝글자 외 마스킹 (외국인포함)
        	// 외국인의 경우 이름에 공백, - 포함 가능 > 공백, -은 마스킹처리하지 않음
        	
        	if(value.length()<3) { // 끝글자 마스킹
        		ret = getMaskedValue(value, 1,false);
        	}else { // 첫글자, 끝글자 외 마스킹 처리 
        		
        		List<Character> excludeCharList= new ArrayList<>(); // 마스킹 제외 대상
        		excludeCharList.add(' ');
        		excludeCharList.add('-');
        		
        		ret= getMaskedByGroupNo(value,REGEX_NAME,2,excludeCharList);
        	}
        }
        return ret;
    }

    
    /**
     * <PRE>
     * 1. MethodName : getMaskedByAddressNew2
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 주소 마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:45
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedByAddressNew2(String addr){
        String ret = "";
        // 면,읍,리,동,로,길,가
        if (!StringUtils.isBlank(addr)) {
        	int idxMyun = addr.indexOf("면 "); 
            int idxUb = addr.indexOf("읍 "); 
            int idxRi = addr.indexOf("리 ");
            int idxDong = addr.indexOf("동 "); 
            int idxRo = addr.indexOf("로 ");
            int idxGil = addr.indexOf("길 ");
            int idxGa = addr.indexOf("가 ");

            int idxMaskingStart = 0;
            if (idxGa != -1) {
              idxMaskingStart = idxGa;
            } else if (idxRo != -1) {
              idxMaskingStart = idxRo;
            } else if (idxGil != -1) {
              idxMaskingStart = idxGil;
            } else if (idxRi != -1) {
              idxMaskingStart = idxRi;
            } else if (idxUb != -1) {
              idxMaskingStart = idxUb;
            } else if (idxMyun != -1) {
              idxMaskingStart = idxMyun;
            } else if (idxDong != -1) {
              idxMaskingStart = idxDong;
            }

            idxMaskingStart += 2;

            // 2022.10.05 적용 > 시구동(로) 이하 전체 마스킹 (서울시 강남구 테헤란로 ***)
            ret = getMaskedValue(addr, idxMaskingStart, true, false);
        }
      
        return ret;
    }
    
    
    /**
     * <PRE>
     * 1. MethodName : getMaskedByCreditCard
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 신용카드 마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:10
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedByCreditCard(String orgValue) {
        int len = 0;
        String nOrgValue = orgValue;
        
        if(!StringUtils.isBlank(orgValue)) {
     	   
            nOrgValue = ((nOrgValue.replaceAll(" ", "")).replaceAll("-", ""));
            len = nOrgValue.length();

            if(len < 12) { // 카드번호 최소길이 부족
         	   nOrgValue = MASK_CREDIT_CARD_ERR;
            }else {
         	   StringBuilder orgValueTmp = new StringBuilder(nOrgValue.substring(0, 4) + "-");
                orgValueTmp.append(nOrgValue.substring(4, 8) + "-");
                orgValueTmp.append(nOrgValue.substring(8, 12) + "-");
                orgValueTmp.append(nOrgValue.substring(12, len));

                nOrgValue = orgValueTmp.toString();
                // 2022.10.05 적용 > 앞 6자리 이하 전체 마스킹
                return getMaskedCreditCard(nOrgValue,len);
            }
        }
        
        return nOrgValue;
    }	

    public static String getMaskedCreditCard(String value,int length){
        String ret = "";

        if (!StringUtils.isBlank(value)) {
            if (length==16) {
               // ret = value.replaceAll(REGEX_CREDIT_CARD,MASK_CREDIT_CARD);
            	ret = value.replaceAll(REGEX_CREDIT_CARD4,MASK_CREDIT_CARD4);
            }else if (length==15) {
               // ret = value.replaceAll(REGEX_CREDIT_CARD2,MASK_CREDIT_CARD2);
            	ret = value.replaceAll(REGEX_CREDIT_CARD5,MASK_CREDIT_CARD5);
            }else if (length==14) {
               // ret = value.replaceAll(REGEX_CREDIT_CARD3,MASK_CREDIT_CARD3);
            	ret = value.replaceAll(REGEX_CREDIT_CARD6,MASK_CREDIT_CARD6);
            }else {
                ret = MASK_CREDIT_CARD_ERR;
            }
        }

        return ret;
    }
 	
    
    /**
     * <PRE>
     * 1. MethodName : getMaskedByBankAccountNum
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 계좌번호 마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:10
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedByBankAccountNum(String orgValue) {
        String ret = "";
 	   if (!StringUtils.isBlank(orgValue)) {
 		   // ret = getMaskedValue(orgValue, 6,true,false); 
 		  ret = getMaskedValue(orgValue, 6,true,false, excludeCharList); // 앞에서 6자리 이하 전체 마스킹
 	   }
 	   return ret;
    }
    
    /**
     * <PRE>
     * 1. MethodName : getMaskedBySerialNumber
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 유심일련번호, 단말일련번호, esim번호, imei번호 등 마스킹 처리 (모든자리 마스킹)
     * 4. Author     : hansuyeon
     * 5. Create     : 2022. 10. 05 오후 2:55:10
     * </PRE>
     *   @return String
     *   @param value
     */
    public static String getMaskedBySerialNumber(String orgValue) {
       String ret = "";
 	   if (!StringUtils.isBlank(orgValue)) {
 		   ret = getMaskedAll(orgValue); // 모든자리 마스킹 처리 
 	   }
 	   return ret;
    }

    
    /**
     * <PRE>
     * 1. MethodName : getMaskedValue
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 사이즈 기반  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:51
     * </PRE>
     *   @return String
     *   @param orgValue
     *   @param maskLength ( 마스킹 처리 길이 )
     *   @param dirOption  ( 마스킹 처리 앞/뒤 여부 )
     */
    public static String getMaskedValue(String orgValue,int maskLength, boolean dirOption) {
        String result = "";
        if (StringUtils.isBlank(orgValue)) {
            result="";
        }else{
            int length = orgValue.length();
            int startIdx = 0;
            int endIdx = 0;
            if (length > 0) {
                StringBuffer sb = new StringBuffer(orgValue);

                if(dirOption){ // true 앞
                    startIdx = 0;
                    endIdx = maskLength;
                }else{ // false 뒤
                    startIdx = length - maskLength;
                    endIdx = length;
                }
                for (int i = startIdx; i < endIdx; i++) {
                    sb.setCharAt(i, MASK_CHAR);
                }
                result = sb.toString();
            }
        }
        return result;
    }

    /**
     * <PRE>
     * 1. MethodName : getMaskedValue
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 길이와 방향, 마스킹 옵션에 따른  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:54
     * </PRE>
     *   @return String
     *   @param orgValue
     *   @param maskLength (마스킹 길이)
     *   @param dirOption  (마스킹 방향)
     *   @param maskOption (마스킹 toggle)
     */
    public static String getMaskedValue(String orgValue,int maskLength, boolean dirOption,boolean toggle) {
        // maskOption : true이면 maskLength만큼 *로변경, false이면 maskLength를 제외한 나머지를 *로 변경
        String result = "";
        if (StringUtils.isBlank(orgValue)) {
            result="";
        }else{
            int length = orgValue.length();
            int startIdx = 0;
            int endIdx = 0;
            if (length > 0) {
                StringBuffer sb = new StringBuffer(orgValue);

                if(dirOption&toggle){ // true 앞, masking
                    startIdx = 0;
                    endIdx = maskLength;
                }else if(!dirOption&toggle){ // false 뒤, masking
                    startIdx = length - maskLength;
                    endIdx = length;
                }else if(dirOption&!toggle){ // true 앞,나머지를 masking
                    startIdx = maskLength;
                    endIdx = length;
                }else if(!dirOption&!toggle){ // false 뒤,나머지를 masking
                    startIdx = 0;
                    endIdx = length-maskLength;
                }

                for (int i = startIdx; i < endIdx; i++) {
                    sb.setCharAt(i, MASK_CHAR);
                }
                result = sb.toString();
            }
        }
        return result;
    }

    /**
    * <PRE>
    * 1. MethodName : getMaskedValue
    * 2. ClassName  : AbstractMaskingServiceImpl
    * 3. Comment    : 길이와 방향, 마스킹 옵션에 따른  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:54
    * </PRE>
    *   @return String
    *   @param orgValue
    *   @param maskLength (마스킹 길이)
    *   @param dirOption  (마스킹 방향)
    *   @param toggle (마스킹 toggle)
    *   @param excludeCharList (마스킹 제외Char)
    */
    public static String getMaskedValue(String orgValue, int maskLength, boolean dirOption, boolean toggle,
            List<Character> excludeCharList) {
        // maskOption : true이면 maskLength만큼 *로변경, false이면 maskLength를 제외한 나머지를 *로 변경
        String result = "";
        if (StringUtils.isBlank(orgValue)) {
            result="";
        } else {
            int length = orgValue.length();
            int startIdx = 0;
            int endIdx = 0;
            if (length > 0) {
                StringBuilder sb = new StringBuilder(orgValue);

                if(dirOption&toggle){ // true 앞, masking
                    startIdx = 0;
                    endIdx = maskLength;
                } else if(!dirOption&toggle){ // false 뒤, masking
                    startIdx = length - maskLength;
                    endIdx = length;
                } else if(dirOption&!toggle){ // true 앞,나머지를 masking
                    startIdx = maskLength;
                    endIdx = length;
                } else if(!dirOption&!toggle){ // false 뒤,나머지를 masking
                    startIdx = 0;
                    endIdx = length-maskLength;
                }

                for (int i = startIdx; i < endIdx; i++) {
                    if( excludeCharList != null && excludeCharList.contains(Character.valueOf(sb.charAt(i)))) {
                        continue;
                    }
                    sb.setCharAt(i, MASK_CHAR);
                }
                result = sb.toString();
            }
        }
        return result;
    }

    /**
     * <PRE>
     * 1. MethodName : getMaskedValue
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 인덱스 위치에 따른  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:58
     * </PRE>
     *   @return String
     *   @param orgValue
     *   @param maskLength
     *   @param sidx
     *   @param eidx
     */
    public static String getMaskedValue(String orgValue,int sidx, int eidx) {
        String result = "";
        if (StringUtils.isBlank(orgValue)) {
            result="";
        }else{
            int length = orgValue.length();
            if (length > 0) {
                StringBuffer sb = new StringBuffer(orgValue);

                for (int i = sidx; i < eidx; i++) {
                    sb.setCharAt(i, MASK_CHAR);
                }
                result = sb.toString();
            }
        }
        return result;
    }

    /**
     * <PRE>
     * 1. MethodName : getMaskedAllValue
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 전체  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:56:01
     * </PRE>
     *   @return String
     *   @param orgValue
     */
    public static String getMaskedAll(String orgValue) {
        String result = "";
        if (StringUtils.isBlank(orgValue)) {
            result="";
        }else{
            int length = orgValue.length();
            if (length > 0) {
                StringBuffer sb = new StringBuffer(orgValue);

                for (int i = 0; i < length; i++) {
                    sb.setCharAt(i, MASK_CHAR);
                }
                result = sb.toString();
            }
        }
        return result;
    }

   

    // 2014.03.08 정보보안 강화, 마스킹 처리 메소드 생성.

    /**
     * <PRE>
     * 1. MethodName : getMaskedValue
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 사이즈 기반  마스킹 처리
     * 4. Author     : archer
     * 5. Create     : 2012. 9. 10. 오후 2:55:51
     * </PRE>
     *   @return String
     *   @param orgValue
     *   @param maskLength ( 마스킹 처리 길이 )
     *   @param dirOption  ( 마스킹 처리 앞/뒤 여부 )
     */
    public static String getMaskedByValue(String orgValue,int maskLength, boolean dirOption) {
        String result = "";
        int sMaskLength = maskLength;
        if (StringUtils.isBlank(orgValue)) {
            result="";
        }else{
            int length = orgValue.length();
            int startIdx = 0;
            int endIdx = 0;
            if (length > 0) {
                StringBuffer sb = new StringBuffer(orgValue);

                if (orgValue.length() < sMaskLength) {
                    sMaskLength = orgValue.length();
                }
                if(dirOption){ // true 앞
                    startIdx = 0;
                    endIdx = sMaskLength;
                }else{ // false 뒤
                    startIdx = length - sMaskLength;
                    endIdx = length;
                }
                for (int i = startIdx; i < endIdx; i++) {
                    sb.setCharAt(i, MASK_CHAR);
                }
                result = sb.toString();
            }
        }
        return result;
    }

  

    /**
     * <PRE>
     * 1. MethodName : getMaskedByGroupNo
     * 2. ClassName  : MaskingUtil
     * 3. Comment    : 매칭할 값, 정규표현식, 마스킹 처리할 하나의 그룹 번호를 전달하면, 해당 그룹만 마스킹된 값을 리턴한다. (excludeCharList에 포함된 문자는 제외하고 마스킹) 
     * 4. Author     : hansuyeon
     * 5. Create     : 2022. 09. 20. 오후 17:48:01
     * </PRE>
     *   @return String
     *   @param target, regExp, groupNo
    */
	private static String getMaskedByGroupNo(String target, String regExp, int groupNo, List<Character> excludeCharList) {
		 String ret = "";
	     if (!StringUtils.isBlank(target)) {
	    	 
    	 	// 정규표현식과 값이 일치하는지 확인
    		Matcher matcher = Pattern.compile(regExp).matcher(target);
    		if(matcher.find()) { // 지정한 그룹 마스킹
    			 int strIndex= matcher.start(groupNo); // 지정된 그룹이 매칭되는 시작인덱스
    			 int endIndex= matcher.end(groupNo); // 지정된 그룹이 매칭되는 끝인덱스+1
 
    			 StringBuilder sb = new StringBuilder(target);
     			 
     			 for (int i = strIndex; i < endIndex; i++) {
                     if( excludeCharList != null && excludeCharList.contains(Character.valueOf(sb.charAt(i)))) {
                        continue;
                     }
                     sb.setCharAt(i, MASK_CHAR);
                 }
     			 
     			 ret= sb.toString();
    		}else { // 일치하지 않는 경우 전체 마스킹
    			ret = getMaskedAll(target);
    		}
	     }
	     return ret;
	}
    
   public static String getMaskedCardByExpire(String value) {
        String ret = "";
        if (!StringUtils.isBlank(value)) {
        	
        	String nOrgValue= value.replaceAll(" ", "");
        	nOrgValue= value.replaceAll("/", "");
        	nOrgValue= value.replaceAll("-", "");
        	
        	int len= nOrgValue.length();
        	
            if (len == 8) {
                ret = value.replaceAll(REGEX_CARD_YYYYMMDD,MASK_CARD_YYYYMMDD);
            } else {
                ret = value.replaceAll(REGEX_CARD_YYYYMM,MASK_CARD_YYYYMM);
            }
        }
        return ret;
   }
	
   // -------------------- start: 아직 사용되는 곳이 없는 메서드 --------------------
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedSSN
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 주민등록번호  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:52:59
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedSSN(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           ret = value.replaceAll(REGEX_SSN_DASH,MASK_SSN_DASH);
       }
       return ret;
   }
   
   
   public static String getMaskedSSN2(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           ret = value.replaceAll(REGEX_SSN_DASH2,MASK_SSN_DASH2);
       }
       return ret;
   }
   
   public static String getMaskedSSNWithoutDash(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           ret = value.replaceAll(REGEX_SSN,MASK_SSN);
       }
       return ret;
   }
   
   public static String getMaskedByTelNo(String orgValue) {
	   return getMaskedTelNo(orgValue);
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedBusiNo
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 법인번호/사업자번호   마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:54:12
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedBusiNo(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           ret = value.replaceAll(REGEX_BUSI_NO,MASK_BUSI_NO);
       }
       return ret;
   }
   
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedTel444
    * 2. ClassName  : MaskingUtils
    * 3. Comment    : 전화번호(444포멧) 마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 19. 오전 10:09:11
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedTel444(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           // ret = value.replaceAll(REGEX_TEL_444,MASK_TEL_444);
       	ret = value.replaceAll(REGEX_TEL2_444,MASK_TEL2_444);
       }
       return ret;
   }
   
   /**
   * <PRE>
   * 1. MethodName : getMaskedInterTel
   * 2. ClassName  : AbstractMaskingServiceImpl
   * 3. Comment    : 국제전화번호  마스킹 처리
   * 4. Author     : archer
   * 5. Create     : 2012. 9. 10. 오후 2:52:59
   * </PRE>
   *   @return String
   *   @param value
   */
   public static String getMaskedInterTel(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           ret = value.replaceAll(REGEX_INTERTEL, MASK_INTERTEL);
       	
       	   if(ret.indexOf("*")!= -1 && ret.lastIndexOf("*") > ret.indexOf("*")) {
            	// +82-10-*1234*-5678 형식을 +82-10-****-5678 형식으로 변경
            	ret= ret.substring(0, ret.indexOf("*")) 
            		 + ( ret.substring(ret.indexOf("*")+1, ret.lastIndexOf("*")) ).replaceAll("\\d", "*")
            		 + ret.substring(ret.lastIndexOf("*")+1);
           }
       	
       }
       return ret;
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedCardExpire
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 카드 유효기간  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:15
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedCardExpire(String value){
       return getMaskedCardByExpire(value);
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedBankNo
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 은행계좌번호  마스킹 처리 (앞 6자리 이하 전체 마스킹)
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:20
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedBankNo(String value){
       String ret = "";
       // 앞 6자리 이하 전체 마스킹
       if (!StringUtils.isBlank(value)) {
           ret = getMaskedValue(value, 6,true,false, excludeCharList);
       }
       return ret;
   }
   
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedEmail
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 이메일  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:23
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedEmail(String value){
       return getMaskedByEmail(value);
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedIpAddr
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : IP주소  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:38
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedIpAddr(String value){
       return getMaskedByIP(value);
   }

   
   /**
    * <PRE>
    * 1. MethodName : getMaskedPassportNo
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 여권번호  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:42
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedPassportNo(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           ret = getMaskedValue(value, 4,false);
       }
       return ret;
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedDriver
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 운전면허  마스킹 처리
    * 4. Author     : archer
    * 5. Create     : 2012.11. 19. 오후 2:55:42
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedDriver(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           // ret = value.replaceAll(REGEX_DRIVER2,MASK_DRIVER2);
       	ret = value.replaceAll(REGEX_DRIVER3,MASK_DRIVER3);
       }
       return ret;
   }
   
   
   public static String getMaskedDriverWithoutDash(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
           // ret = value.replaceAll(REGEX_DRIVER2,MASK_DRIVER2);
       	ret = value.replaceAll(REGEX_DRIVER4,MASK_DRIVER4);
       }
       return ret;
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedVeteranId
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 국가유공증 마스킹 처리
    * 4. Author     : hsy
    * 5. Create     : 2022.09. 20. 오후 2:18:42
    * </PRE>
    *   @return String
    *   @param value
    */
   public static String getMaskedVeteranId(String value){
       String ret = "";
       if (!StringUtils.isBlank(value)) {
       	ret = value.replaceAll(REGEX_VETERAN_ID,MASK_VETERAN_ID);
       }
       return ret;
   }
   
   /**
    * <PRE>
    * 1. MethodName : getMaskedAddress
    * 2. ClassName  : MaskingUtil
    * 3. Comment    : 주소  마스킹 처리 (전체마스킹)
    * 4. Author     : archer
    * 5. Create     : 2012. 9. 10. 오후 2:55:48
    * </PRE>
    *   @return String
    *   @param addr
    *   @param bunziAfter
    */
   public static String getMaskedAddress(String addr, String bunziAfter){
       String ret = "";
       if (!StringUtils.isBlank(addr) && !StringUtils.isBlank(bunziAfter)) {
          ret = getMaskedAll(addr) +" "+getMaskedAll(bunziAfter);   // 2014.03.11 전체 주소 마스킹 처리로 인하여 수정함.
       }
       return ret;
   }
   
   public static String getMaskedPassword(String orgValue) {
        return getMaskedAll(orgValue);
   }
	
   public static String getMaskedBySSN(String value) {
        return getMaskedSSN(value);
    }

    public static String getMaskedByPassportNo(String orgValue) {
        return getMaskedPassportNo(orgValue);
    }
	
   public static String getMaskedByDriver(String value) {
	   String ret = "";
	   if (!StringUtils.isBlank(value)) {
		   // ret = value.replaceAll(REGEX_DRIVER2,MASK_DRIVER2);
		   ret = value.replaceAll(REGEX_DRIVER4,MASK_DRIVER4);
	   }
	   return ret;
   }
	
   public static String getMaskedByPassword(String orgValue) {
	   return getMaskedAll(orgValue);
   }
	
   public static String getMaskedByName(String value) {
	   return getMaskedName(value);
   }
	
   public static String getMaskedByCtn(String orgValue) {
	   return "";
   }
	
   public static String getMaskedByAddress(String addr, boolean isNewAddr){
	   return getMaskedAll(addr);
   } 
	
	
   public static String getMaskedByOldAddress(String addr) {
        return getMaskedAll(addr);
   }
	
   public static String getMaskedByNewAddress(String addr) {
        return getMaskedAll(addr);
   }
	 
   public static String getMaskedByAddressLast6(String addr){
	   String ret = "";
	   if (!StringUtils.isBlank(addr)) {
	       if (addr.length() < 7) {
	           ret = getMaskedAll(addr);
	       } else {
	           ret = getMaskedByValue(addr, 6, false);
	       }
	   }
	
	   return ret;
   }
	
   public static String getMaskedByAddressNew(String addr1, String addr2){
        String ret = "";
        String ret2 = addr2;
        if (!StringUtils.isBlank(addr1)) {
            if (ret2 == null) ret2 = "";
            String addr = addr1.trim() + " " + ret2;

            // 면,읍,리,동,로,길,가
            int idxMyun = addr.indexOf("면 ");
            int idxUb = addr.indexOf("읍 ");
            int idxRi = addr.indexOf("리 ");
            int idxDong = addr.indexOf("동 ");
            int idxRo = addr.indexOf("로 ");
            int idxGil = addr.indexOf("길 ");
            int idxGa = addr.indexOf("가 ");

            int idxMaskingStart = 0;
            if (idxGa != -1) {
                idxMaskingStart = idxGa;
            } else if (idxRo != -1) {
                idxMaskingStart = idxRo;
            } else if (idxGil != -1) {
                idxMaskingStart = idxGil;
            } else if (idxRi != -1) {
                idxMaskingStart = idxRi;
            } else if (idxUb != -1) {
                idxMaskingStart = idxUb;
            } else if (idxMyun != -1) {
                idxMaskingStart = idxMyun;
            } else if (idxDong != -1) {
                idxMaskingStart = idxDong;
            }

            idxMaskingStart += 2;

            ret = getMaskedValue(addr, idxMaskingStart, true, false); // 시구동(로) 이하 전체 마스킹 (서울시 강남구 테헤란로 ***)
        }
        return ret;
   }
	

   public static String getMaskedClubByCardNo(String cardNo) {
        String retCardNo = "";

        if (!StringUtils.isBlank(cardNo)) {

            if (cardNo.length() == 16) {
                // retCardNo = cardNo.substring(0, 4) + "-" + cardNo.substring(4, 6) + "**"+ "-****-***" + cardNo.substring(15) ;
            	retCardNo = cardNo.substring(0, 4) + "-" + cardNo.substring(4, 6) + "**"+ "-****-****"; // 앞 6자리 이하 전체 마스킹 2022.10.05 적용
            } else {
                StringBuffer sb = new StringBuffer();
                int length = cardNo.length(); // 15자리, 14자리
                if (length > 0) {
                    int beginIndex = (length/2)-1; // 앞 6자리 이하 전체 마스킹 2022.10.05 적용
                    sb.append(cardNo.substring(0, beginIndex));
                    for (int i = beginIndex;i< length;i++) {
                        sb.append("*");
                    }
                    retCardNo = sb.toString();
                } else {
                    retCardNo = cardNo;
                }

            }
        }

        return retCardNo;
   }

   public static String getMaskedBySvcNo(String value) {
        String ret = "";
        if (!StringUtils.isBlank(value)) {
            if (value.matches(REGEX_TEL_NO2)) {
                ret = getMaskedTelNo(value);
            } else if (value.matches(REGEX_TEL2_444)) {
                ret = getMaskedTel444(value);
            } else if (value.matches(REGEX_ID)) {
                ret = getMaskedId(value);
            } else {
                int idxSubstr = value.length();
                if (idxSubstr > 3) {
                    idxSubstr = 3;
                }
                ret = getMaskedByValue(value, idxSubstr,false); // 뒤에서 세자리 마스킹
            }

        }
        return ret;
   }

   public static String getMaskedByIP(String value) {
        String ret = "";
        if (!StringUtils.isBlank(value)) {
            // ret = value.replaceAll(REGEX_IPADDR,MASK_IPADDR2);
        	ret = value.replaceAll(REGEX_IPADDR,MASK_IPADDR3);
        }
        return ret;
   }

   public static String getMaskedByBan(String value) {
        String ret = "";
        if (!StringUtils.isBlank(value)) {
            ret = getMaskedByValue(value, 3,false);
        }
        return ret;
   }
   
   // -------------------- end: 아직 사용되는 곳이 없는 메서드 --------------------

}
