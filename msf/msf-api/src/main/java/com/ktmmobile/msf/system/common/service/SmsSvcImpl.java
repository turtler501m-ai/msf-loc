package com.ktmmobile.msf.system.common.service;

import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.ktmmobile.msf.system.common.dao.SmsDao;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpErropPageException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;


@Service
public class SmsSvcImpl implements SmsSvc{

    @Autowired
    private MplatFormService mplatFormService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;


//    @Autowired
//    LoginDao loginDao;

    @Autowired
    SmsDao smsDao;

    //private static final String domain = "ktmmobile.com";

    private static Logger logger = LoggerFactory.getLogger(SmsSvcImpl.class);

    //sms 프로퍼티 읽어오기
    @Value("${sms.eventcode}")
    private String eventCode;

    @Value("${sms.callcenterold}")
    private String callCenterOld;

    @Value("${sms.callcenter}")
    private String callCenter;

    @Value("${sms.domain}")
    private String domain;

    @Value("${sms.usim.rcptNo}")
    private String rcptNo;

    @Value("${SERVER_NAME}")
    private String serverName;

    //@Value("${sms.usim.rcptNo.old}")
    //private String rcptNoOld;

    //@Value("${sms.usim.rcptNo}")
    //private String rcptNo;

    //@Value("${sms.request.rcptNo}")
    //private String reqRcptNo;


    /**
     * 본인인증 문자전송
     * @method	: sendSmsForAuth
     * @date	: 2014. 5. 28.
     * @author	: dev
     * @history	:
     *
     * @comment :
     *	KTBILL.P_SMS_B2C
     *	(
     *		I_EVENT				IN		VARCHAR2,	---	I_EVENT = WEBAUTH
     *		I_CTN				IN		VARCHAR2,	---	수신 전화번호
     *		I_CALLBACK			IN		VARCHAR2,	---	발신자번호 ( 고객센터 = '18992214' )
     *		I_MSG				IN		VARCHAR2,	---	문자내용(한글40자, 영문80자)
     *		I_CUST_YN			IN		VARCHAR2,	---	핸드폰 가입고객여부(Y/N)
     *		O_RET_CODE		OUT	VARCHAR2,	---	회신 '000' = 예약등록 성공, 나머지 실패
     *		O_RET_MSG		OUT	VARCHAR2	---	회신 실패시 에러메세지
     *	)
     *
     * @param rMobile
     * @param isCallbackOld
     * @param message
     * @return
     */

    @Override
    public Map<String, Object> sendSmsForAuth(String rMobile, boolean isCallbackOld, String message){
        Random random;
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        String f = "";
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            a = String.valueOf(random.nextInt(10));
            b = String.valueOf(random.nextInt(10));
            c = String.valueOf(random.nextInt(10));
            d = String.valueOf(random.nextInt(10));
            e = String.valueOf(random.nextInt(10));
            f = String.valueOf(random.nextInt(10));
        } catch (NoSuchAlgorithmException e1) {
            throw new McpErropPageException(COMMON_EXCEPTION);
        }

        String sendNo = a+b+c+d+e+f;


        String callBack = callCenter;//고객센터
        if(isCallbackOld){
            callBack = callCenterOld;//고객센터
        }

        String messageTem = message;
        if(null==messageTem || "".equals(messageTem)){
            messageTem = "[" + domain + "][인증번호:" + sendNo + "] 인증번호를 입력해 주세요";
        }

        // 테스트환경 확윈 위한 로그

        int key = this.sendSms(rMobile, eventCode, callBack, messageTem);

        // 테스트환경 확윈 위한 로그
        logger.debug(":::::::::::::::::::::send success????" + key);

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("sNumber",sendNo);//리턴값에 인증번호 세팅

