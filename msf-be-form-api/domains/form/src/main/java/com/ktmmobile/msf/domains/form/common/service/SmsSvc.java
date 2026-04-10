package com.ktmmobile.msf.domains.form.common.service;

import java.util.Map;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscOtpSvcInfoRes;


public interface SmsSvc {

    /**
     * SMS 본인 인증번호 전송
     */
    public Map<String, Object> sendSmsForAuth(String rMobile, boolean isCallbackOld, String message);
    /**
     * SMS 번호와 이름으로 유저체크
     */
    public Map<String, Object> sendSmsUserCheck(String rName, String rMobile);
    /**
     * SMS 전송
     */
    public Map<String, Object> sendSmsApply(String rName, String rMobile);
    /**
     * SMS 전송 값을 setting 하여 넘긴다
     */
    public int sendSms(String rMobile, String eventCode,String callBack, String message);

    public int sendLms(String subject , String rMobile, String message) ;

    public int sendLms(String subject , String rMobile, String message, String pCallCenter) ;

    public int sendKakaoNoti(String subject , String rMobile, String message, String pCallCenter, String kTemplateCd, String senderKey) ;

    /**
     * <pre>
     * 설명     : SMS전송
     * kTemplateCd 존재 여부에 따라...
     * sendKakaoNoti , sendLms 분기 처리
     * @return
     * @return: kTemplateCd
     * </pre>
     */
    public int sendMsgQueue(String subject , String rMobile, String message, String pCallCenter, String kTemplateCd, String senderKey) ;
    /**
     * SellUsim SMS
     */
    public void sendSellUsimSms(boolean isCallbackOld, String message);




    /**
     * MMS 발송
     */
    public int sendMms(String subject,String rMobile, String url, String pCallCenter, int fileSize);

    //신규 모듈 문자 발송
    public Map<String, Object> sendSmsForAuth(String rMobile, boolean isCallbackOld, String message,String reserved02, String reserved03);

    public int sendSms(String rMobile, String callBack, String message, String reserved02, String reserved03);

    public int sendLms(String subject , String rMobile, String message, String pCallCenter, String reserved02, String reserved03) ;

    public int sendMsgQueue(String subject , String rMobile, String message, String pCallCenter, String kTemplateCd, String senderKey,String reserved02, String reserved03) ;

    public int sendKakaoNoti(String subject , String rMobile, String message, String pCallCenter, String kTemplateCd, String senderKey, String reserved02) ;
}
