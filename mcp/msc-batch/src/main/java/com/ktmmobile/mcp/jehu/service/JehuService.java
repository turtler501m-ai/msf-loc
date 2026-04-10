package com.ktmmobile.mcp.jehu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.jehu.dao.JehuDao;


@Service
public class JehuService {

    private static final Logger logger = LoggerFactory.getLogger(JehuService.class);

    @Autowired
    private JehuDao jehuDao;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${SERVER_NAME}")
    private String serverName;

    public int selectJehuSuggCount() {
        return jehuDao.selectJehuSuggCount();
    }

    public String selectJehuManager() {
        return jehuDao.selectJehuManager();
    }

    public int sendLms(String subject,String rMobile, String message,String pCallCenter){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
        params.add("I_MSG_TYPE","3");
        params.add("I_CTN",rMobile.replace("-", ""));
        params.add("I_CALLBACK",pCallCenter);
        params.add("I_MSG",message);

       RestTemplate restTemplate = new RestTemplate();
       return restTemplate.postForObject(apiInterfaceServer + "/sms/addSms", params, Integer.class);
    }

    /*
     * 신규 문자모듈 발송(LMS)
     */
    public int sendLms(String subject,String rMobile, String message,String pCallCenter, String reserved02, String reserved03){

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String,Object>();
        params.add("I_SUBJECT",subject);
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

    public int selectDelCount() {
        return jehuDao.selectDelCount();
    }

    public int deleteJehuData() {
        return jehuDao.deleteJehuData();
    }

}