        return map;
    }

    @Override
    public Map<String, Object> sendSmsUserCheck(String rName, String rMobile){
        Map<String, Object> map = new HashMap<String,Object>();
        return map;
    }


    /**
     * 신청현황 조회
     */
    @Override
    public Map<String, Object> sendSmsApply(String rName, String rMobile){
        Map<String, Object> map = new HashMap<String,Object>();
        return map;
    }

    @Override
    public int sendSms(String rMobile, String eventCode,String callBack, String message){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_EVENT",eventCode);
        params.add("I_MSG_TYPE","1");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",callBack);
        params.add("I_MSG",message);
        params.add("I_CUST_YN","N");
        params.add("O_RET_CODE","");
        params.add("O_RET_MSG","");
        params.add("Success","100");
        params.add("I_SUBJECT"," ");

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/sms/addSms", params, Integer.class); // smsSql.insertSms
    }


    @Override
    public int sendLms(String subject,String rMobile, String message){
        return this.sendLms(subject, rMobile,message,callCenter);
    }

    @Override
    public int sendLms(String subject,String rMobile, String message,String pCallCenter){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
        params.add("I_MSG_TYPE","3");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);
        params.add("I_MSG",message);
        params.add("Success","100");

           RestTemplate restTemplate = new RestTemplate();
           return restTemplate.postForObject(apiInterfaceServer + "/sms/addSms", params, Integer.class); // smsSql.insertSms
    }



    @Override
    public int sendKakaoNoti(String subject,String rMobile, String message,String pCallCenter, String kTemplateCd, String senderKey){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
        params.add("I_MSG_TYPE","6");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);
        params.add("I_MSG",message);
        params.add("I_NEXT_TYPE", "9");
        params.add("I_TEMPLATE_CD", kTemplateCd);
        params.add("I_SENDER_KEY", senderKey);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(apiInterfaceServer + "/sms/addKakaoNoti", params, Integer.class);
    }

    @Override
    public int sendMsgQueue(String subject,String rMobile, String message,String pCallCenter, String kTemplateCd, String senderKey){

        if (StringUtils.isBlank(kTemplateCd)) {
            return sendLms(subject,rMobile, message,pCallCenter);
        } else {
            return sendKakaoNoti(subject,rMobile, message,pCallCenter, kTemplateCd,  senderKey);
        }

    }

    @Override
    @Transactional
    public void sendSellUsimSms(boolean isCallbackOld, String message) {

        String callBack = callCenter;//고객센터
        if(isCallbackOld){
            callBack = callCenterOld;//고객센터
        }

        List<NmcpCdDtlDto> usimAdminSmsNumList = NmcpServiceUtils.getCodeList("USIMADMINSMSNUM"); //단기유심관리자 휴대폰 번호
        if (usimAdminSmsNumList != null) {
            for (NmcpCdDtlDto usimAdminSmsNum : usimAdminSmsNumList) {
                String messageTem = "[kt M모바일] USIM 해외 가입자가 등록되었습니다";
                this.sendSms(usimAdminSmsNum.getDtlCd(), eventCode, callBack, messageTem);
            }
        }

    }

    /*
     * 신규 문자모듈 발송(SMS)
     */
    @Override
    public Map<String, Object> sendSmsForAuth(String rMobile, boolean isCallbackOld, String message,String reserved02, String reserved03){
        Random random;
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        String f = "";
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            a = String.valueOf(random.nextInt(10));
            b = String.valueOf(random.nextInt(10));
            c = String.valueOf(random.nextInt(10));
            d = String.valueOf(random.nextInt(10));
            e = String.valueOf(random.nextInt(10));
            f = String.valueOf(random.nextInt(10));
        } catch (NoSuchAlgorithmException e1) {
            throw new McpErropPageException(COMMON_EXCEPTION);
        }

        String sendNo = a+b+c+d+e+f;


        String callBack = callCenter;//고객센터
        if(isCallbackOld){
            callBack = callCenterOld;//고객센터
        }

        String messageTem = message;
        if(null==messageTem || "".equals(messageTem)){
            messageTem = "[" + domain + "][인증번호:" + sendNo + "] 인증번호를 입력해 주세요";
        }

        // 테스트환경 확윈 위한 로그

        int key = this.sendSms(rMobile,callBack, messageTem,reserved02,reserved03);


        Map<String, Object> map = new HashMap<String,Object>();
        map.put("sNumber",sendNo);//리턴값에 인증번호 세팅

        return map;
    }

    /*
     * 신규 문자모듈 발송(SMS)
     */
    @Override
    public int sendSms(String rMobile, String callBack, String message, String reserved02, String reserved03){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_MSG_TYPE","1");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",callBack);
        params.add("I_MSG",message);
        params.add("I_SUBJECT","");
        params.add("RESERVED02",reserved02); //발송 목적
        params.add("RESERVED03",reserved03); //발송자
        if("DEV".equals(serverName) || "LOCAL".equals(serverName) || "STG".equals(serverName)){
            params.add("IS_REAL", "N");
        }else{
            params.add("IS_REAL", "Y");
        }

       RestTemplate restTemplate = new RestTemplate();
       return restTemplate.postForObject(apiInterfaceServer + "/sms/addNewSms", params, Integer.class);
    }

    /*
     * 신규 문자모듈 발송(LMS)
     */
    @Override
    public int sendLms(String subject,String rMobile, String message,String pCallCenter, String reserved02, String reserved03){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",StringUtil.substringByBytes(subject,0,120));
        params.add("I_MSG_TYPE","2");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);
        params.add("I_MSG",message);
        params.add("RESERVED02",reserved02); //발송 목적
        params.add("RESERVED03",reserved03); //발송자
        if("DEV".equals(serverName) || "LOCAL".equals(serverName) || "STG".equals(serverName)){
            params.add("IS_REAL", "N");
        }else{
            params.add("IS_REAL", "Y");
        }

       RestTemplate restTemplate = new RestTemplate();
       return restTemplate.postForObject(apiInterfaceServer + "/sms/addNewSms", params, Integer.class);
    }

    /*
     * 신규 문자모듈 발송
     */
    @Override
    public int sendMsgQueue(String subject , String rMobile, String message, String pCallCenter, String kTemplateCd, String senderKey,String reserved02, String reserved03) {
        if (StringUtils.isBlank(kTemplateCd)) {
            return sendLms(subject,rMobile, message,pCallCenter,reserved02,reserved03);
        } else {
            return sendKakaoNoti(subject,rMobile, message,pCallCenter, kTemplateCd, senderKey, reserved02);
        }
    }

    /*
     * 신규 문자모듈 발송(카카오톡)
     */
    @Override
    public int sendKakaoNoti(String subject,String rMobile, String message,String pCallCenter, String kTemplateCd, String senderKey, String reserved02){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
        params.add("I_MSG_TYPE","6");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);
        params.add("I_MSG",message);
        params.add("I_NEXT_TYPE", "2");
        params.add("I_TEMPLATE_CD", kTemplateCd);
        params.add("I_SENDER_KEY", senderKey);
        params.add("RESERVED02", reserved02);
        params.add("RESERVED03", "SYSTEM");

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(apiInterfaceServer + "/sms/addNewKakaoNoti", params, Integer.class);
    }




