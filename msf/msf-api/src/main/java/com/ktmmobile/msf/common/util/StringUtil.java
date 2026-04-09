package com.ktmmobile.msf.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil extends StringUtils{

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    //비밀번호 패턴

    //* 영문, 숫자, 특수문자 중 3종류를 조합하여 8~16자리
    @Deprecated
    private static final String Passwrod_PATTERN_8 = "^(?=.*[a-zA-Z\\s]+)(?=.*[0-9]+)(?=.*[!@#$%^*+=-]+).{8,16}$";
    //* 영문, 숫자, 특수문자 중 2종류를 조합하여 10~16자리
    @Deprecated
    private static final String Passwrod_PATTERN_TEXT_NUM10 = "^(?=.*[a-zA-Z\\s]+)(?=.*[0-9]+).{10,16}$";
    @Deprecated
    private static final String Passwrod_PATTERN_NUM_SPECIAL10 = "^(?=.*[!@#$%^*+=-]+)(?=.*[0-9]+).{10,16}$";
    @Deprecated
    private static final String Passwrod_PATTERN_TEXT_SPECIAL10 = "^(?=.*[a-zA-Z\\s]+)(?=.*[!@#$%^*+=-]+).{10,16}$";

    //* 영문 숫자 특수기호 조합 10자리 이상 (정규식)
    private static final String  Passwrod_PATTERN_10 = "(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,15}$";  //



    public static boolean isNotNull(String str){
        boolean result = StringUtils.isEmpty(str);
        if(result){
            return !result;
        }

        if(StringUtil.equals("null", str)){
            return false;
        }

        return true;
    }

    public static String NVL(String parameter, String nullChange) {

        if (isBlank(parameter)) {
            return nullChange;
        }
        return parameter;
    }

    public static boolean isBlank(String param) {
        return StringUtils.isBlank(param);
    }

    /**
     * int형 값 반환 default 0
     */
    public static int toInteger(String str){
        int result = 0;
        try{
            result = Integer.parseInt(str);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static long toLong(String str){
        long result = 0;

        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 숫자인지 체크 함수
     */
    public static boolean isNumeric(String str){
        try{
            Double.parseDouble(str) ;
            return true ;
        } catch (NumberFormatException e) {
            return false ;
        } catch(Exception e){
            return false ;
        }

    }
    public static String booleanToYN(boolean set){
        String result = "N";
        try{
            if(set) result = "Y";
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static String booleanToString(boolean set){
        String result = "false";
        try{
            if(set) result = "true";
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static boolean YNToBoolean(String set){
        boolean result = false;
        try{
            if("Y".equals(set)) result = true;
        }catch (Exception e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 문자열을 바이트 단위로 substring하기
     *
     * new String(str.getBytes(), 0, endBytes) 코드를 사용하면
     * 한글 바이트에 딱 맞춰 자르지 않는 경우 깨지는 문제가 있어서 따로 메서드를 만들었다.
     *
     * UTF-8 기준 한글은 3바이트, 알파벳 대소문자나 숫자 및 띄어쓰기는 1바이트로 계산된다.
     *
     * @param str
     * @param beginBytes
     * @param endBytes
     * @return
     */
    public static String substringByBytes(String str, int beginBytes, int endBytes) {
        if (str == null || str.length() == 0) {
            return "";
        }

        int intBeginBytes = beginBytes ;
        if (intBeginBytes < 0) {
            intBeginBytes = 0;
        }

        if (endBytes < 1) {
            return "";
        }

        int len = str.length();

        int beginIndex = -1;
        int endIndex = 0;

        int curBytes = 0;
        String ch = null;
        for (int i = 0; i < len; i++) {
            ch = str.substring(i, i + 1);
            curBytes += ch.getBytes().length;
            if (beginIndex == -1 && curBytes >= intBeginBytes) {
                beginIndex = i;
            }

            if (curBytes > endBytes) {
                break;
            } else {
                endIndex = i + 1;
            }
        }

        return str.substring(beginIndex, endIndex);
    }

    public static String equalsReplace(String str1, String str2, String replace){
        String result = str1;
        try{
            if(StringUtil.equals(str1, str2))
                result = replace;
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static String decode(String str1, String str2, String ifStr, String elseStr){
        String result = str1;
        try{
            if( StringUtil.equalsIgnoreCase(str1, str2) ) result = ifStr;
            else result = elseStr;
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public static String decode(boolean flag, String ifStr, String elseStr){
        String result = ifStr;
        try{
            if(flag) result = ifStr;
            else result = elseStr;
        }catch (Exception e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        }

        result[0] = phoneNum1;
        result[1] = phoneNum2;
        result[2] = phoneNum3;

        return result;
    }

    /**
     * 전화번호(FAX) 자릿수 자르기
     */
    public static String getDivPhoneNum(String phoneNum){
        String phoneNum1 = "";
        String phoneNum2 = "";
        String phoneNum3 = "";

        try {
            if(phoneNum.length() == 11){
                phoneNum1 = phoneNum.substring(0, 3);
                phoneNum2 = phoneNum.substring(3, 7);
                phoneNum3 = phoneNum.substring(7, 11);
            } else if(phoneNum.length() == 10){
                phoneNum1 = phoneNum.substring(0, 3);
                phoneNum2 = phoneNum.substring(3, 6);
                phoneNum3 = phoneNum.substring(6, 10);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return phoneNum1 + "-" + phoneNum2 + "-" + phoneNum3;
    }

    /**
     * 휴대전화번호 자릿수 자르기
     */
    public static String[] getMobileNum(String value){
        String[] result = new String[3];
        String mobileNum1 = "";
        String mobileNum2 = "";
        String mobileNum3 = "";
        String moblieNum = StringUtil.replace(value, "-", "");
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
            logger.error(e.getMessage());
        }

        result[0] = mobileNum1;
        result[1] = mobileNum2;
        result[2] = mobileNum3;

        return result;
    }

    public static String getMobileFullNum(String value) {
        String[] result = getMobileNum(value);
        return result[0] + "-" + result[1] + "-" + result[2];
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        }
        result[0] = email_id;
        result[1] = address;

        return result;
    }

    public static String[] getCardNumber(String str){
        String[] result = new String[4];
        String org = StringUtil.replace(str, "-", "");
        if(org.length() == 16){
            result[0] = StringUtil.substring(org, 0, 4);
            result[1] = StringUtil.substring(org, 4, 8);
            result[2] = StringUtil.substring(org, 8, 12);
            result[3] = StringUtil.substring(org, 12, 16);
        }

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
            logger.error(e.getMessage());
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

    public static String cutNearestWon(String str){
        String result=null;
        if(null !=str && !str.equals("0") && !str.equals("Infinity")){
            //jstl에서 넘어오는 값이 소수점으로 넘어온다
            int i= Integer.parseInt(str.split("\\.")[0]);
            i = (int) (Math.floor(i/10)*10);
            result = i+"";
        }
        return result;
    }

    public static int getRandomInt(int leng){
        int result=0;

        Random numGen = null;
        try {
            numGen = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }

        if(leng>0){
            double check= Math.floor(numGen.nextDouble()*leng);
            result = (int) check+1;
        }
        return result;
    }

     /**
     * 휴대전화번호 패턴 체크
     * @param mobileCnt 전화번호
     * @return
     */
    public static boolean checkMobile(String mobileCnt) {
        boolean rtnObj = false;
        //String  regex = "^01(?:0|1[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
        // 010으로 시작하는 11자리 번호와 011, 016, 017, 018, 019로 시작하는 10자리 번호 처리
        String regex = "^010\\d{8}$|^01[1-9]\\d{7}$";
        rtnObj = Pattern.matches(regex, mobileCnt);

        return rtnObj;

    }



    /**
     * <pre>
     * 설명    : binary 를 받아서 base64 Encoding 처리한다.
     * @param buffer byte[]
     * @return base64 인코딩된 스트링 문자열
     * </pre>
     */
    public static String encodeBase64String(byte[] buffer) {
        try {
            return org.apache.commons.codec.binary.Base64.encodeBase64String(buffer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }


    /**
     * Html Name,Code 로 최환된 값을 Html Tag로 원복한다.
     * @method decodeXssTag
     * @author ant
     * @since 2016. 8. 8.
     * @param value
     * @return
     */
    public static String decodeXssTag(String value) {
        if (value == null) {
            return "";
        }

        String cleanString = value;

        cleanString = cleanString.replaceAll("\\%26", "&");
        cleanString = cleanString.replaceAll("\\%2B", "+");
        cleanString = cleanString.replaceAll("\\%3F", "?");

        cleanString = cleanString.replaceAll("&amp;", "&");
        cleanString = cleanString.replaceAll("&lt;",  "<");
        cleanString = cleanString.replaceAll("&gt;",  ">");
        cleanString = cleanString.replaceAll("&#40;", "(");
        cleanString = cleanString.replaceAll("&quot;","\"");
        cleanString = cleanString.replaceAll("&#x27;","'");
        cleanString = cleanString.replaceAll("&#x2F;","/");
        cleanString = cleanString.replaceAll("&#41;", ")");

        cleanString = cleanString.replaceAll("&amp;#40;", "(");
        cleanString = cleanString.replaceAll("&amp;#x27;","'");
        cleanString = cleanString.replaceAll("&amp;#x2F;","/");
        cleanString = cleanString.replaceAll("&amp;#41;", ")");

        cleanString = cleanString.replaceAll("&nbsp;","");

        return cleanString;
    }

    /**
     * <pre>
     * 설명    : base64스트링 된 문자열을 받아서 byte배열로 치환한다.
     * @param targetStr base64된 문자열
     * @return binary
     * </pre>
     */
    public static byte[] decodeBase64(String targetStr) {
        try {
            return org.apache.commons.codec.binary.Base64.decodeBase64(targetStr);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null ;
        }
    }


    /**
     * 패스워드 패턴 체크 - 문자 조합 자릿수 체크, 동일 문자 연속 3자리 체크, 연속 문자 3자리 체크, 키보드 연속 입력 3자리 체크
     * @param pw 패스워드
     * @return
     */
    public static boolean passwordPatternCheck(String pw) {

        boolean rtnObj = true;

        boolean checkPw1=  Pattern.matches(Passwrod_PATTERN_10, pw);
        /*
         * boolean checkPw2= Pattern.matches(Passwrod_PATTERN_TEXT_NUM10, pw); boolean
         * checkPw3= Pattern.matches(Passwrod_PATTERN_NUM_SPECIAL10, pw); boolean
         * checkPw4= Pattern.matches(Passwrod_PATTERN_TEXT_SPECIAL10, pw);
         */

        //영문,숫자,특수문자 자릿수 조합 체크
        if(!checkPw1 ) {
            return false;
        }

        //동일 문자 연속 체크 3자리 체크
        char chr_pass;
        for (int i = 0; i < pw.length(); i++) {
            chr_pass = pw.charAt(i);
            if ((pw.length() - 3) >= i ) {
                if (chr_pass == pw.charAt(i+1) && chr_pass == pw.charAt(i+2)) {
                    return false;
                }
            }
        }

        //연속 문자 체크 3자리 체크
        int SamePass_1 = 0; //연속성(+) 카운드
        int SamePass_2 = 0; //연속성(-) 카운드

        char chr_pass_0;
        char chr_pass_1;
        char chr_pass_2;

        for (int i = 0; i < pw.length(); i++) {
            chr_pass_0 = pw.charAt(i);
            int indexTmp = i + 1;
            if (pw.length() > indexTmp) {
                chr_pass_1 = pw.charAt(indexTmp);
            } else {
                chr_pass_1 = 0;
            }

            int indexTmp2 = i + 2;
            if (pw.length() > indexTmp2) {
                chr_pass_2 = pw.charAt(indexTmp2);
            } else {
                chr_pass_2 = 0;
            }

            // 연속성(+) 카운드
            if (chr_pass_0 - chr_pass_1 == 1 && chr_pass_1 - chr_pass_2 == 1) {
                SamePass_1 = SamePass_1 + 1;
            }

            // 연속성(-) 카운드
            if (chr_pass_0 - chr_pass_1 == -1 && chr_pass_1 - chr_pass_2 == -1) {
                SamePass_2 = SamePass_2 + 1;
            }
        }

        if (SamePass_1 > 0 || SamePass_2 > 0) {
            return false;
        }

        // 키보드 연속 3자리 값 입력 체크 START
        boolean keyboardCheck = false;
        String listThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
        StringBuffer listThreeChar_sbf= new StringBuffer(listThreeChar);
        String listThreeChar_r = listThreeChar_sbf.reverse().toString();

        String[] arrThreeChar = listThreeChar.split("\\|");
        for (int i=0; i<arrThreeChar.length; i++) {
            if (pw.toLowerCase().matches(".*" + arrThreeChar[i] + ".*")) {
                keyboardCheck = true;
            }
        }

        String[] arrThreeChar_r = listThreeChar_r.split("\\|");
        for (int i=0; i<arrThreeChar.length; i++) {
            if (pw.toLowerCase().matches(".*" + arrThreeChar_r[i] + ".*")) {
                keyboardCheck = true;
            }
        }

        if (keyboardCheck) {
            return false;
        }
        // 키보드 연속 3자리 값 입력 체크 END

        return rtnObj;
    }

    /**
     * 숫자포맷출력
     *
     * @param Long value
     * @return
     */
    public static String addComma(long val) {
        DecimalFormat df = new DecimalFormat("###,##0");
        return df.format(val);
    }

    /**
     * 숫자포맷출력
     *
     * @param String value
     * @return
     */
    public static String addComma(String val) {
        long rslt = 0;

        try {
            rslt = Long.parseLong(val);
        } catch (Exception e) {
            return "";
        }

        return StringUtil.addComma(rslt);
    }

    public static final String strtoDateStr(String cToStr, String type) {
        if (cToStr != null && !cToStr.equals("") && cToStr.length() == 8) {
            return cToStr.substring(0,4) + type + cToStr.substring(4,6) + type + cToStr.substring(6,8);
        }
        return "";
    }


    /*
     * 특수문자 제거
     * 한글유니코드(\uAC00-\uD7A3), 숫자 0~9(0-9), 영어 소문자a~z(a-z), 대문자A~Z(A-Z), 공백(\s)를 제외한(^) 단어일 경우 체크
     */
    public static String  onlyStringReplace(String str){
            String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
            String retStr =str.replaceAll(match, "");
            return retStr;
    }

    /*
     * 숫자만..
     */
    public static String onlyNum(String str){
        if (str == null) {
            return "";
        }
        String match = "[^0-9]";

        String retStr =str.replaceAll(match, "");
        return retStr;
    }


    /**
     * 주소 입력  
     * 한글, 숫자, 영문, 공백, 기본 특수문자( . - , ( ) / ) 총 6종
     * 허용
     */
    public static boolean isAllowedAddr(int cp) {
        // 한글
        if (cp >= 0xAC00 && cp <= 0xD7A380) return true;

        // 한글 자모 (ㄱ~ㆎ)
        if (cp >= 0x3131 && cp <= 0x318E) return true;

        // 한글 자모 확장 (ᄀ~ᇿ) — 대부분 필요 없음, 그래도 포함 가능
        if (cp >= 0x1100 && cp <= 0x11FF) return true;

        // 숫자
        if (cp >= '0' && cp <= '9') return true;
        // 영문
        if ((cp >= 'A' && cp <= 'Z') || (cp >= 'a' && cp <= 'z')) return true;

        // 공백
        if (cp == ' ') return true;

        // 주소 특수문자 #&+
        switch (cp) {
            case '.': case ',': case '(': case ')':
            case '#': case '+': case '/': case '-':
                return true;
            default:
                return false;
        }

    }

    public static String yyyymmddDot(String paramstr,String type) {

        String str = StringUtil.NVL(paramstr,"");
        if(!"".equals(str)) {
            if(str.length() >= 8) {
                if("99991231".equals(str)) {
                    str = "-";
                } else {
                    str = str.substring(0, 4)+type+str.substring(4, 6)+type+str.substring(6, 8);
                }
            }
        }

        return str;
    }


    public static String safeCastToString(Object obj) {
        return (obj instanceof String) ? (String) obj : (obj != null ? obj.toString() : null);
    }

    public static String generateNumbers(int num) throws NoSuchAlgorithmException {
        int iterations;
        if (num < 1 || num > 6) {
            iterations = 6;
        } else {
            iterations = num;
        }

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}



