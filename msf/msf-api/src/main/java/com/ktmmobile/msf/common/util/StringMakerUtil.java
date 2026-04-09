package com.ktmmobile.msf.common.util;

import org.apache.commons.lang.StringUtils;

public class StringMakerUtil {

    /**
     * 이름 뒤 1글자
     */
    public static String getName(String str){
        if (str == null) {
            return "";
        }
        // return StringUtil.substring(str, 0, str.length() - 1) + "*";

        // 2022.10.05 이름 마스킹 규칙 변경
        return MaskingUtil.getMaskedName(str);
    }

    /**
     * 전화번호 국번  뒤2글자,  번호  앞1글자  (000-00**-*000) > 가운데 번호 마스킹으로 규칙 변경
     */
    public static String getPhoneNum(String str){
        String[] nums = StringUtil.getMobileNum(str);

//        StringBuffer sb = new StringBuffer();
//        if(str.length() > 9){
//            sb.append(nums[0]);
//            sb.append("-");
//            sb.append(StringUtil.substring(nums[1], 0, nums[1].length() - 2) + "**");
//            sb.append("-");
//            sb.append("*" + StringUtil.substring(nums[2], 1));
//        }
//        return sb.toString();

        // nums[0] > 전화번호 앞자리
        // nums[1] > 전화번호 중간자리
        // nums[2] > 전화번호 끝자리

        String telNo= nums[0]+"-"+nums[1]+"-"+nums[2];
        return MaskingUtil.getMaskedTelNo(telNo);
    }

    /**
     * 주소 마스킹 : 번지  이후 > 시구동(로) 이하 전체 마스킹으로 규칙 변경
     */
    public static String getAddress(String str){
//        StringBuffer sb = new StringBuffer();
//        String l_expressionString =  "([동면리로])[^동면리로]*$";
//        String replaceStr = str.replaceAll( l_expressionString, "" );
//        sb.append(StringUtil.rightPad(replaceStr, str.length(), "*"));
//        return sb.toString();

        // 마스킹해제 인증 완료 시
        if(SessionUtils.getMaskingSession() > 0 ) {
            return str;
        }
        return MaskingUtil.getMaskedByAddressNew2(str);
    }

    /**
     * email 앞3글자 > email 전체 마스킹 (@앞자리 전체 마스킹)으로 규칙 변경
     */
    public static String getEmail(String str){
//        StringBuffer sb = new StringBuffer();
//        if(str.length() > 3){
//            sb.append("***" + StringUtil.substring(str, 3));
//        }
//        return sb.toString();

        // 마스킹해제 인증 완료 시
        if(SessionUtils.getMaskingSession() > 0 ) {
            return str;
        }
        return MaskingUtil.getMaskedEmail2(str);
    }

    /**
     * 계좌번호 앞 6글자 표기
     */
    public static String getBankNumber(String str){
        StringBuffer sb = new StringBuffer();
        if(str.length() > 6){
            sb.append(StringUtil.rightPad(StringUtil.substring(str, 0, 6), str.length(), "*"));
        }
        return sb.toString();
    }

    /**
     * 신용카드 뒤 8자리 > 신용카드 앞 6자리 이하 전체 마스킹
     */
    public static String getCardNumber(String str){
//        StringBuffer sb = new StringBuffer();
//        String[] nums = StringUtil.getCardNumber(str);
//        sb.append(nums[0]);
//        sb.append("-");
//        sb.append(nums[1]);
//        sb.append("****-****");
//        return sb.toString();
        return MaskingUtil.getMaskedByCreditCard(str);
    }


    /**
     * 111111*********1 로 마스킹 처리된 상태로 데이터 들어오는 경우 > 신용카드 앞 6자리 이하 전체 마스킹 재 적용
     */
    public static String getCardNumber2(String str){

        String ret= "";
        String nOrgValue = str;
        if (!StringUtils.isBlank(str)) {
            nOrgValue = ((nOrgValue.replaceAll(" ", "")).replaceAll("-", ""));
            ret= nOrgValue.substring(0,nOrgValue.length()-1)+"*";
        }

        return ret;
    }

    /**
     * 카드유효일 (전체 마스킹)
     */
    public static String getCardExpirDate(String str){
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtil.rightPad("", str.length(), "*")); // 전체 마스킹
        return sb.toString();
    }

    /**
     * 카드유효일 (전체 마스킹) > MaskingUtil 적용
     */
    public static String getCardExpirDate2(String str){

        // 마스킹해제 인증 완료 시
        if(SessionUtils.getMaskingSession() > 0 ) {
            return str;
        }

        return MaskingUtil.getMaskedCardByExpire(str);
    }

    /**
     * 아이디 뒤 4글자 > 아이디 뒤 3글자로 마스킹 규칙 변경
     */
    public static String getUserId(String str){
        // return StringUtil.substring(str, 0, str.length() - 4) + "****";
        return MaskingUtil.getMaskedId(str);
    }

}