// DB 에 핀번호고 없는 경우 사용시 위한 주석 처리 - 조건에 맞추서 수정해서 사용
//	public  Map<String, Object> sendSmsId(String rMobile, String pin){
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("PIN", pin.trim());
//		String userId = (String) dao.selectByPk("User.selectFindIdList", map);
//
//		String I_EVENT = eventCode;	//정해진 코드
//		String I_CALLBACK = callCenter;//고객센터
//		String I_MSG = "[" + domain + "] 고객님의 아이디는"+userId+"입니다.";
//
//		map = this.sendSms(rMobile, I_EVENT, I_CALLBACK, I_MSG);
//
//		return map;
//	}
//
//
//	public  Map<String, Object> sendSmsPw(String rMobile, String pin){
//		Map<String, Object> map = new HashMap<String,Object>();
//
//		String sendPw = "";
//		StringBuffer sb = new StringBuffer();
//		StringBuffer sc = new StringBuffer("!");
//
//		for(int i = 0; i<4; i++){
//			sb.append((char)((Math.random() * 26)+97));
//		}
//		for(int i = 0; i<4; i++){
//			sb.append((char)((Math.random() * 10)+48));
//		}
//
//		sb.setCharAt((int)(Math.random()*4+4), sc.charAt((int)(Math.random()*sc.length()-1)));
//
//		sendPw = sb.toString();
//
//		String I_MSG = "[" + domain + "] 고객님의 임시 비밀번호는"+sendPw+"입니다.";
//
////		EncryptVO vo = new EncryptVO();
////		vo.setValue(sendPw);
////		sendPw = encryptService.encryptSHA(vo);
//		map.put("PIN", pin.trim());
//		map.put("PASSWORD", sendPw.trim());
////		dao.update("User.updateUserPw", map);
//
//		String I_EVENT = eventCode;	//정해진 코드
//		String I_CALLBACK = callCenter;//고객센터
//
//		map = this.sendSms(rMobile, I_EVENT, I_CALLBACK, I_MSG);
//
//		return map;
//	}
//
//
//	/**
//	 * 정회원인증, 다회선 인증
//	 */
//	public Map<String, Object> sendInfomationMemberCheck(String rName, String rMobile){
//		Map<String, Object> map = new HashMap<String,Object>();
//
//		map.put("MOBILE_NO", rMobile.trim());
//		map.put("NAME", rName.replaceAll("\\p{Z}",""));
//
//		//선불 고객 조회
//		String I_CNL = (String)dao.selectByPk("inside.selectPrepayment", rMobile.trim());
//		if(I_CNL == null || StringUtil.isEmpty(I_CNL)){//선불 고객이 아닐경우 조회
//			I_CNL = (String) dao.selectByPk("request.selectContractNoList", map);
//		}
//
//		if(StringUtil.isNotEmpty(I_CNL)){
//			map = this.sendSmsForAuth(rName, rMobile, false);
//			map.put("I_CNL", I_CNL);
//		}
//
//		return map;
//	}

    @Override
    public int sendMms(String subject,String rMobile, String fileUrl, String pCallCenter, int fileSize){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
        params.add("I_MSG_TYPE","3");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);


        //------------ MMS 관련 ----------------
        params.add("I_FILECNT","1"); // 파일갯수
        params.add("I_FILECNT_CHECKUP","1"); // 실제체크된 파일 갯수
        params.add("I_FILELOC1",fileUrl); // 파일위치
        params.add("I_FILESIZE1",fileSize);
        params.add("IS_FILE","Y");
        //-----------------------------------

        params.add("Success","100");

           RestTemplate restTemplate = new RestTemplate();
           return restTemplate.postForObject(apiInterfaceServer + "/sms/addSms", params, Integer.class);
    }

}
